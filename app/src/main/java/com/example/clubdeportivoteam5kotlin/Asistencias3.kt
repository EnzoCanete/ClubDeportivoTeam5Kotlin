package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Asistencias3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asistencias3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.asistencias3)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dni = intent.getStringExtra(Asistencias2.EXTRA_DNI).orEmpty()
        val tipo = intent.getStringExtra(Asistencias2.EXTRA_TIPO).orEmpty()
        val socio = SocioRepository.buscarPorDni(dni)
        findViewById<TextView>(R.id.txtNmbasist).text = socio?.let { "${it.nombre} ${it.apellido}" }.orEmpty()
        findViewById<TextView>(R.id.txtDniasist).text = dni
        findViewById<TextView>(R.id.txtTipoasist).text = tipo

        val btnConfasistencia = findViewById<Button>(R.id.btnConfasist)
        btnConfasistencia.setOnClickListener {
            Toast.makeText(this, "Asistencia confirmada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MenuPrincipal::class.java))
        }

        val btnRegresar3 = findViewById<Button>(R.id.btnregasist3)
        btnRegresar3.setOnClickListener {
            val intent = Intent (this, MenuPrincipal::class.java)
            startActivity(intent)
        }
    }
}