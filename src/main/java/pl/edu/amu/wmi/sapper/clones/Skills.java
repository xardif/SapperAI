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
        double DirtyBombPriority=0.0;
        double FakeBombPriority=0.0;
        double DynamitePriority=0.0;
        double HomeMadeBombPriority=0.0;
        double ChemicalBombPriority=0.0;


        for(int x=0;x<types.size();x++)
            switch (types.get(x)) {
                case Nuke:
                    countNuke++;
                    NukePriority = (NukePriority + (priorityLevel * (types.size() - x)))/countNuke;
                    break;
                case C4:
                    countC4++;
                    C4Priority = (C4Priority + (priorityLevel * (types.size() - x)))/countC4;
                    break;
                case DirtyBomb:
                    countDirtyBomb++;
                    DirtyBombPriority = (DirtyBombPriority + (priorityLevel * (types.size() - x)))/countDirtyBomb;
                    break;
                case Dynamite:
                    countDynamite++;
                    DynamitePriority = (DynamitePriority + (priorityLevel * (types.size() - x)))/countDynamite;
                    break;
                case FakeBomb:
                    countFakeBomb++;
                    FakeBombPriority = (FakeBombPriority + (priorityLevel * (types.size() - x)))/countFakeBomb;
                    break;
                case ChemicalBomb:
                    countChemicalBomb++;
                    ChemicalBombPriority = (ChemicalBombPriority + (priorityLevel * (types.size() - x)))/countChemicalBomb;
                    break;
                case HomeMadeBomb:
                    countHomeMadeBomb++;
                    HomeMadeBombPriority = (HomeMadeBombPriority + (priorityLevel * (types.size() - x)))/countHomeMadeBomb;
                    break;
            }





        System.out.println(countNuke);
        System.out.println("Fake priority: "+FakeBombPriority);
        System.out.println("Nuke priority: "+NukePriority);
        System.out.println("C4 priority: "+C4Priority);

        String skillsForNukeBomb = "";
        String skillsForC4Bomb = "";
        String skillsForFakeBomb = "";
        String skillsForChemicalBomb = "";
        String skillsForDynamiteBomb = "";
        String skillsForHomeMadeBomb = "";
        String skillsForDirtyBomb = "";

        if(NukePriority>=0.66)
            skillsForNukeBomb="111";
        else if (NukePriority>=0.33 && NukePriority<0.66)
            skillsForNukeBomb="110";
        else if(NukePriority<0.33 && NukePriority>0)
            skillsForNukeBomb="100";
        else
            skillsForNukeBomb="000";

        if(C4Priority>=0.66)
            skillsForC4Bomb="111";
        else if (C4Priority>=0.33 && C4Priority<0.66)
            skillsForC4Bomb="110";
        else if(C4Priority<0.33 && C4Priority>0)
            skillsForC4Bomb="100";
        else
            skillsForC4Bomb="000";

        if(DirtyBombPriority>=0.66)
            skillsForDirtyBomb="111";
        else if (DirtyBombPriority>=0.33 && DirtyBombPriority<0.66)
            skillsForDirtyBomb="110";
        else if(DirtyBombPriority<0.33 && DirtyBombPriority>0)
            skillsForDirtyBomb="100";
        else
            skillsForDirtyBomb="000";

        if(ChemicalBombPriority>=0.66)
            skillsForChemicalBomb="111";
        else if (ChemicalBombPriority>=0.33 && ChemicalBombPriority<0.66)
            skillsForChemicalBomb="110";
        else if(ChemicalBombPriority<0.33 && ChemicalBombPriority>0)
            skillsForChemicalBomb="100";
        else
            skillsForChemicalBomb="000";


        if(DynamitePriority>=0.66)
            skillsForDynamiteBomb="111";
        else if (DynamitePriority>=0.33 && DynamitePriority<0.66)
            skillsForDynamiteBomb="110";
        else if(DynamitePriority<0.33 && DynamitePriority>0)
            skillsForDynamiteBomb="100";
        else
            skillsForDynamiteBomb="000";

        if(HomeMadeBombPriority>=0.66)
            skillsForHomeMadeBomb="111";
        else if (HomeMadeBombPriority>=0.33 && HomeMadeBombPriority<0.66)
            skillsForHomeMadeBomb="110";
        else if(HomeMadeBombPriority<0.33 && HomeMadeBombPriority>0)
            skillsForHomeMadeBomb="100";
        else
            skillsForHomeMadeBomb="000";


        if(FakeBombPriority>=0.66)
            skillsForFakeBomb="111";
        else if (FakeBombPriority>=0.33 && FakeBombPriority<0.66)
            skillsForFakeBomb="110";
        else if(FakeBombPriority<0.33 && FakeBombPriority>0)
            skillsForFakeBomb="100";
        else
            skillsForFakeBomb="000";


        String skills=skillsForNukeBomb+skillsForC4Bomb+skillsForDirtyBomb+skillsForChemicalBomb+skillsForDynamiteBomb+skillsForHomeMadeBomb+skillsForFakeBomb;
        return skills;
    }
}
