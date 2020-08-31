
<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	include 'init.php';
	showStudent();
	
}

function showStudent()
{
	global $dbh;

		$query = " SELECT * FROM users; ";
	  $stmt1 = $dbh->query($query);
	  $rec1 = $stmt1->fetchAll(PDO::FETCH_ASSOC);

		$temp_array  = array();

		if($rec1 > 0) {
			foreach($rec1 as $row ){
				$temp_array[] = $row;

			}
		}

		header('Content-Type: application/json');
		echo json_encode(array("students"=>$temp_array));
		mysqli_close($dbh);







}



?>
