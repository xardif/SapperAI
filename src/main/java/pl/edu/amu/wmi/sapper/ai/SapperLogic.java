package pl.edu.amu.wmi.sapper.ai;

import java.util.ArrayList;
import java.util.List;

import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;

public class SapperLogic {
	private Field field;
	private int turnPosition;	//0-N, 1-NE, 2-E, 3-SE, 4-S, 5-SW, 6-W, 7-NW
	
	public SapperLogic(Field field) {
		setField(field);
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
	
	public int movesCount(Field to) {
		int xDiff = this.field.getXPosition() - to.getXPosition();	//je�eli minus to w d�
		int yDiff = this.field.getYPosition() - to.getYPosition();	//je�eli minus to w prawo
		
		//idzie w gore
		if(xDiff == 1 && yDiff == 0) {
			if(turnPosition == 0)	//jest zwrocony w gore
				return  0;
			else {	//obrot
				this.turnPosition = 0;
				return 1;
			}
		}
		//idzie w gora prawo
		if(xDiff == 1 && yDiff == -1) {
			if(turnPosition == 1)	//jest zwrocony w gora prawo
				return  0;
			else {	//obrot
				this.turnPosition = 1;
				return 1;
			}
		}
		//idzie w prawo
		if(xDiff == 0 && yDiff == -1) {
			if(turnPosition == 2)	//jest zwrocony w prawo
				return  0;
			else {	//obrot
				this.turnPosition = 2;
				return 1;
			}
		}
		//idzie w dol prawo
		if(xDiff == -1 && yDiff == -1) {
			if(turnPosition == 3)	//jest zwrocony w dol prawo
				return  0;
			else {	//obrot
				this.turnPosition = 3;
				return 1;
			}
		}
		//idzie w dol
		if(xDiff == -1 && yDiff == 0) {
			if(turnPosition == 4)	//jest zwrocony w dol
				return  0;
			else {	//obrot
				this.turnPosition = 4;
				return 1;
			}
		}
		//idzie w dol lewo
		if(xDiff == -1 && yDiff == 1) {
			if(turnPosition == 5)	//jest zwrocony w dol lewo
				return  0;
			else {	//obrot
				this.turnPosition = 5;
				return 1;
			}
		}		
		//idzie w lewo
		if(xDiff == 0 && yDiff == 1) {
			if(turnPosition == 6)	//jest zwrocony w lewo
				return  0;
			else {	//obrot
				this.turnPosition = 6;
				return 1;
			}
		}

		//idzie w gora lewo
		if(xDiff == 1 && yDiff == 1) {
			if(turnPosition == 7)	//jest zwrocony w gora lewo
				return  0;
			else {	//obrot
				this.turnPosition = 7;
				return 1;
			}
		}	
		return 0;
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
        map.PrintSolution(SolutionPathList);
        System.out.println("Koszt przej�cia (w sekundach): " + pathCost);
        
        return SolutionPathList;
	}
}
