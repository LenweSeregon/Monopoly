package mvc_game_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import mvc_game_controler.Game_controler;
import mvc_game_enums.Card_type;
import mvc_game_model.Player;
import mvc_game_model_terrains.Abstract_terrain;
import mvc_game_model_terrains.Station_terrain;
import mvc_game_pattern_observer.Observer;
import utils.Resources;
import view.Window;

@SuppressWarnings("serial")
public class Game_view extends JPanel implements Observer {

	private Game_controler controler;
	private Board_view board;
	private UI_view ui;
	private Window window_ref;

	private boolean is_building;
	private boolean pause;
	private boolean winner;
	private String text_winner;

	private int width;
	private int height;

	private BufferedImage[] eng_anim;
	private int idx_anim;

	/**
	 * Constructeur de la classe qui représente la vue du jeu en général, cette
	 * classe contient en réalité deux grosses parties visuelles qui sont l'UI
	 * et le plateau
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 * @param controler
	 *            une référence vers le controleur
	 * @param win_ref
	 *            une référence vers la fenetre principale
	 */
	public Game_view(int width, int height, Game_controler controler, Window win_ref) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.width = width;
		this.height = height;

		this.board = null;
		this.controler = controler;
		this.window_ref = win_ref;
		this.is_building = true;

		this.pause = false;
		this.winner = false;
		this.text_winner = "";

