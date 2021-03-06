var app = angular.module('cargatrabajoController', ['ngTouch','smart-table','ivh.treeview']);




app.controller('cargatrabajoController',['$scope','$http','$interval','i18nService','Utilidades','$routeParams','$window','$location','$route','uiGridConstants','$mdDialog','$uibModal', '$document','$timeout','$q','$filter',
	function($scope, $http, $interval,i18nService,$utilidades,$routeParams,$window,$location,$route,uiGridConstants,$mdDialog,$uibModal,$document,$timeout,$q,$filter,ivhTreeviewMgr) {
	var mi = this;
	i18nService.setCurrentLang('es');
	
    mi.idTotal = 0;
    mi.responsableTotal = "Total";
    mi.actividadesAtrasadasTotal = 0;
	mi.actividadesAlertaTotal = 0;
	mi.actividadesACumplirTotal = 0; 
	mi.actividadesCompletadas  = 0;
    mi.exportar = false;
    mi.grafica = true;
    mi.idPrestamo = 0;
    mi.estructuraProyecto=[];
    mi.objetosSeleccionados=[];
    mi.datosTabla = [];
    mi.mostrar = false;
    mi.dataChartLine = [];
    mi.etiquetasChartLine = [];
    mi.actividadesterminadas = [];
    
    mi.pieColors = ['#fd7b7d','#dddd7d','#bae291','#9cc3e2'];
    
    mi.lineColors = ['#9cc3e2'];
    
    
    	mi.optionsPie = {
    			
			legend: {
				display: true,
				position: 'right'
			},
			pieceLabel: {
			    render: 'percentage',
			    fontColor: ['green', 'white', 'red'],
			    precision: 2
			  }
			  };
    	
    	
    	
    	
    	mi.seriesLine = ['Actividades Terminadas'];
    	
    	mi.options = {
    			    scales: {
    			      yAxes: [
    			        {
    			          id: 'y-axis-1',
    			          type: 'linear',
    			          
    			          display: true,
    			          position: 'left',
    			          scaleLabel: {
	   	                       display: true,
	   	                       labelString: 'Total',
   	                      },
   	                   stacked: true,
   	                ticks: {
   	                   min: 0,
   	                   stepSize: 1,
   	               },
   	                  gridLines: {
                          display: false
                      }
    			          
    			        	 
    			        }
    			      ],
    			      xAxes: [{
    			    	  scaleLabel: {
    	                       display: true,
    	                       labelString: "Mes"
    	                     },
    	                     gridLines: {
    	                          display: false
    	                      }
    			      }
    			      ]
    			    }
    			  };
    	  
    	  mi.datasetOverride = [{ yAxisID: 'y-axis-1' }];

    mi.redireccionSinPermisos=function(){
		$window.location.href = '/main.jsp#!/forbidden';		
	}
    
    mi.reiniciarVista=function(){
		if($location.path()=='/cargatrabajo/rv')
			$route.reload();
		else
			$location.path('/cargatrabajo/rv');
	}
    
    $http.post('/SProyecto',{accion: 'getProyectos'}).success(
		function(response) {
			mi.prestamos = []; 
			mi.prestamos.push({'value' : 0, 'text' : 'Seleccione un préstamo'});
			if (response.success){
				for (var i = 0; i < response.entidades.length; i++){
					mi.prestamos.push({'value': response.entidades[i].id, 'text': response.entidades[i].nombre});
				}
				mi.prestamo = mi.prestamos[0];
				
				
				
				
				
			}
		});
    
	mi.tObjetos = [
		{value: 0,text: "Seleccione una Opción"},
		{value: 1,text: 'Préstamo'},
		{value: 2,text: 'Componente'},
		{value: 3,text: 'Producto'},
		{value: 4,text: 'Sub Producto'}
	];
	
	mi.tObjeto = mi.tObjetos[0];

	
	
	mi.mesActual = "";

	
	mi.reset = function(){
		mi.entidades = [
			{value: 0, text: 'Seleccione una opción'}
		]
		
		mi.unidadesEjecutoras = [
			{value: 0, text: "Seleccione una opción"}
		]
		
		mi.prestamos = [
			{value: 0,text: "Seleccione una opción"}
		];
		
		mi.componentes = [
			{value: 0,text: "Seleccione una opción"}
		];
		
		mi.productos = [
			{value: 0,text: "Seleccione una opción"}
		];
		
		mi.subProductos = [
			{value: 0,text: "Seleccione una opción"}
		];
		
		mi.entidad = mi.entidades[0];
		mi.unidadEjecutora = mi.unidadesEjecutoras[0];
		mi.prestamo = mi.prestamos[0];
		mi.componente = mi.componentes[0];
		mi.producto = mi.productos[0];
		mi.subProducto = mi.subProductos[0];
	}
	
	mi.displayObjeto = function(objetoSeleccionado){
		mi.mostrarcargando=false;
		if(objetoSeleccionado === 0){
			mi.entidadHide = false;
			mi.unidadEjecutoraHide = false;
			mi.prestamoHide = false;
			mi.componenteHide = false;
			mi.productoHide = false;
			mi.subProductoHide = false;
			mi.reset();
		}else if(objetoSeleccionado === 1){
			mi.entidadHide = true;
			mi.unidadEjecutoraHide = true;
			mi.prestamoHide = true;
			mi.componenteHide = false;
			mi.productoHide = false;
			mi.subProductoHide = false;
			mi.reset();
			mi.getEntidades();
		}else if(objetoSeleccionado === 2){
			mi.entidadHide = true;
			mi.unidadEjecutoraHide = true;
			mi.prestamoHide = true;
			mi.componenteHide = true;
			mi.productoHide = false;
			mi.subProductoHide = false;
			mi.reset();
			mi.getEntidades();
		}else if (objetoSeleccionado === 3){
			mi.entidadHide = true;
			mi.unidadEjecutoraHide = true;
			mi.prestamoHide = true;
			mi.componenteHide = true;
			mi.productoHide = true;
			mi.subProductoHide = false;
			mi.reset();
			mi.getEntidades();
		}else if (objetoSeleccionado === 4){
			mi.entidadHide = true;
			mi.unidadEjecutoraHide = true;
			mi.prestamoHide = true;
			mi.componenteHide = true;
			mi.productoHide = true;
			mi.subProductoHide = true;
			mi.reset();
			mi.getEntidades();
		}
	}
	
	mi.getEntidades = function(){
		
		$http.post('/SEntidad', {accion: 'cargar'}).success(
				function(response){
					mi.entidades = [];
					mi.entidades.push({'value' : 0, 'text' : 'Seleccione una opción'});
					if(response.success){
						for(var i=0; i< response.entidades.length; i++){
							mi.entidades.push({'value' : response.entidades[i].entidad, 'text' : response.entidades[i].nombre});
						}
						
						mi.entidad = mi.entidades[0];
					}
				}
		)
	}
	
	
	
	$scope.toggle = function () {
	      mi.type = mi.type === 'polarArea' ?
	        'pie' : 'polarArea';
	};
	
	mi.charOptions= {
		legend: { display: true, position: 'top' }
	}
	
	mi.generar = function(){
		
		if (mi.tObjeto.value == 1 || mi.prestamo.value != 0){
			mi.grafica = true;
			mi.idPrestamo = mi.prestamo.value;
			mi.mostrar = false;
			
			$http.post('/SCargaTrabajo', {accion: 'getEstructrua', idPrestamo :mi.prestamo.value}).success(
					function(response){
						var estructura = response.estructura;
						
						mi.objetosSeleccionados.push({
							objetoId: estructura.objetoId,
							objetoTipo: estructura.objetoTipo
						})
						
						if (estructura.children!=null || estructura.children!= undefined)
							mi.agregarhijos(estructura.children);
						
						var idPrestamos = "";
						var idComponentes = "";
						var idProductos = "";
						var idSubproductos = "";
						
						for (x in mi.objetosSeleccionados){
							switch (mi.objetosSeleccionados[x].objetoTipo){
								case 1: idPrestamos = idPrestamos + (idPrestamos.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
								break;
								case 2: idComponentes = idComponentes + (idComponentes.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
								break;
								case 3: idProductos = idProductos + (idProductos.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
								break;
								case 4: idSubproductos = idSubproductos + (idSubproductos.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
								break;
							} 
						}
						
						$http.post('/SCargaTrabajo', 
								{
									accion: 'getCargaTrabajoPrestamo', 
									idPrestamos:idPrestamos,
									idComponentes:idComponentes,
									idProductos:idProductos,
									idSubproductos:idSubproductos,
									anio_inicio:mi.fechaInicio,
									anio_fin: mi.fechaFin
									
								}).success(function(response){
									if(response.success){
										mi.rowCollection = [];
										mi.rowCollection = response.cargatrabajo;
								        mi.displayedCollection = [].concat(mi.rowCollection);
								        mi.actividadesAtrasadasTotal = 0;
										mi.actividadesAlertaTotal = 0;
										mi.actividadesACumplirTotal = 0; 
										mi.actividadesCompletadas  = 0;
								        for (x in mi.rowCollection){
								        	mi.actividadesAtrasadasTotal = mi.actividadesAtrasadasTotal + mi.rowCollection[x].actividadesAtrasadas;
											mi.actividadesAlertaTotal = mi.actividadesAlertaTotal + mi.rowCollection[x].actividadesAlerta;
											mi.actividadesACumplirTotal = mi.actividadesACumplirTotal +  mi.rowCollection[x].actividadesACumplir; 
											mi.actividadesCompletadas = mi.actividadesCompletadas + mi.rowCollection[x].actividadesCompletadas;
								        }
								        
								        
								    	
								    	 mi.labels = ["Retrasadas", "En alerta", "A cumplir","Completadas"];
								    	 mi.data = [mi.actividadesAtrasadasTotal, mi.actividadesAlertaTotal,
								    		 mi.actividadesACumplirTotal,mi.actividadesCompletadas];
								    	 
								    	 mi.mostrar = true;
									}
								});
						
						
						$http.post('/SCargaTrabajo', 
								{
									accion: 'getActividadesTerminadas', 
									idPrestamos:idPrestamos,
									idComponentes:idComponentes,
									idProductos:idProductos,
									idSubproductos:idSubproductos,
									anio_inicio:mi.fechaInicio,
									anio_fin: mi.fechaFin
									
									
								}).success(function(response){
									if(response.success){
										mi.actividadesterminadas = response.actividadesterminadas;
										mi.dataChartLine = [];
										mi.etiquetasChartLine=[];
										
										var aniotemp=0;
										for (x in mi.actividadesterminadas ){
											
											
											if (x > 0 ){
												var sigueinteMes = mi.obtenerSiguienteMes(mestemp,aniotemp);
												while (sigueinteMes.mes < mi.actividadesterminadas[x].mes 
														|| sigueinteMes.anio < mi.actividadesterminadas[x].anio){
													mi.dataChartLine.push(0);
													mi.etiquetasChartLine.push (mi.obtenerMes(sigueinteMes.mes) + 
															(mi.fechaInicio != mi.fechaFin ? "-" + sigueinteMes.anio : ""));
													sigueinteMes = mi.obtenerSiguienteMes(sigueinteMes.mes,sigueinteMes.anio);	
												}
											}
											mi.dataChartLine.push(mi.actividadesterminadas[x].total)
											mi.etiquetasChartLine.push(mi.obtenerMes(mi.actividadesterminadas[x].mes) + 
													(mi.fechaInicio != mi.fechaFin ? "-" + mi.actividadesterminadas[x].anio : ""));
											 
											mestemp = mi.actividadesterminadas[x].mes;
											aniotemp = mi.actividadesterminadas[x].anio;
										}
										
										
										  
										  mi.dataChartLine = [
											  mi.dataChartLine
										    
										  ];
									}
								});
			});
			
			
			
			
		}
	};
	
	mi.obtenerSiguienteMes = function(mes,anio){
		var siguiente = [];
		if (mes <12){
			siguiente.mes = mes+ 1;
			siguiente.anio=anio;
		}else{
			siguiente.mes = 1;
			siguiente.anio = anio+ 1;
		}
		return siguiente;
	}
	
	
	mi.agregarhijos = function (hijos){
		for (x in hijos){
			
			mi.objetosSeleccionados.push(
					{
						objetoId: hijos[x].objetoId,
						objetoTipo: hijos[x].objetoTipo
					}
			);
		
			if (hijos[x].children!=null || hijos[x].children!= undefined)
				mi.agregarhijos(hijos[x].children);
		}
	}
	
	mi.existeResponsable = function(id){
		for(x in mi.cargaTrabajo){
			if (mi.cargaTrabajo[x].id == id)
				return x;
		}
		return -1;
	}
	
	mi.exportarExcel = function(){

		var idPrestamos = "";
		var idComponentes = "";
		var idProductos = "";
		var idSubproductos = "";
		
		for (x in mi.objetosSeleccionados){
			switch (mi.objetosSeleccionados[x].objetoTipo){
				case 1: idPrestamos = idPrestamos + (idPrestamos.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
				break;
				case 2: idComponentes = idComponentes + (idComponentes.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
				break;
				case 3: idProductos = idProductos + (idProductos.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
				break;
				case 4: idSubproductos = idSubproductos + (idSubproductos.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
				break;
			} 
		}
		
		$http.post('/SCargaTrabajo', { 
			accion: 'exportarExcel', 
			idPrestamos:idPrestamos,
			idComponentes:idComponentes,
			idProductos:idProductos,
			idSubproductos:idSubproductos,
			anio_inicio:mi.fechaInicio,
			anio_fin: mi.fechaFin,
			t:moment().unix()
		  } ).then(
				  function successCallback(response) {
					  var anchor = angular.element('<a/>');
					  anchor.attr({
				         href: 'data:application/ms-excel;base64,' + response.data,
				         target: '_blank',
				         download: 'CargaTrabajo.xls'
					  })[0].click();
				  }.bind(this), function errorCallback(response){
					 		
			 	}
		  	);
		};
	
	mi.reset();
	
	// ------------
	mi.getEstructura = function (){
		
		if(mi.prestamo.value > 0)
		{
			if(mi.fechaInicio != null && mi.fechaInicio.toString().length == 4 && 
					mi.fechaFin != null && mi.fechaFin.toString().length == 4)
			{
				if (mi.fechaFin >= mi.fechaInicio){
					mi.inicializar();
					mi.generar();
				}else{
					$utilidades.mensaje('warning','La fecha inicial es mayor a la fecha final');
				}
			}
		}
		
		
		
		
	}
	
	mi.llamarModal = function(idproyecto) {
		var resultado = $q.defer();
		var modalInstance = $uibModal.open({
			animation : 'true',
			ariaLabelledBy : 'modal-title',
			ariaDescribedBy : 'modal-body',
			templateUrl : 'estructuraproyecto.jsp',
			controller : 'modalEstructura',
			controllerAs : 'estructura',
			backdrop : 'static',
			size : 'md',
			resolve : {
				$idproyecto : function() {
					return idproyecto;
				}				
			}
		});
		
		modalInstance.result.then(function(itemSeleccionado) {
			resultado.resolve(itemSeleccionado);
		});
		return resultado.promise;
	};
	
	mi.filtrarEstrucrura = function(titulo, mensaje){
		var resultado = mi.llamarModal(mi.prestamo.value); 
		mi.mostrar = false;
		resultado.then(function(itemSeleccionados) {
			mi.objetosSeleccionados = itemSeleccionados;
			var idPrestamos = "";
			var idComponentes = "";
			var idProductos = "";
			var idSubproductos = "";
			
			for (x in mi.objetosSeleccionados){
				switch (mi.objetosSeleccionados[x].objetoTipo){
					case 1: idPrestamos = idPrestamos + (idPrestamos.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
					break;
					case 2: idComponentes = idComponentes + (idComponentes.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
					break;
					case 3: idProductos = idProductos + (idProductos.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
					break;
					case 4: idSubproductos = idSubproductos + (idSubproductos.length > 0 ? "," : "") + mi.objetosSeleccionados[x].objetoId; 
					break;
				} 
			}
			
			$http.post('/SCargaTrabajo', 
					{
						accion: 'getCargaTrabajoPrestamo', 
						idPrestamos:idPrestamos,
						idComponentes:idComponentes,
						idProductos:idProductos,
						idSubproductos:idSubproductos
						
					}).success(function(response){
						if(response.success){
							mi.rowCollection = [];
							mi.rowCollection = response.cargatrabajo;
					        mi.displayedCollection = [].concat(mi.rowCollection);
					        mi.actividadesAtrasadasTotal = 0;
							mi.actividadesAlertaTotal = 0;
							mi.actividadesACumplirTotal = 0; 
							mi.actividadesCompletadas  = 0;
					        for (x in mi.rowCollection){
					        	mi.actividadesAtrasadasTotal = mi.actividadesAtrasadasTotal + mi.rowCollection[x].actividadesAtrasadas;
								mi.actividadesAlertaTotal = mi.actividadesAlertaTotal + mi.rowCollection[x].actividadesAlerta;
								mi.actividadesACumplirTotal = mi.actividadesACumplirTotal +  mi.rowCollection[x].actividadesACumplir; 
								mi.actividadesCompletadas = mi.actividadesCompletadas + mi.rowCollection[x].actividadesCompletadas;
					        }
					        
					        
					    	
					    	 mi.labels = ["Actividades retrasadas", "Actividades en alerta", "Actividades a cumplir","Actividades completadas"];
					    	 mi.data = [mi.actividadesAtrasadasTotal, mi.actividadesAlertaTotal,
					    		 mi.actividadesACumplirTotal,mi.actividadesCompletadas];
					    	 mi.mostrar = true;
						}
					});
			
			
		});
	};
	
	
	mi.llamarModalEstructuraResponsable = function(idproyecto,idresponsable) {
		var resultado = $q.defer();
		var modalInstance = $uibModal.open({
			animation : 'true',
			ariaLabelledBy : 'modal-title',
			ariaDescribedBy : 'modal-body',
			templateUrl : 'estructuraresponsable.jsp',
			controller : 'modalEstructuraResponsable',
			controllerAs : 'estructura',
			backdrop : 'static',
			size : 'lg',
			resolve : {
				$idproyecto : function() {
					return idproyecto;
				},
				$idresponsable : function() {
					return idresponsable;
				}	
			}
		});
		
		modalInstance.result.then(function(itemSeleccionado) {
			resultado.resolve(itemSeleccionado);
		});
		return resultado.promise;
	};
	
	
	mi.actividadesResponsable = function(valor){
		var resultado = mi.llamarModalEstructuraResponsable(mi.prestamo.value,valor.id); 
	};
	
	
	mi.obtenerMes = function(valor){
		switch (valor){
			case 1: return "Ene"; 
			case 2: return "Feb"; 
			case 3: return "Mar"; 
			case 4: return "Abr"; 
			case 5: return "May"; 
			case 6: return "Jun"; 
			case 7: return "Jul"; 
			case 8: return "Ago"; 
			case 9: return "Sept"; 
			case 10: return "Octe"; 
			case 11: return "Nov"; 
			case 12: return "Dic"; 
		}
	}
	
	mi.inicializar = function(){
		mi.idTotal = 0;
	    mi.responsableTotal = "Total";
	    mi.actividadesAtrasadasTotal = 0;
		mi.actividadesAlertaTotal = 0;
		mi.actividadesACumplirTotal = 0; 
		mi.actividadesCompletadas  = 0;
	    mi.exportar = false;
	    mi.grafica = true;
	    mi.idPrestamo = 0;
	    mi.estructuraProyecto=[];
	    mi.objetosSeleccionados=[];
	    mi.datosTabla = [];
	    mi.mostrar = false;
	    
	    mi.dataChartLine = [];
	    mi.etiquetasChartLine = [];
	    mi.actividadesterminadas = [];
	};
	
}]);



app.controller('modalEstructura', [ '$uibModalInstance',
	'$scope', '$http', '$interval', 'i18nService', 'Utilidades',
	'$timeout', '$log', '$idproyecto',modalEstructura ]);

function modalEstructura($uibModalInstance, $scope, $http, $interval,
	i18nService, $utilidades, $timeout, $log, $idproyecto) {

	var mi = this;
	
	mi.seleccionados = [];
	
	$http.post('/SCargaTrabajo', {accion: 'getEstructrua', idPrestamo :$idproyecto}).success(
			function(response){
				mi.estructuraProyecto = response.estructura;
				
	});

	

	mi.ok = function() {
		mi.seleccionados = [];
		
		mi.customOpts = {
			  useCheckboxes: false,
			  onToggle: mi.awesomeCallback,
			  twistieCollapsedTpl: '<span class="glyphicon glyphicon-chevron-right"></span>',
			  twistieExpandedTpl: '<span class="glyphicon glyphicon-chevron-down"></span>',
			  
			};
		
		if (mi.estructuraProyecto.selected)
			mi.seleccionados.push({objetoId: mi.estructuraProyecto.objetoId, objetoTipo: mi.estructuraProyecto.objetoTipo});
		
		if (mi.estructuraProyecto.children!=null || mi.estructuraProyecto.children!= undefined)
			mi.agregarhijos(mi.estructuraProyecto.children);
		
		if (mi.seleccionados.length > 0) {
			$uibModalInstance.close(mi.seleccionados);
		} else {
			$utilidades.mensaje('warning', 'Debe seleccionar una fila');
		}
	};

	mi.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
	
	mi.agregarhijos = function (hijos){
		for (x in hijos){
			if (hijos[x].selected)
				mi.seleccionados.push(
						{
							objetoId: hijos[x].objetoId,
							objetoTipo: hijos[x].objetoTipo
						}
				);
			
			if (hijos[x].children!=null || hijos[x].children!= undefined)
				mi.agregarhijos(hijos[x].children);
		}
	}
};


app.controller('modalEstructuraResponsable', [ '$uibModalInstance',
	'$scope', '$http', '$interval', 'i18nService', 'Utilidades',
	'$timeout', '$log', '$idproyecto','$idresponsable',modalEstructuraResponsable ]);

function modalEstructuraResponsable($uibModalInstance, $scope, $http, $interval,
	i18nService, $utilidades, $timeout, $log, $idproyecto,$idresponsable) {

	var mi = this;
	
	
	
	$http.post('/SCargaTrabajo', {accion: 'getEstructruaPorResponsable', idPrestamo :$idproyecto,idColaborador:$idresponsable}).success(
			function(response){
				mi.estructuraProyecto = response.estructura;
				
	});

	

	mi.ok = function() {
		
	};

	mi.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
	
	mi.claseIcon = function (item) {
	    switch (item.objetoTipo) {
	        case 1:
	            return 'glyphicon glyphicon-record';
	        case 2:
	            return 'glyphicon glyphicon-th';
	        case 3:
	            return 'glyphicon glyphicon-certificate';
	        case 4:
	            return 'glyphicon glyphicon-link';
	        case 5:
	            return 'glyphicon glyphicon-th-list';
	    }
	};
	
	mi.obtenerColor = function(row){
		
		 
		var style={}
		switch (row.estado){
			case 1: style.color="#fd9496"; break;
			case 2: style.color="#e2e291"; break;
			case 3: style.color="#c7e7a5"; break;
			case 4: style.color="#b0cfe8"; break;
		}
		return style;
	}
	

	mi.seriesLine = function (){
		
		
		
	}
	
	
	
	
};

