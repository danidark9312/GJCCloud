<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%><jsp:useBean id="cons"
	class="com.sf.util.Parametros" scope="session" />
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Administrador</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="css/animate.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/custom.css" rel="stylesheet">
	<link href="css/plugins/dropzone/basic.css" rel="stylesheet">
	<link href="css/plugins/dropzone/dropzone.css" rel="stylesheet">
</head>
<%-- <c:choose> --%>
<%-- 	<c:when --%>
<%-- 		test="${not empty permisos.rol.descripcion && cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}"> --%>
<body>
	<div id="page-wrapper" class="gray-bg">
		<%@ include file="/WEB-INF/jsp/general/menu.jsp"%>
		<%@ include file="/WEB-INF/jsp/general/userTool.jsp"%>
		<div class="wrapper wrapper-content">
			<div class="container">
				<div class="row">
					<div class="col-md-12 col-lg-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>
									<spring:message code="label.maestroActividad.titulo" />
								</h5>
							</div>
							<div class="panel-body">
								<form role="form" class="panel-body" id="filtroAudienciaForm">
									<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<input type='hidden' name='idusercreacion' id='idusercreacion'
										value='<sec:authentication property="principal.id"/>' />
									<div class="tab-content">
										<div class="row">
											<div class="form-group col-md-4 col-sm-12">
												<label>
													<spring:message code="label.filter.homeAdmin.actividad"></spring:message>
												</label>
												<input type="text" class="form-control" id="nombreActividadFiltro" name="nombreActividadFiltro"></input>
											</div>
											<div class="form-group col-md-4 col-sm-12">
												<label>
													<spring:message code="label.filter.homeAdmin.caso"></spring:message>
												</label>
												<input type="text" class="form-control" id="nombreCasoFiltro" name="nombreCasoFiltro"></input>
											</div>
											<div class="form-group col-md-4 col-sm-12">
												<label>
													<spring:message code="label.filter.homeAdmin.responsables"></spring:message>
												</label>
												<select class="form-control" id="responsablesFiltro" name="responsablesFiltro">
												</select>
											</div>
											<div class="row">
												<div class="col-md-9 col-sm-12"></div>
												<div class="form-group col-md-3 col-sm-12 btns-forms">
													<button class="btn btn-azul" type="button" onclick="mostrarTabla();" style="width: 45%">
														<spring:message code="label.maestroActividad.filtrar"></spring:message>
													</button>
													<button class="btn btn-azul" type="button" onclick="limpiarFiltros();" style="width: 45%">
														<spring:message code="label.maestroActividad.limpiar"></spring:message>
													</button>
												</div>
											</div>
											<hr class="border-bottom">
											<div class="row">
												<div class="alert alert-success hide" id="messageExitoso"></div>
											</div>
											<div class="row">
												<div class="alert alert-danger hide" id="messageError"></div>
											</div>
											<div class="row">
											</div>
											<!-- GRAFICAS -->
											<div id="graficas"></div>
											<!-- TABLA -->
											<div id="tablaActividades"></div>
											<div class="row">
												<div class=" col-sm-6 col-md-6 text-right form-inline"></div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/jsp/general/pieDePagina.jsp"%>
	</div>
	<!-- Ventanas modales -->
	<%@ include file="/WEB-INF/jsp/seguridad/modalMensajeFinalizacionSesion.jsp"%>
	<!-- permisos -->
	<%@ include file="/WEB-INF/jsp/seguridad/permisos.jsp"%>
	<!-- Mainly scripts -->
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/plugins/msjs/waitingfor.js"></script>
	<!-- permisos -->
	<script src="js/seguridad/permisos.js"></script>
	<!-- Data Tables -->
	<script src="js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="js/plugins/dataTables/dataTables.responsive.js"></script>
	<script src="js/plugins/dataTables/dataTables.tableTools.min.js"></script>
	<!-- Jquery Base 64 Plugin -->
	<script src="js/plugins/jquery-base64/jquery.base64.min.js"></script>
	<!-- Custom and plugin javascript -->
	<script src="js/bootstrap.min.js"></script>
	<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="js/inspinia.js"></script>
	<script src="js/plugins/pace/pace.min.js"></script>
	<script type="text/javascript" src="js/plugins/google/jsapichart.js"></script>
	<!-- TODO #####  -->
<!-- 	<script src="js/reportes/audiencia.js"></script> -->
<!-- 	<script src="js/reportes/reportesGeneral.js"></script> -->
	<script src="js/general/fechas.js"></script>
	<script src="js/casos/caso.js"></script>
	<script src="js/general/paginasInicio/homeAdmin.js"></script>
	<script src="js/general/paginasInicio/graficas.js"></script>
<!-- 	<script src="js/reportes/reportesGeneral.js"></script> -->
	<!-- #####  -->
	<!-- JQUERY SELECT2 INPUT -->
	<script src="js/plugins/select2/select2.min.js"></script>
	<!-- Input Mask-->
	<script src="js/plugins/jasny/jasny-bootstrap.min.js"></script>
	<script src="js/general/cargarComboBox.js"></script>
	<script src="js/general/general.js"></script>
	<!-- JQUERY BlockUI -->
	<script src="js/plugins/jqueryBlockUI/jquery.blockUI.js"></script>
	<!-- JQUERY MASKED INPUT -->
	<script src="js/plugins/masked-input/jquery.maskedinput.min.js"></script>
	<!-- JQUERY NUMBER INPUT -->
	<script src="js/plugins/jquery.number/jquery.number.js"></script>
	<!-- JQUERY VALIDATE -->
	<script src="${pageContext.request.contextPath}/js/plugins/validate/jquery.validate.min.js"></script>
	<!-- finalizacin de Sesion -->
	<script src="js/seguridad/session.js"></script>
	<script>	
		var proceso = "<spring:message code='label.title.reportes.audiencia.proceso' />";
		var radicado = "<spring:message code='label.title.reportes.audiencia.radicado' />";
		var demandado = "<spring:message code='label.title.reportes.audiencia.demandado' />";
		var audiencia = "<spring:message code='label.title.reportes.audiencia.audiencia' />";
		var alertasEnviadas = "<spring:message code='label.title.reportes.audiencia.alertas' />";
		var responsables = "<spring:message code='label.title.reportes.audiencia.responsables' />";
		var lugar = "<spring:message code='label.title.reportes.audiencia.lugar' />";
		var fechaVencimiento = "<spring:message code='label.title.reportes.audiencia.fechaVencimiento' />";
		var context = "${pageContext.request.contextPath}";
		$(document).ready(function(){
			// $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
			//cargarCombox();
			cargarResponsables("responsablesFiltro");
			mostrarTabla();
			var token = $("#_csrf").val();
			var cdusuarioLogeado = $("#idusercreacion").val()
			directorioActual("Home", "Administrador");
		});
	</script>
</body>
<%-- 	</c:when> --%>
<%-- 	<c:otherwise> --%>
<%-- 		<%@ include file="/WEB-INF/jsp/seguridad/errorNoAutorizacion.jsp"%> --%>
<%-- 	</c:otherwise> --%>
<%-- </c:choose> --%>
</html>