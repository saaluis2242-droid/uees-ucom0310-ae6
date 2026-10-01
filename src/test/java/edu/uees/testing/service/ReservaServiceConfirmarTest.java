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
import static org.mockito.Mockito.doThrow;
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
    @DisplayName("[CP-17] Sin disponibilidad: lanza excepción y no guarda ni notifica")
    void reservaNoDisponibleNoSeGuardaNiNotifica() {
        // Arrange (Stub: el horario NO está disponible)
        when(disponibilidad.estaDisponible(any())).thenReturn(false);
        Reserva reserva = new Reserva("R-002", "NORMAL");

        // Act
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> servicio.confirmar(reserva));

        // Assert
        assertEquals("Horario no disponible", ex.getMessage());
        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
        verify(repository, never()).guardar(any());
        verify(notificador, never()).enviarConfirmacion(any());
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

    @Test
    @DisplayName("[CP-19] Se consulta la disponibilidad una sola vez y con la misma reserva")
    void disponibilidadSeConsultaUnaVezConLaMismaReserva() {
        // Arrange
        when(disponibilidad.estaDisponible(any())).thenReturn(true);
        Reserva reserva = new Reserva("R-003", "VIP");

        // Act
        servicio.confirmar(reserva);

        // Assert
        verify(disponibilidad, times(1)).estaDisponible(reserva);
    }

    @Test
    @DisplayName("[CP-20] Primero se guarda la reserva y después se envía la notificación")
    void seGuardaAntesDeNotificar() {
        // Arrange
        when(disponibilidad.estaDisponible(any())).thenReturn(true);
        Reserva reserva = new Reserva("R-004", "ESTUDIANTE");

        // Act
        servicio.confirmar(reserva);

        // Assert (el orden importa: no se debe avisar al cliente de algo que no se guardó)
        InOrder orden = inOrder(repository, notificador);
        orden.verify(repository).guardar(reserva);
        orden.verify(notificador).enviarConfirmacion(reserva);
    }

    @Test
    @DisplayName("[CP-25] Si falla el guardado, la excepción se propaga y no se notifica al cliente")
    void siFallaElGuardadoNoSeNotifica() {
        // Arrange (Stub: hay disponibilidad, pero el repositorio falla al guardar)
        when(disponibilidad.estaDisponible(any())).thenReturn(true);
        doThrow(new IllegalStateException("Base de datos no disponible"))
                .when(repository).guardar(any());
        Reserva reserva = new Reserva("R-005", "NORMAL");

        // Act
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> servicio.confirmar(reserva));

        // Assert (no se debe avisar al cliente de una reserva que no quedó guardada)
        assertEquals("Base de datos no disponible", ex.getMessage());
        verify(notificador, never()).enviarConfirmacion(any());
    }
}
