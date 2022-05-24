package mggcode.controller;

import mggcode.entity.Presentador;
import mggcode.repository.PresentadorRepository;
import mggcode.service.PresentadorService;

import java.sql.SQLException;
import java.util.List;

public class PresentadorController {
    private static PresentadorController controller = null;

    // Mi Servicio unido al repositorio
    private final PresentadorService presentadorService;

    // Implementamos nuestro Singleton para el controlador
    private PresentadorController(PresentadorService presentadorService) {
        this.presentadorService = presentadorService;
    }

    public static PresentadorController getInstance() {
        if (controller == null) {
            controller = new PresentadorController(new PresentadorService(new PresentadorRepository()));
        }
        return controller;
    }

    // Ejemplo de operaciones
    public List<Presentador> getAllPresentadores() {
        try {
            return presentadorService.getAllPresentadores();
        } catch (SQLException e) {
            System.err.println("Error PresentadorController en getAllPresentadores: " + e.getMessage());
            return null;
        }
    }

    public Presentador getPresentadorById(Integer id) {
        try {
            return presentadorService.getPresentadorById(id);
        } catch (SQLException e) {
            System.err.println("Error PresentadorController en getPresentadorById " + e.getMessage());
            return null;
        }
    }

    public Presentador postPresentador(Presentador presentador) {
        try {
            return presentadorService.postPresentador(presentador);
        } catch (SQLException e) {
            System.err.println("Error PresentadorController en postPresentador: " + e.getMessage());
            return null;
        }
    }

    public Presentador updatePresentador(Presentador presentador) {
        try {
            return presentadorService.updatePresentador(presentador);
        } catch (SQLException e) {
            System.err.println("Error PresentadorController en updatePresentador: " + e.getMessage());
            return null;
        }
    }

    public Presentador deletePresentador(Presentador presentador) {
        try {
            return presentadorService.deletePresentador(presentador);
        } catch (SQLException e) {
            System.err.println("Error PresentadorController en deletePresentador: " + e.getMessage());
            return null;
        }
    }

}
