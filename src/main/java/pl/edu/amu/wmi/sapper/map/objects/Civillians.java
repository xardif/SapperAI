package pl.edu.amu.wmi.sapper.map.objects;

public class Civillians extends FieldObject {
	private int number;
	private boolean isDead;
	
	public Civillians(int number) {
		this.number = number;
		this.isDead = false;
	}
}
