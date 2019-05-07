package br.com.rotatepairing.commands;

import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.PrintStreamScreenAdapter;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
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
    public Void call() throws IOException {
        CommandLine.usage(this, new PrintStreamScreenAdapter(EnvironmentHolder.getEnvironment().getScreen()));
        return null;
    }
}
