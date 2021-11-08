package bpc.pruebaheinsohn.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author brahyam.pineda
 */
@Entity
@Table(name = "rel_estudiante_asignatura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelEstudianteAsignatura.findAll", query = "SELECT r FROM RelEstudianteAsignatura r")
    , @NamedQuery(name = "RelEstudianteAsignatura.findByIdRelEstudianteAsignatura", query = "SELECT r FROM RelEstudianteAsignatura r WHERE r.idRelEstudianteAsignatura = :idRelEstudianteAsignatura")})
public class RelEstudianteAsignatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rel_estudiante_asignatura")
    private Integer idRelEstudianteAsignatura;
    @JoinColumn(name = "id_asignatura", referencedColumnName = "id_asignatura")
    @ManyToOne(optional = false)
    private Asignatura asignatura;
    @JoinColumn(name = "id_estudiante", referencedColumnName = "id_estudiante")
    @ManyToOne(optional = false)
    private Estudiante estudiante;

    public RelEstudianteAsignatura() {
    }

    public RelEstudianteAsignatura(Integer idRelEstudianteAsignatura) {
        this.idRelEstudianteAsignatura = idRelEstudianteAsignatura;
    }

    public Integer getIdRelEstudianteAsignatura() {
        return idRelEstudianteAsignatura;
    }

    public void setIdRelEstudianteAsignatura(Integer idRelEstudianteAsignatura) {
        this.idRelEstudianteAsignatura = idRelEstudianteAsignatura;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRelEstudianteAsignatura != null ? idRelEstudianteAsignatura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelEstudianteAsignatura)) {
            return false;
        }
        RelEstudianteAsignatura other = (RelEstudianteAsignatura) object;
        if ((this.idRelEstudianteAsignatura == null && other.idRelEstudianteAsignatura != null) || (this.idRelEstudianteAsignatura != null && !this.idRelEstudianteAsignatura.equals(other.idRelEstudianteAsignatura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RelEstudianteAsignatura[ idRelEstudianteAsignatura=" + idRelEstudianteAsignatura + " ]";
    }

}
