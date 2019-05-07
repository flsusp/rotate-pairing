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

    private List<String> pilots;
    private List<String> copilots;
    private List<PairAffinity> pairingHistory;
    private List<Pair> pairs;
    private HardSoftScore score;

    @ProblemFactCollectionProperty
    public List<PairAffinity> getPairingHistory() {
        return pairingHistory;
    }

    public void setPairingHistory(List<PairAffinity> pairingHistory) {
        this.pairingHistory = pairingHistory;
    }

    @PlanningEntityCollectionProperty
    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    @ValueRangeProvider(id = "availablePilots")
    @ProblemFactCollectionProperty
    public List<String> getPilots() {
        return pilots;
    }

    public void setPilots(List<String> pilots) {
        this.pilots = pilots;
    }

    @ValueRangeProvider(id = "availableCopilots")
    @ProblemFactCollectionProperty
    public List<String> getCopilots() {
        return copilots;
    }

    public void setCopilots(List<String> copilots) {
        this.copilots = copilots;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public void print() {
        this.pairs.stream()
                .map(Pair::toString)
                .forEach(pair -> System.out.println(pair));
        System.out.println(this.score);
    }
}
