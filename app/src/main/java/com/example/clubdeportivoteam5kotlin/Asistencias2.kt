package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Asistencias2 : AppCompatActivity() {
    companion object {
        const val EXTRA_DNI = "EXTRA_DNI"
        const val EXTRA_TIPO = "EXTRA_TIPO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asistencias2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.asistencias2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tipo = intent.getStringExtra(EXTRA_TIPO).orEmpty()
        val dniInput = findViewById<EditText>(R.id.asistDni)
        val btnBuscarasist = findViewById<Button>(R.id.btnBuscarasist)
        btnBuscarasist.setOnClickListener {
            val dni = dniInput.text.toString().trim()
            when {
                dni.isEmpty() -> Toast.makeText(this, "Ingresá un DNI", Toast.LENGTH_SHORT).show()
                !dni.matches(Regex("^\\d{7,8}$")) -> Toast.makeText(this, "Formato de DNI inválido", Toast.LENGTH_SHORT).show()
                SocioRepository.buscarPorDni(dni) == null -> Toast.makeText(this, "No hay registro con ese DNI", Toast.LENGTH_SHORT).show()
                else -> startActivity(Intent(this, Asistencias3::class.java)
                    .putExtra(EXTRA_DNI, dni)
                    .putExtra(EXTRA_TIPO, tipo))
            }
        }

        val btnRegresar2 = findViewById<Button>(R.id.btnregasist2)
        btnRegresar2.setOnClickListener {
            finish()
        }
    }
}