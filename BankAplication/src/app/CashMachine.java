package app;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Timestamp;

import exceptions.AccountNotFoundException;
import exceptions.InsuficientBalanceException;
import exceptions.InvalidCpfException;
import exceptions.InvalidValueException;
import exceptions.SameAccountTransferenceException;
import persistence.AccountDAO;
import remote.interfaces.ICashMachine;

/**
 * Servant para interface remota com as implementações da interface
 * ICashMachine.
 * @author thalesaguiar
 *
 */
@SuppressWarnings("serial")
public class CashMachine extends UnicastRemoteObject implements ICashMachine{
	
	private AccountDAO myDao;
	
	protected CashMachine() throws RemoteException {
		super();
		myDao = new AccountDAO();
	}
	
	private boolean validateCpf(String cpf) {
		return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
				|| cpf.matches("\\d{12}"); 
	}
	
	@Override
	public String createAccount(String userName, String userCPF, double balance) 
			throws RemoteException, NullPointerException, InvalidValueException, InvalidCpfException, SQLException {
		
		System.out.println("Creating account...");
		if(balance < 0) {
			throw new InvalidValueException();
		} else if(!validateCpf(userCPF)) {
			throw new InvalidCpfException();
		} else if(userName == null || userName.equals("") || userName == ("\n")) {
			throw new NullPointerException();
		} else {
			Account acc = myDao.createAccount(userName, userCPF, balance);
			return "" + acc.getAccountNumber();
		}
	}


	@Override
	public String deposit(int accountNumber, double value) 
			throws RemoteException, InvalidValueException, SQLException, AccountNotFoundException {
		
		Account acc = myDao.getByAccountNumber(accountNumber);
		
		if(acc == null) throw new AccountNotFoundException();
		
		double currBalance = acc.deposit(value);
		myDao.updateBalance(accountNumber, currBalance);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String opDescription = now.toString() + "\tDEPOSIT\t\t+" + value + "\t= " + currBalance;
		myDao.registerOperation(opDescription, acc.getId());
		return "" + currBalance;
	}
	
	@Override
	public String withdraw(int accountNumber, double value) 
			throws RemoteException, InvalidValueException, InsuficientBalanceException, 
				   SQLException, AccountNotFoundException {
		
		Account acc = myDao.getByAccountNumber(accountNumber);
		
		if(acc == null) throw new AccountNotFoundException();
		
		double currBalance = acc.withcdraw(value);
		myDao.updateBalance(accountNumber, currBalance);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String opDescription = now.toString() + "\tWITHDRAW\t-" + value + "\t= " + currBalance;
		myDao.registerOperation(opDescription, acc.getId());
		return "" + currBalance;
	}

	@Override
	public String transferece(int receAccNumber, int accNumber, double value) 
			throws RemoteException, InvalidValueException, AccountNotFoundException,
				   SameAccountTransferenceException, InsuficientBalanceException, SQLException {
		
		String result = "";
		if (value < 0)
			throw new InvalidValueException();
		else if (receAccNumber == accNumber)
			throw new SameAccountTransferenceException();
		else {
			String balanceAcc1 = withdraw(accNumber, value);
			String balanceAcc2 = deposit(receAccNumber, value);
			
			result = "\nYour acount:\t R$ " + balanceAcc1 
					 + "\nOther account:\t R$ " + balanceAcc2; 
		}
		
		return result;
	}

	@Override
	public String printStatement(int accountNumber) 
			throws RemoteException, SQLException, AccountNotFoundException {
		
		Account acc = myDao.getByAccountNumber(accountNumber);
		
		if(acc == null) throw new AccountNotFoundException();
		
		StringBuilder statement = new StringBuilder();
		statement.append("============================================================");
		String result = myDao.getAccStatement(acc.getId());
		statement.append(result);
		statement.append("\n============================================================");
		
		return statement.toString();
	}

}