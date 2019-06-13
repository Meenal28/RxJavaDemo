package com.app.demo.rx.interfaces

import com.app.demo.rx.model.CountryModel
import io.reactivex.Observable

interface CountryListView {

    fun onSuccess(response: MutableList<CountryModel>)
    fun onFailure()
    fun showProgressDialog()
    fun dismissProgressDialog()

}