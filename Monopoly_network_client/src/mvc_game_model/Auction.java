package mvc_game_model;

import java.util.ArrayList;
import java.util.Collections;

import mvc_game_model_terrains.Buyable_terrain;

public class Auction {

	private Buyable_terrain ref_terrain_auction;
	private ArrayList<Player> ref_players;
	private ArrayList<Integer> auction_players;

	/**
	 * Constructeur de la classe qui représente une enchere dans notre partie.
	 * cette enchere va contenir les différentes encheres et permettre de
	 * connaitre le vainqueur de lenchere
	 * 
	 * @param terrain
	 *            le terrain mit en enchere
	 * @param list_player
	 *            la liste des joueurs participant à l'enchere
	 */
	public Auction(Buyable_terrain terrain, ArrayList<Player> list_player) {
		this.ref_terrain_auction = terrain;
		this.ref_players = new ArrayList<Player>();
		this.auction_players = new ArrayList<Integer>();

		for (Player p : list_player) {
			if (!p.get_surrend()) {
				ref_players.add(p);
				auction_players.add(0);
			}
		}
	}

	/**
	 * Méthode permettant de récupérer le terrain mit en enchere
	 * 
	 * @return le terrain mit en enchere
	 */
	public Buyable_terrain get_terrain() {
		return ref_terrain_auction;
	}

	/**
	 * Méthode permettant de récupérer l'enchere maximum
	 * 
	 * @return l'enchere maximum
	 */
	public int get_max_auction() {
		return Collections.max(auction_players);
	}

	/**
	 * Méthode peremettant de donner une valeure d'enchere pour un joueur via
	 * son pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur
	 * @param price
	 *            l'enchere du joueur
	 */
	public void set_auction_from_pseudo(String pseudo, int price) {
		int index = get_index_from_pseudo(pseudo);
		auction_players.set(index, price);
	}

	/**
	 * Méthode permettant de récupérer le pseudo du joueur qui a gagné l'enchere
	 * 
	 * @return le pseudo du joueur qui a gagné l'enchere
	 */
	public String get_player_pseudo_max_auction() {

		int max = auction_players.get(0);
		int index_max = 0;
		for (int i = 1; i < auction_players.size(); i++) {
			if (auction_players.get(i) > max) {
				max = auction_players.get(i);
				index_max = i;
			}
		}
		if (max == 0) {
			return null;
		} else {
			return ref_players.get(index_max).get_pseudo();
		}
	}

	/**
	 * Méthode permettant de récupérer le joueur ayant gagné l'enchere
	 * 
	 * @return le joueur qui a gagné l'enchere
	 */
	public Player get_player_max_auction() {
		String max = get_player_pseudo_max_auction();
		if (max == null) {
			return null;
		}

		for (Player p : ref_players) {
			if (p.get_pseudo().equals(max)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de récupérer l'indice du joueur par rapport à son
	 * pseudo
	 * 
	 * @param pseudo
	 *            le pseudo du joueur que l'on cherche
	 * @return l'index dans l'array list du joueur, -1 si pas trouvé
	 */
	private int get_index_from_pseudo(String pseudo) {
		for (int i = 0; i < ref_players.size(); i++) {
			if (ref_players.get(i).get_pseudo().equals(pseudo)) {
				return i;
			}
		}

		return -1;
	}
}
