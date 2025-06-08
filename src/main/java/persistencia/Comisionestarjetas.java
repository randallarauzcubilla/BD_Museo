/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 *
 * @author Randall AC
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "comisionestarjetas")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Comisionestarjetas.findAll", query = "SELECT c FROM Comisionestarjetas c"),
    @javax.persistence.NamedQuery(name = "Comisionestarjetas.findByIdComision", query = "SELECT c FROM Comisionestarjetas c WHERE c.idComision = :idComision"),
    @javax.persistence.NamedQuery(name = "Comisionestarjetas.findByTipoTarjeta", query = "SELECT c FROM Comisionestarjetas c WHERE c.tipoTarjeta = :tipoTarjeta"),
    @javax.persistence.NamedQuery(name = "Comisionestarjetas.findByPorcentajeComision", query = "SELECT c FROM Comisionestarjetas c WHERE c.porcentajeComision = :porcentajeComision")})
public class Comisionestarjetas implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idComision")
    private Integer idComision;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "tipoTarjeta")
    private String tipoTarjeta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "porcentajeComision")
    private BigDecimal porcentajeComision;
    @javax.persistence.OneToMany(mappedBy = "idComision")
    private Collection<RegistroComisiones> registroComisionesCollection;

    public Comisionestarjetas() {
    }

    public Comisionestarjetas(Integer idComision) {
        this.idComision = idComision;
    }

    public Comisionestarjetas(Integer idComision, String tipoTarjeta, BigDecimal porcentajeComision) {
        this.idComision = idComision;
        this.tipoTarjeta = tipoTarjeta;
        this.porcentajeComision = porcentajeComision;
    }

    public Integer getIdComision() {
        return idComision;
    }

    public void setIdComision(Integer idComision) {
        this.idComision = idComision;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public BigDecimal getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(BigDecimal porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
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
        hash += (idComision != null ? idComision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comisionestarjetas)) {
            return false;
        }
        Comisionestarjetas other = (Comisionestarjetas) object;
        if ((this.idComision == null && other.idComision != null) || (this.idComision != null && !this.idComision.equals(other.idComision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Comisionestarjetas[ idComision=" + idComision + " ]";
    }

}
