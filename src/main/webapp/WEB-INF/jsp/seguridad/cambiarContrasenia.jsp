<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
	<title></title>
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
	<script src="js/seguridad/cambiarContrasenia.js"></script>
</head>
<body class="top-navigation bg-login">
	<input type="hidden" name="email" id="email" value="${param.id}">
	<input type="hidden" name="token" id="token" value="${param.token}">
	<div id="wrapper">
		<div id="login">
			<div class="bg-negro-70">
				<div class="container">
					<div class="row">
						<div class="col-sm-6 col-lg-6 text-center intro-login">
							<div class="wrapper-content">
								<img class="logo-login" src="img/logo-login.png" style="text-align: left">
								<hr>
								<p class="text-right texto-intro-login">							​​
									<spring:message code="label.mensaje.loguin" />
								</p>
							</div>
						</div>
						<div class="col-sm-6 col-lg-6 form-login">
							<div class="wrapper-content">
								<h3>
									Reiniciar Contraseña
								</h3>
								<hr>								
								<form method="POST" role="form"	class="form-horizontal" id="cambiarContrasenia-form">
								<input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" />																											
									<div class="form-group">
										<label class="col-lg-4 control-label">Digite su nueva contraseña</label>
										<div class="col-lg-8">
											<input type="password" class="form-control" required="" id="nuevaContrasenia" name="nuevaContrasenia" tabindex="2" onkeydown="codigoTecla()">
										</div>
									</div>
									<hr>
									<div class="form-group">
										<label class="col-lg-4 control-label">Confirme su Contraseña</label>
										<div class="col-lg-8">
											<input type="password" class="form-control" required="" id="confirmarcontrasenia" name="confirmarcontrasenia"
											 onkeyup="checkPass(); return false;" tabindex="2" onkeypress="codigoTecla()">
											<span id="confirmMessage" class="confirmMessage"></span>
										</div>
									</div>									
									<hr>
									<div align="right">
										<div id="email" >
											<input type="Button" value="Enviar" id="checkemail" class="btn btn-sm btn-white"  onclick="cambiarContrasenia()"></input>
										</div>
										<br>
										<div id="canelar">
											<a class="btn btn-sm btn-white" href="${pageContext.request.contextPath}/login" tabindex="3">
												Cancelar
											</a>
										</div>
									</div>
								</form>								
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12" id="divMensajeError">
							<p style="color: rgb(179, 13, 13);"></p>
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
					<div class="col-lg-8 text-right"><spring:message code="software.footer" />&nbsp;<spring:message code="software.footer.version" />
				</div>
			</div>
		</div>
	</div>

	<!-- Mainly scripts -->
	<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/msjs/waitingfor.js"></script>			 
	 <script src="${pageContext.request.contextPath}/js/general/general.js"></script>
	<!-- JQUERY BlockUI -->
	<script src="js/plugins/jqueryBlockUI/jquery.blockUI.js"></script>
	
	<script>
				$(document).ready(
						function() {
							try {
								waitingDialog.hide();
							} catch(e) {
								console.log(e);
							}
							// $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
						});
	</script>
	 
</body>
</html>