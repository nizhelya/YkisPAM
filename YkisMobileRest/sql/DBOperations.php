<?php
include_once "DB.php";
class DBOperations {
    public function __constructor() {

    }
      public function addMyFlatByUser($kod ,$uid,$email){
        $com = new DbConnect();
        $sql = 'CALL YISGRAND.addMyFlatByUser("'.$kod.'" ,"'.$uid.'" ,"'.$email.'" , @success , @message)';
//              print_r($sql);
       mysqli_query( $com->getDb(), $sql) ;
       $sqlCallBack = 'SELECT @success , @message ' ;
          return mysqli_query( $com->getDb(), $sqlCallBack);
    }
  
//appartment
    public function getFlatById($address_id){
        $com = new DbConnect();
        $sql = 'SELECT * FROM YIS.APPARTMENT as t1 WHERE t1.address_id= '.$address_id.'';
        return mysqli_query($com->getDb(), $sql);
    }

    public function getApartmentsByUser($uid){
        $com = new DbConnect();
        $sql = 'SELECT  t2.id , t2.user_id ,t2.uid ,  t1.`address_id`, t1.`raion_id`, t1.`house_id`, (case when t3.`osmd_id` = 0 then "Внески ОСББ" else t3.abbr end) as osbb, t1.`kod`, t1.`address`, t1.`nanim`, t1.`fio`, t1.`order`, t1.`data`, t1.`area_full`, t1.`area_life`, t1.`area_dop`, t1.`area_balk`, t1.`area_otopl`, t1.`room`,
    (case when t1.`privat` = "да" then true else false end) as privat , t1.`tenant`, t1.`tenant_tbo`, t1.`podnan`, t1.`absent`,(case when t1.`subsidia` = "да" then true else false end) as subsidia, (case when t1.`vxvoda` = "да" then true else false end) as vxvoda, (case when t1.`teplomer` = "да" then true else false end) as teplomer, t1.`distributor`, t1.`dvodomer_id`, t1.`dteplomer_id`,(case when t1.`lift` = "да" then true else false end) as lift, (case when t1.`kvartplata` = "да" then true else false end) as kvartplata,
    (case when t1.`otoplenie` = "да" then true else false end) as otoplenie, (case when t1.`privat` = "да" then true else false end) as ateplo, (case when t1.`podogrev` = "да" then true else false end) as podogrev, (case when t1.`voda` = "да" then true else false end) as voda, (case when t1.`stoki` = "да" then true else false end) as stoki, (case when t1.`avoda` = "да" then true else false end) as avoda, (case when t1.`astoki` = "да" then true else false end) as astoki, (case when t1.`tbo` = "да" then true else false end) as tbo, t1.`tarif_kv`, t1.`tarif_ot`, t1.`tarif_aot`, t1.`tarif_gv`, t1.`tarif_xv`, t1.`tarif_st`, t1.`tarif_tbo`, (case when t1.`aggr_kv` = "да" then true else false end) as aggr_kv, (case when t1.`aggr_voda` = "да" then true else false end) as aggr_voda ,(case when t1.`aggr_teplo` = "да" then true else false end) as aggr_teplo,(case when t1.`aggr_tbo` = "да" then true else false end) as aggr_tbo,   (case when t1.`boiler` = "да" then true else false end) as boiler, t1.`enaudit`, t1.`enaudit_id`, t1.`tne`, t1.`kte`, t1.`length`, t1.`diametr`, t1.`heated`, t1.`ztp`, t1.`ovu`, t1.`paused`, t1.`phone`, t1.`email`, t1.`osmd`, t3.`osmd_id`,IFNULL(t4.ipay , 0) as ipay,IFNULL( t4.pb , 0) as pb , IFNULL(t4.mtb , 0 ) as mtb ,  t1.`what_change`, t1.`operator`, t1.`data_change`, t1.`data_in`     
    FROM YISGRAND.MYFLAT as t2  
    LEFT JOIN YIS.APPARTMENT as t1 USING(`address_id`) 
    LEFT JOIN YIS.HOUSE as t3 on t3.house_id = t2.house_id 
    LEFT JOIN YISGRAND.OSMD as t4 on t4.osmd_id = t1.osmd_id 
    WHERE  t2.uid = "'.$uid.'"';
//        print_r($sql);
        return mysqli_query($com->getDb(), $sql);
    }
    //blocks
    public function getBlocks(){
        $com = new DbConnect();
        $sql = 'SELECT * FROM YIS.RAION as t1 WHERE t1.raion_id in (1,3,4,5,2,6,7,11,12) ORDER BY t1.raion_id';
//   $sql = 'SELECT "0" as raion_id , "Выберите район" as raion UNION SELECT t2.raion_id , t2.raion FROM YIS.RAION as t2 WHERE t2.raion_id in (1,3,4,5,2,6,7,11,12)';
        return mysqli_query($com->getDb(), $sql);
    }
    //streets
    public function getStreetsFromBlock($block_id){
        $com = new DbConnect();
        $sql = 'SELECT t1.street_id, t1.street FROM YIS.STREET as t1 ,YIS.HOUSE as t2'
            .' WHERE t1.street_id=t2.street_id AND t2.raion_id= '.$block_id.' GROUP BY t1.street_id ORDER BY t1.street';
        return mysqli_query($com->getDb(), $sql);
    }
    //houses
    public function getHousesFromStreet($street_id , $block_id){
        $com = new DbConnect();
        $sql = 'SELECT t1.house_id, t1.house FROM YIS.HOUSE as t1'
            .' WHERE t1.street_id='.$street_id.' AND t1.raion_id= '.$block_id.' ORDER BY t1.house';
        return mysqli_query($com->getDb(), $sql);
    }
    //flats
    public function getFlatsFromHouse($house_id){
        $com = new DbConnect();
        $sql = 'SELECT t1.address_id , t1.address , t1.kod FROM YIS.ADDRESS as t1 WHERE t1.house_id = '.$house_id.' ORDER BY CAST(t1.appartment AS signed)';
        return mysqli_query($com->getDb(), $sql);
    }
    //insertFlat
  
