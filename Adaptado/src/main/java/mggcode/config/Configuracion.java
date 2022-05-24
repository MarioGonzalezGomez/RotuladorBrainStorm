package mggcode.config;


import java.io.FileInputStream;
import java.util.Properties;

public class Configuracion {

    private static Configuracion configuracion;
    public static Properties config;

    private Configuracion() {
        config = new Properties();
        loadConfig();
    }

    public static Configuracion getConfiguracion() {
        if (configuracion == null) {
            configuracion = new Configuracion();
        }
        return configuracion;
    }

    public void loadConfig() {
        try {
            config.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (Exception e) {
            System.out.println("Error cargando configuración");
        }
    }
}
