package exceptions;

/**
 * Exceção que representa a tentativa de cadastrar/digitar um cpf em um formato diferente de ###.###.###-## onde # repr
 * esenta um dígito de 0 a 9.
 * 
 * @author thalesaguiar
 *
 */
public class InvalidCpfException extends BankException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCpfException() {
		super("The cpf must have the foloowiing format: ###.###.###-##, where # is a digit!");
	}

}
