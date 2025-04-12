package co.edu.uniquindio;

public class Soldado {
    private String nombre;
    private String cedula;
    private int edad;
    private Especialidad especialidad;
    private Rango rango;
    private boolean disponibilidad;

    public Soldado(String nombre, String cedula, int edad, Especialidad especialidad, Rango rango, boolean disponibilidad) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.edad = edad;
        this.especialidad = especialidad;
        this.rango = rango;
        this.disponibilidad = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Rango getRango() {
        return rango;
    }

    public void setRango(Rango rango) {
        this.rango = rango;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
