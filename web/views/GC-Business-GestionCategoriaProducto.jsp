<%-- 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Productos
    Nombre              : GC-Business-GestionCategoriaProducto.jsp
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Crear, Modificar, Consultar e Inactivar una categoria de producto
--%>
<%@page import="gilead.gcbusiness.dao.impl.DaoLineaProductoImpl"%>
<%@page import="gilead.gcbusiness.model.BeanLineaProducto"%>
<%@page import="java.util.List"%>
<%@page import="gilead.gcbusiness.model.BeanUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List opciones = (List)session.getAttribute("accesos");
    BeanUsuario usuario = (BeanUsuario) session.getAttribute("usuario");
%>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta charset="utf-8" />
        <title>GC BUSINESS - Gestión de Categorias de Producto</title>

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
                        if(opciones.contains(11)){
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
                                if(opciones.contains(12)){
                            %>
                            <li class="">
                                <a href="#">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Registrar Venta
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }    
                                if(opciones.contains(13)){
                            %>
                            <li class="">
                                <a href="#">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Notas
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }    
                                if(opciones.contains(14)){
                            %>
                            <li class="">
                                <a href="#">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Cotización
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }    
                                if(opciones.contains(15)){
                            %>
                            <li class="">
                                <a href="#">
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
                        if(opciones.contains(31)){
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
                                if(opciones.contains(32)){
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
                                if(opciones.contains(33)){
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
                                if(opciones.contains(34)){
                            %>
                            <li class="">
                                <a href="#">
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
                        if(opciones.contains(21)){
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
                                if(opciones.contains(22)){
                            %>
                            <li class="">
                                <a href="#">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Registrar Compra
                                </a>

                                <b class="arrow"></b>
                            </li>
                            <%
                                }    
                                if(opciones.contains(23)){
                            %>
                            <li class="">
                                <a href="#">
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
                        if(opciones.contains(41)){
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
                                if(opciones.contains(42)){
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
                                if(opciones.contains(43)){
                            %>
                            <li class="">
                                <a href="#">
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
                        if(opciones.contains(51)){
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
                                if(opciones.contains(52)){
                            %>
                            <li class="">
                                <a href="#">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    Ingresos y Salidas
                                </a>

                                <b class="arrow"></b>
                            </li>                           
                            <%
                                }    
                                if(opciones.contains(53)){
                            %>
                            <li class="">
                                <a href="#">
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
                        if(opciones.contains(1)){
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
                                if(opciones.contains(10)){
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
                                if(opciones.contains(2)){
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
                                if(opciones.contains(3)){
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
                                if(opciones.contains(4)){
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
                                if(opciones.contains(5)){
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
                                if(opciones.contains(6)){
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
                                if(opciones.contains(7)){
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
                        if(opciones.contains(61)){
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
                                if(opciones.contains(62)){
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
                                if(opciones.contains(63)){
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
                                if(opciones.contains(64)){
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
                                if(opciones.contains(65)){
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
                                if(opciones.contains(66)){
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
                                if(opciones.contains(67)){
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
                        if(opciones.contains(71)){
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
                            <li class="active">Categorias</li>
                        </ul><!-- /.breadcrumb -->

                    </div>

                    <div class="page-content">

                        <div class="page-header">
                            <h1>
                                Categorias
                                <small>
                                    <i class="ace-icon fa fa-angle-double-right"></i>
                                    Crear, modificar, eliminar categorias de productos
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
                                    <table id="tablaCategoriaProductos" class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>Nro</th>
                                                <th>Linea</th>   
                                                <th>Descripción</th>    
                                                <th>Abreviatura</th> 
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
            <div class="modal" id="modalAgregarCategoriaProducto" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="blue bigger">Registrar Nueva Categoria Producto</h4>
                        </div>

                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12">
                                    <input type="hidden" id="idcategoriaproducto" value="">
                                    <input type="hidden" id="opcion" value="">
                                    
                                    <div class="form-group">
                                        <label for="lineaproducto" class="col-sm-3 control-label">Linea</label>
                                        <div class="col-sm-9">
                                            <select id="lineaproducto" name="lineaproducto" class="col-xs-10 col-sm-6">
                                                <option value="0" selected="selected">Seleccione</option>
                                                <%
                                                    DaoLineaProductoImpl daoLineaProducto = new DaoLineaProductoImpl();
                                                    List<BeanLineaProducto> lineaproducto = daoLineaProducto.accionListar();

                                                    for (int i = 0; i < lineaproducto.size(); i++) {
                                                        //if(lineaproducto.get(i).getEstado().equals("I")){        @01 FAMILIAS INACTIVAS
                                                          //  lineaproductoesInactivas.add(lineaproducto.get(i).getIdlineaproducto());
                                                        //}    
                                                %>
                                                            <option value="<%= lineaproducto.get(i).getIdlineaproducto() %>">
                                                                <%= lineaproducto.get(i).getDescripcion() %> 
                                                            </option>
                                                <%
                                                       
                                                    }
                                                %>
                                            </select>
                                        </div>
                                    </div>
                                    
                                    &nbsp;&nbsp;
                                    
                                    <div class="form-group">
                                        <label for="descripcion" class="col-sm-3 control-label">Descripción</label>

                                        <div class="col-sm-9">
                                            <input type="text" id="descripcion" class="form-control" style="text-transform:uppercase" tabindex="1"/>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;
                                    
                                    <div class="form-group">
                                        <label for="abreviatura" class="col-sm-3 control-label">Abreviatura</label>

                                        <div class="col-sm-9">
                                            <input type="text" id="abreviatura" class="form-control" style="text-transform:uppercase" tabindex="1"/>
                                        </div>
                                    </div>

                                    &nbsp;&nbsp;
                                    
                                    <div class="form-group" id="divestado">
                                        <label for="estado" class="col-sm-2 control-label">Estado</label>
                                        <div class="col-sm-3">  
                                            <select id="estado" name="estado" class="form-control" tabindex="5">
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
                
                $('body').on('shown.bs.modal', '#modalAgregarCategoriaProducto', function () {
                    $('input:visible:enabled:first', this).focus();
                });            
            
                var tablaCategoriaProductos = $('#tablaCategoriaProductos').DataTable({
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
                        url: "../CategoriaProducto",
                        data: {"opcion": "listar"},
                        dataSrc: "data"
                    },
                    columns: [
                        {"data": "nro"},
                        {"data": "lineaproducto"},
                        {"data": "descripcion"},
                        {"data": "abreviatura"},
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
                new $.fn.dataTable.Buttons(tablaCategoriaProductos, {
                    buttons: [
                        {
                            "text": "<i class='fa fa-plus bigger-110 blue'></i>",
                            "titleAttr": "Nuevo",
                            "className": "btn btn-white btn-primary btn-bold",
                            "action": function () {
                                $('#opcion').val('insertar');
                                $('#lineaproducto').val('0');
                                $('#descripcion').val('');
                                $('#abreviatura').val('');
                                $('#estado').val('A');
                                $('#divestado').hide();
                                $('#descripcion').prop('disabled', false);
                                $('#modalAgregarCategoriaProducto .blue').text('Registrar Nueva Categoria Producto');
                                $('#modalAgregarCategoriaProducto').modal('show');
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

                tablaCategoriaProductos.buttons().container().appendTo($('.tableTools-container'));

                $('#btnGuardar').click(function (event) {
                    var idcategoriaproducto = $('#idcategoriaproducto').val();
                    var idlineaproducto = $('#lineaproducto').val();
                    var descripcion = $('#descripcion').val();
                    var abreviatura = $('#abreviatura').val();
                    var estado = $('#estado').val();
                    var opcion = $('#opcion').val();
                    $.ajax({
                        method: "POST",
                        url: "../CategoriaProducto",
                        data: {"opcion": opcion, "idlineaproducto": idlineaproducto, "idcategoriaproducto": idcategoriaproducto, "descripcion": descripcion, "abreviatura": abreviatura, "estado": estado}
                    }).done(function (data) {
                        var obj = jQuery.parseJSON(data);
                        if (obj.mensaje.indexOf('ERROR') !== -1) {
                            $('.divError').html(obj.html);
                            $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                $('.divError').removeClass('tada animated');
                            });
                        } else {
                            tablaCategoriaProductos.ajax.reload();
                            alertify.success(obj.mensaje);
                        }
                        $('#modalAgregarCategoriaProducto').modal('hide');
                    });
                });                         

                //Actualizar registro
                $(document).on('click', '.actualizar', function () {
                    var idcategoriaproducto = $(this).attr('id');
                    var row = $(this).parent().parent();
                    $.ajax({
                        url: "../CategoriaProducto",
                        method: "POST",
                        data: {"opcion": "buscar", "idcategoriaproducto": idcategoriaproducto},
                        success: function (data) {
                            var obj = jQuery.parseJSON(data);
                            $('#opcion').val('actualizar');
                            $('#idcategoriaproducto').val(obj.idcategoriaproducto);
                            $('#lineaproducto').val(obj.idlineaproducto);
                            $('#descripcion').val(obj.descripcion);
                            $('#abreviatura').val(obj.abreviatura);
                            $('#estado').val(obj.estado);
                            $('#divestado').hide();
                            $('#modalAgregarCategoriaProducto .blue').text('Modificar Categoria Producto');
                            $('#modalAgregarCategoriaProducto').modal('show');
                        },
                        error: function (error) {
                            alertify.error("ERROR AL EJECUTAR AJAX DE OBTENER DATOS USUARIO");
                        }
                    }).done();

                });

                //Eliminar registro
                $(document).on('click', '.eliminar', function () {
                    var idcategoriaproducto = $(this).attr('id');
                    var row = $(this).parent().parent();
                    $.ajax({
                        url: "../CategoriaProducto",
                        method: "POST",
                        data: {"opcion": "eliminar", "idcategoriaproducto": idcategoriaproducto},
                        success: function (data) {
                            var obj = jQuery.parseJSON(data);
                            if (obj.mensaje.indexOf('ERROR') !== -1) {
                                $('.divError').html(obj.html);
                                $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                    $('.divError').removeClass('tada animated');
                                });
                            } else {
                                tablaCategoriaProductos.ajax.reload();
                                alertify.success(obj.mensaje);
                            }
                        },
                        error: function (error) {
                            alertify.error("ERROR AL EJECUTAR AJAX DE INHABILITAR");
                        }
                    }).done();
                });

                //Activar categoriaproducto
                $(document).on('click', '.activar', function () {
                    var idcategoriaproducto = $(this).attr('id');
                    var row = $(this).parent().parent();
                    $.ajax({
                        url: "../CategoriaProducto",
                        method: "POST",
                        data: {"opcion": "activar", "idcategoriaproducto": idcategoriaproducto},
                        success: function (data) {
                            var obj = jQuery.parseJSON(data);
                            if (obj.mensaje.indexOf('ERROR') !== -1) {
                                $('.divError').html(obj.html);
                                $('.divError').addClass('tada animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                                    $('.divError').removeClass('tada animated');
                                });
                            } else {
                                tablaCategoriaProductos.ajax.reload();
                                alertify.success(obj.mensaje);
                            }
                        },
                        error: function (error) {
                            alertify.error("ERROR AL EJECUTAR AJAX DE INHABILITAR");
                        }
                    }).done();
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
