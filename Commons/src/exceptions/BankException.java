package exceptions;

import java.rmi.RemoteException;

public class BankException extends RemoteException {

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
