<?php
$response = array();
include_once "GeneralFunctions.php";

if (isset($_POST['uid']) ) {
    $uid = $_POST['uid'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultApartments = $dbOperationsObject->getApartmentsByUser($uid);
        $apartments = $generalFunctionsObject->getApartmentsByUser($resultApartments);
        $response["success"] = 1;
            $response["message"] = "Success!";
        $response["apartments"] = $apartments;


} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}
echo json_encode($response);

?>
