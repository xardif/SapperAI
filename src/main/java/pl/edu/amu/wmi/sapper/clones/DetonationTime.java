package pl.edu.amu.wmi.sapper.clones;


import pl.edu.amu.wmi.sapper.map.objects.types.Type;

import java.util.HashMap;
import java.util.Map;

public class DetonationTime {
    public Map<Type, Integer>  getTime(Skills solution){
        Map<Type, Integer> bomby= new HashMap<Type, Integer>();
        String skills = solution.toString();
        String part[] = new String[7];
        part[0]=skills.substring(0,2);
        part[1]=skills.substring(2,4);
        part[2]=skills.substring(4,6);
        part[3]=skills.substring(6,8);
        part[4]=skills.substring(8,10);
        part[5]=skills.substring(10,12);
        part[6]=skills.substring(12,14);

        if(part[0]=="01")
            bomby.put(Type.Nuke, 10);
        else if(part[0]=="10")
            bomby.put(Type.Nuke, 7);
        else if(part[0]=="11")
            bomby.put(Type.Nuke, 4);

        if(part[1]=="01")
            bomby.put(Type.C4, 10);
        else if(part[1]=="10")
            bomby.put(Type.C4, 7);
        else if(part[1]=="11")
            bomby.put(Type.C4, 4);

        if(part[2]=="01")
            bomby.put(Type.DirtyBomb, 10);
        else if(part[2]=="10")
            bomby.put(Type.DirtyBomb, 7);
        else if(part[2]=="11")
            bomby.put(Type.DirtyBomb, 4);

        if(part[3]=="01")
            bomby.put(Type.ChemicalBomb, 10);
        else if(part[3]=="10")
            bomby.put(Type.ChemicalBomb, 7);
        else if(part[3]=="11")
            bomby.put(Type.ChemicalBomb, 4);

        if(part[4]=="01")
            bomby.put(Type.Dynamite, 10);
        else if(part[4]=="10")
            bomby.put(Type.Dynamite, 7);
        else if(part[4]=="11")
            bomby.put(Type.Dynamite, 4);

        if(part[5]=="01")
            bomby.put(Type.HomeMadeBomb, 10);
        else if(part[5]=="10")
            bomby.put(Type.HomeMadeBomb, 7);
        else if(part[5]=="11")
            bomby.put(Type.HomeMadeBomb, 4);

        if(part[6]=="01")
            bomby.put(Type.FakeBomb, 10);
        else if(part[6]=="10")
            bomby.put(Type.FakeBomb, 7);
        else if(part[6]=="11")
            bomby.put(Type.FakeBomb, 4);

        return (bomby);
    }
}
