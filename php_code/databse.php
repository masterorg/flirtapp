<?php
//class for databse connections
/**
* 
*/
define("DB_HOST", "mysql9.000webhost.com");
define("DB_USER", "a4364992_flirt");
define("DB_PASSWORD", "dasa1016188");
define("DB_DATABASE", "a4364992_flirta");
class Database 
{

	private $conn;

	function __construct()
	{
		
	}

	function __destruct()
	{
		$this->close();
	}

	//method for closing dataconnetion

	public function close()
	{
			//mysqli_close($this->conn);
			unset($this->conn);
	}

	//method  for mysql_connection
	public function connect()
	{
		$this->conn=mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE) or die("Connection error");
		//$this->conn=mysqli_connect("localhost","root","","flirtapp") or die("Connection error");
	}

	//method for login
	public function login($username,$password)
	{
		$password=md5($password);//password hashing
		$query="SELECT * FROM t_User WHERE UserName='$username' AND Password='$password'";

			$result = mysqli_query($this->conn,$query);
			if(mysqli_num_rows($result)==1)
			{
				$row=mysqli_fetch_array($result);
				$userID=$row["ID"];
				$_SESSION["ID"] = $userId;
				$array["userID"] = $userID;
				$array["UserName"] = $row["UserName"];
				$array["Email"] = $row["Email"];
				$array["Gender"] = $row["Gender"];
				$array["DayOfBirth"] = $row["DayOfBirth"];
				$array["AvatarPath"] = $row["AvatarPath"];
				$array["success"] = true;
				$array["active"] = $this->isActive($userID);
				return $array;
			}
			else
			{
				$array["userID"] = null;
				$array["success"] = false;
				$array["active"] = null;
				return $array;
			}
	}
	
	//metoda za signup *Stefan Zivkov*
	public function signup($username, $password, $email, $gender){
		if(isset($username) && isset($password) && isset($email) && isset($gender)){
			
			$UsernameVaildQuery = "SELECT `ID` FROM `t_User` WHERE `UserName`='$username'";
			$EmailVaildQuery = "SELECT `ID` FROM `t_User` WHERE `Email`='$email'";
			$validUsername = mysqli_query($this->conn,$UsernameVaildQuery);
			$validEmail = mysqli_query($this->conn,$EmailVaildQuery);
			if(mysqli_num_rows($validUsername) == 1){
				$resArray["succes"] = false;
				$resArray["message"] = "Username exists!";
				return $resArray;
			}
			else if(mysqli_num_rows($validEmail) == 1){
				$resArray["succes"] = false;
				$resArray["message"] = "Email exists!";
				return $resArray;
			}
			else {
				$password=md5($password);//password hashing
				$hash = md5($username);//can putt double md5
				
				$query="INSERT INTO `t_User`( `UserName`, `Password`, `Email`, `Gender`, `LastActivity`, `IsActiv`, `DayOfBirth`, `IsLogedIn`, `Hash`)
				 VALUES ('$username','$password','$email','$gender',0,0,0,0,'$hash')";

				//execute the query
				 $result = mysqli_query($this->conn,$query);
				 
				 if($result){
					$resArray["succes"] = true;
					$resArray["message"] = "Profile created!";
					return $resArray;
				 }
				 else {
					$resArray["succes"] = false;
					$resArray["message"] = "Mysql error!";
					return $resArray;
				 }
			}
		}
	}

	//method for checking status of account
	public function isActive($userID)
	{
		$query="SELECT * FROM `t_User` WHERE `ID`='$userID' AND `IsActiv`=1";
		$result =mysqli_query($this->conn,$query) or die("IsActiv error");
		

		if($result->num_rows==1)
		{
			$query1="UPDATE `t_User` SET `IsLogedIn`=1  WHERE `ID`=$userID";//set isLoged=true
			mysqli_query($this->conn,$query1);
			$this->lastActivity($userID);
			return true;
		}
		return false;
	}

	//check if user with given username exists
	public function isUserExists($username)
	{
		$query="SELECT `ID` FROM `t_User` WHERE `UserName`='$username'";
		$result =mysqli_query($this->conn,$query);
		if(mysqli_num_rows($result)==1)/*ISPRAVKA*/
		{
			return true;
		}
		return false;
	}
	
	//check if user with given email exists
	public function isEmailExists($email)
	{
		$query="SELECT `ID` FROM `t_User` WHERE `Email`='$email'";
		$result =mysqli_query($this->conn,$query);
		if(mysqli_num_rows($result)==1)/*ISPRAVKA*/
		{
			return true;
		}
		return false;
	}

	//method for updating lastActivity
	public function lastActivity($userId)
	{
		$timest = mktime();
		$query="UPDATE  `t_User` SET `LastActivity`=$timest WHERE `ID`=$userId";
		mysqli_query($this->conn,$query) or die("Error in updating lastActivity");
	}


	/*REGISTRATION FUNCTION*/
	public function register($regArray)

	{
		//checking username and email
		$checkUs=$this->checkUserEmail($regArray["username"],$regArray["email"]);
		

		if(!isset($checkUs["username"]) && !isset($checkUs["email"]))
		{
				//taking values from $regArray
				$username=$regArray["username"];
				$password=md5($regArray["password"]);
				$email=$regArray["email"];
				$gender=md5($regArray["gender"]);
				$hash = md5($username);//can putt double md5


				/*-------------------THE END OF INFORMATION EXCTRACTING*/


				$query="INSERT INTO `t_user`( `UserName`, `Password`, `Email`, `Gender`, `LastActivity`, `IsActiv`, `DayOfBirth`, `IsLogedIn`, `Hash`)
				 VALUES ('$username','$password','$email','$gender',0,0,0,0,'$hash')";

				//execute the query
				 $result =mysqli_query($this->conn,$query) or die("Registration error");
				 $checkUs["registered"]="true";
				 $checkUs["hash"]=$hash;
				 $checkUs["r_email"]=$email;//user email 


		}

		return $checkUs;
		 
	}

	//function for checking email and username uniqunes

	public function checkUserEmail($username,$email)

	{
		$statusArr=array();


		$query="SELECT * FROM `t_user` WHERE `UserName`='$username'";
		
		$result =mysqli_query($this->conn,$query);
		if(mysqli_num_rows($result)==1)
		{
			$statusArr["username"]="exists";
		}
		
		/*CHECK IF EMAIL IS unique*/
		$query2="SELECT * FROM `t_user` WHERE `Email`='$email'";
		
		$result2 =mysqli_query($this->conn,$query2);
		if(mysqli_num_rows($result2)==1)
		{
			$statusArr["email"]="exists";
		}


		//response array
		return $statusArr;
	}

	

	//method for activation account from mail
	public function activateLogin($strVal)
	{
		$strVal = addslashes($strVal);
		$this->connect();
		$query = "SELECT ID FROM t_User WHERE Hash = '$strVal' AND IsActiv =0";
		$result = mysqli_query($this->conn,$query) or die("Something went wrong, try again");
		if(mysqli_num_rows($result)==1)
		{
			$query = "update t_User set IsActiv = 1 where Hash = '$strVal' and IsActiv = 0";
			if(mysqli_query($this->conn,$query) or die("Something went wrong, try again"))
			{
				$row = mysqli_fetch_array($result);
				$this->lastActivity($row[0][0]);
				$this->close();
				return true;
			}
			else
			{
				$this->close();
				return false;
			}
		}
		$this->close();
		return false;


	}


				/***********************USER PROFILE DATA***********************/
	
	function getUserFriends($userID)
	{
		$this->connect();
		if(isset($userID))
		{		
			$query = "select * from t_UserFriends where (UserReq = '$userID' or UserAdd = '$userID') and IsAccepted = 1 and IsDeleted = 0";
			$result = mysqli_query($this->conn,$query);
			if(mysqli_num_rows($result) > 0)
			{
				$userFriends = array();
				$userFriends["success"] = true;
				$userFriends["friends"] = array();
				$friendID = null;
				while($row = mysqli_fetch_array($result))
				{
					$friendAdd = $row["UserReq"] == $userID ? $row["UserAdd"] : $row["UserReq"];
					
					$query = "select * from t_User where ID = '$friendAdd'";
					
					$friendResult = mysqli_query($this->conn,$query);
					if(mysqli_num_rows($friendResult) == 1)
					{
						$userProfile = array();
						$rowF = mysqli_fetch_array($friendResult);
						$userProfile["ID"] = $friendAdd;
						$userProfile["UserName"] = $rowF["UserName"];
						$userProfile["Email"] = $rowF["Email"];
						$userProfile["Gender"] = $rowF["Gender"];
						$userProfile["DayOfBirth"] = $rowF["DayOfBirth"];
						$userProfile["AvatarPath"] = $rowF["AvatarPath"];
						$userFriends["friends"][] = $userProfile;
					}
				}
				$this->close();
				return $userFriends;
			}
			else 
			{
				$this->close();
				return array("success" => false);
			}

		}
		$this->close();
		return null;
	}

	function getUserMatch($userID)
	{
		if(isset($userID))
		{		
			$this->connect();
			$query = "SELECT m.UserRelated, m.MatchID
						FROM t_UserRelatedMatch m, t_UserRelatedMatch a
						WHERE m.UserID = a.UserRelated
						AND m.UserRelated = a.UserID
						AND m.MatchID = a.MatchID
						AND m.UserID = '$userID'
						AND m.Seen = 0";
			$result = mysqli_query($this->conn,$query);
			if(mysqli_num_rows($result) > 0)
			{
				$userMatches = array();
				$array = array();
				$array["success"] = true;
				$arrayp["matches"] = array();
				
				while($row = mysqli_fetch_array($result))
				{
					$relatedID = $row["UserRelated"];
					$matchID = $row["MatchID"];
					$query = "select u.UserName,u.Email,u.Gender,m.Name,m.Description from t_User u, s_MatchType m where u.ID = $relatedID and m.ID = $matchID";
					$queryUpdate = "update t_UserRelatedMatch set Seen = 1 where UserRelated = $relatedID and UserID = $userID and MatchID = $matchID";
					
					if(mysqli_query($this->conn,$queryUpdate))
					{
						$res = mysqli_query($this->conn,$query);
						if(mysqli_num_rows($res) == 1)
						{
							$rows = mysqli_fetch_array($res);
							
							$userMatches = array();
							$userMatches["UserName"] = $rows["UserName"];
							$userMatches["Email"] = $rows["Email"];
							$userMatches["Gender"] = $rows["Gender"];
							$userMatches["Name"] = $rows["Name"];
							$userMatches["Description"] = $rows["Description"];
							
							$array["matches"][] = $userMatches;
						}
					}
				}
				$this->close();
				return $array;
			}
			else 
			{
				$this->close();
				return array("success" => false);
			}
		}
	}
	
	function addUser($userID,$userAdd)
	{
		$this->connect();
		if(isset($userID))
		{		
			$query = "insert into t_UserFriends(UserReq,UserAdd) values('$userID','$userAdd')";
			if(mysqli_query($this->conn,$query))
			{
				echo json_encode(array("success" => true));
			}
			else 
			{
				echo json_encode(array("success" => false));
			}
		}
		$this->close();
	}
	
	function addMatch($userID,$userAdd,$matchID)
	{
		$this->connect();
		if(isset($userID))
		{		
			$query = "insert into t_UserRelatedMatch(UserID,UserRelated,MatchID) values('$userID','$userAdd','$matchID')";
			if(mysqli_query($this->conn,$query))
			{
				echo json_encode(array("success" => true));
			}
			else 
			{
				echo json_encode(array("success" => false));
			}
		}
		$this->close();
	}
	
	function deleteFriend($userID,$userDel)
	{
		$this->connect();
		if(isset($userID) && isset($userDel))
		{		
			$query = "update t_UserFriends set IsDeleted = 1 where (UserAdd = $userID and UserReq = $userDel) or (UserAdd = $userDel and UserReq = $userID)";
			if(mysqli_query($this->conn,$query))
			{
				echo json_encode(array("success" => true));
			}
			else 
			{
				echo json_encode(array("success" => false));
			}
		}
		$this->close();
	}
	
	
	function deleteMatch($userID,$userDel,$matchID)
	{
		$this->connect();
		if(isset($userID) && isset($matchID))
		{		
			$query = "delete from t_UserRelatedMatch where UserID = $userID and UserRelated = $userDel and MatchID = $matchID";
			if(mysqli_query($this->conn,$query))
			{
				echo json_encode(array("success" => true));
			}
			else 
			{
				echo json_encode(array("success" => false));
			}
		}
		$this->close();
	}
	
	function getAllMatchData()
	{
		$this->connect();
		
		$return = array();
		$return["success"] = true;
		$return["matches"] = array();
		
		$query = "select * from s_MatchType";
		$result = mysqli_query($this->conn,$query);
		
		while($row = mysqli_fetch_array($result))
		{
			$res = array();
			$res["ID"] = $row["ID"];
			$res["Name"] = $row["Name"];
			$res["Description"] = $row["Description"];
			$res["PicturePath"] = $row["PicturePath"];
			
			$return["matches"][] = $res;
			
		}
			
		$this->close();
		
		return $return;
	}
	
	
	function getUserNonFriends($userID)
	{
		if(isset($userID))
		{
			$this->connect();
			$query = "select * from t_UserFriends where (UserReq = '$userID' or UserAdd = '$userID') and IsAccepted = 1 and IsDeleted = 0";
			$result = mysqli_query($this->conn,$query);
			if(mysqli_num_rows($result) > 0)
			{
				$userFriends = array();
				$userFriends["success"] = true;
				$userFriends["nonFriends"] = array();
				$friendAdd = "(";
				while($row = mysqli_fetch_array($result))
				{
					$friendAddTmp = $row["UserReq"] == $userID ? $row["UserAdd"] : $row["UserReq"];
					$friendAdd = $friendAdd.$friendAddTmp.",";
				}
				$friendAdd = $friendAdd."$userID)";
			
				$query = "select * from t_User where ID not in $friendAdd";
				
				$friendResult = mysqli_query($this->conn,$query);
				if(mysqli_num_rows($friendResult) > 0)
				{
					while($row = mysqli_fetch_array($friendResult))
					{
						$userProfile = array();
						$userProfile["ID"] = $row["ID"];
						$userProfile["UserName"] = $row["UserName"];
						$userProfile["Email"] = $row["Email"];
						$userProfile["Gender"] = $row["Gender"];
						$userProfile["DayOfBirth"] = $row["DayOfBirth"];
						$userProfile["AvatarPath"] = $row["AvatarPath"];
						$userProfile["Pending"] = $this->isPending($userID,$row["ID"]);
						$userFriends["nonFriends"][] = $userProfile;
					}
				}
				
				$this->close();
				return $userFriends;
			}
			else 
			{
				$this->close();
				return array("success" => false);
			}
		}
		return array("success" => false);
	}
	
	private function isPending($userID,$userFriend)
	{
		$query = "select * from t_UserFriends where UserAdd = $userID and UserReq = $userFriend and IsAccepted = 0";
		$result = mysqli_query($this->conn,$query);
		if(mysqli_num_rows($result) > 0)
		{
			return true;
		}
		else 
		{
			return false;
		}
	return array("success" => false);
	}
	
	

}


?>