package bpc.pruebaheinsohn.util;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JsfUtil {

    public static void mostrarMensajeError(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            mostrarMensajeError(msg);
        } else {
            mostrarMensajeError(defaultMsg);
        }
    }

    public static void mostrarMensajeError(List<String> messages) {
        for (String message : messages) {
            mostrarMensajeError(message);
        }
    }

    public static void mostrarMensajeError(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void mostrarMensajeInformativo(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
}
