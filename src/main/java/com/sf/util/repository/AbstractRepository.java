package com.sf.util.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.sf.util.DAOException;

public class AbstractRepository<T> {
	private static final Logger LOG = Logger
			.getLogger(AbstractRepository.class);
	
	@PersistenceContext
    protected EntityManager entityManager;
    
    public T persist(T t)throws DAOException {
        try {
         entityManager.persist(t);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return t;
    }
    
    public T merge(T t)throws DAOException {
        try {
        	t = entityManager.merge(t);
        } catch(PersistenceException e) {
        	LOG.error(
					"PersistenceException: Error realizando merge de la clase " + t.toString() + ". El error es: "
							+ e.getMessage(), e);
			
			throw new DAOException(e.getMessage(),e);
        } catch(ConstraintViolationException e) {
        	LOG.error("ConstraintViolationException: Error realizando merge de la clase " + t.toString() + ". El error es: "
							+ e.getMessage(), e);
			
			throw new DAOException(e.getMessage(),e);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return t;
        
    }
    
    public void remove(T t)throws DAOException {
        
        entityManager.remove(t);
    }
    
    
    
}
