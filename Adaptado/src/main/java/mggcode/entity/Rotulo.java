package mggcode.entity;

import javafx.beans.property.SimpleStringProperty;


public class Rotulo {
    private SimpleStringProperty textoPrincipal;
    private SimpleStringProperty textoSecundario;


    public Rotulo() {
        textoPrincipal = new SimpleStringProperty();
        textoSecundario = new SimpleStringProperty();
    }


    public String getTextoPrincipal() {
        return textoPrincipal.get();
    }

    public SimpleStringProperty textoPrincipalProperty() {
        return textoPrincipal;
    }

    public void setTextoPrincipal(String textoPrincipal) {
        this.textoPrincipal.set(textoPrincipal);
    }

    public String getTextoSecundario() {
        return textoSecundario.get();
    }

    public SimpleStringProperty textoSecundarioProperty() {
        return textoSecundario;
    }

    public void setTextoSecundario(String textoSecundario) {
        this.textoSecundario.set(textoSecundario);
    }

}
