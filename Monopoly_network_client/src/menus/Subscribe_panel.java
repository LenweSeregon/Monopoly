package menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import network.Client;
import utils.Resources;

@SuppressWarnings("serial")
public class Subscribe_panel extends JPanel {

	private My_JLabel error_message;

	/**
	 * Constructeur de la classe représentant le panneau d'inscription dans la
	 * fenetre de démarrage. Cette classe n'a pas besoin d'être plus commenté,
	 * elle initialise les différents élements et réagit au clique sur le jlabel
	 * en bas de la fenetre pour changer de panneau vers la connexion
	 * 
	 * @param width
	 *            la largeur du panneau d'inscription
	 * @param height
	 *            la hauteur du panneau d'inscription
	 * @param name
	 *            le nom qui référence le panneau
	 * @param ref
	 *            une référence vers le pannel principal pour changer vers
	 *            connexion si besoin
	 */
	public Subscribe_panel(int width, int height, String name, Starting_menu ref) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());

		int h_up = (int) (height * 0.15);
		int h_center = (int) (height * 0.60);
		int h_down = (int) (height * 0.25);

		JPanel up_center = new JPanel();
		up_center.setPreferredSize(new Dimension(width, h_up));
		up_center.setLayout(new BorderLayout());
		up_center.setBackground(new Color(161, 213, 165));

		My_JLabel title = new My_JLabel("Register", 40.f);
		title.setForeground(Color.WHITE);
		up_center.add(title, BorderLayout.CENTER);

		JPanel mid_center = new JPanel();
		mid_center.setBorder(new EmptyBorder(70, 110, 70, 110));
		mid_center.setPreferredSize(new Dimension(width, h_center));
		mid_center.setLayout(new GridLayout(3, 2, 50, 35));
		mid_center.setBackground(new Color(181, 233, 185));

		My_JLabel username = new My_JLabel("Username", 25.f);
		username.setForeground(Color.WHITE);

		JTextField usr_value = new JTextField();
		JPasswordField pwd_value = new JPasswordField();
		JPasswordField confirm_pwd_value = new JPasswordField();

		usr_value.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, 20.f));
		usr_value.setHorizontalAlignment(JTextField.CENTER);
		usr_value.setBorder(new LineBorder(Color.BLACK, 6));
		usr_value.setBackground(Color.WHITE);
		usr_value.setForeground(Color.BLACK);
		usr_value.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						if (!is_strict_alphanumeric(String.valueOf(pwd_value.getPassword()))
								|| !is_strict_alphanumeric(String.valueOf(confirm_pwd_value.getPassword()))) {
							error_message.setText("Some inputs no alphanumeric");
							error_message.setForeground(Color.RED);
							error_message.setVisible(true);
							return;
						}

						MessageDigest md5 = MessageDigest.getInstance("MD5");
						String hex = (new HexBinaryAdapter())
								.marshal(md5.digest(String.valueOf(pwd_value.getPassword()).getBytes()));
						try_subscribe_server(usr_value.getText(), hex, hex);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		My_JLabel password = new My_JLabel("Password", 25.f);
		password.setForeground(Color.WHITE);

		pwd_value.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, 20.f));
		pwd_value.setHorizontalAlignment(JTextField.CENTER);
		pwd_value.setBorder(new LineBorder(Color.BLACK, 6));
		pwd_value.setBackground(Color.WHITE);
		pwd_value.setForeground(Color.BLACK);
		pwd_value.setEchoChar('*');
		pwd_value.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						if (!is_strict_alphanumeric(String.valueOf(pwd_value.getPassword()))
								|| !is_strict_alphanumeric(String.valueOf(confirm_pwd_value.getPassword()))) {
							error_message.setText("Some inputs no alphanumeric");
							error_message.setForeground(Color.RED);
							error_message.setVisible(true);
							return;
						}
						MessageDigest md5 = MessageDigest.getInstance("MD5");
						String hex = (new HexBinaryAdapter())
								.marshal(md5.digest(String.valueOf(pwd_value.getPassword()).getBytes()));
						try_subscribe_server(usr_value.getText(), hex, hex);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		My_JLabel confirm_password = new My_JLabel("Confirm pass", 25.f);
		confirm_password.setForeground(Color.WHITE);

		confirm_pwd_value.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, 20.f));
		confirm_pwd_value.setHorizontalAlignment(JTextField.CENTER);
		confirm_pwd_value.setBorder(new LineBorder(Color.BLACK, 6));
		confirm_pwd_value.setBackground(Color.WHITE);
		confirm_pwd_value.setForeground(Color.BLACK);
		confirm_pwd_value.setEchoChar('*');
		confirm_pwd_value.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						if (!is_strict_alphanumeric(String.valueOf(pwd_value.getPassword()))
								|| !is_strict_alphanumeric(String.valueOf(confirm_pwd_value.getPassword()))) {
							error_message.setText("Some inputs no alphanumeric");
							error_message.setForeground(Color.RED);
							error_message.setVisible(true);
							return;
						}
						MessageDigest md5 = MessageDigest.getInstance("MD5");
						String hex = (new HexBinaryAdapter())
								.marshal(md5.digest(String.valueOf(pwd_value.getPassword()).getBytes()));
						try_subscribe_server(usr_value.getText(), hex, hex);
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		mid_center.add(username);
		mid_center.add(usr_value);
		mid_center.add(password);
		mid_center.add(pwd_value);
		mid_center.add(confirm_password);
		mid_center.add(confirm_pwd_value);

		JPanel down_center = new JPanel();
		down_center.setPreferredSize(new Dimension(width, h_down));
		down_center.setLayout(new GridLayout(3, 1, 10, 20));
		down_center.setBorder(new EmptyBorder(0, 220, 0, 220));
		down_center.setBackground(new Color(181, 233, 185));

		error_message = new My_JLabel("", 20.f);
		error_message.setForeground(Color.RED);
		error_message.setVisible(false);

		My_JButton validate = new My_JButton("Validate", 30.f);
		validate.setForeground(Color.WHITE);
		validate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		validate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (!is_strict_alphanumeric(String.valueOf(pwd_value.getPassword()))
							|| !is_strict_alphanumeric(String.valueOf(confirm_pwd_value.getPassword()))) {
						error_message.setText("Some inputs no alphanumeric");
						error_message.setForeground(Color.RED);
						error_message.setVisible(true);
						return;
					}
					MessageDigest md5 = MessageDigest.getInstance("MD5");
					String hex = (new HexBinaryAdapter())
							.marshal(md5.digest(String.valueOf(pwd_value.getPassword()).getBytes()));
					try_subscribe_server(usr_value.getText(), hex, hex);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
			}
		});

		My_JLabel already_sub = new My_JLabel("<html>Already register ? <font color='red'>Click here</font></html>",
				20.f);
		already_sub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		already_sub.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				ref.show_center_content(name);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		down_center.add(error_message);
		down_center.add(validate);
		down_center.add(already_sub);

		this.add(up_center, BorderLayout.NORTH);
		this.add(mid_center, BorderLayout.CENTER);
		this.add(down_center, BorderLayout.SOUTH);

	}

	private boolean is_strict_alphanumeric(String str) {
		if (str.length() == 0) {
			return false;
		}
		return str.matches("[A-Za-z0-9]+");
	}

	/**
	 * Méthode permettant d'essayer de s'incrire sur le serveur en respectant
	 * les conditions
	 * 
	 * @param pseudo
	 *            le pseudo avec lequel on essaye
	 * @param pwd
	 *            le password avec lequel on essaye
	 * @param pwd_conf
	 *            la confirmation de password
	 */
	public void try_subscribe_server(String pseudo, String pwd, String pwd_conf) {
		if (!pseudo.isEmpty() && !pwd.isEmpty() && !pwd_conf.isEmpty()) {
			if (pwd.equals(pwd_conf)) {
				try {
					if (!is_strict_alphanumeric(pseudo) || !is_strict_alphanumeric(pwd)
							|| !is_strict_alphanumeric(pwd_conf)) {
						error_message.setText("Some inputs no alphanumeric");
						error_message.setForeground(Color.RED);
						error_message.setVisible(true);
						return;
					}
					Client c = new Client(InetAddress.getLocalHost(), 2000, pseudo);
					c.connect_to_server();
					c.send_message("ACCOUNT" + " " + "SUBSCRIPTION" + " " + pseudo + " " + pwd + " " + pwd_conf);

					String res = c.receive_message();
					if (res.equals("SUBSCRIPTION_ACCEPTED")) {
						error_message.setText("Inscription succeed");
						error_message.setForeground(Color.GREEN);
						error_message.setVisible(true);
					} else if (res.equals("PSEUDO_ALREADY_TAKEN")) {
						error_message.setText("Pseudo already taken");
						error_message.setForeground(Color.RED);
						error_message.setVisible(true);
					} else {
						error_message.setText("Inscription impossible");
						error_message.setForeground(Color.RED);
						error_message.setVisible(true);
					}

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					error_message.setText("Connect to server impossible");
					error_message.setVisible(true);
				}
			} else {
				error_message.setText("Both password won't match");
				error_message.setVisible(true);
			}
		} else {
			error_message.setText("Some input are empty");
			error_message.setVisible(true);
		}
	}
}
