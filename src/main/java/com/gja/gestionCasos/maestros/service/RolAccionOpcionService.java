package com.gja.gestionCasos.maestros.service;

import org.json.simple.JSONObject;

import com.sf.roles.Rol;

import com.sf.util.BusinessException;
import com.sf.util.DAOException;



public interface RolAccionOpcionService {

	JSONObject guardar(Rol rol) throws BusinessException, DAOException;
  
}
