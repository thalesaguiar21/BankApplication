package exceptions;

/**
 * Exceção de mais alto nível das possíveis exceções da aplicação, todas as outras exceções são sub-classes dessa.
 * 
 * @author thalesaguiar
 *
 */
public class BankException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BankException(String message) {
		super(message);
	}

}
