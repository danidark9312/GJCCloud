package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.AlertaTareaRol;
import com.sf.util.DAOException;

public interface MaestroEscalamientoNotificacionRepository {
 public AlertaTareaRol merge(AlertaTareaRol alertaTareaRol)throws DAOException ;
 public int eliminar( AlertaTareaRol alertaRol) throws DAOException ;
 public AlertaTareaRol consultarConfiguracion (AlertaTareaRol alertaTareaRol) throws DAOException ;
List<AlertaTareaRol> consultarNotificacionEnviar(int anio, int mes, int dia ) throws DAOException;
public Integer update(AlertaTareaRol alertaTareaRol) throws DAOException ;
 
}
