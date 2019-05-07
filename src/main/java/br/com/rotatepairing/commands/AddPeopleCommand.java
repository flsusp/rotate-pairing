package br.com.rotatepairing.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(description = "Adds a person to the team.",
        name = "add", mixinStandardHelpOptions = true)
public class AddPeopleCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new AddPeopleCommand(), args);
    }

    @Override
    public Void call() {
        return null;
    }
}
