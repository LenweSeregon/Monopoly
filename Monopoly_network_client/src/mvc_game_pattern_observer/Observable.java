package mvc_game_pattern_observer;

import java.util.ArrayList;
import java.util.Vector;

import mvc_game_enums.Card_type;
import mvc_game_model.Player;
import mvc_game_model_terrains.Abstract_terrain;
import mvc_game_model_terrains.Station_terrain;

public interface Observable {

	/**
	 * Méthode permettant d'ajouter un observeur à un observabl
	 * 
	 * @param ob
	 *            l'observeur à ajouter
	 */
	public void add_observer(Observer ob);

	/**
	 * Méthode permettant de retirer un observeur à un observable
	 * 
	 * @param ob
	 *            l'observeur a retirer
	 */
	public void remove_observer(Observer ob);

	/**
	 * Méthode permettant de supprimer tout les observeurs
	 */
	public void remove_all_observers();

	/**
	 * Méthode permettant de notify du chargement du plateau et des données
	 * 
	 * @param terrains
	 *            les terrains chargés
	 * @param players
	 *            les joeuurs chargés
	 * @param your_pseudo
	 *            le pseudo du client
	 */
	public void notify_model_loaded(ArrayList<Abstract_terrain> terrains, ArrayList<Player> players,
			String your_pseudo);

	/**
	 * Méthode permettant de notifier du tour d'un joueur
	 * 
	 * @param pseudo
	 *            le pseudo du joueur dont c'est le tour
	 */
	public void notify_player_turn(String pseudo);

	/**
	 * Méthode permettant de notifier de l'abandon d'un joueur
	 * 
	 * @param pseudo
	 *            le pseudo dont le joueur a abandonné
	 */
	public void notify_player_surrend(String pseudo);

	/**
	 * Méthode permettant de notifier du changement d'argent d'un joueur
	 * 
	 * @param pseudo
	 *            le pseudo dont le joeur a eu son argent changé
	 * @param money
	 *            le nombre de monos dont a le joueur
	 */
	public void notify_player_money_change(String pseudo, int money);

	/**
	 * Méthode permettant de notifer d'un message a ajouter a un joueur
	 * 
	 * @param pseudo
	 *            le joueur a qui l'on veut assigner un message
	 * @param message
	 *            le message
	 */
	public void notify_player_message(String pseudo, String message);

	/**
	 * Méthode permettant de notifier un vainqueur de partie
	 * 
	 * @param text
	 *            le message a afficher sur l'ecran de fin
	 */
	public void notify_winner(String text);

	/**
	 * Méthode permettant de notify de l'animation des dés
	 * 
	 * @param launch
	 *            valeur booléenne si l'on veut activer ou désactiver le lancé
	 *            de dés
	 */
	public void notify_animation_dice(boolean launch);

	/**
	 * Méthode permettant de notifier des valeurs de dés
	 * 
	 * @param values
	 *            les valeurs de dés dans un vecteur
	 */
	public void notify_dice_values(Vector<Integer> values);

	/**
	 * Méthode permettant de notifier du changement de position du pion d'un
	 * joueur
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param position
	 *            la position du joueur
	 * @param fast_move
	 *            est ce qu'on veut bouger rapidement ou non
	 */
	public void notify_piece_move(String pseudo, int position, boolean fast_move);

	/**
	 * Méthode permettant de notifier de la possibilité d'achat d'une maison
	 * 
	 * @param number_house
	 *            le numéro de la maison dans la possibilité d'etre aacheté
	 */
	public void notify_buy_possible(int number_house);

	/**
	 * Méthode permettant de notifier de l'achat d'une proprieté
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param position_house
	 *            la position de la propriété du jouer
	 */
	public void notify_buyed_house(String pseudo, int position_house);

	/**
	 * Méthode permettant de notifier la vente d'une proprieté
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param position_house
	 *            la position de la propriété vendu
	 */
	public void notify_selled_house(String pseudo, int position_house);

	/**
	 * Méthode permettant de notifier du rfux d'achat de la maison
	 */
	public void notify_refused_buy_house();

	/**
	 * Méthode permettant de notifer de la mise en hypothéque d'une maison
	 * 
	 * @param pseudo
	 *            le pseudo du metteur
	 * @param number_house
	 *            le numéro de la maison
	 * @param mortgaging
	 *            est ce que la maison est hypothéqué ou déhypothéqué
	 */
	public void notify_mortgage_house(String pseudo, int number_house, boolean mortgaging);

	/**
	 * Méthode permettant de notifier le nombre de maison sur une propriété
	 * 
	 * @param pseudo
	 *            le pseudo du metteur de maison
	 * @param number_property
	 *            le numéro de la propriété
	 * @param number_house
	 *            le nombre de maison
	 */
	public void notify_nb_house_property(String pseudo, int number_property, int number_house);

	/**
	 * Méthode permettant de notifer si un joueur est en prison ou non
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param in_jail
	 *            est ce que le joueur est en prison
	 */
	public void notify_player_jail(String pseudo, boolean in_jail);

	/**
	 * Méthode permettant de notifier qu'un joueur doit gérer son emprisonnement
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui doit gérer son emprisonnement
	 */
	public void notify_need_manage_jail(String pseudo, boolean have_to_manage);

	/**
	 * Méthode permettant de notifier qu'un joueur doit gérer le paiement d'un
	 * tax ou d'un loyer
	 * 
	 * @param owner
	 *            le pseudo du propriétaire qui doit etre payé
	 * @param sum
	 *            la somme qui doit etre versée
	 * @param have_to_pay
	 *            est ce que le joueur doit payer
	 * @param debter
	 *            la personne endetté
	 */
	public void notify_need_pay_rent(String owner, int sum, boolean have_to_pay, String debter);

