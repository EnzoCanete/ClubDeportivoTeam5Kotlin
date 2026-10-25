package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PagosQR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagos_qr)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pagosQr)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dni = intent.getStringExtra(Pagos1.EXTRA_DNI).orEmpty()
        val tipo = intent.getStringExtra(Pagos1.EXTRA_TIPO).orEmpty()
        val medio = intent.getStringExtra(Pagos1.EXTRA_MEDIO).orEmpty()
        val actividad = intent.getStringExtra(Pagos1.EXTRA_ACTIVIDAD).orEmpty()
        val monto = intent.getDoubleExtra(Pagos1.EXTRA_MONTO, 0.0)
        val tvMonto: TextView = findViewById(R.id.pagosMontoQr)
        tvMonto.text = "Monto a pagar: ${Pagos1.formatearMonto(monto)}"

        findViewById<Button>(R.id.continuarPagoQr).setOnClickListener {
            startActivity(Intent(this, PagosEscanearQR::class.java).apply {
                putExtra(Pagos1.EXTRA_DNI, dni)
                putExtra(Pagos1.EXTRA_TIPO, tipo)
                putExtra(Pagos1.EXTRA_MEDIO, medio)
                putExtra(Pagos1.EXTRA_ACTIVIDAD, actividad)
                putExtra(EXTRA_MONTO, monto)
            })
        }
    }

    companion object {
        const val EXTRA_MONTO = "EXTRA_MONTO_QR"
    }
}