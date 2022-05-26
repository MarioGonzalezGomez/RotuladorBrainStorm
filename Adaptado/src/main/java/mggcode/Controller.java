package mggcode;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import mggcode.conexion.ConexionIPF;
import mggcode.conexion.ControladorBD;
import mggcode.conexion.HibernateController;
import mggcode.config.Configuracion;
import mggcode.controller.*;
import mggcode.entity.*;
import mggcode.repository.PersonajeRepository;
import mggcode.repository.PresentadorRepository;
import mggcode.utiles.comparators.CompararDeclaracionPorId;
import mggcode.utiles.comparators.CompararFaldonPorId;
import mggcode.utiles.comparators.CompararPersonajePorId;
import mggcode.utiles.comparators.CompararPresentadorPorId;


import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static java.lang.System.exit;

public class Controller implements Initializable {
    private final int LONGITUD_MAXIMA_NOMBRE = 10;
    private final int LONGITUD_MAXIMA_CARGO = 10;
    private final int LONGITUD_MAXIMA_DECLARACION = 30;
    private final int LONGITUD_MAXIMA_TITULAR = 10;
    private final int LONGITUD_MAXIMA_FALDON = 30;
    private final int LONGITUD_MAXIMA_LOCALIZACION = 15;


    private ConexionIPF c;


    private ObservableList<Presentador> datosPresentador;
    private ObservableList<Personaje> datosPersonaje;
    private ObservableList<Declaracion> datosDeclaraciones;
    private ObservableList<Faldon> datosFaldon;
    private ObservableList<Equipo> datosEquipo;
    private ObservableList<Localizacion> datosLocalizacion;

    private List<Presentador> presentadoresBD;
    private List<Personaje> personajesBD;
    private List<Declaracion> declaracionesBD;
    private List<Faldon> faldonesBD;
    private List<Equipo> equiposBD;

    private Presentador presentadorActual = new Presentador();
    private Personaje personajeActual = new Personaje();
    private Declaracion declaracionActual = new Declaracion();
    private Faldon faldonActual = new Faldon();
    private Localizacion localizacionActual = new Localizacion();

    private String tipoActual;
    //private String tipoLanzado;

    private PresentadorController presentadorController;
    private PersonajeController personajeController;
    private DeclaracionController declaracionController;
    private FaldonController faldonController;
    private EquipoController equipoController;

    @FXML
    private Label lblRotulo;

    @FXML
    private TextArea txtAreaSelection;

    @FXML
    private ChoiceBox<Equipo> chboxEquipo;

    @FXML
    private TextField fieldCargo;

    @FXML
    private TextField fieldCargoPersonaje;

    @FXML
    private TextField fieldPersonaje;

    @FXML
    private TextField fieldPresentador;

    @FXML
    private TextField fieldDeclaracion;

    @FXML
    private TextField fieldSelection1;

    @FXML
    private TextField fieldSelection2;

    @FXML
    private TextField fieldTitular;

    @FXML
    private TextField fieldFaldon;

    @FXML
    private TextField fieldTituloLocalizacion;

    @FXML
    private TextField fieldLocalizacion;

    @FXML
    private TableView<Personaje> tblPersonaje;

    @FXML
    private TableView<Presentador> tblPresentador;

    @FXML
    private TableView<Declaracion> tblDeclaracion;

    @FXML
    private TableView<Faldon> tblFaldon;

    @FXML
    private TableView<Localizacion> tblLocalizacion;

    @FXML
    private TableColumn<Personaje, String> col1Personaje;

    @FXML
    private TableColumn<Presentador, String> col1Presentador;

    @FXML
    private TableColumn<Personaje, String> col2Personaje;

    @FXML
    private TableColumn<Presentador, String> col2Presentador;

    @FXML
    private TableColumn<Personaje, String> col3Personaje;

    @FXML
    private TableColumn<Declaracion, String> col1Declaraciones;

    @FXML
    private TableColumn<Faldon, String> col1Faldon;

    @FXML
    private TableColumn<Faldon, String> col2Faldon;

    @FXML
    private TableColumn<Declaracion, Integer> colIdDeclaracion;

    @FXML
    private TableColumn<Faldon, Integer> colIdFaldon;

    @FXML
    private TableColumn<Personaje, Integer> colIdPersonaje;

    @FXML
    private TableColumn<Presentador, Integer> colIdPresentador;

    @FXML
    private TableColumn<Localizacion, String> col1Localizacion;

    @FXML
    private TableColumn<Localizacion, String> col2Localizacion;


