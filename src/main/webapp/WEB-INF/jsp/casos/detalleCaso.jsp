 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%><jsp:useBean id="cons"
	class="com.sf.util.Parametros" scope="session" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>Detalle Caso</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
<link href="css/plugins/jasny-bootstrap/jasny-bootstrap.min.css" rel="stylesheet">
<link href="css/plugins/dropzone/basic.css" rel="stylesheet" />
<link href="css/plugins/dropzone/dropzone.css" rel="stylesheet" />
<link href="js/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">
<script>
	var contexto = '${pageContext. request. contextPath}';
</script>
	<style type="text/css">
		.modal-dialog{
		    overflow-y: initial !important
		}
		
		.ui-autocomplete.ui-front.ui-menu.ui-widget.ui-widget-content.ui-corner-all{
		z-index: 2000;
		
		}
/* 		.modal-body{ */
/* 		    overflow-y: auto; */
/* 		} */
	</style>
</head>
<c:set var="principalRol" scope="session" value="S"/>
<c:choose>
	<c:when test="${not empty permisos.rol.descripcion && principalRol == permisos.listaRolAccionOpcion[0].snLectura}">
		<body class="top-navigation">
			<div id="wrapper">
				<%@ include file="/WEB-INF/jsp/general/menu.jsp"%>
				<%@ include file="/WEB-INF/jsp/general/userTool.jsp"%>
				<div id="page-wrapper" class="gray-bg">
					<div class="contenido">
						<div class="container">
							<div class="row">
								<div class="col-md-12 col-lg-12">
									<div class="ibox float-e-margins">
										<div class="ibox-title">
											<h5>Detalle del caso</h5>
											<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
												<a data-toggle="modal" class="btn btn-azul" href="#modal-nuevoCaso" style="float: right"
													onclick="cargarFormularios();">
													<spring:message code="button.label.nuevoCaso" />
												</a>
											</c:if>
											<p style="float: right">&nbsp;</p>
											<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
												<a data-toggle="modal" class="btn btn-azul" href="#modal-modificarDetalleCaso"
													style="float: right" onclick="cargarPais();">
													<spring:message code="button.label.modificarDetalle" />
												</a>
											</c:if>
										</div>
										<div class="ibox-content">
											<div class="panel blank-panel">
												<div class="panel-heading">
													<div class="panel-options">
														<ul class="nav nav-tabs"> 
																<c:if test="${cons.getRolAdmind() == menuPermisos.rol.codigo || cons.getRolSocio() == menuPermisos.rol.codigo || cons.getRolAbogado() == menuPermisos.rol.codigo
																		|| cons.getRolDependiente()  == menuPermisos.rol.codigo }">
															<li class="active">
																	<a data-toggle="tab" href="#tab-1">
																		<spring:message code="label.pestana.detalle" />
																	</a>
															</li>
																</c:if>
																<c:if test="${cons.getRolAdmind() == menuPermisos.rol.codigo || cons.getRolSocio() == menuPermisos.rol.codigo || cons.getRolAbogado() == menuPermisos.rol.codigo
																		|| cons.getRolDependiente()  == menuPermisos.rol.codigo }">
															<li class="">
																	<a data-toggle="tab" href="#tab-2">
																		<spring:message code="label.pestana.equipoCaso" />
																	</a>
															</li>
																</c:if>
																<c:if test="${cons.getRolAdmind() == menuPermisos.rol.codigo || cons.getRolSocio() == menuPermisos.rol.codigo || cons.getRolAbogado() == menuPermisos.rol.codigo
																		|| cons.getRolDependiente()  == menuPermisos.rol.codigo }">
															<li class="">
																	<a data-toggle="tab" href="#tab-3" id="actividadesTab">
																		<spring:message code="label.pestana.actividades" />
																	</a>
															</li>
																</c:if>
															<%-- 															<c:if test="${permisos.accion == '' and permisos.rol == rol}"> --%>
																<c:if test="${cons.getRolAdmind() == menuPermisos.rol.codigo || cons.getRolSocio() == menuPermisos.rol.codigo || cons.getRolAbogado() == menuPermisos.rol.codigo
																		|| cons.getRolDependiente() || cons.getRolContabilidad() == menuPermisos.rol.codigo }">
															<li name="confidencial" class="">
																	<a data-toggle="tab" href="#tab-4">
																		<spring:message code="label.pestana.confidencial" />
																	</a>
															</li>
																</c:if>
															<%-- 															</c:if> --%>
														</ul>
														<a data-toggle="modal" href="" onclick="navegarA('visorCaso');" title="Regresar"
															class="btn btn-success  btn-circle btn-xs"
															style="position: relative; float: right; top: -4em;">
															<i class="glyphicon glyphicon-arrow-left"></i>
														</a>
													</div>
												</div>
												<div class="panel-body">
												<!-- Aca se inicializan las variables para cuando se haga la redireccion a las tabs en detalleCaso -->
													<form role="form-inline" class="panel-body">
														<div class="row">
															<div class="alert alert-success" id="messageExitoso" style="display: none"></div>
														</div>
														<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}"
															value="${_csrf.token}" />
														<input type="hidden" value="${caso.codigo}" name="codigoParam" id="codigoParam" />
														<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}"
															value="${_csrf.token}" />
														<input type='hidden' name='idusercreacion' id='idusercreacion'
															value='<sec:authentication property="principal.id"/>' />
														<input hidden="hidden" id="aniosCaducidadParam" name="aniosCaducidadParam"
															value="${aniosCaducidad}" />
														<input type="hidden" name="actMiembro" id="actMiembro" value="${actMiembro}" />
														<input type="hidden" name="addMiembro" id="addMiembro" value="${addMiembro}" />
														<input type="hidden" name="actMiembroUpdate" id="actMiembroUpdate"
															value="${actMiembroUpdate}" />
														<input type="hidden" name="actActividad" id="actActividad" value="${actActividad}" />
														<input type="hidden" name="addActividad" id="addActividad" value="${addActividad}" />
														<input type="hidden" name="actTarea" id="actTarea" value="${actTarea}" />
														<input type="hidden" name="actConfidencial" id="actConfidencial" value="${actConfidencial}" />
														<input type="hidden" name="actArchivoActividad" id="actArchivoActividad" value="${actArchivoActividad}" />
														<input type="hidden" name="redirectActividad" id="redirectActividad" value="${redirectActividad}" />
														
														<div class="tab-content detalles-caso">
															<!-- TAB DETALLE CASO -->
															<c:if test="${cons.getRolAdmind() == menuPermisos.rol.codigo || cons.getRolSocio() == menuPermisos.rol.codigo || cons.getRolAbogado() == menuPermisos.rol.codigo
																		|| cons.getRolDependiente()  == menuPermisos.rol.codigo }">
																<%@include file="/WEB-INF/jsp/casos/detalleCaso/tabDetalleCaso.jsp"%>
															</c:if>
															<!--  TAB EQUIPO CASO -->
															<c:if test="${cons.getRolAdmind() == menuPermisos.rol.codigo || cons.getRolSocio() == menuPermisos.rol.codigo || cons.getRolAbogado() == menuPermisos.rol.codigo
																		|| cons.getRolDependiente()  == menuPermisos.rol.codigo }">
																<%@include file="/WEB-INF/jsp/casos/detalleCaso/tabEquipoCaso.jsp"%>
															</c:if>															
															<!-- TAB ACTIVIDADES -->
															<c:if test="${cons.getRolAdmind() == menuPermisos.rol.codigo || cons.getRolSocio() == menuPermisos.rol.codigo || cons.getRolAbogado() == menuPermisos.rol.codigo
																		|| cons.getRolDependiente()  == menuPermisos.rol.codigo }">
																<%@include file="/WEB-INF/jsp/casos/detalleCaso/tabActividades.jsp"%>
															</c:if>												
															<!-- TAB PRESTAMOS averiguar permiso del padre tal como esta -->
