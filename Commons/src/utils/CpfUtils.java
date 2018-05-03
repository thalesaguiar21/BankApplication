package utils;

public class CpfUtils {
	
	public static boolean isValid(String cpf) {
		boolean valid = true;
		valid = valid && (cpf != null);
		valid = valid && (cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}") || cpf.matches("\\d{11}"));
		return valid;
	}
	
	public static String formatToPersist(String cpf) {
		if(CpfUtils.isValid(cpf)) {
			return cpf.trim().replace(".", "").replace("-", "");
		}
		return null;
	}
}
