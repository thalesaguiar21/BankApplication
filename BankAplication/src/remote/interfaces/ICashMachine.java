package remote.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import exceptions.AccountNotFoundException;
import exceptions.InsuficientBalanceException;
import exceptions.InvalidCpfException;
import exceptions.InvalidValueException;
import exceptions.SameAccountTransferenceException;

/**
 * Interface remota criada para gerenciar as operações entre o cliente e o servidor. Representa um caixa eletrônico e s
 * uas principais operações.
 * 
 * @author thalesaguiar
 *
 */
public interface ICashMachine extends Remote {
	
	/**
	 * Cria uma nova conta com um número de conta aleatório.
	 * 
	 * @param userName Nome do dono da conta.
	 * @param userCPF Cpf do dono da conta.
	 * @param balance Saldo inicial da conta.
	 * @return Uma string contendo o número da nova conta.
	 * @throws RemoteException
	 * @throws NullPointerException Quando o nome do usuário for nulo, vazio, ou uma quebra de linha.
	 * @throws InvalidValueException Quando o o saldo inicial for negativo.
	 * @throws InvalidCpfException Quando o cpf não for da forma ###.###.###-## onde # é um número entre 0 e 9.
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String createAccount(String userName, String userCPF, double balance) 
			throws RemoteException, NullPointerException, InvalidValueException, InvalidCpfException, SQLException;
	
	/**
	 * Realiza um depósito de um valor em uma conta com o númeroda conta passado como parâmetro.
	 * 
	 * @param accountNumber Número da conta que receberá o dinheiro.
	 * @param value Valor que será depositado.
	 * @return Uma string contendo o novo saldo da conta.
	 * @throws RemoteException 
	 * @throws InvalidValueException Quando o valor do depósito for negativo.
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String deposit(int accountNumber, double value) 
			throws RemoteException, InvalidValueException, SQLException, AccountNotFoundException;
	
	/**
	 * Faz uma saque em uma conta com o número de conta passado como parâmetro.
	 * 
	 * @param accountNumber Número da conta que será retirado o dinheiro.
	 * @param value Valor que será retirado da conta.
	 * @return Uma string contendo o novo saldo da conta.
	 * @throws RemoteException
	 * @throws InvalidValueException Quando o valor que será retirado da conta for negativo.
	 * @throws InsuficientBalanceException Quando a conta não possuir saldo suficiente.
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String withdraw(int accountNumber, double value) 
			throws RemoteException, InvalidValueException, InsuficientBalanceException, 
				   SQLException, AccountNotFoundException;
	
	/**
	 * Transfere um determinado valor entre duas contas. Não é possível fazer transferências entre duas contas A e B
	 * caso o número da conta de A seja o mesmo de B. Além disso é notável que a tranferência é composta de um saque e 
	 * um depósito.
	 * 
	 * @param receiAccNumber Número da conta que receberá o valor.
	 * @param accNumber Número da conta que está transferindo o valor.
	 * @param value Valor que será transferido.
	 * @return Uma string contendo os novos saldos das contas.
	 * @throws RemoteException
	 * @throws InvalidValueException Quando o valor que será transferido for negativo.
	 * @throws SameAccountTransferenceException Quando as duas contas possuírem o mesmo número.
	 * @throws InsuficientBalanceException Quando a conta que fará a tranferência não possuir saldo suficiente.
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String transferece(int receiAccNumber, int accNumber, double value) 
			throws RemoteException, InvalidValueException, AccountNotFoundException,
				   SameAccountTransferenceException, InsuficientBalanceException, SQLException;
	
	
	/**
	 * Emite um extrato bancário contendo todas as operações realizadas na conta desde a sua abertura.
	 * 
	 * @param accountNumber Número da conta que terá o extrato emitido.
	 * @return Uma string contendo o histórico da conta.
	 * @throws RemoteException
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String printStatement(int accountNumber) 
			throws RemoteException, SQLException, AccountNotFoundException;
	
}
