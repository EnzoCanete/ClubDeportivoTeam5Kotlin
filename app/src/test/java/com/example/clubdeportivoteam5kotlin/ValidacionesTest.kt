package com.example.clubdeportivoteam5kotlin

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidacionesTest {

    @Test
    fun dniValidoSoloAcepta7U8Digitos() {
        assertTrue(Validaciones.esDniValido("1234567"))
        assertTrue(Validaciones.esDniValido("12345678"))
        assertFalse(Validaciones.esDniValido("123456"))
        assertFalse(Validaciones.esDniValido("12a4567"))
        assertFalse(Validaciones.esDniValido(null))
    }

    @Test
    fun correoValidoRechazaFormatosIncompletos() {
        assertTrue(Validaciones.esCorreoValido("ana.gomez@club.com"))
        assertFalse(Validaciones.esCorreoValido("ana"))
        assertFalse(Validaciones.esCorreoValido("ana@club"))
        assertFalse(Validaciones.esCorreoValido(null))
    }

    @Test
    fun nombreValidoRechazaDigitos() {
        assertTrue(Validaciones.esNombreValido("Ana"))
        assertTrue(Validaciones.esNombreValido("Gómez"))
        assertFalse(Validaciones.esNombreValido("Ana2"))
        assertFalse(Validaciones.esNombreValido("  "))
        assertFalse(Validaciones.esNombreValido(null))
    }
}
