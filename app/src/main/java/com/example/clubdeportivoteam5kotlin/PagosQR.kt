package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
        val actividad = intent.getStringExtra(Pagos1.EXTRA_ACTIVIDAD).orEmpty()
        val etMonto: InputConLabel = findViewById(R.id.pagosMontoQr)

        findViewById<Button>(R.id.continuarPagoQr).setOnClickListener {
            val monto = etMonto.text.trim().toDoubleOrNull() ?: 0.0
            if (monto <= 0) {
                Toast.makeText(this, "Ingresá un monto mayor a 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(this, PagosEscanearQR::class.java).apply {
                putExtra(Pagos1.EXTRA_DNI, dni)
                putExtra(Pagos1.EXTRA_ACTIVIDAD, actividad)
                putExtra(EXTRA_MONTO, monto)
            })
        }
    }

    companion object {
        const val EXTRA_MONTO = "EXTRA_MONTO_QR"
    }
}
