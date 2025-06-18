
package persistencia;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Randall AC
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "precios")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Precios.findAll", query = "SELECT p FROM Precios p"),
    @javax.persistence.NamedQuery(name = "Precios.findByIdPrecio", query = "SELECT p FROM Precios p WHERE p.idPrecio = :idPrecio"),
    @javax.persistence.NamedQuery(name = "Precios.findByPrecioLunesSabado", query = "SELECT p FROM Precios p WHERE p.precioLunesSabado = :precioLunesSabado"),
    @javax.persistence.NamedQuery(name = "Precios.findByPrecioDomingo", query = "SELECT p FROM Precios p WHERE p.precioDomingo = :precioDomingo")})
public class Precios implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idPrecio")
    private Integer idPrecio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "precioLunesSabado")
    private BigDecimal precioLunesSabado;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "precioDomingo")
    private BigDecimal precioDomingo;
    @javax.persistence.JoinColumn(name = "idSala", referencedColumnName = "idSala")
    @javax.persistence.ManyToOne
    private Salas idSala;

    public Precios() {
    }

    public Precios(Integer idPrecio) {
        this.idPrecio = idPrecio;
    }

    public Precios(Integer idPrecio, BigDecimal precioLunesSabado, BigDecimal precioDomingo) {
        this.idPrecio = idPrecio;
        this.precioLunesSabado = precioLunesSabado;
        this.precioDomingo = precioDomingo;
    }

    public Integer getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(Integer idPrecio) {
        this.idPrecio = idPrecio;
    }

    public BigDecimal getPrecioLunesSabado() {
        return precioLunesSabado;
    }

    public void setPrecioLunesSabado(BigDecimal precioLunesSabado) {
        this.precioLunesSabado = precioLunesSabado;
    }

    public BigDecimal getPrecioDomingo() {
        return precioDomingo;
    }

    public void setPrecioDomingo(BigDecimal precioDomingo) {
        this.precioDomingo = precioDomingo;
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
        hash += (idPrecio != null ? idPrecio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Precios)) {
            return false;
        }
        Precios other = (Precios) object;
        if ((this.idPrecio == null && other.idPrecio != null) || (this.idPrecio != null && !this.idPrecio.equals(other.idPrecio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Precios[ idPrecio=" + idPrecio + " ]";
    }

}
