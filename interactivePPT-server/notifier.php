<?php
if ($argc > 1) {
    $path = implode(' ', array_slice($argv, 1));
    $escapedPath = addslashes($path);
    $socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
    $dbHandler = new mysqli('localhost', 'root', 'toor', 'interactive_ppt');
    $pptId = $dbHandler->query("SELECT id FROM Presentation WHERE path='$escapedPath' LIMIT 1;")->fetch_row()[0];
    $dbHandler->close();
    if (is_resource($socket)) {
        $port = 50000 + $pptId;
        socket_bind($socket, '46.101.247.168', $port);
        file_put_contents('fajl.txt', $port); // for debugging purposes
        socket_listen($socket);
        $connection = socket_accept($socket);
        if (is_resource($connection)) {
            while (true) {
                sleep(5);
                socket_send($connection, "bok", strlen("bok"), 0);
            }
            socket_close($socket);
            socket_close($connection);
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