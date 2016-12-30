<?php
function sendGCM($message, $id) {
	
    $url = 'https://fcm.googleapis.com/fcm/send';

    $fields = array (
            'registration_ids' => $id,
            'data' => array (
                    "message" => $message
            )
    );
    $fields = json_encode ( $fields );

    $headers = array (
            'Authorization: key=' . "AIzaSyBwVsMO4cJqYy-sgm_A6dU-cW96ujH73Kg",
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
		$pId = $result['id'];
		$command = "SELECT token FROM Notification WHERE presentationID = $pId;";
		$recordSet = $dbHandler->query($command);
		if ($recordSet) {
            for ($i=0; $i<$recordSet->num_rows; $i++) {
                array_push($ids, $recordSet->fetch_assoc());
            }
            $recordSet->free();
        }
		sendGCM($uploadfile,$ids);
	}else{
		$command = "INSERT INTO Presentation VALUES (default, '$uploadfile', '$accessCode',2);";
		$dbHandler->query($command);
	}
	
	
?>
