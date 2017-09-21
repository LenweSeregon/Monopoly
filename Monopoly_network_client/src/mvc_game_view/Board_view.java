package mvc_game_view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import mvc_game_controler.Game_controler;
import mvc_game_enums.Card_type;
import mvc_game_enums.Color_terrain;
import mvc_game_enums.Company_type;
import mvc_game_enums.Public_type;
import mvc_game_enums.Terrain_type;
import mvc_game_model.Player;
import mvc_game_model_terrains.Abstract_terrain;
import mvc_game_model_terrains.Card_terrain;
import mvc_game_model_terrains.Company_terrain;
import mvc_game_model_terrains.Property_terrain;
import mvc_game_model_terrains.Public_terrain;
import mvc_game_model_terrains.Station_terrain;
import settings.Setting_configuration;

@SuppressWarnings("serial")
public class Board_view extends JPanel {

	private int width;
	private int height;

	private UI_view ui_reference;
	private ArrayList<Piece_view> pieces;
	private ArrayList<Abstract_board_terrain_view> terrains;
	private Middle_board middle;
	private Game_controler ref_controler;

	/**
	 * Constructeur de la classe représentant la vue du plateau
	 * 
	 * @param ref_controler
	 *            la référence vers le controleur
	 * @param width
	 *            la largeur du plateau
	 * @param height
	 *            la hauteur du plateau
	 */
	public Board_view(Game_controler ref_controler, int width, int height) {
		this.width = width;
		this.height = height;
		this.ref_controler = ref_controler;
		this.ui_reference = null;
		this.terrains = new ArrayList<Abstract_board_terrain_view>();
		this.pieces = new ArrayList<Piece_view>();

		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.GREEN);
		this.setLayout(null);
	}

	/**
	 * Méthode qui pour tous les terrains désactive la possibilité d'afficher le
	 * zoom
	 */
	public void disable_show_informations_terrains() {
		for (Abstract_board_terrain_view v : terrains) {
			v.set_allow_show_informations(false);
		}
	}

	/**
	 * Méthode qui pour tous les terrains active la possibilité d'afficher le
	 * zoom
	 */
	public void enable_show_informations_terrain() {
		for (Abstract_board_terrain_view v : terrains) {
			v.set_allow_show_informations(true);
		}
	}

	/**
	 * Méthode qui permet de construire le milieu du plateau en fonction du
	 * numéro de terrain
	 * 
	 * @param number
	 *            le numéro de terrain
	 */
	public void build_midde_from_number_to_buy(int number) {
		disable_show_informations_terrains();
		for (Abstract_board_terrain_view terrain : terrains) {
			if (terrain instanceof Board_terrain_property_view && terrain.get_number() == number) {
				Board_terrain_property_view ter = (Board_terrain_property_view) (terrain);
				middle.build_center_buying(ter.get_name(), ter.get_color(), ter.get_price(), ter.get_rents(),
						ter.get_house_price());

			} else if (terrain instanceof Board_terrain_image_view && terrain.get_number() == number) {
				Board_terrain_image_view ter = (Board_terrain_image_view) (terrain);
				middle.build_center_image_buying(ter.get_title(), ter.get_path_img(), ter.get_description());
			}
		}
	}

	/**
	 * Méthode permettant de demander la construction ou non du milieu du
	 * plateau pour permettre de se téléporter
	 * 
	 * @param show
	 *            est ce qu'on doit construire ou non le plateau
	 */
	public void build_middle_nb_dice_chose(boolean show) {
		if (show) {
			this.disable_show_informations_terrains();
			this.middle.build_chose_nb_dice();
		} else {
			this.middle.build_center_empty();
			this.enable_show_informations_terrain();
		}
	}

	/**
	 * Méthode permettant de demander la construction ou non du milieu du
	 * plateau pour permettre de se téléporter
	 * 
	 * @param stations
	 *            les différentes station ou l'on peut se déplacer
	 * @param show
	 *            est ce qu'on doit construire ou non le plateau
	 */
	public void build_middle_station_teleport(ArrayList<Station_terrain> stations, boolean show) {
		if (show) {
			this.disable_show_informations_terrains();
			this.middle.build_teleport_station(stations);
		} else {
			this.middle.build_center_empty();
			this.enable_show_informations_terrain();
		}
	}

	/**
	 * Méthode permettant de demander la construction ou non du milieu du
	 * plateau pour montrer le paenneau de choix de joueur pour l'échange
	 * 
	 * @param emitter
	 *            le joueur qui demande a afficher le panneau
	 * @param players
	 *            les différents joueurs pouvant prendre part à l'échange
	 * @param show
	 *            est ce qu'on doit construire ou non le panel
	 */
	public void build_middle_trade_player_chose(Player emitter, ArrayList<Player> players, boolean show) {
		if (show) {
			this.disable_show_informations_terrains();
			this.middle.build_trade_player_chose(emitter, players);
		} else {
			this.middle.build_center_empty();
			this.enable_show_informations_terrain();
		}
	}

	/**
	 * Méthode permettande demander la construction ou non du milieu du plateau
	 * pour montrer l'échange en cours
	 * 
	 * @param first
	 *            le premier joueur qui participe à l'échange
	 * @param second
	 *            le deuxiéme joueur qui participe à l'échange
	 * @param show
	 *            est ce qu'on doit construire le plateau
	 */
	public void build_middle_trade_start(Player first, Player second, boolean show) {
		if (show) {
			this.middle.init_trade();
			this.disable_show_informations_terrains();
			this.middle.build_trade_start(first, second);
		} else {
			this.middle.build_center_empty();
			this.enable_show_informations_terrain();
		}
	}

	/**
	 * Méthode permettant d'ajouter ou retirer une propriété de la liste
	 * d'échange de l'adversaire
	 * 
	 * @param number
	 *            le nuémro de la propriété
	 * @param add
	 *            est ce que la propriété est ajouté ou retiré ?
	 */
	public void add_versus_terrain_trade(int number, boolean add) {
		this.middle.add_versus_terrain(number, add);
	}

	/**
	 * Méthode permettant de définir la somme de l'adversaire dans le milieu du
	 * plateau durant l'échange
	 * 
	 * @param money
	 *            la somme proposé par l'adversaire
	 */
	public void change_money_trade(int money) {
		this.middle.change_versus_money(money);
	}

	/**
	 * Méthode permettant de définir si le panel d'un joueur doit être en
	 * position validation ou non
	 * 
	 * @param versus
	 *            est ce que le joueur qui a validé ou pas est l'adversaire
	 * @param validate
	 *            est ce qu'il a validé ou non
	 */
	public void change_validate_status(boolean versus, boolean validate) {
		this.middle.change_validate_value(versus, validate);
	}

	/**
	 * Méthode permettant de demander au plateau du milieu d'afficher le panel
	 * pour l'enchere
	 * 
	 * @param players
	 *            les différents joueurs qui participent à l'enchere
	 * @param number_property
	 *            le numéro de la propriété à afficher pour l'enchere
	 * @param auction
	 *            est ce qu'on doit afficher le panel
	 */
	public void build_auction_from_number(ArrayList<Player> players, int number_property, boolean auction) {
		if (auction) {
			this.disable_show_informations_terrains();
			// this.middle.build_auction_running(int number_property);
			for (Abstract_board_terrain_view terrain : terrains) {
				if (terrain instanceof Board_terrain_property_view && terrain.get_number() == number_property) {
					Board_terrain_property_view ter = (Board_terrain_property_view) (terrain);
					middle.init_auction(players);
					middle.build_auction_running_property(ter.get_name(), ter.get_color(), ter.get_price(),
							ter.get_rents(), ter.get_house_price(), number_property);

				} else if (terrain instanceof Board_terrain_image_view && terrain.get_number() == number_property) {
					Board_terrain_image_view ter = (Board_terrain_image_view) (terrain);
					middle.init_auction(players);
					middle.build_auction_running_image(ter.get_title(), ter.get_path_img(), ter.get_description(),
							number_property);
				}
			}
		} else {
			this.middle.build_center_empty();
			this.enable_show_informations_terrain();
		}
	}

	/**
	 * Méthode permettant de construire le plateau du milieu pour afficher la
	 * carte que vient de tirer le joueur
	 * 
	 * @param type
	 *            le type de la carte tiré
	 * @param description
	 *            la description de la carte
	 * @param show
	 *            est ce qu'on doit afficher le panel
	 */
	public void build_show_card(Card_type type, String description, boolean show) {
		if (show) {
			this.disable_show_informations_terrains();
			this.middle.build_center_display_card(type, description);
		} else {
			this.middle.build_center_empty();
			this.enable_show_informations_terrain();
		}
	}

	/**
	 * Méthode permettant de changer la valeur d'enchere du plateau du milieu
	 * 
	 * @param pseudo
	 *            le pseudo du joueur auquel il faut changer l'encheree
	 * @param value
	 *            la value d'argent à changer
	 */
	public void change_middle_auction_value(String pseudo, int value) {
		this.middle.change_price_player(pseudo, value);
	}

	/**
	 * Méthode permettant de construire le milieu du plateau pour permettre au
	 * joueur de choisir la carte a voler
	 * 
	 * @parma stealer le joueur qui va voler
	 * @param players
	 *            les différents joueur que l'on va voler
	 * @param show
	 *            est ce qu'on doit montrer le panel
	 */
	public void build_middle_for_steal(Player stealer, ArrayList<Player> players, boolean show) {
		if (show) {
			this.disable_show_informations_terrains();
			this.middle.build_steal_card(stealer, players);
		} else {
			this.middle.build_center_empty();
			this.enable_show_informations_terrain();
		}
	}

	/**
	 * Méthode étant appelé lorsque l'on veut gérer l'obligation de payer pour
	 * un joueur
	 * 
	 * @param owner
	 *            le propriété qui doit etre payé
	 * @param sum
	 *            la somme qui doit etre payé
	 * @param have_to_pay
	 *            est ce que le joueur doit payer
	 */
	public void build_middle_for_paid(String owner, int sum, boolean have_to_pay) {
		if (have_to_pay) {
			this.disable_show_informations_terrains();
			this.middle.build_pay_center(owner, sum);
		} else {
			this.middle.build_center_empty();
			this.enable_show_informations_terrain();
		}
	}

	/**
	 * Méthode étant appelé lorsque l'on veut gérer l'emprisonnement d'un joueur
	 * pour lui demander ce qu'il veut faire
	 */
	public void build_middle_for_jail_managing(String pseudo, boolean have_to_manage) {
		if (have_to_manage) {
			this.middle.build_manage_jailing(pseudo);
			this.disable_show_informations_terrains();
		} else {
			this.middle.build_center_empty();
			this.enable_show_informations_terrain();
		}
	}

	/**
	 * Méthode qui permet de dire au milieu du plateau de se vider
	 */
	public void empty_middle_board() {
		if (middle != null) {
			this.middle.build_center_empty();
		}
	}

	/**
	 * Méthode qui permet de construire la carte
	 * 
	 * @param terrains
	 *            les différents terrains qui doivent etre représentés
	 */
	public void build_map_bis(ArrayList<Abstract_terrain> terrains) {
		int pos_x = width - 130;
		int pos_y = height - 130;

		int width_little_terrain = 80;
		int height_little_terrain = 130;

		int width_huge_terrain = 130;
		int height_huge_terrain = 130;

		// Build middle board
		this.middle = new Middle_board(ref_controler, this, width_huge_terrain, height_huge_terrain,
				width_little_terrain * 9, width_little_terrain * 9);
		this.add(middle);

		// Build terrains

		for (Abstract_terrain terrain : terrains) {
			int position = terrain.get_number();
			String name = terrain.get_name();
			Color_terrain color = Color_terrain.NONE;
			int price = 0;
			int h_p = 0;
			int[] rents = null;

			if (terrain instanceof Property_terrain) {
				Property_terrain ter = (Property_terrain) terrain;
				color = ter.get_color();
				price = ter.get_buy_price();
				rents = new int[6];
				h_p = ter.get_house_price();
				for (int i = 0; i < 6; i++) {
					rents[i] = ter.get_rents()[i];
				}
			} else if (terrain instanceof Public_terrain) {
				Public_terrain ter = (Public_terrain) terrain;
				price = ter.get_cost();
			}

			if (position >= 1 && position <= 11) {
				Orientation o = Orientation.SOUTH;
				if (position == 1) {
					Board_terrain_image_view v = new Board_terrain_image_view(name, position, o, get_image(terrain),
							pos_x, pos_y, width_huge_terrain, height_huge_terrain, middle, terrain.get_type(), price);
					pos_x -= width_little_terrain;
					this.terrains.add(v);
					this.add(v);
				} else if (position == 11) {
					pos_x -= (width_huge_terrain - width_little_terrain);
					Board_terrain_image_view v = new Board_terrain_image_view(name, position, o, get_image(terrain),
							pos_x, pos_y, width_huge_terrain, height_huge_terrain, middle, terrain.get_type(), price);
					pos_y -= width_little_terrain;
					this.terrains.add(v);
					this.add(v);
				} else {
					if (terrain.get_type() == Terrain_type.PROPERTY) {
						Board_terrain_property_view v = new Board_terrain_property_view(position, o, pos_x, pos_y,
								width_little_terrain, height_little_terrain, name, color, price, h_p, rents, middle);
						this.terrains.add(v);
						this.add(v);
					} else {
						Board_terrain_image_view v = new Board_terrain_image_view(name, position, o, get_image(terrain),
								pos_x, pos_y, width_little_terrain, height_little_terrain, middle, terrain.get_type(),
								price);
						this.terrains.add(v);
						this.add(v);
					}

					pos_x -= width_little_terrain;

				}

			} else if (position >= 12 && position <= 21) {
				Orientation o = Orientation.WEST;
				if (position == 21) {
					pos_y -= (width_huge_terrain - width_little_terrain);
					Board_terrain_image_view v = new Board_terrain_image_view(name, position, o, get_image(terrain),
							pos_x, pos_y, width_huge_terrain, height_huge_terrain, middle, terrain.get_type(), price);
					pos_x += width_huge_terrain;
					this.terrains.add(v);
					this.add(v);
				} else {
					if (terrain.get_type() == Terrain_type.PROPERTY) {
						Board_terrain_property_view v = new Board_terrain_property_view(position, o, pos_x, pos_y,
								height_little_terrain, width_little_terrain, name, color, price, h_p, rents, middle);
						this.terrains.add(v);
						this.add(v);
					} else {
						Board_terrain_image_view v = new Board_terrain_image_view(name, position, o, get_image(terrain),
								pos_x, pos_y, height_little_terrain, width_little_terrain, middle, terrain.get_type(),
								price);
						this.terrains.add(v);
						this.add(v);
					}
					pos_y -= width_little_terrain;
				}
			} else if (position >= 22 && position <= 31) {
				Orientation o = Orientation.NORTH;
				if (position == 31) {
					Board_terrain_image_view v = new Board_terrain_image_view(name, position, o, get_image(terrain),
							pos_x, pos_y, width_huge_terrain, height_huge_terrain, middle, terrain.get_type(), price);
					pos_y += height_huge_terrain;
					this.terrains.add(v);
					this.add(v);
				} else {
					if (terrain.get_type() == Terrain_type.PROPERTY) {
						Board_terrain_property_view v = new Board_terrain_property_view(position, o, pos_x, pos_y,
								width_little_terrain, height_little_terrain, name, color, price, h_p, rents, middle);
						this.terrains.add(v);
						this.add(v);
					} else {
						Board_terrain_image_view v = new Board_terrain_image_view(name, position, o, get_image(terrain),
								pos_x, pos_y, width_little_terrain, height_little_terrain, middle, terrain.get_type(),
								price);
						this.terrains.add(v);
						this.add(v);
					}
					pos_x += width_little_terrain;
				}
			} else {
				Orientation o = Orientation.EAST;
				if (terrain.get_type() == Terrain_type.PROPERTY) {
					Board_terrain_property_view v = new Board_terrain_property_view(position, o, pos_x, pos_y,
							height_little_terrain, width_little_terrain, name, color, price, h_p, rents, middle);
					this.terrains.add(v);
					this.add(v);
				} else {
					Board_terrain_image_view v = new Board_terrain_image_view(name, position, o, get_image(terrain),
							pos_x, pos_y, height_little_terrain, width_little_terrain, middle, terrain.get_type(),
							price);
					this.terrains.add(v);
					this.add(v);
				}
				pos_y += width_little_terrain;
			}
		}

		this.revalidate();
		this.repaint();
	}

	public void build_player_pieces(ArrayList<Player> players) {
		for (Player p : players) {
			Abstract_board_terrain_view terrain = get_board_terrain_from_position(p.get_position());
			Dimension pos = terrain.get_free_position();
			int x = (int) (terrain.get_position_x() + pos.getWidth());
			int y = (int) (terrain.get_position_y() + pos.getHeight());
			int width_piece = 30;
			int height_piece = 30;
			Piece_view piece = new Piece_view(p.get_pseudo(), p.get_piece_name(), p.get_position(), x, y, width_piece,
					height_piece);
			this.pieces.add(piece);
			this.add(piece);
			terrain.set_nb_piece(terrain.get_nb_piece() + 1);
			this.setComponentZOrder(piece, 0);
			piece.set_pos_relative(x, y);
		}
	}

	/**
	 * Méthode qui permet de lier l'ui au plateau pour transiter les
	 * informations
	 * 
	 * @param ref_ui
	 */
	public void bind_ui_reference(UI_view ref_ui) {
		this.ui_reference = ref_ui;
	}

	/**
	 * Méthode qui permet de récupérer la vue d'un terrain en fonction de la
	 * position du terrain
	 * 
	 * @param position
	 *            la position du terrain
	 * @return le terrain a la position donnée
	 */
	public Abstract_board_terrain_view get_board_terrain_from_position(int position) {
		return this.terrains.get(position - 1);
	}

	/**
	 * Méthode permettant de bouger un pion sur le plateau et de le
	 * repositionner comme il se doit pour garder un affichage cohérent
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param position
	 *            la position ou l'on veut afficher le pion
	 * @param fast_move
	 *            valeure booléenne pour le déplacement rapide ou non
	 */
	public void piece_moved(String pseudo, int position, boolean fast_move) {

		Player_view player = this.ui_reference.get_player_from_pseudo(pseudo);

		if (fast_move) {
			ref_controler.disable_waiting_movement();

			int old_pos = player.get_position();
			Abstract_board_terrain_view old = get_board_terrain_from_position(old_pos);
			Abstract_board_terrain_view current = get_board_terrain_from_position(position);

			old.set_nb_piece(old.get_nb_piece() - 1);
			Dimension pos = current.get_free_position();
			int x = (int) (current.get_position_x() + pos.getWidth());
			int y = (int) (current.get_position_y() + pos.getHeight());

			for (Piece_view piece : pieces) {
				if (piece.get_pseudo().equals(pseudo)) {
					piece.set_pos_relative(x, y);
					piece.set_pos(position);
				}
			}
			current.set_nb_piece(current.get_nb_piece() + 1);
			player.set_position(position);
			if (Setting_configuration.mask_board) {
				get_board_terrain_from_position(position).set_fog_of_war(false);
				get_board_terrain_from_position(position).repaint();
			}

		} else {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int old_pos = player.get_position();

					while (old_pos != position) {
						if (old_pos == 41) {
							old_pos = 1;
						}

						if (old_pos == position) {
							break;
						}

						Abstract_board_terrain_view old = get_board_terrain_from_position(old_pos);

						int cur_pos = old_pos + 1;
						if (cur_pos > 40) {
							cur_pos = 1;
						}
						Abstract_board_terrain_view current = get_board_terrain_from_position(cur_pos);

						old.set_nb_piece(old.get_nb_piece() - 1);
						Dimension pos = current.get_free_position();
						int x = (int) (current.get_position_x() + pos.getWidth());
						int y = (int) (current.get_position_y() + pos.getHeight());

						for (Piece_view piece : pieces) {
							if (piece.get_pseudo().equals(pseudo)) {
								piece.set_pos_relative(x, y);
								piece.set_pos(position);
							}
						}
						current.set_nb_piece(current.get_nb_piece() + 1);
						player.set_position(position);

						old_pos++;
						try {
							Thread.sleep(150);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (Setting_configuration.mask_board) {
						get_board_terrain_from_position(position).set_fog_of_war(false);
						get_board_terrain_from_position(position).repaint();
					}
					ref_controler.disable_waiting_movement();
				}
			}).start();
		}

	}

	/**
	 * Méthode permettatn de récupérer l'iamge à afficher en fonction du type du
	 * terrain
	 * 
	 * @param terrain
	 *            le terraib dont l'on veut connaitre l'image
	 * @return le chemin de l'image
	 */
	public String get_image(Abstract_terrain terrain) {
		switch (terrain.get_type()) {
		case CARDS:
			Card_terrain card = (Card_terrain) terrain;
			if (card.get_card_type() == Card_type.LUCK) {
				return "chance.jpg";
			} else {
				return "caisse.jpg";
			}

		case COMPANY:
			Company_terrain company = (Company_terrain) terrain;
			if (company.get_type_company() == Company_type.SUPPLY) {
				return "supply.jpg";
			} else {
				return "water.jpg";
			}
		case GO:
			return "go.png";
		case GO_JAIL:
			return "police.png";
		case JAIL:
			return "prison.png";
		case PARKING:
			return "park.png";
		case PROPERTY:
			return "station.jpg";
		case PUBLIC:
			Public_terrain pub = (Public_terrain) terrain;
			if (pub.get_public_type() == Public_type.TAX) {
				return "tax.jpg";
			} else {
				return "luxury.jpg";
			}

		case STATION:
			return "station.jpg";
		default:
			return null;
		}

	}
}
