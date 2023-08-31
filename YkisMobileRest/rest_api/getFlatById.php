<?php
$response = array();

include_once "GeneralFunctions.php";


if (isset($_POST['address_id']) && !empty($_POST['address_id']) &&
     isset($_POST['user_id']) && !empty($_POST['user_id']) &&
    isset($_POST['token']) && !empty($_POST['token'])
) {
    $address_id = $_POST['address_id'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultApartments = $dbOperationsObject->getFlatById($address_id);
        $apartments = $generalFunctionsObject->getFlatById($resultApartments);

        $response["success"] = 1;
        $response["message"] = "Успешно";
        $response["appartments"] = $appartments;

        echo json_encode($response);

  
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

