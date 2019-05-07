package br.com.rotatepairing.commands;

import br.com.rotatepairing.PeopleHistory;
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
        Stream.of(PeopleHistory.load().getCurrentPeople())
                .forEach(person -> System.out.println("> " + person));
        return null;
    }
}
