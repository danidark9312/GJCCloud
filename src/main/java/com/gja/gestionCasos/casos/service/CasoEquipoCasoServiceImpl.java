package com.gja.gestionCasos.casos.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCasoPK;
import com.gja.gestionCasos.casos.entities.Celular;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.repository.CasoEquipoCasoRepository;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;


@Service("CasoEquipoCasoService")
@Transactional(readOnly = true)
public class CasoEquipoCasoServiceImpl implements CasoEquipoCasoService{
	@Autowired
	CasoEquipoCasoRepository casoEquipoCasoRepository;
	@Autowired
	EquipoCasoService equipoCasoService;
	

	@Transactional
	public CasoEquipoCaso consultarCasoEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK) throws DAOException,BusinessException {
		
		CasoEquipoCaso casoEquipoCaso = casoEquipoCasoRepository.consultarCasoEquipoCaso(casoEquipoCasoPK);
		
		Hibernate.initialize(casoEquipoCaso.getResponsablesTareas());
		for (ResponsableTarea responsable:casoEquipoCaso.getResponsablesTareas()) {
			TareaParticularCaso tarea = responsable.getTareasParticularesCaso();
			Hibernate.initialize(tarea.getActividadParticular());
		}
		Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getTelefonoList());
		Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getCelularList());
		Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getCorreoList());
		Hibernate.initialize(casoEquipoCaso.getEquipoCaso().getUsuario());
		Hibernate.initialize(casoEquipoCaso.getParentesco());
			
			
		
		return casoEquipoCaso;
	}
	

	@Transactional
	private User setAbogadosDatos(EquipoCaso equipoCasoExistente, CasoEquipoCaso casoEquipoCaso, EquipoCaso equipoCaso){
		User usuario = equipoCasoExistente.getUsuario();
		usuario.setNombre(equipoCaso.getNombre());
		usuario.setApellido(equipoCaso.getApellido());
		//Se setea la informacion, ya tiene conexion con user
		usuario.setNumeroTarjetaProfesional(casoEquipoCaso.getEquipoCaso().getUsuario().getNumeroTarjetaProfesional());
		
		if (null!= casoEquipoCaso.getEquipoCaso().getCelularList() && !casoEquipoCaso.getEquipoCaso().getCelularList().isEmpty() ){
			usuario.setCelular(casoEquipoCaso.getEquipoCaso().getCelularList().get(0).getNumero());
		}
		if (casoEquipoCaso.getEquipoCaso().getTelefonoList() != null &&  !casoEquipoCaso.getEquipoCaso().getTelefonoList().isEmpty() ){
			usuario.setNumeroTelefono(casoEquipoCaso.getEquipoCaso().getTelefonoList().get(0).getNumero());
		}
		if (casoEquipoCaso.getEquipoCaso().getCorreoList() != null && !casoEquipoCaso.getEquipoCaso().getCorreoList().isEmpty() ){
			usuario.setEmail(casoEquipoCaso.getEquipoCaso().getCorreoList().get(0).getCorreo());
		}
		if (casoEquipoCaso.getDireccion()  != null && casoEquipoCaso.getDireccion() != "" ){
			usuario.setDireccion(casoEquipoCaso.getDireccion());
		}
		
		return usuario;
	}
	
	@Transactional
	public CasoEquipoCaso modificarMiembroEquipoCaso(
			CasoEquipoCaso casoEquipoCaso, CasoEquipoCasoPK casoEquipoCasoPK, String nombreFoto) throws DAOException,
			BusinessException {
		EquipoCaso equipoCasoExistente = equipoCasoService.findByPK(new EquipoCaso(casoEquipoCasoPK.getCodigoEquipoCaso()));
		if(nombreFoto == null)
			nombreFoto = equipoCasoExistente.getNombreFoto();
		
		EquipoCaso equipoCaso = casoEquipoCaso.getEquipoCaso();
		if (equipoCasoExistente != null) {
			equipoCasoService.borrarEquipoCaso(equipoCasoExistente);
			equipoCaso.setCodigoEquipoCaso(equipoCasoExistente.getCodigoEquipoCaso());
			if (casoEquipoCasoPK.getMiembro() == Integer.parseInt(Parametros.getAbogado())) {
				equipoCasoExistente.getUsuario().setNombre(equipoCaso.getNombre());
				equipoCasoExistente.getUsuario().setApellido(equipoCaso.getApellido());
				//Se setea la informacion, ya tiene conexion con user
				equipoCasoExistente.getUsuario().setNumeroTarjetaProfesional(casoEquipoCaso.getEquipoCaso().getUsuario().getNumeroTarjetaProfesional());
				equipoCaso.setUsuario(equipoCasoExistente.getUsuario());
				
				equipoCaso.setUsuario(setAbogadosDatos(equipoCasoExistente, casoEquipoCaso, equipoCaso));
			}
		}
		if (nombreFoto != null) {
			equipoCaso.setNombreFoto(nombreFoto);
		}
		equipoCaso = equipoCasoService.guardarEquipoCaso(equipoCaso);
		casoEquipoCaso.getCasoEquipoCasoPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
		casoEquipoCaso.setEquipoCaso(equipoCaso);
		casoEquipoCaso = casoEquipoCasoRepository.guardarCasoEquipoCaso(casoEquipoCaso);
		return casoEquipoCaso;
	}
	
	@ Transactional
	public CasoEquipoCaso inactivarEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK) throws DAOException,
			BusinessException {
		CasoEquipoCaso casoEquipoCaso = casoEquipoCasoRepository.consultarCasoEquipoCaso(casoEquipoCasoPK);
		casoEquipoCaso.setActivo(Parametros.getEstadoInactivo());
		casoEquipoCaso.setContacto(Parametros.getMiembroContactoNo());
		casoEquipoCaso = casoEquipoCasoRepository.guardarCasoEquipoCaso(casoEquipoCaso);
		Hibernate.initialize(casoEquipoCaso.getCaso());
		return casoEquipoCaso;
	}
	
	@Transactional
	public CasoEquipoCaso guardarCasoEquipoCaso(CasoEquipoCaso casoEquipoCaso)
			throws DAOException, BusinessException {
		casoEquipoCaso = casoEquipoCasoRepository.guardarCasoEquipoCaso(casoEquipoCaso);
		return casoEquipoCaso;
	}

	public List<CasoEquipoCaso> obtenerMiembrosCaso(CasoEquipoCasoPK casoEquipoCasoPK) throws DAOException,
			BusinessException {
		List<CasoEquipoCaso> miembros = null;
		miembros = casoEquipoCasoRepository.obtenerMiembrosCaso(casoEquipoCasoPK);
		return miembros;
	}
	
	
	/**
	 *   Metodo para agregar mas miembros del caso desde el modificar.
	 */
	@Transactional
	public List<CasoEquipoCaso> agregarMiembrosEquipoCaso(List<CasoEquipoCaso> nuevosMiembros) throws DAOException, BusinessException {
		for (CasoEquipoCaso casoEquipoCaso:nuevosMiembros) {
			EquipoCaso equipoCaso = casoEquipoCaso.getEquipoCaso();
			if (casoEquipoCaso.getCasoEquipoCasoPK().getMiembro() == Integer.parseInt(Parametros.getAbogado())) {
				User usuario = new User();
				usuario.setId(equipoCaso.getIdentificacion());
				equipoCaso.setUsuario(usuario);
			}
			equipoCaso = equipoCasoService.guardarEquipoCaso(equipoCaso);
			casoEquipoCaso.setEquipoCaso(equipoCaso);
			casoEquipoCaso.getCasoEquipoCasoPK().setCodigoEquipoCaso(equipoCaso.getCodigoEquipoCaso());
			guardarCasoEquipoCaso(casoEquipoCaso);
		}
		
		return nuevosMiembros;
	}
	
	@Override
	public List<CasoEquipoCaso> obtenerAbogadosDelCaso(Integer codigoCaso) throws DAOException, BusinessException {
		return casoEquipoCasoRepository.obtenerAbogadosDelCaso(codigoCaso);
	}
	
}
