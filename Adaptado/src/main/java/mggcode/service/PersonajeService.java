package mggcode.service;

import mggcode.entity.Personaje;
import mggcode.repository.PersonajeRepository;

import java.sql.SQLException;
import java.util.List;

public class PersonajeService extends BaseService<Personaje, Integer, PersonajeRepository> {

    public PersonajeService(PersonajeRepository repository) {
        super(repository);
    }

    public List<Personaje> getAllPersonajes() throws SQLException {
        // Obtenemos la lista
        return this.findAll();
    }

    public Personaje getPersonajeById(Integer id) throws SQLException {
        return this.getById(id);
    }

    public Personaje postPersonaje(Personaje personaje) throws SQLException {
        return this.save(personaje);
    }

    public Personaje updatePersonaje(Personaje personaje) throws SQLException {
        return this.update(personaje);
    }

    public Personaje deletePersonaje(Personaje personaje) throws SQLException {
        return this.delete(personaje);
    }

}
