package com.example.clubdeportivoteam5kotlin

import org.junit.Test

import org.junit.Assert.*

class SocioRepositoryTest {

    @Test
    fun seedEsRecuperablePorDni() {
        assertNotNull(SocioRepository.buscarPorDni("30111222"))
        assertNull(SocioRepository.buscarPorDni("99999999"))
    }

    @Test
    fun agregarRechazaDuplicado() {
        val antes = SocioRepository.listaSocios.size
        assertFalse(SocioRepository.agregar(SocioRepository.buscarPorDni("30111222")!!))
        assertEquals(antes, SocioRepository.listaSocios.size)
    }

    @Test
    fun agregarNuevoQuedaBusquable() {
        val nuevo = Socio("35000111", "Nico", "Prueba", "nico@club.com", "Socio", true, false, "2026-09-30")
        assertTrue(SocioRepository.agregar(nuevo))
        assertEquals(nuevo, SocioRepository.buscarPorDni("35000111"))
        SocioRepository.listaSocios.remove(nuevo)
    }

    @Test
    fun fechaVencimientoUsaFormatoYyyyMMdd() {
        val formato = Regex("""\d{4}-\d{2}-\d{2}""")
        assertTrue(SocioRepository.listaSocios.all { formato.matches(it.fechaVencimiento) })
    }
}
