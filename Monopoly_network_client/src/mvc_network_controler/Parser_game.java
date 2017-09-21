package mvc_network_controler;

import java.io.StringReader;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import settings.Setting_configuration;

public class Parser_game extends Parser_message {

	private Network_controler controler;

	/**
	 * Constructeur de la classe qui représente le parseur de message du coté
	 * jeu
	 * 
	 * @param controler
	 *            une référence sur le controler de réseau
	 */
	public Parser_game(Network_controler controler) {
		super("Game");
		this.controler = controler;
	}

	@Override
	public boolean analyse_string(String message) {
		String[] splitted = message.split("&!");

		if (splitted.length >= 2) {
			String parse_type = splitted[0];
			String parse_info = splitted[1];
			String parse_info_bis = splitted[2];

			if (parse_type.equals("GAME")) {
				if (parse_info.equals("CONFIG")) {
					if (parse_info_bis.equals("TERRAINS")) {
						// System.out.println("Terrain");
						return analyse_configuration_terrain(message);
					} else {
						// System.out.println("Config");
						return analyse_configuration(message);
					}
				} else if (parse_info.equals("INFORMATION")) {
					controler.game_ready();
					return true;
				} else if (parse_info.equals("IN_GAME")) {
					return analyse_in_game(message);
				}
			} else {
				return false;
			}
		}

		return false;
	}

