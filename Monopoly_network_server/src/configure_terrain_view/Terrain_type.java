package configure_terrain_view;

/**
 * Une énumération représentant tout les différents type de terrain que l'on
 * peut trouver sur le plateau du monopoly
 * 
 * @author nicolas
 *
 */
public enum Terrain_type {
	PROPERTY("PROPERTY"), STATION("STATION"), COMPANY("COMPANY"), GO("GO"), PARKING("PARKING"), GO_JAIL(
			"GO_JAIL"), JAIL("JAIL"), CARDS("CARDS"), PUBLIC("PUBLIC");

	private final String name;

	private Terrain_type(String s) {
		name = s;
	}

	public static Terrain_type fromString(String text) {
		for (Terrain_type b : Terrain_type.values()) {
			if (b.name.equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}
}
