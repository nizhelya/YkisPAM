<?php
$DBOperations_path = "../sql/DBOperations.php";
include($DBOperations_path);
$DBOperationsMtb_path = "../sql/DBOperationsMtb.php";
include($DBOperationsMtb_path);
class GeneralFunctionsClass
{
    public function __constructor()
    {

    }
    public function getFlatById($resultApartments): array
    {
        $apartments = array();
        while ($rowApartment = mysqli_fetch_array($resultApartments)) {
            $apartment = array(
                'address_id' => $rowApartment['address_id'],
                'address' => $rowApartment['address'],
                'nanim' => $rowApartment['nanim'],
                'fio' => $rowApartment['fio'],
                'order' => $rowApartment['order'],
                'data' => $rowApartment['data'],
                'area_full' => $rowApartment['area_full'],
                'area_life' => $rowApartment['area_life'],
                'area_dop' => $rowApartment['area_dop'],
                'area_balk' => $rowApartment['area_balk'],
                'area_otopl' => $rowApartment['area_otopl'],

                'tenant' => $rowApartment['tenant'],
                'podnan' => $rowApartment['podnan'],
                'absent' => $rowApartment['absent'],
                'email' => $rowApartment['email'],
                'phone' => $rowApartment['phone'],
                'raion_id' => $rowApartment['raion_id'],
                'house_id' => $rowApartment['house_id'],
                'tenant_tbo' => $rowApartment['tenant_tbo'],
                'room' => $rowApartment['room'],
                'privat' => $rowApartment['privat'],
                'subsidia' => $rowApartment['subsidia'],
                'vxvoda' => $rowApartment['vxvoda'],
                'teplomer' => $rowApartment['teplomer'],
                'distributor' => $rowApartment['distributor'],
                'lift' => $rowApartment['lift'],
                'kvartplata' => $rowApartment['kvartplata'],
                'otoplenie' => $rowApartment['otoplenie'],
                'ateplo' => $rowApartment['ateplo'],
                'podogrev' => $rowApartment['podogrev'],
                'voda' => $rowApartment['voda'],
                'stoki' => $rowApartment['stoki'],
                'avoda' => $rowApartment['avoda'],
                'astoki' => $rowApartment['astoki'],
                'tbo' => $rowApartment['tbo'],
                'aggr_kv' => $rowApartment['aggr_kv'],
                'aggr_voda' => $rowApartment['aggr_voda'],
                'aggr_teplo' => $rowApartment['aggr_teplo'],
                'aggr_tbo' => $rowApartment['aggr_tbo'],
                'boiler' => $rowApartment['boiler'],
                'enaudit' => $rowApartment['enaudit'],
                'heated' => $rowApartment['heated'],
                'ztp' => $rowApartment['ztp'],
                'ovu' => $rowApartment['ovu'],
                'paused' => $rowApartment['paused'],
                'osmd' => $rowApartment['osmd'],
                'osmd_id' => $rowApartment['osmd_id'],
                'osbb' => $rowApartment['osbb'],
                'what_change' => $rowApartment['what_change'],
                'data_change' => $rowApartment['data_change'],
                'enaudit_id' => $rowApartment['enaudit_id'],
                'tarif_kv' => $rowApartment['tarif_kv'],
                'tarif_ot' => $rowApartment['tarif_ot'],
                'tarif_aot' => $rowApartment['tarif_aot'],
                'tarif_gv' => $rowApartment['tarif_gv'],
                'tarif_xv' => $rowApartment['tarif_xv'],
                'tarif_st' => $rowApartment['tarif_st'],
                'tarif_tbo' => $rowApartment['tarif_tbo'],
                'tne' => $rowApartment['tne'],
                'kte' => $rowApartment['kte'],
                'length' => $rowApartment['length'],
                'diametr' => $rowApartment['diametr'],
                'dvodomer_id' => $rowApartment['dvodomer_id'],
                'dteplomer_id' => $rowApartment['dteplomer_id'],
                'data_in' => $rowApartment['data_in'],
                'operator' => $rowApartment['operator'],
                'kod' => $rowApartment['kod']
            );

            $apartments[] = $apartment;
        }
        return $apartments;
    }

