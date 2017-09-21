package mvc_game_enums;

/**
 * Enumération qui représente les deux type de compagnie disponible dans le jeu
 * 
 * @author nicolas
 *
 */
public enum Company_type {

	WATER("Eau"), SUPPLY("Electricite");

	private final String name;

	private Company_type(String s) {
		name = s;
	}

	public static Company_type fromString(String text) {
		for (Company_type b : Company_type.values()) {
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
