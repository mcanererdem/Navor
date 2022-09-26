package com.illaki.navigationtest1.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.illaki.navigationtest1.R
import com.illaki.navigationtest1.model.Country
import com.illaki.navigationtest1.service.CountryAPIService
import com.illaki.navigationtest1.service.CountryDatabase
import com.illaki.navigationtest1.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private val customSharedPreferences = CustomSharedPreferences(getApplication())
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        Toast.makeText(getApplication(), "Countries From Room", Toast.LENGTH_SHORT).show()

        val updateTime = customSharedPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQL()
        } else {
            getDataFromAPI()
        }
    }

    private fun refreshFromAPI() {
        getDataFromAPI()
    }

    fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(countryAPIService.getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInRoom(t)
                        Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_SHORT).show()
                    }
                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                    }
                }))
    }

    private fun getDataFromSQL() {
        countryLoading.value = true

        launch {
            val countries = CountryDatabase(getApplication()).getDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries From Room", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInRoom(list: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).getDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())

            var i = 0
            while (i < listLong.size) {
                list[i].uuid = listLong[i].toInt()
                i++
            }
            showCountries(list)
        }
        customSharedPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}