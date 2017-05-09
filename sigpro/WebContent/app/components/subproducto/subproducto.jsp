<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.SecurityUtils"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div ng-controller="controlSubproducto as subproducto" class="maincontainer all_page">

	<script type="text/ng-template" id="map.html">
        <div class="modal-header">
            <h3 class="modal-title">Mapa de Ubicación</h3>
        </div>
        <div class="modal-body" style="height: 400px;">
            			<ui-gmap-google-map id="mainmap" ng-if="refreshMap" center="map.center" zoom="map.zoom" options="map.options" events="map.events"  >
							<ui-gmap-marker idkey="1" coords="posicion"></ui-gmap-marker>
						</ui-gmap-google-map>
		</div>
        <div class="modal-footer">
            <button class="btn btn-primary" type="button" ng-click="ok()">OK</button>
        </div>
    </script>
    
	<script type="text/ng-template" id="buscarPorSubproducto.jsp">
	    <%@ include file="/app/components/subproducto/buscarPorSubproducto.jsp"%>
	</script>
	<shiro:lacksPermission name="40010">
		<p ng-init="subproducto.redireccionSinPermisos()"></p>
	</shiro:lacksPermission>

	<div class="panel panel-default">
	   	<div class="panel-heading"><h3>Subproducto</h3></div>
	</div>
		<div class="subtitulo">
			{{ subproducto.objetoTipoNombre }} {{ subproducto.productoNombre }}
		</div>
	
  
	<div align="center" ng-hide="subproducto.esForma">
		<div class="col-sm-12 operation_buttons" align="right">
			<div class="btn-group">
				<shiro:hasPermission name="40040">
					<label class="btn btn-primary" ng-click="subproducto.nuevo()" uib-tooltip="Nuevo">
					<span class="glyphicon glyphicon-plus"></span> Nuevo</label>
				</shiro:hasPermission>
				<shiro:hasPermission name="40010">
					<label class="btn btn-primary" ng-click="subproducto.editar()" uib-tooltip="Editar">
					<span class="glyphicon glyphicon-pencil"></span> Editar</label>
				</shiro:hasPermission>
				<shiro:hasPermission name="40030">
					<label class="btn btn-danger" ng-click="subproducto.borrar()" uib-tooltip="Borrar">
					<span class="glyphicon glyphicon-trash"></span> Borrar</label>
				</shiro:hasPermission>
			</div>
		</div>
		<shiro:hasPermission name="40010">
			<div class="col-sm-12" align="center">
				<div style="height: 35px;">
					<div style="text-align: right;">
						<div class="btn-group" role="group" aria-label="">
							<a class="btn btn-default" href ng-click="subproducto.reiniciarVista()" role="button" uib-tooltip="Reiniciar la vista de la tabla" tooltip-placement="left">
								<span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
							</a>
						</div>
					</div>
				</div>
				<br/>
				<div id="grid1" ui-grid="subproducto.opcionesGrid"
					ui-grid-save-state ui-grid-move-columns ui-grid-resize-columns
					ui-grid-selection ui-grid-pinning ui-grid-pagination>
					<div class="grid_loading" ng-hide="!subproducto.mostrarCargando">
						<div class="msg">
							<span><i class="fa fa-spinner fa-spin fa-4x"></i> 
								<br />
								<br /> <b>Cargando, por favor espere...</b> 
							</span>
						</div>
					</div>
				</div>
				<br/>
			<div class="total-rows">Total de {{  subproducto.totalElementos + (subproducto.totalElementos == 1 ? " Subproducto" : " Subproductos" ) }}</div>
				<ul uib-pagination total-items="subproducto.totalElementos"
					ng-model="subproducto.paginaActual"
					max-size="subproducto.numeroMaximoPaginas"
					items-per-page="subproducto.elementosPorPagina"
					first-text="Primero" last-text="Último" next-text="Siguiente"
					previous-text="Anterior" class="pagination-sm"
					boundary-links="true" force-ellipses="true"
					ng-change="subproducto.cambioPagina()"></ul>
			</div>
		</shiro:hasPermission>

	</div>

	<div ng-show="subproducto.esForma" class="row second-main-form">
		<div class="page-header">
			<h2 ng-hide="!subproducto.esNuevo"><small>Nuevo Subproducto</small></h2>
			<h2 ng-hide="subproducto.esNuevo"><small>Edición de Subproducto</small></h2>
		</div>
		<div class="col-sm-12 operation_buttons" >
			<div class="btn-group" ng-hide="subproducto.esNuevo" >
				<label class="btn btn-default" ng-click="subproducto.irAActividades()" uib-tooltip="Actividades" tooltip-placement="bottom">
				<span class="glyphicon glyphicon-th-list"></span></label>
				<label class="btn btn-default" ng-click="subproducto.irARiesgos()" uib-tooltip="Riesgos" tooltip-placement="bottom">
				<span class="glyphicon glyphicon-warning-sign"></span></label>
			</div>
			<div class="btn-group" style="float: right;">
				<shiro:hasPermission name="40020">
					<label class="btn btn-success" ng-click="form.$valid ? subproducto.guardar() : ''" ng-disabled="!form.$valid" uib-tooltip="Guardar">
					<span class="glyphicon glyphicon-floppy-saved"></span> Guardar</label> 
				</shiro:hasPermission>
				<label class="btn btn-primary" ng-click="subproducto.cancelar()" uib-tooltip="Ir a Tabla">
				<span class="glyphicon glyphicon-list-alt"></span> Ir a Tabla</label>
			</div>
		</div>
		<div>
		<div class="col-sm-12">
			<form name="form" class="css-form">
				
					<div class="form-group">
						<label for="id" class="floating-label">ID {{subproducto.subproducto.id}}</label>
						<br/><br/> 
					</div>
				
				
					<div class="form-group">
						<input type="text" class="inputText" ng-model="subproducto.subproducto.nombre" ng-required="true" 
						ng-value="subproducto.subproducto.nombre" onblur="this.setAttribute('value', this.value);"/>
						<label class="floating-label">* Nombre</label>
					</div>
					
					<div class="form-group"  >
						<input type="number" ng-model="subproducto.subproducto.snip" class="inputText"
							ng-value="subproducto.subproducto.snip" onblur="this.setAttribute('value', this.value);">
						<label class="floating-label">SNIP</label>
					</div>
				
					<div class="form-group-row row" >
						<div class="form-group col-sm-2" >
					       <input type="number" class="inputText" ng-model="subproducto.subproducto.programa"  ng-maxlength="4" style="text-align: center" 
					       		ng-value="subproducto.subproducto.programa" onblur="this.setAttribute('value', this.value);" ng-maxlength="4" style="text-align: center"/>
					       <label for="iprog" class="floating-label">Programa</label>
						</div>
						<div class="form-group col-sm-2" >
						  <input type="number" class="inputText" ng-model="subproducto.subproducto.subprograma" 
						  		ng-value="subproducto.subproducto.subprograma" onblur="this.setAttribute('value', this.value);" ng-maxlength="4" style="text-align: center" />
						  <label for="isubprog" class="floating-label">Subprograma</label>
						</div>
						<div class="form-group col-sm-2" >
						  <input type="number" class="inputText" ng-model="subproducto.subproducto.proyecto_"  ng-maxlength="4" style="text-align: center" 
						  		ng-value="subproducto.subproducto.proyecto_" onblur="this.setAttribute('value', this.value);"/>
						  <label for="iproy_" class="floating-label">Proyecto</label>
						</div>
						<div class="form-group col-sm-2" >
						  <input type="number" class="inputText" ng-model="subproducto.subproducto.actividad" ng-maxlength="4" style="text-align: center" 
						  		ng-value="subproducto.subproducto.actividad" onblur="this.setAttribute('value', this.value);"/>
						  <label for="iobra" class="floating-label">Actividad</label>
						</div>
						<div class="form-group col-sm-2" >
						  <input type="number" class="inputText" ng-model="subproducto.subproducto.obra" ng-maxlength="4" style="text-align: center" 
						  		ng-value="subproducto.subproducto.obra" onblur="this.setAttribute('value', this.value);"/>
						  <label for="iobra" class="floating-label">Obra</label>
						</div>
						<div class="form-group col-sm-2" >
						  <input type="number" class="inputText" ng-model="subproducto.subproducto.fuente" ng-maxlength="4" style="text-align: center" 
						  		 ng-value="subproducto.subproducto.fuente" onblur="this.setAttribute('value', this.value);"/>
						  <label for="campo5" class="floating-label">Fuente</label>
						</div>
					</div>
					
					<div class="form-group">
				            	<input type="text" class="inputText" ng-model="subproducto.coordenadas" ng-readonly="true" 
				            		ng-value="subproducto.coordenadas" onblur="this.setAttribute('value', this.value);"
				            		ng-click="subproducto.open(subproducto.subproducto.latitud, subproducto.subproducto.longitud); "/>
				            	<span class="label-icon" ng-click="subproducto.open(subproducto.subproducto.latitud, subproducto.subproducto.longitud); "><i class="glyphicon glyphicon-map-marker"></i></span>
				            	<label class="floating-label">Coordenadas</label>
					</div>

					<div class="form-group" >
						<input type="text" class="inputText" ng-model="subproducto.subproducto.descripcion" 
							ng-value="subproducto.subproducto.descripcion" onblur="this.setAttribute('value', this.value);"/>
						<label for="campo2" class="floating-label">Descripción</label>
					</div>
					
					<div class="form-group" >
			            <input type="text" class="inputText" ng-model="subproducto.tipoNombre" ng-readonly="true" ng-required="true" 
			            	ng-value="subproducto.tipoNombre" onblur="this.setAttribute('value', this.value);" 
			            	ng-click="subproducto.buscarTipo()"/>
			            <span class="label-icon" ng-click="subproducto.buscarTipo()"><i class="glyphicon glyphicon-search"></i></span>
			            <label class="floating-label">* Tipo</label>
			        </div>
			        
			        <div class="form-group">
			            <input type="text" class="inputText" ng-model="subproducto.unidadEjecutoraNombre" ng-readonly="true" ng-required="true" 
			            	ng-value="subproducto.unidadEjecutoraNombre" onblur="this.setAttribute('value', this.value);" 
			            	ng-click="subproducto.buscarUnidadEjecutora()"/>
			            <span class="label-icon" ng-click="subproducto.buscarUnidadEjecutora()"><i class="glyphicon glyphicon-search"></i></span>
			          <label class="floating-label">* Unidad Ejecutora</label>
			        </div>
			        
			        <div class="form-group" ng-repeat="campo in subproducto.camposdinamicos">
						<div ng-switch="campo.tipo">
								<div ng-switch-when="texto" class="form-group" >
									<input type="text" id="{{ 'campo_'+campo.id }}" ng-model="campo.valor" class="inputText" 
										ng-value="campo.valor" onblur="this.setAttribute('value', this.value);"/>	
									<label for="campo.id" class="floating-label">{{ campo.label }}</label>
								</div>
								<div ng-switch-when="entero" class="form-group" >
									<input type="number" id="{{ 'campo_'+campo.id }}" numbers-only ng-model="campo.valor" class="inputText"   
									ng-value="campo.valor" onblur="this.setAttribute('value', this.value);"/>
									<label for="campo.id" class="floating-label">{{ campo.label }}</label>
								</div>
								<div ng-switch-when="decimal" class="form-group" >
									<input type="number" id="{{ 'campo_'+campo.id }}" ng-model="campo.valor" class="inputText"  
									ng-value="campo.valor" onblur="this.setAttribute('value', this.value);"/>
									<label for="campo.id" class="floating-label">{{ campo.label }}</label>
								</div>
								<div ng-switch-when="booleano" class="form-group" >
									<input type="checkbox" id="{{ 'campo_'+campo.id }}" ng-model="campo.valor" />
									<label for="campo.id" class="floating-label">{{ campo.label }}</label>
								</div>
								<div ng-switch-when="fecha" class="form-group" >
									<input type="text" id="{{ 'campo_'+campo.id }}" class="inputText" uib-datepicker-popup="{{subproducto.formatofecha}}" ng-model="campo.valor" is-open="campo.isOpen"
														datepicker-options="subproducto.fechaOptions" close-text="Cerrar" current-text="Hoy" clear-text="Borrar" ng-click="subproducto.abrirPopupFecha($index)"
														ng-value="campo.valor" onblur="this.setAttribute('value', this.value);"/>
														<span class="label-icon" ng-click="subproducto.abrirPopupFecha($index)">
															<i class="glyphicon glyphicon-calendar"></i>
														</span>
									<label for="campo.id" class="floating-label">{{ campo.label }}</label>
								</div>
								<div ng-switch-when="select" class="form-group" >
									<select id="{{ 'campo_'+campo.id }}" class="inputText" ng-model="campo.valor">
													<option value="">Seleccione una opción</option>
													<option ng-repeat="number in campo.opciones"
														ng-value="number.valor">{{number.label}}</option>
								</select>
									<label for="campo.id" class="floating-label">{{ campo.label }}</label>
								</div>
							</div>
					</div>
					
				<div class="panel panel-default">
					<div class="panel-heading label-form" style="text-align: center;">Datos de auditoría</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group" style="text-align: right">
									<label for="usuarioCreo" class="label-form">Usuario que creo</label>
				  					<p>{{ subproducto.subproducto.usuarioCreo }}</pl>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label for="fechaCreacion" class="label-form">Fecha de creación</label>
				  					<p>{{ subproducto.subproducto.fechaCreacion }}</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group" style="text-align: right">
									<label for="usuarioActualizo" class="label-form">Usuario que actualizo</label>
				  					<p>{{ subproducto.subproducto.usuarioActualizo }}</p>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label for="fechaActualizacion" class="label-form">Fecha de actualizacion</label>
				  					<p>{{ subproducto.subproducto.fechaActualizacion }}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			</div>
		</div>
		<div class="col-sm-12 operation_buttons" align="right">
		<div align="center" class="label-form">Los campos marcados con * son obligatorios</div>
			<div class="btn-group">
				<shiro:hasPermission name="40020">
					<label class="btn btn-success" ng-click="form.$valid ? subproducto.guardar() : ''" ng-disabled="!form.$valid" uib-tooltip="Guardar">
					<span class="glyphicon glyphicon-floppy-saved"></span> Guardar</label> 
				</shiro:hasPermission>
				<label class="btn btn-primary" ng-click="subproducto.cancelar()" uib-tooltip="Ir a Tabla">
				<span class="glyphicon glyphicon-list-alt"></span> Ir a Tabla</label>
			</div>
		</div>
	</div>

</div>