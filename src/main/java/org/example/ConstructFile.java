package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConstructFile {

    protected static void processFiles(String[] inputFiles, String outputDirectory, String outputPrefix, boolean shortStats, boolean fullStats, boolean appendMode) {
        outputDirectory = validateOutputDirectory(outputDirectory);
        String integersFileName = constructOutputFileName(outputDirectory, outputPrefix, "integers.txt");
        String floatsFileName = constructOutputFileName(outputDirectory, outputPrefix, "floats.txt");
        String stringsFileName = constructOutputFileName(outputDirectory, outputPrefix, "strings.txt");
        //Создадим объект класса Statistic для обработки статистики
        Statistic statistic = new Statistic();

        try {
            //Создавать будм файлы только по мере необходимости для каждого типа данных
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
                                    Statistic.updateStatsForInteger(intValue);
                                    if (integersWriter == null) {
                                        integersWriter = new PrintWriter(new FileWriter(integersFileName, appendMode));
                                    }
                                    integersWriter.println(line);
                                } catch (NumberFormatException e) {
                                    System.out.print("Не удается преоброзовать в целое число");
                                    // Пропускаем строку, если не удается преобразовать в целое число
                                }
                            } else if (line.matches("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?")) {
                                try {
                                    double floatValue = Double.parseDouble(line);
                                    Statistic.updateStatsForFloat(floatValue);
                                    if (floatsWriter == null) {
                                        floatsWriter = new PrintWriter(new FileWriter(floatsFileName, appendMode));
                                    }
                                    floatsWriter.println(line);
                                } catch (NumberFormatException e) {
                                    System.out.print("Не удается преоброзовать в число с плавающей точкой");
                                    // Пропускаем строку, если не удается преобразовать в число с плавающей точкой
                                }
                            } else {
                                Statistic.updateStatsForString(line);
                                if (stringsWriter == null) {
                                    stringsWriter = new PrintWriter(new FileWriter(stringsFileName, appendMode));
                                }
                                stringsWriter.println(line);
                            }
                        }

                        scanner.close();
                    } else {

                    }
                } else {

                }
            }

            //Закрываем
            if (integersWriter != null) {
                integersWriter.close();
            }
            if (floatsWriter != null) {
                floatsWriter.close();
            }
            if (stringsWriter != null) {
                stringsWriter.close();
            }

            System.out.println("\n"+"Фильтрация завершена. Результаты записаны в " + outputDirectory + "\n"+
                    outputPrefix + "integers.txt" +"\n"+
                    outputPrefix + "string.txt" +"\n"+
                    outputPrefix + "float.txt"+
                    "\n"+"\n");

            if (shortStats || fullStats) {
                System.out.println("Вывод статистики::");

                if (shortStats) {
                    System.out.println("Короткая статистика:"+"\n");
                    statistic.printShortStats();
                }

                if (fullStats) {
                    System.out.println("Полная статистика:"+"\n");
                    statistic.printShortStats();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка во время фильтрации."+"\n");
        }
    }

    private static String validateOutputDirectory(String outputDirectory) {
        File directory = new File(outputDirectory);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Неправильно задана директория вывода. Сохранение файлов в текущую директорию"+"\n");
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
}
