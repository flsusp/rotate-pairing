package br.com.rotatepairing.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(subcommands = {
        HelpCommand.class,
        InitCommand.class,
        PeopleCommand.class
})
public class PairsCommand implements Callable<Void> {

    public static void main(String[] args) {
        CommandLine.call(new PairsCommand(), args);
    }

    @Override
    public Void call() {
        CommandLine.usage(this, System.out);
        return null;
    }
}
