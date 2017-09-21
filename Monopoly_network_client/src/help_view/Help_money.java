package help_view;

import utils.Resources;

@SuppressWarnings("serial")
public class Help_money extends Abstract_help_page {

	/**
	 * Constructeur de la classe qui représente le panneau d'aide pour l'argent
	 * 
	 * @param width
	 *            la largeur de la fenetre
	 * @param height
	 *            la hauteur de la fenetre
	 */
	public Help_money(int width, int height) {
		super(width, height);
		super.set_title("Money");

		super.set_desc_1(
				"<html><div style='text-align: justify;'> L'argent est l'élément le plus important dans Monopoly. "
						+ "Effectivement c'est lui qui va vous permettre d'acheter des propriétés, "
						+ "de payer les taxes ou encore de payer les loyers des autres joueurs lorsque "
						+ "vous tomberez sur leurs propriétés.</div></html>");

		super.set_desc_2(
				"<html> <div style='text-align: justify;'> Vous l'aurez donc compris, l'argent est l'élément le plus important du jeu "
						+ "et pour cela, on peut adopter différentes stratégies mais il vaut mieux "
						+ "toujours acheter les propriétés sur lesquelles on tombe pour pouvoir se faire de "
						+ "l'argent par la suite, tout en gardant un petit pécule de côté.<br>"
						+ "Vous gagnerez de l'argent via les autres joueurs, dans certaines cartes et lorsque vous "
						+ "passerez par la case départ </div></html>");

		super.set_photo_1(Resources.get_image("money_1.jpeg"));
		super.set_photo_2(Resources.get_image("money_2.jpg"));
		super.set_photo_3(Resources.get_image("money_3.jpeg"));
	}
}
