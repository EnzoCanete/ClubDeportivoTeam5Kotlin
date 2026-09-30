package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Persona1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona1)

        val campoNombre = findViewById<InputConLabel>(R.id.campoNombre)
        val campoApellido = findViewById<InputConLabel>(R.id.campoApellido)
        val campoDNI = findViewById<InputConLabel>(R.id.campoDNI)
        val campoCorreo = findViewById<InputConLabel>(R.id.campoCorreo)
        val campoAptoFisico = findViewById<InputConLabel>(R.id.campoAptoFisico)
        val btnContinuar = findViewById<Button>(R.id.btnContinuardatos)

        btnContinuar.setOnClickListener {
            val intent = Intent(this, Persona2::class.java)
            startActivity(intent)
        }
    }
}