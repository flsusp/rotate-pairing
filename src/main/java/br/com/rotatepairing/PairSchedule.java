package br.com.rotatepairing;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@PlanningSolution
public class PairSchedule {

    private List<String> people;
    private List<String> roles;
    private List<PairAffinity> pairingAffinity;
    private List<RoleAffinity> roleAffinity;
    private List<Pair> pairs;
    private HardSoftScore score;

    @ValueRangeProvider(id = "availableRoles")
    @ProblemFactCollectionProperty
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @ProblemFactCollectionProperty
    public List<PairAffinity> getPairingAffinity() {
        return pairingAffinity;
    }

    public void setPairingAffinity(List<PairAffinity> pairingAffinity) {
        this.pairingAffinity = pairingAffinity;
    }

    @ProblemFactCollectionProperty
    public List<RoleAffinity> getRoleAffinity() {
        return roleAffinity;
    }

    public void setRoleAffinity(List<RoleAffinity> roleAffinity) {
        this.roleAffinity = roleAffinity;
    }

    @PlanningEntityCollectionProperty
    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    @ValueRangeProvider(id = "availablePeople")
    @ProblemFactCollectionProperty
    public List<String> getPeople() {
        return people;
    }

    public void setPeople(List<String> people) {
        this.people = people;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public void print(Screen screen) {
        this.pairs.stream()
                .map(Pair::toString)
                .forEach(pair -> screen.show(pair));
        screen.show(this.score.toString());
    }
}
