package app;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.ConnectException;
import java.sql.SQLException;
import java.util.Scanner;

import exceptions.BankException;
import remote.interfaces.ICashMachine;

/**
 * Implementação da interface com o usuário da aplicação. Responsável por se conectar com o servidor e fazer as devidas 
 * requisições para ele a partir de seu <i>stub</i>.
 * 
 * @author thalesaguiar
 *
 */
public class Client {
	
	private ICashMachine stub;
	private Scanner reader;
	
	/**
	 * Sobrecarga para o construtor padrão da classe Client. Além de inicializar um <i>Scanner</i> é aqui que é realiza
	 * da a conexão com o servidor.
	 * 
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public Client() throws MalformedURLException, RemoteException, NotBoundException {
		stub = (ICashMachine) Naming.lookup("rmi://localhost:9090/CashMachine");
		reader = new Scanner(System.in);
	}
	
	/**
	 * Inicializa a interface com usuário, permitindo que ele escolha a operação que deseja realizar e o redireciona pa
	 * ra o método referente à operação selecionada.
	 * @throws RemoteException 
	 */
	public void selectOperation () throws RemoteException {
		int userOp = -1;
		
		do {
			
			System.out.println("Select one of the following operations: ");
			System.out.println("1) Create an account.");
			System.out.println("2) Deposit.");
			System.out.println("3) Withdraw.");
			System.out.println("4) Transference.");
			System.out.println("5) Print statement.");
			System.out.println("6) Exit.");
			
			System.out.print("Enter the operation: ");
			
			try {
				userOp = Integer.parseInt(reader.nextLine());
				
				switch (userOp) {
				case 1:
					createOpUi();
					break;
				
				case 2:
					depositOpUi();
					break;
					
				case 3:
					withdrawOpUi();
					break;
					
				case 4:
					transferenceOpUi();
					break;
					
				case 5:
					printStatementOpUi();
					break;
					
				case 6:
					System.out.println("\nExiting the system...\n");
					break;
	
				default:
					System.out.println("\nThis is not a valid operation, please try again!\n");
					break;
				}
			
			} catch (NumberFormatException numForm) {
				System.out.println("\nInvalid number inserted!");
			} catch (NullPointerException noUserName) {
				System.out.println("\nPlase, insert an user name!");
			} catch (BankException bankE) { 
				System.out.println("ERROR: " + bankE.getMessage());
			} catch (SQLException sqlErr) {
				System.out.println("ERROR: The request to the DataBase have failed, plase contact the support!");
			} catch (ConnectException connExc) {
				System.out.println("ERROR: Could not connect to server!");
			} finally {
				System.out.println("\n");
			}
			if (userOp != 6) System.out.println("Press enter to go back to the menu...");
			reader.nextLine();
			for (int i = 0; i < 50; i++) {
				System.out.println();
			}
		} while (userOp != 6);
		
		reader.close();
	}
	
	/**
	 * Inicializa a interface para criação de contas bacárias. O usuário deve digitar os seguintes dados: Nome, CPF e S
	 * aldo inicial para a criação da conta, levando em consideração que o cpf deve estar no seu formato padrão.
	 * 
	 * @throws RemoteException
	 */
	public void createOpUi() throws RemoteException, BankException, SQLException {
		
		System.out.println("\nEnter the foloowing information to create a new account: ");
		
		System.out.print("Name: ");
		String userName = reader.nextLine();
		
		System.out.print("CPF: ");
		String userCPF = reader.nextLine();
		
		System.out.print("Balance: ");
		double balance = Double.parseDouble(reader.nextLine());
		
		String accNumber = stub.createAccount(userName, userCPF, balance);
		System.out.println("\nYour account was created with success! The account number is " + accNumber);
	}
	
	/**
	 * Inicializa a interface para realizar depósitos em conta. O usuário deve fornecer os seguintes dados para o depós
	 * ito: Número da conta e valor para depositar.
	 * 
	 * @throws RemoteException
	 */
	public void depositOpUi() throws RemoteException, BankException, SQLException {
	
		System.out.println("\nEnter the following information to make a deposit: ");
		
		System.out.print("Account number: ");
		int accountNumber = Integer.parseInt(reader.nextLine());
		
		System.out.print("Value: ");
		double value = Double.parseDouble(reader.nextLine());
		System.out.println("\n");
		
		String result = stub.deposit(accountNumber, value);
		System.out.println("\nValue deposited with success, your account new balance is: " + result);
	}
	
	/**
	 * Inicializa a interface para retirada de dinheiro da conta. Para isso o usuário deve fornecer o número da conta e
	 * o valor que será retirado.
	 * 
	 * @throws RemoteException
	 */
	public void withdrawOpUi() throws RemoteException, BankException, SQLException {
	
		System.out.println("\nEnter the following information to make a withdraw: ");
		
		System.out.print("Account number: ");
		int accountNumber = Integer.parseInt(reader.nextLine());
		
		System.out.print("Value: ");
		double value = Double.parseDouble(reader.nextLine());
		System.out.println("\n");
		
		String result = stub.withdraw(accountNumber, value);
		System.out.println("\nValue withdrawn with success, your account new balance is: " + result);
	}
	
	/**
	 * Inicializa a interface para tranferência de dinheiro entre contas bancárias. Para isso o usuário deve fornecer o
	 * número da sua conta, a número da conta que receberá o dinheiro e o valor que será transferido.
	 * 
	 * @throws RemoteException
	 */
	public void transferenceOpUi() throws RemoteException, BankException, SQLException {

		System.out.println("\nEnter the following information to make a transferece: ");
		
		System.out.print("Your account number: ");
		int accNumber = Integer.parseInt(reader.nextLine());
		
		System.out.print("Receiver account number: ");
		int receiAccNumber = Integer.parseInt(reader.nextLine());
		
		System.out.print("Value: ");
		double value = Double.parseDouble(reader.nextLine());
		System.out.println("\n");
		
		String result = stub.transferece(receiAccNumber, accNumber, value);
		System.out.println("\nValue transfered with success, the accounts new balances ares:" + result);
	}
	
	/**
	 * Inicializa a interface para emissão de extrato bancário. Para isso é necessário que o usuário forneça o número d
	 * a sua conta
	 * 
	 */
	public void printStatementOpUi() throws RemoteException, BankException, SQLException {
		
		System.out.println("\nEnter the following information to print the statement: ");
		
		System.out.print("Account number: ");
		int accNumber = Integer.parseInt(reader.nextLine());
		
		String result = stub.printStatement(accNumber);
		System.out.println(result);
	}

	
	public static void main(String[] args) 
			throws NotBoundException, BankException, RemoteException, MalformedURLException {

		Client myClient = new Client();
		myClient.selectOperation();

	}

}
