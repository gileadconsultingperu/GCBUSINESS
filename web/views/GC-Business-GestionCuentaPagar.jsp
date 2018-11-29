<%-- 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Proveedores
    Nombre              : GC-Business-GestionCuentaPagar.jsp
    Versión             : 1.0
    Fecha Creación      : 28-10-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Gestionar Cuentas por Pagar
--%>
<%@page import="gilead.gcbusiness.dao.impl.DaoProveedorImpl"%>
<%@page import="gilead.gcbusiness.model.BeanProveedor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gilead.gcbusiness.model.BeanTipoDocumento"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoTipoDocumentoImpl"%>
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
        <title>GC BUSINESS - Gestión de Cuentas por Pagar</title>

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
                            <i class="menu-icon fa fa-file-text"></i>
                            <span class="menu-text"> Reportes </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(72)) {
                            %>
                            <li class="">
                                <a href="GC-Business-ReporteVenta.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Reporte de Ventas
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(73)) {
                            %>
                            <li class="">
                                <a href="GC-Business-ReporteCuentaCobrar.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Reporte Cuentas por Cobrar
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(74)) {
                            %>
                            <li class="">
                                <a href="GC-Business-ReporteInventario.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Reporte de Inventario
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(75)) {
                            %>
                            <li class="">
                                <a href="GC-Business-ReporteMovimientoInventario.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Reporte de Movimientos de Inventario
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(76)) {
                            %>
                            <li class="">
                                <a href="GC-Business-ReporteMovimientoInventarioxProducto.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Reporte de Movimientos de Inventario por Producto
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
                                <a href="#">Proveedores</a>
                            </li>
                            <li class="active">Cuentas por pagar</li>
                        </ul><!-- /.breadcrumb -->

                    </div>

                    <div class="page-content">

                        <div class="page-header">
                            <h1>
                                Cuentas por pagar
                            </h1>
                        </div><!-- /.page-header -->

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <div class="clearfix">
                                    <div class="pull-right tableTools-container"></div>
                                </div>
                                <div class="page-body">                         
                                    <input type="hidden" id="idproveedor" name="idproveedor" value="0">

                                    <div class="row">                
                                        <div class="col-xs-12 col-sm-12">
                                            <div class="form-group">
                                                <label for="proveedor" class="control-label" style="width: 60px;">Cliente:</label>
                                                <select class="chosen-select" id="proveedor" tabindex="1" style="width: 540px;" data-placeholder="Seleccione proveedor...">
                                                    <option value="" selected="selected"></option>
                                                    <%
                                                        DaoProveedorImpl daoProveedor = new DaoProveedorImpl();
                                                        List<BeanProveedor> proveedor = daoProveedor.accionListar();
                                                        for (int i = 0; i < proveedor.size(); i++) {%>
                                                    <option value="<%= proveedor.get(i).getIdproveedor()%>"><%= proveedor.get(i).getNumerodocumento() + " | " + proveedor.get(i).getNombre()%>
                                                    </option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <label for="estadoCC" class="control-label" style="width: 60px;">Estado:</label>
                                                <select id="estadoCC" name="estadoCC" class="styled-select tipo_comprobante" style="width: 160px;" tabindex="6">
                                                    <option value="P">PENDIENTE</option>
                                                    <option value="C">CANCELADO</option>
                                                </select>
                                            </div> 

                                            &nbsp;&nbsp;

                                            <div class="form-group">
                                                <div id="divtablaCuentaPagar" class="col-md-12">        			
                                                    <div class="table-responsive">
                                                        <table id="tablaCuentaPagar" class="table table-bordered table-striped table-hover">
                                                            <thead>
                                                                <tr>
                                                                    <th>Fecha Compra</th>
                                                                    <th>Comprobante</th>
                                                                    <th>Monto Total Compra</th>
                                                                    <th>Saldo por Pagar</th>
                                                                    <th>IDCompra</th>
                                                                    <th>Acciones</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="cuentapagar_data">
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div> 
                                        </div>        		
                                    </div>          
                                </div>

                                <hr>
                                <div class="row">
                                    <div class="col-md-12 center" style="margin-top: 5px;">
                                        <button class="btn btn-xs btn-primary" style="font-size: 1.2em;" id="btnLimpiar">
                                            <i class="fa fa-eraser"></i>
                                            Limpiar
                                        </button>
                                    </div>
                                </div> 
                                <!-- PAGE CONTENT ENDS -->
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->
                </div>
            </div><!-- /.main-content -->

            <!-- Modales -->
            <!--MODAL REGISTRAR PAGO-->
            <div class="modal fade" id="modalRegistrarPago" tabindex="-1" role="dialog" aria-labelledby="modalEliminarLabel">
                <div class="modal-dialog" role="document">                                            
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="modalPagosLabel">Pagos</h4>
                        </div>
                        <div class="modal-body">
                            <form id="formRegistrarPago" class="form-horizontal" action="" method="POST">  
                                <input type="hidden" id="idcuentaPagar" name="idcuentaPagar" value="0">
                                <div class="form-group"> 
                                    <label for="comprobante" class="col-sm-3 control-label">Comprobante:</label>
                                    <div class="col-sm-3">  
                                        <input id="comprobante" class="form-control" name="comprobante"  tabindex="-1" disabled/>   
                                    </div>
                                    <label for="saldoTotal" class="col-sm-2 control-label">Saldo:</label>
                                    <div class="col-sm-3">  
                                        <input id="saldoTotal" class="form-control" name="saldoTotal" tabindex="-1" disabled/>    
                                    </div>
                                </div>
                                &nbsp;
                                <div class="form-group">
                                    <label class="col-sm-2">Movimientos</label>
                                    <div id="divtablaPagos" class="col-sm-12">        			
                                        <div class="table-responsive">
                                            <table id="tablaPagos" class="table table-bordered table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>ID MOVIMIENTO</th>
                                                        <th style="text-align: center">Nº</th>
                                                        <th>Fecha</th>
                                                        <th>Monto</th>
                                                        <th>Saldo anterior</th>
                                                        <th>Saldo actual</th>                                    
                                                        <th>Imprimir</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="pagos_data">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>        		
                                </div>
                                <div class="divPago" style="display: none;">
                                    &nbsp;
                                    <div class="form-group">
                                        <label class="col-sm-3">Realizar Pago</label><input type="checkbox" id="chkPago" checked value="N">
                                    </div>
                                    <div class="form-group"> 
                                        <label for="montoPagar" class="col-sm-3 control-label">Monto a pagar:</label>
                                        <div class="col-sm-2">  
                                            <input id="montoPagar" class="form-control" name="montoPagar"  tabindex="1" style="text-transform: uppercase"/>   
                                        </div>
                                        <label for="referencia" class="col-sm-3 control-label">Referencia:</label>
                                        <div class="col-sm-4">  
                                            <input id="referencia" class="form-control" name="referencia" tabindex="2" style="text-transform: uppercase"/>    
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="modal-footer"> 
                            <div class="form-group">
                                <div style="display: none;" class="divPago col-sm-offset-1 col-sm-4">
                                    <input id="aceptarPago" type="button" class="btn btn-primary" value="Aceptar">
                                </div>
                                <div class="col-sm-4">
                                    <button type="button" class="btn btn-default" data-dismiss="modal" id="btnCancelar" onclick="$('#formRegistrarPago')[0].reset();">CERRAR</button>
                                </div>                           
                            </div>
                            <div class="form-group">
                                <hr style="margin-top: 15px;margin-bottom: 15px;">
                                <div class="divError"></div>
                            </div>
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

        <!-- page specific plugin scripts -->
        <script src="../assets/js/jquery-ui.min.js"></script>
        <script src="../assets/js/jquery.ui.touch-punch.min.js"></script>

        <script type="text/javascript">

                                        $(document).ready(function () {

                                            $('#proveedor').change(function () {
                                                var idproveedor = $(this).val();
                                                var estadoCC = $('#estadoCC').val();
                                                if (idproveedor !== null && idproveedor !== "") {
                                                    $('#idproveedor').val(idproveedor);
                                                    cargarCuentasPagar(idproveedor, estadoCC);
                                                }
                                            });

                                            $('#estadoCC').change(function () {
                                                var idproveedor = $('#idproveedor').val();
                                                var estadoCC = $('#estadoCC').val();
                                                cargarCuentasPagar(idproveedor, estadoCC);
                                            });


                                            function cargarCuentasPagar(idproveedor, estado) {
                                                var tablaCuentaPagar = $('#tablaCuentaPagar').DataTable({
                                                    bAutoWidth: false,
                                                    "processing": true,
                                                    "iDisplayLength": -1,
                                                    "bLengthChange": false,
                                                    "bInfo": false,
                                                    "searching": false,
                                                    "paging": false,
                                                    destroy: true,
                                                    responsive: true,
                                                    "order": [[0, 'asc']],
                                                    ajax: {
                                                        method: "POST",
                                                        url: "../CuentaPagar?opcion=listar&idproveedor=" + idproveedor + "&estado=" + estado,
                                                        dataSrc: "dataCuentaPagar"
                                                    },
                                                    columns: [
                                                        {"data": "fechacompra"},
                                                        {"data": "comprobante"},
                                                        {"data": "totalcompra"},
                                                        {"data": "saldo"},
                                                        {"data": "idcompra"},
                                                        {"data": "acciones"}
                                                    ],
                                                    dom: '<"row"<"col-xs-10 col-sm-2 col-md-2"l><"col-xs-12 col-sm-4 col-md-4"B><"col-xs-12 col-sm-4 col-md-4"f>>' +
                                                            'tr<"row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>> ',
                                                    'columnDefs': [
                                                        {
                                                            'targets': [0, 1, 2, 3, 4],
                                                            'createdCell': function (td, cellData, rowData, row, col) {
                                                                $(td).attr('contenteditable', 'false');
                                                            }
                                                        },
                                                        {
                                                            "targets": [4],
                                                            "visible": false,
                                                            "searchable": false
                                                        }
                                                    ],
                                                    buttons: [
                                                    ],
                                                    language: {
                                                        "url": "../assets/util/espanol.txt"
                                                    }
                                                });

                                                $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';
                                                new $.fn.dataTable.Buttons(tablaCuentaPagar, {
                                                    buttons: [
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

                                                tablaCuentaPagar.buttons().container().appendTo($('.tableTools-container'));
                                            }
                                            ;

                                            //Registrar Pago
                                            $(document).on('click', '.pagar', function () {
                                                var idcuentapagar = $(this).attr('id');
                                                var table = $(this).parent().parent().parent();
                                                $("#comprobante").val(table.find('td:eq(1)').html());
                                                $("#saldoTotal").val(table.find('td:eq(3)').html());
                                                $("#idcuentaPagar").val(idcuentapagar);
                                                cargarPagos(idcuentapagar);
                                                deshabilitarPago();
                                                if ($("#estadoCC").val() === "P") {
                                                    $(".divPago").each(function () {
                                                        $(this).prop("style").display = "inline";
                                                    });
                                                }
                                                $("#modalRegistrarPago").modal('show');
                                            });

                                            //Mostrar detalle cuenta pagar
                                            $(document).on('click', '.detallar', function () {
                                                var idcuentapagar = $(this).attr('id');
                                                window.open('../ImprimirComprobante?tipo=CP&idcuentapagar=' + idcuentapagar, '_blank');
                                            });

                                            //Imprimir movimiento cuenta pagar
                                            $(document).on('click', '.imprimir', function () {
                                                var idmovimientocuentapagar = $(this).attr('id');
                                                window.open('../ImprimirComprobante?tipo=MCP&idmovimientocuentapagar=' + idmovimientocuentapagar, '_blank');
                                            });

                                            function cargarPagos(idCuentaPagar) {
                                                var dataTable = $('#tablaPagos').DataTable({
                                                    bAutoWidth: false,
                                                    "processing": true,
                                                    "iDisplayLength": -1,
                                                    "bLengthChange": false,
                                                    "bInfo": false,
                                                    "searching": false,
                                                    "paging": false,
                                                    destroy: true,
                                                    responsive: true,
                                                    "order": [[1, 'asc']],
                                                    ajax: {
                                                        method: "POST",
                                                        url: "../CuentaPagar?opcion=listarmovimientos&idcuentapagar=" + idCuentaPagar,
                                                        dataSrc: "dataMovimientosCuentaPagar"
                                                    },
                                                    columns: [
                                                        {"data": "idMovimientoCC"},
                                                        {"data": "nroPago"},
                                                        {"data": "fecha"},
                                                        {"data": "monto"},
                                                        {"data": "saldoAnt"},
                                                        {"data": "saldoAct"},
                                                        {"data": "acciones"}
                                                    ],
                                                    'columnDefs': [
                                                        {
                                                            'targets': [0, 1, 2, 3, 4, 5],
                                                            'createdCell': function (td, cellData, rowData, row, col) {
                                                                $(td).attr('contenteditable', 'false');
                                                            }
                                                        },
                                                        {
                                                            "targets": [0],
                                                            "visible": false
                                                        }
                                                    ],
                                                    language: {
                                                        "url": "../assets/util/espanol.txt"
                                                    }
                                                });
                                            }
                                            ;

                                            $("#chkPago").click(function () {
                                                if ($(this).is(':checked')) {
                                                    habilitarPago();
                                                } else {
                                                    deshabilitarPago();
                                                }
                                            });

                                            function habilitarPago() {
                                                $("#montoPagar").prop('disabled', false);
                                                $("#referencia").prop('disabled', false);
                                                $('#aceptarPago').prop('disabled', false);
                                                $("#montoPagar").focus();
                                            }
                                            ;

                                            function deshabilitarPago() {
                                                $("#chkPago").prop('checked', false).removeAttr('checked');
                                                $("#montoPagar").prop('disabled', true);
                                                $("#referencia").prop('disabled', true);
                                                $('#aceptarPago').prop('disabled', true);
                                            }
                                            ;

                                            $("#modalRegistrarPago").on('hidden.bs.modal', function () {
                                                $(this).find('form')[0].reset(); //para borrar todos los datos que tenga los input, textareas, select  
                                                $(".divPago").each(function () {
                                                    $(this).prop("style").display = "none";
                                                });
                                            });

                                            $('#aceptarPago').click(function (event) {
                                                event.preventDefault();
                                                var saldo = $("#saldoTotal").val();
                                                var cant = $('#montoPagar').val();
                                                if (cant === "" || cant === null) {
                                                    alertify.error("Debe ingresar el monto a pagar");
                                                    $('#montoPagar').focus();
                                                    return;
                                                } else {
                                                    var a = Math.floor(cant);
                                                    var b = cant - a;
                                                    if (cant === "0" || (a === 0 && b === 0)) {
                                                        alertify.error("Cuidado! El monto a pagar es CERO");
                                                        $('#montoPagar').focus();
                                                        return;
                                                    } else {
                                                        if (parseFloat(cant) > parseFloat(saldo)) {
                                                            alertify.error("Cuidado! El monto a pagar es MAYOR a la deuda");
                                                            $('#montoPagar').focus();
                                                            return;
                                                        } else {
                                                            var myform = $('#formRegistrarPago');
                                                            // Find disabled inputs, and remove the "disabled" attribute
                                                            var disabled = myform.find(':input:disabled').removeAttr('disabled');
                                                            // serialize the form
                                                            var frm = myform.serialize();
                                                            // re-disabled the set of inputs that you previously enabled
                                                            disabled.attr('disabled', 'disabled');
                                                            //alert("frm: "+frm);
                                                            //return;    
                                                            $.ajax({
                                                                method: "POST",
                                                                url: "../CuentaPagar?opcion=pago",
                                                                data: frm
                                                            }).done(function (data) {
                                                                var obj = jQuery.parseJSON(data);
                                                                if (obj.mensaje.indexOf('ERROR') !== -1) {
                                                                    $('#formRegistrarPago .modal-footer .divError').html(obj.html);
                                                                    $('#formRegistrarPago .modal-footer .divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                                                        $('#formRegistrarPago .modal-footer .divError').removeClass('tada animated');
                                                                    });
                                                                } else {
                                                                    alertify.success(obj.mensaje);
                                                                    cargarCuentasPagar($('#idproveedor').val(), "P");
                                                                    $(".divPago").each(function () {
                                                                        $(this).prop("style").display = "none";
                                                                    });
                                                                    $('#modalRegistrarPago').modal('hide');
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            });

                                            $('#btnLimpiar').click(function () {
                                                $('#proveedor')
                                                        .find('option:first-child').prop('selected', true)
                                                        .end().trigger('chosen:updated');
                                                $('#idproveedor').val('0');
                                                $('#estadoCC').val("P");
                                                $('#tablaCuentaPagar tbody').remove();
                                            });

                                            if (!ace.vars['touch']) {
                                                $('.chosen-select').chosen({allow_single_deselect: true});
                                                //resize the chosen on window resize

                                                $(window)
                                                        .off('resize.chosen')
                                                        .on('resize.chosen', function () {
                                                            $('.chosen-select').each(function () {
                                                                var $this = $(this);
                                                                $this.next().css({'width': 540});
                                                            });
                                                        }).trigger('resize.chosen');
                                                //resize chosen on sidebar collapse/expand
                                                $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
                                                    if (event_name !== 'sidebar_collapsed')
                                                        return;
                                                    $('.chosen-select').each(function () {
                                                        var $this = $(this);
                                                        $this.next().css({'width': 540});
                                                    });
                                                });
                                            }



                                        });
        </script>
    </body>
    <%
        } else {
            response.sendRedirect("../");
        }
    %>
</html>p