package mggcode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@NamedQuery(name = "Faldon.findAll", query = "SELECT d FROM Faldon d")
public class Faldon {
    private int id;
    private int position;
    private String titular;
    private String texto;
    private Tipo tipo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    @Basic
    @Column(name = "titular")
    public String getTitular() {
        return titular;
    }

    @Basic
    @Column(name = "texto")
    public String getTexto() {
        return texto;
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Basic
    @Column(name = "position")
    public int getPosition() {
        return position;
    }
}
