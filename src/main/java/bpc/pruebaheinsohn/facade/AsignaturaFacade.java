package bpc.pruebaheinsohn.facade;

import bpc.pruebaheinsohn.entidades.Asignatura;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author brahyam.pineda
 */
@Stateless
public class AsignaturaFacade extends AbstractFacade<Asignatura> {
    
    @PersistenceContext(unitName = "Prueba_Heinsohn_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsignaturaFacade() {
        super(Asignatura.class);
    }
}
