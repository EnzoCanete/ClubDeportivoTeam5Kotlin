package com.example.clubdeportivoteam5kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Carnet1 : AppCompatActivity() {

    private var emitidoDni: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carnet1)

        val dniInput: InputConLabel = findViewById(R.id.DniCarnet)
        val lblNombre: TextView = findViewById(R.id.lblNombreSocio)
        val lblDni: TextView = findViewById(R.id.lblDniSocio)
        val lblNum: TextView = findViewById(R.id.lblNumSocio)
        val lblVenc: TextView = findViewById(R.id.lblVencimientoSocio)
        val btnImprimirCarnet = findViewById<Button>(R.id.btnImprimirCarnet)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        // Emite solo si el DNI existe y la cuota está al día (R09)
        fun emitir(): Boolean {
            val dni = dniInput.text.trim()
            if (dni.isEmpty()) {
                Toast.makeText(this, "Ingresá un DNI", Toast.LENGTH_SHORT).show()
                return false
            }
            val socio = SocioRepository.buscarPorDni(dni)
            if (socio == null) {
                emitidoDni = null
                Toast.makeText(this, "No hay socio activo con ese DNI", Toast.LENGTH_SHORT).show()
                return false
            }
            if (!socio.cuotaAlDia) {
                emitidoDni = null
                Toast.makeText(this, "Cuota vencida: debe renovar", Toast.LENGTH_SHORT).show()
                return false
            }
            lblNombre.text = "${socio.nombre} ${socio.apellido}"
            lblDni.text = socio.dni
            lblNum.text = "SOC-${socio.dni}"
            lblVenc.text = socio.fechaVencimiento
            emitidoDni = socio.dni
            Toast.makeText(this, "Carnet emitido", Toast.LENGTH_SHORT).show()
            return true
        }

        findViewById<Button>(R.id.btnBuscarCarnet).setOnClickListener { emitir() }

        // Accion al presionar Imprimir / Guardar
        btnImprimirCarnet.setOnClickListener {
            if (emitidoDni != null && emitidoDni == dniInput.text.trim()) {
                Toast.makeText(this, "Mostrá el QR en puerta", Toast.LENGTH_SHORT).show()
            } else if (emitir()) {
                Toast.makeText(this, "Mostrá el QR en puerta", Toast.LENGTH_SHORT).show()
            }
        }

        // Accion para regresar a la pantalla anterior
        btnRegresar.setOnClickListener {
            finish()
        }
    }
}