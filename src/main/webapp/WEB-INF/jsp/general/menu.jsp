<!--Resets al style
	Reset a menu responsivo no visible 
	Reset a menu opciones no se superponga sobre logo GJA
	No se hacen en .css para no incluir el css en todos los jsp
-->
<style>
@media (max-width: 767px){
	.dropdown-menu > li > a {
	    color: #FFF !important;
	}
	.dropdown-menu > li > a:hover{
		background-color: rgba(88,101,127,0.5) !important;
	}
/* 	.navbar-nav > li > a:hover{ */
/* 		background-color: rgba(88,101,127,0.5) !important; */
/* 	} */
	.open > li > a:hover{
		background-color: rgba(88,101,127,0.5) !important;
	}
	.top-navigation > li > a:hover{
		background-color: rgba(88,101,127,0.5) !important;
	}
}
@media (min-width: 768px){
	.navbar-fixed-top .navbar-collapse, .navbar-static-top .navbar-collapse, .navbar-fixed-bottom .navbar-collapse {
		left: 50%;
    	position: relative;
	}
}
</style>
<div class="menu-top">
	<div class="row bg-menu">
		<nav class="navbar navbar-static-top" role="navigation">
			<div
				style="width: 25%; height: 140px; float: left; background-color: #ecedef;padding-left: 4%;padding-top: 1px"">
				<a href="#" class="navbar-brand" style="padding-top: 0%;"> <img
					src="img/logo-header.png" class="logo-desktop" style="width: 80%">
				</a>
			</div>
			
			<div style="width: 10%;height: 140px;float: left;background-color: #ecedef;border-top: 120px solid #ecedef;border-right: 100px solid #314b7e;">
			</div>
			<div class="container" style="   width: 65%;float: left;"> 
				<div class="navbar-header" style="position: absolute; bottom: 14px;">
					<button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse"
						class="navbar-toggle collapsed" type="button">
						<i class="fa fa-reorder"></i>
					</button>
					<div class="navbar-collapse collapse" id="navbar">
						<ul class="nav navbar-nav">
							<li class="dropdown">
								<a aria-expanded="false" role="button" class="dropdown-toggle" href="home"
									style="color: #ffffff">
									<spring:message code="label.menu.title.inicio" />
								</a>
							</li>
							<li class="dropdown">
								<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"
									style="color: #ffffff">
									<spring:message code="label.menu.title.administradores" />
									<span class="caret"></span>
								</a>
								<ul role="menu" class="dropdown-menu">
									<c:forEach var="rolAccionOpcion" items="${menuPermisos.listaRolAccionOpcion}">
										<c:forEach var="menuOpcion" items="${menuPermisos.opciones}">
											<c:if test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Maestro de Actividades'}">
												<li>
													<a href="maestroActividad">
														<spring:message code="label.menu.administradores.actividades" />
													</a>
												</li>
											</c:if>
											<c:if test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Maestro de Roles'}">
												<li>
													<a href="maestroRoles">
														<spring:message code="label.menu.administradores.roles" />
													</a>
												</li>
											</c:if>
											<c:if test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Maestro de Tipos de Caso'}">
												<li>
													<a href="maestroTipoCaso">
														<spring:message code="label.menu.administradores.tipoCaso" />
													</a>
												</li>
											</c:if>
											<c:if test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Maestro de Usuarios'}">
												<li>
													<a href="maestroUsuarios">
														<spring:message code="label.menu.administradores.usuarios" />
													</a>
												</li>
											</c:if>
											<c:if test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Maestro de Reasignacion de Casos'}">
												<li>
													<a href="maestroReasignacionCasos">
														<spring:message code="label.menu.administradores.reasignacionCasos" />
													</a>
												</li>
											</c:if>
											<c:if test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Maestro Calendario Judicial'}">
												<li>
													<a href="maestroCalendarioJudicial">
														<spring:message code="label.menu.administradores.calendarioJudicial" />
													</a>
												</li>
											</c:if>
										</c:forEach> 
									</c:forEach>
								</ul>
							</li>
							<li class="dropdown">
								<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"
									style="color: #ffffff">
									<spring:message code="label.menu.title.casos" />
									<span class="caret"></span>
								</a>
								<ul role="menu" class="dropdown-menu">
									<c:forEach var="rolAccionOpcion" items="${menuPermisos.listaRolAccionOpcion}">
										<c:forEach var="menuOpcion" items="${menuPermisos.opciones}">
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Visor de casos'}">
												<li>
													<a href="visorCaso">
														<spring:message code="label.menu.casos.visorCaso" />
													</a>
												</li>
											</c:if>
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Papelera'}">
												<li>
													<a href="papelera">
														<spring:message code="label.title.papelera" />
													</a>
												</li>
											</c:if>											
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Lista de Clientes'}">
												<li>
													<a href="listaClientes">
														<spring:message code= "label.title.clientes"/>
													</a>
												</li>
											</c:if>																						
											<c:if
 												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Listado de Archivos'}"> 
 												<li>
 													<a href="listaArchivos">
 														<spring:message code= "label.title.archivos"/>
 													</a>
 												</li>
 											</c:if>
										</c:forEach>
									</c:forEach>
								</ul>
							</li>
							<li class="dropdown">
								<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"
									style="color: #ffffff">
									<spring:message code="label.menu.title.reportes" />
									<span class="caret"></span>
								</a>
								<ul role="menu" class="dropdown-menu">									
									<c:forEach var="rolAccionOpcion" items="${menuPermisos.listaRolAccionOpcion}">
										<c:forEach var="menuOpcion" items="${menuPermisos.opciones}">
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Reporte Audiencia'}">
												<li>
													<a href="audiencia">
														<spring:message code="label.menu.reportes.audiencias" />
													</a>
												</li>
											</c:if>
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Reporte Auditoria'}">
												<li>
													<a href="auditoria">
														<spring:message code="label.title.reportes.auditoria" />
													</a>
												</li>
											</c:if>
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Reporte Caducidad de Casos'}">
												<li>
													<a href="caducidadCasos">
														<spring:message code="label.menu.reportes.caducidadCasos" />
													</a>
												</li>
											</c:if>
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Reporte Documentos Pendientes'}">
												<li>
													<a href="documentosPendientes">
														<spring:message code="label.menu.reportes.documentosPendientes" />
													</a>
												</li>
											</c:if>
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Recursos Pendientes'}">
												<li>
													<a href="recursosPendientes">
														<spring:message code="label.menu.reportes.recursosPendientes" />
													</a>
												</li>
											</c:if>
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Reporte Tareas Proximas a Vencer'}">
												<li>
													<a href="tareasProximoVencimiento">
														<spring:message code="label.menu.reportes.tareasProximoVencimiento" />
													</a>
												</li>
											</c:if>
											<c:if
												test="${cons.getParametroSiCorto() == rolAccionOpcion.snLectura && rolAccionOpcion.rolAccionOpcionPK.codigoOpcion == menuOpcion.codigo && menuOpcion.nombre == 'Reporte Auditoria'}">
												<li>
													<a href="prestamoReporte">
														<spring:message code="label.menu.reportes.prestamo" />
													</a>
												</li>
											</c:if>
										</c:forEach>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</nav>
	</div>
</div>