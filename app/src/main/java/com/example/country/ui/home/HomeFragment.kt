package com.example.country.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.country.CountryApplication
import com.example.country.R
import com.example.country.databinding.FragmentHomeBinding
import com.example.country.extension.NetworkHelper
import com.example.country.extension.viewBinding
import com.example.country.extension.viewModelFactory
import com.example.country.ui.adapter.CountryAdapter
import com.example.country.ui.model.CountriesModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val _binding by viewBinding(FragmentHomeBinding::bind)


    private val _viewModel by viewModels<HomeViewModel>()


    val onCountriesActionCallback by lazy {
        object : CountryAdapter.onSelectCallback {
            override fun onSelect(position: Int, data: CountriesModel.CountryModel) {
                val countryJson = Gson().toJson(data)
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationCountryDetails(
                    countryJson = countryJson
                )
                findNavController().navigate(action)
            }
        }
    }

    private val countriesAdapter = CountryAdapter(onCountriesActionCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        observeData()

        _viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Snackbar.make(view, error, Snackbar.LENGTH_LONG).show()
        }

        _binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                countriesAdapter.filter(newText.orEmpty())
                return true
            }
        })

    }

    private fun initRV() {
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1
        val layout = GridLayoutManager(requireContext(), spanCount)
        _binding.rvCountry.apply {
            adapter = countriesAdapter
            layoutManager = layout
            setOnClickListener { }
        }
    }


    private fun fetchData() {
        lifecycleScope.launch {
            _viewModel.getCountries()
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            _viewModel.getGetCountries.collect {
                countriesAdapter.updateList(it)
            }


        }
    }


}