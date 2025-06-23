package com.example.country.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.country.R
import com.example.country.databinding.ItemCountryBinding
import com.example.country.ui.model.CountriesModel
import kotlin.properties.Delegates

class CountryAdapter(val callback: onSelectCallback) :
    RecyclerView.Adapter<CountryAdapter.ItemViewHolder>() {

    private var countryList: List<CountriesModel.CountryModel> by Delegates.observable(ArrayList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    private var fullList: List<CountriesModel.CountryModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = countryList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = countryList.size

    fun updateList(items: List<CountriesModel.CountryModel>) {
        countryList = items
        fullList = items
    }

    fun updateData(position: Int, isCheck: Boolean) {
        notifyItemChanged(position)
    }

    class ItemViewHolder(
        private val binding: ItemCountryBinding, private val callback: onSelectCallback
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CountriesModel.CountryModel) {
            binding.txtCountry.text = "${item.name},${item.region}"
            binding.txtCode.text = item.code
            binding.txtCapital.text = item.capital
            binding.root.setOnClickListener {
                callback.onSelect(adapterPosition, item)
            }
            Glide.with(binding.root.context).load(item.flag).error(R.drawable.ic_error).into(binding.imgFlag)
        }
    }

    fun filter(query: String) {
        countryList = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.name.contains(query, ignoreCase = true) || it.region.contains(
                    query, ignoreCase = true
                ) || it.code.contains(query, ignoreCase = true) || it.capital.contains(
                    query, ignoreCase = true
                )
            }
        }
    }

    interface onSelectCallback {
        fun onSelect(position: Int, data: CountriesModel.CountryModel)
    }
}
