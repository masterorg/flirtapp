<?php

include_once("databse.php");

$database = new Database();

echo json_encode($database->getAllMatchData());

?>