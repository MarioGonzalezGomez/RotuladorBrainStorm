package mggcode.repository;

import jakarta.persistence.TypedQuery;
import mggcode.conexion.HibernateController;
import mggcode.entity.Declaracion;

import java.sql.SQLException;
import java.util.List;

public class DeclaracionRepository implements CrudRepository<Declaracion, Integer> {

    @Override
    public List<Declaracion> findAll() throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        TypedQuery<Declaracion> query = hc.getManager().createNamedQuery("Declaracion.findAll", Declaracion.class);
        List<Declaracion> list = query.getResultList();
        hc.close();
        return list;
    }

    @Override
    public Declaracion getById(Integer ID) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        Declaracion declaracion = hc.getManager().find(Declaracion.class, ID);
        hc.close();
        if (declaracion != null)
            return declaracion;
        throw new SQLException("Error DeclaracionRepository no existe Declaracion con ID: " + ID);
    }

    @Override
    public Declaracion save(Declaracion declaracion) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().persist(declaracion);
            hc.getTransaction().commit();
            hc.close();
            return declaracion;
        } catch (Exception e) {
            throw new SQLException("Error DeclaracionRepository al insertar Declaracion en BD");
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Declaracion update(Declaracion declaracion) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().merge(declaracion);
            hc.getTransaction().commit();
            hc.close();
            return declaracion;
        } catch (Exception e) {
            throw new SQLException("Error DeclaraciontRepository al actualizar Declaracion con id: " + declaracion.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Declaracion delete(Declaracion declaracion) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            declaracion = hc.getManager().find(Declaracion.class, declaracion.getId());
            hc.getManager().remove(declaracion);
            hc.getTransaction().commit();
            hc.close();
            return declaracion;
        } catch (Exception e) {
            throw new SQLException("Error DeclaracionRepository al eliminar Declaracion con id: " + declaracion.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }
}
