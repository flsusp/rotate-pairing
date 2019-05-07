package br.com.rotatepairing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class PairingHistory {

    public static final String CONFIG_FILE = "/pairing.history";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Map<String, PairAffinityBuilder> pairsAffinity;

    public PairingHistory(Map<String, PairAffinityBuilder> pairsAffinity) {
        this.pairsAffinity = pairsAffinity;
    }

    public static PairingHistory load() throws IOException {
        Map<String, PairAffinityBuilder> pairsAffinity = generatePairs(PeopleHistory.load().getCurrentPeople());

        try (BufferedReader reader = new BufferedReader(new FileReader(EnvironmentHolder.getEnvironment().getConfigurationDirectory() + CONFIG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");

                String pilot = attributes[0].trim();
                String copilot = attributes[1].trim();
                LocalDate week = LocalDate.parse(attributes[2].trim(), DATE_TIME_FORMATTER);

                String affinityKey = createAffinityKey(pilot, copilot);
                if (pairsAffinity.containsKey(affinityKey)) {
                    pairsAffinity.get(affinityKey).registerPairingLog(week);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new PairingHistory(pairsAffinity);
    }

    private static String createAffinityKey(String firstPerson, String secondPerson) {
        if (firstPerson.compareTo(secondPerson) > 0) {
            return firstPerson + ":" + secondPerson;
        }
        return secondPerson + ":" + firstPerson;
    }

    private static Map<String, PairAffinityBuilder> generatePairs(String[] people) {
        Map<String, PairAffinityBuilder> pairAffinityMap = new HashMap<>();

        Stream.of(people).forEach(person -> pairAffinityMap.put(createAffinityKey(person, ""), new PairAffinityBuilder(person, "")));
        Stream.of(people).forEach(firstPerson ->
                Stream.of(people)
                        .filter(secondPerson -> !Objects.equals(firstPerson, secondPerson))
                        .forEach(copilot -> pairAffinityMap.put(createAffinityKey(firstPerson, copilot), new PairAffinityBuilder(firstPerson, copilot))));

        return pairAffinityMap;
    }

    public void printAffinity(Screen screen) {
        pairsAffinity.values().stream()
                .sorted()
                .forEach(pairAffinity -> screen.show("> %32s + %32s => %3d", pairAffinity.getFirstPerson(), pairAffinity.getSecondPerson(), pairAffinity.getNormalizedScore()));
    }

    public List<PairAffinity> buildPairAffinityList() {
        return pairsAffinity.values().stream()
                .flatMap(PairAffinityBuilder::build)
                .collect(toList());
    }
}
