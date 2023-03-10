package minesweeperGame;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Minesweeper {

	// This arrays holding all the cells
	final private static Cell[][] cellsArray = new Cell[10][10];

	private int[] userInput = new int[2]; // e.g { 2, 6 } --> assign from Scanner value

	// To keep track of if a game is still continuing or not
	private boolean hasNextTurn = true;

	// Scanner to read user's input
	private Scanner userInputScanner = new Scanner(System.in);

	private boolean playAgain = true;

	public static void main(String[] args) {
		// Minesweeper instance
		Minesweeper minesweeperGame = new Minesweeper();

		// Placing a default Cell classes in 10x10 arrays
		minesweeperGame.placeCells();

		while (minesweeperGame.playAgain) {

			// Reset the hasNextTurn
			minesweeperGame.hasNextTurn = true;
			
			// Reset the fields of each Cell
			minesweeperGame.resetCells();

			// Generate a random number pairs for mine coordinates
			int[][] minesCoordinates = minesweeperGame.generateRandomMineCoordinate();

			// Place mines based on the random coordinates generated
			minesweeperGame.placeMines(minesCoordinates);

			// Allocate the numbers around the mines in cellsArray
			minesweeperGame.handleNumOfMinesSurrounded();

			// ---- DELETE LATER ----
			// printAll(); // for the dev purpose

			while (minesweeperGame.hasNextTurn) {
				// Print a current progress of a game
				minesweeperGame.printProgress();

				System.out.println();
				System.out.println();

				// Validate the input if it's integer
				minesweeperGame.userInput[0] = minesweeperGame.getUserInput("Enter Column Number: ");
				minesweeperGame.userInput[1] = minesweeperGame.getUserInput("Enter Row Number: ");

				// Look up selected coordinate in cellsArray
				Cell selectedCell = cellsArray[minesweeperGame.userInput[0]][minesweeperGame.userInput[1]];

				// Update the Cell state
				selectedCell.setIsRevealed(true);

				// Check if surrounding cells can be revealed or not
				minesweeperGame.revealSurroundingCells();

				// If selected cell has mine, exit
				if (selectedCell.getMinesNearby() == 999)
					minesweeperGame.hasNextTurn = false;

				// If a use reveals all the cell except mines, break out the loop
				if (minesweeperGame.hasRevealedAll())
					break;
			}

			// Show Result
			minesweeperGame.showResult();

			// Ask a user if playing again
			minesweeperGame.handlePlayAgain();

		}

	}

	/**
	 * Placing a default Cell classes in 10x10 arrays
	 */
	public void placeCells() {
		for (int i = 0; i < cellsArray.length; i++) {
			for (int j = 0; j < cellsArray[i].length; j++) {
				cellsArray[i][j] = new Cell();
			}
		}
	}
	
	/**
	 * 
	 * Loop through cellsArray to reset all the fields of each Cell
	 * 
	 */
	public void resetCells() {
		for (int i = 0; i < cellsArray.length; i++) {
			for (int j = 0; j < cellsArray[i].length; j++) {
				cellsArray[i][j].setHasMine(false);
				cellsArray[i][j].setMinesNearby(0);
				cellsArray[i][j].setIsRevealed(false);
				cellsArray[i][j].setResult(" ");
			}	
		}
	}
	
	
	/**
	 * @return 2D int arrays that has 10 slots, and each has a pair of coordinate
	 *         with number between 0 ~ 9
	 *
	 */
	public int[][] generateRandomMineCoordinate() {

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
	 * @param minesCoordinates is 2D arrays that contains non-identical pairs of
	 *                         numbers Looping through minesCoordinates arrays to
	 *                         log 999 at the mine coordinate position
	 * 
	 */
	public void placeMines(int[][] minesCoordinates) {
		for (int i = 0; i < minesCoordinates.length; i++) {
			int[] coordinate = minesCoordinates[i]; // e.g. [2, 6]
			// 999 to indicate it's a cell with mine
			cellsArray[coordinate[0]][coordinate[1]].setMinesNearby(999);
			cellsArray[coordinate[0]][coordinate[1]].setHasMine(true);
			cellsArray[coordinate[0]][coordinate[1]].setResult("x");
		}
	}

	/**
	 * This method is to allocate and assigned the number of mines surrounded on
	 * each cell in the cellsArray arrays
	 */
	public void handleNumOfMinesSurrounded() {
		for (int i = 0; i < cellsArray.length; i++) {
			for (int j = 0; j < cellsArray[i].length; j++) {

				if (cellsArray[i][j].getHasMine()) {
					if (j != 0) {
						// left
						if (!cellsArray[i][j - 1].getHasMine()) {
							cellsArray[i][j - 1].increaseMinesNearbyByOne();
							cellsArray[i][j - 1].setResult(Integer.toString(cellsArray[i][j - 1].getMinesNearby()));
						}

						// left up
						if (i != 0 && !cellsArray[i - 1][j - 1].getHasMine()) {
							cellsArray[i - 1][j - 1].increaseMinesNearbyByOne();
							cellsArray[i - 1][j - 1]
									.setResult(Integer.toString(cellsArray[i - 1][j - 1].getMinesNearby()));
						}

						// left bottom
						if (i != 9 && !cellsArray[i + 1][j - 1].getHasMine()) {
							cellsArray[i + 1][j - 1].increaseMinesNearbyByOne();
							cellsArray[i + 1][j - 1]
									.setResult(Integer.toString(cellsArray[i + 1][j - 1].getMinesNearby()));
						}

					}

					if (j != 9) {
						// right
						if (!cellsArray[i][j + 1].getHasMine()) {
							cellsArray[i][j + 1].increaseMinesNearbyByOne();
							cellsArray[i][j + 1].setResult(Integer.toString(cellsArray[i][j + 1].getMinesNearby()));
						}

						// right up
						if (i != 0 && !cellsArray[i - 1][j + 1].getHasMine()) {
							cellsArray[i - 1][j + 1].increaseMinesNearbyByOne();
							cellsArray[i - 1][j + 1]
									.setResult(Integer.toString(cellsArray[i - 1][j + 1].getMinesNearby()));
						}

						// right bottom
						if (i != 9 && !cellsArray[i + 1][j + 1].getHasMine()) {
							cellsArray[i + 1][j + 1].increaseMinesNearbyByOne();
							cellsArray[i + 1][j + 1]
									.setResult(Integer.toString(cellsArray[i + 1][j + 1].getMinesNearby()));
						}
					}

					// Above
					if (i != 0 && !cellsArray[i - 1][j].getHasMine()) {
						cellsArray[i - 1][j].increaseMinesNearbyByOne();
						cellsArray[i - 1][j].setResult(Integer.toString(cellsArray[i - 1][j].getMinesNearby()));
					}

					// Bottom
					if (i != 9 && !cellsArray[i + 1][j].getHasMine()) {
						cellsArray[i + 1][j].increaseMinesNearbyByOne();
						cellsArray[i + 1][j].setResult(Integer.toString(cellsArray[i + 1][j].getMinesNearby()));
					}

				}
			}
		}
	}
	
	
	/**
	 * 
	 * This is to check if the cells around the selected cell can be revealed or not
	 * 
	 */
	public void revealSurroundingCells() {
		// For the surrounding cells
		for (int i = 0; i < cellsArray.length; i++) {
			for (int j = 0; j < cellsArray[i].length; j++) {
				
				if(i == userInput[0] && j == userInput[1] && cellsArray[i][j].getMinesNearby() == 0) {
					
					if (j != 0) {
						// left
						if (cellsArray[i][j - 1].getMinesNearby() == 0) {
							cellsArray[i][j - 1].setResult(" ");
							cellsArray[i][j - 1].setIsRevealed(true);
						}

						// left up
						if (i != 0 && cellsArray[i - 1][j - 1].getMinesNearby() == 0) {
							cellsArray[i - 1][j - 1].setResult(" ");
							cellsArray[i - 1][j - 1].setIsRevealed(true);
						}

						// left bottom
						if (i != 9 && cellsArray[i + 1][j - 1].getMinesNearby() == 0) {
							cellsArray[i + 1][j - 1].setResult(" ");
							cellsArray[i + 1][j - 1].setIsRevealed(true);
						}

					}
					
					if (j != 9) {
						// right
						if (cellsArray[i][j + 1].getMinesNearby() == 0) {
							cellsArray[i][j + 1].setResult(" ");
							cellsArray[i][j + 1].setIsRevealed(true);
						}

						// right up
						if (i != 0 && cellsArray[i - 1][j + 1].getMinesNearby() == 0) {
							cellsArray[i - 1][j + 1].setResult(" ");
							cellsArray[i - 1][j + 1].setIsRevealed(true);
						}

						// right bottom
						if (i != 9 && cellsArray[i + 1][j + 1].getMinesNearby() == 0) {
							cellsArray[i + 1][j + 1].setResult(" ");
							cellsArray[i + 1][j + 1].setIsRevealed(true);
						}
					}
					
					
					// Above
					if (i != 0 && cellsArray[i - 1][j].getMinesNearby() == 0) {
						cellsArray[i - 1][j].setResult(" ");
						cellsArray[i - 1][j].setIsRevealed(true);
					}
					
					// Bottom
					if (i != 9 && cellsArray[i + 1][j].getMinesNearby() == 0) {
						cellsArray[i + 1][j].setResult(" ");
						cellsArray[i + 1][j].setIsRevealed(true);
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
	public void printProgress() {

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
				Cell current = cellsArray[i][j];

				if (current.getIsRevealed()) {
					System.out.print(current.getResult());

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
	public int getUserInput(String message) {
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
	 * @return boolean value to indicate if ther is any cells with mine that has
	 *         been revealed
	 * 
	 *         This is to check if a user has revealed all cells except mines (90
	 *         cells by default)
	 */
	public boolean hasRevealedAll() {
		for (int i = 0; i < cellsArray.length; i++) {
			for (int j = 0; j < cellsArray[i].length; j++) {
				Cell current = cellsArray[i][j];

				if (!current.getIsRevealed() && !current.getHasMine()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * It prints a message depending on the user results
	 */
	public void showResult() {
		printProgress();
		System.out.println();
		System.out.println();
		System.out.println();

		if (!hasNextTurn) {
			System.out.println("!!! Booom !!!\nGame Over...");
		} else {
			System.out.println("You won!");
		}
	}

	/**
	 * This is to handle whether or not a user continues playing a game, show
	 * different message depending on the user's selection
	 */
	public void handlePlayAgain() {
		System.out.println();
		System.out.println("Would you like to play again?\ny --> Play again\nn --> Stop playing");

		char input = userInputScanner.next().charAt(0);

		if (input != 'y') {
			System.out.println();
			System.out.println("Bye for now!");
			playAgain = false;
		} else {
			System.out.println();
			System.out.println("Got it, let's try again!");
			System.out.println();
		}
	}

	// For dev purpose
	// public static void printAll() {

	// 	// Insert tab space
	// 	System.out.print("\t");

	// 	// For the coordinates for the row
	// 	for (int i = 0; i < 10; i++) {
	// 		System.out.print("  " + i + " ");
	// 	}

	// 	System.out.println(); // Insert one line for readability

	// 	// For the each columns
	// 	for (int i = 0; i < 10; i++) {
	// 		// For the coordinates for the columns
	// 		// Insert tab space and |
	// 		System.out.println();
	// 		System.out.print(" " + i + "\t| ");

	// 		for (int j = 0; j < 10; j++) {
	// 			Cell current = cellsArray[i][j];

	// 			System.out.print(current.getResult());
	// 			// System.out.print(current.getMinesNearby());

	// 			// Print | for the end of coordinate
	// 			System.out.print(" | ");
	// 		}

	// 	}
		
	// 	System.out.println();
	// 	System.out.println();
	// }
}
