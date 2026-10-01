package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Persona2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona2)

        val spnTipo = findViewById<Spinner>(R.id.spnTipo)
        val spnActividad = findViewById<Spinner>(R.id.spnActividad)
        val spnHorario = findViewById<Spinner>(R.id.spnHorario)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        // Membresía simulada con valores fijos declarados; solo `tipo` entra al modelo
        spnTipo.adapter = spinner("Socio", "No Socio")
        spnActividad.adapter = spinner("Musculación", "Natación", "Fútbol", "Tenis")
        spnHorario.adapter = spinner("Mañana", "Tarde", "Noche")

        btnRegistrar.setOnClickListener {
            val socio = Socio(
                dni = intent.getStringExtra(Persona1.EXTRA_DNI).orEmpty(),
                nombre = intent.getStringExtra(Persona1.EXTRA_NOMBRE).orEmpty(),
                apellido = intent.getStringExtra(Persona1.EXTRA_APELLIDO).orEmpty(),
                correo = intent.getStringExtra(Persona1.EXTRA_CORREO).orEmpty(),
                tipo = spnTipo.selectedItem.toString(),
                aptoFisico = true,
                // El alta no incluye cobro: la cuota vence desde el día de hoy.
                // El filtro de vencimientos (c7) define si hoy ya cuenta como vencido.
                cuotaAlDia = false,
                fechaVencimiento = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
            )

            if (SocioRepository.agregar(socio)) {
                startActivity(
                    Intent(this, AvisoExito::class.java).putExtra("tituloExito", "Registro Exitoso!")
                )
                finish()
            } else {
                Toast.makeText(this, "Ya existe una persona con ese DNI", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun spinner(vararg valores: String) =
        ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, valores.toList())
}
