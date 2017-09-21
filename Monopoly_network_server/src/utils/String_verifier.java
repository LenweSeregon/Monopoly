package utils;

public class String_verifier {

	/**
	 * Méthode permettant de savoir si un string est uniquement un numérique
	 * 
	 * @param str
	 *            le string a vérifier
	 * @return vrai si le string n'est qu'un numérique, faux sinon
	 */
	public static boolean string_is_numeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

}
