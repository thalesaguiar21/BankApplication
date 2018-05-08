import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dao.AccountDao;
import dao.SessionManager;
import domain.Account;

public class TestORM {
	
	public static void main(String[] args) {
		AccountDao accDao = new AccountDao();
		
		Account acc = accDao.createAccount("12312312300", "Test", 700.0);
		
		System.out.println("Account created with success!");
	}
	
	

}
