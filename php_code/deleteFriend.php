<?php

include_once("databse.php");

if(isset($_POST["userID"]) && isset($_POST["userIDDel"]))
{
	@session_start();
	
	$databse = new Database();
	
	$databse->deleteFriend($_POST["userID"],$_POST["userIDDel"]);
}
	

?>