<!-- 															obtener constante con tabprestamos y pregungar si permiso lectura como arriba -->
<%-- 															<c:if --%>
<%-- 																test="${not empty permisos.rol.descripcion && cons.getParametroSiCorto() ==  --%>
<%-- 																	permisos.listaRolAccionOpcion[0].snLectura}"> --%>
															<c:if test="${cons.getRolAdmind() == menuPermisos.rol.codigo || cons.getRolSocio() == menuPermisos.rol.codigo || cons.getRolAbogado() == menuPermisos.rol.codigo
																		|| cons.getRolDependiente() || cons.getRolContabilidad() == menuPermisos.rol.codigo }">
																<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
																	<%@include file="/WEB-INF/jsp/casos/detalleCaso/tabPrestamos.jsp"%>
																</c:if>
															</c:if>
<%-- 															</c:if> --%>
														</div>
													</form>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="footer">
					<div class="container">
						<div class="row">
							<div class="col-lg-4">
								<img src="img/logo-footer.png">
							</div>
							<div class="col-lg-8 text-right">
								<spring:message code="software.footer" />
							</div>
						</div>
					</div>
				</div>
				<%@include file="/WEB-INF/jsp/casos/detalleCaso/modales.jsp"%>
				<%@ include file="/WEB-INF/jsp/seguridad/modalMensajeFinalizacionSesion.jsp"%>
			</div>
			<!-- Mainly scripts -->
			<script src="js/jquery-2.1.1.js"></script>
			<script src="js/bootstrap.min.js"></script>
			
			<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
			<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
			<script src="js/plugins/msjs/waitingfor.js"></script>
			<script src="js/bootstrap-dialog.min.js"></script>
			<!-- permisos -->
			<script src="js/seguridad/permisos.js"></script>
			<!-- permisos -->
			<%@ include file="/WEB-INF/jsp/seguridad/permisos.jsp"%>
			<!-- DROPZONE -->
			<script src="js/plugins/dropzone/dropzone.js"></script>
			<!-- Custom and plugin javascript -->
			<script src="js/inspinia.js"></script>
			<script src="js/plugins/pace/pace.min.js"></script>
			<!-- Data Tables -->
			<script src="js/plugins/dataTables/jquery.dataTables.js"></script>
			<script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
			<script src="js/plugins/dataTables/dataTables.responsive.js"></script>
			<script src="js/plugins/dataTables/dataTables.tableTools.min.js"></script>
			<!-- JQUERY NUMBER INPUT -->
			<script src="js/plugins/jquery.number/jquery.number.js"></script>
			<!-- JQUERY BlockUI Plugin -->
			<script src="js/plugins/jqueryBlockUI/jquery.blockUI.js"></script>
			<!-- JQUERY SELECT2 INPUT -->
			<script src="js/plugins/select2/select2.min.js"></script>
			<!-- JS caso para cargar los estados del caso -->
			<script src="js/general/cargarComboBox.js"></script>
