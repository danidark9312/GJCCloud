package com.gja.gestionCasos.casos.repository;

import java.util.List;

import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface EquipoCasoRepository {

	public EquipoCaso guardarEquipoCaso(EquipoCaso equipoCaso) throws DAOException,BusinessException;
	public List<User> obtenerAbogado() throws DAOException,BusinessException;
	public List<User> obtenerAbogadosSeleccionados(String codigoAbogadoSeleccionado) throws DAOException,BusinessException;
	public void borrarEquipoCaso(EquipoCaso equipoCaso) throws DAOException,BusinessException;
	public List<User> obtenerAbogadosFueraDelCaso(String codigoCaso) throws DAOException,BusinessException;
	public EquipoCaso findByPK(EquipoCaso equipoCaso) throws DAOException;
	public List<User> obtenerAbogadosIniciales(String obtenerAbogadosSeleccionados) throws DAOException,BusinessException;
	public EquipoCaso findEquipoCasoByIdentificacion(String identificacion) throws DAOException,BusinessException;
	public User findUserByPK(String idUser) throws DAOException,BusinessException;
	List<Caso> obtenerCasosAbogado(User abogado) throws DAOException, BusinessException;
}
