package mggcode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name = "Presentador.findAll", query = "SELECT d FROM Presentador d")
public class Presentador {
    private int id;
    private int position;
    private String nombre;
    private String cargo;
    private Tipo tipo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    @Basic
    @Column(name = "cargo")
    public String getCargo() {
        return cargo;
    }

    @Basic
    @Column(name = "position")
    public int getPosition() {
        return position;
    }

    public Tipo getTipo() {
        return tipo;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
