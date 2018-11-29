package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoProductoImpl;
import gilead.gcbusiness.dto.DTOProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_Producto_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);

        String opcion = request.getParameter("opcion");
        System.out.println("Entro GESTION PRODUCTO");
        if (opcion.equals("agregarVenta")) {
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null;
            ResultSet rsProducto, rsTarifas, rsLotes;
            String respuesta = "<tr class='detalleVenta'>";
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);
            try {
                cn = db.getConnection();
                st = cn.createStatement();

                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));

                String query = "SELECT p.id_producto, p.codigo_interno, p.descripcion, u.abreviatura as medida, p.afecto_igv, p.afecto_isc, p.id_tipoisc, p.base_isc, p.factor_isc,"
                        + "p.flag_lote, p.estado FROM gcbusiness.producto p, gcbusiness.unidadmedida u WHERE p.id_unidadmedida = u.id_unidadmedida AND P.id_producto = " + idproducto;

                rsProducto = st.executeQuery(query);

                if (rsProducto.next()) {
                    Integer orden = Integer.parseInt(request.getParameter("numeroproductos"));
                    //orden++;
                    respuesta += "<td style='display: none'>" + rsProducto.getInt("id_producto") + "</td>" //COLUMNA 00:  IdProducto
                            + "<td style='display: none'>" + orden + "</td>" //COLUMNA 01:  #
                            + "<td>" + rsProducto.getString("codigo_interno") + "</td>" //COLUMNA 02:  Código
                            + "<td>" + rsProducto.getString("descripcion") + "</td>" //COLUMNA 03:  Descripción
                            + "<td> <input class='input_cantidad' id='cantidad_" + orden + "' type='number' value='1' min='1' style='font-size:10px'></td>" //COLUMNA 04:  Cantidad
                            + "<td>" + rsProducto.getString("medida") + "</td>";     //COLUMNA 05:  Medida

                    String flag_lote = rsProducto.getString("flag_lote");
                    String afecto_igv = rsProducto.getString("afecto_igv");

                    //INICIO TARIFAS
                    query = "SELECT id_tarifa, valor from gcbusiness.tarifaproducto WHERE id_producto = " + idproducto + " AND valor>0 ORDER BY valor DESC";

                    rsTarifas = st.executeQuery(query);
                    String comboTarifas = "<td> <select class='select_tarifa' id='tarifa_" + orden + "'>";
                    ArrayList<Double> precios = new ArrayList<Double>();
                    while (rsTarifas.next()) {
                        precios.add(rsTarifas.getDouble("valor"));
                        comboTarifas += "<option value='" + rsTarifas.getDouble("valor") + "' >" + rsTarifas.getDouble("valor") + "</option>";
                    }
                    comboTarifas += "</select>";

                    Double precio_unitario = precios.get(0);
                    //FIN TARIFAS

                    Double igvDecimal = 0.00;

                    //CALCULO VALOR UNITARIO  E  IGV
                    Double valor_unitario = 0.00;
                    Double igv = 0.00;
                    if (afecto_igv.equals("G")) {
                        igvDecimal = 0.18;
                    }
                    valor_unitario = precio_unitario / (1 + igvDecimal);
                    igv = valor_unitario * igvDecimal;

                    //COLUMNA 06:  Precio Unitario
                    respuesta += comboTarifas;

                    //COLUMNA 07:  Valor Unitario
                    respuesta += "<td style='display: none' class='valor_unitario' id='valor_uni_" + orden + "'>" + Math.round(valor_unitario * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 08:  Precio Total
                    respuesta += "<td style='display: none' id='precio_tot_" + orden + "'>" + Math.round(precio_unitario * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 09:  Valor Total
                    respuesta += "<td style='display: none' id='valor_tot_" + orden + "'>" + Math.round(valor_unitario * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 10:  Afecto IGV
                    respuesta += "<td style='display: none' id='afecto_igv_" + orden + "' style='display: '>" + afecto_igv + "</td>";

                    //COLUMNA 11:  IGV
                    respuesta += "<td style='display: none' id='igv_" + orden + "'>" + Math.round(igv * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 12:  ISC 
                    respuesta += "<td style='display: none' id='isc_" + orden + "' style='display: '>" + 0.00 + "</td>";

                    //COLUMNA 13:  Precio Unitario con Descuento
                    respuesta += "<td style='display: none' style='display: ' id='precio_uni_dscto_" + orden + "'>" + Math.round(precio_unitario * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 14:  Valor Unitario con Descuento
                    respuesta += "<td style='display: none' style='display: ' id='valor_uni_dscto_" + orden + "'>" + Math.round(valor_unitario * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 15:  Precio Total con Descuento
                    respuesta += "<td id='precio_tot_dscto_" + orden + "'>" + Math.round(precio_unitario * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 16:  IGV con Descuento
                    respuesta += "<td id='igv_dscto_" + orden + "'>" + Math.round(igv * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 17:  Valor Total con Descuento
                    respuesta += "<td style='display: none' id='valor_tot_dscto_" + orden + "'>" + Math.round(valor_unitario * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 18:  Descuento
                    respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px'>"
                            + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                            + "<option value='P'>%</option>"
                            //        + "<option value='M'>MONTO</option>"
                            + "</select>\n"
                            + "</td>";

                    //COLUMNA 19:  Descuento Porcentaje
                    respuesta += "<td style='display: none' id='dscto_porc_" + orden + "'>" + 0.00 + "</td>";

                    //COLUMNA 20:  Descuento Monto
                    respuesta += "<td style='display: none' id='dscto_mont_" + orden + "'>" + 0.00 + "</td>";

                    if (flag_lote.equals("S")) {
                        query = "SELECT l.id_lote, l.numero_lote, l.fecha_vencimiento, a.stock_actual from gcbusiness.lote l, gcbusiness.almacenproductolote a "
                                + "WHERE l.id_lote = a.id_lote AND a.id_producto = l.id_producto AND a.id_producto = " + idproducto
                                + " AND a.id_almacen = " + Integer.parseInt((String) session.getAttribute("idAlmacen"));

                        rsLotes = st.executeQuery(query);
                        String comboLotes = "<td> <select class='select_lote' id='lote_" + orden + "'>";
                        ArrayList<Double> listaStock = new ArrayList<Double>();
                        while (rsLotes.next()) {
                            listaStock.add(rsLotes.getDouble("stock_actual"));
                            comboLotes += "<option value='" + rsLotes.getInt("id_lote") + " | " + rsLotes.getDouble("stock_actual") + "' >" + rsLotes.getString("numero_lote") + "|" + dateFormat.format(rsLotes.getDate("fecha_vencimiento")) + "</option>";
                        }
                        comboLotes += "</select>";

                        //COLUMNA 21:  Lote|F.V.
                        respuesta += comboLotes;

                        //COLUMNA 22:  Stock Actual
                        respuesta += "<td id='stock_" + orden + "'>" + listaStock.get(0) + "</td>";

                    } else {
                        query = "SELECT stock_actual from gcbusiness.almacenproductolote "
                                + "WHERE id_producto = " + idproducto
                                + " AND id_almacen = " + Integer.parseInt((String) session.getAttribute("idAlmacen"));

                        rsLotes = st.executeQuery(query);
                        String comboLotes = "<td>No aplica</td>";
                        Double stockActual = null;
                        if (rsLotes.next()) {
                            stockActual = rsLotes.getDouble("stock_actual");
                        }
                        //COLUMNA 21:  Lote|F.V.
                        respuesta += comboLotes;

                        //COLUMNA 22:  Stock Actual
                        respuesta += "<td id='stock_" + orden + "'>" + stockActual + "</td>";
                    }

                    //COLUMNA 23:  Bonificación
                    respuesta += "<td> "
                            + "<input id='bonificacion_" + orden + "' class='ace ace-switch bonificacion' type='checkbox'/>"
                            + "<span class='lbl' data-lbl=\"SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NO\"></span>"
                            + "</td>";

                    //COLUMNA 24:  Acciones
                    respuesta += "<td> "
                            + "<button type='button' name='eliminarDetalleVenta' id='" + orden + "' class='btn btn-danger btn-xs eliminarDetalleVenta' title='Eliminar'  ><span class='glyphicon glyphicon-trash'></span></button>"
                            + "</td>";

                }
            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                respuesta = "ERROR";
            } finally {
                out.println(respuesta);
                out.close();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (cn != null) {
                        cn.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (opcion.equals("agregarCompra")) {
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null;
            ResultSet rsProducto, rsTarifas, rsLotes;
            String respuesta = "<tr class='detalleCompra'>";
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);
            try {
                cn = db.getConnection();
                //cn.setAutoCommit(false);
                st = cn.createStatement();

                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));

                String query = "SELECT p.id_producto, p.codigo_interno, p.descripcion, u.abreviatura as medida, p.afecto_igv, p.afecto_isc, p.id_tipoisc, p.base_isc, p.factor_isc,"
                        + "p.flag_lote, p.estado FROM gcbusiness.producto p, gcbusiness.unidadmedida u WHERE p.id_unidadmedida = u.id_unidadmedida AND P.id_producto = " + idproducto;

                rsProducto = st.executeQuery(query);

                if (rsProducto.next()) {
                    Integer orden = Integer.parseInt(request.getParameter("numeroproductos"));
                    orden++;
                    respuesta += "<td style='display: none' id='idproducto_" + orden + "'>" + rsProducto.getInt("id_producto") + "</td>" //COLUMNA 00:  IdProducto
                            + "<td style='display: none'>" + orden + "</td>" //COLUMNA 01:  #
                            + "<td id='codigoproducto_" + orden + "'>" + rsProducto.getString("codigo_interno") + "</td>" //COLUMNA 02:  Código
                            + "<td id='descriproducto_" + orden + "'>" + rsProducto.getString("descripcion") + "</td>" //COLUMNA 03:  Descripción
                            + "<td> <input class='input_cantidad' id='cantidad_" + orden + "' type='number' value='1' min='1' style='font-size:10px'></td>" //COLUMNA 04:  Cantidad
                            + "<td>" + rsProducto.getString("medida") + "</td>";     //COLUMNA 05:  Medida

                    String flag_lote = rsProducto.getString("flag_lote");

                    //COLUMNA 06:  Tipo Afectacion
                    respuesta += "<td> <select class='select_afectacion' id='afectacion_" + orden + "'>"
                            + "<option value='G' >GRAVADA</option>"
                            + "<option value='E' >EXONERADA</option>"
                            + "<option value='I' >INAFECTA</option>"
                            + "</select>";

                    //COLUMNA 07:  Valor Unitario sin Dcto
                    //respuesta += "<td class='valor_unitario' id='valor_uni_" + orden + "'>" + 0.00 + "</td>";
                    respuesta += "<td> <input class='input_valorunitario' id='valor_uni_" + orden + "' type='text' style='font-size:10px; width:65px;'> </td>";

                    //COLUMNA 08:  Precio Unitario sin Dcto
                    respuesta += "<td class='precio_unitario' id='precio_uni_" + orden + "'>" + 0.00 + "</td>";
                    //respuesta += "<td> <input class='input_preciounitario' id='precio_uni_" + orden + "' type='text' style='font-size:10px; width:65px;'> </td>";

                    //COLUMNA 09:  Precio Total sin Dcto
                    respuesta += "<td style='display: none' class='precio_total' id='precio_tot_" + orden + "'>" + 0.00 + "</td>";

                    //COLUMNA 10:  Valor Total sin Dcto
                    respuesta += "<td style='display: none' class='valor_total' id='valor_tot_" + orden + "'>" + 0.00 + "</td>";

                    //COLUMNA 11: IGV sin Dcto
                    respuesta += "<td id='igv_" + orden + "' style='display: none'>" + 0.00 + "</td>";

                    //COLUMNA 12:  ISC 
                    respuesta += "<td id='isc_" + orden + "' style='display: none'>" + 0.00 + "</td>";

                    //COLUMNA 13:  Descuento
                    respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px'>"
                            + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                            + "<option value='P'>%</option>"
                            //+ "<option value='M'>MONTO</option>"
                            + "</select>\n"
                            + "</td>";

                    //COLUMNA 14:  Descuento Monto
                    respuesta += "<td style='display: none' id='dscto_mont_" + orden + "'>" + 0.00 + "</td>";

                    //COLUMNA 15:  Precio Unitario Dcto
                    respuesta += "<td style='display: none' class='precio_unitario_dcto' id='precio_uni_dcto_" + orden + "'>" + 0.00 + "</td>";

                    //COLUMNA 16:  Valor Unitario Dcto
                    respuesta += "<td style='display: none' class='valor_unitario_dcto' id='valor_uni_dcto_" + orden + "'>" + 0.00 + "</td>";

                    //COLUMNA 17:  Precio Compra
                    //respuesta += "<td> <input class='input_preciocompra' id='precio_compra_" + orden + "' type='text' style='font-size:10px; width:65px;'> </td>";
                    respuesta += "<td style='display: none' class='precio_compra' id='precio_compra_" + orden + "'>" + 0.00 + "</td>";

                    //COLUMNA 18:  Valor Compra
                    //respuesta += "<td> <input class='input_valorcompra' id='valor_compra_" + orden + "' type='text' style='font-size:10px; width:65px;'> </td>";
                    respuesta += "<td class='valor_compra' id='valor_compra_" + orden + "'>" + 0.00 + "</td>";

                    //COLUMNA 19: IGV
                    respuesta += "<td id='igv_compra_" + orden + "' style='display: none'>" + 0.00 + "</td>";

                    if (flag_lote.equals("S")) {
                        query = "SELECT l.id_lote, l.numero_lote, l.fecha_vencimiento from gcbusiness.lote l, gcbusiness.almacenproductolote a "
                                + "WHERE l.id_lote = a.id_lote AND a.id_producto = l.id_producto AND a.id_producto = " + idproducto + " AND l.estado='A'"
                                + " AND a.id_almacen = " + Integer.parseInt((String) session.getAttribute("idAlmacen"));

                        rsLotes = st.executeQuery(query);
                        String comboLotes = "<td> <select class='select_lote' id='lote_" + orden + "'>";
                        while (rsLotes.next()) {
                            comboLotes += "<option value='" + rsLotes.getInt("id_lote") + "' >" + rsLotes.getString("numero_lote") + "|" + dateFormat.format(rsLotes.getDate("fecha_vencimiento")) + "</option>";
                        }
                        comboLotes += "</select>"
                                + "<button type='button' name='adminLote' id='boton_lote_" + orden + "' class='btn btn-primary btn-xs adminLote' title='Lote'  ><span class='glyphicon glyphicon-book'></span></button>"
                                + "</td>";
                        //COLUMNA 20:  Lote|F.V.
                        respuesta += comboLotes;
                    } else {
                        //COLUMNA 20:  Lote|F.V.
                        respuesta += "<td>No aplica</td>";
                    }

                    //COLUMNA 21:  Bonificación
                    respuesta += "<td> "
                            + "<input id='bonificacion_" + orden + "' class='ace ace-switch bonificacion' type='checkbox'/>"
                            + "<span class='lbl' data-lbl=\"SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NO\"></span>"
                            + "</td>";

                    //COLUMNA 22:  Acciones
                    respuesta += "<td> "
                            + "<button type='button' name='eliminarDetalleCompra' id='" + orden + "' class='btn btn-danger btn-xs eliminarDetalleCompra' title='Eliminar'  ><span class='glyphicon glyphicon-trash'></span></button>"
                            + "</td>";

                }
            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                respuesta = "ERROR";
            } finally {
                out.println(respuesta);
                out.close();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (cn != null) {
                        cn.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                List<DTOProducto> listProductos = daoProductoImpl.accionListarDTOProducto();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listProductos.size(); i++) {
                    String acciones = "";

                    if (listProductos.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listProductos.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("codigo", listProductos.get(i).getCodigo());
                    obj.put("descripcion", listProductos.get(i).getDescripcion());
                    obj.put("marca", listProductos.get(i).getDescripcionmarca());
                    obj.put("categoriaproducto", listProductos.get(i).getDescripcioncategoria());
                    obj.put("unidadmedida", listProductos.get(i).getAbreviaturaunidadmedida());
                    obj.put("estado", "A".equals(listProductos.get(i).getEstado()) ? "ACTIVO" : "INACTIVO");
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        } else if (opcion.equals("insertar")) {
            System.out.println("INSERTAR PRODUCTO");
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null, st1 = null;
            String sqlEjecucion = null;
            String json = null;
            try {
                cn = db.getConnection();
                cn.setAutoCommit(false);
                st = cn.createStatement();
                st1 = cn.createStatement();

                Integer idProducto = 0;
                String query = "SELECT NEXTVAL('gcbusiness.producto_id_producto_seq')";
                sqlEjecucion = query;

                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    idProducto = rs.getInt(1);
                }

                Integer idmarca = Integer.parseInt(request.getParameter("idmarca"));
                String descripcion = request.getParameter("descripcion");
                String codigo = request.getParameter("codigo");
                String codigosunat = request.getParameter("codigosunat");
                String afectoigv = request.getParameter("afectoigv");
                String afectoisc = request.getParameter("afectoisc");
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                Integer idunidadmedida = Integer.parseInt(request.getParameter("idunidadmedida"));
                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));
                Integer idtipoproducto = Integer.parseInt(request.getParameter("idtipoproducto"));
                Double preciocompra = Double.parseDouble(request.getParameter("preciocompra"));
                String flaglote = request.getParameter("flaglote");
                Integer idtipoisc = Integer.parseInt(request.getParameter("idtipoisc"));
                Double baseisc = Double.parseDouble(request.getParameter("baseisc"));
                Double factorisc = Double.parseDouble(request.getParameter("factorisc"));
                String preciosventa = request.getParameter("preciosVenta") != null ? (String) request.getParameter("preciosVenta") : "";
                String codigoproveedor = request.getParameter("codigoproveedor");
                Double pesoproveedor = Double.parseDouble(request.getParameter("pesoproveedor"));
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                Double valorcompra = preciocompra;

                query = "INSERT INTO gcbusiness.producto (id_producto, id_marca, id_categoriaproducto, id_moneda, id_tipoproducto, id_unidadmedida, codigo_interno,"
                        + " codigo_ean, codigo_sunat, descripcion, afecto_igv, afecto_isc, id_tipoisc, base_isc, factor_isc, valor_compra, precio_compra, flag_lote, imagen,"
                        + " codigo_proveedor, peso_proveedor, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                        + " VALUES (" + idProducto + ", " + idmarca + ", " + idcategoriaproducto + ", " + idmoneda + ", " + idtipoproducto + ", " + idunidadmedida + ", '" + codigo.toUpperCase()
                        + "', '" + codigo.toUpperCase() + "', '" + codigosunat + "', '" + descripcion.toUpperCase() + "', '" + afectoigv + "', '" + afectoisc + "', " + idtipoisc + ", " + baseisc + ", " + factorisc + ", " + Math.round(valorcompra * Math.pow(10, 2)) / Math.pow(10, 2) + ", " + preciocompra + ", '" + flaglote + "', null"
                        + ", '" + codigoproveedor + "', " + pesoproveedor + ", 'A','" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                st.executeUpdate(query);

                if (flaglote.equals("N")) {
                    String qry3 = "SELECT id_almacen FROM gcbusiness.almacen WHERE estado = 'A' ORDER BY id_almacen";
                    ResultSet rs3 = st.executeQuery(qry3);
                    while (rs3.next()) {
                        query = "INSERT INTO gcbusiness.almacenproductolote (id_almacen, id_producto, stock_actual, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                                + " VALUES (" + rs3.getInt("id_almacen") + ", " + idProducto + ", 0, '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                        sqlEjecucion = query;
                        st1.executeUpdate(query);
                    }
                }

                query = "INSERT INTO gcbusiness.tarifaproducto (id_tarifa, id_producto, valor, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) VALUES (";
                String qry = "";
                if (!preciosventa.equals("")) {
                    String[] lstPrecios = preciosventa.split("-/-");
                    String precio[];
                    for (String precios : lstPrecios) {
                        if (!precios.equals("")) {
                            precio = precios.split(";");
                            if (precio.length > 0) {
                                qry = query + precio[0] + ", " + idProducto + ", " + precio[1] + ", 'A','" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                                sqlEjecucion = qry;
                                st.executeUpdate(qry);
                            }
                        }
                    }
                } else {
                    String qry2 = "SELECT id_tarifa FROM gcbusiness.tarifa ORDER BY id_tarifa";
                    ResultSet rs2 = st.executeQuery(qry2);
                    Integer idtarifa;
                    while (rs2.next()) {
                        idtarifa = rs2.getInt(1);
                        qry = query + idtarifa + ", " + idProducto + ", 0.00 , 'A','" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                        sqlEjecucion = qry;
                        st1.executeUpdate(qry);
                    }
                }

                query = "INSERT INTO gcbusiness.proveedorproducto (id_producto, id_proveedor, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                        + "SELECT " + idProducto + ",id_proveedor, 'N', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "'  FROM gcbusiness.proveedor ";
                sqlEjecucion = query;
                st.executeUpdate(query);

                json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE EL PRODUCTO</em>\" ";

                cn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                json += ",";
                json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> " + ex.getMessage().replace("\n", "").concat(". " + sqlEjecucion) + "</div>\" ";
                if (cn != null) {
                    System.out.println("Rollback");
                    try {
                        //deshace todos los cambios realizados en los datos
                        cn.rollback();
                    } catch (SQLException ex1) {
                        //System.err.println( "No se pudo deshacer" + ex1.getMessage() );
                        Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                json += "}";
                out.print(json);
                out.close();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (cn != null) {
                        cn.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                    if (st1 != null) {
                        st1.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else if (opcion.equals("actualizar")) {
            System.out.println("ACTUALIZAR PRODUCTO");
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null;
            String sqlEjecucion = null;
            String json = null;
            try {
                cn = db.getConnection();
                cn.setAutoCommit(false);
                st = cn.createStatement();

                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                Integer idmarca = Integer.parseInt(request.getParameter("idmarca"));
                String descripcion = request.getParameter("descripcion");
                String codigo = request.getParameter("codigo");
                String codigosunat = request.getParameter("codigosunat");
                String afectoigv = request.getParameter("afectoigv");
                String afectoisc = request.getParameter("afectoisc");
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                Integer idunidadmedida = Integer.parseInt(request.getParameter("idunidadmedida"));
                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));
                Integer idtipoproducto = Integer.parseInt(request.getParameter("idtipoproducto"));
                Double preciocompra = Double.parseDouble(request.getParameter("preciocompra"));
                String flaglote = request.getParameter("flaglote");
                Integer idtipoisc = Integer.parseInt(request.getParameter("idtipoisc"));
                Double baseisc = Double.parseDouble(request.getParameter("baseisc"));
                Double factorisc = Double.parseDouble(request.getParameter("factorisc"));
                String preciosventa = request.getParameter("preciosVenta") != null ? (String) request.getParameter("preciosVenta") : "";
                String codigoproveedor = request.getParameter("codigoproveedor");
                Double pesoproveedor = Double.parseDouble(request.getParameter("pesoproveedor"));
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                Double valorcompra = preciocompra;
                if (afectoigv.equals("G")) {
                    valorcompra = preciocompra * (1 / (1 + 0.18));
                }

                String query = "UPDATE gcbusiness.producto SET id_marca=" + idmarca + ", id_categoriaproducto=" + idcategoriaproducto + ", id_moneda=" + idmoneda + ", id_tipoproducto=" + idtipoproducto
                        + ", id_unidadmedida=" + idunidadmedida + ", codigo_interno='" + codigo + "', codigo_sunat='" + codigosunat + "', descripcion='" + descripcion + "', afecto_igv='" + afectoigv + "', afecto_isc='" + afectoisc + "', id_tipoisc=" + idtipoisc
                        + ", base_isc=" + baseisc + ", factor_isc=" + factorisc + ", valor_compra=" + Math.round(valorcompra * Math.pow(10, 2)) / Math.pow(10, 2) + ", precio_compra=" + preciocompra + ", flag_lote='" + flaglote + "', estado='" + estado
                        + "', codigo_proveedor='" + codigoproveedor + "', peso_proveedor=" + pesoproveedor + ", fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', terminal_modificacion='" + request.getRemoteHost()
                        + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_producto=" + idproducto + ";";

                System.err.println("query: " + query);

                sqlEjecucion = query;
                st.executeUpdate(query);

                query = "UPDATE gcbusiness.tarifaproducto SET valor=";
                String qry = "";

                String[] lstPrecios = preciosventa.split("-/-");
                String precio[];
                for (String precios : lstPrecios) {
                    if (!precios.equals("")) {
                        precio = precios.split(";");
                        if (precio.length > 0) {
                            qry = query + precio[1] + ", fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario
                                    + "', terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr()
                                    + "' WHERE id_tarifa=" + precio[0] + " AND id_producto=" + idproducto + ";";
                            sqlEjecucion = qry;
                            st.executeUpdate(qry);
                        }
                    }
                }

                json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE EL PRODUCTO</em>\" ";

                cn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                json += ",";
                json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> " + ex.getMessage().replace("\n", "").concat(". " + sqlEjecucion) + "</div>\" ";
                if (cn != null) {
                    System.out.println("Rollback");
                    try {
                        //deshace todos los cambios realizados en los datos
                        cn.rollback();
                    } catch (SQLException ex1) {
                        //System.err.println( "No se pudo deshacer" + ex1.getMessage() );
                        Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                json += "}";
                out.print(json);
                out.close();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (cn != null) {
                        cn.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (opcion.equals("buscar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                DTOProducto dtoProducto = (DTOProducto) daoProductoImpl.accionObtener(idproducto);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idproducto", dtoProducto.getIdproducto());
                obj.put("codigo", dtoProducto.getCodigo());
                obj.put("codigosunat", dtoProducto.getCodigosunat());
                obj.put("descripcion", dtoProducto.getDescripcion());
                obj.put("familiaproducto", dtoProducto.getIdfamiliaproducto());
                obj.put("claseproducto", dtoProducto.getIdclaseproducto());
                obj.put("lineaproducto", dtoProducto.getIdlineaproducto());
                obj.put("categoriaproducto", dtoProducto.getIdcategoriaproducto());
                obj.put("afectoigv", dtoProducto.getAfectoIGV());
                obj.put("afectoisc", dtoProducto.getAfectoISC());
                obj.put("marca", dtoProducto.getIdmarca());
                obj.put("unidadmedida", dtoProducto.getIdunidadmedida());
                obj.put("moneda", dtoProducto.getIdmoneda());
                obj.put("tipoproducto", dtoProducto.getIdtipoproducto());
                obj.put("preciocompra", dtoProducto.getPreciocompra());
                obj.put("tipoisc", dtoProducto.getIdtipoISC());
                obj.put("baseisc", dtoProducto.getBaseISC());
                obj.put("factorisc", dtoProducto.getFactorISC());
                obj.put("codigoproveedor", dtoProducto.getCodigoproveedor());
                obj.put("pesoproveedor", dtoProducto.getPesoproveedor());
                obj.put("flaglote", dtoProducto.getFlaglote());
                obj.put("estado", dtoProducto.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                String msg = daoProductoImpl.accionEliminar(idproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE EL PRODUCTO</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        } else if (opcion.equals("activar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                String msg = daoProductoImpl.accionActivar(idproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE EL PRODUCTO</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
