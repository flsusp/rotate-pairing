package br.com.rotatepairing;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class SimpleHistory {

    public void addAll(String[] valuesToAdd, String configFile) throws IOException {
        String[] currentValues = getCurrentValues(configFile);
        valuesToAdd = ArrayUtils.removeElements(valuesToAdd, currentValues);

        if (valuesToAdd.length > 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnvironmentHolder.getEnvironment().getConfigurationDirectory() + configFile, true))) {
                writer.write(Stream.of(valuesToAdd)
                        .map(value -> "+" + value)
                        .collect(joining("\n")));
                writer.newLine();
            }
        }
    }

    public String[] getCurrentValues(String configFile) throws IOException {
        Set<String> values = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EnvironmentHolder.getEnvironment().getConfigurationDirectory() + configFile))) {
            String command;
            while ((command = reader.readLine()) != null) {
                if (command.startsWith("+")) {
                    values.add(command.substring(1));
                } else if (command.startsWith("-")) {
                    values.remove(command.substring(1));
                }
            }
        }
        return values.toArray(new String[0]);
    }

    public void removeAll(String[] peopleToRemove, String configFile) throws IOException {
        if (peopleToRemove.length > 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnvironmentHolder.getEnvironment().getConfigurationDirectory() + configFile, true))) {
                writer.write(Stream.of(peopleToRemove)
                        .map(value -> "-" + value)
                        .collect(joining("\n")));
                writer.newLine();
            }
        }
    }
}
