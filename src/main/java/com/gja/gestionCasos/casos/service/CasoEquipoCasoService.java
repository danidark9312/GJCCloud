package com.gja.gestionCasos.casos.service;

import java.util.List;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCasoPK;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface CasoEquipoCasoService {

	public CasoEquipoCaso consultarCasoEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK)throws DAOException,BusinessException;
	public CasoEquipoCaso modificarMiembroEquipoCaso(CasoEquipoCaso casoEquipoCaso, CasoEquipoCasoPK casoEquipoCasoPK,String nombreFoto)throws DAOException,BusinessException;
	public CasoEquipoCaso inactivarEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK)throws DAOException,BusinessException;
	public CasoEquipoCaso guardarCasoEquipoCaso(CasoEquipoCaso casoEquipoCaso)throws DAOException,BusinessException;
	public List<CasoEquipoCaso> obtenerMiembrosCaso(CasoEquipoCasoPK casoEquipoCasoPK)throws DAOException,BusinessException;
	public List<CasoEquipoCaso> agregarMiembrosEquipoCaso(List<CasoEquipoCaso> nuevosMiembros)throws DAOException,BusinessException;
	public List<CasoEquipoCaso> obtenerAbogadosDelCaso(Integer codigoCaso)throws DAOException, BusinessException;
	
}
