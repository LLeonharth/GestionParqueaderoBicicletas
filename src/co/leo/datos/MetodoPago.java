package co.leo.datos;

import java.time.LocalDate;

public class MetodoPago {
    private double valor;
    private String metodoPago;
    private LocalDate fechaPago;

    public MetodoPago() {
    }
    public double obtenerValor() {return valor;}
    public void modificarValor(double valor) {this.valor = valor;}
    public String obtenerMetodoPago() {return metodoPago;}
    public void modificarMetodoPago(String metodoPago) {this.metodoPago = metodoPago;}
    public LocalDate obtenerFechaPago() {return fechaPago;}
    public void modificarFechaPago(LocalDate fechaPago) {this.fechaPago = fechaPago;}
}
