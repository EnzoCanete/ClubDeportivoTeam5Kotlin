package com.example.clubdeportivoteam5kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Carnet1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carnet1)

        val btnImprimirCarnet = findViewById<Button>(R.id.btnImprimirCarnet)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        // Accion al presionar Imprimir / Guardar
        btnImprimirCarnet.setOnClickListener {
            Toast.makeText(this, "Procesando carnet...", Toast.LENGTH_SHORT).show()
        }

        // Accion para regresar a la pantalla anterior
        btnRegresar.setOnClickListener {
            finish()
        }
    }
}