<!-- 			<script src="js/general/cargarComboBoxSuccess.js"></script> -->
			<!-- JS para modificar el caso -->
			<script src="js/casos/modificarCaso.js"></script>
			<script src="js/general/fechas.js"></script>
			<script src="js/casos/caso.js"></script>
			<!-- input file -->
			<script src="js/plugins/jasny-bootstrap/jasny-bootstrap.js"></script>
			<script src="js/plugins/jasny-bootstrap/jasny-bootstrap.min.js"></script>
			<script src="js/plugins/jasny-bootstrap/fileinput.js"></script>
			<script src="js/plugins/jquery.number/jquery.number.js"></script>
			<!-- input file -->
			<!-- detalle caso -->
			<script src="js/casos/detalleCaso.js"></script>
			<script src="js/casos/listaArchivos.js"></script>
			<script src="js/general/general.js"></script>
			<!-- finalizacin de Sesion -->
			<script src="js/seguridad/session.js"></script>
			<script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>
			<script>
				var tituloNotificaciones = "<spring:message code='label.campo.numeroDeNotificaciones' />";
				var tituloNumeroDias = "<spring:message code='label.campo.numeroDeDias' />";
				var tituloFechaVencimiento = "<spring:message code='label.campo.fechaVencimientoActividad' />";
				var tituloEstado = "<spring:message code='label.campo.estadoActividad' />";
				var permisoEliminar = "${snEliminar}";
				var permisoEscritura = "${snEscritura}";
			</script>
			<script src="js/casos/detalleCasoReady.js"></script>
		</body>
	</c:when>
	<c:otherwise>
		<%@ include file="/WEB-INF/jsp/seguridad/errorNoAutorizacion.jsp"%>
	</c:otherwise>
</c:choose>
</html>