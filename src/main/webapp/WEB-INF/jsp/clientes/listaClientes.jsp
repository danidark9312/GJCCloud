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
	<c:when
		test="${not empty permisos.rol.descripcion && cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
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
													<spring:message code="label.menu.title.clientes" />
												</h5>
											</div>
											<div class="ibox-content">
												<div class="panel blank-panel">
													<div class="panel-heading">
														<div class="panel-options">
															<ul class="nav nav-tabs">
																<li class="active">
																	<a data-toggle="tab" href="#tab-1">
																		<spring:message code="label.detalleClientes.ListaClientes" />
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
																	<!-- filtro tipo casos -->
																		<div class="form-group col-md-3">
																			<label>
																				<spring:message code="label.detalleCaso.nombreCaso" />
																			</label>
																			
																			<input id="filtroNombreCaso" class="form-control m-b" id="filtroNombreCaso" name="filtroNombreCaso"></input>
																		</div>
																		<!-- filtro estado Actual -->
																		<div class="form-group col-md-3">
																			<label>
																				<spring:message code="label.title.nombreClientes" />
																			</label>
																			<input type="search" class="form-control m-b" id="filtroNombreCliente" name="filtroNombreCliente"></input>
																		</div>
																		<!-- filtro por parte del nombre -->
																		<div class="col-md-3"></div>
																		<div class="form-group col-md-3 btns-forms hidden-sm hidden-xs text-right">
																			<label> </label>
																			<button class="btn btn-azul" type="button" onclick="listaClientes.mostrarTablaListadoCliente();">
																				<spring:message code="label.detalleCaso.filtrar" />
																			</button>
																			<button class="btn btn-azul" type="button" onclick="listaClientes.limpiarFiltros();">
																				<spring:message code="label.detalleCaso.limpiar" />
																			</button>
																		</div>
																		<div class="form-group col-md-3 btns-forms hidden-md hidden-lg">
																			<label> </label>
																			<button class="btn btn-azul col-sm-6" type="button" onclick="listaClientes.mostrarTablaListadoCliente();">
																				<spring:message code="label.detalleCaso.filtrar" />
																			</button>
																			<button class="btn btn-azul" type="button" onclick="listaClientes.limpiarFiltros();">
																				<spring:message code="label.detalleCaso.limpiar" />
																			</button>
																		</div>
																	</div>
																	<hr class="border-bottom">
																	<div class="row">
																		<div class="alert alert-success" id="messageExitoso"></div>
																	</div>
																	<div class="row">
																		<div class="col-sm-12 col-md-6">
																			<h3 class="titulo-azul">
																				<spring:message code="label.menu.title.clientes" />
																			</h3>
																		</div>
