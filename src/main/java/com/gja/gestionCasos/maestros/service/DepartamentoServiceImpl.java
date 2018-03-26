package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.maestros.entities.Ciudad;
import com.gja.gestionCasos.maestros.entities.Departamento;
import com.gja.gestionCasos.maestros.entities.Pais;
import com.gja.gestionCasos.maestros.repository.DepartamentoRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

@Service("DepartamentoService")
@Transactional(readOnly = true)

public class DepartamentoServiceImpl implements DepartamentoService {

	@Autowired
	DepartamentoRepositoryImpl departamentoRepository;
	
	public List<Departamento> departamentosPorPais(Pais pais)
			throws DAOException, BusinessException {
			
			List<Departamento> departamento =null;
			try {
				departamento = departamentoRepository.obtenerDepartamentoPorPaises(pais);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		
			
		return departamento;
	}


	public Departamento  consultarCodigoDepartamento(Departamento departamento) throws DAOException{
		Departamento departamentoByPK = null;
		departamentoByPK =  departamentoRepository.consultarCodigoDepartamento(departamento);
		return departamentoByPK;
	}

	
	
}
