package com.example.clubdeportivoteam5kotlin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class Vencimientos : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vencimientos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vencimientos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rv = findViewById(R.id.rvVencimientos)

        rv.addItemDecoration(
            SeparadorTabla(16.dp())
        )

        // Fuera de HU-05: ocultar "Enviar recordatorio"
        findViewById<Button>(R.id.btnRegistrar).visibility = View.GONE
        cargar()

    }

    override fun onResume() {
        super.onResume()
        cargar()
    }

    private fun cargar() {
        // listarSocios() ya excluye No Socios y ordena por fecha de vencimiento.
        val socios = SocioRepository.listarSocios()
        val mensaje = when {
            socios.isEmpty() -> "No hay socios para mostrar"
            socios.all { it.cuotaAlDia } -> "No hay cuotas vencidas"
            else -> null
        }
        mensaje?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        rv.adapter = VtoAdapter(socios) { dni ->
            startActivity(Intent(this, Pagos1::class.java).putExtra(Pagos1.EXTRA_DNI, dni))
        }
    }

    private class VtoAdapter(
        private val items: List<Socio>,
        private val onClick: (String) -> Unit
    ) : RecyclerView.Adapter<VtoAdapter.Vh>() {

        class Vh(v: View) : RecyclerView.ViewHolder(v) {
            val dni: TextView = v.findViewById(R.id.vtoDni)
            val nombre: TextView = v.findViewById(R.id.vtoNombre)
            val fecha: TextView = v.findViewById(R.id.vtoFecha)
        }

        override fun onCreateViewHolder(p: ViewGroup, t: Int): Vh =
            Vh(LayoutInflater.from(p.context).inflate(R.layout.tabla_vencimientos, p, false))

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(h: Vh, pos: Int) {
            val s = items[pos]
            h.dni.text = s.dni
            h.nombre.text = "${s.nombre} ${s.apellido}"
            h.fecha.text = "${s.fechaVencimiento} · ${if (s.cuotaAlDia) "Por vencer" else "Vencida"}"
            h.itemView.setOnClickListener { onClick(s.dni) }
        }

    }


    private fun Int.dp(): Int {

        return (this * resources.displayMetrics.density).toInt()
    }
}
