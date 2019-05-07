package br.com.rotatepairing;

public class Environment {

    private final String homeDirectory;

    public Environment(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public String getConfigurationDirectory() {
        return getHomeDirectory() + "/.pairs";
    }
}

class DefaultEnvironment extends Environment {

    public DefaultEnvironment() {
        super(".");
    }
}