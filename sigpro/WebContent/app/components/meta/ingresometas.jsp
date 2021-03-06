<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
	.ui-grid-tree-header-row {
    	font-weight: normal !important;
	}
	
	.ui-grid-tree-padre {
    	font-weight: bold;
	}
	
	.ui-grid-category {
  		text-align: center;border-right: 0px;box-shadow: -1px 1px #d4d4d4 
	}
		
	.divGrid {
		resize: vertical;
		overflow: scroll;
		margin: 5px;
		height: 360px;
	}
</style>

	<%@ page import="org.apache.shiro.SecurityUtils" %>
	<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
	<div ng-controller="prestamometasController as pmetasc" class="maincontainer all_page" id="title">
	    
  	    <shiro:lacksPermission name="30010">
			<p ng-init="pmetasc.redireccionSinPermisos()"></p>
		</shiro:lacksPermission>
		
		<div class="panel panel-default">
			<div class="panel-heading"><h3>Metas de Préstamo</h3></div>
		</div>
		
		<div class="container" style="width: 100%">
    	<div style="height: 30%; width: 100%;">
    		<div class="row">
				<div class="form-group col-sm-3">
					<select  class="inputText" ng-model="pmetasc.prestamo"
						ng-options="a.text for a in pmetasc.prestamos" ng-required="true"></select>
					<label for="prestamo" class="floating-label">Préstamos</label>
				</div>
			</div>
			
    		<div class="row">
				<div class="form-group col-sm-2">
					<input type="text"  class="inputText" uib-datepicker-popup="{{pmetasc.formatofecha}}" ng-model="pmetasc.fechaInicio" is-open="pmetasc.fi_abierto"
			            datepicker-options="pmetasc.fechaOptions" close-text="Cerrar" current-text="Hoy" clear-text="Borrar"  
			            ng-required="true"  ng-click="pmetasc.abrirPopupFechaInicio(1000)"
			            ng-value="pmetasc.fechaInicio" onblur="this.setAttribute('value', this.value);"/>
			            <span class="label-icon" ng-click="pmetasc.abrirPopupFechaInicio(1000)">
			              <i class="glyphicon glyphicon-calendar"></i>
			            </span>
				  	<label for="campo.id" class="floating-label">* Año Inicial</label>
				</div>
				<div class="form-group col-sm-2">
					<input type="text"  class="inputText" uib-datepicker-popup="{{pmetasc.formatofecha}}" ng-model="pmetasc.fechaFin" is-open="pmetasc.ff_abierto"
			            datepicker-options="pmetasc.fechaOptions" close-text="Cerrar" current-text="Hoy" clear-text="Borrar" 
			            ng-required="true"  ng-click="pmetasc.abrirPopupFechaFin(1000)"
			            ng-value="pmetasc.fechaFin" onblur="this.setAttribute('value', this.value);"/>
			            <span class="label-icon" ng-click="pmetasc.abrirPopupFechaFin(1000)">
			              <i class="glyphicon glyphicon-calendar"></i>
			            </span>
				  	<label for="campo.id" class="floating-label">* Año Final</label>
				</div>
				<div class="form-group col-sm-2">
					<select class="inputText" ng-model="pmetasc.agrupacion" 
						ng-options="a.text for a in pmetasc.agrupaciones" ng-required="true">
					</select>
					<label class="floating-label">Agrupacion</label>
				</div>	
				
				<div class="form-group col-sm-2" >
					<label class="btn btn-default" ng-click="pmetasc.generar()" uib-tooltip="Generar Reporte" 
						tooltip-placement="bottom">
					<span class="glyphicon glyphicon-new-window"></span></label>
				</div>
				<div class="operation_buttons" align="right">
					<div class="btn-group">		
						<label class="btn btn-default" ng-click="pmetasc.guardar()" uib-tooltip="Guardar" ng-hide="!pmetasc.mostrarGuardar"
							tooltip-placement="bottom">
						<span class="glyphicon glyphicon-floppy-disk"></span></label>					
						<label class="btn btn-default" ng-click="pmetasc.congelar()" uib-tooltip="Congelar" 
							ng-hide="!pmetasc.mostrarCongelar" ng-disabled="pmetasc.reporteCongelado"
							tooltip-placement="bottom">
						<span class="glyphicon glyphicon-ban-circle"></span></label>
						<label class="btn btn-default" ng-click="pmetasc.exportarExcel()" uib-tooltip="Descargar excel" ng-hide="!pmetasc.mostrarDescargar"
							tooltip-placement="bottom">
						<span class="glyphicon glyphicon-export"></span></label>
					</div>
				</div>
			</div>
    	</div>
		<br>
		
    	<div id="grid" ui-grid="pmetasc.opcionesGrid" ng-hide="!pmetasc.mostrarDescargar" class="divGrid"
			ui-grid-resize-columns ui-grid-pinning ui-grid-auto-resize
			ui-grid-grouping ui-grid-edit ui-grid-cellNav >
			<div class="grid_loading" ng-hide="!pmetasc.mostrarCargando">
				<div class="msg">
					<span><i class="fa fa-spinner fa-spin fa-4x"></i> 
						<br />
						<br /> <b>Cargando, por favor espere...</b> 
					</span>
				</div>
			</div>
		</div>
    	
	</div>
</div>
