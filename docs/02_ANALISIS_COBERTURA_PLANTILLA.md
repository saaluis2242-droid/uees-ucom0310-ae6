# Análisis de cobertura – Ae6

## Resultado observado
| Momento | Pruebas | Instrucciones | Ramas | ReservaService (instr. / ramas) | Reserva (instr. / ramas) |
|---|---|---|---|---|---|
| Laboratorio 1 | 16 | 33 % | 44 % | 62 % / 66 % | 0 % / 0 % |
| + confirmar() con Mockito (Lab 2) | 21 | 87 % | 83 % | 100 % / 100 % | 60 % / 50 % |
| + ReservaTest (Lab 2) | 25 | 100 % | 100 % | 100 % / 100 % | 100 % / 100 % |
| + CP-25 y CP-26 (Ae6) | 27 | 100 % | 100 % | 100 % / 100 % | 100 % / 100 % |

- Cobertura de líneas final: 39/39. Cobertura de ramas final: 18/18.
- Clases analizadas: ReservaService (puedeCancelar, calcularTotal, confirmar) y Reserva.

## Huecos relevantes
1. Antes del Lab 2, `confirmar()` estaba en 0 % (8 líneas y 4 ramas sin ejecutar): ningún flujo con dependencias estaba protegido.
2. Con las pruebas de `confirmar()`, el constructor de `Reserva` tenía 3 de 6 ramas sin ejecutar (id nulo, id en blanco, tipo nulo) y `cancelar()` en 0 %.
3. Con 100 % de cobertura, una mutación manual (cambiar `equalsIgnoreCase` por `equals` en ESTUDIANTE) **no hizo fallar ninguna prueba**. La rama se ejecutaba, pero nadie revisaba el caso en minúsculas.

## Experimento de mutación manual (con 25 pruebas y 100 % de cobertura)
| Mutación | ¿Se detectó? | Prueba que falló |
|---|---|---|
| `>= 2` → `> 2` | Sí | CP-02 |
| VIP `0.85` → `0.80` | Sí | CP-08, CP-10 |
| `totalBase < 0` → `<= 0` | Sí | CP-13 |
| Quitar validación de reserva nula | Sí | CP-18 |
| Notificar antes de guardar | Sí | CP-20 (y CP-25 después de Ae6) |
| ESTUDIANTE `equalsIgnoreCase` → `equals` | **No** (antes de CP-26) | — → ahora CP-26 |

## Decisiones
- Pruebas añadidas a partir del análisis: ReservaTest (CP-21 a CP-24), CP-25 (falla al guardar) y CP-26 (estudiante en minúsculas).
- Riesgos que protegen: reservas sin id, notificar una reserva que no se guardó y perder el descuento por cómo se escribe el tipo.
- Por qué no basta el porcentaje: CP-19, CP-20, CP-25 y CP-26 no subieron la cobertura ni un punto, pero detectan errores reales. La cobertura mide qué se ejecutó, no qué se comprobó.

## Limitaciones conocidas
- No hay regla definida para confirmar una reserva ya CANCELADA (el código lo permite).
- Si falla el notificador, la reserva queda CONFIRMADA y guardada pero el cliente no recibe aviso; no está definido qué debería pasar.
- `calcularTotal` usa `double` para dinero; con valores con muchos decimales podría haber errores de redondeo.
- Un tipo vacío (`""`) no se convierte en NORMAL en `Reserva`; no está definido si debería.
