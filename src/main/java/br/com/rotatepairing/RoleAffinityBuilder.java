package br.com.rotatepairing;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RoleAffinityBuilder {

    private final String person;
    private final Map<String, Double> scoresByRole = new HashMap<>();

    public RoleAffinityBuilder(String person) {
        this.person = person;
    }

    public void registerRoleLog(String role, long numberOfWeeks) {
        if (!scoresByRole.containsKey(role)) {
            scoresByRole.put(role, 100.0);
        }
        double actualScore = scoresByRole.get(role);
        double newScore = actualScore - (100.0 / (double) numberOfWeeks);
        scoresByRole.put(role, newScore);
    }

    public Stream<RoleAffinity> build() {
        return scoresByRole.entrySet().stream()
                .map(entry -> new RoleAffinity(entry.getKey(), person, entry.getValue()));
    }
}
