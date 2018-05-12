package dao;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.Account;
import domain.Log;

public class LogDao {

	public Log create(String msg, Account acc) {
		Log log = new Log(msg, acc);
		Session session = SessionManager.getSession();
		Transaction t = null;
		
		try {
			t = session.beginTransaction();
			Long logId = (Long) session.save(log);
			log.setId(logId);
			t.commit();
		} catch (Exception e) {
			if(t.isActive()) {
				t.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return log;
	}
	
	public Log create(Log log, Account acc) {
		Session session = SessionManager.getSession();
		Transaction t = null;
		
		try {
			t = session.beginTransaction();
			Long logId = (Long) session.save(log);
			log.setId(logId);
			t.commit();
		} catch (Exception e) {
			if(t.isActive()) {
				t.rollback();
			}
			e.printStackTrace();
		}
		return log;
	}
	
	public void delete(Log log) {
		Session session = SessionManager.getSession();
		Transaction t = null;
		
		try {
			t = session.beginTransaction();
			log = session.load(Log.class, log.getId());
			session.delete(log);
			t.commit();
		} catch (Exception e) {
			if(t.isActive()) {
				t.rollback();
			}
		}
	}
	
	public void update(Log log) {
		Session session = SessionManager.getSession();
		Transaction t = null;
		
		try {
			t = session.beginTransaction();
			session.update(log);
			t.commit();
		} catch (Exception e) {
			if(t.isActive())
				t.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Log> findAll() {
		Collection<Log> logs = null;
		Session session = SessionManager.getSession();
		
		try {
			logs = session.createQuery(" from Log ").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return logs;
	}
}
