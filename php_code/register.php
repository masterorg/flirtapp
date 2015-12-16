<?php
	header('Access-Control-Allow-Origin: *');
	header("Content-Type: text/json");
include_once("functions.php");//session function are here
include_once("databse.php");

if(isset($_POST["username"]) && isset($_POST["password"]) && isset($_POST["email"]) && isset($_POST["gender"]))
{

	//put values in variables
	$username = $_POST["username"];
	$password = $_POST["password"];
	$email = $_POST["email"];
	$gender = $_POST["gender"];

$dataBase = new Database();//inicialze the databse instance

$dataBase->connect();//connect to database



echo  json_encode($dataBase->signup($username, $password, $email, $gender));
$dataBase->close();//close the database connection

}
else {
	$array["succes"] = false;
	$array["message"] = "Please fill whole registration!";
	echo json_encode($array);
}





?>