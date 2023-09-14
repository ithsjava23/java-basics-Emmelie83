package org.example;

import java.util.Locale;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv", "SE");
        Locale.setDefault(swedishLocale);
        Scanner scan = new Scanner(System.in);
        int selection;
        int[] timeAndPrices = new int[24];
        int[][] sortedArray = new int[2][24];
        int min;
        int max;
        double average;

        while(true) {
            printMenu();
            selection = getUserInput(scan);
            switch (selection) {
                case 1:
                    inputPrices(scan, timeAndPrices);
                    break;
                case 2:
                    min = findMin(timeAndPrices);
                    max = findMax(timeAndPrices);
                    average = findAverage(timeAndPrices);
                    printMinMaxAverage(timeAndPrices, min, max, average);
                    break;
                case 3:
                    printSortedArray(sortArray(timeAndPrices, sortedArray));
                    break;
                case 4:
                    findBestChargingTime(timeAndPrices);
                    break;
                case 5:
                    printDiagram(timeAndPrices);
                case -1:
                    return;
            }
        }
    }


    public static void printMenu() {
        System.out.printf("\nElpriser\n");
        System.out.printf("========\n");
        System.out.printf("1. Inmatning\n");
        System.out.printf("2. Min, Max och Medel\n");
        System.out.printf("3. Sortera\n");
        System.out.printf("4. Bästa Laddningstid (4h)\n");
        System.out.printf("5. Visualisering\n");
        System.out.printf("e. Avsluta\n");
    }


    public static int getUserInput(Scanner scan) {
        while (true) {
            if (scan.hasNextInt()) {
                int number = scan.nextInt();
                if (number >= 1 && number <= 5) {
                    scan.nextLine();
                    return number;
                }
                System.out.println("Felaktig input. Försök igen:");
            } else if (scan.hasNext()) {
                String input = scan.nextLine().toLowerCase();
                if (input.equals("e")) {
                    return -1;
                }
                System.out.println("Felaktig input. Försök igen:");
            }
        }
    }

    public static void inputPrices(Scanner scan, int[] array) {
        System.out.printf("Ange elpriserna under dygnets timmar i hela ören.");
        for (int i = 0; i < array.length; i++) {
            System.out.printf("\nElpriset kl. %02d-%02d: ", i, i + 1);
            array[i] = checkInputPrices(scan);
        }
    }

    public static int checkInputPrices(Scanner scan) {
        while(true) {
            if (scan.hasNextInt()) {
                return scan.nextInt();
            } else if (scan.hasNext()) {
                System.out.printf("Felaktig input. Försök igen.\n");
                scan.nextLine();
            }
        }
    }

    public static int findMin(int[] array) {
        int min = array[0];
        for (int j : array) {
            if (j < min) {
                min = j;
            }
        }
        return min;
    }

    public static int findMax(int[] array) {
        int max = array[0];
        for (int j : array) {
            if (j > max) {
                max = j;
            }
        }
        return max;
    }

    public static double findAverage(int[] array) {
        int sum = 0;
        for (int j : array) {
            sum += j;
        }
        return (double) sum / (double) array.length;
    }

    public static void printMinMaxAverage(int[] array, int min, int max, double average) {
        int minIndex = 0;
        int maxIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == min) {
                minIndex = i;
            }
            if (array[i] == max) {
                maxIndex = i;
            }
        }
        System.out.printf("\nLägsta pris: %02d-%02d, %d öre/kWh\n", minIndex, minIndex + 1, min);
        System.out.printf("Högsta pris: %02d-%02d, %d öre/kWh\n", maxIndex, maxIndex + 1, max);
        System.out.printf("Medelpris: %.2f öre/kWh\n", average);
    }


    public static int[][] sortArray(int[] array, int[][] array2) {
        for (int i = 0; i < array.length; i++) {
            array2[0][i] = i;
            array2[1][i] = array[i];
        }
        int temp;
        for (int i = 0; i < array2[1].length; i++)
        {
            for (int j = 0; j < array2[1].length - 1; j++)
            {
                if (array2[1][j] < array2[1][j + 1])
                {
                    temp = array2[1][j + 1];
                    array2[1][j + 1] = array2[1][j];
                    array2[1][j] = temp;

                    temp = array2[0][j + 1];
                    array2[0][j + 1] = array2[0][j];
                    array2[0][j] = temp;
                }
            }
        }
        return array2;
    }

    public static void printSortedArray(int[][] array2) {
        for (int i = 0; i < array2[1].length; i++) {
            System.out.printf("%02d-%02d %d öre\n", array2[0][i], array2[0][i] + 1, array2[1][i]);
        }
    }

    public static void findBestChargingTime(int[] array) {
        int min = array[0] + array[1] + array[2] + array[3];
        int time = 0;
        for (int i = 1; i < array.length - 3; i++) {
            int group = array[i] + array[i + 1] + array[i + 2] + array[i + 3];
            if (group < min) {
                min = group;
                time = i;
            }
        }
        double average = ((double) min / (double) 4);
        System.out.printf("Påbörja laddning klockan %02d\n", time);
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", average);
    }

    public static void printDiagram(int[] array) {
        int minPrice = findMin(array);
        int maxPrice = findMax(array);
        int incrementRange =  (maxPrice - minPrice) / 5;

        System.out.printf("%3d| ", maxPrice);
        for (int i = 0; i < array.length; i++)
            if (array[i] == maxPrice)
                if (i == array.length - 1) System.out.printf(" x");
                else System.out.printf(" x ");
            else if (i == array.length - 1) System.out.printf("  ");
            else
                System.out.printf("   ");

        for (int i = 4; i > 0; i--) {
                System.out.printf("\n   | ");
            for (int j = 0; j < array.length; j++) {
                if (array[j] >= minPrice + i * incrementRange) {
                    if (j == array.length - 1) System.out.printf(" x");
                    else System.out.printf(" x ");
                } else if (j == array.length - 1) System.out.printf("  ");
                else
                    System.out.printf("   ");
            }
        }

        System.out.printf("\n%3d| ", minPrice);
        for (int i = 0; i < array.length; i++)
            if (i == array.length - 1) System.out.printf(" x");
            else {
                System.out.printf(" x ");
            }
        System.out.printf("\n   |------------------------------------------------------------------------");
        System.out.printf("\n   | 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23\n");

    }
}


