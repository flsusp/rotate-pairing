package br.com.rotatepairing;

public class PairAffinity {

    private final String firstPerson;
    private final String secondPerson;
    private final double score;

    public PairAffinity(String firstPerson, String secondPerson, double score) {
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
        this.score = score;
    }

    public String getFirstPerson() {
        return firstPerson;
    }

    public String getSecondPerson() {
        return secondPerson;
    }

    public int getNormalizedScore() {
        return (int) (score / 10.0);
    }
}
