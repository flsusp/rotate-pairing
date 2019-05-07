package br.com.rotatepairing.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(description = "Remove person from the team.",
        name = "rm", mixinStandardHelpOptions = true)
public class RemovePeopleCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new RemovePeopleCommand(), args);
    }

    @Override
    public Void call() {
        return null;
    }
}