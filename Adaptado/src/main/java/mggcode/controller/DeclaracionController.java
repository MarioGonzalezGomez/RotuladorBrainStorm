package mggcode.controller;

import mggcode.entity.Declaracion;
import mggcode.repository.DeclaracionRepository;
import mggcode.service.DeclaracionService;

import java.sql.SQLException;
import java.util.List;

public class DeclaracionController {
    private static DeclaracionController controller = null;

    // Mi Servicio unido al repositorio
    private final DeclaracionService declaracionService;

    // Implementamos nuestro Singleton para el controlador
    private DeclaracionController(DeclaracionService declaracionService) {
        this.declaracionService = declaracionService;
    }

    public static DeclaracionController getInstance() {
        if (controller == null) {
            controller = new DeclaracionController(new DeclaracionService(new DeclaracionRepository()));
        }
        return controller;
    }

    // Ejemplo de operaciones
    public List<Declaracion> getAllDeclaraciones() {
        try {
            return declaracionService.getAllDeclaraciones();
        } catch (SQLException e) {
            System.err.println("Error DeclaracionController en getAllDeclaraciones: " + e.getMessage());
            return null;
        }
    }

    public Declaracion getDeclaracionById(Integer id) {
        try {
            return declaracionService.getDeclaracionById(id);
        } catch (SQLException e) {
            System.err.println("Error DeclaracionController en getDeclaracionById " + e.getMessage());
            return null;
        }
    }

    public Declaracion postDeclaracion(Declaracion declaracion) {
        try {
            return declaracionService.postDeclaracion(declaracion);
        } catch (SQLException e) {
            System.err.println("Error DeclaracionController en postDeclaracion: " + e.getMessage());
            return null;
        }
    }

    public Declaracion updateDeclaracion(Declaracion declaracion) {
        try {
            return declaracionService.updateDeclaracion(declaracion);
        } catch (SQLException e) {
            System.err.println("Error DeclaracionController en updateDeclaracion: " + e.getMessage());
            return null;
        }
    }

    public Declaracion deleteDeclaracion(Declaracion declaracion) {
        try {
            return declaracionService.deleteDeclaracion(declaracion);
        } catch (SQLException e) {
            System.err.println("Error DeclaracionController en deleteDeclaracion: " + e.getMessage());
            return null;
        }
    }

}
