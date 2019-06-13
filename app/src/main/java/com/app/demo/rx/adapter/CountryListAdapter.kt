package com.app.demo.rx.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ahmadrosid.svgloader.SvgLoader
import com.app.demo.rx.activity.CountryListActivity
import com.app.demo.rx.model.CountryModel
import com.app.demo.rx.R


class CountryListAdapter(var context: Context) : RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {

    var countryList: MutableList<CountryModel> = ArrayList()
    lateinit var activity: Activity


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CountryListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.country_list_item,
                parent, false)
        return CountryListViewHolder(view)
    }


    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {

        val countryData = countryList.get(position)
        holder.textView.text = countryData.name

        loadImage(countryData.flag, holder.imageView)

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun setItems(results: MutableList<CountryModel>) {
        countryList = results
        notifyDataSetChanged()
    }

    /**
     * Method to load SVG logo image using library
     */
    private fun loadImage(urlString: String?, imageView: ImageView) {
        SvgLoader.pluck()
                .with(context as Activity?)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(urlString, imageView)
    }

    fun setContext(context: Context, countryListActivity1: CountryListActivity) {
        this.context = context
        this.activity = countryListActivity1
    }


    inner class CountryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.tvCountryName)
        var imageView: ImageView = itemView.findViewById(R.id.ivLogo)
    }
}

