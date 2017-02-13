<?php
if ($argc > 1) {
    $path = implode(' ', array_slice($argv, 1));
    file_put_contents('fajl.txt', $path);   //for debugging purposes
}
?>