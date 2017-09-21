package credits_view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import UI_elements.My_JButton;
import lobby_view.Lobby_view;
import menus.Abstract_panel;

@SuppressWarnings("serial")
public class Credits_view extends Abstract_panel {

	private JPanel center_container;
	private CardLayout center_container_cl;

	private Lobby_view parent_ref;

	private General_credits_view general_credits;
	private Problems_credits_view problems_credits;
	private Difficulties_credits_view difficulties_credits;
	private Features_credits_view features_credits;

	/**
	 * Constructeur de la classe qui représente de manière général les crédits
	 * notre application. Cette classe contient un cardlayout pour permettre de
	 * choisir quel crédit on veut afficher.
	 * 
	 * @param width
	 *            la largeur de notre panel
	 * @param height
	 *            la hauteur de notre panel
	 * @param parent_ref
	 *            la référence sur le panel parent pour pouvoir revenir
	 *            facilement
	 */
	public Credits_view(int width, int height, Lobby_view parent_ref) {
		super(width, height);
		this.parent_ref = parent_ref;

		this.build_header(1.0f, 0.15f);
		this.build_center(1.0f, 0.70f);
		this.build_footer(1.0f, 0.15f);
	}

	@Override
	protected void build_header(float w_mod, float h_mod) {
		super.build_header(w_mod, h_mod);
		this.header.setBackground(new Color(170, 203, 172));
		this.header.setLayout(new BorderLayout());
		this.header.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		JPanel container = new JPanel();
		container.setBackground(new Color(170, 203, 172));
		container.setLayout(new GridLayout(1, 4));

		My_JButton general = new My_JButton("General", 17.f);
		My_JButton problems = new My_JButton("Problems", 17.f);
		My_JButton difficulties = new My_JButton("Difficulties", 17.f);
		My_JButton fonct = new My_JButton("Features", 17.f);

		general.setForeground(Color.WHITE);
		general.setBorderPainted(true);
		general.setBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE));
		general.setBackground(new Color(108, 125, 110));
		general.setOpaque(true);
		general.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		general.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				general.setBackground(new Color(108, 125, 110));
				problems.setBackground(new Color(170, 203, 172));
				difficulties.setBackground(new Color(170, 203, 172));
				fonct.setBackground(new Color(170, 203, 172));
				show_center_content("general");
			}
		});

		problems.setForeground(Color.WHITE);
		problems.setBorderPainted(true);
		problems.setBorder(new MatteBorder(0, 2, 0, 0, Color.WHITE));
		problems.setBackground(new Color(170, 203, 172));
		problems.setOpaque(true);
		problems.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		problems.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				general.setBackground(new Color(170, 203, 172));
				problems.setBackground(new Color(108, 125, 110));
				difficulties.setBackground(new Color(170, 203, 172));
				fonct.setBackground(new Color(170, 203, 172));
				show_center_content("problems");
			}
		});

		difficulties.setForeground(Color.WHITE);
		difficulties.setBorderPainted(true);
		difficulties.setBorder(new MatteBorder(0, 2, 0, 0, Color.WHITE));
		difficulties.setBackground(new Color(170, 203, 172));
		difficulties.setOpaque(true);
		difficulties.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		difficulties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				general.setBackground(new Color(170, 203, 172));
				problems.setBackground(new Color(170, 203, 172));
				difficulties.setBackground(new Color(108, 125, 110));
				fonct.setBackground(new Color(170, 203, 172));
				show_center_content("difficulties");
			}
		});

		fonct.setForeground(Color.WHITE);
		fonct.setBorderPainted(true);
		fonct.setBorder(new MatteBorder(0, 2, 0, 0, Color.WHITE));
		fonct.setBackground(new Color(170, 203, 172));
		fonct.setOpaque(true);
		fonct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fonct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				general.setBackground(new Color(170, 203, 172));
				problems.setBackground(new Color(170, 203, 172));
				difficulties.setBackground(new Color(170, 203, 172));
				fonct.setBackground(new Color(108, 125, 110));
				show_center_content("features");
			}
		});

		container.add(general);
		container.add(problems);
		container.add(difficulties);
		container.add(fonct);

		this.header.add(container, BorderLayout.CENTER);
	}

	@Override
	protected void build_center(float w_mod, float h_mod) {
		super.build_center(w_mod, h_mod);
		this.center.setLayout(new BorderLayout());
		this.center.setBackground(Color.WHITE);

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		center_container = new JPanel();
		center_container_cl = new CardLayout();
		center_container.setLayout(center_container_cl);

		general_credits = new General_credits_view(w, h);
		problems_credits = new Problems_credits_view(w, h);
		difficulties_credits = new Difficulties_credits_view(w, h);
		features_credits = new Features_credits_view(w, h);

		center_container.add(general_credits, "general");
		center_container.add(problems_credits, "problems");
		center_container.add(difficulties_credits, "difficulties");
		center_container.add(features_credits, "features");

		center_container_cl.show(center_container, "general");

		this.center.add(center_container, BorderLayout.CENTER);
	}

	/**
	 * Méthode permettant d'afficher un panel par rapport au nom qu'on lui a
	 * donné à sa création
	 * 
	 * @param name
	 *            le nom du panel que l'on veut afficher
	 */
	public void show_center_content(String name) {
		center_container_cl.show(center_container, name);
	}

	@Override
	protected void build_footer(float w_mod, float h_mod) {
		super.build_footer(w_mod, h_mod);
		this.footer.setBackground(new Color(170, 203, 172));
		this.footer.setLayout(new BorderLayout());
		this.footer.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));

		JPanel container = new JPanel();
		container.setOpaque(false);
		container.setLayout(new GridLayout(1, 4, 0, 35));

		My_JButton back = new My_JButton("Return", 25.f);
		back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		back.setForeground(Color.WHITE);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent_ref.show_center_content("buttons");
			}
		});

		container.add(back);
		container.add(new JLabel());
		container.add(new JLabel());
		container.add(new JLabel());

		this.footer.add(container, BorderLayout.CENTER);
	}

	@Override
	protected void build_right(float w_mod, float h_mod) {
		super.build_right(w_mod, h_mod);
		this.right.setBackground(new Color(170, 203, 172));
		this.right.setBorder(new MatteBorder(0, 5, 0, 0, new Color(117, 138, 119)));
	}

	@Override
	protected void build_left(float w_mod, float h_mod) {
		super.build_left(w_mod, h_mod);
		this.left.setBackground(new Color(170, 203, 172));
		this.left.setLayout(new BorderLayout());
		this.left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));
	}
}
