package br.com.rotatepairing.commands;

import br.com.rotatepairing.PeopleHistory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

@Command(description = "Adds a person to the team.",
        name = "add", mixinStandardHelpOptions = true)
public class AddPeopleCommand implements Callable<Void> {

    private static final String PERSON_NAME_REGEX = "^[a-zA-Z][a-zA-Z0-9\\-_]*$";
    private static final Pattern PERSON_NAME_PATTERN = Pattern.compile(PERSON_NAME_REGEX);

    @Parameters(arity = "1..*", description = "at least one person name matching regular expression " + PERSON_NAME_REGEX)
    String[] people;

    public static void main(String[] args) {
        CommandLine.call(new AddPeopleCommand(), args);
    }

    @Override
    public Void call() throws IOException {
        if (validatePeopleNames()) {
            PeopleHistory.load()
                    .addAll(people);
        }

        return null;
    }

    private boolean validatePeopleNames() {
        boolean errorFound = false;
        for (String person : people) {
            if (!PERSON_NAME_PATTERN.matcher(person).matches()) {
                System.err.println("Person name should match regular expression " + PERSON_NAME_REGEX + ". Value '" + person + "' is not considered valid.");
                errorFound = true;
            }
        }
        return !errorFound;
    }
}
