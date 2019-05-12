package br.com.rotatepairing.commands;

import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.PairingHistory;
import br.com.rotatepairing.PeopleHistory;
import br.com.rotatepairing.Screen;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

import static java.util.Arrays.asList;

@CommandLine.Command(description = "Prints affinity for people and roles according to the pairing history.",
        name = "roles", mixinStandardHelpOptions = true)
public class RolesAffinityCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new RolesAffinityCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        Screen screen = EnvironmentHolder.getEnvironment().getScreen();
        screen.show("List of people and roles affinity. The higher the affinity the more probable to have this\nperson with this role in the next team iterations.");
        PairingHistory.load()
                .printRolesAffinity(screen, asList(PeopleHistory.load().getCurrentPeople()));

        return null;
    }
}
