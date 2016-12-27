<?php

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
	
	$command = "INSERT INTO Presentation VALUES (default, '$uploadfile', '$accessCode');";
	$dbHandler->query($command);
?>
