package pl.edu.amu.wmi.sapper.map.objects;

public class Civilians extends FieldObject {
	private int number;
	private boolean isDead;
	
	public Civilians(int number) {
		this.number = number;
		this.isDead = false;
	}
}
