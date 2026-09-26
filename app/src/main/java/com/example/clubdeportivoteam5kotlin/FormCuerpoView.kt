package com.example.clubdeportivoteam5kotlin

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class FormCuerpoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private val tituloForm = TextView(context)

    init {
        orientation = VERTICAL

        setPadding(
            24.dp(),
            24.dp(),
            24.dp(),
            24.dp()
        )

        setBackgroundResource(R.drawable.fondo_form_cuerpo)

        addView(tituloForm)

        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )

        params.bottomMargin = 20.dp()

        tituloForm.textSize = 24f
        tituloForm.gravity = Gravity.CENTER

        tituloForm.layoutParams = params


        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FormCuerpoView,
            0,
            0
        ).apply {
            try {
                tituloForm.text = getString(
                    R.styleable.FormCuerpoView_tituloText
                )
            } finally {
                recycle()
            }
        }
    }

    override fun addView(
        child: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ) {
        super.addView(child, index, params)

        if (child != null && child != tituloForm && childCount > 2) {

            val anterior = getChildAt(childCount - 2)

            val layoutParams =
                anterior.layoutParams as MarginLayoutParams

            layoutParams.bottomMargin = 24.dp()

            anterior.layoutParams = layoutParams
        }
    }

    private fun Int.dp(): Int =
        (this * resources.displayMetrics.density).toInt()
}