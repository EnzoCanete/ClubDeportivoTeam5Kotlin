package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menuPrincipal)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val menuCrearCarnet = findViewById<MenuItem>(R.id.menuCrearCarnet)
        val menuListadoVencidos = findViewById<MenuItem>(R.id.menuListadoVencidos)
        val menuRegistrarAsistencia = findViewById<MenuItem>(R.id.menuRegistrarAsistencia)
        val menuRegistrarPersona = findViewById<MenuItem>(R.id.menuRegistrarPersonas)
        val menuCargarPago = findViewById<MenuItem>(R.id.menuCargarPago)

        val botonNotificaciones = findViewById<BotonIcono>(R.id.botonNotificaciones)

        botonNotificaciones.onBotonClick = {
            startActivity(
                Intent(this, Vencimientos::class.java)
            )
        }


        menuCrearCarnet.setOnMenuClickListener {
            startActivity(
                Intent(this, Carnet1::class.java)
            )
        }

        menuListadoVencidos.setOnMenuClickListener {
            startActivity(
                Intent(this, Vencimientos::class.java)
            )
        }

        menuRegistrarAsistencia.setOnMenuClickListener {
            startActivity(
                Intent(this, Asistencias1::class.java)
            )
        }

        menuRegistrarPersona.setOnMenuClickListener {
            startActivity(
                Intent(this, Persona1::class.java)
            )
        }

        menuCargarPago.setOnMenuClickListener {
            startActivity(
                Intent(this, Pagos1::class.java)
            )
        }
    }
}