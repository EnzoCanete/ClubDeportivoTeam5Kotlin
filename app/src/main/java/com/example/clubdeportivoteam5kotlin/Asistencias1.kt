package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Asistencias1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asistencias1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.asistencias1)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itemProfesor = findViewById<MenuItem>(R.id.asistProfesor)
        itemProfesor.setOnClickListener {
            val intent = Intent (this, Asistencias2::class.java)
            startActivity(intent)
        }
        val itemAlumno = findViewById<MenuItem>(R.id.asistAlumno)
        itemAlumno.setOnClickListener {
            val intent = Intent (this, Asistencias2::class.java)
            startActivity(intent)
        }
        val btnRegresar = findViewById<Button>(R.id.btnregasist)
        btnRegresar.setOnClickListener {
            val intent = Intent (this, MenuPrincipal::class.java)
            startActivity(intent)
        }
    }
}
