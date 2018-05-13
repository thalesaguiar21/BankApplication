package rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Set;

import dao.AccountDao;
import dao.LogDao;
import dao.UserDao;
import domain.AOperation;
import domain.Account;
import domain.Log;
import domain.User;
import exceptions.BankException;
import operation.CreateAccOperation;
import operation.DepositOperation;
import operation.FindAccountsOperation;
import operation.PrintLogOperation;
import operation.TransferenceOperation;
import operation.WithdrawOperation;
import services.ICashMachine;

public class CashMachine extends UnicastRemoteObject implements ICashMachine, Serializable {
	
	static final long serialVersionUID = 0;

	public CashMachine() throws RemoteException {
		super();
	}

	@Override
	public Object executeAccountOp(AOperation operation) throws RemoteException {
		if(operation != null) {
			switch (operation.getType()) {
			case CREATE:
				return createAccount(operation);
				
			case WITHDRAW:
				return withdraw((WithdrawOperation) operation);
				
			case DEPOSIT:
				return deposit((DepositOperation) operation);
				
			case TRANSFERENCE:				
				return transference((TransferenceOperation) operation);
				
			case FIND_ACCOUNTS:
				return findAccounts((FindAccountsOperation) operation);
				
			case GET_LOG:
				return printLog((PrintLogOperation) operation);

			default:
				break;
			}
		} else
			System.out.println("[ERROR] No operation found!");
		return false;
	}

	private String createAccount(AOperation op) throws BankException {
		CreateAccOperation cOp = (CreateAccOperation) op;
		UserDao userDao = new UserDao();
		AccountDao accDao = new AccountDao();
		LogDao logDao = new LogDao();
		User usr = userDao.findByCpf(cOp.getCpf());
		
		if(usr == null) {
			usr = userDao.createUser(cOp.getOwnerName(), cOp.getCpf());
		}
		
		Account acc = accDao.createAccount(usr, cOp.getValue());
		logDao.create(cOp.getLog(), acc);
		String result = "[ACCOUNT]\tNº " + acc.getAccNumber() + "\tBalance: " + acc.getBalance() + "\n";
		return result;
	}
	
	private String withdraw(WithdrawOperation operation) {
		AccountDao accDao = new AccountDao();
		LogDao logDao = new LogDao();
		Account acc = accDao.findByNumber(Long.valueOf(operation.getOriginAccount()));
		if(acc != null) {
			acc.withdraw(operation.getValue());
			accDao.updateAccount(acc);
			logDao.create(operation.getLog(), acc);
			return "Success! Yout new balance is: " + acc.getBalance() + "\n";
		} else
			return "Could not find account with number: " + operation.getOriginAccount() + "\n";
	}
	
	private String deposit(DepositOperation operation) {
		AccountDao accDao = new AccountDao();
		LogDao logDao = new LogDao();
		Account acc = accDao.findByNumber(Long.valueOf(operation.getOriginAccount()));
		if(acc != null) {
			if(acc.deposit(operation.getValue())) {
				accDao.updateAccount(acc);
				logDao.create(operation.getLog(), acc);
				return "Success! Your new balance is " + acc.getBalance() + "\n";
			} else {
				return "Failed to deposit! The following value is not valid: " + operation.getValue() + "\n";
			}
		}
		return "Could not find account with number: " + operation.getOriginAccount() + "\n";
	}
	
	private boolean transference(TransferenceOperation operation) {
		return true;
	}
	
	private StringBuilder findAccounts(FindAccountsOperation operation) {
		StringBuilder result = null;
		if(operation.isValid()) {
			UserDao userDao = new UserDao();
			User usr = userDao.findByCpf(operation.getOwnerCpf());
			if(usr != null) {
				usr.getAccounts();
				Set<Account> accounts = usr.getAccounts();
				result = new StringBuilder();
				for(Account acc : accounts) {
					result.append("[ACCOUNT]\tNº " + acc.getAccNumber() + "\tBalance: " + acc.getBalance() + "\n");
				}
			}
		}
		return result;
	}
	
	private StringBuilder printLog(PrintLogOperation operation) {
		StringBuilder result = null;
		if(operation.isValid()) {
			LogDao logDao = new LogDao();
			Collection<Log> logs = logDao.findByAccNumber(Long.valueOf(operation.getOriginAccount()));
			if(logs != null) {
				result = new StringBuilder();
				result.append("[OPERATION]\tValue\t\tDate\n");
				result.append("----------------------------------------------");
				for(Log log : logs) {
					result.append(log.getMsg() + "\n");
				}
			}
		}
		return result;
	}
}
