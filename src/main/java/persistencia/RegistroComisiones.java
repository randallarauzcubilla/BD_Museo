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
@javax.persistence.Table(name = "registro_comisiones")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "RegistroComisiones.findAll", query = "SELECT r FROM RegistroComisiones r"),
    @javax.persistence.NamedQuery(name = "RegistroComisiones.findByIdRegistroComision", query = "SELECT r FROM RegistroComisiones r WHERE r.idRegistroComision = :idRegistroComision"),
    @javax.persistence.NamedQuery(name = "RegistroComisiones.findByValorCobrado", query = "SELECT r FROM RegistroComisiones r WHERE r.valorCobrado = :valorCobrado")})
public class RegistroComisiones implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idRegistroComision")
    private Integer idRegistroComision;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "valorCobrado")
    private BigDecimal valorCobrado;
    @javax.persistence.JoinColumn(name = "idComision", referencedColumnName = "idComision")
    @javax.persistence.ManyToOne
    private Comisionestarjetas idComision;
    @javax.persistence.JoinColumn(name = "idEntrada", referencedColumnName = "idEntrada")
    @javax.persistence.ManyToOne
    private Entradas idEntrada;

    public RegistroComisiones() {
    }

    public RegistroComisiones(Integer idRegistroComision) {
        this.idRegistroComision = idRegistroComision;
    }

    public RegistroComisiones(Integer idRegistroComision, BigDecimal valorCobrado) {
        this.idRegistroComision = idRegistroComision;
        this.valorCobrado = valorCobrado;
    }

    public Integer getIdRegistroComision() {
        return idRegistroComision;
    }

    public void setIdRegistroComision(Integer idRegistroComision) {
        this.idRegistroComision = idRegistroComision;
    }

    public BigDecimal getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(BigDecimal valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public Comisionestarjetas getIdComision() {
        return idComision;
    }

    public void setIdComision(Comisionestarjetas idComision) {
        this.idComision = idComision;
    }

    public Entradas getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Entradas idEntrada) {
        this.idEntrada = idEntrada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroComision != null ? idRegistroComision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroComisiones)) {
            return false;
        }
        RegistroComisiones other = (RegistroComisiones) object;
        if ((this.idRegistroComision == null && other.idRegistroComision != null) || (this.idRegistroComision != null && !this.idRegistroComision.equals(other.idRegistroComision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.RegistroComisiones[ idRegistroComision=" + idRegistroComision + " ]";
    }

}
