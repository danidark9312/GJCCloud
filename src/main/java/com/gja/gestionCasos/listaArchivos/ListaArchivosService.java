package com.gja.gestionCasos.listaArchivos;

import java.util.List;

import com.gja.gestionCasos.actividades.entities.Archivo;
import com.gja.gestionCasos.listaArchivos.entities.ListaArchivos;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface ListaArchivosService {

	ListaArchivos guardarlistaArchivo(ListaArchivos listaGuardarArchivo) throws DAOException, BusinessException; 
	Long contarArchivosPorFiltro(String nombreArchivo) throws DAOException;
	List<ListaArchivos> encontrarArchivosPorFiltro(String nombreArchivo, int displayStart, int displayLength, String sSearch) throws DAOException;	
	Long getCantidadArchivos(String nombreArchivo, int displayStart, int displayLength, String sSearch) throws DAOException;
	ListaArchivos find(int id) throws DAOException, BusinessException;
}
