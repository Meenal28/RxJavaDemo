package com.app.demo.rx.presenter

import android.support.v7.widget.SearchView
import com.app.demo.rx.interfaces.CountryListView
import com.app.demo.rx.model.CountryModel
import com.app.demo.rx.network.ApiInterface
import com.app.demo.rx.network.RetrofitClientInstance
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class CountryListPresenter(var countryListView: CountryListView?) {

    var listOfCountry  = ArrayList<CountryModel>()

    fun getCountryData() {
        countryListView?.showProgressDialog()
        var apiInterface = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface::class.java)
        getObservable(apiInterface).subscribeWith(getObserver())
    }


    private fun getObservable(apiInterface: ApiInterface): Observable<MutableList<CountryModel>> {
        return apiInterface
                .getCountriesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getObserver(): Observer<MutableList<CountryModel>> {
        return object : DisposableObserver<MutableList<CountryModel>>() {
            override fun onComplete() {

            }

            override fun onNext(response: MutableList<CountryModel>) {
                listOfCountry.addAll(response)
                countryListView?.onSuccess(response)
            }

            override fun onError(e: Throwable) {
                countryListView?.onFailure()
            }

        }
    }

    fun applySearchObservable(searchView: SearchView) {
        fromView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter { text ->
                    !text.isEmpty()
                }
                .distinctUntilChanged()
                .map { s -> dataFromList(s) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t -> countryListView?.onSuccess(t) }
    }

    /**
     * Method to get search query matching list item
     */
    private fun dataFromList(query: String): MutableList<CountryModel> {
        val searchList: MutableList<CountryModel> = ArrayList()
        //val filteredList : Observable<List<CountryModel>> = Observable<List<CountryModel>>()
        for (row in listOfCountry) {
            if (row.name != null && row.name.toLowerCase().contains(query.toLowerCase())) {
                searchList.add(row)
            }
        }
        return searchList
    }

    /**
     * Method to set observable on searchView
     */
    fun fromView(searchView: SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null) {
                    subject.onNext(text)
                }
                return true
            }

        })
        return subject
    }

}