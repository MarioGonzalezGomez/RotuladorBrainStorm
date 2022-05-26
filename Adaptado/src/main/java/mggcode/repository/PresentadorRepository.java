package mggcode.repository;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import javafx.collections.ObservableList;
import mggcode.conexion.HibernateController;
import mggcode.entity.Presentador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PresentadorRepository implements CrudRepository<Presentador, Integer> {

    @Override
    public List<Presentador> findAll() throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        TypedQuery<Presentador> query = hc.getManager().createNamedQuery("Presentador.findAll", Presentador.class);
        List<Presentador> list = query.getResultList();
        hc.close();
        return list;
    }

    @Override
    public Presentador getById(Integer ID) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        Presentador presentador = hc.getManager().find(Presentador.class, ID);
        hc.close();
        if (presentador != null)
            return presentador;
        throw new SQLException("Error PresentadorRepository no existe presentador con ID: " + ID);
    }

    @Override
    public Presentador save(Presentador presentador) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().persist(presentador);
            hc.getTransaction().commit();
            hc.close();
            return presentador;
        } catch (Exception e) {
            throw new SQLException("Error PresentadorRepository al insertar presentador en BD");
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Presentador update(Presentador presentador) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            hc.getManager().merge(presentador);
            hc.getTransaction().commit();
            hc.close();
            return presentador;
        } catch (Exception e) {
            throw new SQLException("Error PresentadortRepository al actualizar presentador con id: " + presentador.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    @Override
    public Presentador delete(Presentador presentador) throws SQLException {
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        try {
            hc.getTransaction().begin();
            presentador = hc.getManager().find(Presentador.class, presentador.getId());
            hc.getManager().remove(presentador);
            hc.getTransaction().commit();
            hc.close();
            return presentador;
        } catch (Exception e) {
            throw new SQLException("Error PresentadorRepository al eliminar presentador con id: " + presentador.getId());
        } finally {
            if (hc.getTransaction().isActive()) {
                hc.getTransaction().rollback();
            }
            hc.close();
        }
    }

    public void deleteAll() throws SQLException{
        HibernateController hc = HibernateController.getInstance();
        hc.open();
        hc.getTransaction().begin();
        Query query = hc.getManager().createQuery("DELETE Presentador d");
        query.executeUpdate();
        hc.getTransaction().commit();
        hc.close();
    }

}
