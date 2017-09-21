package mvc_network_controler;

public abstract class Parser_message {

	protected String parser_name;

	/**
	 * Constructeur de la classe abstraite qui représente un parseur de message
	 * 
	 * @param name
	 *            le nom du parseru
	 */
	public Parser_message(String name) {
		this.parser_name = name;
	}

	/**
	 * Méthode abstraite qui va permettre de parser les différents messages qui
	 * arrive du serveur
	 * 
	 * @param message
	 *            le message a analyser
	 * @return vrai si le message a pu etre analyser et l'action réalisé, faux
	 *         sinon
	 */
	public abstract boolean analyse_string(String message);

}