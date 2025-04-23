package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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
    @Test
    void testcalcularKilometrajePromedioTrasporteTropas() {

        Batallon batallon = new Batallon("Alpha", "A-01");

        VehiculoTransporteTropa v1 = new VehiculoTransporteTropa(
                "V1", "Modelo1", 2020, 5000.0, EstadoOperativo.DISPONIBLE, 10
        );
        VehiculoTransporteTropa v2 = new VehiculoTransporteTropa(
                "V2", "Modelo2", 2021, 3000.0, EstadoOperativo.DISPONIBLE, 8
        );

        batallon.getListVehiculosTransporteTropa().add(v1
        );
        batallon.getListVehiculosTransporteTropa().add(v2);

        double promedio = batallon.calcularKilometrajePromedioTrasporteTropas();

        assertEquals(4000.0, promedio, 0.01, "El promedio debe ser (5000 + 3000)/2 = 4000");
    }


    @Test
    void testfiltralmisionesporMisionesPorFechayUbicacion() {
        Batallon batallon = new Batallon("Alpha", "A-01");

        Mision m1 = new Mision("M1", LocalDate.of(2023, 5, 10),
                LocalDate.of(2023, 9, 12),
                LocalDate.of(2023, 5, 24),
                "Base Sur");

        Mision m2 = new Mision("M2", LocalDate.of(2023, 6, 15),
                LocalDate.of(2023, 9, 12),
                LocalDate.of(2023, 6, 24),
                "Base Norte");

        batallon.getListMisiones().add(m1);
        batallon.getListMisiones().add(m2);

        LinkedList<Mision> resultado = batallon.filtralmisionesporMisionesPorFechayUbicacion(
                "Base Sur",
                LocalDate.of(2023, 5, 1),
                LocalDate.of(2023, 5, 31)
        );

        assertEquals(1, resultado.size());
        assertEquals("M1", resultado.get(0).getId());
    }

    @Test

    void testVehiculosOrdenadosPorMisione() {


        Batallon batallon = new Batallon("Élite", "E-01");

        VehiculoTransporteTropa v1 = new VehiculoTransporteTropa(
                "V1", "ModeloA", 2020, 5000.0, EstadoOperativo.DISPONIBLE, 10
        );
        v1.setMisionesCompletadas(15);

        VehiculoBlindado v2 = new VehiculoBlindado(
                "V2", "TanqueX", 2021, 3000.0, EstadoOperativo.EN_MISION, 5
        );
        v2.setMisionesCompletadas(30);
        VehiculoApoyo v3 = new VehiculoApoyo(
                "V3", "ApoyoY", 2019, 8000.0, EstadoOperativo.DISPONIBLE, TipoFuncion.MEDICO
        );
        v3.setMisionesCompletadas(5);

        batallon.getListVehiculosTransporteTropa().add(v1);
        batallon.getListVehiculosBlindados().add(v2);
        batallon.getListVehiculosApoyo().add(v3);


        List<Vehiculo> resultado = batallon.VehiculosOrdenadosPorMisiones();


        assertEquals(3, resultado.size(), "Deberían haber 3 vehículos");


        assertEquals(30, resultado.get(0).getMisionesCompletadas(), "El vehículo con 30 misiones debería ir primero");
        assertEquals(15, resultado.get(1).getMisionesCompletadas(), "El vehículo con 15 misiones debería ir segundo");
        assertEquals(5, resultado.get(2).getMisionesCompletadas(), "El vehículo con 5 misiones debería ir último");

        assertSame(v2, resultado.get(0), "El vehículo V2 debería estar en la posición 0");
    }

    @Test
    void testRegistrarMisionConVehiculoExistente() {
        // Crear vehículo de prueba
        VehiculoTransporteTropa vehiculo = new VehiculoTransporteTropa(
                "VEH001",
                "Camión Militar",
                2020,
                100000,
                EstadoOperativo.DISPONIBLE,
                10
        );

        // Agregar vehículo al batallón
        batallon.getListVehiculosTransporteTropa().add(vehiculo);

        // Crear lista de personal para la misión
        LinkedList<Soldado> personal = new LinkedList<>();
        personal.add(new Soldado("Carlos", "999", 30, Especialidad.MEDICO, Rango.TENIENTE, true));

        // Registrar misión
        boolean resultado = batallon.registrarMision(
                LocalDate.of(2025, 4, 22),
                "Zona A",
                personal,
                "VEH001"
        );

        // Validar que la misión fue registrada correctamente
        assertTrue(resultado);
        assertEquals(1, batallon.getListMisiones().size());

        Mision mision = batallon.getListMisiones().get(0);
        assertEquals("Zona A", mision.getUbicacion());
        assertEquals(personal, mision.getPersonal());
        assertEquals(vehiculo, mision.getTheVehiculo());
        assertEquals(1, vehiculo.getListMisiones().size());
    }

    @Test
    void testBuscarVehiculosPorAnio() {
        Batallon b = new Batallon("Batallon A", "001");
        VehiculoApoyo v1 = new VehiculoApoyo("1", "ModeloX", 2020, 10000, EstadoOperativo.DISPONIBLE, TipoFuncion.LOGISTICA);
        VehiculoApoyo v2 = new VehiculoApoyo("2", "ModeloY", 2020, 20000, EstadoOperativo.DISPONIBLE, TipoFuncion.MEDICO);
        b.agregarVehiculoTransporteTropa(new VehiculoTransporteTropa("3", "TroopX", 2021, 15000, EstadoOperativo.DISPONIBLE, 30));

        b.agregarVehiculoApoyo(v1);
        b.agregarVehiculoApoyo(v2);

        List<Vehiculo> result = b.buscarVehiculosPorAnio(2020);
        assertEquals(2, result.size());
    }

    @Test
    void testActualizarEstadoVehiculos() {
        Batallon b = new Batallon("Batallon A", "001");
        VehiculoBlindado v1 = new VehiculoBlindado("1", "BlindadoA", 2020, 80000, EstadoOperativo.DISPONIBLE, 5);
        VehiculoApoyo v2 = new VehiculoApoyo("2", "ApoyoB", 2022, 10000, EstadoOperativo.DISPONIBLE, TipoFuncion.LOGISTICA);
        LocalDate fecha = LocalDate.now().plusDays(1);
        Mision misionFutura = new Mision("1", fecha, fecha, fecha, "ZonaX");

        v2.getListMisiones().add(misionFutura);

        b.getListVehiculosBlindados().add(v1);
        b.getListVehiculosApoyo().add(v2);


        Map<EstadoOperativo, Long> resumen = b.actualizarEstadoVehiculos(50000);

        assertEquals(1, resumen.get(EstadoOperativo.EN_MANTENIMIENTO));
        assertEquals(1, resumen.get(EstadoOperativo.EN_MISION));
    }

}




