package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
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
        intent.getStringExtra(EXTRA_DNI)?.takeIf { it.isNotEmpty() }?.let { dniInput.text = it }
        val rgMedio: RadioGroup = findViewById(R.id.radioOpcionesPagos)
        val spnActividad: Spinner = findViewById(R.id.spnActividadPago)
        spnActividad.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ACTIVIDADES_CON_PRECIO)

        findViewById<Button>(R.id.continuarPagoUno).setOnClickListener {
            val dni = dniInput.text.trim()
            if (dni.isEmpty()) {
                Toast.makeText(this, "Ingresá un DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val socio = SocioRepository.buscarPorDni(dni)
            if (socio == null) {
                Toast.makeText(this, "No hay socio activo con ese DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val tipoPago = if (spnActividad.selectedItemPosition == 0) "cuota" else "diaria"
            val nombreActividad = if (spnActividad.selectedItemPosition == 0) null
                else ACTIVIDADES[spnActividad.selectedItemPosition - 1]
            if (nombreActividad == "Musculación" && !socio.aptoFisico) {
                Toast.makeText(this, "Musculación requiere apto físico", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val medio = when (rgMedio.checkedRadioButtonId) {
                R.id.radioEfectivo -> "efectivo"
                R.id.radioDebito -> "debito"
                R.id.radioCredito -> "credito"
                R.id.radioQr -> "qr"
                else -> {
                    Toast.makeText(this, "Seleccioná un medio de pago", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            val destino = when (rgMedio.checkedRadioButtonId) {
                R.id.radioEfectivo -> Intent(this, PagosEfectivo::class.java)
                R.id.radioDebito, R.id.radioCredito -> Intent(this, PagosTarjeta::class.java)
                    .putExtra(PagosTarjeta.EXTRA_TIPO_TARJETA, medio)
                R.id.radioQr -> Intent(this, PagosQR::class.java)
                else -> null
            }!!
            val monto = if (nombreActividad != null) PRECIOS[nombreActividad]!!.toDouble() else 0.0
            destino.apply {
                putExtra(EXTRA_DNI, dni)
                putExtra(EXTRA_TIPO, tipoPago)
                putExtra(EXTRA_ACTIVIDAD, nombreActividad?.let { "$it: $$${PRECIOS[it]}" }.orEmpty())
                putExtra(EXTRA_MEDIO, medio)
                putExtra(EXTRA_MONTO, monto)
            }
            startActivity(destino)
        }
    }

    companion object {
        const val EXTRA_DNI = "EXTRA_DNI"
        const val EXTRA_TIPO = "EXTRA_TIPO_PAGO"
        const val EXTRA_ACTIVIDAD = "EXTRA_ACTIVIDAD"
        const val EXTRA_MEDIO = "EXTRA_MEDIO"
        const val EXTRA_MONTO = "EXTRA_MONTO"
        // precios fijos declarados R13 (legado FormActividadDiaria)
        private val PRECIOS = mapOf(
            "Musculación" to 10000,
            "Nutrición" to 20000,
            "Tenis" to 15000,
            "Spinning" to 10000,
            "Yoga" to 10000,
            "Natación" to 10000
        )
        private val ACTIVIDADES = PRECIOS.keys.toList()
        private val ACTIVIDADES_CON_PRECIO =
            listOf("Cuota mensual") + ACTIVIDADES.map { "$it - $${PRECIOS[it]}" }
    }
}
