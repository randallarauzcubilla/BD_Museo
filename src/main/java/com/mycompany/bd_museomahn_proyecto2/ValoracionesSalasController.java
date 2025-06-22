package com.mycompany.bd_museomahn_proyecto2;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import controladores.ImagenesSalasJpaController;
import controladores.SalasJpaController;
import controladores.ValoracionesJpaController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import persistencia.Colecciones;
import persistencia.Especies;
import persistencia.ImagenesSalas;
import persistencia.Salas;
import persistencia.Tematicas;
import persistencia.Valoraciones;

/**
 * FXML Controller class
 *
 * @author randa
 */
public class ValoracionesSalasController implements Initializable {

    private int estrellasSeleccionadas = 0;
    private final SalasJpaController salasDAO = new SalasJpaController();
    private final ValoracionesJpaController valoracionesDAO = new ValoracionesJpaController();
    private File archivoQR;
    @FXML
    private Pane vistaValoraciones;
    @FXML
    private Button btnVolver;
    @FXML
    private StackPane stackPaneQRSalas;
    @FXML
    private ImageView imagenQRSalas;
    @FXML
    private StackPane stackPaneImagenTipoSalas;
    @FXML
    private ImageView ImagenTipoSalas;
    @FXML
    private Button btnIzquierda;
    @FXML
    private Button btnDerecha;
    @FXML
    private TextArea txtDescripcionValoracion;
    @FXML
    private TextField txtNombreSala;
    @FXML
    private TextField txtTipoColeccion;
    @FXML
    private TextField txtTipo;
    @FXML
    private TextArea txtDetalle;
    @FXML
    private Label lbMensajeTitulo;
    @FXML
    private Label lbMensajeTitulo2;
    @FXML
    private Label lbMensajeTitulo6;
    @FXML
    private Label lbMensajeTitul3;
    @FXML
    private Label lbMensajeTitulo4;
    @FXML
    private Label lbMensajeTitulo5;
    @FXML
    private Button btnCargarQR;
    @FXML
    private Button btnValidarQRSala;
    @FXML
    private Label lblPromedio;
    private List<ImagenesSalas> imagenesSalaActual;
    private int indiceImagenActual = 0;
    private final ImagenesSalasJpaController imagenesSalasDAO = new ImagenesSalasJpaController();
    @FXML
    private Button btnGuardar;
    @FXML
    private ToggleButton btnEstrella1;
    @FXML
    private ToggleButton btnEstrella2;
    @FXML
    private ToggleButton btnEstrella3;
    @FXML
    private ToggleButton btnEstrella4;
    @FXML
    private ToggleButton btnEstrella5;
    @FXML
    private Button btnActivarCampo;
    @FXML
    private Label lbPromedioEstrellas;
    private List<Especies> especiesAsociadas = new ArrayList<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtDescripcionValoracion.setVisible(false);
        txtNombreSala.setEditable(false);
        txtTipoColeccion.setEditable(false);
        txtTipo.setEditable(false);
        txtDetalle.setEditable(false);
        btnEstrella1.setVisible(false);
        btnEstrella2.setVisible(false);
        btnEstrella3.setVisible(false);
        btnEstrella4.setVisible(false);
        btnEstrella5.setVisible(false);
        btnGuardar.setVisible(false);
        btnActivarCampo.setVisible(true);
        lbMensajeTitulo2.setVisible(true);
        lbPromedioEstrellas.setVisible(false);
        stackPaneImagenTipoSalas.setVisible(false);
        ImagenTipoSalas.setVisible(false);
        lbMensajeTitulo.setVisible(false);
        btnIzquierda.setVisible(false);
        btnDerecha.setVisible(false);
        btnActivarCampo.setVisible(false);
        lbMensajeTitulo2.setVisible(false);
        txtNombreSala.setVisible(false);
        txtTipoColeccion.setVisible(false);
        txtTipo.setVisible(false);
        txtDetalle.setVisible(false);
        lbMensajeTitul3.setVisible(false);
        lbMensajeTitulo4.setVisible(false);
        lbMensajeTitulo5.setVisible(false);
        lbMensajeTitulo6.setVisible(false);
    }

    @FXML
    private void btnVolverOnAction(ActionEvent event) {
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
    private void btnIzquierdaOnAction(ActionEvent event) {
        if (imagenesSalaActual != null && !imagenesSalaActual.isEmpty()) {
            indiceImagenActual = (indiceImagenActual - 1 + imagenesSalaActual.size()) % imagenesSalaActual.size();
            mostrarImagenActual();
        }
    }

    @FXML
    private void btnDerechaOnAction(ActionEvent event) {
        if (imagenesSalaActual != null && !imagenesSalaActual.isEmpty()) {
            indiceImagenActual = (indiceImagenActual + 1) % imagenesSalaActual.size();
            mostrarImagenActual();
        }
    }

    private String extraerCodigoQRSala(String textoQR) {
        Pattern pattern = Pattern.compile("(QR_Sala_\\d+)");
        Matcher matcher = pattern.matcher(textoQR);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            System.out.println("No se encontró un identificador válido de sala en el QR.");
            return null;
        }
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

    @FXML
    private void btnCargarQROnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        archivoQR = fileChooser.showOpenDialog(null);

        if (archivoQR != null) {
            Image imagen = new Image(archivoQR.toURI().toString());
            imagenQRSalas.setImage(imagen);
            System.out.println("Imagen cargada correctamente: " + archivoQR.getName());
        } else {
            System.out.println("No se seleccionó ninguna imagen.");
        }
    }

    private String leerCodigoQR() {
        if (archivoQR == null) {
            System.out.println("No hay archivo QR cargado.");
            return null;
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(archivoQR);
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result resultado = new MultiFormatReader().decode(bitmap);
            return resultado.getText();
        } catch (Exception e) {
            System.out.println("Error al leer el código QR: " + e.getMessage());
            return null;
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    private void btnValidarQRSalaOnAction(ActionEvent event) {
        String textoQR = leerCodigoQR();

        if (textoQR == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", null, "No se pudo leer el código QR.");
            return;
        }

        String codigoSala = extraerCodigoQRSala(textoQR);
        if (codigoSala == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", null, "El código QR no corresponde a una sala.");
            return;
        }

        int idSala = Integer.parseInt(codigoSala.replace("QR_Sala_", ""));

        if (!SesionVisitante.isSalaAutorizada(idSala)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Acceso denegado", null,
                    "No estás autorizado para ingresar a esta sala.");
            return;
        }

        Salas sala = salasDAO.findSalasById(idSala);
        if (sala == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", null, "No se encontró la sala con id: " + idSala);
            return;
        }
        stackPaneImagenTipoSalas.setVisible(true);
        ImagenTipoSalas.setVisible(true);
        lbMensajeTitulo.setVisible(true);
        btnIzquierda.setVisible(true);
        btnDerecha.setVisible(true);
        btnActivarCampo.setVisible(true);
        lbMensajeTitulo2.setVisible(true);
        txtNombreSala.setVisible(true);
        txtTipoColeccion.setVisible(true);
        txtTipo.setVisible(true);
        txtDetalle.setVisible(true);
        lbMensajeTitul3.setVisible(true);
        lbMensajeTitulo4.setVisible(true);
        lbMensajeTitulo5.setVisible(true);
        lbMensajeTitulo6.setVisible(true);
        cargarDatosSalaEnVista(sala);
    }

    private void mostrarImagenActual() {
        if (imagenesSalaActual != null && !imagenesSalaActual.isEmpty()
                && indiceImagenActual >= 0 && indiceImagenActual < imagenesSalaActual.size()) {
            ImagenesSalas imagen = imagenesSalaActual.get(indiceImagenActual);
            File archivoImagen = new File(imagen.getUrlImagen());

            if (archivoImagen.exists()) {
                System.out.println("Imagen encontrada en: " + archivoImagen.getAbsolutePath());
                Image img = new Image(archivoImagen.toURI().toString());
                ImagenTipoSalas.setImage(img);
            } else {
                System.out.println("La imagen no se encuentra en la ruta especificada: " + archivoImagen.getAbsolutePath());
                mostrarImagenPorDefecto();
            }
        } else {
            System.out.println("No hay imágenes asociadas a esta sala.");
            mostrarImagenPorDefecto();
        }
    }

    private void mostrarImagenPorDefecto() {
        Image imagenPorDefecto = new Image(getClass().getResource("/Imagenes/ImagNoDisponible.jpg").toExternalForm());
        ImagenTipoSalas.setImage(imagenPorDefecto);
    }

    private String obtenerReseña() {
        return txtDescripcionValoracion.getText();
    }

    @FXML
    private void btnActivarCampoOnAction(ActionEvent event) {
        txtDescripcionValoracion.setVisible(true);
        btnEstrella1.setVisible(true);
        btnEstrella2.setVisible(true);
        btnEstrella3.setVisible(true);
        btnEstrella4.setVisible(true);
        btnEstrella5.setVisible(true);
        lbPromedioEstrellas.setVisible(true);
        btnGuardar.setVisible(true);
    }

    @FXML
    private void btnEstrellaOnAction(ActionEvent event) {
        // Actualizamos el número de estrellas según el botón presionado
        if (event.getSource() == btnEstrella1) {
            estrellasSeleccionadas = 1;
        } else if (event.getSource() == btnEstrella2) {
            estrellasSeleccionadas = 2;
        } else if (event.getSource() == btnEstrella3) {
            estrellasSeleccionadas = 3;
        } else if (event.getSource() == btnEstrella4) {
            estrellasSeleccionadas = 4;
        } else if (event.getSource() == btnEstrella5) {
            estrellasSeleccionadas = 5;
        }
        // Cambiar color de las estrellas seleccionadas
        actualizarEstrellasUI();
    }

    private void actualizarEstrellasUI() {
        // Actualizar el estilo visual de los botones de estrellas
        // Cambiar color de fondo de los botones según la cantidad de estrellas seleccionadas
        btnEstrella1.setStyle("-fx-background-color: " + (1 <= estrellasSeleccionadas ? "gold" : "gray"));
        btnEstrella2.setStyle("-fx-background-color: " + (2 <= estrellasSeleccionadas ? "gold" : "gray"));
        btnEstrella3.setStyle("-fx-background-color: " + (3 <= estrellasSeleccionadas ? "gold" : "gray"));
        btnEstrella4.setStyle("-fx-background-color: " + (4 <= estrellasSeleccionadas ? "gold" : "gray"));
        btnEstrella5.setStyle("-fx-background-color: " + (5 <= estrellasSeleccionadas ? "gold" : "gray"));
    }

    @FXML
    private void btnGuardarOnAction(ActionEvent event) {
        // Verificar que se haya seleccionado al menos una estrella
        if (estrellasSeleccionadas == 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Debe seleccionar al menos una estrella", "Por favor seleccione una cantidad de estrellas.");
            return;
        }

        // Obtener la reseña escrita
        String reseña = obtenerReseña();

        // Verificar que la reseña no esté vacía
        if (reseña.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error", "Debe escribir una reseña", "Por favor, ingrese un comentario.");
            return;
        }

        // Obtener la sala asociada a través del código QR (ya lo tienes con `idSala`)
        int idSala = obtenerIdSalaDeQR();

        if (idSala == -1) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo identificar la sala", "Código QR no válido.");
            return;
        }

        Salas sala = salasDAO.findSalasById(idSala);

        if (sala == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró la sala", "Por favor, intente con otro código QR.");
            return;
        }

        // Crear el objeto de valoración
        Valoraciones nuevaValoracion = new Valoraciones();
        nuevaValoracion.setEstrellas(estrellasSeleccionadas);
        nuevaValoracion.setObservacion(reseña);
        nuevaValoracion.setIdSala(sala);

        // Guardar en la base de datos
        try {
            valoracionesDAO.create(nuevaValoracion);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Valoración guardada", "Tu valoración ha sido registrada exitosamente.");

            // Después de guardar, deshabilitar las estrellas
            deshabilitarEstrellas();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar la valoración", "Hubo un problema al intentar guardar la valoración.");
            e.printStackTrace();
        }
        txtDescripcionValoracion.setVisible(false);
        btnActivarCampo.setVisible(false);
        btnGuardar.setVisible(false);
        lbMensajeTitulo2.setVisible(false);
    }

    private int obtenerIdSalaDeQR() {
        String textoQR = leerCodigoQR();
        if (textoQR == null) {
            return -1;  // Código QR no encontrado o no válido
        }

        String codigoSala = extraerCodigoQRSala(textoQR);
        if (codigoSala == null) {
            return -1;  // El QR no corresponde a una sala válida
        }

        return Integer.parseInt(codigoSala.replace("QR_Sala_", ""));
    }

    private double calcularPromedioValoracion(Salas sala) {
        // Obtener todas las valoraciones de la sala desde la base de datos
        List<Valoraciones> valoraciones = obtenerValoracionesPorSala(sala);

        // Si no hay valoraciones, el promedio es 0
        if (valoraciones == null || valoraciones.isEmpty()) {
            return 0;
        }

        // Calcular el promedio
        double totalEstrellas = 0;
        for (Valoraciones valoracion : valoraciones) {
            totalEstrellas += valoracion.getEstrellas();
        }
        return totalEstrellas / valoraciones.size();
    }

    private List<Valoraciones> obtenerValoracionesPorSala(Salas sala) {
        return valoracionesDAO.findValoracionesBySala(sala.getIdSala()); // Usamos el controlador para obtener las valoraciones
    }

    private void mostrarPromedioValoracion(double promedio) {
        // Mostrar promedio como estrellas
        int estrellas = (int) Math.round(promedio);  // Redondeamos a la estrella más cercana

        // Actualizamos el estilo visual de las estrellas
        // Por ejemplo, si el promedio es 3.5, se mostrarán 3 estrellas doradas y la 4ta gris
        btnEstrella1.setStyle("-fx-background-color: " + (1 <= estrellas ? "gold" : "gray"));
        btnEstrella2.setStyle("-fx-background-color: " + (2 <= estrellas ? "gold" : "gray"));
        btnEstrella3.setStyle("-fx-background-color: " + (3 <= estrellas ? "gold" : "gray"));
        btnEstrella4.setStyle("-fx-background-color: " + (4 <= estrellas ? "gold" : "gray"));
        btnEstrella5.setStyle("-fx-background-color: " + (5 <= estrellas ? "gold" : "gray"));

        // Mostrar el promedio numérico
        lbPromedioEstrellas.setText(String.format("%.1f", promedio));  // Muestra el promedio con decimales
    }

    private void cargarDatosSalaEnVista(Salas sala) {
        if (sala != null) {
            txtNombreSala.setText(sala.getNombre());
            txtTipo.setText(sala.getIdMuseo().getTipo());
            // Mostrar colecciones o nombre de temática
            if (!sala.getColeccionesCollection().isEmpty()) {
                StringBuilder nombresColecciones = new StringBuilder();
                for (Colecciones c : sala.getColeccionesCollection()) {
                    nombresColecciones.append("• ").append(c.getNombreColeccion()).append("\n");
                }
                txtTipoColeccion.setText(nombresColecciones.toString());
            } else if (!sala.getTematicasCollection().isEmpty()) {
                Tematicas t = sala.getTematicasCollection().iterator().next();
                txtTipoColeccion.setText("Temática: " + t.getNombreDeTematica());
            } else {
                txtTipoColeccion.setText("Sin colección o temática");
            }
            if (!sala.getTematicasCollection().isEmpty()) {
                Tematicas t = sala.getTematicasCollection().iterator().next();
                txtDetalle.setText(t.getCaracteristicas());
            } else if (!sala.getColeccionesCollection().isEmpty()) {
                for (Colecciones c : sala.getColeccionesCollection()) {
                    if (c.getDescripcion() != null && !c.getDescripcion().trim().isEmpty()) {
                        txtDetalle.setText(c.getDescripcion().trim());
                        break;
                    }
                }
            } else {
                txtDetalle.setText("Sin detalles disponibles.");
            }
            imagenesSalaActual = imagenesSalasDAO.findBySala(sala.getIdSala());
            indiceImagenActual = 0;
            if (imagenesSalaActual != null && !imagenesSalaActual.isEmpty()) {
                mostrarImagenActual();
            } else {
                ImagenTipoSalas.setImage(null);
            }
            double promedio = calcularPromedioValoracion(sala);
            mostrarPromedioValoracion(promedio);
        }
    }

    private void deshabilitarEstrellas() {
        btnEstrella1.setDisable(true);
        btnEstrella2.setDisable(true);
        btnEstrella3.setDisable(true);
        btnEstrella4.setDisable(true);
        btnEstrella5.setDisable(true);
    }
}
