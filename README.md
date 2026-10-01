# UEES UCOM0310 — Semana 7 — Ae6

Suite de pruebas unitarias del módulo de reservas (cancelación, descuentos y confirmación).
Autor: Luis Saá Garzón · Rama de trabajo: `ae6/suite-pruebas`

## Requisitos
- Java 21
- Maven 3.9+
- Git

## Cómo ejecutar las pruebas
```bash
mvn clean test
```
Resultado esperado: 27 pruebas, 0 fallos, `BUILD SUCCESS`.

## Cobertura
Después de `mvn clean test`, abrir `target/site/jacoco/index.html`.
Resultado actual: 100 % de instrucciones y ramas. Ver la interpretación en `docs/02_ANALISIS_COBERTURA_PLANTILLA.md`.

## Estructura de pruebas
| Clase | Qué prueba | Dobles de prueba |
|---|---|---|
| `service/ReservaServiceTest` | `puedeCancelar` y `calcularTotal` | Ninguno (métodos sin dependencias) |
| `service/ReservaServiceConfirmarTest` | `confirmar(Reserva)` | Stub: `DisponibilidadClient` · Mock: `ReservaRepository`, `Notificador` |
| `domain/ReservaTest` | Constructor y `cancelar()` de `Reserva` | Ninguno |

## Documentación
- `docs/01_MATRIZ_CASOS_PLANTILLA.md` — matriz de 26 casos.
- `docs/02_ANALISIS_COBERTURA_PLANTILLA.md` — análisis JaCoCo y mutaciones.
- `docs/03_PULL_REQUEST_PLANTILLA.md` — descripción del Pull Request.
- `docs/04_AUTORREVISION.md` — autorrevisión técnica.

## Regla de trabajo
No se modificó el código productivo para hacer pasar pruebas. Primero se diseñó el caso, luego la prueba y finalmente se interpretó el resultado.

## Uso de IA
Se usó Claude (Anthropic) como apoyo. Detalle en `docs/03_PULL_REQUEST_PLANTILLA.md`.
