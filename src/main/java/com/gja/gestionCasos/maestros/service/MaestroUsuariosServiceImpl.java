package com.gja.gestionCasos.maestros.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gja.gestionCasos.administradores.repository.ChangePasswordRepository;
import com.gja.gestionCasos.casos.entities.CasoEquipoCaso;
import com.gja.gestionCasos.casos.entities.Celular;
import com.gja.gestionCasos.casos.entities.Correo;
import com.gja.gestionCasos.casos.entities.EquipoCaso;
import com.gja.gestionCasos.casos.entities.Telefono;
import com.gja.gestionCasos.casos.repository.EquipoCasoRepository;
import com.gja.gestionCasos.casos.repository.EquipoCasoRepositoryImpl;
import com.gja.gestionCasos.maestros.entities.EstadoUsuario;
import com.gja.gestionCasos.maestros.entities.TipoDocumento;
import com.gja.gestionCasos.maestros.repository.MaestroActividadesRepository;
import com.gja.gestionCasos.maestros.repository.MaestroRolesRepositoryImpl;
import com.gja.gestionCasos.maestros.repository.MaestroTareaActividadRepositoryImpl;
import com.gja.gestionCasos.maestros.repository.MaestroUsuariosRepositoryImpl;
import com.gja.gestionCasos.maestros.repository.RolesUsuariosRepositoryImpl;
import com.sf.roles.Rol;
import com.sf.roles.RolesUsuarios;
import com.sf.social.signinmvc.user.model.ChangePassword;
import com.sf.social.signinmvc.user.model.ChangePasswordPK;
import com.sf.social.signinmvc.user.model.Role;
import com.sf.social.signinmvc.user.model.User;
import com.sf.util.BusinessException;
import com.sf.util.DAOException;
import com.sf.util.Parametros;

@Service("maestroUsuariosService")
@Transactional(readOnly = true)
public class MaestroUsuariosServiceImpl implements MaestroUsuariosService {
	@Autowired
	MaestroActividadesRepository maestroActividadesRepository;
	@Autowired
	MaestroRolesRepositoryImpl maestroRolRepository;
	@Autowired
	MaestroTareaActividadRepositoryImpl maestroTareaActividadRepository;
	@Autowired
	MaestroUsuariosRepositoryImpl maestroUsuariosRepository;
	@Autowired
	RolesUsuariosRepositoryImpl rolesUsuariosRepository;
	@Autowired
	ChangePasswordRepository changePasswordRepository;
	@Autowired
	private EquipoCasoRepository equipoCasoRepository;
	
	public SecureRandom random = new SecureRandom();
	public boolean existIdUsuario(User usuario) throws BusinessException, DAOException {

		return maestroUsuariosRepository.existIdUsuario(usuario.getId());
	}
	
	public boolean existCorreoUsuario(User usuario) throws BusinessException, DAOException {
		return maestroUsuariosRepository.existCorreoUsuario(usuario.getEmail());
	}
	@Transactional
	public ChangePassword existCorreoUsuarioToken(User usuario) throws BusinessException, DAOException {
		boolean tempExisteCorreo= false;
		ChangePassword informacion = new ChangePassword();
		String token="";
		tempExisteCorreo = maestroUsuariosRepository.existCorreoUsuario(usuario.getEmail());
		if (tempExisteCorreo==true) {
			token = new BigInteger(130, random).toString(32);
			ChangePasswordPK pk= new ChangePasswordPK();
			pk.setEmail(usuario.getEmail());
			pk.setToken(token);
			informacion.setChangePasswordPK(pk);
			informacion.setUsado(Parametros.getTokenNoUsado());
			informacion = changePasswordRepository.save(informacion);
		}
		return informacion;

	}

	public boolean existNameRol(Rol rol) throws DAOException, BusinessException {

		return maestroRolRepository.existNameRol(rol.getDescripcion());
	}

	public User findByPK(User usuario) throws DAOException, BusinessException {

		return maestroUsuariosRepository.findByPK(usuario);
	}

	public List<EstadoUsuario> getEstados() throws DAOException, BusinessException {

		return maestroUsuariosRepository.getEstados();
	}

	public List<RolesUsuarios> getRolesUsuarios() throws BusinessException, DAOException {

		return rolesUsuariosRepository.getRolesUsuarios();
	}

	public List<User> getUsuarios(String activo, String rol, String sSearch, int displayStart, int displayLength) throws BusinessException, DAOException {

		return maestroUsuariosRepository.getUsuarios(activo, rol, sSearch, displayStart, displayLength);
	}
	