    //////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c = ConexionIPF.getConexion();
        configuracionInicial();
        iniciarDatos();
        addEscuchadoresListas();
    }

    private void configuracionInicial() {
        configurarTablas();
        configurarFields();
        addEsuchadoresElementos();
    }

    private void iniciarDatos() {
        presentadorController = PresentadorController.getInstance();
        declaracionController = DeclaracionController.getInstance();
        personajeController = PersonajeController.getInstance();
        faldonController = FaldonController.getInstance();
        equipoController = EquipoController.getInstance();

        datosPresentador = FXCollections.observableArrayList(
                cargarPresentadores()
        );


        datosPersonaje = FXCollections.observableArrayList(
                cargarPersonajes()
        );

        datosDeclaraciones = FXCollections.observableArrayList(
                cargarDeclaraciones()
        );

        datosFaldon = FXCollections.observableArrayList(
                cargarFaldones()
        );

        datosEquipo = FXCollections.observableArrayList(
                cargarEquipos()
        );

        datosPresentador.sort(new CompararPresentadorPorId());
        datosPresentador.forEach(presentador -> {
            presentador.setPosition(datosPresentador.indexOf(presentador) + 1);
        });
        datosPersonaje.sort(new CompararPersonajePorId());
        datosPersonaje.forEach(per -> {
            per.setPosition(datosPersonaje.indexOf(per) + 1);
        });
        datosDeclaraciones.sort(new CompararDeclaracionPorId());
        datosDeclaraciones.forEach(dec -> {
            dec.setPosition(datosDeclaraciones.indexOf(dec) + 1);
        });

        datosFaldon.sort(new CompararFaldonPorId());
        datosFaldon.forEach(fal -> {
            fal.setPosition(datosFaldon.indexOf(fal) + 1);
        });

        Localizacion l1 = new Localizacion();
        l1.setTexto("Directo");
        l1.setTipo(Tipo.SIMPLE);
        ArrayList<Localizacion> localizaciones = new ArrayList<>();
        localizaciones.add(l1);
        datosLocalizacion = FXCollections.observableArrayList(
                localizaciones
        );

        tblPresentador.setItems(datosPresentador);
        tblPersonaje.setItems(datosPersonaje);
        tblDeclaracion.setItems(datosDeclaraciones);
        tblFaldon.setItems(datosFaldon);
        tblLocalizacion.setItems(datosLocalizacion);

        chboxEquipo.setItems(datosEquipo);
    }

    private List<Presentador> cargarPresentadores() {

        var x = presentadorController.getAllPresentadores();
        presentadoresBD = x;
        return Objects.requireNonNullElseGet(x, ArrayList::new);
    }

    private List<Personaje> cargarPersonajes() {
        var x = personajeController.getAllPersonajes();
        personajesBD = x;
        return Objects.requireNonNullElseGet(x, ArrayList::new);
    }

    private List<Declaracion> cargarDeclaraciones() {
        var x = declaracionController.getAllDeclaraciones();
        declaracionesBD = x;
        return Objects.requireNonNullElseGet(x, ArrayList::new);
    }

    private List<Faldon> cargarFaldones() {
        var x = faldonController.getAllFaldones();
        faldonesBD = x;
        return Objects.requireNonNullElseGet(x, ArrayList::new);
    }

    private List<Equipo> cargarEquipos() {
       // String ruta = Configuracion.config.getProperty("rutaLogos");
//
       // Equipo f = new Equipo();
       // f.setNombre("-");
//
       // equipoController.postEquipo(f);
       // Equipo rm = new Equipo();
       // rm.setNombre("Real Madrid");
       // rm.setLogo(ruta + "RealMadrid.png");
       // equipoController.postEquipo(rm);
//
       // Equipo lp = new Equipo();
       // lp.setNombre("Liverpool");
       // lp.setLogo(ruta + "Liverpool.png");
       // equipoController.postEquipo(lp);

        var x = equipoController.getAllEquipos();
        equiposBD = x;
        return Objects.requireNonNullElseGet(x, ArrayList::new);
    }


    private void addEsuchadoresElementos() {
        tblPresentador.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null) {
                    presentadorActual = (Presentador) t1;
                }
                tipoActual = "presentador";
                if (presentadorActual != null && txtAreaSelection != null && fieldSelection1 != null) {
                    limpiarSeleccion();
                    modificarFields();
                    txtAreaSelection.setText(presentadorActual.getNombre());
                    fieldSelection1.setText(presentadorActual.getCargo());
                }
            }
        });

        tblPresentador.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {
                    if (fieldPresentador.isFocused() || fieldCargo.isFocused()) {
                    } else {
                        tblPresentador.getSelectionModel().clearSelection();
                    }
                }

            }
        });

        tblPersonaje.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null) {
                    personajeActual = (Personaje) t1;
                }
                tipoActual = "personaje";
                if (personajeActual != null && txtAreaSelection != null && fieldSelection1 != null) {
                    limpiarSeleccion();
                    modificarFields();
                    txtAreaSelection.setText(personajeActual.getNombre());
                    fieldSelection1.setText(personajeActual.getCargo());
                    if (personajeActual.getEquipo() != null) {
                        fieldSelection2.setText(personajeActual.getEquipo().getNombre());
                    }
                }
            }
        });

        tblPersonaje.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {
                    if (fieldPersonaje.isFocused() || fieldCargoPersonaje.isFocused() || chboxEquipo.isFocused()) {
                    } else {
                        tblPersonaje.getSelectionModel().clearSelection();
                    }
                }

            }
        });

        tblDeclaracion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null) {
                    declaracionActual = (Declaracion) t1;
                }
                tipoActual = "declaracion";
                if (declaracionActual != null && txtAreaSelection != null && fieldSelection1 != null) {
                    limpiarSeleccion();
                    modificarFields();
                    txtAreaSelection.setText(declaracionActual.getTexto());
                }
            }
        });

        tblDeclaracion.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {
                    if (fieldDeclaracion.isFocused()) {
                    } else {
                        tblDeclaracion.getSelectionModel().clearSelection();
                    }
                }
            }
        });


        tblFaldon.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null) {
                    faldonActual = (Faldon) t1;
                }
                tipoActual = "faldon";
                if (faldonActual != null && txtAreaSelection != null && fieldSelection1 != null) {
                    limpiarSeleccion();
                    modificarFields();
                    txtAreaSelection.setText(faldonActual.getTexto());
                    fieldSelection1.setText(faldonActual.getTitular());
                }
            }
        });

        tblFaldon.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {
                    if (fieldFaldon.isFocused() || fieldTitular.isFocused()) {
                    } else {
                        tblFaldon.getSelectionModel().clearSelection();
                    }
                }

            }
        });

        tblLocalizacion.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (t1 != null) {
                    localizacionActual = (Localizacion) t1;
                }
                tipoActual = "localizacion";
                if (localizacionActual != null && txtAreaSelection != null && fieldSelection1 != null) {
                    limpiarSeleccion();
                    modificarFields();
                    txtAreaSelection.setText(localizacionActual.getTexto());
                    fieldSelection1.setText(localizacionActual.getTitulo());
                }
            }
        });

        tblLocalizacion.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {
                    if (fieldLocalizacion.isFocused() || fieldTituloLocalizacion.isFocused()) {
                    } else {
                        tblLocalizacion.getSelectionModel().clearSelection();
                    }
                }
            }
        });

        fieldPresentador.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > LONGITUD_MAXIMA_NOMBRE) {
                    fieldPresentador.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
                } else {
                    fieldPresentador.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
                }
            }
        });
        fieldCargo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > LONGITUD_MAXIMA_CARGO) {
                    fieldCargo.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
                } else {
                    fieldCargo.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
                }
            }
        });

        fieldPersonaje.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > LONGITUD_MAXIMA_NOMBRE) {
                    fieldPersonaje.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
                } else {
                    fieldPersonaje.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
                }
            }
        });

        fieldCargoPersonaje.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > LONGITUD_MAXIMA_CARGO) {
                    fieldCargoPersonaje.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
                } else {
                    fieldCargoPersonaje.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
                }
            }
        });

        fieldDeclaracion.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > LONGITUD_MAXIMA_DECLARACION) {
                    fieldDeclaracion.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
                } else {
                    fieldDeclaracion.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
                }
            }
        });
        fieldTitular.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > LONGITUD_MAXIMA_TITULAR) {
                    fieldTitular.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
                } else {
                    fieldTitular.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
                }
            }
        });

        fieldFaldon.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > LONGITUD_MAXIMA_FALDON) {
                    fieldFaldon.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
                } else {
                    fieldFaldon.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
                }
            }
        });

        fieldTituloLocalizacion.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > LONGITUD_MAXIMA_LOCALIZACION) {
                    fieldTituloLocalizacion.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
                } else {
                    fieldTituloLocalizacion.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
                }
            }
        });

        fieldLocalizacion.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1.length() > LONGITUD_MAXIMA_LOCALIZACION) {
                    fieldLocalizacion.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000;");
                } else {
                    fieldLocalizacion.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
                }
            }
        });

    }

    private void addEscuchadoresListas() {
        datosPresentador.addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(Change<? extends Presentador> change) {
                datosPresentador.forEach(presentador -> {
                    presentador.setPosition(datosPresentador.indexOf(presentador) + 1);
                });
            }
        });


        datosPersonaje.addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(Change<? extends Personaje> change) {
                datosPersonaje.forEach(per -> {
                    per.setPosition(datosPersonaje.indexOf(per) + 1);
                });
            }
        });

        datosDeclaraciones.addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(Change<? extends Declaracion> change) {
                datosDeclaraciones.forEach(dec -> {
                    dec.setPosition(datosDeclaraciones.indexOf(dec) + 1);
                });
            }
        });

        datosFaldon.addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(Change<? extends Faldon> change) {
                datosFaldon.forEach(fal -> {
                    fal.setPosition(datosFaldon.indexOf(fal) + 1);
                });
            }
        });

    }

    private void limpiarSeleccion() {
        txtAreaSelection.clear();
        fieldSelection1.clear();
        fieldSelection2.clear();
    }

    private void modificarFields() {
        switch (tipoActual) {
            case "presentador":
                if (presentadorActual.getTipo() == Tipo.SIMPLE) {
                    fieldSelection1.setVisible(false);
                    lblRotulo.setText("PRESENTADOR");
                } else {
                    fieldSelection1.setVisible(true);
                    lblRotulo.setText("PRESENTADOR+CARGO");
                }
                fieldSelection2.setVisible(false);
                break;
            case "personaje":
                if (personajeActual.getTipo() == Tipo.SIMPLE) {
                    fieldSelection1.setVisible(false);
                    lblRotulo.setText("PERSONAJE");
                } else {
                    fieldSelection1.setVisible(true);
                    lblRotulo.setText("PERSONAJE+CARGO");
                }

                if (personajeActual.getEquipo() != null) {
                    fieldSelection2.setVisible(true);
                    if (personajeActual.getEquipo().getNombre().equals("-")) {
                        fieldSelection2.setVisible(false);
                    }
                } else {
                    fieldSelection2.setVisible(false);
                }

                break;
            case "declaracion":
                fieldSelection1.setVisible(false);
                fieldSelection2.setVisible(false);
                lblRotulo.setText("CRAWL");
                break;
            case "faldon":
                if (faldonActual.getTipo() == Tipo.SIMPLE) {
                    fieldSelection1.setVisible(false);
                    lblRotulo.setText("FALDÓN");
                } else {
                    fieldSelection1.setVisible(true);
                    lblRotulo.setText("FALDÓN+TITULAR");
                }
                fieldSelection2.setVisible(false);
                break;
            case "localizacion":
                if (localizacionActual.getTipo() == Tipo.SIMPLE) {
                    fieldSelection1.setVisible(false);
                    lblRotulo.setText("LOCALIZACIÓN");
                } else {
                    fieldSelection1.setVisible(true);
                    lblRotulo.setText("LOCALIZACIÓN+TITULAR");
                }
                fieldSelection2.setVisible(false);
                break;
            default:
                System.out.println("Ha salido default al modificar Fields");
                break;
        }
    }

    private void configurarTablas() {
        tblPresentador.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tblPresentador.setEditable(true);
        tblPersonaje.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tblPersonaje.setEditable(true);
        tblDeclaracion.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tblDeclaracion.setEditable(true);
        tblFaldon.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tblFaldon.setEditable(true);
        tblLocalizacion.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tblLocalizacion.setEditable(true);

        colIdPresentador.setCellValueFactory(new PropertyValueFactory<>("position"));
        col1Presentador.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col2Presentador.setCellValueFactory(new PropertyValueFactory<>("cargo"));

        colIdPersonaje.setCellValueFactory(new PropertyValueFactory<>("position"));
        col1Personaje.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        col2Personaje.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        col3Personaje.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colIdDeclaracion.setCellValueFactory(new PropertyValueFactory<>("position"));
        col1Declaraciones.setCellValueFactory(new PropertyValueFactory<>("texto"));

        colIdFaldon.setCellValueFactory(new PropertyValueFactory<>("position"));
        col1Faldon.setCellValueFactory(new PropertyValueFactory<>("titular"));
        col2Faldon.setCellValueFactory(new PropertyValueFactory<>("texto"));

        col1Localizacion.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        col2Localizacion.setCellValueFactory(new PropertyValueFactory<>("texto"));

    }

    private void configurarFields() {
        fieldPresentador.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
        fieldCargo.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
        fieldPersonaje.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
        fieldCargoPersonaje.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
        fieldDeclaracion.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
        fieldTitular.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
        fieldFaldon.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
        fieldLocalizacion.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");
        fieldTituloLocalizacion.setStyle("-fx-text-box-border: #3B83BD; -fx-focus-color: #3B83BD;");

        fieldSelection2.setVisible(false);
    }

    public List<Presentador> getRotulosSeleccionadosPresentador() {
        if (tblPresentador != null) {
            return tblPresentador.getSelectionModel().getSelectedItems();
        }
        return null;
    }

    public List<Personaje> getRotulosSeleccionadosPersonaje() {
        if (tblPersonaje != null) {
            return tblPersonaje.getSelectionModel().getSelectedItems();
        }
        return null;
    }

    public List<Declaracion> getRotulosSeleccionadosDeclaracion() {
        if (tblDeclaracion != null) {
            return tblDeclaracion.getSelectionModel().getSelectedItems();
        }
        return null;
    }

    private List<Faldon> getRotulosSeleccionadosFaldones() {
        if (tblFaldon != null) {
            return tblFaldon.getSelectionModel().getSelectedItems();
        }
        return null;
    }

    private List<Localizacion> getRotulosSeleccionadosLocalizaciones() {
        if (tblLocalizacion != null) {
            return tblLocalizacion.getSelectionModel().getSelectedItems();
        }
        return null;
    }


