package exceptions;

/**
 * Exce��o de mais alto n�vel das poss�veis exce��es da aplica��o, todas as outras exce��es s�o sub-classes dessa.
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
