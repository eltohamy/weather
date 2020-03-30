package com.weather.ui.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.weather.AppControl
import com.weather.data.DataManager
import com.weather.di.component.ActivityComponent
import javax.inject.Inject

/**
 * Created by Tohamy on 30/03/2020
 */
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var dataManager: DataManager
    var baseActivity: BaseActivity? = null

    private val activityComponent: ActivityComponent?
        get() = if (baseActivity != null) {
            baseActivity!!.activityComponent()
        } else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppControl[activity!!].component!!.inject(this)
        if (activityComponent != null)
            activityComponent!!.inject(this)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.baseActivity = activity
        }
    }


    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

}
