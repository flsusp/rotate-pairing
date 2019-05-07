package br.com.rotatepairing;

public class EnvironmentHolder {

    private static Environment environment = new DefaultEnvironment();

    public static Environment getEnvironment() {
        return environment;
    }

    public static void setEnvironment(Environment environment) {
        EnvironmentHolder.environment = environment;
    }
}
