//<script type="text/javascript" src="https://www.google.com/jsapi"></script>
// Load the Visualization API and the piechart package.
google.load('visualization', '1.1', {
	packages : ['charteditor', 'controls', 'corechart']
});
COLORESGRAFICAS = ['#314B7E', '#B0CAFF', '#6398FF', '#58657F', '#4F79CC'];
ERRORES = {
	consultaIncorrecta: 'No se realizo la consulta correctamente.',
	errorTransaccion: 'Hubo un error de red durante la transacción.'	
};
IDCONTROLGRAFICA = 'controlGraficas';
IDDASHBOARD = 'dashboard';
IDGRAFICA = 'graficas';
STRINGS = {
	'tituloActividadPrincipal': 'Actividades principales',
	'tituloActividadTipoCaso': 'Actividades tipo caso'
};
TIEMPOINICIARGRAFICA = 1000;
TIEMPODURACIONANIMACIONGRAFICA = 5500;
TIEMPOMOSTRARMSJERROR = 3000;

function Grafica(){
	/**
	 * Variables privadas
	 */
	
	/**
	 * Metodos privados
	 */
	
	/**
	 * Metodos publicos
	 */
	var aumentarTamano = function(){
		_OPCIONESTORTA.chartArea.height = '100%';
		_OPCIONESTORTA.chartArea.left = '30%';
		_OPCIONESTORTA.chartArea.top = '10%';
		_OPCIONESTORTA.chartArea.width = '100%';
	}

	var crearGrafica = function(idContenedorGrafica){
		return new google.visualization.PieChart(document.getElementById(idContenedorGrafica));
	}

	var dibujarGrafica = function(grafica, data, opciones){
		grafica.draw(data, opciones);
	}
			
	//TODO implementar
	var limpiarGrafica = function(idGrafica){
		return false;
	}
	
	return {
		aumentarTamanoGrafica: aumentarTamano,
		crearGrafica: crearGrafica,
		dibujarGrafica: dibujarGrafica,
		limpiarGrafica: limpiarGrafica
	}
}

function Conexion(){
	/**
	 * Variables privadas
	 */
	__csrf = $('#_csrf').val();
	DIVCARGA = '#divcarga';
	DIVMSJERROR = '#divmsjerror';
	
	/**
	 * Metodos privados
	 */
	var _mostrarCarga = function(){
		$(DIVCARGA).show();
	}
	
	var _mostrarError = function(msj){
		_ocultarCarga();
		$(DIVMSJERROR).html(msj);
		$(DIVMSJERROR).show();
		setTimeout(function(){
			$(DIVMSJERROR).hide();
		}, TIEMPOMOSTRARMSJERROR);
	}
	
	var _ocultarCarga = function(){
		$(DIVCARGA).hide();
	}
	
	/**
	 * Metodos publicos
	 */
	var ajax = function(parametros){
		_mostrarCarga();
		data = [ { name: 'contrasenaAnterior', value:
			$(INPUTCONTRASENAANTERIOR).val() }, { name: 'contrasenaNueva', value:
				$(INPUTCONTRASENANUEVA).val() } ];
  
		waitingDialog.show('');$.ajax({
			data: parametros.data,
			type : 'POST',
			url : contexto + parametros.controlador + parametros.metodo + '?_csrf=' + __csrf,
			success : function(res){
				waitingDialog.hide();
				res = JSON.parse(res);
				if(res.STATUS == 'SUCCESS'){
					_ocultarCarga();
					parametros.metodoOnSuccess(res);
				}else{
					_mostrarError(ERRORES['consultaIncorrecta']);
				}
			},
			error: function(e){
				waitingDialog.hide();
				_mostrarError(ERRORES['errorTransaccion']);
			}
		});
	};	

	return {
		ajax: ajax
	};
}

