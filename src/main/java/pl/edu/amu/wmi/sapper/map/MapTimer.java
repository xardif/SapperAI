package pl.edu.amu.wmi.sapper.map;

import java.util.Queue;

import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.ui.Controller;

public class MapTimer implements Runnable {

	private int time = 0;
	public int getTime() {
		return time;
	}

	private Map map;
	private Queue<BombType> bombs;
	private Controller con;
	
	public MapTimer(Map m, Queue<BombType> q, Controller c) {
		map = m;
		bombs = q;
		con = c;
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
			
<<<<<<< HEAD
			//map.checkBombs();
=======
			map.decrementDetonation(bombs);
			map.checkBombs(bombs, con);
>>>>>>> 8d8a1e7e90382e757c240a7a7be95b656ab06333
			
		}

	}

}
