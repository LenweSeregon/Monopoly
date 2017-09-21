package mvc_network_controler;

public class Parser_general extends Parser_message {

	private Network_controler controler;

	/**
	 * Constructeur de la classe qui représente le parseur de message du coté
	 * génréral
	 * 
	 * @param controler
	 *            une référence sur le controler de réseau
	 */
	public Parser_general(Network_controler controler) {
		super("General");
		this.controler = controler;
	}

	@Override
	public boolean analyse_string(String message) {
		String[] splitted = message.split(" ");

		if (splitted.length >= 2) {
			String parse_type = splitted[0];
			String parse_info = splitted[1];

			if (parse_type.equals("GENERAL")) {
				if (parse_info.equals("DISCONNECTION")) {
					controler.server_down();
					return true;
				} else if (parse_info.equals("GAME_READY")) {
					controler.game_ready();
					return true;
				} else if (parse_info.equals("BUILD_BOARD")) {
					controler.get_game_controler().build_board(splitted[2]);
					return true;
				}
			} else {
				return false;
			}
		}

		return false;
	}
}
