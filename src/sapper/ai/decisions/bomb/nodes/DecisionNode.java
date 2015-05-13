package sapper.ai.decisions.bomb.nodes;

import java.util.List;

import sapper.ai.decisions.bomb.BombPriority;
import sapper.map.objects.types.BombType;

public interface DecisionNode {

	List<DecisionNode> getChildren();
	BombPriority getBombPriority(BombType bomb);
	
}
