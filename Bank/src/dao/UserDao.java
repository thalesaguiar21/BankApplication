package dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

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
		User usr = new User(name, CpfUtils.formatToPersist(cpf));
		Session session = SessionManager.getSession();
		
		try {
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
			session.close();
		}
		return usr;
	}
	
	
	public void deleteUser(User usr) {
		Transaction t = null;
		Session session = SessionManager.getSession();
		
		try {
			t = session.beginTransaction();
			usr = session.load(User.class, usr.getId());
			if(usr != null)
				session.delete(usr);
			t.commit();
		} catch (Exception e) {
			if(t.isActive()) {
				t.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public User update(User usr) {
		Transaction t = null;
		Session session = SessionManager.getSession();
		
		try {
			t = session.beginTransaction();
			session.update(usr);
			t.commit();
		} catch (Exception e) {
			if(t.isActive()) {
				t.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		return usr;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<User> findAll() {
		List<User> users = null;
		try {
			Session session = SessionManager.getSession();
			users = session.createQuery(" from User ").list();
		} catch (Exception e) {
			System.out.println("COULD NOT FIND USERS!");
			e.printStackTrace();
		}
		return users;
	}
	
	
	@SuppressWarnings("unchecked")
	public User findByCpf(String cpf) {
		User usr = null;
		if(!CpfUtils.isValid(cpf))
			return usr;
		
		Session session = SessionManager.getSession();
		
		try {
			String projection = " from User where cpf = :user_cpf ";
			Query query = session.createQuery(projection);
			query.setParameter("user_cpf", cpf);
			List<User> result = query.getResultList();
			if(result != null && result.size() > 0)
				usr = result.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return usr;
	}
}
