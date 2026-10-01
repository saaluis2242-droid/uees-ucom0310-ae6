# Matriz de casos – Laboratorio 1 (Semana 7)

Estudiante: Luis Saá Garzón

| ID | Regla | Escenario | Entrada | Esperado | Tipo | Riesgo |
|---|---|---|---|---|---|---|
| CP-01 | Cancelación (>= 2 h) | Anticipación habitual | 5 | true | Normal | Comprueba la regla general |
| CP-02 | Cancelación (>= 2 h) | Justo en el límite | 2 | true | Límite | Detecta si se usa > en lugar de >= |
| CP-03 | Cancelación (>= 2 h) | Justo debajo del límite | 1 | false | Límite | Protege la frontera inferior |
| CP-04 | Cancelación (>= 2 h) | Sin anticipación | 0 | false | Extremo | No debe permitir cancelar al momento |
| CP-05 | Cancelación (>= 2 h) | Justo encima del límite | 3 | true | Límite | Confirma el lado permitido cerca de la frontera |
| CP-06 | Cancelación (>= 2 h) | Horas negativas | -1 | false | Inválido | Valor sin sentido no debe permitir cancelar |
| CP-07 | Descuentos | Cliente NORMAL | NORMAL, 100 | 100 | Normal | Sin descuento |
| CP-08 | Descuentos | Cliente VIP | VIP, 100 | 85 | Alternativo | Descuento del 15 % |
| CP-09 | Descuentos | Cliente ESTUDIANTE | ESTUDIANTE, 100 | 90 | Alternativo | Descuento del 10 % |
| CP-10 | Descuentos | Tipo en minúsculas | vip, 200 | 170 | Alternativo | La regla ignora mayúsculas/minúsculas |
| CP-11 | Descuentos | Tipo que no existe | PREMIUM, 100 | 100 | Alternativo | Tipos desconocidos se cobran como NORMAL |
| CP-12 | Descuentos | Tipo nulo | null, 50 | 50 | Inválido | No debe lanzar NullPointerException |
| CP-13 | Descuentos | Total cero | VIP, 0 | 0 | Límite | 0 es válido (la condición es < 0) |
| CP-14 | Descuentos | Total negativo | NORMAL, -1 | IllegalArgumentException | Inválido | Mensaje "Total base inválido" |
| CP-15 | Descuentos | Total apenas negativo | ESTUDIANTE, -0.01 | IllegalArgumentException | Límite / Inválido | Frontera del total en cero |
