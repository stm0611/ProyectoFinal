package co.edu.uniquindio;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;


public class Batallon {
    private String nombre;
    private String id;

    private LinkedList<VehiculoApoyo> listVehiculosApoyo;
    private LinkedList<VehiculoBlindado> listVehiculosBlindados;
    private LinkedList<VehiculoTransporteTropa> listVehiculosTransporteTropa;
    private static LinkedList<Soldado> personal = new LinkedList<>();
    private LinkedList<Mision> listMisiones;

    public Batallon(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;

        this.listVehiculosApoyo = new LinkedList<>();
        this.listVehiculosBlindados = new LinkedList<>();
        this.listVehiculosTransporteTropa = new LinkedList<>();
        this.personal = new LinkedList<>();
        this.listMisiones = new LinkedList<>();

    }

    public static void setPersonal(LinkedList<Soldado> nuevoPersonal) {
        personal = nuevoPersonal;
    }

//Metodo Registrar mision
    public boolean registrarMision (LocalDate fechaMision, String ubicacionMision, LinkedList listPersonal, String idVehiculo){

        boolean flag = false;

        String idMisionNueva = String.valueOf(listMisiones.size()+1);
        Mision newMision = new Mision(idMisionNueva, fechaMision, fechaMision, fechaMision.plusDays(3), ubicacionMision);

        newMision.setPersonal(listPersonal);

        for(VehiculoTransporteTropa vehiculo : listVehiculosTransporteTropa){
            if(vehiculo.getId().equals(idVehiculo)){
                newMision.setTheVehiculo(vehiculo);
                LinkedList<Mision> listMisionesAux = vehiculo.getListMisiones();
                listMisionesAux.add(newMision);
                vehiculo.setListMisiones(listMisionesAux);
                flag = true;
            }
        }
        listMisiones.add(newMision);
        return flag;
    }

    public void agregarVehiculoTransporteTropa(VehiculoTransporteTropa v) {
        listVehiculosTransporteTropa.add(v);
    }

//Metodo buscar vehiculos por año de fabricacion
    public List<Vehiculo> buscarVehiculosPorAnio(int anio) {
        List<Vehiculo> todosVehiculos = new ArrayList<>();
        todosVehiculos.addAll(listVehiculosApoyo);
        todosVehiculos.addAll(listVehiculosBlindados);
        todosVehiculos.addAll(listVehiculosTransporteTropa);

        return todosVehiculos.stream()
                .filter(v -> v.getAnioFabricacion() == anio)
                .sorted(Comparator.comparing(v -> v.getClass().getSimpleName() + v.getModelo()))
                .collect(Collectors.toList());
    }

// Metodo Actualizar estado operativo de todos los vehículos
    public Map<EstadoOperativo, Long> actualizarEstadoVehiculos(double umbralKm) {
        List<Vehiculo> todosVehiculos = new ArrayList<>();
        todosVehiculos.addAll(listVehiculosApoyo);
        todosVehiculos.addAll(listVehiculosBlindados);
        todosVehiculos.addAll(listVehiculosTransporteTropa);

        LocalDate hoy = LocalDate.now();

        for (Vehiculo vehiculo : todosVehiculos) {
            boolean enMision = vehiculo.getListMisiones().stream()
                    .anyMatch(m -> !m.getFecha().isBefore(hoy));

            if (vehiculo.getKilometraje() > umbralKm) {
                vehiculo.setEstadoOperativo(EstadoOperativo.EN_MANTENIMIENTO);
            } else if (enMision) {
                vehiculo.setEstadoOperativo(EstadoOperativo.EN_MISION);
            } else {
                vehiculo.setEstadoOperativo(EstadoOperativo.DISPONIBLE);
            }
        }

        return todosVehiculos.stream()
                .collect(Collectors.groupingBy(Vehiculo::getEstadoOperativo, Collectors.counting()));
    }

    public void agregarVehiculoApoyo(VehiculoApoyo v) {
        listVehiculosApoyo.add(v);
    }

