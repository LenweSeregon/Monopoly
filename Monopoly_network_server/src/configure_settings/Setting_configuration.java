package configure_settings;

public class Setting_configuration {

	// General variants

	public static int starting_money = 150000;
	public static boolean random_position = false;
	public static boolean random_properties_gave = false;
	public static int nb_dices = 2;
	public static boolean ask_nb_dices_turn = false;
	public static boolean jail_if_sum_3_dices = false;
	public static int nb_house = 32;
	public static int nb_hotel = 12;
	public static int nb_turn_before_end = -1;
	public static boolean suicide_mod = false;
	public static boolean rent_double = false;

	// Auction
	public static boolean auction_allow = true;
	public static int minutes_for_auction = 30;

	// Board
	public static boolean random_board = false;
	public static boolean mask_board = false;

	// Property
	public static boolean no_care_equality_house = false;
	public static int percentage_sell = 50;
	public static int rate_rebuy_hypotheck = 10;

	// Gare, eau, electricite
	public static boolean teleportation_station = false;

	// Jail
	public static int nb_turn_in_jail = 3;
	public static int sum_to_out_jail = 5000;
	public static boolean allow_earn_money_during_jailing = false;
	public static boolean rent_each_turn_jailing = false;
	public static boolean jail_3_double = true;

	// Start tile, taxe, parking public
	public static boolean double_sum_on_case_start = false;
	public static boolean tax_according_to_player_money = false;
	public static boolean earn_money_on_parking = false;

	// Luck and communauty
	public static boolean steal_card = false;
	public static boolean mix_card = false;
}
