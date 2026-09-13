package co.leo.datos;

import java.time.LocalDate;

public class Reportes {
    private LocalDate fecha;
    private int numeroBicicletas;
    private double valorIngresado;

    public Reporte() {
    }
    public LocalDate obtenerFecha() {return fecha;}
    public void modificarFecha(LocalDate fecha) {this.fecha = fecha;}
    public int obtenerNumeroBicicletas() {return numeroBicicletas;}
    public void modificarNumeroBicicletas(int numeroBicicletas) {this.numeroBicicletas = numeroBicicletas;}
    public double obtenerValorIngresado() {return valorIngresado;}
    public void modificarValorIngresado(double valorIngresado) {this.valorIngresado = valorIngresado;}
}