    public LinkedList<Vehiculo> obtenerVehiculosCantMisiones() {
        LinkedList<Vehiculo> vehiculosMisionesCompletadas = new LinkedList<>();


        for (VehiculoApoyo vehiculo : listVehiculosApoyo) {
            if (vehiculo.getMisionesCompletadas() > 50){
                vehiculosMisionesCompletadas.add(vehiculo);
            }
        }

        for(VehiculoBlindado vehiculo : listVehiculosBlindados){
            if (vehiculo.getMisionesCompletadas() > 50){
                vehiculosMisionesCompletadas.add(vehiculo);
            }
        }

        for(VehiculoTransporteTropa vehiculo : listVehiculosTransporteTropa){
            if (vehiculo.getMisionesCompletadas() > 50){
                vehiculosMisionesCompletadas.add(vehiculo);
            }
        }

        return vehiculosMisionesCompletadas;
    }

    public int encontrarPosicionValida(){
        for (int i = 0; i < personal.size();i++){
            if(personal.get(i)== null){
                return i;
            }
        }
        return -1;
    }

    public boolean crearSoldado(Soldado nuevoSoldado) {
        int posicion = encontrarPosicionValida();
        if (posicion != -1) {
            personal.set(posicion, nuevoSoldado);
            return true;
        } else {
            return false;
        }
    }
    public Soldado buscarSoldadoPorId(String cedulaBuscada) {
        for (Soldado soldado : personal) {
            if (soldado != null && soldado.getCedula().equals(cedulaBuscada)) {
                return soldado;
            }
        }
        return null; // No se encontró el soldado
    }
    public double calcularEdadPromedio() {
        double sumaEdades = 0;
        int cantidadSoldados = 0;

        for (Soldado soldado : personal) {
            if (soldado != null) {
                sumaEdades += soldado.getEdad();
                cantidadSoldados++;
            }
        }
        if (cantidadSoldados == 0) {
            return 0; // Retornar 0 si no hay soldados
        }
        return sumaEdades / cantidadSoldados; // Calcular promedio
    }

    public LinkedList<Soldado> obtenerSoldadosDisponiblesPorRango(Rango rango) {
        LinkedList<Soldado> soldadosDisponibles = new LinkedList<>();

        // Recorrer el personal y agregar los soldados disponibles con el rango especificado
        for (Soldado soldado : personal) {
            if (soldado != null && soldado.getDisponibilidad() && soldado.getRango() == rango) {
                soldadosDisponibles.add(soldado);
            }
        }
        return soldadosDisponibles;
    }

    public LinkedList<Soldado> buscarSoldadoPorEspecialidad(Especialidad especialidad) {
        LinkedList<Soldado> soldadosPorEspecialidad = new LinkedList<>();

        // Recorrer el personal y agregar los soldados con la especialidad especificada
        for (Soldado soldado : personal) {
            if (soldado != null && soldado.getEspecialidad() == especialidad) {
                soldadosPorEspecialidad.add(soldado);
            }
        }
        return soldadosPorEspecialidad;
    }

    public Vehiculo obtenerVehiculoConMasMisionesCompletadas() {
        Vehiculo vehiculoConMasMisiones = null;
        int maxMisiones = -1;

        // Recorrer los vehículos de cada tipo y encontrar el que tiene más misiones completadas
        for (VehiculoApoyo vehiculo : listVehiculosApoyo) {
            if (vehiculo.getMisionesCompletadas() > maxMisiones) {
                maxMisiones = vehiculo.getMisionesCompletadas();
                vehiculoConMasMisiones = vehiculo;
            }
        }

        for (VehiculoBlindado vehiculo : listVehiculosBlindados) {
            if (vehiculo.getMisionesCompletadas() > maxMisiones) {
                maxMisiones = vehiculo.getMisionesCompletadas();
                vehiculoConMasMisiones = vehiculo;
            }
        }

        for (VehiculoTransporteTropa vehiculo : listVehiculosTransporteTropa) {
            if (vehiculo.getMisionesCompletadas() > maxMisiones) {
                maxMisiones = vehiculo.getMisionesCompletadas();
                vehiculoConMasMisiones = vehiculo;
            }
        }

        return vehiculoConMasMisiones;
    }
    //creacion del metodo de que calcule  el kilometraje promedio de los vehiculos

