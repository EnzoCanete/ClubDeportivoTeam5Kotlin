package com.example.clubdeportivoteam5kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Consulta1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta1)

        val dniInput: InputConLabel = findViewById(R.id.DniConsulta)
        val lblNombre: TextView = findViewById(R.id.lblNombreConsulta)
        val lblTipo: TextView = findViewById(R.id.lblTipoConsulta)
        val lblCorreo: TextView = findViewById(R.id.lblCorreoConsulta)
        val lblApto: TextView = findViewById(R.id.lblAptoConsulta)
        val lblVenc: TextView = findViewById(R.id.lblVencimientoConsulta)
        val lblHistorial: TextView = findViewById(R.id.lblHistorialConsulta)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        findViewById<Button>(R.id.btnBuscarConsulta).setOnClickListener {
            val dni = dniInput.text.trim()
            if (dni.isEmpty()) {
                Toast.makeText(this, "Ingresá un DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val persona = SocioRepository.buscarPorDni(dni)
            if (persona == null) {
                lblNombre.text = "--"
                lblTipo.text = "--"
                lblCorreo.text = "--"
                lblApto.text = "--"
                lblVenc.text = "--"
                lblHistorial.text = "--"
                Toast.makeText(this, "No hay ninguna persona registrada con ese DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lblNombre.text = "${persona.nombre} ${persona.apellido}"
            lblTipo.text = persona.tipo
            lblCorreo.text = persona.correo
            lblApto.text = if (persona.aptoFisico) "Sí" else "No"
            lblVenc.text = if (persona.tipo == "Socio") {
                "${persona.fechaVencimiento} · ${if (persona.cuotaAlDia) "Al día" else "Vencida"}"
            } else {
                "No aplica (paga por día)"
            }

            val pagos = SocioRepository.pagosDe(dni)
            lblHistorial.text = if (pagos.isEmpty()) {
                "Sin pagos registrados"
            } else {
                pagos.joinToString("\n\n") { p ->
                    val concepto = if (p.tipo == "cuota") "Cuota mensual" else p.actividad ?: "Pago diario"
                    "${p.fecha} · $concepto\n${Pagos1.formatearMonto(p.monto)} · ${p.medio}"
                }
            }
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }
}