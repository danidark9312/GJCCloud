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
	<title>GTH | Login</title>
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body class="top-navigation bg-login">
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
									<i class="fa fa-lock"></i> <spring:message code="label.campo.ingresarAplicacion" />
								</h3>
								<hr>
								<input type="hidden" id="mensajeError" name="mensajeError" value="${mensajeError}" />
								<form action="${pageContext.request.contextPath}/login/authenticate" method="POST" role="form"
									class="form-horizontal" id="login-form">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

									<div class="form-group">
										<label class="col-lg-4 control-label"><spring:message code="label.campo.nombreUsuario" /></label>
										<div class="col-lg-8">
											<input type="email" class="form-control" required="" id="user-email" name="username" tabindex="1">
										</div>
									</div>
									<div class="form-group">
										<label class="col-lg-4 control-label"><spring:message code="label.campo.contrasena" /></label>
										<div class="col-lg-8">
											<input type="password" class="form-control" required="" id="user-password" name="password" tabindex="2">
										</div>
									</div>
									<div class="form-group">
										<div class="col-lg-12 text-right">
											<button class="btn btn-sm btn-white" type="submit" tabindex="3">
												<spring:message code="button.label.login" />
											</button>
										</div>
									</div>
									<hr>
									<div id="password">
										<a class="btn btn-sm btn-white" href="${pageContext.request.contextPath}/sendEmail" tabindex="3">
											<spring:message code="label.label.olvidarContrasenia" />
										</a>
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
	<!-- Custom and plugin javascript -->

	<script src="${pageContext.request.contextPath}/js/plugins/pace/pace.min.js"></script>

	<script>
		$(document).ready(function(){
			$("#divMensajeError").hide();
			if ($("#mensajeError").val() != ""){
				$("#divMensajeError p").text($("#mensajeError").val());
				$("#divMensajeError").show();
			}
		});
	</script>
</body>
</html>