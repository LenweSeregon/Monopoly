package mvc_game_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import mvc_game_controler.Game_controler;
import mvc_game_enums.Buy_order;
import mvc_game_enums.Card_type;
import mvc_game_enums.Color_terrain;
import mvc_game_enums.Jail_manage_result;
import mvc_game_enums.Terrain_type;
import mvc_game_model.Player;
import mvc_game_model_terrains.Abstract_terrain;
import mvc_game_model_terrains.Buyable_terrain;
import mvc_game_model_terrains.Company_terrain;
import mvc_game_model_terrains.Property_terrain;
import mvc_game_model_terrains.Station_terrain;
import settings.Setting_configuration;
import utils.Resources;

@SuppressWarnings("serial")
public class Middle_board extends JPanel {

	private int width;
	private int height;

	private JPanel container;
	private Game_controler ref_controler;

	private My_JLabel player_1_price;
	private My_JLabel player_2_price;
	private My_JLabel player_3_price;
	private My_JLabel player_4_price;

	private JTextField field;

	private ArrayList<String> pseudos;
	private ArrayList<Integer> values;

	private ArrayList<Integer> to_display_versus_trade;
	private JPanel versus_prop;
	private ArrayList<Buyable_terrain> terrains_versus;
	private My_JLabel money_given_versus;
	private boolean validate_versus;
	private boolean validate_client;
	private JPanel left;
	private JPanel right;

	/**
	 * Constructeur de la classe qui représente le milieu du plateau de jeu et
	 * c'est l'endroit ou on va faire apparaitre les informations des zooms et
	 * quelques actions
	 * 
	 * @param controler
	 *            la référence sur le controleur du jeu
	 * @param ref_board
	 *            la référence sur la partie plateau
	 * @param pos_x
	 *            la position X du milieu
	 * @param pos_y
	 *            la position Y du milieu
	 * @param width
	 *            la largeur du milieu
	 * @param height
	 *            la hauteur du milieu
	 */
	public Middle_board(Game_controler controler, Board_view ref_board, int pos_x, int pos_y, int width, int height) {
		this.ref_controler = controler;
		this.setBounds(pos_x, pos_y, width, height);
		this.setBackground(Color.BLUE);
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(120, 175, 120, 175));

		this.width = width;
		this.height = height;

		container = new JPanel();
		container.setBackground(Color.WHITE);
		container.setBorder(new LineBorder(Color.BLACK, 2));
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * Méthode permettant de vider le milieu du plateau
	 */
	public void build_center_empty() {

		container.setVisible(false);
		ref_controler.repaint_root();
	}

