import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * Author: Alex Vasileiadis
 * Written: 29/10/2022
 * Last updated: 10/11/2022
 *
 * Compilation: BingoGame.java
 * Execution: BingoGame
 *
 * Description: This program simulates a bingo game. It takes the number of players from an input file,
 * generates unique bingo cards for each player based on a template, and starts the game. The program
 * randomly selects numbers, and players mark off those numbers on their cards. The winner is the first
 * player to mark off all numbers on one of their cards.
 */
public class BingoGame {

	/**
	 * Reads the template from the input file.
	 *
	 * @param arr     The array to fill with template numbers.
	 * @param scanner Scanner object for reading from the input file.
	 */
	private static void fillTemplate(int[][] arr, Scanner scanner) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				if (scanner.hasNextInt()) {
					arr[i][j] = scanner.nextInt();
				} else {
					// Handle if the file doesn't have enough integers
					System.err.println("Insufficient input in file.");
					return;
				}
			}
		}
	}

	/**
	 * Randomizes unique numbers within a specified range.
	 *
	 * @param arr The array to fill with random numbers.
	 * @param a   The lower bound of the range.
	 * @param b   The upper bound of the range.
	 * @param N   The number of random numbers to generate.
	 */
	private static void randomize(int[] arr, int a, int b, int N) {
		int i = 0;
		while (i < N) {
			int num = (int) (a + (Math.random() * (b - a + 1)));
			boolean unique = true;
			for (int k = 0; k < i; k++) {
				if (num == arr[k]) {
					unique = false;
					break;
				}
			}
			if (unique) {
				arr[i] = num;
				i++;
			}
		}
	}

	/**
	 * Fills all cards of a player with unique numbers based on a template.
	 *
	 * @param tombola The 4D array representing the bingo cards for all players.
	 * @param template The template used for generating the cards.
	 */
	private static void fillAllCards(int[][][][] tombola, int[][] template) {
		for (int i = 0; i < tombola.length; i++) {
			int range = 0;
			for (int j = 0; j < tombola[0][0][0].length; j++) {
				range += 10;
				int min = range - 10, max = range - 1;
				if (j == 0) min++;
				if (j == tombola[0][0][0].length - 1) max++;
				int nums = max - min + 1;

				int[] randomNums = new int[nums];
				randomize(randomNums, min, max, nums);

				int n = 0, t = 0, cnt = 0;
				for (int k = 0; k < tombola[0].length; k++) {
					cnt = 0;
					int[] posNum = new int[2];
					for (int l = 0; l < tombola[0][0].length; l++) {
						if (template[t++][j] == 1) {
							tombola[i][k][l][j] = randomNums[n++];
							posNum[cnt++] = l;
						} else {
							tombola[i][k][l][j] = 0;
						}
						if (cnt == 2) {
							if (tombola[i][k][posNum[0]][j] > tombola[i][k][posNum[1]][j]) {
								int temp = tombola[i][k][posNum[0]][j];
								tombola[i][k][posNum[0]][j] = tombola[i][k][posNum[1]][j];
								tombola[i][k][posNum[1]][j] = temp;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Displays bingo cards for each player in the console.
	 *
	 * @param tombola The 4D array representing the bingo cards for all players.
	 */
	private static void displayCards(int[][][][] tombola) {
		String line = "-----------------------------------------";
		for (int i = 0; i < tombola.length; i++) {
			System.out.printf("Displaying the bingo cards for player %d\n\n" + line + "\n\n", i + 1);
			for (int j = 0; j < tombola[0].length; j++) {
				System.out.printf("Displaying card No %d\n\n\n", j + 1);
				for (int k = 0; k < tombola[0][0].length; k++) {
					System.out.print("\t");
					for (int l = 0; l < tombola[0][0][0].length; l++) {
						if (tombola[i][j][k][l] != 0)
							System.out.printf("%d\t", tombola[i][j][k][l]);
						else
							System.out.print("\t");

					}
					System.out.print("\n\n");
				}
				System.out.print("\n\n");
			}
		}
	}

	/**
	 * Crosses a randomly chosen number for each player.
	 *
	 * @param tombola The 4D array representing the bingo cards for all players.
	 * @param counter The array to keep track of crossed numbers for each player.
	 * @param num     The number to cross.
	 */
	private static void cross(int[][][][] tombola, int[][] counter, int num) {
		for (int i = 0; i < tombola.length; i++) {
			for (int j = 0; j < tombola[0].length; j++) {
				for (int k = 0; k < tombola[0][0].length; k++) {
					for (int l = 0; l < tombola[0][0][0].length; l++) {
						if (tombola[i][j][k][l] == num)
							counter[i][j]++;
					}
				}
			}
		}
	}

	/**
	 * Retrieves a card of a chosen player.
	 *
	 * @param tombola The 4D array representing the bingo cards for all players.
	 * @param player  The index of the player.
	 * @param card    The index of the card.
	 * @return The bingo card of the chosen player.
	 */
	private static int[][] getCard(int[][][][] tombola, int player, int card) {
		int[][] cards = new int[tombola[0][0].length][tombola[0][0][0].length];

		for (int i = 0; i < tombola[0][0].length; i++)
			System.arraycopy(tombola[player][card][i], 0, cards[i], 0, tombola[0][0][0].length);

		return cards;
	}

	/**
	 * Conducts the bingo game.
	 *
	 * @param tombola The 4D array representing the bingo cards for all players.
	 * @param players The number of players.
	 * @throws InterruptedException If the game thread is interrupted.
	 */
	private static void game(int[][][][] tombola, int players) throws InterruptedException {
		int num = 90;
		int[] gameNum = new int[num];
		randomize(gameNum, 1, num, num);

		// Counter of crossed numbers in each card
		int[][] counter = new int[players][tombola[0].length];
		for (int j = 0; j < players; j++) {
			for (int k = 0; k < tombola[0].length; k++) {
				counter[j][k] = 0;
			}
		}

		// Starting the game
		System.out.println("Let's Play BINGO!!!\n");
		int i = 0;
		boolean endGame = false;
		while (!endGame) {
			System.out.println("The next number drawn is " + gameNum[i++]);
			sleep(50);
			cross(tombola, counter, gameNum[i]);

			// Check for winners
			for (int j = 0; j < players; j++) {
				for (int k = 0; k < tombola[0].length; k++) {
					if (counter[j][k] == 15) { // Displaying 1 or more players who have won
						endGame = true; // Exiting from the loop
						displayWinner(getCard(tombola, j, k), j + 1);
					}
				}
			}
		}
	}

	/**
	 * Displays the winner of the game.
	 *
	 * @param card   The bingo card of the winning player.
	 * @param player The index of the winning player.
	 */
	public static void displayWinner(int[][] card, int player) {
		StdDraw.setXscale(0.0, 45.0);
		StdDraw.setYscale(0.0, 45.0);
		StdDraw.clear(Color.WHITE);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text(20.0, 30.0, "Player " + player + " is a Winner");
		double y = 26.0, x = 5.0;
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 9; c++) {
				if (card[r][c] == 0) {
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.filledSquare(x, y, 2.0);
				} else {
					StdDraw.setPenColor(Color.RED);
					StdDraw.filledSquare(x, y, 2.0);
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(x, y, (card[r][c] + ""));
				}
				x = x + 4.0;
			}
			x = 5.0;
			y = y - 4.0;
		}
		String file = "Player " + player + " is a Winner" + ".jpg";
		StdDraw.show(2000);
	}

	/**
	 * Main method.
	 *
	 * @param args Command-line arguments.
	 * @throws IOException          If an I/O error occurs while reading the input file.
	 * @throws InterruptedException If the game thread is interrupted.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// Read input from file
		File inputFile = new File("BingoGameInput.txt");
		Scanner scanner = new Scanner(inputFile);

		// Read the number of players from the file
		int players = scanner.nextInt();

		// Rest of your code remains the same
		int card = 6, row = 3, col = 9;
		int[][][][] tombola = new int[players][card][row][col];
		int[][] template = new int[card * row][col];

		// Preparing the cards and displaying them
		fillTemplate(template, scanner);
		fillAllCards(tombola, template);
		displayCards(tombola);

		// Starting the Game
		game(tombola, players);
	}
}
