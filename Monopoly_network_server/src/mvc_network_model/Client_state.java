package mvc_network_model;

/**
 * Cette énumération représente l'état d'un client à un instant T, en fonction
 * de cet état, des actions seront disponibles ou non
 * 
 * @author nicolas
 *
 */
public enum Client_state {
	LOOKING_FOR_GAME("Search game"), OCCUPIED("Occupied"), ONLINE("Online"), MISSING("Missing"), INGAME("In game");

	private final String name;

	private Client_state(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}
}
