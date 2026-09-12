package co.leo.datos;

import java.time.LocalDateTime;

public class MetodoPago {
    private double valor;
    private String metodoPago;
    private LocalDateTime fechaPago;

    public MetodoPago() {
    }
    public double obtenerValor() {return valor;}
    public void modificarValor(double valor) {this.valor = valor;}
    public String obtenerMetodoPago() {return metodoPago;}
    public void modificarMetodoPago(String metodoPago) {this.metodoPago = metodoPago;}
    public LocalDateTime obtenerFechaPago() {return fechaPago;}
    public void modificarFechaPago(LocalDateTime fechaPago) {this.fechaPago = fechaPago;}
}