function GraficaGeneral(){
	/**
	 * Variables privadas
	 */
	_dataTmp = null;
	_graficaTmp = null;
	INPUTCONTRASENACONFIRMACION = '#contrasenaConfirmacion';
	LINKCIERRESESION = 'j_spring_security_logout';
	grafica = new Grafica();
	_OPCIONESTORTA = {
		animation : {
			duration : TIEMPODURACIONANIMACIONGRAFICA,
			easing : 'linear',
			startup : true
		},
		chartArea : {
			height : '70%',
			left : '30%',
			width : '70%',
			top : '10%'
		},
		colors : COLORESGRAFICAS,
		slice : {},
		is3D : true,
		title : 'Titulo',
	};

	/**
	 * Metodos privados
	 */
	var _agregarEventos = function(){
		google.visualization.events.addListener(_graficaTmp, 'select', _seleccionarTipoActividad);
	}

	var _animarGrafica = function(){
		grafica.aumentarTamanoGrafica();
		_dibujarTorta();
	}

	var _crearTablaDeArray = function(array){
		return google.visualization.arrayToDataTable(array);
	}

	var _crearTabla = function(){
		var datos = google.visualization.DataTable();
		datos.addColumn('tipo', 'etiqueta');
		datos.addRows([['etiqueta', 'val'], ['etiqueta', 'val']]);

		return datos;
	}

	var __iniciarGraficador = function(){
		window.setTimeout(function(){
			_dibujarTorta();
		}, 10);
		window.setTimeout(function(){
			_animarGrafica();
		}, TIEMPOINICIARGRAFICA);
	}

	var _iniciarGraficadorDetalle = function(dataDetalle){
		console.log('dataDetalle:\t' + dataDetalle);
	}
	
	var _irADetalle = function(objDetalle){
		console.log('detalle de: ' + objDetalle);
		var con = new Conexion();
		var data = {};
		
		data['actividad'] = objDetalle;
		/**
		con.ajax({
			controlador: '/reportes',
			data: data,
			metodo: '/consultarDetalleGeneral',
			metodoOnSuccess: _mostrarDetalleGeneral
		});
		*/
	}
	
	var _mostrarDetalleGeneral = function(data){
		console.log('_mostrarDetalleGeneral con:\t' + data);
	}
	
	var _seleccionarTipoActividad = function(){
		var tipoActividadSeleccionada = _graficaTmp.getSelection()[0];
		if(tipoActividadSeleccionada){
			_irADetalle(_dataTmp.getValue(tipoActividadSeleccionada.row, 0));
		}
		// _limpiarTmps();
	}

	/**
	 * Realza (levantando) una opcion del grafico
	 */
	var _levantarOpcion = function(opcion, opciones){
		try{
			$.each(opciones, function(index, opcion){
				_OPCIONESTORTA['slices'].opcion = {
					offset : 0.2
				};
			});
		}catch(e){
			console.log('No se puede levantar las opciones por:\t' + e);
			_OPCIONESTORTA['slices'] = {
				opcion : {
					offset : 0.2
				}
			};
		}
	}

	var _limpiarTmps = function(){
		_dataTmp = null;
		_graficaTmp = null;
	}
	
	var _metodoOnSuccess = function(){
		//TODO Metodo on success llamar function hacer algo
	}


	/**
	 * Metodos publicos
	 */
	var dibujar = function(){
		var data = null;
		var graficaInstancia = null;
		var opciones = null;
		
		data = _crearTablaDeArray([['Tareas', 'Cantidad pendientes'], ['Audiencias', 5], ['Recursos pendientes', 5],
				['Documentación pendientes', 3], ['Solicitud prejudicial', 9]]);
		opciones = _OPCIONESTORTA;
		opciones['title'] = STRINGS['tituloActividadPrincipal'];
		graficaInstancia = grafica.crearGrafica(IDGRAFICA);
		_graficaTmp = graficaInstancia;
		//TODO revisar si puedo eliminar _dataTmp
		_dataTmp = data;
		grafica.dibujarGrafica(_graficaTmp, data, opciones);
		eventos = _agregarEventos();
	}

	return {
		dibujar : dibujar
	};
}

