package pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.IV;

import java.util.ArrayList;
import java.util.List;

import pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.DecisionNode;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.BombPriority;

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
