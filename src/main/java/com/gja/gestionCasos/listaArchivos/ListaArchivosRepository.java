package com.gja.gestionCasos.listaArchivos;

import java.util.List;

import com.gja.gestionCasos.listaArchivos.entities.ListaArchivos;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface ListaArchivosRepository {
	
	List<ListaArchivos> getAllArchivos(String nombreArchivo, int displayStart, int displayLength, String sSearch) throws DAOException;	
	ListaArchivos guardarArchivo(ListaArchivos listaGuardarArchivo) throws DAOException, BusinessException;
	Long getCantidadArchivos(String nombreArchivo, int displayStart, int displayLength, String sSearch) throws DAOException;
	ListaArchivos find(int id) throws DAOException, BusinessException;
}
