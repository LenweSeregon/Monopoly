package mvc_game_model;

import java.util.ArrayList;

public class Trade {

	private Trade_datas first;
	private Trade_datas second;
	private boolean is_started;
	private boolean first_want_end;
	private boolean second_want_end;

	/**
	 * Constructeur de la classe qui représente la classe permettant de gérer
	 * les échanges entre les différents joueur
	 * 
	 * @param pseudo_first
	 *            le pseudo du premier joueur
	 * @param pseudo_second
	 *            le pseudo du deuxiéme joueur
	 */
	public Trade(String pseudo_first, String pseudo_second) {
		this.first = new Trade_datas(pseudo_first);
		this.second = new Trade_datas(pseudo_second);
		this.is_started = true;
		this.first_want_end = false;
		this.second_want_end = false;
	}

	/**
	 * Méthode permettant de savoir si on peut arreter le trade, c'est à dire
	 * que les deux joueurs ont acceptés l'echange
	 * 
	 * @return vrai si on peut arreter l'echange, faux sinon
	 */
	public boolean can_stop_trade() {
		return first_want_end && second_want_end;
	}

	/**
	 * Méthode permettant de signaler qu'aucun des deux joueurs n'est maintenant
	 * pret a finir l'échange. Cette méthode est notamment appelé lorsqu'un
	 * joueur ajoute ou retirer une propriété / de l'argen
	 */
	public void nobody_ready() {
		first_want_end = false;
		second_want_end = false;
	}

	/**
	 * Méthode permettant de définir si un joueur via son pseudo veut valider
	 * l'enchere
	 * 
	 * @param pseudo
	 *            le pseudo du jouer qui veut valider l'enchere
	 * @param end
	 *            est ce que le joueur veut valider l'enchere
	 */
	public void set_want_end(String pseudo, boolean end) {
		if (first.pseudo.equals(pseudo)) {
			first_want_end = end;
		} else if (second.pseudo.equals(pseudo)) {
			second_want_end = end;
		}
	}

	/**
	 * Méthode permettant de récupérer les données du premier joueur de
	 * l'échange
	 * 
	 * @return les données du premier joueur
	 */
	public Trade_datas get_first() {
		return first;
	}

	/**
	 * Méthode permettant de récupérer les données du deuxiéme joueur de
	 * l'échange
	 * 
	 * @return les données du deuxiéme joueur
	 */
	public Trade_datas get_second() {
		return second;
	}

	/**
	 * Méthode permettant de récupérer le pseudo du premiére joueur de l'échange
	 * 
	 * @return le pseudo du premiére joueur de l'échange
	 */
	public String get_pseudo_first() {
		return first.pseudo;
	}

	/**
	 * Méthode permettant de récupérer le pseudo du deuxième joueur de l'échange
	 *
	 * @return le pseudo du deuxième joueur de l'échange
	 */
	public String get_pseudo_second() {
		return second.pseudo;
	}

	/**
	 * Méthode permettant d'ajouter un propriété d'échange à un joueur via son
	 * pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a ajouter une propriété
	 * @param number
	 *            le numéro de la propriété à ajouter
	 */
	public void add_property(String pseudo, int number) {
		if (first.pseudo.equals(pseudo)) {
			first.buyable_number.add(number);
		} else if (second.pseudo.equals(pseudo)) {
			second.buyable_number.add(number);
		}
	}

	/**
	 * Méthode permettant de retirer une propriété d'échange à un joueur via son
	 * pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a retirer une propriété
	 * @param number
	 *            le numéro de la propriété à retirer
	 */
	public void remove_property(String pseudo, int number) {
		if (first.pseudo.equals(pseudo)) {
			first.buyable_number.remove((Integer) (number));
		} else if (second.pseudo.equals(pseudo)) {
			second.buyable_number.remove((Integer) (number));
		}
	}

	/**
	 * Méthode permettant de choisir la somme d'argent convenu pendant un
	 * échange pour un joueur
	 * 
	 * @param pseudo
	 *            le joueur qui choisit la somme d'argent
	 * @param money
	 *            l'argent décidé par le joueur
	 */
	public void set_money_value(String pseudo, int money) {
		if (first.pseudo.equals(pseudo)) {
			first.money = money;
		} else if (second.pseudo.equals(pseudo)) {
			second.money = money;
		}
	}

	/**
	 * Méthode permettant de récupérer les données de transaction d'un joueur
	 * via son pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur que l'on recherche
	 * @return les données de transation du joueur
	 */
	public Trade_datas get_trade_datas(String pseudo) {
		if (first.pseudo.equals(pseudo)) {
			return first;
		} else {
			return second;
		}
	}

	/**
	 * Méthode permettant de savoir si l'échange à commencé
	 * 
	 * @return vrai si l'echange est démarré, faux sinon
	 */
	public boolean is_started() {
		return this.is_started;
	}

	/**
	 * Méthode permettant de démarrer l'échange
	 */
	public void start_trade() {
		this.is_started = true;
	}

	/**
	 * Méthode permettant d'arreter l'échange
	 */
	public void stop_trade() {
		this.is_started = false;
	}

	public class Trade_datas {
		public String pseudo;
		public int money;
		public ArrayList<Integer> buyable_number;

		public Trade_datas(String pseudo) {
			this.pseudo = pseudo;
			this.money = 0;
			this.buyable_number = new ArrayList<Integer>();
		}

	}
}
