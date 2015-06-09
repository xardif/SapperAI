package pl.edu.amu.wmi.sapper.map.objects.types;

public enum Type {
	C4("C4"), Nuke("Nuke"),
	DirtyBomb("DirtyBomb"), FakeBomb("FakeBomb"), HomeMadeBomb("HomeMadeBomb"),
	Dynamite("Dynamite"), ChemicalBomb("ChemicalBomb");

	private final String name;

	Type(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
