<?php
include_once("databse.php");

if(isset($_POST["userID"]))
{
	$databse = new Database();
	echo  json_encode($databse->getUserNonFriends($_POST["userID"]));
}

?>