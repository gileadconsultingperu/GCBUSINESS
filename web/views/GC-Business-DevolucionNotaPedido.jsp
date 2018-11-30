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
<%@page import="gilead.gcbusiness.model.BeanMotivoNota"%>
<%@page import="gilead.gcbusiness.dao.impl.DaoMotivoNotaImpl"%>
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
    String idventa = request.getParameter("idventa");
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
                                <a href="GC-Business-GestionNota.jsp">Notas</a>
                            </li>
                            <li class="active">Devolución Nota Pedido</li>
                        </ul><!-- /.breadcrumb -->

                    </div>

                    <div class="page-content">

                        <div class="page-header">
                            <h1>
                                Devolución Nota Pedido
                            </h1>
                        </div><!-- /.page-header -->

                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row datos_comprobante">
                            <div class="col-xs-12">
                                <input type="hidden" id="idsucursalmodifica" value="">
                                <input type="hidden" id="idalmacenmodifica" value="">
                                <input type="hidden" id="idtipocomprobantemodifica" value="">
                                <input type="hidden" id="idseriemodifica" value="">
                                <input type="hidden" id="flaggravadamodifica" value="">
                                <div class="panel panel-primary">
                                    <div class="panel-heading"> Datos Comprobante </div>
                                    <div class="panel-body">
                                        <div class="row col-md-12">
                                            <div class="form-group">
                                                <label for="serie" class="control-label" style="width: 40px;">Serie:</label>
                                                <select id="serie" name="serie" class="styled-select tipo_comprobante" style="width: 70px;" tabindex="1" >
                                                    <%
                                                        DaoSerieImpl daoSerie = new DaoSerieImpl();
                                                        List<BeanSerie> serie = daoSerie.listarSerieTipoComprobanteAlmacen("99", idalmacen);

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
                                                <select id="moneda" name="moneda" class="styled-select tipo_comprobante" style="width: 175px;" tabindex="3" disabled>
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
                                            </div>

                                            <div class="form-group">
                                                <label for="comprobantemodifica" class="control-label" style="width: 110px;">Documento que modifica:</label>
                                                <input type="text" name="tipocomprobantemodifica" id="tipocomprobantemodifica" tabindex="2" style="width: 120px;" class="styled-select tipo_comprobante" disabled>
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <input type="text" name="seriemodifica" id="seriemodifica" tabindex="2" style="width: 120px;" class="styled-select tipo_comprobante" disabled>
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <input type="text" name="correlativomodifica" id="correlativomodifica" tabindex="2" style="width: 120px;" class="styled-select tipo_comprobante" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </div><!-- /.col -->
                            </div>
                        </div><!-- /.row -->
                        <div class="row datos_cliente">
                            <div class="col-xs-12">
                                <input type="hidden" id="idcliente" value="0">
                                <div class="panel panel-primary">
                                    <div class="panel-heading"> Datos Cliente </div>
                                    <div class="panel-body">
                                        <div class="row col-md-12">
                                            <div class="form-group">
                                                <label id="lblcliente" for="cliente" class="control-label" style="width: 80px;">Cliente:</label>
                                                <input type="text" name="cliente" id="cliente" tabindex="16" style="width: 540px;" disabled>
                                            </div>
                                            <div class="form-group">
                                                <label for="ruc" class="control-label" style="width: 80px;">RUC:</label>
                                                <input type="text" name="ruc" id="ruc" tabindex="15" style="width: 433px;" disabled>
                                            </div>
                                            <div class="form-group">
                                                <label for="direccion" class="control-label" style="width: 80px;">Dirección:</label>
                                                <input type="text" name="direccion" id="direccion" tabindex="16" style="width: 540px;" disabled>
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
                                            <div class="form-group hidden">
                                                <label for="producto" class="control-label hidden" style="width: 80px;">Producto:</label>
                                                <select class="chosen-select hidden" id="producto" tabindex="18" style="width: 400px;" data-placeholder="Seleccione producto...">
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
                                                <button class="btn btn-sm btn-primary hidden" id="btnAgregarDetalle" tabindex="21" title="Agregar Producto">
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
                                            <input type="text" name="total_igv" id="total_igv" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_isc" class="control-label" style="width: 80px;">Total ISC:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_isc" id="total_isc" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_otros_impuestos" class="control-label" style="width: 80px;">Total otros impuestos:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_otros_impuestos" id="total_otros_impuestos" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                    </div>
                                    <div class="panel-footer">
                                        <div class="form-group">
                                            <label for="total_impuestos" class="control-label" style="width: 80px;">Total:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_impuestos" id="total_impuestos" tabindex="-1" value="0.00" style="width: 200px;" disabled>
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
                                            <input id="input_dcto_global" size="17" type="text">
                                            <select id="select_dcto_total">
                                                <option value="P">%</option>
                                                <option value="M">Monto</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_gravadas" class="control-label" style="width: 180px;">Total gravadas:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_gravadas" id="total_gravadas" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_inafectas" class="control-label" style="width: 180px;">Total inafectas:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_inafectas" id="total_inafectas" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_exoneradas" class="control-label" style="width: 180px;">Total exoneradas:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_exoneradas" id="total_exoneradas" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_gratuitas" class="control-label" style="width: 180px;">Total gratuitas:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_gratuitas" id="total_gratuitas" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                        <div class="form-group">
                                            <label for="total_otros_cargos" class="control-label" style="width: 180px;">Total otros cargos:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_otros_cargos" id="total_otros_cargos" tabindex="-1" placeholder="0.00" style="width: 200px;">
                                        </div>
                                        <div class="form-group">
                                            <label for="total_descuentos" class="control-label" style="width: 180px;">Total descuentos:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_descuentos" id="total_descuentos" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>

                                        <div class="form-group" style="display:none;">
                                            <label for="total_anticipos" class="control-label" style="width: 180px;">Total anticipos:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_anticipos" id="total_anticipos" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                        <div class="form-group" style="display:none;">
                                            <label for="total_percepciones" class="control-label" style="width: 180px;">Total percepciones:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_percepciones" id="total_percepciones" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>

                                    </div>
                                    <div class="panel-footer">
                                        <div class="form-group">
                                            <label for="total_venta" class="control-label" style="width: 180px;">Importe total venta:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_venta" id="total_venta" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                        <div class="form-group mostrar_tipo_cambio" style="display:none;">
                                            <label for="total_venta_soles" class="control-label" style="width: 180px;">Importe total venta en soles:</label>
                                            <span class="label label-default" >
                                                <label class="lab_mon"> S/</label>
                                            </span>
                                            <input type="text" name="total_venta_soles" id="total_venta_soles" tabindex="-1" value="0.00" style="width: 200px;" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3">
                            </div>
                            <div class="col-md-6 center" style="margin-top: 5px;">
                                <button class="btn btn-xs btn-primary registrar_venta" style="font-size: 1.2em;"> <span><i class="fa fa-save"></i></span> Registrar Nota</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="#" id="imprimir" class="btn btn-xs btn-primary imprimir" target="_blank" style="font-size: 1.2em;">
                                    <span><i class="fa fa-print"></i></span> Imprimir
                                </a> 
                                <!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button class="btn btn-xs btn-primary limpiar" style="font-size: 1.2em;"> <span><i class="fa fa-trash"></i></span> Limpiar</button>-->
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
                            var idventa = '<%=idventa%>';
                            $.ajax({
                                url: "../Comprobante",
                                method: "GET",
                                data: {"opcion": "obtenerventa", "idventa": idventa},
                                success: function (data) {
                                    var obj = jQuery.parseJSON(data);
                                    $('#idsucursalmodifica').val(obj.idsucursal);
                                    $('#idalmacenmodifica').val(obj.idalmacen);
                                    $('#flaggravadamodifica').val(obj.flaggravada);
                                    $('#idtipocomprobantemodifica').val(obj.idtipocomprobante);
                                    $('#tipocomprobantemodifica').val(obj.tipocomprobante);
                                    $('#idseriemodifica').val(obj.idserie);
                                    $('#seriemodifica').val(obj.serie);
                                    $('#correlativomodifica').val(obj.correlativoserie);
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

                                    var idcliente = obj.idcliente;
                                    $.ajax({
                                        url: "../Cliente",
                                        method: "POST",
                                        data: {"opcion": "buscar", "idcliente": idcliente},
                                        success: function (data) {
                                            var obj = jQuery.parseJSON(data);
                                            $('#idcliente').val(obj.idcliente);
                                            $('#cliente').val(obj.nombre);
                                            $('#ruc').val(obj.numerodocumento);
                                            $('#direccion').val(obj.direccion);
                                            $('#vendedor').val(obj.vendedor);
                                        },
                                        error: function (error) {
                                            alertify.error("ERROR AL EJECUTAR AJAX DE OBTENER DATOS USUARIO");
                                        }
                                    }).done();

                                    //Obtener detalle
                                    $.get('../Comprobante', {
                                        "opcion": "obtenerdetalleventa",
                                        "idventa": idventa,
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

                            cargarCorrelativo();
                        });

                        var cargarCorrelativo = function () {
                            var idSerie = $('#serie').val();
                            $.get('../Serie', {
                                idSerie: idSerie
                            }, function (response) {
                                $('#correlativo').val(response);
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
                                alertify.error("Alerta! El stock debe ser menor o igual a " + stockActual);
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
                                    $this.next().css({'width': $this.parent().width()});
                                });
                            });
                        }


                        $('.registrar_venta').click(function (event) {
                            event.preventDefault();

                            var rowCount = $('#detalleVenta >tbody >tr').length;
                            if (rowCount === 0) {
                                alertify.error("NO HA INGRESADO NINGÚN PRODUCTO");
                                $('#producto').focus();
                                return;
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

                            var tipo_dctoglobal = $("#select_dcto_total").val();

                            var flag_gravada = $('#flaggravadamodifica').val();

                            $('.registrar_venta').prop('disabled', true);
                            $.ajax({
                                method: "POST",
                                url: "../Nota",
                                data: {"opcion": "insertar", "idsucursalmodifica": $('#idsucursalmodifica').val(), "idalmacenmodifica": $('#idalmacenmodifica').val(), "detalleventa": JSON.stringify(table), "idcliente": $('#idcliente').val(), "idtipoComprobante": 8, "idvendedor": $('#vendedor').val(),
                                    "idserie": $('#serie').val(), "idmoneda": $('#moneda').val(), "idcomprobantemodifica": '<%=idventa%>', "idtipocomprobantemodifica": $('#idtipocomprobantemodifica').val(), "idseriemodifica": $('#idseriemodifica').val(), "correlativoseriemodifica": $('#correlativomodifica').val(),
                                    "idtiponota": 12, "motivo": "DEVOLUCION", "tipo_dctoglobal": tipo_dctoglobal, "pcto_dctoglobal": $('#dcto_global_pcto').val(),
                                    "monto_dctoglobal": $('#dcto_global_monto').val(), "flag_gravada": flag_gravada, "total_gravada": $('#total_gravadas').val(), "total_inafecta": $('#total_inafectas').val(),
                                    "total_exonerada": $('#total_exoneradas').val(), "total_gratuita": $('#total_gratuitas').val(), "total_impuesto": $('#total_impuestos').val(), "total_igv": $('#total_igv').val(),
                                    "total_isc": $('#total_isc').val(), "total_otrotributo": $('#total_otros_impuestos').val(), "total_valorventa": $('#total_valorventa').val(), "total_precioventa": $('#total_precioventa').val(),
                                    "total_descuento": $('#total_descuentos').val(), "total_otrocargo": $('#total_otros_cargos').val(), "total_venta": $('#total_venta').val()
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
                                    $('#imprimir').attr('href', '../ImprimirComprobante?tipo=DE&idnota=' + obj.idnota + '&total=' + $('#total_venta').val());
                                    window.open('../ImprimirComprobante?tipo=DE&idnota=' + obj.idnota + '&total=' + $('#total_venta').val(), '_blank');
                                }
                            });

                        });

                        /*$('.limpiar').click(function () {
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
                         $('#select_dcto_total').prop('selectedIndex', 0);
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
                         
                         $('#estadopago').prop('selectedIndex', 0);
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
                         });*/

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
