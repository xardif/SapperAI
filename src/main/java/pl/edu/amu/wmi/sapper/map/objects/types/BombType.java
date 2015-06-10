package pl.edu.amu.wmi.sapper.map.objects.types;

import java.awt.Image;

import javax.swing.ImageIcon;

import pl.edu.amu.wmi.sapper.ai.neural.BombRecognize;
import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.util.Calculator;

public class BombType {
	
	private int radius;
	private Type type;
	private BombSize size;
	private int timeToDetonation;
	private boolean isActive;
	private boolean isDetonated;
	private Image image;
	private Field field;
	
	@Deprecated
	public BombType(int radius, Type type, BombSize size, int timeToDetonation, boolean isActive) {
		this.radius = radius;
		this.type = type;
		this.size = size;
		this.timeToDetonation = timeToDetonation;
		this.isActive = isActive;
		this.isDetonated = false;
	}
	
	public BombType(Type type, BombSize size, int timeToDetonation, boolean isActive, String imagePath) {
		this.radius = radius;
		this.type = type;
		this.size = size;
		this.timeToDetonation = timeToDetonation;
		this.isActive = isActive;
		this.isDetonated = false;
		this.image = new ImageIcon(BombRecognize.class.getResource(imagePath)).getImage();
	}
	
	public BombType(Bomb bomb) {
		this.size = BombSize.valueOf(bomb.getSize());
		this.timeToDetonation = bomb.getTimeToDetonation();
		this.isActive = bomb.isActive();
		this.isDetonated = false;
		this.image = new ImageIcon(BombRecognize.class.getResource(bomb.getPathToTypeImage())).getImage();
	}
	
	 public int getRadius() {
		 return radius;
	 }
	 
	 public Type getMaterial() {
		 return type;
	 }
	 
	 public BombSize getSize() {
		 return size; 
	 }
	 
	 public int getTimeToDetonation() {
		 return timeToDetonation;
	 }
	 
	 public boolean getIsActive() {
		 return isActive;
	 }
	 public void setIsActive() {
		 this.isActive = false;
	 }
	 
	 public boolean getIsDetonated() {
		 return isDetonated;
	 }
	 
	 @Override
	 public String toString() {
		return "BombType [radius=" + radius + ", type=" + type + ", size="
				+ size + ", timeToDetonation=" + timeToDetonation
				+ ", isActive=" + isActive + ", isDetonated=" + isDetonated
				+ "]";
	}

	public void setIsdetonated() {
		 this.isDetonated = true;
	 }

	public int getPotentialVictims() {
		return Calculator.calculateVictims(this);
	}
	

	public Image getImage() {
		return image;
	}
	
	public static BombType buildRandom() {
		
		return null;
		
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
	private int calculateRadius() {
		int x = 0;
		int c = 0;
		
		switch(type) {
		case C4:
			x = 3;
			c = 2;
			break;
		case ChemicalBomb:
			x = 3;
			c = 3;
			break;
		case DirtyBomb:
			x = 4;
			c = 3;
			break;
		case Dynamite:
			x = 3;
			c = 1;
			break;
		case FakeBomb:
			x = 0;
			c = 0;
			break;
		case HomeMadeBomb:
			x = 1;
			c = 1;
			break;
		case Nuke:
			x = 5;
			c = 3;
			break;
		}

		return (int) ((x/5) * size.getValue() + c );
		
	}
	
	
}
