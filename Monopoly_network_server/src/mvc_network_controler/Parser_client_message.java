package mvc_network_controler;

import mvc_network_model.Client_entity;

public abstract class Parser_client_message {

	protected String parser_name;

	/**
	 * Constructeur de la classe représentant la classe abstraite pour le
	 * parsing de message durant la communication entre le serveur et les
	 * clients
	 * 
	 * @param name
	 *            le nom du parseur
	 */
	public Parser_client_message(String name) {
		this.parser_name = name;
	}

	/**
	 * Méthode ayant pour but d'analyser le message du client, si celui ci est
	 * correct, d'executer les actions en conséquence et d'ensuite avertir si le
	 * partsing c'est bien passé
	 * 
	 * @param client
	 *            le client qui a envoyé le message
	 * @param message
	 *            le message du client
	 * @return vrai si le parsing a été réussi, faux sinon
	 */
	public abstract boolean analyse_string(Client_entity client, String message);

}
