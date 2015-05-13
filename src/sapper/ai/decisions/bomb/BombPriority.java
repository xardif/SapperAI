package sapper.ai.decisions.bomb;

public enum BombPriority implements Comparable<BombPriority> {

	ZERO(0),
	VERY_LOW(1),
	LOW(2),
	MEDIUM(3),
	HIGH(4),
	VERY_HIGH(5),
	CRITICAL(6);
	
	private int value;    

	private BombPriority(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
