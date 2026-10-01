# Club Deportivo — DAM (Team 5 Kotlin)

App Android para empleados del club (no autogestión de socios).

- Stack: Kotlin + Views + Intents, 1 módulo `app`, package `com.example.clubdeportivoteam5kotlin`.
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

## Guía de prueba

Qué probar en cada pantalla y qué responde la app. Datos: tabla de arriba (`30111222` al día, `32456789` vencido, `28998877` al día, `99999999` inexistente). El estado es en memoria: se pierde al cerrar la app.

### Acceso (`Login`)

| Input | Respuesta esperada |
|--------|-------------------|
| `admin` / `1234` | Abre `MenuPrincipal` |
| Vacío alguno | Toast "Ingrese usuario y contraseña", no navega |
| Otro usuario/clave | Toast "Credenciales incorrectas", queda en login |

### Alta de persona (`Persona1` → `Persona2`)

| Input | Respuesta esperada |
|--------|-------------------|
| Faltan campos | Toast "Complete nombre, apellido, DNI y correo" |
| Sin apto físico | Toast "El apto físico es obligatorio" |
| DNI ya registrado (ej `30111222`) | Toast "Ya existe una persona con ese DNI" |
| Datos completos + apto + DNI nuevo | Registra y llega a éxito |

### Cobro de cuota (`Pagos1` → efectivo / tarjeta / QR)

| Input | Respuesta esperada |
|--------|-------------------|
| DNI vacío | Toast "Ingresá un DNI", no deriva |
| DNI inexistente (ej `99999999`) | Toast "No hay socio activo con ese DNI" |
| Sin medio elegido | Toast "Seleccioná un medio de pago" |
| Efectivo con monto > 0 | Marca cuota al día + pantalla de éxito |
| Efectivo con monto 0 | Toast "Ingresá un monto mayor a 0" |
| Tarjeta con menos de 16 dígitos | Toast "Tarjeta inválida: 16 dígitos" |
| Crédito con CVC ≠ 3 dígitos | Toast "CVC inválido: 3 dígitos" |
| Crédito en cuotas ≠ 1/3/6 | Toast "Cuotas válidas: 1, 3 o 6" |
| QR: monto > 0 y confirma la muestra | Marca cuota al día + éxito (sin cámara) |

### Actividad del día (`Pagos1`, opción actividad)

| Input | Respuesta esperada |
|--------|-------------------|
| "Cuota mensual" (sin actividad) | Cobra como cuota normal, sin detalle |
| Musculación sin apto | Toast "Musculación requiere apto físico", no cobra |
| Actividad válida (apto si corresponde) | Deriva al cobro con el detalle y llega a éxito |

### Carnet (`Carnet1`)

| Input | Respuesta esperada |
|--------|-------------------|
| `30111222` (al día) | Emite carnet con QR: Toast "Carnet emitido" |
| `32456789` (vencido) | Toast "Cuota vencida: debe renovar", no emite |
| `99999999` | Toast "No hay socio activo con ese DNI", no emite |

### Vencimientos

| Input | Respuesta esperada |
|--------|-------------------|
| Hay impagos (ej `32456789` inicial) | Lista ordenados por vencimiento |
| Todos al día | Toast "No hay cuotas vencidas" |
| Tocar una fila | Abre el cobro con el DNI precargado; al cobrar sale del listado |

### Asistencias (flujo futuro, simulado)

| Input | Respuesta esperada |
|--------|-------------------|
| DNI vacío | Toast "Ingresá un DNI", no deriva |
| Formato inválido (ej `123`) | Toast "Formato de DNI inválido", no deriva |
| DNI inexistente (ej `99999999`) | Toast "No hay registro con ese DNI", no deriva |
| DNI válido | Muestra nombre, DNI y tipo reales (Alumno/Profesor) |
| Confirmar asistencia | Toast "Asistencia confirmada" y vuelve a `MenuPrincipal` (sin guardar nada) |
