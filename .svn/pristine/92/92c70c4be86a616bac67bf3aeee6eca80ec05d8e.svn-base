package com.gja.gestionCasos.maestros.repository;





import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;


import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.casos.repository.CasoRepository;
import com.sf.util.DAOException;
import com.sf.util.repository.AbstractRepository;


@Repository("maestroTareaActividadRepository")

public class MaestroTareaActividadRepositoryImpl extends AbstractRepository<TareaActividad> implements MaestroTareaActividadRepository{
	private static final Logger LOG = Logger
			.getLogger(CasoRepository.class);

	public TareaActividad findByPK(TareaActividad tareaActividad) throws DAOException {
		TareaActividad actividadReturn;
		try {
			actividadReturn = entityManager.find(TareaActividad.class, tareaActividad.getCdtareaactividad());
			inicializarListas(actividadReturn);
			} catch (IllegalStateException e) {
			LOG.error("IllegalStateException: Error consultando el caso por PK " + tareaActividad.getCdtareaactividad() + ". El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("Exception: Error consultando el caso por PK " +tareaActividad.getCdtareaactividad() + ". El error es: "
							+ e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		return actividadReturn;
	}
	public void inicializarListas(TareaActividad actividadReturn){
		Hibernate.initialize(actividadReturn.getCdactividad());
		//Hibernate.initialize(actividadReturn.getCdtarea());
		Hibernate.initialize(actividadReturn.getCdtareaactividad());
		Hibernate.initialize(actividadReturn.getDsdetalle());
	
	}
}
