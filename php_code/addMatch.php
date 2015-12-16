<?php

include_once("databse.php");

if(isset($_POST["userID"]) && isset($_POST["matchID"]) && isset($_POST["userIDAdd"])
{
	@session_start();
	
	$databse = new Database();
	
	$databse->addMatch($_POST["userID"],$_POST["userIDAdd"],$_POST["matchID"]);
}
	

?>