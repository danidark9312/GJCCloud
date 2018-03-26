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
	
	<title>Casos</title>
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="css/animate.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/plugins/chosen/chosen.css" rel="stylesheet">
	<link href="css/custom.css" rel="stylesheet">
	<link href="js/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">
	<style>
	
	.ui-datepicker{
	font-size: 20px;
	}
	.ui-datepicker-month{
	font-size: 15px;
	}
	.ui-datepicker-year{
	font-size: 15px;
	}
	
	</style>
</head>
<c:choose>
	<c:when test="${not empty permisos.rol.descripcion && cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
		<body class="top-navigation">
			<div id="wrapper">
				<div id="page-wrapper" class="gray-bg">
					<%@ include file="/WEB-INF/jsp/general/menu.jsp"%>
					<%@ include file="/WEB-INF/jsp/general/userTool.jsp"%>
					<div class="wrapper wrapper-content">
						<div class="container">
							<div class="contenido">
								<div class="container">
									<div class="row">
										<div class="col-md-12 col-lg-12">
											<div class="ibox float-e-margins">
												<div class="ibox-title">
													<h5>
														<spring:message code="label.maestroCalendarioJudicial.titulo" />
													</h5>
												</div>
												<div class="ibox-content">
													<form role="form" class="panel-body">
														<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}" value="${_csrf.token}" />
														<input type='hidden' name='idusercreacion' id='idusercreacion'
															value='<sec:authentication property="principal.id"/>' />
														<div class="row">
															<div class="alert alert-success" id="messageExitoso">
																&nbsp; <a class="alert-link" href="#">&nbsp;</a>.
															</div>
															<div class="alert alert-danger" id="messageError">
																&nbsp; <a class="alert-link" href="#">&nbsp;</a>.
															</div>
														</div>
														<div class="row">
															<div class="col-md-8">
																<div class="form-group col-md-12" id="datepickerDiv">
																</div>
															</div>
															<div class="col-md-4">
																<table id="tablaListadoCalendarios"
																	class='table table-striped table-hover'>
																	<thead>
																		<tr class='tr_titulo'>
																			<th>Dias judiciales no habiles</th>
																			<th>Accion</th>
																		</tr>
																	</thead>
																</table>
															</div>
															<div></div>
														</div>
														<div class="row">
															<div class="col-sm-12" style="text-align: center;">
																<a href="javascript:addFecha()"
																	class="btn btn-success"
																	style="background-color: #449D44;"> <i
																	class="glyphicon glyphicon-plus"></i>&nbsp;Adicionar
																	Fecha
																</a>
															</div>
															<div></div>
														</div>
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
							</div>
						</div>
						<%@ include file="/WEB-INF/jsp/general/pieDePagina.jsp"%>
					</div>
				</div>
				<!-- Ventanas modales -->
				
				<%@ include file="/WEB-INF/jsp/seguridad/modalMensajeFinalizacionSesion.jsp"%>
				<%@ include file="/WEB-INF/jsp/modales/modalEscalamientoNotificacion.jsp"%>
				<!-- permisos -->
				<%@ include file="/WEB-INF/jsp/seguridad/permisos.jsp" %>

				<!-- Mainly scripts -->
				<script src="js/jquery-2.1.1.js"></script>
				<script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>
				             
				<script src="js/bootstrap.min.js"></script>
				<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
				<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
				<script src="js/plugins/jeditable/jquery.jeditable.js"></script>
				<script src="js/plugins/msjs/waitingfor.js"></script>
				<!-- permisos -->
				<script src="js/seguridad/permisos.js"></script>

				<!-- Data Tables -->
				<script src="js/plugins/dataTables/jquery.dataTables.js"></script>
				<script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
				<script src="js/plugins/dataTables/dataTables.responsive.js"></script>
				<script src="js/plugins/dataTables/dataTables.tableTools.min.js"></script>
				<script src="js/maestros/maestroCalendarioJudicial.js"></script>

				<!-- Custom and plugin javascript -->
				<script src="js/inspinia.js"></script>
				<script src="js/plugins/pace/pace.min.js"></script>

				<!-- JQUERY VALIDATE -->
				<script src="${pageContext.request.contextPath}/js/plugins/validate/jquery.validate.min.js"></script>

				<script src="js/general/fechas.js"></script>
				<script src="js/general/cargarComboBox.js"></script>
				<script src="js/general/general.js"></script>
				<script src="js/maestros/maestroEscalamientoNotificacion.js"></script>

				<!-- Chosen -->
				<script src="js/plugins/chosen/chosen.jquery.js"></script>
		<!-- Jquery Validate -->
			<script src="js/plugins/validate/jquery.validate.min.js"></script>
			<script src="js/plugins/select2/select2.min.js"></script>

				<!-- finalizacin de Sesion -->
				<script src="js/seguridad/session.js"></script>

				<script>
					$(document).ready(function(){
						startDatepicker();
						startDatatable();
						findAllDates();
						directorioActual("Administradores", "Calendario Judicial");
						$("#messageExitoso").hide();
						$("#messageError").hide();
						session.tiempo(session.tiempoSesion);
					});
				</script>
			</div>
		</body>
	</c:when>
	<c:otherwise>
		<%@ include file="/WEB-INF/jsp/seguridad/errorNoAutorizacion.jsp"%>
	</c:otherwise>
</c:choose>			
</html>