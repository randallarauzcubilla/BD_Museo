package com.mycompany.bd_museomahn_proyecto2;

import java.math.BigDecimal;
import javafx.beans.property.*;

/**
 *
 * AC RANDALL
 */
public class RegistroSala {

    private final IntegerProperty idEntrada;
    private final StringProperty fechaCompra;
    private final StringProperty fechaVisita;
    private final ObjectProperty<BigDecimal> precioTotal;

    public RegistroSala(Integer idEntrada, String fechaCompra, String fechaVisita, BigDecimal precioTotal) {
        this.idEntrada = new SimpleIntegerProperty(idEntrada);
        this.fechaCompra = new SimpleStringProperty(fechaCompra);
        this.fechaVisita = new SimpleStringProperty(fechaVisita);
        this.precioTotal = new SimpleObjectProperty<>(precioTotal);
    }

    public int getIdEntrada() {
        return idEntrada.get();
    }

    public IntegerProperty idEntradaProperty() {
        return idEntrada;
    }

    public String getFechaCompra() {
        return fechaCompra.get();
    }

    public StringProperty fechaCompraProperty() {
        return fechaCompra;
    }

    public String getFechaVisita() {
        return fechaVisita.get();
    }

    public StringProperty fechaVisitaProperty() {
        return fechaVisita;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal.get();
    }

    public ObjectProperty<BigDecimal> precioTotalProperty() {
        return precioTotal;
    }
}
