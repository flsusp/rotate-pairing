package br.com.rotatepairing.commands;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

@CommandLine.Command(description = "Inits the configuration for your team.",
        name = "init", mixinStandardHelpOptions = true)
public class InitCommand implements Callable<Void> {

    public static final String CONFIG_DIR = "./.pairs";

    public static void main(String[] args) {
        CommandLine.call(new InitCommand(), args);
    }

    public static boolean isInitialized() {
        return new File(CONFIG_DIR).exists();
    }

    @Override
    public Void call() throws Exception {
        if (isInitialized()) {
            System.out.println("Current workspace already initialized. All created files reside in " + CONFIG_DIR + " folder.");
        } else {
            initialize();
        }
        return null;
    }

    private void initialize() throws IOException {
        createDir(CONFIG_DIR);
        createFileFromTemplate(CONFIG_DIR + "/pairing.history", "templates/pairing.history");
        createFileFromTemplate(CONFIG_DIR + "/people.history", "templates/people.history");
        createFileFromTemplate(CONFIG_DIR + "/config.yaml", "templates/config.yaml");
    }

    private void createFileFromTemplate(String fileToCreate, String templateFromClasspath) throws IOException {
        InputStream template = InitCommand.class.getClassLoader().getResourceAsStream(templateFromClasspath);
        File file = new File(fileToCreate);
        file.createNewFile();

        try (FileOutputStream output = new FileOutputStream(file)) {
            IOUtils.copy(template, output);
        }
    }

    private void createDir(String configDir) throws IOException {
        FileUtils.forceMkdir(new File(configDir));
    }
}
