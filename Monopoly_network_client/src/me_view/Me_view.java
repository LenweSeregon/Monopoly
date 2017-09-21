package me_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import lobby_view.Lobby_view;
import menus.Abstract_panel;

@SuppressWarnings("serial")
public class Me_view extends Abstract_panel {

	private Lobby_view parent_ref;

	/**
	 * Constructeur de la classe qui représente la vue qui donne les différents
	 * informations sur le créateur de l'application
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 * @param parent_ref
	 *            la référenec vers le parent SWING contenant
	 */
	public Me_view(int width, int height, Lobby_view parent_ref) {
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

		My_JLabel title = new My_JLabel("Me", 35.f);
		title.setForeground(Color.WHITE);

		this.header.add(title, BorderLayout.CENTER);
	}

	@Override
	protected void build_center(float w_mod, float h_mod) {
		super.build_center(w_mod, h_mod);
		this.center.setLayout(new BorderLayout());
		this.center.setBackground(Color.WHITE);

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		JPanel desc = new JPanel();
		desc.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));
		desc.setLayout(new BorderLayout());
		desc.setBackground(Color.WHITE);
		desc.setPreferredSize(new Dimension(w, h));

		My_JLabel title_desc = new My_JLabel("<html> Me, myself and I :</html>", 33.f);
		title_desc.setBorder(new EmptyBorder(20, 20, 0, 0));
		title_desc.setHorizontalAlignment(JLabel.LEFT);
		title_desc.setPreferredSize(new Dimension(w, (int) (h * 0.25)));
		title_desc.setForeground(Color.DARK_GRAY);

		My_JLabel text_desc = new My_JLabel(
				"<html> <div style='text-align: justify;'> My name is Nicolas Serf, at the time of writing this program,"
						+ " I am a third year student of license. One big module of this year"
						+ " is the COO which is realized in Java in which we have to realize this monopoly. "
						+ "In the future, I would like to orient myself in a master's degree in software engineering and "
						+ "specialized in video games. My professional project would certainly be to integrate a video game "
						+ "studio and in the future to create my own company. </div></html>",
				15.f);
		text_desc.setBorder(new EmptyBorder(0, 20, 20, 20));
		text_desc.setForeground(Color.DARK_GRAY);
		text_desc.setPreferredSize(new Dimension(w, (int) (h * 0.75)));
		text_desc.setMaximumSize(new Dimension(w, (int) (h * 0.75)));

		desc.add(title_desc, BorderLayout.NORTH);
		desc.add(text_desc, BorderLayout.CENTER);

		this.center.add(desc, BorderLayout.CENTER);

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
		this.right.setLayout(new BorderLayout());
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
