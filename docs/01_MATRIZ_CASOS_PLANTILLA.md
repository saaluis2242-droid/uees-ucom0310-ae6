# Matriz de casos – Ae6 (Semana 7)

Estudiante: Luis Saá Garzón · Rama: `ae6/suite-pruebas`

Tipos usados: **Normal**, **Alternativo**, **Límite**, **Inválido** (entrada no válida que no lanza excepción) y **Excepción** (el método debe lanzar una excepción).

| ID | Método | Escenario | Entrada | Esperado | Tipo | Riesgo que protege | Origen |
|---|---|---|---|---|---|---|---|
| CP-01 | puedeCancelar | Anticipación habitual | 5 | true | Normal | Regla general de cancelación | Lab 1 |
| CP-02 | puedeCancelar | Límite válido | 2 | true | Límite | Uso de > en lugar de >= | Lab 1 |
| CP-03 | puedeCancelar | Justo debajo del límite | 1 | false | Límite | Frontera inferior | Lab 1 |
| CP-04 | puedeCancelar | Sin anticipación | 0 | false | Límite | Cancelación inmediata | Lab 1 |
| CP-05 | puedeCancelar | Justo encima del límite | 3 | true | Límite | Lado permitido de la frontera | Lab 1 |
| CP-06 | puedeCancelar | Horas negativas | -1 | false | Inválido | Valores sin sentido | Lab 1 |
| CP-07 | calcularTotal | Cliente NORMAL | NORMAL, 100 | 100 | Normal | Cobro sin descuento | Lab 1 |
| CP-08 | calcularTotal | Cliente VIP | VIP, 100 | 85 | Alternativo | Descuento del 15 % | Lab 1 |
| CP-09 | calcularTotal | Cliente ESTUDIANTE | ESTUDIANTE, 100 | 90 | Alternativo | Descuento del 10 % | Lab 1 |
| CP-10 | calcularTotal | VIP en minúsculas | vip, 200 | 170 | Alternativo | Comparación sin distinguir mayúsculas | Lab 1 |
| CP-11 | calcularTotal | Tipo desconocido | PREMIUM, 100 | 100 | Alternativo | Tipos no previstos | Lab 1 |
| CP-12 | calcularTotal | Tipo nulo | null, 50 | 50 | Inválido | NullPointerException | Lab 1 |
| CP-13 | calcularTotal | Total cero | VIP, 0 | 0 | Límite | Frontera del total (< 0) | Lab 1 |
| CP-14 | calcularTotal | Total negativo | NORMAL, -1 | IllegalArgumentException "Total base inválido" | Excepción | Totales inválidos | Lab 1 |
| CP-15 | calcularTotal | Total apenas negativo | ESTUDIANTE, -0.01 | IllegalArgumentException | Excepción (límite) | Frontera del total | Lab 1 |
| CP-16 | confirmar | Reserva disponible | Stub = true | CONFIRMADA, guarda y notifica | Normal | Flujo principal | Lab 2 |
| CP-17 | confirmar | Sin disponibilidad | Stub = false | IllegalStateException, PENDIENTE, no guarda ni notifica | Excepción | Notificar algo no confirmado | Lab 2 |
| CP-18 | confirmar | Reserva nula | null | IllegalArgumentException, no consulta dependencias | Excepción | Seguir el flujo sin datos | Lab 2 |
| CP-19 | confirmar | Consulta única | Stub = true | estaDisponible 1 vez con la misma reserva | Normal | Consultas repetidas u objeto equivocado | Lab 2 |
| CP-20 | confirmar | Orden de pasos | Stub = true | Guardar antes de notificar | Normal | Notificar algo no guardado | Lab 2 |
| CP-21 | Reserva (constructor) | Id nulo | null, NORMAL | IllegalArgumentException "Id obligatorio" | Excepción | Reservas sin identificador | Lab 2 |
| CP-22 | Reserva (constructor) | Id en blanco | "   ", NORMAL | IllegalArgumentException "Id obligatorio" | Excepción | Reservas sin identificador | Lab 2 |
| CP-23 | Reserva (constructor) | Tipo nulo | R-010, null | Tipo NORMAL, PENDIENTE | Alternativo | Valor por defecto | Lab 2 |
| CP-24 | Reserva.cancelar | Cancelar | R-011, VIP | CANCELADA | Normal | Cambio de estado | Lab 2 |
| CP-25 | confirmar | Falla al guardar | Stub = true, guardar lanza excepción | Se propaga la excepción y no se notifica | Excepción | Avisar de una reserva que no se guardó | Ae6 |
| CP-26 | calcularTotal | ESTUDIANTE en minúsculas | estudiante, 200 | 180 | Alternativo | Mutación que JaCoCo no detecta | Ae6 |

**Resumen:** 26 casos · 6 normales · 6 alternativos · 5 límite · 2 inválidos · 7 excepciones.
