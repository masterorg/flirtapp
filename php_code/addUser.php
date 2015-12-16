<?php

include_once("databse.php");

if(isset($_POST["userID"]) && isset($_POST["userIDAdd"]))
{
	@session_start();
	
	$databse = new Database();
	
	$databse->addUser($_POST["userID"],$_POST["userIDAdd"]);
}
	

?>