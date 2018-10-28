/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.gcbusiness.controller;

import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.io.PrintWriter;
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

                    idTarifa = Integer.parseInt((String) fila1.get("Precio Unitario"));
                    query = "SELECT valor FROM gcbusiness.tarifaproducto WHERE id_producto =" + idProducto + " AND id_tarifa = " + idTarifa;
                    ResultSet rs3 = st.executeQuery(query);
                    if (rs3.next()) {
                        precio_unitario_venta = rs3.getDouble(1);
                    }
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

                    //Insertar detalle venta
                    query = "INSERT INTO gcbusiness.detalle_cotizacion (id_cotizacion, id_producto, cantidad, tipo_igv, pcto_igv, valor_unitario_venta, precio_unitario_venta, valor_venta, precio_venta, "
                            + "monto_igv, tipo_isc, monto_isc, flag_bonificacion, tipo_descuento, monto_descuento, pcto_descuento, valor_venta_descuento, precio_venta_descuento, monto_igv_descuento, "
                            + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                            + "VALUES (" + idCotizacion + ", " + idProducto + ", " + cantidad + ", '" + tipoIGV + "', " + pctoIGV + ", " + valor_unitario_venta + ", " + precio_unitario_venta + ", " + valor_venta + ", " + precio_venta + ", "
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
