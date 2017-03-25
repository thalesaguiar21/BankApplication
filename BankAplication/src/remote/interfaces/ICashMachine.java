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
 * Interface remota criada para gerenciar as opera��es entre o cliente e o servidor. Representa um caixa eletr�nico e s
 * uas principais opera��es.
 * 
 * @author thalesaguiar
 *
 */
public interface ICashMachine extends Remote {
	
	/**
	 * Cria uma nova conta com um n�mero de conta aleat�rio.
	 * 
	 * @param userName Nome do dono da conta.
	 * @param userCPF Cpf do dono da conta.
	 * @param balance Saldo inicial da conta.
	 * @return Uma string contendo o n�mero da nova conta.
	 * @throws RemoteException
	 * @throws NullPointerException Quando o nome do usu�rio for nulo, vazio, ou uma quebra de linha.
	 * @throws InvalidValueException Quando o o saldo inicial for negativo.
	 * @throws InvalidCpfException Quando o cpf n�o for da forma ###.###.###-## onde # � um n�mero entre 0 e 9.
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String createAccount(String userName, String userCPF, double balance) 
			throws RemoteException, NullPointerException, InvalidValueException, InvalidCpfException, SQLException;
	
	/**
	 * Realiza um dep�sito de um valor em uma conta com o n�meroda conta passado como par�metro.
	 * 
	 * @param accountNumber N�mero da conta que receber� o dinheiro.
	 * @param value Valor que ser� depositado.
	 * @return Uma string contendo o novo saldo da conta.
	 * @throws RemoteException 
	 * @throws InvalidValueException Quando o valor do dep�sito for negativo.
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String deposit(int accountNumber, double value) 
			throws RemoteException, InvalidValueException, SQLException, AccountNotFoundException;
	
	/**
	 * Faz uma saque em uma conta com o n�mero de conta passado como par�metro.
	 * 
	 * @param accountNumber N�mero da conta que ser� retirado o dinheiro.
	 * @param value Valor que ser� retirado da conta.
	 * @return Uma string contendo o novo saldo da conta.
	 * @throws RemoteException
	 * @throws InvalidValueException Quando o valor que ser� retirado da conta for negativo.
	 * @throws InsuficientBalanceException Quando a conta n�o possuir saldo suficiente.
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String withdraw(int accountNumber, double value) 
			throws RemoteException, InvalidValueException, InsuficientBalanceException, 
				   SQLException, AccountNotFoundException;
	
	/**
	 * Transfere um determinado valor entre duas contas. N�o � poss�vel fazer transfer�ncias entre duas contas A e B
	 * caso o n�mero da conta de A seja o mesmo de B. Al�m disso � not�vel que a tranfer�ncia � composta de um saque e 
	 * um dep�sito.
	 * 
	 * @param receiAccNumber N�mero da conta que receber� o valor.
	 * @param accNumber N�mero da conta que est� transferindo o valor.
	 * @param value Valor que ser� transferido.
	 * @return Uma string contendo os novos saldos das contas.
	 * @throws RemoteException
	 * @throws InvalidValueException Quando o valor que ser� transferido for negativo.
	 * @throws SameAccountTransferenceException Quando as duas contas possu�rem o mesmo n�mero.
	 * @throws InsuficientBalanceException Quando a conta que far� a tranfer�ncia n�o possuir saldo suficiente.
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String transferece(int receiAccNumber, int accNumber, double value) 
			throws RemoteException, InvalidValueException, AccountNotFoundException,
				   SameAccountTransferenceException, InsuficientBalanceException, SQLException;
	
	
	/**
	 * Emite um extrato banc�rio contendo todas as opera��es realizadas na conta desde a sua abertura.
	 * 
	 * @param accountNumber N�mero da conta que ter� o extrato emitido.
	 * @return Uma string contendo o hist�rico da conta.
	 * @throws RemoteException
	 * @throws SQLException Quando ocorre algum erro ao executar querys.
	 */
	public String printStatement(int accountNumber) 
			throws RemoteException, SQLException, AccountNotFoundException;
	
}
