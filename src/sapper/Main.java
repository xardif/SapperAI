package sapper;

import sapper.map.Field;
import sapper.map.Map;
import sapper.map.objects.Blockade;
import sapper.map.objects.Bomb;
import sapper.map.objects.Civillians;
import sapper.map.objects.FieldObject;
import sapper.map.objects.Sapper;
import sapper.map.objects.types.Type;

public class Main {

	public static void init(Map map) {
		//bomba
		Bomb b = new Bomb(Type.C4, 2, 180, true);
		map.setField(15, 15, b);
		
		//blokady
		map.setField(2, 3, new Blockade());
		map.setField(2, 4, new Blockade());
		map.setField(2, 5, new Blockade());
		map.setField(3, 5, new Blockade());
		map.setField(4, 5, new Blockade());
		map.setField(5, 5, new Blockade());
		map.setField(6, 5, new Blockade());
		map.setField(7, 5, new Blockade());
		map.setField(8, 5, new Blockade());
		map.setField(8, 4, new Blockade());
		map.setField(8, 3, new Blockade());
		map.setField(8, 2, new Blockade());
		map.setField(8, 1, new Blockade());
		
		map.setField(0, 8, new Blockade());
		map.setField(0, 9, new Blockade());
		map.setField(2, 8, new Blockade());
		map.setField(2, 9, new Blockade());
		map.setField(2, 10, new Blockade());
		map.setField(3, 10, new Blockade());
		map.setField(4, 10, new Blockade());
		map.setField(5, 10, new Blockade());
		map.setField(6, 10, new Blockade());
		map.setField(7, 10, new Blockade());
		map.setField(8, 10, new Blockade());
		map.setField(8, 9, new Blockade());
		map.setField(8, 8, new Blockade());
		map.setField(8, 7, new Blockade());
		
		for(int i=1; i<14; i++)
			map.setField(12, i, new Blockade());
		
		map.setField(14, 14, new Blockade());
		map.setField(14, 15, new Blockade());
		
		//cywile
		map.setField(11, 15, new Civillians(20));
		map.setField(13, 12, new Civillians(87));
		map.setField(13, 14, new Civillians(100));
		map.setField(15, 10, new Civillians(11));


		//saper
		Sapper s = new Sapper(map.getField(0, 0));
		map.setField(0, 0, s);
		s.getSapperLogic().findPath(s.getSapperLogic().getField(), map.getField(15, 15));
	}
	
	public static void main(String[] args) {
		Map map = new Map();
		init(map);
		
		/*
		Field f = new Field();
		f.getObjects().add(new Bomb());
		
		for(FieldObject obj: f.getObjects()) {
			if( obj instanceof Bomb ) {
				Bomb bomb = (Bomb) obj;
				System.out.println("radius = " + bomb.getBombType().getRadius());
			}		
		}
		*/
	}

}