package com.mycompany.bd_museomahn_proyecto2;

import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import persistencia.Comisionestarjetas;

/**
 *
 * @author Randall AC
 */
public class EntradaVisual {

    private final SimpleStringProperty museo;
    private final SimpleStringProperty fecha;
    private final SimpleObjectProperty<BigDecimal> precio;
    private final SimpleStringProperty sala;
    private final SimpleObjectProperty<Comisionestarjetas> tarjeta;

    public EntradaVisual(String museo, String fecha, BigDecimal precio, String sala, Comisionestarjetas tarjeta) {
        this.museo = new SimpleStringProperty(museo);
        this.fecha = new SimpleStringProperty(fecha);
        this.precio = new SimpleObjectProperty<>(precio);
        this.sala = new SimpleStringProperty(sala);
        this.tarjeta = new SimpleObjectProperty<>(tarjeta);
    }

    public String getMuseo() {
        return museo.get();
    }

    public String getFecha() {
        return fecha.get();
    }

    public BigDecimal getPrecio() {
        return precio.get();
    }

    public String getSala() {
        return sala.get();
    }

    public Comisionestarjetas getTarjeta() {
        return tarjeta.get();
    }
}