<!-- 																		<div class="hidden-sm hidden-xs col-md-6 form-group btns-forms text-right"> -->
<!-- 																			<label> </label> -->
<!-- 																			<button class="btn btn-azul" type="button" onclick="activarModal();" style="margin-top:0;"> -->
<%-- 																				<spring:message code="label.titulo.casoOffnile" /> --%>
<!-- 																			</button> -->
<%-- 																			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}"> --%>
<!-- 																				<a data-toggle="modal" class="btn btn-azul" href="#modal-nuevoCaso" -->
<!-- 																					onclick="cargarFormularios();"  style="margin-top:0;"> -->
<%-- 																					<spring:message code="button.label.nuevoCaso" /> --%>
<!-- 																				</a> -->
<%-- 																			</c:if> --%>
<!-- 																		</div> -->
																		<div class="col-sm-12 hidden-md hidden-lg form-group btns-forms">
																			<label> </label>
																			<button class="btn btn-azul col-sm-6" type="button" onclick="activarModal();">
																				<spring:message code="label.titulo.casoOffnile" />
																			</button>
																			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
																				<a data-toggle="modal" class="btn btn-azul col-sm-6" href="#modal-modificarDemandado"
																					onclick="modificarMiembroEquipo(this);">
																					<spring:message code="button.label.nuevoCaso" />
																				</a>
																			</c:if>
																		</div>
																	</div>
																	<!-- TABLA -->
																	<div id="tablaListado"></div>
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
			<!-- titulos constantes -->
			<input id="tituloNotificaciones" type="hidden" value="<spring:message code='label.campo.numeroDeNotificaciones' />" />
			<input id="tituloNumeroDias" type="hidden" value="<spring:message code='label.campo.numeroDeDias' />" />
			<input id="tituloFechaVencimiento" type="hidden" value="<spring:message code='label.campo.fechaVencimientoActividad' />" />
			<input id="tituloEstado" type="hidden" value="<spring:message code='label.campo.estadoActividad' />" />
			<!-- Ventanas modales -->
			<%@ include file="/WEB-INF/jsp/seguridad/modalMensajeFinalizacionSesion.jsp"%>
			
			
			<%@include file="/WEB-INF/jsp/casos/detalleCaso/modales.jsp"%>
			
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
				<%@ include file="/WEB-INF/jsp/casos/nuevoCaso.jsp"%>
			</c:if>
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
				<%@ include file="/WEB-INF/jsp/modales/modalConfiguracionAlarmas.jsp"%>
			</c:if>
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
				<%@ include file="/WEB-INF/jsp/modales/modalIngresarOtroResponsable.jsp"%>
			</c:if>
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
				<%@ include file="/WEB-INF/jsp/modales/modalConfirmacionEstadoActividad.jsp"%>
			</c:if>
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
				<%@ include file="/WEB-INF/jsp/casos/casoOffLine.jsp"%>
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
			<script src="js/general/fechas.js"></script>
			<script src="js/casos/caso.js"></script>
			<script src="js/casos/listaClientes.js"></script>
			<script src="js/casos/casoOffLine.js"></script>
			<!-- JS para modificar el caso -->
			<script src="js/casos/modificarCaso.js"></script>
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
			<!-- finalizacion de Sesion -->
			<script src="js/seguridad/session.js"></script>
			<script>
				$(document).ready(
						function() {
							try {
								waitingDialog.hide();
							} catch(e) {
								console.log(e);
							}
							var codigo;
							$('#filtroEstadoProcesal').prop('disabled', true);
							// $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
							$("#messageExitoso").hide();
							ocultarMensajesError();
							cargarCombox();							
							directorioActual("Casos", "Clientes");
							cargarformularioModificarMiembros();
							listaClientes.mostrarTablaListadoCliente();
							var token = $("#_csrf").val();
							
							var cdusuarioLogeado = $("#idusercreacion").val()
							myDropzone = new Dropzone("#my-awesome-dropzone", {
								url : contexto + "/fileUpload/importarExcel?" + "_csrf=" + token + "&cdusuarioLogeado="
										+ cdusuarioLogeado,
								dictDefaultMessage : "Para subir arrastre un archivo u oprima ",
								addRemoveLinks : true,
								dictRemoveFile : 'Remover',
								acceptedFiles : ".xls,.xlsx",
								maxFilesize : 30,
								maxFiles : 1,
								parallelUploads : 1,
								autoProcessQueue : false,
								init : function(){
									this.on("maxfilesexceeded", function(file){
										this.removeAllFiles();
										this.addFile(file);
									});
								},
								success : function(file, response) {
									if (response.STATUS == "SUCCESS") {
										$("#messageExitosoCasoOfLine").html("Se guard\u00f3 el caso exitosamente.");
										$("#messageExitosoCasoOfLine").show();
										codigo = response.casoCodigo;
										setTimeout("abrirDetalleCaso(" + codigo + ");", 3000);
									} else {
										$("#messageErrorCasoOfLine").html(response.MENSAJE);
										$("#messageErrorCasoOfLine").show();
									}
									setTimeout(function() {
										$("#messageExitosoCasoOfLine").hide();
										$("#messageErrorCasoOfLine").hide();
										myDropzone.removeAllFiles();
									}, 10000)
								},
							});
							$("#btnCancelarCarga").click(function() {
								myDropzone.removeAllFiles(true);
							});
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