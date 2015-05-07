package sapper.map.objects;

import sapper.ai.SapperLogic;
import sapper.map.Field;

public class Sapper extends FieldObject {
	private SapperLogic sapper;
	
	public Sapper(Field field) {
		this.sapper = new SapperLogic(field);
	}
	
	public SapperLogic getSapperLogic() {
		return sapper;
	}
}
