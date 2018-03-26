<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%><jsp:useBean id="cons" class="com.sf.util.Parametros" scope="session"/>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GTH</title>    
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="css/animate.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/custom.css" rel="stylesheet">
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript" src="js/backfix.min.js"></script>
	<script type="text/javascript">
		bajb_backdetect.OnBack = function()
		{
			alert('You clicked it!');
			window.location.replace("http://stackoverflow.com");
		}
	</script>
	
</head>
<body class="top-navigation">
	<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}" value="${_csrf.token}" />
	<div id="wrapper">
		<div id="page-wrapper" class="gray-bg">
			<%@ include file="/WEB-INF/jsp/general/menu.jsp"%>
			<%@ include file="/WEB-INF/jsp/general/userTool.jsp"%>
		<c:if test="${cons.getRolAdmind() == menuPermisos.rol.codigo || cons.getRolSocio() == menuPermisos.rol.codigo || cons.getRolAbogado() == menuPermisos.rol.codigo
			|| cons.getRolDependiente() == menuPermisos.rol.codigo }">
		
			<div class="wrapper wrapper-content">
				<div class="container">
					<div class="contenido">
						<div class="container">
							<div class="row">
								<div class="col-md-7">
									<div id="rowReporteTareas" class="row">
										<div class="col-md-12">
											<div class="ibox float-e-margins">
												<div class="ibox-title">
													<div class="row">
														<div class="col-md-9">
															<h5><spring:message code="label.title.reportes.tareasProximoVencimiento" /></h5>
														</div>
														<div class="col-md-3 text-right">
															<a class="btn btn-azul" href="${pageContext.request.contextPath}/tareasProximoVencimiento">
																<i class="glyphicon glyphicon-plus"></i>
																<spring:message code="label.title.button.ver.mas"></spring:message>
															</a>
														</div>				
													</div>
												</div>
												<div class="ibox-content">
													<div id="tablaTareasProximoVencimiento"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-5">
									<div id="rowGraficas" class="row">
										<div class="col-md-12">
											<div class="row">
												<div class="col-md-12">
													<%@ include file="/WEB-INF/jsp/graficos/graficoActividadesPrincipales.jsp"%>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<%@ include file="/WEB-INF/jsp/graficos/graficoCasosPorTipoCaso.jsp"%>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<%@ include file="/WEB-INF/jsp/graficos/graficoCasosPorEstado.jsp"%>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<%@ include file="/WEB-INF/jsp/graficos/graficoEstadosTareas.jsp"%>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>	
		<c:if test="${cons.getRolSecretaria() == menuPermisos.rol.codigo}">
			<script type="text/javascript">
				window.location.replace("/gestionCasos/visorCaso");
			</script>
		</c:if>
		<c:if test="${cons.getRolContabilidad() == menuPermisos.rol.codigo}">
			<script type="text/javascript">
				window.location.replace("/gestionCasos/visorCaso");
			</script>
		</c:if>
			<%@ include file="/WEB-INF/jsp/general/pieDePagina.jsp"%>
		</div>
	</div>
	
	<!-- Ventanas modales -->
	<%@ include file="/WEB-INF/jsp/seguridad/modalMensajeFinalizacionSesion.jsp"%>
	
	<!-- Mainly scripts -->
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- Session -->
	<script src="js/seguridad/session.js"></script>
	<!-- Custom and plugin javascript -->
	<script src="js/inspinia.js"></script>
	<script src="js/plugins/pace/pace.min.js"></script>
	<!-- Flot -->
	<script src="js/plugins/flot/jquery.flot.js"></script>
	<script src="js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="js/plugins/flot/jquery.flot.resize.js"></script>
	
	
	<script src="js/reportes/tareasProximoVencimiento.js"></script>
	<script src="js/casos/detalleCaso.js"></script>
	
	
	<!-- Data Tables -->
	<script src="js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="js/plugins/dataTables/dataTables.responsive.js"></script>
	<script src="js/plugins/dataTables/dataTables.tableTools.min.js"></script>
	<script src="js/plugins/msjs/waitingfor.js"></script>

	<!-- JQUERY BlockUI -->
	<script src="js/plugins/jqueryBlockUI/jquery.blockUI.js"></script>
	
	<!-- JQUERY SELECT2 INPUT -->
	<script src="js/plugins/select2/select2.min.js"></script>

	<!-- finalizacin de Sesion -->
	<script  src="js/seguridad/session.js"></script>
	<script>
		var tarea = "<spring:message code='label.title.reportes.tareasProximoVencimiento.tarea' />";
		var responsables = "<spring:message code='label.title.reportes.tareasProximoVencimiento.responsables' />";
		var fechaVencimiento = "<spring:message code='label.title.reportes.tareasProximoVencimiento.fechaVencimiento' />";
		var codigoCaso = "<spring:message code='label.title.reportes.tareasProximoVencimiento.codigoCaso' />";
		var nombreCaso = "<spring:message code='label.title.reportes.tareasProximoVencimiento.caso' />";
	</script>
	
	<script>
		google.charts.load('current', {'packages':['bar', 'corechart']});
		google.charts.setOnLoadCallback(cargarHome);
		
		
		function cargarHome() {
			graficoCasosPorTipoCaso.mostrarGrafico();
			graficoActividadesPrincipales.mostrarGrafico();
			graficoCasosPorEstado.mostrarGrafico();
			graficoEstadosTareas.mostrarGrafico();
		}
// 		google.charts.setOnLoadCallback(graficoCasosPorTipoCaso.prueba());
		$(document).ready( 
				function() {
					$(document).ajaxStart($.blockUI)
						.ajaxStop($.unblockUI);
					session.tiempo(session.tiempoSesion);
					tareasProximoVencimiento.mostrarTabla();
					$("#selectCaso").select2({
						placeholder : "Seleccione un caso",
						allowClear : true,
						width : "100%"
					});
					$("#selectResponsable").select2({
						placeholder : "Seleccione un responsable",
						allowClear : true,
						width : "100%"
					});
					graficoEstadosTareas.cargarSelectCasos("selectCaso");
				}
		);
	</script>
</body>
</html>