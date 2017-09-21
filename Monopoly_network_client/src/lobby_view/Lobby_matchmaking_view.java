package lobby_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import UI_elements.My_JLabel;

@SuppressWarnings("serial")
public class Lobby_matchmaking_view extends JPanel {

	private Lobby_searching_bar bar;

	/**
	 * Constructeur de la classe qui représente la vue de matchmaking. Cette
	 * classe est surtout ici pour wrapper et contenir la classe qui permet
	 * l'animation de la recherche de partie
	 * 
	 * @param width
	 *            la largeur de la fenetre
	 * @param height
	 *            la hauteur de la fenetre
	 */
	public Lobby_matchmaking_view(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);

		int h_up = (int) (height * 0.2);
		int h_center = (int) (height * 0.4);
		int h_down = (int) (height * 0.3);

		JPanel up = new JPanel();
		up.setLayout(new BorderLayout());
		up.setPreferredSize(new Dimension(width, h_up));

		My_JLabel title = new My_JLabel("Matchmaking", 40.f);
		up.add(title, BorderLayout.CENTER);

		JPanel center = new JPanel();
		center.setBorder(new EmptyBorder(150, 50, 50, 50));
		center.setLayout(new GridLayout(2, 1, 50, 20));
		center.setPreferredSize(new Dimension(width, h_center));

		My_JLabel search = new My_JLabel("Ongoing research", 30.f);

		bar = new Lobby_searching_bar();
		center.add(search);
		center.add(bar);

		JPanel down = new JPanel();
		down.setPreferredSize(new Dimension(width, h_down));

		this.add(up, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(down, BorderLayout.SOUTH);
	}

	public void stop_bar() {
		this.bar.set_is_running(false);
	}

	public void launch_bar() {
		if (!this.bar.is_running()) {
			new Thread(bar).start();
		}
	}
}
