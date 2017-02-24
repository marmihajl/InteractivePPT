<?php

$dbHandler = new mysqli('localhost', 'root', 'toor', 'interactive_ppt');
$dbHandler->set_charset("utf8");
if (isset($_POST['json'])) {
    $_POST = array_merge($_POST, json_decode($_POST['json'], true));
    $userfile = null;
    $filename;
    if (!empty($_FILES)) {
        $keys = array_keys($_FILES);
        $userfile = $_FILES[$keys[0]]['tmp_name'];
        $filename = $_FILES[$keys[0]]['name'];
    }
}
switch ($_POST['request_type']) {
    case 'create_survey':
        $survey = $_POST['survey'];
        $title = $survey['name'];
        $description = $survey['description'];
        $facebookId = $survey['author'];
        $questions = $survey['questions'];
        $accessCode;
        if (isset($survey['access_code'])) {
            $accessCode = $survey['access_code'];
            $command = "INSERT INTO Survey VALUES (default, '$title', '$description', '$accessCode');SET @survey := LAST_INSERT_ID();";
        }
        else {
            srand(time(0));
            rand();
            do {
                $accessCode = '';
                for ($i=0 ; $i<10 ; $i++) {
                    switch (rand(0,1)) {
                        case 0:
                            $accessCode .= rand(0,9);
                            break;
                        case 1:
                            $accessCode .= chr(rand(97,122));
                            break;
                    }
                }
                $recordSet = $dbHandler->query("SELECT * FROM Presentation WHERE access_code='$accessCode' LIMIT 1;");
            } while ($recordSet->num_rows > 0);
            $recordSet->free();
            $fileUri = 'null';
            if ($userfile!==null) {
                if (is_uploaded_file($userfile)) {
                    move_uploaded_file($userfile, "ppt/$filename");
                    $fileUri = "'ppt/$filename'";
                    $fileChecksum = hash_file('md5', trim($fileUri, "'"));
                }            
            }
            $command = "INSERT INTO Presentation VALUES (default, $fileUri, '$fileChecksum', '$accessCode', (SELECT idUser FROM Users WHERE app_uid='$facebookId' LIMIT 1));INSERT INTO Survey VALUES (default, '$title', '$description', '$accessCode');SET @survey := LAST_INSERT_ID();";
        }


        if (count($questions)) {
            foreach ($questions as $q) {
                $command .= "INSERT INTO Questions VALUES (default, '$q[name]', $q[required_answer], $q[type], @survey);SET @question := LAST_INSERT_ID();";
                $questionOptionsCommand = "INSERT INTO Question_options VALUES ";
                $defaultQuestionOptionsCommand = "INSERT INTO Default_question_options(idOption, idQuestion) VALUES ";
                $valuesToInsert = "";
                foreach ($q['options'] as $o) {
                    $valuesToInsert .= "(createOptionAndGetId('$o[name]'), @question),";
                }
                if (!empty($q['options'])) {
                    $valuesToInsert = substr_replace($valuesToInsert, ';', strlen($valuesToInsert)-1, 1);
                    $command .= $questionOptionsCommand . $valuesToInsert . $defaultQuestionOptionsCommand . $valuesToInsert;
                }
            }
        }
        $dbHandler->multi_query($command);

        echo 'true';
        break;
    case 'get_surveys':
        $appUid = $_POST['app_uid'];
        $command = "SELECT s.idSurvey AS \"id\",s.name,p.access_code,p.path AS \"link_to_presentation\" FROM Presentation p JOIN Users u ON p.author=u.idUser LEFT JOIN Survey s ON s.access_code=p.access_code WHERE u.app_uid='$appUid';";
        $recordSet = $dbHandler->query($command);
        $outputArray = array();
        if ($recordSet) {
            for ($i=0 ; $i < $recordSet->num_rows ; $i++) {
                array_push($outputArray, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }
        echo '{"data":' . json_encode($outputArray) . '}';
        
        break;
    case 'get_questions':
        $survey = $_POST['survey_id'];
        $command = "SELECT q.idQuestions, q.name, q.Question_type_idQuestion_type FROM Questions q, Survey s WHERE s.idSurvey='$survey' AND s.idSurvey=q.Survey_idSurvey;";
        $recordSet = $dbHandler->query($command);
        $outputArray = array();
        if ($recordSet) {
            for ($i=0 ; $i < $recordSet->num_rows ; $i++) {
                array_push($outputArray, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }
        echo '{"questions":' . json_encode($outputArray) . '}';

        break;
	case 'get_survey':
		$idSurvey=$_POST['survey_id'];
        $command = "SELECT s.name, s.description, p.path FROM Survey s, Presentation p WHERE s.idSurvey=$idSurvey AND p.access_code=s.access_code;";
        $recordSet = $dbHandler->query($command);
        if ($recordSet) {
            $surveyInfo = $recordSet->fetch_assoc();
            $result = "{\"name\":\"$surveyInfo[name]\", \"description\":\"$surveyInfo[description]\", \"link_to_presentation\":\"$surveyInfo[link_to_presentation]\", \"questions\":[";
            $recordSet->free();
            $command= "SELECT question_details FROM getQuestionDetails WHERE idSurvey = $idSurvey;";
            $recordSet= $dbHandler->query($command);
            if($recordSet){
                for($i = 0; $i < $recordSet->num_rows; $i++){
                    $result .= $recordSet->fetch_row()[0] . ',';
                }
                $recordSet->free();
                $result = rtrim($result, ",");
            }
            echo $result . ']}';            
        }
        else {
            echo 'null';
        }
		
		break;
    case 'get_results':
        $question = $_POST['id'];
        $command = "SELECT o.choice_name, count(o.idOptions) AS count FROM Answers a LEFT JOIN Options o ON a.idOption=o.idOptions WHERE a.idQuestion=$question GROUP BY o.idOptions;";
        $recordSet = $dbHandler->query($command);
        $outputArray = array();
        if ($recordSet) {
            for ($i=0 ; $i < $recordSet->num_rows ; $i++) {
                array_push($outputArray, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }
        echo '{"results":' . json_encode($outputArray, JSON_NUMERIC_CHECK) . '}';

        break;
		
	case 'get_text_results':
        $question = $_POST['id'];
        $command = "SELECT o.choice_name FROM Answers a LEFT JOIN Options o ON a.idOption=o.idOptions WHERE a.idQuestion=$question;";
        $recordSet = $dbHandler->query($command);
        $outputArray = array();
        if ($recordSet) {
            for ($i=0 ; $i < $recordSet->num_rows ; $i++) {
                array_push($outputArray, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }
        echo '{"results":' . json_encode($outputArray, JSON_NUMERIC_CHECK) . '}';

        break;
		
	case 'get_presentation':
        $accessCode = $_POST['access_code'];
        $command = "SELECT presentation_details FROM getPresentationDetails WHERE access_code='$accessCode' LIMIT 1;";
        $recordSet = $dbHandler->query($command);
        if ($recordSet && $recordSet->num_rows) {
			echo $recordSet->fetch_row()[0];
            $recordSet->free();
        }
		else {
            echo 'null';
        }
        break;
	
    case 'submit_answers':
        $answers = json_decode($_POST['answers'], true);
        $appUid = $answers['app_uid'];
        $answers = $answers['data'];
        $userId = $dbHandler->query("SELECT idUser FROM Users WHERE app_uid='$appUid' LIMIT 1;")->fetch_row()[0];
        $answeredQuestions = array();
        foreach ($answers as $a) {
            array_push($answeredQuestions, $a['id_question']);
        }
        $answeredRange = implode(',', $answeredQuestions);
        if ($dbHandler->query("SELECT * FROM Answers a WHERE a.idUser=$userId AND a.idQuestion IN ($answeredRange) LIMIT 1;")->num_rows) {
            echo 'false';
        }
        else {
            $command="INSERT INTO Answers VALUES";
            if(count($answers)){
                foreach ($answers as $a){
                    $command.= "(default, $userId, '$a[id_question]', createOptionAndGetId('$a[option_name]')),";
                }
                $command = rtrim($command, ",");
                $dbHandler->query($command);
            }
            echo 'true';
        }
		
        break;
    case 'get_user_info':
        $appUid = $_POST['app_uid'];
        $command = "SELECT name FROM Users WHERE app_uid='$appUid';";
        $nameOfUser = $dbHandler->query($command)->fetch_array()['name'];
        echo $nameOfUser; 

        break;
    case 'register_user':
        $appUid = $_POST['app_uid'];
        $fullName = $_POST['name'];
        $token = $_POST['token'];
        if ($dbHandler->query("SELECT * FROM Users WHERE app_uid='$appUid';")->num_rows === 0) {
            $dbHandler->query("INSERT INTO Users VALUES (default, '$fullName', '$appUid', 3, '$token');");
        }
        else {
            $dbHandler->query("UPDATE Users SET name='$fullName', token='$token' WHERE app_uid='$appUid';");
        }
        echo 'true';

        break;
    case 'delete_file':
	    $data=$_POST['file'];
        $dir = "ppt";
        $dirHandle = opendir($dir);
        while ($file = readdir($dirHandle)) {
            if($file==$data) {
                unlink($dir.'/'.$file);
            }
        }
        closedir($dirHandle);

    	break;
    case 'get_presentation_list':
        $uid = $_POST['uid'];
        $userPpts = array();
        $subbedPpts = array();
        $recordSet = $dbHandler->query("SELECT count(s.access_code) AS \"num_of_surveys\", p.access_code, (SELECT p2.path FROM Presentation p2 WHERE p.access_code=p2.access_code) AS \"path\" FROM Presentation p LEFT JOIN Survey s ON s.access_code=p.access_code WHERE p.author=(SELECT idUser FROM Users WHERE app_uid='$uid') GROUP BY p.access_code, s.access_code;");
        if ($recordSet) {
            for ($i=0; $i<$recordSet->num_rows; $i++) {
                array_push($userPpts, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }

        $recordSet = $dbHandler->query("SELECT count(s.access_code) AS \"num_of_surveys\", p.access_code, (SELECT p2.path FROM Presentation p2 WHERE p.access_code=p2.access_code) AS \"path\", (SELECT u2.name FROM Users u2 WHERE u.idUser=u2.idUser LIMIT 1) AS \"author_name\" FROM Presentation p JOIN Subscription sub ON sub.idUser=(SELECT idUser FROM Users WHERE app_uid='$uid') AND sub.idPresentation=p.id LEFT JOIN Users u ON u.idUser=p.author LEFT JOIN Survey s ON s.access_code=p.access_code GROUP BY p.access_code, s.access_code, u.idUser;");
        if ($recordSet) {
            for ($i=0; $i<$recordSet->num_rows; $i++) {
                array_push($subbedPpts, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }
        echo json_encode(array('my_ppts' => $userPpts, 'subbed_ppts' => $subbedPpts));

        break;
		
	case 'save_subscription':
	
		$id = $_POST['id'];
		$path = $_POST['path'];
		
		$command = "SELECT id FROM Presentation WHERE path = '$path' LIMIT 1;";	
		$result = $dbHandler->query($command);
		$row = $result->fetch_assoc();
		$presentationID = (int)$row['id'];
		
		$command = "SELECT idUser FROM Users WHERE app_uid = '$id' LIMIT 1;";	
		$result = $dbHandler->query($command);
		$row = $result->fetch_assoc();
		$id2 = (int)$row['idUser'];
		$command = "SELECT * FROM Subscription WHERE idUser = $id2 AND idPresentation = $presentationID;";
		if($dbHandler->query($command)->num_rows === 0){
			$command = "INSERT INTO Subscription VALUES($id2,$presentationID, default);";	
			$dbHandler->query($command);
		}
        else {
			$command = "UPDATE Subscription SET active = 'yes' WHERE idUser = $id2 AND idPresentation = $presentationID;";	
			$dbHandler->query($command);
		}
		
		echo 'true';
		
		break;
		
	case 'update_subscription':
	
        $path = $_POST['path'];
        
        $command = "UPDATE Subscription SET active = 'no' WHERE idPresentation = (SELECT id FROM Presentation WHERE path = '$path');";
        $dbHandler->query($command);
        
        echo 'true';
        
        break;
	
	case 'check_status':
	
		$id = $_POST['id'];
		$path = $_POST['path'];
		
		$command = "SELECT active FROM Subscription WHERE idUser = (SELECT idUser FROM Users WHERE app_uid = '$id') AND idPresentation = (SELECT id FROM Presentation WHERE path = '$path');";
		$result = $dbHandler->query($command)->fetch_assoc()['active'];
		
		if($result === "yes") {
            echo 'yes';
        }
		else {
            echo 'no';
        }
	
    	break;

    case 'save_interested_user':
        $path = $_POST['path'];
        $userUid = $_POST['id'];

        $command = "SET @pptid := (SELECT id FROM Presentation WHERE path = '$path' LIMIT 1);INSERT INTO Reply_request VALUES ((SELECT idUser FROM Users WHERE app_uid = '$userUid' LIMIT 1), @pptid, CURRENT_TIMESTAMP());SELECT @pptid;";
        if ($dbHandler->multi_query($command)) {
            $dbHandler->next_result();
            do {
                if ($result = $dbHandler->store_result()) {
                    $pptId = $result->fetch_row()[0];
                    $result->free();
                }
                else {
                    if ($dbHandler->affected_rows === -1) {
                        $dbHandler->close();
                        die('false');
                    }
                }
            } while ($dbHandler->more_results() && $dbHandler->next_result());
            if (!msg_queue_exists($pptId)) {
                echo 'false';
            }
            else {
                $queue = msg_get_queue($pptId);
                if ($queue === false) {
                    echo 'false';
                }
                else {
                    $userName = $dbHandler->query("SELECT name FROM Users WHERE app_uid='$userUid' LIMIT 1;")->fetch_row()[0];
                    $message = "$userUid\t$userName\t\t";
                    msg_receive(msg_get_queue(-$pptId), 0, $receivedType, 1, $irrelevantData, false);
                    msg_send($queue, 1, $message, false);
                    echo 'true';
                }
            }
        }
        else {
            echo 'false';
        }
        break;

    case 'get_notifiers_listening_port':
        $path = $_POST['path'];

        setlocale(LC_CTYPE, "en_US.UTF-8");
        $descriptorspec = array(
            1 => array("pipe", "w")
        );

        $pptId = $dbHandler->query("SELECT id FROM Presentation WHERE path='$path' LIMIT 1;")->fetch_row()[0];

        $process = proc_open('nohup php -f notifier.php -- ' . $pptId . ' &', $descriptorspec, $pipes);
        if (is_resource($process)) {
            echo stream_get_contents($pipes[1]);
        }

        break;

    case 'send_chat_message':
        $pptId = $_POST['pptid'];
        $userUid = $_POST['userid'];
        $content = $_POST['content'];

        if (!msg_queue_exists($pptId)) {
            echo 'false';
        }
        else {
            $queue = msg_get_queue($pptId);
            if ($queue === false) {
                echo 'false';
            }
            else {
                $userName = $dbHandler->query("SELECT name FROM Users WHERE app_uid='$userUid' LIMIT 1;")->fetch_row()[0];
                $message = "$userUid\t$userName\t$content\t";
                msg_receive(msg_get_queue(-$pptId), 0, $receivedType, 1, $irrelevantData, false);
                msg_send($queue, 1, $message, false);
                echo 'true';
            }
        }

        break;

    case 'delete_audience':
        $userUid = $_POST['app_uid'];
        $path = $_POST['path'];

        $command = "DELETE FROM Reply_request WHERE presentation = (SELECT id FROM Presentation WHERE path='$path' LIMIT 1) AND user = (SELECT idUser FROM Users WHERE app_uid='$userUid' LIMIT 1);";
        $dbHandler->query($command);

        break;
    case 'get_survey_list':
        $pptPath = $_POST['ppt_path'];
        $command = "SELECT s.idSurvey AS \"id\", s.name, s.description, count(s.idSurvey) AS \"num_of_questions\" FROM Survey s JOIN Questions q ON s.idSurvey=q.Survey_idSurvey JOIN Presentation p WHERE p.path='$pptPath' AND s.access_code=p.access_code GROUP BY s.idSurvey;";
        $recordSet = $dbHandler->query($command);
        $outputArray = array();
        if ($recordSet) {
            for ($i=0 ; $i < $recordSet->num_rows ; $i++) {
                array_push($outputArray, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }
        echo '{"surveys":' . json_encode($outputArray, JSON_NUMERIC_CHECK) . '}';
        
        break;
    case 'get_file_checksum':
        $path = $_POST['path'];
        echo hash_file('md5', $path);

        break;
    case 'shutdown_listener':
        $path = $_POST['path'];
        $pptId = $dbHandler->query("SELECT id FROM Presentation WHERE path='$path' LIMIT 1;")->fetch_row()[0];

        $queue = msg_get_queue($pptId);
        if ($queue === false) {
            echo 'false';
        }
        else {
            msg_receive(msg_get_queue(-$pptId), 0, $receivedType, 1, $irrelevantData, false);
            msg_send($queue, 1, "0\texit\t\t", false);
            echo 'true';
        }
        
        break;
    case 'kill_connection':
        $path = $_POST['path'];
        $userUid = $_POST['id'];
        $pptId = $dbHandler->query("SELECT id FROM Presentation WHERE path='$path' LIMIT 1;")->fetch_row()[0];

        $queue = msg_get_queue($pptId);
        if ($queue === false) {
            echo 'false';
        }
        else {
            msg_receive(msg_get_queue(-$pptId), 0, $receivedType, 1, $irrelevantData, false);
            msg_send($queue, 1, "$userUid\texit\t\t", false);
            echo 'true';
        }
        
        break;
}
$dbHandler->close();

?>
