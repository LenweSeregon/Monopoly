package configure_settings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import menus.Abstract_panel;
import view.Window;

@SuppressWarnings("serial")
public class Setting_configuration_view extends Abstract_panel {

	private JPanel center_container;
	private CardLayout center_container_cl;
	private Window window_ref;
	private int port;
	private int per_game;

	/**
	 * Constructeur de la classe preséentant la vue pour le parametrage des
	 * options général, cette classe sert juste à instancer les différents
	 * panels de configuration et les agencer dans un cardlayout
	 * 
	 * @param width
	 *            largeur du panel
	 * @param height
	 *            hauteur du panel
	 */
	public Setting_configuration_view(int width, int height, Window window_ref, int port, int per_game) {
		super(width, height);
		this.window_ref = window_ref;
		this.port = port;
		this.per_game = per_game;

		this.build_header(1.0f, 0.10f);
		this.build_center(1.0f, 0.8f);
		this.build_footer(1.0f, 0.10f);
	}

	@Override
	protected void build_header(float w_mod, float h_mod) {
		super.build_header(w_mod, h_mod);
		this.header.setBackground(new Color(129, 198, 134));
		this.header.setLayout(new BorderLayout());
		this.header.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		JPanel header_content = new JPanel();
		header_content.setLayout(new BorderLayout());
		header_content.setOpaque(false);

		JLabel title = new My_JLabel("Settings configuration", 45.f);
		title.setForeground(Color.WHITE);

		header_content.add(title, BorderLayout.CENTER);

		this.header.add(header_content, BorderLayout.CENTER);
	}

	@Override
	protected void build_center(float w_mod, float h_mod) {
		super.build_center(w_mod, h_mod);
		this.center.setLayout(new BorderLayout());
		this.center.setBackground(new Color(170, 203, 172));

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(w, h));
		container.setLayout(new BorderLayout());

