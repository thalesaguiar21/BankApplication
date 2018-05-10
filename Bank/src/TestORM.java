import dao.AccountDao;
import domain.Account;
import domain.User;

public class TestORM {
	
	public static void main(String[] args) {
		AccountDao accDao = new AccountDao();
		User usr = new User();
		usr.setCpf("12312312300");
		usr.setName("Test");
		
		Account acc = accDao.createAccount(usr, 700.0);
		
		System.out.println("Account created with success!");
	}
}
