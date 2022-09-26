package com.illaki.navigationtest1.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.illaki.navigationtest1.model.Country

@Dao
interface CountryDao{

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    @Query("SELECT * FROM Country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM Country WHERE uuid = :countryUUID")
    suspend fun getCountryWithUUID(countryUUID: Int) : Country

    @Query("DELETE FROM Country WHERE uuid = :countryUUID")
    suspend fun deleteCountryWithUUID(countryUUID: Int)

    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()
}