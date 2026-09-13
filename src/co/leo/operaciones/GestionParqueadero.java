package co.leo.operaciones;

import co.leo.datos.Bicicleta;
import co.leo.datos.MetodoPago;
import co.leo.datos.Reportes;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestionParqueadero {
    private static final int CAPACIDAD = 20;
    private static final double VALOR_MINUTO = 10.0;
    private Bicicleta[] bicicletas;
    private List<MetodoPago> pagos;
    public GestionParqueadero() {
        bicicletas=new Bicicleta[CAPACIDAD];
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

        if (bicicleta == null) {
            return false;
        }

        if (obtenerCantidadBicicletas() >= CAPACIDAD) {
            return false;
        }

        if (buscarPorPlaca(bicicleta.obtenerPlaca()) != null) {
            return false;
        }

        for (int i = 0; i < bicicletas.length; i++) {

            if (bicicletas[i] == null) {
                bicicletas[i] = bicicleta;
                return true;
            }
        }

        return false;
    }

    public Bicicleta buscarPorPlaca(String placa) {

        if (placa == null) {
            return null;
        }

        for (Bicicleta bicicleta : bicicletas) {

            if (bicicleta != null &&
                    bicicleta.obtenerPlaca().equalsIgnoreCase(placa)) {

                return bicicleta;
            }
        }

        return null;
    }

    public boolean verificarDuenio(Bicicleta bicicleta, String dni) {

        if (bicicleta == null || dni == null) {
            return false;
        }

        return bicicleta.obtenerDni().equals(dni);
    }

    public double calcularValor(Bicicleta bicicleta) {

        if (bicicleta == null ||
                bicicleta.obtenerFechaIngreso() == null) {
            return 0;
        }

        long minutos = Duration.between(
                bicicleta.obtenerFechaIngreso(),
                LocalDateTime.now()
        ).toMinutes();

        if (minutos <= 0) {
            minutos = 1;
        }

        return minutos * VALOR_MINUTO;
    }

    public Pago registrarPago(
            Bicicleta bicicleta,
            String metodoPago) {

        double valor = calcularValor(bicicleta);

        Pago pago = new Pago();

        pago.modificarValor(valor);
        pago.modificarMetodoPago(metodoPago);
        pago.modificarFechaPago(LocalDateTime.now());

        pagos.add(pago);

        return pago;
    }

    public boolean liberarCupo(String placa) {

        for (int i = 0; i < bicicletas.length; i++) {

            if (bicicletas[i] != null &&
                    bicicletas[i].obtenerPlaca()
                            .equalsIgnoreCase(placa)) {

                bicicletas[i] = null;
                return true;
            }
        }

        return false;
    }

    public Reporte generarReporte(LocalDate fecha) {

        Reporte reporte = new Reporte();

        int cantidad = 0;
        double valor = 0;

        for (Pago pago : pagos) {

            if (pago.obtenerFechaPago()
                    .toLocalDate()
                    .equals(fecha)) {

                cantidad++;
                valor += pago.obtenerValor();
            }
        }

        reporte.modificarFecha(fecha);
        reporte.modificarNumeroBicicletas(cantidad);
        reporte.modificarValorIngresado(valor);

        return reporte;
    }

    public int obtenerCantidadBicicletas() {

        int cantidad = 0;

        for (Bicicleta bicicleta : bicicletas) {

            if (bicicleta != null) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int obtenerCuposDisponibles() {
        return CAPACIDAD - obtenerCantidadBicicletas();
    }

    public Bicicleta[] obtenerBicicletas() {
        return bicicletas;
    }

    public List<MetodoPago> obtenerPagos() {
        return pagos;
    }
}
