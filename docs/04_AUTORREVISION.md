# Autorrevisión técnica – Ae6

| Punto revisado | Resultado | Acción tomada |
|---|---|---|
| Files changed línea por línea | Revisado en el PR | Sin cambios en código productivo (`src/main`) |
| target/ o archivos generados | No aparecen | `.gitignore` ya excluye `target/`, `.idea/`, `.vscode/` |
| Credenciales o datos sensibles | No hay | — |
| Nombres de pruebas expresivos | Sí, con `@DisplayName` e ID de la matriz | — |
| Aserciones comprueban comportamiento | Sí, incluidos mensajes de excepción | — |
| Casos límite presentes | Sí (0, 1, 2, 3 horas; 0 y -0.01 en total) | — |
| Mocks verifican interacciones relevantes | Sí, solo guardar, notificar, consulta única y orden | No se usó `verifyNoMoreInteractions` para no atar las pruebas a detalles internos |
| Cobertura interpretada | Sí, con experimento de mutación | Se detectó un hueco (ESTUDIANTE en minúsculas) y se agregó CP-26 |
| Matriz de casos | Faltaba distinguir Inválido de Excepción y la columna Método | Se rehízo la matriz con esas columnas |
| Nombre del archivo de la matriz | En el Lab 1 se había renombrado la plantilla | Se restauró `docs/01_MATRIZ_CASOS_PLANTILLA.md` con `git mv` |
| Commits explican la evolución | Sí, prefijos `test:` y `docs:` | — |
| PR indica cómo verificar | Sí | — |
