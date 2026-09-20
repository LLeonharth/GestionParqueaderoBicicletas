package co.leo.operaciones;

import co.leo.datos.Bicicleta;
import co.leo.datos.MetodoPago;
import co.leo.datos.Reportes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestionParqueadero {
    private int capacidad;
    private double valorMinuto;
    private ArrayList<Bicicleta> bicicletas;
    private ArrayList<MetodoPago> pagos;
    public GestionParqueadero() {
        capacidad = 20;
        valorMinuto = 10;
        bicicletas=new ArrayList<>();
        pagos=new ArrayList<>();
    }
    public Bicicleta crearBicicleta(String placa, String nombres,
                                    String apellidos, String dni, String serial, String color) {
        Bicicleta bicicleta=new Bicicleta();
        bicicleta.modificarPlaca(placa);
        bicicleta.modificarNombres(nombres);
        bicicleta.modificarApellidos(apellidos);
        bicicleta.modificarDni(dni);
        bicicleta.modificarSerial(serial);
        bicicleta.modificarColor(color);
        bicicleta.modificarFechaIngreso(LocalDateTime.now());
        return bicicleta;
    }
    public boolean registrarIngreso(Bicicleta bicicleta) {
        if (bicicleta == null) {return false;}
        if (obtenerCantidadBicicletas() >= capacidad) {return false;}
        if (buscarPorPlaca(bicicleta.obtenerPlaca()) != null) {return false;}
        bicicletas.add(bicicleta);
        return true;
    }
    public Bicicleta buscarPorPlaca(String placa) {
        if (placa == null) {return null;}
        for (Bicicleta bicicleta : bicicletas) {
            if (bicicleta.obtenerPlaca().equalsIgnoreCase(placa)) {return bicicleta;}
        }
        return null;
    }
    public boolean verificarDuenno(Bicicleta bicicleta, String dni) {
        if (bicicleta == null || dni == null) {return false;}
        return bicicleta.obtenerDni().equals(dni);
    }
    public double calcularValor(Bicicleta bicicleta) {
        if (bicicleta == null || bicicleta.obtenerFechaIngreso() == null) {return 0;}
        long minutos = Duration.between(bicicleta.obtenerFechaIngreso(), LocalDateTime.now()).toMinutes();
        if (minutos <= 0) {minutos = 1;}
        return minutos * valorMinuto;
    }
    public MetodoPago registrarPago(Bicicleta bicicleta, String metodoPago) {
        double valor = calcularValor(bicicleta);
        MetodoPago pago = new MetodoPago();
        pago.modificarValor(valor);
        pago.modificarMetodoPago(metodoPago);
        pago.modificarFechaPago(LocalDate.now());
        pagos.add(pago);
        return pago;
    }
    public boolean liberarCupo(String placa) {
        for (int i = 0; i < bicicletas.size(); i++) {
            if (bicicletas.get(i).obtenerPlaca().equalsIgnoreCase(placa)) {
                bicicletas.remove(i);
                return true;
            }
        }
        return false;
    }
    public Reportes generarReporte(LocalDate fecha) {
        Reportes reporte=new Reportes();
        int cantidad = 0;
        double valor = 0;
        for (MetodoPago pago : pagos) {
            if (pago.obtenerFechaPago().equals(fecha)) {
                cantidad++;
                valor += pago.obtenerValor();
            }
        }
        reporte.modificarFecha(fecha);
        reporte.modificarNumeroBicicletas(cantidad);
        reporte.modificarValorIngresado(valor);
        return reporte;
    }
    public int obtenerCantidadBicicletas() {return bicicletas.size();}
    public int obtenerCuposDisponibles() {return capacidad - obtenerCantidadBicicletas();}
    public ArrayList<Bicicleta> obtenerBicicletas() {return bicicletas;}
    public ArrayList<MetodoPago> obtenerPagos() {
        return pagos;
    }
    public Reportes registrarSalida(String placa, String dni, String metodoPago) {
        Bicicleta bicicleta = buscarPorPlaca(placa);
        if (bicicleta == null) {
            System.out.println("Error: La bicicleta con placa " + placa + " no está en el parqueadero.");
            return null;
        }
        if (!verificarDuenno(bicicleta, dni)) {
            System.out.println("Error: El DNI no coincide con el dueño registrado.");
            return null;
        }
        MetodoPago pagoRealizado = registrarPago(bicicleta, metodoPago);
        System.out.println("Pago registrado exitosamente por un valor de: $" + pagoRealizado.obtenerValor());
        liberarCupo(placa);
        LocalDate hoy = LocalDate.now();
        return generarReporte(hoy);
    }
}
