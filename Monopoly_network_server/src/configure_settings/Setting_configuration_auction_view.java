package configure_settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import UI_elements.My_JLabel;

@SuppressWarnings("serial")
public class Setting_configuration_auction_view extends JPanel {

	/**
	 * Constructeur de la classe preséentant la vue pour le parametrage des
	 * options pour les encheres
	 * 
	 * @param width
	 *            largeur du panel
	 * @param height
	 *            hauteur du panel
	 */
	public Setting_configuration_auction_view(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setBackground(Color.BLACK);

		int width_left = (int) (width * 0.35);
		int width_center = (int) (width * 0.40);
		int width_right = (int) (width * 0.20);

		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(width_left, height));
		left.setLayout(new GridLayout(16, 1));
		left.setBorder(new MatteBorder(0, 0, 0, 3, Color.BLACK));

		JPanel center = new JPanel();
		center.setPreferredSize(new Dimension(width_center, height));
		center.setLayout(new GridLayout(16, 1));
		center.setBorder(new EmptyBorder(0, 20, 0, 20));

		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(width_right, height));
		right.setLayout(new GridLayout(5, 1));
		right.setBorder(new MatteBorder(0, 3, 0, 0, Color.BLACK));

		// RIGHT

		My_JLabel auction_val = new My_JLabel("Auction time : 30 seconds", 15.f);

		// CENTER

		My_JLabel title_auction_val = new My_JLabel("Time for auction : ", 20.f);
		title_auction_val.setHorizontalAlignment(JLabel.LEFT);

		JSlider nb_minute_auction = new JSlider();
		Hashtable<Integer, JLabel> table_auction = new Hashtable<Integer, JLabel>();
		table_auction.put(10, new JLabel("10"));
		table_auction.put(20, new JLabel("20"));
		table_auction.put(30, new JLabel("30"));
		table_auction.put(40, new JLabel("40"));
		table_auction.put(50, new JLabel("50"));
		nb_minute_auction.setLabelTable(table_auction);
		nb_minute_auction.setPaintLabels(true);
		nb_minute_auction.setSnapToTicks(true);
		nb_minute_auction.setMaximum(50);
		nb_minute_auction.setMinimum(10);
		nb_minute_auction.setValue(30);
		nb_minute_auction.setMajorTickSpacing(10);
		nb_minute_auction.setMinorTickSpacing(10);
		nb_minute_auction.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Setting_configuration.minutes_for_auction = source.getValue();
				auction_val.setText("Auction time : " + source.getValue() + " seconds");
			}
		});

		// LEFT

		// ALLOW AUCTION

		JPanel allow_auction = new JPanel();
		allow_auction.setLayout(new GridLayout(1, 2));

		My_JLabel title_allow_auction = new My_JLabel("Allow auction ", 20.f);
		title_allow_auction.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_auction = new JRadioButton("Enable");
		enable_auction.setActionCommand("enable");
		enable_auction.setSelected(true);
		enable_auction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.auction_allow = true;
				Setting_configuration.minutes_for_auction = nb_minute_auction.getValue();
				auction_val.setText("Auction time : " + nb_minute_auction.getValue() + " minutes");
				nb_minute_auction.setVisible(true);
				title_auction_val.setVisible(true);
			}
		});

		JRadioButton disable_auction = new JRadioButton("Disable");
		disable_auction.setActionCommand("disable");
		disable_auction.setSelected(false);
		disable_auction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.auction_allow = false;
				nb_minute_auction.setValue(3);
				Setting_configuration.minutes_for_auction = -1;
				auction_val.setText("Auction time : Unspecify");
				nb_minute_auction.setVisible(false);
				title_auction_val.setVisible(false);
			}
		});

		ButtonGroup group_jail_sum = new ButtonGroup();
		group_jail_sum.add(enable_auction);
		group_jail_sum.add(disable_auction);

		allow_auction.add(enable_auction);
		allow_auction.add(disable_auction);

		left.add(title_allow_auction);
		left.add(allow_auction);
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());

		center.add(title_auction_val);
		center.add(nb_minute_auction);
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());

		right.add(auction_val);
		right.add(new JPanel());
		right.add(new JPanel());
		right.add(new JPanel());
		right.add(new JPanel());

		this.add(left, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
	}
}
