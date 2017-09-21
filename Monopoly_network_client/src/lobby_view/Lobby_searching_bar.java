package lobby_view;

import UI_elements.My_JLabel;

@SuppressWarnings("serial")
public class Lobby_searching_bar extends My_JLabel implements Runnable {

	volatile private boolean is_running;

	/**
	 * Constructeur de la classe qui représente l'animation de la recherche de
	 * partie. Cette classe est simplement lancer lorsque l'on lance la
	 * recherche de partie pour avoir les petits points qui crée une animation
	 */
	public Lobby_searching_bar() {
		super("", 50.f);
		is_running = false;
	}

	/**
	 * Permet de décider si on veut lancer ou non l'animation
	 * 
	 * @param running
	 */
	public void set_is_running(boolean running) {
		is_running = running;
		if (!running) {
			this.setText("");
		}
	}

	/**
	 * Permet de savoir si l'animation est en cours de fonctionnement
	 * 
	 * @return
	 */
	public boolean is_running() {
		return this.is_running;
	}

	/**
	 * Permet de savoir a combien de point l'on est dans l'animation
	 * 
	 * @param s
	 *            la caine ou l'on veut compter le nombre de point
	 * @return le nombre de point
	 */
	private int count_nb_dot(String s) {
		int nb = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '.') {
				nb++;
			}
		}
		return nb;
	}

	@Override
	public void run() {
		is_running = true;
		while (is_running) {
			this.setText(this.getText() + " . ");
			if (count_nb_dot(this.getText()) == 6) {
				this.setText(". ");
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
