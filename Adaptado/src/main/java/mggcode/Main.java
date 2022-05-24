package mggcode;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mggcode.conexion.ConexionIPF;
import mggcode.conexion.ControladorBD;
import mggcode.conexion.HibernateController;
import mggcode.config.Configuracion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Configuracion.getConfiguracion();
        String idioma = Configuracion.config.getProperty("language");
        String pais = Configuracion.config.getProperty("country");
        Locale locale = new Locale(idioma, pais);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.strings", locale);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"), resourceBundle);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Rotulos Deportivos");
        stage.setScene(scene);
        stage.setMaximized(true);
        Controller controller = fxmlLoader.getController();
        stage.setOnCloseRequest(e -> {
            controller.shutdown();
            Platform.exit();
        });
        stage.show();
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        // IniciarPrograma init = IniciarPrograma.getIniciarPrograma();
        // init.iniciarIPF();
        ConexionIPF.getConexion();
        Configuracion.getConfiguracion();
        ControladorBD bd = ControladorBD.getInstance();
        bd.open();
        bd.initData("sql/init.sql");
        bd.close();
        HibernateController.getInstance();

        launch();

    }
}
