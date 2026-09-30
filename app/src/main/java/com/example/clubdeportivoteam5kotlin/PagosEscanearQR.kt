package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PagosEscanearQR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagos_escanear_qr)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pagosEscanearQr)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // QR simulado sin cámara; el layout ya muestra el placeholder
        val dni = intent.getStringExtra(Pagos1.EXTRA_DNI).orEmpty()
        val actividad = intent.getStringExtra(Pagos1.EXTRA_ACTIVIDAD).orEmpty()
        val monto = intent.getDoubleExtra(PagosQR.EXTRA_MONTO, 0.0)
        Toast.makeText(this, "QR simulado DNI $dni $$monto", Toast.LENGTH_SHORT).show()

        // "Compartir Link" = confirmar pago simulado
        findViewById<Button>(R.id.btnMenuprincipal).apply {
            text = "Confirmar pago"
            setOnClickListener {
                SocioRepository.marcarCuotaAlDia(dni)
                val base = "Pago con QR simulado de $$monto (DNI $dni)"
                startActivity(Intent(this@PagosEscanearQR, AvisoExito::class.java).apply {
                    putExtra("tituloExito", "Pago exitoso!")
                    putExtra(AvisoExito.EXTRA_DETALLE, if (actividad.isEmpty()) base else "$actividad: $base")
                })
            }
        }

        findViewById<Button>(R.id.botonVolver).setOnClickListener { finish() }
    }
}
