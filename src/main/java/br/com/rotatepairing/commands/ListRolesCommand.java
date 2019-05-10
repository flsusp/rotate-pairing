package br.com.rotatepairing.commands;

import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.RoleHistory;
import br.com.rotatepairing.Screen;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Command(description = "List the roles of the pairs in the team.",
        name = "ls", mixinStandardHelpOptions = true)
public class ListRolesCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new ListRolesCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        Screen screen = EnvironmentHolder.getEnvironment().getScreen();

        Stream.of(RoleHistory.load().getCurrentRoles())
                .forEach(role -> screen.show("> " + role));

        return null;
    }
}
