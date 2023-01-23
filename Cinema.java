package cinema;
import java.util.Scanner;

public class Cinema {

    Scanner scanner = new Scanner(System.in);

    private int rows;
    private int seats;
    private double totalTickets;
    private double soldTickets;
    final int ticketPrice = 10;
    final int ticketPriceBackRow = 8;
    private int currentIncome = 0;
    private boolean run = true;
    private final int[][] bookedTickets = new int[10][10];

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        cinema.seatingSetup();
        cinema.displayCinema();
        while (cinema.run){
            cinema.menu();
        }
    }

    public void seatingSetup() {

        //Enter the number of rows
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();

        //Enter the number of seats in each row
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();

    }

    public void menu() {

        System.out.println();

        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        int select = scanner.nextInt();

        switch(select) {
            case 1:
                displayCinema();
                break;
            case 2:
                buyTicket();
                break;
            case 3:
                statistics();
                break;
            case 0:
                run = false;
                break;
            default:
                System.out.println("Not a valid selection!");
                break;
        }
    }

    public void buyTicket() {
        boolean purchaseComplete = false;

        while(!purchaseComplete) {
            //Enter the row and seat you want to book
            System.out.println("\n\nEnter a row number:");
            int bookedRow = scanner.nextInt();

            System.out.println("Enter a seat number in that row:");
            int bookedSeat = scanner.nextInt();

            if (bookedRow < 0 || bookedRow > bookedTickets.length - 1 || bookedSeat < 0 || bookedSeat > bookedTickets.length - 1) {
                System.out.println("Wrong input!");
            } else if (bookedTickets[bookedRow][bookedSeat] == 1){
                System.out.println("That ticket has already been purchased!");
            } else {
                bookedTickets[bookedRow][bookedSeat] = 1;
                soldTickets += 1;
                getTicketPrice(bookedRow);
                purchaseComplete = true;
            }
        }

    }

    public void displayCinema() {

        System.out.println("Cinema: ");
        for (int i = 0; i <= rows; i++) {
            if (i == 0) {
                System.out.print("  ");
            } else {
                System.out.print(i + " ");
            }
            for (int k = 1; k <= seats; k++) {
                if (i == 0) {
                    System.out.print(k + " ");
                } else if(bookedTickets[i][k] == 1) {
                    System.out.print("B ");
                }
                else {
                    System.out.print("S ");
                }
            }
            System.out.println();
        }
    }

    public void getTicketPrice(int bookedRow) {

        int boughtTicket = ticketPrice;

        if (seats*rows > 60) {
            if(bookedRow > rows /2) {
                boughtTicket = ticketPriceBackRow;
            }
        }
        currentIncome += boughtTicket;
        System.out.println("\nTicket price: $" + boughtTicket + "\n");
    }

    public void statistics() {

        //Calculate Total Tickets
        totalTickets = seats*rows;

        System.out.printf("Number of purchased tickets: %.0f\n", soldTickets);
        System.out.printf("Percentage: %.2f%%\n", percentageOfSoldTickets());
        System.out.printf("Current Income: $%d \n", currentIncome);
        System.out.printf("Total income: $%.0f \n", totalIncome());

    }

    public double percentageOfSoldTickets() {
        if(soldTickets == 0) {
            return 0;
        } else {
            return (soldTickets/totalTickets)*100;
        }
    }

    public double totalIncome() {
        double totalIncome = 0;

        if (totalTickets > 60) {
            if (rows % 2 == 0) {
                totalIncome = (totalTickets/2 * ticketPrice) + (totalTickets/2 * ticketPriceBackRow);
            } else if (rows % 2 != 0) {
                //front-row
                var frontRowIncome = (rows/2*seats*ticketPrice);
                var backRowIncome = (rows/2*seats*ticketPriceBackRow) + seats*ticketPriceBackRow;
                totalIncome = frontRowIncome + backRowIncome;
            } else if (totalTickets <= 60) {
                totalIncome = ticketPrice * totalTickets;
            }
        }
        return totalIncome;
    }

}
