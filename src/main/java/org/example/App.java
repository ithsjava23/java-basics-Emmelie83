package org.example;

import java.sql.SQLOutput;
import java.util.Scanner;

public class App {
    // Create scanner object
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        // Declare and initialize variables
        int selection;
        int[][] timeAndPrices = new int[2][24];


        while(true) {
            selection = printMenu();
            switch (selection) {
                case 1:
                    inputPrices(timeAndPrices);
                    break;
                case 2:
                    findMinMaxAverage(timeAndPrices);
                    break;
                case 3:
                    sortAndPrintArray(timeAndPrices);
                    break;
                case 4:

                    break;
                case -1:
                    // End the program
                    return;
            }
        }
    }

    /**
     * Method for printing the menu.
     * @return user's selection
     */
    public static int printMenu() {
        int selection = 0;
        System.out.printf("\nElpriser\n");
        System.out.printf("========\n");
        System.out.printf("1. Inmatning\n");
        System.out.printf("2. Min, Max och Medel\n");
        System.out.printf("3. Sortera\n");
        System.out.printf("4. Bästa Laddningstid (4h)\n");
        System.out.printf("e. Avsluta\n");
        selection = input();
        return selection;
    }

    /**
     * Method for testing and returning user input. If user input is not a valid integer or "e", an error message
     * is printed.
     * @return user input if user inputs 1-4, -1 if user inputs "e"
     */
    public static int input()
    {
        int number;
        String string;

        while (true)
        {
            if (scan.hasNextInt())
            {
                number = scan.nextInt();
                if (number > 0 && number < 5)
                {
                    scan.nextLine();
                    return number;
                }
                System.out.println("Felaktig input. Försök på nytt:");
                scan.nextLine();
                continue;
            }
            if(scan.hasNext())
            {
                string = scan.nextLine().toLowerCase();
                if(string.equals("e"))
                {
                    return -1;
                }
                System.out.println("Felaktig input. Försök på nytt:");
            }
        }
    }

    public static void inputPrices(int[][] array) {
        System.out.printf("Ange elpriserna under dygnets timmar i hela ören.");
        for (int i = 0; i < array[1].length; i++) {
            System.out.printf("\nElpriset kl. %02d-%02d: ", i, i + 1);
            array[0][i] = i;
            array[1][i] = scan.nextInt();
        }
    }

    public static void findMinMaxAverage(int[][] array) {
        int min = array[1][0];
        int max = array[1][0];
        int minIndex = array[0][0];
        int maxIndex = array[0][0];
        int sum = 0;
        double average;
        for (int i = 0; i < array[1].length; i++) {
            if (array[1][i] < min) {
                min = array[1][i];
                minIndex = array[0][i];
            }
            if (array[1][i] > max) {
                max = array[1][i];
                maxIndex = array[0][i];
            }
            sum += array[1][i];
        }
        average = (double) sum / (double) array[1].length;
        System.out.printf("\nLägsta pris: %02d-%02d, %d öre/kWh\n", minIndex, minIndex + 1, min);
        System.out.printf("Högsta pris: %02d-%02d, %d öre/kWh\n", maxIndex, maxIndex + 1, max);
        System.out.printf("Medelpris: %.2f öre/kWh\n", average);
    }

    public static void sortAndPrintArray(int[][] array) {
        int temp;
        for (int i = 0; i < array[1].length; i++)
        {
            for (int j = 0; j < array[1].length - 1; j++)
            {
                //
                if (array[1][j] < array[1][j + 1])
                {
                    //
                    temp = array[1][j + 1];
                    array[1][j + 1] = array[1][j];
                    array[1][j] = temp;
                    //
                    temp = array[0][j + 1];
                    array[0][j + 1] = array[0][j];
                    array[0][j] = temp;
                }
            }
        }
        for (int i = 0; i < array[1].length; i++) {
            System.out.printf("%02d-%02d %d öre\n", array[0][i], array[0][i] + 1, array[1][i]);
        }
    }


}


