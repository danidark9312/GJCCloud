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
	<link href="js/plugins/jquery-ui/jquery-ui.css" rel="stylesheet">
		<style type="text/css">
.modal-dialog {
	overflow-y: initial !important
}

.ui-front {
	z-index: 1300 !important;
}
/* 		.modal-body{ */
/* 		    overflow-y: auto; */
/* 		} */
</style>

<script>
var radicadoParameter = "<%=request.getParameter("radicado")%>";

function validateParamRadicado(){
	if(radicadoParameter!="null"){
		$("#radicadoFiltro").val(radicadoParameter);
	}
}

</script>



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
													<spring:message code="label.menu.title.casos" />
												</h5>
											</div>
											<div class="ibox-content">
												<div class="panel blank-panel">
													<div class="panel-heading">
														<div class="panel-options">
															<ul class="nav nav-tabs">
																<li class="active">																																
																	<a data-toggle="tab" href="#tab-1">
																		<spring:message code="label.detalleCaso.visorCaso" />
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
															<input type='hidden' name='tipoCasoSession' id='tipoCasoSession'
																value='${tipoCaso}' />
															<input type='hidden' name='actividadPrincipalSession' id='actividadPrincipalSession'
																value='${actividadPrincipal}' />
															<input type='hidden' name='estadoSession' id='estadoSession'
																value='${estado}' />
															<input type='hidden' name='codigoRol' id='codigoRol'
																value='${permisos.rol.codigo}' />	
																
															<div class="tab-content">
																<div id="tab-1" class="tab-pane active">
																	<div class="row">
																		<div class="form-group col-md-3">
																			<label>
																				<spring:message code="label.detalleCaso.tiposCasos" />
																			</label>
																			<select class="form-control m-b" id="cmbTipoDeCaso" name="txtTipoCaso"></select>
																		</div>
																		<div class="form-group col-md-3">
																			<label>
																				<spring:message code="label.detalleCaso.estadoActual" />
																			</label>
																			<select class="form-control m-b" id="filtroEstadoCaso" name="txtEstadoCaso">
																			</select>
																		</div>
																		<div class="form-group col-md-3">
																			<label>
																				<spring:message code="label.detalleCaso.estadoProcesal" />
																			</label>
																			<select class="form-control m-b" id="filtroEstadoProcesal"
																				name="filtroEstadoProcesal"></select>
																		</div>
																		<div class="form-group col-md-3">
																			<label>
																				<spring:message code="label.detalleCaso.nombreCaso"></spring:message>
																			</label>
																			<input class="form-control" id="nombreCasoFiltro" name="nombreCasoFiltro"></input>
																		</div>
																	</div>
																	<div class="row">
																		<div class="form-group col-md-3">
																			<label>
																				<spring:message code="label.detalleCaso.radicadoFiltro" />
																			</label>
																			<input class="form-control" id="radicadoFiltro" name="radicadoFiltro"></input>
																		</div>
																	</div>
																	<div class="row">
																		<div class="col-md-9"></div>
																		<div class="form-group hidden-xs hidden-sm col-md-3 btns-forms text-right">
																			<label> </label>
																			<button class="btn btn-azul" type="button" onclick="mostrarTabla();">
																				<spring:message code="label.detalleCaso.filtrar" />
																			</button>
																			<button class="btn btn-azul" type="button" onclick="limpiarFiltros();">
																				<spring:message code="label.detalleCaso.limpiar" />
																			</button>
																		</div>
																		<div class="form-group hidden-md hidden-lg col-sm-12 col-xs-12 btns-forms">
																			<label> </label>
																			<button class="btn btn-azul col-sm-6" type="button" onclick="mostrarTabla();">
																				<spring:message code="label.detalleCaso.filtrar" />
																			</button>
																			<button class="btn btn-azul col-sm-6" type="button" onclick="limpiarFiltros();">
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
																				<spring:message code="label.detalleCaso.visorCasos" />
																			</h3>
																		</div>
																		<div class="hidden-sm hidden-xs col-md-6 form-group btns-forms text-right">
																			<label> </label>
																			<button class="btn btn-azul" type="button" onclick="activarModal();" style="margin-top:0;">
																				<spring:message code="label.titulo.casoOffnile" />
																			</button>
																			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
																				<a data-toggle="modal" class="btn btn-azul" href="#modal-nuevoCaso"
																					onclick="cargarFormularios();"  style="margin-top:0;">
																					<spring:message code="button.label.nuevoCaso" />
																				</a>
																			</c:if>
																		</div>
																		<div class="col-sm-12 hidden-md hidden-lg form-group btns-forms">
																			<label> </label>
																			<button class="btn btn-azul col-sm-6" type="button" onclick="activarModal();">
																				<spring:message code="label.titulo.casoOffnile" />
																			</button>
																			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
																				<a data-toggle="modal" class="btn btn-azul col-sm-6" href="#modal-nuevoCaso"
																					onclick="cargarFormularios();">
																					<spring:message code="button.label.nuevoCaso" />
																				</a>
																			</c:if>
																		</div>
																	</div>
																	<!-- TABLA -->
<!-- 																	<div class="table-responsive"> -->
																		<div id="tablaListado"></div>
																		<div class="row">
																			<div class=" col-sm-6 col-md-6 text-right form-inline"></div>
<!-- 																		</div> -->
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
			<script src="js/casos/visorCaso.js"></script>
			<script src="js/casos/casoOffLine.js"></script>
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
			<script src="${pageContext.request.contextPath}/js/plugins/jquery-ui/jquery-ui.js"></script>
			<!-- finalizacion de Sesion -->
			<script src="js/seguridad/session.js"></script>
			<script>
				$(document).ready(
						function() {
							findAllDisabledDates();
							try {
								
								setRadicadoAutocomplete();
								validateParamRadicado();
								
								waitingDialog.hide();
								var codigo;
								$('#filtroEstadoProcesal').prop('disabled', true);
								$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
								$("#messageExitoso").hide();
								ocultarMensajesError();
								cargarCombox();							
								directorioActual("Casos", "Visor de Casos");
								mostrarTabla();
								var token = $("#_csrf").val();
								var cdusuarioLogeado = $("#idusercreacion").val();
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
								waitingDialog.hide();
							} catch(e) {
								console.log(e);
								waitingDialog.hide();
							}
						});
			</script>

		</body>
	</c:when>
	<c:otherwise>
		<%@ include file="/WEB-INF/jsp/seguridad/errorNoAutorizacion.jsp"%>
	</c:otherwise>
</c:choose>
</html>