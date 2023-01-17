package minesweeperGame;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Minesweeper {

	// This arrays holds all the numbers and mines --> reference to game answer
	final static Cell[][] cellsWithAll = new Cell[10][10];

	static int[] userInput = new int[2]; // e.g { 2, 6 } --> assign from Scanner value

	// To keep track of if a game is still continuing or not
	static boolean hasNextTurn = true;

	// Scanner to read user's input
	static Scanner userInputScanner = new Scanner(System.in);

	public static void main(String[] args) {

		// Placing a default Cell classes in 10x10 arrays
		placeCells();

		// Generate a random number pairs for mine coordinates
		int[][] minesCoordinates = generateRandomMineCoordinate();
		
		// Place mines based on the random coordinates generated
		placeMines(minesCoordinates);
		
		// Allocate the numbers around the mines in cellsWithAll
		handleNumOfMinesSurrounded();
		
		// printAll(); // for the dev 

		while (hasNextTurn) {
			// Print a current progress of a game
			printProgress();

			System.out.println();
			System.out.println();

			// Validate the input if it's integer
			userInput[0] = getUserInput("Enter Column Number: ");
			userInput[1] = getUserInput("Enter Row Number: ");

			// Look up selected coordinate in cellsWithAll
			Cell selectedCell = cellsWithAll[userInput[0]][userInput[1]];
			
			// Update the Cell state 
			selectedCell.setIsRevealed();
			
			// If selected cell has mine, exit
			if (selectedCell.getMinesNearby() == 999) hasNextTurn = false;
			
			// If a use reveals all the cell except mines, break out the loop
			if (hasRevealedAll()) break;	
		}
		
		// Show Result
		showResult();
		
	}
	
	
	/**
	 * Placing a default Cell classes in 10x10 arrays
	 */
	public static void placeCells() {
		for (int i = 0; i < cellsWithAll.length; i++) {
			for (int j = 0; j < cellsWithAll[i].length; j++) {
				cellsWithAll[i][j] = new Cell();
			}
		}
	}
	
	
	/**
	 * @return 2D int arrays that has 10 slots, and each has a pair of coordinate
	 *         with number between 0 ~ 9
	 *
	 */
	public static int[][] generateRandomMineCoordinate() {

		Random random = new Random();
		int[][] minesCoordinates = new int[10][2];

		for (int i = 0; i < minesCoordinates.length; i++) {

			for (int j = 0; j < minesCoordinates[i].length; j++) {
				minesCoordinates[i][j] = random.nextInt(10); // 0 ~ 9
			}

			// If exactly same pair is generated, decrease the i to go around the same index
			// of loop
			for (int t = 0; t < i; t++) {
				if (Arrays.equals(minesCoordinates[i], minesCoordinates[t])) {
					i = i - 1;
				}
			}
		}

		return minesCoordinates;
	}
	
	
	/**
	 * 
	 * @param minesCoordinates is 2D arrays that contains non-identical pairs of numbers
	 * 
	 * Looping through minesCoordinates arrays to log 999 at the mine coordinate position
	 * 
	 */
	public static void placeMines(int[][] minesCoordinates) {
		for (int i = 0; i < minesCoordinates.length; i++) {
			int[] coordinate = minesCoordinates[i]; // e.g. [2, 6]
			// 999 to indicate it's a cell with mine
			cellsWithAll[coordinate[0]][coordinate[1]].setMinesNearby(999);
			cellsWithAll[coordinate[0]][coordinate[1]].setHasMine(true);
		}
	}
	
	
	/**
	 * This method is to allocate and assigned the number of mines surrounded 
	 * on each cell in the cellsWithAll arrays
	 */
	public static void handleNumOfMinesSurrounded() {
		for (int i = 0; i < cellsWithAll.length; i++) {
			for (int j = 0; j < cellsWithAll[i].length; j++) {

				if (cellsWithAll[i][j].getMinesNearby() == 999) {
					if (j != 0) {
						// left
						if (cellsWithAll[i][j - 1].getMinesNearby() != 999) {
							cellsWithAll[i][j - 1].increaseMinesNearbyByOne();
						}

						// left up
						if (i != 0 && cellsWithAll[i - 1][j - 1].getMinesNearby() != 999) {
							cellsWithAll[i - 1][j - 1].increaseMinesNearbyByOne();
						}

						// left bottom
						if (i != 9 && cellsWithAll[i + 1][j - 1].getMinesNearby() != 999) {
							cellsWithAll[i + 1][j - 1].increaseMinesNearbyByOne();
						}

					}

					if (j != 9) {
						// right
						if (cellsWithAll[i][j + 1].getMinesNearby() != 999) {
							cellsWithAll[i][j + 1].increaseMinesNearbyByOne();
						}

						// right up
						if (i != 0 && cellsWithAll[i - 1][j + 1].getMinesNearby() != 999) {
							cellsWithAll[i - 1][j + 1].increaseMinesNearbyByOne();
						}

						// right bottom
						if (i != 9 && cellsWithAll[i + 1][j + 1].getMinesNearby() != 999) {
							cellsWithAll[i + 1][j + 1].increaseMinesNearbyByOne();
						}
					}

					// Above
					if (i != 0 && cellsWithAll[i - 1][j].getMinesNearby() != 999) {
						cellsWithAll[i - 1][j].increaseMinesNearbyByOne();
					}

					// Bottom
					if (i != 9 && cellsWithAll[i + 1][j].getMinesNearby() != 999) {
						cellsWithAll[i + 1][j].increaseMinesNearbyByOne();
					}

				}
			}
		}
	}
	
	
	/**
	 * This is to print a current progress by using a data stored in cellsToShow
	 * arrays 1. If a cell is not selected, it prints "?" 2. If a cell has been
	 * selected and is a non mine cell, print a number 3. If a cell has been
	 * selected and is a mine cell, print "X"
	 */
	public static void printProgress() {

		// Insert tab space
		System.out.print("\t");

		// For the coordinates for the row
		for (int i = 0; i < 10; i++) {
			System.out.print("  " + i + " ");
		}

		System.out.println(); // Insert one line for readability

		// For the each columns
		for (int i = 0; i < 10; i++) {
			// For the coordinates for the columns
			// Insert tab space and |
			System.out.println();
			System.out.print(" " + i + "\t| ");

			for (int j = 0; j < 10; j++) {
				Cell current = cellsWithAll[i][j];
				
				if(current.getIsRevealed()) {
					// For the cells that a user has selected
					
					 if (current.getMinesNearby() == 0) {
						// For a cell with no mines nearby
						System.out.print(" ");
	
					} else if (current.getMinesNearby() == 999) {
						// For a cell with a mine
						System.out.print("x");
	
					} else {
						// For a non-mine cell with numbers --> 1 ~ 8
						System.out.print(current.getMinesNearby());
					}
				} else {
					// For the cells that has not selected yet
					System.out.print("?");
				}

				// Print | for the end of coordinate
				System.out.print(" | ");
			}

		}
	}
	
	
	/**
	 * @param message to show a user as an error indicator
	 * @return return the valid input value
	 * 
	 *         This is to validate the user input and returns the valid number
	 */
	public static int getUserInput(String message) {
		boolean isValid = false;
		int input = 0;

		while (!isValid) {
			System.out.print(message);

			if (!userInputScanner.hasNextInt()) {
				System.out.println("Oops, please enter a number!");
				userInputScanner.next();
			} else {
				input = userInputScanner.nextInt();

				if (input < 0 || input > 9) {
					System.out.println("Oops, please select a number between 0 and 9!");
				} else {
					isValid = true;
				}
			}
			System.out.println();
		}
		return input;
	}
	
	
	/**
	 * @return boolean value to indicate if ther is any cells with mine that has been revealed
	 * 
	 * This is to check if a user has revealed all cells except mines (90 cells by default)
	 */
	public static boolean hasRevealedAll() {
		for(int i = 0; i < cellsWithAll.length; i++) {
			for(int j = 0; j < cellsWithAll[i].length; j++) {
				Cell current = cellsWithAll[i][j];
				
				if(!current.getIsRevealed() && !current.getHasMine()) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	/**
	 * It prints a message depending on the user results
	 */
	public static void showResult() {
		printProgress();
		System.out.println();
		System.out.println();
		System.out.println();
		
		if(!hasNextTurn) {
			System.out.println("!!! Booom !!!\nGame Over...");
		} else {
			System.out.println("You won!");
		}
	}
	
	
	// For dev purpuse
	public static void printAll() {

		// Insert tab space
		System.out.print("\t");

		// For the coordinates for the row
		for (int i = 0; i < 10; i++) {
			System.out.print("  " + i + " ");
		}

		System.out.println(); // Insert one line for readability

		// For the each columns
		for (int i = 0; i < 10; i++) {
			// For the coordinates for the columns
			// Insert tab space and |
			System.out.println();
			System.out.print(" " + i + "\t| ");

			for (int j = 0; j < 10; j++) {
				Cell current = cellsWithAll[i][j];
				
					if (current.getMinesNearby() == 999) {
						// For a mine cell
						System.out.print("x");
	
					} else {
						// For a non-mine cell --> 1 ~ 8
						System.out.print(current.getMinesNearby());
					}
				

				// Print | for the end of coordinate
				System.out.print(" | ");
			}

		}
	}
}
