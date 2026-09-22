package co.leo.operaciones;

import co.leo.datos.MetodoPago;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import co.leo.datos.Bicicleta;
import co.leo.datos.Reportes;
import co.leo.operaciones.GestionParqueadero;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class GestionParqueaderoTest {
    @Test
    public void testCrearBicicleta() {
        GestionParqueadero gestion = new GestionParqueadero();
        Bicicleta bicicleta = gestion.crearBicicleta("ABC123", "Leonardo", "Miñope", "123456", "SER001", "Roja");
        assertNotNull(bicicleta);
    }
    @Test
    public void testRegistrarIngreso() {
        GestionParqueadero gestion = new GestionParqueadero();
        Bicicleta bicicleta = gestion.crearBicicleta("ABC123", "Leonardo", "Miñope", "123456", "SER001", "Roja");
        boolean resultado = gestion.registrarIngreso(bicicleta);
        assertTrue(resultado);
        assertEquals(1, gestion.obtenerCantidadBicicletas());
    }
    @Test
    public void testRegistroIngreso() {
        GestionParqueadero gestion = new GestionParqueadero();
        Bicicleta bicicleta = gestion.crearBicicleta("ABC123", "Leonardo", "Miñope", "123456", "SER001", "Roja");
        boolean resultado = gestion.registrarIngreso(bicicleta);
        assertTrue(resultado);
        assertEquals(1, gestion.obtenerCantidadBicicletas());
    }
    @Test
    public void testVerificarDuennoCorrecto() {
        GestionParqueadero gestion = new GestionParqueadero();
        Bicicleta bicicleta = gestion.crearBicicleta("ABC123", "Leonardo", "Miñope", "123456", "SER001", "Roja");
        boolean resultado = gestion.verificarDuenno(bicicleta, "123456");
        assertTrue(resultado);
    }
    @Test
    public void testVerificarDuennoIncorrecto() {
        GestionParqueadero gestion = new GestionParqueadero();
        Bicicleta bicicleta = gestion.crearBicicleta("ABC123", "Leonardo", "Miñope", "123456", "SER001", "Roja");
        boolean resultado = gestion.verificarDuenno(bicicleta, "999999");
        assertFalse(resultado);
    }
    @Test
    public void testCalcularValor() {
        GestionParqueadero gestion = new GestionParqueadero();
        Bicicleta bicicleta = gestion.crearBicicleta("ABC123", "Leonardo", "Miñope", "123456", "SER001", "Roja"
        );
        double valor = gestion.calcularValor(bicicleta);
        assertTrue(valor >= 10);
    }
    @Test
    public void testRegistrarPago() {
        GestionParqueadero gestion = new GestionParqueadero();
        Bicicleta bicicleta = gestion.crearBicicleta("ABC123", "Leonardo", "Miñope", "123456", "SER001", "Roja");
        gestion.registrarIngreso(bicicleta);
        MetodoPago pago = gestion.registrarPago(bicicleta, "Efectivo");
        assertNotNull(pago);
        assertEquals(1, gestion.obtenerPagos().size());
    }
    @Test
    public void testLiberarCupo() {
        GestionParqueadero gestion = new GestionParqueadero();
        Bicicleta bicicleta = gestion.crearBicicleta("ABC123", "Leonardo", "Miñope", "123456", "SER001", "Roja");
        gestion.registrarIngreso(bicicleta);
        boolean resultado = gestion.liberarCupo("ABC123");
        assertTrue(resultado);
        assertEquals(0, gestion.obtenerCantidadBicicletas());
    }
    @Test
    public void testRegistrarSalida() {
        GestionParqueadero gestion = new GestionParqueadero();
        Bicicleta bicicleta = gestion.crearBicicleta("ABC123", "Leonardo", "Miñope", "123456", "SER001", "Roja");
        gestion.registrarIngreso(bicicleta);
        Reportes reporte = gestion.registrarSalida("ABC123", "123456", "Efectivo");
        assertNotNull(reporte);
        assertEquals(0, gestion.obtenerCantidadBicicletas());
        assertEquals(1, gestion.obtenerPagos().size());
    }
    @Test
    public void testCapacidadParqueadero() {
        GestionParqueadero gestion = new GestionParqueadero();
        for (int i = 1; i <= 20; i++) {
            Bicicleta bicicleta = gestion.crearBicicleta("ABC" + i, "Leonardo", "Miñope", "DNI" + i, "SER" + i, "Roja");
            assertTrue(gestion.registrarIngreso(bicicleta));
        }
        assertEquals(20, gestion.obtenerCantidadBicicletas());
        assertEquals(0, gestion.obtenerCuposDisponibles());
        Bicicleta bicicletaExtra = gestion.crearBicicleta("ABC21", "Leonardo", "Miñope", "DNI21", "SER21", "Azul");
        assertFalse(gestion.registrarIngreso(bicicletaExtra));
    }
}
