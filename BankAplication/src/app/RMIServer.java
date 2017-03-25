package app;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import remote.interfaces.ICashMachine;

/**
 * Servidor RMI registrado na porta 9090.
 * 
 * @author thalesaguiar
 *
 */
public class RMIServer {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		
		ICashMachine cashMachine = new CashMachine();
		
		LocateRegistry.createRegistry(9090);
		
		Naming.rebind("rmi://localhost:9090/CashMachine", cashMachine);
		System.out.println("Server ready and registred in the RMI Registry.");
	}

}