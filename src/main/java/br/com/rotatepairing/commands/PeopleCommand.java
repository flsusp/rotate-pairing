package br.com.rotatepairing.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(description = "Manage the people of the team.", name = "people",
        subcommands = {
                AddPeopleCommand.class,
                RemovePeopleCommand.class,
                ListPeopleCommand.class
        })
public class PeopleCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new PeopleCommand(), args);
    }

    @Override
    public Void call() {
        CommandLine.usage(this, System.out);
        return null;
    }
}
