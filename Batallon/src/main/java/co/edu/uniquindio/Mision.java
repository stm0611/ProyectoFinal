package co.edu.uniquindio;

import java.time.LocalDate;
import java.util.LinkedList;

    public class Mision {

        private String id;
        private LocalDate fecha;
        private LocalDate FechaInicio;
        private LocalDate FechaFin;
        private String ubicacion;
        private LinkedList<Soldado> personal;
        private Vehiculo theVehiculo;

        public Mision(String id, LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFin, String ubicacion) {
            this.id = id;
            this.fecha = fecha;
            this.FechaInicio = fechaInicio;
            this.FechaFin = fechaFin;
            this.ubicacion = ubicacion;
            this.personal = new LinkedList<>();
        }

        public boolean asignarSoldadoAMision(Soldado soldado) {
            if (soldado.getDisponibilidad()) {
                personal.add(soldado);
                soldado.setDisponibilidad(false);
                return true;
            }
            return false;
        }

        public void finalizarMision() {
            for (Soldado soldado : personal) {
                soldado.setDisponibilidad(true);
            }
        }

        public void liberarSoldados() {
            for (Soldado soldado : personal) {
                soldado.setDisponibilidad(true);
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        public String getUbicacion() {
            return ubicacion;
        }

        public void setUbicacion(String ubicacion) {
            this.ubicacion = ubicacion;
        }

        public  LinkedList<Soldado> getPersonal() {
            return personal;
        }

        public void setPersonal(LinkedList<Soldado> personal) {
            this.personal = personal;
        }

        public Vehiculo getTheVehiculo() {
            return theVehiculo;
        }

        public void setTheVehiculo(Vehiculo theVehiculo) {
            this.theVehiculo = theVehiculo;
        }
    }

