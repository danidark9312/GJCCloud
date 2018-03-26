package com.gja.gestionCasos.casos.repository;

import java.util.List;

import javax.persistence.Query;

import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface CasoRepository {
	Caso findByPK(Caso caso) throws DAOException;
	Long getCountFilter(CasoFiltro caso);
	List<Caso> encontrarCasoPorFiltro(CasoFiltro codigoCaso);
	List<User> consultarCorreoAdmind ();
	public String getNombreCaso(Integer codigoCaso) throws DAOException;
	public List<Caso> obtenerCasosAudiencias(CasoFiltro casoFiltro) throws DAOException;
	public Long getCountAudiencias(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public String getEstadoCaso(Caso caso) throws DAOException;
	public List<Object[]> obtenerAudienciasExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public List<Object[]> obtenerCaducidadCasos(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public Query getQueryCaducidadCaso(CasoFiltro casoFiltro, boolean isCountSelect) throws DAOException, BusinessException;
	public Long getCountCaducidadCasos(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public List<Object[]> obtenerCaducidadCasosExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public List<Object[]> obtenerDocumentosPendientes(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public List<Object[]> obtenerDocumentosPendientesExcel(CasoFiltro casoFiltro) throws DAOException, BusinessException;
	public Long getCountDocumentosPendientes(CasoFiltro caso) throws DAOException, BusinessException;
	public List<Object[]> consultarCasosPorTipoCaso(EquipoCaso equipoCaso) throws DAOException,BusinessException;
	public List<Object[]> consultarCasosActividadesPrincipales(EquipoCaso equipoCaso) throws DAOException,BusinessException; 
	public List<Object[]> consultarCasosPorEstadoCaso(EquipoCaso equipoCaso) throws DAOException,BusinessException;
	public List<Object[]> getDemandadosCasoString(String codigoCaso) throws DAOException,BusinessException;
	public List<Object[]> consultarNombresCasos(EquipoCaso equipoCaso) throws DAOException,BusinessException;
}
