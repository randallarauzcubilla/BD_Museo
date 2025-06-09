/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.bd_museomahn_proyecto2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

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
    private ComboBox<?> cbFiltroElementos;
    @FXML
    private TextField txtFiltroBusqueda;
    @FXML
    private TableView<?> tvVerElementosMuseo;
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
    private ComboBox<?> cbTipoTarjeta;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnFiltrarOnAction(ActionEvent event) {
    }

    @FXML
    private void btnEditarOnAction(ActionEvent event) {
    }

    @FXML
    private void btnEliminarOnAction(ActionEvent event) {
    }

    @FXML
    private void btnInsertarOnAction(ActionEvent event) {
    }

    @FXML
    private void btnGuardarOnAction(ActionEvent event) {
    }

    @FXML
    private void btnCancelarOnAction(ActionEvent event) {
    }
    
}