	public Long getCountUsuario(String activo, String rol, String sSearch) throws BusinessException, DAOException {

		return maestroUsuariosRepository.getCountUsuario(activo, rol, sSearch);
	}

	@Transactional
	public User saveNewUsuario(User usuario, RolesUsuarios rolesUsuarios) throws BusinessException, DAOException {
		if(validarNoEmail(usuario.getEmail())) {
			throw new BusinessException("Email invalido");
		}
		List<RolesUsuarios> rolesUsuariosList = new ArrayList<RolesUsuarios>();
		usuario.setEmail(usuario.getEmail());
		usuario.setActivo((usuario.getActivo().toLowerCase().equals(Parametros.getParametroActivo().toLowerCase())
						? Parametros.getParametroSiCorto() : Parametros.getParametroNoCorto()));
		usuario.setRole(Role.ROLE_SUPER_USER);
		usuario.setPassword(encodePassword(usuario.getPassword()));

		/**
		 * No necesito devolver el rolUsuario si guarda el rol basta con
		 * retornar el usuario guardado
		 */
		usuario = maestroUsuariosRepository.persist(usuario);
		rolesUsuarios = rolesUsuariosRepository.persist(rolesUsuarios);
		rolesUsuariosList.add(rolesUsuarios);
		usuario.setRolesUsuariosList(rolesUsuariosList);
		if(usuario.getEquipoCasoSet() != null) {
			if (usuario.getRolesUsuariosList() != null && !usuario.getRolesUsuariosList().isEmpty() && usuario.getRolesUsuariosList().get(0).getRolesUsuariosPK().getCodigoRol() == Integer.parseInt(Parametros.getRolAbogado()))
				guardarUsuarioEnEquipoCaso(usuario);
		}
		return usuario;
	}
	
	private void numeroEquipoCaso() throws DAOException, BusinessException{
		
	}
	
	private void guardarUsuarioEnEquipoCaso(User usuario) throws DAOException, BusinessException{
		EquipoCaso equipoCaso = new EquipoCaso();
		List<Telefono> telefonos =  new ArrayList<Telefono>();
		List<Celular> celulares = new ArrayList<Celular>();
		List<Correo> correos =  new ArrayList<Correo>();
		if (usuario.getEquipoCasoSet() == null) {
			equipoCaso.setNombre(usuario.getNombre());
			equipoCaso.setApellido(usuario.getApellido());
			equipoCaso.setIdentificacion(usuario.getId());
			equipoCaso.setCelularList(celulares);
			equipoCaso.setTelefonoList(telefonos);
			equipoCaso.setCorreoList(correos);
			usuario.setNumeroTarjetaProfesional(usuario.getNumeroTarjetaProfesional());
//			usuario.setId(usuario.getId());
			equipoCasoRepository.guardarEquipoCaso(equipoCaso);
		} else if (!usuario.getEquipoCasoSet().isEmpty()) {
			List<EquipoCaso> equipoCasoList = usuario.getEquipoCasoSet();
			equipoCaso = equipoCasoList.get(0);
			
			equipoCasoRepository.borrarEquipoCaso(equipoCaso);
			
			celulares.add(new Celular(usuario.getCelular(), equipoCaso.getCodigoEquipoCaso()));
			
			telefonos.add(new Telefono(usuario.getNumeroTelefono(), equipoCaso.getCodigoEquipoCaso()));
			
			correos.add(new Correo(usuario.getEmail(), equipoCaso.getCodigoEquipoCaso()));
			equipoCaso.setCelularList(celulares);
			equipoCaso.setTelefonoList(telefonos);
			equipoCaso.setCorreoList(correos);		
			equipoCaso.setNombre(usuario.getNombre());
			equipoCaso.setApellido(usuario.getApellido());
			equipoCaso.setIdentificacion(usuario.getId());
			equipoCaso.setUsuario(usuario);
			equipoCaso.setTipoDocumento(usuario.getTipoDocumento());
			equipoCaso.setFechaNacimiento(usuario.getNacimientoUsuario());			
			for (CasoEquipoCaso casoEquipoCaso:equipoCaso.getCasoEquipoCasoSet()) {
				casoEquipoCaso.setDireccion(usuario.getDireccion());
			}
			usuario.setNumeroTarjetaProfesional(usuario.getNumeroTarjetaProfesional());
	//		usuario.setId(usuario.getId());
			equipoCaso = equipoCasoRepository.guardarEquipoCaso(equipoCaso);
		}
	}
	
