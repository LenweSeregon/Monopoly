package configure_terrain_view;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import utils.DB;

@SuppressWarnings("serial")
public class My_table_model extends AbstractTableModel {

	private static Vector<Table_terrain_value> terrains;
	private String[] headers;

	/**
	 * Constructeur de la classe représentant le modele de notre tableau de
	 * données. On trouve à l'intérieur les différents valeures logiques de
	 * terrains qui tableau qu'on va pouvoir modifier
	 */
	public My_table_model() {
		super();

		this.headers = new String[] { "Number", "Name", "Color", "Price", "Rent", "1 house", "2 houses", "3 houses",
				"4 houses", "5 houses" };

		terrains = new Vector<Table_terrain_value>();

		this.load_datas();
	}

	/**
	 * Méthode permettant de récupérer les différents terrains
	 * 
	 * @return le différents terrains dans un vecteur
	 */
	public static Vector<Table_terrain_value> get_terrains() {
		return terrains;
	}

	/**
	 * Méthode permettant de charger les différentes données qui se trouve dans
	 * la base de données.
	 */
	public void load_datas() {
		ResultSet res = DB.get_instance().get_all_terrains();

		while (true) {
			try {
				if (res.next()) {
					boolean editable = res.getBoolean(1);
					int number = res.getInt(2);
					String name = res.getString(3);
					Color_terrain color = Color_terrain.fromString(res.getString(4));
					int price = res.getInt(5);
					int rent = res.getInt(6);

					int house_price = res.getInt(7);
					int house_1 = res.getInt(8);
					int house_2 = res.getInt(9);
					int house_3 = res.getInt(10);
					int house_4 = res.getInt(11);
					int house_5 = res.getInt(12);

					int[] houses = new int[] { house_1, house_2, house_3, house_4, house_5 };

					Terrain_type type = Terrain_type.fromString(res.getString(13));

					terrains.add(new Table_terrain_value(editable, number, name, color, price, rent, house_price,
							houses, type));

				} else {
					break;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		Table_terrain_value terrain = terrains.get(rowIndex);

		if (!terrain.is_editable()) {
			return false;
		} else {
			if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 10) {
				return false;
			} else {
				if (terrain.get_type() == Terrain_type.PROPERTY) {
					return true;
				} else if (terrain.get_type() == Terrain_type.STATION || terrain.get_type() == Terrain_type.COMPANY) {
					if (columnIndex == 3) {
						return true;
					} else {
						return false;
					}
				} else if (terrain.get_type() == Terrain_type.PUBLIC) {
					if (columnIndex == 4) {
						return true;
					} else {
						return false;
					}
				}
				return true;
			}
		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue != null) {
			Table_terrain_value terrain = terrains.get(rowIndex);

			switch (columnIndex) {
			case 3:
				terrain.set_price(Integer.valueOf((String) aValue));
				break;
			case 4:
				terrain.set_rent(Integer.valueOf((String) aValue));
				break;
			case 5:
				terrain.set_houses_number(0, Integer.valueOf((String) aValue));
				break;
			case 6:
				terrain.set_houses_number(1, Integer.valueOf((String) aValue));
				break;
			case 7:
				terrain.set_houses_number(2, Integer.valueOf((String) aValue));
				break;
			case 8:
				terrain.set_houses_number(3, Integer.valueOf((String) aValue));
				break;
			case 9:
				terrain.set_houses_number(4, Integer.valueOf((String) aValue));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public int getRowCount() {
		return terrains.size();
	}

	@Override
	public int getColumnCount() {
		return this.headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return terrains.elementAt(rowIndex).get_number();
		case 1:
			return terrains.elementAt(rowIndex).get_name();
		case 2:
			return terrains.elementAt(rowIndex).get_color();
		case 3:
			return terrains.elementAt(rowIndex).get_price();
		case 4:
			return terrains.elementAt(rowIndex).get_rent();
		case 5:
			return terrains.elementAt(rowIndex).get_houses()[0];
		case 6:
			return terrains.elementAt(rowIndex).get_houses()[1];
		case 7:
			return terrains.elementAt(rowIndex).get_houses()[2];
		case 8:
			return terrains.elementAt(rowIndex).get_houses()[3];
		case 9:
			return terrains.elementAt(rowIndex).get_houses()[4];
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		return headers[columnIndex];
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 2:
			return Color.class;
		default:
			return Object.class;
		}
	}
}
