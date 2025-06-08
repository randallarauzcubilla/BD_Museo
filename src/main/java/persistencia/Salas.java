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
@javax.persistence.Table(name = "salas")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Salas.findAll", query = "SELECT s FROM Salas s"),
    @javax.persistence.NamedQuery(name = "Salas.findByIdSala", query = "SELECT s FROM Salas s WHERE s.idSala = :idSala"),
    @javax.persistence.NamedQuery(name = "Salas.findByNombre", query = "SELECT s FROM Salas s WHERE s.nombre = :nombre"),
    @javax.persistence.NamedQuery(name = "Salas.findByTematica", query = "SELECT s FROM Salas s WHERE s.tematica = :tematica")})
public class Salas implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idSala")
    private Integer idSala;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "nombre")
    private String nombre;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Lob
    @javax.persistence.Column(name = "descripcion")
    private String descripcion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "tematica")
    private String tematica;
    @javax.persistence.OneToMany(mappedBy = "idSala")
    private Collection<Tematicas> tematicasCollection;
    @javax.persistence.OneToMany(mappedBy = "idSala")
    private Collection<Colecciones> coleccionesCollection;
    @javax.persistence.OneToMany(mappedBy = "idSala")
    private Collection<EntradaSalas> entradaSalasCollection;
    @javax.persistence.JoinColumn(name = "idMuseo", referencedColumnName = "idMuseo")
    @javax.persistence.ManyToOne
    private Museos idMuseo;
    @javax.persistence.OneToMany(mappedBy = "idSala")
    private Collection<ImagenesSalas> imagenesSalasCollection;
    @javax.persistence.OneToMany(mappedBy = "idSala")
    private Collection<Precios> preciosCollection;
    @javax.persistence.OneToMany(mappedBy = "idSala")
    private Collection<Valoraciones> valoracionesCollection;

    public Salas() {
    }

    public Salas(Integer idSala) {
        this.idSala = idSala;
    }

    public Salas(Integer idSala, String nombre, String descripcion, String tematica) {
        this.idSala = idSala;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tematica = tematica;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public Collection<Tematicas> getTematicasCollection() {
        return tematicasCollection;
    }

    public void setTematicasCollection(Collection<Tematicas> tematicasCollection) {
        this.tematicasCollection = tematicasCollection;
    }

    public Collection<Colecciones> getColeccionesCollection() {
        return coleccionesCollection;
    }

    public void setColeccionesCollection(Collection<Colecciones> coleccionesCollection) {
        this.coleccionesCollection = coleccionesCollection;
    }

    public Collection<EntradaSalas> getEntradaSalasCollection() {
        return entradaSalasCollection;
    }

    public void setEntradaSalasCollection(Collection<EntradaSalas> entradaSalasCollection) {
        this.entradaSalasCollection = entradaSalasCollection;
    }

    public Museos getIdMuseo() {
        return idMuseo;
    }

    public void setIdMuseo(Museos idMuseo) {
        this.idMuseo = idMuseo;
    }

    public Collection<ImagenesSalas> getImagenesSalasCollection() {
        return imagenesSalasCollection;
    }

    public void setImagenesSalasCollection(Collection<ImagenesSalas> imagenesSalasCollection) {
        this.imagenesSalasCollection = imagenesSalasCollection;
    }

    public Collection<Precios> getPreciosCollection() {
        return preciosCollection;
    }

    public void setPreciosCollection(Collection<Precios> preciosCollection) {
        this.preciosCollection = preciosCollection;
    }

    public Collection<Valoraciones> getValoracionesCollection() {
        return valoracionesCollection;
    }

    public void setValoracionesCollection(Collection<Valoraciones> valoracionesCollection) {
        this.valoracionesCollection = valoracionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSala != null ? idSala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salas)) {
            return false;
        }
        Salas other = (Salas) object;
        if ((this.idSala == null && other.idSala != null) || (this.idSala != null && !this.idSala.equals(other.idSala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Salas[ idSala=" + idSala + " ]";
    }

}
