package br.com.rotatepairing;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;

public class PairingHistory {

    public static final String CONFIG_FILE = "/pairing.history";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Map<String, PairAffinityBuilder> pairsAffinity;
    private Map<String, RoleAffinityBuilder> roleAffinity;

    public PairingHistory(Map<String, PairAffinityBuilder> pairsAffinity, Map<String, RoleAffinityBuilder> roleAffinity) {
        this.pairsAffinity = pairsAffinity;
        this.roleAffinity = roleAffinity;
    }

    public static PairingHistory load() throws IOException {
        Map<String, PairAffinityBuilder> pairsAffinity = generatePairs(PeopleHistory.load().getCurrentPeople());
        Map<String, RoleAffinityBuilder> roleAffinity = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(EnvironmentHolder.getEnvironment().getConfigurationDirectory() + CONFIG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");

                String pilot = attributes[0].trim();
                String copilot = attributes[1].trim();
                String role = attributes[2].trim();
                LocalDate week = LocalDate.parse(attributes[3].trim(), DATE_TIME_FORMATTER);
                long numberOfWeeks = calculateNumberOfWeeksFor(week);

                String affinityKey = createAffinityKey(pilot, copilot);
                if (pairsAffinity.containsKey(affinityKey)) {
                    pairsAffinity.get(affinityKey).registerPairingLog(numberOfWeeks);
                }

                registerRoleAffinity(roleAffinity, pilot, role, numberOfWeeks);
                registerRoleAffinity(roleAffinity, copilot, role, numberOfWeeks);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new PairingHistory(pairsAffinity, roleAffinity);
    }

    private static long calculateNumberOfWeeksFor(LocalDate week) {
        long numberOfWeeks = ChronoUnit.WEEKS.between(week, LocalDate.now());
        if (numberOfWeeks > 99L) {
            numberOfWeeks = 99L;
        }
        if (numberOfWeeks < 1L) {
            numberOfWeeks = 1L;
        }
        return numberOfWeeks;
    }

    private static void registerRoleAffinity(Map<String, RoleAffinityBuilder> roleAffinity, String person, String role, long numberOfWeeks) {
        if (!StringUtils.isEmpty(person)) {
            if (!roleAffinity.containsKey(person)) {
                roleAffinity.put(person, new RoleAffinityBuilder(person));
            }
            roleAffinity.get(person).registerRoleLog(role, numberOfWeeks);
        }
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

    public static void save(List<Pair> pairs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EnvironmentHolder.getEnvironment().getConfigurationDirectory() + CONFIG_FILE, true))) {
            for (Pair pair : pairs) {
                writer.write(pair.pilot + "," + pair.copilot + "," + pair.role + "," + DATE_TIME_FORMATTER.format(LocalDate.now()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printPairsAffinity(Screen screen) {
        pairsAffinity.values().stream()
                .sorted()
                .forEach(pairAffinity -> screen.show("> %20s + %20s => %3d", pairAffinity.getFirstPerson(), pairAffinity.getSecondPerson(), pairAffinity.getNormalizedScore()));
    }

    public List<PairAffinity> buildPairAffinityList() {
        return pairsAffinity.values().stream()
                .flatMap(PairAffinityBuilder::build)
                .collect(toList());
    }

    public List<RoleAffinity> buildRoleAffinityList() {
        return roleAffinity.values().stream()
                .flatMap(RoleAffinityBuilder::build)
                .collect(toList());
    }

    public void printRolesAffinity(Screen screen, List<String> people) {
        roleAffinity.values().stream()
                .flatMap(RoleAffinityBuilder::build)
                .filter(roleAffinity -> people.contains(roleAffinity.getPerson()))
                .sorted(comparingDouble(RoleAffinity::getNormalizedScore).reversed()
                        .thenComparing(RoleAffinity::getPerson)
                        .thenComparing(RoleAffinity::getRole))
                .forEach(roleAffinity -> screen.show("> %20s + %20s => %3d", roleAffinity.getPerson(), roleAffinity.getRole(), roleAffinity.getNormalizedScore()));
    }
}
