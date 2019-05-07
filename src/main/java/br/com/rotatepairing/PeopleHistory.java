package br.com.rotatepairing;

import br.com.rotatepairing.commands.InitCommand;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class PeopleHistory {

    public static final String CONFIG_FILE = "/people.history";

    public static PeopleHistory load() {
        return new PeopleHistory();
    }

    public void addAll(String[] peopleToAdd) throws IOException {
        String[] currentPeople = getCurrentPeople();
        peopleToAdd = ArrayUtils.removeElements(peopleToAdd, currentPeople);

        if (peopleToAdd.length > 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(InitCommand.CONFIG_DIR + CONFIG_FILE, true))) {
                writer.write(Stream.of(peopleToAdd)
                        .map(person -> "+" + person)
                        .collect(joining("\n")));
                writer.newLine();
            }
        }
    }

    public String[] getCurrentPeople() throws IOException {
        Set<String> people = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(InitCommand.CONFIG_DIR + CONFIG_FILE))) {
            String command;
            while ((command = reader.readLine()) != null) {
                if (command.startsWith("+")) {
                    people.add(command.substring(1));
                } else if (command.startsWith("-")) {
                    people.remove(command.substring(1));
                }
            }
        }
        return people.toArray(new String[0]);
    }

    public void removeAll(String[] peopleToRemove) throws IOException {
        if (peopleToRemove.length > 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(InitCommand.CONFIG_DIR + CONFIG_FILE, true))) {
                writer.write(Stream.of(peopleToRemove)
                        .map(person -> "-" + person)
                        .collect(joining("\n")));
                writer.newLine();
            }
        }
    }
}
