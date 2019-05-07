package br.com.rotatepairing.commands;

import br.com.rotatepairing.PeopleHistory;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(description = "Remove person from the team.",
        name = "rm", mixinStandardHelpOptions = true)
public class RemovePeopleCommand implements Callable<Void> {

    @CommandLine.Parameters(arity = "1..*", description = "at least one person name")
    String[] people;

    public static void main(String[] args) {
        CommandLine.call(new RemovePeopleCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        PeopleHistory.load()
                .removeAll(people);
        return null;
    }
}