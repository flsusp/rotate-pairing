package br.com.rotatepairing.commands;

import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.PeopleHistory;
import br.com.rotatepairing.Screen;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Command(description = "List the people of the team.",
        name = "ls", mixinStandardHelpOptions = true)
public class ListPeopleCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new ListPeopleCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        Screen screen = EnvironmentHolder.getEnvironment().getScreen();

        Stream.of(PeopleHistory.load().getCurrentPeople())
                .forEach(person -> screen.show("> " + person));

        return null;
    }
}
