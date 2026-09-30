# Club Deportivo — DAM (Team 5 Kotlin)

App Android para empleados del club (no autogestión de socios).

- Stack: Kotlin + Views + Intents, 1 módulo `app`, package `com.example.clubdeportivoteam5kotlin`.
- Código real: `03-codigo/club-deportivo-enzo`, rama `master`.
- Estado en memoria, sin DB (`SocioRepository` en memoria).

## Cómo correr

1. Abrir `03-codigo/club-deportivo-enzo` en Android Studio.
2. Sync Gradle.
3. Run en emulador/dispositivo.



## Datos de prueba

Login: `admin` / `1234`.

| DNI | Nombre | Tipo | Cuota | Vencimiento |
|-----|--------|------|-------|-------------|
| `30111222` | Ana Gomez | Socio | al día | 2026-10-31 |
| `32456789` | Luis Sosa | No Socio | VENCIDA | 2026-09-20 |
| `28998877` | Marta Rios | Socio | al día | 2026-11-30 |

DNI inexistente: cualquier otro (ej `99999999`).

Fuente seeds: `SocioRepository` (`app/src/main/java/.../SocioRepository.kt`).

## Precios actividades (R13)

| Actividad | Precio |
|-----------|--------|
| Musculación | 10000 |
| Nutrición | 20000 |
| Tenis | 15000 |
| Spinning / Yoga / Natación | 10000 |

- Musculación exige apto físico (R04).
- Carnet solo con cuota al día (R09).

## Flujos

- `MenuPrincipal` → `RegistrarPersonas` (`Persona1`/`Persona2`): alta socio/no socio.
- `CargarPago` (`Pagos1` → efectivo / tarjeta / QR): cobro cuota o actividad diaria.
- `CrearCarnet` (`Carnet1`): emite carnet si cuota al día.
- Vencimientos: listado diario de socios con cuota vencida.
