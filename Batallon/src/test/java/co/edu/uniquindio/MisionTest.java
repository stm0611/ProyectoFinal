package co.edu.uniquindio;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MisionTest {

    @Test
    void testLiberarSoldados() {

        Mision mision = new Mision("M001", LocalDate.of(2023,2,24),
                LocalDate.now(), LocalDate.now().plusDays(1), "Base");

        Soldado soldado1 = new Soldado("Juan", "1001", 25,
                Especialidad.MEDICO, Rango.SOLDADO, false);

        Soldado soldado2 = new Soldado("Ana", "1002", 30,
                Especialidad.COMUNICACIONES, Rango.CABO, false);

        mision.getPersonal().add(soldado1);
        mision.getPersonal().add(soldado2);

        mision.finalizarMision();

        assertTrue(soldado1.isDisponibilidad(), "El soldado 1 debería estar disponible");
        assertTrue(soldado2.isDisponibilidad(), "El soldado 2 debería estar disponible");
        assertEquals(2, mision.getPersonal().size(), "La lista de personal debe mantener sus elementos");
    }

}