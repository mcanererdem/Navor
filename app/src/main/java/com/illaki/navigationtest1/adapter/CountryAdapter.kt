package com.illaki.navigationtest1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.illaki.navigationtest1.R
import com.illaki.navigationtest1.model.Country

class CountryAdapter(val countryList: ArrayList<Country>) :RecyclerView.Adapter<CountryAdapter.CountryHolder>() {
    class CountryHolder(var view: View): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country, parent, false)
        return CountryHolder(view)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.name_textView).text = countryList[position].countryName
        holder.view.findViewById<TextView>(R.id.region_textView).text = countryList[position].countryRegion

        //Glide
    }

    override fun getItemCount(): Int = countryList.size

    fun update(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}