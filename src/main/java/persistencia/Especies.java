/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package persistencia;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Randall AC
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "especies")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Especies.findAll", query = "SELECT e FROM Especies e"),
    @javax.persistence.NamedQuery(name = "Especies.findByIdEspecie", query = "SELECT e FROM Especies e WHERE e.idEspecie = :idEspecie"),
    @javax.persistence.NamedQuery(name = "Especies.findByNombreCientificoDeEspecie", query = "SELECT e FROM Especies e WHERE e.nombreCientificoDeEspecie = :nombreCientificoDeEspecie"),
    @javax.persistence.NamedQuery(name = "Especies.findByNombreComunDeEspecie", query = "SELECT e FROM Especies e WHERE e.nombreComunDeEspecie = :nombreComunDeEspecie"),
    @javax.persistence.NamedQuery(name = "Especies.findByFechaExtincion", query = "SELECT e FROM Especies e WHERE e.fechaExtincion = :fechaExtincion"),
    @javax.persistence.NamedQuery(name = "Especies.findByEpoca", query = "SELECT e FROM Especies e WHERE e.epoca = :epoca"),
    @javax.persistence.NamedQuery(name = "Especies.findByPeso", query = "SELECT e FROM Especies e WHERE e.peso = :peso"),
    @javax.persistence.NamedQuery(name = "Especies.findByTamanio", query = "SELECT e FROM Especies e WHERE e.tamanio = :tamanio")})
public class Especies implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "idEspecie")
    private Integer idEspecie;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "nombreCientificoDeEspecie")
    private String nombreCientificoDeEspecie;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "nombreComunDeEspecie")
    private String nombreComunDeEspecie;
    @javax.persistence.Column(name = "fechaExtincion")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaExtincion;
    @javax.persistence.Column(name = "epoca")
    private String epoca;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Column(name = "peso")
    private BigDecimal peso;
    @javax.persistence.Column(name = "tamanio")
    private BigDecimal tamanio;
    @javax.persistence.Lob
    @javax.persistence.Column(name = "caracteristicas")
    private String caracteristicas;
    @javax.persistence.JoinColumn(name = "idColeccion", referencedColumnName = "idColeccion")
    @javax.persistence.ManyToOne
    private Colecciones idColeccion;

    public Especies() {
    }

    public Especies(Integer idEspecie) {
        this.idEspecie = idEspecie;
    }

    public Especies(Integer idEspecie, String nombreCientificoDeEspecie, String nombreComunDeEspecie) {
        this.idEspecie = idEspecie;
        this.nombreCientificoDeEspecie = nombreCientificoDeEspecie;
        this.nombreComunDeEspecie = nombreComunDeEspecie;
    }

    public Integer getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Integer idEspecie) {
        this.idEspecie = idEspecie;
    }

    public String getNombreCientificoDeEspecie() {
        return nombreCientificoDeEspecie;
    }

    public void setNombreCientificoDeEspecie(String nombreCientificoDeEspecie) {
        this.nombreCientificoDeEspecie = nombreCientificoDeEspecie;
    }

    public String getNombreComunDeEspecie() {
        return nombreComunDeEspecie;
    }

    public void setNombreComunDeEspecie(String nombreComunDeEspecie) {
        this.nombreComunDeEspecie = nombreComunDeEspecie;
    }

    public Date getFechaExtincion() {
        return fechaExtincion;
    }

    public void setFechaExtincion(Date fechaExtincion) {
        this.fechaExtincion = fechaExtincion;
    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getTamanio() {
        return tamanio;
    }

    public void setTamanio(BigDecimal tamanio) {
        this.tamanio = tamanio;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Colecciones getIdColeccion() {
        return idColeccion;
    }

    public void setIdColeccion(Colecciones idColeccion) {
        this.idColeccion = idColeccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEspecie != null ? idEspecie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especies)) {
            return false;
        }
        Especies other = (Especies) object;
        if ((this.idEspecie == null && other.idEspecie != null) || (this.idEspecie != null && !this.idEspecie.equals(other.idEspecie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Especies[ idEspecie=" + idEspecie + " ]";
    }

}
