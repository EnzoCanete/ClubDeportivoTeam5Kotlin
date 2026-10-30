package com.example.clubdeportivoteam5kotlin

object Validaciones {

    fun esTextoValido(texto: String?): Boolean {
        return !texto.isNullOrBlank()
    }

    fun esDniValido(dni: String?): Boolean {
        if (dni.isNullOrBlank()) return false
        val regex = Regex("^\\d{7,8}$")
        return regex.matches(dni.trim())
    }

    fun esCorreoValido(correo: String?): Boolean {
        if (correo.isNullOrBlank()) return false
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return regex.matches(correo.trim())
    }

    fun esMontoValido(montoStr: String?): Boolean {
        val monto = montoStr?.toDoubleOrNull()
        return monto != null && monto > 0.0
    }
}