package bpc.pruebaheinsohn.bean;

import bpc.pruebaheinsohn.entidades.Asignatura;
import bpc.pruebaheinsohn.facade.EstudianteFacade;
import bpc.pruebaheinsohn.entidades.Estudiante;
import bpc.pruebaheinsohn.entidades.RelEstudianteAsignatura;
import bpc.pruebaheinsohn.facade.AsignaturaFacade;
import bpc.pruebaheinsohn.util.JsfUtil;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author brahyam.pineda
 */
@Named(value = "estudianteBean")
@SessionScoped
public class EstudianteBean implements Serializable {

    @EJB
    private EstudianteFacade estudianteFacade;

    @EJB
    private AsignaturaFacade asignaturaFacade;

    private List<Estudiante> lstEstudiantes;
    private Estudiante estudianteSeleccionado;
    private List<String> lstTiposDocumento;
    private List<Asignatura> lstAsignaturas;
    private List<String> lstAsignaturasSeleccionadas;

    public EstudianteBean() {
    }

    @PostConstruct
    public void init() {
        listarEstudiantes();
    }

    public void listarEstudiantes() {
        lstEstudiantes = getEstudianteFacade().findAll();
    }

    public void listarAsignaturas() {
        lstAsignaturas = getAsignaturaFacade().findAll();
    }

    public EstudianteFacade getEstudianteFacade() {
        return estudianteFacade;
    }

    public AsignaturaFacade getAsignaturaFacade() {
        return asignaturaFacade;
    }

    public List<Estudiante> getLstEstudiantes() {
        if (lstEstudiantes == null) {
            lstEstudiantes = new ArrayList<>();
        }
        return lstEstudiantes;
    }

    public void setLstEstudiantes(List<Estudiante> lstEstudiantes) {
        this.lstEstudiantes = lstEstudiantes;
    }

    public Estudiante getEstudianteSeleccionado() {
        return estudianteSeleccionado;
    }

    public void setEstudianteSeleccionado(Estudiante estudianteSeleccionado) {
        this.estudianteSeleccionado = estudianteSeleccionado;
    }

    public List<String> getLstTiposDocumento() {
        return lstTiposDocumento;
    }

    public void setLstTiposDocumento(List<String> lstTiposDocumento) {
        this.lstTiposDocumento = lstTiposDocumento;
    }

    public List<Asignatura> getLstAsignaturas() {
        return lstAsignaturas;
    }

    public void setLstAsignaturas(List<Asignatura> lstAsignaturas) {
        this.lstAsignaturas = lstAsignaturas;
    }

    public List<String> getLstAsignaturasSeleccionadas() {
        return lstAsignaturasSeleccionadas;
    }

    public void setLstAsignaturasSeleccionadas(List<String> lstAsignaturasSeleccionadas) {
        this.lstAsignaturasSeleccionadas = lstAsignaturasSeleccionadas;
    }

    /////////////////////////////////////////////////////////
    // Métodos
    /////////////////////////////////////////////////////////
    public Estudiante preparaCrear() {
        estudianteSeleccionado = new Estudiante();
        if (lstTiposDocumento == null || lstTiposDocumento.isEmpty()) {
            lstTiposDocumento = new ArrayList<>();
            lstTiposDocumento.add("CC");
            lstTiposDocumento.add("TI");
            lstTiposDocumento.add("CE");
            lstTiposDocumento.add("RC");
            lstTiposDocumento.add("PA");
        }
        listarAsignaturas();
        lstAsignaturasSeleccionadas = new ArrayList<>();

        return estudianteSeleccionado;
    }

    public void crear() {
        for (String id : lstAsignaturasSeleccionadas) {
            Asignatura asignatura = getAsignaturaFacade().find(Integer.parseInt(id));
            if (estudianteSeleccionado.getLstRelEstudianteAsignatura() == null) {
                estudianteSeleccionado.setLstRelEstudianteAsignatura(new ArrayList<RelEstudianteAsignatura>());
            }

            RelEstudianteAsignatura item = new RelEstudianteAsignatura();
            item.setEstudiante(estudianteSeleccionado);
            item.setAsignatura(asignatura);
            estudianteSeleccionado.getLstRelEstudianteAsignatura().add(item);
        }

        persist(JsfUtil.PersistAction.CREATE, "Estudiante creado correctamente");
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('wdg-crear-estudiante').hide();");
    }

    public void actualizar() {
        persist(JsfUtil.PersistAction.UPDATE, "Estudiante actualizado correctamente");
    }

    public void eliminar() {
        persist(JsfUtil.PersistAction.DELETE, "Estudiante eliminado correctamente");
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (estudianteSeleccionado != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getEstudianteFacade().edit(estudianteSeleccionado);
                } else {
                    getEstudianteFacade().remove(estudianteSeleccionado);
                }
                JsfUtil.mostrarMensajeInformativo(successMessage);
                listarEstudiantes();
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.mostrarMensajeError(msg);
                } else {
                    JsfUtil.mostrarMensajeError(ex, "Ha ocurrido un error al tratar de almacenar la información en base de datos");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.mostrarMensajeError(ex, "Ha ocurrido un error al tratar de almacenar la información en base de datos");
            }
        }
    }

    public Estudiante getEstudiante(Integer id) {
        return getEstudianteFacade().find(id);
    }

    public Asignatura getAsignatura(Integer id) {
        return getAsignaturaFacade().find(id);
    }

    @FacesConverter(forClass = Estudiante.class)
    public static class EstudianteConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstudianteBean bean = (EstudianteBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estudianteBean");
            return bean.getEstudiante(getKey(value));
        }

        Integer getKey(String value) {
            Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Estudiante) {
                Estudiante o = (Estudiante) object;
                return getStringKey(o.getIdEstudiante());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Estudiante.class.getName()});
                return null;
            }
        }
    }

}
