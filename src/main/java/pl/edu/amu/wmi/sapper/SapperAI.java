package pl.edu.amu.wmi.sapper;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.ImageIcon;

import pl.edu.amu.wmi.sapper.ai.SapperLogic;
import pl.edu.amu.wmi.sapper.ai.decisions.BombPriorityTree;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.BombPriority;
import pl.edu.amu.wmi.sapper.ai.neural.BombRecognize;
import pl.edu.amu.wmi.sapper.clones.*;
import pl.edu.amu.wmi.sapper.map.Field;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.MapTimer;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;
import pl.edu.amu.wmi.sapper.ui.Controller;
import pl.edu.amu.wmi.sapper.util.Calculator;
import pl.edu.amu.wmi.sapper.util.JsonParser;

public class SapperAI implements Runnable {

	private final Controller controller;
    private final Map map;

	public SapperAI(Controller controller) {
		this.controller = controller;
        Map map;
        try {
            map = JsonParser.parse("/map/main.json");
        } catch (IOException e) {
            e.printStackTrace();
            map = null;
        }
        this.map = map;
        controller.setMap(map);
	}

    /**
	 * 		SapperLogic s = new SapperLogic(map.getField(0, 0));
	 map.setField(0, 0, new Sapper());
	 s.findPath(s.getField(), map.getField(15, 15), map);
	 */
	@Override
	public void run() {
		/*
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JSON file", "json");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    String path = chooser.getSelectedFile().getAbsolutePath();
		*/

		List<Bomb> bombs = map.getAllBombs();
		System.out.println("All bombs: ");
		for(Bomb bomb: bombs)
			System.out.print(bomb.toString() + " ");
		System.out.println();

		BombRecognize recognizer = new BombRecognize();
		List<BombType> bombTypes = new ArrayList<>();
		Calculator.setMap(map);
		for(Bomb bomb: bombs) {
			Image img = new ImageIcon(BombRecognize.class.getResource(bomb.getPathToTypeImage())).getImage();
			BombType bombType = new BombType(bomb);
			bombType.setType(Type.valueOf(recognizer.recognize(img)));
			bombType.setRadius(bombType.calculateRadius());
			bombType.setField(map.findObject(bomb));
			bombTypes.add(bombType);
		}

		BombPriorityTree tree = BombPriorityTree.buildBombPriorityTree();
		Queue<BombType> sortedBombTypes = tree.sortBombsTypesByPriority(bombTypes);

		GARun cos = new GARun();
		String actualSkills = cos.runGA(sortedBombTypes);

		SapperLogic logic = new SapperLogic(map.getField(0, 0));

		DetonationTime detonation = new DetonationTime();
		java.util.Map<Type, Integer> skillsMap = detonation.getTime(actualSkills);

		if(skillsMap.isEmpty()) System.out.println("EMPTY");

		for(Type type: skillsMap.keySet()) {
			System.out.println( type.toString() + " -> " + skillsMap.get(type).toString());
		}

		logic.setSkills(skillsMap);

		Field start = map.getField(0, 0);

		Runnable timer = new MapTimer(map, sortedBombTypes);
		new Thread(timer);
		
		
		List<Field> finalPath = new ArrayList<>();
		
		for(BombType bombType: sortedBombTypes) {
			List<Field> temporary = logic.findPath(bombType.getField(), map); 
			finalPath.addAll( temporary.subList(finalPath.size() == 0 ? 0 : finalPath.size(), temporary.size()) );
			logic.setField(finalPath.get(finalPath.size() - 1));
		}
		
		/*
		logic.findPath(map.getField(3, 0), map);
		for(Field f: path)
			System.out.print(f.getXPosition() + " " + f.getYPosition() + "; ");
		System.out.println();

		path = logic.findPath(map.getField(3, 3), map);
		for(Field f: path)
			System.out.print(f.getXPosition() + " " + f.getYPosition() + "; ");
		System.out.println();
			*/	
		
		controller.goPath(finalPath);
			/*
			logic.setField(path.get(path.size() - 1));
			start = path.get(path.size() - 1);
*/
		
	}
}
