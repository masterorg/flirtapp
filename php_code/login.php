<?php
	header('Access-Control-Allow-Origin: *');
	header("Content-Type: text/json");
include_once("functions.php");//session function are here
include_once("databse.php");

if(isset($_POST["username"]) && isset($_POST["password"]))
{

	//put values in variables
	$username=$_POST["username"];
	$password=$_POST["password"];

$dataBase = new Database();//inicialze the databse instance

$dataBase->connect();//connect to database



echo  json_encode($dataBase->login($username,$password));
$dataBase->close();//close the database connection

}





?>