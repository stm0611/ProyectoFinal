package co.edu.uniquindio;

import java.time.LocalDate;
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

    public boolean registrarMision(LocalDate fechaMision, String ubicacionMision,
                                   LinkedList listPersonal, String idVehiculomision){
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


    public boolean crearSoldado(Soldado nuevoSoldado){
        int posicion = encontrarPosicionValida();
        if(posicion != -1){
            personal.set(posicion)= nuevoSoldado;
            return true;
        }else {
            return false;
        }
    }
    public boolean eliminarsoladado(){

    }
}
