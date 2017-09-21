package mvc_game_view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.border.LineBorder;

import mvc_game_enums.Terrain_type;
import settings.Setting_configuration;
import utils.Resources;

@SuppressWarnings("serial")
public class Board_terrain_image_view extends Abstract_board_terrain_view {

	private BufferedImage image;
	private String path;
	private Middle_board ref_middle;
	private Terrain_type type;
	private int price;

	private String title;
	private String path_img;
	private String description;

	/**
	 * Constructeur de la classe qui représente une image sur le plateau, cette
	 * classe affiche beaucoup moins d'informations lorsque l'on zoom dessus
	 * 
	 * @param name
	 *            le nom du terrain
	 * @param number
	 *            le numéro du terrain
	 * @param o
	 *            l'orientation du terrain
	 * @param path
	 *            le chemin du terrain
	 * @param pos_x
	 *            la position X du terrain
	 * @param pos_y
	 *            la position Y du terrain
	 * @param width
	 *            la largeur du terrain
	 * @param height
	 *            la hauteur du terrain
	 * @param middle_ref
	 *            la référence sur le milieu du plateau
	 * @param type
	 *            le type du terrain
	 * @param price
	 *            le prix du terrain (peut valoir -1)
	 */
	public Board_terrain_image_view(String name, int number, Orientation o, String path, int pos_x, int pos_y,
			int width, int height, Middle_board middle_ref, Terrain_type type, int price) {
		super(name, number, o, width, height, pos_x, pos_y);
		this.image = Resources.get_image(path);
		this.path = path;
		this.ref_middle = middle_ref;
		this.type = type;
		this.price = price;
		this.setBorder(new LineBorder(Color.BLACK, 1));

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				unshow_informations();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				show_informations();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		build_informations();
	}

	/**
	 * Permet de récupérer le titre pour le zoom
	 * 
	 * @return le titre du terrain
	 */
	public String get_title() {
		return this.title;
	}

	/**
	 * Permet de récupérer le chemin jusqu'a l'image pour le zoom
	 * 
	 * @return le chemin de l'image
	 */
	public String get_path_img() {
		return this.path_img;
	}

	/**
	 * Permet de récupérer la description du terrain pour le zoom
	 * 
	 * @return la description du terrain
	 */
	public String get_description() {
		return this.description;
	}

