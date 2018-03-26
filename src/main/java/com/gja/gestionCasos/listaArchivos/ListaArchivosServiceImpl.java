package com.gja.gestionCasos.listaArchivos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.Archivo;
import com.gja.gestionCasos.listaArchivos.entities.ListaArchivos;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("listaArchivosService")
@Transactional(readOnly = true)
public class ListaArchivosServiceImpl implements ListaArchivosService{

	
	@Autowired
	private JavaMailSender mailSender;	
	@Autowired
	ListaArchivosRepository listaArchivosRepository;
	
	@Transactional
	public ListaArchivos guardarlistaArchivo(ListaArchivos listaGuardarArchivo) throws DAOException,
		BusinessException {			
		return listaArchivosRepository.guardarArchivo(listaGuardarArchivo);
}
	public ListaArchivos find(int id) throws DAOException,
	BusinessException {			
		return listaArchivosRepository.find(id);
	}
	
	@Override
	public Long contarArchivosPorFiltro(String nombreArchivo) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ListaArchivos> encontrarArchivosPorFiltro(String nombreArchivo, int displayStart, int displayLength, String sSearch) throws DAOException {
		return listaArchivosRepository.getAllArchivos(nombreArchivo, displayStart, displayLength, sSearch);
	}

	@Override
	public Long getCantidadArchivos(String nombreArchivo, int displayStart,	int displayLength, String sSearch) throws DAOException {
		return listaArchivosRepository.getCantidadArchivos(nombreArchivo, displayStart, displayLength, sSearch);		
	}
}
