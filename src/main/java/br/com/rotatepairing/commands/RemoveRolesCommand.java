package br.com.rotatepairing.commands;

import br.com.rotatepairing.RoleHistory;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(description = "Remove role from the team.",
        name = "rm", mixinStandardHelpOptions = true)
public class RemoveRolesCommand implements Callable<Void> {

    @CommandLine.Parameters(arity = "1..*", description = "at least one role name")
    String[] people;

    public static void main(String[] args) {
        CommandLine.call(new RemoveRolesCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        RoleHistory.load()
                .removeAll(people);
        return null;
    }
}