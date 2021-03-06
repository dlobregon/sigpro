<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal-header">
<h3 class="modal-title">Cargar desde Project</h3>
</div>
<div class="modal-body" id="modal-body">
<form name="form">
	<div class="grid_loading" ng-hide="!cargararchivoc.mostrarcargando" style="position:relative; z-index:4; top: 30px;">
				  	<div class="msg">
				      <span><i class="fa fa-spinner fa-spin fa-4x"></i>
						  <br /><br />
						  <b>Cargando, por favor espere...</b>
					  </span>
					</div>
				</div>
  <div class="row">
    <div class="col-sm-12">
		<div class="form-group" >
				<label for="campo3">* Nombre del Archivo</label>
	          	<div class="input-group">
		          	<input  class="form-control" id="file" name="file" type="file" 
		          	onchange="angular.element(this).scope().cargarArchivo(this)"
				 	file-upload multiple style="display: none;"accept=".mpp"  >
				 	
	            	<input type="text" id="nombreArchivo" class="form-control" placeholder="Seleccione archivo" 
	            	ng-model="cargararchivoc.nombreArchivo"  
	            	ng-readonly="true" ng-required="true"/>
	            	<span class="input-group-addon" >
	            	<label class="glyphicon glyphicon-search" for="file" ></label>
	            	</span>
	          	</div>
	          	<br/>
	          	<div class="form-group">
						<input type="checkbox"  ng-model="cargararchivoc.multiproyecto" />
						<label  class="floating-label">Multiproyecto</label>
				</div>
		</div>
    </div>
    
    </div>
    <br/>
    <div class="row">
	    <div class="col-sm-12 operation_buttons" align="right" style="position:relative; z-index:2;">
		    <div class="btn-group">
		    
		        <label  class="btn btn-success" ng-click="cargararchivoc.ok()"> &nbsp;&nbsp;&nbsp;&nbsp;Ok&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<label class="btn btn-primary" ng-click="cargararchivoc.cancel()">Cancelar</label>
	    	</div>
	      
	    </div>
  </div>
  </form>
</div>