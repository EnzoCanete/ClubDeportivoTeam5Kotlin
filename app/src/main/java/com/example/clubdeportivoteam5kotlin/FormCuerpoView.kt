package com.example.clubdeportivoteam5kotlin

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class FormCuerpoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
): LinearLayout(context, attrs) {

    init {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )

        orientation = VERTICAL

        setPadding(
            24.dp(),
            24.dp(),
            24.dp(),
            24.dp()
        )
        setBackgroundResource(R.drawable.fondo_form_cuerpo)
    }

    // Sobreescribe addView para que agregue un gap entre los elementos hijo que no sean el primero
    override fun addView(
        child: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ) {
        super.addView(child, index, params)

        if (child != null && childCount > 1) {
            val layoutParams =
                child.layoutParams as MarginLayoutParams

            layoutParams.topMargin = 24.dp()

            child.layoutParams = layoutParams
        }
    }

    private fun Int.dp(): Int =
        (this * resources.displayMetrics.density).toInt()
}