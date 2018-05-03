package exceptions;

public class BankException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2335364022627504592L;
	
	public BankException() {
		
	}
	
	public BankException(String msg) {
		super(msg);
	}

}
