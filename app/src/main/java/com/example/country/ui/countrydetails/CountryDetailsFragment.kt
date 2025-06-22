package com.example.country.ui.countrydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.country.databinding.FragmentCountryDetailsBinding
import com.example.country.databinding.FragmentHomeBinding
import com.example.country.extension.viewBinding
import com.example.country.ui.home.HomeViewModel

class CountryDetailsFragment : Fragment() {


    private val _binding by viewBinding(FragmentHomeBinding::bind)

    private val _viewModel by viewModels<HomeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}