<?php
function sendGCM($message, $id) {
	
    $url = 'https://fcm.googleapis.com/fcm/send';

    $fields = array (
            'registration_ids' => $id,
            'data' => array("message" => $message)
    );
    $fields = json_encode ( $fields );

    $headers = array (
            'Authorization: key=' . "AAAA9FXlWXQ:APA91bEByAfXZmjQb7vXGwIrj3V9b59glPuDPh99vc0nks1XSGcN1xuLfCmX-ajD6LgiJnNbze7wkeJA1L8rK-uexFgs-YF6Z0Yw1bHulxUWf0pKzGpW5J43uzwqDsxyT1YUrPpRcC_j1-Uf01qESDydVqJzBrzTpg",
            'Content-Type: application/json'
    );

    $ch = curl_init ();
    curl_setopt ( $ch, CURLOPT_URL, $url );
    curl_setopt ( $ch, CURLOPT_POST, true );
    curl_setopt ( $ch, CURLOPT_HTTPHEADER, $headers );
    curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, true );
    curl_setopt ( $ch, CURLOPT_POSTFIELDS, $fields );

    $result = curl_exec ( $ch );
    echo $result;
    curl_close ( $ch );
}

	$dbHandler = new mysqli('localhost', 'root', 'toor', 'interactive_ppt');
	$dbHandler->set_charset("utf8");
	
	$uploaddir = "ppt/";
	$uploadfile = $uploaddir . basename($_FILES["file"]["name"]);
	move_uploaded_file($_FILES["file"]["tmp_name"], $uploadfile);
	
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
            $recordSet = $dbHandler->query("SELECT * FROM Presentation WHERE access_code='$accessCode' LIMIT 1;");
        } while ($recordSet->num_rows > 0);
		
    $recordSet->free();
	
	$command = "SELECT * FROM Presentation WHERE path = '$uploadfile' LIMIT 1;";
	$recordSet = $dbHandler->query($command);
	if($recordSet->num_rows > 0){
		$result = $recordSet->fetch_assoc();
		$pId = (int)$result['id'];
		$command = "SELECT token FROM Users u JOIN Subscription s JOIN Presentation p WHERE u.idUser = s.idUser AND p.id = s.idPresentation AND p.id = $pId";
		$recordSet = $dbHandler->query($command);
		$outputArray = array();
		for ($i=0 ; $i < $recordSet->num_rows ; $i++) {
                array_push($outputArray, $recordSet->fetch_assoc()['token']);
            }
        $recordSet->free();
		$command = "SELECT access_code FROM Presentation WHERE id = $pId LIMIT 1;";
		$ac = $dbHandler->query($command)->fetch_assoc()['access_code'];
		$command = "SELECT presentation_details FROM getPresentationDetails WHERE access_code='$ac' LIMIT 1;";
        $recordSet = $dbHandler->query($command);
        if ($recordSet && $recordSet->num_rows) {
			sendGCM($recordSet->fetch_row()[0],$outputArray);
			//sendGCM($outputArray,$ids);
            $recordSet->free();
        }
		else {
            echo 'null';
        }
		//sendGCM($ac,$recordSet);
	}else{
		$command = "INSERT INTO Presentation VALUES (default, '$uploadfile', '$accessCode',2);";
		$dbHandler->query($command);
	}
	
	
?>
