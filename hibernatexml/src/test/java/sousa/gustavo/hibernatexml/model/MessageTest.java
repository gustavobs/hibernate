package sousa.gustavo.hibernatexml.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class MessageTest {

	private SessionFactory factory;
	
	@Before
	public void setUp() {
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		builder.configure("hibernate.cfg.xml");
		StandardServiceRegistry registry = builder.build();
		factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}
	
	@Test
	public void testCreateAndRead() {
		Session session = factory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			{
				Message m = new Message();
				m.setMessage("hello hibernate!");			
				session.persist(m);
			}
			
			{
				List<Message> messages = session.createCriteria(Message.class).list();
				Assert.assertEquals(1, messages.size());
				Assert.assertEquals("hello hibernate!", messages.get(0).getMessage());
			}
				
			transaction.commit();
		}catch(Exception e) {
			transaction.rollback();
		}		

	}

}
