package mggcode.service;

import mggcode.entity.Faldon;
import mggcode.repository.FaldonRepository;

import java.sql.SQLException;
import java.util.List;

public class FaldonService extends BaseService<Faldon, Integer, FaldonRepository> {

    public FaldonService(FaldonRepository repository) {
        super(repository);
    }

    public List<Faldon> getAllFaldones() throws SQLException {
        // Obtenemos la lista
        return this.findAll();
    }

    public Faldon getFaldonById(Integer id) throws SQLException {
        return this.getById(id);
    }

    public Faldon postFaldon(Faldon faldon) throws SQLException {
        return this.save(faldon);
    }

    public Faldon updateFaldon(Faldon faldon) throws SQLException {
        return this.update(faldon);
    }

    public Faldon deleteFaldon(Faldon faldon) throws SQLException {
        return this.delete(faldon);
    }

}
