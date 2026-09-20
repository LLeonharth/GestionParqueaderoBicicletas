package co.leo.gui;

import co.leo.datos.Bicicleta;
import co.leo.datos.Reportes;
import co.leo.operaciones.GestionParqueadero;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Ventana extends JFrame {
    private final GestionParqueadero servicio = new GestionParqueadero();
    private JTextField txtPlaca;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtDni;
    private JTextField txtSerial;
    private JTextField txtColor;
    private JTextField txtPlacaSalida;
    private JTextField txtDniSalida;
    private JComboBox<String> cbMetodoPago;
    private JTextArea txtAreaConsola;
    private JButton btnRegistrarIngreso;
    private JButton btnRegistrarSalida;
    private JButton btnReporte;

    public Ventana() {
        setTitle("Gestión de Parqueadero de Bicicletas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        actualizarAreaTexto();
    }
    private void inicializarComponentes() {
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel pnlIngreso = new JPanel(new GridLayout(7, 2, 6, 6));
        pnlIngreso.setBorder(BorderFactory.createTitledBorder("1. Registrar ingreso de bicicleta"));
        pnlIngreso.add(new JLabel("Placa:"));
        txtPlaca = new JTextField();
        pnlIngreso.add(txtPlaca);

        pnlIngreso.add(new JLabel("Nombres:"));
        txtNombres = new JTextField();
        pnlIngreso.add(txtNombres);

        pnlIngreso.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        pnlIngreso.add(txtApellidos);

        pnlIngreso.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        pnlIngreso.add(txtDni);

        pnlIngreso.add(new JLabel("Serial:"));
        txtSerial = new JTextField();
        pnlIngreso.add(txtSerial);

        pnlIngreso.add(new JLabel("Color:"));
        txtColor = new JTextField();
        pnlIngreso.add(txtColor);

        btnRegistrarIngreso = new JButton("Registrar bicicleta");
        pnlIngreso.add(new JLabel());
        pnlIngreso.add(btnRegistrarIngreso);

        JPanel pnlSalida = new JPanel(new GridLayout(4, 2, 6, 6));
        pnlSalida.setBorder(BorderFactory.createTitledBorder("2. Registrar salida"));
        pnlSalida.add(new JLabel("Placa:"));
        txtPlacaSalida = new JTextField();
        pnlSalida.add(txtPlacaSalida);

        pnlSalida.add(new JLabel("DNI del dueño:"));
        txtDniSalida = new JTextField();
        pnlSalida.add(txtDniSalida);

        pnlSalida.add(new JLabel("Método de pago:"));

        cbMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia"});
        pnlSalida.add(cbMetodoPago);
        btnRegistrarSalida = new JButton("Registrar salida");
        pnlSalida.add(new JLabel());
        pnlSalida.add(btnRegistrarSalida);

        JPanel pnlReporte = new JPanel(new GridLayout(1, 2, 6, 6));
        pnlReporte.setBorder(
                BorderFactory.createTitledBorder("3. Reporte diario"));
        pnlReporte.add(new JLabel("Consultar reporte del día:"));
        btnReporte = new JButton("Generar reporte");
        pnlReporte.add(btnReporte);

        panelIzquierdo.add(pnlIngreso);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(pnlSalida);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(pnlReporte);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Estado del Parqueadero"));

        txtAreaConsola = new JTextArea();
        txtAreaConsola.setEditable(false);
        txtAreaConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));

        panelDerecho.add(new JScrollPane(txtAreaConsola), BorderLayout.CENTER);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        btnRegistrarIngreso.addActionListener(e -> registrarIngreso());
        btnRegistrarSalida.addActionListener(e -> registrarSalida());
        btnReporte.addActionListener(e -> generarReporte());
    }
    private void registrarIngreso() {
        String placa = txtPlaca.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String dni = txtDni.getText().trim();
        String serial = txtSerial.getText().trim();
        String color = txtColor.getText().trim();

        if (placa.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || serial.isEmpty() || color.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Bicicleta bicicleta = servicio.crearBicicleta(placa, nombres, apellidos, dni, serial, color);
        boolean registrado = servicio.registrarIngreso(bicicleta);
        if (registrado) {
            JOptionPane.showMessageDialog(this, "Bicicleta registrada correctamente.");
            limpiarCamposIngreso();
            actualizarAreaTexto();
        }else{
            JOptionPane.showMessageDialog(this, "No fue posible registrar la bicicleta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void registrarSalida() {
        String placa = txtPlacaSalida.getText().trim();
        String dni = txtDniSalida.getText().trim();
        String metodoPago = (String) cbMetodoPago.getSelectedItem();
        if (placa.isEmpty() || dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la placa y el DNI.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Reportes reporte = servicio.registrarSalida(placa, dni, metodoPago);
        if (reporte != null) {
            JOptionPane.showMessageDialog(this, "Salida registrada correctamente." + "\nValor pagado: $" + reporte.obtenerValorIngresado());
            txtPlacaSalida.setText("");
            txtDniSalida.setText("");
            actualizarAreaTexto();
        }else{
            JOptionPane.showMessageDialog(this, "No fue posible registrar la salida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void generarReporte() {
        Reportes reporte = servicio.generarReporte(LocalDate.now());
        JOptionPane.showMessageDialog(
                this,
                "Fecha: " + reporte.obtenerFecha()
                        + "\nBicicletas: "
                        + reporte.obtenerNumeroBicicletas()
                        + "\nValor ingresado: $"
                        + reporte.obtenerValorIngresado()
        );
    }
    private void limpiarCamposIngreso() {
        txtPlaca.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDni.setText("");
        txtSerial.setText("");
        txtColor.setText("");
    }
    private void actualizarAreaTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("       ESTADO DEL PARQUEADERO            \n");
        sb.append("=========================================\n\n");
        sb.append(" BICICLETAS ACTUALES: ")
                .append(servicio.obtenerCantidadBicicletas())
                .append(" / 20\n");
        sb.append(" CUPOS DISPONIBLES: ")
                .append(servicio.obtenerCuposDisponibles())
                .append("\n");
        sb.append("-----------------------------------------\n");
        if (servicio.obtenerBicicletas().isEmpty()) {
            sb.append("No hay bicicletas actualmente.\n");
        }else{
            for (Bicicleta bicicleta :
                    servicio.obtenerBicicletas()) {
                sb.append(" Placa: ")
                        .append(bicicleta.obtenerPlaca())
                        .append("\n");
                sb.append("  Dueño: ")
                        .append(bicicleta.obtenerNombres())
                        .append(" ")
                        .append(bicicleta.obtenerApellidos())
                        .append("\n");
                sb.append("  DNI: ")
                        .append(bicicleta.obtenerDni())
                        .append("\n");

                sb.append("  Serial: ")
                        .append(bicicleta.obtenerSerial())
                        .append("\n");
                sb.append("  Color: ")
                        .append(bicicleta.obtenerColor())
                        .append("\n");
                sb.append("-----------------------------------------\n");
            }
        }
        txtAreaConsola.setText(sb.toString());
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {System.err.println("No se pudo establecer el Look and Feel.");}
        SwingUtilities.invokeLater(() -> {
            Ventana ventana = new Ventana();
            ventana.setVisible(true);
        });
    }
}
