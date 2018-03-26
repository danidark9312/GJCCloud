<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<% 
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
    <title><spring:message code="software.title"/></title>
    
    <script>
		var contexto = '${pageContext. request. contextPath}';
	</script>
    
    <sitemesh:write property="head"/>
</head>

<!-- possible classes: minified, no-right-panel, fixed-ribbon, fixed-header, fixed-width-->
		<sec:authorize access="isAnonymous()">
		<body class="top-navigation bg-login">
		<header id="header">
			<!--<span id="logo"></span>-->

			
			<!--  
			<span id="login-header-space"> <span class="hidden-mobile">Need an account?</span> <a href="register.html" class="btn btn-danger">Creat account</a> </span>
			-->
		</header>
		</body>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
		<body >
		<!-- HEADER -->
		</body>
		<header id="header">
			

			

		</header>
		<!-- END HEADER -->
		</sec:authorize>
    <sitemesh:write property="body"/>
</body>
</html>