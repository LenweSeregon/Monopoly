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
public class Setting_configuration_general_view extends JPanel {

	/**
	 * Constructeur de la classe preséentant la vue pour le parametrage des
	 * options pour le jeu en général
	 * 
	 * @param width
	 *            largeur du panel
	 * @param height
	 *            hauteur du panel
	 */
	public Setting_configuration_general_view(int width, int height) {
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

		// RIGHT PART

		My_JLabel money_val = new My_JLabel("Start money : 150000", 15.f);

		My_JLabel dice_val = new My_JLabel("Number of dice : 2", 15.f);

		My_JLabel house_val = new My_JLabel("Number of house : 32", 15.f);

		My_JLabel hotel_val = new My_JLabel("Number of hotel : 12", 15.f);

		My_JLabel turn_end_val = new My_JLabel("Turn end : Unspecify", 15.f);

		// CENTER PART

		// JAIL SUM

		JPanel jail_sum = new JPanel();
		jail_sum.setLayout(new GridLayout(1, 2));
		jail_sum.setVisible(false);

		My_JLabel title_jail_sum = new My_JLabel("Enable jail at sum > 50 : ", 20.f);
		title_jail_sum.setHorizontalAlignment(JLabel.LEFT);
		title_jail_sum.setVisible(false);

		JRadioButton enable_jail_sum = new JRadioButton("Enable");
		enable_jail_sum.setActionCommand("enable");
		enable_jail_sum.setSelected(false);
		enable_jail_sum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.jail_if_sum_3_dices = true;
			}
		});

		JRadioButton disable_jail_sum = new JRadioButton("Disable");
		disable_jail_sum.setActionCommand("disable");
		disable_jail_sum.setSelected(true);
		disable_jail_sum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.jail_if_sum_3_dices = false;
			}
		});

		ButtonGroup group_jail_sum = new ButtonGroup();
		group_jail_sum.add(enable_jail_sum);
		group_jail_sum.add(disable_jail_sum);

		jail_sum.add(enable_jail_sum);
		jail_sum.add(disable_jail_sum);

		// NB TURN END

		My_JLabel title_nb_turn_end = new My_JLabel("Number turn before end : ", 20.f);
		title_nb_turn_end.setHorizontalAlignment(JLabel.LEFT);
		title_nb_turn_end.setVisible(false);

		JSlider nb_turn_end = new JSlider();
		Hashtable<Integer, JLabel> table_turn_end = new Hashtable<Integer, JLabel>();
		table_turn_end.put(50, new JLabel("50"));
		table_turn_end.put(100, new JLabel("100"));
		table_turn_end.put(150, new JLabel("150"));
		table_turn_end.put(200, new JLabel("200"));
		table_turn_end.put(250, new JLabel("250"));
		nb_turn_end.setLabelTable(table_turn_end);
		nb_turn_end.setPaintLabels(true);
		nb_turn_end.setSnapToTicks(true);
		nb_turn_end.setMaximum(250);
		nb_turn_end.setMinimum(50);
		nb_turn_end.setValue(150);
		nb_turn_end.setMajorTickSpacing(1);
		nb_turn_end.setMinorTickSpacing(1);
		nb_turn_end.setVisible(false);
		nb_turn_end.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Setting_configuration.nb_turn_before_end = source.getValue();
				turn_end_val.setText("Turn end : " + source.getValue());
			}
		});

		// SUICIDE MODE

		JPanel suicide = new JPanel();
		suicide.setLayout(new GridLayout(1, 2));

		My_JLabel title_suicide = new My_JLabel("Suicide mode : ", 20.f);
		title_suicide.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_suicide = new JRadioButton("Enable");
		enable_suicide.setActionCommand("enable");
		enable_suicide.setSelected(false);
		enable_suicide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.suicide_mod = true;
			}
		});

		JRadioButton disable_suicide = new JRadioButton("Disable");
		disable_suicide.setActionCommand("disable");
		disable_suicide.setSelected(true);
		disable_suicide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.suicide_mod = false;
			}
		});

		ButtonGroup group_suicide = new ButtonGroup();
		group_suicide.add(enable_suicide);
		group_suicide.add(disable_suicide);

		suicide.add(enable_suicide);
		suicide.add(disable_suicide);

		// RENT *2

		JPanel rentMult = new JPanel();
		rentMult.setLayout(new GridLayout(1, 2));

		My_JLabel title_rent = new My_JLabel("Rent multiply X minutes : ", 20.f);
		title_rent.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_rent = new JRadioButton("Enable");
		enable_rent.setActionCommand("enable");
		enable_rent.setSelected(false);
		enable_rent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.rent_double = true;
			}
		});

		JRadioButton disable_rent = new JRadioButton("Disable");
		disable_rent.setActionCommand("disable");
		disable_rent.setSelected(true);
		disable_rent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.rent_double = false;
			}
		});

		ButtonGroup group_rent = new ButtonGroup();
		group_rent.add(enable_rent);
		group_rent.add(disable_rent);

		rentMult.add(enable_rent);
		rentMult.add(disable_rent);

		// LEFT PART

		// START MONEY
		My_JLabel title_money = new My_JLabel("Money start : ", 20.f);
		title_money.setHorizontalAlignment(JLabel.LEFT);

		JSlider start_money = new JSlider();
		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
		table.put(100000, new JLabel("100000"));
		table.put(125000, new JLabel("125000"));
		table.put(150000, new JLabel("150000"));
		table.put(175000, new JLabel("175000"));
		table.put(200000, new JLabel("200000"));
		start_money.setLabelTable(table);
		start_money.setPaintLabels(true);
		start_money.setSnapToTicks(true);
		start_money.setMaximum(200000);
		start_money.setMinimum(100000);
		start_money.setValue(150000);
		start_money.setMajorTickSpacing(1000);
		start_money.setMinorTickSpacing(100);

		start_money.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				money_val.setText("Start money : " + source.getValue());
				Setting_configuration.starting_money = source.getValue();
			}
		});

		// RANDOM START
		JPanel random_start = new JPanel();
		random_start.setLayout(new GridLayout(1, 2));

		My_JLabel title_random_start = new My_JLabel("Random start : ", 20.f);
		title_random_start.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_random_start = new JRadioButton("Enable");
		enable_random_start.setActionCommand("enable");
		enable_random_start.setSelected(false);
		enable_random_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.random_position = true;
			}
		});

		JRadioButton disable_random_start = new JRadioButton("Disable");
		disable_random_start.setActionCommand("disable");
		disable_random_start.setSelected(true);
		disable_random_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.random_position = false;
			}
		});

		ButtonGroup group_random_start = new ButtonGroup();
		group_random_start.add(enable_random_start);
		group_random_start.add(disable_random_start);

		random_start.add(enable_random_start);
		random_start.add(disable_random_start);

		// RANDOM PROPERTY

		JPanel random_property = new JPanel();
		random_property.setLayout(new GridLayout(1, 2));

		My_JLabel title_random_property = new My_JLabel("Random property at start : ", 20.f);
		title_random_property.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_random_property = new JRadioButton("Enable");
		enable_random_property.setActionCommand("enable");
		enable_random_property.setSelected(false);
		enable_random_property.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.random_properties_gave = true;
			}
		});

		JRadioButton disable_random_property = new JRadioButton("Disable");
		disable_random_property.setActionCommand("disable");
		disable_random_property.setSelected(true);
		disable_random_property.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.random_properties_gave = false;
			}
		});

		ButtonGroup group_random_property = new ButtonGroup();
		group_random_property.add(enable_random_property);
		group_random_property.add(disable_random_property);

		random_property.add(enable_random_property);
		random_property.add(disable_random_property);

		// NUMBER DICE
		My_JLabel nb_dice_title = new My_JLabel("Number of dices : ", 20.f);
		nb_dice_title.setHorizontalAlignment(JLabel.LEFT);

		JSlider nb_dice = new JSlider();
		nb_dice.setPaintLabels(true);
		nb_dice.setSnapToTicks(true);
		nb_dice.setMaximum(3);
		nb_dice.setMinimum(2);
		nb_dice.setValue(2);
		nb_dice.setMajorTickSpacing(1);
		nb_dice.setMinorTickSpacing(1);
		nb_dice.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				dice_val.setText("Number of dice : " + source.getValue());
				Setting_configuration.nb_dices = source.getValue();
				if (source.getValue() == 3) {
					jail_sum.setVisible(true);
					title_jail_sum.setVisible(true);
				} else {
					jail_sum.setVisible(false);
					title_jail_sum.setVisible(false);
					Setting_configuration.jail_if_sum_3_dices = false;
					enable_jail_sum.setSelected(false);
					disable_jail_sum.setSelected(true);
				}
			}
		});

		// ALLOW DICE NUMBER SELECTION

		JPanel dice_selection = new JPanel();
		dice_selection.setLayout(new GridLayout(1, 2));

		My_JLabel title_dice_selection = new My_JLabel("Enable number of dice at start : ", 20.f);
		title_dice_selection.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_dice_selection = new JRadioButton("Enable");
		enable_dice_selection.setActionCommand("enable");
		enable_dice_selection.setSelected(false);
		enable_dice_selection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.ask_nb_dices_turn = true;
			}
		});

		JRadioButton disable_dice_selection = new JRadioButton("Disable");
		disable_dice_selection.setActionCommand("disable");
		disable_dice_selection.setSelected(true);
		disable_dice_selection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.ask_nb_dices_turn = false;
			}
		});

		ButtonGroup group_dice_selection = new ButtonGroup();
		group_dice_selection.add(enable_dice_selection);
		group_dice_selection.add(disable_dice_selection);

		dice_selection.add(enable_dice_selection);
		dice_selection.add(disable_dice_selection);

		// NB HOUSE

		My_JLabel title_house = new My_JLabel("Number of house : ", 20.f);
		title_house.setHorizontalAlignment(JLabel.LEFT);

		JSlider nb_house = new JSlider();
		Hashtable<Integer, JLabel> table_house = new Hashtable<Integer, JLabel>();
		table_house.put(16, new JLabel("16"));
		table_house.put(24, new JLabel("24"));
		table_house.put(32, new JLabel("32"));
		table_house.put(40, new JLabel("40"));
		table_house.put(48, new JLabel("48"));
		nb_house.setLabelTable(table_house);
		nb_house.setPaintLabels(true);
		nb_house.setSnapToTicks(true);
		nb_house.setMaximum(48);
		nb_house.setMinimum(16);
		nb_house.setValue(32);
		nb_house.setMajorTickSpacing(1);
		nb_house.setMinorTickSpacing(1);
		nb_house.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Setting_configuration.nb_house = source.getValue();
				house_val.setText("Number of house : " + source.getValue());
			}
		});

		// NB HOTEL

		My_JLabel title_hotel = new My_JLabel("Number of hotel : ", 20.f);
		title_hotel.setHorizontalAlignment(JLabel.LEFT);

		JSlider nb_hotel = new JSlider();
		Hashtable<Integer, JLabel> table_hotel = new Hashtable<Integer, JLabel>();
		table_hotel.put(4, new JLabel("4"));
		table_hotel.put(8, new JLabel("8"));
		table_hotel.put(12, new JLabel("12"));
		table_hotel.put(16, new JLabel("16"));
		table_hotel.put(20, new JLabel("20"));
		nb_hotel.setLabelTable(table_hotel);
		nb_hotel.setPaintLabels(true);
		nb_hotel.setSnapToTicks(true);
		nb_hotel.setMaximum(20);
		nb_hotel.setMinimum(4);
		nb_hotel.setValue(12);
		nb_hotel.setMajorTickSpacing(1);
		nb_hotel.setMinorTickSpacing(1);
		nb_hotel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Setting_configuration.nb_hotel = source.getValue();
				hotel_val.setText("Number of hotel : " + source.getValue());
			}
		});

		// Allow turn end

		JPanel turn_end = new JPanel();
		turn_end.setLayout(new GridLayout(1, 2));

		My_JLabel title_turn_end = new My_JLabel("Enable end at specific turn : ", 20.f);
		title_turn_end.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_turn_end = new JRadioButton("Enable");
		enable_turn_end.setActionCommand("enable");
		enable_turn_end.setSelected(false);
		enable_turn_end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				title_nb_turn_end.setVisible(true);
				nb_turn_end.setVisible(true);
				Setting_configuration.nb_turn_before_end = nb_turn_end.getValue();
				turn_end_val.setText("Turn end : " + nb_turn_end.getValue());
			}
		});

		JRadioButton disable_turn_end = new JRadioButton("Disable");
		disable_turn_end.setActionCommand("disable");
		disable_turn_end.setSelected(true);
		disable_turn_end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				title_nb_turn_end.setVisible(false);
				nb_turn_end.setVisible(false);
				Setting_configuration.nb_turn_before_end = -1;
				turn_end_val.setText("Turn end : Unspecify");
			}
		});

		ButtonGroup group_turn_end = new ButtonGroup();
		group_turn_end.add(enable_turn_end);
		group_turn_end.add(disable_turn_end);

		turn_end.add(enable_turn_end);
		turn_end.add(disable_turn_end);

		left.add(title_money);
		left.add(start_money);
		left.add(title_random_start);
		left.add(random_start);
		left.add(title_random_property);
		left.add(random_property);
		left.add(nb_dice_title);
		left.add(nb_dice);
		left.add(title_dice_selection);
		left.add(dice_selection);
		left.add(title_house);
		left.add(nb_house);
		left.add(title_hotel);
		left.add(nb_hotel);
		left.add(title_turn_end);
		left.add(turn_end);

		center.add(title_suicide);
		center.add(suicide);
		center.add(title_rent);
		center.add(rentMult);
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(title_jail_sum);
		center.add(jail_sum);
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(new JPanel());
		center.add(title_nb_turn_end);
		center.add(nb_turn_end);

		right.add(money_val);
		right.add(dice_val);
		right.add(house_val);
		right.add(hotel_val);
		right.add(turn_end_val);

		this.add(left, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
	}
}
