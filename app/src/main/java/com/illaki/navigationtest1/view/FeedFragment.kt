package com.illaki.navigationtest1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.illaki.navigationtest1.R
import com.illaki.navigationtest1.adapter.CountryAdapter
import com.illaki.navigationtest1.viewmodel.CountryViewModel

class FeedFragment : Fragment() {
    private var countryUuid = 0
    private lateinit var countryViewModel: CountryViewModel
    private lateinit var countryRecyclerView: RecyclerView
    private lateinit var tvError: TextView
    private lateinit var loadingProgressBar: ProgressBar
    private var countryAdapter = CountryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        countryViewModel.refreshData()

        countryRecyclerView = view.findViewById<RecyclerView>(R.id.feedFragment_countryList_recylerView)
        tvError = view.findViewById<TextView>(R.id.feedFragment_error_textView)
        loadingProgressBar = view.findViewById<ProgressBar>(R.id.progressBar_FeedLoading)

        countryRecyclerView.layoutManager = LinearLayoutManager(context)
        countryRecyclerView.adapter = countryAdapter

        observeLiveData()
    }

    fun observeLiveData() {
        countryViewModel.countries.observe(viewLifecycleOwner, Observer {countryList ->
            countryList?.let {
                countryRecyclerView.visibility = View.VISIBLE
                countryAdapter.update(countryList)
            }

        })

        countryViewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            if (error) {
                tvError.visibility = View.VISIBLE
            } else {
                tvError.visibility = View.GONE
            }

        })

        countryViewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                loadingProgressBar.visibility = View.VISIBLE
                tvError.visibility = View.GONE
                countryRecyclerView.visibility = View.GONE
            }else {
                loadingProgressBar.visibility = View.GONE
            }
        })
    }
}