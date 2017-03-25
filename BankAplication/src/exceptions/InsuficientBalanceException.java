package exceptions;

/**
 * Exceção que representa a tentativa de retirada de dinheiro de uma conta sem saldo suficiente.
 * @author thalesaguiar
 *
 */
public class InsuficientBalanceException extends BankException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InsuficientBalanceException() {
		super("Your account don't have enough balance");
	}
}
