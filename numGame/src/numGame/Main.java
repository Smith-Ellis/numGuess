package numGame;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Integer> playerPositions = new ArrayList<Integer>();
    static ArrayList<Integer> cpuPositions = new ArrayList<Integer>();

    public static void main(String[] args) {
        while (true) {
            playGame();
            System.out.print("Do you want to play again? (yes/no): ");
            String playAgain = sc.next().toLowerCase();
            if (!playAgain.equals("yes")) {
                System.out.println("Thanks for playing!");
                break;
            }
            // Clear the game boards and positions lists for a new game
            clearGame();
        }
 
    }

    public static void clearGame() {
        playerPositions.clear();
        cpuPositions.clear();
    }

    public static void playGame() {
        char[][] gameBoard = {
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };

        printGameBoard(gameBoard);

        while (true) {
            System.out.print("Enter your placement (1-9): ");
            int playerPosition = sc.nextInt();
            while (playerPositions.contains(playerPosition) || cpuPositions.contains(playerPosition)) {
                System.out.print("Position taken, enter a new position: ");
                playerPosition = sc.nextInt();
            }

            placePiece(gameBoard, playerPosition, "player");
            String result = checkWin();
            if (!result.isEmpty()) {
                System.out.println(result);
                break;
            }

            Random rand = new Random();
            int cpuPosition;
            do {
                cpuPosition = rand.nextInt(9) + 1;
            } while (playerPositions.contains(cpuPosition) || cpuPositions.contains(cpuPosition));

            placePiece(gameBoard, cpuPosition, "cpu");
            printGameBoard(gameBoard);

            result = checkWin();
            if (!result.isEmpty()) {
                System.out.println(result);
                break;
            }

            if (playerPositions.size() + cpuPositions.size() == 9) {
                System.out.println("It's a draw!");
                break;
            }
        }
    }

    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void placePiece(char[][] gameBoard, int position, String user) {
        char symbol = (user.equals("player")) ? 'X' : 'O';

        int row = (position - 1) / 3 * 2;
        int col = (position - 1) % 3 * 2;
        gameBoard[row][col] = symbol;
        if (user.equals("player")) {
            playerPositions.add(position);
        } else {
            cpuPositions.add(position);
        }
    }

    public static String checkWin() {
        List<Integer> topRow = Arrays.asList(1, 2, 3);
        List<Integer> midRow = Arrays.asList(4, 5, 6);
        List<Integer> botRow = Arrays.asList(7, 8, 9);
        List<Integer> leftCol = Arrays.asList(1, 4, 7);
        List<Integer> midCol = Arrays.asList(2, 5, 8);
        List<Integer> rightCol = Arrays.asList(3, 6, 9);
        List<Integer> Cross1 = Arrays.asList(1, 5, 9);
        List<Integer> Cross2 = Arrays.asList(3, 5, 7);

        List<List<Integer>> winning = new ArrayList<List<Integer>>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(Cross1);
        winning.add(Cross2);

        for (List<Integer> l : winning) {
            if (playerPositions.containsAll(l)) {
                return "\nCongrats! You win!";
            } else if (cpuPositions.containsAll(l)) {
                return "\nCPU wins!";
            } else if (playerPositions.size() + cpuPositions.size() == 9) {
                return "\nOops, it's a tie!";
            }
        }
        return "";
    }
}
