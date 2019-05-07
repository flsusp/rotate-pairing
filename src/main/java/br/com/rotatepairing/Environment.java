package br.com.rotatepairing;

public class Environment {

    private final String homeDirectory;
    private final Screen screen;

    public Environment(String homeDirectory, Screen screen) {
        this.homeDirectory = homeDirectory;
        this.screen = screen;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public String getConfigurationDirectory() {
        return getHomeDirectory() + "/.pairs";
    }

    public Screen getScreen() {
        return screen;
    }
}

class DefaultEnvironment extends Environment {

    public DefaultEnvironment() {
        super(".", message -> System.out.println(message));
    }
}