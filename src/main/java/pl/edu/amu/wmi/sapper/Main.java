package pl.edu.amu.wmi.sapper;

import java.awt.Image;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.edu.amu.wmi.sapper.ai.SapperLogic;
import pl.edu.amu.wmi.sapper.ai.neural.BombRecognize;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.objects.Blockade;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.Civilians;
import pl.edu.amu.wmi.sapper.map.objects.Sapper;
import pl.edu.amu.wmi.sapper.map.objects.types.BombSize;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;
import pl.edu.amu.wmi.sapper.ui.ImageUtil;
import pl.edu.amu.wmi.sapper.util.JsonParser;

public class Main {

	
	/**
	 * 		SapperLogic s = new SapperLogic(map.getField(0, 0));
		map.setField(0, 0, new Sapper());
		s.findPath(s.getField(), map.getField(15, 15), map);
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JSON file", "json");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    String path = chooser.getSelectedFile().getAbsolutePath();
		*/
		Map map = null;
	    try {
			map = JsonParser.parse("/map/main.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    List<Bomb> bombs = map.getAllBombs();
	    BombRecognize recognizer = new BombRecognize();
	    
		for(Bomb bomb: bombs) {
			Image img = new ImageIcon(BombRecognize.class.getResource(bomb.getPathToTypeImage())).getImage();
			BombType bombType = new BombType(bomb);
			bombType.setType(Type.valueOf(recognizer.recognize(img)));
		}
		
		
		
	}

}
