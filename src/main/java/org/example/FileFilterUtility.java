package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileFilterUtility {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java FileFilterUtility <input_file1> <input_file2> ...");
            return;
        }

        String integersFileName = "C:\\Users\\dandy\\IdeaProjects\\FileSwapper\\src\\main\\java\\org\\example\\integers.txt";
        String floatsFileName = "C:\\Users\\dandy\\IdeaProjects\\FileSwapper\\src\\main\\java\\org\\example\\floats.txt";
        String stringsFileName = "C:\\Users\\dandy\\IdeaProjects\\FileSwapper\\src\\main\\java\\org\\example\\strings.txt";

        try {
            PrintWriter integersWriter = new PrintWriter(new FileWriter(integersFileName));
            PrintWriter floatsWriter = new PrintWriter(new FileWriter(floatsFileName));
            PrintWriter stringsWriter = new PrintWriter(new FileWriter(stringsFileName));

            for (String inputFile : args) {
                processFile(inputFile, integersWriter, floatsWriter, stringsWriter);
            }

            integersWriter.close();
            floatsWriter.close();
            stringsWriter.close();

            System.out.println("Filtering завершено. Results written to integers.txt, floats.txt, and strings.txt.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred during filtering.");
        }
    }

    private static void processFile(String inputFile, PrintWriter integersWriter, PrintWriter floatsWriter, PrintWriter stringsWriter) {
        try {
            Scanner scanner = new Scanner(new File(inputFile));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.matches("-?\\d+")) {
                    integersWriter.println(line);
                } else if (line.matches("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?")) {
                    floatsWriter.println(line);
                } else {
                    stringsWriter.println(line);
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred while processing file: " + inputFile);
            e.printStackTrace();
        }
    }
}
