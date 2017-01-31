<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="org.apache.shiro.SecurityUtils" %>
	<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
	<div ng-controller="recursoController as recursoc" class="maincontainer all_page" id="title">
	    <script type="text/ng-template" id="buscarRecursoTipo.jsp">
    		<%@ include file="/app/components/recurso/buscarRecursoTipo.jsp"%>
  	    </script>
		<h3>Recursos</h3><br/>
		<div class="row" align="center" ng-hide="recursoc.mostraringreso">
			<div class="col-sm-12 operation_buttons" align="right">
				<div class="btn-group">
			       <shiro:hasPermission name="crearCooperante">
			       		<label class="btn btn-primary" ng-click="recursoc.nuevo()">Nuevo</label>
			       </shiro:hasPermission>
			       <shiro:hasPermission name="editarCooperante"><label class="btn btn-primary" ng-click="recursoc.editar()">Editar</label></shiro:hasPermission>
			       <shiro:hasPermission name="eliminarCooperante">
			       		<label class="btn btn-primary" ng-click="recursoc.borrar()">Borrar</label>
			       </shiro:hasPermission>
    			</div>
    		</div>
    		<shiro:hasPermission name="verCooperante">
    		<div class="col-sm-12" align="center">
    			<div style="height: 35px;">
					<div style="text-align: right;"><div class="btn-group" role="group" aria-label="">
						<a class="btn btn-default" href ng-click="recursoc.reiniciarVista()" role="button" uib-tooltip="Reiniciar la vista de la tabla" tooltip-placement="left"><span class="glyphicon glyphicon-repeat" aria-hidden="true"></span></a>
					</div>
					</div>
				</div>
				<br/>
				<div id="maingrid" ui-grid="recursoc.gridOptions" ui-grid-save-state
						ui-grid-move-columns ui-grid-resize-columns ui-grid-selection ui-grid-pinning ui-grid-pagination class="grid">
					<div class="grid_loading" ng-hide="!recursoc.mostrarcargando">
				  	<div class="msg">
				      <span><i class="fa fa-spinner fa-spin fa-4x"></i>
						  <br /><br />
						  <b>Cargando, por favor espere...</b>
					  </span>
					</div>
				  </div>
				</div>
				<ul uib-pagination total-items="recursoc.totalRecursos"
						ng-model="recursoc.paginaActual"
						max-size="recursoc.numeroMaximoPaginas"
						items-per-page="recursoc.elementosPorPagina"
						first-text="Primero"
						last-text="Último"
						next-text="Siguiente"
						previous-text="Anterior"
						class="pagination-sm" boundary-links="true" force-ellipses="true"
						ng-change="recursoc.cambioPagina()"
				></ul>
			</div>
    		</shiro:hasPermission>

		</div>
		<div class="row" ng-show="recursoc.mostraringreso">
			<h4 ng-hide="!recursoc.esnuevo">Nuevo recurso</h4>
			<h4 ng-hide="recursoc.esnuevo">Edición de recurso</h4>
			<div class="col-sm-12 operation_buttons" align="right">
				<div class="btn-group">
			        <label class="btn btn-success" ng-click="recursoc.guardar()">Guardar</label>
			        <label class="btn btn-primary" ng-click="recursoc.irATabla()">Ir a Tabla</label>
    			</div>
    		</div>

			<div class="col-sm-12">
				<form>
						<div class="form-group">
							<label for="id">ID</label>
    						<label class="form-control" id="id">{{ recursoc.recurso.id }}</label>
						</div>
						<div class="form-group">
							<label for="nombre">* Nombre</label>
    						<input type="text" class="form-control" id="nombre" placeholder="Nombre" ng-model="recursoc.recurso.nombre">
						</div>

						<div class="form-group">
							<label for="campo3">* Tipo Recurso</label>
				          	<div class="input-group">
				            	<input type="text" class="form-control" id="irectipo" name="irectipo" placeholder="Tipo de Recurso" ng-model="recursoc.recurso.recursotiponombre" ng-readonly="true" required/>
				            	<span class="input-group-addon" ng-click="recursoc.buscarRecursoTipo()"><i class="glyphicon glyphicon-search"></i></span>
				          	</div>
						</div>
						<div class="form-group">
							<label for="campo3">* Unidad de Medida</label>
				          	<div class="input-group">
				            	<input type="text" class="form-control" id="iumedidad" name="iumedidad" placeholder="Unidad de Medida" ng-model="recursoc.recurso.medidanombre" ng-readonly="true" required/>
				            	<span class="input-group-addon" ng-click="recursoc.buscarUnidadMedida()"><i class="glyphicon glyphicon-search"></i></span>
				          	</div>
						</div>

						<div class="form-group" ng-repeat="campo in recursoc.camposdinamicos">
							<label for="campo.id">{{ campo.label }}</label>
							<div ng-switch="campo.tipo">
								<input ng-switch-when="texto" type="text" id="{{ 'campo_'+campo.id }}" ng-model="campo.valor" class="form-control" />
								<input ng-switch-when="entero" type="text" id="{{ 'campo_'+campo.id }}" numbers-only ng-model="campo.valor" class="form-control" />
								<input ng-switch-when="decimal" type="number" id="{{ 'campo_'+campo.id }}" ng-model="campo.valor" class="form-control" />
								<input ng-switch-when="booleano" type="checkbox" id="{{ 'campo_'+campo.id }}" ng-model="campo.valor" />
								<p ng-switch-when="fecha" class="input-group">
									<input type="text" id="{{ 'campo_'+campo.id }}" class="form-control" uib-datepicker-popup="{{recursoc.formatofecha}}" ng-model="campo.valor" is-open="campo.isOpen"
														datepicker-options="mi.fechaOptions" close-text="Cerrar" /><span
														class="input-group-btn">
														<button type="button" class="btn btn-default"
															ng-click="recursoc.abrirPopupFecha($index)">
															<i class="glyphicon glyphicon-calendar"></i>
														</button>
													</span>
								</p>
								<select ng-switch-when="select" id="{{ 'field_'+field.id }}" class="form-control" ng-model="x.value">
													<option value="">Seleccione una opción</option>
													<option ng-repeat="number in field.options"
														value="{{number.value}}">{{number.label}}</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="descripcion">Descripción</label>
    						<input type="text" class="form-control" id="descripcion" placeholder="Descripción" ng-model="recursoc.recurso.descripcion">
						</div>
						<div class="form-group">
							<label for="usuarioCreo">Usuario que creo</label>
    						<label class="form-control" id="usuarioCreo">{{ recursoc.recurso.usuarioCreo }}</label>
						</div>
						<div class="form-group">
							<label for="fechaCreacion">Fecha de creación</label>
    						<label class="form-control" id="fechaCreacion">{{ recursoc.recurso.fechaCreacion }}</label>
						</div>
						<div class="form-group">
							<label for="usuarioActualizo">Usuario que actualizo</label>
    						<label class="form-control" id="usuarioCreo">{{ recursoc.recurso.usuarioActualizo }}</label>
						</div>
						<div class="form-group">
							<label for="fechaActualizacion">Fecha de actualizacion</label>
    						<label class="form-control" id="usuarioCreo">{{ recursoc.recurso.fechaActualizacion }}</label>
						</div>
				</form>
			</div>
			<div align="center">Los campos marcados con * son obligatorios</div>
			<div class="col-sm-12 operation_buttons" align="right">
				<div class="col-sm-12 operation_buttons" align="right">
					<div class="btn-group">
				        <label class="btn btn-success" ng-click="recursoc.guardar()">Guardar</label>
				        <label class="btn btn-primary" ng-click="recursoc.irATabla()">Ir a Tabla</label>
	    			</div>
	    		</div>
    		</div>
		</div>
	</div>
