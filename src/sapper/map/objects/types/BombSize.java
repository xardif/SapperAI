package sapper.map.objects.types;

public enum BombSize {

	TINY(1),
	SMALL(2),
	MEDIUM(3),
	BIG(4),
	VERY_BIG(5);
	
	private int value;    

	private BombSize(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}	
	
}
