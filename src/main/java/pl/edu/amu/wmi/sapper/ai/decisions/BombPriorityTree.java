package pl.edu.amu.wmi.sapper.ai.decisions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.types.BombSize;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.BombPriority;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class BombPriorityTree {

	private Map<BombPriority, J48> priorityTrees;
	private static Map<BombPriority, String> priorityToAAFF;
	private static List<Attribute> attributes;
	
	private FastVector types;
	private FastVector sizes;
	
	static {
		priorityToAAFF = new HashMap<BombPriority, String>();
		for(BombPriority priority: BombPriority.values()) {
			priorityToAAFF.put(priority, "./src/main/resources/arff/BombPriorityTrainingSetFor" + 
								priority.toString() + ".arff");		
		}
	}
	
	private BombPriorityTree() {
		priorityTrees = new HashMap<>();
		
		types = new FastVector(Type.values().length);
		for(Type t: Type.values())
			types.addElement(t.toString());
		
		sizes = new FastVector(BombSize.values().length);
		for(BombSize s: BombSize.values())
			sizes.addElement(s.toString());
		
		attributes = new ArrayList<>();
        
	}
	
	/**
	 * Buduje drzewo decyzyjne na podstawie plik��w ARFF do szacowania priorytetu rozbrojenia danej bomby.
	 * 
	 * @return obiekt DecisionTree reprezentuj��cy wytrenowane drzewo.
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
		BombPriority currentResult = BombPriority.CRITICAL;
		
		for(BombPriority priority: BombPriority.values()) {
			double current = getProbabilityForPriority(priority, bomb);
			if(current == 0.0) {
				currentResult = priority;
			}
		}
		
		return currentResult;
	}
	
	public Queue<BombType> sortBombsTypesByPriority(Collection<BombType> collection) {
		List<BombType> list = new ArrayList<>();
		for(BombType bomb: collection)
			list.add(bomb);
		
		Collections.sort(list, new Comparator<BombType>() {
			@Override
			public int compare(BombType o1, BombType o2) {
				return getBombPriority(o2).compareTo(getBombPriority(o1));
			}
		});
		
		Queue<BombType> result = new ArrayDeque<>();
		
		for(BombType bomb: list)
			result.add(bomb);
		
		return result;		
	}
	
	private void addTree(BombPriority priority, J48 tree) {
		priorityTrees.put(priority, tree);
	}
	
	private double getProbabilityForPriority(BombPriority priority, BombType bomb) {
		
		FastVector features = new FastVector();
		for(Attribute attr: attributes)
			features.addElement(attr);
	    FastVector classVal = new FastVector();
        classVal.addElement("true");
        classVal.addElement("false");
		
        features.addElement(new Attribute(priority.toString(),classVal));
         
        Instances data = new Instances("TestInstances",features,0);
		
		Instance instance = new Instance(data.numAttributes());
		data.add(instance);
		data.setClassIndex(data.numAttributes() - 1); 
		
		instance.setValue((Attribute) features.elementAt(0), bomb.getMaterial().toString());
		instance.setValue((Attribute) features.elementAt(1), bomb.getSize().toString());
		instance.setValue((Attribute) features.elementAt(2), Boolean.toString(bomb.getIsActive()));
		instance.setValue((Attribute) features.elementAt(3), bomb.getPotentialVictims());
		instance.setDataset(data);
		
		try {
			return priorityTrees.get(priority).classifyInstance(instance);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	private static Instances prepareTrainingSet(BombPriority priority) {
		try {
			DataSource dataSource = new DataSource(priorityToAAFF.get(priority));
			Instances instances = dataSource.getDataSet();
			instances.setClassIndex(instances.numAttributes() - 1);
			Enumeration<Attribute> e = (Enumeration<Attribute>) instances.<Attribute>enumerateAttributes();
			if(attributes.isEmpty())
			while(e.hasMoreElements())
				attributes.add(e.nextElement());
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
			System.out.println(trainingSet.toString());
			System.out.println(trainingSet.toSummaryString());
			e.printStackTrace();
			return null;
		}
		
	}
	
}