    public double calcularKilometrajePromedioTrasporteTropas() {
        if (listVehiculosTransporteTropa.size() == 0) {
            return 0;
        }
        double kilometrajePromedio = 0;
        for (VehiculoTransporteTropa vehiculo : listVehiculosTransporteTropa) {
            kilometrajePromedio += vehiculo.getKilometraje();
        }
        return kilometrajePromedio / listVehiculosTransporteTropa.size();
    }

    public double calcularKilometrajePromedioBlindados() {
        if (listVehiculosBlindados.size() == 0) {
            return 0;
        }
        double kilometrajePromedio = 0;
        for (VehiculoBlindado vehiculo : listVehiculosBlindados) {
            kilometrajePromedio += vehiculo.getKilometraje();
        }
        return kilometrajePromedio / listVehiculosBlindados.size();
    }

    public double calcularKilometrajePromedioApoyo() {
        if (listVehiculosApoyo.size() == 0) {
            return 0;
        }
        double kilometrajePromedio = 0;
        for (VehiculoApoyo vehiculo : listVehiculosApoyo) {
            kilometrajePromedio += vehiculo.getKilometraje();
        }
        return kilometrajePromedio / listVehiculosApoyo.size();
    }
    //Metodo para filtrar misiones por ubicacion y fecha
    public LinkedList<Mision> filtralmisionesporMisionesPorFechayUbicacion(String ubicacionMision,
                                                                           LocalDate fechaInicio,
                                                                           LocalDate fechaFin) {
        LinkedList<Mision> misionesFiltradas = new LinkedList<>();

        for (Mision mision : this.listMisiones) {
            boolean cumpleUbicacion = ubicacionMision == null ||
                    ubicacionMision.equalsIgnoreCase(mision.getUbicacion());

            boolean cumpleFecha = true;
            if (fechaInicio != null) {
                cumpleFecha = !mision.getFecha().isBefore(fechaInicio);
            }
            if (fechaFin != null) {
                cumpleFecha = cumpleFecha && !mision.getFecha().isAfter(fechaFin);
            }

            if (cumpleUbicacion && cumpleFecha) {
                misionesFiltradas.add(mision);
            }
        }

        return misionesFiltradas;
    }


// Metodo para oredenar vehiculos por misiones

    public List<Vehiculo> VehiculosOrdenadosPorMisiones() {

        List<Vehiculo> vehiculosOrdenados = new LinkedList<>();
        vehiculosOrdenados.addAll(this.listVehiculosTransporteTropa);
        vehiculosOrdenados.addAll(this.listVehiculosBlindados);
        vehiculosOrdenados.addAll(this.listVehiculosApoyo);

        vehiculosOrdenados.sort((v1, v2) ->
                v2.getMisionesCompletadas() - v1.getMisionesCompletadas()
        );

        return vehiculosOrdenados;
    }
//get and set de lista vehiculostransporte

    public LinkedList<VehiculoTransporteTropa> getListVehiculosTransporteTropa() {
        return listVehiculosTransporteTropa;
    }

    public void setListVehiculosTransporteTropa(LinkedList<VehiculoTransporteTropa> listVehiculosTransporteTropa) {
        this.listVehiculosTransporteTropa = listVehiculosTransporteTropa;
    }

    public LinkedList<VehiculoApoyo> getListVehiculosApoyo() {
        return listVehiculosApoyo;
    }

    public void setListVehiculosApoyo(LinkedList<VehiculoApoyo> listVehiculosApoyo) {
        this.listVehiculosApoyo = listVehiculosApoyo;
    }

    public LinkedList<VehiculoBlindado> getListVehiculosBlindados() {
        return listVehiculosBlindados;
    }

    public void setListVehiculosBlindados(LinkedList<VehiculoBlindado> listVehiculosBlindados) {
        this.listVehiculosBlindados = listVehiculosBlindados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setListMisiones(LinkedList<Mision> listMisiones) {
        this.listMisiones = listMisiones;
    }

    public LinkedList<Soldado> getPersonal() {
        return personal;
    }


    public LinkedList<Mision> getListMisiones() {
        return listMisiones;
    }

}