	/**
	 * Méthode qui va construire les informations titre, chemin de l'image et
	 * description en fonction du type de terrain
	 */
	public void build_informations() {
		switch (type) {
		case CARDS:
			if (name.equals("Luck card")) {
				title = "Carte chance";
				path_img = "chance.jpg";
				description = "<html><div style='text-align: center;'>"
						+ "Si vous tombez sur cette case, vous devez tirer une carte chance !" + "</div></html>";
			} else {
				title = "Carte de communauté";
				path_img = "caisse.jpg";
				description = "<html><div style='text-align: center;'>"
						+ "Si vous tombez sur cette case, vous devez tirer une carte de communauté" + "</div></html>";
			}
			break;
		case COMPANY:
			if (name.equals("Electricite")) {
				title = "Compagnie de distribution d'électricité";
				path_img = "water.jpg";
				description = "<html><div style='text-align: center;'>"
						+ "Si l'on possède UNE carte de compagnie de service public, le loyer est 4 fois le montant indiqué par les dés.<br><br>"
						+ "Si l'on possède les DEUX cartes de compagnie de service public, le loyer est 10 fois le montant indiqué par les dés"
						+ "</div></html>";
			} else {
				title = "Compagnie de distribution des eaux";
				path_img = "supply.jpg";
				description = "<html><div style='text-align: center;'>"
						+ "Si l'on possède UNE carte de compagnie de service public, le loyer est 4 fois le montant indiqué par les dés.<br><br>"
						+ "Si l'on possède les DEUX cartes de compagnie de service public, le loyer est 10 fois le montant indiqué par les dés"
						+ "</div></html>";
			}
			break;
		case GO:
			String text = "Si vous passez par cette case vous touchez M20000";
			if (Setting_configuration.double_sum_on_case_start) {
				text += "<br>Si vous tombez sur cette case, vous touchez en plus M20000";
			}
			title = "Case départ";
			path_img = "go.png";
			description = "<html><div style='text-align: center;'>" + text + "</div></html>";
			break;
		case GO_JAIL:
			title = "Aller en prison";
			path_img = "police.png";
			description = "<html><div style='text-align: center;'>"
					+ "Si vous tombez sur cette case, vous devez aller en prison sans passer par la case départ."
					+ "</div></html>";
			break;
		case JAIL:
			title = "Prison";
			path_img = "prison.png";
			description = "<html><div style='text-align: center;'>"
					+ "Si vous tombé sur cette case, vous êtes simplement en visite dans la prison et il ne se passe rien.<br>"
					+ "Si vous deviez aller en prison aux tours précédents et que vous êtes ici, vous êtes incarcéré"
					+ "</div></html>";
			break;
		case PARKING:
			title = "Parc gratuit";
			path_img = "park.png";
			if (Setting_configuration.earn_money_on_parking) {
				description = "<html><div style='text-align: center;'>"
						+ "Si vous tombez sur le parc gratuit, vous touchez l'ensemble des gains remportés par la banque depuis le dernier passage"
						+ "</div></html>";
			} else {
				description = "<html><div style='text-align: center;'>"
						+ "Si vous tombez sur le parc gratuit, rien ne se passe." + "</div></html>";
			}

			break;
		case PUBLIC:
			if (name.equals("impots")) {
				title = "Impôts sur le revenu";
				path_img = "tax.jpg";
				if (!Setting_configuration.tax_according_to_player_money) {
					description = "<html><div style='text-align: center;'>"
							+ "Si vous tombez sur cette case, vous devez payer M" + price
							+ " à la banque en guise d'impôts." + "</div></html>";
				} else {
					description = "<html><div style='text-align: center;'>"
							+ "Si vous tombez sur cette case, vous devez payer 10% de votre patrimoine à la banque en guise d'impôts."
							+ "</div></html>";
				}
			} else {
				title = "Taxe de luxe";
				path_img = "luxury.jpg";
				description = "<html><div style='text-align: center;'>"
						+ "Si vous tombez sur cette case, vous devez payer M" + price
						+ " à la banque en guise de taxe de luxe." + "</div></html>";
			}
			break;
		case STATION:
			title = name;
			path_img = "station.jpg";
			description = "<html><div style='text-align: center;'>"
					+ " Loyer : M2500<br>2 gares : M5000<br>3 gares : M10000<br>4 gares : M20000" + "</div></html>";
			break;
		default:
			break;
		}
	}

	/**
	 * Méthode permettant de récupérer le chemin de l'image du plateau
	 * 
	 * @return
	 */
	public String get_path() {
		return this.path;
	}

	/**
	 * Méthode permettant de demander l'afficahge du zoom sur le milieu du
	 * plateau
	 */
	public void show_informations() {
		if (allow_show_informations && !fog_of_war) {
			ref_middle.build_center_image(title, path, description);
		}
	}

	/**
	 * Méthode permettant de demander le déaffichage du zoom sur le milieu du
	 * plateau
	 */
	public void unshow_informations() {
		if (allow_show_informations) {
			ref_middle.build_center_empty();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, width, height);

		if (!fog_of_war) {

			int rotation_radius = 0;
			if (orientation == Orientation.SOUTH) {
				rotation_radius = 0;
			} else if (orientation == Orientation.NORTH) {
				rotation_radius = 180;
			} else if (orientation == Orientation.WEST) {
				rotation_radius = 90;
			} else if (orientation == Orientation.EAST) {
				rotation_radius = 270;
			}

			AffineTransform at = new AffineTransform();
			at.translate(getWidth() / 2, getHeight() / 2);

			if (width != height) {
				at.rotate(Math.toRadians(rotation_radius));
			}

			if (orientation == Orientation.NORTH || orientation == Orientation.SOUTH) {
				at.scale((float) ((float) width / image.getWidth()), (float) ((float) height / image.getHeight()));
			} else {
				at.scale((float) ((float) height / image.getWidth()), (float) ((float) width / image.getHeight()));
			}

			at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
			g2.drawImage(image, at, null);
		}
	}
}
