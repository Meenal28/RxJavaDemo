package com.app.demo.rx.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.app.demo.rx.adapter.CountryListAdapter
import com.app.demo.rx.model.CountryModel
import com.app.demo.rx.R
import com.app.demo.rx.interfaces.CountryListView
import com.app.demo.rx.presenter.CountryListPresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_country_list.*

class CountryListActivity : AppCompatActivity(), CountryListView {


    var countryListAdapter: CountryListAdapter? = null
    lateinit var countryListPresenter: CountryListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        initViews()

    }

    /**
     * Method to init views
     */
    private fun initViews() {
        rvCountryData.layoutManager = LinearLayoutManager(this)
        countryListAdapter = CountryListAdapter(this)
        rvCountryData.adapter = countryListAdapter

        countryListPresenter = CountryListPresenter(this)
        countryListPresenter.getCountryData()
        search.setOnClickListener { countryListPresenter.applySearchObservable(search) }

    }

    override fun showProgressDialog() {
        if (!progressbar.isShown) {
            progressbar.visibility = View.VISIBLE

        }
    }

    override fun dismissProgressDialog() {
        progressbar.visibility = View.GONE
    }


    override fun onSuccess(response: MutableList<CountryModel>) {
        dismissProgressDialog()
        if (rvCountryData != null && countryListAdapter != null && response != null) {
            countryListAdapter?.setItems(response)
        }

    }

    override fun onFailure() {
        Toast.makeText(this, getString(R.string.network_msg), Toast.LENGTH_SHORT).show()
        dismissProgressDialog()
    }

}
