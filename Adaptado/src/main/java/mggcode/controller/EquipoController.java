package mggcode.controller;

import mggcode.entity.Equipo;
import mggcode.repository.EquipoRepository;
import mggcode.service.EquipoService;

import java.sql.SQLException;
import java.util.List;

public class EquipoController {
    private static EquipoController controller = null;

    // Mi Servicio unido al repositorio
    private final EquipoService equipoService;

    // Implementamos nuestro Singleton para el controlador
    private EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    public static EquipoController getInstance() {
        if (controller == null) {
            controller = new EquipoController(new EquipoService(new EquipoRepository()));
        }
        return controller;
    }

    // Ejemplo de operaciones
    public List<Equipo> getAllEquipos() {
        try {
            return equipoService.getAllEquipos();
        } catch (SQLException e) {
            System.err.println("Error EquipoController en getAllEquipos: " + e.getMessage());
            return null;
        }
    }

    public Equipo getEquipoById(Integer id) {
        try {
            return equipoService.getEquipoById(id);
        } catch (SQLException e) {
            System.err.println("Error EquipoController en getEquipoById " + e.getMessage());
            return null;
        }
    }

    public Equipo postEquipo(Equipo equipo) {
        try {
            return equipoService.postEquipo(equipo);
        } catch (SQLException e) {
            System.err.println("Error EquipoController en psotEquipo: " + e.getMessage());
            return null;
        }
    }

    public Equipo updateEquipo(Equipo equipo) {
        try {
            return equipoService.updateEquipo(equipo);
        } catch (SQLException e) {
            System.err.println("Error EquipoController en updateEquipo: " + e.getMessage());
            return null;
        }
    }

    public Equipo deleteEquipo(Equipo equipo) {
        try {
            return equipoService.deleteEquipo(equipo);
        } catch (SQLException e) {
            System.err.println("Error EquipoController en deleteEquipo: " + e.getMessage());
            return null;
        }
    }

}