    public function getApartmentsByUser($resultApartmentsMyflat): array
    {
        $apartments = array();
   //     print_r(1);
        while ($rowApartment = mysqli_fetch_array($resultApartmentsMyflat)) {
            $apartment = array(
                'id'=>$rowApartment['id'],
                'user_id'=>$rowApartment['user_id'],
                'uid'=>$rowApartment['uid'],
                'address_id' => $rowApartment['address_id'],
                'address' => $rowApartment['address'],
                'nanim' => $rowApartment['nanim'],
                'fio' => $rowApartment['fio'],
                'order' => $rowApartment['order'],
                'data' => $rowApartment['data'],
                'area_full' => $rowApartment['area_full'],
                'area_life' => $rowApartment['area_life'],
                'area_dop' => $rowApartment['area_dop'],
                'area_balk' => $rowApartment['area_balk'],
                'area_otopl' => $rowApartment['area_otopl'],
                'tenant' => $rowApartment['tenant'],
                'podnan' => $rowApartment['podnan'],
                'absent' => $rowApartment['absent'],
                'email' => $rowApartment['email'],
                'phone' => $rowApartment['phone'],
                'raion_id' => $rowApartment['raion_id'],
                'house_id' => $rowApartment['house_id'],
                'tenant_tbo' => $rowApartment['tenant_tbo'],
                'room' => $rowApartment['room'],
                'privat' => $rowApartment['privat'],
                'subsidia' => $rowApartment['subsidia'],
                'vxvoda' => $rowApartment['vxvoda'],
                'teplomer' => $rowApartment['teplomer'],
                'distributor' => $rowApartment['distributor'],
                'lift' => $rowApartment['lift'],
                'kvartplata' => $rowApartment['kvartplata'],
                'otoplenie' => $rowApartment['otoplenie'],
                'ateplo' => $rowApartment['ateplo'],
                'podogrev' => $rowApartment['podogrev'],
                'voda' => $rowApartment['voda'],
                'stoki' => $rowApartment['stoki'],
                'avoda' => $rowApartment['avoda'],
                'astoki' => $rowApartment['astoki'],
                'tbo' => $rowApartment['tbo'],
                'aggr_kv' => $rowApartment['aggr_kv'],
                'aggr_voda' => $rowApartment['aggr_voda'],
                'aggr_teplo' => $rowApartment['aggr_teplo'],
                'aggr_tbo' => $rowApartment['aggr_tbo'],
                'boiler' => $rowApartment['boiler'],
                'enaudit' => $rowApartment['enaudit'],
                'heated' => $rowApartment['heated'],
                'ztp' => $rowApartment['ztp'],
                'ovu' => $rowApartment['ovu'],
                'paused' => $rowApartment['paused'],
                'osmd' => $rowApartment['osmd'],
                'osmd_id' => $rowApartment['osmd_id'],
                'osbb' => $rowApartment['osbb'],
                'what_change' => $rowApartment['what_change'],
                'data_change' => $rowApartment['data_change'],
                'enaudit_id' => $rowApartment['enaudit_id'],
                'tarif_kv' => $rowApartment['tarif_kv'],
                'tarif_ot' => $rowApartment['tarif_ot'],
                'tarif_aot' => $rowApartment['tarif_aot'],
                'tarif_gv' => $rowApartment['tarif_gv'],
                'tarif_xv' => $rowApartment['tarif_xv'],
                'tarif_st' => $rowApartment['tarif_st'],
                'tarif_tbo' => $rowApartment['tarif_tbo'],
                'tne' => $rowApartment['tne'],
                'kte' => $rowApartment['kte'],
                'length' => $rowApartment['length'],
                'diametr' => $rowApartment['diametr'],
                'dvodomer_id' => $rowApartment['dvodomer_id'],
                'dteplomer_id' => $rowApartment['dteplomer_id'],
                'data_in' => $rowApartment['data_in'],
                'operator' => $rowApartment['operator'],
                'kod' => $rowApartment['kod'],
                'ipay' => $rowApartment['ipay'],
                'mtb' => $rowApartment['mtb'],
                'pb' => $rowApartment['pb']
            );

            $apartments[] = $apartment;
        }
        return $apartments;
    }
    public function getBlocks($resultBlocks): array
    {
        $blocks = array();
        while ($rowBlocks = mysqli_fetch_array($resultBlocks)) {
            $block = array(
                'raion_id'=>$rowBlocks['raion_id'],
                'raion'=>$rowBlocks['raion']
            );
            $blocks[] = $block;
        }
        return $blocks;
    }
    public function getStreetsFromBlock($resultStreets): array
    {
        $streets = array();
        while ($rowStreets = mysqli_fetch_array($resultStreets)) {
            $street = array(
                'street_id'=>$rowStreets['street_id'],
                'street'=>$rowStreets['street']
            );
            $streets[] = $street;
        }
        return $streets;
    }
    public function getHousesFromStreet($resultHouses): array
    {
        $houses = array();
        // print_r($resultHouses);
        while ($rowHouses = mysqli_fetch_array($resultHouses)) {
            $house = array(
                'house_id'=>$rowHouses['house_id'],
                'house'=>$rowHouses['house']
            );

            $houses[] = $house;
        }
        return $houses;
    }

