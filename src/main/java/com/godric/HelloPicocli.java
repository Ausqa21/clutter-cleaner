package com.godric;

import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command()
public class HelloPicocli implements Callable<Integer> {

    @CommandLine.Parameters(arity = "1..*")
    private List<String> messages;

    @CommandLine.Option(names = { "-p", "--prefix" })
    private String prefix;

    @CommandLine.Option(names = { "-g", "--greeting" })
    private boolean displayGreeting;

    @CommandLine.Option(names = { "-r", "--repeat" })
    private Integer repeat = 1;

    @CommandLine.Option(names = { "-i", "--input" })
    private Path messagesFile;

    @Override
    public Integer call() {
        if (this.displayGreeting) {
            System.out.println("Welcome! Here are your messages");
        }

        if (this.messagesFile != null) {
            try {
                if (this.messages == null) {
                    this.messages = new ArrayList<>();
                }
                this.messages.addAll(Files.readAllLines(this.messagesFile));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.printf("Error reading file %s%n", this.messagesFile);
                return 1;
            }
        }

        if (this.messages == null || this.messages.size() < 1) {
            System.out.printf("%sHello, Picocli%n", this.prefix == null ? "" : this.prefix);
        } else {
            for (String msg: this.messages) {
                System.out.printf("%s%s%n", this.prefix == null ? "" : this.prefix, msg);;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        var rc = new CommandLine(new HelloPicocli()).execute(args);
        System.exit(rc);
    }
}
