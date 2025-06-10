package com.mycompany.bd_museomahn_proyecto2;

import controladores.MuseosJpaController;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import persistencia.Colecciones;
import persistencia.Comisionestarjetas;
import persistencia.Especies;
import persistencia.Museos;
import persistencia.Precios;
import persistencia.Salas;
import persistencia.Tematicas;

/**
 * FXML Controller class
 *
 * @author randa
 */
public class MantenimientoController implements Initializable {

    @FXML
    private Pane ventanaPrincipal;
    @FXML
    private Button btnFiltrar;
    @FXML
    private ComboBox<String> cbFiltroElementos;
    @FXML
    private TextField txtFiltroBusqueda;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;
    @FXML
    private DatePicker datePickerSelect;
    @FXML
    private TextField txtCampo3;
    @FXML
    private TextField txtCampo5;
    @FXML
    private TextField txtCampo4;
    @FXML
    private TextField txtCampo2;
    @FXML
    private TextField txtCampo1;
    @FXML
    private TextField txtCampo6;
    @FXML
    private TextField txtCampo7;
    @FXML
    private DatePicker datePickerSelectAuxiliar;
    @FXML
    private ComboBox<Precios> cbTipoTarjeta;
    @FXML
    private TextArea txtAreaDescripcion;
    @FXML
    private Accordion actualizarVista;
    @FXML
    private TitledPane titledPaneMantenimientos;
    @FXML
    private TitledPane titledPaneEntradas;
    @FXML
    private TitledPane titledPaneValoraciones;
    @FXML
    private TitledPane titledPaneReportes;
    @FXML
    private TextField txtNumeroDeSala;
    @FXML
    private ComboBox<String> cbEntidadesMantenimiento;
    @FXML
    private TabPane tvVerElementosClases;
    private Tab Museos;
    @FXML
    private TableView<Museos> tvMuseos;
    @FXML
    private TableView<Salas> tvSalas;
    @FXML
    private TableView<Colecciones> tvColecciones;
    @FXML
    private TableView<Especies> tvEspecies;
    @FXML
    private TableView<Tematicas> tvTematicas;
    @FXML
    private TableView<Precios> tvPrecios;
    @FXML
    private TableView<Comisionestarjetas> tvComisionesTarjeta;
    private boolean modoEdicion = false;
    private Museos museoSeleccionado = null;

    //INSTANCIAS GLOBALES
    private MuseosJpaController museosJpa = new MuseosJpaController();
    @FXML
    private ComboBox<String> cbTipoMuseo;
    @FXML
    private Button btnSalir;
    @FXML
    private Tab tapMuseos;
    @FXML
    private Tab tapSalas;
    @FXML
    private Tab tapColecciones;
    @FXML
    private Tab tapEspecies;
    @FXML
    private Tab tapTematicas;
    @FXML
    private Tab tapPrecios;
    @FXML
    private Tab tapComisionesTarjeta;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEntidadesMantenimiento.setItems(FXCollections.observableArrayList(
                "MUSEOS", "Salas", "Colecciones", "Especies", "Temáticas", "Precios", "Comisiones de tarjetas"
        ));

