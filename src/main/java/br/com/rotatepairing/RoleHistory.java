package br.com.rotatepairing;

import java.io.IOException;

public class RoleHistory extends SimpleHistory {

    public static final String CONFIG_FILE = "/roles.history";

    public static RoleHistory load() {
        return new RoleHistory();
    }

    public void addAll(String[] rolesToAdd) throws IOException {
        super.addAll(rolesToAdd, CONFIG_FILE);
    }

    public String[] getCurrentRoles() throws IOException {
        return super.getCurrentValues(CONFIG_FILE);
    }

    public void removeAll(String[] rolesToRemove) throws IOException {
        super.removeAll(rolesToRemove, CONFIG_FILE);
    }
}
