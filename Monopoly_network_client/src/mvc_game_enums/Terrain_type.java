package mvc_game_enums;

/**
 * Enumeration qui représente les différents type de terrains qui constituent le
 * plateau de jeu. Ces différents types vont servir a réaliser différentes
 * action en fonction
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
