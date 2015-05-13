package sapper.ai.decisions.bomb.nodes.IV;

import java.util.ArrayList;
import java.util.List;

import sapper.ai.decisions.bomb.BombPriority;
import sapper.ai.decisions.bomb.nodes.DecisionNode;
import sapper.map.objects.types.BombType;

public class VictimsVeryBigABCBombNode implements DecisionNode {

	@Override
	public List<DecisionNode> getChildren() {
		return new ArrayList<DecisionNode>();
	}

	@Override
	public BombPriority getBombPriority(BombType bomb) {
		return BombPriority.CRITICAL;
	}

}
