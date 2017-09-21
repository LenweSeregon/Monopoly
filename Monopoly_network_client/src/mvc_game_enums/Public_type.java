package mvc_game_enums;

/**
 * Enumeration qui représente les différents type de terrain public entre les 2
 * valeurs d'énumération possible
 * 
 * @author nicolas
 *
 */
public enum Public_type {
	TAX("Impot"), LUXURY("fortune");

	private final String name;

	private Public_type(String s) {
		name = s;
	}

	public static Public_type fromString(String text) {
		for (Public_type b : Public_type.values()) {
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
