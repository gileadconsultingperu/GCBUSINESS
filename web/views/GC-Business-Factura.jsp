<%--
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Ventas
    Nombre              : GC-Business-RegistrarVenta.jsp
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Vista Inicial al acceder al sistema
--%>
<%@page import="gilead.gcbusiness.model.BeanVendedor"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoVendedorImpl"%>
<%@page import="gilead.gcbusiness.model.BeanCliente"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoClienteImpl"%>
<%@page import="gilead.gcbusiness.model.BeanProducto"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoProductoImpl"%>
<%@page import="gilead.gcbusiness.model.BeanUbigeo"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoUbigeoImpl"%>
<%@page import="gilead.gcbusiness.model.BeanMoneda"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoMonedaImpl"%>
<%@page import="gilead.gcbusiness.model.BeanSerie"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoSerieImpl"%>
<%@page import="java.util.List"%>
<%@page import="gilead.gcbusiness.model.BeanUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    //String ID = (String) session.getAttribute("ID");
    List opciones = (List) session.getAttribute("accesos");
    BeanUsuario usuario = (BeanUsuario) session.getAttribute("usuario");
    Integer idalmacen = Integer.parseInt(session.getAttribute("idAlmacen").toString());
    String descripcionalmacen = (String) session.getAttribute("descripcionAlmacen");
    String idcotizacion = (String) request.getParameter("idcotizacion");
