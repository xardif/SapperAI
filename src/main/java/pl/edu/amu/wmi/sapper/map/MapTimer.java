package pl.edu.amu.wmi.sapper.map;

public class MapTimer implements Runnable {

	private int time = 0;
	public int getTime() {
		return time;
	}

	private Map map;
	
	public MapTimer(Map m) {
		map = m;
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time++;
			
			//map.checkBombs();
			
		}

	}

}
