package services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import domain.AOperation;

public interface ICashMachine extends Remote {
	public Object executeAccountOp(AOperation operation) throws RemoteException;
}
