package br.com.rotatepairing.commands;

import br.com.rotatepairing.*;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.Arrays.asList;

@CommandLine.Command(description = "Prints suggestion of pairs for the next team iteration.",
        name = "next", mixinStandardHelpOptions = true)
public class NextCommand implements Callable<Void> {

    @CommandLine.Option(names = "-s", description = "save suggestion to team history", defaultValue = "false")
    boolean save;

    public static void main(String[] args) {
        CommandLine.call(new NextCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        Screen screen = EnvironmentHolder.getEnvironment().getScreen();
        screen.show("Suggestion of pairs to the next team iteration:");

        long startTimestamp = System.currentTimeMillis();

        SolverFactory<PairSchedule> solverFactory = SolverFactory
                .createFromXmlResource("pair-schedule.xml");
        Solver<PairSchedule> solver = solverFactory.buildSolver();

        List<String> people = asList(PeopleHistory.load().getCurrentPeople());
        List<String> roles = asList(RoleHistory.load().getCurrentRoles());
        PairingHistory pairingHistory = PairingHistory.load();

        PairSchedule unsolvedCourseSchedule = new PairSchedule();
        unsolvedCourseSchedule.setPairingAffinity(pairingHistory.buildPairAffinityList());
        unsolvedCourseSchedule.setRoleAffinity(pairingHistory.buildRoleAffinityList());
        unsolvedCourseSchedule.setPeople(people);
        unsolvedCourseSchedule.setRoles(roles);
        unsolvedCourseSchedule.setPairs(createPairsAccordingToNumberOfPeople(people.size()));

        PairSchedule solvedCourseSchedule = solver.solve(unsolvedCourseSchedule);

        solvedCourseSchedule.print(screen);

        long endTimestamp = System.currentTimeMillis();
        screen.show("Calculation took <%s> ms", (endTimestamp - startTimestamp));

        return null;
    }

    private List<Pair> createPairsAccordingToNumberOfPeople(int numberOfPeople) {
        int numberOfPairs = numberOfPeople / 2;
        if (numberOfPeople % 2 != 0) {
            numberOfPairs++;
        }

        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < numberOfPairs; i++) {
            pairs.add(new Pair());
        }
        return pairs;
    }
}
