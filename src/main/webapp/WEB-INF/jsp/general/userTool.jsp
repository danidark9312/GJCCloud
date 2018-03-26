<%@ include file="/WEB-INF/jsp/modales/modalCambioContrasena.jsp"%>
<div class="margen-superior"></div>
<div id="breadcrumbs-logout">
	<div class="container">
		<div class="row">
			<div class="col-md-6" id="directorioActual"></div>
			<div class="col-md-6">
				<ul class="nav navbar-top-links navbar-right">
					<li class="dropdown">
						<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown">
							<sec:authentication property="principal.nombre" />
							&nbsp;
							<sec:authentication property="principal.apellido" />
							<span class="caret"></span>
						</a>
						<ul role="menu" class="dropdown-menu">
							<li>
								<!-- Reset a estilo responsivo de boostrap que cambia el color a este componente style="color: #999999  !important"-->
								<a href="#" onclick="Contrasena.abrirModal(true);" style="color: #999999  !important">
									<i class="fa fa-key"></i>
									<spring:message code="label.menu.administradores.cambiarContrasena" />
								</a>
							</li>
							<li>
								<!-- Reset a estilo responsivo de boostrap que cambia el color a este componente style="color: #999999  !important"-->
								<a href="j_spring_security_logout" style="color: #999999 !important">
									<i class="fa fa-sign-out"></i>
									<sprin:message code="label.menu.administradores.salir" />
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
