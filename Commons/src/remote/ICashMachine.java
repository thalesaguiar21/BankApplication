package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import domain.AOperation;

public interface ICashMachine extends Remote {
	public boolean executeAccountOp(AOperation operation) throws RemoteException;
}
