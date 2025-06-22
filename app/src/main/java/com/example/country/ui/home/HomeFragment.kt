package com.example.country.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.country.CountryApplication
import com.example.country.R
import com.example.country.databinding.FragmentHomeBinding
import com.example.country.extension.NetworkHelper
import com.example.country.extension.viewBinding
import com.example.country.extension.viewModelFactory
import com.example.country.ui.adapter.CountryAdapter
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val _binding by viewBinding(FragmentHomeBinding::bind)

    private val countriesAdapter = CountryAdapter()

    private val _viewModel by viewModels<HomeViewModel>(factoryProducer = {
        viewModelFactory {
            HomeViewModel(
                NetworkHelper(requireContext()),
                CountryApplication.appModule.getCountriesUseCase,
                CountryApplication.appModule.getLocalCountriesUseCase,
                CountryApplication.appModule.insertCountryUseCase,
                CountryApplication.postExecutionThread
            )
        }
    }

    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        observeData()

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