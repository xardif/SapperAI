package pl.edu.amu.wmi.sapper.map.objects.types;

public class BombType {
	private int radius;
	private Type type;
	private BombSize size;
	private int timeToDetonation;
	private boolean isActive;
	private boolean isDetonated;
	
	public BombType() {}
	
	public BombType(int radius, Type type, BombSize size, int timeToDetonation, boolean isActive) {
		this.radius = radius;
		this.type = type;
		this.size = size;
		this.timeToDetonation = timeToDetonation;
		this.isActive = isActive;
		this.isDetonated = false;
	}
	
	 public int getRadius() {
		 return radius;
	 }
	 
	 public Type getMaterial() {
		 return type;
	 }
	 
	 public BombSize getSize() {
		 return size; 
	 }
	 
	 public int getTimeToDetonation() {
		 return timeToDetonation;
	 }
	 
	 public boolean getIsActive() {
		 return isActive;
	 }
	 public void setIsActive() {
		 this.isActive = false;
	 }
	 
	 public boolean getIsDetonated() {
		 return isDetonated;
	 }
	 @Override
	public String toString() {
		return "BombType [radius=" + radius + ", type=" + type + ", size="
				+ size + ", timeToDetonation=" + timeToDetonation
				+ ", isActive=" + isActive + ", isDetonated=" + isDetonated
				+ "]";
	}

	public void setIsdetonated() {
		 this.isDetonated = true;
	 }

	public int getPotentialVictims() {
		// TODO Auto-generated method stub
		return size.getValue() * radius;
	}
}
