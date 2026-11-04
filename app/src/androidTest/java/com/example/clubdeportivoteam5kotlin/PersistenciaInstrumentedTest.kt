package com.example.clubdeportivoteam5kotlin

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class PersistenciaInstrumentedTest {

    private lateinit var helper: ClubDbHelper

    @Before
    fun setUp() {
        val ctx = ApplicationProvider.getApplicationContext<android.content.Context>()
        ctx.deleteDatabase(ClubDbHelper.DB_NAME)
        helper = ClubDbHelper(ctx)
        SocioRepository.init(ctx)
    }

    @After
    fun tearDown() {
        helper.close()
    }

    @Test
    fun seedVienePrecargado() {
        assertNotNull(SocioRepository.buscarPorDni("30111222"))
        assertNotNull(SocioRepository.buscarPorDni("28998877"))
        assertNotNull(SocioRepository.buscarPorDni("34123456"))
        assertNotNull(SocioRepository.buscarPorDni("32456789"))
        assertNull(SocioRepository.buscarPorDni("99999999"))
    }

    @Test
    fun altaQuedaPersistidaTrasReabrir() {
        val nuevo = Socio("35000111", "Nico", "Prueba", "nico@club.com", "Socio", true, false, "2026-12-31")
        assertTrue(SocioRepository.agregar(nuevo))

        helper.close()
        val ctx = ApplicationProvider.getApplicationContext<android.content.Context>()
        helper = ClubDbHelper(ctx)
        SocioRepository.init(ctx)

        val recuperado = SocioRepository.buscarPorDni("35000111")
        assertNotNull(recuperado)
        assertEquals("Nico", recuperado!!.nombre)
        assertEquals("Prueba", recuperado.apellido)
    }

    @Test
    fun cuotaExtiendeVencimiento30DiasYPagoQuedaPersistido() {
        val antes = SocioRepository.buscarPorDni("30111222")!!.fechaVencimiento!!
        assertTrue(SocioRepository.registrarPago("30111222", "cuota", null, 50000.0, "efectivo"))
        val socio = SocioRepository.buscarPorDni("30111222")!!
        assertEquals(sumar30Dias(antes), socio.fechaVencimiento)
        assertTrue(socio.cuotaAlDia)
        assertEquals(1, SocioRepository.pagosDe("30111222").size)
    }

    private fun sumar30Dias(fecha: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val cal = Calendar.getInstance().apply {
            time = sdf.parse(fecha)!!
            add(Calendar.DAY_OF_MONTH, 30)
        }
        return sdf.format(cal.time)
    }

    @Test
    fun diariaMantieneVencimientoNulo() {
        assertTrue(SocioRepository.registrarPago("32456789", "diaria", "Musculación", 10000.0, "efectivo"))
        val socio = SocioRepository.buscarPorDni("32456789")!!
        assertNull(socio.fechaVencimiento)
        assertEquals(1, SocioRepository.pagosDe("32456789").size)
    }

    @Test
    fun altaAsignaVencimientoSoloASocioYPersisteTrasReabrir() {
        val hoy = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date())
        assertTrue(SocioRepository.agregar(Socio("36000222", "Sol", "Socio", "sol@club.com", "Socio", true, false, null)))
        assertTrue(SocioRepository.agregar(Socio("36000333", "Nora", "NoSocio", "nora@club.com", "No Socio", true, false, "2026-12-31")))

        helper.close()
        val ctx = ApplicationProvider.getApplicationContext<android.content.Context>()
        helper = ClubDbHelper(ctx)
        SocioRepository.init(ctx)

        val socio = SocioRepository.buscarPorDni("36000222")!!
        assertEquals("Socio", socio.tipo)
        assertEquals(hoy, socio.fechaVencimiento)
        assertTrue(socio.cuotaAlDia)

        val noSocio = SocioRepository.buscarPorDni("36000333")!!
        assertEquals("No Socio", noSocio.tipo)
        assertNull("No Socio nunca tiene vencimiento", noSocio.fechaVencimiento)
        assertFalse(noSocio.cuotaAlDia)
    }

    @Test
    fun cuotaAlDiaSoloParaSocioConVencimientoVigente() {
        assertTrue(SocioRepository.buscarPorDni("30111222")!!.cuotaAlDia)
        assertFalse(SocioRepository.buscarPorDni("34123456")!!.cuotaAlDia)
        assertFalse(SocioRepository.buscarPorDni("32456789")!!.cuotaAlDia)
    }

    @Test
    fun listarSociosExcluyeNoSociosYOrdenaPorVencimiento() {
        val socios = SocioRepository.listarSocios()
        assertEquals(3, socios.size)
        assertTrue(socios.all { it.tipo == "Socio" })
        val fechas = socios.map { it.fechaVencimiento!! }
        assertEquals(fechas, fechas.sorted())
        assertNull(socios.firstOrNull { it.dni == "32456789" })
    }

    @Test
    fun pagoSobreviveTrasCerrarYReabrir() {
        SocioRepository.registrarPago("34123456", "cuota", null, 50000.0, "debito")
        assertEquals(1, SocioRepository.pagosDe("34123456").size)

        helper.close()
        val ctx = ApplicationProvider.getApplicationContext<android.content.Context>()
        helper = ClubDbHelper(ctx)
        SocioRepository.init(ctx)

        val pagos = SocioRepository.pagosDe("34123456")
        assertEquals(1, pagos.size)
        assertEquals("cuota", pagos[0].tipo)
        assertEquals("debito", pagos[0].medio)
    }
}
