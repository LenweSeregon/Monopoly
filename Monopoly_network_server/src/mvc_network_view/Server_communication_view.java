package mvc_network_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import utils.Resources;

@SuppressWarnings("serial")
public class Server_communication_view extends JPanel {

	private Vector<String> messages;
	private JTextArea message_list;

	/**
	 * Constructeur de la classe qui représente la console de communication du
	 * serveur. Cette console va permettre de voir toutes les informations qui
	 * transiste sur le serveur
	 * 
	 * @param width
	 *            la largeur de la console
	 * @param height
	 *            la hauteur de la console
	 */
	public Server_communication_view(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.messages = new Vector<String>();

		int h_up = (int) (height * 0.2);
		int h_center = (int) (height * 0.65);
		int h_down = (int) (height * 0.15);

		JPanel up = new JPanel();
		up.setPreferredSize(new Dimension(width, h_up));
		up.setLayout(new BorderLayout());
		up.setBackground(Color.WHITE);

		My_JLabel title = new My_JLabel("Console", 45.f);
		title.setForeground(new Color(129, 198, 134));
		up.add(title);

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(width, h_center));
		center.setLayout(new BorderLayout());
		center.setBackground(Color.WHITE);
		center.setBorder(new EmptyBorder(0, 40, 0, 40));

		message_list = new JTextArea();
		message_list.setLineWrap(true);
		message_list.setWrapStyleWord(true);
		message_list.setFont(Resources.get_font("kabel.ttf").deriveFont(Font.BOLD, 16.f));
		message_list.setBorder(new EmptyBorder(20, 20, 20, 20));
		message_list.setForeground(Color.WHITE);
		message_list.setEditable(false);
		message_list.setBackground(new Color(170, 203, 172));

		JScrollPane scroll = new JScrollPane(message_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(width, h_center));
		scroll.setOpaque(true);
		scroll.setBackground(Color.BLACK);

		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scroll.getViewport().setPreferredSize(new Dimension(width, h_center));
		scroll.getViewport().setBorder(null);
		scroll.setViewportBorder(null);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(4);

		JPanel scroll_container = new JPanel();
		scroll_container.setBorder(new LineBorder(new Color(117, 138, 119), 5));
		scroll_container.setLayout(new BorderLayout());
		scroll_container.add(scroll, BorderLayout.CENTER);

		center.add(scroll_container, BorderLayout.CENTER);

		JPanel down = new JPanel();
		down.setPreferredSize(new Dimension(width, h_down));
		down.setLayout(new GridLayout(1, 2, 20, 50));
		down.setBackground(Color.WHITE);

		My_JButton clear = new My_JButton("Clear", 20.f);
		clear.setForeground(new Color(129, 198, 134));
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				message_list.setText("");
			}
		});
		My_JButton fake = new My_JButton("Fake", 20.f);
		fake.setForeground(new Color(129, 198, 134));

		down.add(clear);
		down.add(fake);

		this.add(up, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(down, BorderLayout.SOUTH);

	}

	/**
	 * Méthode permettant d'ajouter un message à notre console
	 * 
	 * @param message
	 *            le message a ajouter
	 */
	public void add_message(String message) {
		this.messages.add(message);
		message_list.append(" > " + message + "\n");
	}
}
