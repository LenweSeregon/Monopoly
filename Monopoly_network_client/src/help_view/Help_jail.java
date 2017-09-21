package help_view;

import utils.Resources;

@SuppressWarnings("serial")
public class Help_jail extends Abstract_help_page {

	/**
	 * Constructeur de la classe qui représente le panneau d'aide pour la prison
	 * 
	 * @param width
	 *            la largeur de la fenetre
	 * @param height
	 *            la hauteur de la fenetre
	 */
	public Help_jail(int width, int height) {
		super(width, height);
		super.set_title("Jail");

		super.set_desc_1(
				"<html><div style='text-align: justify;'> La prison est un endroit sur Monopoly qui en fonction"
						+ " des variantes que vous avez choisis peut se révéler un endroit agréable ou désagréable. Sachez "
						+ "toutefois qu'il est très mauvais de tomber sur la case prison en début de partie car vous serez "
						+ "ainsi perturbé dans l'achat de vos propriétés.  </div></html>");

		super.set_desc_2("<html> <div style='text-align: justify;'> Il y a 3 moyens d'aller en prison. Tomber sur "
				+ "la case aller en prison, réaliser 3 fois de suite un double / triple, et finalement tomber sur une "
				+ "carte chance ou communautaire aller en prison. Pour sortir de prison, il faut attendre 3 tours et payer "
				+ "une cotion ou réaliser un double et certaines régles sont paramétrables dans les variantes du jeu pour modifier "
				+ "encore quelques points. </div></html>");

		super.set_photo_1(Resources.get_image("jail_1.jpg"));
		super.set_photo_2(Resources.get_image("jail_2.jpg"));
		super.set_photo_3(Resources.get_image("jail_3.jpeg"));
	}
}
