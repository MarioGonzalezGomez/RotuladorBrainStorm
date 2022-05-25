package mggcode.utiles.comparators;

import mggcode.entity.Declaracion;

import java.util.Comparator;

public class CompararDeclaracionPorId implements Comparator<Declaracion> {

    @Override
    public int compare(Declaracion o1, Declaracion o2) {
        return o1.getPosition() - o2.getPosition();
    }
}
