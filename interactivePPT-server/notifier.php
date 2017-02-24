<?php
const MEM_SEGMENT_SIZE = 102400;
const MAX_CLIENTS_NUM = 1000;

function str_to_nts($value) {
  return "$value\0";
}

function str_from_mem($value) {
    $i = strpos($value, "\0");
    if ($i === false) {
        return $value;
    }
    $result =  substr($value, 0, $i);
    return $result;
}

function handle_client($ssock, $csock) {
    global $main_process;

    $pid = pcntl_fork();

    if ($pid == -1) {
        /* fork failed */
        echo "fork failure!\n";
        die;
    }
    elseif ($pid == 0) {
        /* child process */
        $main_process = false;
        socket_close($ssock);
        interact($csock);
        socket_close($csock);
    }
    else {
        socket_close($csock);
    }
}

function get_ppt_process_info($communicationInfoMemSeg) {
    $retval = json_decode(str_from_mem(shmop_read($communicationInfoMemSeg, 0, 0)), true);
    return $retval;
}

function set_ppt_process_info($communicationInfoMemSeg, $assocArray) {
    shmop_write($communicationInfoMemSeg, str_to_nts(json_encode($assocArray)), 0);
}

function interact($csock) {
    global $pptId;
    global $semNewMessage;
    global $semCommunicationInfoChange;
    global $semLikeQueue;
    global $parentProcess;

    $clientPresenter = false;
    $communicationInfoMemSeg = shmop_open(-$pptId, 'w', 0666, 50);
    $messageContentMemSeg = shmop_open($pptId, 'w', 0666, MEM_SEGMENT_SIZE);

    sem_acquire($semCommunicationInfoChange);
    $pptProcessInfo = get_ppt_process_info($communicationInfoMemSeg);
    $procNum = $pptProcessInfo['process_num'];
    if ($procNum == -1) {  //za rad s desktop aplikacijom
        $clientPresenter = true;
        $procNum++; //ako će i desktop aplikacija sudjelovati u chatu
        shmop_close($communicationInfoMemSeg);
        shmop_close($messageContentMemSeg);

        switch (pcntl_fork()) {
            case 0:
                $communicationInfoMemSeg = shmop_open(-$pptId, 'w', 0666, 50);
                $messageContentMemSeg = shmop_open($pptId, 'w', 0666, MEM_SEGMENT_SIZE);
                msg_send($semLikeQueue, 1, 'x', false);
                $keepRunning = true;
                $queue = msg_get_queue($pptId);
                while ($keepRunning) {
                    msg_receive($queue, 0, $receivedType, 102400, $message, false);
                    if ($message === "0\texit\t\t") {
                        $keepRunning = false;
                    }

                    sem_acquire($semCommunicationInfoChange);
                    $pptProcessInfo = get_ppt_process_info($communicationInfoMemSeg);
                    $oldAllMessageLength = $pptProcessInfo['content_length'];
                    $pptProcessInfo['content_length'] += strlen($message);
                    set_ppt_process_info($communicationInfoMemSeg, $pptProcessInfo);
                    shmop_write($messageContentMemSeg, str_to_nts($message), $oldAllMessageLength);
                    for ($i=0; $i<$pptProcessInfo['process_num']; $i++) {
                        sem_release($semNewMessage);
                    }
                    sem_release($semCommunicationInfoChange);
                    usleep(200000);
                }
                msg_remove_queue($queue);
                shmop_close($communicationInfoMemSeg);
                shmop_close($messageContentMemSeg);
                return;
            default:
                $communicationInfoMemSeg = shmop_open(-$pptId, 'w', 0666, 50);
                $messageContentMemSeg = shmop_open($pptId, 'w', 0666, MEM_SEGMENT_SIZE);
                break;
        }
    }
    else {
        socket_recv($csock, $uid, 50, 0);
    }
    $procNum++;
    $pptProcessInfo['process_num'] = $procNum;
    set_ppt_process_info($communicationInfoMemSeg, $pptProcessInfo);
    sem_release($semCommunicationInfoChange);

    $content = str_from_mem(shmop_read($messageContentMemSeg, 0, 0));
    $totalBytesReceived = strlen($content);     //trebalo bi biti jednako $pptProcessInfo['content_length']
    if ($totalBytesReceived) {
        socket_send($csock, str_replace("\n", "\f", $content) . "\n", $totalBytesReceived + 1, 0);
    }
    while (sem_acquire($semNewMessage)) { //sem je brojački semafor s maskimalnim brojem jednakim broju procesa - hint: pamtiti broj paralelnih procesa (isključujući onaj od desktop app, ak tam ne bude prikaza poruka)
        $newContent = shmop_read($messageContentMemSeg, $totalBytesReceived, 0);
        $bytesReceived = strpos($newContent, "\0");     // strlen($newContent) ne valja jer PHP tretira nullstring (\0) kao obične znakove
        if ($bytesReceived) {
            $messageInfo = explode("\t", $newContent);
            if ($messageInfo[1] === 'exit') {
                if ($messageInfo[0] === '0') {
                    if ($clientPresenter) {
                        msg_send($semLikeQueue, 1, 'x', false);
                    }
                    socket_send($csock, "exit\n", strlen("exit\n"), 0);
                    break;  //ubija sve konekcije
                }
                elseif (isset($uid) && $uid === $messageInfo[1]) {
                    socket_send($csock, "exit\n", strlen("exit\n"), 0);
                    break;  //ubija konekciju čiji klijent je zatražio prekid
                }
            }
            elseif ($messageInfo[2] === '') {
                if ($clientPresenter) {
                    socket_send($csock, $newContent, $bytesReceived, 0); // šalji notifikaciju o zainteresiranosti desktop aplikaciji
                }
            }
            else {
                socket_send($csock, $newContent, $bytesReceived, 0);
            }
            $totalBytesReceived += $bytesReceived;
        }
        if ($clientPresenter) {
            msg_send($semLikeQueue, 1, 'x', false);
        }
        usleep(100000);
    }

    sem_acquire($semCommunicationInfoChange);
    $pptProcessInfo = get_ppt_process_info($communicationInfoMemSeg);
    $pptProcessInfo['process_num']--;
    set_ppt_process_info($communicationInfoMemSeg, $pptProcessInfo);
    sem_release($semCommunicationInfoChange);

    if ($clientPresenter) {
        posix_kill($parentProcess, SIGTERM);
        sleep(1);   // pretpostavimo da nakon tog vremena više nitko neće koristiti sljedeće resurse
        shmop_delete($communicationInfoMemSeg);
        shmop_delete($messageContentMemSeg);
        shmop_close($communicationInfoMemSeg);
        shmop_close($messageContentMemSeg);        
        sem_remove($semNewMessage);
        sem_remove($semCommunicationInfoChange);
        msg_remove_queue($semLikeQueue);
    }
    else {
        shmop_close($communicationInfoMemSeg);
        shmop_close($messageContentMemSeg);
    }
}