		window_ref.getGlassPane().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				e.consume();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				e.consume();
			}
		});
		window_ref.getGlassPane().addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				e.consume();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				e.consume();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				e.consume();
			}
		});
		window_ref.getGlassPane().setVisible(false);

		eng_anim = new BufferedImage[4];
		eng_anim[0] = Resources.get_image("engrenage1.png");
		eng_anim[1] = Resources.get_image("engrenage2.png");
		eng_anim[2] = Resources.get_image("engrenage3.png");
		eng_anim[3] = Resources.get_image("engrenage4.png");

		idx_anim = 0;

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (is_building) {

					repaint();
					try {
						Thread.sleep(65);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					idx_anim += 1;
					if (idx_anim == 4) {
						idx_anim = 0;
					}
					repaint();
				}
			}
		}).start();

		this.build_center(height, height);
		this.build_left(width - height, height);
		repaint();
	}

	/**
	 * Méthode permettant de construire le centre du plateu avec le plateau
	 * 
	 * @param width
	 *            la largeur du center
	 * @param height
	 *            la hauteur du centre
	 */
	private void build_center(int width, int height) {
		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(width, height));
		center.setLayout(new BorderLayout());
		center.setBackground(new Color(170, 203, 172));

		this.board = new Board_view(controler, width, height);
		center.add(this.board, BorderLayout.CENTER);

		this.add(center, BorderLayout.CENTER);
	}

	/**
	 * Méthode permettant de construire la gauche du plateau avec l'ui
	 * 
	 * @param width
	 *            la largeur de la gauche
	 * @param height
	 *            la hauteur de la gauche
	 */
	private void build_left(int width, int height) {
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(width, height));
		left.setLayout(new BorderLayout());

		this.ui = new UI_view(width, height, controler, window_ref);
		left.add(this.ui, BorderLayout.CENTER);
		this.board.bind_ui_reference(this.ui);

		this.ui.bind_board_view_ref(this.board);

		this.add(left, BorderLayout.WEST);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Toolkit.getDefaultToolkit().sync();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (winner) {
			g2.setColor(new Color(60, 60, 60, 150));
			g2.fillRect(0, 0, width, height);
			FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(55.f));
			int width_text = fm.stringWidth(text_winner);
			int height_text = fm.getHeight();

			g2.setFont(Resources.get_font("kabel.ttf").deriveFont(55.f));
			g2.setColor(Color.WHITE);
			g2.drawString(text_winner, (width - width_text) / 2, (height - height_text) / 2);
		} else if (pause) {
			g2.setColor(new Color(40, 40, 40, 200));
			g2.fillRect(0, 0, width, height);
			FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(55.f));
			int width_text = fm.stringWidth("Pause");
			int height_text = fm.getHeight();

			g2.setFont(Resources.get_font("kabel.ttf").deriveFont(55.f));
			g2.setColor(Color.WHITE);
			g2.drawString("Pause", (width - width_text) / 2, (height - height_text) / 2);
		} else if (is_building) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, width, height);
			g2.setColor(Color.WHITE);
			g2.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, 50.f));
			FontMetrics fm = g2.getFontMetrics(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, 50.f));
			int width_text = fm.stringWidth("Building your board");
			g2.drawString("Building your board", (width - width_text) / 2, 300);
			g2.drawImage(eng_anim[idx_anim], (width - (height / 3)) / 2, (height - (height / 3)) / 2, height / 3,
					height / 3, this);
		}
	}

	@Override
	public void update_model_loaded(ArrayList<Abstract_terrain> terrains, ArrayList<Player> players,
			String your_pseudo) {
		board.setVisible(false);
		ui.setVisible(false);
		this.board.build_map_bis(terrains);
		this.board.build_player_pieces(players);
		this.ui.build_ui(players, your_pseudo);
		revalidate();
		repaint();
	}

	@Override
	public void update_player_money_change(String pseudo, int money) {
		this.ui.change_money_player(pseudo, money);
	}

	@Override
	public void update_player_message(String pseudo, String message) {
		this.ui.change_msg_player(pseudo, message);
	}

	@Override
	public void update_player_turn(String pseudo) {
		this.ui.change_player_turn(pseudo);
	}

	@Override
	public void update_player_surrend(String pseudo) {
		this.ui.change_player_surrend(pseudo);
	}

	@Override
	public void update_winner(String text) {
		this.winner = true;
		this.text_winner = text;

		window_ref.getGlassPane().setVisible(true);
		this.repaint();
	}

	@Override
	public void update_animation_dice(boolean launch) {
		if (launch) {
			this.ui.launch_animation();
		} else {
			this.ui.disable_animation();
		}
	}

	@Override
	public void update_dice_values(Vector<Integer> values) {
		this.ui.set_dices_values(values);
	}

	@Override
	public void update_piece_move(String pseudo, int position, boolean fast_move) {
		this.board.piece_moved(pseudo, position, fast_move);
	}

	@Override
	public void update_buy_possible(int number_house) {
		this.board.build_midde_from_number_to_buy(number_house);
	}

	@Override
	public void update_buyed_house(String pseudo, int position_house) {
		this.ui.add_property_to_player(pseudo, position_house);
		this.board.enable_show_informations_terrain();
		this.board.empty_middle_board();
	}

	@Override
	public void update_refused_buy_house() {
		this.board.enable_show_informations_terrain();
		this.board.empty_middle_board();
	}

	@Override
	public void update_mortgage_house(String pseudo, int number_house, boolean mortgaging) {
		this.ui.mortgage_terrain(pseudo, number_house, mortgaging);
	}

	@Override
	public void update_nb_house_property(String pseudo, int number_property, int number_house) {
		this.ui.set_nb_house_terrain(pseudo, number_property, number_house);
	}

	@Override
	public void update_player_jail(String pseudo, boolean in_jail) {
		this.ui.set_player_jail(pseudo, in_jail);
	}

	@Override
	public void update_need_manage_jail(String pseudo, boolean have_to_manage) {
		this.board.build_middle_for_jail_managing(pseudo, have_to_manage);
	}

	@Override
	public void update_selled_house(String pseudo, int position_house) {
		this.ui.remove_property_to_player(pseudo, position_house);
	}

	@Override
	public void update_need_pay_rent(String owner, int sum, boolean have_to_pay, String debter) {
		this.board.build_middle_for_paid(owner, sum, have_to_pay);
	}

	@Override
	public void update_auction_start(ArrayList<Player> players, int number_property, boolean auction) {
		this.board.build_auction_from_number(players, number_property, auction);
	}

	@Override
	public void update_auction_value(String pseudo, int value) {
		this.board.change_middle_auction_value(pseudo, value);
	}

	@Override
	public void update_show_card_info(Card_type type_card, String description, boolean show) {
		this.board.build_show_card(type_card, description, show);
	}

	@Override
	public void update_want_to_trade(Player emitter, ArrayList<Player> players, boolean show) {
		this.board.build_middle_trade_player_chose(emitter, players, show);
	}

	@Override
	public void update_start_trade(Player first, Player second, boolean show) {
		this.board.build_middle_trade_start(first, second, show);
	}

	@Override
	public void update_trade_change_properties(int number_property, boolean add) {
		this.board.add_versus_terrain_trade(number_property, add);
	}

	@Override
	public void update_trade_change_money(int money) {
		this.board.change_money_trade(money);
	}

	@Override
	public void update_trade_validate(boolean versus, boolean validate) {
		this.board.change_validate_status(versus, validate);
	}

	@Override
	public void update_pause_game(boolean pause) {
		this.pause = pause;
		repaint();
	}

	@Override
	public void update_repaint_root() {
		repaint();
	}

	@Override
	public void update_fire_go() {
		is_building = false;

		board.setVisible(true);
		ui.setVisible(true);
		revalidate();
		repaint();

	}

	@Override
	public void update_message_receive(String message) {
		this.ui.add_message_receive(message);
	}

	@Override
	public void update_bankrupty(String owner, int sum, String debter) {
		controler.send_bankruptcy(owner, sum, debter);
	}

	@Override
	public void update_start_panel_steal_card(Player stealer, ArrayList<Player> players, boolean show) {
		this.board.build_middle_for_steal(stealer, players, show);
	}

	@Override
	public void update_start_panel_teleport_station(ArrayList<Station_terrain> stations, boolean show) {
		this.board.build_middle_station_teleport(stations, show);
	}

	@Override
	public void update_start_panel_chose_nb_dice(boolean show) {
		this.board.build_middle_nb_dice_chose(show);
	}

}
