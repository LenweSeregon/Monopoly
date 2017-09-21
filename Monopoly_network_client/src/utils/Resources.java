package utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Resources {

	private static Vector<Buffered_image_text> images;
	private static Vector<Font_text> fonts;

	/**
	 * Constructeur de la classe qui représente notre lien avec les ressources
	 * de l'application. Cette classe via ces différentes méthodes s'assurent
	 * qu'il ne se trouve dans l'application au maximum qu'une seule instance
	 * d'une ressources
	 */
	public Resources() {
		images = new Vector<Buffered_image_text>();
		fonts = new Vector<Font_text>();
	}

	/**
	 * Méthode permettant de récupérer une image via son nom d'accés dans le
	 * dossier à partir du dossier 'resources/images/'
	 * 
	 * @param name
	 *            le nom de l'image que l'on veut charger
	 * @return l'image sous forme d'une bufferedImage que l'on vient de
	 *         récupérer
	 */
	public static BufferedImage get_image(String name) {
		for (Buffered_image_text bmt : images) {
			if (bmt.get_name().equals(name)) {
				return bmt.get_image();
			}
		}

		Buffered_image_text create = new Buffered_image_text(name);
		images.addElement(create);
		return create.get_image();
	}

	/**
	 * Méthode permettant de récupérer une font via son nom d'accés dans le
	 * dossier à partir du dossier 'resources/fonts/'
	 * 
	 * @param name
	 *            le nom de la font que l'on veut charger
	 * @return la font sous forme d'une bufferedImage que l'on vient de
	 *         récupérer
	 */
	public static Font get_font(String name) {
		for (Font_text font : fonts) {
			if (font.get_name().equals(name)) {
				return font.get_font();
			}
		}

		Font_text create = new Font_text(name);
		fonts.addElement(create);
		return create.get_font();
	}

	private static class Buffered_image_text {

		private String name;
		private BufferedImage image;

		public Buffered_image_text(String name) {
			this.name = name;
			try {
				this.image = ImageIO.read(Resources.class.getResource("/images/" + name));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public String get_name() {
			return this.name;
		}

		public BufferedImage get_image() {
			return this.image;
		}
	}

	private static class Font_text {
		private String name;
		private Font font;

		public Font_text(String name) {
			this.name = name;

			try {
				this.font = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream("/fonts/" + name));
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public String get_name() {
			return this.name;
		}

		public Font get_font() {
			return this.font;
		}
	}
}
