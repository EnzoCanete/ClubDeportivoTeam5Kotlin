# Club Deportivo — DAM (Team 5 Kotlin)

## Grupo N° 5

- Cañete, Enzo
- Hotasegui, Juan Martín
- Sánchez, Karina
- Valdez, Emmanuel
- Vallejo, Oscar Nilton

App Android para empleados del club (no autogestión de socios).

- Stack: Kotlin + Views + Intents, 1 módulo `app`, package `com.example.clubdeportivoteam5kotlin`.
- Persistencia: SQLite nativo (`ClubDbHelper`) a través de `SocioRepository`; personas y pagos sobreviven al cierre de la app.
- DNI: 7 u 8 dígitos numéricos (válido para DNIs existentes de 7 y seeds de 8).

## Cómo correr

1. Abrir `03-codigo/club-deportivo-enzo` en Android Studio.
2. Sync Gradle.
3. Run en emulador/dispositivo.

## Datos de prueba

Login: `admin` / `1234`.

El seed se crea en `ClubDbHelper.onCreate` con fechas relativas al primer arranque:

| DNI | Nombre | Tipo | Cuota | Vencimiento |
|-----|--------|------|-------|-------------|
| `30111222` | Ana Gomez | Socio | al día | hoy + 7 días |
| `28998877` | Marta Rios | Socio | al día | hoy + 40 días |
| `34123456` | Pedro Diaz | Socio | VENCIDA | hoy − 20 días |
| `32456789` | Luis Sosa | No Socio | sin vencimiento | — |
| `45897070` | Enrique Rodriguez | Profesor | no aplica | — |

- Socios: `30111222`, `28998877` (al día) y `34123456` (vencido).
- No Socios: `32456789` (sin vencimiento; no aparece en vencimientos ni carnet).
- Profesor: `45897070` (solo para el flujo simulado de asistencias).
- DNI inexistente: cualquier otro (ej. `99999999`).

El seed solo corre cuando se crea la base. Si ya tenés la app instalada, borrá los datos de la app o desinstalá para recrearlo; una instalación existente conserva sus personas y pagos.

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

- `MenuPrincipal` → `Persona1`/`Persona2`: alta de socio/no socio.
- `Pagos1` → efectivo / tarjeta / QR: cobro de cuota (Socio) o actividad diaria (No Socio).
- `Carnet1`: emite carnet si el Socio tiene cuota al día.
- `Vencimientos`: listado de todos los Socios ordenados por fecha, con estado `Vencida` o `Por vencer`. No incluye No Socios.
- `Consulta1`: búsqueda por DNI con datos de la persona e historial de pagos persistidos.
- `Asistencias1/2/3`: flujo simulado, sin persistencia.

## Guía de prueba

Persistencia: cerrar y reabrir la app conserva altas y pagos. El seed se crea solo al instalar o limpiar los datos.

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
| DNI con 6 dígitos o letras (ej `123456`, `12a45b`) | Toast "DNI inválido: 7 u 8 dígitos numéricos", no navega |
| Nombre o apellido con dígitos (ej `Ana2`) | Toast "Nombre y apellido no pueden contener números" |
| Correo inválido (ej `ana`) | Toast "Correo inválido" |
| Sin apto físico | Toast "El apto físico es obligatorio" |
| DNI ya registrado (ej `30111222`) | Toast "Ya existe una persona con ese DNI" |
| DNI de 7 dígitos válido | Pasa (compatibilidad con DNIs existentes) |
| DNI de 8 dígitos válido + datos completos + apto | Registra y llega a éxito |

### Cobro de cuota (`Pagos1` → efectivo / tarjeta / QR)

