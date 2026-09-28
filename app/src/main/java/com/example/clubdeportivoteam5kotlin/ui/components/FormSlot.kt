package com.example.clubdeportivoteam5kotlin.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class FormSlot @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    init {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        orientation = VERTICAL
    }

    override fun addView(child: View?) {
        super.addView(child)

        if (child != null && childCount > 1) {
            val layoutParams = child.layoutParams as MarginLayoutParams
            layoutParams.topMargin = 12.dp()
            child.layoutParams = layoutParams
        }
    }

    private fun Int.dp(): Int =
        (this * resources.displayMetrics.density).toInt()
}
