package pl.edu.amu.wmi.sapper.map.objects;

import pl.edu.amu.wmi.sapper.map.objects.types.BombSize;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;

public class Bomb extends FieldObject {
	private BombType bombType;

	public Bomb() {
		this.bombType = new BombType();
	}
	
	public Bomb(Type mType, int size, int timeToDetonation, boolean isActive) {
		int r = size*2;	//tu powinno by� dobranie pola ra�enia na podstawie materia�u i wielko�ci
		this.bombType = new BombType(r, mType, BombSize.valueOf(size), timeToDetonation, isActive);
	}

	public BombType getBombType() {
		return bombType;
	}
	
}
