package exceptions;

/**
 * Exceção que representa a tentativa de realizar alguma operações com uma conta que não está registrada no banco de d
 * ados.
 * @author thalesaguiar
 *
 */
public class AccountNotFoundException extends BankException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException() {
		super("The account is not registred!");
	}
	
}
