package pl.edu.amu.wmi.sapper.clones;

import pl.edu.amu.wmi.sapper.map.objects.types.BombType;
import pl.edu.amu.wmi.sapper.map.objects.types.Type;

import java.util.*;


public class Skills {

    public String getskills(Queue<BombType> result){

        List<BombType> list = new ArrayList<>(result);
        System.out.println("Sorted bombs: " + list.toString());
        List<Type> types = new ArrayList<>();

        for(int i=0; i<list.size();i++){
            types.add(list.get(i).getMaterial());
          //System.out.println(list.get(i).getMaterial());
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



       /* System.out.println(countNuke);
        System.out.println("Fake priority: "+FakeBombPriority);
        System.out.println("Nuke priority: "+NukePriority);
        System.out.println("C4 priority: "+C4Priority);*/

        String skillsForNukeBomb = "";
        String skillsForC4Bomb = "";
        String skillsForFakeBomb = "";
        String skillsForChemicalBomb = "";
        String skillsForDynamiteBomb = "";
        String skillsForHomeMadeBomb = "";
        String skillsForDirtyBomb = "";

        if(NukePriority>=0.66)
            skillsForNukeBomb="11";
        else if (NukePriority>=0.33 && NukePriority<0.66)
            skillsForNukeBomb="10";
        else if(NukePriority<0.33 && NukePriority>0)
            skillsForNukeBomb="01";
        else
            skillsForNukeBomb="00";

        if(C4Priority>=0.66)
            skillsForC4Bomb="11";
        else if (C4Priority>=0.33 && C4Priority<0.66)
            skillsForC4Bomb="10";
        else if(C4Priority<0.33 && C4Priority>0)
            skillsForC4Bomb="01";
        else
            skillsForC4Bomb="00";

        if(DirtyBombPriority>=0.66)
            skillsForDirtyBomb="11";
        else if (DirtyBombPriority>=0.33 && DirtyBombPriority<0.66)
            skillsForDirtyBomb="10";
        else if(DirtyBombPriority<0.33 && DirtyBombPriority>0)
            skillsForDirtyBomb="01";
        else
            skillsForDirtyBomb="00";

        if(ChemicalBombPriority>=0.66)
            skillsForChemicalBomb="11";
        else if (ChemicalBombPriority>=0.33 && ChemicalBombPriority<0.66)
            skillsForChemicalBomb="10";
        else if(ChemicalBombPriority<0.33 && ChemicalBombPriority>0)
            skillsForChemicalBomb="01";
        else
            skillsForChemicalBomb="00";


        if(DynamitePriority>=0.66)
            skillsForDynamiteBomb="11";
        else if (DynamitePriority>=0.33 && DynamitePriority<0.66)
            skillsForDynamiteBomb="10";
        else if(DynamitePriority<0.33 && DynamitePriority>0)
            skillsForDynamiteBomb="01";
        else
            skillsForDynamiteBomb="00";

        if(HomeMadeBombPriority>=0.66)
            skillsForHomeMadeBomb="11";
        else if (HomeMadeBombPriority>=0.33 && HomeMadeBombPriority<0.66)
            skillsForHomeMadeBomb="10";
        else if(HomeMadeBombPriority<0.33 && HomeMadeBombPriority>0)
            skillsForHomeMadeBomb="01";
        else
            skillsForHomeMadeBomb="00";


        if(FakeBombPriority>=0.66)
            skillsForFakeBomb="11";
        else if (FakeBombPriority>=0.33 && FakeBombPriority<0.66)
            skillsForFakeBomb="10";
        else if(FakeBombPriority<0.33 && FakeBombPriority>0)
            skillsForFakeBomb="01";
        else
            skillsForFakeBomb="00";


        String skills=skillsForNukeBomb+skillsForC4Bomb+skillsForDirtyBomb+skillsForChemicalBomb+skillsForDynamiteBomb+skillsForHomeMadeBomb+skillsForFakeBomb;
        return skills;
    }
}