////////////////////////////////////////////////////////////////////////////

    @FXML
    void addToList(MouseEvent event) {
        if (!fieldPresentador.getText().equals("")) {
            Presentador presentador = new Presentador();
            presentador.setNombre(fieldPresentador.getText());
            if (fieldCargo.getText().equals("")) {
                presentador.setTipo(Tipo.SIMPLE);
            } else {
                presentador.setTipo(Tipo.DOBLE);
                presentador.setCargo(fieldCargo.getText());
            }
            //Con esta condición meto el elemento detrás del seleccionado si tengo seleccionado un único elemento. En cualquier
            //otro caso, se enviará al final de la lista
            List<Presentador> seleccion = getRotulosSeleccionadosPresentador();
            if (seleccion.size() == 1 && datosPresentador.indexOf(seleccion.get(0)) != datosPresentador.size() - 1 && presentadorActual == seleccion.get(0)) {
                int posicion = datosPresentador.indexOf(seleccion.get(0));
                presentador.setPosition(posicion + 2);
                List<Presentador> siguientes = new ArrayList<>();
                datosPresentador.forEach(element -> {
                    if (datosPresentador.indexOf(element) > posicion) {
                        siguientes.add(element);
                    }
                });
                datosPresentador.set(posicion + 1, presentador);
                datosPresentador.removeAll(siguientes);
                datosPresentador.addAll(siguientes);
            } else {
                datosPresentador.add(presentador);
            }
            fieldPresentador.clear();
            fieldPresentador.setPromptText("");
            fieldCargo.clear();
        } else {
            fieldPresentador.setPromptText("Obligatorio rellenar");
        }

    }

    @FXML
    void addToList2(MouseEvent event) {
        if (!fieldPersonaje.getText().equals("")) {
            Personaje personaje = new Personaje();
            personaje.setNombre(fieldPersonaje.getText());

            if (fieldCargoPersonaje.getText().equals("")) {
                personaje.setTipo(Tipo.SIMPLE);
            } else {
                personaje.setTipo(Tipo.DOBLE);
                personaje.setCargo(fieldCargoPersonaje.getText());
            }
            if (chboxEquipo.getValue() != null) {
                personaje.setEquipo(chboxEquipo.getSelectionModel().getSelectedItem());
            } else {
                personaje.setEquipo(null);
            }

            List<Personaje> seleccion = getRotulosSeleccionadosPersonaje();
            if (seleccion.size() == 1 && datosPersonaje.indexOf(seleccion.get(0)) != datosPersonaje.size() - 1 && personajeActual == seleccion.get(0)) {
                int posicion = datosPersonaje.indexOf(seleccion.get(0));
                personaje.setPosition(posicion + 2);
                List<Personaje> siguientes = new ArrayList<>();
                datosPersonaje.forEach(element -> {
                    if (datosPersonaje.indexOf(element) > posicion) {
                        siguientes.add(element);
                    }
                });
                datosPersonaje.set(posicion + 1, personaje);
                datosPersonaje.removeAll(siguientes);
                datosPersonaje.addAll(siguientes);
            } else {
                datosPersonaje.add(personaje);
            }
            fieldPersonaje.clear();
            fieldPersonaje.setPromptText("");
            fieldCargoPersonaje.clear();
        } else {
            fieldPersonaje.setPromptText("Obligatorio rellenar");
        }

    }

    @FXML
    void addToList3(MouseEvent event) {
        if (!fieldDeclaracion.getText().equals("")) {
            Declaracion declaracion = new Declaracion();
            declaracion.setTexto(fieldDeclaracion.getText());
            List<Declaracion> seleccion = getRotulosSeleccionadosDeclaracion();
            if (seleccion.size() == 1 && datosDeclaraciones.indexOf(seleccion.get(0)) != datosDeclaraciones.size() - 1 && declaracionActual == seleccion.get(0)) {
                int posicion = datosDeclaraciones.indexOf(seleccion.get(0));
                declaracion.setPosition(posicion + 2);
                List<Declaracion> siguientes = new ArrayList<>();
                datosDeclaraciones.forEach(element -> {
                    if (datosDeclaraciones.indexOf(element) > posicion) {
                        siguientes.add(element);
                    }
                });
                datosDeclaraciones.set(posicion + 1, declaracion);
                datosDeclaraciones.removeAll(siguientes);
                datosDeclaraciones.addAll(siguientes);
            } else {
                datosDeclaraciones.add(declaracion);
            }
            fieldDeclaracion.clear();
            fieldDeclaracion.setPromptText("");

        } else {
            fieldDeclaracion.setPromptText("Obligatorio rellenar");
        }
    }

    @FXML
    void addToList4(MouseEvent event) {
        if (!fieldFaldon.getText().equals("")) {
            Faldon faldon = new Faldon();
            faldon.setTexto(fieldFaldon.getText());
            if (fieldTitular.getText().equals("")) {
                faldon.setTipo(Tipo.SIMPLE);
            } else {
                faldon.setTipo(Tipo.DOBLE);
                faldon.setTitular(fieldTitular.getText());
            }
            List<Faldon> seleccion = getRotulosSeleccionadosFaldones();
            if (seleccion.size() == 1 && datosFaldon.indexOf(seleccion.get(0)) != datosFaldon.size() - 1 && faldonActual == seleccion.get(0)) {
                int posicion = datosFaldon.indexOf(seleccion.get(0));
                faldon.setPosition(posicion + 2);
                List<Faldon> siguientes = new ArrayList<>();
                datosFaldon.forEach(element -> {
                    if (datosFaldon.indexOf(element) > posicion) {
                        siguientes.add(element);
                    }
                });
                datosFaldon.set(posicion + 1, faldon);
                datosFaldon.removeAll(siguientes);
                datosFaldon.addAll(siguientes);
            } else {
                datosFaldon.add(faldon);
            }
            fieldTitular.clear();
            fieldFaldon.clear();
            fieldFaldon.setPromptText("");
        } else {
            fieldFaldon.setPromptText("Obligatorio rellenar");
        }
    }

    @FXML
    void addToList5(MouseEvent event) {
        if (!fieldLocalizacion.getText().equals("")) {
            Localizacion localizacion = new Localizacion();
            localizacion.setTexto(fieldLocalizacion.getText());

            if (fieldTituloLocalizacion.getText().equals("")) {
                localizacion.setTipo(Tipo.SIMPLE);
            } else {
                localizacion.setTipo(Tipo.DOBLE);
                localizacion.setTitulo(fieldTituloLocalizacion.getText());
            }
            List<Localizacion> seleccion = getRotulosSeleccionadosLocalizaciones();
            if (seleccion.size() == 1 && datosLocalizacion.indexOf(seleccion.get(0)) != datosLocalizacion.size() - 1 && localizacionActual == seleccion.get(0)) {
                int posicion = datosLocalizacion.indexOf(seleccion.get(0));
                List<Localizacion> siguientes = new ArrayList<>();
                datosLocalizacion.forEach(element -> {
                    if (datosLocalizacion.indexOf(element) > posicion) {
                        siguientes.add(element);
                    }
                });
                datosLocalizacion.set(posicion + 1, localizacion);
                datosLocalizacion.removeAll(siguientes);
                datosLocalizacion.addAll(siguientes);
            } else {
                datosLocalizacion.add(localizacion);
            }
            fieldTituloLocalizacion.clear();
            fieldLocalizacion.clear();
            fieldLocalizacion.setPromptText("");
        } else {
            fieldLocalizacion.setPromptText("Obligatorio rellenar");
        }
    }

    @FXML
    void subirElemento(MouseEvent event) {
        List<Presentador> seleccion = getRotulosSeleccionadosPresentador();
        if (seleccion.size() == 1 && datosPresentador.indexOf(seleccion.get(0)) != 0) {
            int posicionSeleccion = datosPresentador.indexOf(seleccion.get(0));
            Presentador seleccionado = datosPresentador.get(posicionSeleccion);
            Presentador actual = datosPresentador.get(posicionSeleccion - 1);
            datosPresentador.set(posicionSeleccion - 1, seleccionado);
            datosPresentador.set(posicionSeleccion, actual);
        }
    }

    @FXML
    void subirElemento2(MouseEvent event) {
        List<Personaje> seleccion = getRotulosSeleccionadosPersonaje();
        if (seleccion.size() == 1 && datosPersonaje.indexOf(seleccion.get(0)) != 0) {
            int posicionSeleccion = datosPersonaje.indexOf(seleccion.get(0));
            Personaje seleccionado = datosPersonaje.get(posicionSeleccion);
            Personaje actual = datosPersonaje.get(posicionSeleccion - 1);
            datosPersonaje.set(posicionSeleccion - 1, seleccionado);
            datosPersonaje.set(posicionSeleccion, actual);
        }
    }

    @FXML
    void subirElemento3(MouseEvent event) {
        List<Declaracion> seleccion = getRotulosSeleccionadosDeclaracion();
        if (seleccion.size() == 1 && datosDeclaraciones.indexOf(seleccion.get(0)) != 0) {
            int posicionSeleccion = datosDeclaraciones.indexOf(seleccion.get(0));
            Declaracion seleccionado = datosDeclaraciones.get(posicionSeleccion);
            Declaracion actual = datosDeclaraciones.get(posicionSeleccion - 1);
            datosDeclaraciones.set(posicionSeleccion - 1, seleccionado);
            datosDeclaraciones.set(posicionSeleccion, actual);
        }
    }

    @FXML
    void subirElemento4(MouseEvent event) {
        List<Faldon> seleccion = getRotulosSeleccionadosFaldones();
        if (seleccion.size() == 1 && datosFaldon.indexOf(seleccion.get(0)) != 0) {
            int posicionSeleccion = datosFaldon.indexOf(seleccion.get(0));
            Faldon seleccionado = datosFaldon.get(posicionSeleccion);
            Faldon actual = datosFaldon.get(posicionSeleccion - 1);
            datosFaldon.set(posicionSeleccion - 1, seleccionado);
            datosFaldon.set(posicionSeleccion, actual);
        }
    }

    @FXML
    void subirElemento5(MouseEvent event) {
        List<Localizacion> seleccion = getRotulosSeleccionadosLocalizaciones();
        if (seleccion.size() == 1 && datosLocalizacion.indexOf(seleccion.get(0)) != 0) {
            int posicionSeleccion = datosLocalizacion.indexOf(seleccion.get(0));
            Localizacion seleccionado = datosLocalizacion.get(posicionSeleccion);
            Localizacion actual = datosLocalizacion.get(posicionSeleccion - 1);

            datosLocalizacion.set(posicionSeleccion - 1, seleccionado);
            datosLocalizacion.set(posicionSeleccion, actual);
        }
    }


    @FXML
    void bajarElemento(MouseEvent event) {
        List<Presentador> seleccion = getRotulosSeleccionadosPresentador();
        if (seleccion.size() == 1 && datosPresentador.indexOf(seleccion.get(0)) != datosPresentador.size() - 1) {
            int posicionSeleccion = datosPresentador.indexOf(seleccion.get(0));
            Presentador seleccionado = datosPresentador.get(posicionSeleccion);
            Presentador actual = datosPresentador.get(posicionSeleccion + 1);
            datosPresentador.set(posicionSeleccion + 1, seleccionado);
            datosPresentador.set(posicionSeleccion, actual);
        }
    }

    @FXML
    void bajarElemento2(MouseEvent event) {
        List<Personaje> seleccion = getRotulosSeleccionadosPersonaje();
        if (seleccion.size() == 1 && datosPersonaje.indexOf(seleccion.get(0)) != datosPersonaje.size() - 1) {
            int posicionSeleccion = datosPersonaje.indexOf(seleccion.get(0));
            Personaje seleccionado = datosPersonaje.get(posicionSeleccion);
            Personaje actual = datosPersonaje.get(posicionSeleccion + 1);
            datosPersonaje.set(posicionSeleccion + 1, seleccionado);
            datosPersonaje.set(posicionSeleccion, actual);
        }
    }

    @FXML
    void bajarElemento3(MouseEvent event) {
        List<Declaracion> seleccion = getRotulosSeleccionadosDeclaracion();
        if (seleccion.size() == 1 && datosDeclaraciones.indexOf(seleccion.get(0)) != datosDeclaraciones.size() - 1) {
            int posicionSeleccion = datosDeclaraciones.indexOf(seleccion.get(0));
            Declaracion seleccionado = datosDeclaraciones.get(posicionSeleccion);
            Declaracion actual = datosDeclaraciones.get(posicionSeleccion + 1);
            datosDeclaraciones.set(posicionSeleccion + 1, seleccionado);
            datosDeclaraciones.set(posicionSeleccion, actual);
        }
    }

    @FXML
    void bajarElemento4(MouseEvent event) {
        List<Faldon> seleccion = getRotulosSeleccionadosFaldones();
        if (seleccion.size() == 1 && datosFaldon.indexOf(seleccion.get(0)) != datosFaldon.size() - 1) {
            int posicionSeleccion = datosFaldon.indexOf(seleccion.get(0));
            Faldon seleccionado = datosFaldon.get(posicionSeleccion);
            Faldon actual = datosFaldon.get(posicionSeleccion + 1);
            datosFaldon.set(posicionSeleccion + 1, seleccionado);
            datosFaldon.set(posicionSeleccion, actual);
        }
    }

    @FXML
    void bajarElemento5(MouseEvent event) {
        List<Localizacion> seleccion = getRotulosSeleccionadosLocalizaciones();
        if (seleccion.size() == 1 && datosLocalizacion.indexOf(seleccion.get(0)) != datosLocalizacion.size() - 1) {
            int posicionSeleccion = datosLocalizacion.indexOf(seleccion.get(0));
            Localizacion seleccionado = datosLocalizacion.get(posicionSeleccion);
            Localizacion actual = datosLocalizacion.get(posicionSeleccion + 1);
            datosLocalizacion.set(posicionSeleccion + 1, seleccionado);
            datosLocalizacion.set(posicionSeleccion, actual);
        }
    }

    @FXML
    void eliminar(MouseEvent event) {
        List<Presentador> seleccion = getRotulosSeleccionadosPresentador();
        datosPresentador.removeAll(seleccion);

    }

    @FXML
    void eliminar2(MouseEvent event) {
        List<Personaje> seleccion = getRotulosSeleccionadosPersonaje();
        datosPersonaje.removeAll(seleccion);
    }

    @FXML
    void eliminar3(MouseEvent event) {
        List<Declaracion> seleccion = getRotulosSeleccionadosDeclaracion();
        datosDeclaraciones.removeAll(seleccion);
    }

    @FXML
    void eliminar4(MouseEvent event) {
        List<Faldon> seleccion = getRotulosSeleccionadosFaldones();
        datosFaldon.removeAll(seleccion);
    }

    @FXML
    void eliminar5(MouseEvent event) {
        List<Localizacion> seleccion = getRotulosSeleccionadosLocalizaciones();
        datosLocalizacion.removeAll(seleccion);
    }


