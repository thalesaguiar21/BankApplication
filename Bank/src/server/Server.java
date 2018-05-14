package server;

import java.rmi.RemoteException;

import javax.xml.ws.Endpoint;

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
			System.out.println("Publishing server...");
			ICashMachine cashMachine = new CashMachine();
			Endpoint.publish("http://" + server.getAddress() + "/CashMachine", cashMachine);
			System.out.println("Server running on -> " + server.getAddress());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
