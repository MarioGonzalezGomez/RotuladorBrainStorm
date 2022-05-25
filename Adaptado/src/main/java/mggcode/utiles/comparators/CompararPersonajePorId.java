package mggcode.utiles.comparators;

import mggcode.entity.Personaje;

import java.util.Comparator;

public class CompararPersonajePorId implements Comparator<Personaje> {

    @Override
    public int compare(Personaje o1, Personaje o2) {
        return o1.getPosition() - o2.getPosition();
    }
}

