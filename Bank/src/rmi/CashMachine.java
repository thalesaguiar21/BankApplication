package rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import domain.AOperation;
import remote.ICashMachine;

public class CashMachine extends UnicastRemoteObject implements ICashMachine, Serializable {
	
	static final long serialVersionUID = 0;

	public CashMachine() throws RemoteException {
		super();
	}

	@Override
	public boolean executeAccountOp(AOperation operation) throws RemoteException {
		if(operation != null)
			System.out.println(operation.createFormattedLog());
		else
			System.out.println("[ERROR] No operation found!");
		return false;
	}

}
