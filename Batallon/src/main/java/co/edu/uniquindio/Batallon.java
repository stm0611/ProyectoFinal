package co.edu.uniquindio;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.LinkedList;

public class Batallon {
    private String nombre;
    private String id;

    private LinkedList<VehiculoApoyo> listVehiculosApoyo;
    private LinkedList<VehiculoBlindado> listVehiculosBlindados;
    private LinkedList<VehiculoTransporteTropa> listVehiculosTransporteTropa;
    private static LinkedList<Soldado> personal = Mision.getPersonal();
    private LinkedList<Mision> listMisiones;

    public Batallon(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;

        this.listVehiculosApoyo = new LinkedList<>();
        this.listVehiculosBlindados = new LinkedList<>();
        this.listVehiculosTransporteTropa = new LinkedList<>();
        this.listMisiones = new LinkedList<>();
    }

    public static void setPersonal(LinkedList<Soldado> nuevoPersonal) {
        personal = nuevoPersonal;
    }

    public boolean registrarMision(LocalDate fechaMision, String ubicacionMision,
                                   LinkedList listPersonal, String idVehiculoMision){
        boolean flag = false;

        //Convertir de int a string
        String cantMisionesActuales = String.valueOf(listMisiones.size()+1);

        Mision newMision = new Mision(cantMisionesActuales,fechaMision,ubicacionMision);

        return flag;
    }
    public boolean registarMision (LocalDate fechaMision, String ubicacioMision, LinkedList listPersonal, String idVehiculo){

        boolean flag = false;
        
        String idMisionNueva = String.valueOf(listMisiones.size()+1);
        Mision newMision = new Mision (idMisionNueva, fechaMision, ubicacioMision);
        newMision.setPersonal(listPersonal);

        for(VehiculoTransporteTropa vehiculo : listVehiculosTransporteTropa){
            if(vehiculo.getId().equals(idVehiculo)){
                newMision.setTheVehiculo(vehiculo);
                LinkedList<Mision> listMisionesAux = vehiculo.getListMisiones();
                listMisionesAux.add(newMision);
                vehiculo.setListMisiones(listMisionesAux);
            }
        }
        listMisiones.add(newMision);

        return flag;
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
            if (soldado != null && soldado.isDisponibilidad() && soldado.getRango() == rango) {
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

}