function GraficaTiposCaso(){
	/**
	 * Variables privadas
	 */
	__csrf = $('#_csrf').val();
	_dataTmp = null;
	_graficaTmp = null;
	INPUTCONTRASENACONFIRMACION = '#contrasenaConfirmacion';
	LINKCIERRESESION = 'j_spring_security_logout';
	grafica = new Grafica();
	_OPCIONESTORTA = {
		animation : {
			duration : TIEMPODURACIONANIMACIONGRAFICA,
			easing : 'linear',
			startup : true
		},
		chartArea : {
			height : '70%',
			left : '30%',
			width : '70%',
			top : '10%'
		},
		colors : COLORESGRAFICAS,
		slice : {},
		is3D : true,
		title : 'Titulo',
	};

	/**
	 * Metodos privados
	 */
	var _agregarEventos = function(grafica, data){
		google.visualization.events.addListener(_graficaTmp, 'select', _seleccionarTipoActividad);
		
		return {
			_dataTmp: data,
			_graficaTmp: grafica
		};
	}

	var _animarGrafica = function(){
		grafica.aumentarTamanoGrafica();
		_dibujarTorta();
	}

	var _crearTablaDeArray = function(array){
		console.log('||||||||||||||||||||||||||||');
		console.log(google);
		return google.visualization.arrayToDataTable(array);
	}

	var _crearTabla = function(){
		var datos = google.visualization.DataTable();
		datos.addColumn('tipo', 'etiqueta');
		datos.addRows([['etiqueta', 'val'], ['etiqueta', 'val']]);

		return datos;
	}

	var _irADetalle = function(objDetalle){
		console.log('detalle de: ' + objDetalle);
		
	}

	var __iniciarGraficador = function(){
		window.setTimeout(function(){
			_dibujarTorta();
		}, 10);
		window.setTimeout(function(){
			_animarGrafica();
		}, TIEMPOINICIARGRAFICA);
	}

	var _iniciarGraficadorDetalle = function(dataDetalle){
		console.log('dataDetalle:\t' + dataDetalle);
	}

	/**
	 * Realza (levantando) una opcion del grafico
	 */
	var _levantarOpcion = function(opcion, opciones){
		try{
			$.each(opciones, function(index, opcion){
				_OPCIONESTORTA['slices'].opcion = {
					offset : 0.2
				};
			});
		}catch(e){
			console.log('No se puede levantar las opciones por:\t' + e);
			_OPCIONESTORTA['slices'] = {
				opcion : {
					offset : 0.2
				}
			};
		}
	}

	var _limpiarTmps = function(){
		_dataTmp = null;
		_graficaTmp = null;
	}

	var _seleccionarTipoActividad = function(){
		var tipoActividadSeleccionada = _graficaTmp.getSelection()[0];
		if(tipoActividadSeleccionada){
			_irADetalle(_dataTmp.getValue(tipoActividadSeleccionada.row, 0));
		}
		// _limpiarTmps();
	}
	
	var _getChart = function(){
		var chart = null;
		
		chart = new google.visualization.ChartWrapper({
			//chartType: 'BarChart',
	        chartType: 'ColumnChart',
			containerId : IDGRAFICA,
			options : {
				colors : COLORESGRAFICAS
			}
		});
		
		return chart;
	}
	
	var _getControlWrapper = function(){
		var control = null;
		
		control = new google.visualization.ControlWrapper({
			controlType : 'ChartRangeFilter',
			containerId : IDCONTROLGRAFICA,
			options : {
				filterColumnIndex : 1,
				ui : {
					chartOptions : {
						colors : COLORESGRAFICAS[1],
						height : 50,
						width : 600,
						chartArea : {
							width : '80%'
						}
					},
					chartView : {
						columns : [0, 1]
					}
				}
			}
		});
		
		return control;
	}
	
	var _getDashboard = function(){
		return new google.visualization.Dashboard(document.getElementById(IDDASHBOARD));
	}
	
	var _setChartOptions = function(wrapper){
		wrapper.setOption('height', 400);
		wrapper.setOption('width', 600);
		wrapper.setOption('chartArea.width', '80%');
		wrapper.setOption('animation.duration', 0);
	}
	
	var _getDataCombo = function(){
		var data = new google.visualization.DataTable();
		
		data.addColumn('string', 'X');
		data.addColumn('number', 'Y1');
		for (var i = 0; i < 100; i++){
			data.addRow(['i' + i, Math.floor(Math.random() * 100)]);
		}
		
		return data;
	}
	
	var _dibujarComboEspecial = function(){
		var chart = null;
		var control = null;
		var dash = null;
		var data = null;
				
		data = _getDataCombo();
		dash = _getDashboard();
		control = _getControlWrapper();
		chart = _getChart();
		_setChartOptions(chart);

		dash.bind([control], [chart]);
		dash.draw(data);
	}

	/**
	 * Metodos publicos
	 */
	var dibujar = function(){
		var data = null;
		var eventos = null;
		var graficaInstancia = null;
		var opciones = null;
		
		data = _crearTablaDeArray([['Tareas', 'Cantidad pendientes'], ['Audiencias', 5], ['Recursos pendientes', 5],
				['Documentación pendientes', 3], ['Solicitud prejudicial', 9]]);
		opciones = _OPCIONESTORTA;
		opciones['title'] = STRINGS['tituloActividadPrincipal'];
		graficaInstancia = grafica.crearGrafica(IDGRAFICA);
		grafica.dibujarGrafica(graficaInstancia, data, opciones);
		eventos = _agregarEventos(graficaInstancia, data);
		_dataTmp = eventos._dataTmp;
		_graficaTmp = eventos._graficaTmp;
	}

	return {
		dibujar : dibujar
	};
}

var graficaGeneral = new GraficaGeneral();

google.setOnLoadCallback(graficaGeneral.dibujar);


