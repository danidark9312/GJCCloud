package com.gja.gestionCasos.casos.repository;

import java.util.List;

import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.CasoEquipoCasoPK;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface CasoEquipoCasoRepository {

	public CasoEquipoCaso guardarCasoEquipoCaso(CasoEquipoCaso casoEquipoCaso) throws DAOException,BusinessException;
	public CasoEquipoCaso consultarCasoEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK) throws DAOException,BusinessException;
	public void borrarCasoEquipoCaso(CasoEquipoCasoPK casoEquipoCasoPK) throws DAOException, BusinessException;
	public List<CasoEquipoCaso> obtenerMiembrosCaso(CasoEquipoCasoPK casoEquipoCasoPK) throws DAOException, BusinessException;
	public List<CasoEquipoCaso> obtenerAbogadosDelCaso(Integer codigoCaso)throws DAOException, BusinessException;
}
