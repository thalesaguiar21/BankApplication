package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.Account;

public class AccountDao {
	
	public List<Account> createAccounts(Collection<Account> accounts) {
		List<Account> accs = new ArrayList<>();
		for(Account acc : accounts) {
			accs.add(createAccount(acc.getOwnerCpf(), acc.getOwnerName(), acc.getBalance()));
		}
		return accs;
	}
	
	public Account createAccount(String cpf, String ownerName, double balance) {
		Session session = SessionManager.getSession();
		Transaction t = null;
		Long accId = null;
		Account acc = new Account(balance, cpf, ownerName);
		
		try {
			t = session.beginTransaction();
			accId = (Long) session.save(acc);
			acc.setId(accId);
			t.commit();
		} catch (HibernateException e) {
			if(t != null)
				t.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return acc;
	}
	
	public void updateAccounts(Collection<Account> accounts) {
		for(Account acc : accounts) {
			updateAccount(acc);
		}
	}
	
	public void updateAccount(Account acc) {
		
	}
	
	public void listAccounts() {
		
	}
	
	public void deleteAccounts(Collection<Account> accounts) {
		for(Account acc : accounts) {
			deleteAccount(acc);
		}
	}
	
	public void deleteAccount(Account acc) {
		
	}
	
}
