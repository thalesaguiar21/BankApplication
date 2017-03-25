package persistence;

import java.sql.SQLException;
import java.util.List;

import app.Account;

/**
 * DAO criado para o controle de acesso aos dados das contas banc�rias dentro da aplica��o.
 * 
 * @author thalesaguiar
 *
 */
public class AccountDAO {
	
	private SQLITEJDBCMannager dbManager;
	
	/**
	 * Sobrecarga do construtor padr�o para objetos da classe AccountDAO. Nele tamb�m � criado, caso n�o exista, um nov
	 * o arquivo chamado <strong>bank.db</strong> que ser� respons�vel pelo armazenamento dos dados da aplica��o.
	 */
	public AccountDAO() {
		dbManager = new SQLITEJDBCMannager();
		dbManager.start();
	}
	
	/**
	 * Insere uma nova conta banc�ria no banco de dados.
	 * 
	 * @param acc Conta que ser� inserida no banco.
	 * @return true Caso a opera��o ocorra normalmente.
	 */
	public Account createAccount(String owner, String ownerCPF, double balance) {
		String sql = "";
		try {
			Account acc = new Account(owner, ownerCPF, balance);
			sql = "INSERT INTO account (owner, cpf, account_number, balance)"
					+ "VALUES ('" + acc.getOwner() + "', '" + acc.getOwnerCPF() + "', "
					+ acc.getAccountNumber() + ", " + acc.getBalance() + ");";
			
			dbManager.execUpdate(sql);
			return acc;
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		} catch (ClassNotFoundException clasF) {
			System.out.println("Ocorreu um erro ao tentar encontrar uma classe para o Driver JDBC");
		}
		
		return null;
	}
	
	/**
	 * Atualiza a coluna balance da tabela account onde a coluna account_number
	 * possui valor igual ao passado como par�metro.
	 * 
	 * @param accNumber N�mero que ser� comparado com a coluna account_number.
	 * @param balance Novo saldo da conta.
	 * @throws SQLException Quando n�o for poss�vel executar o query.
	 */
	public void updateBalance(int accNumber, double balance) throws SQLException{
		try {
			String sql = "UPDATE account SET balance = " + balance
							+ " WHERE account_number = " + accNumber + ";";
			dbManager.execUpdate(sql);
		} catch (ClassNotFoundException e) {
			System.out.println("Ocorreu um erro ao tentar encontrar uma classe para o Driver JDBC");
		} 
	}

	/**
	 * Busca no banco de dados por uma conta com um dado n�mero.
	 * 
	 * @param accNumber N�mero da conta que ser� buscada.
	 * @return Uma inst�ncia de Account contendo as informa��es da conta banc�ria encontrada.
	 * @throws SQLException Quando ocorrer algum erro durante a execu��o do query. 
	 * @throws ClassNotFoundException Quando n�o for poss�vel inicializar o driver JDBC.
	 */
	public Account getByAccountNumber(int accNumber) throws SQLException {
		try {
			String sql = "SELECT * FROM account "
					+ "WHERE account_number = " + accNumber + ";";
			List<Account> accounts = dbManager.findAcc(sql);
			return (accounts.isEmpty()) ? (null) : (accounts.get(0));
		} catch (ClassNotFoundException clasF) {
			System.out.println("Ocorreu um erro ao tentar encontrar uma classe para o Driver JDBC");
			return null;
		}
	}
	
	/**
	 * Registra um log com as opera��es realizadas na conta banc�ria.
	 * 
	 * @param opDescription Descri��o da opera��o realizada.
	 * @param accID Valor da chave prim�ria da tabela account.
	 * @throws SQLException Quando ocorrer algum erro durante a execu��o do query.
	 * @throws ClassNotFoundException Quando n�o for poss�vel inicializar o driver JDBC.
	 */
	public void registerOperation(String opDescription, int accID) throws SQLException {
		try {
			String sql = "INSERT INTO account_log (id_account, description) "
					+ "VALUES (" + accID + ", '" + opDescription + "');";
			dbManager.execUpdate(sql);
		} catch (ClassNotFoundException clasF) {
			System.out.println("Ocorreu um erro ao tentar encontrar uma classe para o Driver JDBC");
		}
	}
	
	/**
	 * Cria uma string contendo todos os registros de opera��es relizadas na conta logo ap�s sua abertura.
	 * 
	 * @param accId Chave prim�ria da conta que ser� emitido o extrato banc�rio.
	 * @return String contendo todas as opera��es realizadas na conta ap�s sua abertura.
	 * @throws SQLException Quando n�o for poss�vel executar o query.
	 */
	public String getAccStatement(int accId) throws SQLException {
		
		try {
			String sql = "SELECT description FROM account_log WHERE id_account = " + accId + ";";
			String result = dbManager.getLog(sql);
			
			return result;
		} catch (ClassNotFoundException clasF) {
			System.out.println("Ocorreu um erro ao tentar encontrar uma classe para o Driver JDBC");
			return null;
		}
		
	}
}
