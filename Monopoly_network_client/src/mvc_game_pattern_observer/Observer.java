package mvc_game_pattern_observer;

import java.util.ArrayList;
import java.util.Vector;

import mvc_game_enums.Card_type;
import mvc_game_model.Player;
import mvc_game_model_terrains.Abstract_terrain;
import mvc_game_model_terrains.Station_terrain;

public interface Observer {

	/**
	 * Méthode permettant d'etre notifié du chargement du modele et des elements
	 * graphiques
	 * 
	 * @param terrains
	 *            les terrains qui viennent d'etre charge
	 * @param players
	 *            les joueurs qui viennent d'etre charge
	 * @param your_pseudo
	 *            le pseudo du client
	 */
	public void update_model_loaded(ArrayList<Abstract_terrain> terrains, ArrayList<Player> players,
			String your_pseudo);

	/**
	 * Méthode permettant d'etre notifié du nouveau tour d'un jour
	 * 
	 * @param pseudo
	 *            le pseudo du joueur a qui c'est le tour
	 */
	public void update_player_turn(String pseudo);

	/**
	 * Méthode permettant d'etre notifié de l'abandon d'un joueur
	 * 
	 * @param pseudo
	 *            le joueur qui a abandonné
	 */
	public void update_player_surrend(String pseudo);

	/**
	 * Méthode permettant d'etre notifié du changement d'argent d'un joueur
	 * 
	 * @param pseudo
	 *            le joueur qui a eu un changement d'argent
	 * @param money
	 *            l'argent du nouveau joueur
	 */
	public void update_player_money_change(String pseudo, int money);

	/**
	 * Méthode permettant d'etre notifié de l'ajout d'un message pour le joueur
	 * 
	 * @param pseudo
	 *            le joueur qui a eu un changement de message
	 * @param message
	 *            le message a lui envoyer
	 */
	public void update_player_message(String pseudo, String message);

	/**
	 * Méthode permettant d'etre notifié du vainqueur de la partie
	 * 
	 * @param text
	 *            le texte qui représente la victoire / défaite
	 */
	public void update_winner(String text);

	/**
	 * Méthode permettant d'etre notifié de l'animation ou non des dés de jeu
	 * 
	 * @param launch
	 *            valeure booléenne de lancement des animations de dés
	 */
	public void update_animation_dice(boolean launch);

	/**
	 * Méthode permettant d'etre notifié de la valeur des dés
	 * 
	 * @param values
	 *            la valeur des dés dans un vecteur
	 */
	public void update_dice_values(Vector<Integer> values);

	/**
	 * Méthode permettant d'etre notifié du changement de position du pion de
	 * joueur
	 * 
	 * @param pseudo
	 *            le joueur dont le pion a été déplacé
	 * @param position
	 *            la nouvelle position du joueur
	 * @param fast_move
	 *            valeure booléenne pour le mouvement rapide ou non
	 */
	public void update_piece_move(String pseudo, int position, boolean fast_move);

	/**
	 * Méthode permettant d'etre notifié de la possibilité d'acheter un terrain
	 * 
	 * @param number_house
	 *            le numéro du terrain qui pourrait etre acheté
	 */
	public void update_buy_possible(int number_house);

	/**
	 * Méthode permettant d'etre notifié de l'achat d'une propriété
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui achete
	 * @param position_house
	 *            la position de la propriété qui a été acheté
	 */
	public void update_buyed_house(String pseudo, int position_house);

	/**
	 * Méthode permettant d'etre notifié du refus d'achat d'une propriété
	 */
	public void update_refused_buy_house();

	/**
	 * Méthode permettant d'etre notifié de la mise en hypothéque ou
	 * déhypotheque d'une maison
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui réalise l'action
	 * @param number_house
	 *            le numéro de la propriété sur laquelle on fait l'action
	 * @param mortgaging
	 *            est ce qu'on hypothéque ou déhypotheque
	 */
	public void update_mortgage_house(String pseudo, int number_house, boolean mortgaging);

	/**
	 * Méthode permettant d'etre notifié d'un changement de maison sur une
	 * propriété
	 * 
	 * @param pseudo
	 *            le pseudo de la personne qui change son nombre de maison
	 * @param number_property
	 *            le numéro de la propriété sur laquelle on chagne le nombre de
	 *            maison
	 * @param number_house
	 *            le nombre de maison qui sont sur la propriété
	 */
	public void update_nb_house_property(String pseudo, int number_property, int number_house);

	/**
	 * Méthode permettant d'etre notifié de la mise en prison ou non d'un joueur
	 * 
	 * @param pseudo
	 *            le pseudo du joueur cible de l'action
	 * @param in_jail
	 *            valeure booléenne sur la msie en prison du joueur
	 */
	public void update_player_jail(String pseudo, boolean in_jail);

	/**
	 * Méthode permettant d'etre notifié de la necessité de gérer
	 * l'emprisonnement d'un joueur
	 * 
	 * @param pseudo
	 *            le joueur qui doit gérer l'emprisonnement
	 */
	public void update_need_manage_jail(String pseudo, boolean have_to_manage);

	/**
	 * Méthode peremttant d'etre notifié de la vente d'une propriété
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui vend
	 * @param position_house
	 *            le numéro de la propriété vendue
	 */
	public void update_selled_house(String pseudo, int position_house);

	/**
	 * Méthode permettant d'etre notifier qu'un joueur doit gérer le paiement
	 * d'un tax ou d'un loyer
	 * 
	 * @param owner
	 *            le pseudo du propriétaire qui doit etre payé
	 * @param sum
	 *            la somme qui doit etre versée
	 * @param have_to_pay
	 *            est ce que le joueur doit payer
	 * @param la
	 *            personne endetté
	 */
	public void update_need_pay_rent(String owner, int sum, boolean have_to_pay, String debter);