        cbEntidadesMantenimiento.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarCamposPorEntidad(newVal);
                limpiarCampos();
            }
        });

        tvVerElementosClases.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tapMuseos) { // asegurarse de que se use el fx:id real del Tab
                cargarDatosMuseos();
            }
        });

        cbEntidadesMantenimiento.getSelectionModel().select("MUSEOS");
        tvVerElementosClases.getSelectionModel().select(Museos); // usa el Tab
        cargarDatosMuseos(); // fuerza la carga al iniciar
    }

    private String getEntidadSeleccionada() {
        Tab tabSeleccionado = tvVerElementosClases.getSelectionModel().getSelectedItem();
        String entidad = tabSeleccionado.getText();
        System.out.println("Entidad seleccionada: " + entidad);
        return entidad;
    }

    @FXML
    private void btnFiltrarOnAction(ActionEvent event) {
    }

    @FXML
    private void btnEditarOnAction(ActionEvent event) {
        if (getEntidadSeleccionada().equals("MUSEOS")) {
            museoSeleccionado = tvMuseos.getSelectionModel().getSelectedItem();
            if (museoSeleccionado != null) {
                txtCampo1.setText(museoSeleccionado.getNombre());
                txtCampo2.setText(museoSeleccionado.getTipo());
                txtCampo3.setText(museoSeleccionado.getUbicacion());

                Date fecha = museoSeleccionado.getFechaFundacion();
                if (fecha != null) {
                    Instant instant = fecha.toInstant();
                    LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                    datePickerSelect.setValue(localDate);
                } else {
                    datePickerSelect.setValue(null);
                }

                txtCampo4.setText(museoSeleccionado.getDirector());
                txtCampo5.setText(museoSeleccionado.getSitioWeb());

                modoEdicion = true;
            } else {
                mostrarError("Debe seleccionar un museo para editar.");
            }
        }
    }

    @FXML
    private void btnEliminarOnAction(ActionEvent event) {
        if (getEntidadSeleccionada().equals("MUSEOS")) {
            Museos seleccionado = tvMuseos.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                try {
                    museosJpa.delete(seleccionado);
                    cargarDatosMuseos();
                    mostrarAlerta("Museo eliminado.");
                    limpiarCampos();
                } catch (Exception e) {
                    mostrarError("Error al eliminar museo: " + e.getMessage());
                }
            } else {
                mostrarError("Seleccione un museo para eliminar.");
            }
        }
    }

    @FXML
    private void btnInsertarOnAction(ActionEvent event) {
        String entidad = cbEntidadesMantenimiento.getSelectionModel().getSelectedItem();

        switch (entidad) {
            case "MUSEOS":
                insertarMuseo();
                break;
            case "Salas":
                // insertarSala();
                break;
            case "Colecciones":
                // insertarColeccion();
                break;
            // ...
            default:
                System.out.println("Entidad no reconocida: " + entidad);
        }
    }

    private void insertarMuseo() {
        if (getEntidadSeleccionada().equals("MUSEOS")) {
            Museos nuevo = new Museos();
            nuevo.setNombre(txtCampo1.getText());
            nuevo.setTipo(cbTipoMuseo.getValue());
            nuevo.setUbicacion(txtCampo3.getText());

            LocalDate localDate = datePickerSelect.getValue();
            if (localDate != null) {
                Date fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                nuevo.setFechaFundacion(fecha);
            } else {
                nuevo.setFechaFundacion(null);
            }

            nuevo.setDirector(txtCampo4.getText());
            nuevo.setSitioWeb(txtCampo5.getText());

            try {
                museosJpa.create(nuevo);
                cargarDatosMuseos();
                mostrarAlerta("Museo insertado correctamente.");
                limpiarCampos();
            } catch (Exception e) {
                mostrarError("Error al insertar museo: " + e.getMessage());
            }
        }
    }

    @FXML
    private void btnGuardarOnAction(ActionEvent event) {
        if (modoEdicion && museoSeleccionado != null) {
            museoSeleccionado.setNombre(txtCampo1.getText());
            museoSeleccionado.setTipo(txtCampo2.getText());
            museoSeleccionado.setUbicacion(txtCampo3.getText());

            LocalDate localDate = datePickerSelect.getValue();
            if (localDate != null) {
                Date fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                museoSeleccionado.setFechaFundacion(fecha);
            } else {
                museoSeleccionado.setFechaFundacion(null);
            }

            museoSeleccionado.setDirector(txtCampo4.getText());
            museoSeleccionado.setSitioWeb(txtCampo5.getText());

            try {
                museosJpa.edit(museoSeleccionado);
                cargarDatosMuseos();
                mostrarAlerta("Cambios guardados correctamente.");
            } catch (Exception e) {
                mostrarError("Error al guardar cambios: " + e.getMessage());
            }

            modoEdicion = false;
            museoSeleccionado = null;
            limpiarCampos();
        }
    }

    @FXML
    private void btnCancelarOnAction(ActionEvent event) {
        limpiarCampos();
        modoEdicion = false;
        museoSeleccionado = null;
    }

    public void cargarDatosMuseos() {
        // Limpiar columnas anteriores por si ya se configuraron
        tvMuseos.getColumns().clear();

        TableColumn<Museos, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Museos, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Museos, String> colUbicacion = new TableColumn<>("Ubicación");
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        TableColumn<Museos, String> colFechaFundacion = new TableColumn<>("Fecha Fundación");
        colFechaFundacion.setCellValueFactory(cellData -> {
            Date fecha = cellData.getValue().getFechaFundacion();
            String fechaStr = (fecha != null) ? new SimpleDateFormat("dd/MM/yyyy").format(fecha) : "";
            return new SimpleStringProperty(fechaStr);
        });

        TableColumn<Museos, String> colDirector = new TableColumn<>("Director");
        colDirector.setCellValueFactory(new PropertyValueFactory<>("director"));

        TableColumn<Museos, String> colSitioWeb = new TableColumn<>("Sitio Web");
        colSitioWeb.setCellValueFactory(new PropertyValueFactory<>("sitioWeb"));

        tvMuseos.getColumns().addAll(colNombre, colTipo, colUbicacion, colFechaFundacion, colDirector, colSitioWeb);

        Collection<Museos> listaMuseos = museosJpa.findMuseosEntities();
        tvMuseos.setItems(FXCollections.observableArrayList(listaMuseos));
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtCampo1.clear();
        txtCampo2.clear();
        txtCampo3.clear();
        txtCampo4.clear();
        txtCampo5.clear();
        txtCampo6.clear();
        txtCampo7.clear();
        datePickerSelect.setValue(null);
        datePickerSelectAuxiliar.setValue(null);
        cbTipoTarjeta.setValue(null);
        txtAreaDescripcion.clear();
        cbTipoMuseo.setValue(null);
    }

    private void actualizarCamposPorEntidad(String entidad) {
        ocultarTodosLosCampos();

        switch (entidad) {
            case "MUSEOS":
                mostrarCamposMuseos();
                tvVerElementosClases.getSelectionModel().select(tapMuseos);
                break;

            case "Salas":
                mostrarCamposSalas();
                tvVerElementosClases.getSelectionModel().select(tapSalas);
                break;

            case "Colecciones":
                mostrarCamposColecciones();
                tvVerElementosClases.getSelectionModel().select(tapColecciones);
                break;

            case "Especies":
                mostrarCamposEspecies();
                tvVerElementosClases.getSelectionModel().select(tapEspecies);
                break;

            case "Temáticas":
                mostrarCamposTematicas();
                tvVerElementosClases.getSelectionModel().select(tapTematicas);
                break;

            case "Precios":
                mostrarCamposPrecios();
                tvVerElementosClases.getSelectionModel().select(tapPrecios);
                break;

            case "Comisiones de tarjetas":
                mostrarCamposComisiones();
                tvVerElementosClases.getSelectionModel().select(tapComisionesTarjeta);
                break;
            default:
                // Si es una entidad no conocida, deja todo oculto.
                break;
        }
    }

