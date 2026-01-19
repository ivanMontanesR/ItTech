package util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * PATRON SINGLETON PARA CREAR UNA FÁBRICA DE CONEXIONES A LA BD
 * es un patrón singleton porque sólo puede haber una única conexión 
 * a la BD, por tanto, nos aseguramos con esta forma de codificar que
 * simpre tendremos únicamente una sola conexión.
 * 
 * @author Eva Royo
 * @version 1.0 
 *
 */
public class HibernateUtil { 

	// DEFINIMOS UNA VARIABLE QUE ES FINAL
	private static final SessionFactory sessionFactory = buildSessionFactory();

	// DEFINIMOS UN MÉTODO ESTÁTICO para construir la sesión
	private static SessionFactory buildSessionFactory() {
		try {
			//creamos la fábrica de sesiones utilizando hibernate.cfg.xml
			Configuration configuration = new Configuration().configure();
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure() ;          	
			return configuration.buildSessionFactory(builder.build());
			
			//lo anterior tambien lo podemos poner asi    	       	
			//return new Configuration().configure().buildSessionFactory(
			//			new StandardServiceRegistryBuilder().configure().build() );		
		}
		catch (Throwable ex) {
			// Nos aseguramos de que vamos a registrar la excepción
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}

