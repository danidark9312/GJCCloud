package com.gja.gestionCasos.maestros.service;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.repository.RolAccionOpcionRepository;
import com.sf.roles.Rol;
import com.sf.roles.RolAccionOpcion;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;



@Service("rolAccionOpcionService")
public class RolAccionOpcionServiceImpl implements RolAccionOpcionService{
	
	private static final Logger LOG = Logger
			.getLogger(RolAccionOpcionServiceImpl.class);
	
	@Autowired
	RolAccionOpcionRepository rolAccionOpcionRepository;

	@Override
	@Transactional
	public JSONObject guardar(Rol rol) throws BusinessException, DAOException {
		JSONObject root = new JSONObject();
			try {
				if(rol != null && null != rol.getRolAccionOpcionList() && !rol.getRolAccionOpcionList().isEmpty()){
					for (RolAccionOpcion objRolAccionOperacion : rol.getRolAccionOpcionList()){
						objRolAccionOperacion.getRolAccionOpcionPK().setCodigoRol(rol.getCodigo());
						objRolAccionOperacion.setRol(rol);
						rolAccionOpcionRepository.merge(objRolAccionOperacion);	
					}
				}
			} catch (Exception e) {
				LOG.error("Error; guardando las opciones del ROL");
			}
		return root;
	}



}
