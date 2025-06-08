/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Randall AC
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "entrada_salas")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "EntradaSalas.findAll", query = "SELECT e FROM EntradaSalas e"),
    @javax.persistence.NamedQuery(name = "EntradaSalas.findByIdEntradaSala", query = "SELECT e FROM EntradaSalas e WHERE e.idEntradaSala = :idEntradaSala"),
    @javax.persistence.NamedQuery(name = "EntradaSalas.findByPrecioSala", query = "SELECT e FROM EntradaSalas e WHERE e.precioSala = :precioSala")})
public class EntradaSalas implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idEntradaSala")
    private Integer idEntradaSala;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "precioSala")
    private BigDecimal precioSala;
    @javax.persistence.JoinColumn(name = "idEntrada", referencedColumnName = "idEntrada")
    @javax.persistence.ManyToOne
    private Entradas idEntrada;
    @javax.persistence.JoinColumn(name = "idSala", referencedColumnName = "idSala")
    @javax.persistence.ManyToOne
    private Salas idSala;

    public EntradaSalas() {
    }

    public EntradaSalas(Integer idEntradaSala) {
        this.idEntradaSala = idEntradaSala;
    }

    public EntradaSalas(Integer idEntradaSala, BigDecimal precioSala) {
        this.idEntradaSala = idEntradaSala;
        this.precioSala = precioSala;
    }

    public Integer getIdEntradaSala() {
        return idEntradaSala;
    }

    public void setIdEntradaSala(Integer idEntradaSala) {
        this.idEntradaSala = idEntradaSala;
    }

    public BigDecimal getPrecioSala() {
        return precioSala;
    }

    public void setPrecioSala(BigDecimal precioSala) {
        this.precioSala = precioSala;
    }

    public Entradas getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Entradas idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Salas getIdSala() {
        return idSala;
    }

    public void setIdSala(Salas idSala) {
        this.idSala = idSala;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntradaSala != null ? idEntradaSala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntradaSalas)) {
            return false;
        }
        EntradaSalas other = (EntradaSalas) object;
        if ((this.idEntradaSala == null && other.idEntradaSala != null) || (this.idEntradaSala != null && !this.idEntradaSala.equals(other.idEntradaSala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.EntradaSalas[ idEntradaSala=" + idEntradaSala + " ]";
    }

}
