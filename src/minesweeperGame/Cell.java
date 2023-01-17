package minesweeperGame;

public class Cell {
	private boolean hasMine;
	private int minesNearby;
	private boolean isRevealed;
	
	public Cell() {
		this.hasMine = false;
		this.minesNearby = 0;
		this.isRevealed = false;
	}
	
	public boolean getHasMine() {
		return this.hasMine;
	}
	
	public void setHasMine(boolean b) {
		this.hasMine = b;
	}
	
	public void setMinesNearby(int n) {
		this.minesNearby = n;
	}
	
	public void increaseMinesNearbyByOne() {
		this.minesNearby += 1;
	}
	
	public int getMinesNearby() {
		return this.minesNearby;
	}
	
	public void setIsRevealed() {
		this.isRevealed = true;
	}
	
	public boolean getIsRevealed() {
		return this.isRevealed;
	}
}
