package rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

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
	
	private StringBuilder transference(TransferenceOperation operation) {
		StringBuilder result = new StringBuilder(); 
		if(operation.isValid()) {
			AccountDao accDao = new AccountDao();
			LogDao logDao = new LogDao();
			Account accOrigin = accDao.findByNumber(Long.valueOf(operation.getOriginAccount()));
			Account accTarget = accDao.findByNumber(Long.valueOf(operation.getTargetAccount()));
			if(accOrigin != null && accTarget != null) {
				if(accOrigin.withdraw(operation.getValue()) && accTarget.deposit(operation.getValue())) {
					accDao.updateAccount(accOrigin);
					accDao.updateAccount(accTarget);
					logDao.create(operation.getLog(), accOrigin);
					logDao.create(operation.getLog(), accTarget);
					result.append("Success! Your new balance is: " + accOrigin.getBalance());
				} else {
					result.append("Could not withdraw or deposit the value: " + operation.getValue());
				}
			} else {
				result.append("Could not find one of the accounts");
			}
		} else {
			result.append("Invalid operation!");
		}
		return result;
	}
	
	private StringBuilder findAccounts(FindAccountsOperation operation) {
		StringBuilder result = null;
		if(operation.isValid()) {
			result = new StringBuilder();
			UserDao userDao = new UserDao();
			User usr = userDao.findByCpf(operation.getOwnerCpf());
			if(usr != null) {
				AccountDao accDao = new AccountDao();
				Collection<Account> accounts = accDao.findByOwner(usr.getId());
				for(Account acc : accounts) {
					result.append("[ACCOUNT]\tNº " + acc.getAccNumber() + "\tBalance: " + acc.getBalance() + "\n");
				}
			} else {
				result.append("Could not find user with cpf " + operation.getOwnerCpf());
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
				result.append("----------------------------------------------\n");
				for(Log log : logs) {
					result.append(log.getMsg() + "\n");
				}
			}
		}
		return result;
	}
}
