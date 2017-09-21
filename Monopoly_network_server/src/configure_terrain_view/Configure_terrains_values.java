package configure_terrain_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;

import UI_elements.My_JButton;
import UI_elements.My_JLabel;
import menus.Abstract_panel;
import view.Window;

@SuppressWarnings("serial")
public class Configure_terrains_values extends Abstract_panel {

	private Window window_ref;
	private int port;
	private int per_game;

	/**
	 * Constructeur de la classe qui représentes la vue pour paramétrere les
	 * différentes valeurs de terrain qui se trouvent sur le plateau
	 * 
	 * @param width
	 *            la largeur du panel
	 * @param height
	 *            la hauteur du panel
	 * @param window_ref
	 *            la référence vers la fenetre
	 * @param port
	 *            référence vers le port du serveur
	 */
	public Configure_terrains_values(int width, int height, Window window_ref, int port, int per_game) {
		super(width, height);

		this.build_header(1.0f, 0.10f);
		this.build_center(1.0f, 0.8f);
		this.build_footer(1.0f, 0.10f);

		this.window_ref = window_ref;
		this.port = port;
		this.per_game = per_game;
	}

	@Override
	protected void build_header(float w_mod, float h_mod) {
		super.build_header(w_mod, h_mod);
		this.header.setBackground(new Color(129, 198, 134));
		this.header.setLayout(new BorderLayout());
		this.header.setBorder(new MatteBorder(0, 0, 5, 0, new Color(117, 138, 119)));

		JPanel header_content = new JPanel();
		header_content.setLayout(new BorderLayout());
		header_content.setOpaque(false);

		JLabel title = new My_JLabel("Configure terrains", 55.f);
		title.setForeground(Color.WHITE);

		header_content.add(title, BorderLayout.CENTER);

		this.header.add(header_content, BorderLayout.CENTER);
	}

	@Override
	protected void build_center(float w_mod, float h_mod) {
		super.build_center(w_mod, h_mod);
		this.center.setLayout(new BorderLayout());
		this.center.setBackground(new Color(170, 203, 172));

		load_terrains(w_mod, h_mod);
	}

	public void load_terrains(float w_mod, float h_mod) {

		int w = (int) (width * w_mod);
		int h = (int) (height * h_mod);

		JTable table = new JTable(new My_table_model());
		table.setDefaultRenderer(Color.class, new My_color_cell_renderer());

		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(w, h));
		scroll.setOpaque(true);
		scroll.setBackground(Color.BLACK);

		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scroll.getViewport().setPreferredSize(new Dimension(w, h));
		scroll.getViewport().setBorder(null);
		scroll.setViewportBorder(null);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(4);

		this.center.add(scroll, BorderLayout.CENTER);

	}

	@Override
	protected void build_footer(float w_mod, float h_mod) {
		super.build_footer(w_mod, h_mod);
		this.footer.setBackground(new Color(129, 198, 134));
		this.footer.setLayout(new GridLayout(1, 5));
		this.footer.setBorder(new MatteBorder(5, 0, 0, 0, new Color(117, 138, 119)));

		My_JButton next = new My_JButton("Next", 25.f);
		next.setForeground(Color.WHITE);
		next.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window_ref.launch_configure_settings_menu(port, per_game);
			}
		});

		footer.add(new JLabel());
		footer.add(new JLabel());
		footer.add(new JLabel());
		footer.add(new JLabel());
		footer.add(next);

	}

	@Override
	protected void build_right(float w_mod, float h_mod) {
		super.build_right(w_mod, h_mod);
		this.right.setBackground(Color.YELLOW);
	}

	@Override
	protected void build_left(float w_mod, float h_mod) {
		super.build_left(w_mod, h_mod);
		this.left.setBackground(Color.WHITE);
		this.left.setLayout(new BorderLayout());
		this.left.setBorder(new MatteBorder(0, 0, 0, 5, new Color(117, 138, 119)));
	}
}
