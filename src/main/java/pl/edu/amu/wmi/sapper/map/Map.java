package pl.edu.amu.wmi.sapper.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.Civilians;
import pl.edu.amu.wmi.sapper.map.objects.Blockade;
import pl.edu.amu.wmi.sapper.map.objects.FieldObject;
import pl.edu.amu.wmi.sapper.map.objects.Sapper;

public class Map {
	private int X = 16;
	private int Y = 16;
	private Field[][] fields = new Field[X][Y];
	
	public Map() {
		for(int x=0; x<X; x++)
			for(int y=0; y<Y; y++)
				fields[x][y] = new Field(null, null, 1, x, y, -1);
	}
	
	public Field getField(int x, int y) {
		if (x<0 || x>=X)
			return null;
		else if (y<0 || y>=Y)
			return null;
		else
			return fields[x][y];
	}
	
	public int getFieldCost(int x, int y) {
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
	
	public int getX() {
		return X;
	}

	public int getY() {
		return Y;
	}
	
	public List<Bomb> getAllBombs() {
		List<Bomb> result = new ArrayList<>();
		for(Field[] line: fields) {
			for(Field field: line) {
				List<FieldObject> objects = field.getObjects(); 
				for(FieldObject object: objects)
					if(object instanceof Bomb)
						result.add((Bomb) object);
			}
		}
		return result;		
	}
		
	public void PrintSolution(ArrayList<Field> solutionPathList) {
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
					if (getField(i,j).getObjects().isEmpty())
						System.out.print("o "); //solution path
					else if (getField(i,j).getObjects().get(0) instanceof Sapper)
						System.out.print("S ");	//sapper
					else if (getField(i,j).getObjects().get(0) instanceof Bomb)
						System.out.print("! ");	//bomb
					else if(getField(i,j).getObjects().get(0) instanceof Civilians)
						System.out.print("o%"); //civillians
					moveCounter++;
				}
				else if (getField(i,j).getObjects().isEmpty())
					System.out.print(". ");	//road
				else if(getField(i,j).getObjects().get(0) instanceof Blockade)
					System.out.print("# "); //blockade
				else if(getField(i,j).getObjects().get(0) instanceof Civilians)
					System.out.print("% "); //civillians
			}
			System.out.println("");
		}
		System.out.println("Liczba krokow: " + moveCounter);
	}
	
	public static Map buildRandomMap() {
		Map map = new Map();
		
		map.setField(0, 0, new Sapper());
		
		Random random = new Random();
		for(int x = 0; x < map.getX(); x++)
			for(int y = 1; y < map.getY(); y++) {
						
				int decision = random.nextInt(10);
				
				switch(decision) {
				
				
				
				}
				
			}
		
		return map;
	}
	
	
}
