<?php

$dbHandler = new mysqli('localhost', 'root', 'toor', 'interactive_ppt');
$dbHandler->set_charset("utf8");
if (isset($_POST['json'])) {
    $_POST = array_merge($_POST, json_decode($_POST['json']));
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
                move_uploaded_file($userfile, "img/$filename");
                $fileUri = "'img/$filename'";
            }            
        }

        $author = $dbHandler->query("SELECT idUser FROM User WHERE facebook_id='$facebookId' LIMIT 1;")->fetch_assoc()['idUser'];
        $command = "INSERT INTO Survey VALUES (default, '$title', '$description', '$accessCode', $fileUri, $author);";
        $dbHandler->query($command);

        $idSurvey = $$dbHandler->insert_id;

        if (count($questions)) {
            foreach ($questions as $q) {
                $dbHandler->query("INSERT INTO Questions VALUES ('$q[text]', 0, 0, $q[type], $idSurvey);");

                $idQuestion = $$dbHandler->insert_id;
                foreach ($q['answers'] as $o) {
                    $optionRecordSet = $dbHandler->query("SELECT idOptions FROM Options WHERE choice_name='$o[text]' LIMIT 1;");
                    $idAnswer = -1;
                    if ($optionRecordSet->num_rows) {
                        $idAnswer = $optionRecordSet->fetch_assoc()['idOptions'];
                    }
                    $optionRecordSet->free();
                    if ($idAnswer === -1) {
                        $dbHandler->query("INSERT INTO Options VALUES (default, '$o[text]');");
                        $idAnswer = $dbHandler->insert_id;
                    }
                    $dbHandler->query("INSERT INTO Question_options VALUES ($idQuestion, $idAnswer);");
                }
            }
        }

        echo '{success:true}';
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
        $email = $_POST['address'];
        $command = "SELECT s.name,s.access_code,s.link_to_presentation FROM Survey s, Users u WHERE u.address='$email' AND u.idUser=s.author;";
        $recordSet = $dbHandler->query($command);
        $outputArray = array();
        if ($recordSet) {
            for ($i=0 ; $i < $recordSet->num_rows ; $i++) {
                array_push($outputArray, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }
        echo '{' . json_encode($outputArray) . '}';
        
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
        $id = $_POST['id'];
        $command = "";    //              NEED TO BE ADDED
        $recordSet = $dbHandler->query($command);
        $recordSet->free();

        break;
    case 'get_results':
        $id = $_POST['id'];
        $command = "";    //              NEED TO BE ADDED
        $recordSet = $dbHandler->query($command);
        $recordSet->free();

        break;
    case 'submit_answers':
        $id = $_POST['id'];
        $command = "";    //              NEED TO BE ADDED
        $result = $dbHandler->query($command);

        break;
}
$dbHandler->close();

?>
