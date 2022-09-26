package com.illaki.navigationtest1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.illaki.navigationtest1.R
import com.illaki.navigationtest1.databinding.FragmentCountryBinding
import com.illaki.navigationtest1.viewmodel.CountryViewModel

class CountryFragment : Fragment() {
    private var countryUUID = 0
    private lateinit var viewModel: CountryViewModel
    private lateinit var dataBinding: FragmentCountryBinding

    private lateinit var tvName: TextView
    private lateinit var tvRegion: TextView
    private lateinit var tvCapital: TextView
    private lateinit var tvCurrency: TextView
    private lateinit var tvLanguage: TextView
    private lateinit var ivFlagImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_country, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initilazing views
        tvName = view.findViewById(R.id.tv_countryName_countryFragment)
        tvRegion = view.findViewById(R.id.tv_countryRegion_countryFragment)
        tvCapital = view.findViewById(R.id.tv_countryCapital_countryFragment)
        tvCurrency = view.findViewById(R.id.tv_countryCurreny_countryFragment)
        tvLanguage = view.findViewById(R.id.tv_countryLanguage_countryFragment)
        ivFlagImage = view.findViewById(R.id.iv_countryFlag_countryFragment)

        arguments?.let {
            countryUUID = CountryFragmentArgs.fromBundle(it).countryUuid

        }
        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUUID)

        oberveLiveData()
    }

    private fun oberveLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner) { country ->
            country?.let {
                dataBinding.selectedCountry = it
            }


//            tvName.text = country.countryName
//            tvCapital.text = country.countryCapital
//            tvRegion.text = country.countryRegion
//            tvCurrency.text = country.countryCurrency
//            tvLanguage.text = country.countryLanguage
//            context?.let {
//                ivFlagImage.downloadFromUrl(country.flagImageUrl!!, placeHolderProgressBar(it))
//            }
        }
    }

}