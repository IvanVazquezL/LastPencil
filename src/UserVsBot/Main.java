package UserVsBot;

import java.util.*;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private final static ArrayList<String> names = new ArrayList<>(Arrays.asList("John", "Jack"));
    private static int numberOfPencils = 0;
    private final static Bot botJack = new Bot("Jack");
    private final static Player playerJohn = new Player("John");
    private static String firstPlayer = "";
    private static boolean isFirstPlayersTurn = true;
    private static Player currentPlayer;


    public static void main(String[] args) {
        numberOfPencils = getNumberOfPencils();
        firstPlayer = getFirstPlayersName();
        currentPlayer = firstPlayer.equals("Jack") ? botJack : playerJohn;
        isFirstPlayersTurn = true;

        scanner.nextLine();

        while(numberOfPencils > 0) {
            printPencils(numberOfPencils);

            System.out.println(currentPlayer.getName() + "'s turn!");

            int pencilsToSubtract = getNumberOfPencilsToSubtract();
            numberOfPencils -= pencilsToSubtract;

            isFirstPlayersTurn = !isFirstPlayersTurn;

            if (numberOfPencils == 0) {
                currentPlayer = currentPlayer.getName().equals("Jack") ? playerJohn : botJack;
                System.out.println(currentPlayer.getName() + " won!");
            }

            currentPlayer = currentPlayer.getName().equals("Jack") ? playerJohn : botJack;
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
                String input = getInputDependingOfPlayer();

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

    private static String getInputDependingOfPlayer() {
        if (currentPlayer instanceof Bot) {
            String move = ((Bot) currentPlayer).getMove(numberOfPencils);
            System.out.println(move);
            return move;
        } else {
            return scanner.nextLine();
        }
    }
}

class Player {
    private final String name;

    Player(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }
}

class Bot extends Player{

    Bot(String name) {
        super(name);
    }

    String getMove(int numberOfPencils) {
        if (numberOfPencils == 1) {
            return "1";
        }

        if (numberOfPencils == 4) {
            return "3";
        }

        if (numberOfPencils == 3) {
            return "2";
        }

        if (numberOfPencils == 2) {
            return "1";
        }

        int losingStrategyInitialNumber = 5;
        int losingStrategyIncrement = 4;
        int i = losingStrategyInitialNumber;
        int pastNumber = 0;

        while(i < numberOfPencils) {
            pastNumber = i;
            i += losingStrategyIncrement;
        }

        if (i == numberOfPencils) {
            Random random = new Random();
            return String.valueOf(random.nextInt(3) + 1);
        } else {
            return String.valueOf(Math.abs(pastNumber - numberOfPencils));
        }
    }
}