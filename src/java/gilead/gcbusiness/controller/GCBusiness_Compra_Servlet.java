package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoCompraImpl;
import gilead.gcbusiness.dto.DTOCompra;
import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GCBusiness_Compra_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);

        String opcion = request.getParameter("opcion");
        System.out.println("Entro GESTION COMPRA");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                String fecha_desde = !request.getParameter("desde").equals("") ? (String) request.getParameter("desde") : "01/01/1990";
                String fecha_hasta = !request.getParameter("hasta").equals("") ? (String) request.getParameter("hasta") : "12/12/9999";
                String numeroComprobante = request.getParameter("nroComp") != null ? (String) request.getParameter("nroComp") : "";
                String tipoComprobante = request.getParameter("tipoComp") != null ? (String) request.getParameter("tipoComp") : "0";
                DaoCompraImpl daoCompraImpl = new DaoCompraImpl();
                List<DTOCompra> listCompras = daoCompraImpl.accionListarDTOCompraParaAnular(numeroComprobante, tipoComprobante, fecha_desde, fecha_hasta);
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listCompras.size(); i++) {
                    String acciones = "";

                    if (listCompras.get(i).getEstado().equals("E") && dateFormat.format(listCompras.get(i).getFecha_emision().getTime()).equals(dateFormat.format(ts.getTime()))) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='imprimir' id='" + listCompras.get(i).getIdcompra() + " | " + listCompras.get(i).getCodigoSunatcomprobante() + "' class='btn btn-xs btn-info imprimir' title='Imprimir'><span class='glyphicon glyphicon-print'></span></button>"
                                + "<button type='button' name='anular' id='" + listCompras.get(i).getIdcompra() + " | " + listCompras.get(i).getCodigoSunatcomprobante() + "' class='btn btn-xs btn-danger anular' title='Anular'><span class='glyphicon glyphicon-trash'></span></button>"
                                + "</div>";

                    }
                    if (listCompras.get(i).getEstado().equals("A") || (listCompras.get(i).getEstado().equals("E") && !dateFormat.format(listCompras.get(i).getFecha_emision().getTime()).equals(dateFormat.format(ts.getTime())))) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='imprimir' id='" + listCompras.get(i).getIdcompra() + " | " + listCompras.get(i).getCodigoSunatcomprobante() + "' class='btn btn-xs btn-info imprimir' title='Imprimir'><span class='glyphicon glyphicon-print'></span></button>"
                                + "</div>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("fecha", dateFormat.format(listCompras.get(i).getFecha_emision().getTime()));
                    obj.put("tipocomprobante", listCompras.get(i).getAbreviaturacomprobante());
                    obj.put("serie", listCompras.get(i).getSerie());
                    obj.put("correlativo", listCompras.get(i).getCorrelativoserie());
                    obj.put("nrodocumento", listCompras.get(i).getNumerodocumentoproveedor());
                    obj.put("proveedor", listCompras.get(i).getNombreproveedor());
                    obj.put("moneda", listCompras.get(i).getCodigoSunatMoneda());
                    obj.put("total", listCompras.get(i).getTotal_compra());
                    obj.put("estado", listCompras.get(i).getEstado().equals("E") ? "EMITIDO" : "ANULADO");
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        } else if (opcion.equals("insertar")) {
            System.out.println("INSERTAR COMPRA");
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

                Integer idCompra = 0;
                String query = "SELECT NEXTVAL('gcbusiness.compra_id_compra_seq')";
                sqlEjecucion = query;

                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    idCompra = rs.getInt(1);
                }

                Integer idproveedor = Integer.parseInt(request.getParameter("idproveedor"));
                Integer idtipoComprobante = Integer.parseInt(request.getParameter("idtipoComprobante"));
                String serie = request.getParameter("serie").toUpperCase();
                Integer correlativo = Integer.parseInt(request.getParameter("correlativo"));
                String flag_negociable = request.getParameter("flag_negociable");
                String fecha_vencimiento = request.getParameter("fecha_vencimiento");
                if (flag_negociable.equals("N")) {
                    fecha_vencimiento = null;
                }

                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));

                String formapago = request.getParameter("formapago");

                //LOGICA PARA FORMA DE PAGO
                Double importe_pago = Double.parseDouble(request.getParameter("importe_pago"));
                Double monto_efectivo = Double.parseDouble(request.getParameter("monto_efectivo"));
                Double monto_otro = Double.parseDouble(request.getParameter("monto_otro"));
                String referencia_otro = request.getParameter("referencia_otro");
                Double cambio_pago = Double.parseDouble(request.getParameter("cambio_pago"));
                //FIN LOGICA FORMA DE PAGO

                String estatuspago = request.getParameter("estatuspago");
                Double montopagado = 0.00;

                String tipo_descuentoglobal = request.getParameter("tipo_dctoglobal");
                Double pcto_dctoglobal = Double.parseDouble(request.getParameter("pcto_dctoglobal"));
                Double monto_dctoglobal = Double.parseDouble(request.getParameter("monto_dctoglobal"));

                Double total_gravada = Double.parseDouble(request.getParameter("total_gravada"));
                Double total_inafecta = Double.parseDouble(request.getParameter("total_inafecta"));
                Double total_exonerada = Double.parseDouble(request.getParameter("total_exonerada"));
                Double total_gratuita = Double.parseDouble(request.getParameter("total_gratuita"));
                Double total_impuesto = Double.parseDouble(request.getParameter("total_impuesto"));
                Double total_igv = Double.parseDouble(request.getParameter("total_igv"));
                Double total_isc = Double.parseDouble(request.getParameter("total_isc"));
                Double total_otrotributo = Double.parseDouble(request.getParameter("total_otrotributo"));
                Double total_valorcompra = Double.parseDouble(request.getParameter("total_valorcompra"));
                Double total_preciocompra = Double.parseDouble(request.getParameter("total_preciocompra"));
                Double total_descuento = Double.parseDouble(request.getParameter("total_descuento"));
                Double total_otrocargo = request.getParameter("total_otrocargo").equals("") ? 0.00 : Double.parseDouble(request.getParameter("total_otrocargo"));
                Double total_compra = Double.parseDouble(request.getParameter("total_compra"));

                //INICIO LOGICA PARA ANTICIPO
                Double total_anticipo = Double.parseDouble(request.getParameter("total_anticipo"));;
                //FIN LOGICA ANTICIPO 

                Integer idsucursal = Integer.parseInt((String) session.getAttribute("idSucursal"));
                Integer idalmacen = Integer.parseInt((String) session.getAttribute("idAlmacen"));
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");

                //REGISTRAR COMPRA
                query = "INSERT INTO gcbusiness.compra (id_compra, id_proveedor, id_tipocomprobante, id_sucursal, id_almacen, serie_comprobante, correlativo_serie, fecha_emision, fecha_recepcion, flag_negociable, "
                        + "fecha_vencimiento, id_moneda, id_formapago, id_estatuspago, tipo_descuentoglobal, monto_descuentoglobal, pcto_descuentoglobal, total_gravada, total_inafecta, "
                        + "total_exonerada, total_gratuita, total_impuesto, total_igv, total_isc, total_otrotributo, total_valorcompra, total_preciocompra, total_descuento, total_otrocargo, total_anticipo, "
                        + "total_compra, importe_pago, monto_efectivo, monto_otro, referencia_otro, cambio_pago, estado, motivo_anulacion, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                        + " VALUES (" + idCompra + ", " + idproveedor + ", " + idtipoComprobante + ", " + idsucursal + ", " + idalmacen + ", '" + serie + "', " + correlativo + ", '" + ts + "', '" + ts + "', '" + flag_negociable + "', "
                        + fecha_vencimiento + ", " + idmoneda + ", '" + formapago + "', '" + estatuspago + "', '" + tipo_descuentoglobal + "', " + monto_dctoglobal + ", " + pcto_dctoglobal + ", " + total_gravada + ", " + total_inafecta + ", "
                        + total_exonerada + ", " + total_gratuita + ", " + total_impuesto + ", " + total_igv + ", " + total_isc + ", " + total_otrotributo + ", " + total_valorcompra + ", " + total_preciocompra + ", " + total_descuento + ", " + total_otrocargo + ", " + total_anticipo + ", "
                        + total_compra + ", " + importe_pago + ", " + monto_efectivo + ", " + monto_otro + ", '" + referencia_otro + "', " + cambio_pago + ", 'E', null, '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                st.executeUpdate(query);
                //FIN REGISTRAR COMPRA

                //INICIO REGISTRAR MOVIMIENTO INVENTARIO 
                String observacion = serie + "-" + correlativo;

                Integer idMovimientoInventario = 0;
                query = "SELECT NEXTVAL('gcbusiness.movimientoinventario_id_movimientoinventario_seq')";
                sqlEjecucion = query;

                rs = st.executeQuery(query);

                if (rs.next()) {
                    idMovimientoInventario = rs.getInt(1);
                }

                query = "INSERT INTO gcbusiness.movimientoinventario (id_movimientoinventario, id_almacen, id_motivomovimiento, fecha, observacion, id_referencia, estado,"
                        + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                        + " VALUES (" + idMovimientoInventario + ", " + idalmacen + ", 2 , '" + ts + "', '" + observacion + "', " + idCompra + ", 'A',"
                        + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                st.executeUpdate(query);
                //FIN REGISTRAR MOVIMIENTO INVENTARIO

                //INICIO REGISTRAR DETALLE COMPRA Y DETALLE MOVIMIENTO INVENTARIO
                String detalleCompra = request.getParameter("detallecompra");
                JSONArray arrayDetalleCompra;
                arrayDetalleCompra = new JSONArray(detalleCompra);

                Integer idProducto = 0;
                Double cantidad = 0.00, pctoIGV = 0.00, valor_unitario_compra = 0.00, precio_unitario_compra = 0.00, valor_compra = 0.00, precio_compra = 0.00, monto_igv = 0.00, monto_isc = 0.00,
                        monto_descuento = 0.00, pcto_descuento = 0.00, valor_compra_descuento = 0.00, precio_compra_descuento = 0.00, monto_igv_descuento = 0.00,
                        stock_actual = 0.000, precio_unitario_compra_descuento = 0.00;
                String tipoIGV = "", tipoISC = "", flag_bonificacion = "", descuento = "", tipo_descuento = "", afectoIGV = "";
                for (int i = 0; i < arrayDetalleCompra.length(); i++) {
                    JSONObject fila1 = null;
                    fila1 = arrayDetalleCompra.getJSONObject(i);

                    idProducto = Integer.parseInt((String) fila1.get("IdProducto"));

                    cantidad = Double.parseDouble((String) fila1.get("Cantidad"));

                    valor_unitario_compra = Double.parseDouble((String) fila1.get("Valor Unitario"));
                    //valor_compra = valor_unitario_compra * cantidad;
                    //valor_compra = Math.round(valor_compra * Math.pow(10, 2)) / Math.pow(10, 2);

                    precio_unitario_compra = Double.parseDouble((String) fila1.get("Precio Unitario"));
                    //precio_compra = precio_unitario_compra * cantidad;
                    //precio_compra = Math.round(precio_compra * Math.pow(10, 2)) / Math.pow(10, 2);
                    precio_compra = Double.parseDouble((String) fila1.get("Precio Total sDcto"));

                    //valor_compra = precio_compra / 1.18;
                    //valor_compra = Math.round(valor_compra * Math.pow(10, 2)) / Math.pow(10, 2);
                    valor_compra = Double.parseDouble((String) fila1.get("Valor Total sDcto"));

                    monto_igv = Double.parseDouble((String) fila1.get("IGV sDcto"));
                    //monto_igv = precio_compra - valor_compra;
                    //monto_igv = Math.round(monto_igv * Math.pow(10, 2)) / Math.pow(10, 2);

                    //FALTA TIPO ISC
                    monto_isc = Double.parseDouble((String) fila1.get("ISC"));

                    afectoIGV = (String) fila1.get("Tipo Afectación");
                    flag_bonificacion = (String) fila1.get("Bonificación");

                    //TIPO IGV Y PCTO IGV
                    if (flag_bonificacion.equals("S")) {
                        /*if (afectoIGV.trim().equals("G")) {
                            //tipoIGV = "15"; //Gravado – Bonificaciones
                            tipoIGV = "G";
                            pctoIGV = (precio_compra - valor_compra) / valor_compra;
                            pctoIGV *= 100;
                            pctoIGV = Math.round(pctoIGV * Math.pow(10, 2)) / Math.pow(10, 2);
                        }*/
                        if (afectoIGV.trim().equals("E")) {
                            //tipoIGV = "21"; //Exonerado – Transferencia Gratuita
                            tipoIGV = "E";
                        }
                        /*if (afectoIGV.trim().equals("I")) {
                            //tipoIGV = "31"; //Inafecto – Retiro por Bonificación
                            tipoIGV = "I";
                        }*/
                    } else {
                        if (afectoIGV.trim().equals("G")) {
                            //tipoIGV = "10"; //Gravado – Operación Onerosa
                            tipoIGV = "G";
                            pctoIGV = (precio_compra - valor_compra) / valor_compra;
                            pctoIGV *= 100;
                            pctoIGV = Math.round(pctoIGV * Math.pow(10, 2)) / Math.pow(10, 2);
                        }
                        if (afectoIGV.trim().equals("E")) {
                            //tipoIGV = "20"; //Exonerado – Operación Onerosa
                            tipoIGV = "E";
                        }
                        if (afectoIGV.trim().equals("I")) {
                            //tipoIGV = "30"; //Inafecto – Operación Onerosa
                            tipoIGV = "I";
                        }
                    }

                    //DESCUENTO
                    descuento = (String) fila1.get("Descuento");
                    String[] detalleDcto = descuento.split(" | ");

                    tipo_descuento = detalleDcto[2].trim();

                    if (tipo_descuento.equals("P")) {
                        if (detalleDcto[0] != null && detalleDcto[0].length() > 0 && detalleDcto[0] != "") {
                            pcto_descuento = Double.parseDouble(detalleDcto[0]);
                        }
                        //pcto_descuento = detalleDcto[0]==null?0.00:Double.parseDouble(detalleDcto[0].trim());
                        //valor_compra_descuento = valor_compra*(1 - pcto_descuento/100);
                    } else {
                        if (detalleDcto[0] != null && detalleDcto[0].length() > 0 && detalleDcto[0] != "") {
                            monto_descuento = Double.parseDouble(detalleDcto[0]);
                        }
                        //monto_descuento = detalleDcto[0]==null?0.00:Double.parseDouble(detalleDcto[0].trim());
                        //valor_compra_descuento = valor_compra - monto_descuento;
                    }

                    valor_compra_descuento = Double.parseDouble((String) fila1.get("Valor Compra"));
                    //valor_compra_descuento = Math.round(valor_compra_descuento * Math.pow(10, 2)) / Math.pow(10, 2);

                    precio_compra_descuento = Double.parseDouble((String) fila1.get("Precio Compra"));
                    //precio_compra_descuento = Math.round(precio_compra_descuento * Math.pow(10, 2)) / Math.pow(10, 2);

                    //precio_unitario_compra_descuento = precio_compra_descuento / cantidad;
                    //precio_unitario_compra_descuento = Math.round(precio_unitario_compra_descuento * Math.pow(10, 2)) / Math.pow(10, 2);
                    precio_unitario_compra_descuento = Double.parseDouble((String) fila1.get("Precio Unitario cDcto"));

                    //monto_igv_descuento = precio_compra_descuento - valor_compra_descuento;
                    //monto_igv_descuento = Math.round(monto_igv_descuento * Math.pow(10, 2)) / Math.pow(10, 2);
                    monto_igv_descuento = Double.parseDouble((String) fila1.get("IGV"));

                    //Insertar detalle compra
                    query = "INSERT INTO gcbusiness.detalle_compra (id_compra, id_producto, cantidad, tipo_igv, pcto_igv, valor_unitario_compra, precio_unitario_compra, valor_compra, precio_compra, "
                            + "monto_igv, tipo_isc, monto_isc, flag_bonificacion, tipo_descuento, monto_descuento, pcto_descuento, valor_compra_descuento, precio_compra_descuento, monto_igv_descuento, "
                            + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                            + "VALUES (" + idCompra + ", " + idProducto + ", " + cantidad + ", '" + tipoIGV + "', " + pctoIGV + ", " + valor_unitario_compra + ", " + precio_unitario_compra + ", " + valor_compra + ", " + precio_compra + ", "
                            + monto_igv + ", '" + tipoISC + "', " + monto_isc + ", '" + flag_bonificacion + "', '" + tipo_descuento + "', " + monto_descuento + ", " + pcto_descuento + ", " + valor_compra_descuento + ", " + precio_compra_descuento + ", " + monto_igv_descuento + ", '"
                            + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                    sqlEjecucion = query;
                    st.executeUpdate(query);

                    Integer idlote = null;
                    String columnaLote = (String) fila1.get("Lote|F.V.");
                    if (columnaLote.contains("|")) {
                        String[] detalleLote = columnaLote.split("|");
                        idlote = Integer.parseInt(detalleLote[0].trim());
                    }

                    //OBTENER STOCK ACTUAL
                    if (idlote != null) {
                        query = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_lote=" + idlote + " AND id_almacen=" + idalmacen;
                    } else {
                        query = "SELECT stock_actual FROM gcbusiness.almacenproductolote WHERE id_producto=" + idProducto + " AND id_almacen=" + idalmacen;
                        idlote = null;
                    }

                    sqlEjecucion = query;

                    rs = st.executeQuery(sqlEjecucion);
                    if (rs.next()) {
                        stock_actual = rs.getDouble("stock_actual");
                    }

                    Double nuevostock = stock_actual + cantidad;

                    query = "INSERT INTO gcbusiness.detalle_movimientoinventario (id_movimientoinventario, id_producto, id_lote, cantidad, stock_saldo,"
                            + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                            + " VALUES (" + idMovimientoInventario + ", " + idProducto + ", " + idlote + ", " + cantidad + "," + nuevostock + ","
                            + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                    sqlEjecucion = query;
                    st.executeUpdate(query);

                    //ACTUALIZAR STOCK
                    if (idlote == null) {
                        query = "UPDATE gcbusiness.almacenproductolote SET stock_actual = " + nuevostock
                                + " WHERE id_producto = " + idProducto + " AND id_almacen = " + idalmacen;
                    } else {
                        query = "UPDATE gcbusiness.almacenproductolote SET stock_actual = " + nuevostock
                                + " WHERE id_producto = " + idProducto + " AND id_almacen = " + idalmacen + " AND id_lote = " + idlote;
                    }

                    sqlEjecucion = query;
                    st.executeUpdate(query);

                    //INSERTAR COSTO A HISTORICO
                    query = "INSERT INTO gcbusiness.producto_costo (id_producto, id_compra, fecha, costo, estado,"
                            + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                            + " VALUES (" + idProducto + ", " + idCompra + ", '" + ts + "', " + precio_unitario_compra_descuento + ",'A',"
                            + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                    sqlEjecucion = query;
                    st.executeUpdate(query);

                    //ACTUALIZAR PRECIO COMPRA EN PRODUCTO
                    query = "UPDATE gcbusiness.producto SET precio_compra = " + precio_unitario_compra_descuento + " WHERE id_producto = " + idProducto;

                    sqlEjecucion = query;
                    st.executeUpdate(query);
                }
                //FIN REGISTRAR DETALLE COMPRA Y DETALLE MOVIMIENTO INVENTARIO

                //REGISTRAR A CUENTAS POR PAGAR SI APLICA
                if (total_compra > 0) {
                    if (estatuspago.equals("P") || estatuspago.equals("S")) {
                        if (estatuspago.equals("P")) {
                            montopagado = Double.parseDouble(request.getParameter("montopagado"));
                        }

                        Integer id_cuentapagar = 0;
                        query = "SELECT NEXTVAL('gcbusiness.cuentapagar_id_cuentapagar_seq')";
                        rs = st.executeQuery(query);
                        if (rs.next()) {
                            id_cuentapagar = rs.getInt(1);
                        }

                        Double saldo = total_compra - montopagado;

                        query = "INSERT INTO gcbusiness.cuentapagar(id_cuentapagar, id_proveedor, id_compra, saldo, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                + "VALUES (" + id_cuentapagar + ", " + idproveedor + ", " + idCompra + ", " + saldo + ", 'P' , '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                        sqlEjecucion = query;
                        st.executeUpdate(query);

                        //INSERTAR EN MOVIMIENTO CUENTA COBRAR
                        if (montopagado > 0) {
                            query = "INSERT INTO gcbusiness.movimiento_cuentapagar(id_cuentapagar, fecha, monto, saldo_anterior, saldo_actual, documento_referencia, "
                                    + "estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                                    + "VALUES (" + id_cuentapagar + ", '" + ts + "', " + montopagado + ", " + total_compra + ", " + saldo + ",'" + observacion + "', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                            sqlEjecucion = query;
                            st.executeUpdate(query);
                        }
                    }
                }
                json = "{ \"mensaje\":\"<em>SE GENERÓ CORRECTAMENTE LA COMPRA</em>\" ";
                json += ",";
                json += " \"idcompra\":\"" + idCompra + "\" ";

                cn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Compra_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(GCBusiness_Compra_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                json += "}";
                out.print(json);
                out.close();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Compra_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (cn != null) {
                        cn.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Compra_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else if (opcion.equals("anular")) {
            System.out.println("ANULAR COMPRA");
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

                //RETORNO DE INVENTARIO AL ANULAR COMPRA (BOLETA O FACTURA)
                //OBTENEMOS IDALMACEN PARA SABER A QUE ALMACEN RETORNARA LOS PRODUCTOS 
                Integer id_almacen = 0;
                String sql = "SELECT id_almacen FROM gcbusiness.compra WHERE id_compra=" + idcomprobante;
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
                        + "VALUES (" + id_movimientoinventario + ", " + id_almacen + ", 12, '" + ts + "', 'ANULACION COMPRA', " + idcomprobante + ", 'A', "
                        + "'" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = sql;
                st.executeUpdate(sql);

                //OBTENEMOS IDPRODUCTO Y CANTIDAD PARA RETORNAR AL ALMACEN 
                Integer idProducto = 0, idLote = null;
                Double cantidad;
                sql = "SELECT id_producto, id_lote, cantidad FROM gcbusiness.detalle_compra WHERE id_compra=" + idcomprobante;
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

                //ACTUALIZA CUENTA PAGAR SI HAY CUENTA POR PAGAR
                sql = "UPDATE gcbusiness.cuentapagar SET estado = 'A', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                        + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_compra=" + idcomprobante;
                sqlEjecucion = sql;
                st.executeUpdate(sql);

                //ACTUALIZA COMPRA
                sql = "UPDATE gcbusiness.compra SET estado = 'A', motivo_anulacion='" + motivoAnulacion + "', fecha_modificacion='" + ts + "', usuario_modificacion='" + login_usuario + "', "
                        + "terminal_modificacion='" + request.getRemoteHost() + "', ip_modificacion='" + request.getRemoteAddr() + "' WHERE id_compra=" + idcomprobante;

                sqlEjecucion = sql;
                st.executeUpdate(sql);

                json = "{ \"mensaje\":\"<em>SE ANULÓ CORRECTAMENTE LA COMPRA</em>\" ";

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
        } else if (opcion.equals("buscar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idcompra = Integer.parseInt(request.getParameter("idcompra"));
                DaoCompraImpl daoCompraImpl = new DaoCompraImpl();
                DTOCompra dtoCompra = (DTOCompra) daoCompraImpl.accionObtener(idcompra);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idcompra", dtoCompra.getIdcompra());
                obj.put("proveedor", dtoCompra.getIdproveedor());
                obj.put("moneda", dtoCompra.getIdmoneda());
                obj.put("estado", dtoCompra.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(GCBusiness_Compra_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(GCBusiness_Compra_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
