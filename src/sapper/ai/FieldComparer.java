package sapper.ai;

import java.util.Comparator;

import sapper.map.Field;

public class FieldComparer implements Comparator<Object> {

	public FieldComparer() { }
	
	@Override
	public int compare(Object x, Object y) {
		return ((Field)x).getTotalCost() - ((Field) y).getTotalCost();
	}
}