	/**
	 * Méthode permettant de notifier qu'une propriété est en enchere est qu'il
	 * est maintenant possible de proposé un prix
	 * 
	 * @param players
	 *            les différents jouer qui vont aux encheres
	 * @param number_property
	 *            le numéro de la propriété cible de l'enchere
	 * @param auction
	 *            est ce que l'auction demarrage ou s'arrete
	 */
	public void notify_auction_start(ArrayList<Player> players, int number_property, boolean auction);

	/**
	 * Méthode permettant de notifier qu'un joueur a chnger sa valeur d'enchere
	 * sur une propriété
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a choisit de changer son enchere
	 * @param value
	 *            la valeur de son enchere
	 */
	public void notify_auction_value(String pseudo, int value);

	/**
	 * Méthode permettant de notifier qu'il faut afficher les informations d'une
	 * carte
	 * 
	 * @param type_card
	 *            le type de carte a afficher
	 * @param description
	 *            la description de la carte
	 * @param show
	 *            est ce qu'on doit montrer la carte
	 */
	public void notify_show_card_info(Card_type type_card, String description, boolean show);

	/**
	 * Méthode permettant de notifier qu'il faut afficher dans la vue le panel
	 * pour selectionner avec qui faire l'echange
	 * 
	 * @param emitter
	 *            l'emetteur de la demande d'echange
	 * @param players
	 *            les différents joueurs différents disponible pour faire
	 *            l'echange
	 * @param est
	 *            ce que le panel doit etre affiché
	 */
	public void notify_want_to_trade(Player emitter, ArrayList<Player> players, boolean show);

	/**
	 * Méthode permettant de notifier qu'il faut afficher dans la vue le milieu
	 * du plateau le panel pour réaliser les échanges
	 * 
	 * @param first
	 *            le premiére joueur de l'échange
	 * @param second
	 *            le second joueur de l'échange
	 * @param est
	 *            ce que le panel doit etre affiché
	 */
	public void notify_start_trade(Player first, Player second, boolean show);

	/**
	 * Méthode permettant de notifier qu'un changement dans le propriétés
	 * proposés par l'adversaire durant un échange
	 * 
	 * @param number_property
	 *            le numéro de la propriété qui fait subir un changement
	 * @param add
	 *            est ce que la propriété est ajouté ou retiré de la liste
	 */
	public void notify_trade_change_properties(int number_property, boolean add);

	/**
	 * Méthode permettant de notifier qu'un changement d'argent proposé par
	 * l'adversaire durant un échange
	 * 
	 * @param money
	 *            la nouvelle somme d'argent
	 */
	public void notify_trade_change_money(int money);

	/**
	 * Méthode permettant de notifier de la validation ou non d'un des deux
	 * joueurs de l'échange
	 * 
	 * @param versus
	 *            est ce que le joueur qui valide est l'adversaire
	 * @param validate
	 *            vrai si validé, faux sinon
	 */
	public void notify_trade_validate(boolean versus, boolean validate);

	/**
	 * Méthode permettant de notifier de la mise en pause ou de la reprise du
	 * jeu pour les clients
	 * 
	 * @param pause
	 *            est ce qu'on met en pause ou l'on reprend
	 */
	public void notify_pause_game(boolean pause);

	/**
	 * Méthode permettant de notifier de repaint l'element SWING racine de
	 * l'application
	 */
	public void notify_repaint_root();

	/**
	 * Méthode permettant de notifier que tout les joueurs sont pret et que l'on
	 * peut commencer
	 */
	public void notify_fire_go();

	/**
	 * Méthode peremettant de notifier qu'un message a été recu via le réseau et
	 * qu'il faut l'afficher
	 * 
	 * @param message
	 *            le message a afficher
	 */
	public void notify_message_receive(String message);

	/**
	 * Méthode permettant de notifier qu'un jouer vient d'atteintre la
	 * banqueroute
	 * 
	 * @param owner
	 *            la personne qui a mis en banqueroute le joueur
	 * @param sum
	 *            la somme que le joueur doit
	 * @param debter
	 *            le pseudo du joueur qui est endetté
	 */
	public void notify_bankrupty(String owner, int sum, String debter);

	/**
	 * Méthode permettant de notifier qu'un joueur est tombé sur la carte
	 * communautaire vol et qu'il faut afficher le panel pour voler une carte
	 * 
	 * @param stealer
	 *            le joueur qui va voler
	 * @param players
	 *            les joueurs a qui on va pouvoir voler les cartes
	 * @param show
	 *            est ce qu'on doit montrer le panel ou non
	 */
	public void notify_start_panel_steal_card(Player stealer, ArrayList<Player> players, boolean show);

	/**
	 * Méthode permettant de notifier qu'on doit lancer le panel pour pouvoir se
	 * déplacer vers une gare en fin de tour
	 * 
	 * @param stations
	 *            les différentes station disponibles
	 * @param show
	 *            est ce qu'on doit afficher le panel
	 */
	public void notify_start_panel_teleport_station(ArrayList<Station_terrain> stations, boolean show);

	/**
	 * Méthode permettant de notifier qu'on lancer le panel pour pouvoir choisir
	 * le nombre de dés avant de lancer
	 * 
	 * @param show
	 *            est ce qu'on doit montrer le panel
	 */
	public void notify_start_panel_chose_nb_dice(boolean show);

}
