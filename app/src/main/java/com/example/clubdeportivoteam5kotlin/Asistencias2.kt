package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Asistencias2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asistencias2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.asistencias2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBuscarasist = findViewById<Button>(R.id.btnBuscarasist)
        btnBuscarasist.setOnClickListener {
            val intent = Intent (this, Asistencias3::class.java)
            startActivity(intent)
        }

        val btnRegresar2 = findViewById<Button>(R.id.btnregasist2)
        btnRegresar2.setOnClickListener {
            val intent = Intent (this, Asistencias1::class.java)
            startActivity(intent)
        }
    }
}