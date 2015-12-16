<?php
	include_once("databse.php");

	@session_start();
	
	if(isset($_POST["userID"]))
	{
		$databse = new Database();
		
		echo json_encode($databse->getUserMatch($_POST["userID"]));
	}
?>