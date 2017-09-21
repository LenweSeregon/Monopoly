package help_view;

import utils.Resources;

@SuppressWarnings("serial")
public class Help_house extends Abstract_help_page {

	/**
	 * Constructeur de la classe qui représente le panneau d'aide pour les
	 * maisons
	 * 
	 * @param width
	 *            la largeur de la fenetre
	 * @param height
	 *            la hauteur de la fenetre
	 */
	public Help_house(int width, int height) {
		super(width, height);
		super.set_title("Houses");

		super.set_desc_1("<html><div style='text-align: justify;'> Les maisons présentent un point des"
				+ " plus importants dans Monopoly. En effet, vous ne deviendrez jamais millionnaire sans être ruiné avant"
				+ " si vous ne construisez pas de maison et d'hôtel sur vos propriétés. Ce sont ces éléments qui vont vous"
				+ " permettre de faire monter le prix de passage de vos adversaires sur votre terrain et de devenir riche"
				+ ".</div></html>");

		super.set_desc_2(
				"<html> <div style='text-align: justify;'> Pour placer une maison, il faut tout d'abord que vous "
						+ "disposiez de l'intégralité des terrains de la même couleur, ensuite en fonction des règles du plateau,"
						+ " c'est à dire l'activation d'une variante ou non, il vous faudra équaliser vos maisons, c'est à dire que "
						+ "vous ne pourrez poser que 2 maisons sur un terrain, si tout les terrains disposent d'au moins une maison"
						+ ", et ainsi de suite. Attention encore une fois, une variante désactive cette régle.</div></html>");

		super.set_photo_1(Resources.get_image("house_1.jpg"));
		super.set_photo_2(Resources.get_image("house_2.jpg"));
		super.set_photo_3(Resources.get_image("house_3.jpg"));
	}
}
