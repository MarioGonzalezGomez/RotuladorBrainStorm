package mggcode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name = "Personaje.findAll", query = "SELECT d FROM Personaje d")
@NamedQuery(name = "Personaje.porEquipoId", query = "SELECT e FROM Personaje e WHERE e.equipo.id = ?1")
public class Personaje {
    private int id;
    private int position;
    private String nombre;
    private String cargo;
    private Equipo equipo;
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

    @ManyToOne
    @JoinColumn(name = "equipo_id", referencedColumnName = "id", nullable = true)
    public Equipo getEquipo() {
        return equipo;
    }

    public Tipo getTipo() {return tipo;}


    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Basic
    @Column(name = "position")
    public int getPosition() {return position;}

    public void setPosition(int position) {this.position = position;}
}
