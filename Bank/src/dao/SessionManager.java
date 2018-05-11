package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionManager {

	private static SessionFactory sessionFactory;
	
	private SessionManager() {}
	
	public static Session getSession() {
		if(sessionFactory == null) {
			try {				
				sessionFactory = new Configuration().configure().buildSessionFactory();
			} catch (Throwable e) {
				System.err.println("Failed to create session factory!");
				e.printStackTrace();
			}
		}
		
		return sessionFactory.openSession();
	}
}
