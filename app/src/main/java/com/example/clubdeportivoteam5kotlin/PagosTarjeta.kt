package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PagosTarjeta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagos_tarjetas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pagosTarjetas)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dni = intent.getStringExtra(Pagos1.EXTRA_DNI).orEmpty()
        val tipo = intent.getStringExtra(Pagos1.EXTRA_TIPO).orEmpty()
        val medio = intent.getStringExtra(Pagos1.EXTRA_MEDIO).orEmpty()
        val monto = intent.getDoubleExtra(Pagos1.EXTRA_MONTO, 0.0)
        val actividad = intent.getStringExtra(Pagos1.EXTRA_ACTIVIDAD).orEmpty()
        val esCredito = intent.getStringExtra(EXTRA_TIPO_TARJETA) == "credito"
        val etNumero: InputConLabel = findViewById(R.id.pagosTarjetaNumero)
        val etCvc: InputConLabel = findViewById(R.id.pagosTarjetaCvc)
        val spnCuotas: Spinner = findViewById(R.id.pagosTarjetaCuotas)
        val cuotas = arrayOf("1", "3", "6")
        spnCuotas.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cuotas)
        // CVC + cuotas solo en crédito; en débito se ocultan
        etCvc.visibility = if (esCredito) View.VISIBLE else View.GONE
        spnCuotas.visibility = if (esCredito) View.VISIBLE else View.GONE

        findViewById<Button>(R.id.continuarPagoTarjeta).setOnClickListener {
            val tarjeta = etNumero.text.trim().filter { it.isDigit() }
            if (tarjeta.length != 16) {
                Toast.makeText(this, "Tarjeta inválida: 16 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var detalle = "Medio: débito\nMonto: ${Pagos1.formatearMonto(monto)}\nDNI: $dni"
            if (esCredito) {
                val cvc = etCvc.text.trim().filter { it.isDigit() }
                if (cvc.length != 3) {
                    Toast.makeText(this, "CVC inválido: 3 dígitos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val cuota = spnCuotas.selectedItem.toString()
                if (cuota !in cuotas) {
                    Toast.makeText(this, "Cuotas válidas: 1, 3 o 6", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                detalle = "Medio: crédito en $cuota cuotas\nMonto: ${Pagos1.formatearMonto(monto)}\nDNI: $dni"
            }
            if (SocioRepository.registrarPago(dni, tipo, actividad.ifEmpty { null }, monto, medio)) {
                startActivity(Intent(this, AvisoExito::class.java).apply {
                    putExtra("tituloExito", Pagos1.tituloExito(tipo))
                    putExtra(AvisoExito.EXTRA_DETALLE, detalle)
                })
            } else {
                Toast.makeText(this, "No se pudo registrar el pago", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val EXTRA_TIPO_TARJETA = "EXTRA_TIPO_TARJETA"
    }
}