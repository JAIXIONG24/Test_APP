<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){

	$username = $_POST["username"];
	$email = $_POST["email"];
	$password = $_POST["password"];

	require_once 'init.php';


	$query = " insert into users(username,email,password) values('$username','$email','$password')";
  $stmt = $dbh->query($query);
	$rec = $stmt->fetchAll(PDO::FETCH_ASSOC);

	if($rec){
		$result["success"] = "1";
		$result["message"] = "success";

		echo json_encode($result);

	}else{
		$result["success"] = "0";
		$result["message"] = "error";

		echo json_encode($result);

	}
	
}


?>
