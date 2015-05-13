package sapper.ai.decisions.bomb.nodes.III;

import java.util.ArrayList;
import java.util.List;

import sapper.ai.decisions.bomb.BombPriority;
import sapper.ai.decisions.bomb.nodes.DecisionNode;
import sapper.map.objects.types.BombType;

public class VictimsConventionalBombNode implements DecisionNode {

	@Override
	public List<DecisionNode> getChildren() {
		return new ArrayList<DecisionNode>();
	}

	@Override
	public BombPriority getBombPriority(BombType bomb) {
		int victims = bomb.getPotentialVictims(); 
		
		if(victims == 0) return BombPriority.VERY_LOW;
		else if(victims > 0 && victims <= 200) return BombPriority.LOW;
		else if(victims > 200 && victims <= 400) return BombPriority.MEDIUM;
		else if(victims > 400 && victims <= 600) return BombPriority.MEDIUM;
		else if(victims > 600 && victims <= 800) return BombPriority.HIGH;
		else if(victims > 800 && victims <= 1000) return BombPriority.HIGH;
		else return BombPriority.VERY_HIGH;
	}

}
