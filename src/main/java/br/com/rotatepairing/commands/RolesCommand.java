package br.com.rotatepairing.commands;

import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.PrintStreamScreenAdapter;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(description = "Manage the roles of the pairs in the team.", name = "roles",
        subcommands = {
                AddRolesCommand.class,
                RemoveRolesCommand.class,
                ListRolesCommand.class
        })
public class RolesCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new RolesCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        CommandLine.usage(this, new PrintStreamScreenAdapter(EnvironmentHolder.getEnvironment().getScreen()));
        return null;
    }
}
