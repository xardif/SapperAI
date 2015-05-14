package pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.III;

import java.util.ArrayList;
import java.util.List;

import pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.DecisionNode;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.IV.VictimsBigABCBombNode;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.IV.VictimsMediumABCBombNode;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.IV.VictimsSmallABCBombNode;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.IV.VictimsTinyABCBombNode;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.BombPriority;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.nodes.IV.VictimsVeryBigABCBombNode;

public class SizeABCBombNode implements DecisionNode {

	private DecisionNode tiny = new VictimsTinyABCBombNode();
	private DecisionNode small = new VictimsSmallABCBombNode();
	private DecisionNode medium = new VictimsMediumABCBombNode();
	private DecisionNode big = new VictimsBigABCBombNode();
	private DecisionNode veryBig = new VictimsVeryBigABCBombNode();
	
	@Override
	public List<DecisionNode> getChildren() {
		List<DecisionNode> list = new ArrayList<>();
		list.add(tiny);
		list.add(small);
		list.add(medium);
		list.add(big);
		list.add(veryBig);
		return list;
	}

	@Override
	public BombPriority getBombPriority(BombType bomb) {
		
		switch(bomb.getSize()) {
		case TINY:
			return tiny.getBombPriority(bomb);
		case SMALL:
			return small.getBombPriority(bomb);
		case MEDIUM:
			return medium.getBombPriority(bomb);
		case BIG:
			return big.getBombPriority(bomb);
		case VERY_BIG:
			return veryBig.getBombPriority(bomb);
		default:
			return null;		
		}
		
	}

}
