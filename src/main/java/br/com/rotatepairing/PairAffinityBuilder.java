package br.com.rotatepairing;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class PairAffinityBuilder implements Comparable<PairAffinityBuilder> {

    private final String firstPerson;
    private final String secondPerson;
    private double score = 100.0;

    public PairAffinityBuilder(String firstPerson, String secondPerson) {
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
    }

    public void registerPairingLog(LocalDate week) {
        double numberOfWeeks = ChronoUnit.WEEKS.between(week, LocalDate.now());
        if (numberOfWeeks > 99.0) {
            numberOfWeeks = 99.0;
        }
        if (numberOfWeeks < 1.0) {
            numberOfWeeks = 1.0;
        }
        this.score = this.score - (100.0 / numberOfWeeks);
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
