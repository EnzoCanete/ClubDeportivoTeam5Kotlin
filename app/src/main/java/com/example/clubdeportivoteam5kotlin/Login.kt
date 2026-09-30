package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Credenciales simuladas declaradas en entrega-01, sin sesión ni roles
        findViewById<Button>(R.id.btnIngresar).setOnClickListener {
            val usuario = findViewById<InputConLabel>(R.id.inputUsuario).text
            val clave = findViewById<InputConLabel>(R.id.inputClave).text
            when {
                usuario.isBlank() || clave.isBlank() ->
                    Toast.makeText(this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
                usuario == "admin" && clave == "1234" ->
                    startActivity(Intent(this, MenuPrincipal::class.java))
                else ->
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}