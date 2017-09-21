package mvc_game_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import UI_elements.My_JButton;
import mvc_game_controler.Game_controler;
import mvc_game_model.Player;
import settings.Setting_configuration;
import utils.Max_length_text_document;
import utils.Resources;
import view.Window;

@SuppressWarnings("serial")
public class UI_view extends JPanel {

	private int width;
	private int height;
	private ArrayList<Player_view> players;
	private ArrayList<Dice_view> dices;
	private Game_controler ref_controler;
	private Board_view ref_board;
	private JTextArea text_area;

	volatile private boolean dice_animation_running;

	/**
	 * Constructeur de la classe représentant la vue de l'UI du jeu
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 * @param controler
	 *            la référence sur le controleur du jeu
	 * @param win_ref
	 *            la référence sur la fenetre principale
	 */
	public UI_view(int width, int height, Game_controler controler, Window win_ref) {
		this.width = width;
		this.height = height;
		this.ref_controler = controler;
		this.dice_animation_running = false;

		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(0, 0, 0, 5));
		this.players = new ArrayList<Player_view>();
		this.dices = new ArrayList<Dice_view>();

	}

	/**
	 * Méthode permettant de lier la vue du plateau pour faire transiter les
	 * informations
	 * 
	 * @param ref
	 *            la référence sur la vue du plateau
	 */
	public void bind_board_view_ref(Board_view ref) {
		this.ref_board = ref;
	}

	/**
	 * Méthode permettant d'ajouter une propriété sur un joueur via son pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur auquel ajouter la propriété
	 * @param position
	 *            la position du terrain que l'on veut ajouter
	 */
	public void add_property_to_player(String pseudo, int position) {
		for (Player_view player : players) {
			if (player.get_pseudo().equals(pseudo)) {
				player.add_property(ref_board.get_board_terrain_from_position(position));
			}
		}
	}

	/**
	 * Méthode permettant de retirer une propriété sur un joueur via son pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur auquel retirer la propriété
	 * @param position
	 *            la position du terrain que l'on veut retirer
	 */
	public void remove_property_to_player(String pseudo, int position) {
		for (Player_view player : players) {
			if (player.get_pseudo().equals(pseudo)) {
				player.remove_property(ref_board.get_board_terrain_from_position(position));
			}
		}
	}

	/**
	 * Méthode permettant de construire toutes la partie gauche du plateau avec
	 * les informations qui sont les pseudos
	 * 
	 * @param players
	 *            les pseudos des différents joueurs
	 * @param your_pseudo
	 *            le pseudo du client
	 */
	public void build_ui(ArrayList<Player> players, String your_pseudo) {
		int h_players = (int) (height * 0.75);
		int h_informations = (int) (height * 0.19);
		int h_rest = (int) (height * 0.06);

		JPanel players_part = new JPanel();
		players_part.setBackground(Color.WHITE);
		players_part.setPreferredSize(new Dimension(width, h_players));
		players_part.setLayout(new GridLayout(players.size(), 1, 6, 6));

		for (Player p : players) {
			Player_view p_v = new Player_view(p.get_position_playing(), width, h_players / players.size(),
					p.get_pseudo(), p.get_money(), p.get_pseudo().equals(your_pseudo), p.get_position(), ref_controler);
			p_v.set_turn(p.is_playing());
			this.players.add(p_v);
			players_part.add(p_v);
		}

		JPanel informations_part = new JPanel();
		informations_part.setPreferredSize(new Dimension(width, h_informations));
		informations_part.setLayout(new GridLayout(2, 1));

		JPanel dice_informations = new JPanel();
		dice_informations.setLayout(new GridLayout(1, Setting_configuration.nb_dices, 50, 50));
		dice_informations.setBorder(new EmptyBorder(0, 35, 0, 35));
		if (Setting_configuration.nb_dices == 2) {
			dice_informations.setBorder(new EmptyBorder(10, 110, 10, 110));
		}

		for (int i = 0; i < Setting_configuration.nb_dices; i++) {
			Dice_view dice = new Dice_view();
			this.dices.add(dice);
			dice_informations.add(dice);
		}

		BorderLayout msg_layout = new BorderLayout();
		msg_layout.setHgap(2);
		msg_layout.setVgap(2);
		JPanel msg_informations = new JPanel();
		msg_informations.setLayout(msg_layout);
		msg_informations.setOpaque(false);

		int h_base = h_informations / 2;
		int h_dialog_text = (int) (h_base * 0.7f);
		int h_writing_area = (int) (h_base * 0.3f);

		text_area = new JTextArea();
		text_area.setLineWrap(true);
		text_area.setWrapStyleWord(true);
		text_area.setBackground(Color.WHITE);
		text_area.setEditable(false);
		text_area.setOpaque(true);

		JScrollPane logScroller = new JScrollPane();
		logScroller.setBorder(BorderFactory.createTitledBorder("instant messaging"));
		logScroller.setViewportView(text_area);
		logScroller.setPreferredSize(new Dimension(width, h_dialog_text));
		logScroller.setBorder(new LineBorder(Color.BLACK, 2));
		logScroller.setBackground(Color.WHITE);

		BorderLayout layout_inputs = new BorderLayout();
		layout_inputs.setHgap(5);
		layout_inputs.setVgap(5);
		JPanel inputs = new JPanel();
		inputs.setPreferredSize(new Dimension(width, h_writing_area));
		inputs.setLayout(layout_inputs);

		int w_inp = (int) (width * 0.8);
		int w_but = (int) (width * 0.2);

		Max_length_text_document max_length = new Max_length_text_document();
		max_length.set_max_chars(200);

		JTextField write = new JTextField();
		write.setDocument(max_length);
		write.setPreferredSize(new Dimension(w_inp - 10, h_writing_area));
		write.setBorder(new LineBorder(Color.BLACK, 2));
		write.setBackground(Color.WHITE);
		write.setOpaque(true);
		write.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (write.getText().length() > 0) {
						System.out.println("HERE");
						ref_controler.want_send_message(write.getText());
						write.setText("");
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		My_JButton btn = new My_JButton("Send", 13.f);
		btn.setBorderPainted(true);
		btn.setContentAreaFilled(true);
		btn.setBackground(Color.WHITE);
		btn.setOpaque(true);
		btn.setBorder(new LineBorder(Color.BLACK, 1));
		btn.setPreferredSize(new Dimension(w_but, h_writing_area));
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (write.getText().length() > 0) {
					ref_controler.want_send_message(write.getText());
					write.setText("");
				}
			}
		});

		inputs.add(write, BorderLayout.WEST);
		inputs.add(btn, BorderLayout.EAST);

		msg_informations.add(logScroller, BorderLayout.NORTH);
		msg_informations.add(inputs, BorderLayout.CENTER);

		informations_part.add(dice_informations);
		informations_part.add(msg_informations);

		JPanel rest_part = new JPanel();
		rest_part.setBorder(new EmptyBorder(5, 10, 5, 10));
		rest_part.setPreferredSize(new Dimension(width, h_rest));
		rest_part.setLayout(new GridLayout(2, 3, 5, 5));

		JButton surrend = new JButton("Surrend");
		surrend.setFont(Resources.get_font("kabel.ttf").deriveFont(11.f));
		surrend.setBorder(new LineBorder(Color.BLACK, 2));
		surrend.setBackground(new Color(117, 138, 119));
		surrend.setForeground(Color.WHITE);
		surrend.setRolloverEnabled(false);
		surrend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.set_true_voluntary_surrend();
				ref_controler.surrend();
				ref_controler.repaint_root();
			}
		});

		JButton end_turn = new JButton("End turn");
		end_turn.setFont(Resources.get_font("kabel.ttf").deriveFont(11.f));
		end_turn.setBorder(new LineBorder(Color.BLACK, 2));
		end_turn.setBackground(new Color(117, 138, 119));
		end_turn.setForeground(Color.WHITE);
		end_turn.setRolloverEnabled(false);
		end_turn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.next_player();
				ref_controler.repaint_root();
			}
		});

		JButton roll_dice = new JButton("Roll dices");
		roll_dice.setFont(Resources.get_font("kabel.ttf").deriveFont(11.f));
		roll_dice.setBorder(new LineBorder(Color.BLACK, 2));
		roll_dice.setBackground(new Color(117, 138, 119));
		roll_dice.setForeground(Color.WHITE);
		roll_dice.setRolloverEnabled(false);
		roll_dice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.roll_dice();
				ref_controler.repaint_root();
			}
		});

		JButton trade = new JButton("Trade");
		trade.setFont(Resources.get_font("kabel.ttf").deriveFont(11.f));
		trade.setBorder(new LineBorder(Color.BLACK, 2));
		trade.setBackground(new Color(117, 138, 119));
		trade.setForeground(Color.WHITE);
		trade.setRolloverEnabled(false);
		trade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.want_launch_trade();
				ref_controler.repaint_root();
			}
		});

		JButton pause = new JButton("Pause");
		pause.setFont(Resources.get_font("kabel.ttf").deriveFont(11.f));
		pause.setBorder(new LineBorder(Color.BLACK, 2));
		pause.setBackground(new Color(117, 138, 119));
		pause.setForeground(Color.WHITE);
		pause.setRolloverEnabled(false);
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pause.getText().equals("Pause")) {
					if (ref_controler.ask_pause(true)) {
						pause.setText("Resume");
					}
				} else {
					if (ref_controler.ask_pause(false)) {
						pause.setText("Pause");
					}
				}
				ref_controler.repaint_root();
			}
		});

		rest_part.add(roll_dice);
		rest_part.add(surrend);
		rest_part.add(trade);
		rest_part.add(end_turn);
		rest_part.add(pause);
		rest_part.add(new JLabel());

		this.add(players_part, BorderLayout.NORTH);
		this.add(informations_part, BorderLayout.CENTER);
		this.add(rest_part, BorderLayout.SOUTH);

		this.revalidate();
		this.repaint();
	}

	/**
	 * Méthode permettant d'ajouter un message a notre mesage de réseau
	 * 
	 * @param message
	 *            le message a ajouter
	 */
	public void add_message_receive(String message) {
		text_area.append(message + "\n");
	}

	/**
	 * Méthode permettant de récupérer le joueur via son pseudo dans la liste
	 * des Player_view
	 * 
	 * @param pseudo
	 *            le pseudo du joueru que l'on veut récupérer
	 * @return la référence sur le player_view
	 */
	public Player_view get_player_from_pseudo(String pseudo) {
		for (Player_view p : players) {
			if (p.get_pseudo().equals(pseudo)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Méthode qui permet de désactiver l'animation des dés
	 */
	public void disable_animation() {
		this.dice_animation_running = false;
	}

	/**
	 * Méthode qui permet d'activer l'animation des dés
	 */
	public void launch_animation() {
		this.dice_animation_running = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (dice_animation_running) {
					Random rand = new Random();
					for (Dice_view dice : dices) {
						int value = rand.nextInt(6) + 1;
						dice.set_value(value);
					}
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * Méthode permettant de définir si un joueur avec un pseudo spécifique est
	 * en prison ou non
	 * 
	 * @param player
	 *            le pseudo du joueur
	 * @param in_jail
	 *            la valeur booléenne a attribuer
	 */
	public void set_player_jail(String player, boolean in_jail) {
		for (Player_view v : players) {
			if (v.get_pseudo().equals(player)) {
				v.set_in_jail(in_jail);
			}
		}
	}

	/**
	 * Méthode permettant de définir le nombre de maison sur un terrain d'un
	 * joueur avec un pseudo spécifique
	 * 
	 * @param player
	 *            le pseudo du joueur cible
	 * @param number
	 *            le numéro de la propriété cible
	 * @param nb_house
	 *            le nombre de maison que l'on souhaite mettre
	 */
	public void set_nb_house_terrain(String player, int number, int nb_house) {
		for (Player_view v : players) {
			if (v.get_pseudo().equals(player)) {
				v.set_nb_house_terrain(number, nb_house);
			}
		}
	}

	/**
	 * Méthode permettant de définir l'hypotheque ou la déhypothque d'un terrain
	 * par rapport à un joueur
	 * 
	 * @param player
	 *            le pseudo du joueur cible
	 * @param number
	 *            le numéro de la propriété
	 * @param mortgaging
	 *            valeure booleenne a attribuer
	 */
	public void mortgage_terrain(String player, int number, boolean mortgaging) {
		for (Player_view v : players) {
			if (v.get_pseudo().equals(player)) {
				if (mortgaging) {
					v.mortgage_from_number(number);
				} else {
					v.unmortgage_from_number(number);
				}
			}
		}
	}

	/**
	 * Méthode permettant de choisir les valeurs des dés et de les mettre dans
	 * la vue
	 * 
	 * @param values
	 *            la valeur des dés dans un vecteur
	 */
	public void set_dices_values(Vector<Integer> values) {

		while (dice_animation_running) {
		}

		for (int i = 0; i < values.size(); i++) {
			this.dices.get(i).set_value(values.get(i));
		}
	}

	/**
	 * Méthode qui change l'argent d'un joueur
	 * 
	 * @param pseudo
	 *            le pseudo d'un joueur
	 * @param money
	 *            l'argent a lui mettre
	 */
	public void change_money_player(String pseudo, int money) {
		for (Player_view p : this.players) {
			if (p.get_pseudo().equals(pseudo)) {
				p.set_money(money);
			}
		}
	}

	/**
	 * Méthode qui change le message d'un joueur
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param msg
	 *            le message a insérer
	 */
	public void change_msg_player(String pseudo, String msg) {
		for (Player_view p : this.players) {
			if (p.get_pseudo().equals(pseudo)) {
				p.set_msg_info(msg);
			}
		}
	}

	/**
	 * Méthode qui permet de changer le tour d'un joueur
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui va avoir la main
	 */
	public void change_player_turn(String pseudo) {
		for (Player_view p : this.players) {
			p.set_turn(false);
			if (p.get_pseudo().equals(pseudo)) {
				p.set_turn(true);
			}
		}
	}

	/**
	 * Méthode qui permet de changer le status d'un joueur en abandon
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui a abandonné
	 */
	public void change_player_surrend(String pseudo) {
		for (Player_view p : this.players) {
			if (p.get_pseudo().equals(pseudo)) {
				p.set_surrend(true);
			}
		}
	}

}
