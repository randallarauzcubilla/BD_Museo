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
@javax.persistence.Table(name = "imagenes_salas")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "ImagenesSalas.findAll", query = "SELECT i FROM ImagenesSalas i"),
    @javax.persistence.NamedQuery(name = "ImagenesSalas.findByIdImagen", query = "SELECT i FROM ImagenesSalas i WHERE i.idImagen = :idImagen"),
    @javax.persistence.NamedQuery(name = "ImagenesSalas.findByUrlImagen", query = "SELECT i FROM ImagenesSalas i WHERE i.urlImagen = :urlImagen"),
    @javax.persistence.NamedQuery(name = "ImagenesSalas.findByTipo", query = "SELECT i FROM ImagenesSalas i WHERE i.tipo = :tipo")})
public class ImagenesSalas implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idImagen")
    private Integer idImagen;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "urlImagen")
    private String urlImagen;
    @javax.persistence.Lob
    @javax.persistence.Column(name = "descripcion")
    private String descripcion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "tipo")
    private String tipo;
    @javax.persistence.JoinColumn(name = "idSala", referencedColumnName = "idSala")
    @javax.persistence.ManyToOne
    private Salas idSala;

    public ImagenesSalas() {
    }

    public ImagenesSalas(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public ImagenesSalas(Integer idImagen, String urlImagen, String tipo) {
        this.idImagen = idImagen;
        this.urlImagen = urlImagen;
        this.tipo = tipo;
    }

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        hash += (idImagen != null ? idImagen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImagenesSalas)) {
            return false;
        }
        ImagenesSalas other = (ImagenesSalas) object;
        if ((this.idImagen == null && other.idImagen != null) || (this.idImagen != null && !this.idImagen.equals(other.idImagen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.ImagenesSalas[ idImagen=" + idImagen + " ]";
    }

}
