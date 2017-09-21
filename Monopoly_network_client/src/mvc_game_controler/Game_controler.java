package mvc_game_controler;

import java.util.ArrayList;
import java.util.Vector;

import mvc_game_enums.Action_done;
import mvc_game_enums.Buy_order;
import mvc_game_enums.Card_type;
import mvc_game_enums.Dice_manage_result;
import mvc_game_enums.Earn_card_type;
import mvc_game_enums.House_manage_result;
import mvc_game_enums.Jail_manage_result;
import mvc_game_enums.Lose_card_type;
import mvc_game_enums.Mortgage_manage_result;
import mvc_game_model.Auction;
import mvc_game_model.Dice;
import mvc_game_model.Game_model;
import mvc_game_model.Player;
import mvc_game_model.Trade;
import mvc_game_model.Trade.Trade_datas;
import mvc_game_model_cards.Abstract_card;
import mvc_game_model_cards.Earn_money_card;
import mvc_game_model_cards.Get_out_jail_card;
import mvc_game_model_cards.Go_jail_card;
import mvc_game_model_cards.Lose_money_card;
import mvc_game_model_cards.Move_card;
import mvc_game_model_cards.Steal_card;
import mvc_game_model_terrains.Abstract_terrain;
import mvc_game_model_terrains.Buyable_terrain;
import mvc_game_model_terrains.Property_terrain;
import mvc_game_model_terrains.Station_terrain;
import mvc_network_controler.Network_controler;
import settings.Setting_configuration;
import utils.Sound_player;
import view.Window;

public class Game_controler {

	private Game_model game_model;
	private Network_controler network;
	private Window window_ref;

	volatile private boolean should_roll;
	volatile private boolean should_play;
	volatile private boolean should_decide_buying;
	volatile private boolean should_take_decision_jail;
	volatile private boolean in_auction_mode;
	volatile private boolean in_trade_mode;
	volatile private boolean should_decide_teleport;
	volatile private boolean already_teleport;
	volatile private boolean in_pause;
	volatile private boolean pause_requester;
	volatile private boolean should_wait_steal_card;
	volatile private boolean should_wait_auction_paid;
	volatile private String name_payer_auction;
	volatile private boolean has_bankrupt_already;
	volatile private boolean voluntary_surrend;
	volatile private boolean wait_chose_nb_dice;
	volatile private boolean already_ask_nb_dice;

	volatile private boolean should_pay;
	volatile private int sum_debt;
	volatile private String owner_debt;

	volatile private int rent_multiplier;

	volatile private boolean waiting_paiement_all;
	volatile private ArrayList<String> pseudos_paiement_waiting;

	volatile private boolean waiting_movement;
	volatile private boolean waiting_spin;
	volatile private boolean waiting_card_showing;

	/**
	 * Constructeur de la classe représentant le controleur de notre jeu
	 * 
	 * @param model
	 *            référence sur le model, pour le pattern mvc
	 * @param network
	 *            référence sur le controleur de reseau pour pouvoir communiquer
	 * @param window_ref
	 *            référence sur la fenetre pour pouvoir demander à quitter une
	 *            partie en cas d'abandon
	 */
	public Game_controler(Game_model model, Network_controler network, Window window_ref) {
		this.game_model = model;
		this.network = network;
		this.window_ref = window_ref;
		this.should_play = false;
		this.should_roll = false;
		this.should_decide_buying = false;
		this.should_take_decision_jail = false;
		this.waiting_movement = false;
		this.waiting_spin = false;
		this.should_pay = false;
		this.sum_debt = 0;
		this.owner_debt = null;
		this.in_auction_mode = false;
		this.waiting_paiement_all = false;
		this.pseudos_paiement_waiting = null;
		this.waiting_card_showing = false;
		this.in_trade_mode = false;
		this.should_decide_teleport = false;
		this.in_pause = false;
		this.rent_multiplier = 1;
		this.pause_requester = false;
		this.should_wait_auction_paid = false;
		this.name_payer_auction = null;
		this.should_wait_steal_card = false;
		this.already_teleport = false;
		this.has_bankrupt_already = false;
		this.voluntary_surrend = false;
		this.wait_chose_nb_dice = false;
		this.already_ask_nb_dice = false;
	}

	/**
	 * Méthode permettant de dire que le joueur n'a pas fait de réelle
	 * banqueroute mais a simplement abandonner de lui même
	 */
	public void set_true_voluntary_surrend() {
		this.voluntary_surrend = true;
	}

	/**
	 * Méthode étant appelé par le client pour dire combien de dés il y a dans
	 * le plateau
	 * 
	 * @param nb
	 *            le nombre de dés
	 */
	public void chosen_nb_dice(int nb) {
		this.wait_chose_nb_dice = false;
		// this.game_model.set_nb_dice(nb);
		this.game_model.notify_start_panel_chose_nb_dice(false);

	}

	/**
	 * Méthode appelé par le client lorsque celui ci prend sa décisionq pour la
	 * téléportation vers les gares
	 * 
	 * @param number
	 *            le numéro de a gare ou l'on se déplace
	 */
	public void chosen_station_teleport(int number) {
		if (number == -1) {
			this.should_decide_teleport = false;
			game_model.notify_start_panel_teleport_station(null, false);
		} else {
			network.send_message_to_server("GAME IN_GAME TELEPORT " + network.get_pseudo_client() + " " + number);
		}
	}

	/**
	 * Méthode appelé via une communication client qui permet de recevoir la
	 * réponse du client vers ou il doit se déplacer
	 * 
	 * @param player
	 *            le pseudo du joueur qui s'est téléporté
	 * @param position
	 *            la position du joueur aprés la téléportation
	 */
	public void receive_station_teleport(String player, int position) {
		Player p = game_model.get_player_from_pseudo(player);
		p.move_to_position(position);
		game_model.notify_piece_move(player, position, true);
		if (network.get_pseudo_client().equals(game_model.get_player_from_turn().get_pseudo())) {
			this.should_decide_teleport = false;
			game_model.notify_start_panel_teleport_station(null, false);
		}
	}

	/**
	 * Méthode permettant d'envoyer depuis le client la propriété qui a été volé
	 * par le client
	 * 
	 * @param stealer
	 *            le pseudo du joueur qui vole
	 * @param stolen
	 *            le pseudo du joueur volé
	 * @param number
	 *            le numéro de la propriété volé
	 */
	public void chosen_stole(String stealer, String stolen, int number) {
		if (stealer == null) {
			network.send_message_to_server("GAME IN_GAME STOLE " + "null" + " " + "null" + " " + number);
		} else {
			network.send_message_to_server("GAME IN_GAME STOLE " + stealer + " " + stolen + " " + number);
		}
	}

	/**
	 * Méthode permettant de recevoir via une communication réseau la propriété
	 * qui a été volé par le joueur courant
	 * 
	 * @param stealer
	 *            le pseudo du joueur qui vole
	 * @param stolen
	 *            le pseudo du joueur volé
	 * @param number
	 *            le numéro de la propriété volé
	 */
	public void receive_stole(String stealer, String stolen, int number) {
		if (stealer.equals("null")) {
			this.should_wait_steal_card = false;
			if (game_model.get_player_from_turn().get_pseudo().equals(network.get_pseudo_client())) {
				game_model.notify_start_panel_steal_card(null, null, false);
			}
		} else {
			this.should_wait_steal_card = false;
			if (game_model.get_player_from_turn().get_pseudo().equals(network.get_pseudo_client())) {
				game_model.notify_start_panel_steal_card(null, null, false);
			}

			Player steal = game_model.get_player_from_pseudo(stealer);
			Player stol = game_model.get_player_from_pseudo(stolen);

			Buyable_terrain target = (Buyable_terrain) game_model.get_terrain_from_number(number);
			stol.remove_terrain_owned(target);
			game_model.notify_selled_house(stolen, number);

			steal.add_terrain_owned(target);
			game_model.notify_buyed_house(stealer, number);

			game_model.notify_player_message(stealer, "Has stolen :" + target.get_name());
			game_model.notify_player_message(stolen, "Has been stolen of : " + target.get_name());
		}
	}

	/**
	 * Méthode permettant d'envoyer depuis le client sa propre banqueroute
	 * 
	 * @param owner
	 *            le pseudo du joueur qui nous a mis en banqueroute
	 * @param sum
	 *            la somme qui a été demandé
	 * @param debter
	 *            le pseudo du joueur en banqueroute
	 */
	public void send_bankruptcy(String owner, int sum, String debter) {
		network.send_message_to_server("GAME IN_GAME BANKRUPTCY " + owner + " " + sum + " " + debter);
	}

