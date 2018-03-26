var opcionVista = '';
var permisoLectura = '';
var permisoEscritura = '';
var permisoCrear = '';
var permisoEliminar = '';
var permisoRestaurar = '';
var permisosHijos = null;
var rolUsuario = '';
var S = '';

try{
	opcionVista = $('#opcionVista').val();
	permisoLectura = $('#permisoLectura').val();
	permisoEscritura = $('#permisoEscritura').val();
	permisoCrear = $('#permisoCrear').val();
	permisoEliminar = $('#permisoEliminar').val();
	permisoRestaurar = $('#permisoRestaurar').val();
	rolUsuario = $('#permisorolUsuario').val();
	S = $('#permisoSiCorto').val();
}catch(e){
	console.log('Ocurrio un error grave de seguridad, no se pueden obtener los permisos');
	console.log(e);
}

/**
 * Carga el js session para cierre y cambio contrasena si no esta cargado
 * */
try{
	if (typeof window.JSSESSION == 'undefined') {
		$.getScript("js/seguridad/session.js", function(){return false;});
	}
}catch(e){
	console.log(e);
}

/**
 * Obtener permisos para hijos
 */
try{
	//obtenerPermisosDePadre(opcionVista);
}catch(e){
	console.log(e);
}

function obtenerPermisosDePadre(opcionPadre){
	var data_caso = '';
	
	data_caso += '_csrf=' + $('#_csrf').val();
	data_caso += '&opcion=' + opcionPadre;
	$.ajax({
		type : 'POST',
		url : contexto + '/caso/getPermisosHijos',
		data : data_caso,
		dataType : 'json',
		success : function(response){
			if(response.length > 0){
				setPermisosHijos(permisos);
			}
		},
		error : function(error){
			console.log(error);
		}
	});
}

function setPermisosHijos(permisos){
	return false;
}