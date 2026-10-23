package com.example.clubdeportivoteam5kotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Socio(
    val dni: String,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val tipo: String,
    val aptoFisico: Boolean,
    val cuotaAlDia: Boolean,
    val fechaVencimiento: String?
)

data class Pago(
    val id: Long,
    val dni: String,
    val tipo: String,
    val actividad: String?,
    val monto: Double,
    val medio: String,
    val fecha: String
)

object SocioRepository {

    private lateinit var helper: ClubDbHelper

    fun init(context: Context) {
        helper = ClubDbHelper(context.applicationContext)
    }

    private fun hoy(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

    fun buscarPorDni(dni: String): Socio? {
        helper.readableDatabase.query(
            "personas", null, "dni = ?", arrayOf(dni), null, null, null
        ).use { c ->
            return if (c.moveToFirst()) c.aSocio() else null
        }
    }

    fun listarSocios(): List<Socio> {
        val lista = mutableListOf<Socio>()
        helper.readableDatabase.query(
            "personas", null, "tipo = ?", arrayOf("Socio"), null, null, "fecha_vencimiento"
        ).use { c ->
            while (c.moveToNext()) lista.add(c.aSocio())
        }
        return lista
    }

    fun agregar(socio: Socio): Boolean {
        // Socio arranca con vencimiento hoy (o el indicado); No Socio nunca tiene vencimiento.
        val vencimiento = if (socio.tipo == "Socio") socio.fechaVencimiento ?: hoy() else null
        val cv = ContentValues().apply {
            put("dni", socio.dni)
            put("nombre", socio.nombre)
            put("apellido", socio.apellido)
            put("correo", socio.correo)
            put("tipo", socio.tipo)
            put("apto", if (socio.aptoFisico) 1 else 0)
            put("fecha_vencimiento", vencimiento)
        }
        return helper.writableDatabase.insert("personas", null, cv) != -1L
    }

    fun registrarPago(dni: String, tipo: String, actividad: String?, monto: Double, medio: String): Boolean {
        val db = helper.writableDatabase
        val fecha = hoy()
        db.beginTransaction()
        try {
            val pago = ContentValues().apply {
                put("dni", dni)
                put("tipo", tipo)
                put("actividad", actividad)
                put("monto", monto)
                put("medio", medio)
                put("fecha", fecha)
            }
            val id = db.insert("pagos", null, pago)
            if (id == -1L) { db.endTransaction(); return false }

            if (tipo == "cuota") {
                val cv = ContentValues().apply {
                    put("fecha_vencimiento", sumar30Dias(fecha))
                }
                db.update("personas", cv, "dni = ?", arrayOf(dni))
            }
            db.setTransactionSuccessful()
            return true
        } finally {
            db.endTransaction()
        }
    }

    fun pagosDe(dni: String): List<Pago> {
        val lista = mutableListOf<Pago>()
        helper.readableDatabase.query(
            "pagos", null, "dni = ?", arrayOf(dni), null, null, "id DESC"
        ).use { c ->
            while (c.moveToNext()) lista.add(c.aPago())
        }
        return lista
    }

    private fun Cursor.aSocio(): Socio {
        val vencimiento = getString(getColumnIndexOrThrow("fecha_vencimiento"))
        val tipoSocio = getString(getColumnIndexOrThrow("tipo"))
        val esSocio = tipoSocio == "Socio"
        val hoy = hoy()
        val cuotaAlDia = esSocio && vencimiento != null && vencimiento >= hoy
        return Socio(
            dni = getString(getColumnIndexOrThrow("dni")),
            nombre = getString(getColumnIndexOrThrow("nombre")),
            apellido = getString(getColumnIndexOrThrow("apellido")),
            correo = getString(getColumnIndexOrThrow("correo")),
            tipo = tipoSocio,
            aptoFisico = getInt(getColumnIndexOrThrow("apto")) == 1,
            cuotaAlDia = cuotaAlDia,
            fechaVencimiento = vencimiento
        )
    }

    private fun Cursor.aPago(): Pago = Pago(
        id = getLong(getColumnIndexOrThrow("id")),
        dni = getString(getColumnIndexOrThrow("dni")),
        tipo = getString(getColumnIndexOrThrow("tipo")),
        actividad = getString(getColumnIndexOrThrow("actividad")),
        monto = getDouble(getColumnIndexOrThrow("monto")),
        medio = getString(getColumnIndexOrThrow("medio")),
        fecha = getString(getColumnIndexOrThrow("fecha"))
    )

    private fun sumar30Dias(fecha: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val cal = java.util.Calendar.getInstance().apply {
            time = sdf.parse(fecha)!!
            add(java.util.Calendar.DAY_OF_MONTH, 30)
        }
        return sdf.format(cal.time)
    }
}
