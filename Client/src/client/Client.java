package client;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import domain.AOperation;
import operation.OperationType;
import services.ICashMachine;
import ui.BasicInterface;

public class Client {
	
	private String address;
	private ICashMachine machineStub;
	private BasicInterface UI;
	
	public Client(String serverAdress) {
		if(serverAdress != null) {
			address = serverAdress;
		}
		UI = new BasicInterface();
	}
	
	public void run() {
		OperationType currentOperation = OperationType.EXIT;
		AOperation operation;
		try {
			URL url = new URL("http://" + address + "/CashMachine?wsdl");
			QName qname = new QName("http://rmi/", "CashMachineService");
			
			Service service = Service.create(url, qname);
			
			ICashMachine server = service.getPort(ICashMachine.class);
			do {
				currentOperation = UI.selectOp();
				if(currentOperation != null) {
					operation = UI.redirectOp(currentOperation);
					if(operation != null) {
						Object obj = server.executeAccountOp(operation);
						System.out.println("\n\n" + obj);
					}						
				} else {
					System.out.println("[ERROR] Invalid operation, please try again!");
					System.out.println("\n\n");
				}
			} while(currentOperation != OperationType.EXIT);
		} catch (MalformedURLException e) {
			System.err.println("[ERROR] Invalid adress!");
			e.printStackTrace();
		} catch (RemoteException e) {
			System.err.println("[ERROR] Failed to communicate with server!");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client("localhost:9090");
		client.run();
	}
}
