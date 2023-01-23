# Minesweeper Project

This project was built in Java and it allows users to play a simplified mineesweeper game in a Java console.

<img src="https://github.com/tomokawaguchi/minesweeper/blob/develop/src/minesweeperGame/minesweeper-snapshot.png" width="auto" height="400" />


## Project Brief

### Aims

The aim of this project is to reinforce my foundamental learning in Java by writing code that runs in Java console, understanding of the role of class its fiedls and method in a small application. 

### MVP (conducted as Nology course work)

**Basic Requirements**

- Recreate a simplified version of the game Minesweeper to be played in the java console. 
- The game should be able to randomly generate 10 mines in a 10x10 grid.
- The user will be able to enter a command that represents a coordinate to check a location for a mine. 
- The application will display a number from 0-8 depending on how many mines surround that location. 
- If the user selects a mine, the game will respond "boom!" and the game will be lost. 
- If every non-mine square has been revealed, the game is won Render the grid to the console after every user command


**Bonuses(Optional)**

- Allow for the user to configure number of mines and grid size via a configuration.json file (Difficult).
- Discovering an empty square should reveal all squares around it, and cascade into other nearby empty squares.


## Technical Implementation

Since it is played in the console, in order to communicate with a user, `Scanner` class is used to read the inputs in the console. Also `while` loop is used to kepp rendering the latest progress of the game in every turn of the game. As one of the project requirements, the minesweeper table should holds 100 cells/spot that a user can select, and I have created custom class of 'Cell' that represents indivisual spot in a game table. The `Cell` class holds fields of `hasMine`, `minesNearby`, `isRevealed`, and `result` that will be updated and utilised through the game. 

Some of the featured functionalities are:

**1. Selecting random pair of coordinate**

At the beginning of the game, I have written a logic to set up random 10 pair of coordinates that will be used for coordinates of mines in 10 x 10 grids field. These are stored in 2D int array (`new int[10][2]`). `generateRandomMineCoordinate()` ensures that not only generates 10 pairs of coordinates but also these are all unique to each other. 

**2. Assigning a number of mines surrounded in each Cell class**

To assign numbers of mines surrounded to each Cell, I have first placed mines in a table(`Cell[][] cellsArray = new Cell[10][10]`) by using the random coordinat that I have generated. Then I loop the `cellsArray` to increase/update the `minesNearby` field value of each `Cell` class depending on the mines location. Since `cellsArray` is 2D array, each mine can be identified with inner and outer loops' indexes. For example, a Cell class with a mine would be `cellsArray[i][j]` with a combination with `hasMine` field. Based on that, I have identified 8 cells around it, for example left top cell is `cellsArray[i-1][j-1]`. 

**3. Revealing surrounding cells if there is no mine indictaor number**

In a real minesweeper game, if you select a cell with no number allocated, it will also reveal surrounding cells with no numbers. In my project, I have managed to achive a similar functionality but only with immediate 8 cells around it by utilising a similar logic with assigning a number of mines surrounded(see #2 above).


## Refection

- Initially I have almost achieved this project without `Cell` class by utilisiung 2 of 2D arrays. However I start to realise that it would be very handly to have `Cell` class that can hold each state such as `hasMine`,`minesNearby`and other fields that I have ended up creating. This process was very interesting as it have made me realised how powerful it is to create your own class in such situation.

- It was a great challenge to handle 2D arrays constantly, which is quite different situation from past projects in JavaScript.  

## Future Goals

- Implementing a complete solution to reveal the surrounding cells that have no numbers in addition would be great so that my minesweeper game became one step closer to the real one.
- Making a game more dynamic such as allowing users to select size of table and a number of mines.
