package br.com.rotatepairing;

import java.util.stream.Stream;

public class PairAffinityBuilder implements Comparable<PairAffinityBuilder> {

    private final String firstPerson;
    private final String secondPerson;
    private double score = 100.0;

    public PairAffinityBuilder(String firstPerson, String secondPerson) {
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
    }

    public void registerPairingLog(long numberOfWeeks) {
        this.score = this.score - (100.0 / (double) numberOfWeeks);
    }

    public Stream<PairAffinity> build() {
        return Stream.of(new PairAffinity(firstPerson, secondPerson, score), new PairAffinity(secondPerson, firstPerson, score));
    }

    public int getNormalizedScore() {
        return (int) (score / 10.0);
    }

    public String getFirstPerson() {
        return firstPerson;
    }

    public String getSecondPerson() {
        return secondPerson;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(PairAffinityBuilder otherPair) {
        return (-1) * Integer.valueOf(getNormalizedScore()).compareTo(otherPair.getNormalizedScore());
    }
}