    public function getFlatsFromHouse($resultFlats): array
    {
        $flats = array();
        while ($rowFlats = mysqli_fetch_array($resultFlats)) {
            $flat = array(
                'address_id'=>$rowFlats['address_id'],
                'address'=>$rowFlats['address'],
                'kod'=>$rowFlats['kod']
            );
            $flats[] = $flat;
        }
        return $flats;
    }
    public function addMyFlatByUser($resultCheck): array
    {
        $results = array();
     // print_r($resultCheck);
        while ($row = mysqli_fetch_array($resultCheck)) {            
            $result = array(
                'success'=>$row[0],
                'message'=>$row[1]
            );
            $results[] = $result;
        //   print_r($results);
        }
        return $results;
    }
    public function getFamilyFromFlat($resultFamily): array
    {
        $families = array();
        while ($row = mysqli_fetch_array($resultFamily)) {
            $family = array(
                'rec_id'=>$row['rec_id'],
                'address_id'=>$row['address_id'],
                'address'=>$row['address'],
                'rodstvo'=>$row['rodstvo'],
                'firstname'=>$row['firstname'],
                'surname'=>$row['surname'],
                'lastname'=>$row['lastname'],
                'born'=>$row['born'],
                'sex'=>$row['sex'],
                'phone'=>$row['phone'],
                'subsidia'=>$row['subsidia'],
                'vkl'=>$row['vkl'],
                'inn'=>$row['inn'],
                'document'=>$row['document'],
                'seria'=>$row['seria'],
                'nomer'=>$row['nomer'],
                'datav'=>$row['datav'],
                'organ'=>$row['organ']
            );
            $families[] = $family;
        }
        return $families;
    }

    public function getFlatServices($resultServices): array
    {
        $results = array();
        while ($row = mysqli_fetch_array($resultServices)) {
            $service = array(
                'address_id'=>$row['address_id'],
                'service'=>$row['service'],
                'service1'=>$row['service1'],
                'service2'=>$row['service2'],
                'service3'=>$row['service3'],
                'service4'=>$row['service4'],
                'data'=>$row['data'],
                'period'=>$row['period'],
                'zadol'=>$row['zadol'],
                'zadol1'=>$row['zadol1'],
                'zadol2'=>$row['zadol2'],
                'zadol3'=>$row['zadol3'],
                'zadol4'=>$row['zadol4'],
                'nachisleno'=>$row['nachisleno'],
                'nachisleno1'=>$row['nachisleno1'],
                'nachisleno2'=>$row['nachisleno2'],
                'nachisleno3'=>$row['nachisleno3'],
                'nachisleno4'=>$row['nachisleno4'],
                'oplacheno'=>$row['oplacheno'],
                'oplacheno1'=>$row['oplacheno1'],
                'oplacheno2'=>$row['oplacheno2'],
                'oplacheno3'=>$row['oplacheno3'],
                'oplacheno4'=>$row['oplacheno4'],
                'dolg'=>$row['dolg'],
                'dolg1'=>$row['dolg1'],
                'dolg2'=>$row['dolg2'],
                'dolg3'=>$row['dolg3'],
                'dolg4'=>$row['dolg4']
            );

            $results[] = $service;
        }
        return $results;
    }
    public function getFlatPayments($resultPayments): array
    {
        $results = array();
        while ($row = mysqli_fetch_array($resultPayments)) {
            $service = array(
                'rec_id'=>$row['rec_id'],
                'address_id'=>$row['address_id'],
                'address'=>$row['address'],
                'year'=>$row['god'],
                'data'=>$row['data'],
                'kvartplata'=>$row['kvartplata'],
                'remont'=>$row['remont'],
                'otoplenie'=>$row['otoplenie'],
                'voda'=>$row['voda'],
                'tbo'=>$row['tbo'],
                'summa'=>$row['summa'],
                'prixod'=>$row['prixod'],
                'kassa'=>$row['kassa'],
                'nomer'=>$row['operator'],
                'data_in'=>$row['data_in']
            );
            $results[] = $service;
        }
        return $results;
    }

