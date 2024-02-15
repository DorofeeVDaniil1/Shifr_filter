package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Filter {
    public static void main(String[] args) {
        String outputDirectory = "";
        String outputPrefix = "";
        boolean shortStats = false;// Флаг для опции -s
        boolean fullStats = false; // Флаг для опции -f
        boolean appendMode = false; // Флаг для опции -a


        // Обработка опций командной строки
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o": // Флаг для вывода в конкретную папку
                    if (i + 1 < args.length) {
                        outputDirectory = args[i + 1];
                        i++;  // Пропустить значение опции
                    }
                    break;
                case "-p": // Флаг для создания префикса к файлам
                    if (i + 1 < args.length) {
                        outputPrefix = args[i + 1];
                        i++;  // Пропустить значение опции
                    }
                    break;
                case "-s"://Короткая статитика
                    shortStats = true;
                    break;
                case "-f"://Полная статистика
                    fullStats = true;
                    break;
                case "-a"://Добавление в файл, а не перезапись его
                    appendMode = true;
                    break;

            }
        }

        // Обработка параметра системы -DoutputPrefix
        String systemOutputPrefix = System.getProperty("outputPrefix");
        if (systemOutputPrefix != null && !systemOutputPrefix.isEmpty()) {
            outputPrefix = systemOutputPrefix;
        }

        ConstructFile.processFiles(args, outputDirectory, outputPrefix, shortStats, fullStats,appendMode);
    }
}
