package com.gja.gestionCasos.maestros.repository;

import java.util.List;

import com.gja.gestionCasos.maestros.entities.AlertaTareaVencidaRol;
import com.sf.util.DAOException;

public interface MaestroEscalamientoNotificacionVencidaRepository {
 public AlertaTareaVencidaRol merge(AlertaTareaVencidaRol alertaTareaRolVencida)throws DAOException ;
 public int eliminar(AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException ;
AlertaTareaVencidaRol consultarConfiguracionVencida(AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException;
List<AlertaTareaVencidaRol> consultarNotificacionEnviarVencida(int anio, int mes, int dia) throws DAOException;
public Integer update(AlertaTareaVencidaRol alertaTareaVencidaRol) throws DAOException;
 
}