	/**
	 * Méthode permettant de parser un message qui représente un message en jeu
	 * lui meme et réalise les actions si le parsing est réussi
	 * 
	 * @param message
	 *            le message a analyser
	 * @return vrai si le message a été parser avec succés, faux sinon
	 */
	private boolean analyse_in_game(String message) {
		// System.out.println("Message recu = " + message);
		String[] splitted = message.split("&!");

		if (splitted[2].equals("SURREND")) {
			controler.get_game_controler().surrend_from_player(splitted[3]);
			return true;
		} else if (splitted[2].equals("NEXT_PLAYER")) {
			controler.get_game_controler().change_player_turn_to_next();
			return true;
		} else if (splitted[2].equals("WINNER")) {
			controler.get_game_controler().change_winner(splitted[3]);
			return true;
		} else if (splitted[2].equals("DICE_ROLLING")) {
			controler.get_game_controler().launch_animation_dice();
			return true;
		} else if (splitted[2].equals("DICE_VALUES")) {
			Vector<Integer> values = new Vector<Integer>();
			for (int i = 1; i <= Setting_configuration.nb_dices; i++) {
				values.add(new Integer(splitted[2 + i]));
			}
			controler.get_game_controler().dice_values_receive(values);
			return true;
		} else if (splitted[2].equals("PIECE_MOVE")) {
			controler.get_game_controler().change_piew_position(splitted[3], Integer.valueOf(splitted[4]));
			return true;
		} else if (splitted[2].equals("PLAYER_BUY")) {
			controler.get_game_controler().play_respond_buying(splitted[3], splitted[4]);
			return true;
		} else if (splitted[2].equals("MORTGAGE")) {
			controler.get_game_controler().player_mortgage(splitted[3], Integer.valueOf(splitted[4]),
					Integer.valueOf(splitted[5]));
			return true;
		} else if (splitted[2].equals("UNMORTGAGE")) {
			controler.get_game_controler().player_unmortgage(splitted[3], Integer.valueOf(splitted[4]),
					Integer.valueOf(splitted[5]));
			return true;
		} else if (splitted[2].equals("ADD_HOUSE")) {
			controler.get_game_controler().change_nb_house_on_terrain(splitted[3], Integer.valueOf(splitted[4]),
					Integer.valueOf(splitted[5]));
			return true;
		} else if (splitted[2].equals("REMOVE_PROP")) {
			controler.get_game_controler().change_prop_selled(splitted[3], Integer.valueOf(splitted[4]));
			return true;
		} else if (splitted[2].equals("JAIL_OUT")) {
			controler.get_game_controler().change_jail_out_player(splitted[3]);
			return true;
		} else if (splitted[2].equals("PAY")) {
			controler.get_game_controler().receive_pay_due(splitted[3], splitted[4], Integer.valueOf(splitted[5]));
			return true;
		} else if (splitted[2].equals("AUCTION_START")) {
			controler.get_game_controler().launch_auction(Integer.valueOf(splitted[3]));
			return true;
		} else if (splitted[2].equals("AUCTION_VALUE")) {
			controler.get_game_controler().change_auction_value_for_player(splitted[3], Integer.valueOf(splitted[4]));
			return true;
		} else if (splitted[2].equals("AUCTION_END")) {
			controler.get_game_controler().end_auction();
			return true;
		} else if (splitted[2].equals("TRADE")) {
			controler.get_game_controler().receive_trade_wanted(splitted[3], splitted[4]);
			return true;
		} else if (splitted[2].equals("TRADE_PROP")) {
			controler.get_game_controler().receive_house_adding(splitted[3], Integer.valueOf(splitted[4]),
					Boolean.valueOf(splitted[5]));
			return true;
		} else if (splitted[2].equals("TRADE_MONEY")) {
			controler.get_game_controler().receive_money_change(splitted[3], Integer.valueOf(splitted[4]));
			return true;
		} else if (splitted[2].equals("TRADE_CANCEL")) {
			controler.get_game_controler().receive_cancel_trade();
			return true;
		} else if (splitted[2].equals("TRADE_VALIDATE")) {
			controler.get_game_controler().receive_validate_trade(splitted[3], Boolean.valueOf(splitted[4]));
			return true;
		} else if (splitted[2].equals("TRADE_END")) {
			controler.get_game_controler().receive_trade_end();
			return true;
		} else if (splitted[2].equals("PAUSE")) {
			controler.get_game_controler().receive_pause(Boolean.valueOf(splitted[3]));
			return true;
		} else if (splitted[2].equals("RENT_UP")) {
			controler.get_game_controler().rent_up();
			return true;
		} else if (splitted[2].equals("FIRE_GO")) {
			controler.get_game_controler().fire_go();
			return true;
		} else if (splitted[2].equals("MESSAGE")) {
			controler.get_game_controler().receive_sended_message(splitted[3]);
			return true;
		} else if (splitted[2].equals("BANKRUPTCY")) {
			controler.get_game_controler().bankruptcy_player(splitted[3], Integer.valueOf(splitted[4]), splitted[5]);
			return true;
		} else if (splitted[2].equals("STOLE")) {
			controler.get_game_controler().receive_stole(splitted[3], splitted[4], Integer.valueOf(splitted[5]));
			return true;
		} else if (splitted[2].equals("TELEPORT")) {
			controler.get_game_controler().receive_station_teleport(splitted[3], Integer.valueOf(splitted[4]));
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Méthode permettant d'analyser un message dans la configuration de terrain
	 * 
	 * @param message
	 *            le message a analyser
	 * @return vrai si analyse réussie, faux sinon
	 */
	private boolean analyse_configuration_terrain(String message) {
		String[] splitted = message.split("&!");

		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(splitted[3].trim()));
		reader.setLenient(true);
		Table_terrain_value terrain = gson.fromJson(reader, Table_terrain_value.class);

		Terrains_from_server.terrains_server.addElement(terrain);

		controler.send_message_to_server("GAME" + " " + "CONFIG" + " " + "TERRAINS" + " " + "OK");
		return true;
	}

	/**
	 * Méthode permettant d'analyser un message dans la configuration des
	 * variantes de jeu
	 * 
	 * @param message
	 *            le message a analyser
	 * @return vrai si analyse réussie, faux sinon
	 */
	private boolean analyse_configuration(String message) {
		String[] splitted = message.split("&!");

		String[] splitted_config = splitted[2].split("=");
		if (splitted_config.length == 1) {
			if (splitted_config[0].equals("START")) {
				controler.send_message_to_server("GAME" + " " + "CONFIG" + " " + "OK");
				return true;
			} else if (splitted_config[0].equals("END")) {
				controler.send_message_to_server("GAME" + " " + "CONFIG" + " " + "OK");
				return true;
			} else {
				return false;
			}
		} else if (splitted_config.length != 2) {
			return false;
		} else {

			String elem = splitted_config[0];
			String value = splitted_config[1];

			if (elem.equals("starting_money"))
				Setting_configuration.starting_money = Integer.valueOf(value);
			else if (elem.equals("random_position"))
				Setting_configuration.random_position = Boolean.valueOf(value);
			else if (elem.equals("random_properties_gave"))
				Setting_configuration.random_properties_gave = Boolean.valueOf(value);
			else if (elem.equals("nb_dices"))
				Setting_configuration.nb_dices = Integer.valueOf(value);
			else if (elem.equals("ask_nb_dices_turn"))
				Setting_configuration.ask_nb_dices_turn = Boolean.valueOf(value);
			else if (elem.equals("jail_if_sum_3_dices"))
				Setting_configuration.jail_if_sum_3_dices = Boolean.valueOf(value);
			else if (elem.equals("nb_house"))
				Setting_configuration.nb_house = Integer.valueOf(value);
			else if (elem.equals("nb_hotel"))
				Setting_configuration.nb_hotel = Integer.valueOf(value);
			else if (elem.equals("nb_turn_before_end"))
				Setting_configuration.nb_turn_before_end = Integer.valueOf(value);
			else if (elem.equals("suicide_mod"))
				Setting_configuration.suicide_mod = Boolean.valueOf(value);
			else if (elem.equals("rent_double"))
				Setting_configuration.rent_double = Boolean.valueOf(value);
			else if (elem.equals("auction_allow"))
				Setting_configuration.auction_allow = Boolean.valueOf(value);
			else if (elem.equals("minutes_for_auction"))
				Setting_configuration.minutes_for_auction = Integer.valueOf(value);
			else if (elem.equals("random_board"))
				Setting_configuration.random_board = Boolean.valueOf(value);
			else if (elem.equals("mask_board"))
				Setting_configuration.mask_board = Boolean.valueOf(value);
			else if (elem.equals("no_care_equality_house"))
				Setting_configuration.no_care_equality_house = Boolean.valueOf(value);
			else if (elem.equals("percentage_sell"))
				Setting_configuration.percentage_sell = Integer.valueOf(value);
			else if (elem.equals("rate_rebuy_hypotheck"))
				Setting_configuration.rate_rebuy_hypotheck = Integer.valueOf(value);
			else if (elem.equals("teleportation_station"))
				Setting_configuration.teleportation_station = Boolean.valueOf(value);
			else if (elem.equals("nb_turn_in_jail"))
				Setting_configuration.nb_turn_in_jail = Integer.valueOf(value);
			else if (elem.equals("sum_to_out_jail"))
				Setting_configuration.sum_to_out_jail = Integer.valueOf(value);
			else if (elem.equals("allow_earn_money_during_jailing"))
				Setting_configuration.allow_earn_money_during_jailing = Boolean.valueOf(value);
			else if (elem.equals("rent_each_turn_jailing"))
				Setting_configuration.rent_each_turn_jailing = Boolean.valueOf(value);
			else if (elem.equals("double_sum_on_case_start"))
				Setting_configuration.double_sum_on_case_start = Boolean.valueOf(value);
			else if (elem.equals("tax_according_to_player_money"))
				Setting_configuration.tax_according_to_player_money = Boolean.valueOf(value);
			else if (elem.equals("earn_money_on_parking"))
				Setting_configuration.earn_money_on_parking = Boolean.valueOf(value);
			else if (elem.equals("steal_card"))
				Setting_configuration.steal_card = Boolean.valueOf(value);
			else if (elem.equals("mix_card"))
				Setting_configuration.mix_card = Boolean.valueOf(value);
			else if (elem.equals("jail_3_double")) {
				Setting_configuration.jail_3_double = Boolean.valueOf(value);
			} else {
				System.err.println("BIG ISSUE IN PARSING CONFIG");
				return false;
			}

			controler.send_message_to_server("GAME" + " " + "CONFIG" + " " + "OK");
			return true;
		}
	}
}
