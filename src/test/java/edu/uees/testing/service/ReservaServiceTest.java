package edu.uees.testing.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Laboratorio 1 - Semana 7 - UCOM0310
 * Pruebas de las reglas de cancelación y cálculo de total de ReservaService.
 * Cada prueba sigue la estructura Arrange - Act - Assert (AAA).
 * El ID entre corchetes corresponde a la matriz de casos.
 */
class ReservaServiceTest {

    // En este laboratorio se usa null en los colaboradores porque
    // puedeCancelar() y calcularTotal() no los utilizan.
    private final ReservaService servicio = new ReservaService(null, null, null);

    // Margen de error para comparar valores double
    private static final double DELTA = 0.001;

    @Test
    void entornoJUnitFunciona() {
        assertTrue(true);
    }

    // ===================== Regla de cancelación =====================

    @Test
    @DisplayName("[CP-01] Con 5 horas de anticipación sí se puede cancelar")
    void cincoHorasPermitenCancelar() {
        // Arrange
        int horas = 5;
        // Act
        boolean resultado = servicio.puedeCancelar(horas);
        // Assert
        assertTrue(resultado);
    }

    @Test
    @DisplayName("[CP-02] 2 horas es el límite y sí se permite cancelar")
    void dosHorasEsElLimitePermitido() {
        // Arrange
        int horas = 2;
        // Act
        boolean resultado = servicio.puedeCancelar(horas);
        // Assert
        assertTrue(resultado, "Con exactamente 2 horas la regla dice que sí se puede cancelar");
    }

    @Test
    @DisplayName("[CP-03] Con 1 hora ya no se puede cancelar")
    void unaHoraNoPermiteCancelar() {
        // Arrange
        int horas = 1;
        // Act
        boolean resultado = servicio.puedeCancelar(horas);
        // Assert
        assertFalse(resultado);
    }

    @Test
    @DisplayName("[CP-04] Sin anticipación (0 horas) no se puede cancelar")
    void ceroHorasNoPermiteCancelar() {
        // Arrange
        int horas = 0;
        // Act
        boolean resultado = servicio.puedeCancelar(horas);
        // Assert
        assertFalse(resultado);
    }

    @Test
    @DisplayName("[CP-05] Con 3 horas (justo encima del límite) sí se puede cancelar")
    void tresHorasJustoEncimaDelLimitePermitenCancelar() {
        // Arrange
        int horas = 3;
        // Act
        boolean resultado = servicio.puedeCancelar(horas);
        // Assert
        assertTrue(resultado);
    }

    @Test
    @DisplayName("[CP-06] Horas negativas no permiten cancelar")
    void horasNegativasNoPermitenCancelar() {
        // Arrange
        int horas = -1;
        // Act
        boolean resultado = servicio.puedeCancelar(horas);
        // Assert
        assertFalse(resultado);
    }

    // ===================== Regla de descuentos =====================

    @Test
    @DisplayName("[CP-07] Cliente NORMAL paga el total sin descuento")
    void normalNoRecibeDescuento() {
        // Arrange
        String tipo = "NORMAL";
        double totalBase = 100;
        // Act
        double total = servicio.calcularTotal(tipo, totalBase);
        // Assert
        assertEquals(100.0, total, DELTA);
    }

    @Test
    @DisplayName("[CP-08] Cliente VIP recibe 15% de descuento")
    void vipRecibeQuincePorCiento() {
        // Arrange
        String tipo = "VIP";
        double totalBase = 100;
        // Act
        double total = servicio.calcularTotal(tipo, totalBase);
        // Assert
        assertEquals(85.0, total, DELTA);
    }

    @Test
    @DisplayName("[CP-09] Cliente ESTUDIANTE recibe 10% de descuento")
    void estudianteRecibeDiezPorCiento() {
        // Arrange
        String tipo = "ESTUDIANTE";
        double totalBase = 100;
        // Act
        double total = servicio.calcularTotal(tipo, totalBase);
        // Assert
        assertEquals(90.0, total, DELTA);
    }

    @Test
    @DisplayName("[CP-10] El tipo en minúsculas (vip) también aplica el descuento")
    void vipEnMinusculasTambienRecibeDescuento() {
        // Arrange
        String tipo = "vip";
        double totalBase = 200;
        // Act
        double total = servicio.calcularTotal(tipo, totalBase);
        // Assert
        assertEquals(170.0, total, DELTA);
    }

    @Test
    @DisplayName("[CP-11] Un tipo que no existe se cobra como NORMAL")
    void tipoDesconocidoNoRecibeDescuento() {
        // Arrange
        String tipo = "PREMIUM";
        double totalBase = 100;
        // Act
        double total = servicio.calcularTotal(tipo, totalBase);
        // Assert
        assertEquals(100.0, total, DELTA);
    }

    @Test
    @DisplayName("[CP-12] Si el tipo es null se cobra sin descuento y no se cae")
    void tipoNuloNoRecibeDescuento() {
        // Arrange
        String tipo = null;
        double totalBase = 50;
        // Act
        double total = servicio.calcularTotal(tipo, totalBase);
        // Assert
        assertEquals(50.0, total, DELTA);
    }

    @Test
    @DisplayName("[CP-13] Total base 0 con VIP devuelve 0")
    void totalCeroVipDevuelveCero() {
        // Arrange
        String tipo = "VIP";
        double totalBase = 0;
        // Act
        double total = servicio.calcularTotal(tipo, totalBase);
        // Assert
        assertEquals(0.0, total, DELTA);
    }

    @Test
    @DisplayName("[CP-14] Total base negativo lanza IllegalArgumentException")
    void totalNegativoEsInvalido() {
        // Arrange
        String tipo = "NORMAL";
        double totalBase = -1;
        // Act
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.calcularTotal(tipo, totalBase));
        // Assert
        assertEquals("Total base inválido", ex.getMessage());
    }

    @Test
    @DisplayName("[CP-15] Un total apenas debajo de cero (-0.01) también es inválido")
    void totalApenasNegativoTambienEsInvalido() {
        // Arrange
        String tipo = "ESTUDIANTE";
        double totalBase = -0.01;
        // Act
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.calcularTotal(tipo, totalBase));
        // Assert
        assertEquals("Total base inválido", ex.getMessage());
    }
}
