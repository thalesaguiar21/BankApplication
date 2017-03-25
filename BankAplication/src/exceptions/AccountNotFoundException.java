package exceptions;

/**
 * Exce��o que representa a tentativa de realizar alguma opera��es com uma conta que n�o est� registrada no banco de d
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
