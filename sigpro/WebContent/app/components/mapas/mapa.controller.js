var app = angular.module('mapaController', [ 'ngTouch','angularjs-dropdown-multiselect' ]);

app.controller('mapaController',['$scope','$http','$interval','i18nService','Utilidades','$routeParams','$window','$location','$route','uiGridConstants','$mdDialog','$uibModal','$q','uiGmapGoogleMapApi',
	function($scope, $http, $interval,i18nService,$utilidades,$routeParams,$window,$location,$route,uiGridConstants,$mdDialog,$uibModal,$q,uiGmapGoogleMapApi) {

	var mi = this;
	
	$scope.geoposicionlat =  14.6290845;
	$scope.geoposicionlong = -90.5116158;

	$scope.mostrarTodo = true;
	$scope.mostrarObjetoId = 0;
	$scope.marcas = {};

	$scope.mostrarTodo=true;
	$scope.mostrarProyectos=true;
	$scope.mostrarComponentes=true;
	$scope.mostrarProductos = true;
	$scope.mostrarSubproductos = true;
	$scope.mostrarActividades = true;
	$scope.proyectoid = $routeParams.proyecto_id;
	
	
	$scope.accionServlet = $scope.proyectoid!=null ? 'getMarcasPorProyecto' : 'getMarcasProyecto';
	$scope.mostrarControles = $scope.proyectoid!=null;
	mi.mostrar = false;
	
	
	$http.post('/SProyecto',{accion: 'getProyectos'}).success(
		function(response) {
			mi.prestamos = [];
			mi.prestamos.push({'value' : 0, 'text' : 'Seleccione un proyecto'});
			if (response.success){
				for (var i = 0; i < response.entidades.length; i++){
					mi.prestamos.push({'value': response.entidades[i].id, 'text': response.entidades[i].nombre});
				}
				
				if ($scope.proyectoid !=null && $scope.proyectoid != undefined){
					for (x in mi.prestamos){
						if (mi.prestamos[x].value == $scope.proyectoid){
							mi.prestamo = mi.prestamos[x];
							mi.cargar();
							break;
						}
						
					}
					
				}else{
					mi.prestamo = mi.prestamos[0];
				}
				
				
			}
	});

	mi.cargarMapa = function(){
		
		uiGmapGoogleMapApi.then(function() {
			$scope.map = { center: { latitude: $scope.geoposicionlat, longitude: $scope.geoposicionlong },
			   zoom: 15,
			   height: 400,
			   width: 200,
			   options: {
				   streetViewControl: false,
				   scrollwheel: true,
				  draggable: true,
				  mapTypeId: google.maps.MapTypeId.SATELLITE
			   },
			   events:{
				   click: function (map,evtName,evt) {
					   $scope.posicion = {latitude: evt[0].latLng.lat()+"", longitude: evt[0].latLng.lng()+""} ;
					   $scope.$evalAsync();
				   }
			   },
			   refresh: true
			};
	    });
		
	}
	

	$http.post('/SMapa', { accion: $scope.accionServlet, proyectoId:$routeParams.proyecto_id}).success(
			function(response) {
				$scope.marcas = response.marcas;
	});

	

	 $scope.mostrar = function (objetoId) {

		 switch (objetoId){
		 	case 0:
		 		$scope.mostrarProyectos=$scope.mostrarTodo;
		 		$scope.mostrarComponentes=$scope.mostrarTodo;
		 		$scope.mostrarProductos = $scope.mostrarTodo;
		 		$scope.mostrarSubproductos = $scope.mostrarTodo;
		 		$scope.mostrarActividades = $scope.mostrarTodo;
		 		if ($scope.mostrarTodo){
		 			mi.transclusionModel = [mi.transclusionData[0],mi.transclusionData[1],mi.transclusionData[2],mi.transclusionData[3]];
		 			mi.estadoSubproductos = [mi.optionSubproductos[0],mi.optionSubproductos[1],mi.optionSubproductos[2]];
		 			mi.estadoProductos = [mi.optionProductos[0],mi.optionProductos[1],mi.optionProductos[2]];
		 			mi.estadoComponentes = [mi.optionComponentes[0],mi.optionComponentes[1],mi.optionComponentes[2]];
		 		}else{
		 			mi.transclusionModel = [];
		 			mi.estadoSubproductos = [];
		 			mi.estadoProductos = [];
		 			mi.estadoComponentes = [];
		 		}
		 		for (x in $scope.marcas){
		 			$scope.marcas[x].mostrar = true;
				 }
		 		break;
		 	case 1:
		 		mi.getMostrarTodo();
		 		break;
		 	case 2:
		 		if ($scope.mostrarComponentes)
		 			mi.estadoComponentes = [mi.optionComponentes[0],mi.optionComponentes[1],mi.optionComponentes[2]];
		 		else
		 			mi.estadoComponentes = [];
		 		for (x in $scope.marcas){
					 if ($scope.marcas[x].objetoTipoId==2 )
						 $scope.marcas[x].mostrar = true;
				 }
		 		mi.getMostrarTodo();
		 		break;
		 	case 3:
		 		if ($scope.mostrarProductos)
		 			mi.estadoProductos = [mi.optionProductos[0],mi.optionProductos[1],mi.optionProductos[2]];
		 		else
		 			mi.estadoProductos = [];
		 		for (x in $scope.marcas){
					 if ($scope.marcas[x].objetoTipoId==3 )
						 $scope.marcas[x].mostrar = true;
				 }
		 		mi.getMostrarTodo();
		 		break;
		 	case 4:
		 		
		 		if ($scope.mostrarSubproductos)
		 			mi.estadoSubproductos = [mi.optionSubproductos[0],mi.optionSubproductos[1],mi.optionSubproductos[2]];
		 		else
		 			mi.estadoSubproductos = [];
		 		for (x in $scope.marcas){
					 if ($scope.marcas[x].objetoTipoId==4 )
						 $scope.marcas[x].mostrar = true;
				 }
		 		mi.getMostrarTodo();
		 		break;
		 	case 5:
		 		if ($scope.mostrarActividades)
		 			mi.transclusionModel = [mi.transclusionData[0],mi.transclusionData[1],mi.transclusionData[2],mi.transclusionData[3]];
		 		else
		 			mi.transclusionModel = [];
		 		for (x in $scope.marcas){
					 if ($scope.marcas[x].objetoTipoId==5 )
						 $scope.marcas[x].mostrar = true;
				 }
		 		
		 		mi.getMostrarTodo();
		 		break;
		 }
	  };

	  $scope.mostrarInformaicon = function(objetoId , ObjetoTipo){

	  }



	  $scope.abrirInformacion = function (objetoId , objetoTipo, idEstado ,avance) {
		    var modalInstance = $uibModal.open({

		      ariaLabelledBy: 'Información',
		      ariaDescribedBy: 'modal-body',
		      templateUrl: 'modalInfo.html',
		      controller: 'modalInformacion',
		      controllerAs: 'infoc',

		      resolve : {
		    	    $objetoId : function() {
						return objetoId;
					},
					$objetoTipo : function() {
						return objetoTipo;
					},
					$idEstado : function() {
						return idEstado;
					},
					$avance : function() {
						return avance;
					}
				}
		    });
	 };
	 
	 mi.cargar = function(){
		 if (mi.prestamo!=null && mi.prestamo.value > 0){
		 $http.post('/SMapa', { accion: 'getMarcasPorProyecto', proyectoId:mi.prestamo.value}).success(
					function(response) {
						$scope.marcas = response.marcas;
						for (x in $scope.marcas){
							if ($scope.marcas[x].objetoTipoId == 1){
								$scope.geoposicionlat =  $scope.marcas[x].posicion.latitude;
								$scope.geoposicionlong = $scope.marcas[x].posicion.longitude;
							}
						}
						mi.mostrar=true;
						mi.cargarMapa();
			});
		 } 
	 };
	 
	 
	 mi.transclusionData = [  { id: 1, label: 'Sin iniciar' }, { id: 2, label: 'En proceso' }, { id: 3, label: 'Retrasadas' },{ id: 4, label: 'Completadas' }]; 
	 mi.transclusionSettings = { dynamicTitle: false, showCheckAll: false, showUncheckAll:false,};
	 mi.transclusionModel = [mi.transclusionData[0],mi.transclusionData[1],mi.transclusionData[2],mi.transclusionData[3]];

	 
	 mi.selectActividad = {
			 onItemSelect: function(item) {
				 for (x in $scope.marcas){
					 if ($scope.marcas[x].objetoTipoId==5 && $scope.marcas[x].idEstado == item.id)
						 $scope.marcas[x].mostrar = true;
				 }
				 $scope.mostrarActividades = true;
				 if (mi.transclusionModel.length == 4)
					 mi.getMostrarTodo();
			 },
			 onItemDeselect:function(item) {
				 for (x in $scope.marcas){
					 if ($scope.marcas[x].objetoTipoId==5 && $scope.marcas[x].idEstado == item.id)
						 $scope.marcas[x].mostrar = false;
				 }
				 $scope.mostrarTodo = false;
				 
				 if (mi.transclusionModel.length == 0){
					 $scope.mostrarActividades = false;
				 }
				 
			 }
	 };
	 
	 
	 mi.optionSubproductos = [  { id: 1, label: 'Aceptacion' }, { id: 2, label: 'Advertencia' }, { id: 3, label: 'Riesgo' }]; 
	 mi.extraSetingSubproductos = { dynamicTitle: false, showCheckAll: false, showUncheckAll:false,};
	 mi.estadoSubproductos = [mi.optionSubproductos[0],mi.optionSubproductos[1],mi.optionSubproductos[2]];
	 
	 mi.selectSubproducto = {
			 onItemSelect: function(item) {
				 for (x in $scope.marcas){
					 if (item.id == 3){
						 if ($scope.marcas[x].objetoTipoId==4 && $scope.marcas[x].porcentajeEstado >= 0 &&
								 $scope.marcas[x].porcentajeEstado <= 40)
							 $scope.marcas[x].mostrar = true;
					 }else if (item.id == 2){
						 if ($scope.marcas[x].objetoTipoId==4 && $scope.marcas[x].porcentajeEstado > 40 &&
								 $scope.marcas[x].porcentajeEstado <= 60){
							 $scope.marcas[x].mostrar = true;
						 }
					 }else if (item.id == 1){
						 if ($scope.marcas[x].objetoTipoId==4 && $scope.marcas[x].porcentajeEstado > 60 &&
								 $scope.marcas[x].porcentajeEstado <= 100){
							 $scope.marcas[x].mostrar = true;
						 }
					 }
				 }
				 $scope.mostrarSubproductos = true;
				 if (mi.estadoSubproductos.length == 3)
					 mi.getMostrarTodo();
			 },
			 onItemDeselect:function(item) {
				 
				 for (x in $scope.marcas){
					 if (item.id == 3){
						 if ($scope.marcas[x].objetoTipoId==4 && $scope.marcas[x].porcentajeEstado >= 0 &&
								 $scope.marcas[x].porcentajeEstado <= 40){
							 $scope.marcas[x].mostrar = false;
						 }
					 }else if (item.id == 2){
						 if ($scope.marcas[x].objetoTipoId==4 && $scope.marcas[x].porcentajeEstado > 40 &&
								 $scope.marcas[x].porcentajeEstado <= 60){
							 $scope.marcas[x].mostrar = false;
						 }
					 }else if (item.id == 1){
						 if ($scope.marcas[x].objetoTipoId==4 && $scope.marcas[x].porcentajeEstado > 60 &&
								 $scope.marcas[x].porcentajeEstado <= 100){
							 $scope.marcas[x].mostrar = false;
						 }
					 }
				 }
				 $scope.mostrarTodo = false;
				 
				 if (mi.estadoSubproductos.length == 0){
					 $scope.mostrarSubproductos = false;
				 }
				 
			 }
	 };
	 
	 
	 mi.optionProductos = [  { id: 1, label: 'Aceptacion' }, { id: 2, label: 'Advertencia' }, { id: 3, label: 'Riesgo' }]; 
	 mi.extraSetingProductos = { dynamicTitle: false, showCheckAll: false, showUncheckAll:false,};
	 mi.estadoProductos = [mi.optionProductos[0],mi.optionProductos[1],mi.optionProductos[2]];
	 
	 mi.selectProducto = {
			 onItemSelect: function(item) {
				 for (x in $scope.marcas){
					 if (item.id == 3){
						 if ($scope.marcas[x].objetoTipoId==3 && $scope.marcas[x].porcentajeEstado >= 0 &&
								 $scope.marcas[x].porcentajeEstado <= 40)
							 $scope.marcas[x].mostrar = true;
					 }else if (item.id == 2){
						 if ($scope.marcas[x].objetoTipoId==3 && $scope.marcas[x].porcentajeEstado > 40 &&
								 $scope.marcas[x].porcentajeEstado <= 60){
							 $scope.marcas[x].mostrar = true;
						 }
					 }else if (item.id == 1){
						 if ($scope.marcas[x].objetoTipoId==3 && $scope.marcas[x].porcentajeEstado > 60 &&
								 $scope.marcas[x].porcentajeEstado <= 100){
							 $scope.marcas[x].mostrar = true;
						 }
					 }
				 }
				 $scope.mostrarProductos = true;
				 if (mi.estadoProductos.length == 3)
					 mi.getMostrarTodo();
			 },
			 onItemDeselect:function(item) {
				 
				 for (x in $scope.marcas){
					 if (item.id == 3){
						 if ($scope.marcas[x].objetoTipoId==3 && $scope.marcas[x].porcentajeEstado >= 0 &&
								 $scope.marcas[x].porcentajeEstado <= 40){
							 $scope.marcas[x].mostrar = false;
						 }
					 }else if (item.id == 2){
						 if ($scope.marcas[x].objetoTipoId==3 && $scope.marcas[x].porcentajeEstado > 40 &&
								 $scope.marcas[x].porcentajeEstado <= 60){
							 $scope.marcas[x].mostrar = false;
						 }
					 }else if (item.id == 1){
						 if ($scope.marcas[x].objetoTipoId==3 && $scope.marcas[x].porcentajeEstado > 60 &&
								 $scope.marcas[x].porcentajeEstado <= 100){
							 $scope.marcas[x].mostrar = false;
						 }
					 }
						 
				 }
				 $scope.mostrarTodo = false;
				 
				 if (mi.estadoProductos.length == 0){
					 $scope.mostrarProductos = false;
				 }
				 
			 }
	 };
	 
	 
	 mi.optionComponentes = [  { id: 1, label: 'Aceptacion' }, { id: 2, label: 'Advertencia' }, { id: 3, label: 'Riesgo' }]; 
	 mi.extraSetingComponentes = { dynamicTitle: false, showCheckAll: false, showUncheckAll:false,};
	 mi.estadoComponentes = [mi.optionComponentes[0],mi.optionComponentes[1],mi.optionComponentes[2]];
	 
	 mi.selectComponente = {
			 onItemSelect: function(item) {
				 for (x in $scope.marcas){
					 if (item.id == 3){
						 if ($scope.marcas[x].objetoTipoId==2 && $scope.marcas[x].porcentajeEstado >= 0 &&
								 $scope.marcas[x].porcentajeEstado <= 40)
							 $scope.marcas[x].mostrar = true;
					 }else if (item.id == 2){
						 if ($scope.marcas[x].objetoTipoId==2 && $scope.marcas[x].porcentajeEstado > 40 &&
								 $scope.marcas[x].porcentajeEstado <= 60){
							 $scope.marcas[x].mostrar = true;
						 }
					 }else if (item.id == 1){
						 if ($scope.marcas[x].objetoTipoId==2 && $scope.marcas[x].porcentajeEstado > 60 &&
								 $scope.marcas[x].porcentajeEstado <= 100){
							 $scope.marcas[x].mostrar = true;
						 }
					 }
				 }
				 $scope.mostrarComponentes = true;
				 if (mi.estadoComponentes.length == 3)
					 mi.getMostrarTodo();
			 },
			 onItemDeselect:function(item) {
				 
				 for (x in $scope.marcas){
					 if (item.id == 3){
						 if ($scope.marcas[x].objetoTipoId==2 && $scope.marcas[x].porcentajeEstado >= 0 &&
								 $scope.marcas[x].porcentajeEstado <= 40){
							 $scope.marcas[x].mostrar = false;
						 }
					 }else if (item.id == 2){
						 if ($scope.marcas[x].objetoTipoId==2 && $scope.marcas[x].porcentajeEstado > 40 &&
								 $scope.marcas[x].porcentajeEstado <= 60){
							 $scope.marcas[x].mostrar = false;
						 }
					 }else if (item.id == 1){
						 if ($scope.marcas[x].objetoTipoId==2 && $scope.marcas[x].porcentajeEstado > 60 &&
								 $scope.marcas[x].porcentajeEstado <= 100){
							 $scope.marcas[x].mostrar = false;
						 }
					 }
				 }
				 $scope.mostrarTodo = false;
				 
				 if (mi.estadoComponentes.length == 0){
					 $scope.mostrarComponentes = false;
				 }
				 
			 }
	 };

	 
	 
	 
	 mi.getMostrarTodo = function(){
		 $scope.mostrarTodo = $scope.mostrarProyectos && $scope.mostrarComponentes && $scope.mostrarProductos
			&& $scope.mostrarSubproductos && $scope.mostrarActividades && mi.transclusionModel.length == 4
			&& mi.estadoSubproductos.length == 3 && mi.estadoProductos.length == 3 && mi.estadoComponentes.length == 3;
	 }

}]);


