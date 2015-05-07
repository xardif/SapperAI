package sapper.ai;

import java.util.ArrayList;
import java.util.Collections;

import sapper.map.Field;

public class SortedCostFieldList {

	ArrayList<Field> list;
	FieldComparer fieldComparer;

	public int Count() {
		return list.size();
	}

	public SortedCostFieldList() {
		list = new ArrayList<Field>();
		fieldComparer = new FieldComparer();
	}

	
	public Field FieldAt (int i) {
		return (Field)list.get(i);
	}

	public void RemoveAt (int i) {
		list.remove(i);
	}

	public int IndexOf(Field f) {
		for (int i =0; i< list.size(); i++) {
			Field fieldInTheList = (Field)list.get(i);
			if (fieldInTheList.isMatch (f))
				return i;
		}
		return -1;
	}

	public int push (Field f) {
		int k = Collections.binarySearch(list, f, fieldComparer);
		//int k = list.indexOf(n);
		if (k==-1) // no element
			list.add(0, f);
		else if (k<0) {// find location by complement
			k=~k;
			list.add(k, f);
		}
		else if (k>=0)
		 	list.add(k, f);
					
		return k;
	}

	public Field pop() {
		Field r = (Field) list.get(0);
		list.remove(0);
		return r;
	}
}
