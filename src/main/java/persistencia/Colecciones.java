/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Randall AC
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "colecciones")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Colecciones.findAll", query = "SELECT c FROM Colecciones c"),
    @javax.persistence.NamedQuery(name = "Colecciones.findByIdColeccion", query = "SELECT c FROM Colecciones c WHERE c.idColeccion = :idColeccion"),
    @javax.persistence.NamedQuery(name = "Colecciones.findByNombreColeccion", query = "SELECT c FROM Colecciones c WHERE c.nombreColeccion = :nombreColeccion"),
    @javax.persistence.NamedQuery(name = "Colecciones.findBySiglo", query = "SELECT c FROM Colecciones c WHERE c.siglo = :siglo")})
public class Colecciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idColeccion")
    private Integer idColeccion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "nombreColeccion")
    private String nombreColeccion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "siglo")
    private String siglo;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Lob
    @javax.persistence.Column(name = "descripcion")
    private String descripcion;
    @javax.persistence.JoinColumn(name = "idSala", referencedColumnName = "idSala")
    @javax.persistence.ManyToOne
    private Salas idSala;
    @javax.persistence.OneToMany(mappedBy = "idColeccion")
    private Collection<Especies> especiesCollection;

    public Colecciones() {
    }

    public Colecciones(Integer idColeccion) {
        this.idColeccion = idColeccion;
    }

    public Colecciones(Integer idColeccion, String nombreColeccion, String siglo, String descripcion) {
        this.idColeccion = idColeccion;
        this.nombreColeccion = nombreColeccion;
        this.siglo = siglo;
        this.descripcion = descripcion;
    }

    public Integer getIdColeccion() {
        return idColeccion;
    }

    public void setIdColeccion(Integer idColeccion) {
        this.idColeccion = idColeccion;
    }

    public String getNombreColeccion() {
        return nombreColeccion;
    }

    public void setNombreColeccion(String nombreColeccion) {
        this.nombreColeccion = nombreColeccion;
    }

    public String getSiglo() {
        return siglo;
    }

    public void setSiglo(String siglo) {
        this.siglo = siglo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Salas getIdSala() {
        return idSala;
    }

    public void setIdSala(Salas idSala) {
        this.idSala = idSala;
    }

    public Collection<Especies> getEspeciesCollection() {
        return especiesCollection;
    }

    public void setEspeciesCollection(Collection<Especies> especiesCollection) {
        this.especiesCollection = especiesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idColeccion != null ? idColeccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colecciones)) {
            return false;
        }
        Colecciones other = (Colecciones) object;
        if ((this.idColeccion == null && other.idColeccion != null) || (this.idColeccion != null && !this.idColeccion.equals(other.idColeccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Colecciones[ idColeccion=" + idColeccion + " ]";
    }

}