    //family
    public function getFamilyFromFlat($address_id){
        $com = new DbConnect();
        $sql = 'SELECT t1.* , (case when t1.`subsidia` = "да" then TRUE else FALSE end) as subsidia ,  (case when t1.`vkl` = "да" then TRUE else FALSE end) as vkl , (case when t1.`sex` = "Men" then "Чоловік" when t1.`sex` = "Women" then "Жінка" else "" end) as sex FROM YISGRAND.FAMALY as t1 WHERE t1.address_id = '.$address_id.' ORDER BY t1.surname  , t1.firstname ';
        return mysqli_query($com->getDb(), $sql);
    }

   
    public function deleteFlatByUser($address_id , $uid){
        $com = new DbConnect();
        $sql = 'DELETE FROM  YISGRAND.MYFLAT WHERE address_id = '.$address_id.'  and uid = "'.$uid.'"';
//        print ($sql);
        mysqli_query( $com->getDb(), $sql);
        return $com->getDb();
    }
    public function updateBti($address_id , $phone , $email){
        $com = new DbConnect();
        $sql = 'UPDATE YIS.APPARTMENT as t1 SET t1.email = "'.$email.'" , t1.phone = "'.$phone.'" WHERE t1.address_id = '.$address_id.' ';
        mysqli_query( $com->getDb(), $sql);
        return $com->getDb();
    }


