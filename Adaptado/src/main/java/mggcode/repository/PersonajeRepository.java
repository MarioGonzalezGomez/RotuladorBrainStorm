package mggcode.repository;

import jakarta.persistence.TypedQuery;
import mggcode.conexion.HibernateController;
import mggcode.entity.Personaje;

import java.sql.SQLException;
import java.util.List;

public class PersonajeRepository implements CrudRepository<Personaje, Integer> {

    @Override
    public List<Personaje> findAll() throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        TypedQuery<Personaje> query = hc.getManager().createNamedQuery("Personaje.findAll", Personaje.class);
        List<Personaje> list = query.getResultList();
        hc.close();
        return list;
    }

    @Override
    public Personaje getById(Integer ID) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        Personaje personaje = hc.getManager().find(Personaje.class, ID);
        hc.close();
        if (personaje != null)
            return personaje;
        throw new SQLException("Error PersonajeRepository no existe Personaje con ID: " + ID);
    }

    @Override
    public Personaje save(Personaje personaje) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().persist(personaje);
            hc.getTransaction().commit();
            hc.close();
            return personaje;
        } catch (Exception e) {
            throw new SQLException("Error PersonajeRepository al insertar Personaje en BD");
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Personaje update(Personaje personaje) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().merge(personaje);
            hc.getTransaction().commit();
            hc.close();
            return personaje;
        } catch (Exception e) {
            throw new SQLException("Error PersonajetRepository al actualizar Personaje con id: " + personaje.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Personaje delete(Personaje personaje) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            personaje = hc.getManager().find(Personaje.class, personaje.getId());
            hc.getManager().remove(personaje);
            hc.getTransaction().commit();
            hc.close();
            return personaje;
        } catch (Exception e) {
            throw new SQLException("Error PersonajeRepository al eliminar Personaje con id: " + personaje.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }
}
