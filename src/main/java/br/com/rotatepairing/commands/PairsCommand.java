package br.com.rotatepairing.commands;

import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.PrintStreamScreenAdapter;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.HelpCommand;

@Command(subcommands = {
        HelpCommand.class,
        InitCommand.class,
        PeopleCommand.class,
        AffinityCommand.class,
        NextCommand.class
})
public class PairsCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new PairsCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        CommandLine.usage(this, new PrintStreamScreenAdapter(EnvironmentHolder.getEnvironment().getScreen()));
        return null;
    }
}
