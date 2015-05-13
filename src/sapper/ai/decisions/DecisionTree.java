package sapper.ai.decisions;

import java.util.Collection;
import java.util.Queue;

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
		return null;
		
	}
	
	public DecisionNode getRoot() {
		return root;
	}

	public void setRoot(DecisionNode root) {
		this.root = root;
	}

	
}
