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
public class Setting_configuration_terrain_view extends JPanel {

	/**
	 * Constructeur de la classe preséentant la vue pour le parametrage des
	 * options pour les terrains
	 * 
	 * @param width
	 *            largeur du panel
	 * @param height
	 *            hauteur du panel
	 */
	public Setting_configuration_terrain_view(int width, int height) {
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

		My_JLabel percentage = new My_JLabel("Percentage sell : 50%", 15.f);

		My_JLabel rebuy_val = new My_JLabel("Rebuy increase : 10%", 15.f);

		// CENTER

		// LEFT

		// FREE BUILD
		JPanel allow_free_build = new JPanel();
		allow_free_build.setLayout(new GridLayout(1, 2));

		My_JLabel title_allow_free_build = new My_JLabel("Allow free build ", 20.f);
		title_allow_free_build.setHorizontalAlignment(JLabel.LEFT);

		JRadioButton enable_free_build = new JRadioButton("Enable");
		enable_free_build.setActionCommand("enable");
		enable_free_build.setSelected(false);
		enable_free_build.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.no_care_equality_house = true;
			}
		});

		JRadioButton disable_free_build = new JRadioButton("Disable");
		disable_free_build.setActionCommand("disable");
		disable_free_build.setSelected(true);
		disable_free_build.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting_configuration.no_care_equality_house = false;
			}
		});

		ButtonGroup group_jail_sum = new ButtonGroup();
		group_jail_sum.add(enable_free_build);
		group_jail_sum.add(disable_free_build);

		allow_free_build.add(enable_free_build);
		allow_free_build.add(disable_free_build);

		// PERCENTAGE SELL
		My_JLabel title_percentage = new My_JLabel("Percentage selling : ", 20.f);
		title_percentage.setHorizontalAlignment(JLabel.LEFT);

		JSlider nb_percentage = new JSlider();
		Hashtable<Integer, JLabel> table_percentage = new Hashtable<Integer, JLabel>();
		table_percentage.put(0, new JLabel("0"));
		table_percentage.put(25, new JLabel("25"));
		table_percentage.put(50, new JLabel("50"));
		table_percentage.put(75, new JLabel("75"));
		table_percentage.put(100, new JLabel("100"));
		nb_percentage.setLabelTable(table_percentage);
		nb_percentage.setPaintLabels(true);
		nb_percentage.setSnapToTicks(true);
		nb_percentage.setMaximum(100);
		nb_percentage.setMinimum(0);
		nb_percentage.setValue(50);
		nb_percentage.setMajorTickSpacing(1);
		nb_percentage.setMinorTickSpacing(1);
		nb_percentage.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Setting_configuration.percentage_sell = source.getValue();
				percentage.setText("Percentage sell : " + source.getValue() + "%");
			}
		});

		// RATE REBUY
		My_JLabel title_rebuy = new My_JLabel("Rebuy rate : ", 20.f);
		title_rebuy.setHorizontalAlignment(JLabel.LEFT);

		JSlider rebuy = new JSlider();
		Hashtable<Integer, JLabel> table_rebuy = new Hashtable<Integer, JLabel>();
		table_rebuy.put(0, new JLabel("0"));
		table_rebuy.put(5, new JLabel("5"));
		table_rebuy.put(10, new JLabel("10"));
		table_rebuy.put(15, new JLabel("15"));
		table_rebuy.put(20, new JLabel("20"));
		rebuy.setLabelTable(table_rebuy);
		rebuy.setPaintLabels(true);
		rebuy.setSnapToTicks(true);
		rebuy.setMaximum(20);
		rebuy.setMinimum(0);
		rebuy.setValue(10);
		rebuy.setMajorTickSpacing(1);
		rebuy.setMinorTickSpacing(1);
		rebuy.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Setting_configuration.rate_rebuy_hypotheck = source.getValue();
				rebuy_val.setText("Rebuy increase : " + source.getValue() + "%");
			}
		});

		left.add(title_allow_free_build);
		left.add(allow_free_build);
		left.add(title_percentage);
		left.add(nb_percentage);
		left.add(title_rebuy);
		left.add(rebuy);
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

		right.add(percentage);
		right.add(rebuy_val);
		right.add(new JPanel());
		right.add(new JPanel());
		right.add(new JPanel());

		this.add(left, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		this.add(right, BorderLayout.EAST);
	}
}
