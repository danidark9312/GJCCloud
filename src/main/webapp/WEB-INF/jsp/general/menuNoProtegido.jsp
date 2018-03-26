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
	.navbar-nav > li > a:hover{
		background-color: rgba(88,101,127,0.5) !important;
	}
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
			<div class="container">
				<div class="navbar-header">
					<button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse"
						class="navbar-toggle collapsed" type="button">
						<i class="fa fa-reorder"></i>
					</button>
					<a href="#" class="navbar-brand" style="padding-top: 0%;">
						<img src="img/logo-header.png" class="logo-desktop">
					</a>
					<div class="navbar-collapse collapse" id="navbar">
						<ul class="nav navbar-nav">
							<li class="dropdown">
								<a aria-expanded="false" role="button" class="dropdown-toggle" href="visorCaso"
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
									<li>
										<a href="maestroActividad">
											<spring:message code="label.menu.administradores.actividades" />
										</a>
									</li>
									<li>
										<a href="maestroRoles">
											<spring:message code="label.menu.administradores.roles" />
										</a>
									</li>
									<li>
										<a href="maestroTipoCaso">
											<spring:message code="label.menu.administradores.tipoCaso" />
										</a>
									</li>
									<li>
										<a href="maestroUsuarios">
											<spring:message code="label.menu.administradores.usuarios" />
										</a>
									</li>
								</ul>
							</li>
							<li class="dropdown">
								<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"
									style="color: #ffffff">
									<spring:message code="label.menu.title.casos" />
									<span class="caret"></span>
								</a>
								<ul role="menu" class="dropdown-menu">
									<li>
										<a href="visorCaso">
											<spring:message code="label.menu.casos.visorCaso" />
										</a>
									</li>
									<li>
										<a href="papelera">
											<spring:message code="label.title.papelera" />
										</a>
									</li>
								</ul>
							</li>
							<li class="dropdown">
								<a aria-expanded="false" role="button" href="#" class="dropdown-toggle" data-toggle="dropdown"
									style="color: #ffffff">
									<spring:message code="label.menu.title.reportes" />
									<span class="caret"></span>
								</a>
								<ul role="menu" class="dropdown-menu">
									<li>
										<a href="audiencia">
											<spring:message code="label.menu.reportes.audiencias" />
										</a>
									</li>
									<li>
										<a href="caducidadCasos">
											<spring:message code="label.menu.reportes.caducidadCasos" />
										</a>
									</li>
									<li>
										<a href="documentosPendientes">
											<spring:message code="label.menu.reportes.documentosPendientes" />
										</a>
									</li>
									<li>
										<a href="recursosPendientes">
											<spring:message code="label.menu.reportes.recursosPendientes" />
										</a>
									</li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</nav>
	</div>
</div>