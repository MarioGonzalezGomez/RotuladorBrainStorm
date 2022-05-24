package mggcode.controller;

import mggcode.entity.Personaje;
import mggcode.repository.PersonajeRepository;
import mggcode.service.PersonajeService;

import java.sql.SQLException;
import java.util.List;

public class PersonajeController {
    private static PersonajeController controller = null;

    // Mi Servicio unido al repositorio
    private final PersonajeService personajeService;

    // Implementamos nuestro Singleton para el controlador
    private PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    public static PersonajeController getInstance() {
        if (controller == null) {
            controller = new PersonajeController(new PersonajeService(new PersonajeRepository()));
        }
        return controller;
    }

    // Ejemplo de operaciones
    public List<Personaje> getAllPersonajes() {
        try {
            return personajeService.getAllPersonajes();
        } catch (SQLException e) {
            System.err.println("Error PersonajeController en getAllPersonajes: " + e.getMessage());
            return null;
        }
    }

    public Personaje getPersonajeById(Integer id) {
        try {
            return personajeService.getPersonajeById(id);
        } catch (SQLException e) {
            System.err.println("Error PersonajeController en getPersonajeById " + e.getMessage());
            return null;
        }
    }

    public Personaje postPersonaje(Personaje personaje) {
        try {
            return personajeService.postPersonaje(personaje);
        } catch (SQLException e) {
            System.err.println("Error PersonajeController en psotPersonaje: " + e.getMessage());
            return null;
        }
    }

    public Personaje updatePersonaje(Personaje personaje) {
        try {
            return personajeService.updatePersonaje(personaje);
        } catch (SQLException e) {
            System.err.println("Error PersonajeController en updatePersonaje: " + e.getMessage());
            return null;
        }
    }

    public Personaje deletePersonaje(Personaje personaje) {
        try {
            return personajeService.deletePersonaje(personaje);
        } catch (SQLException e) {
            System.err.println("Error PersonajeController en deletePersonaje: " + e.getMessage());
            return null;
        }
    }

}
