package com.gja.gestionCasos.maestros.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.actividades.entities.Actividad;
import com.gja.gestionCasos.actividades.entities.ActividadTipoCaso;
import com.gja.gestionCasos.actividades.entities.TareaActividad;
import com.gja.gestionCasos.filters.TipoCasoFiltro;
import com.gja.gestionCasos.maestros.entities.TipoCaso;
import com.gja.gestionCasos.maestros.repository.ActividadTipoCasoRepository;
import com.gja.gestionCasos.maestros.repository.ActividadTipoCasoRepositoryImpl;
import com.gja.gestionCasos.maestros.repository.TipoCasoRepository;
import com.gja.gestionCasos.maestros.repository.TipoCasoRepositoryImpl;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;




@Service("tipoCasoService")
@Transactional(readOnly = true)

public class TipoCasoServiceImpl implements TipoCasoService {

	
	private static final Logger LOG = Logger
			.getLogger(PaisServiceImpl.class);
	@Autowired
	TipoCasoRepositoryImpl tipoCasoRepository;
	
	@Autowired
	ActividadTipoCasoRepositoryImpl actividadTipoCasoRepository;
	
	public List<TipoCaso> obtenerTipoCaso()throws DAOException, BusinessException
	{
		List<TipoCaso> tipoDeCaso=null;
		
		tipoDeCaso=tipoCasoRepository.obtenerTipoCaso();
		
		return tipoDeCaso;
		
	}
	
	public Long getCountFilter(TipoCasoFiltro tipoCaso) throws DAOException {
		Long cantidad = tipoCasoRepository.getCountFilter(tipoCaso);
		return cantidad;
	}
	
	public List<TipoCaso> getTipoCasoByFilter(TipoCasoFiltro tipoCaso)
			throws DAOException,BusinessException {
				List<TipoCaso> tipoCasoes = tipoCasoRepository.getTipoCasoByFilter(tipoCaso);
				return tipoCasoes;
	}
	
	@Transactional
	public TipoCaso guardarTipoCaso(TipoCaso tipoCaso)
			throws DAOException, BusinessException {
			List<ActividadTipoCaso> actividades=tipoCaso.getActividadTipoCasoList();
			tipoCaso.setActividadTipoCasoList(null);
			
			if (tipoCaso.getCodigo()!=null){
				actividadTipoCasoRepository.deleteByTipoCaso(tipoCaso.getCodigo());
			}
			
			tipoCaso=tipoCasoRepository.merge(tipoCaso);
			//actividad.setTareaActividadList(tareas);
			for (ActividadTipoCaso objActividadTipoCaso: actividades){
				objActividadTipoCaso.setCdtipocaso(tipoCaso);
				actividadTipoCasoRepository.merge(objActividadTipoCaso);
			}
		
			
			return tipoCaso;
	}
	
	@Transactional
	public int eliminarTipoCaso(TipoCaso tipoCaso)throws DAOException, BusinessException{
		tipoCaso=tipoCasoRepository.findByPK(tipoCaso);
		tipoCaso.setActivo("N");
		tipoCasoRepository.merge(tipoCaso);
		return 1;
	}

	public TipoCaso findByPK(TipoCaso tipoCaso) throws DAOException,
	BusinessException {
		TipoCaso tipoCasoByPK = tipoCasoRepository.findByPK(tipoCaso);
		return tipoCasoByPK;
	}
	
	public Long getCountByActividad(ActividadTipoCaso actividadTipoCaso) throws DAOException,
	BusinessException {
		Long cantidad = tipoCasoRepository.getCountByActividadTipoCasoActivo(actividadTipoCaso.getCdactividad().getCdactividad());
		return cantidad;
	}
	
	public boolean existNameTipoCaso(TipoCaso tipoCaso) throws DAOException,
	BusinessException {
		boolean existe = tipoCasoRepository.existNameTipoCaso(tipoCaso.getNombre());
		return existe;
	}
	
	public Integer consultarCodigoTipoCaso(TipoCaso tipoCaso) throws DAOException,
	BusinessException {
		Integer tipoCasoByPK = tipoCasoRepository.obtenerCodigoTipoCaso(tipoCaso);
		return tipoCasoByPK;
	}
	
	public   TipoCaso obtenerActividades(TipoCaso tipoCaso) throws DAOException,
	BusinessException {
		TipoCaso tipoCasoByPK = tipoCasoRepository.obtenerActividades(tipoCaso);
		return tipoCasoByPK;
	}
	
	
	@SuppressWarnings("unchecked")
	public JSONObject getJsonMostrarTiposCasos(List<TipoCaso> tiposCasos,int sEcho,int cantidad) throws BusinessException{
		JSONObject jsO = new JSONObject();
		JSONObject res = new JSONObject();
		try {
			JSONArray arrayTiposCasos = new JSONArray();
			for (TipoCaso tipoCaso : tiposCasos) {
				jsO = new JSONObject();
				jsO.put("codigoTipoCaso", tipoCaso.getCodigo());
				jsO.put("nombreTipoCaso", tipoCaso.getNombre());
				jsO.put("papelera",
						"<a  data-toggle='modal'  class='btn btn-success btn-circle .btn-xs' style='background-color: red; border:0;' onclick='eliminarTipoCaso("
								+ tipoCaso.getCodigo()
								+ ")' title='Eliminar'><i class='glyphicon glyphicon-trash' style='color: white;'></i></a>");
				jsO.put("accion",
						"<a    class='btn btn-primary  btn-circle .btn-xs'  onclick='cargarModalEscalamientoNotificacion("
								+ tipoCaso.getCodigo()+",\""+tipoCaso.getNombre()+"\")' title='Configurar Alertas'><i class='glyphicon glyphicon-cog' style='color: white;'></i></a>");
				//Aca se coloca si el estado es activo o no
					if(tipoCaso.getActivo().equals("S")) {
						jsO.put("estado", "Activo");				
					}
					else {
						jsO.put("estado", "Inactivo");
					}
				arrayTiposCasos.add(jsO);
				
			}
			res.put("sEcho", sEcho);
			res.put("iTotalRecords", cantidad);
			res.put("iTotalDisplayRecords", cantidad);
			res.put("aaData", arrayTiposCasos);
			res.put("STATUS", "SUCCESS");
		} catch (Exception e) {
			LOG.error("Error; craeando el Json de respuesta de los tipos de Casos a mostrar");
		}
		return res;
	}
	
	
}
