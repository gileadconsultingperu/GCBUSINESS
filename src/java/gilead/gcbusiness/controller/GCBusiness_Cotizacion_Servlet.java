/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoCotizacionImpl;
import gilead.gcbusiness.dao.impl.DaoVentaImpl;
import gilead.gcbusiness.dto.DTOCotizacion;
import gilead.gcbusiness.dto.DTODetalleCotizacion;
import gilead.gcbusiness.dto.DTODetalleVenta;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Luis
 */
public class GCBusiness_Cotizacion_Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);

        String opcion = request.getParameter("opcion");
        System.out.println("Entro GESTION COTIZACION");

        if (opcion.equals("insertar")) {
            System.out.println("INSERTAR COTIZACION");
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

                Integer idCotizacion = 0;
                String query = "SELECT NEXTVAL('gcbusiness.cotizacion_id_cotizacion_seq')";
                sqlEjecucion = query;

                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    idCotizacion = rs.getInt(1);
                }

                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");

                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));

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
                Double total_valorventa = Double.parseDouble(request.getParameter("total_valorventa"));
                Double total_precioventa = Double.parseDouble(request.getParameter("total_precioventa"));
                Double total_descuento = Double.parseDouble(request.getParameter("total_descuento"));
                Double total_otrocargo = request.getParameter("total_otrocargo").equals("") ? 0.00 : Double.parseDouble(request.getParameter("total_otrocargo"));
                Double total_venta = Double.parseDouble(request.getParameter("total_venta"));

                Integer idsucursal = Integer.parseInt((String) session.getAttribute("idSucursal"));
                Integer idalmacen = Integer.parseInt((String) session.getAttribute("idAlmacen"));

                //REGISTRAR COTIZACION
                query = "INSERT INTO gcbusiness.cotizacion (id_cotizacion, id_sucursal, id_almacen, fecha_emision, "
                        + "id_moneda, tipo_descuentoglobal, monto_descuentoglobal, pcto_descuentoglobal, total_gravada, total_inafecta, "
                        + "total_exonerada, total_gratuita, total_impuesto, total_igv, total_isc, total_otrotributo, total_valorventa, total_precioventa, total_descuento, total_otrocargo, "
                        + "total_venta, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                        + " VALUES (" + idCotizacion + ", " + idsucursal + ", " + idalmacen + ", '" + ts + "', "
                        + idmoneda + ", '" + tipo_descuentoglobal + "', " + monto_dctoglobal + ", " + pcto_dctoglobal + ", " + total_gravada + ", " + total_inafecta + ", "
                        + total_exonerada + ", " + total_gratuita + ", " + total_impuesto + ", " + total_igv + ", " + total_isc + ", " + total_otrotributo + ", " + total_valorventa + ", " + total_precioventa + ", " + total_descuento + ", " + total_otrocargo + ", "
                        + total_venta + ", 'E', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                System.out.println("query: " + query);
                st.executeUpdate(query);
                //FIN REGISTRAR COTIZACION

                //INICIO REGISTRAR DETALLE COTIZACION
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

                    stock_actual = Double.parseDouble((String) fila1.get("Stock Actual"));

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
                        tipoIGV = "21"; //Exonerado – Transferencia Gratuita
                        afectoIGV = "E";
                    } else {
                        if (afectoIGV.trim().equals("G")) {
                            tipoIGV = "10"; //Gravado – Operación Onerosa
                            pctoIGV = 18.00;
                        }
                        if (afectoIGV.trim().equals("E")) {
                            tipoIGV = "20"; //Exonerado – Operación Onerosa
                        }
                        if (afectoIGV.trim().equals("I")) {
                            tipoIGV = "30"; //Inafecto – Operación Onerosa
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

                    //Insertar detalle venta
                    query = "INSERT INTO gcbusiness.detalle_cotizacion (id_cotizacion, id_producto, id_lote, cantidad, tipo_igv, pcto_igv, valor_unitario_venta, precio_unitario_venta, valor_venta, precio_venta, "
                            + "monto_igv, tipo_isc, monto_isc, flag_bonificacion, tipo_descuento, monto_descuento, pcto_descuento, valor_venta_descuento, precio_venta_descuento, monto_igv_descuento, "
                            + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                            + "VALUES (" + idCotizacion + ", " + idProducto + ", " + idlote + ", " + cantidad + ", '" + tipoIGV + "', " + pctoIGV + ", " + valor_unitario_venta + ", " + precio_unitario_venta + ", " + valor_venta + ", " + precio_venta + ", "
                            + monto_igv + ", '" + tipoISC + "', " + monto_isc + ", '" + flag_bonificacion + "', '" + tipo_descuento + "', " + monto_descuento + ", " + pcto_descuento + ", " + valor_venta_descuento + ", " + precio_venta_descuento + ", " + monto_igv_descuento + ", '"
                            + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                    sqlEjecucion = query;
                    st.executeUpdate(query);
                }
                //FIN REGISTRAR DETALLE COTIZACION

                json = "{ \"mensaje\":\"<em>SE GENERÓ CORRECTAMENTE LA COTIZACION N° " + idCotizacion + "</em>\" ";
                json += ",";
                json += " \"numerocotizacion\":\"" + idCotizacion + "\" ";

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

        } else if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                String fecha_desde = !request.getParameter("desde").equals("") ? (String) request.getParameter("desde") : "01/01/1990";
                String fecha_hasta = !request.getParameter("hasta").equals("") ? (String) request.getParameter("hasta") : "12/12/9999";
                String estado = request.getParameter("estado") != null ? (String) request.getParameter("estado") : "0";
                Integer idsucursal = Integer.parseInt((String) session.getAttribute("idSucursal"));
                Integer idalmacen = Integer.parseInt((String) session.getAttribute("idAlmacen"));

                DaoCotizacionImpl daoCotizacionImpl = new DaoCotizacionImpl();
                List<DTOCotizacion> listCotizacion = daoCotizacionImpl.accionListarDTOCotizacion(idsucursal, idalmacen, estado, fecha_desde, fecha_hasta);

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);

                for (int i = 0; i < listCotizacion.size(); i++) {
                    String acciones = "";

                    if (listCotizacion.get(i).getEstado().equals("PENDIENTE")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<a href=\"GC-Business-Boleta.jsp?idcotizacion=" + listCotizacion.get(i).getIdcotizacion() + "\" class=\"btn btn-xs btn-info boleta\"><i class=\"ace-icon\" title='Boleta'><b>BO</b></i></a>"
                                + "<a href=\"GC-Business-Factura.jsp?idcotizacion=" + listCotizacion.get(i).getIdcotizacion() + "\" class=\"btn btn-xs btn-default factura\"><i class=\"ace-icon\" title='Factura'><b>FA</b></i></a>"
                                + "<a href=\"GC-Business-NotaPedido.jsp?idcotizacion=" + listCotizacion.get(i).getIdcotizacion() + "\" class=\"btn btn-xs btn-yellow nota\"><i class=\"ace-icon\" title='Nota Pedido'><b>NP</b></i></a>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("fecha", dateFormat.format(listCotizacion.get(i).getFecha_emision().getTime()));
                    obj.put("nrocotizacion", listCotizacion.get(i).getIdcotizacion());
                    obj.put("moneda", listCotizacion.get(i).getCodigoSunatMoneda());
                    obj.put("total", listCotizacion.get(i).getTotal_venta());
                    obj.put("estado", listCotizacion.get(i).getEstado());
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }
                System.out.println(" {\"data\":" + datos.toJSONString() + "} ");
                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        } else if (opcion.equals("obtenercotizacion")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idcotizacion = Integer.parseInt(request.getParameter("idcotizacion"));
                DaoCotizacionImpl daoCotizacionImpl = new DaoCotizacionImpl();
                DTOCotizacion dtoCotizacion = daoCotizacionImpl.accionObtenerDTOCotizacion(idcotizacion);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idsucursal", dtoCotizacion.getIdsucursal());
                obj.put("idalmacen", dtoCotizacion.getIdalmacen());
                obj.put("idmoneda", dtoCotizacion.getIdmoneda());
                obj.put("totaligv", dtoCotizacion.getTotal_igv());
                obj.put("totalisc", dtoCotizacion.getTotal_isc());
                obj.put("totalotrotributo", dtoCotizacion.getTotal_otrotributo());
                obj.put("totalimpuesto", dtoCotizacion.getTotal_impuesto());
                obj.put("tipodescuentoglobal", dtoCotizacion.getTipo_descuentoglobal());
                obj.put("montodescuentoglobal", dtoCotizacion.getMonto_descuentoglobal());
                obj.put("pctodescuentoglobal", dtoCotizacion.getPcto_descuentoglobal());
                obj.put("totalgravada", dtoCotizacion.getTotal_gravada());
                obj.put("totalinafecta", dtoCotizacion.getTotal_inafecta());
                obj.put("totalexonerada", dtoCotizacion.getTotal_exonerada());
                obj.put("totalgratuita", dtoCotizacion.getTotal_gratuita());
                obj.put("totalotrocargo", dtoCotizacion.getTotal_otrocargo());
                obj.put("totaldescuento", dtoCotizacion.getTotal_descuento());
                obj.put("totalventa", dtoCotizacion.getTotal_venta());

                System.out.println(obj.toJSONString());
                out.print(obj.toJSONString());
            }
        } else if (opcion.equals("obtenerdetallecotizacion")) {
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

                Integer idcotizacion = Integer.parseInt(request.getParameter("idcotizacion"));
                Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));

                DaoCotizacionImpl daoCotizacionImpl = new DaoCotizacionImpl();
                List<DTODetalleCotizacion> lstDetalleCotizacion = daoCotizacionImpl.accionListarDTODetalleCotizacion(idcotizacion);

                for (int i = 0; i < lstDetalleCotizacion.size(); i++) {
                    respuesta += "<tr class='detalleVenta'>";
                    Integer orden = i + 1;

                    respuesta += "<td style='display: none'>" + lstDetalleCotizacion.get(i).getIdproducto() + "</td>" //COLUMNA 00:  IdProducto
                            + "<td style='display: none'>" + orden + "</td>" //COLUMNA 01:  #
                            + "<td>" + lstDetalleCotizacion.get(i).getCodigoproducto() + "</td>" //COLUMNA 02:  Código
                            + "<td>" + lstDetalleCotizacion.get(i).getNombreproducto() + "</td>" //COLUMNA 03:  Descripción
                            + "<td> <input class='input_cantidad' id='cantidad_" + orden + "' type='number' value='" + lstDetalleCotizacion.get(i).getCantidad() + "' min='1' style='font-size:10px'></td>" //COLUMNA 04:  Cantidad
                            + "<td>" + lstDetalleCotizacion.get(i).getUnidadmedida() + "</td>";     //COLUMNA 05:  Medida

                    String comboTarifas = "<td> <select class='select_tarifa' id='tarifa_" + orden + "'>";
                    comboTarifas += "<option value='" + lstDetalleCotizacion.get(i).getPreciounitarioventa() + "' >" + Math.round(lstDetalleCotizacion.get(i).getPreciounitarioventa() * Math.pow(10, 2)) / Math.pow(10, 2) + "</option>";
                    comboTarifas += "</select>";

                    //COLUMNA 06:  Precio Unitario
                    respuesta += comboTarifas;

                    //COLUMNA 07:  Valor Unitario
                    respuesta += "<td style='display: none' class='valor_unitario' id='valor_uni_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getValorunitarioventa() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 08:  Precio Total
                    respuesta += "<td style='display: none' id='precio_tot_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getPrecioventa() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 09:  Valor Total
                    respuesta += "<td style='display: none' id='valor_tot_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getValorventa() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 10:  Afecto IGV
                    respuesta += "<td style='display: none' id='afecto_igv_" + orden + "' style='display: '>" + lstDetalleCotizacion.get(i).getAfectoigv() + "</td>";

                    //COLUMNA 11:  IGV
                    respuesta += "<td style='display: none' id='igv_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getMontoigv() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 12:  ISC 
                    respuesta += "<td style='display: none' id='isc_" + orden + "' style='display: '>" + Math.round(lstDetalleCotizacion.get(i).getMontoisc() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 13:  Precio Unitario con Descuento
                    respuesta += "<td style='display: none' style='display: ' id='precio_uni_dscto_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getPreciounitariodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 14:  Valor Unitario con Descuento
                    respuesta += "<td style='display: none' style='display: ' id='valor_uni_dscto_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getValorunitariodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 15:  Precio Total con Descuento
                    respuesta += "<td id='precio_tot_dscto_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getPrecioventadescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 16:  IGV con Descuento
                    respuesta += "<td id='igv_dscto_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getMontoigvdescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 17:  Valor Total con Descuento
                    respuesta += "<td style='display: none' id='valor_tot_dscto_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getValorventadescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 18:  Descuento
                    if (lstDetalleCotizacion.get(i).getTipodescuento().equals("P")) {
                        if (lstDetalleCotizacion.get(i).getPctodescuento() == 0) {
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px'>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                                    + "<option value='P' selected>%</option>"
                        //            + "<option value='M'>MONTO</option>"
                                    + "</select>\n"
                                    + "</td>";
                        } else {
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px' value='" + Math.round(lstDetalleCotizacion.get(i).getPctodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "'>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                                    + "<option value='P' selected>%</option>"
                        //            + "<option value='M'>MONTO</option>"
                                    + "</select>\n"
                                    + "</td>";
                        }
                    } else {
                        if (lstDetalleCotizacion.get(i).getMontodescuento() == 0) {
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px'>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                                    + "<option value='P'>%</option>"
                                    + "<option value='M' selected>MONTO</option>"
                                    + "</select>\n"
                                    + "</td>";
                        } else {
                            respuesta += "<td> <input class='monto_descuento' id='descuento_" + orden + "' placeholder='0.00' size='5' type='text' style='font-size:10px' value='" + Math.round(lstDetalleCotizacion.get(i).getMontodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "'>"
                                    + "<select class='select_tipo_dcto' id='dcto_prod_" + orden + "'>"
                                    + "<option value='P'>%</option>"
                                    + "<option value='M' selected>MONTO</option>"
                                    + "</select>\n"
                                    + "</td>";
                        }
                    }

                    //COLUMNA 19:  Descuento Porcentaje
                    respuesta += "<td style='display: none' id='dscto_porc_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getPctodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";

                    //COLUMNA 20:  Descuento Monto
                    respuesta += "<td style='display: none' id='dscto_mont_" + orden + "'>" + Math.round(lstDetalleCotizacion.get(i).getMontodescuento() * Math.pow(10, 2)) / Math.pow(10, 2) + "</td>";
                    System.out.println("LOTE: " + lstDetalleCotizacion.get(i).getIdlote());
                    if (lstDetalleCotizacion.get(i).getIdlote() != 0) {
                        String query = "SELECT l.id_lote, l.numero_lote, l.fecha_vencimiento, a.stock_actual from gcbusiness.lote l, gcbusiness.almacenproductolote a "
                                + "WHERE l.id_lote = a.id_lote AND a.id_producto = l.id_producto AND a.id_producto = " + lstDetalleCotizacion.get(i).getIdproducto()
                                + " AND a.id_almacen = " + idalmacen + " AND l.id_lote = " + lstDetalleCotizacion.get(i).getIdlote();

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
                                + "WHERE id_producto = " + lstDetalleCotizacion.get(i).getIdproducto()
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
                            + "<input id='bonificacion_" + orden + "' class='ace ace-switch bonificacion' type='checkbox' value='" + lstDetalleCotizacion.get(i).getFlagbonificacion() + "'/>"
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(GCBusiness_Cotizacion_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GCBusiness_Cotizacion_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
