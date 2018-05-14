package services;

import java.rmi.RemoteException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import domain.AOperation;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ICashMachine  {
	
	@WebMethod
	public Object executeAccountOp(AOperation operation) throws RemoteException;
}
