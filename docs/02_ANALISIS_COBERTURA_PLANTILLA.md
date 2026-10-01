# Análisis de cobertura – Laboratorio 2 (Semana 7)

Estudiante: Luis Saá Garzón
Rama: test/lab2-dobles-cobertura

## Resultado observado
| Momento | Pruebas | Instrucciones | Ramas | ReservaService (instr. / ramas) | Reserva (instr. / ramas) |
|---|---|---|---|---|---|
| Solo Laboratorio 1 | 16 | 33 % | 44 % | 62 % / 66 % | 0 % / n/a |
| + pruebas de confirmar() con Mockito | 21 | 87 % | 83 % | 100 % / 100 % | 60 % / 50 % |
| + pruebas de Reserva (a partir del reporte) | 25 | 100 % | 100 % | 100 % / 100 % | 100 % / 100 % |

- Clase o método analizado: ReservaService.confirmar(Reserva) y la clase Reserva.

## Huecos relevantes
1. Antes del Laboratorio 2, confirmar() estaba en 0 %: ninguna prueba protegía la confirmación, el caso sin disponibilidad ni la reserva nula.
2. Con las pruebas de confirmar(), el reporte mostró que el constructor de Reserva tenía 3 de 6 ramas sin ejecutar (id nulo, id en blanco y tipo nulo) y que cancelar() nunca se ejecutaba. Es decir, nadie comprobaba que no se pueda crear una reserva sin id.
3. Código cubierto pero mal probado: las 3 pruebas obligatorias ya dejan ReservaService en 100 %, pero no comprueban que se guarde antes de notificar ni que la disponibilidad se consulte una sola vez. Si alguien invierte esas dos líneas, la cobertura seguiría en 100 % y las 3 pruebas seguirían en verde.

## Decisiones
- ¿Qué prueba nueva se añadió?
  - CP-19 disponibilidadSeConsultaUnaVezConLaMismaReserva y CP-20 seGuardaAntesDeNotificar (InOrder).
  - ReservaTest: CP-21 id nulo, CP-22 id en blanco, CP-23 tipo nulo se asume NORMAL, CP-24 cancelar cambia a CANCELADA.
- ¿Qué riesgo protege?
  - Que se notifique al cliente una reserva que no se guardó, y que se creen reservas sin identificador.
- ¿Por qué no basta con el porcentaje?
  - CP-19 y CP-20 no subieron ni un punto la cobertura, pero sí protegen reglas. El 100 % solo dice que las líneas se ejecutaron, no que el resultado se haya revisado.

## Comportamiento que todavía no está protegido
- Qué pasa si repository.guardar() falla (por ejemplo, se cae la base de datos). Hoy la excepción sube y no se notifica, pero ninguna prueba lo asegura. Prueba propuesta: usar doThrow(new RuntimeException()).when(repository).guardar(any()) y verificar con verify(notificador, never()) que no se envía la confirmación.
- No hay ninguna regla sobre confirmar una reserva que ya está CANCELADA. El código lo permite. Habría que preguntar si es correcto antes de escribir una prueba.
