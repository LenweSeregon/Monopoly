package mvc_network_model;

/**
 * Une énumération représentant les différents état qu'une partie peut avoir
 * 
 * @author nicolas
 *
 */
public enum Server_state {
	RUNNING("Running"), STOPPED("Stopped"), NO_MORE_CONNECTION("No more connection");

	private final String name;

	private Server_state(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}
}
