package edu.uees.testing.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Laboratorio 2 - Semana 7 - UCOM0310
 * Pruebas agregadas a partir del reporte JaCoCo: la clase Reserva tenía
 * ramas del constructor y el método cancelar() sin ejecutar.
 */
class ReservaTest {

    @Test
    @DisplayName("[CP-21] Una reserva con id nulo no se puede crear")
    void reservaConIdNuloLanzaExcepcion() {
        // Arrange
        String id = null;
        // Act
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Reserva(id, "NORMAL"));
        // Assert
        assertEquals("Id obligatorio", ex.getMessage());
    }

    @Test
    @DisplayName("[CP-22] Una reserva con id en blanco no se puede crear")
    void reservaConIdEnBlancoLanzaExcepcion() {
        // Arrange
        String id = "   ";
        // Act
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Reserva(id, "NORMAL"));
        // Assert
        assertEquals("Id obligatorio", ex.getMessage());
    }

    @Test
    @DisplayName("[CP-23] Si el tipo es nulo, la reserva queda como NORMAL y PENDIENTE")
    void tipoNuloSeAsumeNormal() {
        // Arrange
        String tipo = null;
        // Act
        Reserva reserva = new Reserva("R-010", tipo);
        // Assert
        assertEquals("R-010", reserva.getId());
        assertEquals("NORMAL", reserva.getTipo());
        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
    }

    @Test
    @DisplayName("[CP-24] Cancelar una reserva cambia su estado a CANCELADA")
    void cancelarCambiaEstadoACancelada() {
        // Arrange
        Reserva reserva = new Reserva("R-011", "VIP");
        // Act
        reserva.cancelar();
        // Assert
        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
    }
}
