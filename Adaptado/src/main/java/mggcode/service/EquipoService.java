package mggcode.service;

import mggcode.entity.Equipo;
import mggcode.repository.EquipoRepository;

import java.sql.SQLException;
import java.util.List;

public class EquipoService extends BaseService<Equipo, Integer, EquipoRepository> {

    public EquipoService(EquipoRepository repository) {
        super(repository);
    }

    public List<Equipo> getAllEquipos() throws SQLException {
        // Obtenemos la lista
        return this.findAll();
    }

    public Equipo getEquipoById(Integer id) throws SQLException {
        return this.getById(id);
    }

    public Equipo postEquipo(Equipo equipo) throws SQLException {
        return this.save(equipo);
    }

    public Equipo updateEquipo(Equipo equipo) throws SQLException {
        return this.update(equipo);
    }

    public Equipo deleteEquipo(Equipo equipo) throws SQLException {
        return this.delete(equipo);
    }
}
