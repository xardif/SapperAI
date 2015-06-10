package pl.edu.amu.wmi.sapper.map.objects;

public class Bomb extends FieldObject {

    private int size;
    private int timeToDetonation;
    private boolean isActive;
    private String pathToTypeImage;

	public Bomb() {
    }

    public Bomb(int size, int timeToDetonation, boolean isActive, String path) {
        this.size = size;
        this.timeToDetonation = timeToDetonation;
        this.isActive = isActive;
        this.pathToTypeImage = path;
    }

    public int getSize() {
        return size;
    }

    public int getTimeToDetonation() {
        return timeToDetonation;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTimeToDetonation(int timeToDetonation) {
        this.timeToDetonation = timeToDetonation;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Bomb";
    }
    

    public String getPathToTypeImage() {
		return pathToTypeImage;
	}

	public void setPathToTypeImage(String pathToTypeImage) {
		this.pathToTypeImage = pathToTypeImage;
	}

}
