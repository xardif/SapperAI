package sapper.ai.decisions.bomb.nodes.II;

import java.util.List;

import sapper.ai.decisions.bomb.BombPriority;
import sapper.ai.decisions.bomb.nodes.DecisionNode;
import sapper.ai.decisions.bomb.nodes.III.VictimsABCBombNode;
import sapper.ai.decisions.bomb.nodes.III.VictimsConventionalBombNode;
import sapper.map.objects.types.BombType;

public class TypeNode implements DecisionNode {

	private DecisionNode abcNode = new VictimsABCBombNode();
	private DecisionNode conventionalNode = new VictimsConventionalBombNode();
	
	@Override
	public List<DecisionNode> getChildren() {
		// TODO Auto-generated method stub
		return null;
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
