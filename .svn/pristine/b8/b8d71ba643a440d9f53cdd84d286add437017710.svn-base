package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.gja.gestionCasos.actividades.entities.TareaParticularCaso;
import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.gja.gestionCasos.maestros.entities.AlertaTareaVencidaRol;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface MaestroEscalamientoNotificacionService {
	
	public JSONObject guardarEscalamientoNotificacionDetelle( List<AlertaTareaRol> listRolTareas) throws BusinessException, DAOException;
	public  Integer eliminarNotificacionesRoles(Integer resultadoDetalle, AlertaTareaRol alertaRol) throws DAOException, BusinessException;
	public  int eliminarNotificacionesRolesDetalle(AlertaTareaRol alertaRol) throws DAOException, BusinessException;
	AlertaTareaVencidaRol guardarEscalamientoNotificacionVencida(AlertaTareaVencidaRol alertaTareaRol,TareaParticularCaso objTareaParticular)	throws BusinessException, DAOException;
	public JSONObject guardarEscalamientoNotificacionDetelleVencido(List<AlertaTareaVencidaRol> listRolTareas) throws BusinessException, DAOException ;
	public  Integer eliminarNotificacionesRolesVencido(Integer resultadoDetalle,AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException, BusinessException;
	public  int eliminarNotificacionesRolesDetalleVencido(AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException, BusinessException;
	public AlertaTareaRol guardarEscalamientoNotificacion(AlertaTareaRol alertaTareaRol,TareaParticularCaso objTareaParticular) throws BusinessException, DAOException;
	JSONObject consultarConfigiracionAlertaProximaAVencer(AlertaTareaRol alertaTareaRol) throws BusinessException, DAOException;
	JSONObject consultarConfigiracionVencida(AlertaTareaVencidaRol alertaTareaVencidaRol)		throws BusinessException, DAOException;
	public List<AlertaTareaRol> consultarNotificacionesAbiertas() throws BusinessException;
	public  List<Object>  getUsuarioByRol (Rol rol,TipoCaso tipo)  throws BusinessException, DAOException;
	public List<AlertaTareaVencidaRol> consultarNotificacionesVencidas() throws BusinessException, DAOException;
	public Integer updateNotificacion(AlertaTareaRol alertaTareaRol)throws BusinessException, DAOException;
	public Integer updateNotificacionVencida(AlertaTareaVencidaRol alertaTareaRol)throws BusinessException, DAOException;
}
