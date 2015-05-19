package pl.edu.amu.wmi.sapper.ai.decisions.bomb;

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
	
	@Override
	public String toString() {
		switch(this) {
		case CRITICAL: return "CRITICAL";
		case HIGH: return "HIGH";
		case LOW: return "LOW";
		case MEDIUM: return "MEDIUM";
		case VERY_HIGH: return "VERY_HIGH";
		case VERY_LOW: return "VERY_LOW";
		case ZERO: return "ZERO";
		default: return "UNKNOWN";		
		}
	}

}
