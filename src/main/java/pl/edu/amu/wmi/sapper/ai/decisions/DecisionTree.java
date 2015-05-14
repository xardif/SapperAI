package pl.edu.amu.wmi.sapper.ai.decisions;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.DecisionNode;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.I.ActiveNode;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.BombPriority;

public class DecisionTree {

	private DecisionNode root;
	
	public DecisionTree() {
		root = null;
	}
	
	public static DecisionTree buildBombDecisionTree() {
		DecisionTree tree = new DecisionTree();
		tree.setRoot(new ActiveNode());
		return tree;
	}
	
	public BombPriority getBombPriority(BombType bomb) {
		return root.getBombPriority(bomb);
	}
	
	public Queue<BombType> sortBombsByPriority(Collection<BombType> collection) {
		
		Set<BombType> set = new TreeSet<>(new Comparator<BombType>() {
			@Override
			public int compare(BombType o1, BombType o2) {
				return getBombPriority(o1).compareTo(getBombPriority(o2));
			}
		});
		
		for(BombType bomb: collection)
			set.add(bomb);
		
		Queue<BombType> result = new ArrayDeque<>();
		Stack<BombType> reverserStack = new Stack<>();
		
		for(BombType bomb: set)
			reverserStack.push(bomb);
		
		while(!reverserStack.isEmpty())
			result.add(reverserStack.pop());
		
		return result;
		
	}
	
	public DecisionNode getRoot() {
		return root;
	}

	public void setRoot(DecisionNode root) {
		this.root = root;
	}

	
}
