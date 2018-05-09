package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import rmi.CashMachine;
import services.ICashMachine;

public class Server {
	
	public String serverIp;
	public String port;
	public String hostName;
	
	public Server() {
		serverIp = "localhost";
		port = "9090";
		hostName = "localhost";
	}
	
	public String getAddress() {
		return serverIp + ":" + port;
	}
	
	public static void main(String[] args) {
		Server server = new Server();		
		try {
			ICashMachine cashMachine = new CashMachine();
			System.setProperty("java.rmi.server.hostname", server.serverIp);
			LocateRegistry.createRegistry(9090);
			Naming.rebind("rmi://" + server.getAddress() + "/CashMachine", cashMachine);
			System.out.println("Server running on -> " + server.getAddress());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch(MalformedURLException mUrl) {
			mUrl.printStackTrace();
		}
	}
}
