package pl.edu.amu.wmi.sapper.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;

public class SapperLogic {
	private Field field;
	private int turnPosition;	//0-N, 1-NE, 2-E, 3-SE, 4-S, 5-SW, 6-W, 7-NW
	private java.util.Map<Type, Integer> skills;
	
	public SapperLogic(Field field) {
		setField(field);
		skills = new HashMap<>();
		this.turnPosition = 2;	//zak�adamy, �e po starcie programu saper jest zwr�cony do g�ry
	}
	
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	
	public void turnRight() {
		this.turnPosition = (this.turnPosition + 1)%8;
	}	
	
	public void turnLeft() {
		this.turnPosition = (this.turnPosition - 1)%8;
	}
	
	public java.util.Map<Type, Integer> getSkills() {
		return skills;
	}

	public void setSkills(java.util.Map<Type, Integer> skills) {
		this.skills = skills;
	}
	
	public void defuseBomb(BombType bomb) {
		bomb.setIsActive();
	}
	
	public List<Field> findPath(Field fieldStart, Field fieldGoal, Map map)
	{
		List<Field> SolutionPathList = new ArrayList<Field>();

        //tworzenie list OPEN i CLOSED
        SortedCostFieldList OPEN = new SortedCostFieldList();
        SortedCostFieldList CLOSED = new SortedCostFieldList();

        //dodanie punktu startowego do listy OPEN
        OPEN.push(fieldStart);

        //dopoki lista OPEN nie jest pusta
        while (OPEN.Count() > 0) {
            //wybieramy pierwsze pole na liscie OPEN (o najnizszym koszcie)
        	Field fieldCurrent = OPEN.pop();

            //jezeli obecne pole = pole koncowe to znaleziono rozwiazanie (wyjscie z petli while)
            if (fieldCurrent.isMatch(fieldGoal)) {
                fieldGoal.setParentField(fieldCurrent.getParentField());
                break;
            }

            //pobranie nastepnikow pola w ktorym obecnie sie znajdujemy
            ArrayList<Field> successors = fieldCurrent.GetSuccessors(this, fieldGoal, map);

            //dla kazdego nastepnika
            for(Field fieldSuccessor : successors) {
            	//ustawienie kosztu nastepnika jako koszt obecnego pola plus koszt przejscia z tego pola do nastepnika
                //--> zrobione w fnkcji GetSuccessors

                //znajdowanie indeksu nastepnika na liscie OPEN
                int oFound = OPEN.IndexOf(fieldSuccessor);

                //jezeli nastepnik jest na liscie OPEN ale obecne rowiazanie jest tak samo dobre albo lepsze to usuwamy ten nastepnik
                if (oFound > 0) {
                    Field existingField = OPEN.FieldAt(oFound);
                    if (existingField.compareTo(fieldCurrent) <= 0)
                        continue;
                }


                //znajdowanie indeksu nastepnika na liscie CLOSED
                int cFound = CLOSED.IndexOf(fieldSuccessor);

                //jezeli nastepnik jest na liscie CLOSED ale obecne rowiazanie jest tak samo dobre albo lepsze to usuwamy ten nastepnik
                if (cFound > 0) {
                	Field existingField = CLOSED.FieldAt(cFound);
                    if (existingField.compareTo(fieldCurrent) <= 0)
                        continue;
                }

                //usuwanie nastepnika z listy CLOSED i OPEN
                if (oFound != -1)
                    OPEN.RemoveAt(oFound);
                if (cFound != -1)
                    CLOSED.RemoveAt(cFound);

                //ustawnienie rodzica nastepnika na obecne pole
                //--> zrobione w fnkcji GetSuccessors

                //ustawnienie h jako przyblizony dystans do celu (za pomoca heurystyki)
                //--> zrobione w fnkcji GetSuccessors

                //dodanie nastepnika do listy OPEN
                OPEN.push(fieldSuccessor);

            }
            //dodanie obecnego pola do list CLOSED
            CLOSED.push(fieldCurrent);
            //this.field = fieldCurrent;
        }


        //przejscie od mety do startu po rodzicach
        Field p = fieldGoal;
        while (p != null) {
            SolutionPathList.add(0, p);
            p = p.getParentField();
        }
        
        int pathCost = 0;
        for(Field f : SolutionPathList) {
        	pathCost += f.getGCost();
        }
        
        
        //wypisanie rozwiazania
        for(Field f: SolutionPathList) {
        	map.PrintSolution(SolutionPathList);
	        //System.out.println("PathCost " + pathCost);
	        System.out.println();
	        System.out.println();
	        map.moveSapper(this, f);
        }
        
        return SolutionPathList;
	}
}