| Input | Respuesta esperada |
|--------|-------------------|
| DNI vacío | Toast "Ingresá un DNI", no deriva |
| DNI con formato inválido | Toast "DNI inválido: 7 u 8 dígitos numéricos" |
| DNI inexistente (ej `99999999`) | Toast "No hay ninguna persona registrada con ese DNI" |
| Sin medio elegido | Toast "Seleccioná un medio de pago" |
| Socio + Cuota mensual + efectivo | Persiste la cuota, extiende el vencimiento 30 días y muestra éxito |
| No Socio + actividad + efectivo | Persiste el pago diario, mantiene el vencimiento nulo y muestra éxito |
| No Socio + Cuota mensual | Toast "Los no socios no pagan cuota mensual: elegí una actividad" |
| Socio + actividad | Toast "Los socios solo pagan la cuota mensual" |
| Tarjeta con menos de 16 dígitos | Toast "Tarjeta inválida: 16 dígitos" |
| Crédito con CVC ≠ 3 dígitos | Toast "CVC inválido: 3 dígitos" |
| Crédito en cuotas ≠ 1/3/6 | Toast "Cuotas válidas: 1, 3 o 6" |
| QR: confirma la muestra | Persiste el pago y muestra éxito (sin cámara) |

### Actividad del día (`Pagos1`, opción actividad)

| Input | Respuesta esperada |
|--------|-------------------|
| "Cuota mensual" para un No Socio | Toast "Los no socios no pagan cuota mensual: elegí una actividad" |
| Actividad para un Socio | Toast "Los socios solo pagan la cuota mensual" |
| Musculación sin apto | Toast "Musculación requiere apto físico", no cobra |
| Actividad válida | Deriva al medio elegido y registra el pago diario |

### Carnet (`Carnet1`)

| Input | Respuesta esperada |
|--------|-------------------|
| `30111222` (Socio al día) | Emite carnet con QR: Toast "Carnet emitido" |
| `34123456` (Socio vencido) | Toast "Cuota vencida: debe renovar", no emite |
| `32456789` (No Socio) | Toast "El carnet es solo para Socios", no emite |
| `99999999` | Toast "No hay socio activo con ese DNI", no emite |
| DNI inválido | Toast "DNI inválido: 7 u 8 dígitos numéricos", no emite |

### Vencimientos

Listado de **todos los Socios** ordenados por `fecha_vencimiento`, con estado `Vencida` (fecha anterior a hoy) o `Por vencer` (hoy o posterior). No incluye No Socios.

| Input | Respuesta esperada |
|--------|-------------------|
| Hay socios con distinto estado | Lista ordenada por fecha, con estado `Vencida` / `Por vencer` |
| Todos al día | Lista con estado `Por vencer` en todas las filas |
| Tocar una fila | Abre el cobro con el DNI precargado; al cobrar actualiza el vencimiento |
| No hay socios registrados | Toast "No hay socios para mostrar" |
| Ningún vencido | Toast "No hay cuotas vencidas", manteniendo el listado |

### Consulta (`Consulta1`)

| Input | Respuesta esperada |
|--------|-------------------|
| DNI con pagos | Muestra datos, tipo, vencimiento e historial ordenado del más reciente al más antiguo |
| DNI sin pagos | Muestra los datos y "Sin pagos registrados" |
| DNI inválido | Toast "DNI inválido: 7 u 8 dígitos numéricos" y no muestra datos |
| DNI inexistente | Toast "No hay ninguna persona registrada con ese DNI" y no muestra datos |
| Consultar después de cerrar/reabrir | Mismos datos y pagos persistidos |

### Asistencias (flujo simulado)

| Input | Respuesta esperada |
|--------|-------------------|
| DNI vacío | Toast "Ingresá un DNI", no deriva |
| Formato inválido (ej `123`) | Toast "Formato de DNI inválido", no deriva |
| DNI inexistente (ej `99999999`) | Toast "No hay registro con ese DNI", no deriva |
| DNI de otro tipo (ej. Profesor en Alumno) | Toast "La persona no pertenece al tipo seleccionado", no deriva |
| DNI válido | Muestra nombre, DNI y tipo reales (Socio/Profesor) |
| Confirmar asistencia | Toast "Asistencia confirmada" y vuelve a `MenuPrincipal` (sin guardar nada) |
