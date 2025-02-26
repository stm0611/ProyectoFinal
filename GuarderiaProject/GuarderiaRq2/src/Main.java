import javax.swing.*;

public class Main {
  public static void main(String[] args) {

    pedirInformacionEstudiante();
  }

  public static void pedirInformacionEstudiante(){

    Estudiante[] listEstudiante = new Estudiante[10];

    for (int i = 0; i < listEstudiante.length; i++){
      JOptionPane.showMessageDialog(null,"Ingrese la informacion del estudiante " + (i+1) + ":");
      String nombre = JOptionPane.showInputDialog("Ingrese el nombre del estudiante: ");
      int edad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del estudiante:"));
      String identificacion = JOptionPane.showInputDialog("Ingrese su identificacion: ");
      String genero = JOptionPane.showInputDialog("Ingrese el genero: ");
      String alergias = JOptionPane.showInputDialog("Ingrese las alergias: ");
      String nombreAcudiente = JOptionPane.showInputDialog("Ingrese nombre del acudiente: ");
      String numAcudiente = JOptionPane.showInputDialog("Ingrese numero de contacto: ");

      Estudiante newEstudiante = new Estudiante(nombre,edad, identificacion,genero,alergias,nombreAcudiente,numAcudiente);

      listEstudiante[i] = newEstudiante;
    }

  }

  public static void ejemploArreglo(){
    int n = 4;
    int[] arregloEdades = new int[n];

    for(int i = 0; i< arregloEdades.length; i++){
      arregloEdades[i] = (int) (Math.random()*10);
    }

    String mensaje = "";

    for(int i = 0; i< arregloEdades.length; i++) {
      mensaje += arregloEdades[i] + " ";
    }

    JOptionPane.showMessageDialog(null, mensaje);
  }
}