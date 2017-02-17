<?php
if ($argc === 2) {
    $pptId = $argv[1];
    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
    if (is_resource($socket)) {
        $port = 50000 + $pptId;
        socket_bind($socket, '46.101.247.168', $port);
        echo $port;
        fclose(STDOUT);
        socket_listen($socket);
        $connection = socket_accept($socket);
        if (is_resource($connection)) {
            $queue = msg_get_queue($pptId);
            while (true) {
                msg_receive($queue, 0, $receivedType, 256, $userId, false);
                if ($userId === 'exit') {
                    break;
                }
                $dbHandler = new mysqli('localhost', 'root', 'toor', 'interactive_ppt');
                $dbHandler->set_charset("utf8");
                $userName = $dbHandler->query("SELECT name FROM Users WHERE app_uid='$userId' LIMIT 1;")->fetch_row()[0];
                $messageContent = $userId . '-' . $userName;
                $dbHandler->close();
                socket_send($connection, $messageContent, strlen($messageContent), 0);
            }
            socket_close($socket);
            socket_close($connection);
            msg_remove_queue($queue);
        }
        else {
            error_log('konekcija ocito nije inicijalizirana');
        }
    }
    else {
        error_log('socket ocito nije inicijaliziran');
    }

}
?>
