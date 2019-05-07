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
        screen.show("Suggestion of pairs the next team iteration:");

        SolverFactory<PairSchedule> solverFactory = SolverFactory
                .createFromXmlResource("pair-schedule.xml");
        Solver<PairSchedule> solver = solverFactory.buildSolver();

        List<String> people = asList(PeopleHistory.load().getCurrentPeople());

        PairSchedule unsolvedCourseSchedule = new PairSchedule();
        unsolvedCourseSchedule.setPairingHistory(PairingHistory.load().buildPairAffinityList());
        unsolvedCourseSchedule.setPilots(people);
        unsolvedCourseSchedule.setCopilots(people);
        unsolvedCourseSchedule.setPairs(createPairsAccordingToNumberOfPeople(people.size()));

        PairSchedule solvedCourseSchedule = solver.solve(unsolvedCourseSchedule);

        solvedCourseSchedule.print();

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
