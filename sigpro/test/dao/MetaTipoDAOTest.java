package dao;

import static org.junit.Assert.*;

import java.io.File;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.MetaTipo;
import utilities.CHibernateSession;
import utilities.TestSetUp;

public class MetaTipoDAOTest {

	
	@Test
	public void getMetaTiposTest(){
		assertNotNull(MetaTipoDAO.getMetaTipos());
	}
	@Test
	public void getMetaTipoPorIdTest(){
		assertNotNull(MetaTipoDAO.getMetaTipoPorId(1));
	}
	@Test
	public void guardarMetaTipoTest(){
		MetaTipo metaTipo = new MetaTipo();
		metaTipo.setNombre("unit_test");
		metaTipo.setDescripcion("unit_test");
		metaTipo.setUsuarioCreo("admin");
		metaTipo.setEstado(1);
		assertNotNull(MetaTipoDAO.guardarMetaTipo(metaTipo));
	}
	@Test
	public void eliminarMetaTipoTest(){
		assertEquals(MetaTipoDAO.eliminarMetaTipo(new MetaTipo()),false);
	}
	
	@Test
	public void eliminarTotalMetaTipoTest(){
		assertNotNull(MetaTipoDAO.eliminarTotalMetaTipo(new MetaTipo()));
	}
	
	@Test
	public void getMetaTiposPaginaTest(){
		assertNotNull(MetaTipoDAO.getMetaTiposPagina(1, 1));
	}
	
	@Test
	public void getTotalMetaTiposTest(){
		assertEquals(MetaTipoDAO.getTotalMetaTipos().getClass(),Long.class);
	}
	
	@AfterClass
	public static void cleanData(){
		Session session = CHibernateSession.getSessionFactory().openSession();
		try{
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<MetaTipo> criteria = builder.createQuery(MetaTipo.class);
			Root<MetaTipo> root = criteria.from(MetaTipo.class);
			criteria.where( builder.and(builder.equal(root.get("nombre"), "unit_test")));
			MetaTipo MetaTipoTmp =(MetaTipo) session.createQuery( criteria ).getSingleResult();
			session.beginTransaction();
			session.delete(MetaTipoTmp);
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		
	}
}
