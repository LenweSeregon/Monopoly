package mvc_game_model;

import java.util.ArrayList;

import mvc_game_enums.Card_type;
import mvc_game_enums.Earn_card_type;
import mvc_game_model_cards.Abstract_card;
import mvc_game_model_cards.Earn_money_card;
import mvc_game_model_cards.Get_out_jail_card;
import mvc_game_model_cards.Go_jail_card;
import mvc_game_model_cards.Lose_money_card;
import mvc_game_model_cards.Move_card;
import mvc_game_model_cards.Steal_card;
import mvc_game_model_terrains.Abstract_terrain;
import settings.Setting_configuration;

public class Card_container {

	private ArrayList<Abstract_card> luck;
	private ArrayList<Abstract_card> communauty;

	private int iterator_luck;
	private int iterator_communauty;

	/**
	 * Constructeur de la classe qui représente le conteneur des différents
	 * cartes du type communauté et chance. Cette classe permet simplement de
	 * récupérer la prochaine carte de la liste d'un paquet et de charger les
	 * cartes
	 * 
	 * @param terrains
	 *            une référence sur les terrains necessaire à l'iniatialisation
	 */
	public Card_container(ArrayList<Abstract_terrain> terrains) {
		this.iterator_luck = 0;
		this.iterator_communauty = 0;

		this.luck = new ArrayList<Abstract_card>();
		this.communauty = new ArrayList<Abstract_card>();

		this.init_card(terrains);
	}

	private int get_number_from_name(ArrayList<Abstract_terrain> terrains, String name) {
		for (Abstract_terrain terrain : terrains) {
			if (terrain.get_name().equals(name)) {
				return terrain.get_number();
			}
		}
		return -1;
	}

