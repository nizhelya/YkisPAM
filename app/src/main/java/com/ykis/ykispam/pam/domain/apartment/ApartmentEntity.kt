package com.ykis.ykispam.pam.domain.apartment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json




@Entity(tableName = "apartment")
data class ApartmentEntity(
    val id: Int = 0,
    @Json(name = "user_id")
    @ColumnInfo(name = "user_id")
    val userId: Int = 0,
    val uid: String? = null,
    @PrimaryKey(autoGenerate = false)
    @Json(name = "address_id")
    @ColumnInfo(name = "address_id")
    val addressId: Int = 0,

    @Json(name = "raion_id")
    @ColumnInfo(name = "block_id")
    val blockId: Int = 0,
    @Json(name = "house_id")
    @ColumnInfo(name = "house_id")
    val houseId: Int = 0,
//    TODO("Удалить Код")
    val kod: String = "Unknown",
    val address: String = "Unknown",
    val nanim: String = "Unknown",
    val fio: String = "Unknown",
    val order: String = "Unknown",

    @Json(name = "data")
    @ColumnInfo(name = "data")
    val dataOrder: String = "Unknown",

    @Json(name = "area_full")
    @ColumnInfo(name = "area_full")
    val areaFull: Double = 0.00,

    @Json(name = "area_life")
    @ColumnInfo(name = "area_life")
    val areaLife: Double = 0.00,

    @Json(name = "area_dop")
    @ColumnInfo(name = "area_dop")
    val areaDop: Double = 0.00,

    @Json(name = "area_balk")
    @ColumnInfo(name = "area_balk")
    val areaBalk: Double = 0.00,

    @Json(name = "area_otopl")
    @ColumnInfo(name = "area_otopl")
    val areaOtopl: Double = 0.00,
    val tenant: Int = 0,
    val podnan: Int = 0,
    val absent: Int = 0,
    @Json(name = "tenant_tbo")
    @ColumnInfo(name = "tenant_tbo")
    val tenantTbo: Int = 0,
    val room: Int = 0,
    val privat: Byte = 0,
    val subsidia: Byte = 0,
    val vxvoda: Byte = 0,
    val teplomer: Byte = 0,
    val distributor: Byte = 0,
    val lift: Byte = 0,
    val kvartplata: Byte = 0,
    val otoplenie: Byte = 0,
    val ateplo: Byte = 0,
    val podogrev: Byte = 0,
    val voda: Byte = 0,
    val stoki: Byte = 0,
    val avoda: Byte = 0,
    val astoki: Byte = 0,
    val tbo: Byte = 0,
    @Json(name = "aggr_kv")
    @ColumnInfo(name = "aggr_kv")
    val aggrKv: Byte = 0,
    @Json(name = "aggr_voda")
    @ColumnInfo(name = "aggr_voda")
    val aggrVoda: Byte = 0,
    @Json(name = "aggr_teplo")
    @ColumnInfo(name = "aggr_teplo")
    val aggrTeplo: Byte = 0,
    @Json(name = "aggr_tbo")
    @ColumnInfo(name = "aggr_tbo")
    val aggrTbo: Byte = 0,
    val boiler: Byte = 0,
    val enaudit: Byte = 0,
    val heated: Byte = 0,
    val ztp: Byte = 0,
    val ovu: Byte = 0,
    val paused: Byte = 0,
    val osmd: Byte = 0,
    @Json(name = "osmd_id")
    @ColumnInfo(name = "osmd_id")
    val osmdId: Int = 0,
    val osbb: String = "Unknown",
    @Json(name = "what_change")
    @ColumnInfo(name = "what_change")
    val whatChange: String = "Unknown",
    @Json(name = "data_change")
    @ColumnInfo(name = "data_change")
    val dataChange: String = "Unknown",
    @Json(name = "enaudit_id")
    @ColumnInfo(name = "enaudit_id")
    val enaudit_id: Int = 0,
    @Json(name = "tarif_kv")
    @ColumnInfo(name = "tarif_kv")
    val tarifKv: Double = 0.00,
    @Json(name = "tarif_ot")
    @ColumnInfo(name = "tarif_ot")
    val tarifOt: Double = 0.00,
    @Json(name = "tarif_aot")
    @ColumnInfo(name = "tarif_aot")
    val tarifAot: Double = 0.00,
    @Json(name = "tarif_gv")
    @ColumnInfo(name = "tarif_gv")
    val tarifGv: Double = 0.00,
    @Json(name = "tarif_xv")
    @ColumnInfo(name = "tarif_xv")
    val tarifXv: Double = 0.00,
    @Json(name = "tarif_st")
    @ColumnInfo(name = "tarif_st")
    val tarifSt: Double = 0.00,
    @Json(name = "tarif_tbo")
    @ColumnInfo(name = "tarif_tbo")
    val tarifTbo: Double = 0.00,
    val tne: Double = 0.00,
    val kte: Double = 0.00,
    val length: Double = 0.00,
    val diametr: Double = 0.00,
    @Json(name = "dvodomer_id")
    @ColumnInfo(name = "dvodomer_id")
    val dvodomer_id: Int = 0,
    @Json(name = "dteplomer_id")
    @ColumnInfo(name = "dteplomer_id")
    val dteplomer_id: Int = 0,
    val email: String = "Unknown",
    val phone: String = "Unknown",
    val operator: String = "Unknown",
    @Json(name = "data_in")
    @ColumnInfo(name = "data_in")
    val data_in: String = "Unknown",
    val ipay: Int = 0,
    val pb: Int = 0,
    val mtb: Int = 0
)

