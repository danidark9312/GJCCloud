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
<title>INSPINIA | Dashboard v.4</title>
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body class="top-navigation">
	<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}" value="${_csrf.token}" />
	<div id="wrapper">
		<div id="page-wrapper" class="gray-bg">
			<%@ include file="/WEB-INF/jsp/general/menu.jsp"%>
			<%@ include file="/WEB-INF/jsp/general/userTool.jsp"%>
			<div class="wrapper wrapper-content">
				<div class="container"></div>
			</div>
			<%@ include file="/WEB-INF/jsp/general/pieDePagina.jsp"%>
		</div>
	</div>
	<!-- Mainly scripts -->
	<script src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- Session -->
	<script src="${pageContext.request.contextPath}/js/seguridad/session.js"></script>
	<!-- Custom and plugin javascript -->
	<script src="${pageContext.request.contextPath}/js/inspinia.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/pace/pace.min.js"></script>
	<!-- Flot -->
	<script src="${pageContext.request.contextPath}/js/plugins/flot/jquery.flot.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/flot/jquery.flot.resize.js"></script>
	<script>
		$(document).ready(function(){
			document.location.href = location.href + "home";
		});
	</script>
</body>
</html>
