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
	
	<title>FomularioRadicado</title>
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="css/animate.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/custom.css" rel="stylesheet">
	<link href="css/plugins/dropzone/basic.css" rel="stylesheet">
	<link href="css/plugins/dropzone/dropzone.css" rel="stylesheet">
</head>
<%-- <c:choose> --%>
<%-- 	<c:when test="${not empty permisos.rol.descripcion && cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}"> --%>
		<body>
			<div id="page-wrapper" class="gray-bg">
				<%@ include file="/WEB-INF/jsp/general/menu.jsp"%>
				<%@ include file="/WEB-INF/jsp/general/userTool.jsp"%>
				<div class="wrapper wrapper-content">
					<div class="container">
						<!--  -->
						<div class="contenido">
							<div class="container">
								<div class="row">
									<div class="col-md-12 col-lg-12">
										<div class="ibox float-e-margins">
											<div class="ibox-title">
												<h5>
													<spring:message code="label.title.reportes.reportes" />
												</h5>
											</div>
											<div class="ibox-content">
												<div class="panel blank-panel">
													<div class="panel-heading">
														<div class="panel-options">
															<ul class="nav nav-tabs">
																<li class="active">
																	<a data-toggle="tab" href="#tab-1">
																		<spring:message code="label.menu.reportes.prestamo"></spring:message>
																	</a>
																</li>
															</ul>
														</div>
													</div>
													<div class="panel-body">
														<form role="form" class="panel-body" id="filtroAudienciaForm">
															<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}"
																value="${_csrf.token}" />
															<input type="hidden" name="codigoResponsable" id="codigoResponsable"
																value="${codigoResponsable}" />
															<input type='hidden' name='idusercreacion' id='idusercreacion'
																value='<sec:authentication property="principal.id"/>' />
															<div class="tab-content">
																<div id="tab-1" class="tab-pane active">
																<div class="row">
																		
																		<div class="form-group col-md-4">
																			<label>
																				<spring:message code="label.filter.reportes.fechaInicio"></spring:message>
																			</label>
																			<input type="date" class="form-control" id="fechaInicioFiltro" onblur="validarFechaInicioFiltro()"></input>
																		</div>
																		<div class="form-group col-md-4">
																			<label>
																				<spring:message code="label.filter.reportes.fechaFin"></spring:message>
																			</label>
																			<input type="date" class="form-control" id="fechaFinFiltro" onblur="validarFechaFinFiltro()"></input>
																		</div>
																		<div class="form-group col-md-4">
																			<label>
																				<spring:message code="label.filter.reportes.identificadorDeudor"></spring:message>
																			</label>
																			<input type="text" class="form-control" id="identificadorDeudor"></input>
																		</div>
																		
																	</div>
																	<div class="row">
																	<div class="form-group col-md-4">
																			<label>
																				<spring:message code="label.filter.reportes.nombreDeudor"></spring:message>
																			</label>
																			<input type="text" class="form-control" id="nombreDeudor"></input>
																		</div>
																		<div class="form-group col-md-4">
																			<label>
																				<spring:message code="label.filter.reportes.nombreCaso"></spring:message>
																			</label>
																			<input type="text" class="form-control" id="nombreCasoFiltro"
																				name="nombreCasoFiltro"></input>
																		</div>
																		
																		
																	</div>
																	
																	<div class="row">
																		<div class="col-md-9"></div>
																		<div class="form-group hidden-xs hidden-sm col-md-3 btns-forms text-right">
																			<label> </label>
																			<button class="btn btn-azul" type="button" onclick="mostrarTabla()">
																				<spring:message code="label.maestroActividad.filtrar"></spring:message>
																			</button>
																			<button class="btn btn-azul" type="button" onclick="limpiarFiltros()">
																				<spring:message code="label.maestroActividad.limpiar"></spring:message>
																			</button>
																		</div>
																		<div class="form-group hidden-md hidden-lg col-sm-12 col-xs-12 btns-forms">
																			<label> </label>
																			<button class="btn btn-azul col-sm-6 col-xs-6" type="button" onclick="mostrarTabla()">
																				<spring:message code="label.maestroActividad.filtrar"></spring:message>
																			</button>
																			<button class="btn btn-azul col-sm-6 col-xs-6" type="button" onclick="limpiarFiltros()">
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
																		<div class="col-sm-8 col-md-9">
																			<h3 class="titulo-azul">
																				<spring:message code="label.menu.reportes.prestamo"></spring:message>
																			</h3>
																		</div>
																		<div class="col-sm-4 col-md-3 text-right">
																			<button class="btn btn-azul" type="button" onclick="exportarExcel()"
																				style="width: 45%">
																				<spring:message code="label.maestro.exportar.excel"></spring:message>
																			</button>
																		</div>
																	</div>
																	<!-- TABLA -->
																	<div id="tablaAudiencia"></div>
																	<div class="row">
																		<div class=" col-sm-6 col-md-6 text-right form-inline"></div>
																	</div>
																</div>
																<!-- FIN TAB -->
																<div id="tab-2" class="tab-pane"></div>
																<!-- FIN TAB -->
															</div>
														</form>
													</div>
												</div>
												<!-- FIN TABS -->
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!--  -->
					</div>
				</div>
				<%@ include file="/WEB-INF/jsp/general/pieDePagina.jsp"%>
			</div>
			<!-- Ventanas modales -->
			<%@ include file="/WEB-INF/jsp/seguridad/modalMensajeFinalizacionSesion.jsp"%>
			<%@ include file="/WEB-INF/jsp/modales/modalAlertaEnvioMensajes.jsp" %>
			
			<!-- permisos -->
			<%@ include file="/WEB-INF/jsp/seguridad/permisos.jsp" %>
			
			<!-- Mainly scripts -->
			<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
			<script src="${pageContext.request.contextPath}/js/plugins/msjs/waitingfor.js"></script>
			<!-- DROPZONE -->
			<script src="${pageContext.request.contextPath}/js/plugins/dropzone/dropzone.js"></script>
			<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
			<script src="${pageContext.request.contextPath}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/plugins/jeditable/jquery.jeditable.js"></script>
			
			<!-- permisos -->
			<script src="${pageContext.request.contextPath}/js/seguridad/permisos.js"></script>
			
			<!-- Data Tables -->
			<script src="${pageContext.request.contextPath}/js/plugins/dataTables/jquery.dataTables.js"></script>
			<script src="${pageContext.request.contextPath}/js/plugins/dataTables/dataTables.bootstrap.js"></script>
			<script src="${pageContext.request.contextPath}/js/plugins/dataTables/dataTables.responsive.js"></script>
			<script src="${pageContext.request.contextPath}/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
			<!-- Jquery Base 64 Plugin -->
			<script src="${pageContext.request.contextPath}/js/plugins/jquery-base64/jquery.base64.min.js"></script>
			<!-- Custom and plugin javascript -->
			<script src="${pageContext.request.contextPath}/js/inspinia.js"></script>
			<script src="${pageContext.request.contextPath}/js/plugins/pace/pace.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/general/fechas.js"></script>
			<script src="${pageContext.request.contextPath}/js/casos/caso.js"></script>
			<script src="${pageContext.request.contextPath}/js/reportes/prestamoReporte.js"></script>
			<script src="${pageContext.request.contextPath}/js/reportes/reportesGeneral.js"></script>
			<!-- JQUERY SELECT2 INPUT -->
			<script src="${pageContext.request.contextPath}/js/plugins/select2/select2.min.js"></script>
			<!-- Input Mask-->
			<script src="${pageContext.request.contextPath}/js/plugins/jasny/jasny-bootstrap.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/general/cargarComboBox.js"></script>
			<script src="${pageContext.request.contextPath}/js/general/general.js"></script>
			<!-- JQUERY BlockUI -->
			<script src="${pageContext.request.contextPath}/js/plugins/jqueryBlockUI/jquery.blockUI.js"></script>
			<!-- JQUERY MASKED INPUT -->
			<script src="${pageContext.request.contextPath}/js/plugins/masked-input/jquery.maskedinput.min.js"></script>
			<!-- JQUERY NUMBER INPUT -->
			<script src="${pageContext.request.contextPath}/js/plugins/jquery.number/jquery.number.js"></script>
			<!-- JQUERY VALIDATE -->
			<script src="${pageContext.request.contextPath}/js/plugins/validate/jquery.validate.min.js"></script>
			<!-- finalizacin de Sesion -->
			<script src="${pageContext.request.contextPath}/js/seguridad/session.js"></script>
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
			</script>
			<script>
				$(document).ready(function(){
					$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
					cargarAbogado("responsablesFiltro");
					if ($("#codigoResponsable").val()) {
						$("#responsablesFiltro").val($("#codigoResponsable").val());
						$("#responsablesFiltro").prop("disabled", true);
					}
					
					mostrarTabla();
					var token = $("#_csrf").val();
					var cdusuarioLogeado = $("#idusercreacion").val();
					session.tiempo(session.tiempoSesion);
				});
			</script>
		</body>
<%-- 	</c:when> --%>
<%-- 	<c:otherwise> --%>
<%-- 		<%@ include file="/WEB-INF/jsp/seguridad/errorNoAutorizacion.jsp"%> --%>
<%-- 	</c:otherwise> --%>
<%-- </c:choose> --%>
</html>