app.controller('modalInformacion', [ '$uibModalInstance',
	'$scope', '$http', '$interval', 'i18nService', 'Utilidades',
	'$timeout', '$log', '$objetoId', '$objetoTipo', '$idEstado', '$avance',  modalInformacion ]);

function modalInformacion($uibModalInstance, $scope, $http, $interval,
	i18nService, $utilidades, $timeout, $log, $objetoId, $objetoTipo,$idEstado,$avance) {

	var mi = this;
	mi.objeto={};

	$http.post('/SMapa', {
		accion : 'getObjeto',
		objetoId: $objetoId,
		objetoTipo: $objetoTipo
	}).success(function(response) {
		mi.objeto = response.objeto;
		if ($objetoTipo == 5){
			switch ($idEstado){
				case 1 : mi.objeto.estadoNombre = "Sin Iniciar"; break;
				case 2 : mi.objeto.estadoNombre = "En proceso"; break;
				case 3 : mi.objeto.estadoNombre = "Retrasada"; break;
				case 4 : mi.objeto.estadoNombre = "Completada"; break;
				
			}
			mi.objeto.avance = $avance;
		}else {
			if ($avance >=0 && $avance <= 40)
				mi.objeto.estadoNombre = "Riesgo";
			else if ($avance >40 && $avance <= 60)
				mi.objeto.estadoNombre = "Advertencia";
			else if ($avance >60 && $avance <= 100)
				mi.objeto.estadoNombre = "Aceptación";
			mi.objeto.avance = $avance;
		}
	});

	 mi.ok = function () {
		 $uibModalInstance.dismiss('cancel');
	 };


}
