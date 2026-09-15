package co.leo.gui;

import co.leo.datos.Bicicleta;
import co.leo.datos.Reportes;
import co.leo.operaciones.GestionParqueadero;
import javax.swing.*;
import java.time.LocalDate;

public class Ventana extends JFrame {
    private GestionParqueadero servicio;
    public Ventana() {
        servicio = new GestionParqueadero();
        setTitle("Gestión de Parqueadero de Bicicletas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }
    private void registrarIngreso() {
        String placa = JOptionPane.showInputDialog(this, "Ingrese la placa:");
        String nombres = JOptionPane.showInputDialog(this, "Ingrese los nombres:");
        String apellidos = JOptionPane.showInputDialog(this, "Ingrese los apellidos:");
        String dni = JOptionPane.showInputDialog(this, "Ingrese el DNI:");
        String serial = JOptionPane.showInputDialog(this, "Ingrese el serial de la bicicleta:");
        String color = JOptionPane.showInputDialog(this, "Ingrese el color:");
        Bicicleta bicicleta = servicio.crearBicicleta(placa, nombres, apellidos, dni, serial, color);
        boolean registrado = servicio.registrarIngreso(bicicleta);
        if (registrado) {
            JOptionPane.showMessageDialog(this, "Bicicleta registrada correctamente.");
        }else{
            JOptionPane.showMessageDialog(this, "No fue posible registrar la bicicleta.");
        }
    }
    private void registrarSalida() {
        String placa = JOptionPane.showInputDialog(this, "Ingrese la placa:");
        String dni = JOptionPane.showInputDialog(this, "Ingrese el DNI del dueño:");
        String metodoPago = JOptionPane.showInputDialog(this, "Ingrese el método de pago:");
        Reportes reporte = servicio.registrarSalida(placa, dni, metodoPago);
        if (reporte != null) {
            JOptionPane.showMessageDialog(this, "Salida registrada correctamente." + "\nPago realizado." + "\nValor ingresado: $" + reporte.obtenerValorIngresado());
        }else{
            JOptionPane.showMessageDialog(this, "No fue posible registrar la salida.");
        }
    }
    private void generarReporte() {
        Reportes reporte = servicio.generarReporte(LocalDate.now());
        JOptionPane.showMessageDialog(
                this,
                "Fecha: " + reporte.obtenerFecha()
                        + "\nBicicletas: " + reporte.obtenerNumeroBicicletas()
                        + "\nValor ingresado: $" + reporte.obtenerValorIngresado()
        );
    }
    private void inicializarComponentes() {
        JButton botonIngreso = new JButton("Registrar ingreso");
        JButton botonSalida = new JButton("Registrar salida");
        JButton botonReporte = new JButton("Generar reporte");
        JPanel panel = new JPanel();
        panel.add(botonIngreso);
        panel.add(botonSalida);
        panel.add(botonReporte);
        add(panel);
        botonIngreso.addActionListener(e -> registrarIngreso());
        botonSalida.addActionListener(e -> registrarSalida());
        botonReporte.addActionListener(e -> generarReporte());
    }
    public static void main(String[] args) {
        Ventana ventana = new Ventana();
        ventana.setVisible(true);
    }
}
