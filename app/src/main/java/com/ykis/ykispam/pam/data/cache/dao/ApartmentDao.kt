package com.ykis.ykispam.pam.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity

@Dao
interface ApartmentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addApartmentByUser(apartment:List<ApartmentEntity>)

    @Query("delete from apartment")
    fun deleteAllApartments()

    @Query("delete from apartment where address_id = :addressId")
    fun deleteFlat(addressId :Int)
    @Query("select * from apartment where address_id = :addressId")
    fun getFlatById(addressId :Int) : ApartmentEntity


    @Query("select * from apartment")
    fun getApartmentsByUser(): List<ApartmentEntity>


}