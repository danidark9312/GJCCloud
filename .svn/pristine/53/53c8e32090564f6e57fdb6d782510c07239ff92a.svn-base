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
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title><spring:message code="label.maestroRoles.titulo" /></title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="css/animate.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/custom.css" rel="stylesheet">
</head>
<c:choose>
	<c:when test="${not empty permisos.rol.descripcion && cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
		<body class="top-navigation">
			<div id="wrapper">
				<div id="page-wrapper" class="gray-bg">
					<%@ include file="/WEB-INF/jsp/general/menu.jsp"%>
					<%@ include file="/WEB-INF/jsp/general/userTool.jsp"%>
					<div class="contenido">
						<div class="container">
							<div class="row">
								<div class="col-md-12 col-lg-12">
									<div class="ibox float-e-margins">
										<div class="ibox-title">
											<h5>
												<spring:message code="label.maestroRoles.titulo" />
											</h5>
										</div>
										<div class="ibox-content">
											<form role="form" class="panel-body">
												<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}"
													value="${_csrf.token}" />
												<input type='hidden' name='idusercreacion' id='idusercreacion'
													value='<sec:authentication property="principal.id"/>' />
												<div class="row">
													<div class="alert alert-success" id="messageExitoso">
														&nbsp;
														<a class="alert-link" href="#">&nbsp;</a>
													</div>
													<div class="alert alert-danger" id="messageError">
														&nbsp;
														<a class="alert-link" href="#">&nbsp;</a>
													</div>
												</div>
												<div class="row" style="text-align: right; padding-right: 1%;">
													<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
														<a data-toggle="modal" onclick="mostrarModalNuevoRol();"
															class="btn btn-primary btn-circle btn-xs"
															style="background-color: #449D44; border: 0;"
															title="<spring:message code='label.maestroRoles.botonNuevo' /> ">
															<i class="glyphicon glyphicon-plus"></i>
														</a>
													</c:if>												
												</div>
												<div class="row">
													<div class="form-group col-md-3">
														<label>
															<spring:message code="label.detalleCaso.estadoActual" />
														</label>
														<select class="form-control m-b" id="filtroEstado" name="filtroEstado">
															<option value="">Seleccionar</option>
															<option value="1">Activo</option>
															<option value="2">Inactivo</option>
														</select>
													</div>
													<div class="form-group col-md-3">
														
													</div>
													
													<div class="form-group col-md-3">
														
													</div>
													<div class="form-group col-md-3 btns-forms hidden-sm hidden-xs text-right">
														<label> </label>
														<button class="btn btn-azul" type="button" onclick="mostrarTablaRoles();">
															<spring:message code="label.detalleCaso.filtrar" />
														</button>
													</div>
													<div class="form-group col-md-3 btns-forms hidden-md hidden-lg">
														<label> </label>
														<button class="btn btn-azul col-sm-6" type="button" onclick="mostrarTabla();">
															<spring:message code="label.detalleCaso.filtrar" />
														</button>
														<button class="btn btn-azul col-sm-6" type="button" onclick="limpiarFiltros();">
															<spring:message code="label.detalleCaso.limpiar" />
														</button>
													</div>
												</div>
												<div id="tablaListadoRoles"></div>
												<div class="row"></div>
												<!-- TABLA -->
												<div id="tablaListado"></div>
												<hr class="border-bottom">
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
						<%@ include file="/WEB-INF/jsp/general/pieDePagina.jsp"%>
					</div>
				</div>
			</div>
			<!-- permisos -->
			<%@ include file="/WEB-INF/jsp/seguridad/permisos.jsp" %>
			<!-- Ventanas modales -->
			<%@ include file="/WEB-INF/jsp/modales/modalMaestroRol.jsp"%>
			<%@ include file="/WEB-INF/jsp/seguridad/modalMensajeFinalizacionSesion.jsp"%>
			<!-- Mainly scripts -->
			<script src="js/jquery-2.1.1.js"></script>
			<script src="js/bootstrap.min.js"></script>
			<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
			<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
			<script src="js/plugins/jeditable/jquery.jeditable.js"></script>
			<script src="js/plugins/msjs/waitingfor.js"></script>
			<!-- Data Tables -->
			<script src="js/plugins/dataTables/jquery.dataTables.js"></script>
			<script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
			<script src="js/plugins/dataTables/dataTables.responsive.js"></script>
			<script src="js/plugins/dataTables/dataTables.tableTools.min.js"></script>
			<script src="js/seguridad/permisos.js"></script>
			<script src="js/maestros/maestroRol.js"></script>
			<!-- Custom and plugin javascript -->
			<script src="js/inspinia.js"></script>
			<script src="js/plugins/pace/pace.min.js"></script>
			<!-- JQUERY VALIDATE -->
			<script src="${pageContext.request.contextPath}/js/plugins/validate/jquery.validate.min.js"></script>
			<script src="js/general/fechas.js"></script>
			<script src="js/general/cargarComboBox.js"></script>
			<script src="js/general/general.js"></script>
			<!-- finalizacin de Sesion -->
			<script src="js/seguridad/session.js"></script>
			<script>
				var _csrfX = "${_csrf.token}";
				$(document).ready(function(){				
					
					rulesForm();
					mostrarTablaRoles();
					$("#messageExitoso").hide();
					$("#messageError").hide();
					$("#messageExitosoModal").hide();
					$("#messageErrorModal").hide();
					directorioActual("Administradores", "Roles");
					session.tiempo(session.tiempoSesion);
				});
			</script>
		</body>
	</c:when>
	<c:otherwise>
		<%@ include file="/WEB-INF/jsp/seguridad/errorNoAutorizacion.jsp"%>
	</c:otherwise>
</c:choose>
</html>