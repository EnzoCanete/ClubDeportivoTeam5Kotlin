package com.example.clubdeportivoteam5kotlin

import android.content.Context
import android.util.AttributeSet

class BotonIcono @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatImageButton(context, attrs) {

    private var tieneNotificaciones = false

    var onBotonClick: (() -> Unit)? = null

    init {
        actualizarIcono()

        setOnClickListener {
            onBotonClick?.invoke()
        }
    }

    fun setTieneNotificaciones(valor: Boolean) {
        tieneNotificaciones = valor
        actualizarIcono()
    }

    private fun actualizarIcono() {
        setImageResource(
            if (tieneNotificaciones) {
                R.drawable.icono_notf_activa
            } else {
                R.drawable.icono_notif
            }
        )
    }
}