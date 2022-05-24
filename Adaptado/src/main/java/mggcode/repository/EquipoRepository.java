package mggcode.repository;

import jakarta.persistence.TypedQuery;
import mggcode.conexion.HibernateController;
import mggcode.entity.Equipo;

import java.sql.SQLException;
import java.util.List;

public class EquipoRepository implements CrudRepository<Equipo, Integer> {

    @Override
    public List<Equipo> findAll() throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        TypedQuery<Equipo> query = hc.getManager().createNamedQuery("Equipo.findAll", Equipo.class);
        List<Equipo> list = query.getResultList();
        hc.close();
        return list;
    }

    @Override
    public Equipo getById(Integer ID) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        Equipo equipo = hc.getManager().find(Equipo.class, ID);
        hc.close();
        if (equipo != null)
            return equipo;
        throw new SQLException("Error EquipoRepository no existe Equipo con ID: " + ID);
    }

    @Override
    public Equipo save(Equipo equipo) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().persist(equipo);
            hc.getTransaction().commit();
            hc.close();
            return equipo;
        } catch (Exception e) {
            throw new SQLException("Error EquipoRepository al insertar Equipo en BD");
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Equipo update(Equipo equipo) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().merge(equipo);
            hc.getTransaction().commit();
            hc.close();
            return equipo;
        } catch (Exception e) {
            throw new SQLException("Error EquipotRepository al actualizar Equipo con id: " + equipo.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Equipo delete(Equipo equipo) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            equipo = hc.getManager().find(Equipo.class, equipo.getId());
            hc.getManager().remove(equipo);
            hc.getTransaction().commit();
            hc.close();
            return equipo;
        } catch (Exception e) {
            throw new SQLException("Error EquipoRepository al eliminar Equipo con id: " + equipo.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }
}
