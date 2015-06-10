package pl.edu.amu.wmi.sapper.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import pl.edu.amu.wmi.sapper.ai.SapperLogic;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.Civilians;
import pl.edu.amu.wmi.sapper.map.objects.Blockade;
import pl.edu.amu.wmi.sapper.map.objects.Empty;
import pl.edu.amu.wmi.sapper.map.objects.FieldObject;
import pl.edu.amu.wmi.sapper.map.objects.Sapper;

public class Map {
	private int rows;
	private int cols;
	private Field[][] fields;
	
	public Map(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
        fields = new Field[rows][cols];
		for(int x=0; x< this.rows; x++)
			for(int y=0; y< this.cols; y++)
				fields[x][y] = new Field(null, null, 1, x, y, -1);
	}
	
	public Field getField(int x, int y) {
		if (x<0 || x>= rows)
			return null;
		else if (y<0 || y>= cols)
			return null;
		else
			return fields[x][y];
	}
	
	public int getFieldCost(int x, int y) {
		if (x<0 || x>= rows)
			return -1;
		else if (y<0 || y>= cols)
			return -1;
		else
			return fields[x][y].getGCost();
	}
	
	public void setField(int x, int y, FieldObject fieldObject) {
		fields[x][y].getObjects().add(fieldObject);
	}
	
	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
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
	
	public Field findObject(FieldObject object) {
		Field result = null;
		for(Field[] row: fields)
			for(Field field: row)
				for(FieldObject fieldObject: field.getObjects())
					if(fieldObject == object) {
						result = field;
						break;
					}
		return result;
	}
	
	public void PrintSolution(List<Field> solutionPathList) {
		int moveCounter = 0;
		
		for(int i=0; i< rows; i++) {
			for(int j=0; j< cols; j++) {
				boolean solutionNode = false;
				for(Field n : solutionPathList) {
					Field tmp = new Field(null,null,0,i,j, -1);
						
					if(n.isMatch (tmp)) {
						solutionNode = true;
						break;
					}
				}
				if(solutionNode) {
					String toPrint = "";
					for(FieldObject object: getField(i,j).getObjects()) {
						
						if (object instanceof Sapper)
							toPrint += "S";	//sapper
						
						if (object instanceof Bomb)
							toPrint += "!";	//bomb
						
						if(object instanceof Civilians)
							toPrint += "%"; //civillians
						
						if(object instanceof Empty && toPrint.isEmpty()) 
							toPrint += "o"; //solution path

						if(toPrint.length() < 2) toPrint += " ";
						
					}
					System.out.print(toPrint);
					moveCounter++;
					
				} else {
				
					String toPrint = "nie jestem kurwa solution";
					for(FieldObject object: getField(i,j).getObjects()) {
									
						if(object instanceof Blockade)
							toPrint += "#"; //blockade
						
						if(object instanceof Civilians)
							toPrint += "%"; //civillians
						
						if (object instanceof Empty && toPrint.isEmpty())
							toPrint += ".";	//road
						
					}
					
					if(toPrint.length() < 2) toPrint += " ";
					System.out.print(toPrint);
					
				}
				
			}
			System.out.println("");
		}
		//System.out.println("Liczba krokow: " + moveCounter);
	}
	
	public void moveSapper(SapperLogic sapper, Field to) {
		List<FieldObject> fieldObjects = sapper.getField().getObjects();
		
		FieldObject sapperField = null;
		List<FieldObject> newFieldObjects = new ArrayList<>();
		for(FieldObject object: fieldObjects) {
			if( !(object instanceof Sapper) ) {
				newFieldObjects.add(object);
			} else {
				sapperField = object;
			}
		}
		
		fieldObjects.clear();
		fieldObjects.addAll(newFieldObjects);
		if(fieldObjects.isEmpty()) fieldObjects.add(new Empty());
		
		to.getObjects().add(sapperField);
		
		sapper.setField(to);
	}
	
	public static Map buildRandomMap() {
		Map map = new Map(18, 18);
		
		map.setField(0, 0, new Sapper());
		
		Random random = new Random();
		 
		for(int x = 0; x < map.getRows(); x++)
			for(int y = 1; y < map.getCols(); y++) {
						
				int decision = random.nextInt(10);
				
				switch(decision) {
				// Blokada 20%
				case 0: case 1: 
					map.setField(x, y, new Blockade());
				
					break;
					
				// Bomba 10%
				case 2:
					map.setField(x, y, new Bomb()); 
					
					break;
					
				// Cywile 30%	
				case 3: case 4: case 5:
					
					break;
				
				}
				
			}
		
		return map;
	}

    @Override
    public String toString() {
        return "Map{" +
                "rows=" + rows +
                ", cols=" + cols +
                ", fields=" + Arrays.deepToString(fields) +
                '}';
    }
}
