/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoMovimientoAlmacenImpl;
import gilead.gcbusiness.dto.DTOMovimientoAlmacen;
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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Luis
 */
public class GCBusiness_TrasladoAlmacenes_Servlet extends HttpServlet {

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
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);

        String opcion = request.getParameter("opcion");
        System.out.println("Entro TRASLADO ALMACEN");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoMovimientoAlmacenImpl daoMovimientoAlmacenImpl = new DaoMovimientoAlmacenImpl();
                List<DTOMovimientoAlmacen> listMovimientoAlmacen = daoMovimientoAlmacenImpl.listarMovimientoAlmacen();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);

                for (int i = 0; i < listMovimientoAlmacen.size(); i++) {
                    String acciones = "";

                    acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                            + "<button type='button' name='imprimir' id='" + listMovimientoAlmacen.get(i).getIdmovimientoalmacen() + "' class='btn btn-xs btn-info imprimir' title='Imprimir Registro'><i class=\"ace-icon fa fa-print bigger-120\"></i></button>";

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("fecha", dateFormat.format(listMovimientoAlmacen.get(i).getFecha().getTime()));
                    obj.put("almacenorigen", listMovimientoAlmacen.get(i).getAlmacenorigen());
                    obj.put("almacendestino", listMovimientoAlmacen.get(i).getAlmacendestino());
                    obj.put("observacion", listMovimientoAlmacen.get(i).getObservacion());
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }
                System.out.println(" {\"data\":" + datos.toJSONString() + "} ");
                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        } else if (opcion.equals("insertar")) {
            System.out.println("INSERTAR REGISTRO TRASLADO ALMACEN");
            Integer idalmacenorigen = Integer.parseInt(request.getParameter("idalmacenorigen"));
            Integer idalmacendestino = Integer.parseInt(request.getParameter("idalmacendestino"));
            String observacion = request.getParameter("observacion").toUpperCase();
            java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
            String login_usuario = (String) session.getAttribute("login_usuario");
            String data = request.getParameter("detalle");
            String json = null;
            org.json.JSONArray detalle = null;
            try {
                detalle = new org.json.JSONArray(data);
            } catch (JSONException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            PrintWriter out = response.getWriter();
            if (0 < detalle.length()) {
                ConectaDb db = new ConectaDb();
                Connection cn = null;
                Statement st = null;
                String sqlEjecucion = null;
                ResultSet rs = null;

                try {
                    cn = db.getConnection();
                    cn.setAutoCommit(false);
                    st = cn.createStatement();

                    //INGRESAR MOVIMIENTO ALMACEN
                    Integer idMovimientoAlmacen = 0;
                    String query = "SELECT NEXTVAL('gcbusiness.movimientoalmacen_id_movimientoalmacen_seq')";
                    sqlEjecucion = query;

                    rs = st.executeQuery(query);

                    if (rs.next()) {
                        idMovimientoAlmacen = rs.getInt(1);
                    }

                    query = "INSERT INTO gcbusiness.movimientoalmacen (id_movimientoalmacen, id_almacen_origen, id_almacen_destino, fecha, observacion, estado,"
                            + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                            + " VALUES (" + idMovimientoAlmacen + ", " + idalmacenorigen + ", " + idalmacendestino + ", '" + ts + "', '" + observacion + "', 'A',"
                            + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                    sqlEjecucion = query;
                    st.executeUpdate(query);

                    //INGRESAR DETALLE MOVIMIENTO INVENTARIO
                    for (int i = 0; i < detalle.length(); i++) {
                        JSONObject fila = null;
                        fila = detalle.getJSONObject(i);
                        Integer idproducto = fila.getInt("IdProducto");
                        Integer idlote = fila.get("IdLote").equals("null") ? null : fila.getInt("IdLote");
                        Double cantidad = fila.getDouble("Cantidad");

                        query = "INSERT INTO gcbusiness.detalle_movimientoalmacen (id_movimientoalmacen, id_producto, id_lote, cantidad,"
                                + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                                + " VALUES (" + idMovimientoAlmacen + ", " + idproducto + ", " + idlote + ", " + cantidad + ","
                                + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                        sqlEjecucion = query;
                        st.executeUpdate(query);
                    }

                    //EGRESO ALMACEN ORIGEN
                    //INGRESAR MOVIMIENTO INVENTARIO      
                    Integer idMovimientoInventario = 0;
                    query = "SELECT NEXTVAL('gcbusiness.movimientoinventario_id_movimientoinventario_seq')";
                    sqlEjecucion = query;

                    rs = st.executeQuery(query);

                    if (rs.next()) {
                        idMovimientoInventario = rs.getInt(1);
                    }

                    query = "INSERT INTO gcbusiness.movimientoinventario (id_movimientoinventario, id_almacen, id_motivomovimiento, fecha, observacion, id_referencia, estado,"
                            + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                            + " VALUES (" + idMovimientoInventario + ", " + idalmacenorigen + ", 11, '" + ts + "', '" + observacion + "', " + idMovimientoAlmacen + ", 'A',"
                            + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                    sqlEjecucion = query;
                    st.executeUpdate(query);

                    //INGRESAR DETALLE MOVIMIENTO INVENTARIO
                    for (int i = 0; i < detalle.length(); i++) {
                        JSONObject fila = null;
                        fila = detalle.getJSONObject(i);
                        Integer idproducto = fila.getInt("IdProducto");
                        Integer idlote = fila.get("IdLote").equals("null") ? null : fila.getInt("IdLote");
                        Double cantidad = fila.getDouble("Cantidad");

                        //OBTENER STOCK ACTURAL
                        double stock = 0;
                        double nuevostock = 0;
                        if (idlote == null) {
                            query = "SELECT stock_actual FROM gcbusiness.almacenproductolote"
                                    + " WHERE id_producto = " + idproducto + " AND id_almacen = " + idalmacenorigen;
                        } else {
                            query = "SELECT stock_actual FROM gcbusiness.almacenproductolote"
                                    + " WHERE id_producto = " + idproducto + " AND id_almacen = " + idalmacenorigen + " AND id_lote = " + idlote;
                        }

                        System.out.println("OBTENER STOCK ACTUAL query: " + query);
                        rs = st.executeQuery(query);

                        if (rs.next()) {
                            stock = rs.getDouble(1);
                        }

                        nuevostock = stock - cantidad;

                        query = "INSERT INTO gcbusiness.detalle_movimientoinventario (id_movimientoinventario, id_producto, id_lote, cantidad, stock_saldo,"
                                + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                                + " VALUES (" + idMovimientoInventario + ", " + idproducto + ", " + idlote + ", " + cantidad + "," + nuevostock + ","
                                + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                        sqlEjecucion = query;
                        st.executeUpdate(query);

                        //ACTUALIZAR STOCK
                        if (idlote == null) {
                            query = "UPDATE gcbusiness.almacenproductolote SET stock_actual = " + nuevostock
                                    + " WHERE id_producto = " + idproducto + " AND id_almacen = " + idalmacenorigen;
                        } else {
                            query = "UPDATE gcbusiness.almacenproductolote SET stock_actual = " + nuevostock
                                    + " WHERE id_producto = " + idproducto + " AND id_almacen = " + idalmacenorigen + " AND id_lote = " + idlote;
                        }

                        sqlEjecucion = query;
                        st.executeUpdate(query);
                    }

                    //INGRESO ALMACEN DESTINO
                    //INGRESAR MOVIMIENTO INVENTARIO      
                    idMovimientoInventario = 0;
                    query = "SELECT NEXTVAL('gcbusiness.movimientoinventario_id_movimientoinventario_seq')";
                    sqlEjecucion = query;

                    rs = st.executeQuery(query);

                    if (rs.next()) {
                        idMovimientoInventario = rs.getInt(1);
                    }

                    query = "INSERT INTO gcbusiness.movimientoinventario (id_movimientoinventario, id_almacen, id_motivomovimiento, fecha, observacion, id_referencia, estado,"
                            + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                            + " VALUES (" + idMovimientoInventario + ", " + idalmacendestino + ", 3, '" + ts + "', '" + observacion + "', " + idMovimientoAlmacen + ", 'A',"
                            + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                    sqlEjecucion = query;
                    st.executeUpdate(query);

                    //INGRESAR DETALLE MOVIMIENTO INVENTARIO
                    for (int i = 0; i < detalle.length(); i++) {
                        JSONObject fila = null;
                        fila = detalle.getJSONObject(i);
                        Integer idproducto = fila.getInt("IdProducto");
                        Integer idlote = fila.get("IdLote").equals("null") ? null : fila.getInt("IdLote");
                        Double cantidad = fila.getDouble("Cantidad");

                        //OBTENER STOCK ACTURAL
                        double stock = 0;
                        double nuevostock = 0;
                        if (idlote == null) {
                            query = "SELECT stock_actual FROM gcbusiness.almacenproductolote"
                                    + " WHERE id_producto = " + idproducto + " AND id_almacen = " + idalmacendestino;
                        } else {
                            query = "SELECT stock_actual FROM gcbusiness.almacenproductolote"
                                    + " WHERE id_producto = " + idproducto + " AND id_almacen = " + idalmacendestino + " AND id_lote = " + idlote;
                        }

                        System.out.println("OBTENER STOCK ACTUAL query: " + query);
                        rs = st.executeQuery(query);

                        if (rs.next()) {
                            stock = rs.getDouble(1);
                        }

                        nuevostock = stock + cantidad;

                        query = "INSERT INTO gcbusiness.detalle_movimientoinventario (id_movimientoinventario, id_producto, id_lote, cantidad, stock_saldo,"
                                + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                                + " VALUES (" + idMovimientoInventario + ", " + idproducto + ", " + idlote + ", " + cantidad + "," + nuevostock + ","
                                + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                        sqlEjecucion = query;
                        st.executeUpdate(query);

                        //ACTUALIZAR STOCK
                        if (idlote == null) {
                            query = "UPDATE gcbusiness.almacenproductolote SET stock_actual = " + nuevostock
                                    + " WHERE id_producto = " + idproducto + " AND id_almacen = " + idalmacendestino;
                        } else {
                            query = "UPDATE gcbusiness.almacenproductolote SET stock_actual = " + nuevostock
                                    + " WHERE id_producto = " + idproducto + " AND id_almacen = " + idalmacendestino + " AND id_lote = " + idlote;
                        }

                        sqlEjecucion = query;
                        st.executeUpdate(query);
                    }

                    json = "{ \"mensaje\":\"<em>SE GUARDÓ CORRECTAMENTE EL REGISTRO</em>\" ";

                    cn.commit();

                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_IngresoSalida_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> " + ex.getMessage().replace("\"", "\\\"").replace("\n", "").concat(". " + sqlEjecucion) + "</div>\" ";
                    if (cn != null) {
                        System.out.println("Rollback");
                        try {
                            //deshace todos los cambios realizados en los datos
                            cn.rollback();
                        } catch (SQLException ex1) {
                            //System.err.println( "No se pudo deshacer" + ex1.getMessage() );
                            Logger.getLogger(GCBusiness_IngresoSalida_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(GCBusiness_IngresoSalida_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> " + ex.getMessage().replace("\"", "\\\"").replace("\n", "").concat(". " + sqlEjecucion) + "</div>\" ";
                    if (cn != null) {
                        System.out.println("Rollback");
                        try {
                            //deshace todos los cambios realizados en los datos
                            cn.rollback();
                        } catch (SQLException ex1) {
                            //System.err.println( "No se pudo deshacer" + ex1.getMessage() );
                            Logger.getLogger(GCBusiness_IngresoSalida_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                } finally {
                    json += "}";
                    out.print(json);
                    out.close();
                    try {
                        if (cn != null) {
                            cn.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (rs != null) {
                            rs.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(GCBusiness_IngresoSalida_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                json += ",";
                json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> " + "NO EXISTE DETALLE".replace("\"", "\\\"").replace("\n", "").concat(". " + "") + "</div>\" ";
                json += "}";
                out.print(json);
                out.close();
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
