package mggcode.service;

import mggcode.entity.Presentador;
import mggcode.repository.PresentadorRepository;

import java.sql.SQLException;
import java.util.List;

public class PresentadorService extends BaseService<Presentador, Integer, PresentadorRepository> {

    public PresentadorService(PresentadorRepository repository) {
        super(repository);
    }

    public List<Presentador> getAllPresentadores() throws SQLException {
        // Obtenemos la lista
        return this.findAll();
    }

    public Presentador getPresentadorById(Integer id) throws SQLException {
        return this.getById(id);
    }

    public Presentador postPresentador(Presentador presentador) throws SQLException {
        return this.save(presentador);
    }

    public Presentador updatePresentador(Presentador presentador) throws SQLException {
        return this.update(presentador);
    }

    public Presentador deletePresentador(Presentador presentador) throws SQLException {
        return this.delete(presentador);
    }

}
