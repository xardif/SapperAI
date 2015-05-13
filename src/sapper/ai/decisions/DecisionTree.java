package sapper.ai.decisions;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import sapper.ai.decisions.bomb.BombPriority;
import sapper.ai.decisions.bomb.nodes.DecisionNode;
import sapper.ai.decisions.bomb.nodes.I.ActiveNode;
import sapper.map.objects.types.BombType;

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
		
		for(BombType bomb: set)
			result.add(bomb);
		
		return result;
		
	}
	
	public DecisionNode getRoot() {
		return root;
	}

	public void setRoot(DecisionNode root) {
		this.root = root;
	}

	
}
