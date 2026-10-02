package com.example.clubdeportivoteam5kotlin

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SeparadorTabla(
    private val space: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (position != RecyclerView.NO_POSITION &&
            position < state.itemCount - 1
        ) {
            outRect.bottom = space
        }
    }
}