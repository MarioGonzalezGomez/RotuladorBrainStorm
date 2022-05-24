package mggcode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedQuery;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name = "Equipo.findAll", query = "SELECT d FROM Equipo d")
public class Equipo {
    private int id;
    private String nombre;
    private String logo;
    private Collection<Personaje> personajes;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @OneToMany(mappedBy = "equipo")
    public Collection<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(Collection<Personaje> personajes) {
        this.personajes = personajes;
    }

    @Override
    public String toString() {
        StringBuilder conmayus = new StringBuilder();
        if (nombre.contains(" ")) {
            List<String> list = List.of(nombre.split(" "));
            list.forEach(palabra -> {
                palabra = palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
            });
            for (String x : list) {
                conmayus.append(x).append(" ");
            }
            conmayus.delete(conmayus.length() - 1, (conmayus.length()));
        } else {
            conmayus = new StringBuilder(nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase());
        }
        return conmayus.toString();
    }
}
