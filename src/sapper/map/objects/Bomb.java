package sapper.map.objects;
import sapper.map.objects.FieldObject;
import sapper.map.objects.types.BombType;
import sapper.map.objects.types.Type;

public class Bomb extends FieldObject {
	private BombType bombType;

	public Bomb() {
		this.bombType = new BombType();
	}
	
	public Bomb(Type type, int size, int timeToDetonation, boolean isActive) {
		int r = size*2;	//tu powinno byæ dobranie pola ra¿enia na podstawie materia³u i wielkoœci
		this.bombType = new BombType(r, type, size, timeToDetonation, isActive);
	}
	
	public BombType getBombType() {
		return bombType;
	}
	
}
