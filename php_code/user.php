<?php
class User{

	private $userID;
	private $userData = array("userProfile" => array(),"userFriendsData" => array(), "userMatchesData" => array());
	
	function __construct()
	{
		@session_start();
		if(isset($_SESSION["userID"]))
		{
			$userID = $_SESSION["userID"];
			$dataBase = new Database();
			$userData["userProfile"]= $dataBase->getUserProfile($userID);
			if($userData["userProfile"] != null)
			{
				$userData["userFriendsData"] = $dataBase->getUserFriends($userID);
				$userData["userMatchesData"] = $dataBase->getUserMatch($userID);
			}
		}
	}
	
	function getUserData()
	{
		print_r($userData);
		//echo json_encode($userData);
	}
	
	function addFriend()
	{
		
	}
}
?>