//////////////////////////////////////////////////////////////////////////

    @FXML
    void entra(ActionEvent event) {

        if (txtAreaSelection.getText().equals(presentadorActual.getNombre())) {
            if (presentadorActual.getTipo() == Tipo.SIMPLE) {
                c.enviarMensaje("itemset('<Champions>txtPRESENTADOR', 'MAP_STRING_PAR', '" + presentadorActual.getNombre() + "');");
                c.enviarMensaje("itemset('<Champions>txtCARGO', 'MAP_STRING_PAR', ' ');");
                c.enviarMensaje("itemset('<Champions>PRESENTADOR/ENTRA', 'EVENT_RUN');");
            } else {
                c.enviarMensaje("itemset('<Champions>txtPRESENTADOR', 'MAP_STRING_PAR', '" + presentadorActual.getNombre() + "');");
                c.enviarMensaje("itemset('<Champions>txtCARGO', 'MAP_STRING_PAR', '" + presentadorActual.getCargo() + "');");
                c.enviarMensaje("itemset('<Champions>PRESENTADORCARGO/ENTRA', 'EVENT_RUN');");
            }
        } else if (txtAreaSelection.getText().equals(personajeActual.getNombre())) {
            if (personajeActual.getTipo() == Tipo.SIMPLE) {
                c.enviarMensaje("itemset('<Champions>txtPRESENTADOR', 'MAP_STRING_PAR', '" + personajeActual.getNombre() + "');");
                c.enviarMensaje("itemset('<Champions>txtCARGO', 'MAP_STRING_PAR', ' ');");
                if (personajeActual.getEquipo() != null && !personajeActual.getEquipo().getNombre().equals("-")) {
                    c.enviarMensaje("itemset('<Champions>textESCUDO', 'TEX_FILE', '" + personajeActual.getEquipo().getLogo() + "');");
                } else {
                    c.enviarMensaje("itemset('<Champions>textESCUDO', 'TEX_FILE', '" + Configuracion.config.getProperty("rutaLogos") + "Nada.png" + "');");
                }

                c.enviarMensaje("itemset('<Champions>PRESENTADOR/ENTRA', 'EVENT_RUN');");
            } else {
                c.enviarMensaje("itemset('<Champions>txtPRESENTADOR', 'MAP_STRING_PAR', '" + personajeActual.getNombre() + "');");
                c.enviarMensaje("itemset('<Champions>txtCARGO', 'MAP_STRING_PAR', '" + personajeActual.getCargo() + "');");
                if (personajeActual.getEquipo() != null && !personajeActual.getEquipo().getNombre().equals("-")) {
                    c.enviarMensaje("itemset('<Champions>textESCUDO', 'TEX_FILE', '" + personajeActual.getEquipo().getLogo() + "');");
                } else {
                    c.enviarMensaje("itemset('<Champions>textESCUDO', 'TEX_FILE', '" + Configuracion.config.getProperty("rutaLogos") + "Nada.png" + "');");
                }
                c.enviarMensaje("itemset('<Champions>PRESENTADORCARGO/ENTRA', 'EVENT_RUN');");
            }
        } else if (txtAreaSelection.getText().equals(declaracionActual.getTexto())) {
            c.enviarMensaje("itemset('<Champions>txtCRAWL', 'MAP_STRING_PAR', '" + declaracionActual.getTexto() + "');");
            c.enviarMensaje("itemset('<Champions>CRAWL/ENTRA', 'EVENT_RUN');");
        } else if (txtAreaSelection.getText().equals(faldonActual.getTexto())) {
            if (faldonActual.getTipo() == Tipo.SIMPLE) {
                c.enviarMensaje("itemset('<Champions>txtFALDON1', 'MAP_STRING_PAR', '" + faldonActual.getTexto() + "');");
                c.enviarMensaje("itemset('<Champions>txtFALDON_TITULAR', 'MAP_STRING_PAR', ' ');");
                c.enviarMensaje("itemset('<Champions>FALDON1/ENTRA', 'EVENT_RUN');");
            } else {
                c.enviarMensaje("itemset('<Champions>txtFALDON1', 'MAP_STRING_PAR', '" + faldonActual.getTexto() + "');");
                c.enviarMensaje("itemset('<Champions>txtFALDON_TITULAR', 'MAP_STRING_PAR', '" + faldonActual.getTitular() + "');");
                c.enviarMensaje("itemset('<Champions>FALDON2/ENTRA', 'EVENT_RUN');");
            }
        } else if (txtAreaSelection.getText().equals(localizacionActual.getTexto())) {
            if (localizacionActual.getTipo() == Tipo.SIMPLE) {
                c.enviarMensaje("itemset('<Champions>txtLOCALIZACION', 'MAP_STRING_PAR', '" + localizacionActual.getTexto() + "');");
                c.enviarMensaje("itemset('<Champions>txtLOCALIZACION_TITULAR', 'MAP_STRING_PAR', ' ');");
                c.enviarMensaje("itemset('<Champions>LOCALIZACION/ENTRA', 'EVENT_RUN');");
            } else {
                c.enviarMensaje("itemset('<Champions>txtLOCALIZACION', 'MAP_STRING_PAR', '" + localizacionActual.getTexto() + "');");
                c.enviarMensaje("itemset('<Champions>txtLOCALIZACION_TITULAR', 'MAP_STRING_PAR', '" + localizacionActual.getTitulo() + "');");
                c.enviarMensaje("itemset('<Champions>LOCALIZACION/ENTRA', 'EVENT_RUN');");
            }
        } else {
            switch (tipoActual) {
                //Dependiendo del tipo actual, como no dejaremos modificar el tipo de rótulo
                // podemos saber los casos especiales "presentador y cargo".... viendo el "tipo"
                // en los objetos presentadorActual y personajeActual
                case "presentador":
                    if (presentadorActual.getTipo() == Tipo.SIMPLE) {
                        c.enviarMensaje("itemset('<Champions>txtPRESENTADOR', 'MAP_STRING_PAR', '" + txtAreaSelection.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>txtCARGO', 'MAP_STRING_PAR', ' ');");
                        c.enviarMensaje("itemset('<Champions>PRESENTADOR/ENTRA', 'EVENT_RUN');");
                    } else {
                        c.enviarMensaje("itemset('<Champions>txtPRESENTADOR', 'MAP_STRING_PAR', '" + txtAreaSelection.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>txtCARGO', 'MAP_STRING_PAR', '" + fieldSelection1.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>PRESENTADORCARGO/ENTRA', 'EVENT_RUN');");
                    }
                    break;
                case "personaje":
                    if (personajeActual.getTipo() == Tipo.SIMPLE) {
                        c.enviarMensaje("itemset('<Champions>txtPRESENTADOR', 'MAP_STRING_PAR', '" + txtAreaSelection.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>txtCARGO', 'MAP_STRING_PAR', ' ');");
                        if (personajeActual.getEquipo() != null && !personajeActual.getEquipo().getNombre().equals("-")) {
                            c.enviarMensaje("itemset('<Champions>textESCUDO', 'TEX_FILE', '" + personajeActual.getEquipo().getLogo() + "');");
                        } else {
                            c.enviarMensaje("itemset('<Champions>textESCUDO', 'TEX_FILE', '" + Configuracion.config.getProperty("rutaLogos") + "Nada.png" + "');");
                        }
                        c.enviarMensaje("itemset('<Champions>PRESENTADOR/ENTRA', 'EVENT_RUN');");
                    } else {
                        c.enviarMensaje("itemset('<Champions>txtPRESENTADOR', 'MAP_STRING_PAR', '" + txtAreaSelection.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>txtCARGO', 'MAP_STRING_PAR', '" + fieldSelection1.getText() + "');");
                        if (personajeActual.getEquipo() != null && !personajeActual.getEquipo().getNombre().equals("-")) {
                            c.enviarMensaje("itemset('<Champions>textESCUDO', 'TEX_FILE', '" + personajeActual.getEquipo().getLogo() + "');");
                        } else {
                            c.enviarMensaje("itemset('<Champions>textESCUDO', 'TEX_FILE', '" + Configuracion.config.getProperty("rutaLogos") + "Nada.png" + "');");
                        }
                        c.enviarMensaje("itemset('<Champions>PRESENTADORCARGO/ENTRA', 'EVENT_RUN');");
                    }
                    break;

                case "declaracion":
                    c.enviarMensaje("itemset('<Champions>txtCRAWL', 'MAP_STRING_PAR', '" + txtAreaSelection.getText() + "');");
                    c.enviarMensaje("itemset('<Champions>CRAWL/ENTRA', 'EVENT_RUN');");
                    break;
                case "faldon":
                    if (faldonActual.getTipo() == Tipo.SIMPLE) {
                        c.enviarMensaje("itemset('<Champions>txtFALDON1', 'MAP_STRING_PAR', '" + txtAreaSelection.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>txtFALDON_TITULAR', 'MAP_STRING_PAR', ' ');");
                        c.enviarMensaje("itemset('<Champions>FALDON1/ENTRA', 'EVENT_RUN');");
                    } else {
                        c.enviarMensaje("itemset('<Champions>txtFALDON1', 'MAP_STRING_PAR', '" + txtAreaSelection.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>txtFALDON_TITULAR', 'MAP_STRING_PAR', '" + fieldSelection1.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>FALDON2/ENTRA', 'EVENT_RUN');");
                    }

                    break;
                case "localizacion":
                    if (localizacionActual.getTipo() == Tipo.SIMPLE) {
                        c.enviarMensaje("itemset('<Champions>txtLOCALIZACION', 'MAP_STRING_PAR', '" + txtAreaSelection.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>txtLOCALIZACION_TITULAR', 'MAP_STRING_PAR', ' ');");
                        c.enviarMensaje("itemset('<Champions>LOCALIZACION/ENTRA', 'EVENT_RUN');");
                    } else {
                        c.enviarMensaje("itemset('<Champions>txtLOCALIZACION', 'MAP_STRING_PAR', '" + txtAreaSelection.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>txtLOCALIZACION_TITULAR', 'MAP_STRING_PAR', '" + fieldSelection1.getText() + "');");
                        c.enviarMensaje("itemset('<Champions>LOCALIZACION/ENTRA', 'EVENT_RUN');");
                    }
                    break;
                default:
                    System.out.println("Ha salido default");
                    break;
            }
        }

    }

    @FXML
    void sale(ActionEvent event) {
        switch (tipoActual) {
            case "presentador":
                //   if (presentadorActual.getTipo() == Tipo.SIMPLE) {
                c.enviarMensaje("itemset('<Champions>PRESENTADOR/SALE', 'EVENT_RUN');");
                //  } else {
                c.enviarMensaje("itemset('<Champions>PRESENTADORCARGO/SALE', 'EVENT_RUN');");
                //   }
                break;
            case "personaje":
                // if (personajeActual.getTipo() == Tipo.SIMPLE) {
                c.enviarMensaje("itemset('<Champions>PRESENTADOR/SALE', 'EVENT_RUN');");
                // } else {
                c.enviarMensaje("itemset('<Champions>PRESENTADORCARGO/SALE', 'EVENT_RUN');");
                // }
                break;
            case "declaracion":
                c.enviarMensaje("itemset('<Champions>CRAWL/SALE', 'EVENT_RUN');");
                break;
            case "faldon":
                // if (faldonActual.getTipo() == Tipo.SIMPLE) {
                c.enviarMensaje("itemset('<Champions>FALDON1/SALE', 'EVENT_RUN');");
                // } else {
                c.enviarMensaje("itemset('<Champions>FALDON2/SALE', 'EVENT_RUN');");
                // }
                break;

            case "localizacion":
                c.enviarMensaje("itemset('<Champions>LOCALIZACION/SALE', 'EVENT_RUN');");
                break;
            default:
                System.out.println("Error al salir");
                break;
        }
    }


    @FXML
    void modificarSeleccion(ActionEvent event) {
        int position;
        switch (tipoActual) {
            case "presentador":
                Presentador presentador = new Presentador();
                presentador.setNombre(txtAreaSelection.getText());
                if (fieldSelection1.getText() == null) {
                    presentador.setTipo(Tipo.SIMPLE);
                } else {
                    presentador.setCargo(fieldSelection1.getText());
                    presentador.setTipo(Tipo.DOBLE);
                }
                position = datosPresentador.indexOf(presentadorActual);
                datosPresentador.set(position, presentador);
                break;
            case "personaje":
                Personaje personaje = new Personaje();
                personaje.setNombre(txtAreaSelection.getText());
                if (fieldSelection1.getText() == null) {
                    personaje.setTipo(Tipo.SIMPLE);
                } else {
                    personaje.setCargo(fieldSelection1.getText());
                    personaje.setTipo(Tipo.DOBLE);
                }
                personaje.setEquipo(personajeActual.getEquipo());
                position = datosPersonaje.indexOf(personajeActual);
                datosPersonaje.set(position, personaje);
                break;
            case "declaracion":
                Declaracion declaracion = new Declaracion();
                declaracion.setTexto(txtAreaSelection.getText());
                position = datosDeclaraciones.indexOf(declaracionActual);
                datosDeclaraciones.set(position, declaracion);
                break;
            case "faldon":
                Faldon faldon = new Faldon();
                faldon.setTexto(txtAreaSelection.getText());
                if (fieldSelection1.getText() == null) {
                    faldon.setTipo(Tipo.SIMPLE);
                } else {
                    faldon.setTitular(fieldSelection1.getText());
                    faldon.setTipo(Tipo.DOBLE);
                }
                position = datosFaldon.indexOf(faldonActual);
                datosFaldon.set(position, faldon);
                break;
            case "localizacion":
                Localizacion localizacion = new Localizacion();
                localizacion.setTexto(txtAreaSelection.getText());
                if (fieldSelection1.getText() == null) {
                    localizacion.setTipo(Tipo.SIMPLE);
                } else {
                    localizacion.setTitulo(fieldSelection1.getText());
                    localizacion.setTipo(Tipo.DOBLE);
                }
                position = datosLocalizacion.indexOf(localizacionActual);
                datosLocalizacion.set(position, localizacion);
                break;
            default:
                System.out.println("Ha salido default al modificar");
                break;
        }
    }


    //////////////////ELEMENTOS DE MENÚ///////////////////////////////////

    @FXML
    void borrarCrawlAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de borrado");
        alert.setContentText("¿Está seguro de que desea vaciar la tabla de Crawl?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            datosDeclaraciones.clear();
        }
    }

    @FXML
    void borrarFaldonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación de borrado");
        alert.setContentText("¿Está seguro de que desea vaciar la tabla de Faldón?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            datosFaldon.clear();
        }
    }

    @FXML
    void menuCloseAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Salir");
        alert.setContentText("¿Está seguro de que desea salir de la aplicación?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            guardarYsalir();
            exit(0);
        }
    }

    @FXML
    void menuGuardarAction(ActionEvent event) {
        vaciarBD();
        guardar();
    }

    /////////////////////////////////////////////////////////////////////////
    private void vaciarBD() {
        var preDel = new ArrayList<Presentador>();
        var preUpdate = new ArrayList<Presentador>();
        var personajeDel = new ArrayList<Personaje>();
        var personajeUpdate = new ArrayList<Personaje>();
        var decDel = new ArrayList<Declaracion>();
        var decUpdate = new ArrayList<Declaracion>();
        var falDel = new ArrayList<Faldon>();
        var falUpdate = new ArrayList<Faldon>();
        var eqDel = new ArrayList<Equipo>();

        presentadoresBD.forEach(x -> {
            if (!datosPresentador.contains(x)) {
                preDel.add(x);
            } else {
                if (datosPresentador.get(presentadoresBD.indexOf(x)).getPosition() != x.getPosition()) {
                    preUpdate.add(x);
                }
            }
        });

        preDel.forEach(x -> {
            presentadorController.deletePresentador(x);
        });

        preUpdate.forEach(presentador -> {
            presentadorController.updatePresentador(presentador);
        });

        personajesBD.forEach(x -> {
            if (!datosPersonaje.contains(x)) {
                personajeDel.add(x);
            } else {
                if (datosPersonaje.get(personajesBD.indexOf(x)).getPosition() != x.getPosition()) {
                    personajeUpdate.add(x);
                }
            }
        });

        personajeDel.forEach(x -> {
            personajeController.deletePersonaje(x);
        });

        personajeUpdate.forEach(personaje -> {
            personajeController.updatePersonaje(personaje);
        });

        declaracionesBD.forEach(x -> {
            if (!datosDeclaraciones.contains(x)) {
                decDel.add(x);
            } else {
                if (datosDeclaraciones.get(declaracionesBD.indexOf(x)).getPosition() != x.getPosition()) {
                    decUpdate.add(x);
                }
            }
        });

        decDel.forEach(x -> {
            declaracionController.deleteDeclaracion(x);
        });

        decUpdate.forEach(dec -> {
            declaracionController.updateDeclaracion(dec);
        });

        faldonesBD.forEach(x -> {
            if (!datosFaldon.contains(x)) {
                falDel.add(x);
            } else {
                if (datosFaldon.get(faldonesBD.indexOf(x)).getPosition() != x.getPosition()) {
                    falUpdate.add(x);
                }
            }
        });

        falDel.forEach(x -> {
            faldonController.deleteFaldon(x);
        });

        falUpdate.forEach(fal -> {
            faldonController.updateFaldon(fal);
        });

        equiposBD.forEach(x -> {
            if (!datosEquipo.contains(x)) {
                eqDel.add(x);
            }
        });
        eqDel.forEach(x -> {
            equipoController.deleteEquipo(x);
        });

    }

    private void guardar() {
        var preAdd = new ArrayList<Presentador>();
        var perAdd = new ArrayList<Personaje>();
        var decAdd = new ArrayList<Declaracion>();
        var falAdd = new ArrayList<Faldon>();
        var eqAdd = new ArrayList<Equipo>();

        datosPresentador.forEach(x -> {
            if (!presentadoresBD.contains(x)) {
                preAdd.add(x);
            }
        });

        preAdd.forEach(presentador -> {
            presentadorController.postPresentador(presentador);
        });

        datosPersonaje.forEach(x -> {
            if (!personajesBD.contains(x)) {
                perAdd.add(x);
            }
        });

        perAdd.forEach(personaje -> {
            personajeController.postPersonaje(personaje);
        });

        datosDeclaraciones.forEach(x -> {
            if (!declaracionesBD.contains(x)) {
                decAdd.add(x);
            }
        });

        decAdd.forEach(dec -> {
            declaracionController.postDeclaracion(dec);
        });

        datosFaldon.forEach(x -> {
            if (!faldonesBD.contains(x)) {
                falAdd.add(x);
            }
        });

        falAdd.forEach(fal -> {
            faldonController.postFaldon(fal);
        });

        datosEquipo.forEach(x -> {
            if (!equiposBD.contains(x)) {
                eqAdd.add(x);
            }
        });

        eqAdd.forEach(eq -> {
            equipoController.postEquipo(eq);
        });

    }

    private void guardarYsalir() {
        vaciarBD();
        guardar();

        System.out.println("CERRANDO APLICACIÓN");
        c.desconectar();
    }

    public void shutdown() {
        guardarYsalir();
    }

}
