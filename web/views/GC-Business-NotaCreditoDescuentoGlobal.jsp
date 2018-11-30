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
    String descripcionalmacen = (String) session.getAttribute("descripcionAlmacen");
    String idventa = request.getParameter("idventa");
    String idmotivonota = request.getParameter("idtiponota");
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
                            <li class="active">Nota Crédito Electónica</li>
                        </ul><!-- /.breadcrumb -->

                    </div>

                    <div class="page-content">

                        <div class="page-header">
                            <h1>
                                <%if (idmotivonota.equals("7")) {%>
                                Emitir nota crédito - DEVOLUCIÓN PARCIAL
                                <%} else if (idmotivonota.equals("6")) {%>
                                Emitir nota crédito - DEVOLUCIÓN TOTAL
                                <%} else if (idmotivonota.equals("1")) {%>
                                Emitir nota crédito - ANULACIÓN DE LA OPERACIÓN
                                <%} else if (idmotivonota.equals("4")) {%>
                                Emitir nota crédito - DESCUENTO GLOBAL
                                <%}%> 
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
                                                        List<BeanSerie> serie = daoSerie.listarSerieTipoComprobanteAlmacen("07", idalmacen);

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

                                            <div class="form-group hidden">
                                                <label for="motivonota" class="control-label" style="width: 110px;">Tipo de Nota:</label>
                                                <select id="motivonota" name="motivonota" class="styled-select tipo_comprobante" style="width: 320px;" tabindex="6">
                                                    <%
                                                        DaoMotivoNotaImpl daoMotivoNota = new DaoMotivoNotaImpl();
                                                        List<BeanMotivoNota> motivoNota = daoMotivoNota.listarMotivoNotaTipoComprobante("07");

                                                        for (int i = 0; i < motivoNota.size(); i++) {
                                                    %>
                                                    <option value="<%= motivoNota.get(i).getIdmotivonota()%>">
                                                        <%= motivoNota.get(i).getDescripcion()%>
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
                        <div class="row datos_descuento_global">
                            <div class="col-xs-12">
                                <input type="hidden" id="idcliente" value="0">
                                <div class="panel panel-primary">
                                    <div class="panel-heading"> Datos Descuento Global </div>
                                    <div class="panel-body">
                                        <div class="row col-md-12">
                                            <div class="form-group">
                                                <label id="lblsustento" for="sustento" class="control-label" style="width: 80px;">Motivo:</label>
                                                <input type="text" name="sustento" id="sustento" tabindex="16" style="width: 540px;">
                                            </div>
                                            <div class="form-group">
                                                <label id="lbldescuentoglobal" for="descuentoglobal" class="control-label" style="width: 80px;">Descuento Global:</label>
                                                <input type="text" name="descuentoglobal" id="descuentoglobal" tabindex="16" style="width: 122px;">
                                                <label id="lbltipoafecto" for="tipoafecto" class="control-label" style="width: 80px;">Tipo Afectación:</label>
                                                <select id="tipoafecto" name="tipoafecto" style="width: 122px;" tabindex="17">
                                                    <option value="G">GRAVADO</option>
                                                    <option value="E">EXONERADO</option>
                                                    <option value="I">INAFECTO</option>
                                                </select>
                                                <label id="lbligv" for="igv" class="control-label" style="width: 80px;">IGV:</label>
                                                <input type="text" name="igv" id="igv" tabindex="16" style="width: 122px;" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </div><!-- /.col -->
                            </div>
                        </div><!-- /.row -->
                        <div class="row">
                            <div class="col-md-3">
                            </div>
                            <div class="col-md-6 center" style="margin-top: 5px;">
                                <button class="btn btn-xs btn-primary registrar_venta" style="font-size: 1.2em;"> <span><i class="fa fa-save"></i></span> Registrar Nota</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a id="imprimir" class="btn btn-xs btn-primary imprimir" target="_blank" style="font-size: 1.2em;" disabled>
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
                            //alert('<%=idventa%>');
                            var idventa = '<%=idventa%>';
                            var idmotivonota = '<%=idmotivonota%>';
                            $('#motivonota').val(idmotivonota);
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

                        $("#descuentoglobal").keyup(function () {
                            var tipoafecto = $('#tipoafecto').val();
                            var descuentoglobal = $('#descuentoglobal').val();
                            var igv = 0;
                            if (tipoafecto === 'G') {
                                igv = descuentoglobal * 0.18;
                            }
                            $('#igv').val(parseFloat(igv).toFixed(2));
                            //$('#descuentoglobal').val(parseFloat(descuentoglobal).toFixed(2));
                        });

                        $("#descuentoglobal").blur(function () {
                            var tipoafecto = $('#tipoafecto').val();
                            var descuentoglobal = $('#descuentoglobal').val();
                            var igv = 0;
                            if (tipoafecto === 'G') {
                                igv = descuentoglobal * 0.18;
                            }
                            $('#igv').val(parseFloat(igv).toFixed(2));
                            $('#descuentoglobal').val(parseFloat(descuentoglobal).toFixed(2));
                        });

                        $("#tipoafecto").change(function () {
                            var tipoafecto = $('#tipoafecto').val();
                            var descuentoglobal = $('#descuentoglobal').val();
                            var igv = 0;
                            if (tipoafecto === 'G') {
                                igv = descuentoglobal * 0.18;
                            }
                            $('#igv').val(parseFloat(igv).toFixed(2));
                            $('#descuentoglobal').val(parseFloat(descuentoglobal).toFixed(2));
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

                            $('.registrar_venta').prop('disabled', true);
                            $.ajax({
                                method: "POST",
                                url: "../Nota",
                                data: {"opcion": "insertardescuentoglobal", "idsucursalmodifica": $('#idsucursalmodifica').val(), "idalmacenmodifica": $('#idalmacenmodifica').val(), "idcliente": $('#idcliente').val(), "idtipoComprobante": 3, "idvendedor": $('#vendedor').val(),
                                    "idserie": $('#serie').val(), "idmoneda": $('#moneda').val(), "idcomprobantemodifica": '<%=idventa%>', "idtipocomprobantemodifica": $('#idtipocomprobantemodifica').val(), "idseriemodifica": $('#idseriemodifica').val(), "correlativoseriemodifica": $('#correlativomodifica').val(),
                                    "idtiponota": $('#motivonota').val(), "motivo": $("#sustento").val(), "descuentoglobal": $("#descuentoglobal").val(), "tipoafecto": $("#tipoafecto").val(), "igv": $("#igv").val()
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
                                    //$('#imprimir').attr('href', '../ImprimirComprobante?tipo=NC&idnota=' + obj.idnota + '&total=' + $('#total_venta').val());
                                    //window.open('../ImprimirComprobante?tipo=NC&idnota=' + obj.idnota + '&total=' + $('#total_venta').val(), '_blank'); 
                                }
                            });

                        });

                        /*$('.limpiar').click(function () {
                         $('.registrar_venta').prop('disabled', false);
                         });*/
                    });
        </script>
    </body>
    <%
        } else {
            response.sendRedirect("../");
        }
    %>
</html>
