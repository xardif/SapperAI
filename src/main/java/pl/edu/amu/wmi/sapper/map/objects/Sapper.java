package pl.edu.amu.wmi.sapper.map.objects;

import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.ai.SapperLogic;

public class Sapper extends FieldObject {
	private SapperLogic sapper;

	public Sapper(Field field) {
		this.sapper = new SapperLogic(field);
	}

	public SapperLogic getSapperLogic() {
		return sapper;
	}
}
