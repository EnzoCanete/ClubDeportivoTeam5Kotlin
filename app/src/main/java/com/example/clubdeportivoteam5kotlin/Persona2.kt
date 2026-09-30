package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Persona2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona2)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnRegistrar.setOnClickListener {
            Toast.makeText(this, "Registro procesado con éxito", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AvisoExito::class.java).putExtra("tituloExito", "Registro Exitoso!")
            startActivity(intent)
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}