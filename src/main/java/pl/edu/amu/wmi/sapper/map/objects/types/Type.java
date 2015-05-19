package pl.edu.amu.wmi.sapper.map.objects.types;

public enum Type {
	C4, Nuke, DirtyBomb, FakeBomb, HomeMadeBomb, Dynamite, ChemicalBomb;
	
	@Override
	public String toString() {
		switch(this) {
		case C4: return "C4";
		case ChemicalBomb: return "ChemicalBomb";
		case DirtyBomb: return "DirtyBomb";
		case Dynamite: return "Dynamite";
		case FakeBomb: return "FakeBomb";
		case HomeMadeBomb: return "HomeMadeBomb";
		case Nuke: return "Nuke";
		default: return "UNKNOWN";		
		}
	}
}
