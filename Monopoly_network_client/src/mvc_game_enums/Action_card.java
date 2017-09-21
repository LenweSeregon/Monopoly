package mvc_game_enums;

/**
 * Une énumérations qui représentes les différents actions qui peuvent etre
 * réalisé via les cartes
 * 
 * @author nicolas
 *
 */
public enum Action_card {
	GO_JAIL("GO_JAIL"), PAY_TAXE("PAY_TAXE"), PURPLE("PURPLE"), ORANGE("ORANGE"), RED("RED"), YELLOW("YELLOW"), GREEN(
			"GREEN"), DARK_BLUE("DARK_BLUE"), NONE("NONE");

	private final String name;

	private Action_card(String s) {
		name = s;
	}

	public static Action_card fromString(String text) {
		for (Action_card b : Action_card.values()) {
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
