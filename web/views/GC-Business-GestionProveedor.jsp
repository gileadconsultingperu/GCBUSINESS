<%-- 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Administracion
    Nombre              : GC-Business-GestionProveedor.jsp
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Crear, Modificar, Consultar e Inactivar un proveedor
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="gilead.gcbusiness.model.BeanTipoDocumento"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoTipoDocumentoImpl"%>
<%@page import="gilead.gcbusiness.model.BeanTipoPersona"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoTipoPersonaImpl"%>
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
        <title>GC BUSINESS - Gestión de Proveedores</title>

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
                            <i class="menu-icon fa fa-cogs"></i>
                            <span class="menu-text"> Reportes </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if(opciones.contains(72)){
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
                                if(opciones.contains(73)){
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
                                if(opciones.contains(74)){
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
                            <li class="active">Gestión Proveedores</li>
                        </ul><!-- /.breadcrumb -->

                    </div>

                    <div class="page-content">

                        <div class="page-header">
                            <h1>
                                Gestión Proveedores
                                <small>
                                    <i class="ace-icon fa fa-angle-double-right"></i>
                                    Crear, modificar, eliminar proveedores
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
                                    <table id="tablaProveedores" class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>Nro</th>
                                                <th>Nombre/Razón Social</th>
                                                <th>Tipo Documento</th>   
                                                <th>Nº Documento</th>
                                                <th>Dirección</th>
                                                <th>Teléfono</th>   
                                                <th>Correo</th>
                                                <th>Tipo Persona</th>  
                                                <th>Estado</th>
                                                <th>Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody id="employee_data">
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
            <div class="modal" id="modalAgregarProveedor" tabindex="-1">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="blue bigger">Registrar Nuevo Proveedor</h4>
                        </div>

                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12">
                                    <input type="hidden" id="idproveedor" value="">
                                    <input type="hidden" id="opcion" value="">

                                    <div class="form-group">
                                        <label for="nombre" class="col-sm-4 control-label">Nombre completo o Razón Social</label>
                                        <div class="col-sm-8">
                                            <input type="text" id="nombre" class="form-control"  style="text-transform:uppercase" tabindex="1"/>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="tipodocumento" class="col-sm-4 control-label">Tipo Documento</label>
                                        <div class="col-sm-8">
                                            <select id="tipodocumento" name="tipodocumento" class="col-xs-10 col-sm-6" tabindex="2">
                                                <%
                                                    DaoTipoDocumentoImpl daoTipoDocumento = new DaoTipoDocumentoImpl();
                                                    List<BeanTipoDocumento> tipodocumento = daoTipoDocumento.accionListar();

                                                    for (int i = 0; i < tipodocumento.size(); i++) {
                                                %>
                                                <option value="<%= tipodocumento.get(i).getIdtipodocumento()%>">
                                                    <%= tipodocumento.get(i).getAbreviatura()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>   
                                        </div>
                                    </div>                                  

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="numerodocumento" class="col-sm-4 control-label">Número Documento</label>

                                        <div class="col-sm-8">
                                            <input type="text" id="numerodocumento" class="form-control" style="text-transform:uppercase" tabindex="3"/>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="direccion" class="col-sm-4 control-label">Dirección</label>

                                        <div class="col-sm-8">
                                            <input type="text" id="direccion" class="form-control" style="text-transform:uppercase" tabindex="4"/>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="telefono" class="col-sm-4 control-label">Teléfono</label>

                                        <div class="col-sm-8">
                                            <input type="text" id="telefono" class="form-control" style="text-transform:uppercase" tabindex="5"/>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="correo" class="col-sm-4 control-label">Correo</label>

                                        <div class="col-sm-8">
                                            <input type="text" id="correo" class="form-control" style="text-transform:uppercase" tabindex="6"/>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="tipopersona" class="col-sm-4 control-label">Tipo Persona</label>
                                        <div class="col-sm-8">
                                            <select id="tipopersona" name="tipopersona" class="col-xs-10 col-sm-6" tabindex="7">
                                                <%
                                                    DaoTipoPersonaImpl daoTipoPersona = new DaoTipoPersonaImpl();
                                                    List<BeanTipoPersona> tipopersona = daoTipoPersona.accionListar();

                                                    for (int i = 0; i < tipopersona.size(); i++) {
                                                %>
                                                <option value="<%= tipopersona.get(i).getIdtipopersona()%>">
                                                    <%= tipopersona.get(i).getDescripcion()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>   
                                        </div>
                                    </div>                                          

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="departamento" class="col-sm-4 control-label">Departamento</label>
                                        <div class="col-sm-8">
                                            <select id="departamento" name="departamento" class="col-xs-10 col-sm-6" tabindex="9">
                                                <%
                                                    DaoUbigeoImpl daoUbigeo = new DaoUbigeoImpl();
                                                    List<BeanUbigeo> departamento = daoUbigeo.accionListarDepartamentos();

                                                    for (int i = 0; i < departamento.size(); i++) {
                                                %>
                                                <option value="<%= departamento.get(i).getCodigo_ubidepartamento()%>">
                                                    <%= departamento.get(i).getDescripcionUbigeo()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>   
                                        </div>
                                    </div>         

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="provincia" class="col-sm-4 control-label">Provincia</label>
                                        <div class="col-sm-8">
                                            <select id="provincia" name="provincia" class="col-xs-10 col-sm-6" tabindex="10">                                               
                                            </select>   
                                        </div>
                                    </div>         

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="distrito" class="col-sm-4 control-label">Distrito</label>
                                        <div class="col-sm-8">
                                            <select id="distrito" name="distrito" class="col-xs-10 col-sm-6" tabindex="11">                                               
                                            </select>   
                                        </div>
                                    </div>         

                                    &nbsp;&nbsp;

                                    <div class="form-group" id="divestado">
                                        <label for="estado" class="col-sm-4 control-label">Estado</label>
                                        <div class="col-sm-8">  
                                            <select id="estado" name="estado" class="form-control" tabindex="12">
                                                <option value="A">ACTIVO</option>
                                                <option value="I">INACTIVO</option>
                                            </select> 
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
                                <i class="ace-icon fa fa-"></i>
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
        <script type="text/javascript">
                    if ('ontouchstart' in document.documentElement)
                        document.write("<script src='../assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
        </script>
        <script src="../assets/js/bootstrap.min.js"></script>

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

                        $('body').on('shown.bs.modal', '#modalAgregarProveedor', function () {
                            $('input:visible:enabled:first', this).focus();
                        });

                        var tablaProveedores = $('#tablaProveedores').DataTable({
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
                                url: "../Proveedor",
                                data: {"opcion": "listar"},
                                dataSrc: "data"
                            },
                            columns: [
                                {"data": "nro"},
                                {"data": "nombre"},
                                {"data": "tipodocumento"},
                                {"data": "numerodocumento"},
                                {"data": "direccion"},
                                {"data": "telefono"},
                                {"data": "correo"},
                                {"data": "tipopersona"},
                                {"data": "estado"},
                                {"data": "acciones"}
                            ],
                            dom: '<"row"<"col-xs-12 col-sm-4 col-md-4"l><"col-xs-12 col-sm-4 col-md-4"B><"col-xs-12 col-sm-4 col-md-4"f>>' +
                                    'tr<"row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>> ',
                            'columnDefs': [
                                {
                                    'targets': [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
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

                        $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';
                        new $.fn.dataTable.Buttons(tablaProveedores, {
                            buttons: [
                                {
                                    "text": "<i class='fa fa-plus bigger-110 blue'></i>",
                                    "titleAttr": "Nuevo",
                                    "className": "btn btn-white btn-primary btn-bold",
                                    "action": function () {
                                        $('#opcion').val('insertar');
                                        $('#nombre').val('');
                                        $('#tipodocumento').val('1');
                                        $('#numerodocumento').val('');
                                        $('#direccion').val('');
                                        $('#telefono').val('');
                                        $('#correo').val('');
                                        $('#tipopersona').val('1');
                                        $('#estado').val('A');
                                        $('#divestado').hide();
                                        $('#tipodocumento').prop('disabled', false);
                                        //$("#departamento").val('01');
                                        var departamentoActual = $("#departamento").val();

                                        $.get('../Ubigeo', {
                                            "codigo_ubidepartamento": departamentoActual
                                        }, function (response) {
                                            $('#provincia').html(response);

                                            var provinciaActual = $("#provincia").val();

                                            $.get('../Ubigeo', {
                                                "codigo_ubiprovincia": provinciaActual
                                            }, function (response) {
                                                $('#distrito').html(response);
                                            });
                                        });

                                        $('#modalAgregarProveedor .blue').text('Registrar Nuevo Proveedor');
                                        $('#modalAgregarProveedor').modal('show');
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

                        tablaProveedores.buttons().container().appendTo($('.tableTools-container'));

                        $('#btnGuardar').click(function (event) {
                            var idtipodocumento = $('#tipodocumento').val();
                            var numerodocumento = $('#numerodocumento').val();
                            var nombre = $('#nombre').val();
                            var idtipopersona = $('#tipopersona').val();
                            var idproveedor = $('#idproveedor').val();
                            var direccion = $('#direccion').val();
                            var telefono = $('#telefono').val();
                            var correo = $('#correo').val();
                            var codigoubidistrito = $('#distrito').val();
                            var estado = $('#estado').val();
                            var opcion = $('#opcion').val();
                            $.ajax({
                                method: "POST",
                                url: "../Proveedor",
                                data: {"opcion": opcion, "idtipodocumento": idtipodocumento, "idproveedor": idproveedor, "nombre": nombre, "numerodocumento": numerodocumento,
                                    "idtipopersona": idtipopersona, "direccion": direccion, "telefono": telefono, "correo": correo, "estado": estado,
                                    "codigoubidistrito": codigoubidistrito}
                            }).done(function (data) {
                                var obj = jQuery.parseJSON(data);
                                if (obj.mensaje.indexOf('ERROR') !== -1) {
                                    $('.divError').html(obj.html);
                                    $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                        $('.divError').removeClass('tada animated');
                                    });
                                } else {
                                    tablaProveedores.ajax.reload();
                                    alertify.success(obj.mensaje);
                                }
                                $('#modalAgregarProveedor').modal('hide');
                            });
                        });

                        //Actualizar registro
                        $(document).on('click', '.actualizar', function () {
                            var idproveedor = $(this).attr('id');
                            var row = $(this).parent().parent();
                            $.ajax({
                                url: "../Proveedor",
                                method: "POST",
                                data: {"opcion": "buscar", "idproveedor": idproveedor},
                                success: function (data) {
                                    var obj = jQuery.parseJSON(data);
                                    $('#opcion').val('actualizar');
                                    $('#idproveedor').val(obj.idproveedor);
                                    $('#tipodocumento').val(obj.tipodocumento);
                                    $('#numerodocumento').val(obj.numerodocumento);
                                    $('#tipopersona').val(obj.tipopersona);
                                    $('#nombre').val(obj.nombre);
                                    $('#direccion').val(obj.direccion);
                                    $('#telefono').val(obj.telefono);
                                    $('#correo').val(obj.correo);
                                    var codigoubidistrito = obj.codigoubidistrito + "";
                                    //alertify.error("codigoubidistrito: "+codigoubidistrito.substr(0,2));
                                    var ubidepartamento = codigoubidistrito.substr(0, 2);
                                    $('#departamento').val(ubidepartamento);
                                    $.get('../Ubigeo', {
                                        "codigo_ubidepartamento": ubidepartamento
                                    }, function (response) {
                                        $('#provincia').html(response);

                                        var ubiprovincia = codigoubidistrito.substr(0, 4);

                                        $('#provincia').val(ubiprovincia);

                                        $.get('../Ubigeo', {
                                            "codigo_ubiprovincia": ubiprovincia
                                        }, function (response) {
                                            $('#distrito').html(response);
                                            $('#distrito').val(codigoubidistrito);
                                        });
                                    });
                                    //$('#distrito').val(obj.codigoubidistrito);
                                    $('#estado').val(obj.estado);
                                    $('#divestado').hide();
                                    $('#modalAgregarProveedor .blue').text('Modificar Proveedor');
                                    $('#modalAgregarProveedor').modal('show');
                                },
                                error: function (error) {
                                    alertify.error("ERROR AL EJECUTAR AJAX DE OBTENER DATOS USUARIO");
                                }
                            }).done();

                        });

                        //Eliminar registro
                        $(document).on('click', '.eliminar', function () {
                            var idproveedor = $(this).attr('id');
                            var row = $(this).parent().parent();
                            $.ajax({
                                url: "../Proveedor",
                                method: "POST",
                                data: {"opcion": "eliminar", "idproveedor": idproveedor},
                                success: function (data) {
                                    var obj = jQuery.parseJSON(data);
                                    if (obj.mensaje.indexOf('ERROR') !== -1) {
                                        $('.divError').html(obj.html);
                                        $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                            $('.divError').removeClass('tada animated');
                                        });
                                    } else {
                                        tablaProveedores.ajax.reload();
                                        alertify.success(obj.mensaje);
                                    }
                                },
                                error: function (error) {
                                    alertify.error("ERROR AL EJECUTAR AJAX DE INHABILITAR");
                                }
                            }).done();
                        });

                        //Activar proveedor
                        $(document).on('click', '.activar', function () {
                            var idproveedor = $(this).attr('id');
                            var row = $(this).parent().parent();
                            $.ajax({
                                url: "../Proveedor",
                                method: "POST",
                                data: {"opcion": "activar", "idproveedor": idproveedor},
                                success: function (data) {
                                    var obj = jQuery.parseJSON(data);
                                    if (obj.mensaje.indexOf('ERROR') !== -1) {
                                        $('.divError').html(obj.html);
                                        $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                            $('.divError').removeClass('tada animated');
                                        });
                                    } else {
                                        tablaProveedores.ajax.reload();
                                        alertify.success(obj.mensaje);
                                    }
                                },
                                error: function (error) {
                                    alertify.error("ERROR AL EJECUTAR AJAX DE INHABILITAR");
                                }
                            }).done();
                        });

                        $('#departamento').change(function () {
                            var codigoDpto = $('#departamento').val();
                            $.get('../Ubigeo', {
                                "codigo_ubidepartamento": codigoDpto
                            }, function (response) {
                                $('#provincia').html(response);

                                var provinciaActual = $("#provincia").val();

                                $.get('../Ubigeo', {
                                    "codigo_ubiprovincia": provinciaActual
                                }, function (response) {
                                    $('#distrito').html(response);
                                });
                            });
                        });

                        $('#provincia').change(function () {
                            var codigoProv = $('#provincia').val();
                            $.get('../Ubigeo', {
                                "codigo_ubiprovincia": codigoProv
                            }, function (response) {
                                $('#distrito').html(response);
                            });
                        });
                    });
        </script>
    </body>
    <%
        } else {
            response.sendRedirect("../");
        }
    %>
</html>
