package cinema;

import java.util.Scanner;

public class Cinema {
    public static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static void printCinema(char[][] seats) {
        System.out.println("Cinema:");
        for(int i = 0; i <= seats.length; ++i) {
            for(int j = 0; j <= seats[0].length; ++j) {
                if(i == 0) {
                    if (j == 0) {
                        System.out.print(" ");
                    } else {
                        System.out.print(j);
                    }
                } else {
                    if (j == 0) {
                        System.out.print(i);
                    } else {
                        System.out.print(seats[i - 1][j - 1]);
                    }
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static int calcTotalIncome(char[][] seats, int priceFront, int priceBack, int threshold) {
        int total = 0;
        if (seats.length * seats[0].length <= threshold) {
            total = seats.length * seats[0].length * priceFront;
        } else {
            total = seats.length / 2 * seats[0].length * priceFront;
            total += (seats.length / 2 + seats.length % 2) * seats[0].length * priceBack;
        }
        return total;
    }

    public static void printStatistics(int purchased, int numSeats, int income, int total) {
        System.out.println("Number of purchased tickets: " + purchased);
        System.out.printf("Percentage: %.2f%%\n", (double) purchased / numSeats * 100);
        System.out.println("Current income: $" + income);
        System.out.println("Total income: $" + total);
    }

    public static int buyTicket(char[][] seats, int priceFront, int priceBack, int threshold) {
        int row = 0, col = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a row number:");
            row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            col = scanner.nextInt();
            try {
                if (seats[row - 1][col -1] == 'B') {
                    System.out.println("That ticket has already been purchased!");
                } else {
                    seats[row - 1][col - 1] = 'B';
                    int price = row <= threshold ? priceFront : priceBack;
                    System.out.println("Ticket price: $" + price);
                    return price;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong input!");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        final int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        final int cols = scanner.nextInt();
        char[][] seats = new char[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                seats[i][j] = 'S';
            }
        }
        final int priceFront = 10;
        final int priceBack = 8;
        final int threshold = 60;
        int purchased = 0;
        final int numSeats = seats.length * seats[0].length;
        int income = 0;
        final int total = calcTotalIncome(seats, priceFront, priceBack, threshold);

        int choice = -1;
        do {
            printMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    printCinema(seats);
                    break;
                case 2:
                    income += buyTicket(seats, priceFront, priceBack, rows * cols <= threshold ? rows : rows / 2);
                    ++purchased;
                    break;
                case 3:
                    printStatistics(purchased, numSeats, income, total);
            }
        } while (choice != 0);
    }
}