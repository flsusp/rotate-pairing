package br.com.rotatepairing;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.Comparator;

@PlanningEntity(difficultyComparatorClass = PairComparator.class)
public class Pair {

    public String pilot;
    public String copilot;
    public String role;

    @PlanningVariable(valueRangeProviderRefs = {"availableRoles"})
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @PlanningVariable(valueRangeProviderRefs = {"availablePeople"})
    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    @PlanningVariable(valueRangeProviderRefs = {"availablePeople"}, nullable = true)
    public String getCopilot() {
        return copilot;
    }

    public void setCopilot(String copilot) {
        this.copilot = copilot;
    }

    @Override
    public String toString() {
        return "> " + pilot + " + " + copilot + " (" + role + ")";
    }
}

