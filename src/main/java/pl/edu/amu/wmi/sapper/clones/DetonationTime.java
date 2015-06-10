package pl.edu.amu.wmi.sapper.clones;


import pl.edu.amu.wmi.sapper.map.objects.types.Type;

import java.util.HashMap;
import java.util.Map;

public class DetonationTime {
    public Map<Type, Integer>  getTime(String solution){
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

        if(part[0].equals("01"))
            bomby.put(Type.Nuke, 10);
        else if(part[0].equals("10"))
            bomby.put(Type.Nuke, 7);
        else if(part[0].equals("11"))
            bomby.put(Type.Nuke, 4);

        if(part[1].equals("01"))
            bomby.put(Type.C4, 10);
        else if(part[1].equals("10"))
            bomby.put(Type.C4, 7);
        else if(part[1].equals("11"))
            bomby.put(Type.C4, 4);

        if(part[2].equals("01"))
            bomby.put(Type.DirtyBomb, 10);
        else if(part[2].equals("10"))
            bomby.put(Type.DirtyBomb, 7);
        else if(part[2].equals("11"))
            bomby.put(Type.DirtyBomb, 4);

        if(part[3].equals("01"))
            bomby.put(Type.ChemicalBomb, 10);
        else if(part[3].equals("10"))
            bomby.put(Type.ChemicalBomb, 7);
        else if(part[3].equals("11"))
            bomby.put(Type.ChemicalBomb, 4);

        if(part[4].equals("01"))
            bomby.put(Type.Dynamite, 10);
        else if(part[4].equals("10"))
            bomby.put(Type.Dynamite, 7);
        else if(part[4].equals("11"))
            bomby.put(Type.Dynamite, 4);

        if(part[5].equals("01"))
            bomby.put(Type.HomeMadeBomb, 10);
        else if(part[5].equals("10"))
            bomby.put(Type.HomeMadeBomb, 7);
        else if(part[5].equals("11"))
            bomby.put(Type.HomeMadeBomb, 4);

        if(part[6].equals("01"))
            bomby.put(Type.FakeBomb, 10);
        else if(part[6].equals("10"))
            bomby.put(Type.FakeBomb, 7);
        else if(part[6].equals("11"))
            bomby.put(Type.FakeBomb, 4);

        return (bomby);
    }
}
