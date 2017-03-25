package exceptions;

/**
 * Exceção criada para representar a tentativa de usar valores negativos de dinheiro para as operações bancárias.
 * @author thalesaguiar
 *
 */
public class InvalidValueException extends BankException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidValueException() {
		super("The value can not be negative");
	}

}
