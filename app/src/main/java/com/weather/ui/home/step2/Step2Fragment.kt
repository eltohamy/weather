package com.weather.ui.home.step2

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.Const
import com.weather.R
import com.weather.databinding.FragmentStep2Binding
import com.weather.ui.base.BaseFragment
import com.weather.ui.home.step1.adapters.MarginDecoration
import com.weather.util.GpsUtils
import com.weather.util.Step2ViewModelFactory

class Step2Fragment : BaseFragment() {

    private lateinit var step2ViewModel: Step2ViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var fragmentStep2Binding: FragmentStep2Binding
    private var isGPSEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStep2Binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_step2, container, false)
        return fragmentStep2Binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        baseActivity!!.activityComponent()!!.inject(this)
        baseActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        baseActivity?.supportActionBar?.setHomeButtonEnabled(false)
        baseActivity?.supportActionBar?.title = getString(R.string.title_step2)
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        fragmentStep2Binding.forecastRecycler.layoutManager = linearLayoutManager
        fragmentStep2Binding.forecastRecycler.addItemDecoration(MarginDecoration(activity!!))
        if (fragmentStep2Binding.step2ViewModel == null) {
            step2ViewModel = ViewModelProvider(this, Step2ViewModelFactory(dataManager))
                .get(Step2ViewModel::class.java)
            step2ViewModel.cityName.observe(viewLifecycleOwner, Observer {
                fragmentStep2Binding.cityName.text = it
            })
            step2ViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
                if (errorMessage != null)
                    showError(errorMessage) else hideError()
            })
            step2ViewModel.loadMoreVisibility.observe(viewLifecycleOwner, Observer {
                finishLoading()
            })
            fragmentStep2Binding.step2ViewModel = step2ViewModel
            locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
            GpsUtils(activity as Context).turnGPSOn(object : GpsUtils.OnGpsListener {

                override fun gpsStatus(isGPSEnable: Boolean) {
                    isGPSEnabled = isGPSEnable
                }
            })
        }
    }

    private fun showError(@StringRes errorMessage: Int) {
        fragmentStep2Binding.textError.setText(errorMessage)
        fragmentStep2Binding.group.visibility = View.VISIBLE
    }

    private fun hideError() {
        fragmentStep2Binding.group.visibility = View.GONE
    }

    private fun finishLoading() {
    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Const.GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }

    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> Toast.makeText(context, R.string.permission_request, Toast.LENGTH_LONG)
                .show()

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> Toast.makeText(
                context,
                R.string.permission_request,
                Toast.LENGTH_LONG
            ).show()

            else -> ActivityCompat.requestPermissions(
                activity as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Const.LOCATION_REQUEST
            )
        }
    }

    private fun startLocationUpdate() {
        locationViewModel.getLocationData().observe(this, Observer {
            step2ViewModel.loadWeatherForecast(it.latitude, it.longitude)
        })
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            activity as Context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    activity as Context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            activity as Activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            activity as Activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Const.LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }
}

