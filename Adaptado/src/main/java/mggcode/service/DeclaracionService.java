package mggcode.service;

import mggcode.entity.Declaracion;
import mggcode.repository.DeclaracionRepository;

import java.sql.SQLException;
import java.util.List;

public class DeclaracionService extends BaseService<Declaracion, Integer, DeclaracionRepository> {

    public DeclaracionService(DeclaracionRepository repository) {
        super(repository);
    }

    public List<Declaracion> getAllDeclaraciones() throws SQLException {
        // Obtenemos la lista
        return this.findAll();
    }

    public Declaracion getDeclaracionById(Integer id) throws SQLException {
        return this.getById(id);
    }

    public Declaracion postDeclaracion(Declaracion declaracion) throws SQLException {
        return this.save(declaracion);
    }

    public Declaracion updateDeclaracion(Declaracion declaracion) throws SQLException {
        return this.update(declaracion);
    }

    public Declaracion deleteDeclaracion(Declaracion declaracion) throws SQLException {
        return this.delete(declaracion);
    }
}
