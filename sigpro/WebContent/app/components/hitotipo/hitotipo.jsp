<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="org.apache.shiro.SecurityUtils" %>
	<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
	<div ng-controller="hitotipoController as hitotipoc" class="maincontainer all_page" id="title">
		<shiro:lacksPermission name="16010">
			<p ng-init="hitotipoc.redireccionSinPermisos()"></p>
		</shiro:lacksPermission>
		<div class="panel panel-default">
		  <div class="panel-heading"><h3>Tipo de Hito</h3></div>
		</div>
		
		<div class="row" align="center" ng-hide="hitotipoc.mostraringreso">
			<div class="col-sm-12 operation_buttons" align="right">
				<div class="btn-group">
			       <shiro:hasPermission name="16040">
			       		<label class="btn btn-primary" ng-click="hitotipoc.nuevo()" uib-tooltip="Nuevo">
			       			<span class="glyphicon glyphicon-plus"></span>Nuevo
			       		</label>
			       </shiro:hasPermission> 
			       <shiro:hasPermission name="16010"><label class="btn btn-primary" ng-click="hitotipoc.editar()" uib-tooltip="Editar">
			       		<span class="glyphicon glyphicon-pencil"></span> Editar</label>
			       	</shiro:hasPermission>
			       <shiro:hasPermission name="16030">
			       		<label class="btn btn-danger" ng-click="hitotipoc.borrar()" uib-tooltip="Borrar">
			       			<span class="glyphicon glyphicon-trash"></span>Borrar
			       		</label>
			       </shiro:hasPermission>
    			</div>				
    		</div>
    		<shiro:hasPermission name="16010">
    		<div class="col-sm-12" align="center">
    			<div style="height: 35px;">
					<div style="text-align: right;"><div class="btn-group" role="group" aria-label="">
						<a class="btn btn-default" href ng-click="hitotipoc.reiniciarVista()" role="button" uib-tooltip="Reiniciar la vista de la tabla" tooltip-placement="left"><span class="glyphicon glyphicon-repeat" aria-hidden="true"></span></a>
					</div>
					</div>
				</div>
				<br/>
				<div id="maingrid" ui-grid="hitotipoc.gridOptions" ui-grid-save-state 
						ui-grid-move-columns ui-grid-resize-columns ui-grid-selection ui-grid-pinning ui-grid-pagination class="grid">
					<div class="grid_loading" ng-hide="!hitotipoc.mostrarcargando">
				  	<div class="msg">
				      <span><i class="fa fa-spinner fa-spin fa-4x"></i>
						  <br /><br />
						  <b>Cargando, por favor espere...</b>
					  </span>
					</div>
				  </div>
				</div>
				<br/>
			<div class="total-rows">Total de {{  hitotipoc.totalHitotipo + (hitotipoc.totalHitotipo == 1 ? " tipo de hito" : " tipos de hito" ) }}</div>
				<ul uib-pagination total-items="hitotipoc.totalHitotipo" 
						ng-model="hitotipoc.paginaActual" 
						max-size="hitotipoc.numeroMaximoPaginas" 
						items-per-page="hitotipoc.elementosPorPagina"
						first-text="Primero"
						last-text="Último"
						next-text="Siguiente"
						previous-text="Anterior"
						class="pagination-sm" boundary-links="true" force-ellipses="true"
						ng-change="hitotipoc.cambioPagina()"
				></ul>
			</div>
    		</shiro:hasPermission>
    		
		</div>
		<div  class="row second-main-form" ng-show="hitotipoc.mostraringreso">
		
			<div class="page-header">
				<h2 ng-hide="!hitotipoc.esnuevo"><small>Nuevo Tipo Hito</small></h2>
				<h2 ng-hide="hitotipoc.esnuevo"><small>Edición de Tipo Hito</small></h2>
			</div>
			
			<div class="col-sm-12 operation_buttons" align="right">
				<div class="btn-group">
					<shiro:hasPermission name="16020">
			        	<label class="btn btn-success"  ng-click="form.$valid ? hitotipoc.guardar() : ''" ng-disabled="form.$invalid" uib-tooltip="Guardar">
					<span class="glyphicon glyphicon-floppy-saved"></span> Guardar</label>
					</shiro:hasPermission>
			        <label class="btn btn-primary" ng-click="hitotipoc.irATabla()" uib-tooltip="Ir a Tabla">
				<span class="glyphicon glyphicon-list-alt"></span> Ir a Tabla</label>
    			</div>
    		</div>
			
			<div class="col-sm-12">
				<form name="form">
					<div class="form-group" ng-show="!hitotipoc.esnuevo">
						<label for="id" class="floating-label">ID {{ hitotipoc.hitotipo.id }}</label>
						<br/><br/>
					</div>
					<div class="form-group">
   						<input type="text" class="inputText" ng-model="hitotipoc.hitotipo.nombre" ng-required="true"
   						ng-value="hitotipoc.hitotipo.nombre" onblur="this.setAttribute('value', this.value);">
   						<label class="floating-label">* Nombre</label>
					</div>
					<div class="form-group">
		    			<select class="inputText" ng-model="hitotipoc.datoTipoSeleccionado" ng-options="tipo as tipo.nombre for tipo in hitotipoc.datoTipos track by tipo.id" ng-required="true"
		    			ng-readonly="true" ng-disabled="!hitotipoc.esnuevo" ng-required="true" >
							<option disabled selected value> * Tipo de Hito </option>
						</select>
						<label class="floating-label">* Tipo:</label>
		    		 </div>
					<div class="form-group">
   						<input type="text" class="inputText" ng-model="hitotipoc.hitotipo.descripcion"
   						ng-value="hitotipoc.hitotipo.descripcion" onblur="this.setAttribute('value', this.value);">
   						<label class="floating-label">Descripción</label>
					</div>
					
					<br/>
				<div class="panel panel-default">
					<div class="panel-heading label-form" style="text-align: center;">Datos de auditoría</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group" style="text-align: right">
									<label for="usuarioCreo" class="label-form">Usuario que creo</label>
				  					<p>{{ hitotipoc.hitotipo.usuarioCreo }}</pl>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label for="fechaCreacion"  class="label-form">Fecha de creación</label>
				  					<p>{{ hitotipoc.hitotipo.fechaCreacion }}</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group" style="text-align: right">
									<label for="usuarioActualizo"  class="label-form">Usuario que actualizo</label>
				  					<p>{{ hitotipoc.hitotipo.usuarioActualizo }}</p>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label for="fechaActualizacion"  class="label-form">Fecha de actualizacion</label>
				  					<p>{{ hitotipoc.hitotipo.fechaActualizacion }}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
						
						
				</form>
			</div>
			<div align="center" class="label-form">Los campos marcados con * son obligatorios</div>
			<div class="col-sm-12 operation_buttons" align="right">
				<div class="col-sm-12 operation_buttons" align="right">
					<div class="btn-group">
						<shiro:hasPermission name="16020">
				        	<label class="btn btn-success" ng-click="form.$valid ? hitotipoc.guardar() : ''" ng-disabled="form.$invalid" uib-tooltip="Guardar">
					<span class="glyphicon glyphicon-floppy-saved"></span> Guardar</label>
				        </shiro:hasPermission>
				        <label class="btn btn-primary" ng-click="hitotipoc.irATabla()" uib-tooltip="Ir a Tabla">
				<span class="glyphicon glyphicon-list-alt"></span> Ir a Tabla</label>
	    			</div>
	    		</div>
    		</div>
		</div>
	</div>
