<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="org.apache.shiro.SecurityUtils" %>
	<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
	<div ng-controller="metaController as metac" class="maincontainer all_page" id="title">
		<h3>Metas de {{ metac.nombreTipoPcp }}</h3><br/>
		<h4>{{ metac.nombrePcp }}</h4><br/>
		<div class="row" align="center" ng-hide="metac.mostraringreso">
			<div class="col-sm-12 operation_buttons" align="right">
				<div class="btn-group">
			       <shiro:hasPermission name="17040">
			       		<label class="btn btn-primary" ng-click="metac.nueva()">Nueva</label>
			       </shiro:hasPermission> 
			       <shiro:hasPermission name="17010"><label class="btn btn-primary" ng-click="metac.editar()">Editar</label></shiro:hasPermission>
			       <shiro:hasPermission name="17030">
			       		<label class="btn btn-primary" ng-click="metac.borrar()">Borrar</label>
			       </shiro:hasPermission>
			        
			        
    			</div>				
    		</div>
    		<shiro:hasPermission name="17010">
    		<div class="col-sm-12" align="center">
    			<div style="height: 35px;">
					<div style="text-align: right;"><div class="btn-group" role="group" aria-label="">
						<a class="btn btn-default" href ng-click="metac.reiniciarVista()" role="button" uib-tooltip="Reiniciar la vista de la tabla" tooltip-placement="left"><span class="glyphicon glyphicon-repeat" aria-hidden="true"></span></a>
					</div>
					</div>
				</div>
				<br/>
				<div id="maingrid" ui-grid="metac.gridOptions" ui-grid-save-state 
						ui-grid-move-columns ui-grid-resize-columns ui-grid-selection ui-grid-pinning ui-grid-pagination class="grid">
					<div class="grid_loading" ng-hide="!metac.mostrarcargando">
				  	<div class="msg">
				      <span><i class="fa fa-spinner fa-spin fa-4x"></i>
						  <br /><br />
						  <b>Cargando, por favor espere...</b>
					  </span>
					</div>
				  </div>
				</div>
				<ul uib-pagination total-items="metac.totalMetas" 
						ng-model="metac.paginaActual" 
						max-size="metac.numeroMaximoPaginas" 
						items-per-page="metac.elementosPorPagina"
						first-text="Primero"
						last-text="Último"
						next-text="Siguiente"
						previous-text="Anterior"
						class="pagination-sm" boundary-links="true" force-ellipses="true"
						ng-change="metac.cambioPagina()"
				></ul>
			</div>
    		</shiro:hasPermission>
    		
		</div>
		<div class="row main-form" ng-show="metac.mostraringreso">
			<h4 ng-hide="!metac.esnueva">Nueva meta</h4>
			<h4 ng-hide="metac.esnueva">Edición de meta</h4>
			<div class="col-sm-12 operation_buttons" align="right">
				<div class="btn-group">
					<shiro:hasPermission name="17020">
			        	<label class="btn btn-success" ng-click="form.$valid ? metac.guardar() : ''" ng-disabled="!form.$valid">Guardar</label>
			        </shiro:hasPermission>
			        <label class="btn btn-primary" ng-click="metac.irATabla()">Ir a Tabla</label>
    			</div>
    		</div>
			
			<div class="col-sm-12">
				<form name="form">
						<div class="form-group">
							<label for="id">ID</label>
    						<p class="form-control-static">{{ metac.meta.id }}</p>
						</div>
						<div class="form-group">
							<label for="nombre">* Nombre</label>
    						<input type="text" class="form-control" id="nombre" placeholder="Nombre" ng-model="metac.meta.nombre" ng-required="true">
						</div>
						<div class="form-group">
							<label for="descripcion">Descripción</label>
    						<input type="text" class="form-control" id="descripcion" placeholder="Descripción" ng-model="metac.meta.descripcion">
						</div>
						<div class="form-group">
							<label for="campo1">* Tipo Meta</label>
							<select  class="form-control" ng-model="metac.meta.tipoMetaId" ng-required="true">
								<option value="">Seleccione una opción</option>
								<option ng-repeat="opcion in metac.metatipos"
									ng-selected="option.selected = metac.meta.tipoMetaId"
									value="{{opcion.id}}">{{opcion.nombre}}
								</option>
								
							</select>
						</div>
						<div class="form-group">
							<label for="campo1">* Unidad de Medida</label>
							<select  class="form-control" ng-model="metac.meta.unidadMedidaId" ng-required="true">
								<option value="">Seleccione una opción</option>
								<option ng-repeat="opcion in metac.metaunidades"
									ng-selected="option.selected = metac.meta.unidadMedidaId"
									value="{{opcion.id}}">{{opcion.nombre}}
								</option>
								
							</select>
						</div>
					<br/>
					
						<div class="panel panel-default">
					<div class="panel-heading" style="text-align: center;">Datos de auditoría</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group" style="text-align: right">
									<label for="usuarioCreo">Usuario que creo</label> 
									<p class="form-control-static"> {{ metac.meta.usuarioCreo }}</p>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group" >
									<label for="fechaCreacion">Fecha de creación</label>
									<p class="form-control-static" id="fechaCreacion"> {{ metac.meta.fechaCreacion }} </p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group" style="text-align: right">
									<label for="usuarioActualizo">Usuario que actualizo</label> 
									<p class="form-control-static" id="usuarioCreo">{{ metac.meta.usuarioActualizo }} </p>
								</div>	
							</div>
							<div class="col-sm-6">		
								<div class="form-group">
									<label for="fechaActualizacion">Fecha de actualizacion</label> 
									<p class="form-control-static" id="usuarioCreo">{{ metac.meta.fechaActualizacion }} </p>
								</div>
							</div>
						</div>
					</div>
				</div>
				</form>
			</div>
			<div align="center">Los campos marcados con * son obligatorios</div>
			<div class="col-sm-12 operation_buttons" align="right">
				<div class="col-sm-12 operation_buttons" align="right">
					<div class="btn-group">
						<shiro:hasPermission name="17020">
				        	<label class="btn btn-success" ng-click="form.$valid ? metac.guardar() : ''" ng-disabled="!form.$valid">Guardar</label>
				        </shiro:hasPermission>
				        <label class="btn btn-primary" ng-click="metac.irATabla()">Ir a Tabla</label>
	    			</div>
	    		</div>
    		</div>
		</div>
	</div>
