package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
            val nombre = campoNombre.text.trim()
            val apellido = campoApellido.text.trim()
            val dni = campoDNI.text.trim()
            val correo = campoCorreo.text.trim()

            when {
                nombre.isBlank() || apellido.isBlank() || dni.isBlank() || correo.isBlank() ->
                    Toast.makeText(this, "Complete nombre, apellido, DNI y correo", Toast.LENGTH_SHORT).show()
                // Apto obligatorio simple (sin foto ni fecha, fuera de alcance de HU-01)
                campoAptoFisico.text.isBlank() ->
                    Toast.makeText(this, "El apto físico es obligatorio", Toast.LENGTH_SHORT).show()
                SocioRepository.buscarPorDni(dni) != null ->
                    Toast.makeText(this, "Ya existe una persona con ese DNI", Toast.LENGTH_SHORT).show()
                else -> startActivity(
                    Intent(this, Persona2::class.java)
                        .putExtra(EXTRA_NOMBRE, nombre)
                        .putExtra(EXTRA_APELLIDO, apellido)
                        .putExtra(EXTRA_DNI, dni)
                        .putExtra(EXTRA_CORREO, correo)
                )
            }
        }
    }

    companion object {
        const val EXTRA_NOMBRE = "nombre"
        const val EXTRA_APELLIDO = "apellido"
        const val EXTRA_DNI = "dni"
        const val EXTRA_CORREO = "correo"
    }
}
