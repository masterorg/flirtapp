<?php
header('Access-Control-Allow-Origin: *');
	header("Content-Type: text/json");
include_once("functions.php");//session function are here
include_once("databse.php");

	//put values in variables
	$username= "ognjen.djordjevic";
	$password= "flirtapp";

$dataBase = new Database();//inicialze the databse instance

echo  json_encode($dataBase->getUserMatch(2));

?>