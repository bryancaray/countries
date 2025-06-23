package com.example.country.ui.countrydetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.country.CountryApplication
import com.example.country.R
import com.example.country.databinding.FragmentCountryDetailsBinding
import com.example.country.databinding.FragmentHomeBinding
import com.example.country.domain.model.Countries
import com.example.country.extension.NetworkHelper
import com.example.country.extension.viewBinding
import com.example.country.extension.viewModelFactory
import com.example.country.ui.home.HomeViewModel
import com.example.country.ui.model.CountriesModel
import com.google.gson.Gson

class CountryDetailsFragment : Fragment(R.layout.fragment_country_details) {


    private val _binding by viewBinding(FragmentCountryDetailsBinding::bind)

    private val _viewModel by viewModels<CountryDetailsViewModel>(factoryProducer = {
        viewModelFactory {
            CountryDetailsViewModel(
            )
        }
    })
    private lateinit var country: CountriesModel.CountryModel

    val args: CountryDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryJson = args.countryJson
        country = Gson().fromJson(countryJson, CountriesModel.CountryModel::class.java)
        initView()
    }

    private fun initView() {
        with(_binding) {
            with(country) {
                txtDetailCountry.text = "$name ($code)"
                txtCapital.text = capital
                txtRegion.text = region
                txtCurrency.text = currency
                txtLanguage.text = language
                Glide.with(requireContext()).load(flag).error(R.drawable.ic_error).into(imgFlag)

            }
        }
    }
}