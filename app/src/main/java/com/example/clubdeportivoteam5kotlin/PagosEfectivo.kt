package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView

class PagosEfectivo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagos_efectivo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pagosEfectivo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dni = intent.getStringExtra(Pagos1.EXTRA_DNI).orEmpty()
        val tipo = intent.getStringExtra(Pagos1.EXTRA_TIPO).orEmpty()
        val actividad = intent.getStringExtra(Pagos1.EXTRA_ACTIVIDAD).orEmpty()
        val monto = intent.getDoubleExtra(Pagos1.EXTRA_MONTO, 0.0)
        val tvMonto: TextView = findViewById(R.id.pagosEfectivoMonto)
        tvMonto.text = "Monto a pagar: ${Pagos1.formatearMonto(monto)}"

        findViewById<Button>(R.id.continuarPagoEfectivo).setOnClickListener {
            if (SocioRepository.registrarPago(dni, tipo, actividad.ifEmpty { null }, monto, "efectivo")) {
                val base = "Medio: efectivo\nMonto: ${Pagos1.formatearMonto(monto)}\nDNI: $dni"
                startActivity(Intent(this, AvisoExito::class.java).apply {
                    putExtra("tituloExito", Pagos1.tituloExito(tipo))
                    putExtra(AvisoExito.EXTRA_DETALLE, if (actividad.isEmpty()) base else "$actividad\n$base")
                })
            } else {
                Toast.makeText(this, "No se pudo registrar el pago", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
