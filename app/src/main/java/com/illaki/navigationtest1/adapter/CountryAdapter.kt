package com.illaki.navigationtest1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.illaki.navigationtest1.R
import com.illaki.navigationtest1.databinding.ItemCountryBinding
import com.illaki.navigationtest1.model.Country
import com.illaki.navigationtest1.util.downloadFromUrl
import com.illaki.navigationtest1.util.placeHolderProgressBar
import com.illaki.navigationtest1.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryHolder>(), CountryOnClickListener {
    class CountryHolder(var view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_country, parent, false)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater, R.layout.item_country, parent, false)
        return CountryHolder(view)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {

        holder.view.country = countryList[position]
        holder.view.listener = this

//        holder.view.findViewById<TextView>(R.id.name_textView).text = countryList[position].countryName
//        holder.view.findViewById<TextView>(R.id.region_textView).text = countryList[position].countryRegion
//
//        //Glide
//
//        holder.view.setOnClickListener {
//            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
//
//        holder.view.imageView_.downloadFromUrl(countryList[position].flagImageUrl!!, placeHolderProgressBar(holder.view.context))
//
    }

    override fun getItemCount(): Int = countryList.size

    fun update(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onClickListener(v: View) {
        val uuid = v.tv_countyUUID.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}