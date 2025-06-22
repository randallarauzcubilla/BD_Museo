package com.mycompany.bd_museomahn_proyecto2;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import controladores.EntradaSalasJpaController;
import controladores.EntradasJpaController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import persistencia.EntradaSalas;
import persistencia.Entradas;

/**
 * FXML Controller class
 *
 * @author randa
 */
public class ValidarEntradaController implements Initializable {

    private final EntradasJpaController entradasDAO = new EntradasJpaController();
    private final EntradaSalasJpaController entradaSalasDAO = new EntradaSalasJpaController();
    @FXML
    private Button btnVolverMantenimiento;
    @FXML
    private TableView<RegistroSala> tvVerSalasDeIngreso;
    @FXML
    private ImageView imageQR;
    @FXML
    private Button btnValidarQR;
    @FXML
    private Button btnCargarImagen;
    @FXML
    private Label lbMensajeUsuario;
    @FXML
    private Label lbConfirmaciónSalas;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaSalasDeIngreso();
        lbConfirmaciónSalas.setVisible(false);
        lbMensajeUsuario.setVisible(false);
        tvVerSalasDeIngreso.setVisible(false);
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

    @FXML
    private void btnValidarQROnAction(ActionEvent event) {
        SesionVisitante.limpiarSesion();
        if (imageQR.getImage() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Imagen no cargada");
            alerta.setHeaderText("No hay QR para validar");
            alerta.setContentText("Primero cargá una imagen con un código QR.");
            alerta.showAndWait();
            return;
        }

        String textoQR = leerCodigoQR(imageQR);
        String codigoQR = extraerCodigoQR(textoQR);
        if (codigoQR == null || codigoQR.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Código QR inválido");
            alerta.setHeaderText("No se pudo leer el código QR");
            alerta.setContentText("Asegurate de que el QR esté visible y bien escaneado.");
            alerta.showAndWait();
            return;
        }

        System.out.println("Código QR leído: " + codigoQR);

        List<Entradas> entradas = entradasDAO.findByCodigoQR(codigoQR);

        if (entradas.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Entrada no encontrada");
            alerta.setHeaderText("Código QR inválido");
            alerta.setContentText("No se encontró ninguna entrada registrada con este código.");
            alerta.showAndWait();
            return;
        }
        LocalDate fechaActual = LocalDate.now();

        List<Entradas> entradasValidas = entradas.stream()
                .filter(e -> {
                    Date fechaVisita = e.getFechaVisita();
                    LocalDate fechaVisitaLocal;
                    if (fechaVisita instanceof java.sql.Date) {
                        fechaVisitaLocal = ((java.sql.Date) fechaVisita).toLocalDate();
                    } else {
                        fechaVisitaLocal = fechaVisita.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                    }
                    return !fechaVisitaLocal.isBefore(fechaActual);
                })
                .collect(Collectors.toList());

        if (entradasValidas.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Entrada no válida");
            alerta.setHeaderText("Fecha incorrecta");
            alerta.setContentText("Ninguna entrada de esta venta es válida para hoy.");
            alerta.showAndWait();
            tvVerSalasDeIngreso.getItems().clear();
            return;
        }

        // Mostrar salas disponibles
        ObservableList<String> salasDisponibles = FXCollections.observableArrayList();
        for (Entradas entrada : entradasValidas) {
            List<EntradaSalas> salasPermitidas = entradaSalasDAO.findByIdEntrada(entrada);
            for (EntradaSalas sala : salasPermitidas) {
                salasDisponibles.add(sala.getIdSala().getNombre());
            }
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Entrada autorizada");
        alerta.setHeaderText("¡Acceso permitido!");
        alerta.setContentText("Puedes ingresar a las siguientes salas:\n" + String.join(", ", salasDisponibles));
        alerta.showAndWait();
        lbConfirmaciónSalas.setVisible(true);
        lbMensajeUsuario.setVisible(true);
        tvVerSalasDeIngreso.setVisible(true);
        List<Integer> idsSalas = entradasValidas.stream()
                .flatMap(e -> entradaSalasDAO.findByIdEntrada(e).stream())
                .map(es -> es.getIdSala().getIdSala())
                .distinct()
                .collect(Collectors.toList());
        SesionVisitante.setSalasAutorizadas(idsSalas);

        ObservableList<RegistroSala> registros = FXCollections.observableArrayList();
        for (Entradas entrada : entradasValidas) {
            registros.add(convertirARegistroSala(entrada));
        }
        tvVerSalasDeIngreso.setItems(registros);
    }

    private String leerCodigoQR(ImageView qrImagen) {
        try {
            BufferedImage bufferedImage;
            if (qrImagen.getImage() != null) {
                bufferedImage = ImageIO.read(new URL(qrImagen.getImage().getUrl()));
            } else {
                System.out.println("No hay imagen en ImageView.");
                return null;
            }

            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result resultado = new MultiFormatReader().decode(bitmap);
            return resultado.getText();
        } catch (Exception e) {
            System.out.println("Error al leer el código QR: " + e.getMessage());
            return null;
        }
    }

    @FXML
    private void btnCargarImagenOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File archivoSeleccionado = fileChooser.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            Image imagen = new Image(archivoSeleccionado.toURI().toString());
            imageQR.setImage(imagen);
            System.out.println("Imagen cargada correctamente: " + archivoSeleccionado.getName());
        } else {
            System.out.println("No se seleccionó ninguna imagen.");
        }
    }

    private String extraerCodigoQR(String textoQR) {
        Pattern pattern = Pattern.compile("(QR_Venta_[a-fA-F0-9\\-]+)");
        Matcher matcher = pattern.matcher(textoQR);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            System.out.println("No se encontró un identificador válido en el QR.");
            return null;
        }
    }

    private void configurarTablaSalasDeIngreso() {
        tvVerSalasDeIngreso.getColumns().clear();

        TableColumn<RegistroSala, Number> colId = new TableColumn<>("ID Entrada");
        colId.setCellValueFactory(cellData -> cellData.getValue().idEntradaProperty());

        TableColumn<RegistroSala, String> colFechaCompra = new TableColumn<>("Fecha Compra");
        colFechaCompra.setCellValueFactory(cellData -> cellData.getValue().fechaCompraProperty());

        TableColumn<RegistroSala, String> colFechaVisita = new TableColumn<>("Fecha Visita");
        colFechaVisita.setCellValueFactory(cellData -> cellData.getValue().fechaVisitaProperty());

        TableColumn<RegistroSala, BigDecimal> colPrecio = new TableColumn<>("Precio Total");
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioTotalProperty());

        tvVerSalasDeIngreso.getColumns().addAll(colId, colFechaCompra, colFechaVisita, colPrecio);
    }

    private RegistroSala convertirARegistroSala(Entradas entrada) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        return new RegistroSala(
                entrada.getIdEntrada(),
                df.format(entrada.getFechaCompra()),
                df.format(entrada.getFechaVisita()),
                entrada.getPrecioTotal()
        );
    }

    private void mostrarEntradaEnTabla(Entradas entrada) {
        if (entrada == null) {
            tvVerSalasDeIngreso.getItems().clear();
            return;
        }
        RegistroSala registro = convertirARegistroSala(entrada);
        ObservableList<RegistroSala> lista = FXCollections.observableArrayList(registro);
        tvVerSalasDeIngreso.setItems(lista);
    }
}