    public function getFlatServices($address_id ,$house_id , $service ,$total , $qty){
        $com = new DbConnect();
        $sql = "";
        if($total == false) {
            switch ($qty) {
                case 1 :
                    $limit = 'LIMIT 12';
                    break;
                case 0 :
                    $limit = '';
                    break;
            }

            switch ($service) {

                case 1 :
                    $sql = 'SELECT "voda" as service , "Водопост-ня" as service1 , "Водовід-ня" as service2 , "Аб.водопост." as service3 , "Аб.водовід." as service4 ,  t1.address_id , t1.data  , IFNULL(t1.zadol , 0) as zadol1, IFNULL(t2.zadol , 0 ) as zadol2, IFNULL(t12.zadol , 0) as zadol3 , IFNULL(t13.zadol, 0) as zadol4,
    IFNULL(t1.zadol , 0) + IFNULL(t2.zadol , 0) + IFNULL(t12.zadol , 0 ) + IFNULL(t13.zadol ,0) as zadol,
    IFNULL(t1.nachisleno, 0) as nachisleno1, IFNULL(t2.nachisleno , 0 ) as nachisleno2, IFNULL(t12.nachisleno, 0) as nachisleno3 , IFNULL(t13.nachisleno, 0) as nachisleno4,
    IFNULL(t1.nachisleno , 0) + IFNULL(t2.nachisleno,0) + IFNULL(t12.nachisleno , 0) + IFNULL(t13.nachisleno ,0) as nachisleno,
     IFNULL(t1.oplacheno, 0) as oplacheno1, IFNULL(t2.oplacheno , 0 ) as oplacheno2, IFNULL(t12.oplacheno, 0) as oplacheno3 , IFNULL(t13.oplacheno, 0) as oplacheno4,
    IFNULL(t1.oplacheno ,0) + IFNULL(t2.oplacheno , 0) + IFNULL(t12.oplacheno,0) + IFNULL(t13.oplacheno , 0) as oplacheno,
    IFNULL(t1.dolg, 0) as dolg1, IFNULL(t2.dolg , 0 ) as dolg2, IFNULL(t12.dolg, 0) as dolg3 , IFNULL(t13.dolg, 0) as dolg4,
    IFNULL(t1.dolg , 0) + IFNULL(t2.dolg  , 0)+ IFNULL(t12.dolg , 0) + IFNULL(t13.dolg , 0) as dolg
    FROM YIS.VODA as t1
    LEFT JOIN YIS.STOKI as t2 USING(address_id,data)
    LEFT JOIN YIS.AVODA as t12 USING(address_id , data)
    LEFT JOIN YIS.ASTOKI as t13 USING(address_id , data)
    WHERE t1.address_id = ' . $address_id . ' ORDER BY t1.data DESC  '. $limit . ' ';
                    break;

                case 2 :
                    $sql = 'SELECT "teplo" as service ,"Тепл.енергія" as service1 , "Аб.тепл.енергія" as service2 , "ПТН" as service3 , "Підігрів" as service4 , t1.address_id , t1.data ,  IFNULL(t1.zadol , 0) as zadol1, IFNULL(t2.zadol , 0 ) as zadol2, IFNULL(t12.zadol , 0) as zadol3 , IFNULL(t13.zadol, 0) as zadol4,
    IFNULL(t1.zadol , 0) + IFNULL(t2.zadol , 0) + IFNULL(t12.zadol , 0 ) + IFNULL(t13.zadol ,0) as zadol,
    IFNULL(t1.nachisleno, 0) as nachisleno1, IFNULL(t2.nachisleno , 0 ) as nachisleno2, IFNULL(t12.nachisleno, 0) as nachisleno3 , IFNULL(t13.nachisleno, 0) as nachisleno4,
    IFNULL(t1.nachisleno , 0) + IFNULL(t2.nachisleno,0) + IFNULL(t12.nachisleno , 0) + IFNULL(t13.nachisleno ,0) as nachisleno,
     IFNULL(t1.oplacheno, 0) as oplacheno1, IFNULL(t2.oplacheno , 0 ) as oplacheno2, IFNULL(t12.oplacheno, 0) as oplacheno3 , IFNULL(t13.oplacheno, 0) as oplacheno4,
    IFNULL(t1.oplacheno ,0) + IFNULL(t2.oplacheno , 0) + IFNULL(t12.oplacheno,0) + IFNULL(t13.oplacheno , 0) as oplacheno,
    IFNULL(t1.dolg, 0) as dolg1, IFNULL(t2.dolg , 0 ) as dolg2, IFNULL(t12.dolg, 0) as dolg3 , IFNULL(t13.dolg, 0) as dolg4,
    IFNULL(t1.dolg , 0) + IFNULL(t2.dolg  , 0)+ IFNULL(t12.dolg , 0) + IFNULL(t13.dolg , 0) as dolg
    FROM YIS.OTOPLENIE as t1
    LEFT JOIN YIS.ATEPLO as t2 USING(address_id,data)
    LEFT JOIN YIS.PTN as t12 USING(address_id , data)
    LEFT JOIN YIS.PODOGREV as t13 USING(address_id , data)
    WHERE t1.address_id = ' . $address_id . ' ORDER BY t1.data DESC '. $limit . ' ';
                    break;

                case 3 :
                    $sql = 'SELECT "tbo" as service , "Вивіз ТПВ" as service1 , "none" as service2 , "none"   as service3 , "none" as service4 , 
       t1.address_id , t1.data , 
    IFNULL(t1.zadol , 0) as zadol1 ,
    IFNULL(t1.zadol , 0) as zadol,
    IFNULL(t1.nachisleno, 0) as nachisleno1 ,
    IFNULL(t1.nachisleno, 0) as nachisleno ,
    IFNULL(t1.oplacheno ,0) as oplacheno1 ,
    IFNULL(t1.oplacheno ,0) as oplacheno ,
    IFNULL(t1.dolg , 0) as dolg1,
    IFNULL(t1.dolg , 0) as dolg
    FROM YIS.TBO as t1
    WHERE t1.address_id = ' . $address_id . ' ORDER BY t1.data DESC '. $limit . ' ';
                    break;

                case 4 :
                    if ($house_id == 22) {
                        $sql = 'SELECT "kv" as service,  "Внески жит.фонд" as service1 , "Ремонтний фонд" as service2 , "none" as service3 , "none" as service4 , t1.address_id , t1.data , IFNULL(t1.zadol , 0) as zadol1, IFNULL(t3.zadol , 0 ) as zadol2 ,
    IFNULL(t1.zadol , 0) + IFNULL(t3.zadol , 0) as zadol,
    IFNULL(t1.nachisleno, 0) as nachisleno1, IFNULL(t3.nachisleno , 0 ) as nachisleno2 ,
    IFNULL(t1.nachisleno , 0) + IFNULL(t3.nachisleno ,0) as nachisleno,
     IFNULL(t1.oplacheno, 0) as oplacheno1, IFNULL(t3.oplacheno , 0 ) as oplacheno2 ,
    IFNULL(t1.oplacheno ,0) + IFNULL(t3.oplacheno , 0) as oplacheno ,
    IFNULL(t1.dolg, 0) as dolg1, IFNULL(t3.dolg , 0 ) as dolg2 ,
    IFNULL(t1.dolg , 0) + IFNULL(t3.dolg  , 0) as dolg
    FROM OSBB.KVARTPLATA as t1
    LEFT JOIN OSBB.RFOND as t3 using(address_id , data)
    WHERE t1.address_id = ' . $address_id . ' ORDER BY t1.data DESC '. $limit . ' ';
                    } else {
                        $sql = 'SELECT "kv" as service ,"Внески жит.фонд" as service1 , "Ремонтний фонд" as service2 , "none" as service3 , "none" as service4 , t1.address_id , t1.data ,IFNULL(t1.zadol , 0) as zadol1, IFNULL(t1.rzadol , 0 ) as zadol2 ,
    IFNULL(t1.zadol , 0) + IFNULL(t1.rzadol , 0) as zadol,
    IFNULL(t1.nachisleno, 0) as nachisleno1, IFNULL(t1.remont , 0 ) as nachisleno2 ,
    IFNULL(t1.nachisleno , 0) + IFNULL(t1.remont,0) as nachisleno,
     IFNULL(t1.oplacheno, 0) as oplacheno1, IFNULL(t1.roplacheno , 0 ) as oplacheno2 ,
    IFNULL(t1.oplacheno ,0) + IFNULL(t1.roplacheno , 0) as oplacheno ,
    IFNULL(t1.dolg, 0) as dolg1, IFNULL(t1.rdolg , 0 ) as dolg2 ,
    IFNULL(t1.dolg , 0) + IFNULL(t1.rdolg  , 0) as dolg
    FROM YIS.KVARTPLATA as t1
    WHERE t1.address_id = ' . $address_id . ' ORDER BY t1.data DESC  '. $limit . ' ';
                    }
                    break;
            }
        }else{
            if($house_id == 22){
                $sql = 'SELECT t1.address_id , "total" as service , "none" as service1 , "none" as service2 , "none" as service3 , "none" as service4,  CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01") as data ,  t2.dolg + t3.dolg + t4.dolg + t5.dolg as dolg1  , t6.dolg + t7.dolg + t8.dolg +
           t9.dolg as dolg2 , t10.dolg as dolg3 , t11.dolg + t12.dolg as dolg4 , t2.dolg + t3.dolg + t4.dolg + t5.dolg + t6.dolg + t7.dolg + t8.dolg + t9.dolg + t10.dolg + t11.dolg + t12.dolg as dolg
    FROM YIS.ADDRESS as t1
    LEFT JOIN YIS.VODA as t2 on t1.address_id = t2.address_id and t2.data =CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.STOKI as t3 on t1.address_id = t3.address_id and t3.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.AVODA as t4 on t1.address_id = t4.address_id and t4.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.ASTOKI as t5 on t1.address_id = t5.address_id and t5.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.OTOPLENIE as t6 on t1.address_id = t6.address_id and t6.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.ATEPLO as t7 on t1.address_id = t7.address_id and t7.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.PTN as t8 on t1.address_id = t8.address_id and t8.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.PODOGREV as t9 on t1.address_id = t9.address_id and t9.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.TBO as t10 on t1.address_id = t10.address_id and t10.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN OSBB.KVARTPLATA as t11 on t1.address_id = t11.address_id and t11.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN OSBB.RFOND as t12 on t1.address_id = t12.address_id and t12.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    WHERE t1.address_id = '.$address_id.' ';
            } else {
                $sql = 'SELECT t1.address_id , "total" as service ,"none" as service1 , "none" as service2, "none" as service3 , "none" as service4 ,
       "2000-01-01" as data ,t2.dolg + t3.dolg + t4.dolg + t5.dolg as dolg1  , t6.dolg + t7.dolg + t8.dolg + t9.dolg as dolg2 ,
           t10.dolg as dolg3 , t11.dolg + t11.rdolg as dolg4 , t2.dolg + t3.dolg + t4.dolg + t5.dolg + t6.dolg + t7.dolg + t8.dolg + t9.dolg + t10.dolg + t11.dolg + t11.rdolg as dolg
    FROM YIS.ADDRESS as t1
    LEFT JOIN YIS.VODA as t2 on t1.address_id = t2.address_id and t2.data =CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.STOKI as t3 on t1.address_id = t3.address_id and t3.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.AVODA as t4 on t1.address_id = t4.address_id and t4.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.ASTOKI as t5 on t1.address_id = t5.address_id and t5.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.OTOPLENIE as t6 on t1.address_id = t6.address_id and t6.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.ATEPLO as t7 on t1.address_id = t7.address_id and t7.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.PTN as t8 on t1.address_id = t8.address_id and t8.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.PODOGREV as t9 on t1.address_id = t9.address_id and t9.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.TBO as t10 on t1.address_id = t10.address_id and t10.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    LEFT JOIN YIS.KVARTPLATA as t11 on t1.address_id = t11.address_id and t11.data = CONCAT(EXTRACT(YEAR_MONTH FROM CURDATE()),"01")
    WHERE t1.address_id = '.$address_id.' ';
            }
        }
        // print_r($sql);
        return mysqli_query($com->getDb(), $sql);
    }

