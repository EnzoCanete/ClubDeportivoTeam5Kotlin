package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AvisoExito : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exitoso)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Exitoso)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val titulo = intent.getStringExtra("tituloExito")

        findViewById<TextView>(R.id.tituloExito).text = titulo

        // Para cambiar el texto de tituloExito se utiliza algo como esto:
        // val intent = Intent(this, AvisoExito::class.java).putExtra("tituloExito", "Registro Exitoso!")


        val btnMenuprincipal = findViewById<Button>(R.id.btnMenuprincipal)

        btnMenuprincipal.setOnClickListener {
            val intent = Intent(this, MenuPrincipal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}