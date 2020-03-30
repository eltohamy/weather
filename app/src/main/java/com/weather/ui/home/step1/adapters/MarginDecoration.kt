package com.weather.ui.home.step1.adapters

import android.content.Context
import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView

import com.weather.R

/**
 * Created by Tohamy on 30/03/2020.
 */
class MarginDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val margin: Int = context.resources.getDimensionPixelSize(R.dimen.item_margin)

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.set(margin, margin, margin, margin)
    }
}
