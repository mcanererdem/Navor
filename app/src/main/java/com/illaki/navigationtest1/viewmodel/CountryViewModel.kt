package com.illaki.navigationtest1.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.illaki.navigationtest1.model.Country
import com.illaki.navigationtest1.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application) {
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int) {

        launch {
            val dao = CountryDatabase(getApplication()).getDao()
            val country = dao.getCountryWithUUID(uuid)
            countryLiveData.value = country
        }
    }
}