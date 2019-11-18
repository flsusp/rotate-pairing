package br.com.rotatepairing;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

public class PairComparator implements Comparator<Pair> {

    @Override
    public int compare(Pair pair1, Pair pair2) {
        return new CompareToBuilder()
                .append(pair1.pilot, pair2.pilot)
                .append(pair1.copilot, pair2.copilot)
                .build();
    }
}