	/**
	 * Méthode permettant de recevoir via une communication réseau la
	 * banqueroute d'un joueur
	 * 
	 * @param owner
	 *            la personne qui a endette le banqueroute
	 * @param sum
	 *            la somme de banqueroute e@param debter le pseudo du joueur qui
	 *            est en banqueroute
	 */
	public void bankruptcy_player(String owner, int sum, String debter) {
		Player p = game_model.get_player_from_pseudo(debter);

		if (debter.equals(network.get_pseudo_client()) && Setting_configuration.suicide_mod && !voluntary_surrend) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			network.send_message_to_server("GAME IN_GAME WINNER " + p.get_pseudo());
		} else {
			boolean find = false;
			for (Player player : game_model.get_players()) {
				if (player.get_pseudo().equals(owner)) {
					find = true;
				}
			}

			if (!find) {
				for (Buyable_terrain buyable : p.get_terrains_owned()) {
					buyable.set_mortgage(false);
					buyable.set_owner(null);
					if (buyable instanceof Property_terrain) {
						Property_terrain prop = (Property_terrain) buyable;
						if (prop.get_nb_house() == 5) {
							game_model.add_nb_hotel(1);
							prop.set_nb_house(0);
							game_model.notify_nb_house_property(p.get_pseudo(), prop.get_number(), 0);
						} else {
							game_model.add_nb_house(prop.get_nb_house());
							prop.set_nb_house(0);
							game_model.notify_nb_house_property(p.get_pseudo(), prop.get_number(), 0);
						}
					}
					game_model.notify_selled_house(p.get_pseudo(), buyable.get_number());
				}
				p.set_money(0);
				game_model.notify_player_money_change(p.get_pseudo(), 0);
			} else {
				for (Buyable_terrain buyable : p.get_terrains_owned()) {
					buyable.set_owner(null);
					if (buyable instanceof Property_terrain) {
						Property_terrain prop = (Property_terrain) buyable;
						if (prop.get_nb_house() == 5) {
							game_model.add_nb_hotel(1);
							prop.set_nb_house(0);
							game_model.notify_nb_house_property(p.get_pseudo(), prop.get_number(), 0);
						} else {
							game_model.add_nb_house(prop.get_nb_house());
							prop.set_nb_house(0);
							game_model.notify_nb_house_property(p.get_pseudo(), prop.get_number(), 0);
						}
					}

					game_model.notify_selled_house(p.get_pseudo(), buyable.get_number());
					game_model.get_player_from_pseudo(owner).add_terrain_owned(buyable);
					game_model.notify_buyed_house(owner, buyable.get_number());

				}
				game_model.get_player_from_pseudo(owner)
						.set_money(game_model.get_player_from_pseudo(owner).get_money() + p.get_money());
				game_model.notify_player_money_change(owner, game_model.get_player_from_pseudo(owner).get_money());
				p.set_money(0);
				game_model.notify_player_money_change(p.get_pseudo(), 0);
			}

			p.set_surrend(true);
			game_model.notify_player_surrend(p.get_pseudo());

			if (!p.get_pseudo().equals(network.get_pseudo_client())) {
				surrend_from_player(debter);
			}

			// need to give all money and all properties

			if (p.get_pseudo().equals(network.get_pseudo_client())) {
				has_bankrupt_already = true;
				if (game_model.get_player_from_turn().get_pseudo().equals(debter)) {
					network.send_message_to_server("GAME IN_GAME NEXT_PLAYER");
				}

				if (voluntary_surrend) {
					this.game_model.notify_winner("You willingly surrend ! Gonna exit !");

				} else {
					game_model.notify_winner("Bankruptcy, gonna exit");
				}
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						window_ref.close_window_and_connexion();

					}
				}).start();
			}

		}
	}

	/**
	 * Méthode permettant de dire que tout les joueurs sont pret et que l'on
	 * peut commencer
	 */
	public void fire_go() {
		game_model.notify_fire_go();
	}

	/**
	 * Méthode permettant de demander de dessiner l'element racine
	 */
	public void repaint_root() {
		game_model.notify_repaint_root();
	}

	/**
	 * Méthode permettant de récupérer le controleur de réseau
	 * 
	 * @return le controleur de reseau
	 */
	public Network_controler get_network_controler() {
		return this.network;
	}

	/**
	 * Méthode permettant de savoir si c'est au joueur représenté par le client
	 * a jouer dans le jeu
	 * 
	 * @return vrai si c'est au tour du client, faux sinon
	 */
	public boolean is_player_turn() {
		return this.network.get_pseudo_client().equals(game_model.get_player_from_turn().get_pseudo());
	}

	/**
	 * Méthode permettant de construire le plateau de jeu et ainsi de démarrer
	 * une partie lorsque celui ci est pret.
	 * 
	 * @param pseudos
	 *            les pseudos des différents joueurs qui se trouvent dans la
	 *            partie
	 */
	public void build_board(String pseudos) {
		String[] splitted_pseudos = pseudos.split(":");

		for (String str : splitted_pseudos) {
			String[] split = str.split("&!");
			if (Integer.valueOf(split[0]) == 1) {
				if (this.network.get_pseudo_client().equals(split[1])) {
					this.should_roll = true;
					this.should_play = true;
				}
			}
		}

		this.game_model.build_terrains();
		this.game_model.build_players(splitted_pseudos);
		this.game_model.build_dices();
		this.game_model.build_cards();

		this.game_model.notify_model_loaded(this.game_model.get_terrains(), this.game_model.get_players(),
				network.get_pseudo_client());

		if (Setting_configuration.random_properties_gave) {
			for (Player p : game_model.get_players()) {
				for (Buyable_terrain t : p.get_terrains_owned()) {
					game_model.notify_buyed_house(p.get_pseudo(), t.get_number());
				}
			}
		}

		this.network.send_message_to_server("GAME BUILD_BOARD OK");
	}

	// Direct action

	/**
	 * Méthode appelé par un client lorsque celui ci envoie un message au réseau
	 * pour tout les joueurs
	 * 
	 * @param message
	 *            le message que l'on veut envoyer le message a envoyer
	 */
	public void want_send_message(String message) {

		network.send_message_to_server("GAME IN_GAME MESSAGE " + network.get_pseudo_client() + " : " + message);
	}

	/**
	 * Méthode appelé via la communication lors de la réception du message par
	 * le serveur
	 * 
	 * @param message
	 *            le message que l'on a recu
	 */
	public void receive_sended_message(String message) {
		game_model.notify_message_receive(message);
	}

	/**
	 * Méthode étant appelé via le client pour demander a effectuer une pause ou
	 * une reprise du eu
	 * 
	 * @param pause
	 *            est ce qu'on veut faire une pause ou reprendre le jeu
	 * @return vrai si on peut lancer la pause / le reprendre, faux sinon
	 */
	public boolean ask_pause(boolean pause) {
		if (pause) {
			if (!should_play) {
				game_model.notify_player_message(network.get_pseudo_client(), "Wait your turn before pause");
				return false;
			} else if (in_pause) {
				game_model.notify_player_message(network.get_pseudo_client(), "Already in pause");
				return false;
			} else if (in_auction_mode) {
				game_model.notify_player_message(network.get_pseudo_client(), "In auction mode, wait before pause");
				return false;
			} else if (should_wait_auction_paid) {
				game_model.notify_player_message(network.get_pseudo_client(), "Wait rival pay auction before end");
				return false;
			} else if (should_wait_steal_card) {
				game_model.notify_player_message(network.get_pseudo_client(),
						"Should decide card to steal before pause");
				return false;
			} else if (in_trade_mode) {
				game_model.notify_player_message(network.get_pseudo_client(), "In trade mode, wait before pause");
				return false;
			} else if (should_decide_buying) {
				game_model.notify_player_message(network.get_pseudo_client(),
						"Should decide buying, wait before pause");
				return false;
			} else if (should_pay) {
				game_model.notify_player_message(network.get_pseudo_client(), "Should pay before pause");
				return false;
			} else if (should_take_decision_jail) {
				game_model.notify_player_message(network.get_pseudo_client(),
						"Should take decison about jail before pause");
				return false;
			} else if (waiting_card_showing) {
				game_model.notify_player_message(network.get_pseudo_client(), "Should wait card showing before pause");
				return false;
			} else if (waiting_movement) {
				game_model.notify_player_message(network.get_pseudo_client(), "Shoud wait piece movement before pause");
				return false;
			} else if (waiting_paiement_all) {
				game_model.notify_player_message(network.get_pseudo_client(),
						"Should wait paiement for HB before pause");
				return false;
			} else if (waiting_spin) {
				game_model.notify_player_message(network.get_pseudo_client(),
						"Should waiting dice rolling before pause");
				return false;
			} else {
				pause_requester = true;
				network.send_message_to_server("GAME IN_GAME PAUSE true");
				return true;
			}
		} else {
			if (pause_requester) {
				pause_requester = true;
				network.send_message_to_server("GAME IN_GAME PAUSE false");
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Méthode étant appelé via une communication réseau pour recevoir le fait
	 * de devoir mettre en pause ou non notre partie
	 * 
	 * @param pause
	 *            est ce qu'on a recu une pause ou un reprendre
	 */
	public void receive_pause(boolean pause) {
		in_pause = pause;
		game_model.notify_pause_game(pause);
	}

	/**
	 * Méthode étant appelé lors de l'appuie sur le bouton "Trade" cette action
	 * va déclencher l'affichage d'un panneau ou le joueur choisit avec qui il
	 * souhaite procéder à un échange
	 */
	public void want_launch_trade() {
		if (!should_play) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Not you turn");
		} else if (in_pause) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "In pause, can't start trade");
		} else if (waiting_spin) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Wait until your dices stop spinning");
		} else if (waiting_movement) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Wait until your piece stop moving");
		} else if (should_wait_auction_paid) {
			game_model.notify_player_message(network.get_pseudo_client(), "Wait rival pay auction before start trade");
		} else if (should_wait_steal_card) {
			game_model.notify_player_message(network.get_pseudo_client(), "Should decide card to steal before trade");
		} else if (wait_chose_nb_dice) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should decide nb dice before launch trade");
		} else if (waiting_paiement_all) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should wait other players to pay you before starting trade");
		} else if (should_decide_teleport) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Should take a decision about teleport");
		} else if (waiting_card_showing) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Wait card showing end before starting trade");
		} else if (in_trade_mode) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Trade is running, wait until end before trading again");
		} else if (in_auction_mode) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Auction is running, wait until end");
		} else if (should_pay) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should pay debt before end turn, if can't sell or mortgage");
		} else if (should_decide_buying) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should take a decision about buying before end turn");
		} else if (should_take_decision_jail) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should take a decision about jail before end turn");
		} else {
			this.in_trade_mode = true;
			game_model.notify_want_to_trade(game_model.get_player_from_pseudo(network.get_pseudo_client()),
					game_model.get_players(), true);
		}
	}

	/**
	 * Méthode appelé par le client lorsque celui ci a decidé avec qui il
	 * voulait réaliser son enchere. A partir de ce moment, une communication
	 * réseau est lancé pour demandé à l'autre client de passer aussi en mode
	 * echange
	 * 
	 * @param selected_pseudo
	 *            le pseudo du client avec qui l'échange va se passer
	 */
	public void launch_trade(String selected_pseudo) {
		game_model.notify_want_to_trade(null, null, false);
		network.send_message_to_server("GAME IN_GAME TRADE " + network.get_pseudo_client() + " " + selected_pseudo);
	}

	/**
	 * Méthode permettant de lancer réellement l'échange avec l'affichage du
	 * panneau
	 * 
	 * @param pseudo_1
	 *            le pseudo du premier joueur pour l'affichage
	 * @param pseudo_2
	 *            le pseudo du deuxiéme joueur pour l'affichage
	 */
	public void receive_trade_wanted(String pseudo_1, String pseudo_2) {
		if (network.get_pseudo_client().equals(pseudo_1)) {
			Player first = game_model.get_player_from_pseudo(pseudo_1);
			Player second = game_model.get_player_from_pseudo(pseudo_2);
			this.in_trade_mode = true;
			game_model.start_trade(pseudo_1, pseudo_2);
			game_model.notify_start_trade(first, second, true);
		} else if (network.get_pseudo_client().equals(pseudo_2)) {
			Player first = game_model.get_player_from_pseudo(pseudo_2);
			Player second = game_model.get_player_from_pseudo(pseudo_1);
			this.in_trade_mode = true;
			game_model.start_trade(pseudo_1, pseudo_2);
			game_model.notify_start_trade(first, second, true);
		}
	}

	/**
	 * Méthode étant appelé dans le client et qui permet d'ajouter une propriété
	 * a la lsite que l'on souhaite céder à l'adversaire
	 * 
	 * @param property_number
	 *            le numéro de la maison
	 * @param add
	 *            est ce que l'on doit ajouter ou retirer la propriété
	 * @return vrai si on a pu mettre la propriété dans la liste, faux sinon
	 */
	public boolean trade_house_adding(int property_number, boolean add) {

		game_model.notify_trade_validate(false, false);
		game_model.notify_trade_validate(true, false);
		game_model.get_trade().nobody_ready();

		if (!add) {
			network.send_message_to_server(
					"GAME IN_GAME TRADE_PROP " + network.get_pseudo_client() + " " + property_number + " " + "false");
			game_model.get_trade().remove_property(network.get_pseudo_client(), property_number);
			return true;
		} else {
			Abstract_terrain terrain = game_model.get_terrain_from_number(property_number);
			if (terrain instanceof Property_terrain) {
				Property_terrain prop = (Property_terrain) terrain;
				if (prop.get_nb_house() > 0) {
					this.game_model.notify_player_message(network.get_pseudo_client(),
							"There is an house on this terrain, you can't trade it !");
					return false;
				} else if (!game_model.no_house_on_color(network.get_pseudo_client(), prop.get_color())) {
					this.game_model.notify_player_message(network.get_pseudo_client(),
							"There is some houses on color terrains, you can't trade it !");
					return false;
				} else {
					network.send_message_to_server("GAME IN_GAME TRADE_PROP " + network.get_pseudo_client() + " "
							+ property_number + " " + "true");
					game_model.get_trade().add_property(network.get_pseudo_client(), property_number);
					return true;
				}
			} else {
				return true;
			}
		}
	}

	/**
	 * Méthode étant appelé via une communication réseau et qui permet de savoir
	 * quel propriété a été ajouté / retiré par l'adversaire pour conclure
	 * l'échange
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a réaliser la modification
	 * @param property_number
	 *            le numéro de la propriété
	 * @param add
	 *            est ce que la propriété a été ajouté ou retiré
	 */
	public void receive_house_adding(String pseudo, int property_number, boolean add) {
		if (in_trade_mode) {

			game_model.notify_trade_change_properties(property_number, add);
			Trade trade_ref = game_model.get_trade();
			game_model.notify_trade_validate(false, false);
			game_model.notify_trade_validate(true, false);
			trade_ref.nobody_ready();

			if (trade_ref != null) {
				if (add) {
					trade_ref.add_property(pseudo, property_number);
				} else {
					trade_ref.remove_property(pseudo, property_number);
				}
			}
		}
	}

	/**
	 * Méthode étant appelé chez le client lorsque celui ci veut mettre une
	 * somme d'argent dans l'échange
	 * 
	 * @param money
	 *            la somme voulu par le client
	 * @return si la somme est conforme et que le jouer a assez d'argent vrai,
	 *         sinn faux
	 */
	public boolean trade_money_change(String money) {
		game_model.notify_trade_validate(false, false);
		game_model.get_trade().nobody_ready();
		Player p = game_model.get_player_from_pseudo(network.get_pseudo_client());
		if (String_is_digit(money) && money.length() <= 8 && p.get_money() >= Integer.valueOf(money)) {
			game_model.get_trade().set_money_value(network.get_pseudo_client(), Integer.valueOf(money));
			game_model.notify_trade_validate(false, false);
			game_model.notify_trade_validate(true, false);
			network.send_message_to_server("GAME IN_GAME TRADE_MONEY " + network.get_pseudo_client() + " " + money);
			return true;
		} else {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Incorrect money format or not enough money !");
			return false;
		}
	}

	/**
	 * Méthode étant appelé via une communication réseau pour préciser que
	 * l'argent qu'un joueur propose durant l'échange a changer
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a changer sa somme
	 * @param money
	 *            la somme proposé
	 */
	public void receive_money_change(String pseudo, int money) {
		if (in_trade_mode) {
			game_model.notify_trade_validate(false, false);
			game_model.notify_trade_validate(true, false);
			game_model.get_trade().nobody_ready();
			game_model.notify_trade_change_money(money);
			Trade trade_ref = game_model.get_trade();
			if (trade_ref != null) {
				trade_ref.set_money_value(pseudo, money);
			}
		}
	}

	/**
	 * Méthode permettant d'annuler l'échange chez le client
	 */
	public void cancel_trade() {
		network.send_message_to_server("GAME IN_GAME TRADE_CANCEL");
	}

	/**
	 * Méthode appelé via une communication réseau pour avertir de l'annulation
	 * de l'enchere
	 */
	public void receive_cancel_trade() {
		if (in_trade_mode) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Trade cancelled");
			this.in_trade_mode = false;
			this.game_model.end_trade();
			this.game_model.notify_start_trade(null, null, false);
		}
	}

	/**
	 * Méthode permettant de valider l'échange chez le client
	 */
	public void validate_trade() {
		game_model.get_trade().set_want_end(network.get_pseudo_client(), true);
		game_model.notify_trade_validate(false, true);
		network.send_message_to_server("GAME IN_GAME TRADE_VALIDATE " + network.get_pseudo_client() + " true");
	}

	/**
	 * Méthode appelé via une communication réseau pour avertir de la validation
	 * d'un joueur de l'encherre
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui est pret a valider l'echange
	 */
	public void receive_validate_trade(String pseudo, boolean ready) {
		if (in_trade_mode) {
			game_model.get_trade().set_want_end(pseudo, ready);
			game_model.notify_trade_validate(true, true);
			if (game_model.get_trade().can_stop_trade()) {
				network.send_message_to_server("GAME IN_GAME TRADE_END");
			}
		}
	}

	/**
	 * Méthode étant appelé via une communication réseau pour avertir les 2
	 * joueurs qui sont en échange que l'échange est terminé
	 */
	public void receive_trade_end() {
		if (in_trade_mode) {
			Trade t = game_model.get_trade();

			Trade_datas f_datas = t.get_first();
			Player first = game_model.get_player_from_pseudo(f_datas.pseudo);

			Trade_datas s_datas = t.get_second();
			Player second = game_model.get_player_from_pseudo(s_datas.pseudo);

			if (f_datas.money == 0 && s_datas.money == 0 && f_datas.buyable_number.size() == 0
					&& s_datas.buyable_number.size() == 0) {
				game_model.notify_player_message(first.get_pseudo(), "Trade without element, canceled");
				game_model.notify_player_message(second.get_pseudo(), "Trade without element, canceled");
				game_model.notify_start_trade(null, null, false);
				in_trade_mode = false;
				return;
			} else {

				first.set_money(first.get_money() - f_datas.money);
				game_model.notify_player_money_change(first.get_pseudo(), first.get_money());

				for (Integer i : f_datas.buyable_number) {
					Buyable_terrain target = (Buyable_terrain) game_model.get_terrain_from_number(i);
					boolean mortgage = target.is_mortgage();
					first.remove_terrain_owned(target);
					second.add_terrain_owned(target);
					target.set_mortgage(mortgage);

					game_model.notify_selled_house(first.get_pseudo(), i);
					game_model.notify_buyed_house(second.get_pseudo(), i);
					game_model.notify_mortgage_house(second.get_pseudo(), i, mortgage);
				}

				second.set_money(second.get_money() - s_datas.money);
				game_model.notify_player_money_change(second.get_pseudo(), second.get_money());

				for (Integer i : s_datas.buyable_number) {
					Buyable_terrain target = (Buyable_terrain) game_model.get_terrain_from_number(i);
					boolean mortgage = target.is_mortgage();
					second.remove_terrain_owned(target);
					first.add_terrain_owned(target);
					target.set_mortgage(mortgage);

					game_model.notify_selled_house(second.get_pseudo(), i);
					game_model.notify_buyed_house(first.get_pseudo(), i);
					game_model.notify_mortgage_house(first.get_pseudo(), i, mortgage);
				}

				game_model.notify_player_message(first.get_pseudo(), "Trade executed");
				game_model.notify_player_message(second.get_pseudo(), "Trade executed");

				game_model.notify_start_trade(null, null, false);
				in_trade_mode = false;
			}
		}
	}

	public void rent_up() {
		this.rent_multiplier++;
	}

	/**
	 * Méthode permettant de passer au joueur suivant du coté client lorsque
	 * celui ci clique sur 'end turn'
	 */
	public void next_player() {
		if (!should_play) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Not you turn");
		} else if (in_pause) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "In pause, can't end turn");
		} else if (waiting_spin) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Wait until your dices stop spinning");
		} else if (should_wait_steal_card) {
			game_model.notify_player_message(network.get_pseudo_client(),
					"Should decide card to steal before end turn");
		} else if (waiting_movement) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Wait until your piece stop moving");
		} else if (wait_chose_nb_dice) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Should decide nb dice before end turn");
		} else if (should_wait_auction_paid) {
			game_model.notify_player_message(network.get_pseudo_client(), "Wait rival pay auction before end turn");
		} else if (waiting_paiement_all) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should wait other players to pay you before end turn");
		} else if (should_decide_teleport) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should take a decision about teleport before end turn");
		} else if (waiting_card_showing) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Wait card showing end before end turn");
		} else if (in_trade_mode) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Trade is running, wait until end before end turn");
		} else if (in_auction_mode) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Auction is running, wait until end before end turn");
		} else if (should_pay) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should pay debt before end turn, if can't sell or mortgage");
		} else if (should_roll) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Should roll your dice before end turn");
		} else if (should_decide_buying) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should take a decision about buying before end turn");
		} else if (should_take_decision_jail) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should take a decision about jail before end turn");
		} else {
			Player target = game_model.get_player_from_turn();
			Abstract_terrain t = game_model.get_terrain_from_number(target.get_position());
			if (t instanceof Station_terrain && Setting_configuration.teleportation_station && !already_teleport) {
				this.game_model.notify_player_message(network.get_pseudo_client(),
						"Should take a decision about teleportation");
				ArrayList<Station_terrain> stations = new ArrayList<Station_terrain>();
				for (Abstract_terrain terrain : game_model.get_terrains()) {
					if (terrain instanceof Station_terrain && terrain.get_number() != target.get_position()) {
						stations.add((Station_terrain) terrain);
					}
				}
				this.should_decide_teleport = true;
				this.already_teleport = true;
				game_model.notify_start_panel_teleport_station(stations, true);

			} else {
				this.should_roll = false;
				this.should_play = false;
				this.should_decide_buying = false;
				this.already_teleport = false;
				this.already_ask_nb_dice = false;
				this.network.send_message_to_server("GAME IN_GAME NEXT_PLAYER");
			}
		}
	}

	/**
	 * Méthode permettant d'ajouter une maison a une proprité en faisant les
	 * différents tests pour savoir si ceci est possible
	 * 
	 * @param number
	 *            le numéro de la maison a laquelle on souhaite ajouter une
	 *            maison
	 */
	public void add_house(int number) {
		if (!in_trade_mode && !in_auction_mode && !in_pause && !should_wait_steal_card) {
			House_manage_result res = game_model.add_house_on_terrain(network.get_pseudo_client(), number);
			switch (res) {
			case MORTGAGE_COLOR:
				this.game_model.notify_player_message(network.get_pseudo_client(),
						"There is mortgage on some same color terrains");
				break;
			case NOT_ENOUGH_HOTEL:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Not enough hotel in bank");
				break;
			case NOT_ENOUGH_HOUSE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Not enough house in bank");
				break;
			case IS_MORTGAGE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "This property is mortgage");
				break;
			case FULL_HOUSE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Full house in terrain");
				break;
			case NOT_ALL_HOUSES:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Don't own all houses");
				break;
			case NOT_AN_HOUSE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "This property is not an house");
				break;
			case NOT_ENOUGH_MONEY:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Not enought money to buy houes");
				break;
			case NOT_EQUALIZE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "You don't equalize houses");
				break;
			case OK:
				this.game_model.notify_nb_house_property(network.get_pseudo_client(), number,
						((Property_terrain) (game_model.get_terrain_from_number(number))).get_nb_house());
				this.game_model.notify_player_money_change(network.get_pseudo_client(),
						game_model.get_player_from_pseudo(network.get_pseudo_client()).get_money());
				network.send_message_to_server("GAME IN_GAME ADD_HOUSE " + network.get_pseudo_client() + " " + number
						+ " " + ((Property_terrain) (game_model.get_terrain_from_number(number))).get_nb_house());
				this.game_model.notify_player_message(network.get_pseudo_client(),
						"Has add house to : " + game_model.get_terrain_name_from_number(number));
				break;
			default:
				break;
			}
		} else {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Not your turn");
		}
	}

	/**
	 * Méthode permettant de supprimer une maison en faisant les différents
	 * tests pour savoir si on peut supprimer les maisons
	 * 
	 * @param number
	 *            le numéro de la propriété sur laquelle on veut enlever des
	 *            maisons
	 */
	public void remove_house(int number) {
		if (!in_trade_mode && !in_auction_mode && !in_pause && !should_wait_steal_card) {
			House_manage_result res = game_model.remove_house_on_terrain(network.get_pseudo_client(), number);
			switch (res) {
			case NOT_ENOUGH_HOUSE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Not enough house in bank");
				break;
			case IS_MORTGAGE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "This property is mortgage");
				break;
			case NOT_AN_HOUSE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "This property is not an house");
				break;
			case NOT_EQUALIZE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "You don't equalize houses");
				break;
			case NO_HOUSE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "There is no house on this terrain");
				break;
			case OK:
				this.game_model.notify_nb_house_property(network.get_pseudo_client(), number,
						((Property_terrain) (game_model.get_terrain_from_number(number))).get_nb_house());
				this.game_model.notify_player_money_change(network.get_pseudo_client(),
						game_model.get_player_from_pseudo(network.get_pseudo_client()).get_money());
				network.send_message_to_server("GAME IN_GAME ADD_HOUSE " + network.get_pseudo_client() + " " + number
						+ " " + ((Property_terrain) (game_model.get_terrain_from_number(number))).get_nb_house());
				this.game_model.notify_player_message(network.get_pseudo_client(),
						"Has remove house from : " + game_model.get_terrain_name_from_number(number));
				break;
			default:
				break;

			}
		} else {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Not your turn");
		}
	}

	/**
	 * Méthode permettant a un client de mettre une propriété en hypotheque sous
	 * certaines conditions qui sont tester dans la tentative. Cette fonctionv a
	 * regarder le resultat et agir en conséquence sur l'application et sur
	 * l'envoi de messag en reseau
	 * 
	 * @param number
	 *            le numéro de la propriété que l'on veut hypothequer
	 */
	public void mortgage(int number) {
		if (!in_trade_mode && !in_auction_mode && !in_pause && !should_wait_steal_card) {
			Mortgage_manage_result res = this.game_model.mortgage_from_number(network.get_pseudo_client(), number);
			switch (res) {
			case ALREADY_MORTGAGE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Terrain already mortgage");
				break;
			case HOUSE_ON:
				this.game_model.notify_player_message(network.get_pseudo_client(),
						"There is house on terrain, sell it first");
				break;
			case HOUSE_ON_COLOR:
				this.game_model.notify_player_message(network.get_pseudo_client(),
						"There is some house on color terrains, sell them first");
				break;
			case NOT_BUYABLE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Not buyable");
				break;
			case OK:
				this.game_model.notify_mortgage_house(network.get_pseudo_client(), number, true);
				this.game_model.notify_player_money_change(network.get_pseudo_client(),
						game_model.get_player_from_pseudo(network.get_pseudo_client()).get_money());
				this.game_model.notify_player_message(network.get_pseudo_client(),
						"Has mortgage : " + game_model.get_terrain_name_from_number(number));
				this.network.send_message_to_server("GAME IN_GAME MORTGAGE " + network.get_pseudo_client() + " "
						+ number + " " + game_model.get_player_from_pseudo(network.get_pseudo_client()).get_money());
				break;
			default:
				break;

			}

		} else {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Not your turn");
		}
	}

	/**
	 * Méthode permettant a un client de mettre une propriété en déhypotheque
	 * sous certaines conditions qui sont tester dans la tentative. Cette
	 * fonctionv a regarder le resultat et agir en conséquence sur l'application
	 * et sur l'envoi de messag en reseau
	 * 
	 * @param number
	 *            le numéro de la propriété que l'on veut déhypothequer
	 */
	public void unmortgage(int number) {

		if (!in_trade_mode && !in_auction_mode && !in_pause && !should_wait_steal_card) {
			Mortgage_manage_result res = this.game_model.unmortgage_from_number(network.get_pseudo_client(), number);
			switch (res) {
			case ALREADY_UNMORTGAGE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Terrain already unmortgage");
				break;
			case ERROR:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Unknow error");
				break;
			case NOT_BUYABLE:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Terrain not buyable");
				break;
			case NOT_ENOUGH_MONEY:
				this.game_model.notify_player_message(network.get_pseudo_client(), "Not enough money to unmortgage");
				break;
			case OK:
				this.game_model.notify_mortgage_house(network.get_pseudo_client(), number, false);
				this.game_model.notify_player_money_change(network.get_pseudo_client(),
						game_model.get_player_from_pseudo(network.get_pseudo_client()).get_money());
				this.game_model.notify_player_message(network.get_pseudo_client(),
						"Has unmortgage : " + game_model.get_terrain_name_from_number(number));
				this.network.send_message_to_server("GAME IN_GAME UNMORTGAGE " + network.get_pseudo_client() + " "
						+ number + " " + game_model.get_player_from_pseudo(network.get_pseudo_client()).get_money());
				break;
			default:
				break;
			}
		} else {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Not your turn");
		}
	}

	/**
	 * Méthode permettant au client qui doit décider du type d'action en étant
	 * en prison de donner sa réponse
	 * 
	 * @param pseudo
	 *            le pseudo du client
	 * @param res
	 *            le résultat du client
	 */
	public void answer_manage_jailing(String pseudo, Jail_manage_result res) {
		if (pseudo.equals(game_model.get_player_from_turn().get_pseudo())) {
			this.should_take_decision_jail = false;
			switch (res) {
			case PAY:
				int money_player = game_model.get_player_from_turn().get_money();
				if (money_player >= Setting_configuration.sum_to_out_jail) {
					this.game_model.get_player_from_turn().set_in_jail(false);
					this.game_model.notify_player_jail(pseudo, false);
					this.game_model.notify_need_manage_jail(pseudo, false);
					this.should_roll = true;
					this.network.send_message_to_server("GAME IN_GAME JAIL_OUT " + pseudo);
				} else {
					this.game_model.notify_player_message(pseudo, "Not enough money to get out");
				}
				break;
			case TRY_ROLLING:
				this.should_roll = true;
				this.game_model.notify_need_manage_jail(pseudo, false);
				this.game_model.notify_player_message(pseudo, "Should roll dices");
				break;
			case USE_CARD:
				int nb_card_player = game_model.get_player_from_turn().get_nb_card_out_jail();
				if (nb_card_player > 0) {
					this.game_model.get_player_from_turn().set_in_jail(false);
					this.game_model.notify_player_jail(pseudo, false);
					this.game_model.notify_need_manage_jail(pseudo, false);
					this.should_roll = true;
					this.network.send_message_to_server("GAME IN_GAME JAIL_OUT " + pseudo);
				} else {
					this.game_model.notify_player_message(pseudo, "No such card to get out !");
				}
				break;
			default:

				break;
			}
		}
	}

	/**
	 * Méthode permettant de vendre une propriété qui apparaitent au joueur
	 * 
	 * @param number
	 *            le numéro de la propriété que l'on veut vendre
	 */
	public void sell_property(int number) {
		if (!in_auction_mode && !in_trade_mode && !in_pause && !should_wait_steal_card) {

			if (game_model.sell_property_from_pseudo_to_position(network.get_pseudo_client(), number)) {
				game_model.notify_player_message(network.get_pseudo_client(),
						"Has sell property : " + game_model.get_terrain_name_from_number(number));
				game_model.notify_player_money_change(network.get_pseudo_client(),
						game_model.get_player_from_pseudo(network.get_pseudo_client()).get_money());
				game_model.notify_selled_house(network.get_pseudo_client(), number);
				network.send_message_to_server(
						"GAME IN_GAME REMOVE_PROP " + network.get_pseudo_client() + " " + number);
			} else {
				game_model.notify_player_message(network.get_pseudo_client(),
						"Can't sell property, check if this is your house and that there is not house on or property is mortgage");

			}

		}
	}

	/**
	 * Méthode permettant au client qui doit décider d'acheter ou non une maison
	 * de donner sa réponse
	 * 
	 * @param order
	 *            l'ordre d'achat ou de refus du client
	 */
	public void answer_buy_property(Buy_order order) {
		if (should_decide_buying) {
			if (order == Buy_order.BUY) {
				boolean res = this.game_model.buy_terrain_current_player();
				if (res) {
					this.should_decide_buying = false;
					this.game_model.notify_buyed_house(game_model.get_player_from_turn().get_pseudo(),
							game_model.get_player_from_turn().get_position());
					this.game_model.notify_player_money_change(game_model.get_player_from_turn().get_pseudo(),
							game_model.get_player_from_turn().get_money());
					this.game_model.notify_player_message(game_model.get_player_from_turn().get_pseudo(),
							"Has bought property : " + game_model
									.get_terrain_name_from_number(game_model.get_player_from_turn().get_position()));
					this.network.send_message_to_server(
							"GAME IN_GAME PLAYER_BUY " + game_model.get_player_from_turn().get_pseudo() + " ACCEPT");
				} else {
					this.game_model.notify_player_message(network.get_pseudo_client(), "Not enough money to buy");
				}
			} else {
				this.should_decide_buying = false;
				this.game_model.notify_refused_buy_house();
				this.game_model.notify_player_message(game_model.get_player_from_turn().get_pseudo(),
						"Has refuse to bought property : " + game_model
								.get_terrain_name_from_number(game_model.get_player_from_turn().get_position()));
				this.network.send_message_to_server(
						"GAME IN_GAME PLAYER_BUY " + game_model.get_player_from_turn().get_pseudo() + " REFUSE");

				if (Setting_configuration.auction_allow) {
					this.in_auction_mode = true;
					try {
						Thread.sleep(100);
						this.network.send_message_to_server(
								"GAME IN_GAME AUCTION_START " + game_model.get_player_from_turn().get_position());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Don't need to take a buying decision");
		}
	}

	private boolean String_is_digit(String str) {
		if (str.length() == 0) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Méthode étant appelé par le client pour envoyer une somme d'argent pour
	 * la valeur d'enchere
	 * 
	 * @param value
	 *            la valeur d'enchere
	 */
	public void send_auction_value(String value) {
		if (String_is_digit(value)) {
			int value_transform = Integer.valueOf(value);
			Player p = game_model.get_player_from_pseudo(network.get_pseudo_client());
			if (p.get_capital() >= value_transform) {
				network.send_message_to_server("GAME IN_GAME AUCTION_VALUE " + p.get_pseudo() + " " + value_transform);
			} else {
				this.game_model.notify_player_message(network.get_pseudo_client(), "Not enough money for auction this");
			}
		} else {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Wrong auction format, use positive digit only !");
		}
	}

	/**
	 * Méthode étant appelé via une communication réseau et qui permet de lancer
	 * une enchere via le numéro de propriété
	 * 
	 * @param number
	 *            le numéro de la propriété au enchere
	 */
	public void launch_auction(int number) {
		this.in_auction_mode = true;
		this.game_model.start_auction(number);
		this.game_model.notify_auction_start(game_model.get_players(), number, true);
	}

	/**
	 * Méthode étant appelé via une communication réseau pour dire aux joueurs
	 * que l'enchere est terminé
	 */
	public void end_auction() {

		Auction auction = game_model.get_auction();
		Player winner = auction.get_player_max_auction();
		Buyable_terrain auction_terrain = auction.get_terrain();
		if (winner != null && !winner.get_surrend()) {
			winner.add_terrain_owned(auction_terrain);
			game_model.notify_buyed_house(winner.get_pseudo(), auction_terrain.get_number());

			this.in_auction_mode = false;
			this.game_model.notify_auction_start(null, -1, false);

			if (network.get_pseudo_client().equals(winner.get_pseudo())) {
				should_pay = true;
				owner_debt = "Bank";
				sum_debt = auction.get_max_auction();
				game_model.notify_need_pay_rent(owner_debt, sum_debt, true, winner.get_pseudo());
			}
			should_wait_auction_paid = true;
			name_payer_auction = winner.get_pseudo();

		} else {
			this.in_auction_mode = false;
			this.game_model.notify_auction_start(null, -1, false);
		}
	}

	public void change_auction_value_for_player(String pseudo, int value) {
		this.game_model.change_auction_player(pseudo, value);
		this.game_model.notify_auction_value(pseudo, value);
	}

	/**
	 * Méthode pouvant etre appelé à n'importe quel moment permettant à un
	 * client d'abandonner la partie
	 */
	public void surrend() {
		if (should_pay) {
			send_bankruptcy(owner_debt, sum_debt, network.get_pseudo_client());
		} else {
			send_bankruptcy("BANK", 0, network.get_pseudo_client());
		}
	}

	/**
	 * Méthode permttant de faire rouler les dés à certaines conditions comme le
	 * fait de pouvoir les faire rouler, de ne pas les avoir encore fait rouler,
	 * etc...
	 */
	public void roll_dice() {
		if (!should_play) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Not you turn");
		} else if (in_pause) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "In pause, can't roll dices");
		} else if (!should_roll) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Already rolled");
		} else if (should_wait_auction_paid) {
			game_model.notify_player_message(network.get_pseudo_client(),
					"Wait rival pay auction before rolling dice again");
		} else if (should_decide_buying) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should take buying decision before rolling");
		} else if (should_wait_steal_card) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Should steal card before rolling dice");
		} else if (wait_chose_nb_dice) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should decide nb dice before rolling dice");
		} else if (should_decide_teleport) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should take a decision about teleport before rolling dice");
		} else if (in_trade_mode) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Trade is running, wait before rolling dice");
		} else if (in_auction_mode) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Auction is running, wait before rolling dice");
		} else if (waiting_card_showing) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Wait card showing end before rolling dice");
		} else if (waiting_paiement_all) {
			this.game_model.notify_player_message(network.get_pseudo_client(),
					"Should wait other players to pay you before rolling dice");
		} else if (should_pay) {
			this.game_model.notify_player_message(network.get_pseudo_client(), "Should pay due before rolling");
		} else {

			Player target = game_model.get_player_from_turn();
			Abstract_terrain t = game_model.get_terrain_from_number(target.get_position());
			if (t instanceof Station_terrain && Setting_configuration.teleportation_station && !already_teleport) {
				this.game_model.notify_player_message(network.get_pseudo_client(),
						"Should take a decision about teleportation");
				ArrayList<Station_terrain> stations = new ArrayList<Station_terrain>();
				for (Abstract_terrain terrain : game_model.get_terrains()) {
					if (terrain instanceof Station_terrain && terrain.get_number() != target.get_position()) {
						stations.add((Station_terrain) terrain);
					}
				}
				this.should_decide_teleport = true;
				this.already_teleport = true;
				game_model.notify_start_panel_teleport_station(stations, true);

			} else if (Setting_configuration.ask_nb_dices_turn && !already_ask_nb_dice) {
				already_ask_nb_dice = true;
				wait_chose_nb_dice = true;

				game_model.notify_start_panel_chose_nb_dice(true);
				// notify_

			} else {

				this.waiting_spin = true;
				this.should_roll = false;
				this.network.send_message_to_server("GAME IN_GAME DICE_ROLLING");
				this.game_model.roll_dices();

				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(2000);

							String str = "GAME IN_GAME DICE_VALUES ";
							for (Dice dice : game_model.get_dices()) {
								str += dice.get_value() + " ";
							}
							network.send_message_to_server(str);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	}

	/**
	 * Méthode étant appelé par le client lorsque celui ci appuie sur le bouton
	 * pour payer la taxe ou le loyer qu'il doit a un joueur
	 */
	public void pay_due() {
		Player p = game_model.get_player_from_pseudo(network.get_pseudo_client());
		if (p.get_money() >= sum_debt) {
			network.send_message_to_server("GAME IN_GAME PAY " + p.get_pseudo() + " " + owner_debt + " " + sum_debt);
		}
	}

	///////////////////
	// Indirect action
	///////////////////

	/**
	 * Méthode permettant de lancer l'animtion de roulement des dés
	 */
	public void launch_animation_dice() {
		this.game_model.notify_animation_dice(true);
		Sound_player sound_player = new Sound_player("/sounds/dice.wav", false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				sound_player.play();
			}
		}).start();
	}

	/**
	 * Méthode étant appelé via une communication réseau pour communiquer un
	 * transfert entre différent joueurs
	 * 
	 * @param giver
	 *            le pseudo du joueur qui donne de l'argent
	 * @param getter
	 *            le pseudo du joueur qui prend de l'argent
	 * @param sum
	 *            la somme d'argent versée
	 */
	public void receive_pay_due(String giver, String getter, int sum) {
		if (getter.equals("Bank") || getter.equals("Jail_rent") || getter.equals("Jail_out")) {
			Player give = game_model.get_player_from_pseudo(giver);
			give.set_money(give.get_money() - sum);
			game_model.notify_player_money_change(giver, give.get_money());
			if (Setting_configuration.earn_money_on_parking) {
				game_model.set_parking_sum(game_model.get_parking_sum() + sum);
			}

			if (getter.equals("Bank")) {
				game_model.notify_player_message(giver, "Has pay tax to bank : M" + sum);
			} else if (getter.equals("Jail_rent") || getter.equals("Jail_out")) {
				game_model.notify_player_message(giver, "Has pay rent tax to jail : M" + sum);
			}

			if (getter.equals("Jail_out")) {
				game_model.get_player_from_pseudo(giver).set_in_jail(false);

				game_model.notify_player_jail(game_model.get_player_from_pseudo(giver).get_pseudo(), false);
				game_model.notify_player_message(game_model.get_player_from_turn().get_pseudo(),
						"Has paid M" + Setting_configuration.sum_to_out_jail + " by getting out of jail");
				if (game_model.get_player_from_turn().get_pseudo().equals(network.get_pseudo_client())) {
					this.should_take_decision_jail = false;
					this.should_roll = true;
					this.should_play = true;
					this.should_decide_buying = false;
				}
			}

			if (network.get_pseudo_client().equals(giver)) {
				this.should_pay = false;
				this.sum_debt = -1;
				this.owner_debt = null;
				game_model.notify_need_pay_rent(owner_debt, sum_debt, false, giver);
			}
			if (should_wait_auction_paid) {
				should_wait_auction_paid = false;
				name_payer_auction = null;
			}

		} else {
			Player give = game_model.get_player_from_pseudo(giver);
			Player get = game_model.get_player_from_pseudo(getter);

			give.set_money(give.get_money() - sum);
			get.set_money(get.get_money() + sum);

			game_model.notify_player_money_change(giver, give.get_money());
			game_model.notify_player_money_change(getter, get.get_money());
			game_model.notify_player_message(giver, "Has pay rent to " + getter + " : M" + sum);
			game_model.notify_player_message(getter, "Has been pay by " + giver + " : M" + sum);

			if (network.get_pseudo_client().equals(giver)) {
				this.should_pay = false;
				this.sum_debt = -1;
				this.owner_debt = null;
				game_model.notify_need_pay_rent(owner_debt, sum_debt, false, giver);
			}

			if (game_model.get_player_from_turn().get_pseudo().equals(network.get_pseudo_client())) {
				if (waiting_paiement_all) {
					pseudos_paiement_waiting.remove(giver);
					if (pseudos_paiement_waiting.size() == 0) {
						waiting_paiement_all = false;
					}
				}
			}
		}

	}

	/**
	 * Méthode permettant de changer le nombre de maison qui se trovue sur un
	 * terrain. Cette méthode est appelé via une communication réseau d'un autre
	 * client
	 * 
	 * @param pseudo
	 *            le pseudo du client qui a changer le nombre d'une de ces
	 *            propriétés
	 * @param number
	 *            le numéro de la propriété qui a subit un changement de maison
	 * @param nb_house
	 *            le nombre de maison sur cette propriété
	 */
	public void change_nb_house_on_terrain(String pseudo, int number, int nb_house) {
		int previous_nb_house = ((Property_terrain) (game_model.get_terrain_from_number(number))).get_nb_house();
		if (previous_nb_house > nb_house) {
			game_model.remove_house_on_terrain(game_model.get_player_from_pseudo(pseudo).get_pseudo(), number);
			this.game_model.notify_player_message(pseudo,
					"Has remove house from : " + game_model.get_terrain_name_from_number(number));
		} else {
			game_model.add_house_on_terrain(game_model.get_player_from_pseudo(pseudo).get_pseudo(), number);

			this.game_model.notify_player_message(pseudo,
					"Has add house to : " + game_model.get_terrain_name_from_number(number));
		}
		this.game_model.notify_nb_house_property(pseudo, number, nb_house);
		this.game_model.notify_player_money_change(game_model.get_player_from_turn().get_pseudo(),
				game_model.get_player_from_turn().get_money());
	}

	/**
	 * Méthode étant appelé via une commeication réseau et qui ermet de notifier
	 * qu'un client a mis en hypothéque un terrain
	 * 
	 * @param pseudo
	 *            le pseudo du client qui a hypothéqué
	 * @param number
	 *            le numéro de la propriété qui a été hypothéqué
	 * @param money
	 *            la somme d'argent du joueur aprés l'hypotheque
	 */
	public void player_mortgage(String pseudo, int number, int money) {
		Player p = game_model.get_player_from_pseudo(pseudo);
		String name = p.get_pseudo();

		this.game_model.mortgage_from_number(name, number);
		p.set_money(money);

		this.game_model.notify_player_message(name,
				"Has mortgage : " + game_model.get_terrain_name_from_number(number));
		this.game_model.notify_mortgage_house(name, number, true);
		this.game_model.notify_player_money_change(name, p.get_money());
	}

	/**
	 * Méthode étant appelé via une commeication réseau et qui ermet de notifier
	 * qu'un client a mis en déhypotheque un terrain
	 * 
	 * @param pseudo
	 *            le pseudo du client qui a déhypothéqué
	 * @param number
	 *            le numéro de la propriété qui a été déhypothequé
	 * @param money
	 *            la somme d'argent du joueur aprés la déhypothque
	 */
	public void player_unmortgage(String pseudo, int number, int money) {
		Player p = game_model.get_player_from_pseudo(pseudo);
		String name = p.get_pseudo();

		this.game_model.unmortgage_from_number(name, number);
		p.set_money(money);

		this.game_model.notify_player_message(name,
				"Has unmortgage : " + game_model.get_terrain_name_from_number(number));
		this.game_model.notify_mortgage_house(name, number, false);
		this.game_model.notify_player_money_change(name, p.get_money());
	}

	/**
	 * Méthode étant appelé en réseau qui permet de notifier de l'achat ou du
	 * refus d'achat de la propriété d'un client
	 * 
	 * @param pseudo
	 *            le pseudo du client qui a accepter / refusé l'achat
	 * @param answer
	 *            la réponse sous forme de string du client
	 */
	public void play_respond_buying(String pseudo, String answer) {
		if (answer.equals("ACCEPT")) {
			this.game_model.buy_terrain_player_from_pseudo(pseudo);
			this.game_model.notify_buyed_house(pseudo, game_model.get_player_from_pseudo(pseudo).get_position());
			this.game_model.notify_player_money_change(game_model.get_player_from_pseudo(pseudo).get_pseudo(),
					game_model.get_player_from_pseudo(pseudo).get_money());
			this.game_model.notify_player_message(pseudo, "Has bought property : " + game_model
					.get_terrain_name_from_number(game_model.get_player_from_pseudo(pseudo).get_position()));
		} else {
			this.game_model.notify_player_message(pseudo, "Has refuse to bought property : " + game_model
					.get_terrain_name_from_number(game_model.get_player_from_pseudo(pseudo).get_position()));
		}
	}

	/**
	 * Méthode étant appelé via le réseau pour notifié de la vente d'une
	 * propriété par un joueur
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a vendu la propriété
	 * @param number
	 *            le numéro de la propriété qui a été vendu
	 */
	public void change_prop_selled(String pseudo, int number) {
		this.game_model.sell_property_from_pseudo_to_position(pseudo, number);

		game_model.notify_player_message(pseudo,
				"Has sell property : " + game_model.get_terrain_name_from_number(number));
		game_model.notify_player_money_change(game_model.get_player_from_pseudo(pseudo).get_pseudo(),
				game_model.get_player_from_turn().get_money());
		game_model.notify_selled_house(game_model.get_player_from_pseudo(pseudo).get_pseudo(), number);

	}

	/**
	 * Méthode étant appelé via une communication réseau qui permet de donné la
	 * valeur des dés d'un client
	 * 
	 * @param values
	 *            les différentes valeurs de dés d'un client
	 */
	public void dice_values_receive(Vector<Integer> values) {
		this.game_model.notify_animation_dice(false);
		this.game_model.set_dices_values(values);
		this.game_model.notify_dice_values(values);

		// if
		// (network.get_pseudo_client().equals(game_model.get_player_from_turn().get_pseudo()))
		// {
		// network.send_message_to_server("GAME IN_GAME PIECE_MOVE " +
		// network.get_pseudo_client() + " "
		// + Integer.valueOf(game_model.get_player_from_turn().get_position() +
		// game_model.get_sum_dices()));
		// }
		change_piew_position(game_model.get_player_from_turn().get_pseudo(),
				game_model.get_player_from_turn().get_position() + game_model.get_sum_dices());
	}

	/**
	 * Méthode permettant d'obliger le controleur a ne plus accepter d'action
	 * tant que les pions sont en mouvement
	 */
	public void disable_waiting_movement() {
		this.waiting_movement = false;
	}

	/**
	 * Méthode permettant de dire au controleur que les mouvements des pions
	 * sont terminés
	 * 
	 */
	public void enable_waiting_movement() {
		this.waiting_movement = true;
	}

	/**
	 * Méthode étant appelé en réseau et dispatcher à tout les joueurs et qui va
	 * réaliser l'action par rapport à la position du joueur courant
	 * 
	 * @param pseudo
	 *            le pseudo du client qui a envoyé sa position
	 * @param position
	 *            la position du joueur courant
	 */
	public void change_piew_position(String pseudo, int position) {
		this.waiting_movement = true;
		boolean was_in_jail = false;

		Dice_manage_result res_dices = game_model.manage_dice_rolled(pseudo);
		switch (res_dices) {
		case MULT50:
			game_model.get_player_from_pseudo(pseudo).set_in_jail(true);
			game_model.get_player_from_pseudo(pseudo).move_to_position(11);
			game_model.notify_piece_move(pseudo, 11, true);
			game_model.notify_player_jail(game_model.get_player_from_turn().get_pseudo(), true);
			game_model.notify_player_message(pseudo, "Has been enjailed");
			break;
		case SAME_VALUE_AND_JAILING:
			was_in_jail = true;
			game_model.get_player_from_pseudo(pseudo).set_in_jail(false);
			game_model.notify_player_jail(game_model.get_player_from_turn().get_pseudo(), false);
			game_model.notify_player_message(pseudo, "Has get out of jail");
			game_model.notify_player_money_change(pseudo, game_model.get_player_from_pseudo(pseudo).get_money());
			game_model.notify_piece_move(pseudo, game_model.get_player_from_pseudo(pseudo).get_position(), false);
			break;
		case THREE_TIME_SAME:

			game_model.get_player_from_pseudo(pseudo).set_in_jail(true);
			game_model.get_player_from_pseudo(pseudo).move_to_position(11);
			game_model.notify_piece_move(pseudo, 11, true);
			game_model.notify_player_jail(game_model.get_player_from_turn().get_pseudo(), true);
			game_model.notify_player_message(pseudo, "Has been enjailed");
			break;
		case NOTHING:
			if (game_model.get_player_from_pseudo(pseudo).is_in_jail()) {
				game_model.get_player_from_pseudo(pseudo).increment_turn_in_jail();
				game_model.get_player_from_pseudo(pseudo).move_to_position(11);
				waiting_movement = false;
				if (Setting_configuration.rent_each_turn_jailing) {
					if (game_model.get_player_from_turn().get_pseudo().equals(network.get_pseudo_client())) {
						should_pay = true;
						owner_debt = "Jail_rent";
						sum_debt = 1500;
						game_model.notify_need_pay_rent(owner_debt, sum_debt, true, pseudo);
					}

				}
			} else {

				game_model.notify_player_money_change(pseudo, game_model.get_player_from_pseudo(pseudo).get_money());
				game_model.notify_piece_move(pseudo, game_model.get_player_from_pseudo(pseudo).get_position(), false);
			}
			break;
		default:
			break;
		}

		if (network.get_pseudo_client().equals(pseudo)) {
			this.waiting_spin = false;

			if (game_model.dices_are_same() && !game_model.get_player_from_pseudo(pseudo).is_in_jail()
					&& !was_in_jail) {
				this.should_roll = true;
			}
		}

		manage_action(pseudo);

	}

	public void manage_action(String pseudo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (!waiting_movement) {
						Action_done action = game_model.manage_position(pseudo);
						if (action == Action_done.CHANCE_CARD) {

							Abstract_card card = game_model.get_card_container().get_luck_card();
							new Thread(new Runnable() {
								@Override
								public void run() {
									boolean reaction = false;
									if (game_model.get_player_from_turn().get_pseudo()
											.equals(network.get_pseudo_client())) {
										waiting_card_showing = true;
										game_model.notify_show_card_info(Card_type.LUCK, card.get_description(), true);

									}
									try {
										Thread.sleep(1500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}

									if (card instanceof Earn_money_card) {
										Earn_money_card earn = (Earn_money_card) card;
										Player p = game_model.get_player_from_pseudo(pseudo);
										if (earn.get_type_earn() == Earn_card_type.SIMPLE_EARN) {
											p.set_money(p.get_money() + earn.get_sum_earn());
											game_model.notify_player_money_change(p.get_pseudo(), p.get_money());
										} else if (earn.get_type_earn() == Earn_card_type.EVERYONE_GIVE) {
											if (game_model.get_player_from_turn().get_pseudo()
													.equals(network.get_pseudo_client())) {
												waiting_paiement_all = true;
												pseudos_paiement_waiting = new ArrayList<String>();
												for (Player player_game : game_model.get_players()) {
													if (!player_game.get_surrend()
															&& !player_game.get_pseudo().equals(pseudo)) {
														pseudos_paiement_waiting.add(player_game.get_pseudo());
													}
												}
											}

											if (!network.get_pseudo_client().equals(pseudo)) {
												owner_debt = pseudo;
												sum_debt = earn.get_sum_earn();
												should_pay = true;
												game_model.notify_need_pay_rent(pseudo, earn.get_sum_earn(), true,
														network.get_pseudo_client());
											}

										}
									} else if (card instanceof Get_out_jail_card) {
										Player p = game_model.get_player_from_pseudo(pseudo);
										p.set_nb_card_out_jail(p.get_nb_card_out_jail() + 1);
									} else if (card instanceof Go_jail_card) {
										game_model.get_player_from_pseudo(pseudo).move_to_position(11);
										game_model.notify_piece_move(pseudo, 11, true);
										game_model.get_player_from_pseudo(pseudo).set_in_jail(true);
										game_model.notify_player_jail(game_model.get_player_from_turn().get_pseudo(),
												true);
										game_model.notify_player_message(pseudo, "Has been enjailed");
										if (game_model.get_player_from_turn().get_pseudo()
												.equals(network.get_pseudo_client())) {
											should_roll = false;
										}
									} else if (card instanceof Lose_money_card) {
										Lose_money_card lose = (Lose_money_card) card;
										String owner = "Bank";
										Player p = game_model.get_player_from_pseudo(pseudo);

										if (game_model.get_player_from_turn().get_pseudo()
												.equals(network.get_pseudo_client())) {
											should_pay = true;
											owner_debt = owner;
											int sum = 0;
											if (lose.get_type_earn() == Lose_card_type.SIMPLE_LOSE) {
												sum = lose.get_sum_lose();
											} else {
												sum += p.get_nb_houses_total() * lose.get_house_lose();
												sum += p.get_nb_hotel_total() * lose.get_hotel_lose();
											}
											sum_debt = sum;
											game_model.notify_need_pay_rent(owner_debt, sum_debt, true, pseudo);

										}
									} else if (card instanceof Move_card) {
										Move_card move = (Move_card) card;
										Player p = game_model.get_player_from_pseudo(pseudo);
										if (move.get_position_to_move() < 0) {
											p.move_to_position(p.get_position() - 3);
											game_model.notify_piece_move(pseudo, p.get_position(), true);

										} else {
											if (p.get_position() > move.get_position_to_move()) {
												p.set_money(p.get_money() + 20000);
												game_model.notify_player_money_change(p.get_pseudo(), p.get_money());
											}

											p.move_to_position(move.get_position_to_move());
											game_model.notify_piece_move(pseudo, p.get_position(), true);
										}
										reaction = true;
									}

									if (game_model.get_player_from_turn().get_pseudo()
											.equals(network.get_pseudo_client())) {
										waiting_card_showing = false;
										if (should_pay) {
											game_model.notify_need_pay_rent(owner_debt, sum_debt, true, pseudo);
										} else if (reaction) {
											game_model.notify_show_card_info(Card_type.LUCK, card.get_description(),
													false);
											manage_action(pseudo);
										} else {
											game_model.notify_show_card_info(Card_type.LUCK, card.get_description(),
													false);
										}
									}
								}
							}).start();

						} else if (action == Action_done.COMMUNAUTY_CARD) {
							Abstract_card card = game_model.get_card_container().get_communauty_card();
							new Thread(new Runnable() {
								@Override
								public void run() {
									boolean reaction = false;
									if (game_model.get_player_from_turn().get_pseudo()
											.equals(network.get_pseudo_client())) {
										waiting_card_showing = true;
										game_model.notify_show_card_info(Card_type.COMMUNAUTY, card.get_description(),
												true);

									}
									try {
										Thread.sleep(1500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									if (card instanceof Steal_card) {
										should_wait_steal_card = true;

									} else if (card instanceof Earn_money_card) {
										Earn_money_card earn = (Earn_money_card) card;
										Player p = game_model.get_player_from_pseudo(pseudo);
										if (earn.get_type_earn() == Earn_card_type.SIMPLE_EARN) {
											p.set_money(p.get_money() + earn.get_sum_earn());
											game_model.notify_player_message(pseudo,
													"Has earn : M" + earn.get_sum_earn());
											game_model.notify_player_money_change(p.get_pseudo(), p.get_money());
										} else if (earn.get_type_earn() == Earn_card_type.EVERYONE_GIVE) {
											if (game_model.get_player_from_turn().get_pseudo()
													.equals(network.get_pseudo_client())) {
												waiting_paiement_all = true;
												pseudos_paiement_waiting = new ArrayList<String>();
												for (Player player_game : game_model.get_players()) {
													if (!player_game.get_surrend()
															&& !player_game.get_pseudo().equals(pseudo)) {
														pseudos_paiement_waiting.add(player_game.get_pseudo());
													}
												}
											}

											if (!network.get_pseudo_client().equals(pseudo)) {
												owner_debt = pseudo;
												sum_debt = earn.get_sum_earn();
												should_pay = true;
												game_model.notify_need_pay_rent(pseudo, earn.get_sum_earn(), true,
														network.get_pseudo_client());
											}
										}
									} else if (card instanceof Get_out_jail_card) {
										Player p = game_model.get_player_from_pseudo(pseudo);
										p.set_nb_card_out_jail(p.get_nb_card_out_jail() + 1);
										game_model.notify_player_message(pseudo, "Has win a get out jail card");

									} else if (card instanceof Go_jail_card) {
										game_model.get_player_from_pseudo(pseudo).move_to_position(11);
										game_model.notify_piece_move(pseudo, 11, true);
										game_model.get_player_from_pseudo(pseudo).set_in_jail(true);
										game_model.notify_player_jail(game_model.get_player_from_turn().get_pseudo(),
												true);
										game_model.notify_player_message(pseudo, "Has been enjailed");
										if (game_model.get_player_from_turn().get_pseudo()
												.equals(network.get_pseudo_client())) {
											should_roll = false;
										}
									} else if (card instanceof Lose_money_card) {
										Lose_money_card lose = (Lose_money_card) card;
										String owner = "Bank";
										Player p = game_model.get_player_from_pseudo(pseudo);

										if (game_model.get_player_from_turn().get_pseudo()
												.equals(network.get_pseudo_client())) {
											should_pay = true;
											owner_debt = owner;
											int sum = 0;
											if (lose.get_type_earn() == Lose_card_type.SIMPLE_LOSE) {
												sum = lose.get_sum_lose();
											} else {
												sum += p.get_nb_houses_total() * lose.get_house_lose();
												sum += p.get_nb_hotel_total() * lose.get_hotel_lose();
											}
											sum_debt = sum;
											game_model.notify_need_pay_rent(owner_debt, sum_debt, true, pseudo);
										}
									} else if (card instanceof Move_card) {
										Move_card move = (Move_card) card;
										Player p = game_model.get_player_from_pseudo(pseudo);
										if (move.get_position_to_move() < 0) {
											p.move_to_position(p.get_position() - 3);
											game_model.notify_piece_move(pseudo, p.get_position(), true);

										} else {
											if (p.get_position() > move.get_position_to_move()) {
												p.set_money(p.get_money() + 20000);
												game_model.notify_player_money_change(p.get_pseudo(), p.get_money());
											}

											p.move_to_position(move.get_position_to_move());
											game_model.notify_piece_move(pseudo, p.get_position(), true);
										}
										reaction = true;
									}

									if (game_model.get_player_from_turn().get_pseudo()
											.equals(network.get_pseudo_client())) {
										waiting_card_showing = false;
										if (should_pay) {
											game_model.notify_need_pay_rent(owner_debt, sum_debt, true, pseudo);
										} else if (should_wait_steal_card) {

											game_model.notify_start_panel_steal_card(game_model.get_player_from_turn(),
													game_model.get_players(), true);

										} else if (reaction) {
											game_model.notify_show_card_info(Card_type.LUCK, card.get_description(),
													false);
											manage_action(pseudo);
										} else {
											game_model.notify_show_card_info(Card_type.COMMUNAUTY,
													card.get_description(), false);
										}
									}
								}
							}).start();

						} else if (action == Action_done.BUYABLE_TERRAIN) {
							if (network.get_pseudo_client().equals(game_model.get_player_from_turn().get_pseudo())) {
								should_decide_buying = true;
								game_model.notify_buy_possible(game_model.get_player_from_turn().get_position());
							}
						} else if (action == Action_done.JAIL) {
							game_model.get_player_from_pseudo(pseudo).move_to_position(11);
							game_model.notify_piece_move(pseudo, 11, true);
							game_model.notify_player_jail(game_model.get_player_from_turn().get_pseudo(), true);
							game_model.notify_player_message(pseudo, "Has been enjailed");
						} else if (action == Action_done.MONEY_EARN) {
							game_model.notify_player_money_change(pseudo,
									game_model.get_player_from_pseudo(pseudo).get_money());
						} else if (action == Action_done.RENT_PAYED) {
							String owner = game_model.get_owner_name_of_property(
									game_model.get_player_from_pseudo(pseudo).get_position());

							if (game_model.get_player_from_turn().get_pseudo().equals(network.get_pseudo_client())) {
								should_pay = true;
								owner_debt = owner;
								sum_debt = game_model.get_last_transfert() * rent_multiplier;
								game_model.notify_need_pay_rent(owner_debt, sum_debt, true, pseudo);
							}

						} else if (action == Action_done.TAXE_PAYED) {
							if (game_model.get_player_from_turn().get_pseudo().equals(network.get_pseudo_client())) {
								should_pay = true;
								owner_debt = "Bank";
								sum_debt = game_model.get_last_transfert();
								game_model.notify_need_pay_rent(owner_debt, sum_debt, true, pseudo);

							}

						} else if (action == Action_done.TERRAIN_MORTGAGE) {
							game_model.notify_player_message(pseudo, "Nothing to pay, terrain has been mortgaged");
						} else if (action == Action_done.PLAYER_JAILING) {
							game_model.notify_player_message(pseudo, "Nothing to pay, owner player is in jail");
						}

						break;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * Méthode permettant de notifier via une communication réseau le vainqueur
	 * d'une partie
	 * 
	 * @param winner
	 *            le pseudo du vainqueur de la partie
	 */
	public void change_winner(String winner) {

		if (!has_bankrupt_already) {
			if (this.network.get_pseudo_client().equals(winner)) {
				this.game_model.notify_winner("Victory !");
			} else {
				this.game_model.notify_winner("Defeat !");
			}
		}
	}

	/**
	 * Méthode permettant de notifié via une communication réseau la sortie de
	 * prison d'un joueur
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui sort de prison
	 */
	public void change_jail_out_player(String pseudo) {
		this.game_model.get_player_from_pseudo(pseudo).set_in_jail(false);
		this.game_model.notify_player_jail(pseudo, false);
		this.game_model.notify_player_message(pseudo, "Has get out of jail");
	}

	/**
	 * Méthode permettant de changer le nombre d'argent dont dispose un client
	 * 
	 * @param pseudo
	 *            le pseudo du client a qui on doit changer l'argent
	 * @param money
	 *            l'argent du client
	 */
	public void change_money_player(String pseudo, int money) {
		this.game_model.set_player_money(pseudo, money);
		this.game_model.notify_player_money_change(pseudo, money);
	}

	/**
	 * Méthode étant appelé via une communication réseau et qui va etre appelé
	 * lorsqu'un joueur adverse a abandonner la partie. Dans ce cas, cette
	 * méthode met a jour le modele et regarde si il ne reste plus qu'un joueur,
	 * alors ce dernier a gagné
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a abandonné
	 */
	public void surrend_from_player(String pseudo) {
		this.game_model.set_player_surrend(pseudo);
		this.game_model.notify_player_surrend(pseudo);
		if (in_trade_mode) {
			Trade t = game_model.get_trade();
			if (t.get_pseudo_first().equals(pseudo) || t.get_pseudo_second().equals(pseudo)) {
				cancel_trade();
			}
		}

		if (game_model.get_player_from_turn().get_pseudo().equals(pseudo)) {
			if (should_wait_steal_card) {
				should_wait_steal_card = false;
			}
		}

		if (in_auction_mode) {
			game_model.change_auction_player(pseudo, -1);
			game_model.notify_auction_value(pseudo, -1);
		}

		if (should_wait_auction_paid) {
			if (pseudo.equals(name_payer_auction)) {
				should_wait_auction_paid = false;
				name_payer_auction = null;
			}
		}

		if (this.game_model.get_player_in_game() == 1) {
			this.network.send_message_to_server("GAME IN_GAME WINNER " + game_model.get_last_player().get_pseudo());
		}

	}

	/**
	 * Méthode permettant de notifier de la fin d'un tour et de passer au joueur
	 * suivant dans le jeu.
	 */
	public void change_player_turn_to_next() {
		this.game_model.next_player();
		this.game_model.notify_player_message(network.get_pseudo_client(), "");
		this.game_model.notify_player_turn(this.game_model.get_player_from_turn().get_pseudo());

		if (this.network.get_pseudo_client().equals(this.game_model.get_player_from_turn().get_pseudo())) {
			if (Setting_configuration.nb_turn_before_end != -1) {
				if (game_model.get_nb_turn() > Setting_configuration.nb_turn_before_end) {
					Player winner = game_model.get_winner();
					this.network.send_message_to_server("GAME IN_GAME WINNER " + winner.get_pseudo());
					return;
				}
			}

			this.game_model.notify_player_message(network.get_pseudo_client(), "You must roll dices");
			this.should_roll = true;
			this.should_play = true;
			this.should_decide_buying = false;
			if (this.game_model.get_player_in_game() == 1) {
				this.network.send_message_to_server("GAME IN_GAME WINNER " + game_model.get_last_player().get_pseudo());
			}
		} else {
			this.should_roll = false;
			this.should_play = false;
			this.should_decide_buying = false;
			this.should_take_decision_jail = false;
			this.already_teleport = false;
			this.already_ask_nb_dice = false;
		}

		if (game_model.get_player_from_turn().is_in_jail()) {
			if (game_model.get_player_from_turn().get_turn_in_jail() > Setting_configuration.nb_turn_in_jail) {

				if (game_model.get_player_from_turn().get_pseudo().equals(network.get_pseudo_client())) {
					should_pay = true;
					owner_debt = "Jail_out";
					sum_debt = Setting_configuration.sum_to_out_jail;
					game_model.notify_need_pay_rent(owner_debt, sum_debt, true, network.get_pseudo_client());
				}
				return;
			} else {
				if (game_model.get_player_from_turn().get_pseudo().equals(network.get_pseudo_client())) {
					this.should_take_decision_jail = true;
					this.should_roll = false;
					this.should_play = true;
					this.should_decide_buying = false;
					this.game_model.notify_need_manage_jail(game_model.get_player_from_turn().get_pseudo(), true);
					return;
				}
			}
		}
	}

}
