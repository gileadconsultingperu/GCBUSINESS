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
        <title>GC BUSINESS - Gestión de Productos</title>

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
                        if (opciones.contains(11)) {
                    %>
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-newspaper-o"></i>
                            <span class="menu-text">Ventas </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(12)) {
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
                                if (opciones.contains(13)) {
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
                                if (opciones.contains(14)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionCotizacion.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Cotización
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }
                                if (opciones.contains(15)) {
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
                        if (opciones.contains(31)) {
                    %>                
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-ban"></i>
                            <span class="menu-text"> Clientes </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(32)) {
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
                                if (opciones.contains(33)) {
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
                                if (opciones.contains(34)) {
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
                            <i class="menu-icon fa fa-ban"></i>
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
                            %>
                        </ul>
                    </li>
                    <%
                        }
                        if (opciones.contains(41)) {
                    %>                                       
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-ban"></i>
                            <span class="menu-text"> Proveedores </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(42)) {
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
                                if (opciones.contains(43)) {
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
                        if (opciones.contains(51)) {
                    %>                                    
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text"> Inventario </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(52)) {
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
                                if (opciones.contains(53)) {
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
                        if (opciones.contains(1)) {
                    %>                    
                    <li class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-tachometer"></i>
                            <span class="menu-text"> Productos </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>
                        <b class="arrow"></b>

                        <ul class="submenu">
                            <%
                                if (opciones.contains(10)) {
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
                                if (opciones.contains(2)) {
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
                                if (opciones.contains(3)) {
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
                                if (opciones.contains(4)) {
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
                                if (opciones.contains(5)) {
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
                                if (opciones.contains(6)) {
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
                                if (opciones.contains(7)) {
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
                                if (opciones.contains(67)) {
                            %>
                            <li class="">
                                <a href="GC-Business-GestionAcceso.jsp">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Accesos
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
                                    Cambiar Contraseña
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
                                <a href="#">Productos</a>
                            </li>
                            <li class="active">Gestión Productos</li>
                        </ul><!-- /.breadcrumb -->

                    </div>

                    <div class="page-content">

                        <div class="page-header">
                            <h1>
                                Gestión Productos
                                <small>
                                    <i class="ace-icon fa fa-angle-double-right"></i>
                                    Crear, modificar, eliminar productos
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
                                    <table id="tablaProductos" class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>Código</th>
                                                <th>Descripción</th>   
                                                <th>Marca</th>
                                                <th>Categoría</th>
                                                <th>Unidad Medida</th>   
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
            <div class="modal" id="modalAgregarProducto" tabindex="-1">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="blue bigger">Registrar Nuevo Producto</h4>
                        </div>

                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12">
                                    <input type="hidden" id="idproducto" value="">
                                    <input type="hidden" id="opcion" value="">
                                    <input type="hidden" id="flagPrecioVenta" value="">

                                    <div class="form-group">
                                        <label for="codigo" class="col-sm-1 control-label">Código</label>
                                        <div class="col-sm-3">
                                            <input type="text" id="codigo" class="form-control"  style="text-transform:uppercase" tabindex="1"/>
                                        </div>
                                        <label for="descripcion" class="col-sm-2 control-label">Descripción</label>
                                        <div class="col-sm-6">
                                            <input type="text" id="descripcion" class="form-control"  style="text-transform:uppercase" tabindex="2"/>
                                        </div>
                                    </div>
                                    <!--                  
                                    &nbsp;&nbsp;
                                    
                                    <div class="form-group">
                                        <div class="col-sm-5">
                                            <label for="codigo" class="col-sm-1 control-label">Código</label>
                                        
                                            <input type="text" id="codigo" class="form-control"  style="text-transform:uppercase" tabindex="1"/>
                                        </div>
                                        <div class="col-sm-7">
                                            <label for="descripcion" class="col-sm-2 control-label">Descripción</label>
                                            <input type="text" id="descripcion" class="form-control"  style="text-transform:uppercase" tabindex="2"/>
                                        </div>
                                    </div>                                
                                    -->                  
                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="familiaproducto" class="col-sm-1 control-label">Familia</label>
                                        <div class="col-sm-3">
                                            <select id="familiaproducto" name="familiaproducto" class="col-xs-12 col-sm-12" tabindex="3">
                                                <%
                                                    DaoFamiliaProductoImpl daoFamiliaProducto = new DaoFamiliaProductoImpl();
                                                    List<BeanFamiliaProducto> familiaproducto = daoFamiliaProducto.accionListar();

                                                    for (int i = 0; i < familiaproducto.size(); i++) {
                                                        //if(familiaproducto.get(i).getEstado().equals("I")){        @01 FAMILIAS INACTIVAS
                                                        //  familiaproductoesInactivas.add(familiaproducto.get(i).getIdfamiliaproducto());
                                                        //}    
                                                %>
                                                <option value="<%= familiaproducto.get(i).getIdfamiliaproducto()%>">
                                                    <%= familiaproducto.get(i).getDescripcion()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <label for="claseproducto" class="col-sm-1 control-label">Clase</label>
                                        <div class="col-sm-3">
                                            <select id="claseproducto" name="claseproducto" class="col-xs-12 col-sm-12" tabindex="4">                                               
                                            </select>   
                                        </div> 
                                        <label for="lineaproducto" class="col-sm-1 control-label">Línea</label>
                                        <div class="col-sm-3">
                                            <select id="lineaproducto" name="lineaproducto" class="col-xs-12 col-sm-12" tabindex="5">                                               
                                            </select>   
                                        </div>                                        
                                    </div>

                                    &nbsp;&nbsp;                                  

                                    <div class="form-group">
                                        <label for="categoriaproducto" class="col-sm-1 control-label">Categoría</label>
                                        <div class="col-sm-3">
                                            <select id="categoriaproducto" name="categoriaproducto" class="col-xs-12 col-sm-12" tabindex="6">                                               
                                            </select>   
                                        </div>
                                        <label for="marca" class="col-sm-1 control-label">Marca</label>
                                        <div class="col-sm-3">
                                            <select id="marca" name="marca" class="col-xs-12 col-sm-12" tabindex="7">
                                                <%
                                                    DaoMarcaImpl daoMarca = new DaoMarcaImpl();
                                                    List<BeanMarca> marca = daoMarca.accionListar();

                                                    for (int i = 0; i < marca.size(); i++) {
                                                %>
                                                <option value="<%= marca.get(i).getIdmarca()%>">
                                                    <%= marca.get(i).getDescripcion()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>   
                                        </div>
                                        <label for="unidadmedida" class="col-sm-1 control-label">Unidad Medida</label>
                                        <div class="col-sm-3">
                                            <select id="unidadmedida" name="unidadmedida" class="col-xs-12 col-sm-12" tabindex="8">
                                                <%
                                                    DaoUnidadMedidaImpl daoUnidadMedida = new DaoUnidadMedidaImpl();
                                                    List<BeanUnidadMedida> unidadmedida = daoUnidadMedida.accionListar();

                                                    for (int i = 0; i < unidadmedida.size(); i++) {
                                                %>
                                                <option value="<%= unidadmedida.get(i).getIdunidadmedida()%>">
                                                    <%= unidadmedida.get(i).getAbreviatura()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>   
                                        </div>    
                                    </div> 

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="moneda" class="col-sm-1 control-label">Moneda</label>
                                        <div class="col-sm-3">
                                            <select id="moneda" name="moneda" class="col-xs-12 col-sm-12" tabindex="9">
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
                                        <label for="preciocompra" class="col-sm-1 control-label">Precio Compra</label>
                                        <div class="col-sm-3">
                                            <input type="text" id="preciocompra" class="form-control" style="text-transform:uppercase" tabindex="10"/>
                                        </div>   

                                        <div class="col-sm-4">
                                            <button class="btn btn-sm btn-primary" id="btnPrecioVenta" tabindex="11">
                                                <i class="ace-icon fa fa-bar-chart-o"></i>
                                                Precio Venta
                                            </button>
                                        </div>         
                                    </div>                                  

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="tipoproducto" class="col-sm-1 control-label">Tipo Producto</label>
                                        <div class="col-sm-3">
                                            <select id="tipoproducto" name="tipoproducto" class="col-xs-12 col-sm-12" tabindex="11">
                                                <%
                                                    DaoTipoProductoImpl daoTipoProducto = new DaoTipoProductoImpl();
                                                    List<BeanTipoProducto> tipoproducto = daoTipoProducto.accionListar();

                                                    for (int i = 0; i < tipoproducto.size(); i++) {
                                                %>
                                                <option value="<%= tipoproducto.get(i).getIdtipoproducto()%>">
                                                    <%= tipoproducto.get(i).getAbreviatura()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>   
                                        </div>
                                        <label for="codigosunat" class="col-sm-1 control-label">Código SUNAT</label>
                                        <div class="col-sm-3">
                                            <input type="text" id="codigosunat" class="form-control"  style="text-transform:uppercase" tabindex="12"/>
                                        </div>                                      
                                        <div class="col-sm-4">
                                            <button class="btn btn-sm btn-primary" id="btnAdicionales" tabindex="13">
                                                <i class="ace-icon fa fa-bookmark"></i>
                                                Adicionales
                                            </button>
                                        </div> 
                                    </div>  

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <label for="igv" class="col-sm-1 control-label">IGV</label>
                                        <div class="col-sm-2">
                                            <select id="igv" name="igv" class="col-xs-12 col-sm-12" tabindex="14">
                                                <option value="G">GRAVADO</option>
                                                <option value="E">EXONERADO</option>
                                                <option value="I">INAFECTO</option>
                                            </select>   
                                        </div>
                                        <label class="col-sm-1 middle">          
                                            <input class="ace" type="checkbox" id="isc" tabindex="15"/>
                                            <span class="lbl">  ISC?</span>                                          
                                        </label>  
                                        <label for="tipoisc" class="col-sm-1 control-label">Tipo ISC</label>
                                        <div class="col-sm-3">
                                            <select id="tipoisc" name="tipoisc" class="col-xs-12 col-sm-12" tabindex="16">
                                                <%
                                                    DaoTipoISCImpl daoTipoISC = new DaoTipoISCImpl();
                                                    List<BeanTipoISC> tipoisc = daoTipoISC.accionListar();

                                                    for (int i = 0; i < tipoisc.size(); i++) {
                                                %>
                                                <option value="<%= tipoisc.get(i).getIdtipoisc()%>">
                                                    <%= tipoisc.get(i).getAbreviatura()%> 
                                                </option>
                                                <%

                                                    }
                                                %>
                                            </select>   
                                        </div>
                                        <label for="baseisc" class="col-sm-1 control-label">Base ISC</label>
                                        <div class="col-sm-1">
                                            <input type="text" id="baseisc" class="form-control" style="text-transform:uppercase" tabindex="17"/>
                                        </div> 
                                        <label for="factorisc" class="col-sm-1 control-label">Factor ISC</label>
                                        <div class="col-sm-1">
                                            <input type="text" id="factorisc" class="form-control" style="text-transform:uppercase" tabindex="18"/>
                                        </div> 
                                    </div>

                                    &nbsp;&nbsp;

                                    <div class="form-group">
                                        <div id="divflaglote">
                                            <label class="col-sm-3 middle">          
                                                <input class="ace" type="checkbox" id="flaglote" tabindex="19"/>
                                                <span class="lbl"> Agrupar por Lote</span>                                          
                                            </label>
                                        </div>
                                        <div class="col-sm-4">
                                            <span class="profile-picture" style="display: none" tabindex="20">
                                                <img class="editable img-responsive" alt="Imagen Producto" id="avatar2" src="assets/images/avatars/profile-pic.jpg" />
                                            </span>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;

                                    <div class="form-group" id="divestado">
                                        <label for="estado" class="col-sm-4 control-label">Estado</label>
                                        <div class="col-sm-8">  
                                            <select id="estado" name="estado" class="form-control" tabindex="21">
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
                                <i class="ace-icon fa fa-check"></i>
                                Grabar
                            </button>
                        </div>

                    </div>
                </div>
            </div>

            <div class="modal" id="modalPrecioVenta" tabindex="-1">
                <div class="modal-dialog">                                            
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="blue bigger">Precio de Venta por Tarifa</h4>
                        </div>
                        <div class="modal-body">                                  
                            <div class="row">                
                                <div id="divPrecioVenta" class="col-md-8">        			
                                    <div class="table-responsive">
                                        <table id="tablaPrecioVenta" class="table table-bordered table-striped table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Tarifa</th>
                                                    <th>Precio</th>
                                                </tr>
                                            </thead>
                                            <tbody id="precioventa_data">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>        		
                            </div>
                        </div>
                        <div class="modal-footer">               
                            <button class="btn btn-sm" data-dismiss="modal">
                                <i class="ace-icon fa fa-times"></i>
                                Cancelar
                            </button>

                            <button class="btn btn-sm btn-primary" id="btnGuardarPrecioVenta">
                                <i class="ace-icon fa fa-check"></i>
                                Grabar
                            </button>
                        </div>
                    </div>                 
                </div>
            </div>

            <div class="modal" id="modalAdicionales" tabindex="-1">
                <div class="modal-dialog">                                            
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="blue bigger">Datos Adicionales</h4>
                        </div>
                        <div class="modal-body">                                  
                            <div class="row">                
                                <div class="col-xs-12 col-sm-12">      			
                                    <div class="form-group">
                                        <label for="codigoproveedor" class="col-sm-4 control-label">Código Proveedor</label>
                                        <div class="col-sm-8">
                                            <input type="text" id="codigoproveedor" class="form-control"  style="text-transform:uppercase" tabindex="1"/>
                                        </div>
                                    </div> 

                                    &nbsp;&nbsp;

                                    <div class="form-group">    
                                        <label for="pesoproveedor" class="col-sm-4 control-label">Peso Proveedor</label>
                                        <div class="col-sm-8">
                                            <input type="text" id="pesoproveedor" class="form-control"  style="text-transform:uppercase" tabindex="2"/>
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

                            <button class="btn btn-sm btn-primary" id="btnGuardarPrecioVenta">
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

                        $('body').on('shown.bs.modal', '#modalAgregarProducto', function () {
                            $('input:visible:enabled:first', this).focus();
                        });

                        $('body').on('shown.bs.modal', '#modalAdicionales', function () {
                            $('input:visible:enabled:first', this).focus();
                        });

                        var tablaProductos = $('#tablaProductos').DataTable({
                            bAutoWidth: false,
                            "processing": true,
                            "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "Todos"]],
                            "iDisplayLength": -1,
                            destroy: true,
                            responsive: true,
                            "searching": true,
                            "order": [[1, 'asc']],
                            ajax: {
                                method: "POST",
                                url: "../Producto",
                                data: {"opcion": "listar"},
                                dataSrc: "data"
                            },
                            columns: [
                                {"data": "codigo"},
                                {"data": "descripcion"},
                                {"data": "marca"},
                                {"data": "categoriaproducto"},
                                {"data": "unidadmedida"},
                                {"data": "estado"},
                                {"data": "acciones"}
                            ],
                            dom: '<"row"<"col-xs-12 col-sm-4 col-md-4"l><"col-xs-12 col-sm-4 col-md-4"B><"col-xs-12 col-sm-4 col-md-4"f>>' +
                                    'tr<"row"<"col-sm-12 col-md-6"i><"col-sm-12 col-md-6"p>> ',
                            'columnDefs': [
                                {
                                    'targets': [0, 1, 2, 3, 4, 5],
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
                        new $.fn.dataTable.Buttons(tablaProductos, {
                            buttons: [
                                {
                                    "text": "<i class='fa fa-plus bigger-110 blue'></i>",
                                    "titleAttr": "Nuevo",
                                    "className": "btn btn-white btn-primary btn-bold",
                                    "action": function () {
                                        $('#opcion').val('insertar');
                                        $('#codigo').val('');
                                        $('#codigosunat').val('');
                                        $('#descripcion').val('');
                                        $('#marca').val('1');
                                        $('#unidadmedida').val('1');
                                        $('#moneda').val('1');
                                        $('#preciocompra').val('0');
                                        $('#tipoproducto').val('1');
                                        //$('#tipoisc').val('1');
                                        $('#baseisc').val('0');
                                        $('#factorisc').val('0');
                                        //$('#igv').prop('checked', false);
                                        $('#igv').val('E');
                                        $('#isc').prop('checked', false);
                                        $('#flaglote').prop('checked', false);
                                        $('#divflaglote').show();

                                        preciosVenta = [];
                                        $('.tarifa').remove();

                                        $('#codigoproveedor').val('');
                                        $('#pesoproveedor').val('0');
                                        $('#estado').val('A');
                                        $('#divestado').hide();

                                        var familia = $("#familiaproducto").val();



                                        $.get('../ClaseProducto', {
                                            "familiaproducto": familia
                                        }, function (response) {
                                            $('#claseproducto').html(response);

                                            var clase = $("#claseproducto").val();

                                            $.get('../LineaProducto', {
                                                "claseproducto": clase
                                            }, function (response) {
                                                $('#lineaproducto').html(response);

                                                var linea = $("#lineaproducto").val();

                                                $.get('../CategoriaProducto', {
                                                    "lineaproducto": linea
                                                }, function (response) {
                                                    $('#categoriaproducto').html(response);
                                                });
                                            });
                                        });

                                        $('#modalAgregarProducto .blue').text('Registrar Nuevo Producto');
                                        $('#modalAgregarProducto').modal('show');
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

                        tablaProductos.buttons().container().appendTo($('.tableTools-container'));

                        $('#btnGuardar').click(function (event) {
                            var idproducto = $('#idproducto').val();
                            var codigo = $('#codigo').val();
                            var descripcion = $('#descripcion').val();
                            //var idfamiliaproducto = $('#familiaproducto').val();
                            //var idclaseproducto = $('#claseproducto').val();
                            //var idlineaproducto= $('#lineaproducto').val();
                            var afectoigv = $('#igv').val();
                            var afectoisc = "N";
                            if ($('#isc').is(':checked')) {
                                afectoisc = "S";
                            }
                            var idcategoriaproducto = $('#categoriaproducto').val();
                            var idmarca = $('#marca').val();
                            var idunidadmedida = $('#unidadmedida').val();
                            var idmoneda = $('#moneda').val();
                            var idtipoproducto = $('#tipoproducto').val();
                            var preciocompra = $('#preciocompra').val();
                            var idtipoisc = $('#tipoisc').val();
                            var baseisc = $('#baseisc').val();
                            var factorisc = $('#factorisc').val();
                            var codigoproveedor = $('#codigoproveedor').val();
                            var pesoproveedor = $('#pesoproveedor').val();

                            var flaglote = "N";
                            if ($("#flaglote").is(':checked')) {
                                flaglote = "S";
                            }

                            var estado = $('#estado').val();

                            var codigosunat = $('#codigosunat').val();

                            var opcion = $('#opcion').val();
            <%--
            if(preciosVenta.length===0){                   
                $.getJSON("../Tarifa", {idproducto: 0}, function (data, textStatus, jqXHR) {
                    $.each(data, function (index, item) {//recorremos la lista
                        //alertify.error("entro: "+item.descripcion);
                        var valortarifa = item.valor;
                        var idtarifa = item.idtarifa;
                        var descritarifa = item.descripcion;
                        preciosVenta.push({idtarifa:idtarifa, descripcion:descritarifa, valor:valortarifa}); //NO FUNCIONA ???
                    });          
                });      
            }
            --%>
                            var precios = "";
                            if (preciosVenta.length > 0) {
                                $.each(preciosVenta, function (indice, id) {
                                    precios += preciosVenta[indice].idtarifa + ";" + preciosVenta[indice].valor;
                                    precios += "-/-";
                                });
                            }
                            //alert("precios: "+preciosVenta.length);
                            //return;
                            $.ajax({
                                method: "POST",
                                url: "../Producto",
                                data: {"opcion": opcion, "idmarca": idmarca, "idproducto": idproducto, "descripcion": descripcion, "codigo": codigo, "afectoigv": afectoigv, "afectoisc": afectoisc,
                                    "idcategoriaproducto": idcategoriaproducto, "idunidadmedida": idunidadmedida, "idmoneda": idmoneda, "idtipoproducto": idtipoproducto, "preciocompra": preciocompra,
                                    "idtipoisc": idtipoisc, "baseisc": baseisc, "factorisc": factorisc, "flaglote": flaglote, "codigoproveedor": codigoproveedor, "pesoproveedor": pesoproveedor,
                                    "preciosVenta": precios, "estado": estado, "codigosunat": codigosunat}
                            }).done(function (data) {
                                var obj = jQuery.parseJSON(data);
                                if (obj.mensaje.indexOf('ERROR') !== -1) {
                                    $('.divError').html(obj.html);
                                    $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                        $('.divError').removeClass('tada animated');
                                    });
                                } else {
                                    tablaProductos.ajax.reload();
                                    alertify.success(obj.mensaje);
                                }
                                $('#modalAgregarProducto').modal('hide');
                            });
                        });

                        //Actualizar registro
                        $(document).on('click', '.actualizar', function () {
                            var idproducto = $(this).attr('id');
                            var row = $(this).parent().parent();
                            $.ajax({
                                url: "../Producto",
                                method: "POST",
                                data: {"opcion": "buscar", "idproducto": idproducto},
                                success: function (data) {
                                    var obj = jQuery.parseJSON(data);
                                    $('#opcion').val('actualizar');
                                    $('#idproducto').val(obj.idproducto);

                                    $('#codigo').val(obj.codigo);
                                    $('#descripcion').val(obj.descripcion);

                                    /*if (obj.afectoigv === "S") {
                                     $('#igv').prop('checked', true);
                                     }*/
                                    $('#igv').val(obj.afectoigv);
                                    if (obj.afectoisc === 'S') {
                                        $('#isc').prop('checked', true);
                                        $('label[for="tipoisc"]').show();
                                        $('#tipoisc').show();
                                        $('label[for="baseisc"]').show();
                                        $('#baseisc').show();
                                        $('label[for="factorisc"]').show();
                                        $('#factorisc').show();
                                    }

                                    preciosVenta = [];
                                    $.getJSON("../Tarifa", {idproducto: obj.idproducto}, function (data, textStatus, jqXHR) {
                                        $('.tarifa').remove();
                                        $.each(data, function (index, item) {//recorremos la lista
                                            //alertify.error("entro: "+item.descripcion);
                                            var valortarifa = item.valor;
                                            var idtarifa = item.idtarifa;
                                            var descritarifa = item.descripcion;
                                            preciosVenta.push({idtarifa: idtarifa, descripcion: descritarifa, valor: valortarifa}); //NO FUNCIONA ???
                                        });
                                    });

                                    $('#marca').val(obj.marca);
                                    $('#unidadmedida').val(obj.unidadmedida);
                                    $('#moneda').val(obj.moneda);
                                    $('#tipoproducto').val(obj.tipoproducto);
                                    $('#preciocompra').val(obj.preciocompra);
                                    $('#tipoisc').val(obj.tipoisc);
                                    $('#baseisc').val(obj.baseisc);
                                    $('#factorisc').val(obj.factorisc);
                                    $('#codigoproveedor').val(obj.codigoproveedor);
                                    $('#pesoproveedor').val(obj.pesoproveedor);
                                    $('#codigosunat').val(obj.codigosunat);

                                    if (obj.flaglote === "S") {
                                        $('#flaglote').prop('checked', true);
                                    }
                                    $('#divflaglote').hide();

                                    $('#familiaproducto').val(obj.familiaproducto);

                                    $.get('../ClaseProducto', {
                                        "familiaproducto": obj.familiaproducto
                                    }, function (response) {
                                        $('#claseproducto').html(response);

                                        $('#claseproducto').val(obj.claseproducto);

                                        $.get('../LineaProducto', {
                                            "claseproducto": obj.claseproducto
                                        }, function (response) {
                                            $('#lineaproducto').html(response);

                                            $('#lineaproducto').val(obj.lineaproducto);

                                            $.get('../CategoriaProducto', {
                                                "lineaproducto": obj.lineaproducto
                                            }, function (response) {
                                                $('#categoriaproducto').html(response);
                                                $('#categoriaproducto').val(obj.categoriaproducto);
                                            });
                                        });
                                    });

                                    $('#estado').val(obj.estado);
                                    $('#divestado').hide();
                                    $('#modalAgregarProducto .blue').text('Modificar Producto');
                                    $('#modalAgregarProducto').modal('show');
                                },
                                error: function (error) {
                                    alertify.error("ERROR AL EJECUTAR AJAX DE OBTENER DATOS USUARIO");
                                }
                            }).done();

                        });

                        //Eliminar registro
                        $(document).on('click', '.eliminar', function () {
                            var idproducto = $(this).attr('id');
                            var row = $(this).parent().parent();
                            $.ajax({
                                url: "../Producto",
                                method: "POST",
                                data: {"opcion": "eliminar", "idproducto": idproducto},
                                success: function (data) {
                                    var obj = jQuery.parseJSON(data);
                                    if (obj.mensaje.indexOf('ERROR') !== -1) {
                                        $('.divError').html(obj.html);
                                        $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                            $('.divError').removeClass('tada animated');
                                        });
                                    } else {
                                        tablaProductos.ajax.reload();
                                        alertify.success(obj.mensaje);
                                    }
                                },
                                error: function (error) {
                                    alertify.error("ERROR AL EJECUTAR AJAX DE INHABILITAR");
                                }
                            }).done();
                        });

                        //Activar producto
                        $(document).on('click', '.activar', function () {
                            var idproducto = $(this).attr('id');
                            var row = $(this).parent().parent();
                            $.ajax({
                                url: "../Producto",
                                method: "POST",
                                data: {"opcion": "activar", "idproducto": idproducto},
                                success: function (data) {
                                    var obj = jQuery.parseJSON(data);
                                    if (obj.mensaje.indexOf('ERROR') !== -1) {
                                        $('.divError').html(obj.html);
                                        $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                            $('.divError').removeClass('tada animated');
                                        });
                                    } else {
                                        tablaProductos.ajax.reload();
                                        alertify.success(obj.mensaje);
                                    }
                                },
                                error: function (error) {
                                    alertify.error("ERROR AL EJECUTAR AJAX DE INHABILITAR");
                                }
                            }).done();
                        });

                        $('#familiaproducto').change(function () {
                            var familia = $("#familiaproducto").val();

                            $.get('../ClaseProducto', {
                                "familiaproducto": familia
                            }, function (response) {
                                $('#claseproducto').html(response);

                                var clase = $("#claseproducto").val();

                                $.get('../LineaProducto', {
                                    "claseproducto": clase
                                }, function (response) {
                                    $('#lineaproducto').html(response);

                                    var linea = $("#lineaproducto").val();

                                    $.get('../CategoriaProducto', {
                                        "lineaproducto": linea
                                    }, function (response) {
                                        $('#categoriaproducto').html(response);
                                    });
                                });
                            });
                        });

                        $('#claseproducto').change(function () {
                            var clase = $("#claseproducto").val();

                            $.get('../LineaProducto', {
                                "claseproducto": clase
                            }, function (response) {
                                $('#lineaproducto').html(response);

                                var linea = $("#lineaproducto").val();

                                $.get('../CategoriaProducto', {
                                    "lineaproducto": linea
                                }, function (response) {
                                    $('#categoriaproducto').html(response);
                                });
                            });
                        });

                        $('#lineaproducto').change(function () {
                            var linea = $("#lineaproducto").val();

                            $.get('../CategoriaProducto', {
                                "lineaproducto": linea
                            }, function (response) {
                                $('#categoriaproducto').html(response);
                            });
                        });

                        $('label[for="tipoisc"]').hide();
                        $('#tipoisc').hide();
                        $('label[for="baseisc"]').hide();
                        $('#baseisc').hide();
                        $('label[for="factorisc"]').hide();
                        $('#factorisc').hide();

                        $('#isc').on('click', function () {
                            if ($(this).is(':checked')) {
                                $('label[for="tipoisc"]').show();
                                $('#tipoisc').show();
                                $('label[for="baseisc"]').show();
                                $('#baseisc').show();
                                $('label[for="factorisc"]').show();
                                $('#factorisc').show();
                            } else {
                                $('label[for="tipoisc"]').hide();
                                $('#tipoisc').hide();
                                $('label[for="baseisc"]').hide();
                                $('#baseisc').hide();
                                $('label[for="factorisc"]').hide();
                                $('#factorisc').hide();
                            }
                        });

                        var preciosVenta = [];

                        $("#btnPrecioVenta").click(function () {
                            $('#flagPrecioVenta').val('S');
                            $('#modalPrecioVenta').modal({backdrop: 'static', keyboard: false});
                            if (preciosVenta.length > 0) {
                                $('.tarifa').remove();
                                var nuevaFila = "";
                                $.each(preciosVenta, function (indice, id) {
                                    nuevaFila += "<tr class=\"tarifa\">";
                                    nuevaFila += "<td>" + preciosVenta[indice].descripcion + "</td>";
                                    nuevaFila += "<td><input class=\"valor\" type=\"text\" name=\"valorTarifa\" id=" + preciosVenta[indice].idtarifa + " value=" + preciosVenta[indice].valor + " size=\"6\"></td>";
                                    nuevaFila += "</tr>";
                                });
                                $("#tablaPrecioVenta").append(nuevaFila);
                            } else {
                                $.getJSON("../Tarifa", {idproducto: 0}, function (data, textStatus, jqXHR) {
                                    $('.tarifa').remove();
                                    var nuevaFila = "";
                                    $.each(data, function (index, item) {//recorremos la lista
                                        nuevaFila += "<tr class=\"tarifa\">";
                                        nuevaFila += "<td class=\"descriTarifa\">" + item.descripcion + "</td>";
                                        nuevaFila += "<td><input class=\"valor\" type=\"text\" name=\"valorTarifa\" id=" + item.idtarifa + " value=" + item.valor + " size=\"6\"></td>";
                                        nuevaFila += "</tr>";
                                    });
                                    $("#tablaPrecioVenta").append(nuevaFila);
                                });
                            }

                            //$('#modalAgregarProducto').one('hidden.bs.modal', function() { $('#modalPrecioVenta').modal('show'); }).modal('hide');             
                        });

                        $("#btnGuardarPrecioVenta").click(function () {
                            $('#flagPrecioVenta').val('N');
                            var valortarifa = "";
                            var idtarifa;
                            var descritarifa = "";
                            preciosVenta = [];
                            $('.valor').each(function (index) {
                                descritarifa = $(this).parent().parent().find('td:eq(0)').text();//find('tr').find('td:eq(2)').val();
                                idtarifa = $(this).attr('id');
                                valortarifa = $(this).val();
                                if (valortarifa === "") {
                                    valortarifa = 0;
                                }
                                preciosVenta.push({idtarifa: idtarifa, descripcion: descritarifa, valor: valortarifa});
                            });
                            //$('#modalPrecioVenta').hide();
                            $('#modalPrecioVenta').one('hidden.bs.modal', function () {
                                $('#modalAgregarProducto').modal('show');
                            }).modal('hide');
                        });

                        $("#btnAdicionales").click(function () {
                            $('#modalAdicionales').modal({backdrop: 'static', keyboard: false});
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

                    });
        </script>
    </body>
    <%
        } else {
            response.sendRedirect("../");
        }
    %>
</html>
