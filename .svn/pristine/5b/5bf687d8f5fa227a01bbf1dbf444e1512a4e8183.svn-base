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
		<style type="text/css">
		.modal-dialog{
		    overflow-y: initial !important
		}
/* 		.modal-body{ */
/* 		    overflow-y: auto; */
/* 		} */
	</style>
</head>
<c:choose>
	<c:when test="${not empty permisos.rol.descripcion && cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
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
												<h3 class="text-default">
													<spring:message code="label.title.papelera" />
												</h3>
											</div>
											<div class="ibox-content">
												<div class="panel blank-panel">
													<div class="panel-heading">
														<div class="panel-options">
															<ul class="nav nav-tabs">
																<li class="active">
																	<a data-toggle="tab" href="#tab-1">
																		<spring:message code="label.title.papelera.tab.actividades" />
																	</a>
																</li>
															</ul>
														</div>
													</div>
													<div class="panel-body">
														<form role="form" class="panel-body">
															<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}"
																value="${_csrf.token}" />
															<input type='hidden' name='idusercreacion' id='idusercreacion'
																value='<sec:authentication property="principal.id"/>' />
															<div class="tab-content">
																<div id="tab-1" class="tab-pane active">
																	<div class="row">
																		<div class="form-group col-md-6">
																			<label>
																				<spring:message code="label.title.filter.nombrecaso" />
																			</label>
																			<input type="text" class="form-control m-b" id="nombreCaso" name="nombreCaso" />
																		</div>
																		<div class="form-group col-md-6">
																			<label>
																				<spring:message code="label.filter.reportes.responsables"></spring:message>
																			</label>
																			<select class="form-control" id="responsablesFiltro" name="responsablesFiltro">
																			</select>
																		</div>
																	</div>
																	<div class="row">
																		<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snRestaurar}">
																			<div class="col-md-9"></div>
																			<div class="form-group hidden-xs hidden-sm col-md-3 btns-forms text-right">
																				<label> </label>
																				<button class="btn btn-azul col-sm-6" type="button" onclick="mostrarTablaPapelera()">
																					<spring:message code="label.maestroActividad.filtrar" />
																				</button>
																				<button class="btn btn-azul col-sm-6" type="button" onclick="limpiarFiltros()">
																					<spring:message code="label.maestroActividad.limpiar" />
																				</button>
																			</div>
																			<div class="form-group hidden-md hidden-lg col-sm-12 col-xs-12 btns-forms">
																				<label> </label>
																				<button class="btn btn-azul col-sm-6" type="button" onclick="mostrarTablaPapelera()">
																					<spring:message code="label.maestroActividad.filtrar" />
																				</button>
																				<button class="btn btn-azul col-sm-6" type="button" onclick="limpiarFiltros()">
																					<spring:message code="label.maestroActividad.limpiar" />
																				</button>
																			</div>
																		</c:if>
																	</div>
																	<hr class="border-bottom">
																	<div class="row">
																		<div class="alert alert-success hide" id="messageExitoso"></div>
																	</div>
																	<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
																		<div class="row">
																			<div class="col-sm-6 col-md-6">
																				<h3 class="titulo-azul">
																					<spring:message code="label.title.papelera.tab.actividades" />
																				</h3>
																			</div>
																			<div class="col-sm-6 col-md-6 text-right">
																				<div class="hidden-xs hidden-sm">
																					<button class="btn btn-azul" type="button" onclick="mostrarTablaPapelera()">
																						<spring:message code="label.maestroActividad.filtrar"></spring:message>
																					</button>
																					<button class="btn btn-azul" type="button" onclick="limpiarFiltros()">
																						<spring:message code="label.maestroActividad.limpiar"></spring:message>
																					</button>
																				</div>
																				<div class="col-sm-12 col-xs-12 hidden-md hidden-lg">
																					<a data-toggle="modal" class="btn btn-azul col-sm-12 col-xs-12" href="#modal-nuevoCaso"
																						style="float: right" onclick="cargarFormularios()">
																						<spring:message code="button.label.nuevoCaso" />
																					</a>
																				</div>
																			</div>
																		</div>
																	</c:if>
																	<!-- TABLA -->
																	<div id="tablaListadoPapelera"></div>
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
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
				<%@ include file="/WEB-INF/jsp/casos/nuevoCaso.jsp"%>
				<%@ include file="/WEB-INF/jsp/modales/modalConfiguracionAlarmas.jsp"%>
				<%@ include file="/WEB-INF/jsp/modales/modalIngresarOtroResponsable.jsp"%>
				<%@ include file="/WEB-INF/jsp/seguridad/modalMensajeFinalizacionSesion.jsp"%>
				<%@ include file="/WEB-INF/jsp/modales/modalConfirmacionEstadoActividad.jsp"%>
			</c:if>
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
				<div id="modalConfirmacion" name="modalConfirmacion" class="modal fade" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content" style="width: 620px;">
							<div class="modal-header">
								<div class="row">
									<div class="col-sm-1"></div>
									<div class="col-sm-10">
										<h2 class="modal-title">
											<spring:message code="label.title.confirmacion.continuar" />
										</h2>
									</div>
									<div class="col-sm-1 "></div>
								</div>
								<div class="row" style="text-align: right;; padding-right: 12%;">
									<button type="button" class="btn btn-white" data-dismiss="modal">
										<spring:message code="label.maestroActividad.cancelar" />
									</button>
									<button type="button" class="btn btn-white" id="btnContinuar">
										<spring:message code="label.maestroActividad.continuar" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
				<div id="modalConfBorrarActividad" name="modalConfBorrarActividad" class="modal fade"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content" style="width: 620px;">
							<div class="modal-header">
								<div class="row">
									<div class="col-sm-1"></div>
									<div class="col-sm-10">
										<h2 class="modal-title">
											<spring:message code="label.title.confirmacion.actividad" />
										</h2>
									</div>
									<div class="col-sm-1 "></div>
								</div>
								<div class="row" style="text-align: right;; padding-right: 12%;">
									<button type="button" class="btn btn-white" data-dismiss="modal">
										<spring:message code="label.maestroActividad.cancelar" />
									</button>
									<button type="button" class="btn btn-white" onclick="borradoFisicoActividad()">
										<spring:message code="label.maestroActividad.continuar" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
				<div id="modalConfBorrarTarea" name="modalConfBorrarTarea" class="modal fade" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content" style="width: 620px;">
							<div class="modal-header">
								<div class="row">
									<div class="col-sm-1"></div>
									<div class="col-sm-10">
										<h2 class="modal-title">
											<spring:message code="label.title.confirmacion.tarea" />
										</h2>
									</div>
									<div class="col-sm-1 "></div>
								</div>
								<div class="row" style="text-align: right;; padding-right: 12%;">
									<button type="button" class="btn btn-white" data-dismiss="modal">
										<spring:message code="label.maestroActividad.cancelar" />
									</button>
									<button type="button" class="btn btn-white" onclick="borradoFisicoTarea()">
										<spring:message code="label.maestroActividad.continuar" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<!-- permisos -->
			<%@ include file="/WEB-INF/jsp/seguridad/permisos.jsp" %>
			<!-- Mainly scripts -->
			<script src="js/jquery-2.1.1.js"></script>
			<script src="js/plugins/msjs/waitingfor.js"></script>
			<!-- DROPZONE -->
			<script src="js/plugins/dropzone/dropzone.js"></script>
			<script src="js/bootstrap.min.js"></script>
			<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
			<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
			<script src="js/plugins/jeditable/jquery.jeditable.js"></script>
			<!-- permisos -->
			<script src="js/seguridad/permisos.js"></script>
			<!-- Data Tables -->
			<script src="js/plugins/dataTables/jquery.dataTables.js"></script>
			<script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
			<script src="js/plugins/dataTables/dataTables.responsive.js"></script>
			<script src="js/plugins/dataTables/dataTables.tableTools.min.js"></script>
			<!-- Custom and plugin javascript -->
			<script src="js/inspinia.js"></script>
			<script src="js/plugins/pace/pace.min.js"></script>
			<script src="js/casos/papelera.js"></script>
			<script src="js/casos/casoOffLine.js"></script>
			<script src="js/general/fechas.js"></script>
			<script src="js/casos/caso.js"></script>
			<!-- JQUERY SELECT2 INPUT -->
			<script src="js/plugins/select2/select2.min.js"></script>
			<!-- JQUERY BlockUI -->
			<script src="js/plugins/jqueryBlockUI/jquery.blockUI.js"></script>
			<!-- Input Mask-->
			<script src="js/plugins/jasny/jasny-bootstrap.min.js"></script>
			<script src="js/general/cargarComboBox.js"></script>
			<script src="js/general/general.js"></script>
			<!-- JQUERY MASKED INPUT -->
			<script src="js/plugins/masked-input/jquery.maskedinput.min.js"></script>
			<!-- JQUERY NUMBER INPUT -->
			<script src="js/plugins/jquery.number/jquery.number.js"></script>
			<!-- JQUERY VALIDATE -->
			<script src="${pageContext.request.contextPath}/js/plugins/validate/jquery.validate.min.js"></script>
			<!-- finalizacin de Sesion -->
			<script src="js/seguridad/session.js"></script>
			<script>
				var tituloNotificaciones = "<spring:message code='label.campo.numeroDeNotificaciones' />";
				var tituloNumeroDias = "<spring:message code='label.campo.numeroDeDias' />";
				var tituloFechaVencimiento = "<spring:message code='label.campo.fechaVencimientoActividad' />";
				var tituloEstado = "<spring:message code='label.campo.estadoActividad' />";
			</script>
			<script>
				$(document).ready(function() {
					$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
					cargarAbogado("responsablesFiltro");
					mostrarTablaPapelera();
					ocultarMensajesError();
					cargarCombox();
					session.tiempo(session.tiempoSesion);
				});
			</script>
			<script>
				var tituloCaso = '<spring:message code="label.papelera.titulo.nombre.caso" />';
				var tituloActividad = '<spring:message code="label.papelera.titulo.actividad"/>';
				var tituloVerTarea = '<spring:message code="label.papelera.titulo.nombre.tareas" />';
				var tituloAccion = '<spring:message code="label.papelera.titulo.nombre.accion" />';
			</script>
		</body>
	</c:when>
	<c:otherwise>
		<%@ include file="/WEB-INF/jsp/seguridad/errorNoAutorizacion.jsp"%>
	</c:otherwise>
</c:choose>
</html>