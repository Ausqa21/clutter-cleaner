package com.godric;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command()
public class Main implements Callable<Integer> {

    @CommandLine.Parameters(arity = "1..*")
    private String sourceDirectory;

    @CommandLine.Option(names = { "-m", "--mapping" }, arity = "1..*")
    private String mapping;

    @Override
    public Integer call() {
        if (!Validator.validatePath(this.sourceDirectory)) {
            System.out.printf("%s does not exist", this.sourceDirectory);
            return 1;
        }

        String regex = this.mapping.split(":")[0];
        String targetDirectoryString = this.mapping.split(":")[1];

        try {
            List<Path> matchedFiles = Files.list(Path.of(this.sourceDirectory))
                    .filter(file -> file.toFile().getName().contains(regex))
                    .toList();

            if (matchedFiles.isEmpty()) {
                System.out.println("Search did not return any results");
                return 1;
            }

            Path targetDirectory = Files.createDirectories(Path.of(targetDirectoryString));

            for (Path path: matchedFiles) {
                boolean result = path.toFile().renameTo(new File(targetDirectory.toFile() + File.separator + path.toFile().getName()));
                if (!result) {
                    System.out.printf("Moving %s to %s failed. Check and try again", path, targetDirectory);
                    return 1;
                }
            }

            System.out.printf("%s file(s) moved to %s successfully", matchedFiles.size(), targetDirectory);
        } catch (FileAlreadyExistsException fae) {
            System.out.printf("Can't create directory %s because a file/directory with the same name exists" +
                    " at the same location. Check and try again", targetDirectoryString);
        } catch (IOException e) {
            System.out.println("An error occurred while an I/O operation was taking place. Try again");
        }

        return 0;
    }

    public static void main(String[] args) {
        int rc = new CommandLine(new Main()).execute(args);
        System.exit(rc);
    }
}