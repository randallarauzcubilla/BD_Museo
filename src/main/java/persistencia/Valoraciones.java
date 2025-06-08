/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.io.Serializable;

/**
 *
 * @author Randall AC
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "valoraciones")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Valoraciones.findAll", query = "SELECT v FROM Valoraciones v"),
    @javax.persistence.NamedQuery(name = "Valoraciones.findByIdValoracion", query = "SELECT v FROM Valoraciones v WHERE v.idValoracion = :idValoracion"),
    @javax.persistence.NamedQuery(name = "Valoraciones.findByEstrellas", query = "SELECT v FROM Valoraciones v WHERE v.estrellas = :estrellas")})
public class Valoraciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idValoracion")
    private Integer idValoracion;
    @javax.persistence.Column(name = "estrellas")
    private Integer estrellas;
    @javax.persistence.Lob
    @javax.persistence.Column(name = "observacion")
    private String observacion;
    @javax.persistence.JoinColumn(name = "idSala", referencedColumnName = "idSala")
    @javax.persistence.ManyToOne
    private Salas idSala;

    public Valoraciones() {
    }

    public Valoraciones(Integer idValoracion) {
        this.idValoracion = idValoracion;
    }

    public Integer getIdValoracion() {
        return idValoracion;
    }

    public void setIdValoracion(Integer idValoracion) {
        this.idValoracion = idValoracion;
    }

    public Integer getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(Integer estrellas) {
        this.estrellas = estrellas;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
        hash += (idValoracion != null ? idValoracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoraciones)) {
            return false;
        }
        Valoraciones other = (Valoraciones) object;
        if ((this.idValoracion == null && other.idValoracion != null) || (this.idValoracion != null && !this.idValoracion.equals(other.idValoracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Valoraciones[ idValoracion=" + idValoracion + " ]";
    }

}
