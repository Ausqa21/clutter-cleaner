package com.godric;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class contains methods used to validate the inputs of the cli
 **/
public class Validator {

    /**
     * @param pathString - the path to the file
     * @return true if the file exists; false if the file does not exist or its existence cannot be determined.
     */
    public static boolean validatePath(String pathString) {
        if (pathString == null) {
            return false;
        }

        Path path = Path.of(pathString);

        return Files.exists(path);
    }
}
