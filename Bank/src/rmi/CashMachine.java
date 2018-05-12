package rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dao.AccountDao;
import dao.LogDao;
import dao.UserDao;
import domain.AOperation;
import domain.Account;
import domain.User;
import exceptions.BankException;
import operation.CreateAccOperation;
import operation.DepositOperation;
import operation.WithdrawOperation;
import services.ICashMachine;

public class CashMachine extends UnicastRemoteObject implements ICashMachine, Serializable {
	
	static final long serialVersionUID = 0;

	public CashMachine() throws RemoteException {
		super();
	}

	@Override
	public boolean executeAccountOp(AOperation operation) throws RemoteException {
		if(operation != null) {
			switch (operation.getType()) {
			case CREATE:
				return createAccount(operation);
				
			case WITHDRAW:
				return withdraw((WithdrawOperation) operation);
				
			case DEPOSIT:
				return deposit((DepositOperation) operation);
				
			case TRANSFERENCE:				
				break;
				
			case FIND_ACCOUNTS:
				break;
				
			case GET_LOG:
				break;

			default:
				break;
			}
		} else
			System.out.println("[ERROR] No operation found!");
		return false;
	}

	private boolean createAccount(AOperation op) throws BankException {
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
		System.out.println("Account created!");
		return true;
	}
	
	private boolean withdraw(WithdrawOperation operation) {
		AccountDao accDao = new AccountDao();
		LogDao logDao = new LogDao();
		Account acc = accDao.findByNumber(Long.valueOf(operation.getOriginAccount()));
		if(acc != null) {
			acc.withdraw(operation.getValue());
			accDao.updateAccount(acc);
			logDao.create(operation.getLog(), acc);
		}
		return false;
	}
	
	private boolean deposit(DepositOperation operation) {
		AccountDao accDao = new AccountDao();
		LogDao logDao = new LogDao();
		Account acc = accDao.findByNumber(Long.valueOf(operation.getOriginAccount()));
		if(acc != null) {
			if(acc.deposit(operation.getValue())) {
				accDao.updateAccount(acc);
				logDao.create(operation.getLog(), acc);
				return true;
			}
		}
		return false;
	}
}
