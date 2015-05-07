package sapper.map;

import java.util.ArrayList;

import sapper.map.Field;
import sapper.map.objects.Blockade;
import sapper.map.objects.Bomb;
import sapper.map.objects.Civillians;
import sapper.map.objects.FieldObject;
import sapper.map.objects.Sapper;

public class Map {
	private static int X = 16;
	private static int Y = 16;
	private static Field[][] fields = new Field[X][Y];
	
	public Map() {
		for(int x=0; x<X; x++)
			for(int y=0; y<Y; y++)
				fields[x][y] = new Field(null, null, 1, x, y, -1);
	}
	
	public static Field getField(int x, int y) {
		if (x<0 || x>=X)
			return null;
		else if (y<0 || y>=Y)
			return null;
		else
			return fields[x][y];
	}
	
	public static int getFieldCost(int x, int y) {
		if (x<0 || x>=X)
			return -1;
		else if (y<0 || y>=Y)
			return -1;
		else
			return fields[x][y].getGCost();
	}
	
	public void setField(int x, int y, FieldObject fieldObject) {
		fields[x][y].getObjects().add(fieldObject);
	}
		
	static public void PrintSolution(ArrayList<Field> solutionPathList) {
		int moveCounter = 0;
		
		for(int i=0; i<X; i++) {
			for(int j=0; j<Y; j++) {
				boolean solutionNode = false;
				for(Field n : solutionPathList) {
					Field tmp = new Field(null,null,0,i,j, -1);
						
					if(n.isMatch (tmp)) {
						solutionNode = true;
						break;
					}
				}
				if(solutionNode) {
					if (Map.getField(i,j).getObjects().isEmpty())
						System.out.print("o "); //solution path
					else if (Map.getField(i,j).getObjects().get(0) instanceof Sapper)
						System.out.print("S ");	//sapper
					else if (Map.getField(i,j).getObjects().get(0) instanceof Bomb)
						System.out.print("! ");	//bomb
					else if(Map.getField(i,j).getObjects().get(0) instanceof Civillians)
						System.out.print("o%"); //civillians
					moveCounter++;
				}
				else if (Map.getField(i,j).getObjects().isEmpty())
					System.out.print(". ");	//road
				else if(Map.getField(i,j).getObjects().get(0) instanceof Blockade)
					System.out.print("# "); //blockade
				else if(Map.getField(i,j).getObjects().get(0) instanceof Civillians)
					System.out.print("% "); //civillians
			}
			System.out.println("");
		}
		System.out.println("Liczba krokow: " + moveCounter);
	}
	
	
	
	
}