    public function getWaterMeter($resultWater): array
    {
        $results = array();
        while ($row = mysqli_fetch_array($resultWater)) {
            $waterMeter = array(
                'vodomer_id'=>$row['vodomer_id'],
                'dvodomer_id'=>$row['dvodomer_id'],
                'address_id'=>$row['address_id'],
                'house_id'=>$row['house_id'],
                'address'=>$row['address'],
                'nomer'=>$row['nomer'],
                'model_id'=>$row['model_id'],
                'model'=>$row['model'],
                'st'=>$row['st'],
                'voda'=>$row['voda'],
                'place'=>$row['place'],
                'position'=>$row['position'],
                'obr'=>$row['obr'],
                'joint'=>$row['joint'],
                'sdate'=>$row['sdate'],
                'fpdate'=>$row['fpdate'],
                'pdate'=>$row['pdate'],
                'pp'=>$row['pp'],
                'zdate'=>$row['zdate'],
                'avg'=>$row['avg'],
                'date_ar'=>$row['date_ar'],
                'date_ao'=>$row['date_ao'],
                'out'=>$row['out'],
                'spisan'=>$row['spisan'],
                'paused'=>$row['paused'],
                'data_st'=>$row['data_st'],
                'data_fin'=>$row['data_fin'],
                'norma'=>$row['norma'],
                'data_spis'=>$row['data_spis'],
                'operator'=>$row['operator'],
                'work'=>$row['work']
            );
            $results[] = $waterMeter;
        }
        return $results;
    }

    public function getWaterReadings($resultWater): array
    {
        $results = array();
        while ($row = mysqli_fetch_array($resultWater)) {
            $waterReadings = array(
                'pok_id'=>$row['pok_id'],
                'vodomer_id'=>$row['vodomer_id'],
//                'address_id'=>$row['address_id'],
                'date_readings'=>$row['data'],
                'date_ot'=>$row['date_ot'],
                'date_do'=>$row['date_do'],
                'days'=>$row['days'],
                'last'=>$row['pred'],
                'currant'=>$row['tek'],
                'kub'=>$row['kub'],
                'avg'=>$row['avg'],
                'tarif_xv'=>$row['tarif_xv'],
                'xvoda'=>$row['xvoda'],
                'tarif_st'=>$row['tarif_st'],
                'stoki'=>$row['stoki'],
                'date_st'=>$row['date_st'],
                'date_fin'=>$row['date_fin'],
                'pok_ot'=>$row['pok_ot'],
                'pok_do'=>$row['pok_do'],
                'rday'=>$row['rday'],
                'mday'=>$row['mday'],
                'kub_day'=>$row['kub_day'],
                'qty_kub'=>$row['qty_kub'],
                'data_in'=>$row['data_in'],
                'operator'=>$row['operator'],
                'address_id'=>$row['address_id']

            );
            $results[] = $waterReadings;
        }
        return $results;
    }

    public function addCurrentReading($result): array
    {
        $results = array();
        //print_r($resultFlats);
        while ($rowFlats = mysqli_fetch_array($result)) {
            $flat = array(
                'success'=>$rowFlats[0],
                'message'=>$rowFlats[1]
            );
            $results[] = $flat;
//             print_r($flat);
        }
        return $results;
    }

    public function deleteCurrentReading($result): array
    {
        $results = array();
        while ($rowFlats = mysqli_fetch_array($result)) {
            $flat = array(
                'success'=>$rowFlats[0],
                'message'=>$rowFlats[1]
            );
            $results[] = $flat;
        }
        return $results;
    }

    public function getHeatMeter($resultHeat): array
    {
        $results = array();
        while ($row = mysqli_fetch_array($resultHeat)) {
            $heatMeter = array(
                'teplomer_id'=>$row['teplomer_id'],
//                'dteplomer_id'=>$row['dteplomer_id'],
//                'address'=>$row['address'],
                'nomer'=>$row['nomer'],
                'model_id'=>$row['model_id'],
                'model'=>$row['model'],
                'edizm'=>$row['edizm'],
                'koef'=>$row['koef'],
                'area'=>$row['area'],
                //Дата випуску
                'sdate'=>$row['sdate'],
                //Наступна перевірка
                'fpdate'=>$row['fpdate'],
                //Попередня перевірка
                'pdate'=>$row['pdate'],
//                'pp'=>$row['pp'],
                //Дата встановки пломби
//                'zdate'=>$row['zdate'],
            //в перевірці чи в ремонті
                'out'=>$row['out'],
                'spisan'=>$row['spisan'],
                'data_st'=>$row['data_st'],
                'data_fin'=>$row['data_fin'],
                //Дата списанія
                'data_spis'=>$row['data_spis'],
//                'operator'=>$row['operator'],
                'work'=>$row['work']
            );
            $results[] = $heatMeter;
        }
        return $results;
    }