		int h_buttons = (int) (h * 0.10);
		int h_content = (int) (h * 0.90);

		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(w, h_buttons));
		buttons.setLayout(new GridLayout(1, 8));
		buttons.setBackground(new Color(170, 203, 172));
		buttons.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		My_JButton general = new My_JButton("General", 16.f);
		My_JButton auction = new My_JButton("Auction", 16.f);
		My_JButton board = new My_JButton("Board", 16.f);
		My_JButton terrain = new My_JButton("Terrains", 16.f);
		My_JButton station_elec = new My_JButton("Station, etc", 16.f);
		My_JButton jail = new My_JButton("Jail", 16.f);
		My_JButton tax_start = new My_JButton("Start, tax, etc", 16.f);
		My_JButton cards = new My_JButton("Cards", 16.f);

		general.setForeground(Color.WHITE);
		general.setBorderPainted(true);
		general.setBorder(new MatteBorder(0, 0, 0, 2, Color.WHITE));
		general.setOpaque(true);
		general.setBackground(new Color(108, 125, 110));
		general.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		general.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				general.setBackground(new Color(108, 125, 110));
				auction.setBackground(new Color(170, 203, 172));
				board.setBackground(new Color(170, 203, 172));
				terrain.setBackground(new Color(170, 203, 172));
				station_elec.setBackground(new Color(170, 203, 172));
				jail.setBackground(new Color(170, 203, 172));
				tax_start.setBackground(new Color(170, 203, 172));
				cards.setBackground(new Color(170, 203, 172));
				show_center_content("general");
			}
		});

		auction.setForeground(Color.WHITE);
		auction.setBorderPainted(true);
		auction.setBorder(new MatteBorder(0, 0, 0, 2, Color.WHITE));
		auction.setOpaque(true);
		auction.setBackground(new Color(170, 203, 172));
		auction.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		auction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auction.setBackground(new Color(108, 125, 110));
				general.setBackground(new Color(170, 203, 172));
				board.setBackground(new Color(170, 203, 172));
				terrain.setBackground(new Color(170, 203, 172));
				station_elec.setBackground(new Color(170, 203, 172));
				jail.setBackground(new Color(170, 203, 172));
				tax_start.setBackground(new Color(170, 203, 172));
				cards.setBackground(new Color(170, 203, 172));
				show_center_content("auction");
			}
		});

		board.setBorderPainted(true);
		board.setForeground(Color.WHITE);
		board.setBorder(new MatteBorder(0, 0, 0, 2, Color.WHITE));
		board.setOpaque(true);
		board.setBackground(new Color(170, 203, 172));
		board.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		board.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.setBackground(new Color(108, 125, 110));
				auction.setBackground(new Color(170, 203, 172));
				general.setBackground(new Color(170, 203, 172));
				terrain.setBackground(new Color(170, 203, 172));
				station_elec.setBackground(new Color(170, 203, 172));
				jail.setBackground(new Color(170, 203, 172));
				tax_start.setBackground(new Color(170, 203, 172));
				cards.setBackground(new Color(170, 203, 172));
				show_center_content("board");
			}
		});

		terrain.setBorderPainted(true);
		terrain.setForeground(Color.WHITE);
		terrain.setBorder(new MatteBorder(0, 0, 0, 2, Color.WHITE));
		terrain.setOpaque(true);
		terrain.setBackground(new Color(170, 203, 172));
		terrain.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		terrain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				terrain.setBackground(new Color(108, 125, 110));
				auction.setBackground(new Color(170, 203, 172));
				board.setBackground(new Color(170, 203, 172));
				general.setBackground(new Color(170, 203, 172));
				station_elec.setBackground(new Color(170, 203, 172));
				jail.setBackground(new Color(170, 203, 172));
				tax_start.setBackground(new Color(170, 203, 172));
				cards.setBackground(new Color(170, 203, 172));
				show_center_content("terrain");
			}
		});

		station_elec.setBorderPainted(true);
		station_elec.setForeground(Color.WHITE);
		station_elec.setOpaque(true);
		station_elec.setBackground(new Color(170, 203, 172));
		station_elec.setBorder(new MatteBorder(0, 0, 0, 2, Color.WHITE));
		station_elec.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		station_elec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				station_elec.setBackground(new Color(108, 125, 110));
				auction.setBackground(new Color(170, 203, 172));
				board.setBackground(new Color(170, 203, 172));
				terrain.setBackground(new Color(170, 203, 172));
				general.setBackground(new Color(170, 203, 172));
				jail.setBackground(new Color(170, 203, 172));
				tax_start.setBackground(new Color(170, 203, 172));
				cards.setBackground(new Color(170, 203, 172));
				show_center_content("station");
			}
		});

		jail.setBorderPainted(true);
		jail.setForeground(Color.WHITE);
		jail.setOpaque(true);
		jail.setBackground(new Color(170, 203, 172));
		jail.setBorder(new MatteBorder(0, 0, 0, 2, Color.WHITE));
		jail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jail.setBackground(new Color(108, 125, 110));
				auction.setBackground(new Color(170, 203, 172));
				board.setBackground(new Color(170, 203, 172));
				terrain.setBackground(new Color(170, 203, 172));
				station_elec.setBackground(new Color(170, 203, 172));
				general.setBackground(new Color(170, 203, 172));
				tax_start.setBackground(new Color(170, 203, 172));
				cards.setBackground(new Color(170, 203, 172));
				show_center_content("jail");
			}
		});

		tax_start.setBorderPainted(true);
		tax_start.setForeground(Color.WHITE);
		tax_start.setOpaque(true);
		tax_start.setBackground(new Color(170, 203, 172));
		tax_start.setBorder(new MatteBorder(0, 0, 0, 2, Color.WHITE));
		tax_start.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tax_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tax_start.setBackground(new Color(108, 125, 110));
				auction.setBackground(new Color(170, 203, 172));
				board.setBackground(new Color(170, 203, 172));
				terrain.setBackground(new Color(170, 203, 172));
				station_elec.setBackground(new Color(170, 203, 172));
				jail.setBackground(new Color(170, 203, 172));
				general.setBackground(new Color(170, 203, 172));
				cards.setBackground(new Color(170, 203, 172));
				show_center_content("start");
			}
		});

		cards.setBorderPainted(true);
		cards.setOpaque(true);
		cards.setBackground(new Color(170, 203, 172));
		cards.setBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE));
		cards.setForeground(Color.WHITE);
		cards.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cards.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.setBackground(new Color(108, 125, 110));
				auction.setBackground(new Color(170, 203, 172));
				board.setBackground(new Color(170, 203, 172));
				terrain.setBackground(new Color(170, 203, 172));
				station_elec.setBackground(new Color(170, 203, 172));
				jail.setBackground(new Color(170, 203, 172));
				tax_start.setBackground(new Color(170, 203, 172));
				general.setBackground(new Color(170, 203, 172));
				show_center_content("cards");
			}
		});

		buttons.add(general);
		buttons.add(auction);
		buttons.add(board);
		buttons.add(terrain);
		buttons.add(station_elec);
		buttons.add(jail);
		buttons.add(tax_start);
		buttons.add(cards);

		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(w, h_content));
		content.setBackground(Color.WHITE);

		Setting_configuration_general_view gen = new Setting_configuration_general_view(w, h);
		Setting_configuration_auction_view auc = new Setting_configuration_auction_view(w, h);
		Setting_configuration_board_view boa = new Setting_configuration_board_view(w, h);
		Setting_configuration_terrain_view ter = new Setting_configuration_terrain_view(w, h);
		Setting_configuration_station_view stat = new Setting_configuration_station_view(w, h);
		Setting_configuration_jail_view jai = new Setting_configuration_jail_view(w, h);
		Setting_configuration_start_view sta = new Setting_configuration_start_view(w, h);
		Setting_configuration_cards_view cds = new Setting_configuration_cards_view(w, h);

		center_container = new JPanel();
		center_container_cl = new CardLayout();
		center_container.setLayout(center_container_cl);

		center_container.add(gen, "general");
		center_container.add(auc, "auction");
		center_container.add(boa, "board");
		center_container.add(ter, "terrain");
		center_container.add(stat, "station");
		center_container.add(jai, "jail");
		center_container.add(sta, "start");
		center_container.add(cds, "cards");

		center_container_cl.show(center_container, "general");

		container.add(buttons, BorderLayout.NORTH);
		container.add(center_container, BorderLayout.CENTER);

		this.center.add(container, BorderLayout.CENTER);
	}

	/**
	 * Methode permettant de montrer un panel par rapport à son nom dans le
	 * cardlayout
	 * 
	 * @param name
	 */
	public void show_center_content(String name) {
		center_container_cl.show(center_container, name);
	}

	@Override
	protected void build_footer(float w_mod, float h_mod) {
		super.build_footer(w_mod, h_mod);
		this.footer.setBackground(new Color(129, 198, 134));
		this.footer.setLayout(new GridLayout(1, 5));
		this.footer.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));

		My_JButton configure = new My_JButton("Configure", 25.f);
		configure.setForeground(Color.WHITE);
		configure.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		configure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window_ref.launch_server(port, per_game);
			}
		});

		footer.add(new JLabel());
		footer.add(new JLabel());
		footer.add(new JLabel());
		footer.add(new JLabel());
		footer.add(configure);
	}

	@Override
	protected void build_right(float w_mod, float h_mod) {
		super.build_right(w_mod, h_mod);
		this.right.setBackground(Color.YELLOW);
	}

	@Override
	protected void build_left(float w_mod, float h_mod) {
		super.build_left(w_mod, h_mod);
		this.left.setBackground(Color.WHITE);
		this.left.setLayout(new BorderLayout());
		this.left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));

	}
}
