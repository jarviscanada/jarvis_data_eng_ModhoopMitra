package ca.jrvs.apps.grep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JavaGrepLambdaImp extends JavaGrepImp{

    public static void main(String[] args) {
        if (args.length != 3 ) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        // creating JavaGrepLambdaImp instead of JavaGrepImp
        // JavaGrepLambdaImp inherits all methods except two override methods
        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Implement using lambda and stream APIs
     */
    @Override
    public List<String> readLines(File inputFile) {
        if (!inputFile.isFile()) {
            throw new IllegalArgumentException("The given parameter is not a file:" + inputFile);
        }

        try (FileReader fileReader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            return bufferedReader
                    .lines()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Could not read from file", e);
            return null;
        }
    }

    /**
     * Implement using lambda and stream APIs
     */
    @Override
    public List<File> listFiles(String rootDir) {
        File rootFileDir = new File(rootDir);

        if (!rootFileDir.exists() || !rootFileDir.isDirectory()) {
            return Collections.emptyList();
        }

        return Stream.of(Objects.requireNonNull(rootFileDir.listFiles()))
                .flatMap(file -> file.isFile() ?
                        Stream.of(file) :
                        listFiles(file.getAbsolutePath()).stream())
                .collect(Collectors.toList());
    }

}
