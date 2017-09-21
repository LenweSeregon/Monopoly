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
public class Setting_configuration_jail_view extends JPanel {

	/**
	 * Constructeur de la classe preséentant la vue pour le parametrage des
	 * options pour la prison
	 * 
	 * @param width
	 *            largeur du panel
	 * @param height
	 *            hauteur du panel
	 */
	public Setting_configuration_jail_view(int width, int height) {
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

		My_JLabel time_jail_val = new My_JLabel("Time in jail : 3 turn", 15.f);

		My_JLabel sum_to_out_val = new My_JLabel("Sum to get out jail : 5000M", 15.f);

		// CENTER

		// LEFT

		// NB TURN JAIL
		My_JLabel title_turn_jail = new My_JLabel("Time in jail : ", 20.f);
		title_turn_jail.setHorizontalAlignment(JLabel.LEFT);

		JSlider turn_in_jail = new JSlider();
		Hashtable<Integer, JLabel> table_turn_jail = new Hashtable<Integer, JLabel>();
		table_turn_jail.put(1, new JLabel("1"));
		table_turn_jail.put(2, new JLabel("2"));
		table_turn_jail.put(3, new JLabel("3"));
		table_turn_jail.put(4, new JLabel("4"));
		table_turn_jail.put(5, new JLabel("5"));
		turn_in_jail.setLabelTable(table_turn_jail);
		turn_in_jail.setPaintLabels(true);
		turn_in_jail.setSnapToTicks(true);
		turn_in_jail.setMaximum(5);
		turn_in_jail.setMinimum(1);
		turn_in_jail.setValue(3);
		turn_in_jail.setMajorTickSpacing(1);
		turn_in_jail.setMinorTickSpacing(1);
		turn_in_jail.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Setting_configuration.nb_turn_in_jail = source.getValue();
				time_jail_val.setText("Time in jail : " + source.getValue() + " turn");
			}
		});

		// SUM JAIL
		My_JLabel title_sum_jail = new My_JLabel("Price to go out of jail : ", 20.f);
		title_sum_jail.setHorizontalAlignment(JLabel.LEFT);

		JSlider sum_jail = new JSlider();
		Hashtable<Integer, JLabel> table_sum_jail = new Hashtable<Integer, JLabel>();
		table_sum_jail.put(0, new JLabel("0"));
		table_sum_jail.put(2500, new JLabel("2500"));
		table_sum_jail.put(5000, new JLabel("5000"));
		table_sum_jail.put(7500, new JLabel("7500"));
		table_sum_jail.put(10000, new JLabel("10000"));
		sum_jail.setLabelTable(table_sum_jail);
		sum_jail.setPaintLabels(true);
		sum_jail.setSnapToTicks(true);
		sum_jail.setMaximum(10000);
		sum_jail.setMinimum(0);
		sum_jail.setValue(5000);
		sum_jail.setMajorTickSpacing(100);
		sum_jail.setMinorTickSpacing(100);
		sum_jail.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Setting_configuration.sum_to_out_jail = source.getValue();
				sum_to_out_val.setText("Sum to get out jail : " + source.getValue() + "M");
			}
		});

		// GAIN MONEY IN JAIL

		JPanel allow_gain_money = new JPanel();
		allow_gain_money.setLayout(new GridLayout(1, 2));

		My_JLabel title_gain_money = new My_JLabel("Allow earn money in jail : ", 20.f);
		title_gain_money.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_gain_money = new JRadioButton("Enable");
		enable_gain_money.setActionCommand("enable");
		enable_gain_money.setSelected(false);
		enable_gain_money.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.allow_earn_money_during_jailing = true;
			}
		});

		JRadioButton disable_gain_money = new JRadioButton("Disable");
		disable_gain_money.setActionCommand("disable");
		disable_gain_money.setSelected(true);
		disable_gain_money.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.allow_earn_money_during_jailing = false;
			}
		});

		ButtonGroup group_jail_sum = new ButtonGroup();
		group_jail_sum.add(enable_gain_money);
		group_jail_sum.add(disable_gain_money);

		allow_gain_money.add(enable_gain_money);
		allow_gain_money.add(disable_gain_money);

		// RENT IN JAIL

		JPanel rent_turn_jail = new JPanel();
		rent_turn_jail.setLayout(new GridLayout(1, 2));

		My_JLabel title_rent_jail = new My_JLabel("Rent to jail each turn ", 20.f);
		title_rent_jail.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_rent_turn_jail = new JRadioButton("Enable");
		enable_rent_turn_jail.setActionCommand("enable");
		enable_rent_turn_jail.setSelected(false);
		enable_rent_turn_jail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.rent_each_turn_jailing = true;
			}
		});

		JRadioButton disable_rent_turn_jail = new JRadioButton("Disable");
		disable_rent_turn_jail.setActionCommand("disable");
		disable_rent_turn_jail.setSelected(true);
		disable_rent_turn_jail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.rent_each_turn_jailing = false;
			}
		});

		ButtonGroup group_rent_jail = new ButtonGroup();
		group_rent_jail.add(enable_rent_turn_jail);
		group_rent_jail.add(disable_rent_turn_jail);

		rent_turn_jail.add(enable_rent_turn_jail);
		rent_turn_jail.add(disable_rent_turn_jail);

		// ALLOW JAIL IF 3 DOUBLE

		JPanel jail_3_double = new JPanel();
		jail_3_double.setLayout(new GridLayout(1, 2));

		My_JLabel title_3_double = new My_JLabel("Active jail if 3 double ", 20.f);
		title_3_double.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_3_double = new JRadioButton("Enable");
		enable_3_double.setActionCommand("enable");
		enable_3_double.setSelected(true);
		enable_3_double.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.jail_3_double = true;
			}
		});

		JRadioButton disable_3_double = new JRadioButton("Disable");
		disable_3_double.setActionCommand("disable");
		disable_3_double.setSelected(false);
		disable_3_double.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.jail_3_double = false;
			}
		});

		ButtonGroup group_steal_card = new ButtonGroup();
		group_steal_card.add(enable_3_double);
		group_steal_card.add(disable_3_double);

		jail_3_double.add(enable_3_double);
		jail_3_double.add(disable_3_double);

		left.add(title_turn_jail);
		left.add(turn_in_jail);
		left.add(title_sum_jail);
		left.add(sum_jail);
		left.add(title_gain_money);
		left.add(allow_gain_money);
		left.add(title_rent_jail);
		left.add(rent_turn_jail);
		left.add(title_3_double);
		left.add(jail_3_double);
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());
		left.add(new JPanel());

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
		center.add(new JPanel());
		center.add(new JPanel());

		right.add(time_jail_val);
		right.add(sum_to_out_val);
		right.add(new JPanel());
		right.add(new JPanel());
		right.add(new JPanel());

		this.add(left, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
	}
}
