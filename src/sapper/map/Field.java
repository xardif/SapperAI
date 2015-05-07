package sapper.map;

import java.util.ArrayList;

import sapper.ai.SapperLogic;
import sapper.map.objects.Blockade;
import sapper.map.objects.FieldObject;

public class Field implements Comparable<Object> {
	private int x;
	private int y;
	private ArrayList<FieldObject> objects = new ArrayList<>(2);
	
	private int g;
	private int h;
	private Field goalField;
	private Field parentField;
	private int gCost;
	private int sapperPosition;
	
	public int getXPosition() {
		return x;
	}
	
	public int getYPosition() {
		return y;
	}

	public ArrayList<FieldObject> getObjects() {
		return objects;
	}
	
	public int getTotalCost() {
		return g+h;
	}
	
	public Field getParentField() {
		return this.parentField;
	}
	
	public void setParentField(Field value) {
		this.parentField = value;
	}
	
	public int getGCost() {
		return this.gCost;
	}
	
	public int getSapperPosition() {
		return sapperPosition;
	}
	
	public void setSapperPosition(int value) {
		sapperPosition = value;
	}
	
	public Field(Field parentField, Field goalField, int gCost, int x, int y, int sapperPosition) {
		this.parentField = parentField;
		this.goalField = goalField;
		this.gCost = gCost;
		this.x=x;
		this.y=y;
		this.sapperPosition = sapperPosition;
		InitField();
	}

	private void InitField() {
		this.g = (parentField!=null)? this.parentField.g + gCost:gCost;
		this.h = (goalField!=null)? (int) Euclidean_H():0;
	}

	private double Euclidean_H() {
		double xd = this.x - this.goalField.x ;
		double yd = this.y - this.goalField.y ;
		return Math.sqrt((xd*xd) + (yd*yd));
	}
	
	@Override
	public int compareTo(Object obj) {
		Field n = (Field) obj;
		int cFactor = this.getTotalCost() - n.getTotalCost() ;
		return cFactor;
	}

	public boolean isMatch(Field n) {
		if (n!=null)
			return (this.x==n.x && this.y==n.y);
		else
			return false;
	}

	public int[] getCostAndPosition(Field from, Field to)
	{
		int xDiff = from.getXPosition() - to.getXPosition();	//je¿eli minus to w dó³
		int yDiff = from.getYPosition() - to.getYPosition();	//je¿eli minus to w prawo
		int[] result = new int[2];	//result[0] - koszt obrotu, result[1] - pozycje nastepnika
		
		//powiedzmy, ¿e koszt obrotu =1
		//idzie w gore
		if(xDiff == 1 && yDiff == 0) {
			if(sapperPosition == 0)	{ //jest zwrocony w gore
				result[0] = 0;
				result[1] = 0;
			}
			else {	//obrot
				result[0] = 2;
				result[1] = 0;
			}
		}
		//idzie w gora prawo
		if(xDiff == 1 && yDiff == -1) {
			if(sapperPosition == 1) { //jest zwrocony w gora prawo
				result[0] = 0;
				result[1] = 1;
			}
			else {	//obrot
				result[0] = 1;
				result[1] = 1;
			}
		}
		//idzie w prawo
		if(xDiff == 0 && yDiff == -1) {
			if(sapperPosition == 2)	{ //jest zwrocony w prawo
				result[0] = 0;
				result[1] = 2;
			}
			else {	//obrot
				result[0] = 1;
				result[1] = 2;
			}
		}
		//idzie w dol prawo
		if(xDiff == -1 && yDiff == -1) {
			if(sapperPosition == 3) { //jest zwrocony w dol prawo
				result[0] = 0;
				result[1] = 3;
			}
			else {	//obrot
				result[0] = 1;
				result[1] = 3;
			}
		}
		//idzie w dol
		if(xDiff == -1 && yDiff == 0) {
			if(sapperPosition == 4)	{ //jest zwrocony w dol
				result[0] = 0;
				result[1] = 4;
			}
			else {	//obrot
				result[0] = 1;
				result[1] = 4;
			}
		}
		//idzie w dol lewo
		if(xDiff == -1 && yDiff == 1) {
			if(sapperPosition == 5)	{ //jest zwrocony w dol lewo
				result[0] = 0;
				result[1] = 5;
			}
			else {	//obrot
				result[0] = 1;
				result[1] = 5;
			}
		}		
		//idzie w lewo
		if(xDiff == 0 && yDiff == 1) {
			if(sapperPosition == 6)	{ //jest zwrocony w lewo
				result[0] = 0;
				result[1] = 6;
			}
			else {	//obrot
				result[0] = 1;
				result[1] = 6;
			}
		}

		//idzie w gora lewo
		if(xDiff == 1 && yDiff == 1) {
			if(sapperPosition == 7)	{ //jest zwrocony w gora lewo
				result[0] = 0;
				result[1] = 7;
			}
			else {	//obrot
				result[0] = 1;
				result[1] = 7;
			}
		}	
		return result;
	}
	
	
	public ArrayList<Field> GetSuccessors(SapperLogic sapper, Field goalField) {
		ArrayList<Field> successors = new ArrayList<Field> ();
		
		for (int xd=-1; xd<=1; xd++) {
			for (int yd=-1; yd<=1; yd++) {
				if(Map.getField(x+xd,y+yd) != null) {
					int[] turnCost = getCostAndPosition(Map.getField(x,y), Map.getField(x+xd,y+yd));
					Field f;
					if (Map.getField(x+xd,y+yd).getObjects().isEmpty())
					{
						f = new Field (this, goalField ,Map.getField(x+xd,y+yd).gCost+turnCost[0] ,x+xd,y+yd, turnCost[1]);
						if (!f.isMatch (this.parentField) && !f.isMatch (this))
	                        successors.add(f);
					}
					else if (!(Map.getField(x+xd,y+yd).getObjects().get(0) instanceof Blockade)) {
						f = new Field (this, goalField ,Map.getField(x+xd,y+yd).gCost+turnCost[0] ,x+xd,y+yd, turnCost[1]);
						if (!f.isMatch (this.parentField) && !f.isMatch (this))
	                        successors.add(f);
					}
				}
			}
		}
		return successors;
	}
}
