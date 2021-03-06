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
	<link href="css/custom.css" rel="stylesheet">
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
		<body class="top-navigation">
			<div id="wrapper">
				<div id="page-wrapper" class="gray-bg">
					<%@ include file="/WEB-INF/jsp/general/menu.jsp"%>
					<%@ include file="/WEB-INF/jsp/general/userTool.jsp"%>
					<div class="wrapper wrapper-content">
						<div class="container">
							<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<input type='hidden' name='idusercreacion' id='idusercreacion'
								value='<sec:authentication property="principal.id" />' />
							<div class="row">
								<div class="col-lg-12">
									<div class="ibox float-e-margins">
										<div class="ibox-title">
											<div class="row">
												<div class="col-lg-10">
													<h5>
														<spring:message code="label.column.caso" />
													</h5>
													<div class="ibox-tools">
														<a class="collapse-link">
															<i class="fa fa-chevron-up"></i>
														</a>
														<a class="dropdown-toggle" data-toggle="dropdown" href="#">
															<i class="fa fa-wrench"></i>
														</a>
														<ul class="dropdown-menu dropdown-user">
															<li>
																<a href="#"></a>
															</li>
														</ul>
													</div>
												</div>
												<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
													<div class="col-lg-2">
														<a data-toggle="modal" class="btn btn-primary"   href="#modal-nuevoCaso"
															style="float: right" onclick="cargarFormularios()">
															<spring:message code="button.label.nuevoCaso" />
														</a>
													</div>
												</c:if>
											</div>
										</div>
										<div class="ibox-content">
											<table class="table table-striped table-bordered table-hover dataTables-example">
												<thead>
													<tr>
														<th>
															<spring:message code="label.column.id" />
														</th>
														<th>
															<spring:message code="label.column.completed" />
														</th>
														<th>Platform(s)</th>
														<th>Engine version</th>
														<th>CSS grade</th>
													</tr>
												</thead>
												<tbody>
													<tr class="gradeX">
														<td>Trident</td>
														<td>Internet Explorer 4.0</td>
														<td>Win 95+</td>
														<td class="center">4</td>
														<td class="center">X</td>
													</tr>
													<tr class="gradeC">
														<td>Trident</td>
														<td>Internet Explorer 5.0</td>
														<td>Win 95+</td>
														<td class="center">5</td>
														<td class="center">C</td>
													</tr>
													<tr class="gradeA">
														<td>Trident</td>
														<td>Internet Explorer 5.5</td>
														<td>Win 95+</td>
														<td class="center">5.5</td>
														<td class="center">A</td>
													</tr>
													<tr class="gradeA">
														<td>Trident</td>
														<td>Internet Explorer 6</td>
														<td>Win 98+</td>
														<td class="center">6</td>
														<td class="center">A</td>
													</tr>
													<tr class="gradeA">
														<td>Trident</td>
														<td>Internet Explorer 7</td>
														<td>Win XP SP2+</td>
														<td class="center">7</td>
														<td class="center">A</td>
													</tr>
													<tr class="gradeA">
														<td>Trident</td>
														<td>AOL browser (AOL desktop)</td>
														<td>Win XP</td>
														<td class="center">6</td>
														<td class="center">A</td>
													</tr>
													<tr class="gradeA">
														<td>Gecko</td>
														<td>Firefox 1.0</td>
														<td>Win 98+ / OSX.2+</td>
														<td class="center">1.7</td>
														<td class="center">A</td>
													</tr>
													<tr class="gradeA">
														<td>Gecko</td>
														<td>Firefox 1.5</td>
														<td>Win 98+ / OSX.2+</td>
														<td class="center">1.8</td>
														<td class="center">A</td>
													</tr>
												</tbody>
												<tfoot>
													<tr>
														<th>Rendering engine</th>
														<th>Browser</th>
														<th>Platform(s)</th>
														<th>Engine version</th>
														<th>CSS grade</th>
													</tr>
												</tfoot>
											</table>
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
			<%@ include file="/WEB-INF/jsp/casos/nuevoCaso.jsp"%>
			<!-- Mainly scripts -->
			<script src="js/jquery-2.1.1.js"></script>
			<script src="js/bootstrap.min.js"></script>
			<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
			<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
			<script src="js/plugins/jeditable/jquery.jeditable.js"></script>
			<script src="js/plugins/msjs/waitingfor.js"></script>
			<!-- permisos -->
			<%@ include file="/WEB-INF/jsp/seguridad/permisos.jsp"%>
			<!-- permisos -->
			<script src="js/seguridad/permisos.js"></script>
			<!-- Data Tables -->
			<script src="js/plugins/dataTables/jquery.dataTables.js"></script>
			<script src="js/plugins/dataTables/dataTables.bootstrap.js"></script>
			<script src="js/plugins/dataTables/dataTables.responsive.js"></script>
			<script src="js/plugins/dataTables/dataTables.tableTools.min.js"></script>
			<script src="js/general/fechas.js"></script>
			<script src="js/casos/caso.js"></script>
			<script src="js/general/general.js"></script>
			<!-- Input Mask-->
			<script src="js/plugins/jasny/jasny-bootstrap.min.js"></script>
			<!--     <script src="js/maestros/maestroActividad.js"></script> -->
			<!-- Custom and plugin javascript -->
			<script src="js/inspinia.js"></script>
			<script src="js/plugins/pace/pace.min.js"></script>
			<!-- JQUERY VALIDATE -->
			<script src="${pageContext.request.contextPath}/js/plugins/validate/jquery.validate.min.js"></script>
			<script src="js/general/cargarComboBox.js"></script>
			<script src="js/general/general.js"></script>
			<script>
					$(document).ready(function(){
						modalClose();

						try{
							waitingDialog.hide();
						}catch(e){
							console.log(e);
						}
						//$("#messageExitoso").hide();
						$("#messageError").hide();
						$("div[name=messageExitoso]").each(function(ind, dato){
							$(dato).hide();
						});

						cargarCombox();
						directorioActual("Casos", "Nuevo Caso");

						$('.dataTables-example').dataTable({
							responsive : true,
							"dom" : 'T<"clear">lfrtip',
							"tableTools" : {
								"sSwfPath" : "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
							}
						});

						/* Init DataTables */
						var oTable = $('#editable').dataTable();

						/* Apply the jEditable handlers to the table */
						oTable.$('td').editable('../example_ajax.php', {
							"callback" : function(sValue, y){
								var aPos = oTable.fnGetPosition(this);
								oTable.fnUpdate(sValue, aPos[0], aPos[1]);
							},
							"submitdata" : function(value, settings){
								return {
									"row_id" : this.parentNode.getAttribute('id'),
									"column" : oTable.fnGetPosition(this)[2]
								};
							},
							"width" : "90%",
							"height" : "100%"
						});

					});
					function fnClickAddRow(){
						$('#editable').dataTable().fnAddData([ "Custom row", "New row", "New row", "New row", "New row" ]);
					}
				</script>
		</body>
	</c:when>
	<c:otherwise>
		<%@ include file="/WEB-INF/jsp/seguridad/errorNoAutorizacion.jsp"%>
	</c:otherwise>
</c:choose>
</html>