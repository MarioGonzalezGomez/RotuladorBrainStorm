package mggcode.utiles.comparators;

import mggcode.entity.Faldon;

import java.util.Comparator;

public class CompararFaldonPorId implements Comparator<Faldon> {

    @Override
    public int compare(Faldon o1, Faldon o2) {
        return o1.getPosition() - o2.getPosition();
    }
}