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

    @CommandLine.Option(names = {"-s", "--save"}, description = "save suggestion to pairing history", defaultValue = "false")
    boolean save;

    @CommandLine.Option(names = {"-p", "--number-of-pairs"}, description = "number of pairs to generate", defaultValue = "-1")
    int numberOfPairs;

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

        PairSchedule unsolvedPairSchedule = new PairSchedule();
        unsolvedPairSchedule.setPairingAffinity(pairingHistory.buildPairAffinityList());
        unsolvedPairSchedule.setRoleAffinity(pairingHistory.buildRoleAffinityList());
        unsolvedPairSchedule.setPeople(people);
        unsolvedPairSchedule.setRoles(roles);

        if (numberOfPairs <= 0) {
            unsolvedPairSchedule.setPairs(createPairsAccordingToNumberOfPeople(people.size()));
        } else {
            unsolvedPairSchedule.setPairs(createPairsListOfSize(numberOfPairs));
        }

        PairSchedule solvedPairSchedule = solver.solve(unsolvedPairSchedule);

        solvedPairSchedule.print(screen);

        long endTimestamp = System.currentTimeMillis();
        screen.show("Calculation took <%s> ms", (endTimestamp - startTimestamp));

        if (save) {
            PairingHistory.save(solvedPairSchedule.getPairs());
        }

        return null;
    }

    private List<Pair> createPairsAccordingToNumberOfPeople(int numberOfPeople) {
        int numberOfPairs = numberOfPeople / 2;
        if (numberOfPeople % 2 != 0) {
            numberOfPairs++;
        }

        return createPairsListOfSize(numberOfPairs);
    }

    private List<Pair> createPairsListOfSize(int numberOfPairs) {
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < numberOfPairs; i++) {
            pairs.add(new Pair());
        }
        return pairs;
    }
}