	/**
	 * Méthode permettant d'etre notifier qu'une propriété est en enchere est
	 * qu'il est maintenant possible de proposé un prix
	 * 
	 * * @param players les différents jouer qui vont aux encheres
	 * 
	 * @param number_property
	 *            le numéro de la propriété cible de l'enchere
	 * @param auction
	 *            est ce que l'auction demarrage ou s'arrete
	 */
	public void update_auction_start(ArrayList<Player> players, int number_property, boolean auction);

	/**
	 * Méthode permettant d'etre notifier du changement de valeur d'enchere par
	 * l'un des joueurs
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a changé son enchere
	 * @param value
	 *            la valeur de son enchere
	 */
	public void update_auction_value(String pseudo, int value);

	/**
	 * Méthode permettant d'etre notifié qu'il faut afficher les informations
	 * d'une carte
	 * 
	 * @param type_card
	 *            le type de carte a afficher
	 * @param description
	 *            la description de la carte
	 * @param show
	 *            est ce qu'on doit montrer la carte
	 */
	public void update_show_card_info(Card_type type_card, String description, boolean show);

	/**
	 * Méthode permettant d'etre notifié qu'il faut afficher dans la vue le
	 * panel pour selectionner avec qui faire l'echange
	 * 
	 * @param emitter
	 *            l'emetteur de la demande d'echange
	 * @param players
	 *            les différents joueurs différents disponible pour faire
	 *            l'echange
	 * @param est
	 *            ce que le panel doit etre affiché
	 */
	public void update_want_to_trade(Player emitter, ArrayList<Player> players, boolean show);

	/**
	 * Méthode permettant d'etre notifié qu'il faut afficher dans la vue le
	 * milieu du plateau le panel pour réaliser les échanges
	 * 
	 * @param first
	 *            le premiére joueur de l'échange
	 * @param second
	 *            le second joueur de l'échange
	 * @param est
	 *            ce que le panel doit etre affiché
	 */
	public void update_start_trade(Player first, Player second, boolean show);

	/**
	 * Méthode permettant d'etre notifié qu'un changement dans le propriétés
	 * proposés par l'adversaire durant un échange
	 * 
	 * @param number_property
	 *            le numéro de la propriété qui fait subir un changement
	 * @param add
	 *            est ce que la propriété est ajouté ou retiré de la liste
	 */
	public void update_trade_change_properties(int number_property, boolean add);

	/**
	 * Méthode permettant d'etre notifié qu'un changement d'argent proposé par
	 * l'adversaire durant un échange
	 * 
	 * @param money
	 *            la nouvelle somme d'argent
	 */
	public void update_trade_change_money(int money);

	/**
	 * Méthode permettant d'etre notifié de la validation ou non d'un des deux
	 * joueurs de l'échange
	 * 
	 * @param versus
	 *            est ce que le joueur qui valide est l'adversaire
	 * @param validate
	 *            vrai si validé, faux sinon
	 */
	public void update_trade_validate(boolean versus, boolean validate);

	/**
	 * Méthode permettant d'etre notifié de la mise en pause ou de la reprise du
	 * jeu pour les clients
	 * 
	 * @param pause
	 *            est ce qu'on met en pause ou l'on reprend
	 */
	public void update_pause_game(boolean pause);

	/**
	 * Méthode permettant d'etre notifié de repaint l'element SWING racine de
	 * l'application
	 */
	public void update_repaint_root();

	/**
	 * Méthode permettant de notifier que tout les joueurs sont pret et que l'on
	 * peut commencer
	 */
	public void update_fire_go();

	/**
	 * Méthode peremettant d'etre notifié qu'un message a été recu via le réseau
	 * et qu'il faut l'afficher
	 * 
	 * @param message
	 *            le message a afficher
	 */
	public void update_message_receive(String message);

	/**
	 * Méthode permettant d'etre notifié qu'un joueur vient d'atteintre la
	 * banqueroute
	 * 
	 * @param owner
	 *            la personne qui a mis en banqueroute le joueur
	 * @param sum
	 *            la somme que le joueur doit
	 * @param debter
	 *            le pseudo du joueur qui est endetté
	 */
	public void update_bankrupty(String owner, int sum, String debter);

	/**
	 * Méthode permettant d'etre notifié qu'un joueur est tombé sur la carte
	 * communautaire vol et qu'il faut afficher le panel pour voler une carte
	 * 
	 * @param stealer
	 *            le joueur qui va voler
	 * @param players
	 *            les joueurs a qui on va pouvoir voler les cartes
	 * @param show
	 *            est ce qu'on doit montrer le panel ou non
	 */
	public void update_start_panel_steal_card(Player stealer, ArrayList<Player> players, boolean show);

	/**
	 * Méthode permettant d'etre notifié qu'uon doit lancer le panel pour
	 * pouvoir se déplacer vers une gare en fin de tour
	 * 
	 * @param stations
	 *            les différentes station disponibles
	 * @param show
	 *            est ce qu'on doit afficher le panel
	 */
	public void update_start_panel_teleport_station(ArrayList<Station_terrain> stations, boolean show);

	/**
	 * Méthode permettant d'etre notifié qu'on lancer le panel pour pouvoir
	 * choisir le nombre de dés avant de lancer
	 * 
	 * @param show
	 *            est ce qu'on doit montrer le panel
	 */
	public void update_start_panel_chose_nb_dice(boolean show);

}