	/**
	 * Méthode permettant de construire le milieu du plateau pour afficher le
	 * nombre de dés pouvant être utilisé
	 */
	public void build_chose_nb_dice() {
		this.setBorder(new EmptyBorder(160, 175, 160, 175));

		JPanel sub_container = new JPanel();
		sub_container.setPreferredSize(new Dimension(width, height));
		sub_container.setLayout(new GridLayout(4, 1, 0, 0));
		sub_container.setBorder(new EmptyBorder(130, 50, 130, 50));
		sub_container.setBackground(new Color(117, 138, 119));

		My_JLabel title = new My_JLabel("How many dice you want ?", 15.f);
		title.setBackground(Color.LIGHT_GRAY);
		title.setForeground(Color.WHITE);
		sub_container.add(title);

		int i = 1;
		for (; i <= Setting_configuration.nb_dices; i++) {
			int it = i;
			My_JLabel lab = new My_JLabel("" + it, 20.f);
			lab.setHorizontalAlignment(JLabel.LEFT);
			lab.setOpaque(true);
			lab.setBackground(Color.WHITE);
			lab.setForeground(new Color(117, 138, 119));

			lab.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lab.setBackground(Color.WHITE);
					lab.setForeground(new Color(117, 138, 119));
					lab.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					lab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lab.setBackground(Color.DARK_GRAY);
					lab.setForeground(Color.WHITE);

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					ref_controler.chosen_nb_dice(it);
				}
			});
			sub_container.add(lab);

		}

		for (; i < 4; i++) {
			JLabel lab = new JLabel();
			lab.setBackground(Color.WHITE);
			lab.setOpaque(true);
			sub_container.add(lab);
		}

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	/**
	 * Méthode permettant de construire le milieu du plateau pour que celui ci
	 * permette de choisir un lieu de téléportation
	 * 
	 * @param station
	 *            les différentes station ou l'on peut se téléporter
	 */
	public void build_teleport_station(ArrayList<Station_terrain> stations) {
		this.setBorder(new EmptyBorder(140, 175, 140, 175));

		JPanel sub_container = new JPanel();
		sub_container.setPreferredSize(new Dimension(width, height));
		sub_container.setLayout(new GridLayout(5, 1, 0, 0));
		sub_container.setBorder(new EmptyBorder(130, 50, 130, 50));
		sub_container.setBackground(new Color(117, 138, 119));

		My_JLabel title = new My_JLabel("Where do you want to go?", 15.f);
		title.setBackground(Color.LIGHT_GRAY);
		title.setForeground(Color.WHITE);
		sub_container.add(title);

		for (Station_terrain s : stations) {

			My_JLabel lab = new My_JLabel(s.get_name(), 15.f);
			lab.setHorizontalAlignment(JLabel.LEFT);
			lab.setOpaque(true);
			lab.setBackground(Color.WHITE);
			lab.setForeground(new Color(117, 138, 119));

			lab.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lab.setBackground(Color.WHITE);
					lab.setForeground(new Color(117, 138, 119));
					lab.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					lab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					lab.setBackground(Color.DARK_GRAY);
					lab.setForeground(Color.WHITE);

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					ref_controler.chosen_station_teleport(s.get_number());
				}
			});
			sub_container.add(lab);
		}

		My_JLabel lab = new My_JLabel("Cancel", 15.f);
		lab.setHorizontalAlignment(JLabel.LEFT);
		lab.setOpaque(true);
		lab.setBackground(Color.WHITE);
		lab.setForeground(new Color(117, 138, 119));

		lab.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lab.setBackground(Color.WHITE);
				lab.setForeground(new Color(117, 138, 119));
				lab.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lab.setBackground(Color.DARK_GRAY);
				lab.setForeground(Color.WHITE);

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				ref_controler.chosen_station_teleport(-1);
			}
		});
		sub_container.add(lab);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	/**
	 * Méthode permettant de savoir si un joueur n'a aucune maison sur un groupe
	 * de couleur donné
	 * 
	 * @param p
	 *            le pseudo du joueur pour le test
	 * @param terrains
	 *            ces terrains
	 * @param color
	 *            la couleur de terrain que l'on veut tester
	 * @return vrai si le joueur ne dispose d'aucune maison sur les terrains de
	 *         couleur, faux sinon
	 */
	private boolean no_house_on_color(Player p, ArrayList<Buyable_terrain> terrains, Color_terrain color) {
		for (Buyable_terrain terrain : terrains) {
			if (terrain.get_type() == Terrain_type.PROPERTY) {
				Property_terrain prop = (Property_terrain) terrain;
				if (prop.get_color() == color && prop.get_nb_house() > 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Méthode qui permet de savoir combien de gare posséde le joueur avec le
	 * pseudo en parametre
	 * 
	 * @param p
	 *            le pseudo du joueur dont on veut l'information
	 * @return le nombre de gare possédé par le joueur
	 */
	public int get_nb_station_owned_by_player(Player p) {
		int i = 0;
		for (Buyable_terrain terrain : p.get_terrains_owned()) {
			if (terrain.get_type() == Terrain_type.STATION) {
				i++;
			}
		}
		return i;
	}

	/**
	 * Méthode qui permet de savoir combien de compagnie posséde le joueur avec
	 * le pseudo en parametre
	 * 
	 * @param p
	 *            le pseudo du joueur dont on veut l'information
	 * @return le nombre de compagnie possédé par le joueur
	 */
	public int get_nb_company_owned_by_player(Player p) {
		int i = 0;
		for (Buyable_terrain terrain : p.get_terrains_owned()) {
			if (terrain.get_type() == Terrain_type.COMPANY) {
				i++;
			}
		}
		return i;
	}

	/**
	 * Méthode permettant de créer le milieu du plateau pour qu'on puisse
	 * choisir les cartes à voler chez les adversaires
	 * 
	 * @param stealer
	 *            le joueur qui va voler la carte
	 * @param players
	 *            les joueurs a voler
	 */
	public void build_steal_card(Player stealer, ArrayList<Player> players) {
		this.setBorder(new EmptyBorder(70, 50, 70, 50));

		BorderLayout main_l = new BorderLayout();
		main_l.setHgap(20);
		main_l.setVgap(20);
		JPanel sub_container = new JPanel();
		sub_container.setPreferredSize(new Dimension(width, height));
		sub_container.setLayout(main_l);
		sub_container.setBackground(new Color(117, 138, 119));

		int h_base = (int) (height - 140);
		int w_base = (int) (width - 100);

		int h_h = (int) (h_base * 0.15f);
		int h_c = (int) (h_base * 0.70f);
		int h_f = (int) (h_base * 0.15f);

		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		header.setPreferredSize(new Dimension(w_base, h_h));

		My_JLabel header_tit = new My_JLabel("Click on property or cancel button", 15.f);
		header.add(header_tit, BorderLayout.CENTER);

		JPanel footer = new JPanel();
		footer.setLayout(new BorderLayout());
		footer.setPreferredSize(new Dimension(w_base, h_f));

		My_JButton cancel = new My_JButton("Cancel", 15.f);
		cancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.chosen_stole(null, null, -1);
			}
		});

		footer.add(cancel, BorderLayout.CENTER);

		BorderLayout pans_layout = new BorderLayout();
		pans_layout.setHgap(20);
		pans_layout.setVgap(20);
		JPanel container_pans = new JPanel();
		container_pans.setPreferredSize(new Dimension(w_base, h_c));
		container_pans.setLayout(pans_layout);
		container_pans.setBackground(new Color(117, 138, 119));

		int w_pan = (int) (w_base * 0.33) - 5;

		String[] adding = new String[] { BorderLayout.WEST, BorderLayout.CENTER, BorderLayout.EAST };
		int it = 0;
		for (Player p : players) {
			if (!p.get_pseudo().equals(stealer.get_pseudo())) {
				JPanel pan = new JPanel();
				pan.setBorder(new LineBorder(Color.BLACK, 2));
				pan.setPreferredSize(new Dimension(w_pan, h_c));
				pan.setLayout(new BorderLayout());
				pan.setBackground(Color.WHITE);

				int h_title = (int) (h_c * 0.20);
				int h_props = (int) (h_c * 0.80);

				JPanel title = new JPanel();
				title.setPreferredSize(new Dimension(w_pan, h_title));
				title.setLayout(new BorderLayout());

				My_JLabel title_value = new My_JLabel(p.get_pseudo(), 15.f);

				title.add(title_value, BorderLayout.CENTER);

				JPanel props = new JPanel();
				for (Buyable_terrain terrain : p.get_terrains_owned()) {

					if (terrain instanceof Property_terrain) {
						if (!no_house_on_color(p, p.get_terrains_owned(), ((Property_terrain) terrain).get_color())) {
							continue;
						}
					} else if (terrain instanceof Station_terrain) {
						if (get_nb_station_owned_by_player(p) == 4) {
							continue;
						}
					} else if (terrain instanceof Company_terrain) {
						if (get_nb_company_owned_by_player(p) == 2) {
							continue;
						}
					}

					JPanel pan_lab = new JPanel();
					pan_lab.setPreferredSize(new Dimension(w_base, 20));
					pan_lab.setLayout(new BorderLayout());

					JLabel jLabel = new JLabel(terrain.get_name());
					jLabel.setOpaque(true);
					jLabel.setHorizontalAlignment(JLabel.LEFT);
					jLabel.setBackground(Color.WHITE);
					jLabel.setForeground(new Color(117, 138, 119));
					jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

					jLabel.addMouseListener(new MouseListener() {
						@Override
						public void mouseReleased(MouseEvent e) {
						}

						@Override
						public void mousePressed(MouseEvent e) {
						}

						@Override
						public void mouseExited(MouseEvent e) {
							jLabel.setBackground(Color.WHITE);
							jLabel.setForeground(new Color(117, 138, 119));
							jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

						}

						@Override
						public void mouseEntered(MouseEvent e) {
							jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							jLabel.setBackground(Color.DARK_GRAY);
							jLabel.setForeground(Color.WHITE);

						}

						@Override
						public void mouseClicked(MouseEvent e) {
							ref_controler.chosen_stole(stealer.get_pseudo(), p.get_pseudo(), terrain.get_number());
						}
					});
					pan_lab.add(jLabel, BorderLayout.CENTER);
					props.add(pan_lab);
				}
				BoxLayout boxLayout = new BoxLayout(props, BoxLayout.Y_AXIS);
				props.setLayout(boxLayout);

				JScrollPane jScrollPaneVersus = new JScrollPane(props);
				jScrollPaneVersus.setPreferredSize(new Dimension(w_pan, h_props));
				jScrollPaneVersus.getViewport().setPreferredSize(new Dimension(w_pan, h_props));
				jScrollPaneVersus.setBorder(new MatteBorder(2, 0, 2, 0, Color.BLACK));
				jScrollPaneVersus.setBackground(Color.WHITE);

				pan.add(title, BorderLayout.NORTH);
				pan.add(props, BorderLayout.CENTER);

				container_pans.add(pan, adding[it]);

				it++;
			}
		}

		sub_container.add(header, BorderLayout.NORTH);
		sub_container.add(container_pans, BorderLayout.CENTER);
		sub_container.add(footer, BorderLayout.SOUTH);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	/**
	 * Méthode permettant de construire le milieu du plateau pour qu'on puisse
	 * choisir le joueur avec qui on souhaite commencer l'échange
	 * 
	 * @param emitter
	 *            le joueur qui veut lancer un échange
	 * @param players
	 *            la liste des joueurs avec qui échanger
	 */
	public void build_trade_player_chose(Player emitter, ArrayList<Player> players) {
		this.setBorder(new EmptyBorder(160, 175, 160, 175));

		JPanel sub_container = new JPanel();
		sub_container.setPreferredSize(new Dimension(width, height));
		sub_container.setLayout(new GridLayout(4, 1, 0, 0));
		sub_container.setBorder(new EmptyBorder(130, 50, 130, 50));
		sub_container.setBackground(new Color(117, 138, 119));

		My_JLabel title = new My_JLabel("Which one for trade ?", 17.f);
		title.setBackground(Color.LIGHT_GRAY);
		title.setForeground(Color.WHITE);
		sub_container.add(title);

		int i = 1;
		for (Player p : players) {
			if (!p.get_pseudo().equals(emitter.get_pseudo()) && !p.get_surrend()) {

				My_JLabel lab = new My_JLabel(p.get_pseudo(), 20.f);
				lab.setHorizontalAlignment(JLabel.LEFT);
				lab.setOpaque(true);
				lab.setBackground(Color.WHITE);
				lab.setForeground(new Color(117, 138, 119));

				lab.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
						lab.setBackground(Color.WHITE);
						lab.setForeground(new Color(117, 138, 119));
						lab.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						lab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						lab.setBackground(Color.DARK_GRAY);
						lab.setForeground(Color.WHITE);

					}

					@Override
					public void mouseClicked(MouseEvent e) {
						ref_controler.launch_trade(lab.getText());
					}
				});
				i++;
				sub_container.add(lab);
			}
		}

		for (; i < 4; i++) {
			JLabel lab = new JLabel();
			lab.setBackground(Color.WHITE);
			lab.setOpaque(true);
			sub_container.add(lab);
		}

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();

	}

	/**
	 * Méthode permettant d'initialiser le plateau du milieu a recevoir un
	 * echange
	 */
	public void init_trade() {
		this.to_display_versus_trade = new ArrayList<Integer>();
		validate_versus = false;
		validate_client = false;
	}

	/**
	 * Méthode permettant de mettre a jour dans les echanges la valeur de
	 * l'argent donné par l'adversaire
	 * 
	 * @param money
	 *            la valeur d'argent donnée
	 */
	public void change_versus_money(int money) {
		money_given_versus.setText("M" + money);
		revalidate();
	}

	/**
	 * Méthode permettant d'ajouter un terrain que nous donne l'adversaire
	 * durant l'échange
	 * 
	 * @param number
	 *            le numéro de la propriété que l'adversaire nous donne
	 * @param add
	 *            est ce que le terrain est ajouté ou retirer
	 */
	public void add_versus_terrain(int number, boolean add) {
		if (add) {
			to_display_versus_trade.add(Integer.valueOf(number));
		} else {
			to_display_versus_trade.remove(Integer.valueOf(number));
		}

		int w = width - 260;

		versus_prop.removeAll();

		for (Buyable_terrain terrain : terrains_versus) {
			if (to_display_versus_trade.contains(Integer.valueOf(terrain.get_number()))) {
				JPanel pan = new JPanel();
				pan.setLayout(new BorderLayout());
				pan.setBackground(Color.WHITE);
				pan.setPreferredSize(new Dimension((w / 2) - 15, 20));
				pan.setBorder(new EmptyBorder(0, 5, 0, 0));

				JLabel jLabel = new JLabel(terrain.get_name());
				jLabel.setOpaque(true);
				jLabel.setBackground(Color.WHITE);
				jLabel.setForeground(new Color(117, 138, 119));

				pan.add(jLabel, BorderLayout.CENTER);
				versus_prop.add(pan);
			}
		}

		versus_prop.revalidate();
		versus_prop.repaint();
		revalidate();
		repaint();
	}

	public void change_validate_value(boolean versus, boolean validate) {
		if (versus) {
			validate_versus = validate;
			right.repaint();
		} else {
			validate_client = validate;
			left.repaint();
		}
	}

	/**
	 * Méthode permettant de construire le panel qui représente la fenetre
	 * d'échange
	 * 
	 * @param first
	 *            le premire client de l'échange, ici le client de l'application
	 * @param second
	 *            le second client de l'échange, ici l'adversaire
	 */
	public void build_trade_start(Player first, Player second) {

		this.setBorder(new EmptyBorder(140, 130, 140, 130));
		this.terrains_versus = second.get_terrains_owned();

		JPanel sub_container = new JPanel();
		sub_container.setPreferredSize(new Dimension(width, height));
		sub_container.setLayout(new BorderLayout());
		sub_container.setBackground(new Color(117, 138, 119));

		int h = height - 280;
		int w = width - 260;

		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.setBackground(new Color(117, 138, 119));
		top.setPreferredSize(new Dimension(w, (int) (h * 0.15f)));
		top.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));

		My_JLabel title = new My_JLabel("Trading", 25.f);
		title.setForeground(Color.WHITE);

		top.add(title, BorderLayout.CENTER);

		left = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if (validate_client) {
					g.setColor(new Color(100, 100, 100, 150));
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		left.setLayout(new BorderLayout());
		left.setBackground(Color.WHITE);
		left.setPreferredSize(new Dimension(w / 2, (int) (h * 0.70f)));
		left.setBorder(new MatteBorder(0, 2, 0, 2, Color.BLACK));

		JPanel properties_container_player = new JPanel();
		properties_container_player.setLayout(new BorderLayout());
		properties_container_player.setPreferredSize(new Dimension(w / 2, (int) (h * 0.70f)));

		int height_ref = (int) (h * 0.70f);

		int h_money = (int) (height_ref * 0.30f);
		int h_properties = (int) (height_ref * 0.50f);
		int h_sum = (int) (height_ref * 0.20f);

		JPanel money_trade = new JPanel();
		money_trade.setBackground(Color.WHITE);
		money_trade.setLayout(new GridLayout(2, 1));
		money_trade.setPreferredSize(new Dimension(w / 2, h_money));

		My_JLabel text = new My_JLabel("What you give:", 13.f);

		My_JLabel money_given = new My_JLabel("M0", 13.f);
		money_given.setHorizontalAlignment(JLabel.LEFT);

		money_trade.add(text);
		money_trade.add(money_given);

		JPanel jPanel = new JPanel();

		for (Abstract_terrain terrain : first.get_terrains_owned()) {
			JPanel pan = new JPanel();
			pan.setLayout(new BorderLayout());
			pan.setBackground(Color.YELLOW);
			pan.setPreferredSize(new Dimension((w / 2) - 25, 20));

			JLabel jLabel = new JLabel(terrain.get_name());
			jLabel.setOpaque(true);
			jLabel.setBackground(Color.WHITE);
			jLabel.setForeground(new Color(117, 138, 119));
			jLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			jLabel.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (jLabel.getBackground().equals(Color.WHITE)) {
						if (ref_controler.trade_house_adding(terrain.get_number(), true)) {
							jLabel.setBackground(Color.DARK_GRAY);
							jLabel.setForeground(Color.WHITE);
							left.repaint();
						}
					} else {
						if (ref_controler.trade_house_adding(terrain.get_number(), false)) {
							jLabel.setBackground(Color.WHITE);
							jLabel.setForeground(new Color(117, 138, 119));
							left.repaint();
						}
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});
			pan.add(jLabel, BorderLayout.CENTER);
			jPanel.add(pan);
		}
		BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
		jPanel.setLayout(boxLayout);
		JScrollPane jScrollPane = new JScrollPane(jPanel);
		jScrollPane.setPreferredSize(new Dimension((w / 2) - 22, h_properties));
		jScrollPane.getViewport().setPreferredSize(new Dimension((w / 2) - 25, h_properties));
		jScrollPane.setBorder(new MatteBorder(2, 0, 2, 0, Color.BLACK));
		jScrollPane.setBackground(Color.WHITE);

		JPanel sum_add = new JPanel();
		sum_add.setBackground(Color.WHITE);
		sum_add.setPreferredSize(new Dimension(w / 2, h_sum));
		sum_add.setLayout(new GridLayout(1, 2, 5, 5));
		sum_add.setBorder(new EmptyBorder(20, 10, 20, 10));

		JTextField mon_value = new JTextField();

		My_JButton given_mon = new My_JButton("Give ", 14.f);
		given_mon.setRolloverEnabled(false);
		given_mon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		given_mon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ref_controler.trade_money_change(mon_value.getText())) {
					money_given.setText("M" + mon_value.getText());
				}
			}
		});

		mon_value.setBorder(new LineBorder(Color.BLACK, 2));
		mon_value.setHorizontalAlignment(JTextField.CENTER);
		mon_value.setBackground(Color.WHITE);
		mon_value.setForeground(Color.BLACK);
		mon_value.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (ref_controler.trade_money_change(mon_value.getText())) {
						money_given.setText("M" + mon_value.getText());
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});

		sum_add.add(given_mon);
		sum_add.add(mon_value);

		properties_container_player.add(money_trade, BorderLayout.NORTH);
		properties_container_player.add(jScrollPane, BorderLayout.CENTER);
		properties_container_player.add(sum_add, BorderLayout.SOUTH);

		left.add(properties_container_player, BorderLayout.CENTER);

		right = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if (validate_versus) {
					super.paint(g);
					g.setColor(new Color(100, 100, 100, 150));
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		right.setLayout(new BorderLayout());
		right.setBackground(Color.WHITE);
		right.setPreferredSize(new Dimension(w / 2, (int) (h * 0.70f)));
		right.setBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK));

		JPanel money_trade_versus = new JPanel();
		money_trade_versus.setBackground(Color.WHITE);
		money_trade_versus.setLayout(new GridLayout(2, 1));
		money_trade_versus.setBorder(new EmptyBorder(0, 5, 0, 0));
		money_trade_versus.setPreferredSize(new Dimension(w / 2, h_money));

		My_JLabel text_versus = new My_JLabel("What he give:", 13.f);

		money_given_versus = new My_JLabel("M0", 13.f);
		money_given_versus.setHorizontalAlignment(JLabel.LEFT);

		money_trade_versus.add(text_versus);
		money_trade_versus.add(money_given_versus);

		JPanel properties_container_versus = new JPanel();
		properties_container_versus.setLayout(new BorderLayout());
		properties_container_versus.setPreferredSize(new Dimension(w / 2, (int) (h * 0.70f)));

		versus_prop = new JPanel();
		versus_prop.setBackground(Color.WHITE);

		BoxLayout boxLayoutVersus = new BoxLayout(versus_prop, BoxLayout.Y_AXIS);
		versus_prop.setLayout(boxLayoutVersus);
		JScrollPane jScrollPaneVersus = new JScrollPane(versus_prop);
		jScrollPaneVersus.setPreferredSize(new Dimension((w / 2) - 22, h_properties));
		jScrollPaneVersus.getViewport().setPreferredSize(new Dimension((w / 2) - 22, h_properties));
		jScrollPaneVersus.setBorder(new MatteBorder(2, 0, 2, 0, Color.BLACK));
		jScrollPaneVersus.setBackground(Color.WHITE);

		JPanel sum_add_versus = new JPanel();
		sum_add_versus.setBackground(Color.WHITE);
		sum_add_versus.setPreferredSize(new Dimension(w / 2, h_sum));
		sum_add_versus.setLayout(new GridLayout(1, 2, 5, 5));
		sum_add_versus.setBorder(new EmptyBorder(20, 10, 20, 10));

		properties_container_versus.add(money_trade_versus, BorderLayout.NORTH);
		properties_container_versus.add(jScrollPaneVersus, BorderLayout.CENTER);
		properties_container_versus.add(sum_add_versus, BorderLayout.SOUTH);

		right.add(properties_container_versus, BorderLayout.CENTER);

		JPanel bot = new JPanel();
		bot.setLayout(new BorderLayout());
		bot.setBackground(new Color(117, 138, 119));
		bot.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
		bot.setPreferredSize(new Dimension(w, (int) (h * 0.15f)));

		JPanel bot_buttons = new JPanel();
		bot_buttons.setLayout(new GridLayout(1, 3, 5, 5));
		bot_buttons.setOpaque(false);

		My_JButton btn = new My_JButton("Validate", 17.f);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setForeground(Color.WHITE);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.validate_trade();
			}
		});

		My_JButton btn2 = new My_JButton("Quit", 17.f);
		btn2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn2.setForeground(Color.WHITE);
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.cancel_trade();
			}
		});

		bot_buttons.add(btn);
		bot_buttons.add(new JLabel());
		bot_buttons.add(btn2);

		bot.add(bot_buttons, BorderLayout.CENTER);

		sub_container.add(top, BorderLayout.NORTH);
		sub_container.add(left, BorderLayout.WEST);
		sub_container.add(right, BorderLayout.EAST);
		sub_container.add(bot, BorderLayout.SOUTH);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	/**
	 * Méthode étant appelé lorsque l'on veut construire le milieu du plateau
	 * lorsqu'un joueur est dans l'obligation de payer un joueur
	 * 
	 * @param owner
	 *            le joueur a qui il doit l'argent
	 * @param sum
	 *            la somme d'argent due
	 */
	public void build_pay_center(String owner, int sum) {

		this.setBorder(new EmptyBorder(120, 175, 120, 175));

		JPanel sub_container = new JPanel();
		sub_container.setPreferredSize(new Dimension(width, height));
		sub_container.setLayout(new GridLayout(3, 1, 20, 20));
		sub_container.setBorder(new EmptyBorder(130, 50, 130, 50));

		String own = owner;
		if (owner.equals("Jail_rent")) {
			own = "Jail";
		} else if (owner.equals("Jail_out")) {
			own = "Jail";
		}

		My_JLabel sentence = new My_JLabel("You must pay to " + own, 16.f);

		My_JLabel sum_display = new My_JLabel("M" + sum, 25.f);

		My_JButton pay = new My_JButton("Pay", 25.f);
		pay.setBorder(new LineBorder(Color.BLACK, 2));
		pay.setBorderPainted(true);
		pay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.pay_due();
			}
		});

		sub_container.add(sentence);
		sub_container.add(sum_display);
		sub_container.add(pay);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	/**
	 * Méthode permettent de construire le milieu du plateau pour afficher la
	 * carte qui vient d'etre tiré par le joueur
	 * 
	 * @param type_card
	 *            le type de la carte qui a été tiré
	 * @param description
	 *            la description de la carte
	 */
	public void build_center_display_card(Card_type type_card, String description) {

		this.setBorder(new EmptyBorder(120, 175, 120, 175));

		int height = this.height - 240;

		int h_photo = (int) (height * 0.35);
		int h_title = (int) (height * 0.25) + 20;
		int h_desc = (int) (height * 0.50);

		JPanel sub_container = new JPanel();
		sub_container.setBackground(Color.WHITE);
		sub_container.setLayout(new BorderLayout());
		sub_container.setBorder(new EmptyBorder(10, 10, 10, 10));
		sub_container.setPreferredSize(new Dimension(width, height));

		JPanel photo_pan = new JPanel();
		photo_pan.setPreferredSize(new Dimension(width, h_photo));
		photo_pan.setBackground(Color.WHITE);
		photo_pan.setLayout(new BorderLayout());
		photo_pan.setBorder(new EmptyBorder(0, 10, 0, 10));

		String path = "";
		if (type_card == Card_type.COMMUNAUTY) {
			path = "caisse.jpg";
		} else {
			path = "chance.jpg";
		}
		Image img = Resources.get_image(path);
		Image newimg = img.getScaledInstance(h_photo, h_photo, java.awt.Image.SCALE_SMOOTH);

		JLabel photo = new JLabel();
		photo.setHorizontalAlignment(JLabel.CENTER);
		photo.setIcon(new ImageIcon(newimg));

		photo_pan.add(photo, BorderLayout.CENTER);

		JPanel title_pan = new JPanel();
		title_pan.setPreferredSize(new Dimension(width, h_title));
		title_pan.setBackground(Color.WHITE);
		title_pan.setLayout(new BorderLayout());
		title_pan.setBorder(new EmptyBorder(0, 10, 0, 10));

		String title = "";
		if (type_card == Card_type.COMMUNAUTY) {
			title = "Carte de communauté";
		} else {
			title = "Carte chance";
		}
		My_JLabel tit = new My_JLabel(title, 12.f);

		title_pan.add(tit, BorderLayout.CENTER);

		JPanel desc_pan = new JPanel();
		desc_pan.setPreferredSize(new Dimension(width, h_desc));
		desc_pan.setBackground(Color.WHITE);
		desc_pan.setLayout(new BorderLayout());
		desc_pan.setBorder(new EmptyBorder(10, 10, 10, 10));

		My_JLabel dsc = new My_JLabel("<html><div style='text-align: center;'>" + description + "</div></html>", 14.f);

		desc_pan.add(dsc, BorderLayout.CENTER);

		sub_container.add(photo_pan, BorderLayout.NORTH);
		sub_container.add(title_pan, BorderLayout.CENTER);
		sub_container.add(desc_pan, BorderLayout.SOUTH);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	/**
	 * Méthode étant appelé pour initialiser les différentes variables
	 * d'affichages des actions
	 * 
	 * @param players
	 *            les différents joueurs
	 */
	public void init_auction(ArrayList<Player> players) {
		this.values = new ArrayList<Integer>();
		this.pseudos = new ArrayList<String>();

		for (Player p : players) {
			this.pseudos.add(p.get_pseudo());
			if (p.get_surrend()) {
				this.values.add(-1);
			} else {
				this.values.add(0);
			}
		}
	}

	/**
	 * Méthode étant appelé pour changer une valeur d'enchere d'un joueur
	 * 
	 * @param pseudo
	 *            le pseudo du joueur qui surencherie
	 * @param value
	 *            la valeure
	 */
	public void change_price_player(String pseudo, int value) {
		int k = -1;
		for (int i = 0; i < pseudos.size(); i++) {
			if (pseudos.get(i).equals(pseudo)) {
				k = i;
			}
		}

		if (k == 0) {
			this.player_1_price.setText(pseudo + " : M" + value);
		} else if (k == 1) {
			this.player_2_price.setText(pseudo + " : M" + value);
		} else if (k == 2) {
			this.player_3_price.setText(pseudo + " : M" + value);
		} else if (k == 3) {
			this.player_4_price.setText(pseudo + " : M" + value);
		} else {
			System.err.println("Error");
		}
	}

	/**
	 * Méthode permettant de construire le milieu du plateau de tel sorte qu'il
	 * propose une enchere sur un terrain a image
	 * 
	 * @param title
	 *            le titre a afficher
	 * @param path
	 *            le chemin vers l'image
	 * @param description
	 *            la description de l'image
	 * @param number_house
	 *            le numéro de la propriété en enchere
	 */
	public void build_auction_running_image(String title, String path, String description, int number_house) {
		this.setBorder(new EmptyBorder(60, 175, 60, 175));

		int height_auction = (int) (height * 0.15);
		int height_price = (int) (height * 0.15);

		JPanel auction_container = new JPanel();
		auction_container.setPreferredSize(new Dimension(width, height_auction));
		auction_container.setLayout(new BorderLayout());
		auction_container.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
		auction_container.setBackground(Color.WHITE);

		JPanel auction = new JPanel();
		auction.setBackground(Color.WHITE);
		auction.setPreferredSize(new Dimension(width, height_auction));
		auction.setLayout(new GridLayout(5, 1, 0, 2));
		auction.setBorder(new EmptyBorder(0, 10, 0, 10));

		My_JLabel title_auc = new My_JLabel("Auctions : " + Setting_configuration.minutes_for_auction + "s", 14.f);
		new Thread(new Runnable() {
			@Override
			public void run() {
				int it = Setting_configuration.minutes_for_auction;
				while (it >= 1) {
					title_auc.setText("Auctions : " + it + "s");
					it--;
					revalidate();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		auction.add(title_auc);

		int m = 0;
		for (; m < pseudos.size(); m++) {
			if (m == 0) {
				this.player_1_price = new My_JLabel(pseudos.get(0) + " : M" + values.get(0), 11.f);
				this.player_1_price.setHorizontalAlignment(JLabel.LEFT);
				auction.add(player_1_price);
			} else if (m == 1) {
				this.player_2_price = new My_JLabel(pseudos.get(1) + " : M" + values.get(1), 11.f);
				this.player_2_price.setHorizontalAlignment(JLabel.LEFT);
				auction.add(player_2_price);
			} else if (m == 2) {
				this.player_3_price = new My_JLabel(pseudos.get(2) + " : M" + values.get(2), 11.f);
				this.player_3_price.setHorizontalAlignment(JLabel.LEFT);
				auction.add(player_3_price);
			} else if (m == 3) {
				this.player_4_price = new My_JLabel(pseudos.get(3) + " : M" + values.get(3), 11.f);
				this.player_4_price.setHorizontalAlignment(JLabel.LEFT);
				auction.add(player_4_price);
			}
		}

		for (int j = m; j < 4; j++) {
			auction.add(new JLabel());
		}

		auction_container.add(auction, BorderLayout.CENTER);

		int h_photo = (int) (height_auction * 0.35);
		int h_title = (int) (height_auction * 0.25) + 20;
		int h_desc = (int) (height_auction * 0.50);

		JPanel sub_container = new JPanel();
		sub_container.setBackground(Color.WHITE);
		sub_container.setLayout(new BorderLayout());
		sub_container.setBorder(new EmptyBorder(10, 10, 10, 10));
		sub_container.setPreferredSize(new Dimension(width, height));

		JPanel photo_pan = new JPanel();
		photo_pan.setPreferredSize(new Dimension(width, h_photo));
		photo_pan.setBackground(Color.WHITE);
		photo_pan.setLayout(new BorderLayout());
		photo_pan.setBorder(new EmptyBorder(0, 10, 0, 10));

		Image img = Resources.get_image(path);
		Image newimg = img.getScaledInstance(h_photo, h_photo, java.awt.Image.SCALE_SMOOTH);

		JLabel photo = new JLabel();
		photo.setHorizontalAlignment(JLabel.CENTER);
		photo.setIcon(new ImageIcon(newimg));

		photo_pan.add(photo, BorderLayout.CENTER);

		JPanel title_pan = new JPanel();
		title_pan.setPreferredSize(new Dimension(width, h_title));
		title_pan.setBackground(Color.WHITE);
		title_pan.setLayout(new BorderLayout());
		title_pan.setBorder(new EmptyBorder(0, 10, 0, 10));

		My_JLabel tit = new My_JLabel(title, 12.f);

		title_pan.add(tit, BorderLayout.CENTER);

		JPanel desc_pan = new JPanel();
		desc_pan.setPreferredSize(new Dimension(width, h_desc));
		desc_pan.setBackground(Color.WHITE);
		desc_pan.setLayout(new BorderLayout());
		desc_pan.setBorder(new EmptyBorder(10, 10, 10, 10));

		My_JLabel dsc = new My_JLabel(description, 14.f);

		desc_pan.add(dsc, BorderLayout.CENTER);

		sub_container.add(photo_pan, BorderLayout.NORTH);
		sub_container.add(title_pan, BorderLayout.CENTER);
		sub_container.add(desc_pan, BorderLayout.SOUTH);

		JPanel price_purpose_container = new JPanel();
		price_purpose_container.setLayout(new BorderLayout());
		price_purpose_container.setPreferredSize(new Dimension(width, height_price));
		price_purpose_container.setBackground(Color.WHITE);
		price_purpose_container.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));

		JPanel price_purpose = new JPanel();
		price_purpose.setPreferredSize(new Dimension(width, height_price));
		price_purpose.setBackground(Color.WHITE);
		price_purpose.setLayout(new GridLayout(1, 2, 100, 100));
		price_purpose.setBorder(new EmptyBorder(20, 50, 20, 50));

		field = new JTextField();
		field.setBorder(new LineBorder(Color.BLACK, 2));
		field.setHorizontalAlignment(JTextField.CENTER);
		field.setBackground(Color.WHITE);
		field.setForeground(Color.BLACK);
		field.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try_launching_connection();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});

		My_JButton send_val = new My_JButton("Send", 15.f);
		send_val.setBorderPainted(true);
		send_val.setBorder(new LineBorder(Color.BLACK, 2));
		send_val.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try_launching_connection();
			}
		});

		price_purpose.add(field);
		price_purpose.add(send_val);

		price_purpose_container.add(price_purpose, BorderLayout.CENTER);

		container.removeAll();
		container.add(auction_container, BorderLayout.NORTH);
		container.add(sub_container, BorderLayout.CENTER);
		container.add(price_purpose_container, BorderLayout.SOUTH);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	/**
	 * Méthode permettant de construire le milieu du plateau de tel sorte qu'il
	 * propose une enchere sur un terrain propriété
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param banner_color
	 *            la couleur du terrain
	 * @param price
	 *            le prix du terrain
	 * @param rents
	 *            les loyers du terrain
	 * @param house_price
	 *            le prix des maisons
	 * @param number_house
	 *            le numéro de la propriété en enchere
	 */
	public void build_auction_running_property(String name, Color banner_color, int price, int[] rents, int house_price,
			int number_house) {
		this.setBorder(new EmptyBorder(60, 175, 60, 175));

		int height_card = (int) (height * 0.70);
		int height_auction = (int) (height * 0.15);
		int height_price = (int) (height * 0.15);

		JPanel auction_container = new JPanel();
		auction_container.setPreferredSize(new Dimension(width, height_auction));
		auction_container.setLayout(new BorderLayout());
		auction_container.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
		auction_container.setBackground(Color.WHITE);

		JPanel auction = new JPanel();
		auction.setBackground(Color.WHITE);
		auction.setPreferredSize(new Dimension(width, height_auction));
		auction.setLayout(new GridLayout(5, 1, 0, 2));
		auction.setBorder(new EmptyBorder(0, 10, 0, 10));

		My_JLabel title_auc = new My_JLabel("Auctions : " + Setting_configuration.minutes_for_auction + "s", 14.f);
		new Thread(new Runnable() {
			@Override
			public void run() {
				int it = Setting_configuration.minutes_for_auction;
				while (it >= 1) {
					title_auc.setText("Auctions : " + it + "s");
					it--;
					revalidate();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		auction.add(title_auc);

		int m = 0;
		for (; m < pseudos.size(); m++) {
			if (m == 0) {
				this.player_1_price = new My_JLabel(pseudos.get(0) + " : M" + values.get(0), 11.f);
				this.player_1_price.setHorizontalAlignment(JLabel.LEFT);
				auction.add(player_1_price);
			} else if (m == 1) {
				this.player_2_price = new My_JLabel(pseudos.get(1) + " : M" + values.get(1), 11.f);
				this.player_2_price.setHorizontalAlignment(JLabel.LEFT);
				auction.add(player_2_price);
			} else if (m == 2) {
				this.player_3_price = new My_JLabel(pseudos.get(2) + " : M" + values.get(2), 11.f);
				this.player_3_price.setHorizontalAlignment(JLabel.LEFT);
				auction.add(player_3_price);
			} else if (m == 3) {
				this.player_4_price = new My_JLabel(pseudos.get(3) + " : M" + values.get(3), 11.f);
				this.player_4_price.setHorizontalAlignment(JLabel.LEFT);
				auction.add(player_4_price);
			}
		}

		for (int j = m; j < 4; j++) {
			auction.add(new JLabel());
		}

		auction_container.add(auction, BorderLayout.CENTER);

		JPanel sub_container = new JPanel();
		sub_container.setBackground(Color.WHITE);
		sub_container.setLayout(new BorderLayout());
		sub_container.setBorder(new EmptyBorder(10, 10, 10, 10));
		sub_container.setPreferredSize(new Dimension(width, height_card));

		int height_banner = (int) (0.15 * height_card);
		int height_center = (int) (0.70 * height_card);
		int height_footer = (int) (0.15 * height_card);

		// HEADER
		JPanel banner = new JPanel();
		banner.setBorder(new LineBorder(Color.BLACK, 2));
		banner.setBackground(banner_color);
		banner.setLayout(new GridLayout(2, 1));
		banner.setPreferredSize(new Dimension(width, height_banner));

		My_JLabel title_label = new My_JLabel("Titre de propriété", 22.f);
		title_label.setForeground(Color.BLACK);

		My_JLabel name_label = new My_JLabel(name, 18.f);
		name_label.setForeground(Color.BLACK);

		banner.add(title_label);
		banner.add(name_label);

		// CENTER
		JPanel center = new JPanel();
		center.setBackground(Color.PINK);
		center.setPreferredSize(new Dimension(width, height_center));
		center.setLayout(new BorderLayout());
		center.setBackground(Color.WHITE);

		int h_rent = (int) (height_center * 0.10);
		int h_houses = (int) (height_center * 0.80);
		int h_hypo = (int) (height_center * 0.10);

		JPanel rent = new JPanel();
		rent.setOpaque(false);
		rent.setLayout(new BorderLayout());
		rent.setPreferredSize(new Dimension(width, h_rent));

		My_JLabel label_rent = new My_JLabel("Loyer : M" + rents[0], 15.f);
		label_rent.setForeground(Color.BLACK);
		rent.add(label_rent, BorderLayout.CENTER);

		JPanel houses = new JPanel();
		houses.setLayout(new GridLayout(5, 2));
		houses.setPreferredSize(new Dimension(width, h_houses));
		houses.setOpaque(false);

		for (int i = 1; i <= 5; i++) {
			String str_left = "";
			String str_right = "M" + rents[i];
			if (i == 1) {
				str_left = "Avec " + i + " maison: ";
			} else if (i == 5) {
				str_left = "Avec HOTEL: ";
			} else {
				str_left = "Avec " + i + " maisons: ";
			}

			My_JLabel left = new My_JLabel(str_left, 12.f);
			left.setHorizontalAlignment(JLabel.LEFT);
			left.setForeground(Color.BLACK);
			My_JLabel right = new My_JLabel(str_right, 12.f);
			right.setHorizontalAlignment(JLabel.RIGHT);
			right.setForeground(Color.BLACK);
			houses.add(left);
			houses.add(right);
		}

		JPanel hypo = new JPanel();
		hypo.setOpaque(false);
		hypo.setLayout(new BorderLayout());
		hypo.setPreferredSize(new Dimension(width, h_hypo));

		My_JLabel label_hypo = new My_JLabel("Valeur hypothécaire : M"
				+ (int) (price * (float) (Setting_configuration.percentage_sell / (float) 100)), 15.f);
		label_hypo.setForeground(Color.BLACK);
		hypo.add(label_hypo, BorderLayout.CENTER);

		center.add(rent, BorderLayout.NORTH);
		center.add(houses, BorderLayout.CENTER);
		center.add(hypo, BorderLayout.SOUTH);

		// FOOTER
		JPanel footer = new JPanel();
		footer.setBackground(Color.WHITE);
		footer.setPreferredSize(new Dimension(width, height_footer));
		footer.setLayout(new BorderLayout());

		int h_house = (int) (height_footer * 0.2);
		int h_hotel = (int) (height_footer * 0.2);
		int h_desc = (int) (height_footer * 0.6);

		JPanel house_text = new JPanel();
		house_text.setOpaque(false);
		house_text.setPreferredSize(new Dimension(width, h_house));
		house_text.setLayout(new BorderLayout());

		My_JLabel house_label = new My_JLabel("Prix des maisons : M" + house_price + " chacune", 12.f);
		house_label.setForeground(Color.BLACK);
		house_text.add(house_label, BorderLayout.CENTER);

		JPanel hotel_text = new JPanel();
		hotel_text.setOpaque(false);
		hotel_text.setPreferredSize(new Dimension(width, h_hotel));
		hotel_text.setLayout(new BorderLayout());

		My_JLabel hotel_label = new My_JLabel("Prix d'un hotel : M" + house_price + " plus 4 maisons", 12.f);
		hotel_label.setForeground(Color.BLACK);
		hotel_text.add(hotel_label, BorderLayout.CENTER);

		JPanel desc_text = new JPanel();
		desc_text.setOpaque(false);
		desc_text.setPreferredSize(new Dimension(width, h_desc));
		desc_text.setLayout(new BorderLayout());

		My_JLabel desc_label = new My_JLabel("<html><div style='text-align: center;'>"
				+ "Si un joueur possèdé TOUS les terrains d'un groupe de couleur quelconque, le loyer des terrains nus de ce groupe est doublé"
				+ "</div></html>", 10.f);
		desc_label.setForeground(Color.BLACK);
		desc_text.add(desc_label, BorderLayout.CENTER);

		footer.add(house_text, BorderLayout.NORTH);
		footer.add(hotel_text, BorderLayout.CENTER);
		footer.add(desc_text, BorderLayout.SOUTH);

		// ADDING
		sub_container.add(banner, BorderLayout.NORTH);
		sub_container.add(center, BorderLayout.CENTER);
		sub_container.add(footer, BorderLayout.SOUTH);

		JPanel price_purpose_container = new JPanel();
		price_purpose_container.setLayout(new BorderLayout());
		price_purpose_container.setPreferredSize(new Dimension(width, height_price));
		price_purpose_container.setBackground(Color.WHITE);
		price_purpose_container.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));

		JPanel price_purpose = new JPanel();
		price_purpose.setPreferredSize(new Dimension(width, height_price));
		price_purpose.setBackground(Color.WHITE);
		price_purpose.setLayout(new GridLayout(1, 2, 100, 100));
		price_purpose.setBorder(new EmptyBorder(20, 50, 20, 50));

		field = new JTextField();
		field.setBorder(new LineBorder(Color.BLACK, 2));
		field.setHorizontalAlignment(JTextField.CENTER);
		field.setBackground(Color.WHITE);
		field.setForeground(Color.BLACK);
		field.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try_launching_connection();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});

		My_JButton send_val = new My_JButton("Send", 15.f);
		send_val.setBorderPainted(true);
		send_val.setBorder(new LineBorder(Color.BLACK, 2));
		send_val.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try_launching_connection();
			}
		});

		price_purpose.add(field);
		price_purpose.add(send_val);

		price_purpose_container.add(price_purpose, BorderLayout.CENTER);

		container.removeAll();
		container.add(auction_container, BorderLayout.NORTH);
		container.add(sub_container, BorderLayout.CENTER);
		container.add(price_purpose_container, BorderLayout.SOUTH);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	public void try_launching_connection() {
		ref_controler.send_auction_value(field.getText());
	}

	/**
	 * Méthode permettant de construire le milieu du plateau pour que le joueur
	 * choisisse comment gérer son emprisonnement
	 * 
	 * @param pseudo
	 *            le pseudo du client qui gére son emprisonnement
	 */
	public void build_manage_jailing(String pseudo) {
		this.setBorder(new EmptyBorder(120, 175, 120, 175));

		JPanel sub_container = new JPanel();
		sub_container.setPreferredSize(new Dimension(width, height));
		sub_container.setLayout(new GridLayout(3, 1, 100, 100));
		sub_container.setBorder(new EmptyBorder(50, 50, 50, 50));

		My_JButton use_card = new My_JButton("Get out jail with card", 16.f);
		use_card.setBorderPainted(true);
		use_card.setBorder(new LineBorder(Color.BLACK, 2));
		use_card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		use_card.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.answer_manage_jailing(pseudo, Jail_manage_result.USE_CARD);
			}
		});

		My_JButton pay = new My_JButton("Paid to get out now", 16.f);
		pay.setBorderPainted(true);
		pay.setBorder(new LineBorder(Color.BLACK, 2));
		pay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.answer_manage_jailing(pseudo, Jail_manage_result.PAY);
			}
		});

		My_JButton try_roll = new My_JButton("Try rolling dice", 16.f);
		try_roll.setBorderPainted(true);
		try_roll.setBorder(new LineBorder(Color.BLACK, 2));
		try_roll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		try_roll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.answer_manage_jailing(pseudo, Jail_manage_result.TRY_ROLLING);
			}
		});

		sub_container.add(use_card);
		sub_container.add(pay);
		sub_container.add(try_roll);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	/**
	 * Methode permettant de construire le milieu du plateau pour acheter une
	 * case a image du plateau
	 * 
	 * @param title
	 *            le titre a afficher
	 * @param path
	 *            le chemin de l'image à afficher
	 * @param description
	 *            la description à afficher
	 */
	public void build_center_image_buying(String title, String path, String description) {
		this.setBorder(new EmptyBorder(120, 175, 120, 175));

		int height_card = (int) (height * 0.85);
		int height_button = (int) (height * 0.15);

		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(width, height_button));
		buttons.setBackground(Color.WHITE);
		buttons.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));
		buttons.setLayout(new GridLayout(1, 2, 50, 50));

		My_JButton buy = new My_JButton("Buy", 20.f);
		buy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.answer_buy_property(Buy_order.BUY);
				// build_center_empty();
				// ref_board.enable_show_informations_terrain();
			}
		});

		My_JButton refuse = new My_JButton("Refuse", 20.f);
		refuse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refuse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.answer_buy_property(Buy_order.REFUSE);
				// build_center_empty();
				// ref_board.enable_show_informations_terrain();
			}
		});

		buttons.add(buy);
		buttons.add(refuse);

		int height = height_card - 240;

		int h_photo = (int) (height * 0.35);
		int h_title = (int) (height * 0.25) + 20;
		int h_desc = (int) (height * 0.50);

		JPanel sub_container = new JPanel();
		sub_container.setBackground(Color.WHITE);
		sub_container.setLayout(new BorderLayout());
		sub_container.setBorder(new EmptyBorder(10, 10, 10, 10));
		sub_container.setPreferredSize(new Dimension(width, height));

		JPanel photo_pan = new JPanel();
		photo_pan.setPreferredSize(new Dimension(width, h_photo));
		photo_pan.setBackground(Color.WHITE);
		photo_pan.setLayout(new BorderLayout());
		photo_pan.setBorder(new EmptyBorder(0, 10, 0, 10));

		Image img = Resources.get_image(path);
		Image newimg = img.getScaledInstance(h_photo, h_photo, java.awt.Image.SCALE_SMOOTH);

		JLabel photo = new JLabel();
		photo.setHorizontalAlignment(JLabel.CENTER);
		photo.setIcon(new ImageIcon(newimg));

		photo_pan.add(photo, BorderLayout.CENTER);

		JPanel title_pan = new JPanel();
		title_pan.setPreferredSize(new Dimension(width, h_title));
		title_pan.setBackground(Color.WHITE);
		title_pan.setLayout(new BorderLayout());
		title_pan.setBorder(new EmptyBorder(0, 10, 0, 10));

		My_JLabel tit = new My_JLabel(title, 12.f);

		title_pan.add(tit, BorderLayout.CENTER);

		JPanel desc_pan = new JPanel();
		desc_pan.setPreferredSize(new Dimension(width, h_desc));
		desc_pan.setBackground(Color.WHITE);
		desc_pan.setLayout(new BorderLayout());
		desc_pan.setBorder(new EmptyBorder(10, 10, 10, 10));

		My_JLabel dsc = new My_JLabel(description, 14.f);

		desc_pan.add(dsc, BorderLayout.CENTER);

		sub_container.add(photo_pan, BorderLayout.NORTH);
		sub_container.add(title_pan, BorderLayout.CENTER);
		sub_container.add(desc_pan, BorderLayout.SOUTH);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.add(buttons, BorderLayout.SOUTH);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();
	}

	/**
	 * Méthdoe permettant d'afficher le zoom du milieu d'un terrain image dans
	 * le plateau
	 * 
	 * @param title
	 *            le titre à afficher
	 * @param path
	 *            le chemin de l'image à afficher
	 * @param description
	 *            la description a afficher
	 */
	public void build_center_image(String title, String path, String description) {

		this.setBorder(new EmptyBorder(120, 175, 120, 175));

		int height = this.height - 240;

		int h_photo = (int) (height * 0.35);
		int h_title = (int) (height * 0.25) + 20;
		int h_desc = (int) (height * 0.50);

		JPanel sub_container = new JPanel();
		sub_container.setBackground(Color.WHITE);
		sub_container.setLayout(new BorderLayout());
		sub_container.setBorder(new EmptyBorder(10, 10, 10, 10));
		sub_container.setPreferredSize(new Dimension(width, height));

		JPanel photo_pan = new JPanel();
		photo_pan.setPreferredSize(new Dimension(width, h_photo));
		photo_pan.setBackground(Color.WHITE);
		photo_pan.setLayout(new BorderLayout());
		photo_pan.setBorder(new EmptyBorder(0, 10, 0, 10));

		Image img = Resources.get_image(path);
		Image newimg = img.getScaledInstance(h_photo, h_photo, java.awt.Image.SCALE_SMOOTH);

		JLabel photo = new JLabel();
		photo.setHorizontalAlignment(JLabel.CENTER);
		photo.setIcon(new ImageIcon(newimg));

		photo_pan.add(photo, BorderLayout.CENTER);

		JPanel title_pan = new JPanel();
		title_pan.setPreferredSize(new Dimension(width, h_title));
		title_pan.setBackground(Color.WHITE);
		title_pan.setLayout(new BorderLayout());
		title_pan.setBorder(new EmptyBorder(0, 10, 0, 10));

		My_JLabel tit = new My_JLabel(title, 12.f);

		title_pan.add(tit, BorderLayout.CENTER);

		JPanel desc_pan = new JPanel();
		desc_pan.setPreferredSize(new Dimension(width, h_desc));
		desc_pan.setBackground(Color.WHITE);
		desc_pan.setLayout(new BorderLayout());
		desc_pan.setBorder(new EmptyBorder(10, 10, 10, 10));

		My_JLabel dsc = new My_JLabel(description, 14.f);

		desc_pan.add(dsc, BorderLayout.CENTER);

		sub_container.add(photo_pan, BorderLayout.NORTH);
		sub_container.add(title_pan, BorderLayout.CENTER);
		sub_container.add(desc_pan, BorderLayout.SOUTH);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.setVisible(true);

		this.add(container, BorderLayout.CENTER);
		container.revalidate();

		this.revalidate();
		this.repaint();

		ref_controler.repaint_root();
	}

	/**
	 * Méthode permettant de construire le milieu du plateau pour le zoom sur un
	 * terrain propriété pour l'achat donc avec les boutons
	 * 
	 * @param name
	 *            le nom de la propriété
	 * @param banner_color
	 *            la couleur de la propriété
	 * @param price
	 *            le prix de la propriété
	 * @param rents
	 *            les loyers de la propriété
	 * @param house_price
	 *            le prix d'une maison de la propriété
	 */
	public void build_center_buying(String name, Color banner_color, int price, int[] rents, int house_price) {

		this.setBorder(new EmptyBorder(80, 175, 80, 175));

		int height_card = (int) (height * 0.85);
		int height_button = (int) (height * 0.15);

		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(width, height_button));
		buttons.setBackground(Color.WHITE);
		buttons.setBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK));
		buttons.setLayout(new GridLayout(1, 2, 50, 50));

		My_JButton buy = new My_JButton("Buy", 20.f);
		buy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.answer_buy_property(Buy_order.BUY);
			}
		});

		My_JButton refuse = new My_JButton("Refuse", 20.f);
		refuse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refuse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ref_controler.answer_buy_property(Buy_order.REFUSE);
			}
		});

		buttons.add(buy);
		buttons.add(refuse);

		JPanel sub_container = new JPanel();
		sub_container.setBackground(Color.WHITE);
		sub_container.setLayout(new BorderLayout());
		sub_container.setBorder(new EmptyBorder(10, 10, 10, 10));
		sub_container.setPreferredSize(new Dimension(width, height_card));

		int height_banner = (int) (0.15 * height_card);
		int height_center = (int) (0.70 * height_card);
		int height_footer = (int) (0.15 * height_card);

		// HEADER
		JPanel banner = new JPanel();
		banner.setBorder(new LineBorder(Color.BLACK, 2));
		banner.setBackground(banner_color);
		banner.setLayout(new GridLayout(2, 1));
		banner.setPreferredSize(new Dimension(width, height_banner));

		My_JLabel title_label = new My_JLabel("Titre de propriété", 22.f);
		title_label.setForeground(Color.BLACK);

		My_JLabel name_label = new My_JLabel(name, 18.f);
		name_label.setForeground(Color.BLACK);

		banner.add(title_label);
		banner.add(name_label);

		// CENTER
		JPanel center = new JPanel();
		center.setBackground(Color.PINK);
		center.setPreferredSize(new Dimension(width, height_center));
		center.setLayout(new BorderLayout());
		center.setBackground(Color.WHITE);

		int h_rent = (int) (height_center * 0.10);
		int h_houses = (int) (height_center * 0.80);
		int h_hypo = (int) (height_center * 0.10);

		JPanel rent = new JPanel();
		rent.setOpaque(false);
		rent.setLayout(new BorderLayout());
		rent.setPreferredSize(new Dimension(width, h_rent));

		My_JLabel label_rent = new My_JLabel("Loyer : M" + rents[0], 15.f);
		label_rent.setForeground(Color.BLACK);
		rent.add(label_rent, BorderLayout.CENTER);

		JPanel houses = new JPanel();
		houses.setLayout(new GridLayout(5, 2));
		houses.setPreferredSize(new Dimension(width, h_houses));
		houses.setOpaque(false);

		for (int i = 1; i <= 5; i++) {
			String str_left = "";
			String str_right = "M" + rents[i];
			if (i == 1) {
				str_left = "Avec " + i + " maison: ";
			} else if (i == 5) {
				str_left = "Avec HOTEL: ";
			} else {
				str_left = "Avec " + i + " maisons: ";
			}

			My_JLabel left = new My_JLabel(str_left, 12.f);
			left.setHorizontalAlignment(JLabel.LEFT);
			left.setForeground(Color.BLACK);
			My_JLabel right = new My_JLabel(str_right, 12.f);
			right.setHorizontalAlignment(JLabel.RIGHT);
			right.setForeground(Color.BLACK);
			houses.add(left);
			houses.add(right);
		}

		JPanel hypo = new JPanel();
		hypo.setOpaque(false);
		hypo.setLayout(new BorderLayout());
		hypo.setPreferredSize(new Dimension(width, h_hypo));

		My_JLabel label_hypo = new My_JLabel("Valeur hypothécaire : M"
				+ (int) (price * (float) (Setting_configuration.percentage_sell / (float) 100)), 15.f);
		label_hypo.setForeground(Color.BLACK);
		hypo.add(label_hypo, BorderLayout.CENTER);

		center.add(rent, BorderLayout.NORTH);
		center.add(houses, BorderLayout.CENTER);
		center.add(hypo, BorderLayout.SOUTH);

		// FOOTER
		JPanel footer = new JPanel();
		footer.setBackground(Color.WHITE);
		footer.setPreferredSize(new Dimension(width, height_footer));
		footer.setLayout(new BorderLayout());

		int h_house = (int) (height_footer * 0.2);
		int h_hotel = (int) (height_footer * 0.2);
		int h_desc = (int) (height_footer * 0.6);

		JPanel house_text = new JPanel();
		house_text.setOpaque(false);
		house_text.setPreferredSize(new Dimension(width, h_house));
		house_text.setLayout(new BorderLayout());

		My_JLabel house_label = new My_JLabel("Prix des maisons : M" + house_price + " chacune", 12.f);
		house_label.setForeground(Color.BLACK);
		house_text.add(house_label, BorderLayout.CENTER);

		JPanel hotel_text = new JPanel();
		hotel_text.setOpaque(false);
		hotel_text.setPreferredSize(new Dimension(width, h_hotel));
		hotel_text.setLayout(new BorderLayout());

		My_JLabel hotel_label = new My_JLabel("Prix d'un hotel : M" + house_price + " plus 4 maisons", 12.f);
		hotel_label.setForeground(Color.BLACK);
		hotel_text.add(hotel_label, BorderLayout.CENTER);

		JPanel desc_text = new JPanel();
		desc_text.setOpaque(false);
		desc_text.setPreferredSize(new Dimension(width, h_desc));
		desc_text.setLayout(new BorderLayout());

		My_JLabel desc_label = new My_JLabel("<html><div style='text-align: center;'>"
				+ "Si un joueur possèdé TOUS les terrains d'un groupe de couleur quelconque, le loyer des terrains nus de ce groupe est doublé"
				+ "</div></html>", 10.f);
		desc_label.setForeground(Color.BLACK);
		desc_text.add(desc_label, BorderLayout.CENTER);

		footer.add(house_text, BorderLayout.NORTH);
		footer.add(hotel_text, BorderLayout.CENTER);
		footer.add(desc_text, BorderLayout.SOUTH);

		// ADDING
		sub_container.add(banner, BorderLayout.NORTH);
		sub_container.add(center, BorderLayout.CENTER);
		sub_container.add(footer, BorderLayout.SOUTH);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);
		container.add(buttons, BorderLayout.SOUTH);
		// container.add(sub_container);
		container.setVisible(true);
		container.revalidate();

		this.add(container, BorderLayout.CENTER);
		this.revalidate();

	}

	/**
	 * Méthode permettant de construire le milieu du plateau pour le zoom sur un
	 * terrain propriété
	 * 
	 * @param name
	 *            le nom de la propriété
	 * @param banner_color
	 *            la couleur de la propriété
	 * @param price
	 *            le prix de la propriété
	 * @param rents
	 *            les loyers de la propriété
	 * @param house_price
	 *            le prix d'une maison de la propriété
	 */
	public void build_center(String name, Color banner_color, int price, int[] rents, int house_price) {

		this.setBorder(new EmptyBorder(120, 175, 120, 175));

		JPanel sub_container = new JPanel();
		sub_container.setBackground(Color.WHITE);
		sub_container.setLayout(new BorderLayout());
		sub_container.setBorder(new EmptyBorder(10, 10, 10, 10));
		sub_container.setPreferredSize(new Dimension(width, height));

		int height_banner = (int) (0.15 * height);
		int height_center = (int) (0.70 * height);
		int height_footer = (int) (0.15 * height);

		// HEADER
		JPanel banner = new JPanel();
		banner.setBorder(new LineBorder(Color.BLACK, 2));
		banner.setBackground(banner_color);
		banner.setLayout(new GridLayout(2, 1));
		banner.setPreferredSize(new Dimension(width, height_banner));

		My_JLabel title_label = new My_JLabel("Titre de propriété", 22.f);
		title_label.setForeground(Color.BLACK);

		My_JLabel name_label = new My_JLabel(name, 18.f);
		name_label.setForeground(Color.BLACK);

		banner.add(title_label);
		banner.add(name_label);

		// CENTER
		JPanel center = new JPanel();
		center.setBackground(Color.PINK);
		center.setPreferredSize(new Dimension(width, height_center));
		center.setLayout(new BorderLayout());
		center.setBackground(Color.WHITE);

		int h_rent = (int) (height_center * 0.10);
		int h_houses = (int) (height_center * 0.80);
		int h_hypo = (int) (height_center * 0.10);

		JPanel rent = new JPanel();
		rent.setOpaque(false);
		rent.setLayout(new BorderLayout());
		rent.setPreferredSize(new Dimension(width, h_rent));

		My_JLabel label_rent = new My_JLabel("Loyer : M" + rents[0], 15.f);
		label_rent.setForeground(Color.BLACK);
		rent.add(label_rent, BorderLayout.CENTER);

		JPanel houses = new JPanel();
		houses.setLayout(new GridLayout(5, 2));
		houses.setPreferredSize(new Dimension(width, h_houses));
		houses.setOpaque(false);

		for (int i = 1; i <= 5; i++) {
			String str_left = "";
			String str_right = "M" + rents[i];
			if (i == 1) {
				str_left = "Avec " + i + " maison: ";
			} else if (i == 5) {
				str_left = "Avec HOTEL: ";
			} else {
				str_left = "Avec " + i + " maisons: ";
			}

			My_JLabel left = new My_JLabel(str_left, 12.f);
			left.setHorizontalAlignment(JLabel.LEFT);
			left.setForeground(Color.BLACK);
			My_JLabel right = new My_JLabel(str_right, 12.f);
			right.setHorizontalAlignment(JLabel.RIGHT);
			right.setForeground(Color.BLACK);
			houses.add(left);
			houses.add(right);
		}

		JPanel hypo = new JPanel();
		hypo.setOpaque(false);
		hypo.setLayout(new BorderLayout());
		hypo.setPreferredSize(new Dimension(width, h_hypo));

		My_JLabel label_hypo = new My_JLabel("Valeur hypothécaire : M"
				+ (int) (price * (float) (Setting_configuration.percentage_sell / (float) 100)), 15.f);
		label_hypo.setForeground(Color.BLACK);
		hypo.add(label_hypo, BorderLayout.CENTER);

		center.add(rent, BorderLayout.NORTH);
		center.add(houses, BorderLayout.CENTER);
		center.add(hypo, BorderLayout.SOUTH);

		// FOOTER
		JPanel footer = new JPanel();
		footer.setBackground(Color.WHITE);
		footer.setPreferredSize(new Dimension(width, height_footer));
		footer.setLayout(new BorderLayout());

		int h_house = (int) (height_footer * 0.2);
		int h_hotel = (int) (height_footer * 0.2);
		int h_desc = (int) (height_footer * 0.6);

		JPanel house_text = new JPanel();
		house_text.setOpaque(false);
		house_text.setPreferredSize(new Dimension(width, h_house));
		house_text.setLayout(new BorderLayout());

		My_JLabel house_label = new My_JLabel("Prix des maisons : M" + house_price + " chacune", 12.f);
		house_label.setForeground(Color.BLACK);
		house_text.add(house_label, BorderLayout.CENTER);

		JPanel hotel_text = new JPanel();
		hotel_text.setOpaque(false);
		hotel_text.setPreferredSize(new Dimension(width, h_hotel));
		hotel_text.setLayout(new BorderLayout());

		My_JLabel hotel_label = new My_JLabel("Prix d'un hotel : M" + house_price + " plus 4 maisons", 12.f);
		hotel_label.setForeground(Color.BLACK);
		hotel_text.add(hotel_label, BorderLayout.CENTER);

		JPanel desc_text = new JPanel();
		desc_text.setPreferredSize(new Dimension(width, h_desc));
		desc_text.setLayout(new BorderLayout());

		My_JLabel desc_label = new My_JLabel("<html><div style='text-align: center;'>"
				+ "Si un joueur possèdé TOUS les terrains d'un groupe de couleur quelconque, le loyer des terrains nus de ce groupe est doublé"
				+ "</div></html>", 10.f);
		desc_label.setForeground(Color.BLACK);
		desc_text.add(desc_label, BorderLayout.CENTER);

		footer.add(house_text, BorderLayout.NORTH);
		footer.add(hotel_text, BorderLayout.CENTER);
		footer.add(desc_text, BorderLayout.SOUTH);

		// ADDING
		sub_container.add(banner, BorderLayout.NORTH);
		sub_container.add(center, BorderLayout.CENTER);
		sub_container.add(footer, BorderLayout.SOUTH);

		container.removeAll();
		container.add(sub_container, BorderLayout.CENTER);

		this.add(container, BorderLayout.CENTER);

		container.setVisible(true);
		container.revalidate();

		this.revalidate();
		ref_controler.repaint_root();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		BufferedImage image = Resources.get_image("back_middle.png");

		AffineTransform at = new AffineTransform();
		at.translate(getWidth() / 2, getHeight() / 2);
		at.scale((float) ((float) width / image.getWidth()), (float) ((float) height / image.getHeight()));
		at.translate(-image.getWidth() / 2, -image.getHeight() / 2);

		g2.drawImage(image, at, null);

	}
}
