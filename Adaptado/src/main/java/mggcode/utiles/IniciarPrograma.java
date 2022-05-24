package mggcode.utiles;

import static mggcode.config.Configuracion.config;

public class IniciarPrograma {
    private static IniciarPrograma init;

    private IniciarPrograma() {
    }

    public static IniciarPrograma getIniciarPrograma() {
        if (init == null) {
            init = new IniciarPrograma();
        }
        return init;
    }

    public void iniciarIPF() {
        Runtime aplicacion = Runtime.getRuntime();
        try {
            aplicacion.exec(config.getProperty("rutaIPF"));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
