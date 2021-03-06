package dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import pojo.ObjetoPrestamo;
import pojo.Prestamo;
import utilities.CHibernateSession;
import utilities.CLogger;

public class PrestamoDAO {

	public static Prestamo getPrestamoPorObjetoYTipo(int objetoId, int objetoTipo){
		Session session = CHibernateSession.getSessionFactory().openSession();
		Prestamo ret = null;
		try{
			Query<Prestamo> criteria = session.createQuery("SELECT p FROM Prestamo p "
					+ "INNER JOIN p.objetoPrestamos op "
					+ " where op.id.objetoId = :objId "
					+ " and op.id.objetoTipo = :objTipo "
					+ " and p.estado = 1 ", Prestamo.class);
			criteria.setParameter("objId", objetoId);
			criteria.setParameter("objTipo", objetoTipo);
			ret = criteria.getSingleResult();
		}
		catch(Throwable e){
			CLogger.write("1", PrestamoDAO.class, e);
		}
		finally{
			session.close();
		}
		return ret;
	}
	
	public static ObjetoPrestamo getObjetoPrestamo(int idPrestamo){
		Session session = CHibernateSession.getSessionFactory().openSession();
		ObjetoPrestamo ret = null;
		try{
			Query<ObjetoPrestamo> criteria = session.createQuery("SELECT op FROM Prestamo p "
					+ "INNER JOIN p.objetoPrestamos op "
					+ " where op.id.prestamoid= :idPrestamo "
					+ " and op.estado= 1", ObjetoPrestamo.class);
			criteria.setParameter("idPrestamo", idPrestamo);
			ret = criteria.getSingleResult();
		}
		catch(Throwable e){
			e.printStackTrace();
			CLogger.write("1", PrestamoDAO.class, e);
		}
		finally{
			session.close();
		}
		return ret;
	}

	public static boolean guardarPrestamo(Prestamo prestamo,ObjetoPrestamo objetoPrestamo){
		boolean ret = false;
		Session session = CHibernateSession.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(prestamo);
			
			objetoPrestamo.setEstado(1);
			objetoPrestamo.setPrestamo(prestamo);
			objetoPrestamo.getId().setPrestamoid(prestamo.getId());
			objetoPrestamo.setFechaCreacion(prestamo.getFechaCreacion());
			objetoPrestamo.setUsuarioCreo(prestamo.getUsuarioCreo());
			
			session.saveOrUpdate(objetoPrestamo);
			session.getTransaction().commit();
			ret = true;
		}
		catch(Throwable e){
			e.printStackTrace();
			CLogger.write("2", PrestamoDAO.class, e);
		}
		finally{
			session.close();
		}
		return ret;
	}
}
