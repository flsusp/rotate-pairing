package br.com.rotatepairing.commands;

import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.PairingHistory;
import br.com.rotatepairing.Screen;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(description = "Prints affinity for new pairings according to the pairing history.",
        name = "pairs", mixinStandardHelpOptions = true)
public class PairsAffinityCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new PairsAffinityCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        Screen screen = EnvironmentHolder.getEnvironment().getScreen();
        screen.show("List of possible pairs and its affinity. The higher the affinity the more probable to have this\npair in the next team iterations.");
        PairingHistory.load()
                .printPairsAffinity(screen);

        return null;
    }
}