    public function getFlatPayments($address_id)
    {
        $com = new DbConnect();
        $sql = '
SELECT t1.`rec_id` , t1.`address_id`,t1.`address`, t1.`god`, t1.`data`,sum(t1.`kvartplata`) as kvartplata,sum(t1.`remont`) as remont ,sum(t1.`ateplo`+t1.`otoplenie`+t1.`podogrev`+t1.`ptn`) as otoplenie, 
        sum(t1.`voda` + t1.`stoki` +t1.`avoda` + t1.`astoki`) as voda,sum(t1.`tbo`) as tbo ,sum(t1.`summa`) as summa,t1.`prixod`,t1.`kassa`,t1.`nomer`,t1.`operator`,t1.`data_in` FROM YIS.OPLATA as t1 
        WHERE t1.address_id= '.$address_id.' and t1.data > "20161231" GROUP BY t1.`address_id` , t1.`data` ,t1.`prixod` order by  t1.data DESC;';

        return mysqli_query($com->getDb(), $sql);
    }

    public function getWaterMeter($address_id)
    {
        $com = new DbConnect();
        $sql = 'Select t1.* , t1.paused+t1.spisan+t1.out as work from YIS.VODOMER as t1 where t1.address_id = '.$address_id.' order by work ';
        return mysqli_query($com->getDb(), $sql);
    }

