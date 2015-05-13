package sapper.ai.decisions.bomb.nodes.II;

import java.util.ArrayList;
import java.util.List;

import sapper.ai.decisions.bomb.BombPriority;
import sapper.ai.decisions.bomb.nodes.DecisionNode;
import sapper.ai.decisions.bomb.nodes.III.SizeABCBombNode;
import sapper.ai.decisions.bomb.nodes.III.VictimsConventionalBombNode;
import sapper.map.objects.types.BombType;

public class TypeNode implements DecisionNode {

	private DecisionNode abcNode = new SizeABCBombNode();
	private DecisionNode conventionalNode = new VictimsConventionalBombNode();
	
	@Override
	public List<DecisionNode> getChildren() {
		List<DecisionNode> children = new ArrayList<>();
		children.add(abcNode);
		children.add(conventionalNode);
		return children;
	}

	@Override
	public BombPriority getBombPriority(BombType bomb) {
		
		switch(bomb.getMaterial()) {
		case Nuke:
			return abcNode.getBombPriority(bomb);
		case C4:
			return conventionalNode.getBombPriority(bomb);
		case ChemicalBomb:
			return abcNode.getBombPriority(bomb);
		case DirtyBomb:
			return abcNode.getBombPriority(bomb);
		case Dynamite:
			return conventionalNode.getBombPriority(bomb);
		case FakeBomb:
			return BombPriority.ZERO;
		case HomeMadeBomb:
			return conventionalNode.getBombPriority(bomb);
		default:
			return null;
		}
		
	}

}
