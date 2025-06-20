package com.mycompany.bd_museomahn_proyecto2;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import controladores.ComisionestarjetasJpaController;
import controladores.EntradaSalasJpaController;
import controladores.EntradasJpaController;
import controladores.MuseosJpaController;
import controladores.PreciosJpaController;
import controladores.RegistroComisionesJpaController;
import controladores.SalasJpaController;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import persistencia.Comisionestarjetas;
import persistencia.EntradaSalas;
import persistencia.Entradas;
import persistencia.Museos;
import persistencia.Precios;
import persistencia.RegistroComisiones;
import persistencia.Salas;

/**
 * FXML Controller class
 *
 * @author randa
 */
public class EntradasMuseoController implements Initializable {

    private final EntradasJpaController entradasDAO = new EntradasJpaController();
    private final EntradaSalasJpaController entradaSalasDAO = new EntradaSalasJpaController();
    private final RegistroComisionesJpaController registroComisionesDAO = new RegistroComisionesJpaController();
    private final SalasJpaController salasDAO = new SalasJpaController();
    private final PreciosJpaController preciosJpa = new PreciosJpaController();
    @FXML
    private Pane paneVistaEntradaVenta;
    @FXML
    private Button btnVolverMantenimiento;
    @FXML
    private ComboBox<Museos> cbEscogerMuseo;
    @FXML
    private DatePicker dataPickerDiasVisita;
    @FXML
    private ComboBox<Comisionestarjetas> cbTipoTarjeta;
    @FXML
    private Button btnAgregarCompra;
    @FXML
    private TableView<EntradaVisual> tvVerRegistroCompras;
    @FXML
    private Button btnVender;
    @FXML
    private Label labelMostrarTotal;
    @FXML
    private Label labelImpuesto;
    @FXML
    private Label labelSubtotal;
    @FXML
    private ComboBox<Salas> cbEscogerSala;
    @FXML
    private TextField txtNombreVisitante;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarMuseos();
        cargarComisionesTarjetas();
        configurarTablaRegistroCompras();
        cbEscogerSala.setVisible(false);
        if (!cbTipoTarjeta.getItems().isEmpty()) {
            cbTipoTarjeta.setValue(cbTipoTarjeta.getItems().get(0));
        }
    }

    @FXML
    private void btnVolverMantenimientoOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mantenimiento.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1530, 1000);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar la vista de Mantenimiento: " + e.getMessage());
        }
    }

    private void cargarMuseos() {
        MuseosJpaController museoDAO = new MuseosJpaController();
        Collection<Museos> museos = museoDAO.findMuseosEntities();
        cbEscogerMuseo.setItems(FXCollections.observableArrayList(museos));
        cbEscogerMuseo.setConverter(new StringConverter<Museos>() {
            @Override
            public String toString(Museos museo) {
                return museo != null ? museo.getNombre() : "";
            }

            @Override
            public Museos fromString(String string) {
                return null; // no lo necesitamos
            }
        });
    }

    @FXML
    private void btnAgregarCompraOnAction(ActionEvent event) {
        Museos museo = cbEscogerMuseo.getValue();
        Salas sala = cbEscogerSala.getValue();
        SalasJpaController salaDAO = new SalasJpaController();
        sala = salaDAO.findSalaConPrecios(sala.getIdSala());

        LocalDate fecha = dataPickerDiasVisita.getValue();
        Comisionestarjetas tarjeta = cbTipoTarjeta.getValue();

        if (museo == null || sala == null || fecha == null || tarjeta == null) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("CAMPOS VACÍOS");
            alerta.setHeaderText("¡INFORMACIÓN INCOMPLETA!");
            alerta.setContentText("COMPLETA TODOS LOS ESPACIOS.");
            alerta.showAndWait();
            return;
        }

        // Validación de la fecha: solo permitir fechas igual o posteriores a hoy
        LocalDate fechaActual = LocalDate.now();
        if (fecha.isBefore(fechaActual)) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Fecha inválida");
            alerta.setHeaderText("Fecha de visita incorrecta");
            alerta.setContentText("La fecha seleccionada no puede ser anterior a hoy.");
            alerta.showAndWait();
            return;
        }

        boolean esDomingo = fecha.getDayOfWeek() == DayOfWeek.SUNDAY;
        Precios precio = preciosJpa.findPrecioPorSalaYDia(sala.getIdSala(), esDomingo);
        BigDecimal precioBase = (precio != null)
                ? (esDomingo ? precio.getPrecioDomingo() : precio.getPrecioLunesSabado())
                : BigDecimal.ZERO;

        BigDecimal comision = precioBase.multiply(tarjeta.getPorcentajeComision()).divide(new BigDecimal("100"));
        BigDecimal precioFinal = precioBase.add(comision).setScale(2, RoundingMode.HALF_UP);

        EntradaVisual fila = new EntradaVisual(
                museo.getNombre(),
                fecha.toString(),
                precioFinal,
                sala.getNombre(),
                tarjeta
        );
        tvVerRegistroCompras.getItems().add(fila);

        BigDecimal subtotal = tvVerRegistroCompras.getItems().stream()
                .map(EntradaVisual::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal iva = subtotal.multiply(new BigDecimal("0.13")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalFinal = subtotal.add(iva).setScale(2, RoundingMode.HALF_UP);
        labelSubtotal.setText("$" + subtotal);
        labelImpuesto.setText("$" + iva);
        labelMostrarTotal.setText("$" + totalFinal);
        cbEscogerMuseo.setValue(null);
        cbEscogerSala.setValue(null);
        dataPickerDiasVisita.setValue(null);
    }

    @FXML
    private void btnVenderOnAction(ActionEvent event) {
        List<EntradaVisual> lista = tvVerRegistroCompras.getItems();

        if (lista.isEmpty()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "SIN ENTRADAS", "¡COMPRA NO EXITOSA!", "NO HAY ENTRADAS PARA VENDER.");
            return;
        }

        if (cbTipoTarjeta.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Tipo de tarjeta no seleccionado", "¡Error en la venta!", "Seleccioná un tipo de tarjeta antes de vender.");
            return;
        }

        StringBuilder textoQR = new StringBuilder();
        BigDecimal subtotal = BigDecimal.ZERO;
        String codigoQRVenta = "QR_Venta_" + UUID.randomUUID().toString();

        SalasJpaController salasDAO = new SalasJpaController();
        textoQR.append("QR ID: ").append(codigoQRVenta).append("\n\n");
        for (EntradaVisual entrada : lista) {
            textoQR.append("Museo: ").append(entrada.getMuseo())
                    .append(" | Fecha: ").append(entrada.getFecha())
                    .append(" | Sala: ").append(entrada.getSala()) //  Mostrar sala en el QR
                    .append(" | Precio: ₡").append(entrada.getPrecio())
                    .append("\n");

            subtotal = subtotal.add(entrada.getPrecio());

            Entradas nuevaEntrada = new Entradas();
            nuevaEntrada.setFechaCompra(new Date());
            nuevaEntrada.setFechaVisita(java.sql.Date.valueOf(LocalDate.parse(entrada.getFecha())));
            nuevaEntrada.setPrecioTotal(entrada.getPrecio());
            nuevaEntrada.setCodigoQR(codigoQRVenta);

            entradasDAO.create(nuevaEntrada);

            Salas sala = salasDAO.findSalasByNombre(entrada.getSala());

            if (sala != null) {
                EntradaSalas nuevaEntradaSala = new EntradaSalas();
                nuevaEntradaSala.setIdEntrada(nuevaEntrada);
                nuevaEntradaSala.setIdSala(sala);
                nuevaEntradaSala.setPrecioSala(entrada.getPrecio());
                entradaSalasDAO.create(nuevaEntradaSala);
            } else {
                System.out.println("Sala no encontrada: " + entrada.getSala());
            }

            Comisionestarjetas tarjetaUsada = entrada.getTarjeta();
            if (tarjetaUsada != null) {
                RegistroComisiones nuevaComision = new RegistroComisiones();
                nuevaComision.setIdEntrada(nuevaEntrada);
                nuevaComision.setIdComision(tarjetaUsada);
                BigDecimal porcentajeComision = tarjetaUsada.getPorcentajeComision();
                nuevaComision.setValorCobrado(entrada.getPrecio().multiply(porcentajeComision).divide(new BigDecimal("100")));
                registroComisionesDAO.create(nuevaComision);
            } else {
                System.out.println("Error: No se pudo obtener la comisión para esta entrada.");
            }
        }

        BigDecimal iva = subtotal.multiply(new BigDecimal("0.13")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalFinal = subtotal.add(iva).setScale(2, RoundingMode.HALF_UP);

        textoQR.append("\nSubtotal: ₡").append(subtotal)
                .append("\nIVA (13%): ₡").append(iva)
                .append("\nTotal: ₡").append(totalFinal);

        try {
            generarCodigoQR(textoQR.toString(), codigoQRVenta);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al generar el QR.");
        }

        tvVerRegistroCompras.getItems().clear();
        labelSubtotal.setText("₡0.00");
        labelImpuesto.setText("₡0.00");
        labelMostrarTotal.setText("₡0.00");
        txtNombreVisitante.setText("");

        mostrarAlerta(Alert.AlertType.INFORMATION, "Venta realizada", "¡Tu compra fue exitosa!", "El código QR ha sido generado y guardado.");
    }

    @FXML
    private void onMuseoSeleccionado(ActionEvent event) {
        cbEscogerSala.setVisible(true);
        Museos museoSeleccionado = cbEscogerMuseo.getSelectionModel().getSelectedItem();

        if (museoSeleccionado != null) {
            SalasJpaController salaDAO = new SalasJpaController();
            List<Salas> salas = salaDAO.obtenerSalasPorMuseo(museoSeleccionado.getIdMuseo());
            for (Salas s : salas) {
                System.out.println("→ Sala: " + s.getNombre());
            }
            cbEscogerSala.setItems(FXCollections.observableArrayList(salas));

            cbEscogerSala.setConverter(new StringConverter<Salas>() {
                @Override
                public String toString(Salas sala) {
                    return sala != null ? sala.getNombre() : "";
                }

                @Override
                public Salas fromString(String string) {
                    return null;
                }
            });
        }
    }

    private void cargarComisionesTarjetas() {
        ComisionestarjetasJpaController controller = new ComisionestarjetasJpaController();
        List<Comisionestarjetas> lista = controller.findComisiones();

        cbTipoTarjeta.setItems(FXCollections.observableArrayList(lista));

        cbTipoTarjeta.setConverter(new StringConverter<Comisionestarjetas>() {
            @Override
            public String toString(Comisionestarjetas t) {
                return t != null ? t.getTipoTarjeta() : "";
            }

            @Override
            public Comisionestarjetas fromString(String string) {
                return null;
            }
        });
    }

    public void generarCodigoQR(String texto, String nombreArchivo) throws Exception {
        int ancho = 300;
        int alto = 200;
        String formato = "png";

        BitMatrix matriz = new MultiFormatWriter().encode(
                texto,
                BarcodeFormat.QR_CODE,
                ancho,
                alto
        );
        java.nio.file.Path rutaSalida = Paths.get("C:\\Users\\randa\\OneDrive\\Escritorio", nombreArchivo + "." + formato);
        MatrixToImageWriter.writeToPath(matriz, formato, (java.nio.file.Path) rutaSalida);

        System.out.println("QR guardado en: " + rutaSalida);
    }

    private void configurarTablaRegistroCompras() {
        tvVerRegistroCompras.getColumns().clear();

        TableColumn<EntradaVisual, String> colMuseo = new TableColumn<>("Museo");
        colMuseo.setCellValueFactory(new PropertyValueFactory<>("museo"));

        TableColumn<EntradaVisual, String> colSala = new TableColumn<>("Sala");
        colSala.setCellValueFactory(new PropertyValueFactory<>("sala"));

        TableColumn<EntradaVisual, String> colFecha = new TableColumn<>("Fecha Visita");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<EntradaVisual, BigDecimal> colPrecio = new TableColumn<>("Precio Final");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tvVerRegistroCompras.getColumns().addAll(colMuseo, colSala, colFecha, colPrecio);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
