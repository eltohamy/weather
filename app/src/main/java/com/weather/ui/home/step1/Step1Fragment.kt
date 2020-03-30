package com.weather.ui.home.step1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.R
import com.weather.databinding.FragmentStep1Binding
import com.weather.ui.base.BaseFragment
import com.weather.ui.home.step1.adapters.MarginDecoration
import com.weather.util.Step1ViewModelFactory
import java.util.*

class Step1Fragment : BaseFragment() {

    private lateinit var step1ViewModel: Step1ViewModel
    private lateinit var fragmentStep1Binding: FragmentStep1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStep1Binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_step1, container, false)
        return fragmentStep1Binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActivity!!.activityComponent()!!.inject(this)
        baseActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        baseActivity?.supportActionBar?.setHomeButtonEnabled(false)
        baseActivity?.supportActionBar?.title = getString(R.string.title_step1)
        fragmentStep1Binding.citiesEditText.setOnEditorActionListener() { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    validateCitiesSize()
                    true
                }
                else -> false
            }
        }
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        fragmentStep1Binding.weatherRecycler.layoutManager = linearLayoutManager
        fragmentStep1Binding.weatherRecycler.addItemDecoration(MarginDecoration(activity!!))
        step1ViewModel = ViewModelProvider(this, Step1ViewModelFactory(dataManager))
            .get(Step1ViewModel::class.java)
        step1ViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null)
                showError(errorMessage) else hideError()
        })
        step1ViewModel.loadMoreVisibility.observe(viewLifecycleOwner, Observer {
            finishLoading()
        })
        fragmentStep1Binding.step1ViewModel = step1ViewModel
    }

    private fun validateCitiesSize() {
        val citiesString = fragmentStep1Binding.citiesEditText.text.toString().trim()
        var citiesNames: List<String> = ArrayList<String>()
        if (citiesString.isNotBlank())
            citiesNames = citiesString.toLowerCase(Locale.getDefault()).split(",").map { it.trim() }
                .distinct()
        if (citiesNames.size in 3..7) {
            fragmentStep1Binding.citiesTextInputLayout.error = null
            step1ViewModel.loadWeatherForCities(citiesNames)
        } else {
            fragmentStep1Binding.citiesTextInputLayout.error =
                getString(R.string.invalid_cities_length)
        }
    }

    private fun showError(@StringRes errorMessage: Int) {
        fragmentStep1Binding.textError.setText(errorMessage)
        fragmentStep1Binding.group.visibility = View.VISIBLE
    }

    private fun hideError() {
        fragmentStep1Binding.group.visibility = View.GONE
    }

    private fun finishLoading() {
    }
}