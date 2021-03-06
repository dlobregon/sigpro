var app = angular.module('riesgoController', []);

app.controller('riesgoController',['$scope','$http','$interval','i18nService','Utilidades','$routeParams','$window','$location','$route','uiGridConstants','$mdDialog','$uibModal','$q', 'dialogoConfirmacion', 
	function($scope, $http, $interval,i18nService,$utilidades,$routeParams,$window,$location,$route,uiGridConstants,$mdDialog,$uibModal,$q, $dialogoConfirmacion) {
		var mi=this;
		
		$window.document.title = $utilidades.sistema_nombre+' - Riesgos';
		i18nService.setCurrentLang('es');
		mi.mostrarcargando=true;
		mi.riesgos = [];
		mi.riesgo;
		mi.mostraringreso=false;
		mi.esnuevo = false;
		mi.totalRiesgos = 0;
		mi.paginaActual = 1;
		mi.riesgoTipoid = "";
		mi.riesgoTipoNombre="";
		mi.componenteid = "";
		mi.componenteNombre="";
		mi.productoid="";
		mi.productoNombre="";
		mi.colaboradorid = ""
		mi.colaboradorNombre = "";
		mi.camposdinamicos = {};
		mi.formatofecha = 'dd/MM/yyyy';
		mi.colaboradorid="";
		mi.colaboradorNombre="";
		mi.numeroMaximoPaginas = $utilidades.numeroMaximoPaginas;
		mi.elementosPorPagina = $utilidades.elementosPorPagina;
		mi.proyectoid = "";
		mi.objetoid = $routeParams.objeto_id;
		mi.objetotipo = $routeParams.objeto_tipo;
		mi.objetoNombre = "";
		mi.objetoTipoNombre="";
		
		mi.columnaOrdenada=null;
		mi.ordenDireccion = null;
		mi.filtros = [];
		mi.orden = null;
		mi.probabilidades = [{valor:1, nombre:"Bajo"},{valor:2,nombre:"Medio"},{valor:3,nombre:"Alto"}];
		
		mi.fechaOptions = {
				formatYear : 'yy',
				maxDate : new Date(2020, 5, 22),
				minDate : new Date(1900, 1, 1),
				startingDay : 1
		};
		
		$http.post('/SObjeto', { accion: 'getObjetoPorId', id: $routeParams.objeto_id, tipo: mi.objetotipo }).success(
				function(response) {
					mi.objetoid = response.id;
					mi.objetoNombre = response.nombre;
					mi.objetoTipoNombre = response.tiponombre;
		});
		
		mi.editarElemento = function (event) {
	        var filaId = angular.element(event.toElement).scope().rowRenderIndex;
	        mi.gridApi.selection.selectRow(mi.gridOptions.data[filaId]);
	        mi.editar();
	    };
	    
		mi.gridOptions = {
				enableRowSelection : true,
				enableRowHeaderSelection : false,
				multiSelect: false,
				modifierKeysToMultiSelect: false,
				noUnselect: true,
				enableFiltering: true,
				enablePaginationControls: false,
			    paginationPageSize: $utilidades.elementosPorPagina,
			    rowTemplate: '<div ng-dblclick="grid.appScope.riesgoc.editarElemento($event)" ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.uid" ui-grid-one-bind-id-grid="rowRenderIndex + \'-\' + col.uid + \'-cell\'" class="ui-grid-cell ng-scope ui-grid-disable-selection grid-align-right" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader }" role="gridcell" ui-grid-cell="" ></div>',
				columnDefs : [ 
					{ name: 'id', width: 100, displayName: 'ID', cellClass: 'grid-align-right', type: 'number', enableFiltering: false },
				    { name: 'nombre', width: 200, displayName: 'Nombre',cellClass: 'grid-align-left' ,
						filterHeaderTemplate: '<div class="ui-grid-filter-container"><input type="text" style="width: 90%;" ng-model="grid.appScope.riesgoc.filtros[\'nombre\']" ng-keypress="grid.appScope.riesgoc.filtrar($event)"></input></div>'
				    },
				    { name: 'descripcion', displayName: 'Descripción', cellClass: 'grid-align-left', enableFiltering: false},
				    { name: 'usuarioCreo', displayName: 'Usuario Creación' ,
				    	filterHeaderTemplate: '<div class="ui-grid-filter-container"><input type="text" style="width: 90%;" ng-model="grid.appScope.riesgoc.filtros[\'usuario_creo\']" ng-keypress="grid.appScope.riesgoc.filtrar($event)"></input></div>'
				    },
				    { name: 'fechaCreacion', displayName: 'Fecha Creación', cellClass: 'grid-align-right', type: 'date', cellFilter: 'date:\'dd/MM/yyyy\'', 
				    	filterHeaderTemplate: '<div class="ui-grid-filter-container"><input type="text" style="width: 90%;" ng-model="grid.appScope.riesgoc.filtros[\'fecha_creacion\']" ng-keypress="grid.appScope.riesgoc.filtrar($event)"></input></div>'
				    }
				],
				onRegisterApi: function(gridApi) {
					mi.gridApi = gridApi;
					gridApi.selection.on.rowSelectionChanged($scope,function(row) {
						mi.riesgo = row.entity;
						if (mi.riesgo.fechaEjecucion!=null && mi.riesgo.fechaEjecucion != '')
							mi.riesgo.fechaEjecucion = moment(mi.riesgo.fechaEjecucion,'DD/MM/YYYY').toDate();
					});
					
					gridApi.core.on.sortChanged( $scope, function ( grid, sortColumns ) {
						if(sortColumns.length==1){
							grid.appScope.riesgoc.columnaOrdenada=sortColumns[0].field;
							grid.appScope.riesgoc.ordenDireccion = sortColumns[0].sort.direction;
							for(var i = 0; i<sortColumns.length-1; i++)
								sortColumns[i].unsort();
							grid.appScope.riesgoc.cargarTabla(grid.appScope.riesgoc.paginaActual);
						}
						else if(sortColumns.length>1){
							sortColumns[0].unsort();
						}
						else{
							if(grid.appScope.riesgoc.columnaOrdenada!=null){
								grid.appScope.riesgoc.columnaOrdenada=null;
								grid.appScope.riesgoc.ordenDireccion=null;
							}
						}
					} );
					
					if($routeParams.reiniciar_vista=='rv'){
						mi.guardarEstado();
						mi.obtenerTotalRiesgos();
				    }
				    else{
				    	  $http.post('/SEstadoTabla', { action: 'getEstado', grid:'riesgos', t: (new Date()).getTime()}).then(function(response){
					      if(response.data.success && response.data.estado!='')
					    	  mi.gridApi.saveState.restore( $scope, response.data.estado);
					    	  mi.gridApi.colMovable.on.columnPositionChanged($scope, mi.guardarEstado);
						      mi.gridApi.colResizable.on.columnSizeChanged($scope, mi.guardarEstado);
						      mi.gridApi.core.on.columnVisibilityChanged($scope, mi.guardarEstado);
						      mi.gridApi.core.on.sortChanged($scope, mi.guardarEstado);
						      mi.obtenerTotalRiesgos();
						  });
				    }
				}
			};
		
		mi.cargarTabla = function(pagina){
			mi.mostrarcargando=true;
			$http.post('/SRiesgo', { accion: 'getRiesgosPaginaPorObjeto', pagina: pagina, 
				numeroriesgos: $utilidades.elementosPorPagina, objetoid:$routeParams.objeto_id,objetotipo:$routeParams.objeto_tipo
				,filtro_nombre: mi.filtros['nombre'],
				filtro_usuario_creo: mi.filtros['usuario_creo'], filtro_fecha_creacion: mi.filtros['fecha_creacion'],
				columna_ordenada: mi.columnaOrdenada, orden_direccion: mi.ordenDireccion
				}).success(

					function(response) {
						mi.riesgos = response.riesgos;
						mi.gridOptions.data = mi.riesgos;
						mi.mostrarcargando = false;
					});
		}
		mi.redireccionSinPermisos=function(){
			$window.location.href = '/main.jsp#!/forbidden';		
		}
		mi.guardar=function(){
			
			for (campos in mi.camposdinamicos) {
				if (mi.camposdinamicos[campos].tipo === 'fecha') {
					mi.camposdinamicos[campos].valor_f = moment(mi.camposdinamicos[campos].valor).format('DD/MM/YYYY')
				}
			}
			
			if(mi.riesgo!=null && mi.riesgo.nombre!='' && mi.riesgoTipoid!='' && mi.probabilidad!=null){
				$http.post('/SRiesgo', {
					accion: 'guardarRiesgo',
					esnuevo: mi.esnuevo,
					id: mi.riesgo.id,
					objetoid: mi.objetoid,
					objetotipo: mi.objetotipo,
					riesgotipoid : mi.riesgoTipoid,
					nombre: mi.riesgo.nombre,
					descripcion: mi.riesgo.descripcion,
					objetoId: $routeParams.objeto_id,
					impacto: mi.riesgo.impacto,
					puntuacionimpoacto: mi.riesgo.puntuacionImpacto,
					impactoproyectado: mi.riesgo.impactoProyectado,
					probabilidad: mi.probabilidad!=null ? mi.probabilidad.valor : null,
					gatillossintomas: mi.riesgo.gatillosSintomas,
					respuesta: mi.riesgo.respuesta,
					colaboradorid: mi.colaboradorid,
					riesgossecundarios: mi.riesgo.riesgosSecundarios,
					ejecutado: mi.ejecutado == true ? 1 : 0 ,
					fechaejecucion: moment(mi.riesgo.fechaEjecucion).format('DD/MM/YYYY'),
					objetoTipo:  $routeParams.objeto_tipo,
					datadinamica : JSON.stringify(mi.camposdinamicos)
				}).success(function(response){
					if(response.success){
						mi.esnuevo = false;
						mi.riesgo.id = response.id;
						$utilidades.mensaje('success','Riesgo '+(mi.esnuevo ? 'creado' : 'guardado')+' con éxito');
						mi.riesgo.usuarioCreo = response.usuarioCreo;
						mi.riesgo.fechaCreacion = response.fechaCreacion;
						mi.riesgo.usuarioActualizo = response.usuarioactualizo;
						mi.riesgo.fechaActualizacion = response.fechaactualizacion;
						mi.cargarTabla();
					}
					else
						$utilidades.mensaje('danger','Error al '+(mi.esnuevo ? 'creado' : 'guardado')+' el Riesgo');
				});
			}
			else
				$utilidades.mensaje('warning','Debe de llenar todos los campos obligatorios');
		};

		mi.borrar = function(ev) {
			if(mi.riesgo!=null && mi.riesgo.id!=null){
				$dialogoConfirmacion.abrirDialogoConfirmacion($scope
						, "Confirmación de Borrado"
						, '¿Desea borrar el Riesgo "'+mi.riesgo.nombre+'"?'
						, "Borrar"
						, "Cancelar")
				.result.then(function(data) {
					if(data){
						$http.post('/SRiesgo', {
							accion: 'borrarRiesgo',
							id: mi.riesgo.id
						}).success(function(response){
							if(response.success){
								$utilidades.mensaje('success','Riesgo borrado con éxito');
								mi.riesgo = null;
								mi.cargarTabla();
							}
							else
								$utilidades.mensaje('danger','Error al borrar el Riesgo');
						});
					}
				}, function(){
					
				});
			}
			else
				$utilidades.mensaje('warning','Debe seleccionar el Riesgo que desea borrar');
		};

		mi.nuevo = function() {
			mi.mostraringreso=true;
			mi.esnuevo = true;
			mi.riesgo = {};
			mi.riesgoTipoid = "";
			mi.riesgoTipoNombre="";
			mi.componenteid = "";
			mi.componenteNombre="";
			mi.productoid="";
			mi.productoNombre="";
			mi.camposdinamicos = {};
			mi.probabilidad = {}; 
			mi.gridApi.selection.clearSelectedRows();
		};

		mi.editar = function() {
			if(mi.riesgo!=null && mi.riesgo.id!=null){
				mi.riesgoTipoid = mi.riesgo.riesgotipoid;
				mi.riesgoTipoNombre = mi.riesgo.riesgotiponombre;
				mi.componenteid = mi.riesgo.componenteid;
				mi.componenteNombre = mi.riesgo.componentenombre;
				mi.productoid = mi.riesgo.productoid;
				mi.productoNombre = mi.riesgo.productonombre;
				mi.colaboradorNombre = mi.riesgo.colaboradornombre,
				mi.colaboradorid = mi.riesgo.colaboradorid;
				mi.mostraringreso = true;
				mi.esnuevo = false;
				mi.colaboradorNombre = mi.riesgo.colaboradorNombre;
				mi.colaboradorId = mi.riesgo.colaboradorId;
				if (mi.riesgo.probabilidad !=null && mi.riesgo.probabilidad > 0){
					mi.probabilidad = {
							"valor" : mi.riesgo.probabilidad,
							"nombre" : mi.probabilidades[mi.riesgo.probabilidad - 1].nombre
					}
				}else {
					mi.probabilidad = {};
				}
				mi.ejecutado = mi.riesgo.ejecutado == 1;
				
				var parametros = { 
						accion: 'getRiesgoPropiedadPorTipo', 
						idRiesgo: mi.riesgo.id,
						idRiesgoTipo: mi.riesgoTipoid
				}
				
				$http.post('/SRiesgoPropiedad', parametros).then(function(response){
					mi.camposdinamicos = response.data.componentepropiedades;					
					for (campos in mi.camposdinamicos) {
						switch (mi.camposdinamicos[campos].tipo){
							case "fecha":
								mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? moment(mi.camposdinamicos[campos].valor,'DD/MM/YYYY').toDate() : null;
								break;
							case "entero":
								mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? Number(mi.camposdinamicos[campos].valor) : null;
								break;
							case "decimal":
								mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? Number(mi.camposdinamicos[campos].valor) : null;
								break;
							case "booleano":
								mi.camposdinamicos[campos].valor = mi.camposdinamicos[campos].valor == 'true' ? true : false;
								break;
						}
					}
				});
			}
			else
				$utilidades.mensaje('warning','Debe seleccionar el Riesgo que desea editar');
		}

		mi.irATabla = function() {
			mi.mostraringreso=false;
			mi.esnuevo = false;
		}
		
		mi.guardarEstado=function(){
			var estado = mi.gridApi.saveState.save();
			var tabla_data = { action: 'guardaEstado', grid:'riesgos', estado: JSON.stringify(estado), t: (new Date()).getTime() }; 
			$http.post('/SEstadoTabla', tabla_data).then(function(response){
			});
		}
		
		mi.cambioPagina=function(){
			mi.cargarTabla(mi.paginaActual);
		}
		
		mi.reiniciarVista=function(){
			if($location.path()==('/riesgo/rv'))
				$route.reload();
			else
				$location.path('/riesgo/rv');
		}
		
		mi.abrirPopupFecha = function(index) {
			
			if(index<1000){
				mi.camposdinamicos[index].isOpen = true;
			}
			else{
				switch(index){
					case 1000: mi.fe_abierto = true; break;
				}
			}
		};
		
		
		
		mi.filtrar = function(evt){
			if(evt.keyCode==13){
				mi.obtenerTotalRiesgos();
				mi.gridApi.selection.clearSelectedRows();
				mi.riesgo = null;
			}
		};

		mi.obtenerTotalRiesgos = function(){
			$http.post('/SRiesgo', { accion: 'numeroRiesgosPorObjeto',
				objetoid:$routeParams.objeto_id,objetotipo:$routeParams.objeto_tipo
				,filtro_nombre: mi.filtros['nombre'],
				filtro_usuario_creo: mi.filtros['usuario_creo'], filtro_fecha_creacion: mi.filtros['fecha_creacion']  }).then(
					function(response) {
						mi.totalRiesgos = response.data.totalriesgos;
						mi.paginaActual = 1;
						mi.cargarTabla(mi.paginaActual);
			});
		};
		
		$http.post('/SRiesgo', { accion: 'numeroRiesgos' }).success(
				function(response) {
					mi.totalRiesgos = response.totalriesgos;
					mi.cargarTabla(1);
		});
		
		mi.llamarModalBusqueda = function(servlet, accionServlet, datosCarga,columnaId,columnaNombre) {
			var resultado = $q.defer();
			var modalInstance = $uibModal.open({
				animation : 'true',
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'buscarPorRiesgo.jsp',
				controller : 'buscarPorRiesgo',
				controllerAs : 'modalBuscar',
				backdrop : 'static',
				size : 'md',
				resolve : {
					$servlet : function() {
						return servlet;
					},
					$accionServlet : function() {
						return accionServlet;
					},
					$datosCarga : function() {
						return datosCarga;
					},
					$columnaId : function() {
						return columnaId;
					},
					$columnaNombre : function() {
						return columnaNombre;
					}
				}
			});

			modalInstance.result.then(function(itemSeleccionado) {
				resultado.resolve(itemSeleccionado);
			});
			return resultado.promise;
		};
	
	
	
	
	mi.buscarRiesgoTipo = function() {
		var resultado = mi.llamarModalBusqueda('/SRiesgoTipo', {
			accion : 'numeroComponenteTipos'
		}, function(pagina, elementosPorPagina) {
			return {
				accion : 'getRiesgotiposPagina',
				pagina : pagina,
				numeroriesgostipo : elementosPorPagina
			};
		},'id','nombre');
		
		
		resultado.then(function(itemSeleccionado) {
			mi.riesgoTipoid = itemSeleccionado.id;
			mi.riesgoTipoNombre = itemSeleccionado.nombre;
			
			var parametros = { 
					accion: 'getRiesgoPropiedadPorTipo', 
					idRiesgo: mi.riesgo!=null ? mi.riesgo.id : 0,
					idRiesgoTipo: itemSeleccionado.id
			}
			
			$http.post('/SRiesgoPropiedad', parametros).then(function(response){
				mi.camposdinamicos = response.data.componentepropiedades;
				for (campos in mi.camposdinamicos) {
					switch (mi.camposdinamicos[campos].tipo){
					case "fecha":
						mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? moment(mi.camposdinamicos[campos].valor,'DD/MM/YYYY').toDate() : null;
						break;
					case "entero":
						mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? Number(mi.camposdinamicos[campos].valor) : null;
						break;
					case "decimal":
						mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? Number(mi.camposdinamicos[campos].valor) : null;
						break;
					case "booleano":
						mi.camposdinamicos[campos].valor = mi.camposdinamicos[campos].valor == 'true' ? true : false;
						break;
					}
				}
				
			});
		});
	};
	
	mi.buscarColaborador = function() {
		var resultado = mi.llamarModalBusqueda('/SColaborador', {
			accion : 'totalElementos'
		}, function(pagina, elementosPorPagina) {
			return {
				accion : 'cargar',
				pagina : pagina,
				registros : elementosPorPagina
			};
		},'id','nombreCompleto');
		
		
		resultado.then(function(itemSeleccionado) {
			mi.colaboradorid = itemSeleccionado.id;
			mi.colaboradorNombre = itemSeleccionado.nombreCompleto;
			
		});
	};
	
			
} ]);

