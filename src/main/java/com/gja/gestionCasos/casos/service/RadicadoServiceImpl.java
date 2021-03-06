package com.gja.gestionCasos.casos.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.casos.entities.Radicado;
import com.gja.gestionCasos.casos.entities.RadicadoAcumulado;
import com.gja.gestionCasos.casos.entities.RadicadoPK;
import com.gja.gestionCasos.casos.repository.RadicadoRepository;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

@Service("RadicadoService")
@Transactional(readOnly = true)
public class RadicadoServiceImpl implements RadicadoService{
	
	@Autowired
	RadicadoRepository radicadoRepository;
	
	@Transactional
	public Radicado obtenerRadicado(RadicadoPK radicadoPK) throws DAOException, BusinessException {
		
		Radicado radicado = radicadoRepository.obtenerRadicado(radicadoPK);
		if(radicado!=null){
			
			Hibernate.initialize(radicado.getInstancia());
			Hibernate.initialize(radicado.getRadicadoAcumulado());
		}
		
		return radicado;
	}
	@Transactional
	public Radicado obtenerRadicado(String codigoRadicado) throws DAOException, BusinessException {
		
		Radicado radicado = radicadoRepository.obtenerRadicado(codigoRadicado);
		if(radicado!=null){
			
			Hibernate.initialize(radicado.getInstancia());
			Hibernate.initialize(radicado.getRadicadoAcumulado());
		}
		
		return radicado;
	}
	
	public List<Radicado> findAll() throws DAOException, BusinessException {
		List<Radicado> resultados = radicadoRepository.findAll();
		return resultados;
	}

	@Transactional
	public List<Radicado> modificarRadicados(List<Radicado> radicados, String numeroRadicadoActualizar)
			throws DAOException, BusinessException {
		
		
		if (radicados != null) {
			for (Radicado radicado:radicados) {
				Radicado existeRadicado = obtenerRadicado(radicado.getRadicadoPK());
				if (existeRadicado != null) {
					if (existeRadicado.getRadicadoPK().getCodigoRadicado().equals(numeroRadicadoActualizar)) {
						radicado.setFechaUltimaModificacion(new Date());
						existeRadicado.setInstancia(radicado.getInstancia());
						existeRadicado.setFechaUltimaModificacion(new Date());
						existeRadicado.setUsuarioUltimaModificacion(radicado.getUsuarioUltimaModificacion());
						existeRadicado.setRadicadosAcumulados(radicado.getRadicadosAcumulados());
						radicado = existeRadicado;						
					} else{
						if (existeRadicado.getRadicadoPK().getCodigoRadicado().equals(existeRadicado.getRadicadoPK().getCodigoRadicado())){
							radicado = radicadoRepository.obtenerRadicado(radicado.getRadicadoPK());
							radicado.setActivo(Parametros.getCodigoRadicadoActivoSi());
							radicado = radicadoRepository.guardarRadicado(radicado);
						}
//						throw new BusinessException("Error el n\u00FAmero de radicado ya existe para este caso");
					}
				} else {
					if (!numeroRadicadoActualizar.isEmpty()) {						
						RadicadoPK radicadoPK = new RadicadoPK();
						radicadoPK.setCodigoRadicado(numeroRadicadoActualizar);
						radicadoPK.setCodigoCaso(radicado.getRadicadoPK().getCodigoCaso());
						existeRadicado = obtenerRadicado(radicadoPK);
						radicadoRepository.eliminarRadicado(existeRadicado);
						radicado.setFechaCreacion(new Date());
						radicado.setFechaUltimaModificacion(new Date());
					} else {
						radicado.setFechaCreacion(new Date());
						radicado.setFechaUltimaModificacion(new Date());
						if(radicado.getRadicadosAcumulados()!=null && radicado.getRadicadosAcumulados().size()>0) {
							for(RadicadoAcumulado acumulado : radicado.getRadicadosAcumulados()) {
								acumulado.setRadicado(radicado);
								acumulado.getRadicadoPK().setRadicado(radicado);
							}	
						}
						
					}
				}
				radicado = radicadoRepository.guardarRadicado(radicado);
			}
		}
		return radicados;
	}
	
	@Transactional
	public Radicado eliminarRadicado(Radicado radicado) throws DAOException, BusinessException {
		radicado = radicadoRepository.obtenerRadicado(radicado.getRadicadoPK());
		radicado.setActivo(Parametros.getCodigoRadicadoActivoNo());
		radicado = radicadoRepository.guardarRadicado(radicado);
		return radicado;
	}
	
	@Transactional
	public Radicado guardarRadicado(Radicado radicado) throws DAOException, BusinessException {
		radicado = radicadoRepository.guardarRadicado(radicado);
		return radicado;
	}
}
