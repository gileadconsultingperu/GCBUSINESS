<%-- 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Administracion
    Nombre              : GC-Business-GestionProducto.jsp
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Crear, Modificar, Consultar e Inactivar un producto
--%>
<%@page import="gilead.gcbusiness.model.BeanProducto"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoProductoImpl"%>
<%@page import="gilead.gcbusiness.model.BeanAlmacen"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoAlmacenImpl"%>
<%@page import="gilead.gcbusiness.model.BeanSucursal"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoSucursalImpl"%>
<%@page import="gilead.gcbusiness.model.BeanTipoProducto"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoTipoProductoImpl"%>
<%@page import="gilead.gcbusiness.model.BeanTipoISC"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoTipoISCImpl"%>
<%@page import="gilead.gcbusiness.model.BeanMoneda"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoMonedaImpl"%>
<%@page import="gilead.gcbusiness.model.BeanClaseProducto"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoClaseProductoImpl"%>
<%@page import="gilead.gcbusiness.model.BeanFamiliaProducto"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoFamiliaProductoImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gilead.gcbusiness.model.BeanMarca"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoMarcaImpl"%>
<%@page import="gilead.gcbusiness.model.BeanUnidadMedida"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoUnidadMedidaImpl"%>
<%@page import="gilead.gcbusiness.model.BeanTipoPersona"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoTipoPersonaImpl"%>
<%@page import="gilead.gcbusiness.model.BeanVendedor"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoVendedorImpl"%>
<%@page import="gilead.gcbusiness.model.BeanUbigeo"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoUbigeoImpl"%>
<%@page import="java.util.List"%>
<%@page import="gilead.gcbusiness.model.BeanUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List opciones = (List) session.getAttribute("accesos");
    BeanUsuario usuario = (BeanUsuario) session.getAttribute("usuario");
