package mggcode.utiles.comparators;

import mggcode.entity.Presentador;

import java.util.Comparator;

public class CompararPresentadorPorId implements Comparator<Presentador> {

    @Override
    public int compare(Presentador o1, Presentador o2) {
        return o1.getPosition() - o2.getPosition();
    }
}
