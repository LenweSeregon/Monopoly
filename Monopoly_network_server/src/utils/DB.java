package utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

	private String DB_url;
	private String DB_user;
	private String DB_password;
	private java.sql.Connection DB_connect;
	private java.sql.Statement DB_statement;

	private static DB instance = null;

	/**
	 * Constructeur de la classe qui représente la connexion entre notre
	 * application et notre base de donnée
	 * 
	 * @param url
	 *            l'url sur laquelle on souhaite se connecter
	 * @param user
	 *            l'utilisateur avec lequel on veut se connecter
	 * @param pwd
	 *            le mot de passe de l'utilisateur avec lequel on veut se
	 *            connecter
	 */
	private DB(String url, String user, String pwd) {
		this.DB_url = url;
		this.DB_user = user;
		this.DB_password = pwd;
		this.DB_connect = null;
		this.DB_statement = null;
	}

	public static DB get_instance() {
		if (instance == null) {
			instance = new DB("//localhost/Monopoly", "root", "root");
			instance.connect();
		}
		return instance;
	}

	/**
	 * Méthode permettant de lancer la connexion à la base de données avec les
	 * informations qu'on a fournit au constructeur
	 * 
	 * @return vrai si la connexion a pu etre réalisé, faux sinon
	 */
	public boolean connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.DB_connect = DriverManager.getConnection("jdbc:mysql:" + this.DB_url, this.DB_user, this.DB_password);
			this.DB_statement = this.DB_connect.createStatement();
			return true;
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		} catch (ClassNotFoundException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		} catch (InstantiationException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		} catch (IllegalAccessException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	/**
	 * Méthode permettant de fermer toutes les connexions avec la base de
	 * données
	 */
	public void close() {
		try {
			this.DB_statement.close();
			this.DB_connect.close();
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Méthode permettant de réaliser une requete sql qui va nous retourner un
	 * resultat sous la forme d'un ResultSet
	 * 
	 * @param sql
	 *            la requete sql que l'on souhaite envoyer à la base de données
	 * @return la réponse de la base de données dans une classe dédié à contenir
	 *         ces données
	 */
	public ResultSet exec(String sql) {
		try {
			ResultSet rs = this.DB_statement.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}

	public ResultSet get_all_terrains() {
		String sql = "SELECT * from Terrain";
		ResultSet res = this.exec(sql);
		return res;
	}

	/**
	 * Méthode permettant de demander l'ajout d'un indicateur de ligne
	 * 
	 * @param id
	 *            l'identifiant du niveau auquel l'indicateur de ligne fait
	 *            référence
	 * @param line
	 *            la ligne à laquelle se trouve l'indicateur
	 * @param indication
	 *            l'indice
	 */
	public void insert_user(String username, String password) {
		String sql = "INSERT INTO `User` (`pseudo`, `password`) VALUES (" + "\"" + username + "\"," + "\"" + password
				+ "\")";

		this.insert_values(sql);
	}

	/**
	 * Méthode permettant d'inserer des valeurs dans notre base de données via
	 * une requete SQL.
	 * 
	 * @param sql
	 *            la requete sql d'insertion
	 */
	public void insert_values(String sql) {
		try {
			DB_statement.executeUpdate(sql);
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Méthode permettant de savoir si un client qui a envoyé ces données peut
	 * se connecter au serveur
	 * 
	 * @param pseudo
	 *            le pseudo du client
	 * @param pwd
	 *            le mot de passe du client
	 * @return vrai si le client peut se connecter, faux sinon
	 */
	public boolean able_to_connect(String pseudo, String pwd) {
		String sql = "SELECT pseudo FROM `User` WHERE UPPER(pseudo) LIKE UPPER('" + pseudo + "') AND password LIKE '"
				+ pwd + "'";

		ResultSet res = this.exec(sql);
		try {
			return res.next();
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

	/**
	 * Méthode permettant de savoir si un nom est valide pour une insertion dans
	 * la base de données. Ainsi la fonction regarde simplement si avec une
	 * requete avec le nom donné en paramètre, on recoit une réponse ou non
	 * 
	 * @param pseudo
	 *            le nom dont l'on veut tester la possibilité
	 * @return vrai si le nom est valide, faux sinon
	 */
	public boolean name_available(String pseu) {
		for (int i = 0; i < pseu.length(); i++) {
			if (!Character.isLetter(pseu.charAt(i)) && !Character.isDigit(pseu.charAt(i)) && pseu.charAt(i) != ' ') {
				return false;
			}
		}
		String sql = "SELECT pseudo FROM `User` WHERE UPPER(pseudo) LIKE UPPER('" + pseu + "')";

		ResultSet res = this.exec(sql);
		try {
			return !res.next();
		} catch (SQLException e) {
			Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
		}
		return false;
	}

}
