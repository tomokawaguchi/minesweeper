package minesweeperGame;

public class Cell {
	private boolean hasMine;
	private int minesNearby;
	private boolean isRevealed;
	private String result;
	
	public Cell() {
		this.hasMine = false;
		this.minesNearby = 0;
		this.isRevealed = false;
		this.result = " ";
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
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void increaseMinesNearbyByOne() {
		this.minesNearby += 1;
	}
	
	public int getMinesNearby() {
		return this.minesNearby;
	}
	
	public void setIsRevealed(boolean b) {
		this.isRevealed = b;
	}
	
	public boolean getIsRevealed() {
		return this.isRevealed;
	}
}
