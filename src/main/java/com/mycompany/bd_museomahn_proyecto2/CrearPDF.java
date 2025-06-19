package com.mycompany.bd_museomahn_proyecto2;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import com.itextpdf.text.Document;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CrearPDF {

    public void generarReporteComisionesPDF(List<Object[]> comisiones, String nombreArchivo, LocalDate fechaInicio, LocalDate fechaFin) throws IOException, DocumentException {
        Document documento = new Document();
        String rutaSalida = "C:\\Users\\randa\\OneDrive\\Documents\\NetBeansProjects\\BD_MuseoMAHN_Proyecto2\\PDFs\\" + nombreArchivo;
        PdfWriter.getInstance(documento, new FileOutputStream(rutaSalida));
        documento.open();

        // Agregar título
        documento.add(new Paragraph("Reporte de Comisiones"));
        documento.add(new Paragraph("Fecha de Inicio: " + fechaInicio.toString())); // Usando fechaInicio
        documento.add(new Paragraph("Fecha de Fin: " + fechaFin.toString())); // Usando fechaFin
        documento.add(new Paragraph(""));

        // Agregar la tabla de comisiones
        PdfPTable tabla = new PdfPTable(2); // Dos columnas: Tarjeta y Total Comisión

        // Encabezado de la tabla
        tabla.addCell("Tarjeta");
        tabla.addCell("Total Comisión");

        // Iterar sobre las comisiones y agregar filas a la tabla
        for (Object[] comision : comisiones) {
            String tarjeta = (String) comision[0]; // Suponiendo que el primer campo es la tarjeta
            BigDecimal totalComision = (BigDecimal) comision[1]; // El segundo campo es la comisión total

            tabla.addCell(tarjeta);
            tabla.addCell("$" + totalComision.toString());
        }

        // Agregar la tabla al documento
        documento.add(tabla);

        // Cerrar el documento
        documento.close();
    }

    public void generarReporteSalasValoradasPDF(List<Object[]> datos, String nombreArchivo, String titulo) throws IOException, DocumentException {
        Document documento = new Document();
        String rutaSalida = "C:\\Users\\randa\\OneDrive\\Documents\\NetBeansProjects\\BD_MuseoMAHN_Proyecto2\\PDFs\\" + nombreArchivo;

        PdfWriter.getInstance(documento, new FileOutputStream(rutaSalida)); // ← esta línea faltaba
        documento.open();

        documento.add(new Paragraph(titulo));
        documento.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(2);
        tabla.addCell("Sala");
        tabla.addCell("Promedio de Estrellas");

        for (Object[] fila : datos) {
            tabla.addCell((String) fila[0]);
            tabla.addCell(String.format("%.2f", (Double) fila[1]));
        }

        documento.add(tabla); // ← y esta, si querés asegurarte de que la tabla se incluya
        documento.close();
    }
}
