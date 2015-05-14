package pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes;

import java.util.List;

import pl.edu.amu.wmi.sapper.ai.decisions.bomb.BombPriority;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;

public interface DecisionNode {

	List<DecisionNode> getChildren();
	BombPriority getBombPriority(BombType bomb);
	
}
