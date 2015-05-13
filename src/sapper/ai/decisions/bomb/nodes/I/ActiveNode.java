package sapper.ai.decisions.bomb.nodes.I;

import java.util.ArrayList;
import java.util.List;

import sapper.ai.decisions.bomb.BombPriority;
import sapper.ai.decisions.bomb.nodes.DecisionNode;
import sapper.ai.decisions.bomb.nodes.II.TypeNode;
import sapper.map.objects.types.BombType;

public class ActiveNode implements DecisionNode {
	
	private DecisionNode typeNode = new TypeNode();
	
	@Override
	public BombPriority getBombPriority(BombType bomb) {
		if(bomb.getIsActive())
			return typeNode.getBombPriority(bomb);
		else
			return BombPriority.ZERO;
	}

	@Override
	public List<DecisionNode> getChildren() {
		List<DecisionNode> children = new ArrayList<>();
		children.add(typeNode);
		return children;
	}

}
