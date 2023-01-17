package minesweeperGame;

public class Table  {
	Cell[][] cellsWithAll;
	
//	public Table() {
//		for(int i = 0; i < 10; i++) {
//			for(int j = 0; j < 10; j++) {
//				Cell myCell = new Cell();
//				cellsWithAll[i][j] = ;
//			}
//		}
//	}
	
	
	
	public Cell[][] getTable() {
		return this.cellsWithAll;
	}
	
	public Cell getCell(int[] coordinate) {
		return cellsWithAll[coordinate[0]][coordinate[1]];
	}
}