%>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta charset="utf-8" />
        <title>GC BUSINESS - Registrar Venta</title>

        <meta name="description" content="Common form elements and layouts" />
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
                <link rel="stylesheet" href="../assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
        <![endif]-->

        <!-- Alertify Version Nueva-->
        <link rel="stylesheet" href="../assets/css/alertify/alertify.css">

        <link rel="stylesheet" href="../assets/css/ace-skins.min.css" />
        <link rel="stylesheet" href="../assets/css/ace-rtl.min.css" />

        <!-- page specific plugin styles -->
        <link rel="stylesheet" href="../assets/css/jquery-ui.min.css" />

        <!--[if lte IE 9]>
          <link rel="stylesheet" href="../assets/css/ace-ie.min.css" />
        <![endif]-->

        <!-- inline styles related to this page -->

        <!-- ace settings handler -->
        <script src="../assets/js/ace-extra.min.js"></script>

        <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

        <!--[if lte IE 8]>
        <script src="../assets/js/html5shiv.min.js"></script>
        <script src="../assets/js/respond.min.js"></script>
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
                                <a href="#">Ventas</a>
                            </li>
                            <li>
                                <%if (idcotizacion == null) {%>
                                <a href="GC-Business-RegistrarVenta.jsp">Registrar Venta</a>
                                <%} else {%>
                                <a href="GC-Business-GestionCotizacion.jsp">Cotizaciones</a>
                                <%}%>
                            </li>
                            <li class="active">Factura Electónica</li>
                        </ul><!-- /.breadcrumb -->

                    </div>

                    <div class="page-content">

                        <div class="page-header">
                            <h1>
                                <%if (idcotizacion == null) {%>
                                Emitir factura electrónica - Almacén: <%=descripcionalmacen%>
                                <%} else {%>
                                Emitir factura electrónica - Almacén: <%=descripcionalmacen%> - Cotización: Nro. <%=idcotizacion%>
                                <%}%>   
                            </h1>
                        </div><!-- /.page-header -->

                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row datos_comprobante">
                            <div class="col-xs-12">
                                <div class="panel panel-primary">
                                    <div class="panel-heading"> Datos Comprobante </div>
                                    <div class="panel-body">
                                        <div class="row col-md-12">
                                            <div class="form-group">
                                                <label for="serie" class="control-label" style="width: 40px;">Serie:</label>
                                                <select id="serie" name="serie" class="styled-select tipo_comprobante" style="width: 70px;" tabindex="1" >
                                                    <%
                                                        DaoSerieImpl daoSerie = new DaoSerieImpl();
                                                        List<BeanSerie> serie = daoSerie.listarSerieTipoComprobanteAlmacen("01", idalmacen);

                                                        for (int i = 0; i < serie.size(); i++) {
                                                    %>
                                                    <option value="<%= serie.get(i).getIdserie()%>">
                                                        <%= serie.get(i).getSerie()%>
                                                    </option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                                &nbsp;
                                                <label for="correlativo" class="control-label" style="width: 80px;">Correlativo:</label>
                                                <input type="text" name="correlativo" id="correlativo" tabindex="2" style="width: 80px;" class="styled-select tipo_comprobante" disabled>
                                                &nbsp;&nbsp;&nbsp;
                                                <label for="moneda" class="control-label" style="width: 55px;">Moneda:</label>
                                                <select id="moneda" name="moneda" class="styled-select tipo_comprobante" style="width: 175px;" tabindex="3" >
                                                    <%
                                                        DaoMonedaImpl daoMoneda = new DaoMonedaImpl();
                                                        List<BeanMoneda> moneda = daoMoneda.accionListar();

                                                        for (int i = 0; i < moneda.size(); i++) {
                                                    %>
                                                    <option value="<%= moneda.get(i).getIdmoneda()%>">
                                                        <%= moneda.get(i).getDescripcion()%>
                                                    </option>
                                                    <%

                                                        }
                                                    %>
                                                </select>
                                                &nbsp;&nbsp;&nbsp;
                                                <label for="negociable" class="control-label hidden" style="width: 75px;">Negociable:</label>
                                                &nbsp;
                                                <input id="switch-negociable" class="ace ace-switch hidden" type="checkbox" />
                                                <span class="lbl hidden" data-lbl="SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NO" tabindex="4" ></span>
                                                &nbsp;
                                                <label id="lblfechavencimiento" class="control-label hide" style="width: 130px;">Fecha Vencimiento:</label>
                                                <input type="text" id="fechavencimiento" style="width: 120px;" class="hide" tabindex="5" readonly>
                                                <i id="iconfechavencimiento" class="ace-icon fa fa-calendar hide" style="width: 10px;"></i>
                                            </div>

                                            <div class="form-group">
                                                <label for="formapago" class="control-label" style="width: 110px;">Forma de Pago:</label>
                                                <select id="formapago" name="formapago" class="styled-select tipo_comprobante" style="width: 160px;" tabindex="6">
                                                    <option value="E">EFECTIVO</option>
                                                    <option value="T">TARJETA</option>
                                                    <option value="C">CHEQUE</option>
                                                    <option value="L">LETRA DE CAMBIO</option>
                                                    <option value="D">DEPÓSITO EN CUENTA</option>
                                                    <option value="O">OTROS</option>
                                                </select>
                                                &nbsp;
                                                <label id="lblnumeroletra" class="control-label hide" style="width: 100px;">Número Letra:</label>
                                                <input type="text" name="numeroletra" id="numeroletra" tabindex="7" style="width: 100px;" class="styled-select tipo_comprobante hide">
                                                &nbsp;
                                                <label id="lblmontoletra" class="control-label hide" style="width: 100px;">Monto Letra:</label>
                                                <input type="text" name="montoletra" id="montoletra" tabindex="8" style="width: 100px;" placeholder="0.00" class="styled-select tipo_comprobante hide">
                                                &nbsp;
                                                <label id="lblfechavencimientoletra" class="control-label hide" style="width: 130px;">Fecha Vencimiento:</label>
                                                <input type="text" id="fechavencimientoletra" style="width: 120px;" class="hide" tabindex="9" readonly>
                                                <i id="iconfechavencimientoletra" class="ace-icon fa fa-calendar hide" style="width: 10px;"></i>
                                            </div>
                                            <div class="form-group">
                                                <label for="estadopago" class="control-label" style="width: 110px;">Estatus Pago:</label>
                                                <select id="estadopago" name="estadopago" class="styled-select tipo_comprobante" style="width: 160px;" tabindex="10">
                                                    <option value="S">SIN PAGAR</option>
                                                    <option value="P">PAGADO PARCIALMENTE</option>
                                                    <option value="T" selected="selected">PAGADO TOTALMENTE</option>
                                                </select>
                                                &nbsp;
                                                <label id="lblmontopagado" class="control-label hide" style="width: 100px;">Monto Pagado:</label>
                                                <input type="text" name="montopagado" id="montopagado" tabindex="11" style="width: 100px;" placeholder="0.00" class="styled-select tipo_comprobante hide">
                                            </div>
                                            <div class="form-group hidden">
                                                <label for="facturagravada" class="control-label" style="width: 115px;">Factura Gravada:</label>
                                                <input id="switch-gravada" class="ace ace-switch" type="checkbox" />
                                                <span class="lbl" data-lbl="SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NO" tabindex="12" ></span>
                                            </div>
                                            <div class="form-group hidden">
                                                <label for="anticipo" class="control-label" style="width: 60px;">Anticipo:</label>
                                                <input id="switch-anticipo" class="ace ace-switch" type="checkbox" />
                                                <span class="lbl" data-lbl="SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NO" tabindex="13" ></span>
                                            </div>
                                        </div>
                                    </div>
                                </div><!-- /.col -->
                            </div>
                        </div><!-- /.row -->
                        <div class="row datos_cliente">
                            <div class="col-xs-12">
                                <input type="hidden" id="idcliente" value="0">
                                <input type="hidden" id="ubigeocliente" value="">
                                <div class="panel panel-primary">
                                    <div class="panel-heading"> Datos Cliente </div>
                                    <div class="panel-body">
                                        <div class="row col-md-12">
                                            <div class="form-group">
                                                <label id="lblnuevocliente" for="nuevocliente" class="control-label hide" style="width: 80px;">Nuevo Cliente:</label>
                                                <input type="text" name="nuevocliente" id="nuevocliente" class="hide" tabindex="16" style="width: 540px;" disabled>
                                            </div>
                                            <div class="form-group">
                                                <label for="cliente" class="control-label" style="width: 80px;">Cliente:</label>
                                                <select class="chosen-select" id="cliente" tabindex="14" style="width: 540px;" data-placeholder="Seleccione cliente...">
                                                    <option value="0" selected="selected"></option>
                                                    <%
                                                        DaoClienteImpl daoCliente = new DaoClienteImpl();
                                                        List<BeanCliente> cliente = daoCliente.accionListar();
                                                        for (int i = 0; i < cliente.size(); i++) {
                                                            if (cliente.get(i).getIdtipodocumento() == 2) {%>
                                                    <option value="<%= cliente.get(i).getIdcliente()%>"><%= cliente.get(i).getNumerodocumento() + " | " + cliente.get(i).getNombre()%>
                                                    </option>
                                                    <%
                                                            }
                                                        }
                                                    %>
                                                </select>
                                                &nbsp;&nbsp;
                                                <label id="lbldepartamento" class="control-label hide" style="width: 110px;">Departamento:</label>
                                                <select id="departamento" name="departamento" class="hide"  style="width: 220px;" tabindex="50">
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
                                            <div class="form-group">
                                                <label for="ruc" class="control-label" style="width: 80px;">RUC:</label>
                                                <input type="text" name="ruc" id="ruc" tabindex="15" style="width: 433px;">
                                                &nbsp;
                                                <button type="button" class="btn btn-xs btn-primary" id="buscarsunat">Buscar SUNAT</button>
                                                &nbsp;&nbsp;
                                                <label id="lblprovincia" class="control-label hide" style="width: 110px;">Provincia:</label>
                                                <select id="provincia" name="provincia" class="hide"  style="width: 220px;" tabindex="51">
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="direccion" class="control-label" style="width: 80px;">Dirección:</label>
                                                <input type="text" name="direccion" id="direccion" tabindex="16" style="width: 540px;" disabled>
                                                &nbsp;&nbsp;
                                                <label id="lbldistrito" class="control-label hide" style="width: 110px;">Distrito:</label>
                                                <select id="distrito" name="distrito" class="hide" style="width: 220px;" tabindex="52">
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="vendedor" class="control-label" style="width: 80px;">Vendedor:</label>
                                                <select id="vendedor" name="vendedor" style="width: 200px;" tabindex="17" disabled>
                                                    <option value="0" selected="selected"></option>
                                                    <%
                                                        DaoVendedorImpl daoVendedor = new DaoVendedorImpl();
                                                        List<BeanVendedor> vendedor = daoVendedor.accionListar();

                                                        for (int i = 0; i < vendedor.size(); i++) {
                                                    %>
                                                    <option value="<%= vendedor.get(i).getIdvendedor()%>">
                                                        <%= vendedor.get(i).getDescripcion()%>
                                                    </option>
                                                    <%

                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div><!-- /.col -->
                            </div>
                        </div><!-- /.row -->
                        <div class="row datos_cliente">
                            <div class="col-xs-12">
                                <div class="panel panel-primary">
                                    <div class="panel-heading"> Productos </div>
                                    <div class="panel-body">
                                        <input type="hidden" id="idproducto" value="0">
                                        <input type="hidden" id="rowdetalle" value="0">

                                        <div class="row col-md-12">
                                            <div class="form-group">
                                                <label for="producto" class="control-label" style="width: 80px;">Producto:</label>
                                                <select class="chosen-select" id="producto" tabindex="18" style="width: 400px;" data-placeholder="Seleccione producto...">
                                                    <option value="" selected="selected"></option>
                                                    <%
                                                        DaoProductoImpl daoProducto = new DaoProductoImpl();
                                                        List<BeanProducto> producto = daoProducto.accionListarActivo();
                                                        for (int i = 0; i < producto.size(); i++) {%>
                                                    <option value="<%= producto.get(i).getIdproducto()%>"><%= producto.get(i).getCodigo() + " | " + producto.get(i).getDescripcion()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                                &nbsp;
                                                <label id="lbllote" class="control-label hide" style="width: 80px;">Lote|F.V.:</label>
                                                <select id="lote" name="lote" class="hide"  style="width: 200px;" tabindex="20">
                                                </select>
                                                &nbsp;
                                                <button class="btn btn-sm btn-primary" id="btnAgregarDetalle" tabindex="21" title="Agregar Producto">
                                                    <i class="ace-icon fa fa-plus"></i>
                                                </button>
                                            </div>
                                            <div class="form-group">
                                                <table id="detalleVenta" class="table table-striped table-bordered table-hover" sortable="1" style="font-size:10px">
                                                    <thead>
                                                        <tr>
                                                            <th style="display: none">IdProducto</th>
                                                            <th style="display: none">#</th>
                                                            <th>Código</th>
                                                            <th>Descripción</th>
                                                            <th>Cantidad</th>
                                                            <th>Medida</th>
                                                            <th>Precio Unitario</th>
                                                            <th style="display: none">Valor Unitario</th>
                                                            <th style="display: none">Precio Total sDscto</th>
                                                            <th style="display: none">Valor Total</th>
                                                            <th style="display: none">Afecto IGV</th>
                                                            <th style="display: none">IGV sDscto</th>
                                                            <th style="display: none">ISC</th>
                                                            <th style="display: none">Precio Unitario Dscto</th>
                                                            <th style="display: none">Valor Unitario Dscto</th>
                                                            <th>Precio Total</th>
                                                            <th>IGV</th>
                                                            <th style="display: none">Valor Total Dscto</th>
                                                            <th>Descuento</th>
                                                            <th style="display: none">Descuento Porc</th>
                                                            <th style="display: none">Descuento Mont</th>
                                                            <th>Lote|F.V.</th>
                                                            <th>Stock Actual</th>
                                                            <th>Bonificación</th>
                                                            <th>Acciones</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="detalleVenta_data">
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div><!-- /.col -->
                            </div>
                        </div><!-- /.row -->

                        <div class="row totales_div">
                            <div class="col-md-3">

                            </div>
                            <div class="col-md-4">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">Total impuestos</div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label for="total_igv" class="control-label" style="width: 80px;">Total IGV:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_igv" id="total_igv" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_isc" class="control-label" style="width: 80px;">Total ISC:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_isc" id="total_isc" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_otros_impuestos" class="control-label" style="width: 80px;">Total otros impuestos:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_otros_impuestos" id="total_otros_impuestos" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="form-group">
                                            <label for="total_impuestos" class="control-label" style="width: 80px;">Total:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_impuestos" id="total_impuestos" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <input type="hidden" id="total_valorventa" value="0">
                                <input type="hidden" id="total_precioventa" value="0">
                                <input type="hidden" id="dcto_global_monto" value="0"> 
                                <input type="hidden" id="dcto_global_pcto" value="0"> 
                                <div class="panel panel-primary">
                                    <div class="panel-heading">Totales factura</div>
                                    <div class="panel-body">
                                        <div class="form-group hidden">
                                            <label for="input_dcto_global" class="control-label" style="width: 180px;">Descuento global:</label>
                                            <input type="text" name="input_dcto_global" id="input_dcto_global" tabindex="-1" style="width: 115px;">
                                            <select id="select_dcto_total" style="width: 72px;">
                                                <option value="P">%</option>
                                                <option value="M">Monto</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_gravadas" class="control-label" style="width: 180px;">Total gravadas:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_gravadas" id="total_gravadas" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_inafectas" class="control-label" style="width: 180px;">Total inafectas:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_inafectas" id="total_inafectas" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_exoneradas" class="control-label" style="width: 180px;">Total exoneradas:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_exoneradas" id="total_exoneradas" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_gratuitas" class="control-label" style="width: 180px;">Total gratuitas:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_gratuitas" id="total_gratuitas" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_otros_cargos" class="control-label" style="width: 180px;">Total otros cargos:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_otros_cargos" id="total_otros_cargos" tabindex="-1" placeholder="0.00" style="width: 160px;">
                                        </div>
                                        <div class="form-group">
                                            <label for="total_descuentos" class="control-label" style="width: 180px;">Total descuentos:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_descuentos" id="total_descuentos" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>

                                        <div class="form-group" style="display:none;">
                                            <label for="total_anticipos" class="control-label" style="width: 180px;">Total anticipos:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_anticipos" id="total_anticipos" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                        <div class="form-group" style="display:none;">
                                            <label for="total_percepciones" class="control-label" style="width: 180px;">Total percepciones:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_percepciones" id="total_percepciones" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>

                                    </div>
                                    <div class="panel-footer">
                                        <div class="form-group">
                                            <label for="total_venta" class="control-label" style="width: 180px;">Importe total venta:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_venta" id="total_venta" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                        <div class="form-group mostrar_tipo_cambio" style="display:none;">
                                            <label for="total_venta_soles" class="control-label" style="width: 180px;">Importe total venta en soles:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_venta_soles" id="total_venta_soles" tabindex="-1" value="0.00" style="width: 160px;" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3">
                            </div>
                            <div class="col-md-6 center" style="margin-top: 5px;">
                                <button class="btn btn-xs btn-primary registrar_venta" style="font-size: 1.2em;"> <span><i class="fa fa-save"></i></span> Registrar Venta</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a id="imprimir" class="btn btn-xs btn-primary imprimir" target="_blank" style="font-size: 1.2em;" disabled>
                                    <span><i class="fa fa-print"></i></span> Imprimir
                                </a> 
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button class="btn btn-xs btn-primary limpiar" style="font-size: 1.2em;"> <span><i class="fa fa-trash"></i></span> Limpiar</button>
                            </div>
                            <div class="col-md-3">
                            </div>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.main-content -->

                    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
                    </a>
                </div>
            </div><!-- /.main-container -->
        </div>

        <!-- basic scripts -->

        <!--[if !IE]> -->
        <script src="../assets/js/jquery-2.1.4.min.js"></script>

        <!-- <![endif]-->

        <script src="../assets/js/jquery.tabletojson.min.js"></script>

        <!--[if IE]>
        <script src="../assets/js/jquery-1.11.3.min.js"></script>
        <![endif]-->
        <!--<script src="../assets/js/chosen.jquery.min.js"></script>-->
        <script src="../assets/js/chosen/chosen.jquery.js"></script>
        <script type="text/javascript">
                    if ('ontouchstart' in document.documentElement)
                        document.write("<script src='../assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
        </script>
        <script src="../assets/js/bootstrap.min.js"></script>
        <script src="../assets/js/jquery.maskedinput.min.js"></script>

        <!-- page specific plugin scripts -->

        <!--[if lte IE 8]>
        <script src="../assets/js/excanvas.min.js"></script>
        <![endif]-->
        <script src="../assets/js/jquery-ui.custom.min.js"></script>
        <script src="../assets/js/jquery.ui.touch-punch.min.js"></script>
        <script src="../assets/js/jquery.easypiechart.min.js"></script>
        <script src="../assets/js/jquery.sparkline.index.min.js"></script>
        <script src="../assets/js/jquery.flot.min.js"></script>
        <script src="../assets/js/jquery.flot.pie.min.js"></script>
        <script src="../assets/js/jquery.flot.resize.min.js"></script>

        <!-- ace scripts -->
        <script src="../assets/js/ace-elements.min.js"></script>
        <script src="../assets/js/ace.min.js"></script>

        <!-- page specific plugin scripts -->
        <script src="../assets/js/jquery-ui.min.js"></script>
        <script src="../assets/js/jquery.ui.touch-punch.min.js"></script>

        <script type="text/javascript">
                    $(document).ready(function () {
                        $(window).load(function () {
                            cargarCorrelativo();

                            $('#formapago').val('E');
                            var idalmacen = <%=idalmacen%>;
                            if (idalmacen === 1) {
                                $('#estadopago').val('S');
                            } else {
                                $('#estadopago').val('T');
                            }

                            var idcotizacion = <%=idcotizacion%>;
                            if (idcotizacion !== null) {
                                $.ajax({
                                    url: "../Cotizacion",
                                    method: "GET",
                                    data: {"opcion": "obtenercotizacion", "idcotizacion": idcotizacion},
                                    success: function (data) {
                                        var obj = jQuery.parseJSON(data);
                                        $('#total_igv').val(obj.totaligv);
                                        $('#total_isc').val(parseFloat(obj.totalisc).toFixed(2));
                                        $('#total_otros_impuestos').val(parseFloat(obj.totalotrotributo).toFixed(2));
                                        $('#total_impuestos').val(obj.totalimpuesto);
                                        if (obj.tipodescuentoglobal === 'P') {
                                            if (obj.pctodescuentoglobal !== 0) {
                                                $('#input_dcto_global').val(obj.pctodescuentoglobal);
                                            } else {
                                                $('#input_dcto_global').val('');
                                            }
                                        } else {
                                            if (obj.montodescuentoglobal !== 0) {
                                                $('#input_dcto_global').val(obj.montodescuentoglobal);
                                            } else {
                                                $('#input_dcto_global').val('');
                                            }
                                        }
                                        $('#select_dcto_total').val(obj.tipodescuentoglobal);
                                        $('#total_gravadas').val(obj.totalgravada);
                                        $('#total_exoneradas').val(obj.totalexonerada);
                                        $('#total_inafectas').val(obj.totalinafecta);
                                        $('#total_gratuitas').val(obj.totalgratuita);
                                        $('#total_descuentos').val(obj.totaldescuento);
                                        $('#total_venta').val(obj.totalventa);

                                        //Obtener detalle
                                        $.get('../Cotizacion', {
                                            "opcion": "obtenerdetallecotizacion",
                                            "idcotizacion": idcotizacion,
                                            "idalmacen": obj.idalmacen
                                        }, function (response) {
                                            $('#detalleVenta').append(response);
                                            $('#producto')
                                                    .find('option:first-child').prop('selected', true)
                                                    .end().trigger('chosen:updated');
                                            calcularTotales();
                                        });
                                    },
                                    error: function (error) {
                                        alertify.error("ERROR AL EJECUTAR AJAX DE OBTENER DATOS USUARIO");
                                    }
                                }).done();
                            }
                        });

                        var cargarCorrelativo = function () {
                            var idSerie = $('#serie').val();
                            $.get('../Serie', {
                                idSerie: idSerie
                            }, function (response) {
                                $('#correlativo').val(response);
                            });

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

                        };

                        $('#serie').change(function () {
                            var idSerie = $('#serie').val();
                            $.get('../Serie', {
                                idSerie: idSerie
                            }, function (response) {
                                $('#correlativo').val(response);
                            });
                        });


                        $('#switch-negociable').removeAttr('checked').on('click', function () {
                            //$validation = this.checked;
                            if (this.checked) {
                                $('#fechavencimiento').removeClass('hide');
                                $('#lblfechavencimiento').removeClass('hide');
                                $('#iconfechavencimiento').removeClass('hide');
                                $('#lbldepartamento').removeClass('hide');
                                $('#departamento').removeClass('hide');
                                $('#lblprovincia').removeClass('hide');
                                $('#provincia').removeClass('hide');
                                $('#lbldistrito').removeClass('hide');
                                $('#distrito').removeClass('hide');
                            } else {
                                $('#fechavencimiento').addClass('hide');
                                $('#lblfechavencimiento').addClass('hide');
                                $('#iconfechavencimiento').addClass('hide');
                                $('#lbldepartamento').addClass('hide');
                                $('#departamento').addClass('hide');
                                $('#lblprovincia').addClass('hide');
                                $('#provincia').addClass('hide');
                                $('#lbldistrito').addClass('hide');
                                $('#distrito').addClass('hide');
                            }
                        });

                        $("#fechavencimiento").datepicker({
                            dateFormat: 'dd/mm/yy',
                            minDate: '+1d'
                        }).datepicker("setDate", new Date());

                        $.datepicker.regional['es'] = {
                            closeText: 'Cerrar',
                            prevText: '< Ant',
                            nextText: 'Sig >',
                            currentText: 'Hoy',
                            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
                            monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
                            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
                            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié', 'Juv', 'Vie', 'Sáb'],
                            dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
                            weekHeader: 'Sm',
                            dateFormat: 'dd/mm/yy',
                            firstDay: 1,
                            isRTL: false,
                            showMonthAfterYear: false,
                            yearSuffix: ''
                        };
                        $.datepicker.setDefaults($.datepicker.regional['es']);



                        $('#formapago').change(function () {
                            var formapago = $('#formapago').val();
                            if (formapago === "L") {
                                $('#lblnumeroletra').removeClass('hide');
                                $('#numeroletra').removeClass('hide');
                                $('#lblmontoletra').removeClass('hide');
                                $('#montoletra').removeClass('hide');
                                $('#fechavencimientoletra').removeClass('hide');
                                $('#lblfechavencimientoletra').removeClass('hide');
                                $('#iconfechavencimientoletra').removeClass('hide');
                            } else {
                                $('#lblnumeroletra').addClass('hide');
                                $('#numeroletra').addClass('hide');
                                $('#lblmontoletra').addClass('hide');
                                $('#montoletra').addClass('hide');
                                $('#fechavencimientoletra').addClass('hide');
                                $('#lblfechavencimientoletra').addClass('hide');
                                $('#iconfechavencimientoletra').addClass('hide');
                            }
                        });

                        $("#fechavencimientoletra").datepicker({
                            dateFormat: 'dd/mm/yy',
                            minDate: '+1d'
                        }).datepicker("setDate", new Date());



                        $('#estadopago').change(function () {
                            var formapago = $('#estadopago').val();
                            if (formapago === "P") {
                                $('#lblmontopagado').removeClass('hide');
                                $('#montopagado').removeClass('hide');
                                $('#montopagado').focus();
                            } else {
                                $('#lblmontopagado').addClass('hide');
                                $('#montopagado').addClass('hide');
                                $('#montopagado').val('');
                            }
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


                        $('#cliente').change(function () {
                            $('#idcliente').val('0');
                            $('#ruc').val('');
                            $('#direccion').val('');
                            $('#vendedor').val('0');
                            $('#vendedor').attr('disabled', true);
                            $('#nuevocliente').val('');
                            $('#nuevocliente').addClass('hide');
                            $('#lblnuevocliente').addClass('hide');
                            $('#ubigeocliente').val('');
                            var idcliente = $(this).val();
                            if (idcliente !== "0") {
                                $.ajax({
                                    url: "../Cliente",
                                    method: "POST",
                                    data: {"opcion": "buscar", "idcliente": idcliente},
                                    success: function (data) {
                                        var obj = jQuery.parseJSON(data);
                                        $('#idcliente').val(obj.idcliente);
                                        $('#ruc').val(obj.numerodocumento);
                                        $('#direccion').val(obj.direccion);
                                        $('#vendedor').val(obj.vendedor);
                                    },
                                    error: function (error) {
                                        alertify.error("ERROR AL EJECUTAR AJAX DE OBTENER DATOS USUARIO");
                                    }
                                }).done();
                            }
                        });


                        $("#btnAgregarDetalle").click(function () {
                            var rowCount = $('#detalleVenta >tbody >tr').length;
                            var cont = parseInt($('#rowdetalle').val());
                            var newRow = cont + 1;
                            $('#rowdetalle').val(newRow);
                            var idproducto = $('#producto').val();
                            if (idproducto !== "0" && idproducto !== "") {
                                $.get('../Producto', {
                                    "opcion": "agregarVenta",
                                    "idproducto": idproducto,
                                    "numeroproductos": newRow
                                }, function (response) {
                                    $('#detalleVenta').append(response);
                                    $('#producto')
                                            .find('option:first-child').prop('selected', true)
                                            .end().trigger('chosen:updated');
                                    calcular(newRow);
                                    calcularTotales();
                                });
                            }
                        });

                        $("#detalleVenta").on('click', '.eliminarDetalleVenta', function () {
                            $(this).closest('tr').remove();
                            calcularTotales();
                        });


                        function calcular(orden) {
                            var cantidad = $('#cantidad_' + orden).val();
                            var stockActual = Number($('#stock_' + orden).html());
                            if (cantidad > stockActual) {
                                alertify.error("Alerta! El stock disponible es de " + stockActual);
                                $('#stock_' + orden).css({"background-color": "red", "color": "white"});
                                $('#cantidad_' + orden).focus();
                                return;
                            }
                            var precioUni = Number($('#tarifa_' + orden + ' option:selected').text());

                            var afecto_igv = $('#afecto_igv_' + orden).html();
                            var valorIGV = 0;
                            var igv = 0;
                            var valorUni = 0;
                            if (afecto_igv === 'G') {
                                valorIGV = 0.18;
                                igv = precioUni * (valorIGV / (1 + valorIGV));
                            }
                            $('#igv_' + orden).html(parseFloat(igv * cantidad).toFixed(2));

                            var valorUni = precioUni - igv;
                            $('#valor_uni_' + orden).html(parseFloat(valorUni).toFixed(2));
                            var precioTot = Number(cantidad * precioUni);
                            var valorTot = Number(cantidad * valorUni);
                            $('#precio_tot_' + orden).html(parseFloat(precioTot).toFixed(2));
                            $('#valor_tot_' + orden).html(parseFloat(valorTot).toFixed(2));

                            var descuento = Number($('#descuento_' + orden).val());
                            var tipoDcto = $('#dcto_prod_' + orden).val();
                            var valorTotDscto = 0;
                            if (tipoDcto === 'P') {
                                var montDscto = valorTot * (descuento / 100);
                                $('#dscto_porc_' + orden).html(parseFloat(descuento).toFixed(2));
                                $('#dscto_mont_' + orden).html(parseFloat(montDscto).toFixed(2));
                                valorTotDscto = valorTot - montDscto;
                            } else {
                                var porcDscto = (descuento / valorTot) * 100;
                                $('#dscto_porc_' + orden).html(parseFloat(porcDscto).toFixed(2));
                                $('#dscto_mont_' + orden).html(parseFloat(descuento).toFixed(2));
                                valorTotDscto = valorTot - descuento;
                            }

                            var valorUniDscto = valorTotDscto / cantidad;
                            $('#valor_tot_dscto_' + orden).html(parseFloat(valorTotDscto).toFixed(2));
                            $('#valor_uni_dscto_' + orden).html(parseFloat(valorUniDscto).toFixed(2));

                            var igvDscto = 0;
                            if (afecto_igv === 'G') {
                                valorIGV = 0.18;
                                igvDscto = valorTotDscto * valorIGV;
                            }
                            $('#igv_dscto_' + orden).html(parseFloat(igvDscto).toFixed(2));

                            var precioTotDscto = valorTotDscto + igvDscto;
                            var precioUniDscto = precioTotDscto / cantidad;
                            $('#precio_tot_dscto_' + orden).html(parseFloat(precioTotDscto).toFixed(2));
                            $('#precio_uni_dscto_' + orden).html(parseFloat(precioUniDscto).toFixed(2));
                        }

                        $('#detalleVenta').on('focusout', '.input_cantidad', function () {
                            var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                            var cantidad = $("#cantidad_" + orden).val();
                            $('#cantidad_' + orden).val(parseFloat(Number($("#cantidad_" + orden).val())).toFixed(3).toString());
                            if (cantidad <= 0) {
                                $('#cantidad_' + orden).focus();
                                alertify.error("EL VALOR DE CANTIDAD DEBE SER MAYOR A CERO");
                            }
                            calcular(orden);
                            calcularTotales();
                        });

                        $('#detalleVenta').on('change', '.input_cantidad', function () {
                            var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                            var bonificacion = $('#bonificacion_' + orden).is(':checked');
                            $('#stock_' + orden).css({"background-color": "transparent", "color": "black"});
                            if (bonificacion) {
                                var cantidad = $('#cantidad_' + orden).val();
                                var stockActual = Number($('#stock_' + orden).html());
                                if (cantidad > stockActual) {
                                    alertify.error("Alerta! El stock disponible es de " + stockActual);
                                    $('#stock_' + orden).css({"background-color": "red", "color": "white"});
                                    $('#cantidad_' + orden).focus();
                                    return;
                                }
                            } else {
                                calcular(orden);
                            }
                            calcularTotales();
                        });

                        $('#detalleVenta').on('keyup', '.input_cantidad', function () {
                            var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                            var bonificacion = $('#bonificacion_' + orden).is(':checked');
                            $('#stock_' + orden).css({"background-color": "transparent", "color": "black"});
                            if (bonificacion) {
                                var cantidad = $('#cantidad_' + orden).val();
                                var stockActual = Number($('#stock_' + orden).html());
                                if (cantidad > stockActual) {
                                    alertify.error("Alerta! El stock disponible es de " + stockActual);
                                    $('#stock_' + orden).css({"background-color": "red", "color": "white"});
                                    $('#cantidad_' + orden).focus();
                                    return;
                                }
                            } else {
                                calcular(orden);
                            }
                            calcularTotales();
                        });

                        $('#detalleVenta').on('click', '.bonificacion', function () {
                            var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                            if ($(this).is(':checked')) {
                                var precioUni = Number($('#tarifa_' + orden + ' option:selected').text());
                                $('#valor_uni_' + orden).html(parseFloat(precioUni).toFixed(2));
                                $('#precio_tot_' + orden).html(parseFloat(0).toFixed(2));
                                $('#valor_tot_' + orden).html(parseFloat(0).toFixed(2));
                                $('#igv_' + orden).html(parseFloat(0).toFixed(2));
                                $('#precio_uni_dscto_' + orden).html(parseFloat(precioUni).toFixed(2));
                                $('#valor_uni_dscto_' + orden).html(parseFloat(precioUni).toFixed(2));
                                $('#precio_tot_dscto_' + orden).html(parseFloat(0).toFixed(2));
                                $('#valor_tot_dscto_' + orden).html(parseFloat(0).toFixed(2));
                                $('#igv_dscto_' + orden).html(parseFloat(0).toFixed(2));
                                $('#descuento_' + orden).val('');
                                $('#descuento_' + orden).prop('disabled', true);
                                $('#dscto_porc_' + orden).html(parseFloat(0).toFixed(2));
                                $('#dscto_mont_' + orden).html(parseFloat(0).toFixed(2));
                            } else {
                                calcular(orden);
                                $('#descuento_' + orden).val('');
                                $('#descuento_' + orden).prop('disabled', false);
                            }
                            calcularTotales();
                        });

                        $('#detalleVenta').on('change', '.select_tarifa', function () {
                            var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                            var bonificacion = $('#bonificacion_' + orden).is(':checked');
                            if (bonificacion) {
                                var precioUni = Number($('#tarifa_' + orden + ' option:selected').text());
                                $('#valor_uni_' + orden).html(parseFloat(precioUni).toFixed(2));
                                $('#precio_uni_dscto_' + orden).html(parseFloat(precioUni).toFixed(2));
                                $('#valor_uni_dscto_' + orden).html(parseFloat(precioUni).toFixed(2));
                            } else {
                                calcular(orden);
                            }
                            calcularTotales();
                        });



                        $('#detalleVenta').on('keyup', '.monto_descuento', function () {
                            var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                            var bonificacion = $('#bonificacion_' + orden).is(':checked');
                            if (!bonificacion) {
                                calcular(orden);
                            }
                            calcularTotales();
                        });

                        $('#detalleVenta').on('blur', '.monto_descuento', function () {
                            var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                            var bonificacion = $('#bonificacion_' + orden).is(':checked');
                            if (!bonificacion) {
                                calcular(orden);
                            }
                            calcularTotales();
                        });

                        $('#detalleVenta').on('change', '.select_tipo_dcto', function () {
                            var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                            var bonificacion = $('#bonificacion_' + orden).is(':checked');
                            if (!bonificacion) {
                                calcular(orden);
                            }
                            calcularTotales();
                        });

                        $('#detalleVenta').on('change', '.select_lote', function () {
                            var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                            var array = [];
                            array = $('#lote_' + orden).val().split("|");
                            var idlote = array[0];
                            var stock = array[1];
                            $('#stock_' + orden).html(parseFloat(stock).toFixed(2));
                        });

                        function calcularTotales() {
                            var bonificacion;
                            var afecto_igv = "";
                            var total_igv = 0;
                            var total_isc = 0;
                            var total_gravadas = 0;
                            var total_inafectas = 0;
                            var total_exoneradas = 0;
                            var total_gravadas_sdscto = 0;
                            var total_inafectas_sdscto = 0;
                            var total_exoneradas_sdscto = 0;
                            var total_gratuitas = 0;
                            var total_descuentos = 0;
                            $(".valor_unitario").each(function () {
                                bonificacion = $(this).parent().find('td:eq(23)').find('input.bonificacion').is(':checked');
                                var orden = $(this).parents('tr').find('td:eq(24)').find('button.eliminarDetalleVenta').attr('id');
                                var cantidad = $('#cantidad_' + orden).val();
                                if (bonificacion) {
                                    total_gratuitas += Number($('#precio_uni_dscto_' + orden).html()) * cantidad;
                                } else {
                                    afecto_igv = $(this).parent().find('td:eq(10)').html();
                                    if (afecto_igv === 'G') {
                                        total_gravadas += Number($('#valor_tot_dscto_' + orden).html());
                                        total_gravadas_sdscto += Number($('#valor_tot_' + orden).html());
                                    }
                                    if (afecto_igv === 'E') {
                                        total_exoneradas += Number($('#valor_tot_dscto_' + orden).html());
                                        total_exoneradas_sdscto += Number($('#valor_tot_' + orden).html());
                                    }
                                    if (afecto_igv === 'I') {
                                        total_inafectas += Number($('#valor_tot_dscto_' + orden).html());
                                        total_inafectas_sdscto += Number($('#valor_tot_' + orden).html());
                                    }
                                }
                                total_igv += Number($('#igv_dscto_' + orden).html());
                                total_descuentos += Number($('#dscto_mont_' + orden).html());
                            });

                            //Valor total de venta sin descuento ni impuestos
                            var totalValorVenta = total_gravadas_sdscto + total_exoneradas_sdscto + total_inafectas_sdscto;
                            $('#total_valorventa').val(parseFloat(totalValorVenta).toFixed(2));

                            var dctoGlobal = $('#input_dcto_global').val();
                            var tipoDctoGlobal = $('#select_dcto_total').val();
                            var dctoGlobalMonto = 0;
                            var dctoGlobalPorcentaje = 0;
                            if (dctoGlobal !== '') {
                                if (total_gravadas + total_exoneradas + total_inafectas + total_igv + total_isc > 0) {
                                    if (tipoDctoGlobal === 'P') {
                                        dctoGlobalPorcentaje = dctoGlobal;
                                        $('#dcto_global_pcto').val(dctoGlobalPorcentaje);
                                        dctoGlobalMonto = (total_gravadas + total_exoneradas + total_inafectas + total_igv + total_isc) * dctoGlobalPorcentaje / 100;
                                        $('#dcto_global_monto').val(dctoGlobalMonto);
                                    } else {
                                        dctoGlobalPorcentaje = (dctoGlobal / (total_gravadas + total_exoneradas + total_inafectas + total_igv + total_isc)) * 100;
                                        $('#dcto_global_pcto').val(dctoGlobalPorcentaje);
                                        dctoGlobalMonto = (total_gravadas + total_exoneradas + total_inafectas + total_igv + total_isc) * dctoGlobalPorcentaje / 100;
                                        $('#dcto_global_monto').val(dctoGlobalMonto);
                                    }
                                } else {
                                    $('#input_dcto_global').val('');
                                    alertify.error("NO EXISTE MONTO PARA APLICAR DESCUENTO GLOBAL");
                                    calcularTotales();
                                }
                            }
                            total_gravadas = total_gravadas * (1 - dctoGlobalPorcentaje / 100);
                            total_exoneradas = total_exoneradas * (1 - dctoGlobalPorcentaje / 100);
                            total_inafectas = total_inafectas * (1 - dctoGlobalPorcentaje / 100);
                            total_igv = total_igv * (1 - dctoGlobalPorcentaje / 100);
                            total_isc = total_isc * (1 - dctoGlobalPorcentaje / 100);
                            total_descuentos = total_descuentos + dctoGlobalMonto;

                            $('#total_igv').val(parseFloat(total_igv).toFixed(2));
                            $('#total_gravadas').val(parseFloat(total_gravadas).toFixed(2));
                            $('#total_exoneradas').val(parseFloat(total_exoneradas).toFixed(2));
                            $('#total_inafectas').val(parseFloat(total_inafectas).toFixed(2));
                            $('#total_gratuitas').val(parseFloat(total_gratuitas).toFixed(2));
                            $('#total_descuentos').val(parseFloat(total_descuentos).toFixed(2));

                            var total_impuestos = total_igv + total_isc;
                            $('#total_impuestos').val(parseFloat(total_impuestos).toFixed(2));

                            var total_otros_cargos = $('#total_otros_cargos').val() * 1;

                            var total_venta = total_gravadas + total_exoneradas + total_inafectas + total_igv + total_isc + total_otros_cargos;
                            $('#total_venta').val(parseFloat(total_venta).toFixed(2));

                            //Precio total de venta incluye impuestos y descuentos, pero no cargos.
                            var totalPrecioVenta = total_gravadas + total_exoneradas + total_inafectas + total_igv + total_isc;
                            $('#total_precioventa').val(parseFloat(totalPrecioVenta).toFixed(2));
                        }

                        $("#input_dcto_global").keyup(function () {
                            calcularTotales();
                        });

                        $("#input_dcto_global").blur(function () {
                            calcularTotales();
                        });

                        $("#select_dcto_total").change(function () {
                            calcularTotales();
                        });

                        $("#total_otros_cargos").keyup(function () {
                            calcularTotales();
                        });

                        $("#total_otros_cargos").blur(function () {
                            calcularTotales();
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


                        $('.registrar_venta').click(function (event) {
                            event.preventDefault();
                            if ($('#idcliente').val() === "0" && $('#nuevocliente').val() === "") {
                                alertify.error("DEBE SELECCIONAR EL CLIENTE O INGRESAR NUEVO CLIENTE");
                                return;
                            }

                            if ($('#idcliente').val() === "0" && $('#nuevocliente').val() !== "" && $('#vendedor').val() === "0") {
                                alertify.error("DEBE SELECCIONAR UN VENDEDOR");
                                return;
                            }

                            var rowCount = $('#detalleVenta >tbody >tr').length;
                            if (rowCount === 0) {
                                alertify.error("NO HA INGRESADO NINGÚN PRODUCTO");
                                $('#producto').focus();
                                return;
                            }

                            var boolean = false;
                            $("#detalleVenta tbody tr").each(function () {
                                var nombre = $(this).find("td:eq(3)").html();
                                var disponible = $(this).find("td:eq(22)").html();
                                var cantidad = $(this).find("td:eq(4)").find(':input').val();
                                var unidad = $(this).find("td:eq(5)").html();
                                if (cantidad !== undefined && disponible !== undefined) {
                                    if (Number(cantidad) > Number(disponible)) {
                                        alertify.error("ERROR! SÓLO HAY " + disponible + " " + unidad + "(S) DE " + nombre);
                                        boolean = true;
                                    }
                                }
                            });

                            if (boolean) {
                                alertify.error("ERROR! VALIDAR LAS CANTIDADES DEL DETALLE");
                                return false;
                            }

                            var table = $('#detalleVenta tbody tr').map(function (idxRow, ele) {
                                // comienza construyendo el objeto retVal
                                var retVal = {};
                                // Por cada celda
                                var $td = $(ele).find('td').map(function (idxCell, ele) {
                                    var input = $(ele).find(':input');

                                    // Si la celda contiene un input o un select
                                    if (input.length === 1) {
                                        var attr = $('#detalleVenta thead tr th').eq(idxCell).text();
                                        if (attr === "Bonificación") {
                                            retVal[attr] = input.is(':checked') ? "S" : "N";
                                        } else {
                                            retVal[attr] = input.val();
                                        }
                                    } else {
                                        if (input.length === 2) {
                                            var attr = $('#detalleVenta thead tr th').eq(idxCell).text();
                                            retVal[attr] = $(ele).find('.monto_descuento').val() + " | " + $(ele).find('.select_tipo_dcto').val();
                                        } else {
                                            var attr = $('#detalleVenta thead tr th').eq(idxCell).text();
                                            retVal[attr] = $(ele).text();
                                        }
                                    }
                                });
                                return retVal;
                            }).get();

                            if ($('#estadopago').val() === "P") {
                                if ($('#estadopago').val() >= $('#total_venta').val()) {
                                    alertify.error("PARA UN PAGO PARCIAL EL MONTO A PAGAR: " + $('#montopagado').val() + " DEBE SER MENOR AL MONTO TOTAL DE LA VENTA: " + $('#total_venta').val());
                                    $('#montopagado').focus();
                                }
                            }

                            var flag_negociable = $('#switch-negociable').is(':checked') ? "S" : "N";

                            var tipo_dctoglobal = $("#select_dcto_total").val();
                            /*var monto_dctoglobal = 0;
                             var pcto_dctoglobal = 0;
                             
                             if (tipo_dctoglobal === "P")
                             pcto_dctoglobal = $("#input_dcto_global").val() || 0;
                             else
                             monto_dctoglobal = $("#input_dcto_global").val() || 0;*/

                            var flag_gravada = $('#switch-gravada').is(':checked') ? "S" : "N";

                            var total_anticipo = 0;

                            //CALCULO IMPORTE PAGO
                            var importe_pago = 0;
                            var monto_efectivo = 0;
                            var monto_otro = 0;
                            var referencia_otro = "";
                            var cambio_pago = 0;
                            $('.registrar_venta').prop('disabled', true);
                            $.ajax({
                                method: "POST",
                                url: "../Venta",
                                data: {"opcion": "insertar", "detalleventa": JSON.stringify(table), "idcliente": $('#idcliente').val(), "idtipoComprobante": 1, "idvendedor": $('#vendedor').val(),
                                    "idserie": $('#serie').val(), "flag_negociable": flag_negociable, "fecha_vencimiento": $("#fechavencimiento").val(), "idmoneda": $('#moneda').val(),
                                    "formapago": $('#formapago').val(), "estatuspago": $('#estadopago').val(), "montopagado": $('#montopagado').val(), "tipo_dctoglobal": tipo_dctoglobal, "pcto_dctoglobal": $('#dcto_global_pcto').val(),
                                    "monto_dctoglobal": $('#dcto_global_monto').val(), "flag_gravada": flag_gravada, "total_gravada": $('#total_gravadas').val(), "total_inafecta": $('#total_inafectas').val(),
                                    "total_exonerada": $('#total_exoneradas').val(), "total_gratuita": $('#total_gratuitas').val(), "total_impuesto": $('#total_impuestos').val(), "total_igv": $('#total_igv').val(),
                                    "total_isc": $('#total_isc').val(), "total_otrotributo": $('#total_otros_impuestos').val(), "total_valorventa": $('#total_valorventa').val(), "total_precioventa": $('#total_precioventa').val(),
                                    "total_descuento": $('#total_descuentos').val(), "total_otrocargo": $('#total_otros_cargos').val(), "total_anticipo": total_anticipo, "total_venta": $('#total_venta').val(),
                                    "importe_pago": importe_pago, "monto_efectivo": monto_efectivo, "monto_otro": monto_otro, "referencia_otro": referencia_otro, "cambio_pago": cambio_pago,
                                    "ruc": $('#ruc').val(), "nuevocliente": $('#nuevocliente').val(), "direccion": $('#direccion').val(), "ubigeocliente": $('#ubigeocliente').val(), "idcotizacion": '<%=idcotizacion%>'
                                }
                            }).done(function (data) {
                                var obj = jQuery.parseJSON(data);
                                if (obj.mensaje.indexOf('ERROR') !== -1) {
                                    $('.divError').html(obj.html);
                                    $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                        $('.divError').removeClass('tada animated');
                                    });
                                } else {
                                    alertify.success(obj.mensaje);
                                    $('#imprimir').attr('disabled', false);
                                    $('#imprimir').attr('href', '../Print?linkpdf=' + obj.linkpdf);
                                    window.open('../Print?linkpdf=' + obj.linkpdf, '_blank');
                                    //$('#imprimir').attr('href', '../ImprimirComprobante?tipo=FA&idventa=' + obj.idventa + '&total=' + $('#total_venta').val());
                                    //window.open('../ImprimirComprobante?tipo=FA&idventa=' + obj.idventa + '&total=' + $('#total_venta').val(), '_blank'); 
                                }
                            });

                        });

                        $('.limpiar').click(function () {
                            $('#formapago').val('E');
                            var idalmacen = <%=idalmacen%>;
                            if (idalmacen === 1) {
                                $('#estadopago').val('S');
                            } else {
                                $('#estadopago').val('T');
                            }
                            $('#cliente')
                                    .find('option:first-child').prop('selected', true)
                                    .end().trigger('chosen:updated');
                            $('#idcliente').val('0');
                            $('#producto')
                                    .find('option:first-child').prop('selected', true)
                                    .end().trigger('chosen:updated');
                            $('#ruc').val('');
                            $('#direccion').val('');
                            $('#vendedor').val('0');
                            $('#vendedor').attr('disabled', true);
                            $('#nuevocliente').val('');
                            $('#nuevocliente').addClass('hide');
                            $('#lblnuevocliente').addClass('hide');
                            $('#ubigeocliente').val('');
                            $('#dcto_global_pcto').val('0');
                            $('#dcto_global_monto').val('0');
                            $('#total_valorventa').val('0');
                            $('#total_precioventa').val('0');

                            $('#total_igv').val('0.00');
                            $('#total_impuestos').val('0.00');
                            $('#input_dcto_global').val('');
                            $('#select_dcto_global').prop('selectedIndex', 0);
                            $('#total_gravadas').val('0.00');
                            $('#total_inafectas').val('0.00');
                            $('#total_exoneradas').val('0.00');
                            $('#total_gratuitas').val('0.00');
                            $('#total_otros_cargos').val('');
                            $('#total_descuentos').val('0.00');
                            $('#total_venta').val('0.00');

                            $('#serie').prop('selectedIndex', 0);
                            cargarCorrelativo();
                            $('#moneda').prop('selectedIndex', 0);

                            $('#formapago').prop('selectedIndex', 0);
                            $('#lblnumeroletra').addClass('hide');
                            $('#numeroletra').addClass('hide');
                            $('#lblmontoletra').addClass('hide');
                            $('#montoletra').addClass('hide');
                            $('#fechavencimientoletra').addClass('hide');
                            $('#lblfechavencimientoletra').addClass('hide');
                            $('#iconfechavencimientoletra').addClass('hide');

                            //$('#estadopago').prop('selectedIndex', 0);
                            $('#lblmontopagado').addClass('hide');
                            $('#montopagado').addClass('hide');
                            $('#montopagado').val('');

                            $('#switch-negociable').prop('checked', false);
                            $('#fechavencimiento').addClass('hide');
                            $('#lblfechavencimiento').addClass('hide');
                            $('#iconfechavencimiento').addClass('hide');
                            $('#lbldepartamento').addClass('hide');
                            $('#departamento').addClass('hide');
                            $('#lblprovincia').addClass('hide');
                            $('#provincia').addClass('hide');
                            $('#lbldistrito').addClass('hide');
                            $('#distrito').addClass('hide');
                            $("#fechavencimiento").datepicker({
                                dateFormat: 'dd/mm/yy',
                                minDate: '+1d'
                            }).datepicker("setDate", new Date());
                            $("#fechavencimientoletra").datepicker({
                                dateFormat: 'dd/mm/yy',
                                minDate: '+1d'
                            }).datepicker("setDate", new Date());
                            $('#detalleVenta tbody').remove();
                            $('.registrar_venta').prop('disabled', false);
                            $('#imprimir').attr('disabled', true);
                            $("#imprimir").removeAttr('href');
                            $('#serie').focus();
                        });

                        $('#buscarsunat').click(function (event) {
                            var idtipodocumento = '2';
                            var numerodocumento = $('#ruc').val();
                            $.ajax({
                                method: "POST",
                                url: "../Cliente",
                                data: {"opcion": "buscarexistencia", "idtipodocumento": idtipodocumento, "numerodocumento": numerodocumento}
                            }).done(function (data) {
                                if (data !== 'nulo') {
                                    var obj = jQuery.parseJSON(data);
                                    $('#cliente').val(obj.idcliente);
                                    $('#cliente').trigger("chosen:updated");
                                    $('#idcliente').val(obj.idcliente);
                                    $('#ruc').val(obj.numerodocumento);
                                    $('#direccion').val(obj.direccion);
                                    $('#vendedor').val(obj.vendedor);
                                    $('#vendedor').attr('disabled', true);
                                    $('#nuevocliente').val('');
                                    $('#nuevocliente').addClass('hide');
                                    $('#lblnuevocliente').addClass('hide');
                                } else {
                                    $('#nuevocliente').val('');
                                    $('#ubigeocliente').val('');
                                    $('#direccion').val('');
                                    $('#cliente')
                                            .find('option:first-child').prop('selected', true)
                                            .end().trigger('chosen:updated');
                                    $('#vendedor').attr('disabled', false);
                                    $.ajax({
                                        method: "POST",
                                        url: "../Cliente",
                                        data: {"opcion": "buscarws", "idtipodocumento": idtipodocumento, "numerodocumento": numerodocumento}
                                    }).done(function (data) {
                                        var obj = jQuery.parseJSON(data);
                                        $('#nuevocliente').val(obj.razon_social);
                                        $('#nuevocliente').removeClass('hide');
                                        $('#lblnuevocliente').removeClass('hide');
                                        $('#direccion').val(obj.direccioncompleta);
                                        $('#ubigeocliente').val(obj.ubigeo);
                                    });
                                }
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
