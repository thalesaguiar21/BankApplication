package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.Account;

/**
 * Classe criada para gerenciar operações genéricas com o banco de dados.
 * 
 * @author thalesaguiar
 *
 */
public class SQLITEJDBCMannager {
	
	/**
	 * Inicializa uma conexão com o banco de dados caso ele exista, caso contrário será criado um arquivo chamado 
	 * "bank.db".
	 */
	@SuppressWarnings("unused")
	public void start() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
			System.out.println("Database was opened with success!");
			createTables();
			
		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/**
	 * Cria as tabelas: "account" e "account_log" no banco.
	 * 
	 * @throws ClassNotFoundException Quando não for possível conectar o driver JDBC.
	 * @throws SQLException Quando não for possível executar o query.
	 */
	private void createTables() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
		System.out.println("Connection with database created with success!");
		
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS account ("
					 + "id INTEGER PRIMARY KEY,"
					 + "owner TEXT NOT NULL,"
					 + "cpf CHAR(14),"
					 + "account_number INT NOT NULL,"
					 + "balance REAL NOT NULL)";
		
		stmt.execute(sql);
		System.out.println("Table \"account\" created with success!");
		
		sql = "CREATE TABLE IF NOT EXISTS account_log ("
				+ "id INTEGER PRIMARY KEY,"
				+ "id_account INTEGER NOT NULL,"
				+ "description TEXT NOT NULL,"
				+ "FOREIGN KEY(id_account) REFERENCES account(id) )";

		stmt.execute(sql);
		System.out.println("Table \"account_log\" created with success!");
		
		stmt.close();
		conn.close();
	}
	
	/**
	 * Executa uma operação DDL no banco de dados.
	 * 
	 * @param sql Query que será executado no banco.
	 * @throws ClassNotFoundException Quando não for possível conectar o driver JDBC.
	 * @throws SQLException Quando não for possível executar o query.
	 */
	public void execUpdate(String sql) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
		conn.setAutoCommit(false);
		System.out.println("Connection with database created with success!");
		
		Statement stmt = conn.createStatement();
		
		System.out.println("UPDATE: " + sql);
		stmt.executeUpdate(sql);
		
		stmt.close();
		conn.commit();
		conn.close();
	}
	
	/**
	 * Executa uma operação de busca no banco de dados.
	 * 
	 * @param sql Query que será executado no banco.
	 * @return Uma lista das contas bancárias encontradas no banco de dados.
	 * @throws ClassNotFoundException Quando não for possível conectar o driver JDBC.
	 * @throws SQLException Quando não for possível executar o query.
	 */
	public List<Account> findAcc(String sql) throws ClassNotFoundException, SQLException {
		
		List<Account> result = new ArrayList<Account>();
		
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
		System.out.println("Connection with database created with success!");
		
		Statement stmt = conn.createStatement();
		
		System.out.println("SELECTION: " + sql);
		ResultSet findResult = stmt.executeQuery(sql);
		
		while(findResult.next()) {
			int id = findResult.getInt("id");
			String owner = findResult.getString("owner");
			String cpf = findResult.getString("cpf");
			int accNum = findResult.getInt("account_number");
			double balance = findResult.getDouble("balance");
			
			Account foundAccount = new Account(owner, cpf, balance);
			foundAccount.setAccountNumber(accNum);
			foundAccount.setId(id);
			result.add(foundAccount);
		}
		
		findResult.close();
		stmt.close();
		conn.close();
		
		return result;
	}
	
	
	/**
	 * Cria uma string contendo a descrição de todos os registros retornados pela busca.
	 * 
	 * @param sql Query que será executado no banco de dados.
	 * @return String contendo todas as operações da conta bancária após sua abertura.
	 * @throws ClassNotFoundException Quando não for possível conectar o driver JDBC.
	 * @throws SQLException Quando não for possível executar o query.
	 */
	public String getLog(String sql) throws ClassNotFoundException, SQLException {
		
		String result = "";
		
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:bank.db");
		conn.setAutoCommit(false);
		System.out.println("Connection with database created with success!");
		
		Statement stmt = conn.createStatement();
		
		System.out.println("SELECTION: " + sql);
		ResultSet findResult = stmt.executeQuery(sql);
		
		while(findResult.next()) {
			String description = findResult.getString("description");
			
			result += "\n" + description;
		}
		
		findResult.close();
		stmt.close();
		conn.close();
		
		return result;
	}

}
