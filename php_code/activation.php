<?php

if(isset($_GET["strvld"]))
{
	include_once("databse.php");
	$dataBase = new Database();
	if($dataBase->activateLogin($_GET["strvld"]))
	{
		echo "Activation successful";
	}
	else
	{
		echo "Activation unsuccessful<br/>Please try again";
	}
}
else
{
	echo "Neautorizovan pristup";
}

?>