package pl.edu.amu.wmi.sapper.util;

import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.objects.Civilians;
import pl.edu.amu.wmi.sapper.map.objects.FieldObject;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;

public class Calculator {

	private static Map map;
	
	public static void setMap(Map m) {
		map = m;
	}
	
	public static int calculateVictims(BombType bomb) {
		
		int result = 0;
		
		Field center = bomb.getField();
		int radius = bomb.getRadius();
		
		int leftBorder = ((center.getXPosition() - radius) < 0) ? 0 : (center.getXPosition() - radius);
		int rightBorder = ((center.getXPosition() + radius) >= map.getRows()) ? map.getRows() - 1 : (center.getXPosition() + radius);
		int topBorder = ((center.getYPosition() - radius) < 0) ? 0 : (center.getYPosition() - radius);
		int bottomBorder = ((center.getYPosition() + radius) >= map.getCols()) ? map.getCols() - 1 : (center.getYPosition() + radius);
		
		
		for(int x = leftBorder; x < rightBorder; x++) {
			for(int y = topBorder; y < bottomBorder; y++) {
				for(FieldObject fieldObject: map.getField(x, y).getObjects())
					if(fieldObject instanceof Civilians) {
						result += ((Civilians)fieldObject).getNumber();
					}
			}			
		}		
		
		return result;
		
	}
	
}
