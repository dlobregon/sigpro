var app = angular.module('actividadController', []);

app.controller('actividadController',['$scope','$http','$interval','i18nService','Utilidades','$routeParams','$window','$location','$route','uiGridConstants','$mdDialog','$uibModal','$q','$sce','uiGmapGoogleMapApi', 'dialogoConfirmacion', 
	function($scope, $http, $interval,i18nService,$utilidades,$routeParams,$window,$location,$route,uiGridConstants,$mdDialog,$uibModal,$q,$sce,uiGmapGoogleMapApi, $dialogoConfirmacion) {
		var mi=this;

		$window.document.title = $utilidades.sistema_nombre+' - Actividades';
		i18nService.setCurrentLang('es');
		mi.mostrarcargando=true;
		mi.actividades = [];
		mi.actividad;
		mi.mostraringreso=false;
		mi.esnuevo = false;
		mi.totalActividades = 0;
		mi.objetoid = $routeParams.objeto_id;
		mi.objetotipo = $routeParams.objeto_tipo;
		mi.objetoNombre="";
		mi.objetoTipoNombre = "";
		mi.paginaActual = 1;
		mi.datotipoid = "";
		mi.datotiponombre = "";
		mi.actividadtipoid = "";
		mi.actividadnombre = "";
		mi.formatofecha = 'dd/MM/yyyy';
		mi.camposdinamicos = {};
		mi.numeroMaximoPaginas = $utilidades.numeroMaximoPaginas;
		mi.elementosPorPagina = $utilidades.elementosPorPagina;
		
		mi.dimensiones = [
			{value:1,nombre:'Dias',sigla:'d'}
		];
		
		mi.duracionDimension = mi.dimensiones[0];
		
		mi.cambioDuracion = function(dimension){
			mi.actividad.fechaFin = mi.sumarDias(mi.actividad.fechaInicio,mi.actividad.duracion, dimension.sigla);
		}
		
		mi.sumarDias = function(fecha, dias, dimension){
			if(dimension != undefined && dias != undefined && fecha != ""){
				var cnt = 0;
			    var tmpDate = moment(fecha);
			    while (cnt < (dias -1 )) {
			    	if(dimension=='d'){
			    		tmpDate = tmpDate.add(1,'days');	
			    	}
			        if (tmpDate.weekday() != moment().day("Sunday").weekday() && tmpDate.weekday() != moment().day("Saturday").weekday()) {
			            cnt = cnt + 1;
			        }
			    }
			    tmpDate = moment(tmpDate,'DD/MM/YYYY').toDate();
			    return tmpDate;
			}
		}
		
		mi.responsables =[];

		mi.columnaOrdenada=null;
		mi.ordenDireccion = null;
		mi.coordenadas = "";

		mi.filtros = [];
		$http.post('/SObjeto', { accion: 'getObjetoPorId', id: $routeParams.objeto_id, tipo: mi.objetotipo }).success(
				function(response) {
					mi.objetoid = response.id;
					mi.objetoNombre = response.nombre;
					mi.objetoTipoNombre = response.tiponombre;
					var fechaInicioPadre = moment(response.fechaInicio, 'DD/MM/YYYY').toDate();
					mi.modificarFechaInicial(fechaInicioPadre);
		});
		
		mi.modificarFechaInicial = function(fechaPadre){
			mi.fi_opciones.minDate = fechaPadre;
		}
		
		mi.editarElemento = function (event) {
	        var filaId = angular.element(event.toElement).scope().rowRenderIndex;
	        mi.gridApi.selection.selectRow(mi.gridOptions.data[filaId]);
	        mi.editar();
	    };


		mi.fechaOptions = {
				formatYear : 'yy',
				maxDate : new Date(2050, 12, 31),
				minDate : new Date(1990, 1, 1),
				startingDay : 1
		};

		mi.fi_opciones = {
				formatYear : 'yy',
				maxDate : new Date(2050, 12, 31),
				minDate : new Date(1990, 1, 1),
				startingDay : 1
		};
		
		mi.ff_opciones = {
				formatYear : 'yy',
				maxDate : new Date(2050, 12, 31),
				minDate : new Date(1990, 1, 1),
				startingDay : 1
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
			    useExternalFiltering: true,
			    useExternalSorting: true,
			    rowTemplate: '<div ng-dblclick="grid.appScope.actividadc.editarElemento($event)" ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.uid" ui-grid-one-bind-id-grid="rowRenderIndex + \'-\' + col.uid + \'-cell\'" class="ui-grid-cell ng-scope ui-grid-disable-selection grid-align-right" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader }" role="gridcell" ui-grid-cell="" ></div>',
			    columnDefs : [
					{ name: 'id', width: 100, displayName: 'ID', cellClass: 'grid-align-right', type: 'number', enableFiltering: false },
				    { name: 'nombre', width: 200, displayName: 'Nombre',cellClass: 'grid-align-left',
						filterHeaderTemplate: '<div class="ui-grid-filter-container"><input type="text" style="width: 90%;" ng-model="grid.appScope.actividadc.filtros[\'nombre\']" ng-keypress="grid.appScope.actividadc.filtrar($event)"></input></div>'
				    },
				    { name: 'descripcion', displayName: 'Descripción', cellClass: 'grid-align-left', enableFiltering: false},
				    { name: 'usuarioCreo', displayName: 'Usuario Creación',
				    	filterHeaderTemplate: '<div class="ui-grid-filter-container"><input type="text" style="width: 90%;" ng-model="grid.appScope.actividadc.filtros[\'usuario_creo\']" ng-keypress="grid.appScope.actividadc.filtrar($event)"></input></div>'
				    },
				    { name: 'fechaCreacion', displayName: 'Fecha Creación', cellClass: 'grid-align-right', type: 'date', cellFilter: 'date:\'dd/MM/yyyy\'',
				    	filterHeaderTemplate: '<div class="ui-grid-filter-container"><input type="text" style="width: 90%;" ng-model="grid.appScope.actividadc.filtros[\'fecha_creacion\']" ng-keypress="grid.appScope.actividadc.filtrar($event)"></input></div>'
				    }
				],
				onRegisterApi: function(gridApi) {
					mi.gridApi = gridApi;
					gridApi.selection.on.rowSelectionChanged($scope,function(row) {
						mi.actividad = row.entity;
						mi.actividad.fechaInicio = moment(mi.actividad.fechaInicio,'DD/MM/YYYY').toDate();
						mi.actividad.fechaFin = moment(mi.actividad.fechaFin,'DD/MM/YYYY').toDate();
						mi.ff_opciones.minDate = mi.actividad.fechaInicio;
					});

					gridApi.core.on.sortChanged( $scope, function ( grid, sortColumns ) {
						if(sortColumns.length==1){
							grid.appScope.actividadc.columnaOrdenada=sortColumns[0].field;
							grid.appScope.actividadc.ordenDireccion = sortColumns[0].sort.direction;
							for(var i = 0; i<sortColumns.length-1; i++)
								sortColumns[i].unsort();
							grid.appScope.actividadc.cargarTabla(grid.appScope.actividadc.paginaActual);
						}
						else if(sortColumns.length>1){
							sortColumns[0].unsort();
						}
						else{
							if(grid.appScope.actividadc.columnaOrdenada!=null){
								grid.appScope.actividadc.columnaOrdenada=null;
								grid.appScope.actividadc.ordenDireccion=null;
							}
						}

					} );

					if($routeParams.reiniciar_vista=='rv'){
						mi.guardarEstado();
						 mi.obtenerTotalActividades();
				    }
				    else{
				    	  $http.post('/SEstadoTabla', { action: 'getEstado', grid:'actividades', t: (new Date()).getTime()}).then(function(response){
						      if(response.data.success && response.data.estado!='')
						    	  mi.gridApi.saveState.restore( $scope, response.data.estado);
						      mi.gridApi.colMovable.on.columnPositionChanged($scope, mi.guardarEstado);
							  mi.gridApi.colResizable.on.columnSizeChanged($scope, mi.guardarEstado);
							  mi.gridApi.core.on.columnVisibilityChanged($scope, mi.guardarEstado);

						      mi.obtenerTotalActividades();
						  });

				    }
				}
		};
		mi.redireccionSinPermisos=function(){
			$window.location.href = '/main.jsp#!/forbidden';
		}
		mi.cargarTabla = function(pagina){
			mi.mostrarcargando=true;
			$http.post('/SActividad', { accion: 'getActividadesPaginaPorObjeto', pagina: pagina, numeroactividades: $utilidades.elementosPorPagina,
				objetoid: $routeParams.objeto_id, tipo: mi.objetotipo,
				filtro_nombre: mi.filtros['nombre'],
				filtro_usuario_creo: mi.filtros['usuario_creo'], filtro_fecha_creacion: mi.filtros['fecha_creacion'],
				columna_ordenada: mi.columnaOrdenada, orden_direccion: mi.ordenDireccion
			}).success(
					function(response) {
						mi.actividades = response.actividades;
						mi.gridOptions.data = mi.actividades;
						mi.mostrarcargando = false;
					});
		}

		mi.guardar=function(valid){
			for (campos in mi.camposdinamicos) {
				if (mi.camposdinamicos[campos].tipo === 'fecha') {
					mi.camposdinamicos[campos].valor_f = mi.camposdinamicos[campos].valor!=null ? moment(mi.camposdinamicos[campos].valor).format('DD/MM/YYYY') : "";
				}
			}
			var asignaciones="";
			for (x in mi.responsables){
				asignaciones = asignaciones.concat(asignaciones.length > 0 ? "|" : "",mi.responsables[x].id + "~" + mi.responsables[x].rol); 
			}
			
			if(mi.actividad!=null && mi.actividad.nombre!=null){
				$http.post('/SActividad', {
					accion: 'guardarActividad',
					esnuevo: mi.esnuevo,
					actividadtipoid : mi.actividad.actividadtipoid,
					id: mi.actividad.id,
					objetoid: $routeParams.objeto_id,
					objetotipo: mi.objetotipo,
					nombre: mi.actividad.nombre,
					descripcion: mi.actividad.descripcion,
					fechainicio: moment(mi.actividad.fechaInicio).format('DD/MM/YYYY'),
					fechafin: moment(mi.actividad.fechaFin).format('DD/MM/YYYY'),
					porcentajeavance: mi.actividad.porcentajeavance,
					programa: mi.actividad.programa,
					subprograma: mi.actividad.subprograma,
					proyecto: mi.actividad.proyecto,
					actividad: mi.actividad.actividad,
					obra: mi.actividad.obra,
					longitud: mi.actividad.longitud,
					latitud : mi.actividad.latitud,
					costo: mi.actividad.costo == null ? 0 : mi.actividad.costo,
					acumulacionCosto: mi.actividad.acumulacionCostoId == null ? 0 : mi.actividad.acumulacionCostoId,
					renglon: mi.actividad.renglon,
					ubicacionGeografica: mi.actividad.ubicacionGeografica,
					asignacionroles: asignaciones,
					fechaInicio: moment(mi.actividad.fechaInicio).format('DD/MM/YYYY'),
					fechaFin: moment(mi.actividad.fechaFin).format('DD/MM/YYYY'),
					duaracion: mi.actividad.duracion,
					duracionDimension: mi.duracionDimension.sigla,
					datadinamica : JSON.stringify(mi.camposdinamicos)
				}).success(function(response){
					if(response.success){
						mi.actividad.id = response.id;
						mi.actividad.usuarioCreo=response.usuarioCreo;
						mi.actividad.fechaCreacion=response.fechaCreacion;
						mi.actividad.usuarioActualizo=response.usuarioactualizo;
						mi.actividad.fechaActualizacion=response.fechaactualizacion;
						$utilidades.mensaje('success','Actividad '+(mi.esnuevo ? 'creada' : 'guardado')+' con éxito');
						mi.obtenerTotalActividades();
						mi.esnuevo = false;					
					}
					else
						$utilidades.mensaje('danger','Error al '+(mi.esnuevo ? 'crear' : 'guardar')+' la Actividad');
				});
			}
			else
				$utilidades.mensaje('warning','Debe de llenar todos los campos obligatorios');
		};

		mi.borrar = function(ev) {
			if(mi.actividad!=null && mi.actividad.id!=null){
				$dialogoConfirmacion.abrirDialogoConfirmacion($scope
						, "Confirmación de Borrado"
						, '¿Desea borrar la Actividad "'+mi.actividad.nombre+'"?'
						, "Borrar"
						, "Cancelar")
				.result.then(function(data) {
					if(data){
						$http.post('/SActividad', {
							accion: 'borrarActividad',
							id: mi.actividad.id
						}).success(function(response){
							if(response.success){
								$utilidades.mensaje('success','Actividad borrada con éxito');
								mi.actividad = null;
								mi.obtenerTotalActividades();
							}
							else
								$utilidades.mensaje('danger','Error al borrar la Actividad');
						});
					}
				}, function(){
					
				});
			}
			else
				$utilidades.mensaje('warning','Debe seleccionar la Actividad que desea borrar');
		};

		mi.nuevo = function() {
			mi.datotipoid = "";
			mi.datotiponombre = "";
			mi.actividadtipoid = "";
			mi.actividadnombre = "";
			mi.mostraringreso=true;
			mi.duracionDimension = '';
			mi.esnuevo = true;
			mi.actividad = {};
			mi.coordenadas = "";
			mi.gridApi.selection.clearSelectedRows();
		};

		mi.editar = function() {
			if(mi.actividad!=null && mi.actividad.id!=null){
				mi.actividadResponsable = "";
				mi.mostraringreso = true;
				mi.actividadtipoid = mi.actividad.actividadtipoid;
				mi.esnuevo = false;
				
				if(mi.actividad.duracionDimension.toLowerCase() == 'd'){
					mi.duracionDimension = mi.dimensiones[0];
				}else{
					mi.duracionDimension = '';
				}
				
				mi.coordenadas = (mi.actividad.latitud !=null ?  mi.actividad.latitud : '') +
				(mi.actividad.latitud!=null ? ', ' : '') + (mi.actividad.longitud!=null ? mi.actividad.longitud : '');

				var parametros = {
						accion: 'getActividadPropiedadPorTipo',
						idActividad: mi.actividad.id,
					    idActividadTipo: mi.actividad.actividadtipoid
				}
				$http.post('/SActividadPropiedad', parametros).then(function(response){
					mi.camposdinamicos = response.data.actividadpropiedades
					for (campos in mi.camposdinamicos) {
						switch (mi.camposdinamicos[campos].tipo){
							case "fecha":
								mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? moment(mi.camposdinamicos[campos].valor,'DD/MM/YYYY').toDate() : null;
								break;
							case "entero":
								mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? Number(mi.camposdinamicos[campos].valor): null;
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
				
				$http.post('/SMatrizRACI', {
					accion: 'getAsignacionPorObjeto',
					objetoId: mi.actividad.id,
					objetoTipo:5
					
				}).success(function(response){
					mi.responsables =[];
					if (response.success)
					{
						var asignaciones = response.asignaciones;
						for (x in response.asignaciones){
							var responsable = [];
							responsable.id = asignaciones[x].colaboradorId;
							responsable.nombre = asignaciones[x].colaboradorNombre;
							responsable.nombrerol = mi.obtenerNombreRol(asignaciones[x].rolId);
							responsable.rol = asignaciones[x].rolId;
							mi.responsables.push(responsable);
						}
					}
				});
			}
			else
				$utilidades.mensaje('warning','Debe seleccionar la Actividad que desea editar');
		}

		mi.irATabla = function() {
			mi.mostraringreso=false;
			mi.esNuevo = false;
		}

		mi.guardarEstado=function(){
			var estado = mi.gridApi.saveState.save();
			var tabla_data = { action: 'guardaEstado', grid:'actividades', estado: JSON.stringify(estado), t: (new Date()).getTime() };
			$http.post('/SEstadoTabla', tabla_data).then(function(response){

			});
		}

		mi.cambioPagina=function(){
			mi.cargarTabla(mi.paginaActual);
		}

		mi.reiniciarVista=function(){
			if($location.path()==('/actividad/'+ $routeParams.objeto_id + '/' + mi.objetotipo + '/rv'))
				$route.reload();
			else
				$location.path('/actividad/'+ $routeParams.objeto_id + '/' + mi.objetotipo + '/rv');
		}
		
		mi.irARiesgos=function(actividadid){
			if(mi.actividad!=null){
				$location.path('/riesgo/' + actividadid + '/5' );
			}
		};
		
		mi.irAActividades=function(actividadid){
			if(mi.actividad!=null){
				$location.path('/actividad/'+ actividadid +'/5' );
			}
		};

		mi.abrirPopupFecha = function(index) {
			if(index<1000){
				mi.camposdinamicos[index].isOpen = true;
			}
			else{
				switch(index){
					case 1000: mi.fi_abierto = true; break;
					case 1001: mi.ff_abierto =  true; break;
				}
			}

		};

		mi.actualizarfechafin =  function(){
			if(mi.actividad.fechaInicio!=''){
				var m = moment(mi.actividad.fechaInicio);
				if(m.isValid()){
					mi.ff_opciones.minDate = m.toDate();
					if(mi.actividad.fechaFin!=null && mi.actividad.fechaFin<mi.actividad.fechaInicio)
						mi.actividad.fechaFin = mi.actividad.fechaInicio;
				}
			}
		}

		mi.filtrar = function(evt){
			if(evt.keyCode==13){
				mi.obtenerTotalActividades();
				mi.gridApi.selection.clearSelectedRows();
				mi.actividad = null;
			}
		};


		mi.obtenerTotalActividades=function(){
			$http.post('/SActividad', { accion: 'numeroActividadesPorObjeto',objetoid:$routeParams.objeto_id, tipo: mi.objetotipo,
				filtro_nombre: mi.filtros['nombre'],
				filtro_usuario_creo: mi.filtros['usuario_creo'], filtro_fecha_creacion: mi.filtros['fecha_creacion'] }).success(
					function(response) {
						mi.totalActividades = response.totalactividades;
						mi.cargarTabla(1);
			});
		};
		
		mi.buscarActividadResponsable = function(titulo, mensaje){
			var idResponsables = "";
			var idRoles = [];
			for (x in mi.responsables){
				//idResponsables = idResponsables + (x > 0 ? "," : "") + mi.responsables[x].id;
				idRoles.push(mi.responsables[x].rol);
			}
			
			var resultado = mi.llamarModalBusqueda('/SColaborador', {
				accion : 'totalElementos'	
			}, function(pagina, elementosPorPagina) {
				return {
					accion : 'cargar',
					pagina : pagina,
					numeroresponsablerol : elementosPorPagina,
					idResponsables : idResponsables
					
				};
			},'id','nombreCompleto',idResponsables,idRoles);

			resultado.then(function(itemSeleccionado) {
				var responsable = [];
				responsable.id = itemSeleccionado.id;
				responsable.nombre = itemSeleccionado.nombreCompleto;
				responsable.rol = itemSeleccionado.rol;
				responsable.nombrerol = itemSeleccionado.nombrerol;
				mi.responsables.push(responsable);
			});
		}
		
		mi.buscarAcumulacionCosto = function(){
			var resultado = mi.llamarModalBusqueda('/SAcumulacionCosto', {
				accion : 'numeroAcumulacionCosto' 
			}, function(pagina, elementosPorPagina){
				return{
					accion: 'getAcumulacionCosto',
					pagina: pagina,
					numeroacumulacioncosto : elementosPorPagina
				}
			}, 'id','nombre');
			
			resultado.then(function(itemSeleccionado){
				mi.actividad.acumulacionCostoNombre = itemSeleccionado.nombre;
				mi.actividad.acumulacionCostoId = itemSeleccionado.id;
			});
		}
		
		mi.llamarModalBusqueda = function(servlet, accionServlet, datosCarga,columnaId,columnaNombre,idResponsables, idRoles) {
			var resultado = $q.defer();
			var modalInstance = $uibModal.open({
				animation : 'true',
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'buscarActividadTipo.jsp',
				controller : 'modalBuscar',
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
					},
					$idResponsables : function() {
						return idResponsables;
					},
					$idRoles : function() {
						return idRoles;
					}
					
				}
			});

			modalInstance.result.then(function(itemSeleccionado) {
				resultado.resolve(itemSeleccionado);
			});
			return resultado.promise;
		};
		

		mi.buscarActividadTipo = function(titulo, mensaje) {

			var modalInstance = $uibModal.open({
				animation : 'true',
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'buscarActividadTipo.jsp',
				controller : 'modalBuscarActividadTipo',
				controllerAs : 'modalBuscar',
				backdrop : 'static',
				size : 'md',
				resolve : {
					titulo : function() {
						return titulo;
					},
					mensaje : function() {
						return mensaje;
					}
				}
			});

			modalInstance.result.then(function(selectedItem) {
				mi.actividad.actividadtipoid = selectedItem.id;
				mi.actividad.actividadtiponombre = selectedItem.nombre;

				var parametros = {
						accion: 'getActividadPropiedadPorTipo',
						idActividad: mi.actividad!=null ? mi.actividad.id : 0,
						idActividadTipo: selectedItem.id
				}

				$http.post('/SActividadPropiedad', parametros).then(function(response){
					mi.camposdinamicos = response.data.actividadpropiedades;
					for (campos in mi.camposdinamicos) {
						switch (mi.camposdinamicos[campos].tipo){
						case "fecha":
							mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? moment(mi.camposdinamicos[campos].valor,'DD/MM/YYYY').toDate() : null;
							break;
						case "entero":
							mi.camposdinamicos[campos].valor = (mi.camposdinamicos[campos].valor!='') ? Number(mi.camposdinamicos[campos].valor): null;
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

			}, function() {
			});
	};

	mi.open = function (posicionlat, posicionlong) {
		$scope.geoposicionlat = posicionlat;
		$scope.geoposicionlong = posicionlong;

	    var modalInstance = $uibModal.open({
	      animation: true,
	      templateUrl: 'map.html',
	      controller: 'mapCtrl',
	      resolve: {
	        glat: function(){
	        	return $scope.geoposicionlat;
	        },
	        glong: function(){
	        	return $scope.geoposicionlong;
	        }
	      }

	    });

	    modalInstance.result.then(function(coordenadas) {
	    	if (coordenadas !=null){
		    	mi.coordenadas = coordenadas.latitude + ", " + coordenadas.longitude;
		    	mi.actividad.latitud= coordenadas.latitude;
				mi.actividad.longitud = coordenadas.longitude;
	    	}
	    }, function() {
		});
	  };
	  
	  mi.eliminarResponsable = function(row){
			var index = mi.responsables.indexOf(row);
	        if (index !== -1) {
	            mi.responsables.splice(index, 1);
	        }
	  };
	  
	  mi.obtenerNombreRol = function(valor){
		  
		  switch (valor){
		  	case 'r': return "Responsable";
		  	case 'a': return "Cuentadante";
		  	case 'c': return "Consutor";
		  	case 'i': return "Quien informa";
		  }
		  return "";
	  }
		

} ]);

app.controller('modalBuscarActividadTipo', [ '$uibModalInstance',
	'$scope', '$http', '$interval', 'i18nService', 'Utilidades',
	'$timeout', '$log', 'titulo', 'mensaje', modalBuscarActividadTipo ]);

function modalBuscarActividadTipo($uibModalInstance, $scope, $http, $interval,
	i18nService, $utilidades, $timeout, $log, titulo, mensaje) {

	var mi = this;

	mi.totalElementos = 0;
	mi.paginaActual = 1;
	mi.numeroMaximoPaginas = 5;
	mi.elementosPorPagina = 9;

	mi.mostrarCargando = false;
	mi.data = [];

	mi.itemSeleccionado = null;
	mi.seleccionado = false;

	$http.post('/SActividadTipo', {
		accion : 'numeroActividadTipos'
	}).success(function(response) {
		mi.totalElementos = response.totalcooperantes;
		mi.elementosPorPagina = mi.totalElementos;
		mi.cargarData(1);
	});

	mi.opcionesGrid = {
		data : mi.data,
		columnDefs : [ {
			displayName : 'ID',
			name : 'id',
			cellClass : 'grid-align-right',
			type : 'number',
			width : 70
		}, {
			displayName : 'Nombre Tipo',
			name : 'nombre',
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
					mi.seleccionarEntidad);
		}
	}

	mi.seleccionarEntidad = function(row) {
		mi.itemSeleccionado = row.entity;
		mi.seleccionado = row.isSelected;
	};

	mi.cargarData = function(pagina) {
		var datos = {
			accion : 'cargar',
			pagina : pagina,
			registros : mi.elementosPorPagina
		};

		mi.mostrarCargando = true;
		$http.post('/SActividadTipo', {accion : 'getActividadtiposPagina'}).then(function(response) {
			if (response.data.success) {
				mi.data = response.data.actividadtipos;
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
			$utilidades.mensaje('warning', 'Debe seleccionar un Tipo');
		}
	};

	mi.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};


}

app.controller('mapCtrl',[ '$scope','$uibModalInstance','$timeout', 'uiGmapGoogleMapApi','glat','glong',
    function ($scope, $uibModalInstance,$timeout, uiGmapGoogleMapApi, glat, glong) {
	$scope.geoposicionlat = glat != null ? glat : 14.6290845;
	$scope.geoposicionlong = glong != null ? glong : -90.5116158;
	$scope.posicion = (glat !=null && glong !=null ) ? {latitude: glat, longitude: glong} : null;
	$scope.refreshMap = true;

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

	  $scope.ok = function () {
		  $uibModalInstance.close($scope.posicion);
	  };
}]);


app.controller('modalBuscar', [ '$uibModalInstance',
	'$scope', '$http', '$interval', 'i18nService', 'Utilidades',
	'$timeout', '$log', '$servlet', '$accionServlet', '$datosCarga',
	'$columnaId','$columnaNombre','$idResponsables','$idRoles',modalBuscar ]);

function modalBuscar($uibModalInstance, $scope, $http, $interval,
	i18nService, $utilidades, $timeout, $log, $servlet,$accionServlet,$datosCarga,$columnaId,$columnaNombre,
	$idResponsables,$idRoles) {

	var mi = this;
	mi.roles=[{id:'r',nombre:"Responsable"},{id:'a',nombre:"Cuentadante"},{id:'c',nombre:"Consultor"},{id:'i',nombre:"Quien informa"}];
	
	mi.totalElementos = 0;
	mi.paginaActual = 1;
	mi.numeroMaximoPaginas = 5;
	mi.elementosPorPagina = 9;
	mi.mostrarCargando = false;
	mi.data = [];
	mi.mostrarRoles = $servlet == "/SColaborador";

	mi.itemSeleccionado = null;
	mi.seleccionado = false;
	
	for (x in $idRoles){
		for (y in mi.roles){
			if ($idRoles[x] == mi.roles[y].id){
				mi.roles.splice(y, 1);
				break;
			}
		}
	}
	

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
		$http.post($servlet, $datosCarga(pagina, mi.elementosPorPagina,$idResponsables)).then(
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
		if (mi.seleccionado && ((mi.rolAsignado != null && mi.rolAsignado != undefined) || !mi.mostrarRoles  )) {
			if (mi.mostrarRoles){
				mi.itemSeleccionado.rol = mi.rolAsignado.id;
				mi.itemSeleccionado.nombrerol = mi.rolAsignado.nombre;
			}
			$uibModalInstance.close(mi.itemSeleccionado);
		} else {
			$utilidades.mensaje('warning', 'Debe seleccionar una fila');
		}
	};

	mi.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
}
