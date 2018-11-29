package gilead.gcbusiness.controller;

import gilead.gcbusiness.sql.ConectaDb;
import gilead.gcbusiness.util.NumeroLetra;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GCBusiness_Nota_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");

        //Obtener la sesion activa
        HttpSession session = request.getSession(false);

        String opcion = request.getParameter("opcion");
        System.out.println("GCBusiness_Nota_Servlet - opcion: " + opcion);

        //Insertar nota de credito: DEVOLUCION PARCIAL, DEVOLUCION TOTAL, ANULACION DE LA OPERACION 
        if (opcion.equals("insertar")) {
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null;
            String sqlEjecucion = null;
            String json = null;
            String linkpdf = null;
            try {
                cn = db.getConnection();
                cn.setAutoCommit(false);
                st = cn.createStatement();
                //Obtener valor de ID
                Integer idNota = 0;
                String query = "SELECT NEXTVAL('gcbusiness.nota_id_nota_seq')";
                sqlEjecucion = query;

                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    idNota = rs.getInt(1);
                }

                Integer idserie = Integer.parseInt(request.getParameter("idserie"));

                //Obtener serie y correlativo actual
                String serie = "";
                int correlativo = 0;
                String query2 = "SELECT serie, correlativo FROM gcbusiness.serie WHERE id_serie=" + idserie + ";";
                ResultSet rs2 = st.executeQuery(query2);

                if (rs2.next()) {
                    serie = rs2.getString(1);
                    correlativo = rs2.getInt(2);
                }

                //Actualizar correlativo de la serie
                int newCorrelativo = correlativo + 1;
                String query3 = "UPDATE gcbusiness.serie SET correlativo=" + newCorrelativo + " WHERE id_serie=" + idserie + ";";
                st.executeUpdate(query3);
                cn.commit();

                Integer idcliente = request.getParameter("idcliente").equals("0") ? null : Integer.parseInt(request.getParameter("idcliente"));
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");

                Integer idtipoComprobante = Integer.parseInt(request.getParameter("idtipoComprobante"));
                Integer idvendedor = Integer.parseInt(request.getParameter("idvendedor"));

                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));

                String tipo_descuentoglobal = request.getParameter("tipo_dctoglobal");
                Double pcto_dctoglobal = Double.parseDouble(request.getParameter("pcto_dctoglobal"));
                Double monto_dctoglobal = Double.parseDouble(request.getParameter("monto_dctoglobal"));

                String flag_gravada = request.getParameter("flag_gravada");
                Double total_gravada = Double.parseDouble(request.getParameter("total_gravada"));
                Double total_inafecta = Double.parseDouble(request.getParameter("total_inafecta"));
                Double total_exonerada = Double.parseDouble(request.getParameter("total_exonerada"));
                Double total_gratuita = Double.parseDouble(request.getParameter("total_gratuita"));
                Double total_impuesto = Double.parseDouble(request.getParameter("total_impuesto"));
                Double total_igv = Double.parseDouble(request.getParameter("total_igv"));
                Double total_isc = Double.parseDouble(request.getParameter("total_isc"));
                Double total_otrotributo = Double.parseDouble(request.getParameter("total_otrotributo"));
                Double total_valorventa = Double.parseDouble(request.getParameter("total_valorventa"));
                Double total_precioventa = Double.parseDouble(request.getParameter("total_precioventa"));
                Double total_descuento = Double.parseDouble(request.getParameter("total_descuento"));
                Double total_otrocargo = request.getParameter("total_otrocargo").equals("") ? 0.00 : Double.parseDouble(request.getParameter("total_otrocargo"));
                Double total_venta = Double.parseDouble(request.getParameter("total_venta"));

                Integer idsucursal = Integer.parseInt(request.getParameter("idsucursalmodifica"));
                Integer idalmacen = Integer.parseInt(request.getParameter("idalmacenmodifica"));
                Integer idtiponota = Integer.parseInt(request.getParameter("idtiponota"));
                String motivo = request.getParameter("motivo");
                Integer idtipocomprobantemodifica = Integer.parseInt(request.getParameter("idtipocomprobantemodifica"));
                Integer idseriemodifica = Integer.parseInt(request.getParameter("idseriemodifica"));
                Integer correlativoseriemodifica = Integer.parseInt(request.getParameter("correlativoseriemodifica"));

                //REGISTRAR NOTA
                query = "INSERT INTO gcbusiness.nota (id_nota, id_cliente, id_tipocomprobante, id_sucursal, id_almacen, id_vendedor, id_serie, correlativo_serie, fecha_emision, "
                        + "id_moneda, id_tiponota, motivo, id_tipocomprobante_modifica, id_serie_modifica, correlativo_serie_modifica, tipo_descuentoglobal, monto_descuentoglobal, pcto_descuentoglobal, flag_gravada, total_gravada, total_inafecta, "
                        + "total_exonerada, total_gratuita, total_impuesto, total_igv, total_isc, total_otrotributo, total_valorventa, total_precioventa, total_descuento, total_otrocargo, "
                        + "total_venta, estado, motivo_anulacion, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                        + " VALUES (" + idNota + ", " + idcliente + ", " + idtipoComprobante + ", " + idsucursal + ", " + idalmacen + ", " + idvendedor + ", " + idserie + ", " + correlativo + ", '" + ts + "', "
                        + idmoneda + ", " + idtiponota + ", '" + motivo.toUpperCase() + "', " + idtipocomprobantemodifica + ", " + idseriemodifica + ", " + correlativoseriemodifica + ", '" + tipo_descuentoglobal + "', " + monto_dctoglobal + ", " + pcto_dctoglobal + ", '" + flag_gravada + "', " + total_gravada + ", " + total_inafecta + ", "
                        + total_exonerada + ", " + total_gratuita + ", " + total_impuesto + ", " + total_igv + ", " + total_isc + ", " + total_otrotributo + ", " + total_valorventa + ", " + total_precioventa + ", " + total_descuento + ", " + total_otrocargo + ", "
                        + total_venta + ", 'E', null, '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                System.out.println("query: " + query);
                st.executeUpdate(query);
                //FIN REGISTRAR NOTA

                //INICIO REGISTRAR MOVIMIENTO INVENTARIO 
                String observacion = serie + "-" + correlativo;

                Integer idMovimientoInventario = 0;
                query = "SELECT NEXTVAL('gcbusiness.movimientoinventario_id_movimientoinventario_seq')";
                sqlEjecucion = query;

                rs = st.executeQuery(query);

                if (rs.next()) {
                    idMovimientoInventario = rs.getInt(1);
                }

                Integer idmotivomovimiento = null;
                if (idtipoComprobante == 3) {
                    if (idtiponota == 6 || idtiponota == 7) {
                        idmotivomovimiento = 6;
                    } else if (idtiponota == 1 || idtiponota == 2) {
                        idmotivomovimiento = 5;
                    }
                } else if (idtipoComprobante == 8) {
                    idmotivomovimiento = 18;
                }

                query = "INSERT INTO gcbusiness.movimientoinventario (id_movimientoinventario, id_almacen, id_motivomovimiento, fecha, observacion, id_referencia, estado,"
                        + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                        + " VALUES (" + idMovimientoInventario + ", " + idalmacen + ", " + idmotivomovimiento + ", '" + ts + "', '" + observacion + "', " + idNota + ", 'A',"
                        + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                st.executeUpdate(query);
                //FIN REGISTRAR MOVIMIENTO INVENTARIO

                //INICIO REGISTRAR DETALLE NOTA Y DETALLE MOVIMIENTO INVENTARIO
                String detalleVenta = request.getParameter("detalleventa");
                JSONArray arrayDetalleVenta;
                arrayDetalleVenta = new JSONArray(detalleVenta);

                Integer idProducto = 0, idTarifa = 0;
                Double cantidad = 0.00, pctoIGV = 0.00, valor_unitario_venta = 0.00, precio_unitario_venta = 0.00, valor_venta = 0.00, precio_venta = 0.00, monto_igv = 0.00, monto_isc = 0.00,
                        monto_descuento = 0.00, pcto_descuento = 0.00, valor_venta_descuento = 0.00, precio_venta_descuento = 0.00, monto_igv_descuento = 0.00,
                        stock_actual = 0.000;
                String tipoIGV = "", tipoISC = "", flag_bonificacion = "", descuento = "", tipo_descuento = "", afectoIGV = "";
                for (int i = 0; i < arrayDetalleVenta.length(); i++) {
                    JSONObject fila1 = null;
                    fila1 = arrayDetalleVenta.getJSONObject(i);

                    idProducto = Integer.parseInt((String) fila1.get("IdProducto"));

                    //stock_actual = Double.parseDouble((String) fila1.get("Stock Actual"));
                    cantidad = Double.parseDouble((String) fila1.get("Cantidad"));

                    valor_unitario_venta = Double.parseDouble((String) fila1.get("Valor Unitario"));

                    valor_venta = Double.parseDouble((String) fila1.get("Valor Total"));

                    precio_unitario_venta = Double.parseDouble((String) fila1.get("Precio Unitario"));

                    precio_venta = Double.parseDouble((String) fila1.get("Precio Total sDscto"));

                    monto_igv = Double.parseDouble((String) fila1.get("IGV sDscto"));

                    //FALTA TIPO ISC
                    monto_isc = Double.parseDouble((String) fila1.get("ISC"));

                    afectoIGV = (String) fila1.get("Afecto IGV");
                    flag_bonificacion = (String) fila1.get("Bonificación");

                    //TIPO IGV Y PCTO IGV
                    if (flag_bonificacion.equals("S")) {
                        //tipoIGV = "21"; //Exonerado – Transferencia Gratuita
                        tipoIGV = "E";
                    } else {
                        if (afectoIGV.trim().equals("G")) {
                            //tipoIGV = "10"; //Gravado – Operación Onerosa
                            tipoIGV = "G";
                            pctoIGV = 18.00;
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
                    System.out.println("tipoIGV: " + tipoIGV + "  pctoIGV: " + pctoIGV);

                    //DESCUENTO
                    descuento = (String) fila1.get("Descuento");
                    System.out.println("descuento: " + descuento);
                    String[] detalleDcto = descuento.split(" | ");

                    tipo_descuento = detalleDcto[2].trim();
                    System.out.println("tipo_descuento: " + tipo_descuento);
                    System.out.println("monto_descuento:  " + detalleDcto[0]);

                    monto_descuento = Double.parseDouble((String) fila1.get("Descuento Mont"));
                    pcto_descuento = Double.parseDouble((String) fila1.get("Descuento Porc"));
                    System.out.println("monto_descuento  " + monto_descuento);
                    System.out.println("pcto_descuento  " + pcto_descuento);

                    valor_venta_descuento = Double.parseDouble((String) fila1.get("Valor Total Dscto"));

                    monto_igv_descuento = Double.parseDouble((String) fila1.get("IGV"));

                    precio_venta_descuento = Double.parseDouble((String) fila1.get("Precio Total"));

                    Integer idlote = null;
                    String columnaLote = (String) fila1.get("Lote|F.V.");
                    if (columnaLote.contains("|")) {
                        String[] detalleLote = columnaLote.split("|");
                        idlote = Integer.parseInt(detalleLote[0].trim());
                    }

                    if (idlote == null) {
                        query = "SELECT stock_actual FROM gcbusiness.almacenproductolote "
                                + "WHERE id_almacen = " + idalmacen + " "
                                + "AND id_producto = " + idProducto;
                    } else {
                        query = "SELECT stock_actual FROM gcbusiness.almacenproductolote "
                                + "WHERE id_almacen = " + idalmacen + " "
                                + "AND id_producto = " + idProducto + " "
                                + "AND id_lote = " + idlote;
                    }

                    System.out.println("query " + query);
                    rs = st.executeQuery(query);
                    try {
                        if (rs.next()) {
                            stock_actual = rs.getDouble("stock_actual");
                        }
                    } catch (java.sql.SQLException sqle) {
                    }

                    //Insertar detalle venta
                    query = "INSERT INTO gcbusiness.detalle_nota (id_nota, id_producto, id_lote, cantidad, tipo_igv, pcto_igv, valor_unitario_venta, precio_unitario_venta, valor_venta, precio_venta, "
                            + "monto_igv, tipo_isc, monto_isc, flag_bonificacion, tipo_descuento, monto_descuento, pcto_descuento, valor_venta_descuento, precio_venta_descuento, monto_igv_descuento, "
                            + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                            + "VALUES (" + idNota + ", " + idProducto + ", " + idlote + ", " + cantidad + ", '" + tipoIGV + "', " + pctoIGV + ", " + valor_unitario_venta + ", " + precio_unitario_venta + ", " + valor_venta + ", " + precio_venta + ", "
                            + monto_igv + ", '" + tipoISC + "', " + monto_isc + ", '" + flag_bonificacion + "', '" + tipo_descuento + "', " + monto_descuento + ", " + pcto_descuento + ", " + valor_venta_descuento + ", " + precio_venta_descuento + ", " + monto_igv_descuento + ", '"
                            + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                    sqlEjecucion = query;
                    st.executeUpdate(query);

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
                }
                //FIN REGISTRAR DETALLE VENTA Y DETALLE MOVIMIENTO INVENTARIO

                //REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR SI APLICA
                Integer idcomprobantemodifica = Integer.parseInt(request.getParameter("idcomprobantemodifica"));
                Double saldoAnterior = 0.00, saldoActual = 0.00;
                Integer idcuentacobrar = 0;
                query = "SELECT id_cuentacobrar, saldo FROM gcbusiness.cuentacobrar WHERE id_comprobante = " + idcomprobantemodifica + " AND id_tipocomprobante = " + idtipocomprobantemodifica;
                rs = st.executeQuery(query);
                if (rs.next()) {
                    idcuentacobrar = rs.getInt("id_cuentacobrar");
                    saldoAnterior = rs.getDouble("saldo");
                }

                if (idcuentacobrar != 0) {
                    saldoActual = saldoAnterior - total_venta;

                    if (saldoActual <= 0) {
                        query = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + ", estado = 'C' WHERE id_cuentacobrar = " + idcuentacobrar;
                    } else {
                        query = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + " WHERE id_cuentacobrar = " + idcuentacobrar;
                    }
                    sqlEjecucion = query;
                    st.executeUpdate(query);

                    Integer idNovimientoCuentaCobrar = 0;
                    query = "SELECT NEXTVAL('gcbusiness.movimiento_cuentacobrar_id_movimiento_cuentacobrar_seq')";
                    sqlEjecucion = query;

                    rs = st.executeQuery(query);

                    if (rs.next()) {
                        idNovimientoCuentaCobrar = rs.getInt(1);
                    }

                    query = "INSERT INTO gcbusiness.movimiento_cuentacobrar (id_movimiento_cuentacobrar, id_cuentacobrar,fecha,monto,saldo_anterior,saldo_actual,documento_referencia"
                            + ",estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                            + "VALUES (" + idNovimientoCuentaCobrar + ", " + idcuentacobrar + ",'" + ts + "'," + total_venta + ", " + saldoAnterior + ", " + saldoActual + ", '" + observacion
                            + "', 'A', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                    sqlEjecucion = query;
                    st.executeUpdate(query);
                }
                //FIN REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR

                //ENVIAR DATOS AL SERVICIO FACTURACION ELECTRONICA
                if (idtipoComprobante != 8) {
                    //Obtener montos en letras
                    NumeroLetra NumLetra = new NumeroLetra();
                    String numeroenletra = NumLetra.Convertir(request.getParameter("total_venta"), true);

                    //Obtener datos de la cabecera
                    query = "select e.ruc emp_ruc, e.razon_social emp_denominacion, '6' emp_tipodocumento, e.codigo_ubidistrito emp_ubigeo, \n"
                            + "e.direccion emp_direccion, de.descripcion emp_departamento, pr.descripcion emp_provincia, di.descripcion emp_distrito, e.codigo_pais emp_codigopais,\n"
                            + "td.codigo_sunat cli_tipodocumento, c.numero_documento cli_numerodocumento, c.nombre cli_denominacion, c.direccion cli_direccion,\n"
                            + "tc.codigo_sunat tipocomprobante, s.serie serie, cast(v.correlativo_serie as varchar) correlativo, cast(date(v.fecha_emision) as varchar) fechaemision, to_char(v.fecha_emision, 'HH24:MI:SS') horaemision, m.codigo_sunat moneda,\n"
                            + "cast(v.total_gravada as varchar) totalgravada, cast(v.total_exonerada as varchar) totalexonerada, cast(v.total_igv as varchar) totaligv,\n"
                            + "cast(v.total_descuento as varchar) totaldescuento, cast(v.total_gratuita as varchar) totalgratuita, cast(v.total_venta as varchar) total, ve.descripcion vendedor, tn.codigo_sunat notamotivo, v.motivo notasustento, tcm.codigo_sunat notatipocomprobante, sm.serie notaserie, cast(v.correlativo_serie_modifica as varchar) notacorrelativo\n"
                            + "from gcbusiness.nota v \n"
                            + "left join gcbusiness.empresa e on 1 = 1 \n"
                            + "left join gcbusiness.cliente c on c.id_cliente = v.id_cliente \n"
                            + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante = v.id_tipocomprobante \n"
                            + "left join gcbusiness.tipodocumento td on td.id_tipodocumento = c.id_tipodocumento \n"
                            + "left join gcbusiness.serie s on s.id_serie = v.id_serie \n"
                            + "left join gcbusiness.moneda m on m.id_moneda = v.id_moneda \n"
                            + "left join gcbusiness.vendedor ve on ve.id_vendedor = c.id_vendedor \n"
                            + "left join gcbusiness.ubidistrito di on di.codigo_ubidistrito = e.codigo_ubidistrito \n"
                            + "left join gcbusiness.ubiprovincia pr on pr.codigo_ubiprovincia = di.codigo_ubiprovincia \n"
                            + "left join gcbusiness.ubidepartamento de on de.codigo_ubidepartamento = pr.codigo_ubidepartamento \n"
                            + "left join gcbusiness.motivonota tn on tn.id_motivonota = v.id_tiponota \n"
                            + "left join gcbusiness.tipocomprobante tcm on tcm.id_tipocomprobante = v.id_tipocomprobante_modifica \n"
                            + "left join gcbusiness.serie sm on sm.id_serie = v.id_serie_modifica\n"
                            + "where v.id_nota = " + idNota;

                    rs = st.executeQuery(query);

                    org.json.simple.JSONObject objetoCabecera = null;
                    String moneda = null;
                    while (rs.next()) {
                        String enviarsunat = "N";
                        moneda = rs.getString("moneda");
                        objetoCabecera = new org.json.simple.JSONObject();
                        objetoCabecera.put("ubl", "2.0");//ubl2.1
                        objetoCabecera.put("emp_ruc", rs.getString("emp_ruc"));
                        objetoCabecera.put("emp_denominacion", rs.getString("emp_denominacion"));
                        objetoCabecera.put("emp_nombrecomercial", "-");//ubl2.1
                        objetoCabecera.put("emp_codigofiscal", "0000");//ubl2.1
                        objetoCabecera.put("emp_tipodocumento", rs.getString("emp_tipodocumento"));
                        objetoCabecera.put("emp_ubigeo", rs.getString("emp_ubigeo"));
                        objetoCabecera.put("emp_direccion", rs.getString("emp_direccion"));
                        objetoCabecera.put("emp_departamento", rs.getString("emp_departamento"));
                        objetoCabecera.put("emp_provincia", rs.getString("emp_provincia"));
                        objetoCabecera.put("emp_distrito", rs.getString("emp_distrito"));
                        objetoCabecera.put("emp_codigopais", rs.getString("emp_codigopais"));
                        objetoCabecera.put("cli_tipodocumento", rs.getString("cli_tipodocumento"));
                        objetoCabecera.put("cli_numerodocumento", rs.getString("cli_numerodocumento"));
                        objetoCabecera.put("cli_denominacion", rs.getString("cli_denominacion"));
                        objetoCabecera.put("cli_direccion", rs.getString("cli_direccion"));
                        objetoCabecera.put("tipooperacion", "0101");//ubl2.1 
                        objetoCabecera.put("tipocomprobante", rs.getString("tipocomprobante"));
                        objetoCabecera.put("serie", rs.getString("serie"));
                        objetoCabecera.put("correlativo", rs.getString("correlativo"));
                        objetoCabecera.put("fechaemision", rs.getString("fechaemision"));
                        objetoCabecera.put("horaemision", rs.getString("horaemision"));//ubl2.1
                        objetoCabecera.put("fechavencimiento", rs.getString("fechaemision"));//ubl2.1
                        objetoCabecera.put("moneda", rs.getString("moneda"));
                        objetoCabecera.put("descuentoglobal", "0.00");//ubl2.1
                        objetoCabecera.put("porcentajedescuentoglobal", "0.00");//ubl2.1
                        objetoCabecera.put("basedescuentoglobal", "0.00");//ubl2.1
                        objetoCabecera.put("totalgravada", rs.getString("totalgravada"));
                        objetoCabecera.put("totalinafecta", "0.00");
                        objetoCabecera.put("totalexonerada", rs.getString("totalexonerada"));
                        objetoCabecera.put("totalgratuita", rs.getString("totalgratuita"));
                        objetoCabecera.put("totaligvgratuita", "0.00");//ubl2.1
                        objetoCabecera.put("totalimpuesto", rs.getString("totaligv"));//ubl2.1
                        objetoCabecera.put("totaligv", rs.getString("totaligv"));
                        objetoCabecera.put("totalisc", "0.00");
                        objetoCabecera.put("baseisc", "0.00");//ubl2.1
                        objetoCabecera.put("totalotrotributo", "0.00");
                        objetoCabecera.put("baseotrotributo", "0.00");//ubl2.1
                        objetoCabecera.put("totalvalorventa", rs.getString("total"));//ubl2.1
                        objetoCabecera.put("totalprecioventa", rs.getString("total"));//ubl2.1                        
                        objetoCabecera.put("totaldescuento", rs.getString("totaldescuento"));
                        objetoCabecera.put("totalotrocargo", "0.00");
                        objetoCabecera.put("totalanticipo", "0.00");//ubl2.1
                        objetoCabecera.put("total", rs.getString("total"));
                        objetoCabecera.put("totaldetraccion", "0.00");//ubl2.1
                        objetoCabecera.put("porcentajedetraccion", "0.00");//ubl2.1
                        objetoCabecera.put("codigoproductodetraccion", "-");//ubl2.1
                        objetoCabecera.put("cuentabanco", "-");//ubl2.1
                        objetoCabecera.put("totalpercepcion", "0.00");
                        objetoCabecera.put("basepercepcion", "0.00");
                        objetoCabecera.put("totalincluidopercepcion", "0.00");
                        objetoCabecera.put("tipoproceso", "1");
                        objetoCabecera.put("enviosunat", enviarsunat);
                        objetoCabecera.put("enviocliente", "N");
                        objetoCabecera.put("notatipocomprobante", rs.getString("notatipocomprobante"));
                        objetoCabecera.put("notaserie", rs.getString("notaserie"));
                        objetoCabecera.put("notacorrelativo", rs.getString("notacorrelativo"));
                        objetoCabecera.put("notamotivo", rs.getString("notamotivo"));
                        objetoCabecera.put("notasustento", rs.getString("notasustento"));
                        objetoCabecera.put("vendedor", rs.getString("vendedor"));
                    }
                    //Fin obtener datos de la cabecera

                    //Obtener datos del detalle
                    query = "select p.codigo_interno itemcodigo, p.descripcion itemdescripcion, um.codigo_sunat itemunidadmedida, cast(dv.cantidad as varchar) itemcantidad, cast(dv.valor_venta_descuento as varchar) itemvalorventa, "
                            + "cast(dv.valor_unitario_venta as varchar) itemvalorventaunitario, cast(dv.precio_venta_descuento as varchar) itemprecioventa, cast(dv.precio_unitario_venta as varchar) itemprecioventaunitario, "
                            + "cast(dv.monto_igv as varchar) itemigv, case when dv.flag_bonificacion = 'S' then '21' when dv.tipo_igv = 'G' then '10' when dv.tipo_igv = 'E' then '20' when dv.tipo_igv = 'I' then '30' end itemafectacionigv, dv.flag_bonificacion flagbonificacion, "
                            + "cast(dv.monto_descuento as varchar) itemdescuento, cast(dv.pcto_descuento as varchar) itemporcentajedescuento,case when dv.pcto_descuento>0 then cast(dv.valor_venta as varchar) else '0.00' end itembasedescuento "
                            + "from gcbusiness.detalle_nota dv "
                            + "left join gcbusiness.producto p on p.id_producto = dv.id_producto "
                            + "left join gcbusiness.unidadmedida um on um.id_unidadmedida = p.id_unidadmedida "
                            + "where dv.id_nota = " + idNota;

                    rs = st.executeQuery(query);

                    org.json.simple.JSONArray lista = new org.json.simple.JSONArray();
                    org.json.simple.JSONObject detalle_linea = null;
                    while (rs.next()) {
                        detalle_linea = new org.json.simple.JSONObject();

                        detalle_linea.put("itemcodigo", rs.getString("itemcodigo"));
                        detalle_linea.put("itemcodigosunat", "-");//2.1
                        detalle_linea.put("itemdescripcion", rs.getString("itemdescripcion"));
                        detalle_linea.put("itemunidadmedida", rs.getString("itemunidadmedida"));
                        detalle_linea.put("itemcantidad", rs.getString("itemcantidad"));
                        detalle_linea.put("itemmoneda", moneda);
                        detalle_linea.put("itemvalorventa", rs.getString("itemvalorventa"));
                        if (rs.getString("flagbonificacion").equals("S")) {
                            detalle_linea.put("itemvalorventaunitario", "0.00");
                            detalle_linea.put("itemprecioventaunitario", "0.00");
                            detalle_linea.put("itemvalornoonerosaunitario", rs.getString("itemprecioventaunitario"));
                        } else {
                            detalle_linea.put("itemvalorventaunitario", rs.getString("itemvalorventaunitario"));
                            detalle_linea.put("itemprecioventaunitario", rs.getString("itemprecioventaunitario"));
                            detalle_linea.put("itemvalornoonerosaunitario", "0.00");
                        }
                        detalle_linea.put("itemprecioventa", rs.getString("itemprecioventa"));
                        detalle_linea.put("itemdescuento", rs.getString("itemdescuento"));//2.1
                        detalle_linea.put("itemporcentajedescuento", rs.getString("itemporcentajedescuento"));//2.1
                        detalle_linea.put("itembasedescuento", rs.getString("itembasedescuento"));//2.1
                        detalle_linea.put("itemcargo", "0.00");//2.1
                        detalle_linea.put("itemporcentajecargo", "0.00");//2.1
                        detalle_linea.put("itembasecargo", "0.00");//2.1
                        detalle_linea.put("itemtotalimpuesto", rs.getString("itemigv"));//2.1                       
                        detalle_linea.put("itemigv", rs.getString("itemigv"));
                        if (rs.getString("itemafectacionigv").equals("10")) {
                            detalle_linea.put("itembaseigv", rs.getString("itemvalorventa"));//2.1
                            detalle_linea.put("itemporcentajeigv", "18.00");//2.1
                        } else {
                            detalle_linea.put("itembaseigv", "0.00");//2.1  
                            detalle_linea.put("itemporcentajeigv", "0.00");//2.1
                        }
                        detalle_linea.put("itemafectacionigv", rs.getString("itemafectacionigv"));
                        detalle_linea.put("itemisc", "0.00");
                        detalle_linea.put("itembaseisc", "0.00");
                        detalle_linea.put("itemporcentajeisc", "0.00");
                        detalle_linea.put("itemisctipo", "");

                        lista.add(detalle_linea);
                    }
                    objetoCabecera.put("comprobantedetalles", lista);
                    //Fin obtener datos del detalle

                    //Obtener leyendas
                    org.json.simple.JSONArray lista_leyenda = new org.json.simple.JSONArray();
                    org.json.simple.JSONObject leyenda_linea_1 = new org.json.simple.JSONObject();

                    leyenda_linea_1.put("leyendacodigo", "1000");
                    leyenda_linea_1.put("leyendadescripcion", numeroenletra);

                    lista_leyenda.add(leyenda_linea_1);

                    objetoCabecera.put("leyendas", lista_leyenda);

                    //FIN CONSTRUIR OBJETO JSON PARA ENVIAR AL SERVICIO
                    //
                    try {
                        DefaultHttpClient httpClient = new DefaultHttpClient();
                        HttpPost postRequest = new HttpPost(
                                "http://localhost:8084/WebServiceSunat21/rest/comprobante/procesarComprobante");

                        StringEntity input = new StringEntity(objetoCabecera.toString(), "utf-8");

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

                        linkpdf = aux[6];
                        System.out.println("linkpdf: " + linkpdf);
                        httpClient.getConnectionManager().shutdown();

                    } catch (MalformedURLException e) {

                        e.printStackTrace();
                    } catch (IOException e) {

                        e.printStackTrace();

                    }
                }

                json = "{ \"mensaje\":\"<em>SE GENERÓ CORRECTAMENTE LA NOTA</em>\" ";
                json += ",";
                json += " \"idnota\":\"" + idNota + "\" ";
                json += ",";
                json += " \"linkpdf\":\"" + linkpdf + "\"";

                cn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                json += "}";
                out.print(json);
                out.close();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (cn != null) {
                        cn.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else if (opcion.equals("insertardescuentoglobal")) {
            System.out.println("INSERTAR NOTA");
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

                Integer idNota = 0;
                String query = "SELECT NEXTVAL('gcbusiness.nota_id_nota_seq')";
                sqlEjecucion = query;

                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    idNota = rs.getInt(1);
                }

                Integer idserie = Integer.parseInt(request.getParameter("idserie"));

                //Obtener serie y correlativo actual
                String serie = "";
                int correlativo = 0;
                String query2 = "SELECT serie, correlativo FROM gcbusiness.serie WHERE id_serie=" + idserie + ";";
                ResultSet rs2 = st.executeQuery(query2);

                if (rs2.next()) {
                    serie = rs2.getString(1);
                    correlativo = rs2.getInt(2);
                }

                int newCorrelativo = correlativo + 1;
                String query3 = "UPDATE gcbusiness.serie SET correlativo=" + newCorrelativo + " WHERE id_serie=" + idserie + ";";
                st.executeUpdate(query3);
                cn.commit();

                Integer idcliente = request.getParameter("idcliente").equals("0") ? null : Integer.parseInt(request.getParameter("idcliente"));
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");

                Integer idtipoComprobante = Integer.parseInt(request.getParameter("idtipoComprobante"));
                Integer idvendedor = Integer.parseInt(request.getParameter("idvendedor"));

                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));

                String tipoafecta = request.getParameter("tipoafecta");
                Double total_gravada = Double.parseDouble(request.getParameter("0")),
                        total_exonerada = Double.parseDouble(request.getParameter("0")),
                        total_inafecta = Double.parseDouble(request.getParameter("0"));

                Double descuentoglobal = Double.parseDouble(request.getParameter("descuentoglobal"));
                if (tipoafecta.equals("G")) {
                    total_gravada = descuentoglobal;
                } else if (tipoafecta.equals("E")) {
                    total_exonerada = descuentoglobal;
                } else if (tipoafecta.equals("I")) {
                    total_inafecta = descuentoglobal;
                }

                Double total_igv = Double.parseDouble(request.getParameter("igv"));

                Double total_venta = descuentoglobal + total_igv;

                Integer idsucursal = Integer.parseInt(request.getParameter("idsucursalmodifica"));
                Integer idalmacen = Integer.parseInt(request.getParameter("idalmacenmodifica"));
                Integer idtiponota = Integer.parseInt(request.getParameter("idtiponota"));
                String motivo = request.getParameter("motivo");
                Integer idtipocomprobantemodifica = Integer.parseInt(request.getParameter("idtipocomprobantemodifica"));
                Integer idseriemodifica = Integer.parseInt(request.getParameter("idseriemodifica"));
                Integer correlativoseriemodifica = Integer.parseInt(request.getParameter("correlativoseriemodifica"));

                //REGISTRAR NOTA
                query = "INSERT INTO gcbusiness.nota (id_nota, id_cliente, id_tipocomprobante, id_sucursal, id_almacen, id_vendedor, id_serie, correlativo_serie, fecha_emision, "
                        + "id_moneda, id_tiponota, motivo, id_tipocomprobante_modifica, id_serie_modifica, correlativo_serie_modifica, tipo_descuentoglobal, monto_descuentoglobal, pcto_descuentoglobal, flag_gravada, total_gravada, total_inafecta, "
                        + "total_exonerada, total_gratuita, total_impuesto, total_igv, total_isc, total_otrotributo, total_valorventa, total_precioventa, total_descuento, total_otrocargo, "
                        + "total_venta, estado, motivo_anulacion, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                        + " VALUES (" + idNota + ", " + idcliente + ", " + idtipoComprobante + ", " + idsucursal + ", " + idalmacen + ", " + idvendedor + ", " + idserie + ", " + correlativo + ", '" + ts + "', "
                        + idmoneda + ", " + idtiponota + ", '" + motivo.toUpperCase() + "', " + idtipocomprobantemodifica + ", " + idseriemodifica + ", " + correlativoseriemodifica + ", 'P', 0, 0, 'N', " + total_gravada + ", " + total_inafecta + ", "
                        + total_exonerada + ", 0, " + total_igv + ", " + total_igv + ", 0, 0, " + descuentoglobal + ", " + total_venta + ", 0, 0, "
                        + total_venta + ", 'E', null, '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                System.out.println("query: " + query);
                st.executeUpdate(query);
                //FIN REGISTRAR NOTA

                //INICIO REGISTRAR DETALLE NOTA            
                Double pctoIGV = 0.00;
                String tipoIGV = "";

                if (tipoafecta.trim().equals("G")) {
                    //tipoIGV = "10"; //Gravado – Operación Onerosa
                    tipoIGV = "G";
                    pctoIGV = 18.00;
                }
                if (tipoafecta.trim().equals("E")) {
                    //tipoIGV = "20"; //Exonerado – Operación Onerosa
                    tipoIGV = "E";
                }
                if (tipoafecta.trim().equals("I")) {
                    //tipoIGV = "30"; //Inafecto – Operación Onerosa
                    tipoIGV = "I";
                }

                //Insertar detalle venta
                query = "INSERT INTO gcbusiness.detalle_nota (id_nota, id_producto, id_lote, cantidad, tipo_igv, pcto_igv, valor_unitario_venta, precio_unitario_venta, valor_venta, precio_venta, "
                        + "monto_igv, tipo_isc, monto_isc, flag_bonificacion, tipo_descuento, monto_descuento, pcto_descuento, valor_venta_descuento, precio_venta_descuento, monto_igv_descuento, "
                        + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                        + "VALUES (" + idNota + ", 1, null, 1, '" + tipoIGV + "', " + pctoIGV + ", " + descuentoglobal + ", " + total_venta + ", " + descuentoglobal + ", " + total_venta + ", "
                        + total_igv + ", '', 0, 'N', 'P', 0, 0, " + descuentoglobal + ", " + total_venta + ", " + total_igv + ", '"
                        + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                sqlEjecucion = query;
                st.executeUpdate(query);
                //FIN REGISTRAR DETALLE NOTA

                //REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR SI APLICA
                Integer idcomprobantemodifica = Integer.parseInt(request.getParameter("idcomprobantemodifica"));
                Double saldoAnterior = 0.00, saldoActual = 0.00;
                Integer idcuentacobrar = 0;
                query = "SELECT id_cuentacobrar, saldo FROM gcbusiness.cuentacobrar WHERE id_comprobante = " + idcomprobantemodifica + " AND id_tipocomprobante = " + idtipocomprobantemodifica;
                rs = st.executeQuery(query);
                if (rs.next()) {
                    idcuentacobrar = rs.getInt("id_cuentacobrar");
                    saldoAnterior = rs.getDouble("saldo");
                }

                if (idcuentacobrar != 0) {
                    saldoActual = saldoAnterior - total_venta;

                    if (saldoActual <= 0) {
                        query = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + ", estado = 'C' WHERE id_cuentacobrar = " + idcuentacobrar;
                    } else {
                        query = "UPDATE gcbusiness.cuentacobrar SET saldo = " + saldoActual + " WHERE id_cuentacobrar = " + idcuentacobrar;
                    }
                    sqlEjecucion = query;
                    st.executeUpdate(query);

                    Integer idNovimientoCuentaCobrar = 0;
                    query = "SELECT NEXTVAL('gcbusiness.movimiento_cuentacobrar_id_movimiento_cuentacobrar_seq')";
                    sqlEjecucion = query;

                    rs = st.executeQuery(query);

                    if (rs.next()) {
                        idNovimientoCuentaCobrar = rs.getInt(1);
                    }

                    String observacion = serie + "-" + correlativo;

                    query = "INSERT INTO gcbusiness.movimiento_cuentacobrar (id_movimiento_cuentacobrar, id_cuentacobrar,fecha,monto,saldo_anterior,saldo_actual,documento_referencia"
                            + ",estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                            + "VALUES (" + idNovimientoCuentaCobrar + ", " + idcuentacobrar + ",'" + ts + "'," + total_venta + ", " + saldoAnterior + ", " + saldoActual + ", '" + observacion
                            + "', 'A', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                    sqlEjecucion = query;
                    st.executeUpdate(query);
                }
                //FIN REGISTRAR MOVIMIENTO A CUENTAS POR COBRAR  

                json = "{ \"mensaje\":\"<em>SE GENERÓ CORRECTAMENTE LA NOTA</em>\" ";
                json += ",";
                json += " \"idnota\":\"" + idNota + "\" ";

                cn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                json += "}";
                out.print(json);
                out.close();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (cn != null) {
                        cn.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(GCBusiness_Nota_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GCBusiness_Nota_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
