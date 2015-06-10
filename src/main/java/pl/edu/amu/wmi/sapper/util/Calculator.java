package pl.edu.amu.wmi.sapper.util;

import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.Map;
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
		int rightBorder = ((center.getXPosition() + radius) < 0) ? 0 : (center.getXPosition() + radius);
		int topBorder = ((center.getYPosition() - radius) < 0) ? 0 : (center.getYPosition() - radius);
		int bottomBorder = ((center.getYPosition() + radius) < 0) ? 0 : (center.getYPosition() + radius);
		
		
		for(int x = ) {
			
			
		}
		
		
		return result;
		
	}
	
}
