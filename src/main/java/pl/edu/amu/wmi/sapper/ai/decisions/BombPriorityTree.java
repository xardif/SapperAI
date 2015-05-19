package pl.edu.amu.wmi.sapper.ai.decisions;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.BombPriority;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class BombPriorityTree {

	private Map<BombPriority, J48> priorityTrees;
	private static Map<BombPriority, String> priorityToAAFF;
	
	static {
		priorityToAAFF = new HashMap<BombPriority, String>();
		for(BombPriority priority: BombPriority.values()) {
			priorityToAAFF.put(priority, "./src/main/resources/arff/BombPriorityTrainingSetFor" + 
								priority.toString() + ".arff");		
		}
	}
	
	private BombPriorityTree() {
		priorityTrees = new HashMap<>();
	}
	
	/**
	 * Buduje drzewo decyzyjne na podstawie plików ARFF do szacowania priorytetu rozbrojenia danej bomby.
	 * 
	 * @return obiekt DecisionTree reprezentujący wytrenowane drzewo.
	 */
	public static BombPriorityTree buildBombPriorityTree() {
		BombPriorityTree result = new BombPriorityTree();
		
		for(BombPriority priority: BombPriority.values()) {
			Instances trainingSet = prepareTrainingSet(priority);
			J48 tree = learnTree(trainingSet);				
			result.addTree(priority, tree);
		}
		
		return result;
	}
	
	public BombPriority getBombPriority(BombType bomb) {
		double currentMax = 0;
		BombPriority currentResult = null;
		
		for(BombPriority priority: BombPriority.values()) {
			double current = getProbabilityForPriority(priority, bomb);
			if(current > currentMax) {
				currentMax = current;
				currentResult = priority;
			}
		}

		return currentResult;
	}
	
	private double getProbabilityForPriority(BombPriority priority, BombType bomb) {
		
		Instance instance = new Instance(4);
		instance.setValue(new Attribute("Type"), bomb.getMaterial().toString());
		instance.setValue(new Attribute("Size"), bomb.getSize().toString());
		instance.setValue(new Attribute("Active"), Boolean.toString(bomb.getIsActive()));
		instance.setValue(new Attribute("Victims"), bomb.getPotentialVictims());
		
		try {
			return priorityTrees.get(priority).classifyInstance(instance);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Queue<BombType> sortBombsByPriority(Collection<BombType> collection) {
		
		Set<BombType> set = new TreeSet<>(new Comparator<BombType>() {
			@Override
			public int compare(BombType o1, BombType o2) {
				return getBombPriority(o2).compareTo(getBombPriority(o1));
			}
		});
		
		for(BombType bomb: collection)
			set.add(bomb);
		
		Queue<BombType> result = new ArrayDeque<>();
		//Stack<BombType> reverserStack = new Stack<>();
		
		//for(BombType bomb: set)
		//	reverserStack.push(bomb);
		
		//while(!reverserStack.isEmpty())
		//	result.add(reverserStack.pop());
		
		for(BombType bomb: set)
			result.add(bomb);
		
		return result;
		
	}
	
	private void addTree(BombPriority priority, J48 tree) {
		priorityTrees.put(priority, tree);
	}

	private static Instances prepareTrainingSet(BombPriority priority) {
		try {
			DataSource dataSource = new DataSource(priorityToAAFF.get(priority));
			Instances instances = dataSource.getDataSet();
			instances.setClass(new Attribute(priority.toString()));
			return instances;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	private static J48 learnTree(Instances trainingSet) {
		try {
			J48 tree = new J48();
			tree.buildClassifier(trainingSet);
			return tree;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
