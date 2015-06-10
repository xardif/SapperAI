package pl.edu.amu.wmi.sapper.map.objects;

public class Civilians extends FieldObject {
	private int number;
	private boolean isDead;
	
	public Civilians(int number) {
		this.number = number;
		this.isDead = false;
	}

	public Civilians() {
	}

	public int getNumber() {
		return number;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}

	@Override
	public String toString() {
		return "Civilians";
	}

}
