

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

import pl.edu.amu.wmi.sapper.ai.decisions.BombPriorityTree;
import pl.edu.amu.wmi.sapper.ai.decisions.bomb.BombPriority;
import pl.edu.amu.wmi.sapper.map.objects.Bomb;
import pl.edu.amu.wmi.sapper.map.objects.types.BombSize;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;

public class DecisionBombTest {

	@Test
	public void testGetBombPriority() {
		
		BombPriorityTree tree = BombPriorityTree.buildBombPriorityTree();
		
		BombType bigNuke = new BombType(10, Type.Nuke, BombSize.BIG, 10, true);		
		Assert.assertEquals(BombPriority.VERY_HIGH, tree.getBombPriority(bigNuke));
		
		BombType C4_450Victims = new BombType(150, Type.C4, BombSize.MEDIUM, 10, true);
		Assert.assertEquals(BombPriority.MEDIUM, tree.getBombPriority(C4_450Victims));
		
		BombType fake = new BombType(10, Type.FakeBomb, BombSize.BIG, 10, true);
		Assert.assertEquals(BombPriority.ZERO, tree.getBombPriority(fake));
		
		BombType verybigNuke = new BombType(10, Type.Nuke, BombSize.VERY_BIG, 10, true);
		Assert.assertEquals(BombPriority.CRITICAL, tree.getBombPriority(verybigNuke));
		
		BombType verybigNukeInactive = new BombType(10, Type.Nuke, BombSize.VERY_BIG, 10, false);
		Assert.assertEquals(BombPriority.ZERO, tree.getBombPriority(verybigNukeInactive));
		
	}
	
	@Test
	public void testSortBombsByPriority() {
		
		BombPriorityTree tree = BombPriorityTree.buildBombPriorityTree();
		
		List<BombType> bombs = new ArrayList<>();
		bombs.add(new BombType(10, Type.Nuke, BombSize.BIG, 10, true));
		bombs.add(new BombType(150, Type.C4, BombSize.MEDIUM, 10, true));
		bombs.add(new BombType(10, Type.FakeBomb, BombSize.BIG, 10, true));
		bombs.add(new BombType(10, Type.Nuke, BombSize.VERY_BIG, 10, true));
		
		System.out.println("Bombs: " + bombs.toString());
		
		Queue<BombType> result = tree.sortBombsTypesByPriority(bombs);
		System.out.println("Sorted bombs: " + result.toString());
		
	}

}