	@Transactional
	public User saveUsuario(User usuario, String contrasenaNueva) throws BusinessException, DAOException {
		usuario = findByPK(usuario);
		usuario.setPassword(encodePassword(contrasenaNueva));
		usuario = maestroUsuariosRepository.merge(usuario);
		if(usuario.getRolesUsuariosList() != null && !usuario.getRolesUsuariosList().isEmpty() && usuario.getRolesUsuariosList().get(0).getRolesUsuariosPK().getCodigoRol() == Integer.parseInt(Parametros.getAbogado())){
			guardarUsuarioEnEquipoCaso(usuario);
		}
		return usuario;
	}
	
	@Transactional
	public User saveUsuario(User usuario, RolesUsuarios rolesUsuarios) throws BusinessException, DAOException {
		User usuarioGuardar = new User();
		
		usuarioGuardar = findByPK(usuario);
		if(!usuarioGuardar.getEquipoCasoSet().isEmpty()){
			throw new BusinessException("El usuario no puede inactivarse ya que existe en el equipo del caso.");
		}
		else{
			usuarioGuardar.setActivo((usuario.getActivo().toLowerCase().equals(Parametros.getParametroActivo().toLowerCase())
					? Parametros.getParametroSiCorto() : Parametros.getParametroNoCorto()));
		}
		usuarioGuardar.setApellido(usuario.getApellido());
		usuarioGuardar.setEmail(usuario.getEmail());
		usuarioGuardar.setId(usuario.getId());
		usuarioGuardar.setNombre(usuario.getNombre());
		usuarioGuardar.setNumeroTarjetaProfesional(usuario.getNumeroTarjetaProfesional());
		usuarioGuardar.setNumeroTelefono(usuario.getNumeroTelefono());
		usuarioGuardar.setCelular(usuario.getCelular());
		usuarioGuardar.setDireccion(usuario.getDireccion());
		usuarioGuardar.setAbreviacionAbogado(usuario.getAbreviacionAbogado());
		usuarioGuardar.setTipoDocumento((usuario.getTipoDocumento()));
		usuarioGuardar.setNacimientoUsuario(usuario.getNacimientoUsuario());
		/**
		 * No necesito devolver el rolUsuario si guarda el rol basta con
		 * retornar el usuario guardado
		 */
		
		usuario = maestroUsuariosRepository.merge(usuarioGuardar);
//		usuario.getEquipoCasoSet().get(0);  // Devuelve equipo del caso
				
		if (usuarioGuardar.getRolesUsuariosList().get(0).getRolesUsuariosPK().getCodigoRol() != rolesUsuarios.getRolesUsuariosPK().getCodigoRol())
			rolesUsuariosRepository.borrarRolUsuario(usuario.getId());
		
		rolesUsuarios = rolesUsuariosRepository.merge(rolesUsuarios);
		
		if (usuario.getEquipoCasoSet() != null) {
			if (usuario.getRolesUsuariosList() != null && !usuario.getRolesUsuariosList().isEmpty() 
					&& usuario.getRolesUsuariosList().get(0).getRolesUsuariosPK().getCodigoRol() == Integer.parseInt(Parametros.getRolAbogado())
					|| usuario.getRolesUsuariosList().get(0).getRolesUsuariosPK().getCodigoRol() == Integer.parseInt(Parametros.getRolAsociado())
					|| usuario.getRolesUsuariosList().get(0).getRolesUsuariosPK().getCodigoRol() == Integer.parseInt(Parametros.getRolDependiente())
					|| usuario.getRolesUsuariosList().get(0).getRolesUsuariosPK().getCodigoRol() == Integer.parseInt(Parametros.getRolSocio())){

					guardarUsuarioEnEquipoCaso(usuario);
			}
		}
		
		return usuario;
	}

		
	private String encodePassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		
		return passwordEncoder.encode(password);
    }
	
	@SuppressWarnings("unused")
	private boolean validarNoEmail(String email) {
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		//TODO si en pruebas los emails no presentan errores se pueden eliminar patterns 2 y 3
		String EMAIL_PATTERN_2 = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		String EMAIL_PATTERN_3 = "^(.+)@(.+)$";
		Pattern pattern;
		
		pattern = Pattern.compile(EMAIL_PATTERN);
		
		return !pattern.matcher(email).matches();
	}
	
//	public String verificarEmail(String email) throws BusinessException,
//			DAOException {
//		maestroUsuariosRepository.verificarEmail(email);
//		return null;
//	}
	
	public String obtenerIdPorEmail(String email) throws BusinessException,
			DAOException {
		String id = maestroUsuariosRepository.obtenerIdPorEmail(email);
		return id;
	}

}
