package br.com.rotatepairing.commands;

import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.PrintStreamScreenAdapter;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(description = "Set of commands to print the affinity for pair combinations.", name = "affinity",
        subcommands = {
                CommandLine.HelpCommand.class,
                PairsAffinityCommand.class,
                RolesAffinityCommand.class
        })
public class AffinityCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new AffinityCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        CommandLine.usage(this, new PrintStreamScreenAdapter(EnvironmentHolder.getEnvironment().getScreen()));
        return null;
    }
}
