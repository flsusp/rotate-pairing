package br.com.rotatepairing.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(description = "List the people of the team.",
        name = "ls", mixinStandardHelpOptions = true)
public class ListPeopleCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new ListPeopleCommand(), args);
    }

    @Override
    public Void call() {
        return null;
    }
}
