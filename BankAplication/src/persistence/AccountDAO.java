package persistence;

import java.sql.SQLException;
import java.util.List;

import app.Account;

/**
 * DAO criado para o controle de acesso aos dados das contas bancárias dentro da aplicação.
 * 
 * @author thalesaguiar
 *
 */
public class AccountDAO {
	
	private SQLITEJDBCMannager dbManager;
	
	/**
	 * Sobrecarga do construtor padrão para objetos da classe AccountDAO. Nele também é criado, caso não exista, um nov
	 * o arquivo chamado <strong>bank.db</strong> que será responsável pelo armazenamento dos dados da aplicação.
	 */
	public AccountDAO() {
		dbManager = new SQLITEJDBCMannager();
		dbManager.start();
	}
	
	/**
	 * Insere uma nova conta bancária no banco de dados.
	 * 
	 * @param acc Conta que será inserida no banco.
	 * @return true Caso a operação ocorra normalmente.
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
	 * possui valor igual ao passado como parâmetro.
	 * 
	 * @param accNumber Número que será comparado com a coluna account_number.
	 * @param balance Novo saldo da conta.
	 * @throws SQLException Quando não for possível executar o query.
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
	 * Busca no banco de dados por uma conta com um dado número.
	 * 
	 * @param accNumber Número da conta que será buscada.
	 * @return Uma instância de Account contendo as informações da conta bancária encontrada.
	 * @throws SQLException Quando ocorrer algum erro durante a execução do query. 
	 * @throws ClassNotFoundException Quando não for possível inicializar o driver JDBC.
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
	 * Registra um log com as operações realizadas na conta bancária.
	 * 
	 * @param opDescription Descrição da operação realizada.
	 * @param accID Valor da chave primária da tabela account.
	 * @throws SQLException Quando ocorrer algum erro durante a execução do query.
	 * @throws ClassNotFoundException Quando não for possível inicializar o driver JDBC.
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
	 * Cria uma string contendo todos os registros de operações relizadas na conta logo após sua abertura.
	 * 
	 * @param accId Chave primária da conta que será emitido o extrato bancário.
	 * @return String contendo todas as operações realizadas na conta após sua abertura.
	 * @throws SQLException Quando não for possível executar o query.
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
