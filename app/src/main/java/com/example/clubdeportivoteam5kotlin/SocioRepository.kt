package com.example.clubdeportivoteam5kotlin

data class Socio(
    val dni: String,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val tipo: String,
    val aptoFisico: Boolean,
    val cuotaAlDia: Boolean,
    val fechaVencimiento: String
)

// Repositorio en memoria: la entrega-01 no usa base de datos, se pierde al cerrar la app
object SocioRepository {

    val listaSocios = mutableListOf(
        Socio("30111222", "Ana", "Gomez", "ana.gomez@club.com", "Socio", true, true, "2026-10-31"),
        Socio("32456789", "Luis", "Sosa", "luis.sosa@club.com", "No Socio", true, false, "2026-09-20"),
        Socio("28998877", "Marta", "Rios", "marta.rios@club.com", "Socio", true, true, "2026-11-30")
    )

    fun buscarPorDni(dni: String): Socio? = listaSocios.find { it.dni == dni }

    fun agregar(socio: Socio): Boolean {
        if (buscarPorDni(socio.dni) != null) return false
        listaSocios.add(socio)
        return true
    }
}
