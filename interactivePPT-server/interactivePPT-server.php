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

        $command = "INSERT INTO Survey VALUES (default, '$title', '$description', '$accessCode', $fileUri, (SELECT idUser FROM Users WHERE app_uid='$facebookId' LIMIT 1));SET @survey := LAST_INSERT_ID();";

        if (count($questions)) {
            foreach ($questions as $q) {
                $command .= "INSERT INTO Questions VALUES (default, '$q[name]', $q[required_answer], $q[type], @survey);SET @question := LAST_INSERT_ID();";
                $command .= "INSERT INTO Question_options VALUES ";
                foreach ($q['options'] as $o) {
                    $command .= "(createOptionAndGetId('$o[name]'), @question),";
                }
                $command[strlen($command)-1] = ';';
            }
        }
        $dbHandler->multi_query($command);

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
	case 'get_survey':
		$surveyAccessCode=$_POST['access_code'];
        $command = "SELECT name, description, link_to_presentation FROM Survey WHERE access_code='$surveyAccessCode';";
        $recordSet = $dbHandler->query($command);
        if ($recordSet) {
            $surveyInfo = $recordSet->fetch_assoc();
            $result = "{\"name\":\"$surveyInfo[name]\", \"description\":\"$surveyInfo[description]\", \"link_to_presentation\":\"$surveyInfo[link_to_presentation]\", \"questions\":[";
            $recordSet->free();
            $command= <<< EOS
SELECT concat('{"id":', q.idQuestions, ',"name":"', q.name, '", "type":', qt.idQuestion_type, ', 
"required_answer":', q.answer_required, concat(',"options":[', GROUP_CONCAT(concat('{"id":',o.idOptions, ',"name":"', o.choice_name, '"}') ORDER BY o.idOptions ASC SEPARATOR ','), ']'), '}' ) FROM Questions q
JOIN Question_options qo ON q.idQuestions=qo.idQuestions
JOIN Options o ON qo.idOptions=o.idOptions
JOIN Survey s ON s.idSurvey=q.Survey_idSurvey
JOIN Question_type qt ON qt.idQuestion_type=q.Question_type_idQuestion_type
WHERE s.access_code = '$surveyAccessCode'
GROUP BY q.idQuestions
ORDER BY q.idQuestions;
EOS;
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
            echo 'false';   //provjeriti kak primiti niš
        }
		
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
        $answers = $_POST['answers'];
		$command="INSERT INTO Answers VALUES";
		if(count($answers)){
			foreach ($answers as $a){
			$command.= "(default, '$a[id]', '$a[id_option]'),";
			}
		}
		$command = rtrim($command, ",");
        $dbHandler->query($command);
		echo 'true';
		
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