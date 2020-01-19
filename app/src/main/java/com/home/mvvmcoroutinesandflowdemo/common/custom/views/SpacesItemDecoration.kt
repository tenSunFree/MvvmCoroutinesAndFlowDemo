package com.home.mvvmcoroutinesandflowdemo.common.custom.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.apply {
            left = margin
            right = margin
            bottom = margin
            top = if (parent.getChildLayoutPosition(view) == 0) {
                margin
            } else {
                0
            }
        }
    }
}