package mggcode.repository;

import jakarta.persistence.TypedQuery;
import mggcode.conexion.HibernateController;
import mggcode.entity.Faldon;

import java.sql.SQLException;
import java.util.List;

public class FaldonRepository implements CrudRepository<Faldon, Integer> {

    @Override
    public List<Faldon> findAll() throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        TypedQuery<Faldon> query = hc.getManager().createNamedQuery("Faldon.findAll", Faldon.class);
        List<Faldon> list = query.getResultList();
        hc.close();
        return list;
    }

    @Override
    public Faldon getById(Integer ID) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        Faldon faldon = hc.getManager().find(Faldon.class, ID);
        hc.close();
        if (faldon != null)
            return faldon;
        throw new SQLException("Error FaldonRepository no existe Faldon con ID: " + ID);
    }

    @Override
    public Faldon save(Faldon faldon) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().persist(faldon);
            hc.getTransaction().commit();
            hc.close();
            return faldon;
        } catch (Exception e) {
            throw new SQLException("Error FaldonRepository al insertar Faldon en BD");
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Faldon update(Faldon faldon) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().merge(faldon);
            hc.getTransaction().commit();
            hc.close();
            return faldon;
        } catch (Exception e) {
            throw new SQLException("Error FaldontRepository al actualizar Faldon con id: " + faldon.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Faldon delete(Faldon faldon) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            faldon = hc.getManager().find(Faldon.class, faldon.getId());
            hc.getManager().remove(faldon);
            hc.getTransaction().commit();
            hc.close();
            return faldon;
        } catch (Exception e) {
            throw new SQLException("Error FaldonRepository al eliminar Faldon con id: " + faldon.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }
}
