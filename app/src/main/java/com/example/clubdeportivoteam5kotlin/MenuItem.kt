package com.example.clubdeportivoteam5kotlin

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


class MenuItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
): LinearLayout(context, attrs) {
    private val icon: ImageView
    private val title: TextView

    init {
        isClickable = true
        isFocusable = true

        LayoutInflater.from(context).inflate(
            R.layout.menu_item,
            this,
            true
        )

        icon = findViewById(R.id.menuItemIcono)
        title = findViewById(R.id.menuItemTitulo)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MenuItem,
            0,
            0
        ).apply {
            try {
                icon.setImageResource(
                    getResourceId(
                        R.styleable.MenuItem_icono,
                        0
                    )
                )

                title.text = getString(
                    R.styleable.MenuItem_menuTitulo
                )
            } finally {
                recycle()
            }
        }
    }
    fun setOnMenuClickListener(action: () -> Unit) {
        setOnClickListener {
            action()
        }
    }

}