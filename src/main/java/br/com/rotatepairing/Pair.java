package br.com.rotatepairing;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Pair {

    public String pilot;
    public String copilot;

    @PlanningVariable(valueRangeProviderRefs = {"availablePilots"})
    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    @PlanningVariable(valueRangeProviderRefs = {"availableCopilots"}, nullable = true)
    public String getCopilot() {
        return copilot;
    }

    public void setCopilot(String copilot) {
        this.copilot = copilot;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "pilot='" + pilot + '\'' +
                ", copilot='" + copilot + '\'' +
                '}';
    }
}
