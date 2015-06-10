package pl.edu.amu.wmi.sapper;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.edu.amu.wmi.sapper.ai.SapperLogic;
import pl.edu.amu.wmi.sapper.ai.decisions.BombPriorityTree;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.BombPriority;
import pl.edu.amu.wmi.sapper.ai.neural.BombRecognize;
import pl.edu.amu.wmi.sapper.clones.Algorithm;
import pl.edu.amu.wmi.sapper.clones.DetonationTime;
import pl.edu.amu.wmi.sapper.clones.FitnessCalc;
import pl.edu.amu.wmi.sapper.clones.Population;
import pl.edu.amu.wmi.sapper.clones.Skills;
import pl.edu.amu.wmi.sapper.map.Map;
import pl.edu.amu.wmi.sapper.map.objects.Blockade;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.Civilians;
import pl.edu.amu.wmi.sapper.map.objects.Sapper;
import pl.edu.amu.wmi.sapper.map.objects.types.BombSize;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;
import pl.edu.amu.wmi.sapper.ui.ImageUtil;
import pl.edu.amu.wmi.sapper.util.Calculator;
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
		Skills skills = new Skills();
		
		String targetSkills = skills.getskills(sortedBombTypes);
		System.out.println("!!!!!! Pre Target Skills: " + targetSkills);
		FitnessCalc.setSolution(targetSkills);
		
		/* Create an initial population */
        Population myPop = new Population(50, true);
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        int limit = 3;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness() && generationCount < 3) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
        }
		
		String actualSkills = myPop.getFittest().toString();
		
		System.out.println("!!!!!! Target Skills: " + targetSkills);
		System.out.println("!!!!!! Actual Skills: " + actualSkills);
		
		SapperLogic logic = new SapperLogic(map.getField(0, 0));
				
		DetonationTime detonation = new DetonationTime();
		java.util.Map<Type, Integer> skillsMap = detonation.getTime(actualSkills);
		
		if(skillsMap.isEmpty()) System.out.println("EMPTY");
		
		for(Type type: skillsMap.keySet()) {
			System.out.println( type.toString() + " -> " + skillsMap.get(type).toString());
		}
		
		logic.setSkills(skillsMap);
		
		for(BombType bombType: sortedBombTypes) {
			BombPriority priority = tree.getBombPriority(bombType);
		}
		
	}

}
