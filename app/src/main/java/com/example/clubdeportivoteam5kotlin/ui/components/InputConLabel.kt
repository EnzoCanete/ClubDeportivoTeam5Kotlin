package com.example.clubdeportivoteam5kotlin.ui.components

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.clubdeportivoteam5kotlin.R

class InputConLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private val label: TextView
    private val input: EditText

    init {
        orientation = VERTICAL

        LayoutInflater.from(context).inflate(
            R.layout.input_con_label,
            this,
            true
        )

        label = findViewById(R.id.label)
        input = findViewById(R.id.input)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.InputConLabel,
            0,
            0
        ).apply {
            try {
                label.text = getString(
                    R.styleable.InputConLabel_labelText
                )

                input.hint = getString(
                    R.styleable.InputConLabel_hintText
                )

                when (getInt(
                    R.styleable.InputConLabel_inputType,
                    1
                )) {
                    1 -> input.inputType =
                        InputType.TYPE_CLASS_TEXT

                    2 -> input.inputType =
                        InputType.TYPE_CLASS_NUMBER

                    3 -> input.inputType =
                        InputType.TYPE_CLASS_TEXT or
                                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                    4 -> input.inputType =
                        InputType.TYPE_CLASS_TEXT or
                                InputType.TYPE_TEXT_VARIATION_PASSWORD
                }

            } finally {
                recycle()
            }
        }
    }
}
