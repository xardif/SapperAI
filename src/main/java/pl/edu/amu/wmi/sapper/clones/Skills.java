package pl.edu.amu.wmi.sapper.clones;

import pl.edu.amu.wmi.sapper.ai.decisions.BombPriorityTree;
import pl.edu.amu.wmi.sapper.map.objects.types.BombSize;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class Skills {
    public String getskills(){
        BombPriorityTree tree = BombPriorityTree.buildBombPriorityTree();
        List<BombType> bombs = new ArrayList<>();
        bombs.add(new BombType(10, Type.Nuke, BombSize.BIG, 10, true));
        bombs.add(new BombType(150, Type.C4, BombSize.MEDIUM, 10, true));
        bombs.add(new BombType(10, Type.FakeBomb, BombSize.BIG, 10, true));
        bombs.add(new BombType(10, Type.Nuke, BombSize.VERY_BIG, 10, true));

        System.out.println("Bombs: " + bombs.toString());
        Queue<BombType> result = tree.sortBombsByPriority(bombs);
        List<BombType> list = new ArrayList<>(result);
        System.out.println("Sorted bombs: " + list.toString());

        for(int i=0; i<list.size();i++)
            System.out.println(list.get(i).getMaterial());

        skills="111110100000111110100";
        return skills;
    }
}
