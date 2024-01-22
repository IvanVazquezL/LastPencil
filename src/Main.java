import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private final static ArrayList<String> names = new ArrayList<>(Arrays.asList("John", "Jack"));
    private static int numberOfPencils = 0;

    public static void main(String[] args) {
        numberOfPencils = getNumberOfPencils();
        String firstPlayer = getFirstPlayersName();
        String secondPlayer = getSecondPlayersName(firstPlayer);
        boolean isFirstPlayersTurn = true;

        // workaround for nextLine error
        scanner.nextLine();

        while(numberOfPencils > 0) {
            printPencils(numberOfPencils);

            String player = isFirstPlayersTurn ? firstPlayer : secondPlayer;
            System.out.println(player + "'s turn!");

            int pencilsToSubtract = getNumberOfPencilsToSubtract();
            numberOfPencils -= pencilsToSubtract;

            isFirstPlayersTurn = !isFirstPlayersTurn;

            if (numberOfPencils == 0) {
                player = isFirstPlayersTurn ? firstPlayer : secondPlayer;
                System.out.println(player + " won!");
            }
        }

        scanner.close();

    }

    public static int getNumberOfPencils() {
        do {
            try {
                System.out.println("How many pencils would you like to use:");
                String input = scanner.nextLine();

                if (input.isEmpty()) {
                    throw new NumberFormatException();
                }
                numberOfPencils = Integer.valueOf(input);

                validateNumberOfPencils(numberOfPencils);
            } catch (NumberFormatException e) {
                handleInputError("The number of pencils should be numeric");
            }
        } while(numberOfPencils <= 0);

        return numberOfPencils;
    }

    private static void validateNumberOfPencils(int numberOfPencils) {
        if (numberOfPencils == 0) {
            System.out.println("The number of pencils should be positive");
        } else if (numberOfPencils < 0) {
            throw new InputMismatchException();
        }
    }

    private static void handleInputError(String message) {
        System.out.println(message);
        numberOfPencils = 0;
    }

    public static String getFirstPlayersName() {
        System.out.println("Who will be the first ("+ names.get(0) +", " + names.get(1)+"):");
        String firstPlayer = scanner.next();

        while(!names.contains(firstPlayer)) {
            System.out.printf("Choose between '%s' and '%s'%n", names.get(0), names.get(1));
            firstPlayer = scanner.next();
        }

        return firstPlayer;
    }

    public static String getSecondPlayersName(String firstPlayer) {
        return firstPlayer.equals(names.get(0)) ? names.get(1) : names.get(0);
    }
    public static void printPencils(int numberOfPencils) {
        for(int i = 1; i <= numberOfPencils; i++) {
            System.out.print('|');
        }
        System.out.println();
    }

    public static int getNumberOfPencilsToSubtract() {
        int pencilsToSubtract = 0;
        do {
            try {
                String input = scanner.nextLine();

                if (input.isEmpty()) {
                    throw new NumberFormatException();
                }
                pencilsToSubtract = Integer.valueOf(input);

                if (pencilsToSubtract <= 0 || pencilsToSubtract > 3) {
                    System.out.println("Possible values: '1', '2' or '3'");
                    pencilsToSubtract = 0;
                } else if (pencilsToSubtract > numberOfPencils) {
                    System.out.println("Too many pencils were taken");
                    pencilsToSubtract = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Possible values: '1', '2' or '3'");
                pencilsToSubtract = 0;
            }
        } while(pencilsToSubtract <= 0);

        return pencilsToSubtract;
    }
}