$main_process = true;
if ($argc === 2) {
    $pptId = $argv[1];
    $parentProcess = getmypid();
    $port = 50000 + $pptId;
    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
    if (is_resource($socket) && socket_bind($socket, '46.101.247.168', $port) && socket_listen($socket, 0)) {
        echo $port;
        fclose(STDOUT);

        $communicationInfoMemSeg = shmop_open(-$pptId, 'c', 0666, 50);
        set_ppt_process_info($communicationInfoMemSeg, array('process_num' => -1, 'content_length' => 0));       // -1 u ovom slučaju ima svrhu da prvi proces-dijete (koji će služiti za komunikaciju s desktop aplikacijom) razluči od ostalih procesa-djeteta
        shmop_close($communicationInfoMemSeg);

        shmop_close(shmop_open($pptId, 'c', 0666, MEM_SEGMENT_SIZE));

        // izbriši IPC controls if they somehow survived previous usage
        sem_remove(sem_get($pptId, MAX_CLIENTS_NUM));
        sem_remove(sem_get(-$pptId));
        msg_remove_queue(msg_get_queue($pptId));
        msg_remove_queue(msg_get_queue(-$pptId));

        $semNewMessage = sem_get($pptId, MAX_CLIENTS_NUM);
        $semCommunicationInfoChange = sem_get(-$pptId);
        $semLikeQueue = msg_get_queue(-$pptId);

        for ($i=0; $i<MAX_CLIENTS_NUM; $i++) {
            sem_acquire($semNewMessage);
        }

        while ($main_process) {
            $connection = @socket_accept($socket);

            if ($connection > 0) {
                handle_client($socket, $connection);
            }
        }
    }
    else {
        echo 'error';
    }

}
?>
