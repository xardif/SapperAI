package pl.edu.amu.wmi.sapper.clones;

import pl.edu.amu.wmi.sapper.ai.decisions.BombPriorityTree;
import pl.edu.amu.wmi.sapper.map.objects.types.BombSize;
import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;

import java.util.*;


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
        List<Type> types = new ArrayList<>();
        Map<String, Integer> numberOfBombs  = new HashMap<>();


        for(int i=0; i<list.size();i++){
            types.add(list.get(i).getMaterial());
            System.out.println(list.get(i).getMaterial());
        }

        double priorityLevel =  1.0/(list.size());
        int countC4=0;
        int countChemicalBomb=0;
        int countDirtyBomb=0;
        int countDynamite=0;
        int countFakeBomb=0;
        int countNuke=0;
        int countHomeMadeBomb=0;
        double NukePriority=0.0;
        double C4Priority=0.0;
        double DirtyBobmPriority=0.0;
        double FakeBombPriority=0.0;
        double DynamitePriority=0.0;
        double HomeMadeBombPriority=0.0;


        for(int x=0;x<types.size();x++){
            if(types.get(x)==Type.Nuke)
                countNuke++;
            else if(types.get(x)==Type.C4)
                countC4++;
            else if(types.get(x)==Type.ChemicalBomb)
                countChemicalBomb++;
            else if(types.get(x)==Type.DirtyBomb)
                countDirtyBomb++;
            else if(types.get(x)==Type.Dynamite)
                countDynamite++;
            else if(types.get(x)==Type.FakeBomb)
                countFakeBomb++;
            else if(types.get(x)==Type.HomeMadeBomb)
                countHomeMadeBomb++;
        }



        String skills="111110100000111110100";
        return skills;
    }
}
