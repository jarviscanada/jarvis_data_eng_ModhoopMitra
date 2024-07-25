package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }
    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        for (File file  : listFiles(getRootPath())) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> files = new ArrayList<>();
        File rootFileDir = new File(rootDir); //File type can be file directory as well

        if (!rootFileDir.exists() || !rootFileDir.isDirectory()) {
            return files;
        }

        File[] rootFiles = rootFileDir.listFiles();
        if (rootFiles != null) {
            for (File file : rootFiles) {
                if (file.isFile()) {
                    files.add(file);
                }
                else if (file.isDirectory()) {
                    files.addAll(listFiles(file.getAbsolutePath()));
                }
            }
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile) {
        if (!inputFile.isFile()) {
            throw new IllegalArgumentException("The given parameter is not a file:" + inputFile);
        }

        List<String> lines = new ArrayList<>();

        // try-with-resources to ensure automatic closing of declared resources
        try (FileReader fileReader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while  ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            logger.error("Could not read from file", e);
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        return Pattern.matches(regex, line);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(getOutFile());
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bufferedWriter = new BufferedWriter(osw)) {

            for (String line : lines) {
                bufferedWriter.write(line + '\n');
            }
        }
        catch (IOException e) {
            logger.error("Could not write to file", e);
            throw new IOException(e);
        }
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}