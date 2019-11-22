package auxiliares;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.Date;

import clases.Licencia;
import clases.LicenciaExpirada;
import clases.Persona;
import clases.Titular;
import clases.UsuarioAdministrador;

public class GestorBaseDeDatos {

	public GestorBaseDeDatos() {

	}


	public UsuarioAdministrador buscarUsuarioAdministrador(int idusuario) {
		// crear factory

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UsuarioAdministrador.class)
				.buildSessionFactory();

		// crear sesi�n

		Session session = factory.getCurrentSession();
		// usar el objeto session
		session.beginTransaction();
		UsuarioAdministrador u = session.get(UsuarioAdministrador.class, idusuario);
		session.getTransaction().commit();
		session.close();
		factory.close();
		
		return u;
	}


	public int guardarLicencia(Licencia l) {
		// crear factory

		SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		// crear sesi�n
		Session session = sf.openSession();

		// usar el objeto session

		session.beginTransaction();

		int idt = (int) session.save(l);

		session.getTransaction().commit();
		session.close();
		sf.close();

		return idt;
	}
	
	public int guardarTitular(Titular t) {
		// crear factory

		SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Titular.class).buildSessionFactory();
		// crear sesi�n
		Session session = sf.openSession();

		// usar el objeto session

		session.beginTransaction();

		int idt = (int) session.save(t);

		session.getTransaction().commit();
		session.close();
		sf.close();

		return idt;
	}
	
	public void updateTitular(Titular t) {
		// crear factory

		SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Titular.class).buildSessionFactory();
		// crear sesi�n
		Session session = sf.openSession();

		// usar el objeto session

		session.beginTransaction();

		session.update(t);

		session.getTransaction().commit();
		session.close();
		sf.close();

	}
	
	
	public int guardarUsuarioAdministrador(UsuarioAdministrador t) {
		// crear factory

		SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UsuarioAdministrador.class).buildSessionFactory();
		// crear sesi�n
		Session session = sf.openSession();

		// usar el objeto session

		session.beginTransaction();

		session.save(t);

		session.getTransaction().commit();
		session.close();
		sf.close();

		return 1;
	}
	
	public void guardarPersona(Persona p) {
		// crear factory

		SessionFactory sf = new Configuration().configure("hibernate2.cfg.xml").addAnnotatedClass(Persona.class).buildSessionFactory();
		// crear sesi�n
		Session session = sf.openSession();

		// usar el objeto session

		session.beginTransaction();

		session.save(p);

		session.getTransaction().commit();
		session.close();
		sf.close();
	}
	
	public ArrayList<Persona> getPersonas(int dni) {

		// crear objeto factory
		SessionFactory factory = new Configuration().configure("hibernate2.cfg.xml").addAnnotatedClass(Persona.class)
				.buildSessionFactory();

		// crear sesi�n

		Session session = factory.getCurrentSession();

		// usar el objeto session
		session.beginTransaction();
		
		Query q = session.createQuery("select p from Persona p where p.dni = :dni");
		q.setParameter("dni", dni);
		List<Persona> personas = q.list();
		
		session.getTransaction().commit();
		session.close();

		factory.close();
		return (ArrayList<Persona>) personas;
		
		}
	
	public ArrayList<Titular> getTitular(int dni,String apellido, String nombre) {

		// crear objeto factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Titular.class)
				.buildSessionFactory();

		// crear sesi�n

		Session session = factory.getCurrentSession();

		// usar el objeto session
		session.beginTransaction();
		
		Query q = session.createQuery("select t from Titular t where t.dni = :dni and t.apellido = :apellido and t.nombre = :nombre");
		//t.apellido = :apellido t.nombre = :nombre
		q.setParameter("dni", dni);
		q.setParameter("apellido", apellido);
		q.setParameter("nombre", nombre);
		List<Titular> titulares = q.list();
		
		session.getTransaction().commit();
		session.close();

		factory.close();
		return (ArrayList<Titular>) titulares;
		
		}
	
	
	public ArrayList<LicenciaExpirada> getLicenciasExpiradas() {

		Date fecha_sistema=new Date();
		// crear objeto factory
		
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(LicenciaExpirada.class)
				.buildSessionFactory();

		// crear sesi�n
		//factory.openSession();

		Session session = factory.getCurrentSession();

		// usar el objeto session
		session.beginTransaction();
		
		
		Query q = session.createQuery("select l from LicenciaExpirada l where l.fecha_de_vencimiento < :fecha_sistema ");
		//.fecha_de_vencimiento, l.id, l.clase
		//+ "left outer join Titular t ON l.titular_id=t.id"
		q.setParameter("fecha_sistema", fecha_sistema);
		List<LicenciaExpirada> licenciasExpliradas = q.list();
		
		
		/*
		String queryStr =   "select NEW package.LicenciaExpirada("
				+ " a.field1, b.field2, c.field3, c.field4) from a left outer join b "
				+ "	on a.id=b.fk left outer join c on b.id=c.fk";

			TypedQuery<LicenciaExpirada> query = session.createQuery(queryStr, LicenciaExpirada.class);

			List<LicenciaExpirada> results = query.getResultList();*/
		
		
		session.getTransaction().commit();
		session.close();

		factory.close();
		return  (ArrayList<LicenciaExpirada>) licenciasExpliradas;
		
		}
}
