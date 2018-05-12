import java.util.Collection;

import dao.AccountDao;
import dao.LogDao;
import dao.UserDao;
import domain.Account;
import domain.Log;
import domain.User;

public class TestORM {
	
	public static void main(String[] args) {
		AccountDao accDao = new AccountDao();
		UserDao userDao = new UserDao();
		LogDao logDao = new LogDao(); 
		
		
		try {
			User usr = userDao.createUser("Test", "12312312300");
			Account acc = accDao.createAccount(usr, 700.0);
			Log log = logDao.create("CRIAÇÃO DA CONTA", acc);
			
			Collection<User> users = userDao.findAll();
			for(User user : users) {
				System.out.println("Name: " + user.getName());
				System.out.println("CPF: " + user.getCpf());
			}
			
			log.setMsg("CRIAÇÃO DA CONTA ALTERADO");
			logDao.update(log);
			
			acc.setBalance(8000.0);
			
			accDao.updateAccount(acc);
			
			usr.setName("Test changed");
			
			userDao.update(usr);
			
			Collection<Account> accounts = accDao.findAll();
			for(Account account : accounts) {
				System.out.println("Number: " + account.getAccNumber());
				System.out.println("Balance: " + account.getBalance());
				System.out.println("Owner: " + account.getUser().getName());
			}
			
			Collection<Log> logs = logDao.findAll();
			for(Log logi : logs) {
				System.out.println("Message: " + logi.getMsg());
			}
			
			accDao.deleteAccount(acc);
			userDao.deleteUser(usr);
			logDao.delete(log);
			
			// System.out.println("Account " + acc.getAccNumber() + " created with success!");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
