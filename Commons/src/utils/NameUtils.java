package utils;

public class NameUtils {
	
	public static boolean isValidName(String name) {
		boolean valid = true;
		valid = valid && (name != null);
		valid = valid && (name.matches("[a-zA-Z]{4}[\\sa-zA-Z]*"));
		return valid;
	}
}
