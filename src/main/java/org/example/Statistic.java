package org.example;
class Statistic {
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


    protected  void printShortStats() {
        System.out.println("integers.txt: " + countIntegers);
        System.out.println("floats.txt: " + countFloats);
        System.out.println("strings.txt: " + countStrings);
    }

    protected  void printFullStats() {
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

    protected static void updateStatsForInteger(long value) {
        countIntegers++;
        sumOfIntegers += value;

        // Обновление минимального и максимального значения для целых чисел
        minIntegerValue = Math.min(minIntegerValue, value);
        maxIntegerValue = Math.max(maxIntegerValue, value);
    }

    protected static void updateStatsForFloat(double value) {
        countFloats++;
        sumOfFloats += value;

        // Обновление минимального и максимального значения для чисел с плавающей точкой
        minFloatValue = Math.min(minFloatValue, value);
        maxFloatValue = Math.max(maxFloatValue, value);
    }

    protected static void updateStatsForString(String value) {
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
