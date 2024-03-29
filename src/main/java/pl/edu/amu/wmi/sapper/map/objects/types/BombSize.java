package pl.edu.amu.wmi.sapper.map.objects.types;

public enum BombSize {

	TINY(1),
	SMALL(2),
	MEDIUM(3),
	BIG(4),
	VERY_BIG(5);
	
	private final int value;

	private BombSize(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}	

    public static BombSize valueOf(int i){
        for(BombSize b : BombSize.values()){
            if(b.getValue() == i)
                return b;
        }
        return null;
    }
    
    @Override
    public String toString() {
    	switch(this) {
		case BIG: return "BIG";
		case SMALL: return "SMALL";
		case TINY: return "TINY";
		case MEDIUM : return "MEDIUM";
		case VERY_BIG: return "VERY_BIG";
		default: return "UNKNOWN";    	
    	}
    }

}