app.controller('buscarPorRiesgo', [ '$uibModalInstance',
	'$scope', '$http', '$interval', 'i18nService', 'Utilidades',
	'$timeout', '$log', '$servlet', '$accionServlet', '$datosCarga','$columnaId','$columnaNombre',buscarPorRiesgo ]);

function buscarPorRiesgo($uibModalInstance, $scope, $http, $interval,
	i18nService, $utilidades, $timeout, $log, $servlet,$accionServlet,$datosCarga, $columnaId,$columnaNombre) {
	
	var mi = this;

	mi.totalElementos = 0;
	mi.paginaActual = 1;
	mi.numeroMaximoPaginas = 5;
	mi.elementosPorPagina = 9;

	mi.mostrarCargando = false;
	mi.data = [];

	mi.itemSeleccionado = null;
	mi.seleccionado = false;
	
	$http.post($servlet, $accionServlet).success(function(response) {
		for ( var key in response) {
			mi.totalElementos = response[key];
		}
		mi.cargarData(1);
	});
	
	mi.opcionesGrid = {
		data : mi.data,
		columnDefs : [ {
			displayName : 'ID',
			name : $columnaId,
			cellClass : 'grid-align-right',
			type : 'number',
			width : 70
		}, {
			displayName : 'Nombre',
			name : $columnaNombre,
			cellClass : 'grid-align-left'
		} ],
		enableRowSelection : true,
		enableRowHeaderSelection : false,
		multiSelect : false,
		modifierKeysToMultiSelect : false,
		noUnselect : false,
		enableFiltering : true,
		enablePaginationControls : false,
		paginationPageSize : 5,
		onRegisterApi : function(gridApi) {
			mi.gridApi = gridApi;
			mi.gridApi.selection.on.rowSelectionChanged($scope,
					mi.seleccionarTipoRiesgo);
		}
	}

	mi.seleccionarTipoRiesgo = function(row) {
		mi.itemSeleccionado = row.entity;
		mi.seleccionado = row.isSelected;
	};
	
	mi.cargarData = function(pagina) {
		mi.mostrarCargando = true;
		$http.post($servlet, $datosCarga(pagina, mi.elementosPorPagina)).then(
				function(response) {
					if (response.data.success) {

						for ( var key in response.data) {
							if (key != 'success')
								mi.data = response.data[key];
						}
						mi.opcionesGrid.data = mi.data;

						mi.mostrarCargando = false;
					}
				});
	};

	mi.cambioPagina = function() {
		mi.cargarData(mi.paginaActual);
	}

	mi.ok = function() {
		if (mi.seleccionado) {
			$uibModalInstance.close(mi.itemSeleccionado);
		} else {
			$utilidades.mensaje('warning', 'Debe seleccionar una ENTIDAD');
		}
	};

	mi.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
}