%>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta charset="utf-8" />
        <title>GC BUSINESS - Traslado entre Almacenes</title>

        <meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

        <!-- bootstrap & fontawesome -->
        <link rel="stylesheet" href="../assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="../assets/font-awesome/4.5.0/css/font-awesome.min.css" />

        <!-- page specific plugin styles -->
        <link rel="stylesheet" href="../assets/css/jquery-ui.custom.min.css" />
        <link rel="stylesheet" href="../assets/css/chosen.min.css" />
        <link rel="stylesheet" href="../assets/css/bootstrap-datepicker3.min.css" />
        <link rel="stylesheet" href="../assets/css/bootstrap-timepicker.min.css" />
        <link rel="stylesheet" href="../assets/css/daterangepicker.min.css" />
        <link rel="stylesheet" href="../assets/css/bootstrap-datetimepicker.min.css" />
        <link rel="stylesheet" href="../assets/css/bootstrap-colorpicker.min.css" />
        <link rel="stylesheet" href="../assets/css/ui.jqgrid.min.css" />

        <!-- text fonts -->
        <link rel="stylesheet" href="../assets/css/fonts.googleapis.com.css" />

        <!-- ace styles -->
        <link rel="stylesheet" href="../assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

        <!--[if lte IE 9]>
                <link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
        <![endif]-->

        <!-- Alertify Version Nueva-->
        <link rel="stylesheet" href="../assets/css/alertify/alertify.css">  

        <link rel="stylesheet" href="../assets/css/ace-skins.min.css" />
        <link rel="stylesheet" href="../assets/css/ace-rtl.min.css" />

        <!--[if lte IE 9]>
          <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
        <![endif]-->

        <!-- inline styles related to this page -->

        <!-- ace settings handler -->
        <script src="../assets/js/ace-extra.min.js"></script>

        <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

        <!--[if lte IE 8]>
        <script src="assets/js/html5shiv.min.js"></script>
        <script src="assets/js/respond.min.js"></script>
        <![endif]-->

        <!-- Alertas Version Nueva -->
        <script src="../assets/js/alertify/alertify.js"></script>
        <!--<script src="../assets/js/jquery.tabletojson.min.js"></script>
        <script src="../assets/js/jquery-2.1.4.min.js"></script>
        <script src="../assets/js/jquery-ui.min.js"></script>-->

        <style>
            .table-wrapper-scroll-y {
                display: block;
                max-height: 180px;
                overflow-y: auto;
                -ms-overflow-style: -ms-autohiding-scrollbar;
            }
        </style>
    </head>
    <body class="no-skin">
        <%
            if (usuario != null) {
        %>
        <div id="navbar" class="navbar navbar-default ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <div class="navbar-header pull-left">
                    <a href="#" class="navbar-brand">
                        <small>
                            <%--<i class="fa fa-leaf"></i>--%>
                            GC BUSINESS - Software de Gestión Comercial
                        </small>
                    </a>
                </div>

                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">

                        <li class="light-blue dropdown-modal">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="../assets/images/avatars/avatar2.png" alt="Jason's Photo" />
                                <span class="user-info">
                                    <small>Bienvenido,</small>
                                    <%= usuario.getUsuario()%>
                                </span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                                <li>
                                    <a href="GCPortal_Perfil.jsp">
                                        <i class="ace-icon fa fa-info-circle"></i>
                                        Perfil
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a href="../Logout">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        Cerrar Sesión
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div><!-- /.navbar-container -->
        </div>

        <div class="main-container ace-save-state" id="main-container">
            <script type="text/javascript">
                try {
                    ace.settings.loadState('main-container');
                } catch (e) {
                }
            </script>

            <div id="sidebar" class="sidebar                  responsive                    ace-save-state">
                <script type="text/javascript">
                    try {
                        ace.settings.loadState('sidebar');
                    } catch (e) {
                    }
                </script>

                <ul class="nav nav-list">
                    <%
                        if (opciones.contains(1)) {
                    %>
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-bar-chart-o"></i>
                            <span class="menu-text">Ventas </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(2)) {
                            %>
                            <li class="">
                                <a href="GC-Business-RegistrarVenta.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Registrar Venta
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(3)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionNota.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Notas
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(4)) {
                            %>
                            <li class="">
                                <a href="GC-Business-Comprobante.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Comprobantes
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(5)) {
                            %>
                            <li class="">
                                <a href="GC-Business-Comprobante.jsp?accion=anular">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Anulaciones
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(6)) {
                            %>
                            <li class="">
                                <a href="GC-Business-Cotizacion.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Registrar Cotización
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(7)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionCotizacion.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Cotizaciones
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(8)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionOrdenVenta.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Orden de Venta
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <%
                        }
                        if (opciones.contains(11)) {
                    %>                
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-users"></i>
                            <span class="menu-text"> Clientes </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(12)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionCliente.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Gestion Clientes
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(13)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionContacto.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Gestion Contactos
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(14)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionCuentaCobrar.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Cuentas por Cobrar
                                </a>

                                <b class="arrow"></b>
                            </li>                                                    
                            <%
                                }
                            %>
                        </ul>
                    </li>    
                    <%
                        }
                        if (opciones.contains(21)) {
                    %> 
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-truck"></i>
                            <span class="menu-text"> Compras </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(22)) {
                            %>
                            <li class="">
                                <a href="GC-Business-RegistrarCompra.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Registrar Compra
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(23)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionOrdenCompra.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Orden de Compra
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(24)) {
                            %>
                            <li class="">
                                <a href="GC-Business-ListaCompras.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Lista de Compras 
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <%
                        }
                        if (opciones.contains(31)) {
                    %>                                       
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-briefcase"></i>
                            <span class="menu-text"> Proveedores </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(32)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionProveedor.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Gestion Proveedores
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(33)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionCuentaPagar.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Cuentas por Pagar
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <%
                        }
                        if (opciones.contains(41)) {
                    %>                                    
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-industry"></i>
                            <span class="menu-text"> Inventario </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(42)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionIngresoSalida.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Ingresos y Salidas
                                </a>

                                <b class="arrow"></b>
                            </li>                           
                            <%
                                }
                                if (opciones.contains(43)) {
                            %>
                            <li class="">
                                <a href="GC-Business-TrasladoAlmacen.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Traslado entre Almacenes
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <%
                        }
                        if (opciones.contains(51)) {
                    %>                    
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> Productos </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>
                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(52)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionProducto.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Gestión Productos
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(53)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionFamiliaProducto.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Familias
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(54)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionClaseProducto.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Clases
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(55)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionLineaProducto.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Lineas
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(56)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionCategoriaProducto.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Categorias
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(57)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionMarca.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Marcas
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(58)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionTarifa.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Tarifas
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <%
                        }
                        if (opciones.contains(61)) {
                    %>
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-cogs"></i>
                            <span class="menu-text"> Adminitración </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(62)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionEmpresa.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Empresa
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(63)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionSucursal.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Sucursales
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(64)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionAlmacen.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Almacenes
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(65)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionVendedor.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Vendedores
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(66)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionUsuario.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Usuarios
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <%
                        }
                        if (opciones.contains(71)) {
                    %>
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-cogs"></i>
                            <span class="menu-text"> Reportes </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(72)) {
                            %>
                            <li class="">
                                <a href="#">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Reporte 1
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <%
                        }
                        if (opciones.contains(99)) {
                    %>
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-cogs"></i>
                            <span class="menu-text"> Cambiar Contraseña </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>
                    </li>
                    <%
                        }
                    %>
                </ul><!-- /.nav-list -->

                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>
            </div>

            <div class="main-content">
                <div class="main-content-inner">
                    <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                        <ul class="breadcrumb">
                            <li>
                                <i class="ace-icon fa fa-home home-icon"></i>
                                <a href="#">Inicio</a>
                            </li>
                            <li>
                                <a href="#">Inventario</a>
                            </li>
                            <li class="active">Traslado entre Almacenes</li>
                        </ul><!-- /.breadcrumb -->

                    </div>

                    <div class="page-content">

                        <div class="page-header">
                            <h1>
                                Traslado entre Almacenes
                                <small>
                                    <i class="ace-icon fa fa-angle-double-right"></i>
                                    Crear traslado entre almacenes
                                </small>
                            </h1>
                        </div><!-- /.page-header -->

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <div class="clearfix">
                                    <div class="pull-right tableTools-container"></div>
                                </div>
                                <div>
                                    <table id="tablaTrasladoAlmacenes" class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>Fecha</th>
                                                <th>Almacén Origen</th>   
                                                <th>Almacén Destino</th>
                                                <th>Observación</th>  
                                                <th>Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody id="ingresossalidas_data">
                                        </tbody>
                                    </table>
                                </div>
                                <!-- PAGE CONTENT ENDS -->
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->
                </div>
            </div><!-- /.main-content -->

            <!-- Modales -->
            <div class="modal" id="modalAgregarTrasladoAlmacen" tabindex="-1" style="overflow-y:auto">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="blue bigger">Registrar Nuevo Traslado entre Almacenes</h4>
                        </div>

                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12">
                                    <input type="hidden" id="idproducto" value="">
                                    <input type="hidden" id="codigoproducto" value="">
                                    <input type="hidden" id="descripcionproducto" value="">
                                    <input type="hidden" id="flaglote" value="">

                                    <h5 class="blue bigger">Origen</h5>
                                    <hr/>

                                    <div class="form-group">
                                        <label for="sucursalorigen" class="col-sm-2 control-label">Sucursal</label>
                                        <div class="col-sm-4">
                                            <select id="sucursalorigen" name="sucursalorigen" class="col-xs-12 col-sm-12" tabindex="3">
                                                <%
                                                    DaoSucursalImpl daoSucursal = new DaoSucursalImpl();
                                                    List<BeanSucursal> sucursal = daoSucursal.accionListar();
                                                    for (int i = 0; i < sucursal.size(); i++) {%>
                                                <option value="<%= sucursal.get(i).getIdsucursal()%>">
                                                    <%= sucursal.get(i).getDescripcion()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <label for="almacenorigen" class="col-sm-2 control-label">Almacen</label>
                                        <div class="col-sm-4">
                                            <select id="almacenorigen" name="almacenorigen" class="col-xs-12 col-sm-12" tabindex="3">
                                            </select>
                                        </div>
                                    </div>


                                    &nbsp;&nbsp;

                                    <h5 class="blue bigger">Destino</h5>
                                    <hr/>

                                    <div class="form-group">
                                        <label for="sucursaldestino" class="col-sm-2 control-label">Sucursal</label>
                                        <div class="col-sm-4">
                                            <select id="sucursaldestino" name="sucursaldestino" class="col-xs-12 col-sm-12" tabindex="3">
                                                <%
                                                    daoSucursal = new DaoSucursalImpl();
                                                    sucursal = daoSucursal.accionListar();
                                                    for (int i = 0; i < sucursal.size(); i++) {%>
                                                <option value="<%= sucursal.get(i).getIdsucursal()%>">
                                                    <%= sucursal.get(i).getDescripcion()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <label for="almacendestino" class="col-sm-2 control-label">Almacen</label>
                                        <div class="col-sm-4">
                                            <select id="almacendestino" name="almacendestino" class="col-xs-12 col-sm-12" tabindex="3">
                                            </select>
                                        </div>
                                    </div>


                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="observacion" class="col-sm-2 control-label">Observación</label>
                                        <div class="col-sm-10">
                                            <textarea id="observacion" class="form-control"  style="text-transform:uppercase" rows="2" tabindex="2"></textarea>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;

                                    <h5 class="blue bigger">Detalle</h5>
                                    <hr/>

                                    <div class="form-group">                                        
                                        <label for="producto" class="col-sm-2 control-label">Producto</label>
                                        <div class="col-sm-5">
                                            <select class="chosen-select form-control col-xs-12 col-sm-12" id="producto" data-placeholder="Seleccione producto...">
                                                <option value="" selected="selected"></option>
                                                <%
                                                    DaoProductoImpl daoProducto = new DaoProductoImpl();
                                                    List<BeanProducto> producto = daoProducto.accionListar();
                                                    for (int i = 0; i < producto.size(); i++) {%>
                                                <option value="<%= producto.get(i).getIdproducto() + "|" + producto.get(i).getFlaglote()%>"><%= producto.get(i).getCodigo() + " - " + producto.get(i).getDescripcion()%></option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </div>  
                                        <label for="lote" class="col-sm-2 control-label">Lote|F.V.</label>
                                        <div class="col-sm-3">
                                            <select id="lote" name="lote" class="col-xs-12 col-sm-12" tabindex="3">
                                            </select>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="stockactual" class="col-sm-2 control-label">Stock Actual</label>
                                        <div class="col-sm-3">
                                            <input type="text" id="stockactual" class="form-control"  style="text-transform:uppercase; text-align: right" readonly="readonly" tabindex="1"/>
                                        </div>
                                        <div class="col-sm-1"></div>
                                        <label for="cantidad" class="col-sm-2 control-label">Cantidad</label>
                                        <div class="col-sm-3">
                                            <input type="text" id="cantidad" class="form-control"  style="text-transform:uppercase; text-align: right"  tabindex="1"/>
                                        </div>
                                        <div class="col-sm-1">
                                            <button class="btn btn-sm btn-primary" id="btnAgregarDetalle" tabindex="13" title="Agregar Detalle">
                                                <i class="ace-icon fa fa-plus"></i>
                                            </button>
                                        </div> 
                                    </div>

                                    &nbsp;&nbsp;&nbsp; 

                                    <div class="form-group">
                                        <div id="divdetalleMovimiento" class="col-xs-12">        			
                                            <div class="table-wrapper-scroll-y">
                                                <table id="detalleMovimiento" class="table table-striped table-bordered table-hover" sortable="1">
                                                    <thead>
                                                        <tr>
                                                            <th style="display: none">IdProducto</th>
                                                            <th>Código</th>
                                                            <th>Descripción</th>
                                                            <th>Cantidad</th>
                                                            <th style="display: none">FlagLote</th>
                                                            <th style="display: none">IdLote</th>
                                                            <th>Lote|F.V.</th>
                                                            <th>Acciones</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="detalleMovimiento_data">
                                                    </tbody>
                                                </table>
                                            </div>          
                                        </div>
                                    </div>  
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button class="btn btn-sm" data-dismiss="modal">
                                <i class="ace-icon fa fa-times"></i>
                                Cancelar
                            </button>

                            <button class="btn btn-sm btn-primary" id="btnGuardar">
                                <i class="ace-icon fa fa-check"></i>
                                Grabar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal" id="modalActualizarDetalle" tabindex="-1" style="overflow-y:auto">
                <div class="modal-dialog">                                            
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="blue bigger">Actualizar Detalle</h4>
                        </div>
                        <div class="modal-body">                                  
                            <div class="row">                
                                <div class="col-xs-12 col-sm-12"> 
                                    <input type="hidden" id="madidproducto" value="">
                                    <input type="hidden" id="madidrow" value="">
                                    <input type="hidden" id="madflaglote" value="">

                                    <div class="form-group">                                        
                                        <label for="madproducto" class="col-sm-2 control-label">Producto</label>
                                        <div class="col-sm-4">
                                            <input type="text" id="madproducto" class="form-control"  readonly="readonly" tabindex="1"/>
                                        </div>
                                        <label for="madlote" class="col-sm-2 control-label">Lote | F.V.</label>
                                        <div class="col-sm-4">
                                            <select id="madlote" name="madlote" class="col-xs-12 col-sm-12" tabindex="3">
                                            </select>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="madstockactual" class="col-sm-2 control-label">Stock Actual</label>
                                        <div class="col-sm-4">
                                            <input type="text" id="madstockactual" class="form-control"  style="text-transform:uppercase; text-align: right" readonly="readonly" tabindex="1"/>
                                        </div>
                                        <label for="madcantidad" class="col-sm-2 control-label">Cantidad</label>
                                        <div class="col-sm-4">
                                            <input type="text" id="madcantidad" class="form-control"  style="text-transform:uppercase; text-align: right"  tabindex="1"/>
                                        </div> 
                                    </div>
                                </div>        		
                            </div>
                        </div>
                        <div class="modal-footer">               
                            <button class="btn btn-sm" data-dismiss="modal">
                                <i class="ace-icon fa fa-times"></i>
                                Cancelar
                            </button>

                            <button class="btn btn-sm btn-primary" id="btnActualizarDetalle">
                                <i class="ace-icon fa fa-check"></i>
                                Grabar
                            </button>
                        </div>
                    </div>                 
                </div>
            </div>   

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div><!-- /.main-container -->


        <!-- basic scripts -->

        <!--[if !IE]> -->
        <script src="../assets/js/jquery-2.1.4.min.js"></script>

        <!-- <![endif]-->

        <!--[if IE]>
        <script src="assets/js/jquery-1.11.3.min.js"></script>
        <![endif]-->
        <script src="../assets/js/chosen.jquery.min.js"></script>
        <script type="text/javascript">
                    if ('ontouchstart' in document.documentElement)
                        document.write("<script src='../assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
        </script>
        <script src="../assets/js/bootstrap.min.js"></script>
        <script src="../assets/js/jquery.maskedinput.min.js"></script>

        <!-- page specific plugin scripts 
        <script src="../assets/js/bootstrap-datepicker.min.js"></script>
        <script src="../assets/js/jquery.jqGrid.min.js"></script>
        <script src="../assets/js/grid.locale-en.js"></script>-->

        <!-- page specific plugin scripts -->
        <script src="../assets/js/jquery.dataTables.min.js"></script>
        <script src="../assets/js/jquery.dataTables.bootstrap.min.js"></script>
        <script src="../assets/js/dataTables.buttons.min.js"></script>
        <script src="../assets/js/buttons.flash.min.js"></script>
        <script src="../assets/js/buttons.html5.min.js"></script>
        <script src="../assets/js/buttons.print.min.js"></script>
        <script src="../assets/js/buttons.colVis.min.js"></script>
        <script src="../assets/js/dataTables.select.min.js"></script>
        <script src="../assets/js/dataTables/jszip.min.js"></script>
        <script src="../assets/js/dataTables/pdfmake.min.js"></script>
        <script src="../assets/js/dataTables/vfs_fonts.js"></script>

        <!-- ace scripts -->
        <script src="../assets/js/ace-elements.min.js"></script>
        <script src="../assets/js/ace.min.js"></script>

        <!--<script type="text/javascript" src="../assets/js/jquery.dataTables.min.js"></script>
        <!--<script type="text/javascript" src="../assets/js/dataTables/dataTables.bootstrap.min.js"></script>-->

        <!--<script type="text/javascript" src="../assets/js/dataTables.buttons.min.js"></script>
        <!--<script type="text/javascript" src="../assets/js/dataTables/buttons.bootstrap.min.js"></script>-->

        <!--<script type="text/javascript" src="../assets/js/buttons.html5.min.js"></script>

        <!--<script type="text/javascript" src="../assets/js/dataTables/jszip.min.js"></script>
        <script type="text/javascript" src="../assets/js/dataTables/pdfmake.min.js"></script>
        <script type="text/javascript" src="../assets/js/dataTables/vfs_fonts.js"></script>-->

        <!-- inline scripts related to this page -->
        <script type="text/javascript">

                    $(document).ready(function () {

                        $('body').on('shown.bs.modal', '#modalAgregarTrasladoAlmacen', function () {
                            $('input:visible:enabled:first', this).focus();
                        });

                        $('body').on('shown.bs.modal', '#modalActualizarDetalle', function () {
                            $('input:visible:enabled:first', this).focus();
                        });

                        var tablaTrasladoAlmacenes = $('#tablaTrasladoAlmacenes').DataTable({
                            bAutoWidth: false,
                            "processing": true,
                            "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "Todos"]],
                            "iDisplayLength": -1,
                            destroy: true,
                            responsive: true,
                            "searching": true,
                            "order": [[0, 'asc']],
                            ajax: {
                                method: "POST",
                                url: "../TrasladoAlmacenes",
                                data: {"opcion": "listar"},
                                dataSrc: "data"
                            },
                            columns: [
                                {"data": "fecha"},
                                {"data": "almacenorigen"},
                                {"data": "almacendestino"},
                                {"data": "observacion"},
                                {"data": "acciones"}
                            ],
                            dom: '<"row"<"col-xs-12 col-sm-4 col-md-4"l><"col-xs-12 col-sm-4 col-md-4"B><"col-xs-12 col-sm-4 col-md-4"f>>' +
                                    'tr<"row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>> ',
                            'columnDefs': [
                                {
                                    'targets': [0, 1, 2, 3],
                                    'createdCell': function (td, cellData, rowData, row, col) {
                                        $(td).attr('contenteditable', 'false');
                                    }
                                }
                            ],
                            buttons: [
                            ],
                            language: {
                                "url": "../assets/util/espanol.txt"
                            }
                        });

                        function limpiardetalle() {
                            $('#producto')
                                    .find('option:first-child').prop('selected', true)
                                    .end().trigger('chosen:updated');
                            $('#idproducto').val('');
                            $('#codigoproducto').val('');
                            $('#stockactual').val('');
                            $('#cantidad').val('');
                            $('#flaglote').val('');
                            $('#lote').empty();
                            $('#lote').prop('disabled', true);
                        }

                        $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';
                        new $.fn.dataTable.Buttons(tablaTrasladoAlmacenes, {
                            buttons: [
                                {
                                    "text": "<i class='fa fa-plus bigger-110 blue'></i>",
                                    "titleAttr": "Nuevo",
                                    "className": "btn btn-white btn-primary btn-bold",
                                    "action": function () {
                                        //Origen
                                        $('#sucursalorigen').prop('selectedIndex', 0);
                                        //Obtener almacenes de la sucursal
                                        $.get('../SucursalAlmacen', {
                                            "idSucursal": $('#sucursalorigen').val(),
                                            "accion": "almacen"
                                        }, function (response) {
                                            $('#almacenorigen').html(response);
                                        });
                                        $('#almacenorigen').prop('selectedIndex', 0);
                                        //Destino
                                        $('#sucursaldestino').prop('selectedIndex', 0);
                                        //Obtener almacenes de la sucursal
                                        $.get('../SucursalAlmacen', {
                                            "idSucursal": $('#sucursaldestino').val(),
                                            "accion": "almacen"
                                        }, function (response) {
                                            $('#almacendestino').html(response);
                                        });
                                        $('#almacendestino').prop('selectedIndex', 0);
                                        //
                                        $('#observacion').val('');
                                        limpiardetalle();

                                        $('#detalleMovimiento tbody').remove();

                                        $('#modalAgregarTrasladoAlmacen').modal('show');
                                        $('.divError').empty();
                                    }
                                },
                                {
                                    "extend": "copy",
                                    "text": "<i class='fa fa-copy bigger-110 pink'></i>",
                                    "titleAttr": "Copiar",
                                    "className": "btn btn-white btn-primary btn-bold"
                                },
                                {
                                    "extend": 'excel',
                                    "titleAttr": "Excel",
                                    "text": "<i class='fa fa-file-excel-o bigger-110 green'></i>",
                                    "className": "btn btn-white btn-primary btn-bold"
                                },
                                {
                                    "extend": "pdf",
                                    "titleAttr": "PDF",
                                    "text": "<i class='fa fa-file-pdf-o bigger-110 red'></i>",
                                    "className": "btn btn-white btn-primary btn-bold"
                                },
                                {
                                    "extend": "print",
                                    "titleAttr": "Imprimir",
                                    "text": "<i class='fa fa-print bigger-110 grey'></i>",
                                    "className": "btn btn-white btn-primary btn-bold",
                                    autoPrint: true,
                                    message: 'This print was produced using the Print button for DataTables'
                                }
                            ]
                        });

                        tablaTrasladoAlmacenes.buttons().container().appendTo($('.tableTools-container'));

                        function tableToJSON(tblObj) {
                            var data = [];
                            var $headers = $(tblObj).find("th");
                            var $rows = $(tblObj).find("tbody tr").each(function (index) {
                                $cells = $(this).find("td");
                                data[index] = {};
                                $cells.each(function (cellIndex) {
                                    data[index][$($headers[cellIndex]).html()] = $(this).html();
                                });
                            });
                            return data;
                        }

                        //Guardar registo ingreso o salida
                        $('#btnGuardar').click(function (event) {
                            var idalmacenorigen = $('#almacenorigen').val();
                            var idalmacendestino = $('#almacendestino').val();
                            var observacion = $('#observacion').val();
                            var detalle = tableToJSON($('#detalleMovimiento'));

                            if ($('#almacenorigen').val() === $('#almacendestino').val()) {
                                alertify.error("NO PUEDE SELECCIONAR ALMACEN DESTINO IGUAL ALMACEN ORIGEN");
                                $('#almacendestino').focus();
                                return;
                            }
                            if (detalle.length === 0) {
                                alertify.error("NO HAY DETALLE EN EL REGISTRO");
                                return;
                            }

                            $.ajax({
                                method: "POST",
                                url: "../TrasladoAlmacenes",
                                data: {"opcion": "insertar", "idalmacenorigen": idalmacenorigen, "idalmacendestino": idalmacendestino, "observacion": observacion, "detalle": JSON.stringify(detalle)}
                            }).done(function (data) {
                                var obj = JSON.parse(data);
                                if (obj.mensaje.indexOf('ERROR') !== -1) {
                                    alertify.error(obj.html);
                                } else {
                                    tablaTrasladoAlmacenes.ajax.reload();
                                    alertify.success(obj.mensaje);
                                    $('#modalAgregarTrasladoAlmacen').modal('hide');
                                }
                            });
                        });

                        //Imprimir registro
                        $(document).on('click', '.actualizar', function () {

                        });

                        $("#btnAgregarDetalle").click(function () {
                            if ($('#flaglote').val() === 'N') {
                                if ($('#cantidad').val() === "" || $('#cantidad').val() === null) {
                                    alertify.error("Debe ingresar la cantidad");
                                    $('#cantidad').focus();
                                    return;
                                }
                                if (parseFloat($('#cantidad').val()) === 0) {
                                    alertify.error("Cuidado! Esta ingresando CERO");
                                    $('#cantidad').focus();
                                    return;
                                }
                                //Validar stock por salida
                                if (parseFloat($('#cantidad').val()) > parseFloat($('#stockactual').val())) {
                                    alertify.error("NO HAY STOCK SUFICIENTE");
                                    $('#cantidad').focus();
                                    return;
                                }
                            } else if ($('#flaglote').val() === 'S') {
                                if ($('#lote').val() === "" || $('#lote').val() === null) {
                                    alertify.error("Debe seleccionar un lote");
                                    $('#lote').focus();
                                    return;
                                }
                                if ($('#cantidad').val() === "" || $('#cantidad').val() === null) {
                                    alertify.error("Debe ingresar la cantidad");
                                    $('#cantidad').focus();
                                    return;
                                }
                                if (parseFloat($('#cantidad').val()) === 0) {
                                    alertify.error("Cuidado! Esta ingresando CERO");
                                    $('#cantidad').focus();
                                    return;
                                }
                                //Validar stock por salida
                                if (parseFloat($('#cantidad').val()) > parseFloat($('#stockactual').val())) {
                                    alertify.error("NO HAY STOCK SUFICIENTE");
                                    $('#cantidad').focus();
                                    return;
                                }
                            } else {
                                alertify.error("Debe seleccion un producto");
                                $('#producto').focus();
                                return;
                            }

                            var rowCount = $('#detalleMovimiento >tbody >tr').length;
                            var idrow = rowCount + 1;
                            var nuevaFila = "<tr id=\"" + idrow + "\">";
                            nuevaFila += "<td style=\"display: none\">" + $('#idproducto').val() + "</td>";
                            nuevaFila += "<td>" + $('#codigoproducto').val() + "</td>";
                            nuevaFila += "<td>" + $('#descripcionproducto').val() + "</td>"
                            nuevaFila += "<td>" + $('#cantidad').val() + "</td>";
                            nuevaFila += "<td style=\"display: none\">" + $('#flaglote').val() + "</td>";
                            nuevaFila += "<td style=\"display: none\">" + $('#lote').val() + "</td>";
                            nuevaFila += "<td>" + $("#lote option:selected").text() + "</td>";
                            nuevaFila += "<td><button type='button' name='actualizarDetalleMovimiento' id='" + idrow + "' class='btn btn-info btn-xs actualizarDetalleMovimiento' title='Actualizar'  ><span class='glyphicon glyphicon-edit'></span></button>  <button type='button' name='eliminarDetalleMovimiento' id='" + idrow + "' class='btn btn-danger btn-xs eliminarDetalleMovimiento' title='Eliminar'  ><span class='glyphicon glyphicon-trash'></span></button></td>";

                            $("#detalleMovimiento").append(nuevaFila);

                            limpiardetalle();
                        });

                        $("#detalleMovimiento").on('click', '.eliminarDetalleMovimiento', function () {
                            $(this).closest('tr').remove();
                        });

                        $("#detalleMovimiento").on('click', '.actualizarDetalleMovimiento', function () {
                            var idrow = $(this).attr('id');
                            $('#madidrow').val(idrow);
                            var valores = "";
                            // Obtenemos todos los valores contenidos en los <td> de la fila seleccionada
                            $(this).parents("tr").find("td").each(function () {
                                valores += $(this).html() + "|";
                            });
                            var array = [];
                            array = valores.split("|");
                            $('#madidproducto').val(array[0]);
                            $('#madproducto').val(array[1] + ' - ' + array[2]);
                            $('#madflaglote').val(array[4]);
                            $('#madcantidad').val('');
                            if (array[4] === 'S') {
                                $.get('../Lote', {
                                    "idProducto": array[0],
                                    "accion": "madlote"
                                }, function (response) {
                                    $('#madlote').html(response);
                                    $('#madlote').val(array[5]);
                                });
                                $('#madlote').prop('disabled', false);
                                $('#madstockactual').val('');
                                //Obtener stock
                                if (array[5] !== '') {
                                    var idAlmacen = $('#almacenorigen').val();
                                    var idProducto = array[0];
                                    $.get('../Lote', {
                                        "idAlmacen": idAlmacen,
                                        "idProducto": idProducto,
                                        "idLote": array[5],
                                        "accion": "stock"
                                    }, function (response) {
                                        $('#madstockactual').val(response);
                                    });
                                }
                            } else {
                                if (array[0] !== '') {
                                    var idAlmacen = $('#almacenorigen').val();
                                    $.get('../Lote', {
                                        "idAlmacen": idAlmacen,
                                        "idProducto": array[0],
                                        "accion": "stock"
                                    }, function (response) {
                                        $('#madstockactual').val(response);
                                    });
                                }
                                $('#madlote').empty();
                                $('#madlote').prop('disabled', true);
                            }
                            $('#modalActualizarDetalle').modal({backdrop: 'static', keyboard: false});
                        });

                        $("#btnActualizarDetalle").click(function () {
                            if ($('#madcantidad').val() === "" || $('#madcantidad').val() === null) {
                                alertify.error("Debe ingresar la cantidad");
                                $('#madcantidad').focus();
                                return;
                            }
                            if (parseFloat($('#madcantidad').val()) === 0) {
                                alertify.error("Cuidado! Esta ingresando CERO");
                                $('#madcantidad').focus();
                                return;
                            }
                            if (parseFloat($('#madcantidad').val()) > parseFloat($('#madstockactual').val())) {
                                alertify.error("NO HAY STOCK SUFICIENTE");
                                $('#madcantidad').focus();
                                return;
                            }

                            var idrow = $('#madidrow').val();
                            $("#" + idrow).closest('tr').remove();

                            var array = [];
                            array = $("#madproducto").val().split(" - ");
                            var codigoProducto = array[0];
                            var descripcionProducto = array[1];

                            var nuevaFila = "<tr id=\"" + idrow + "\">";
                            nuevaFila += "<td style=\"display: none\">" + $('#madidproducto').val() + "</td>";
                            nuevaFila += "<td>" + codigoProducto + "</td>";
                            nuevaFila += "<td>" + descripcionProducto + "</td>"
                            nuevaFila += "<td>" + $('#madcantidad').val() + "</td>";
                            nuevaFila += "<td style=\"display: none\">" + $('#madflaglote').val() + "</td>";
                            nuevaFila += "<td style=\"display: none\">" + $('#madlote').val() + "</td>";
                            nuevaFila += "<td>" + $("#madlote option:selected").text() + "</td>";
                            nuevaFila += "<td><button type='button' name='actualizarDetalleMovimiento' id='" + idrow + "' class='btn btn-info btn-xs actualizarDetalleMovimiento' title='Actualizar'  ><span class='glyphicon glyphicon-edit'></span></button>  <button type='button' name='eliminarDetalleMovimiento' id='" + idrow + "' class='btn btn-danger btn-xs eliminarDetalleMovimiento' title='Eliminar'  ><span class='glyphicon glyphicon-trash'></span></button></td>";

                            $("#detalleMovimiento").append(nuevaFila);

                            //Ordenar tabla
                            var $tbody = $('#detalleMovimiento tbody');

                            $tbody.find('tr').sort(function (a, b) {
                                var tda = $(a).attr('id'); // target order attribute
                                var tdb = $(b).attr('id'); // target order attribute
                                // if a < b return 1
                                return tda > tdb ? 1
                                        // else if a > b return -1
                                        : tda < tdb ? -1
                                        // else they are equal - return 0    
                                        : 0;
                            }).appendTo($tbody);

                            $('#madidrow').val('');
                            $('#modalActualizarDetalle').one('hidden.bs.modal', function () {
                                $('#modalAgregarTrasladoAlmacen').modal('show');
                            }).modal('hide');
                        });

                        $('#sucursalorigen').change(function () {
                            var idSucursal = $('#sucursalorigen').val();
                            $.get('../SucursalAlmacen', {
                                "idSucursal": idSucursal,
                                "accion": "almacen"
                            }, function (response) {
                                $('#almacenorigen').html(response);
                            });
                        });

                        $('#sucursaldestino').change(function () {
                            var idSucursal = $('#sucursaldestino').val();
                            $.get('../SucursalAlmacen', {
                                "idSucursal": idSucursal,
                                "accion": "almacen"
                            }, function (response) {
                                $('#almacendestino').html(response);
                            });
                        });

                        $('#producto').change(function () {
                            $('#stockactual').val('');
                            var array = [];
                            array = $('#producto').val().split("|");
                            var idProducto = array[0];
                            var flagLote = array[1];
                            array = $("#producto option:selected").text().split(" - ");
                            var codigoProducto = array[0];
                            var descripcionLote = array[1];
                            $('#idproducto').val(idProducto);
                            $('#codigoproducto').val(codigoProducto);
                            $('#descripcionproducto').val(descripcionLote);
                            $('#flaglote').val(flagLote);
                            if (flagLote === 'S') {
                                $.get('../Lote', {
                                    "idProducto": idProducto,
                                    "accion": "lote"
                                }, function (response) {
                                    $('#lote').html(response);
                                });
                                $('#lote').prop('disabled', false);
                            } else {
                                if (idProducto !== '') {
                                    var idAlmacen = $('#almacenorigen').val();
                                    $.get('../Lote', {
                                        "idAlmacen": idAlmacen,
                                        "idProducto": idProducto,
                                        "accion": "stock"
                                    }, function (response) {
                                        $('#stockactual').val(response);
                                    });
                                }
                                $('#lote').empty();
                                $('#lote').prop('disabled', true);
                            }
                        });

                        $.mask.definitions['~'] = '[+-]';
                        $('.input-mask-date').mask('99/99/9999');

                        $('#lote').change(function () {
                            $('#stockactual').val('');
                            var idLote = $('#lote').val();
                            if (idLote !== '') {
                                var idAlmacen = $('#almacenorigen').val();
                                var idProducto = $('#idproducto').val();
                                $.get('../Lote', {
                                    "idAlmacen": idAlmacen,
                                    "idProducto": idProducto,
                                    "idLote": idLote,
                                    "accion": "stock"
                                }, function (response) {
                                    $('#stockactual').val(response);
                                });
                            }
                        });

                        $('#madlote').change(function () {
                            $('#madstockactual').val('');
                            var idLote = $('#madlote').val();
                            if (idLote !== '') {
                                var idAlmacen = $('#almacenorigen').val();
                                var idProducto = $('#madidproducto').val();
                                $.get('../Lote', {
                                    "idAlmacen": idAlmacen,
                                    "idProducto": idProducto,
                                    "idLote": idLote,
                                    "accion": "stock"
                                }, function (response) {
                                    $('#madstockactual').val(response);
                                });
                            }
                        });

                        //another option is using modals
                        $('#avatar2').on('click', function () {
                            var modal =
                                    '<div class="modal fade">\
                      <div class="modal-dialog">\
                       <div class="modal-content">\
                            <div class="modal-header">\
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>\
                                    <h4 class="blue">Change Avatar</h4>\
                            </div>\
                            \
                            <form class="no-margin">\
                             <div class="modal-body">\
                                    <div class="space-4"></div>\
                                    <div style="width:75%;margin-left:12%;"><input type="file" name="file-input" /></div>\
                             </div>\
                            \
                             <div class="modal-footer center">\
                                    <button type="submit" class="btn btn-sm btn-success"><i class="ace-icon fa fa-check"></i> Submit</button>\
                                    <button type="button" class="btn btn-sm" data-dismiss="modal"><i class="ace-icon fa fa-times"></i> Cancel</button>\
                             </div>\
                            </form>\
                      </div>\
                     </div>\
                    </div>';

                            var modal = $(modal);
                            modal.modal("show").on("hidden", function () {
                                modal.remove();
                            });

                            var working = false;

                            var form = modal.find('form:eq(0)');
                            var file = form.find('input[type=file]').eq(0);
                            file.ace_file_input({
                                style: 'well',
                                btn_choose: 'Click to choose new avatar',
                                btn_change: null,
                                no_icon: 'ace-icon fa fa-picture-o',
                                thumbnail: 'small',
                                before_remove: function () {
                                    //don't remove/reset files while being uploaded
                                    return !working;
                                },
                                allowExt: ['jpg', 'jpeg', 'png', 'gif'],
                                allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
                            });

                            form.on('submit', function () {
                                if (!file.data('ace_input_files'))
                                    return false;

                                file.ace_file_input('disable');
                                form.find('button').attr('disabled', 'disabled');
                                form.find('.modal-body').append("<div class='center'><i class='ace-icon fa fa-spinner fa-spin bigger-150 orange'></i></div>");

                                var deferred = new $.Deferred;
                                working = true;
                                deferred.done(function () {
                                    form.find('button').removeAttr('disabled');
                                    form.find('input[type=file]').ace_file_input('enable');
                                    form.find('.modal-body > :last-child').remove();

                                    modal.modal("hide");

                                    var thumb = file.next().find('img').data('thumb');
                                    if (thumb)
                                        $('#avatar2').get(0).src = thumb;

                                    working = false;
                                });

                                setTimeout(function () {
                                    deferred.resolve();
                                }, parseInt(Math.random() * 800 + 800));

                                return false;
                            });
                        });

                        if (!ace.vars['touch']) {
                            $('.chosen-select').chosen({allow_single_deselect: true});
                            //resize the chosen on window resize

                            $(window)
                                    .off('resize.chosen')
                                    .on('resize.chosen', function () {
                                        $('.chosen-select').each(function () {
                                            var $this = $(this);
                                            $this.next().css({'width': $this.parent().width()});
                                        })
                                    }).trigger('resize.chosen');
                            //resize chosen on sidebar collapse/expand
                            $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
                                if (event_name != 'sidebar_collapsed')
                                    return;
                                $('.chosen-select').each(function () {
                                    var $this = $(this);
                                    $this.next().css({'width': $this.parent().width()});
                                })
                            });
                        }

                        //chosen plugin inside a modal will have a zero width because the select element is originally hidden
                        //and its width cannot be determined.
                        //so we set the width after modal is show
                        $('#modalAgregarTrasladoAlmacen').on('shown.bs.modal', function () {
                            if (!ace.vars['touch']) {
                                $(this).find('.chosen-container').each(function () {
                                    $(this).find('a:first-child').css('width', '300px');
                                    $(this).find('.chosen-drop').css('width', '300px');
                                    $(this).find('.chosen-search input').css('width', '290px');
                                });
                            }
                        })
                        /**
                         //or you can activate the chosen plugin after modal is shown
                         //this way select element becomes visible with dimensions and chosen works as expected
                         $('#modal-form').on('shown', function () {
                         $(this).find('.modal-chosen').chosen();
                         })
                         */
                    });
        </script>
    </body>
    <%
        } else {
            response.sendRedirect("../");
        }
    %>
</html>
