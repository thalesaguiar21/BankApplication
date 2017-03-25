package exceptions;

/**
 * Exceção que representa a tentativa de tranferência de dinheiro entre uma mesma conta bancária.
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
