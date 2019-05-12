package br.com.rotatepairing;

public class RoleAffinity {

    private final String role;
    private final String person;
    private final double score;

    public RoleAffinity(String role, String person, double score) {
        this.role = role;
        this.person = person;
        this.score = score;
    }

    public String getRole() {
        return role;
    }

    public String getPerson() {
        return person;
    }

    public int getNormalizedScore() {
        return (int) (score / 15.0);
    }
}
