package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionManager {

	private static SessionFactory factory;
	
	private SessionManager() {}
	
	public static Session getSession() {
		if(factory == null) {
			try {
				factory = new Configuration().configure().buildSessionFactory();
			} catch (Throwable e) {
				System.err.println("Failed to create session factory!");
				e.printStackTrace();
			}
		}
		
		if(factory.isOpen())
			factory.close();
		
		return factory.openSession();
	}
}
