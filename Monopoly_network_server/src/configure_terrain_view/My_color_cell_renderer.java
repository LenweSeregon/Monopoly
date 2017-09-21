package configure_terrain_view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class My_color_cell_renderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		Color color = null;
		Color_terrain c = (Color_terrain) value;
		if (c.equals(Color_terrain.BROWN)) {
			color = new Color(139, 69, 19);
		} else if (c.equals(Color_terrain.LIGHT_BLUE)) {
			color = new Color(35, 206, 250);
		} else if (c.equals(Color_terrain.PURPLE)) {
			color = new Color(128, 0, 128);
		} else if (c.equals(Color_terrain.ORANGE)) {
			color = Color.ORANGE;
		} else if (c.equals(Color_terrain.YELLOW)) {
			color = Color.YELLOW;
		} else if (c.equals(Color_terrain.RED)) {
			color = Color.RED;
		} else if (c.equals(Color_terrain.GREEN)) {
			color = Color.GREEN;
		} else if (c.equals(Color_terrain.DARK_BLUE)) {
			color = new Color(0, 0, 139);
		} else if (c.equals(Color_terrain.NONE)) {
			color = Color.WHITE;
		} else {
			System.err.println("ISSUE");
		}

		setText("");
		setBackground(color);

		return this;
	}
}