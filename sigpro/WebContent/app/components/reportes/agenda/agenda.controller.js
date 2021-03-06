var app = angular.module('agendaController', []);


app.controller('agendaController',['$scope','$http','$interval','i18nService','Utilidades','$routeParams','$window','$location','$route','uiGridConstants','$mdDialog','$uibModal', '$document','$timeout','$q','$filter',
	function($scope, $http, $interval,i18nService,$utilidades,$routeParams,$window,$location,$route,uiGridConstants,$mdDialog,$uibModal,$document,$timeout,$q,$filter) {
	
	var mi=this;
	mi.proyectoid = "";
	mi.proyectoNombre = "";
	mi.objetoTipoNombre = "";
	
	$window.document.title = $utilidades.sistema_nombre+' - Agenda de Actividades';
	i18nService.setCurrentLang('es');
	 
	$http.post('/SProyecto', { accion: 'obtenerProyectoPorId', id: $routeParams.proyectoId }).success(
			function(response) {
				mi.proyectoid = response.id;
				mi.proyectoNombre = response.nombre;
				mi.objetoTipoNombre = "Proyecto";
	});
	 
	 $http.post('/SAgenda', { accion: 'getAgenda', proyectoid:$routeParams.proyectoId, t: (new Date()).getTime()})
	 .then(function(response){
		 mi.lista = response.data.agenda;
		 mi.agenda = [].concat(mi.lista);
		 var tab = "\t";
		 for (x in mi.agenda){
			 mi.agenda[x].nombre = tab.repeat(mi.agenda[x].objetoTipo -1) + mi.agenda[x].nombre; 
		 }
	});	
	 
	 mi.exportarExcel = function(){
			$http.post('/SAgenda', { accion: 'exportarExcel', proyectoid:$routeParams.proyectoId,t:moment().unix()
				  } ).then(
						  function successCallback(response) {
								var anchor = angular.element('<a/>');
							    anchor.attr({
							         href: 'data:application/ms-excel;base64,' + response.data,
							         target: '_blank',
							         download: 'Agenda.xls'
							     })[0].click();
							  }.bind(this), function errorCallback(response){
							 		
							 	}
							 );
		};
	
	
}]);



		