// ---------- Métodos para mostrar campos específicos según entidad -----------
    private void mostrarCamposMuseos() {
        txtCampo1.setVisible(true); // Nombre
        cbTipoMuseo.setVisible(true); // Mostrar combo box para tipo
        cbTipoMuseo.setPromptText("Tipo de museo"); //Tipo de Museo
        cbTipoMuseo.setItems(FXCollections.observableArrayList("Arte", "Historia", "Musical", "Militar"));
        txtCampo3.setVisible(true); // Ubicación
        txtCampo4.setVisible(true); // Director
        txtCampo5.setVisible(true); // Sitio web
        datePickerSelect.setVisible(true); // Fecha fundación

        txtCampo1.setPromptText("Nombre del museo");
        cbTipoMuseo.setPromptText("Tipo de Museo");
        txtCampo3.setPromptText("Ubicación");
        txtCampo4.setPromptText("Director");
        txtCampo5.setPromptText("Sitio web");
        datePickerSelect.setPromptText("Fecha de fundación");
        cargarDatosMuseos();
    }

    private void mostrarCamposSalas() {
        txtCampo1.setVisible(true); // Nombre
        txtCampo2.setVisible(true); // Número
        txtCampo3.setVisible(true); // Tipo
        txtCampo1.setPromptText("Nombre de la sala");
        txtCampo2.setPromptText("Número de sala");
        txtCampo3.setPromptText("Tipo");
    }

    private void mostrarCamposColecciones() {
        txtCampo1.setVisible(true); // Nombre
        txtCampo2.setVisible(true); // Responsable
        txtCampo3.setVisible(true); // Departamento
        txtAreaDescripcion.setVisible(true);
        txtCampo1.setPromptText("Nombre de la colección");
        txtCampo2.setPromptText("Responsable");
        txtCampo3.setPromptText("Departamento");
        txtAreaDescripcion.setPromptText("Descripción");
    }

    private void mostrarCamposEspecies() {
        txtCampo1.setVisible(true); // Nombre científico
        txtCampo2.setVisible(true); // Nombre común
        txtCampo3.setVisible(true); // Familia
        txtCampo4.setVisible(true); // Género
        txtCampo1.setPromptText("Nombre científico");
        txtCampo2.setPromptText("Nombre común");
        txtCampo3.setPromptText("Familia");
        txtCampo4.setPromptText("Género");
    }

    private void mostrarCamposTematicas() {
        txtCampo1.setVisible(true); // Título
        txtCampo2.setVisible(true); // Área
        txtAreaDescripcion.setVisible(true); // Descripción
        txtCampo1.setPromptText("Título de la temática");
        txtCampo2.setPromptText("Área");
        txtAreaDescripcion.setPromptText("Descripción");
    }

    private void mostrarCamposPrecios() {
        txtCampo1.setVisible(true); // Nombre del precio
        txtCampo2.setVisible(true); // Monto
        datePickerSelect.setVisible(true); // Fecha inicio
        datePickerSelectAuxiliar.setVisible(true); // Fecha fin
        txtCampo1.setPromptText("Tipo de precio");
        txtCampo2.setPromptText("Monto");
        datePickerSelect.setPromptText("Fecha inicio");
        datePickerSelectAuxiliar.setPromptText("Fecha fin");
    }

    private void mostrarCamposComisiones() {
        cbTipoTarjeta.setVisible(true);
        txtCampo1.setVisible(true); // Comisión
        txtCampo1.setPromptText("Porcentaje comisión");
        cbTipoTarjeta.setPromptText("Tipo de tarjeta");
    }

