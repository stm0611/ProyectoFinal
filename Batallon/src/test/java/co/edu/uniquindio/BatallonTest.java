package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BatallonTest {

    private Batallon batallon;

    @BeforeEach
    void setUp() {
        batallon = new Batallon("Batallon A", "001");

        // Crear soldados de prueba
        Soldado soldado1 = new Soldado("Juan", "123", 25, Especialidad.COMUNICACIONES, Rango.CABO, true);
        Soldado soldado2 = new Soldado("Luis", "456", 30, Especialidad.MEDICO, Rango.SARGENTO, true);
        Soldado soldado3 = new Soldado("Pedro", "789", 35, Especialidad.LOGISTICA, Rango.CAPITAN, true);
        Soldado soldado4 = new Soldado("Maria", "101", 28, Especialidad.COMUNICACIONES, Rango.CABO, false); // No disponible

        // Agregar soldados a la lista estática (simulación)
        LinkedList<Soldado> lista = new LinkedList<>();
        lista.add(soldado1);
        lista.add(soldado2);
        lista.add(soldado3);
        lista.add(soldado4);

        // Simular que personal viene de Mision.getPersonal()
        Batallon.setPersonal(lista);
    }

    @Test
    void testBuscarSoldadoPorIdExistente() {
        Soldado resultado = batallon.buscarSoldadoPorId("123");
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void testBuscarSoldadoPorIdInexistente() {
        Soldado resultado = batallon.buscarSoldadoPorId("999");
        assertNull(resultado);
    }

    @Test
    void testObtenerSoldadosDisponiblesPorRango() {
        // Obtener soldados disponibles por rango (Sargento)
        LinkedList<Soldado> soldadosSargentos = batallon.obtenerSoldadosDisponiblesPorRango(Rango.SARGENTO);

        // Verificar que el soldado Luis (Sargento) está en la lista
        assertEquals(1, soldadosSargentos.size());
        assertEquals("Luis", soldadosSargentos.get(0).getNombre());

        // Obtener soldados disponibles por rango (Cabo)
        LinkedList<Soldado> soldadosCabos = batallon.obtenerSoldadosDisponiblesPorRango(Rango.CABO);

        // Verificar que el soldado Juan (Cabo) está en la lista, pero no Maria (por estar no disponible)
        assertEquals(1, soldadosCabos.size());
        assertEquals("Juan", soldadosCabos.get(0).getNombre());
    }

    @Test
    void testBuscarSoldadoPorEspecialidad() {
        // Obtener soldados por especialidad (COMUNICACIONES)
        LinkedList<Soldado> soldadosComunicaciones = batallon.buscarSoldadoPorEspecialidad(Especialidad.COMUNICACIONES);

        // Verificar que los soldados con especialidad COMUNICACIONES están en la lista
        assertEquals(2, soldadosComunicaciones.size());
        assertEquals("Juan", soldadosComunicaciones.get(0).getNombre());
        assertEquals("Maria", soldadosComunicaciones.get(1).getNombre());

        // Obtener soldados por especialidad (MEDICO)
        LinkedList<Soldado> soldadosMedico = batallon.buscarSoldadoPorEspecialidad(Especialidad.MEDICO);

        // Verificar que el soldado Luis (MEDICO) está en la lista
        assertEquals(1, soldadosMedico.size());
        assertEquals("Luis", soldadosMedico.get(0).getNombre());
    }
    
}




