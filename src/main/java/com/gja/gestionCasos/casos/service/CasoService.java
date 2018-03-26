package com.gja.gestionCasos.casos.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONArray;

import com.gja.gestionCasos.actividades.repository.ListaActividades;
import com.gja.gestionCasos.casos.entities.Caso;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.ListaPrestamos;
import com.gja.gestionCasos.filters.CasoFiltro;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.gja.gestionCasos.reportes.entities.Justificacion;
import com.sf.social.signinmvc.security.dto.SocialUserDetails;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;

public interface CasoService {
	Caso guardarCaso(Caso caso, ListaPrestamos prestamos, ListaActividades actividades) throws DAOException, BusinessException; 
    //Caso guardarCaso(Caso caso) throws DAOException, BusinessException;
	Caso findByPK(Caso caso)  throws DAOException, BusinessException;
	Long getCountFilter(CasoFiltro caso);
	List<Caso> encontrarCasoPorFiltro(CasoFiltro casoFiltro) throws DAOException;
	//public void obtenerCodigoEquipo(Caso caso,List<TareaParticularCaso>tareas);
	public JSONArray obtenerCodigoEquipo(Caso caso);
	public Long getCountByTipoCasoCaso(TipoCaso tipoCaso) throws DAOException, BusinessException;
	public String getNombreCaso(Integer codigoCaso) throws DAOException, BusinessException;
	
	Caso modificarEstadoCaso(Caso caso,Justificacion justificacion, HttpServletRequest request) throws DAOException,BusinessException,MessagingException;
	Caso modificarDetalleCaso(Caso caso) throws DAOException,BusinessException;
	List<User> consultarCorreoAdmind () throws DAOException,BusinessException;
	public List<Object[]> consultarCasosPorTipoCaso(EquipoCaso equipoCaso) throws DAOException,BusinessException;
	public List<Object[]> consultarCasosActividadesPrincipales(EquipoCaso equipoCaso) throws DAOException,BusinessException; 
	public List<Object[]> consultarCasosPorEstadoCaso(EquipoCaso equipoCaso) throws DAOException,BusinessException;
	public String getDemandadosCasoString(String codigoCaso) throws DAOException,BusinessException;
	public List<Object[]> consultarNombresCasos(EquipoCaso equipoCaso) throws DAOException,BusinessException;
	void enviarCorreoCambioFechaCaducidad(Caso caso, SocialUserDetails user)
			throws DAOException, BusinessException, MessagingException;
}
