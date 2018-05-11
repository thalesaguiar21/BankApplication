import dao.AccountDao;
import dao.UserDao;
import domain.Account;
import domain.User;

public class TestORM {
	
	public static void main(String[] args) {
		AccountDao accDao = new AccountDao();
		UserDao userDao = new UserDao();
		// User usr = new User("Test", "12312312300");
		
		try {
			User usr = userDao.createUser("Test", "12312312300");
			Account acc = accDao.createAccount(usr, 700.0);
			System.out.println("Account" + acc.getAccNumber() + " created with success!");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
