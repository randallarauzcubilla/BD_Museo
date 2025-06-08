/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Randall AC
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "museos")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Museos.findAll", query = "SELECT m FROM Museos m"),
    @javax.persistence.NamedQuery(name = "Museos.findByIdMuseo", query = "SELECT m FROM Museos m WHERE m.idMuseo = :idMuseo"),
    @javax.persistence.NamedQuery(name = "Museos.findByNombre", query = "SELECT m FROM Museos m WHERE m.nombre = :nombre"),
    @javax.persistence.NamedQuery(name = "Museos.findByTipo", query = "SELECT m FROM Museos m WHERE m.tipo = :tipo"),
    @javax.persistence.NamedQuery(name = "Museos.findByUbicacion", query = "SELECT m FROM Museos m WHERE m.ubicacion = :ubicacion"),
    @javax.persistence.NamedQuery(name = "Museos.findByFechaFundacion", query = "SELECT m FROM Museos m WHERE m.fechaFundacion = :fechaFundacion"),
    @javax.persistence.NamedQuery(name = "Museos.findByDirector", query = "SELECT m FROM Museos m WHERE m.director = :director"),
    @javax.persistence.NamedQuery(name = "Museos.findBySitioWeb", query = "SELECT m FROM Museos m WHERE m.sitioWeb = :sitioWeb")})
public class Museos implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idMuseo")
    private Integer idMuseo;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "nombre")
    private String nombre;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "tipo")
    private String tipo;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ubicacion")
    private String ubicacion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "fechaFundacion")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFundacion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "director")
    private String director;
    @javax.persistence.Column(name = "sitioWeb")
    private String sitioWeb;
    @javax.persistence.OneToMany(mappedBy = "idMuseo")
    private Collection<Salas> salasCollection;

    public Museos() {
    }

    public Museos(Integer idMuseo) {
        this.idMuseo = idMuseo;
    }

    public Museos(Integer idMuseo, String nombre, String tipo, String ubicacion, Date fechaFundacion, String director) {
        this.idMuseo = idMuseo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.fechaFundacion = fechaFundacion;
        this.director = director;
    }

    public Integer getIdMuseo() {
        return idMuseo;
    }

    public void setIdMuseo(Integer idMuseo) {
        this.idMuseo = idMuseo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public Collection<Salas> getSalasCollection() {
        return salasCollection;
    }

    public void setSalasCollection(Collection<Salas> salasCollection) {
        this.salasCollection = salasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMuseo != null ? idMuseo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Museos)) {
            return false;
        }
        Museos other = (Museos) object;
        if ((this.idMuseo == null && other.idMuseo != null) || (this.idMuseo != null && !this.idMuseo.equals(other.idMuseo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Museos[ idMuseo=" + idMuseo + " ]";
    }

}
