package com.mycompany.bd_museomahn_proyecto2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.DatePicker;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.List;
import com.itextpdf.text.DocumentException;
import controladores.ValoracionesJpaController;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class VistaReportesController {

    private final ValoracionesJpaController valoracionesDAO = new ValoracionesJpaController();
    @FXML
    private Pane VistaReportes;
    @FXML
    private Button btnVolver;
    private DatePicker datePickerInicio;
    private DatePicker datePickerFin;
    private final ComisionesService comisionesService = new ComisionesService(MiEntityManager.getEntityManager());
    @FXML
    private Button btnGenerarReporte;
    @FXML
    private DatePicker dataPickerFechaInicio;
    @FXML
    private DatePicker dataPickerFechaFinal;
    @FXML
    private Button btnSalasMejorValoradas;
    @FXML
    private Button btnSalasPeorValoradas;

    public VistaReportesController() {
    }

    @FXML
    private void btnGenerarReporteOnAction(ActionEvent event) throws IOException, DocumentException {
        LocalDate localFechaInicio = dataPickerFechaInicio.getValue();
        LocalDate localFechaFin = dataPickerFechaFinal.getValue();

        if (localFechaInicio != null && localFechaFin != null) {
            // Convertir fechas a Date para el service
            Date fechaInicio = Date.from(localFechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fechaFin = Date.from(localFechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

            //  Obtener comisiones
            List<Object[]> comisiones = comisionesService.obtenerComisionesPorFechas(fechaInicio, fechaFin);

            //  Generar PDF con LocalDate
            CrearPDF crearPDF = new CrearPDF();
            crearPDF.generarReporteComisionesPDF(comisiones, "reporte_comisiones.pdf", localFechaInicio, localFechaFin);

        } else {
            System.out.println("Por favor, seleccione ambas fechas de inicio y fin.");
        }
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

    private void generarReporteMejoresSalas(ActionEvent event) throws IOException, DocumentException {
        List<Object[]> topSalas = valoracionesDAO.obtenerSalasOrdenadasPorValoracion(false);
        CrearPDF crearPDF = new CrearPDF();
        crearPDF.generarReporteSalasValoradasPDF(topSalas, "reporte_mejores_salas.pdf", "Top 10 Salas Mejor Valoradas");
    }

    private void generarReportePeoresSalas(ActionEvent event) throws IOException, DocumentException {
        List<Object[]> peoresSalas = valoracionesDAO.obtenerSalasOrdenadasPorValoracion(true);
        CrearPDF crearPDF = new CrearPDF();
        crearPDF.generarReporteSalasValoradasPDF(peoresSalas, "reporte_peores_salas.pdf", "Top 10 Salas Peor Valoradas");
    }

    @FXML
    private void btnSalasMejorValoradasOnAction(ActionEvent event) throws IOException, DocumentException {
        generarReporteMejoresSalas(event);
    }

    @FXML
    private void btnSalasPeorValoradasOnAction(ActionEvent event) throws IOException, DocumentException {
        generarReportePeoresSalas(event);
    }
}
