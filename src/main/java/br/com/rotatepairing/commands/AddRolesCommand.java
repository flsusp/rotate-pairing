package br.com.rotatepairing.commands;

import br.com.rotatepairing.EnvironmentHolder;
import br.com.rotatepairing.RoleHistory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

@Command(description = "Adds a role to the team.",
        name = "add", mixinStandardHelpOptions = true)
public class AddRolesCommand implements Callable<Void> {

    private static final String ROLE_NAME_REGEX = "^[a-zA-Z][a-zA-Z0-9\\-_.]*$";
    private static final Pattern ROLE_NAME_PATTERN = Pattern.compile(ROLE_NAME_REGEX);

    @Parameters(arity = "1..*", description = "at least one role name matching regular expression " + ROLE_NAME_REGEX)
    String[] roles;

    public static void main(String[] args) {
        CommandLine.call(new AddRolesCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        if (validateRoleNames()) {
            RoleHistory.load()
                    .addAll(roles);
        }

        return null;
    }

    private boolean validateRoleNames() {
        boolean errorFound = false;
        for (String role : roles) {
            if (!ROLE_NAME_PATTERN.matcher(role).matches()) {
                EnvironmentHolder.getEnvironment().getScreen()
                        .show("Role name should match regular expression " + ROLE_NAME_REGEX + ". Value '" + role + "' is not considered valid.");
                errorFound = true;
            }
        }
        return !errorFound;
    }
}
