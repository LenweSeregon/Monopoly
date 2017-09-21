package mvc_game_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import UI_elements.My_JLabel;
import mvc_game_controler.Game_controler;
import utils.Resources;

@SuppressWarnings("serial")
public class Player_view extends JPanel {

	private String pseudo;
	private int money;

	private My_JLabel pseudo_lab;
	private My_JLabel money_lab;

	private My_JLabel msg_info;

	private boolean turn;
	private boolean surrend;
	private boolean in_jail;

	private int position;

	private int width;
	private int height;

	private Card_shower card_shower;

	/**
	 * Constructeur de la classe permettant de réprésenter les informations
	 * relative a un joueur tel que son nom, son argent, ses propriétés, etc...
	 * 
	 * @param position_playing
	 *            la position de jeu du jouerur
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param money
	 *            l'argent du joueur
	 * @param enlight
	 *            est ce que son nom doit etre en rouge(client)
	 * @param position
	 *            la position du joueur dans le plateau
	 * @param ref_controler
	 *            une référence vers le controleur du jeu
	 */
	public Player_view(int position_playing, int width, int height, String pseudo, int money, boolean enlight,
			int position, Game_controler ref_controler) {

		this.width = width;
		this.height = height;

		this.pseudo = pseudo;
		this.money = money;
		this.turn = false;
		this.surrend = false;
		this.in_jail = false;
		this.position = position;

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));

		int h_info = (int) (0.20 * height);
		int h_rest = (int) (0.67 * height);
		int h_msg = (int) (0.13 * height);

		this.pseudo_lab = new My_JLabel(pseudo, 16.f);
		this.pseudo_lab.setHorizontalAlignment(JLabel.LEFT);
		if (enlight) {
			pseudo_lab.setForeground(Color.RED);
		}

		this.money_lab = new My_JLabel("M" + money, 15.f);
		this.money_lab.setHorizontalAlignment(JLabel.RIGHT);
		this.money_lab.setPreferredSize(new Dimension((int) (width * 0.25), h_info));
		this.money_lab.setForeground(Color.WHITE);

		this.msg_info = new My_JLabel("", 11.f);
		this.msg_info.setFont(this.msg_info.getFont().deriveFont(Font.PLAIN));

		JPanel info = new JPanel();
		info.setPreferredSize(new Dimension(width, h_info));
		info.setLayout(new GridLayout(1, 2));
		info.setBorder(new EmptyBorder(2, 10, 2, 10));
		info.setBackground(new Color(117, 138, 119));

		JPanel player_name_icon = new JPanel();
		player_name_icon.setBorder(new LineBorder(Color.BLACK, 2));
		player_name_icon.setPreferredSize(new Dimension((int) (width * 0.75), h_info));
		player_name_icon.setLayout(new GridLayout(1, 2));

		pseudo_lab.setHorizontalAlignment(JLabel.LEFT);

		Image img = new ImageIcon(Resources.get_image("pion/" + position_playing + ".png")).getImage();
		Image newimg = img.getScaledInstance((width / 4) - 50, (int) h_info - (h_info / 2),
				java.awt.Image.SCALE_SMOOTH);

		JLabel piece = new JLabel();
		piece.setIcon(new ImageIcon(newimg));
		piece.setHorizontalAlignment(JLabel.CENTER);

		player_name_icon.add(piece);
		player_name_icon.add(pseudo_lab);

		info.add(player_name_icon);
		info.add(money_lab);

		JPanel rest = new JPanel();
		rest.setPreferredSize(new Dimension(width, h_rest));
		rest.setLayout(new BorderLayout());
		rest.setBorder(new EmptyBorder(15, 10, 15, 10));

		this.card_shower = new Card_shower(ref_controler, this, width, h_rest);
		rest.add(card_shower, BorderLayout.CENTER);

		JPanel msg = new JPanel();
		msg.setPreferredSize(new Dimension(width, h_msg));
		msg.setLayout(new BorderLayout());
		msg.setBorder(new EmptyBorder(2, 10, 2, 10));

		msg.add(msg_info, BorderLayout.CENTER);

		this.add(info, BorderLayout.NORTH);
		this.add(rest, BorderLayout.CENTER);
		this.add(msg, BorderLayout.SOUTH);

		this.repaint();
	}

	/**
	 * Méthode permettant de choisir le nombre de maison sur un terrain dans le
	 * cardshower
	 * 
	 * @param number
	 *            le numéro de la propriété
	 * @param nb_house
	 *            le nombre de maison sur la propriété
	 */
	public void set_nb_house_terrain(int number, int nb_house) {
		this.card_shower.set_nb_house(number, nb_house);
	}

	/**
	 * Méthode permettant de mettre en hypothéque un terrain par rapport a son
	 * numéro
	 * 
	 * @param number
	 *            le numéro de la propriété
	 */
	public void mortgage_from_number(int number) {
		this.card_shower.mortgage_from_number(number);
	}

	/**
	 * Méthode permettant de mettre en déhypothéque un terrain par rapport a son
	 * numéro
	 * 
	 * @param number
	 *            le numéro de la propriété
	 */
	public void unmortgage_from_number(int number) {
		this.card_shower.unmortgage_from_number(number);
	}

	/**
	 * Méthode permettant d'ajouter une propriété dans le card shower de notre
	 * joueur
	 * 
	 * @param terrain
	 *            la référence du terrain que l'on veut ajouter
	 */
	public void add_property(Abstract_board_terrain_view terrain) {
		this.card_shower.add_miniature(terrain);
	}

	/**
	 * Méthode permettant de retirer une propriété dans le card shower de notre
	 * joueur
	 * 
	 * @param terrain
	 *            la référence du terrain que l'on veut retirer
	 */
	public void remove_property(Abstract_board_terrain_view terrain) {
		this.card_shower.remove_miniature(terrain);
	}

	/**
	 * Méthode permettant de définir la position du joueur dans le jeu
	 * 
	 * @param pos
	 *            la position du joueur dans le jeu
	 */
	public void set_position(int pos) {
		this.position = pos;
	}

	/**
	 * La méthode permettnt de récupérer la position du joueur dans le jeu
	 * 
	 * @return la position dans le jeu
	 */
	public int get_position() {
		return this.position;
	}

	/**
	 * Méthode permettant de définir si c'est au tour du joueur ou non
	 * 
	 * @param turn
	 *            la valeure booléeenne pour le tour
	 */
	public void set_turn(boolean turn) {
		this.turn = turn;
		this.repaint();
	}

	/**
	 * Méthode permettant de définir si le joueur a abandonner ou non
	 * 
	 * @param sur
	 *            la valeure booléenne pour l'abandon
	 */
	public void set_surrend(boolean sur) {
		this.surrend = sur;
		this.repaint();
	}

	/**
	 * Méthode permettant de définir si le joueur doit etre affiché comme en
	 * prison ou non
	 * 
	 * @param b
	 *            la valeure booléenne pour la prison
	 */
	public void set_in_jail(boolean b) {
		this.in_jail = b;
		this.repaint();
	}

	/**
	 * Méthode permettant de récupérer le pseudo
	 * 
	 * @return le pseudo du joueur
	 */
	public String get_pseudo() {
		return this.pseudo;
	}

	/**
	 * Méthode permettant de définir le message d'information du joueur
	 * 
	 * @param msg
	 *            le message a afficher
	 */
	public void set_msg_info(String msg) {
		this.msg_info.setText("<html><div style='text-align: center;'>" + msg + "</div></html>");

		this.msg_info.revalidate();
		this.revalidate();
	}

	/**
	 * Méthode permettant de définir l'argent à afficher
	 * 
	 * @param money
	 *            l'argent du joueur a afficher
	 */
	public void set_money(int money) {
		new Thread(new Money_changer(this.money, money)).start();
		this.money = money;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (!turn) {
			g2.setColor(new Color(50, 50, 50, 210));
			g2.fillRect(0, 0, width, height);
		}

		if (surrend) {
			g2.setColor(Color.RED);
			g2.drawLine(0, 0, width, height);
			g2.drawLine(width, 0, 0, height);
		}

		if (in_jail) {
			g2.drawImage(Resources.get_image("jailing.png"), 0, 0, width, height, this);
		}

	}

	public class Money_changer implements Runnable {

		private int start;
		private int end;

		public Money_changer(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			int adder = 0;
			if (start < end) {
				adder = 100;
			} else {
				adder = -100;
			}

			while (start != end) {
				if (Math.abs(start - end) < 100) {
					if (adder > 0) {
						start += Math.abs(start - end);
					} else {
						start -= Math.abs(start - end);
					}
				} else {
					start += adder;
				}
				money_lab.setText("M" + start);

				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
