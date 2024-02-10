package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Filter {

    private static int countIntegers = 0;
    private static long minIntegerValue = Long.MAX_VALUE;
    private static long maxIntegerValue = Long.MIN_VALUE;
    private static double minFloatValue = Double.MAX_VALUE;
    private static double maxFloatValue = Double.MIN_VALUE;
    private static int countFloats = 0;
    private static int countStrings = 0;
    private static int shortestStringLength = Integer.MAX_VALUE;
    private static int longestStringLength = 0;
    private static long sumOfIntegers = 0;
    private static double sumOfFloats = 0.0;

    public static void main(String[] args) {
        String outputDirectory = "";
        String outputPrefix = "";
        boolean shortStats = false;
        boolean fullStats = false;

        // Обработка опций командной строки
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputDirectory = args[i + 1];
                        i++;  // Пропустить значение опции
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        outputPrefix = args[i + 1];
                        i++;  // Пропустить значение опции
                    }
                    break;
                case "-s":
                    shortStats = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
            }
        }

        // Обработка параметра системы -DoutputPrefix
        String systemOutputPrefix = System.getProperty("outputPrefix");
        if (systemOutputPrefix != null && !systemOutputPrefix.isEmpty()) {
            outputPrefix = systemOutputPrefix;
        }

        processFiles(args, outputDirectory, outputPrefix, shortStats, fullStats);

        //System.out.println("Filtering завершено. Results written to the specified directory.");
    }

    private static void processFiles(String[] inputFiles, String outputDirectory, String outputPrefix, boolean shortStats, boolean fullStats) {
        outputDirectory = validateOutputDirectory(outputDirectory);
        String integersFileName = constructOutputFileName(outputDirectory, outputPrefix, "integers.txt");
        String floatsFileName = constructOutputFileName(outputDirectory, outputPrefix, "floats.txt");
        String stringsFileName = constructOutputFileName(outputDirectory, outputPrefix, "strings.txt");

        try {
            PrintWriter integersWriter = null;
            PrintWriter floatsWriter = null;
            PrintWriter stringsWriter = null;

            for (String inputFile : inputFiles) {
                File file = new File(inputFile);

                if (file.exists()) {
                    if (file.isFile()) {
                        Scanner scanner = new Scanner(file);

                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();

                            if (line.matches("-?\\d+")) {
                                try {
                                    long intValue = Long.parseLong(line);
                                    updateStatsForInteger(intValue);
                                    if (integersWriter == null) {
                                        integersWriter = new PrintWriter(new FileWriter(integersFileName));
                                    }
                                    integersWriter.println(line);
                                } catch (NumberFormatException e) {
                                    // Пропускаем строку, если не удается преобразовать в целое число
                                }
                            } else if (line.matches("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?")) {
                                try {
                                    double floatValue = Double.parseDouble(line);
                                    updateStatsForFloat(floatValue);
                                    if (floatsWriter == null) {
                                        floatsWriter = new PrintWriter(new FileWriter(floatsFileName));
                                    }
                                    floatsWriter.println(line);
                                } catch (NumberFormatException e) {
                                    // Пропускаем строку, если не удается преобразовать в число с плавающей точкой
                                }
                            } else {
                                updateStatsForString(line);
                                if (stringsWriter == null) {
                                    stringsWriter = new PrintWriter(new FileWriter(stringsFileName));
                                }
                                stringsWriter.println(line);
                            }
                        }


                        scanner.close();
                    } else {
                       // System.out.println("Finish directory " + inputFile);
                    }
                } else {
                    System.out.println("File not found: " + inputFile);
                }
            }

            if (integersWriter != null) {
                integersWriter.close();
            }
            if (floatsWriter != null) {
                floatsWriter.close();
            }
            if (stringsWriter != null) {
                stringsWriter.close();
            }

            System.out.println("Фильрация завершена. Результаты записаны в  integers.txt, floats.txt и strings.txt.");

            if (shortStats || fullStats) {
                System.out.println("Статистика::");

                if (shortStats) {
                    System.out.println("Короткая статистика:");
                    printShortStats();
                }

                if (fullStats) {
                    System.out.println("Полная статистика:");
                    printFullStats();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка во время фильтрации.");
        }
    }

    private static void printShortStats() {
        System.out.println("integers.txt: " + countIntegers);
        System.out.println("floats.txt: " + countFloats);
        System.out.println("strings.txt: " + countStrings);
    }

    private static void printFullStats() {
        System.out.println("integers.txt: " + countIntegers);
        System.out.println("floats.txt: " + countFloats);
        System.out.println("strings.txt: " + countStrings);

        if (countIntegers > 0) {
            System.out.println("Мин Integer: " + getMinInteger());
            System.out.println("Max Integer: " + getMaxInteger());
            System.out.println("Сумма Integers: " + sumOfIntegers);
            System.out.println("Среднее Integers: " + getAverageOfIntegers());
        }

        if (countFloats > 0) {
            System.out.println("Mин Float: " + getMinFloat());
            System.out.println("Max Float: " + getMaxFloat());
            System.out.println("Сумма Floats: " + sumOfFloats);
            System.out.println("Среднее Floats: " + getAverageOfFloats());
        }

        if (countStrings > 0) {
            System.out.println("Самый короткй  String: " + shortestStringLength);
            System.out.println("Самый длинный String : " + longestStringLength);
        }
    }
    private static String validateOutputDirectory(String outputDirectory) {
        File directory = new File(outputDirectory);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid output directory. Using the current directory.");
            return "";
        }

        return outputDirectory;
    }

    private static String constructOutputFileName(String outputDirectory, String outputPrefix, String fileType) {
        String fileName = outputPrefix + fileType;
        if (!outputDirectory.isEmpty()) {
            fileName = outputDirectory + File.separator + fileName;
        }
        return fileName;
    }

    private static void updateStatsForInteger(long value) {
        countIntegers++;
        sumOfIntegers += value;

        // Обновление минимального и максимального значения для целых чисел
        minIntegerValue = Math.min(minIntegerValue, value);
        maxIntegerValue = Math.max(maxIntegerValue, value);
    }

    private static void updateStatsForFloat(double value) {
        countFloats++;
        sumOfFloats += value;

        // Обновление минимального и максимального значения для чисел с плавающей точкой
        minFloatValue = Math.min(minFloatValue, value);
        maxFloatValue = Math.max(maxFloatValue, value);
    }

    private static void updateStatsForString(String value) {
        countStrings++;
        int length = value.length();
        if (length < shortestStringLength) {
            shortestStringLength = length;
        }
        if (length > longestStringLength) {
            longestStringLength = length;
        }
    }

    private static long getMinInteger() {
        return minIntegerValue;
    }

    private static long getMaxInteger() {
        return maxIntegerValue;
    }

    private static double getAverageOfIntegers() {
        return countIntegers > 0 ? (double) sumOfIntegers / countIntegers : 0;
    }

    private static double getMinFloat() {
        return minFloatValue;
    }

    private static double getMaxFloat() {
        return maxFloatValue;
    }

    private static double getAverageOfFloats() {
        return countFloats > 0 ? sumOfFloats / countFloats : 0.0;
    }
}
