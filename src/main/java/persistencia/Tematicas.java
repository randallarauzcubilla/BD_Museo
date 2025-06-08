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
@javax.persistence.Table(name = "tematicas")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Tematicas.findAll", query = "SELECT t FROM Tematicas t"),
    @javax.persistence.NamedQuery(name = "Tematicas.findByIdTematica", query = "SELECT t FROM Tematicas t WHERE t.idTematica = :idTematica"),
    @javax.persistence.NamedQuery(name = "Tematicas.findByNombreDeTematica", query = "SELECT t FROM Tematicas t WHERE t.nombreDeTematica = :nombreDeTematica"),
    @javax.persistence.NamedQuery(name = "Tematicas.findByEpocaDeTematica", query = "SELECT t FROM Tematicas t WHERE t.epocaDeTematica = :epocaDeTematica")})
public class Tematicas implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idTematica")
    private Integer idTematica;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "nombreDeTematica")
    private String nombreDeTematica;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Lob
    @javax.persistence.Column(name = "caracteristicas")
    private String caracteristicas;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "epocaDeTematica")
    private String epocaDeTematica;
    @javax.persistence.JoinColumn(name = "idSala", referencedColumnName = "idSala")
    @javax.persistence.ManyToOne
    private Salas idSala;

    public Tematicas() {
    }

    public Tematicas(Integer idTematica) {
        this.idTematica = idTematica;
    }

    public Tematicas(Integer idTematica, String nombreDeTematica, String caracteristicas, String epocaDeTematica) {
        this.idTematica = idTematica;
        this.nombreDeTematica = nombreDeTematica;
        this.caracteristicas = caracteristicas;
        this.epocaDeTematica = epocaDeTematica;
    }

    public Integer getIdTematica() {
        return idTematica;
    }

    public void setIdTematica(Integer idTematica) {
        this.idTematica = idTematica;
    }

    public String getNombreDeTematica() {
        return nombreDeTematica;
    }

    public void setNombreDeTematica(String nombreDeTematica) {
        this.nombreDeTematica = nombreDeTematica;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getEpocaDeTematica() {
        return epocaDeTematica;
    }

    public void setEpocaDeTematica(String epocaDeTematica) {
        this.epocaDeTematica = epocaDeTematica;
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
        hash += (idTematica != null ? idTematica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tematicas)) {
            return false;
        }
        Tematicas other = (Tematicas) object;
        if ((this.idTematica == null && other.idTematica != null) || (this.idTematica != null && !this.idTematica.equals(other.idTematica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Tematicas[ idTematica=" + idTematica + " ]";
    }

}
