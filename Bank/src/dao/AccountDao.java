package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.Account;
import domain.User;

public class AccountDao {
	
	public List<Account> createAccounts(Collection<Account> accounts) {
		List<Account> accs = new ArrayList<>();
		for(Account acc : accounts) {
			accs.add(createAccount(acc.getUser(), acc.getBalance()));
		}
		return accs;
	}
	
	public Account createAccount(User usr, double balance) {
		Session session = SessionManager.getSession();
		Transaction t = null;
		Long accId = null;
		Account acc = new Account(balance, usr);
		
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
		Session session = SessionManager.getSession();
		Transaction t = null;
		
		try {
			t = session.beginTransaction();
			session.update(acc);
			t.commit();
		} catch (HibernateException e) {
			if(t != null)
				t.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public Collection<Account> findAll() {
		Session session = SessionManager.getSession();
		@SuppressWarnings("deprecation")
		Criteria c = session.createCriteria(Account.class);
		@SuppressWarnings("unchecked")
		Collection<Account> result = (Collection<Account>) c.list();
		return result;
	}
	
	public void deleteAccounts(Collection<Account> accounts) {
		for(Account acc : accounts) {
			deleteAccount(acc);
		}
	}
	
	public void deleteAccount(Account acc) {
		Session session = SessionManager.getSession();
		Transaction t = null;
		
		try {
			t = session.beginTransaction();
			session.delete(acc);
			t.commit();
		} catch (HibernateException e) {
			if(t != null)
				t.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
}
