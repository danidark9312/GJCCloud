package com.gja.gestionCasos.casos.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.ResponsableTarea;
import com.gja.gestionCasos.actividades.service.ActividadCasoService;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCasoPK;
import com.gja.gestionCasos.casos.entities.Celular;
import com.gja.gestionCasos.casos.entities.Correo;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Telefono;
import com.gja.gestionCasos.casos.repository.EquipoCasoRepository;
import com.gja.gestionCasos.maestros.entities.TipoDocumento;
import com.sf.roles.UsuarioWrapper;
import com.sf.social.signinmvc.user.model.User;
import com.sf.social.signinmvc.user.repository.UserRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("equipoCasoService")
@Transactional(readOnly = true)
public class EquipoCasoServiceImpl implements EquipoCasoService{

	@Autowired
	EquipoCasoRepository equipoCasoRepository;
	@Autowired
	private ActividadCasoService actividadCasoService;
	
	@Autowired
	UserRepository userRepository;
	
	public EquipoCaso guardarEquipoCaso(EquipoCaso equipoCaso)
			throws DAOException, BusinessException {
	
		EquipoCaso equipoCasoExistente = null;
		List<Correo> correos = equipoCaso.getCorreoList();
		List<Telefono> telefonos = equipoCaso.getTelefonoList();
		List<Celular> celulares = equipoCaso.getCelularList();
		TipoDocumento tiposDocumento = equipoCaso.getTipoDocumento();
		Date fechaNacimiento = equipoCaso.getFechaNacimiento();
		String identificacion = equipoCaso.getIdentificacion();
		equipoCaso.setCorreoList(null);
		equipoCaso.setTelefonoList(null);
		equipoCaso.setCelularList(null);
		if(equipoCaso.getIdentificacion() == null){
			equipoCaso.setUsuario(null);
		} else {
			if(equipoCaso.getIdentificacion().equals("0"))
				equipoCaso.setUsuario(null);
		}
		// equipoCaso.setIdentificacion(null);
		equipoCasoExistente = equipoCasoRepository.findEquipoCasoByIdentificacion(equipoCaso.getIdentificacion());
		if (equipoCasoExistente != null) {
			equipoCaso.setCodigoEquipoCaso(equipoCasoExistente.getCodigoEquipoCaso());
			equipoCasoRepository.borrarEquipoCaso(equipoCaso);
			//equipoCaso = equipoCasoRepository.guardarEquipoCaso(equipoCaso);
//			User usuario = userRepository.findById(equipoCaso.getIdentificacion());
//			usuario.setNombre(equipoCaso.getNombre());
//			usuario.setApellido(equipoCaso.getApellido());
//			usuario.setTipoDocumento(equipoCaso.getTipoDocumento());
//			usuario.setNacimientoUsuario(equipoCaso.getFechaNacimiento());
//			usuario = userRepository.save(usuario);
			equipoCasoExistente = null;
		} else {
				equipoCaso = equipoCasoRepository.guardarEquipoCaso(equipoCaso);
		}
		
		if (correos != null) {
			for (Correo correo:correos ) {
				correo.setCodigoEquipoCaso(equipoCaso);	
			}
			equipoCaso.setCorreoList(correos);
		}
		if (telefonos != null) {
			for (Telefono telefono:telefonos) {
				telefono.setCodigoEquipoCaso(equipoCaso);
			}
			equipoCaso.setTelefonoList(telefonos);
		}
		if (celulares != null) {
			for (Celular celular:celulares) {
				celular.setCodigoEquipoCaso(equipoCaso);
			}
			equipoCaso.setCelularList(celulares);
		}
		if (tiposDocumento != null) {			
			equipoCaso.setTipoDocumento(tiposDocumento);
		}
		if (fechaNacimiento != null) {			
			equipoCaso.setFechaNacimiento(fechaNacimiento);
		}
		if (identificacion != null) {			
			equipoCaso.setIdentificacion(identificacion); 
		}
				
		equipoCaso = equipoCasoRepository.guardarEquipoCaso(equipoCaso);
		return equipoCaso;
	}


	public List<User> obtenerAbogados() throws DAOException,BusinessException{
		List<User> abogados=null;
		abogados = equipoCasoRepository.obtenerAbogado();
		return abogados;
		
	}
	
	public List<User> obtenerAbogadosSeleccionados(String codigoAbogadoSeleccionado) throws DAOException,BusinessException{
		List<User> abogados=null;
		abogados = equipoCasoRepository.obtenerAbogadosSeleccionados(codigoAbogadoSeleccionado);
		return abogados;
		
	}
	public List<Caso> obtenerCasosAbogado(User abogado) throws DAOException,BusinessException{
		return equipoCasoRepository.obtenerCasosAbogado(abogado);
	}
	public void borrarEquipoCaso(EquipoCaso equipoCaso) throws DAOException,BusinessException{
		
		equipoCasoRepository.borrarEquipoCaso(equipoCaso);
	}
	
	public List<User> obtenerAbogadosFueraDelCaso(String codigoEquipoCaso) throws DAOException,
			BusinessException {
		List<User> abogados = null;
		EquipoCaso equipoCaso = new EquipoCaso(Integer.parseInt(codigoEquipoCaso));
		equipoCaso = equipoCasoRepository.findByPK(equipoCaso);
		abogados = equipoCasoRepository.obtenerAbogadosFueraDelCaso(equipoCaso.getIdentificacion());
		return abogados;
	}
	
	
	public List<User> obtenerAbogadosIniciales(
			String obtenerAbogadosSeleccionados) throws DAOException,
			BusinessException {
		List<User> abogados = null;
		abogados = equipoCasoRepository.obtenerAbogadosIniciales(obtenerAbogadosSeleccionados);
		return abogados;
	}
	
	public EquipoCaso findEquipoCasoByIdentificacion(String identificacion)
			throws DAOException, BusinessException {
		EquipoCaso equipoCaso = equipoCasoRepository.findEquipoCasoByIdentificacion(identificacion);
		if (equipoCaso !=null){
		Hibernate.initialize(equipoCaso.getCelularList());
		Hibernate.initialize(equipoCaso.getCorreoList());
		Hibernate.initialize(equipoCaso.getTelefonoList());
		}
		return equipoCaso;
	}
	
	public EquipoCaso findByPK(EquipoCaso equipoCaso) throws DAOException,
			BusinessException {
		EquipoCaso equipoCasoReturn = equipoCasoRepository.findByPK(equipoCaso);
		Hibernate.initialize(equipoCaso.getUsuario());
		Hibernate.initialize(equipoCaso.getCelularList());	
		return equipoCasoReturn;
	}
	
	
	public User findUserByPK(String idUser) throws DAOException,
			BusinessException {
		User user = equipoCasoRepository.findUserByPK(idUser);
		return user;
	}
}