    public function getWaterReadings($vodomer_id)
    {
        $com = new DbConnect();
        $sql = 'Select t1.* from YIS.WATER as t1 where t1.vodomer_id = '.$vodomer_id.' ';
        return mysqli_query($com->getDb(), $sql);
    }

    public function addCurrentWaterReading($vodomer_id, $current_value,  $new_value, $date)
    {
        $com = new DbConnect();

        $sql = 'CALL YISGRAND.input_new_pokaz_avodomer_mob('.$vodomer_id.' , "'.$current_value.'" ,"'.$new_value.'" ,"'.$date.'" , @success , @msg);';
//        print_r($sql);
        mysqli_query( $com->getDb(), $sql);
        $sqlCallBack = 'SELECT @success , @msg ';
        return mysqli_query( $com->getDb(), $sqlCallBack);
    }

    public function deleteCurrentWaterReading($pok_id )
    {
        $com = new DbConnect();
        $sql = 'CALL YISGRAND.delete_pokaz_avodomera_mob('.$pok_id.' , @success , @msg);';
//        print_r($sql);
        mysqli_query( $com->getDb(), $sql);
        $sqlCallBack = 'SELECT @success , @msg ';
        return mysqli_query( $com->getDb(), $sqlCallBack);
    }

    public function getHeatMeter($address_id)
    {
        $com = new DbConnect();
        $sql = 'Select t1.*  ,t1.spisan+t1.out as work , '.$address_id.' as address_id from YIS.TEPLOMER as t1 where t1.address_id = '.$address_id.' order by work';
        return mysqli_query($com->getDb(), $sql);
    }

    public function getHeatReadings($teplomer_id)
    {
        $com = new DbConnect();
        $sql = 'Select t1.* from YIS.PTEPLOMER as t1 where t1.teplomer_id = '.$teplomer_id.' ';
        return mysqli_query($com->getDb(), $sql);
    }

    public function addCurrentHeatReading( $teplomer_id,  $current_value, $new_value,  $date)
    {
        $com = new DbConnect();

        $sql = 'CALL YISGRAND.input_new_pokaz_ateplomera_mob('.$teplomer_id.' , "'.$current_value.'" ,"'.$new_value.'" ,"'.$date.'" , @success , @msg);';
        mysqli_query( $com->getDb(), $sql);
        $sqlCallBack = 'SELECT @success , @msg ';
        return mysqli_query( $com->getDb(), $sqlCallBack);
    }

    public function deleteCurrentHeatReading( $pok_id)
    {
        $com = new DbConnect();
        $sql = 'CALL YISGRAND.delete_pokaz_ateplomera_mob('.$pok_id.' , @success , @msg);';
//        print_r($sql);
        mysqli_query( $com->getDb(), $sql);
        $sqlCallBack = 'SELECT @success , @msg ';
        return mysqli_query( $com->getDb(), $sqlCallBack);
    }

}
