/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Randall AC
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "entradas")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Entradas.findAll", query = "SELECT e FROM Entradas e"),
    @javax.persistence.NamedQuery(name = "Entradas.findByIdEntrada", query = "SELECT e FROM Entradas e WHERE e.idEntrada = :idEntrada"),
    @javax.persistence.NamedQuery(name = "Entradas.findByFechaCompra", query = "SELECT e FROM Entradas e WHERE e.fechaCompra = :fechaCompra"),
    @javax.persistence.NamedQuery(name = "Entradas.findByFechaVisita", query = "SELECT e FROM Entradas e WHERE e.fechaVisita = :fechaVisita"),
    @javax.persistence.NamedQuery(name = "Entradas.findByPrecioTotal", query = "SELECT e FROM Entradas e WHERE e.precioTotal = :precioTotal"),
    @javax.persistence.NamedQuery(name = "Entradas.findByCodigoQR", query = "SELECT e FROM Entradas e WHERE e.codigoQR = :codigoQR")})
public class Entradas implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idEntrada")
    private Integer idEntrada;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "fechaCompra")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCompra;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "fechaVisita")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaVisita;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "precioTotal")
    private BigDecimal precioTotal;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "codigoQR")
    private String codigoQR;
    @javax.persistence.OneToMany(mappedBy = "idEntrada")
    private Collection<EntradaSalas> entradaSalasCollection;
    @javax.persistence.OneToMany(mappedBy = "idEntrada")
    private Collection<RegistroComisiones> registroComisionesCollection;

    public Entradas() {
    }

    public Entradas(Integer idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Entradas(Integer idEntrada, Date fechaCompra, Date fechaVisita, BigDecimal precioTotal, String codigoQR) {
        this.idEntrada = idEntrada;
        this.fechaCompra = fechaCompra;
        this.fechaVisita = fechaVisita;
        this.precioTotal = precioTotal;
        this.codigoQR = codigoQR;
    }

    public Integer getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Integer idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public Collection<EntradaSalas> getEntradaSalasCollection() {
        return entradaSalasCollection;
    }

    public void setEntradaSalasCollection(Collection<EntradaSalas> entradaSalasCollection) {
        this.entradaSalasCollection = entradaSalasCollection;
    }

    public Collection<RegistroComisiones> getRegistroComisionesCollection() {
        return registroComisionesCollection;
    }

    public void setRegistroComisionesCollection(Collection<RegistroComisiones> registroComisionesCollection) {
        this.registroComisionesCollection = registroComisionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntrada != null ? idEntrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entradas)) {
            return false;
        }
        Entradas other = (Entradas) object;
        if ((this.idEntrada == null && other.idEntrada != null) || (this.idEntrada != null && !this.idEntrada.equals(other.idEntrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Entradas[ idEntrada=" + idEntrada + " ]";
    }

}