// ---------------- Ocultar todo por defecto ----------------
    private void ocultarTodosLosCampos() {
        // TextFields
        txtCampo1.setVisible(false);
        txtCampo2.setVisible(false);
        txtCampo3.setVisible(false);
        txtCampo4.setVisible(false);
        txtCampo5.setVisible(false);
        txtCampo6.setVisible(false);
        txtCampo7.setVisible(false);
        cbTipoMuseo.setVisible(false);

        // DatePickers
        datePickerSelect.setVisible(false);
        datePickerSelectAuxiliar.setVisible(false);

        // ComboBox
        cbTipoTarjeta.setVisible(false);

        // TextArea
        txtAreaDescripcion.setVisible(false);

        // Limpiar promptTexts
        limpiarPrompts();
    }

// ---------------- Limpiar prompts ----------------
    private void limpiarPrompts() {
        txtCampo1.setPromptText("");
        txtCampo2.setPromptText("");
        txtCampo3.setPromptText("");
        txtCampo4.setPromptText("");
        txtCampo5.setPromptText("");
        txtCampo6.setPromptText("");
        txtCampo7.setPromptText("");
        datePickerSelect.setPromptText("");
        datePickerSelectAuxiliar.setPromptText("");
        txtAreaDescripcion.setPromptText("");
        cbTipoTarjeta.setPromptText("");
        cbTipoMuseo.setPromptText("");
    }

    @FXML
    private void btnSalirOnAction(ActionEvent event) {
        System.exit(0);
    }
}