    public function getHeatReadings( $result): array
    {
        $results = array();
        while ($row = mysqli_fetch_array($result)) {
            $heatReadings = array(
                'pok_id'=>$row['pok_id'],
                'teplomer_id'=>$row['teplomer_id'],
//                'address_id'=>$row['address_id'],
                'date_readings'=>$row['data'],
                'date_ot'=>$row['date_ot'],
                'date_do'=>$row['date_do'],
                'edizm'=>$row['edizm'],
                'koef'=>$row['koef'],
                'days'=>$row['days'],
                'last'=>$row['pred'],
                'currant'=>$row['tek'],
                'gkal'=>$row['gkal'],
                'avg'=>$row['avg'],
                'tarif'=>$row['tarif'],
                'area'=>$row['area'],
                'gkm2'=>$row['gkm2'],
                'otoplenie'=>$row['otoplenie'],
                'pok_ot'=>$row['pok_ot'],
                'pok_do'=>$row['pok_do'],
                'gkal_rasch'=>$row['gkal_rasch'],
                'gkal_day'=>$row['gkal_day'],
                'qty_day'=>$row['qty_day'],
                'day_avg'=>$row['day_avg'],
                'data_in'=>$row['data_in'],
                'operator'=>$row['operator'],
                'qty'=>$row['qty'],
                'address_id'=>$row['address_id']
            );

            $results[] = $heatReadings;
        }
        return $results;
    }
    public function getMtbPayment($resultPayment): array
    {
        $payment = array();
        // print_r($resultHouses);
        while ($row = mysqli_fetch_array($resultPayment)) {
            $payment['kvartplata']= $row['@kvartplata'];
            $payment['teplo']	    = $row['@teplo'];
            $payment['voda']	    = $row['@voda'];
            $payment['tbo']	    = $row['@tbo'] ;
            $payment['payment_id'] = $row['@payment_id'];
            $payment['edrpou'] 	= $row['@edrpou'];
            $payment['firstname']	= $row['@firstname'];
            $payment['patronymic']= $row['@patronymic'];
            $payment['surname']	= $row['@surname'];
            $payment['account']	= $row['@account'];
            $payment['address']	= $row['@address'];
            $payment['data_in']	= $row['@data_in'];
            $payment['success']	= $row['@success'];
            $payment['msg']		= $row['@msg'];
        }
            $partner = array();
            $service = array();
            $teplo = array();
            $voda = array();
            $tbo = array();
            $kvartplata = array();
            $transaction = array();
            $billattr = array();
            $data = array();
            $requestData = array();


            if ($payment['teplo'] >0)  {
                $teplo["ServiceCode"] ="26134519";
                $teplo["Sum"] =  $payment['teplo'];
                $service[] =  $teplo;


            }
            if ($payment['kvartplata'] >0  ) {
                $kvartplata["ServiceCode"] =$payment['edrpou'];
                $kvartplata["Sum"] =  $payment['kvartplata'];
                $service[] =  $kvartplata;
            }
            if ($payment['voda'] >0 ) {
                $voda["ServiceCode"] ="31783053";
                $voda["Sum"] = $payment['voda'];
                $service[] =  $voda;
            }
            if ($payment['tbo'] >0) {
                $tbo["ServiceCode"] ="30750184";
                $tbo["Sum"] = $payment['tbo'] ;
                $service[] =  $tbo;
            }

            $partner["PartnerToken"] = "8aff556f-1025-439a-8c7d-fda279523332";
            $partner["OperationType"] = 10005;


            $billattr["PayerAddress"] = $payment['address'];

            $transaction["TransactionID"] = $payment['payment_id'];
            $transaction["TerminalID"] = "1" ;
            $transaction["DateTime"] = $payment['data_in'];

            $data["PayType"] = "7";
            $data["Phone"] = "";
            $data["Email"] = "";
            $data["Account"] = $payment['account'];
            $data["FirstName"] = $payment['firstname'];
            $data["LastName"] = $payment['surname'];
            $data["MiddleName"] = $payment['patronymic'];
            $data["Service"] = $service;
            $data["BillAttr"] = $billattr;
            $data["Transaction"] = $transaction;


            $requestData["Partner"] =  $partner;
            $requestData["Data"] = json_encode($data);
        return $requestData;
    }
}
