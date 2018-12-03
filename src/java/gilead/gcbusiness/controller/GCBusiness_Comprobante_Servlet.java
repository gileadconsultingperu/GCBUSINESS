package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoComprobanteImpl;
import gilead.gcbusiness.dao.impl.DaoVentaImpl;
import gilead.gcbusiness.dto.DTOComprobante;
import gilead.gcbusiness.dto.DTODetalleVenta;
import gilead.gcbusiness.dto.DTOVenta;
import gilead.gcbusiness.sql.ConectaDb;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class GCBusiness_Comprobante_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);

        String opcion = request.getParameter("opcion");
        System.out.println("Entro COMPROBANTE");
        if (opcion.equals("listarparanotas")) {
            try (PrintWriter out = response.getWriter()) {
                String fecha_desde = !request.getParameter("desde").equals("") ? (String) request.getParameter("desde") : "01/01/1990";
                String fecha_hasta = !request.getParameter("hasta").equals("") ? (String) request.getParameter("hasta") : "12/12/9999";
                String numeroComprobante = request.getParameter("nroComp") != null ? (String) request.getParameter("nroComp") : "";
                String tipoComprobante = request.getParameter("tipoComp") != null ? (String) request.getParameter("tipoComp") : "0";
                DaoComprobanteImpl daoComprobanteImpl = new DaoComprobanteImpl();
                List<DTOComprobante> listComprobante = daoComprobanteImpl.accionListarDTOComprobanteParaNotas(numeroComprobante, tipoComprobante, fecha_desde, fecha_hasta);

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);

                for (int i = 0; i < listComprobante.size(); i++) {
                    String acciones = "";
                    String cantidadnota = "";
                    Integer cantidaddocumento = daoComprobanteImpl.accionContarDocumentoRelacionado(listComprobante.get(i).getIdtipocomprobante(), listComprobante.get(i).getIdserie(), listComprobante.get(i).getCorrelativoserie());

                    if (!listComprobante.get(i).getCodigoSunatcomprobante().equals("00")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                //+ "<a href=\"GC-Business-NotaCredito.jsp?idventa=" + listComprobante.get(i).getIdventa() + "\" class=\"btn btn-xs btn-info imprimir\"><i class=\"ace-icon\" title='Nota Crédito'><b>NC</b></i></a>"
                                + "<button type='button' name='seleccionarnota' id='" + listComprobante.get(i).getIdventa() + " | " + listComprobante.get(i).getIdalmacen() + " | " + listComprobante.get(i).getIdtipocomprobante() + "' class='btn btn-xs btn-info seleccionarnota' title='Nota Crédito'><i class=\"ace-icon\"><b>NC</b></i></button>";;
                    } else {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<a href=\"GC-Business-DevolucionNotaPedido.jsp?idventa=" + listComprobante.get(i).getIdventa() + "&idalmacenorigen=" + listComprobante.get(i).getIdalmacen() + "\" class=\"btn btn-xs btn-info imprimir\"><i class=\"ace-icon\" title='Devolución Nota Pedido'><b>DV</b></i></a>";
                    }

                    if (cantidaddocumento == 0) {
                        cantidadnota = String.valueOf(cantidaddocumento);
                    } else if (cantidaddocumento > 0) {
                        cantidadnota = cantidaddocumento + "&nbsp;&nbsp;&nbsp;&nbsp;<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='verdetalle' id='" + listComprobante.get(i).getIdtipocomprobante() + " | " + listComprobante.get(i).getIdserie() + " | " + listComprobante.get(i).getCorrelativoserie() + "' class='btn btn-xs btn-ligth verdetalle' title='Ver Detalle'><i class=\"ace-icon\">Ver</i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("fecha", dateFormat.format(listComprobante.get(i).getFecha_emision().getTime()));
                    obj.put("codigocomprobante", listComprobante.get(i).getCodigoSunatcomprobante());
                    obj.put("tipocomprobante", listComprobante.get(i).getAbreviaturacomprobante());
                    obj.put("serie", listComprobante.get(i).getSerie());
                    obj.put("correlativo", listComprobante.get(i).getCorrelativoserie());
                    obj.put("nrodocumento", listComprobante.get(i).getNumerodocumentocliente());
                    obj.put("cliente", listComprobante.get(i).getNombrecliente());
                    obj.put("moneda", listComprobante.get(i).getCodigoSunatMoneda());
                    obj.put("total", listComprobante.get(i).getTotal_venta());
                    obj.put("cantidadnota", cantidadnota);
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }
                System.out.println(" {\"data\":" + datos.toJSONString() + "} ");
                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        } else if (opcion.equals("listarcomprobantes")) {
            try (PrintWriter out = response.getWriter()) {
                String accion = request.getParameter("accion");
                String fecha_desde = !request.getParameter("desde").equals("") ? (String) request.getParameter("desde") : "01/01/1990";
                String fecha_hasta = !request.getParameter("hasta").equals("") ? (String) request.getParameter("hasta") : "12/12/9999";
                String numeroComprobante = request.getParameter("nroComp") != null ? (String) request.getParameter("nroComp") : "";
                String tipoComprobante = request.getParameter("tipoComp") != null ? (String) request.getParameter("tipoComp") : "0";
                DaoComprobanteImpl daoComprobanteImpl = new DaoComprobanteImpl();
                List<DTOComprobante> listComprobante = null;
                if (accion.equals("anular")) {
                    listComprobante = daoComprobanteImpl.accionListarDTOComprobanteParaAnular(numeroComprobante, tipoComprobante, fecha_desde, fecha_hasta);
                } else if (accion.equals("visualizar")) {
                    listComprobante = daoComprobanteImpl.accionListarDTOComprobantes(numeroComprobante, tipoComprobante, fecha_desde, fecha_hasta);
                }
                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);

                String ruc = (String) session.getAttribute("rucEmpresa");

                for (int i = 0; i < listComprobante.size(); i++) {
                    String acciones = "";

                    if (accion.equals("anular")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='anular' id='" + listComprobante.get(i).getIdventa() + " | " + listComprobante.get(i).getCodigoSunatcomprobante() + "' class='btn btn-danger btn-xs anular' title='Anular'><span class='glyphicon glyphicon-trash'></span></button>"
                                + "</div>";
                    } else if (accion.equals("visualizar")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='imprimir' id='" + listComprobante.get(i).getIdventa() + " | " + listComprobante.get(i).getCodigoSunatcomprobante() + " | " + listComprobante.get(i).getTotal_venta() + " | " + "/factele/" + ruc + "/sunat/envio/" + ruc + "-" + listComprobante.get(i).getCodigoSunatcomprobante() + "-" + listComprobante.get(i).getSerie() + "-" + listComprobante.get(i).getCorrelativoserie() + ".pdf" + "' class='btn btn-info btn-xs imprimir' title='Imprimir'><span class='glyphicon glyphicon-print'></span></button>"
                                + "</div>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("fecha", dateFormat.format(listComprobante.get(i).getFecha_emision().getTime()));
                    obj.put("tipocomprobante", listComprobante.get(i).getAbreviaturacomprobante());
                    obj.put("serie", listComprobante.get(i).getSerie());
                    obj.put("correlativo", listComprobante.get(i).getCorrelativoserie());
                    obj.put("nrodocumento", listComprobante.get(i).getNumerodocumentocliente());
                    obj.put("cliente", listComprobante.get(i).getNombrecliente());
                    obj.put("moneda", listComprobante.get(i).getCodigoSunatMoneda());
                    obj.put("total", listComprobante.get(i).getTotal_venta());
                    obj.put("estado", listComprobante.get(i).getEstado().equals("E") ? "EMITIDO" : "ANULADO");
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }
                System.out.println(" {\"data\":" + datos.toJSONString() + "} ");
                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        } else if (opcion.equals("anular")) {
            System.out.println("ANULAR COMPROBANTE");
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null, st1 = null;
            String sqlEjecucion = null;
            String json = "{";
            Integer flag_anulado = 0;
            try {
                cn = db.getConnection();
                cn.setAutoCommit(false);
                st = cn.createStatement();
                st1 = cn.createStatement();

                Integer idcomprobante = Integer.parseInt(request.getParameter("idcomprobante").trim());
                String codTipoCmp = request.getParameter("codTipoCmp").trim();
                String motivoAnulacion = request.getParameter("motivo").trim().toUpperCase();
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                String serieobs = "";
                String correlativoserieobs = "";

                if (codTipoCmp.equals("01") || codTipoCmp.equals("03")) {//SI ES FACTURA O BOLETA
                    //OBTENER CANTIDAD DE NOTAS EMITIDAS RELACIONADAS
                    Integer cantidad_nota = 0;
                    String sql = "SELECT count(n.*) cantidad_nota "
                            + "FROM gcbusiness.nota n "
                            + "LEFT JOIN gcbusiness.venta v ON v.id_tipocomprobante=n.id_tipocomprobante_modifica AND v.id_serie=n.id_serie_modifica AND v.correlativo_serie=n.correlativo_serie_modifica "
                            + "WHERE v.id_venta = " + idcomprobante + " AND n.estado = 'E'";
                    System.out.println("sql: " + sql);
                    ResultSet rs = st.executeQuery(sql);
                    try {
                        if (rs.next()) {
                            cantidad_nota = rs.getInt("cantidad_nota");
                        }
                    } catch (java.sql.SQLException sqle) {
                    }

                    //EN CASO NO EXISTA NOTAS EMITIDAS SE PUEDE ANULAR EL COMPROBANTE
                    if (cantidad_nota == 0) {
                        //RETORNO DE INVENTARIO SOLO AL ANULAR BOLETA O FACTURA
                        //OBTENEMOS IDALMACEN PARA SABER A QUE ALMACEN RETORNARA LOS PRODUCTOS 
                        Integer id_almacen = 0;
                        sql = "SELECT v.id_almacen, s.serie, v.correlativo_serie "
                                + "FROM gcbusiness.venta v "
                                + "LEFT JOIN gcbusiness.serie s ON s.id_serie=v.id_serie "
                                + "WHERE v.id_venta=" + idcomprobante;
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        if (rs.next()) {
                            id_almacen = rs.getInt("id_almacen");
                            serieobs = rs.getString("serie");
                            correlativoserieobs = String.valueOf(rs.getInt("correlativo_serie"));
                        }

                        //INSERTAMOS CABECERA MOVIMIENTO INVENTARIO
                        int id_movimientoinventario = 0;
                        sql = "SELECT NEXTVAL('gcbusiness.movimientoinventario_id_movimientoinventario_seq')";
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        if (rs.next()) {
                            id_movimientoinventario = rs.getInt(1);
                        }

                        sql = "INSERT INTO gcbusiness.movimientoinventario(id_movimientoinventario, id_almacen, id_motivomovimiento, fecha, observacion, id_referencia, estado, "
                                + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                + "VALUES (" + id_movimientoinventario + ", " + id_almacen + ", 4, '" + ts + "', 'ANULACION COMPROBANTE " + serieobs + "-" + correlativoserieobs + "', " + idcomprobante + ", 'A', "
                                + "'" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                        sqlEjecucion = sql;
                        st.executeUpdate(sql);

                        //OBTENEMOS IDPRODUCTO Y CANTIDAD PARA RETORNAR AL ALMACEN 
                        Integer idProducto = 0, idLote = null;
                        Double cantidad;
                        sql = "SELECT id_producto, id_lote, cantidad FROM gcbusiness.detalle_venta WHERE id_venta=" + idcomprobante;
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            idProducto = rs.getInt("id_producto");
                            idLote = rs.getInt("id_lote");
                            cantidad = rs.getDouble("cantidad");

                            //OBTENEMOS STOCK ACTUAL DEL PRODUCTO Y DEL ALMACEN CORRESPONDIENTE 
                            Double stockActual = 0.00;
                            if (idLote != 0) {
                                sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen;
                            } else {
                                sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen;
                                idLote = null;
                            }

                            sqlEjecucion = sql;

                            ResultSet rs1 = st1.executeQuery(sql);
                            if (rs1.next()) {
                                stockActual = rs1.getDouble("stock_actual");
                            }

                            Double nuevoStock = stockActual + cantidad;

                            //INSERTAMOS DETALLE MOVIMIENTO INVENTARIO
                            int id_detalle_movimientoinventario = 0;
                            sql = "SELECT NEXTVAL('gcbusiness.detalle_movimientoinventario_id_detalle_movimientoinventario_se')";
                            sqlEjecucion = sql;
                            rs1 = st1.executeQuery(sql);

                            if (rs1.next()) {
                                id_detalle_movimientoinventario = rs1.getInt(1);
                            }

                            sql = "INSERT INTO gcbusiness.detalle_movimientoinventario (id_detalle_movimientoinventario, id_movimientoinventario, id_producto, id_lote, cantidad, stock_saldo, "
                                    + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                    + "VALUES (" + id_detalle_movimientoinventario + ", " + id_movimientoinventario + ", " + idProducto + ", " + idLote + ", " + cantidad + ", " + nuevoStock
                                    + ", '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                            sqlEjecucion = sql;
                            st1.executeUpdate(sql);

                            //ACTUALIZAMOS STOCK 
                            if (idLote != null) {
                                sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen;
                            } else {
                                sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen;
                            }

                            sqlEjecucion = sql;
                            st1.executeUpdate(sql);
                        }

                        //ACTUALIZA CUENTA COBRAR SI HAY CUENTA POR COBRAR
                        sql = "UPDATE gcbusiness.cuentacobrar SET estado = 'A', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                                + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_comprobante=" + idcomprobante;
                        sqlEjecucion = sql;
                        st.executeUpdate(sql);

                        //ACTUALIZA VENTA
                        sql = "UPDATE gcbusiness.venta SET estado = 'A', motivo_anulacion='" + motivoAnulacion + "', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                                + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_venta=" + idcomprobante;

                        sqlEjecucion = sql;
                        st.executeUpdate(sql);
                        flag_anulado = 1;
                    } else { //ERROR ANULAR PRIMERO LAS NOTAS REFERENCIADAS
                        json += " \"mensaje\":\"<em>ERROR AL ANULAR EL COMPROBANTE. El comprobante posee una o varias notas referenciadas.</em>\" ";
                    }
                } else if (codTipoCmp.equals("00")) {//SI ES NOTA DE PEDIDO
                    Integer cantidad_nota = 0;
                    String sql = "SELECT count(n.*) cantidad_nota "
                            + "FROM gcbusiness.nota n "
                            + "LEFT JOIN gcbusiness.venta v ON v.id_tipocomprobante=n.id_tipocomprobante_modifica AND v.id_serie=n.id_serie_modifica AND v.correlativo_serie=n.correlativo_serie_modifica "
                            + "WHERE v.id_venta = " + idcomprobante + " AND n.estado = 'E'";
                    System.out.println("sql: " + sql);
                    ResultSet rs = st.executeQuery(sql);
                    try {
                        if (rs.next()) {
                            cantidad_nota = rs.getInt("cantidad_nota");
                        }
                    } catch (java.sql.SQLException sqle) {
                    }

                    if (cantidad_nota == 0) {
                        //RETORNO DE INVENTARIO SOLO AL ANULAR NOTA DE PEDIDO
                        //OBTENEMOS IDALMACEN PARA SABER A QUE ALMACEN RETORNARA LOS PRODUCTOS 
                        Integer id_almacen = 0;
                        sql = "SELECT v.id_almacen, s.serie, v.correlativo_serie "
                                + "FROM gcbusiness.venta v "
                                + "LEFT JOIN gcbusiness.serie s ON s.id_serie=v.id_serie "
                                + "WHERE v.id_venta=" + idcomprobante;
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        if (rs.next()) {
                            id_almacen = rs.getInt("id_almacen");
                            serieobs = rs.getString("serie");
                            correlativoserieobs = String.valueOf(rs.getInt("correlativo_serie"));
                        }

                        //INSERTAMOS CABECERA MOVIMIENTO INVENTARIO
                        int id_movimientoinventario = 0;
                        sql = "SELECT NEXTVAL('gcbusiness.movimientoinventario_id_movimientoinventario_seq')";
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        if (rs.next()) {
                            id_movimientoinventario = rs.getInt(1);
                        }

                        sql = "INSERT INTO gcbusiness.movimientoinventario(id_movimientoinventario, id_almacen, id_motivomovimiento, fecha, observacion, id_referencia, estado, "
                                + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                + "VALUES (" + id_movimientoinventario + ", " + id_almacen + ", 5, '" + ts + "', 'ANULACION COMPROBANTE " + serieobs + "-" + correlativoserieobs + "', " + idcomprobante + ", 'A', "
                                + "'" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                        sqlEjecucion = sql;
                        st.executeUpdate(sql);

                        //OBTENEMOS IDPRODUCTO Y CANTIDAD PARA RETORNAR AL ALMACEN 
                        Integer idProducto = 0, idLote = null;
                        Double cantidad;
                        sql = "SELECT id_producto, id_lote, cantidad FROM gcbusiness.detalle_venta WHERE id_venta=" + idcomprobante;
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            idProducto = rs.getInt("id_producto");
                            idLote = rs.getInt("id_lote");
                            cantidad = rs.getDouble("cantidad");

                            //OBTENEMOS STOCK ACTUAL DEL PRODUCTO Y DEL ALMACEN CORRESPONDIENTE 
                            Double stockActual = 0.00;
                            if (idLote != 0) {
                                sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen;
                            } else {
                                sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen;
                                idLote = null;
                            }

                            sqlEjecucion = sql;

                            ResultSet rs1 = st1.executeQuery(sql);
                            if (rs1.next()) {
                                stockActual = rs1.getDouble("stock_actual");
                            }

                            Double nuevoStock = stockActual + cantidad;

                            //INSERTAMOS DETALLE MOVIMIENTO INVENTARIO
                            int id_detalle_movimientoinventario = 0;
                            sql = "SELECT NEXTVAL('gcbusiness.detalle_movimientoinventario_id_detalle_movimientoinventario_se')";
                            sqlEjecucion = sql;
                            rs1 = st1.executeQuery(sql);

                            if (rs1.next()) {
                                id_detalle_movimientoinventario = rs1.getInt(1);
                            }

                            sql = "INSERT INTO gcbusiness.detalle_movimientoinventario (id_detalle_movimientoinventario, id_movimientoinventario, id_producto, id_lote, cantidad, stock_saldo, "
                                    + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                    + "VALUES (" + id_detalle_movimientoinventario + ", " + id_movimientoinventario + ", " + idProducto + ", " + idLote + ", " + cantidad + ", " + nuevoStock
                                    + ", '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                            sqlEjecucion = sql;
                            st1.executeUpdate(sql);

                            //ACTUALIZAMOS STOCK 
                            if (idLote != null) {
                                sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen;
                            } else {
                                sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen;
                            }

                            sqlEjecucion = sql;
                            st1.executeUpdate(sql);
                        }

                        //ACTUALIZA CUENTA COBRAR SI HAY CUENTA POR COBRAR
                        sql = "UPDATE gcbusiness.cuentacobrar SET estado = 'A', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                                + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_comprobante=" + idcomprobante;
                        sqlEjecucion = sql;
                        st.executeUpdate(sql);

                        //ACTUALIZA VENTA
                        sql = "UPDATE gcbusiness.venta SET estado = 'A', motivo_anulacion='" + motivoAnulacion + "', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                                + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_venta=" + idcomprobante;

                        sqlEjecucion = sql;
                        st.executeUpdate(sql);
                        flag_anulado = 1;
                    } else { //ERROR ANULAR PRIMERO LAS NOTAS REFERENCIADAS
                        json += " \"mensaje\":\"<em>ERROR AL ANULAR LA NOTA DE PEDIDO. El comprobante posee una o varias notas referenciadas.</em>\" ";
                    }
                } else if (codTipoCmp.equals("07")) {//SI ES NOTA DE CREDITO
                    //RETORNO DE INVENTARIO SOLO AL ANULAR NOTA DE CREDITO
                    //OBTENEMOS IDALMACEN PARA SABER A QUE ALMACEN RETORNARA LOS PRODUCTOS 
                    Integer id_almacen = 0, id_tiponota = 0, id_tipocomprobante_modifica = 0, id_serie_modifica = 0, correlativo_serie_modifica = 0;
                    Double total_venta = 0.00;
                    String sql = "SELECT n.id_almacen, n.id_tiponota, n.id_tipocomprobante_modifica, n.id_serie_modifica, n.correlativo_serie_modifica, n.total_venta, s.serie, n.correlativo_serie "
                            + "FROM gcbusiness.nota n "
                            + "LEFT JOIN gcbusiness.serie s ON s.id_serie=n.id_serie "
                            + "WHERE n.id_nota=" + idcomprobante;
                    sqlEjecucion = sql;
                    ResultSet rs = st.executeQuery(sql);

                    if (rs.next()) {
                        id_almacen = rs.getInt("id_almacen");
                        id_tiponota = rs.getInt("id_tiponota");
                        id_tipocomprobante_modifica = rs.getInt("id_tipocomprobante_modifica");
                        id_serie_modifica = rs.getInt("id_serie_modifica");
                        correlativo_serie_modifica = rs.getInt("correlativo_serie_modifica");
                        total_venta = rs.getDouble("total_venta");
                        serieobs = rs.getString("serie");
                        correlativoserieobs = String.valueOf(rs.getInt("correlativo_serie"));
                    }

                    if (id_tiponota == 1 || id_tiponota == 6 || id_tiponota == 7) {
                        //INSERTAMOS CABECERA MOVIMIENTO INVENTARIO
                        int id_movimientoinventario = 0;
                        sql = "SELECT NEXTVAL('gcbusiness.movimientoinventario_id_movimientoinventario_seq')";
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        if (rs.next()) {
                            id_movimientoinventario = rs.getInt(1);
                        }

                        Integer id_motivomovimiento = null;
                        if (id_tiponota == 1) {
                            id_motivomovimiento = 14;
                        } else {
                            id_motivomovimiento = 13;
                        }

                        sql = "INSERT INTO gcbusiness.movimientoinventario(id_movimientoinventario, id_almacen, id_motivomovimiento, fecha, observacion, id_referencia, estado, "
                                + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                + "VALUES (" + id_movimientoinventario + ", " + id_almacen + ", " + id_motivomovimiento + ", '" + ts + "', 'ANULACION COMPROBANTE " + serieobs + "-" + correlativoserieobs + "', " + idcomprobante + ", 'A', "
                                + "'" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                        sqlEjecucion = sql;
                        st.executeUpdate(sql);

                        //OBTENEMOS IDPRODUCTO Y CANTIDAD PARA RETORNAR AL ALMACEN 
                        Integer idProducto = 0, idLote = null;
                        Double cantidad;
                        sql = "SELECT id_producto, id_lote, cantidad FROM gcbusiness.detalle_nota WHERE id_nota=" + idcomprobante;
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            idProducto = rs.getInt("id_producto");
                            idLote = rs.getInt("id_lote");
                            cantidad = rs.getDouble("cantidad");

                            //OBTENEMOS STOCK ACTUAL DEL PRODUCTO Y DEL ALMACEN CORRESPONDIENTE 
                            Double stockActual = 0.00;
                            if (idLote != 0) {
                                sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen;
                            } else {
                                sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen;
                                idLote = null;
                            }

                            sqlEjecucion = sql;

                            ResultSet rs1 = st1.executeQuery(sql);
                            if (rs1.next()) {
                                stockActual = rs1.getDouble("stock_actual");
                            }

                            Double nuevoStock = stockActual - cantidad;

                            //INSERTAMOS DETALLE MOVIMIENTO INVENTARIO
                            int id_detalle_movimientoinventario = 0;
                            sql = "SELECT NEXTVAL('gcbusiness.detalle_movimientoinventario_id_detalle_movimientoinventario_se')";
                            sqlEjecucion = sql;
                            rs1 = st1.executeQuery(sql);

                            if (rs1.next()) {
                                id_detalle_movimientoinventario = rs1.getInt(1);
                            }

                            sql = "INSERT INTO gcbusiness.detalle_movimientoinventario (id_detalle_movimientoinventario, id_movimientoinventario, id_producto, id_lote, cantidad, stock_saldo, "
                                    + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                    + "VALUES (" + id_detalle_movimientoinventario + ", " + id_movimientoinventario + ", " + idProducto + ", " + idLote + ", " + cantidad + ", " + nuevoStock
                                    + ", '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                            sqlEjecucion = sql;
                            st1.executeUpdate(sql);

                            //ACTUALIZAMOS STOCK 
                            if (idLote != null) {
                                sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen;
                            } else {
                                sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen;
                            }

                            sqlEjecucion = sql;
                            st1.executeUpdate(sql);
                        }

                        //REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR SI APLICA
                        Integer idcomprobantemodifica = 0;
                        sql = "SELECT id_venta FROM gcbusiness.venta "
                                + "WHERE id_tipocomprobante = " + id_tipocomprobante_modifica + " "
                                + "AND id_serie = " + id_serie_modifica + " "
                                + "AND correlativo_serie = " + correlativo_serie_modifica;

                        rs = st.executeQuery(sql);
                        if (rs.next()) {
                            idcomprobantemodifica = rs.getInt("id_venta");
                        }

                        Double saldoAnterior = 0.00, saldoActual = 0.00;
                        Integer idcuentacobrar = 0;
                        sql = "SELECT id_cuentacobrar, saldo FROM gcbusiness.cuentacobrar WHERE id_comprobante = " + idcomprobantemodifica + " AND id_tipocomprobante = " + id_tipocomprobante_modifica;
                        rs = st.executeQuery(sql);
                        if (rs.next()) {
                            idcuentacobrar = rs.getInt("id_cuentacobrar");
                            saldoAnterior = rs.getDouble("saldo");
                        }

                        if (idcuentacobrar != 0) {
                            saldoActual = saldoAnterior + total_venta;

                            if (saldoActual <= 0) {
                                sql = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + ", estado = 'C' WHERE id_cuentacobrar = " + idcuentacobrar;
                            } else {
                                sql = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + ", estado = 'P' WHERE id_cuentacobrar = " + idcuentacobrar;
                            }
                            sqlEjecucion = sql;
                            st.executeUpdate(sql);

                            Integer idNovimientoCuentaCobrar = 0;
                            sql = "SELECT NEXTVAL('gcbusiness.movimiento_cuentacobrar_id_movimiento_cuentacobrar_seq')";
                            sqlEjecucion = sql;

                            rs = st.executeQuery(sql);

                            if (rs.next()) {
                                idNovimientoCuentaCobrar = rs.getInt(1);
                            }

                            String observacion = "ANULACION NOTA";

                            sql = "INSERT INTO gcbusiness.movimiento_cuentacobrar (id_movimiento_cuentacobrar, id_cuentacobrar,fecha,monto,saldo_anterior,saldo_actual,documento_referencia"
                                    + ",estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                                    + "VALUES (" + idNovimientoCuentaCobrar + ", " + idcuentacobrar + ",'" + ts + "'," + total_venta * (-1) + ", " + saldoAnterior + ", " + saldoActual + ", '" + observacion
                                    + "', 'A', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                            sqlEjecucion = sql;
                            st.executeUpdate(sql);
                        }
                        //FIN REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR  

                        //ACTUALIZA NOTA
                        sql = "UPDATE gcbusiness.nota SET estado = 'A', motivo_anulacion='" + motivoAnulacion + "', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                                + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_nota=" + idcomprobante;

                        sqlEjecucion = sql;
                        st.executeUpdate(sql);
                        flag_anulado = 1;
                    } else if (id_tiponota == 4) {
                        //REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR SI APLICA
                        Integer idcomprobantemodifica = 0;
                        sql = "SELECT id_venta FROM gcbusiness.venta "
                                + "WHERE id_tipocomprobante = " + id_tipocomprobante_modifica + " "
                                + "AND id_serie = " + id_serie_modifica + " "
                                + "AND correlativo_serie = " + correlativo_serie_modifica;

                        rs = st.executeQuery(sql);
                        if (rs.next()) {
                            idcomprobantemodifica = rs.getInt("id_venta");
                        }

                        Double saldoAnterior = 0.00, saldoActual = 0.00;
                        Integer idcuentacobrar = 0;
                        sql = "SELECT id_cuentacobrar, saldo FROM gcbusiness.cuentacobrar WHERE id_comprobante = " + idcomprobantemodifica + " AND id_tipocomprobante = " + id_tipocomprobante_modifica;
                        rs = st.executeQuery(sql);
                        if (rs.next()) {
                            idcuentacobrar = rs.getInt("id_cuentacobrar");
                            saldoAnterior = rs.getDouble("saldo");
                        }

                        if (idcuentacobrar != 0) {
                            saldoActual = saldoAnterior + total_venta;

                            if (saldoActual <= 0) {
                                sql = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + ", estado = 'C' WHERE id_cuentacobrar = " + idcuentacobrar;
                            } else {
                                sql = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + ", estado = 'P' WHERE id_cuentacobrar = " + idcuentacobrar;
                            }
                            sqlEjecucion = sql;
                            st.executeUpdate(sql);

                            Integer idNovimientoCuentaCobrar = 0;
                            sql = "SELECT NEXTVAL('gcbusiness.movimiento_cuentacobrar_id_movimiento_cuentacobrar_seq')";
                            sqlEjecucion = sql;

                            rs = st.executeQuery(sql);

                            if (rs.next()) {
                                idNovimientoCuentaCobrar = rs.getInt(1);
                            }

                            String observacion = "ANULACION NOTA";

                            sql = "INSERT INTO gcbusiness.movimiento_cuentacobrar (id_movimiento_cuentacobrar, id_cuentacobrar,fecha,monto,saldo_anterior,saldo_actual,documento_referencia"
                                    + ",estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                                    + "VALUES (" + idNovimientoCuentaCobrar + ", " + idcuentacobrar + ",'" + ts + "'," + total_venta * (-1) + ", " + saldoAnterior + ", " + saldoActual + ", '" + observacion
                                    + "', 'A', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                            sqlEjecucion = sql;
                            st.executeUpdate(sql);
                        }
                        //FIN REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR  

                        //ACTUALIZA NOTA
                        sql = "UPDATE gcbusiness.nota SET estado = 'A', motivo_anulacion='" + motivoAnulacion + "', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                                + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_nota=" + idcomprobante;

                        sqlEjecucion = sql;
                        st.executeUpdate(sql);
                        flag_anulado = 1;
                    }
                } else if (codTipoCmp.equals("99")) {//SI ES DEVOLUCION NOTA PEDIDO
                    //RETORNO DE INVENTARIO SOLO AL ANULAR NOTA DE CREDITO
                    //OBTENEMOS IDALMACEN PARA SABER A QUE ALMACEN RETORNARA LOS PRODUCTOS 
                    Integer id_almacen = 0, id_tiponota = 0, id_tipocomprobante_modifica = 0, id_serie_modifica = 0, correlativo_serie_modifica = 0;
                    Double total_venta = 0.00;
                    String sql = "SELECT n.id_almacen, n.id_tiponota, n.id_tipocomprobante_modifica, n.id_serie_modifica, n.correlativo_serie_modifica, n.total_venta, s.serie, n.correlativo_serie "
                            + "FROM gcbusiness.nota n "
                            + "LEFT JOIN gcbusiness.serie s ON s.id_serie=n.id_serie "
                            + "WHERE n.id_nota=" + idcomprobante;
                    sqlEjecucion = sql;
                    ResultSet rs = st.executeQuery(sql);

                    if (rs.next()) {
                        id_almacen = rs.getInt("id_almacen");
                        id_tiponota = rs.getInt("id_tiponota");
                        id_tipocomprobante_modifica = rs.getInt("id_tipocomprobante_modifica");
                        id_serie_modifica = rs.getInt("id_serie_modifica");
                        correlativo_serie_modifica = rs.getInt("correlativo_serie_modifica");
                        total_venta = rs.getDouble("total_venta");
                        serieobs = rs.getString("serie");
                        correlativoserieobs = String.valueOf(rs.getInt("correlativo_serie"));
                    }

                    if (id_tiponota == 12) {
                        //INSERTAMOS CABECERA MOVIMIENTO INVENTARIO
                        int id_movimientoinventario = 0;
                        sql = "SELECT NEXTVAL('gcbusiness.movimientoinventario_id_movimientoinventario_seq')";
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        if (rs.next()) {
                            id_movimientoinventario = rs.getInt(1);
                        }

                        sql = "INSERT INTO gcbusiness.movimientoinventario(id_movimientoinventario, id_almacen, id_motivomovimiento, fecha, observacion, id_referencia, estado, "
                                + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                + "VALUES (" + id_movimientoinventario + ", " + id_almacen + ", 18, '" + ts + "', 'ANULACION COMPROBANTE " + serieobs + "-" + correlativoserieobs + "', " + idcomprobante + ", 'A', "
                                + "'" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                        sqlEjecucion = sql;
                        st.executeUpdate(sql);

                        //OBTENEMOS IDPRODUCTO Y CANTIDAD PARA RETORNAR AL ALMACEN 
                        Integer idProducto = 0, idLote = null;
                        Double cantidad;
                        sql = "SELECT id_producto, id_lote, cantidad FROM gcbusiness.detalle_nota WHERE id_nota=" + idcomprobante;
                        sqlEjecucion = sql;
                        rs = st.executeQuery(sql);

                        while (rs.next()) {
                            idProducto = rs.getInt("id_producto");
                            idLote = rs.getInt("id_lote");
                            cantidad = rs.getDouble("cantidad");

                            //OBTENEMOS STOCK ACTUAL DEL PRODUCTO Y DEL ALMACEN CORRESPONDIENTE 
                            Double stockActual = 0.00;
                            if (idLote != 0) {
                                sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen;
                            } else {
                                sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen;
                                idLote = null;
                            }

                            sqlEjecucion = sql;

                            ResultSet rs1 = st1.executeQuery(sql);
                            if (rs1.next()) {
                                stockActual = rs1.getDouble("stock_actual");
                            }

                            Double nuevoStock = stockActual - cantidad;

                            //INSERTAMOS DETALLE MOVIMIENTO INVENTARIO
                            int id_detalle_movimientoinventario = 0;
                            sql = "SELECT NEXTVAL('gcbusiness.detalle_movimientoinventario_id_detalle_movimientoinventario_se')";
                            sqlEjecucion = sql;
                            rs1 = st1.executeQuery(sql);

                            if (rs1.next()) {
                                id_detalle_movimientoinventario = rs1.getInt(1);
                            }

                            sql = "INSERT INTO gcbusiness.detalle_movimientoinventario (id_detalle_movimientoinventario, id_movimientoinventario, id_producto, id_lote, cantidad, stock_saldo, "
                                    + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                    + "VALUES (" + id_detalle_movimientoinventario + ", " + id_movimientoinventario + ", " + idProducto + ", " + idLote + ", " + cantidad + ", " + nuevoStock
                                    + ", '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                            sqlEjecucion = sql;
                            st1.executeUpdate(sql);

                            //ACTUALIZAMOS STOCK 
                            if (idLote != null) {
                                sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen;
                            } else {
                                sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen;
                            }

                            sqlEjecucion = sql;
                            st1.executeUpdate(sql);
                        }

                        //REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR SI APLICA
                        Integer idcomprobantemodifica = 0;
                        sql = "SELECT id_venta FROM gcbusiness.venta "
                                + "WHERE id_tipocomprobante = " + id_tipocomprobante_modifica + " "
                                + "AND id_serie = " + id_serie_modifica + " "
                                + "AND correlativo_serie = " + correlativo_serie_modifica;

                        rs = st.executeQuery(sql);
                        if (rs.next()) {
                            idcomprobantemodifica = rs.getInt("id_venta");
                        }

                        Double saldoAnterior = 0.00, saldoActual = 0.00;
                        Integer idcuentacobrar = 0;
                        sql = "SELECT id_cuentacobrar, saldo FROM gcbusiness.cuentacobrar WHERE id_comprobante = " + idcomprobantemodifica + " AND id_tipocomprobante = " + id_tipocomprobante_modifica;
                        rs = st.executeQuery(sql);
                        if (rs.next()) {
                            idcuentacobrar = rs.getInt("id_cuentacobrar");
                            saldoAnterior = rs.getDouble("saldo");
                        }

                        if (idcuentacobrar != 0) {
                            saldoActual = saldoAnterior + total_venta;

                            if (saldoActual <= 0) {
                                sql = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + ", estado = 'C' WHERE id_cuentacobrar = " + idcuentacobrar;
                            } else {
                                sql = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + ", estado = 'P' WHERE id_cuentacobrar = " + idcuentacobrar;
                            }
                            sqlEjecucion = sql;
                            st.executeUpdate(sql);

                            Integer idNovimientoCuentaCobrar = 0;
                            sql = "SELECT NEXTVAL('gcbusiness.movimiento_cuentacobrar_id_movimiento_cuentacobrar_seq')";
                            sqlEjecucion = sql;

                            rs = st.executeQuery(sql);

                            if (rs.next()) {
                                idNovimientoCuentaCobrar = rs.getInt(1);
                            }

                            String observacion = "ANULACION NOTA";

                            sql = "INSERT INTO gcbusiness.movimiento_cuentacobrar (id_movimiento_cuentacobrar, id_cuentacobrar,fecha,monto,saldo_anterior,saldo_actual,documento_referencia"
                                    + ",estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                                    + "VALUES (" + idNovimientoCuentaCobrar + ", " + idcuentacobrar + ",'" + ts + "'," + total_venta * (-1) + ", " + saldoAnterior + ", " + saldoActual + ", '" + observacion
                                    + "', 'A', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                            sqlEjecucion = sql;
                            st.executeUpdate(sql);
                        }
                        //FIN REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR  

                        //ACTUALIZA NOTA
                        sql = "UPDATE gcbusiness.nota SET estado = 'A', motivo_anulacion='" + motivoAnulacion + "', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                                + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_nota=" + idcomprobante;

                        sqlEjecucion = sql;
                        st.executeUpdate(sql);
                        flag_anulado = 1;
                    }
                }

                if (flag_anulado == 1) {
                    String qry = null;
                    ResultSet rs = null;
                    String tipocomtpobante = null;
                    String serie = null;
                    String correlativo = null;
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String fechageneracion = formatter.format(c.getTime());
                    String motivo = null;
                    if (codTipoCmp.equals("01")) {
                        qry = "select tc.codigo_sunat, s.serie codigo_serie, cast(v.correlativo_serie as varchar) correlativo_serie, v.motivo_anulacion "
                                + "from gcbusiness.venta v "
                                + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante = v.id_tipocomprobante "
                                + "left join gcbusiness.serie s on s.id_serie = v.id_serie "
                                + "where id_venta = " + idcomprobante;

                        rs = st.executeQuery(qry);

                        while (rs.next()) {
                            tipocomtpobante = rs.getString("codigo_sunat");
                            serie = rs.getString("codigo_serie");
                            correlativo = rs.getString("correlativo_serie");
                            motivo = rs.getString("motivo_anulacion");
                        }
                        //
                        try {
                            DefaultHttpClient httpClient = new DefaultHttpClient();
                            HttpPost postRequest = new HttpPost(
                                    "http://localhost:8084/WebServiceSunat21/rest/comprobante/comunicarBajaDesdeSistema");

                            StringEntity input = new StringEntity(tipocomtpobante + "|" + serie + "|" + correlativo + "|" + fechageneracion + "|" + motivo, "utf-8");

                            input.setContentType("application/json");
                            postRequest.setEntity(input);

                            HttpResponse httpResponse = httpClient.execute(postRequest);

                            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                                throw new RuntimeException("Failed : HTTP error code : "
                                        + httpResponse.getStatusLine().getStatusCode());
                            }

                            BufferedReader br = new BufferedReader(new InputStreamReader(
                                    (httpResponse.getEntity().getContent())));

                            String output;
                            String[] aux = null;
                            System.out.println("Output from Server .... \n");
                            while ((output = br.readLine()) != null) {
                                aux = output.split("\\|", 0);
                            }

                            httpClient.getConnectionManager().shutdown();

                        } catch (MalformedURLException e) {

                            e.printStackTrace();
                        } catch (IOException e) {

                            e.printStackTrace();

                        }
                        //
                    } else if (codTipoCmp.equals("03")) {
                        qry = "select tc.codigo_sunat, s.serie codigo_serie, cast(v.correlativo_serie as varchar) correlativo_serie, v.motivo_anulacion "
                                + "from gcbusiness.venta v "
                                + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante = v.id_tipocomprobante "
                                + "left join gcbusiness.serie s on s.id_serie = v.id_serie "
                                + "where id_venta = " + idcomprobante;

                        rs = st.executeQuery(qry);

                        while (rs.next()) {
                            tipocomtpobante = rs.getString("codigo_sunat");
                            serie = rs.getString("codigo_serie");
                            correlativo = rs.getString("correlativo_serie");
                        }
                        //
                        try {
                            DefaultHttpClient httpClient = new DefaultHttpClient();
                            HttpPost postRequest = new HttpPost(
                                    "http://localhost:8084/WebServiceSunat21/rest/comprobante/anularComprobanteDesdeSistema");

                            StringEntity input = new StringEntity(tipocomtpobante + "|" + serie + "|" + correlativo, "utf-8");

                            input.setContentType("application/json");
                            postRequest.setEntity(input);

                            HttpResponse httpResponse = httpClient.execute(postRequest);

                            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                                throw new RuntimeException("Failed : HTTP error code : "
                                        + httpResponse.getStatusLine().getStatusCode());
                            }

                            BufferedReader br = new BufferedReader(new InputStreamReader(
                                    (httpResponse.getEntity().getContent())));

                            String output;
                            String[] aux = null;
                            System.out.println("Output from Server .... \n");
                            while ((output = br.readLine()) != null) {
                                aux = output.split("\\|", 0);
                            }

                            httpClient.getConnectionManager().shutdown();

                        } catch (MalformedURLException e) {

                            e.printStackTrace();
                        } catch (IOException e) {

                            e.printStackTrace();

                        }
                        //
                    } else if (codTipoCmp.equals("07") || codTipoCmp.equals("08")) {
                        String notatipocomprobante = null;
                        //Obtener tipo comprobante referencia
                        qry = "select tc.codigo_sunat, s.serie codigo_serie, cast(v.correlativo_serie as varchar) correlativo_serie, tcr.codigo_sunat codigo_sunat_ref, v.motivo_anulacion "
                                + "from gcbusiness.nota v "
                                + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante = v.id_tipocomprobante "
                                + "left join gcbusiness.serie s on s.id_serie = v.id_serie "
                                + "left join gcbusiness.tipocomprobante tcr on tcr.id_tipocomprobante = v.id_tipocomprobante_modifica "
                                + "where id_nota = " + idcomprobante;

                        rs = st.executeQuery(qry);

                        while (rs.next()) {
                            tipocomtpobante = rs.getString("codigo_sunat");
                            serie = rs.getString("codigo_serie");
                            correlativo = rs.getString("correlativo_serie");
                            notatipocomprobante = rs.getString("codigo_sunat_ref");
                            motivo = rs.getString("motivo_anulacion");
                        }

                        if (notatipocomprobante.equals("01")) {
                            //
                            try {
                                DefaultHttpClient httpClient = new DefaultHttpClient();
                                HttpPost postRequest = new HttpPost(
                                        "http://localhost:8084/WebServiceSunat21/rest/comprobante/comunicarBajaDesdeSistema");

                                StringEntity input = new StringEntity(tipocomtpobante + "|" + serie + "|" + correlativo + "|" + fechageneracion + "|" + motivo, "utf-8");

                                input.setContentType("application/json");
                                postRequest.setEntity(input);

                                HttpResponse httpResponse = httpClient.execute(postRequest);

                                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                                    throw new RuntimeException("Failed : HTTP error code : "
                                            + httpResponse.getStatusLine().getStatusCode());
                                }

                                BufferedReader br = new BufferedReader(new InputStreamReader(
                                        (httpResponse.getEntity().getContent())));

                                String output;
                                String[] aux = null;
                                System.out.println("Output from Server .... \n");
                                while ((output = br.readLine()) != null) {
                                    aux = output.split("\\|", 0);
                                }

                                httpClient.getConnectionManager().shutdown();

                            } catch (MalformedURLException e) {

                                e.printStackTrace();
                            } catch (IOException e) {

                                e.printStackTrace();

                            }
                            //
                        } else if (notatipocomprobante.equals("03")) {
                            //
                            try {
                                DefaultHttpClient httpClient = new DefaultHttpClient();
                                HttpPost postRequest = new HttpPost(
                                        "http://localhost:8084/WebServiceSunat21/rest/comprobante/anularComprobanteDesdeSistema");

                                StringEntity input = new StringEntity(tipocomtpobante + "|" + serie + "|" + correlativo, "utf-8");

                                input.setContentType("application/json");
                                postRequest.setEntity(input);

                                HttpResponse httpResponse = httpClient.execute(postRequest);

                                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                                    throw new RuntimeException("Failed : HTTP error code : "
                                            + httpResponse.getStatusLine().getStatusCode());
                                }

                                BufferedReader br = new BufferedReader(new InputStreamReader(
                                        (httpResponse.getEntity().getContent())));

                                String output;
                                String[] aux = null;
                                System.out.println("Output from Server .... \n");
                                while ((output = br.readLine()) != null) {
                                    aux = output.split("\\|", 0);
                                }

                                httpClient.getConnectionManager().shutdown();

                            } catch (MalformedURLException e) {

                                e.printStackTrace();
                            } catch (IOException e) {

                                e.printStackTrace();

                            }
                            //
                        }
                    }

                    json += " \"mensaje\":\"<em>SE ANULÓ CORRECTAMENTE EL COMRPOBANTE</em>\" ";

                    cn.commit();
                }

            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                json += " \"mensaje\":\"<em>ERROR AL REALIZAR LA OPERACIÓN</em>\" ";
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

        } else if (opcion.equals("obtenerventa")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idventa = Integer.parseInt(request.getParameter("idventa"));
                DaoVentaImpl daoVentaImpl = new DaoVentaImpl();
                DTOVenta dtoVenta = daoVentaImpl.accionObtener(idventa);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idsucursal", dtoVenta.getIdsucursal());
                obj.put("idalmacen", dtoVenta.getIdalmacen());
                obj.put("idtipocomprobante", dtoVenta.getIdtipocomprobante());
                obj.put("tipocomprobante", dtoVenta.getAbreviaturacomprobante());
                obj.put("idserie", dtoVenta.getIdserie());
                obj.put("serie", dtoVenta.getSerie());
                obj.put("correlativoserie", dtoVenta.getCorrelativoserie());
                obj.put("idcliente", dtoVenta.getIdcliente());
                obj.put("idmoneda", dtoVenta.getIdmoneda());
                obj.put("flaggravada", dtoVenta.getFlag_gravada());
                obj.put("totaligv", dtoVenta.getTotal_igv());
                obj.put("totalisc", dtoVenta.getTotal_isc());
                obj.put("totalotrotributo", dtoVenta.getTotal_otrotributo());
                obj.put("totalimpuesto", dtoVenta.getTotal_impuesto());
                obj.put("tipodescuentoglobal", dtoVenta.getTipo_descuentoglobal());
                obj.put("montodescuentoglobal", dtoVenta.getMonto_descuentoglobal());
                obj.put("pctodescuentoglobal", dtoVenta.getPcto_descuentoglobal());
                obj.put("totalgravada", dtoVenta.getTotal_gravada());
                obj.put("totalinafecta", dtoVenta.getTotal_inafecta());
                obj.put("totalexonerada", dtoVenta.getTotal_exonerada());
                obj.put("totalgratuita", dtoVenta.getTotal_gratuita());
                obj.put("totalotrocargo", dtoVenta.getTotal_otrocargo());
                obj.put("totaldescuento", dtoVenta.getTotal_descuento());
                obj.put("totalventa", dtoVenta.getTotal_venta());

                System.out.println(obj.toJSONString());
                out.print(obj.toJSONString());
            }
        } else if (opcion.equals("obtenerdetalleventa")) {
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null;
            ResultSet rsLotes;
            String respuesta = "";
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);
            try {
                cn = db.getConnection();
                st = cn.createStatement();

                Integer idventa = Integer.parseInt(request.getParameter("idventa"));
                Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
                Integer idmotivonota = request.getParameter("idmotivonota") == null ? 7 : Integer.parseInt(request.getParameter("idmotivonota"));

                DaoVentaImpl daoVentaImpl = new DaoVentaImpl();
                List<DTODetalleVenta> lstDetalleVenta = daoVentaImpl.accionListarDTODetalleVenta(idventa);

                for (int i = 0; i < lstDetalleVenta.size(); i++) {
                    respuesta += "<tr class='detalleVenta'>";
                    Integer orden = i + 1;

                    if (idmotivonota == 7) {
                        respuesta += "<td style='display: none'>" + lstDetalleVenta.get(i).getIdproducto() + "</td>" //COLUMNA 00:  IdProducto
                                + "<td style='display: none'>" + orden + "</td>" //COLUMNA 01:  #
                                + "<td>" + lstDetalleVenta.get(i).getCodigoproducto() + "</td>" //COLUMNA 02:  Código
                                + "<td>" + lstDetalleVenta.get(i).getNombreproducto() + "</td>" //COLUMNA 03:  Descripción
                                + "<td> <input class='input_cantidad' id='cantidad_" + orden + "' type='number' value='" + lstDetalleVenta.get(i).getCantidad() + "' min='1' style='font-size:10px'></td>" //COLUMNA 04:  Cantidad
                                + "<td>" + lstDetalleVenta.get(i).getUnidadmedida() + "</td>";     //COLUMNA 05:  Medida
                    } else {
                        respuesta += "<td style='display: none'>" + lstDetalleVenta.get(i).getIdproducto() + "</td>" //COLUMNA 00:  IdProducto
                                + "<td style='display: none'>" + orden + "</td>" //COLUMNA 01:  #
                                + "<td>" + lstDetalleVenta.get(i).getCodigoproducto() + "</td>" //COLUMNA 02:  Código
                                + "<td>" + lstDetalleVenta.get(i).getNombreproducto() + "</td>" //COLUMNA 03:  Descripción
                                + "<td> <input class='input_cantidad' id='cantidad_" + orden + "' type='number' value='" + lstDetalleVenta.get(i).getCantidad() + "' min='1' style='font-size:10px' disabled></td>" //COLUMNA 04:  Cantidad
                                + "<td>" + lstDetalleVenta.get(i).getUnidadmedida() + "</td>";     //COLUMNA 05:  Medida  
                    }

                    String comboTarifas = "<td> <select class='select_tarifa' id='tarifa_" + orden + "' disabled>";
                    comboTarifas += "<option value='" + lstDetalleVenta.get(i).getPreciounitarioventa() + "' >" + Math.round(lstDetalleVenta.get(i).getPreciounitarioventa() * Math.pow(10, 2)) / Math.pow(10, 2) + "</option>";
                    comboTarifas += "</select>";

                    //COLUMNA 06:  Precio Unitario
                    respuesta += comboTarifas;

                    //COLUMNA 07:  Valor Unitario
                    respuesta += "<td style='display: none' class='valor_unitario' id='valor_uni_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getValorunitarioventa() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 08:  Precio Total
                    respuesta += "<td style='display: none' id='precio_tot_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getPrecioventa() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 09:  Valor Total
                    respuesta += "<td style='display: none' id='valor_tot_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getValorventa() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 10:  Afecto IGV
                    respuesta += "<td style='display: none' id='afecto_igv_" + orden + "' style='display: '>" + lstDetalleVenta.get(i).getAfectoigv() + "</td>";

                    //COLUMNA 11:  IGV
                    respuesta += "<td style='display: none' id='igv_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getMontoigv() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 12:  ISC 
                    respuesta += "<td style='display: none' id='isc_" + orden + "' style='display: '>" + Math.round(lstDetalleVenta.get(i).getMontoisc() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 13:  Precio Unitario con Descuento
                    respuesta += "<td style='display: none' style='display: ' id='precio_uni_dscto_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getPreciounitariodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 14:  Valor Unitario con Descuento
                    respuesta += "<td style='display: none' style='display: ' id='valor_uni_dscto_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getValorunitariodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 15:  Precio Total con Descuento
                    respuesta += "<td id='precio_tot_dscto_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getPrecioventadescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 16:  IGV con Descuento
                    respuesta += "<td id='igv_dscto_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getMontoigvdescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 17:  Valor Total con Descuento
                    respuesta += "<td style='display: none' id='valor_tot_dscto_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getValorventadescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 18:  Descuento
                    if (lstDetalleVenta.get(i).getTipodescuento().equals("P")) {
                        if (lstDetalleVenta.get(i).getPctodescuento() == 0) {
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px' disabled>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "' disabled>"
                                    + "<option value='P' selected>%</option>"
                                    //            + "<option value='M'>MONTO</option>"
                                    + "</select>\n"
                                    + "</td>";
                        } else {
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px' value='" + Math.round(lstDetalleVenta.get(i).getPctodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "' disabled>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "' disabled>"
                                    + "<option value='P' selected>%</option>"
                                    //            + "<option value='M'>MONTO</option>"
                                    + "</select>\n"
                                    + "</td>";
                        }
                    } else {
                        if (lstDetalleVenta.get(i).getMontodescuento() == 0) {
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px'>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                                    + "<option value='P'>%</option>"
                                    + "<option value='M' selected>MONTO</option>"
                                    + "</select>\n"
                                    + "</td>";
                        } else {
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px' value='" + Math.round(lstDetalleVenta.get(i).getMontodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "'>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                                    + "<option value='P'>%</option>"
                                    + "<option value='M' selected>MONTO</option>"
                                    + "</select>\n"
                                    + "</td>";
                        }
                    }

                    //COLUMNA 19:  Descuento Porcentaje
                    respuesta += "<td style='display: none' id='dscto_porc_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getPctodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 20:  Descuento Monto
                    respuesta += "<td style='display: none' id='dscto_mont_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getMontodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";
                    System.out.println("LOTE: " + lstDetalleVenta.get(i).getIdlote());
                    if (lstDetalleVenta.get(i).getIdlote() != 0) {
                        String query = "SELECT l.id_lote, l.numero_lote, l.fecha_vencimiento, a.stock_actual from gcbusiness.lote l, gcbusiness.almacenproductolote a "
                                + "WHERE l.id_lote = a.id_lote AND a.id_producto = l.id_producto AND a.id_producto = " + lstDetalleVenta.get(i).getIdproducto()
                                + " AND a.id_almacen = " + idalmacen + " AND l.id_lote = " + lstDetalleVenta.get(i).getIdlote();

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
                        //respuesta += "<td id='stock_" + orden + "'>" + Math.round(listaStock.get(0) * Math.pow(10, 3)) / Math.pow(10, 3) + "</td>";
                        respuesta += "<td id='stock_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getCantidad() * Math.pow(10, 3)) / Math.pow(10, 3) + "</td>";

                    } else {
                        String query = "SELECT stock_actual from gcbusiness.almacenproductolote "
                                + "WHERE id_producto = " + lstDetalleVenta.get(i).getIdproducto()
                                + " AND id_almacen = " + idalmacen;

                        rsLotes = st.executeQuery(query);
                        String comboLotes = "<td>No aplica</td>";
                        Double stockActual = null;
                        if (rsLotes.next()) {
                            stockActual = rsLotes.getDouble("stock_actual");
                        }
                        //COLUMNA 21:  Lote|F.V.
                        respuesta += comboLotes;

                        //COLUMNA 22:  Stock Actual
                        //respuesta += "<td id='stock_" + orden + "'>" + Math.round(stockActual * Math.pow(10, 3)) / Math.pow(10, 3) + "</td>";
                        respuesta += "<td id='stock_" + orden + "'>" + Math.round(lstDetalleVenta.get(i).getCantidad() * Math.pow(10, 3)) / Math.pow(10, 3) + "</td>";
                    }

                    //COLUMNA 23:  Bonificación
                    if (lstDetalleVenta.get(i).getFlagbonificacion().equals("S")) {
                        respuesta += "<td> "
                                + "<input id='bonificacion_" + orden + "' class='ace ace-switch bonificacion' type='checkbox' checked disabled/>"
                                + "<span class='lbl' data-lbl=\"SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NO\" ></span>"
                                + "</td>";
                    } else if (lstDetalleVenta.get(i).getFlagbonificacion().equals("N")) {
                        respuesta += "<td> "
                                + "<input id='bonificacion_" + orden + "' class='ace ace-switch bonificacion' type='checkbox' disabled/>"
                                + "<span class='lbl' data-lbl=\"SI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NO\" ></span>"
                                + "</td>";
                    }

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
        } else if (opcion.equals("obtenernotas")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idtipocomprobante = Integer.parseInt(request.getParameter("idtipocomprobante"));
                Integer idserie = Integer.parseInt(request.getParameter("idserie"));
                Integer correlativoserie = Integer.parseInt(request.getParameter("correlativoserie"));
                DaoComprobanteImpl daoComprobanteImpl = new DaoComprobanteImpl();
                List<DTOComprobante> listComprobante = daoComprobanteImpl.accionObtenerDocumentoRelacionado(idtipocomprobante, idserie, correlativoserie);

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listComprobante.size(); i++) {
                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("tipocomprobante", listComprobante.get(i).getAbreviaturacomprobante());
                    obj.put("serie", listComprobante.get(i).getSerie());
                    obj.put("correlativo", listComprobante.get(i).getCorrelativoserie());
                    datos.add(obj);
                }
                System.out.println(" {\"data\":" + datos.toJSONString() + "} ");
                out.print(" {\"data\":" + datos.toJSONString() + "} ");
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
