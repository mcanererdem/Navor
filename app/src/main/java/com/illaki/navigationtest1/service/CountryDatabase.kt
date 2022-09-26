package com.illaki.navigationtest1.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.illaki.navigationtest1.model.Country

@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDatabase: RoomDatabase() {

    abstract fun getDao() : CountryDao

    companion object {
        @Volatile private var instance: CountryDatabase? = null

        val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, CountryDatabase::class.java, "countrydatabase"
        ).build()
    }
}