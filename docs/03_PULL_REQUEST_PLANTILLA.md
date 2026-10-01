# test: completar suite, cobertura y evidencia de Ae6

## Objetivo
Proteger con pruebas automatizadas las reglas críticas del módulo de reservas: cancelación, cálculo de descuentos y confirmación de reservas con dependencias externas.

## Cambios realizados
- Pruebas de `puedeCancelar` con caso normal, límites (0, 1, 2, 3) y valor inválido.
- Pruebas de `calcularTotal` para NORMAL, VIP, ESTUDIANTE, mayúsculas/minúsculas, tipo nulo o desconocido, total cero y totales negativos.
- Pruebas de `confirmar` con Mockito: Stub para la disponibilidad y Mock para repositorio y notificador.
- Pruebas de la clase `Reserva` agregadas a partir del reporte JaCoCo.
- Matriz de casos, análisis de cobertura, autorrevisión y README actualizados en `docs/`.

## Casos de prueba
- 27 pruebas (26 casos de la matriz + prueba de entorno) en 3 clases: `ReservaServiceTest`, `ReservaServiceConfirmarTest` y `ReservaTest`.
- Detalle completo en `docs/01_MATRIZ_CASOS_PLANTILLA.md`.
- Stub: `DisponibilidadClient` (`when(...).thenReturn(true/false)`).
- Mock: `ReservaRepository` y `Notificador` (`verify`, `never()`, `InOrder`).

## Cómo verificar
```bash
git switch ae6/suite-pruebas
mvn clean test
```
Resultado esperado: `Tests run: 27, Failures: 0, Errors: 0, Skipped: 0` y `BUILD SUCCESS`.
Reporte de cobertura: `target/site/jacoco/index.html`.

## Cobertura
- 100 % de instrucciones y 100 % de ramas (39/39 líneas, 18/18 ramas).
- El 100 % no garantiza corrección: con 100 % una mutación en el descuento de ESTUDIANTE no era detectada; se agregó CP-26. Detalle en `docs/02_ANALISIS_COBERTURA_PLANTILLA.md`.

## Limitaciones
- No está definida la regla para confirmar una reserva CANCELADA.
- No está definido qué hacer si falla el notificador después de guardar.
- Uso de `double` para valores monetarios.
- Las pruebas de `puedeCancelar` y `calcularTotal` construyen el servicio con colaboradores `null` porque esos métodos no los usan.

## Autorrevisión
- [x] Compila
- [x] Pruebas en verde
- [x] Sin archivos accidentales (`target/` está en `.gitignore`)
- [x] Commits descriptivos
- [x] Documentación actualizada

## Uso de IA
Se utilizó Claude (Anthropic) como apoyo para proponer casos de prueba adicionales, generar borradores del código de prueba y de la documentación, y sugerir el experimento de mutación. El estudiante revisó el código, ejecutó la suite con `mvn clean test`, generó y revisó el reporte JaCoCo, realizó los commits y el Pull Request.
