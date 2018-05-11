package dao;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.User;
import exceptions.BankException;
import utils.CpfUtils;

public class UserDao {

	public User createUser(String name, String cpf) throws BankException {
		if(!CpfUtils.isValid(cpf)) {
			throw new BankException("Invalid CPF");
		}
		
		Transaction t = null;
		Long userId = null;
		User usr = new User(name, cpf);
		
		try {
			Session session = SessionManager.getSession();
			t = session.beginTransaction();
			userId = (Long) session.save(usr);
			usr.setId(userId);
			t.commit();
		} catch (Exception e) {
			if(t.isActive()) {
				t.rollback();
			}
			e.printStackTrace();
		} finally {
			
		}
		return usr;
	}
	
	
	public void deleteUser(User usr) {
		Transaction t = null;
		
		try {
			Session session = SessionManager.getSession();
			t = session.beginTransaction(); 
			session.delete(usr);
			t.commit();
		} catch (Exception e) {
			if(t.isActive()) {
				t.rollback();
			}
			e.printStackTrace();
		}
	}
	
	public Collection<User> findAll() {
		try {
			Session session = SessionManager.getSession();
			@SuppressWarnings("deprecation")
			Criteria c = session.createCriteria(User.class);
			@SuppressWarnings("unchecked")
			Collection<User> result = (Collection<User>) c.list();
			return result;
		} catch (Exception e) {
			System.out.println("COULD NOT FIND USERS!");
			e.printStackTrace();
		}
		return null;
	}
}
