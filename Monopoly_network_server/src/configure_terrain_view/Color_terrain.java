package configure_terrain_view;

/**
 * Une énumérations qui permet de définir les différentes couleures qui peuvent
 * se trouver dans l'application.
 * 
 * @author nicolas
 *
 */
public enum Color_terrain {

	BROWN("BROWN"), LIGHT_BLUE("LIGHT_BLUE"), PURPLE("PURPLE"), ORANGE("ORANGE"), RED("RED"), YELLOW("YELLOW"), GREEN(
			"GREEN"), DARK_BLUE("DARK_BLUE"), NONE("NONE");

	private final String name;

	private Color_terrain(String s) {
		name = s;
	}

	public static Color_terrain fromString(String text) {
		for (Color_terrain b : Color_terrain.values()) {
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