	private void init_card(ArrayList<Abstract_terrain> terrains) {
		// Luck card
		luck.add(new Lose_money_card("Amende pour ivresse. Payez 2.000", Card_type.LUCK, 2000));
		luck.add(new Go_jail_card("Allez en prison. Ne franchissze pas la case Départ", Card_type.LUCK));
		luck.add(new Earn_money_card(" Vous avez gagné le prix de mots croisés. Recevez 10.000", Card_type.LUCK, 10000,
				Earn_card_type.SIMPLE_EARN));
		luck.add(new Lose_money_card("Faites des réparations. 2.500 par maison, 10.000 par hôtel.", Card_type.LUCK,
				2500, 10000));
		luck.add(new Earn_money_card("Votre immeuble et votre prêt vous rapportent. Vous devez toucher 15.000.",
				Card_type.LUCK, 15000, Earn_card_type.SIMPLE_EARN));
		luck.add(new Get_out_jail_card("Vous êtes libérés de prison", Card_type.LUCK));
		luck.add(new Move_card("Rendez vous à l'Avenue Henri-Martin.", Card_type.LUCK,
				get_number_from_name(terrains, "Avenue Henri-Martin")));
		luck.add(new Move_card("Avancez jusqu'à la case Départ", Card_type.LUCK, get_number_from_name(terrains, "Go")));
		luck.add(new Move_card("Avancez au Boulevard de la Villette.", Card_type.LUCK,
				get_number_from_name(terrains, "Boulevard de la Vilette")));
		luck.add(new Move_card("Allez à la gare de Lyon", Card_type.LUCK, get_number_from_name(terrains, "Gare Lyon")));
		luck.add(new Move_card("Reculez de trois cases.", Card_type.LUCK, -3));
		luck.add(new Lose_money_card("Amende pour excès de vitesse, 1500.", Card_type.LUCK, 1500));
		luck.add(new Lose_money_card("Vous êtes imposés pour des réparations. 4.000 par maison. 11.500 par hôtel.",
				Card_type.LUCK, 4000, 11500));
		luck.add(new Move_card("Rendez-vous rue de la Paix.", Card_type.LUCK,
				get_number_from_name(terrains, "Rue de la paix")));
		luck.add(new Lose_money_card("Payez pour frais de scolarité, 15.000.", Card_type.LUCK, 15000));
		luck.add(new Earn_money_card("La banque vous verse 5.000.", Card_type.LUCK, 5000, Earn_card_type.SIMPLE_EARN));

		// Communauty card
		if (Setting_configuration.steal_card) {
			communauty.add(new Steal_card(
					"Vous voulez n'importe quelle carte à un de vos adversaires tant que celui-ci "
							+ "ne pose pas tout les terrains de même couleur / les 4 gares / les 2 compagnies",
					Card_type.COMMUNAUTY));
		}
		communauty.add(new Move_card("Retournez à Belleville.", Card_type.COMMUNAUTY,
				get_number_from_name(terrains, "Boulevard de Belleville")));
		communauty.add(new Earn_money_card("La vente devotre stock vous rapporte 5.000.", Card_type.COMMUNAUTY, 5000,
				Earn_card_type.SIMPLE_EARN));
		communauty.add(new Go_jail_card("Allez en prison. Ne franchissez pas la case Départ.", Card_type.COMMUNAUTY));
		communauty.add(new Get_out_jail_card("Vous êtes libérés de prison", Card_type.COMMUNAUTY));
		communauty.add(new Move_card("Retournez à Belleville.", Card_type.COMMUNAUTY,
				get_number_from_name(terrains, "Boulevard de Belleville")));
		communauty.add(new Lose_money_card("Payez à l'Hôpital, 10.000.", Card_type.COMMUNAUTY, 10000));
		communauty.add(new Earn_money_card("C'est votre anniversaire, chaque joueur doit vous donner 1.000.",
				Card_type.COMMUNAUTY, 1000, Earn_card_type.EVERYONE_GIVE));

		communauty.add(new Go_jail_card("Allez en prison. Ne franchissez pas la case Départ.", Card_type.COMMUNAUTY));
		communauty.add(new Move_card("Placez-vous sur la case Départ", Card_type.COMMUNAUTY,
				get_number_from_name(terrains, "Go")));
		communauty.add(new Earn_money_card("La vente devotre stock vous rapporte 5.000.", Card_type.COMMUNAUTY, 5000,
				Earn_card_type.SIMPLE_EARN));
		communauty.add(
				new Lose_money_card("Payez votre police d'Assurance s'élevant à 5.000.", Card_type.COMMUNAUTY, 5000));
		communauty.add(new Earn_money_card("Recevez votre revenu annuel, 10.000.", Card_type.COMMUNAUTY, 10000,
				Earn_card_type.SIMPLE_EARN));
		communauty.add(new Lose_money_card("Payez une amende de 1.000 ou tirez une carte chance.", Card_type.COMMUNAUTY,
				1000));
		communauty.add(new Earn_money_card(" Recevez votre intérêt sur l'emprunt, 2500.", Card_type.COMMUNAUTY, 2500,
				Earn_card_type.SIMPLE_EARN));
		communauty.add(new Earn_money_card("Vous héritez de 10.000.", Card_type.COMMUNAUTY, 10000,
				Earn_card_type.SIMPLE_EARN));
		communauty.add(new Earn_money_card("Les contributions vous rapportent 2.000.", Card_type.COMMUNAUTY, 2000,
				Earn_card_type.SIMPLE_EARN));
		communauty.add(new Earn_money_card("Les contributions vous rapportent 1.000.", Card_type.COMMUNAUTY, 1000,
				Earn_card_type.SIMPLE_EARN));
		communauty.add(new Lose_money_card("Payez la note du médecin, 5.000", Card_type.COMMUNAUTY, 5000));
		communauty.add(new Earn_money_card("Erreur de la banque, recevez 20.000.", Card_type.COMMUNAUTY, 20000,
				Earn_card_type.SIMPLE_EARN));

	}

	public Abstract_card get_luck_card() {

		iterator_luck++;
		if (iterator_luck == luck.size()) {
			iterator_luck = 1;
		}

		return luck.get(iterator_luck - 1);
	}

	public Abstract_card get_communauty_card() {

		iterator_communauty++;
		if (iterator_communauty == communauty.size()) {
			iterator_communauty = 1;
		}

		return communauty.get(iterator_communauty - 1);
	}

}
