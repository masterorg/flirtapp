<?php

include_once("databse.php");

if(isset($_POST["userID"]) && isset($_POST["matchID"]) && isset($_POST["userIDDel"])
{
	@session_start();
	
	$databse = new Database();
	
	$databse->deleteMatch($_POST["userID"],$_POST["userIDAdd"],$_POST["matchID"]);
}
	

?>