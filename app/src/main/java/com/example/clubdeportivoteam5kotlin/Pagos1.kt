package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Pagos1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagos1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pagos1)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dniInput: InputConLabel = findViewById(R.id.DniPagos)
        val rgMedio: RadioGroup = findViewById(R.id.radioOpcionesPagos)

        findViewById<Button>(R.id.continuarPagoUno).setOnClickListener {
            val dni = dniInput.text.trim()
            if (dni.isEmpty()) {
                Toast.makeText(this, "Ingresá un DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (SocioRepository.buscarPorDni(dni) == null) {
                Toast.makeText(this, "No hay socio activo con ese DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            when (rgMedio.checkedRadioButtonId) {
                R.id.radioEfectivo -> Intent(this, PagosEfectivo::class.java)
                R.id.radioDebito -> Intent(this, PagosTarjeta::class.java)
                    .putExtra(PagosTarjeta.EXTRA_TIPO, "debito")
                R.id.radioCredito -> Intent(this, PagosTarjeta::class.java)
                    .putExtra(PagosTarjeta.EXTRA_TIPO, "credito")
                R.id.radioQr -> Intent(this, PagosQR::class.java)
                else -> {
                    Toast.makeText(this, "Seleccioná un medio de pago", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }.let {
                it.putExtra(EXTRA_DNI, dni)
                startActivity(it)
            }
        }
    }

    companion object {
        const val EXTRA_DNI = "EXTRA_DNI"
    }
}
