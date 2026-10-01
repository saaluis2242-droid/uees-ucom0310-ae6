package edu.uees.testing.service;

import edu.uees.testing.availability.DisponibilidadClient;
import edu.uees.testing.domain.EstadoReserva;
import edu.uees.testing.domain.Reserva;
import edu.uees.testing.notification.Notificador;
import edu.uees.testing.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Laboratorio 2 - Semana 7 - UCOM0310
 * Pruebas de confirmar(Reserva) usando dobles de prueba con Mockito.
 * - disponibilidad funciona como STUB: solo le decimos qué responder (when...thenReturn).
 * - repository y notificador funcionan como MOCK: revisamos si fueron llamados (verify).
 */
class ReservaServiceConfirmarTest {

    private DisponibilidadClient disponibilidad;
    private ReservaRepository repository;
    private Notificador notificador;
    private ReservaService servicio;

    @BeforeEach
    void prepararDobles() {
        // Se crean dobles nuevos antes de cada prueba para que no se mezclen entre sí
        disponibilidad = mock(DisponibilidadClient.class);
        repository = mock(ReservaRepository.class);
        notificador = mock(Notificador.class);
        servicio = new ReservaService(disponibilidad, repository, notificador);
    }

    @Test
    @DisplayName("[CP-16] Reserva disponible: se confirma, se guarda y se notifica")
    void reservaDisponibleSeConfirmaGuardaYNotifica() {
        // Arrange (Stub: el horario sí está disponible)
        when(disponibilidad.estaDisponible(any())).thenReturn(true);
        Reserva reserva = new Reserva("R-001", "NORMAL");

        // Act
        servicio.confirmar(reserva);

        // Assert (estado + interacciones con los Mocks)
        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        verify(repository).guardar(reserva);
        verify(notificador).enviarConfirmacion(reserva);
    }

    @Test
    @DisplayName("[CP-18] Reserva nula: se detiene antes de consultar cualquier dependencia")
    void reservaNulaNoConsultaDependencias() {
        // Arrange: no se configura nada, la validación debe cortar el flujo antes

        // Act
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.confirmar(null));

        // Assert
        assertEquals("Reserva obligatoria", ex.getMessage());
        verify(disponibilidad, never()).estaDisponible(any());
        verify(repository, never()).guardar(any());
        verify(notificador, never()).enviarConfirmacion(any());
    }
}
