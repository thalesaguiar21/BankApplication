package exceptions;

/**
 * Exce��o que representa a tentativa de tranfer�ncia de dinheiro entre uma mesma conta banc�ria.
 * @author thalesaguiar
 *
 */
public class SameAccountTransferenceException extends BankException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SameAccountTransferenceException() {
		super("The receiver account is the same as the account making the transference!");
	}

}
