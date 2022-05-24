package mggcode.controller;

import mggcode.entity.Faldon;
import mggcode.repository.FaldonRepository;
import mggcode.service.FaldonService;

import java.sql.SQLException;
import java.util.List;

public class FaldonController {
    private static FaldonController controller = null;

    // Mi Servicio unido al repositorio
    private final FaldonService faldonService;

    // Implementamos nuestro Singleton para el controlador
    private FaldonController(FaldonService faldonService) {
        this.faldonService = faldonService;
    }

    public static FaldonController getInstance() {
        if (controller == null) {
            controller = new FaldonController(new FaldonService(new FaldonRepository()));
        }
        return controller;
    }

    // Ejemplo de operaciones
    public List<Faldon> getAllFaldones() {
        try {
            return faldonService.getAllFaldones();
        } catch (SQLException e) {
            System.err.println("Error FaldonController en getAllFaldones: " + e.getMessage());
            return null;
        }
    }

    public Faldon getFaldonById(Integer id) {
        try {
            return faldonService.getFaldonById(id);
        } catch (SQLException e) {
            System.err.println("Error FaldonController en getFaldonById " + e.getMessage());
            return null;
        }
    }

    public Faldon postFaldon(Faldon faldon) {
        try {
            return faldonService.postFaldon(faldon);
        } catch (SQLException e) {
            System.err.println("Error FaldonController en postFaldon: " + e.getMessage());
            return null;
        }
    }

    public Faldon updateFaldon(Faldon faldon) {
        try {
            return faldonService.updateFaldon(faldon);
        } catch (SQLException e) {
            System.err.println("Error FaldonController en updateFaldon: " + e.getMessage());
            return null;
        }
    }

    public Faldon deleteFaldon(Faldon faldon) {
        try {
            return faldonService.deleteFaldon(faldon);
        } catch (SQLException e) {
            System.err.println("Error FaldonController en deleteFaldon: " + e.getMessage());
            return null;
        }
    }

}
