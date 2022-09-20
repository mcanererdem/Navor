package com.illaki.navigationtest1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.illaki.navigationtest1.model.Country

class CountryViewModel : ViewModel() {
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val country1 = Country(
            "Türkiye",
            "Asia",
            "Ankara",
            "TRY",
            "Türkçe",
            "www.aa.com"
        )

        val country2 = Country(
            "Portegese",
            "Europe",
            "Lizbon",
            "Euro",
            "Portekizce",
            "www.aa.com"
        )

        val country3 = Country(
            "Vietnam",
            "Asia",
            "Hanoi",
            "dong",
            "Vietnamese",
            "www.aa.com"
        )

        val countryList = arrayListOf<Country>(country1, country2, country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }
}