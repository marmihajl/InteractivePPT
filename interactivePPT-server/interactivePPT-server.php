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
        $title = $_POST['title'];
        $description = $_POST['description'];
        $facebookId = $_POST['author'];
        $questions = $_POST['questions'];
        $accessCode;
        srand(time(0));
        rand();
        do {
            $accessCode = '';
            for ($i=0 ; $i<15 ; $i++) {
                $accessCode .= chr(rand(33,126));
            }
            $recordSet = $dbHandler->query("SELECT * FROM Survey WHERE access_code='$accessCode' LIMIT 1;");
        } while ($recordSet->num_rows > 0);
        $recordSet->free();
        $fileUri = 'null';
        if ($userfile!==null) {
            if (is_uploaded_file($userfile)) {
                move_uploaded_file($userfile, "ppt/$filename");
                $fileUri = "'ppt/$filename'";
            }            
        }

        $author = $dbHandler->query("SELECT idUser FROM Users WHERE app_uid='$facebookId' LIMIT 1;")->fetch_assoc()['idUser'];
        $command = "INSERT INTO Survey VALUES (default, '$title', '$description', '$accessCode', $fileUri, $author);";
        $dbHandler->query($command);

        $idSurvey = $dbHandler->insert_id;

        if (count($questions)) {
            foreach ($questions as $q) {
                $dbHandler->query("INSERT INTO Questions VALUES (default, '$q[text]', 0, 0, $q[type], $idSurvey);");

                $idQuestion = $dbHandler->insert_id;
                foreach ($q['answers'] as $o) {
                    $optionRecordSet = $dbHandler->query("SELECT o.idOptions FROM Options o, Question_options qo WHERE qo.idQuestions=$idQuestion AND o.choice_name='$o[text]' LIMIT 1;");
                    $idAnswer = -1;
                    if ($optionRecordSet->num_rows) {
                        $idAnswer = $optionRecordSet->fetch_assoc()['idOptions'];
                    }
                    $optionRecordSet->free();
                    if ($idAnswer === -1) {
                        $dbHandler->query("INSERT INTO Options VALUES (default, '$o[text]');");
                        $idAnswer = $dbHandler->insert_id;
                    }
                    $dbHandler->query("INSERT INTO Question_options VALUES ($idAnswer, $idQuestion);");
                }
            }
        }

        echo 'true';
        break;
    case 'edit_survey':
        $id = $_POST['id'];
        $newTitle = $_POST['title'];
        $newDescription = $_POST['description'];
        $command = "";    //              NEED TO BE ADDED
        $result = $dbHandler->query($command);

        break;
    case 'delete_survey':
        $id = $_POST['id'];
        $command = "";    //              NEED TO BE ADDED
        $result = $dbHandler->query($command);

        break;
    case 'get_surveys':
        $appUid = $_POST['app_uid'];
        $command = "SELECT s.name,s.access_code,s.link_to_presentation FROM Survey s, Users u WHERE u.app_uid='$appUid' AND u.idUser=s.author;";
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
    case 'get_details':
        $accessCode = $_POST['access_code'];
        $command = "SELECT name, description, link_to_presentation FROM Survey WHERE access_code='$accessCode';";    //              NEED TO BE ADDED
        $recordSet = $dbHandler->query($command);
        $outputArray = array();
        if ($recordSet) {
            for ($i=0 ; $i < $recordSet->num_rows ; $i++) {
                array_push($outputArray, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }
        echo json_encode($outputArray);

        break;
    case 'get_questions':
        $survey = $_POST['access_code'];
        $command = "SELECT q.idQuestions, q.name FROM Questions q, Survey s WHERE s.access_code='$survey' AND s.idSurvey=q.Survey_idSurvey;";
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
    case 'get_results':
        $question = $_POST['id'];
        $command = "SELECT o.choice_name, count(o.idOptions) AS count FROM Answers a LEFT JOIN Options o ON a.Options_idOptions=o.idOptions LEFT JOIN Question_options qo ON qo.idOptions=o.idOptions LEFT JOIN Questions q ON q.idQuestions=qo.idQuestions WHERE q.idQuestions=$question GROUP BY o.idOptions;";
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
    case 'submit_answers':
        $id = $_POST['id'];
        $command = "";    //              NEED TO BE ADDED
        $result = $dbHandler->query($command);

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
        if ($dbHandler->query("SELECT * FROM Users WHERE app_uid='$appUid';")->num_rows === 0) {
            $dbHandler->query("INSERT INTO Users VALUES (default, '$fullName', '$appUid', 3);");
        }
        else {
            $dbHandler->query("UPDATE Users SET name='$fullName' WHERE app_uid='$appUid';");
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

}
$dbHandler->close();

?>
