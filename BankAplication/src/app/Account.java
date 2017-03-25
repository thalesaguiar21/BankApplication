package app;
import java.util.Random;

import exceptions.InsuficientBalanceException;
import exceptions.InvalidValueException;


/**
 * Entidade para representar contas banc�rias.
 * 
 * @author thalesaguiar
 *
 */
public class Account {
	
	private int id;
	private String owner;
	private String ownerCPF;
	private int accountNumber;
	private double balance;
	private Random rand;
	
	/**
	 * Sobrecarga do construtor padr�o para objetos da classe Account. � necess�rio informar o nome do usus�rio, seu 
	 * CPF e o saldo inicial da conta. Al�m disso, para cada conta � criado um n�mero aleat�rio que representa o n�mero
	 * da conta.
	 * 
	 * @param owner Nome do dono da conta.
	 * @param ownerCPF CPF do dono da conta.
	 * @param balance Saldo inicial da conta.
	 */
	public Account(String owner, String ownerCPF, double balance)  {
		this.owner = owner;
		this.ownerCPF = ownerCPF;
		this.balance = balance;
		
		rand = new Random();
		accountNumber = rand.nextInt(300000);
	}
	
	/**
	 * Realiza um dep�sito na conta banc�ria verificando a validade do valor passado.
	 * 
	 * @param value Valor que ser� depositado na conta.
	 * @return O novo saldo da conta.
	 * @throws InvalidValueException Quando o valor do dep�sito � negativo.
	 */
	public double deposit(double value) throws InvalidValueException {
		if(value < 0)
			throw new InvalidValueException();
		else
			balance += value;
		return balance;
	}
	
	/**
	 * Realiza um saque na conta banc�ria verificando a validade do valor passado assim como se existe saldo suficiente.
	 * 
	 * @param value Valor que ser� retirado da conta.
	 * @return O novo saldo da conta.
	 * @throws InvalidValueException Quando o valor que ser� sacado for negativo.
	 * @throws InsuficientBalanceException Quando a conta n�o possuir saldo suficiente para o saque.
	 */
	public double withcdraw(double value) throws InvalidValueException, InsuficientBalanceException {
		if(value < 0)
			throw new InvalidValueException();
		else if(value > balance)
			throw new InsuficientBalanceException();
		else
			balance -= value;
		return balance;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getOwnerCPF() {
		return ownerCPF;
	}


	public void setOwnerCPF(String ownerCPF) {
		this.ownerCPF = ownerCPF;
	}


	public int getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
