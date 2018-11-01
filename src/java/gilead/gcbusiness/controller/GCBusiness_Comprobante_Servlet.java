package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoComprobanteImpl;
import gilead.gcbusiness.dao.impl.DaoVentaImpl;
import gilead.gcbusiness.dto.DTOComprobante;
import gilead.gcbusiness.dto.DTODetalleVenta;
import gilead.gcbusiness.dto.DTOVenta;
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

                    acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                            + "<a href=\"GC-Business-NotaCredito.jsp?idventa=" + listComprobante.get(i).getIdventa() + "\" class=\"btn btn-xs btn-info imprimir\"><i class=\"ace-icon fa fa-pencil-square-o bigger-120\" title='Nota Crédito'></i></a>";

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("fecha", dateFormat.format(listComprobante.get(i).getFecha_emision().getTime()));
                    obj.put("tipocomprobante", listComprobante.get(i).getAbreviaturacomprobante());
                    obj.put("serie", listComprobante.get(i).getSerie());
                    obj.put("correlativo", listComprobante.get(i).getCorrelativoserie());
                    obj.put("nrodocumento", listComprobante.get(i).getNumerodocumentocliente());
                    obj.put("cliente", listComprobante.get(i).getNombrecliente());
                    obj.put("moneda", listComprobante.get(i).getCodigoSunatMoneda());
                    obj.put("total", listComprobante.get(i).getTotal_venta());
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
                List<DTOComprobante> listComprobante = daoComprobanteImpl.accionListarDTOComprobanteParaAnular(numeroComprobante, tipoComprobante, fecha_desde, fecha_hasta);

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);

                for (int i = 0; i < listComprobante.size(); i++) {
                    String acciones = "";

                    if (accion.equals("anular")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='anular' id='" + listComprobante.get(i).getIdventa() + " | " + listComprobante.get(i).getCodigoSunatcomprobante() + "' class='btn btn-danger btn-xs anular' title='Anular'><span class='glyphicon glyphicon-trash'></span></button>"
                                + "</div>";
                    } else if (accion.equals("visualizar")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='imprimir' id='" + listComprobante.get(i).getIdventa() + " | " + listComprobante.get(i).getCodigoSunatcomprobante() + "' class='btn btn-info btn-xs imprimir' title='Imprimir'><span class='glyphicon glyphicon-print'></span></button>"
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
            String json = null;
            try {
                cn = db.getConnection();
                cn.setAutoCommit(false);
                st = cn.createStatement();
                st1 = cn.createStatement();

                Integer idcomprobante = Integer.parseInt(request.getParameter("idcomprobante").trim());
                String codTipoCmp = request.getParameter("codTipoCmp").trim();
                String motivoAnulacion = request.getParameter("motivo").trim();
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");

                if (codTipoCmp.equals("01") || codTipoCmp.equals("03")) {//SI ES FACTURA O BOLETA
                    //RETORNO DE INVENTARIO SOLO AL ANULAR BOLETA O FACTURA
                    //OBTENEMOS IDALMACEN PARA SABER A QUE ALMACEN RETORNARA LOS PRODUCTOS 
                    Integer id_almacen = 0;
                    String sql = "SELECT id_almacen FROM gcbusiness.venta WHERE id_venta=" + idcomprobante;
                    sqlEjecucion = sql;
                    ResultSet rs = st.executeQuery(sql);

                    if (rs.next()) {
                        id_almacen = rs.getInt("id_almacen");
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
                            + "VALUES (" + id_movimientoinventario + ", " + id_almacen + ", 4, '" + ts + "', 'ANULACION COMPROBANTE', " + idcomprobante + ", 'A', "
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
                            sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen + ";";
                        } else {
                            sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen + ";";
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
                            sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen + ";";
                        } else {
                            sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen + ";";
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

                } else if (codTipoCmp.equals("00")) {//SI ES NOTA DE PEDIDO
                    //RETORNO DE INVENTARIO SOLO AL ANULAR NOTA DE PEDIDO
                    //OBTENEMOS IDALMACEN PARA SABER A QUE ALMACEN RETORNARA LOS PRODUCTOS 
                    Integer id_almacen = 0;
                    String sql = "SELECT id_almacen FROM gcbusiness.venta WHERE id_venta=" + idcomprobante;
                    sqlEjecucion = sql;
                    ResultSet rs = st.executeQuery(sql);

                    if (rs.next()) {
                        id_almacen = rs.getInt("id_almacen");
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
                            + "VALUES (" + id_movimientoinventario + ", " + id_almacen + ", 5, '" + ts + "', 'ANULACION COMPROBANTE', " + idcomprobante + ", 'A', "
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
                            sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen + ";";
                        } else {
                            sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen + ";";
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
                            sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen + ";";
                        } else {
                            sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen + ";";
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

                } else if (codTipoCmp.equals("07") || codTipoCmp.equals("08")) {//SI ES NOTA DE CREDITO O DEBITO
                    //RETORNO DE INVENTARIO SOLO AL ANULAR NOTA DE CREDITO
                    //OBTENEMOS IDALMACEN PARA SABER A QUE ALMACEN RETORNARA LOS PRODUCTOS 
                    Integer id_almacen = 0;
                    String sql = "SELECT id_almacen FROM gcbusiness.nota WHERE id_nota=" + idcomprobante;
                    sqlEjecucion = sql;
                    ResultSet rs = st.executeQuery(sql);

                    if (rs.next()) {
                        id_almacen = rs.getInt("id_almacen");
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
                            + "VALUES (" + id_movimientoinventario + ", " + id_almacen + ", 13, '" + ts + "', 'ANULACION COMPROBANTE', " + idcomprobante + ", 'A', "
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
                            sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen + ";";
                        } else {
                            sql = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen + ";";
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
                            sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_lote=" + idLote + " AND id_almacen=" + id_almacen + ";";
                        } else {
                            sql = "UPDATE gcbusiness.almacenproductolote SET stock_actual=" + nuevoStock + " WHERE id_producto=" + idProducto + " AND id_almacen=" + id_almacen + ";";
                        }

                        sqlEjecucion = sql;
                        st1.executeUpdate(sql);
                    }

                    //ACTUALIZA CUENTA COBRAR SI HAY CUENTA POR COBRAR
//                    sql = "UPDATE gcbusiness.cuentacobrar SET estado = 'A', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
//                            + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_comprobante=" + idcomprobante;
//                    sqlEjecucion = sql;
//                    st.executeUpdate(sql);
                    //ACTUALIZA VENTA
                    sql = "UPDATE gcbusiness.nota SET estado = 'A', motivo_anulacion='" + motivoAnulacion + "', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                            + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_nota=" + idcomprobante;

                    sqlEjecucion = sql;
                    st.executeUpdate(sql);

                }

                json = "{ \"mensaje\":\"<em>SE ANULÓ CORRECTAMENTE EL COMRPOBANTE</em>\" ";

                cn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Producto_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                json = "{ \"mensaje\":\"<em>ERROR AL REALIZAR LA OPERACIÓN</em>\" ";
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

                DaoVentaImpl daoVentaImpl = new DaoVentaImpl();
                List<DTODetalleVenta> lstDetalleVenta = daoVentaImpl.accionListarDTODetalleVenta(idventa);

                for (int i = 0; i < lstDetalleVenta.size(); i++) {
                    respuesta += "<tr class='detalleVenta'>";
                    Integer orden = i + 1;

                    respuesta += "<td style='display: none'>" + lstDetalleVenta.get(i).getIdproducto() + "</td>" //COLUMNA 00:  IdProducto
                            + "<td style='display: none'>" + orden + "</td>" //COLUMNA 01:  #
                            + "<td>" + lstDetalleVenta.get(i).getCodigoproducto() + "</td>" //COLUMNA 02:  Código
                            + "<td>" + lstDetalleVenta.get(i).getNombreproducto() + "</td>" //COLUMNA 03:  Descripción
                            + "<td> <input class='input_cantidad' id='cantidad_" + orden + "' type='number' value='" + lstDetalleVenta.get(i).getCantidad() + "' min='1' max='100' style='font-size:10px'></td>" //COLUMNA 04:  Cantidad
                            + "<td>" + lstDetalleVenta.get(i).getUnidadmedida() + "</td>";     //COLUMNA 05:  Medida

                    String comboTarifas = "<td> <select class='select_tarifa' id='tarifa_" + orden + "'>";
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
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px'>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                                    + "<option value='P' selected>%</option>"
                                    + "<option value='M'>MONTO</option>"
                                    + "</select>\n"
                                    + "</td>";
                        } else {
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px' value='" + Math.round(lstDetalleVenta.get(i).getPctodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "'>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                                    + "<option value='P' selected>%</option>"
                                    + "<option value='M'>MONTO</option>"
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
                        respuesta += "<td id='stock_" + orden + "'>" + Math.round(listaStock.get(0) * Math.pow(10, 3)) / Math.pow(10, 3) + "</td>";

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
                        respuesta += "<td id='stock_" + orden + "'>" + Math.round(stockActual * Math.pow(10, 3)) / Math.pow(10, 3) + "</td>";
                    }

                    //COLUMNA 23:  Bonificación
                    respuesta += "<td> "
                            + "<input id='bonificacion_" + orden + "' class='ace ace-switch bonificacion' type='checkbox' value='" + lstDetalleVenta.get(i).getFlagbonificacion() + "'/>"
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
