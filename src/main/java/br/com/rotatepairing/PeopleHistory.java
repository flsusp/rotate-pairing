package br.com.rotatepairing;

import java.io.IOException;

public class PeopleHistory extends SimpleHistory {

    public static final String CONFIG_FILE = "/people.history";

    public static PeopleHistory load() {
        return new PeopleHistory();
    }

    public void addAll(String[] peopleToAdd) throws IOException {
        super.addAll(peopleToAdd, CONFIG_FILE);
    }

    public String[] getCurrentPeople() throws IOException {
        return super.getCurrentValues(CONFIG_FILE);
    }

    public void removeAll(String[] peopleToRemove) throws IOException {
        super.removeAll(peopleToRemove, CONFIG_FILE);
    }
}
