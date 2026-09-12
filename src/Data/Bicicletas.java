package Data;

import java.time.LocalDateTime;


public class Bicicletas{
    private String placa;
    private String nombres;
    private String apellidos;
    private String dni;
    private String serial;
    private String color;
    private LocalDateTime fechaIngreso;

    public String obtenerPlaca() {return placa;}
    public void modificarPlaca(String placa) {this.placa = placa;}
    public String obtenerNombres() {return nombres;}
    public void modificarNombres(String nombres) {this.nombres = nombres;}
    public String obtenerApellidos() {return apellidos;}
    public void modificarApellidos(String apellidos) {this.apellidos = apellidos;}
    public String obtenerDni() {return dni;}
    public void modificarDni(String dni) {this.dni = dni;}
    public String obtenerSerial() {return serial;}
    public void modificarSerial(String serial) {this.serial = serial;}
    public String obtenerColor() {return color;}
    public void modificarColor(String color) {this.color = color;}
    public LocalDateTime obtenerFechaIngreso() {return fechaIngreso;}
    public void modificarFechaIngreso(LocalDateTime fechaIngreso) {this.fechaIngreso = fechaIngreso;}
}
