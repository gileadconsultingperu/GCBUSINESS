package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoCuentaPagarImpl;
import gilead.gcbusiness.dto.DTOCuentaPagar;
import gilead.gcbusiness.model.BeanMovimientoCuentaPagar;
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

public class GCBusiness_CuentaPagar_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);

        String opcion = request.getParameter("opcion");
        System.out.println("opcion: "+opcion);
        System.out.println("Entro CUENTA PAGAR");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idproveedor = Integer.parseInt(request.getParameter("idproveedor"));
                String estado = request.getParameter("estado");
                DaoCuentaPagarImpl daoCuentaPagarImpl = new DaoCuentaPagarImpl();
                List<DTOCuentaPagar> listCuentaPagar = daoCuentaPagarImpl.listarCuentaPagar(idproveedor, estado);

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);

                for (int i = 0; i < listCuentaPagar.size(); i++) {
                    String acciones = "";

                    acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                            + "<button type='button' name='pagar' id='"+listCuentaPagar.get(i).getIdcuentapagar()+"' class='btn btn-info btn-xs pagar' title='Registrar Pago'><i class=\"ace-icon fa fa-check-square bigger-120\"></i></span></button>" 
                            + "<button type='button' name='detallar' id='"+listCuentaPagar.get(i).getIdcuentapagar()+"' class='btn btn-info btn-xs detallar' title='Imprimir'><i class=\"ace-icon fa fa-print bigger-120\"></i></span></button>"
                            + "</div>";

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("fechacompra", dateFormat.format(listCuentaPagar.get(i).getFechaemision().getTime()));
                    obj.put("comprobante", listCuentaPagar.get(i).getSerie() +" - "+ listCuentaPagar.get(i).getCorrelativoserie());
                    obj.put("totalcompra", listCuentaPagar.get(i).getTotal_compra());
                    obj.put("saldo", listCuentaPagar.get(i).getSaldo());    
                    obj.put("idcompra", listCuentaPagar.get(i).getIdcompra());
                    obj.put("acciones", acciones);       
                    datos.add(obj);
                }
                out.print(" {\"dataCuentaPagar\":" + datos.toJSONString() + "} ");
            }
        } else if (opcion.equals("listarmovimientos")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idcuentapagar = Integer.parseInt(request.getParameter("idcuentapagar"));
                System.out.println("idcc: "+idcuentapagar);
                DaoCuentaPagarImpl daoCuentaPagarImpl = new DaoCuentaPagarImpl();
                List<BeanMovimientoCuentaPagar> listCuentaPagar = daoCuentaPagarImpl.listarMovimientosCuentaPagar(idcuentapagar);

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);

                for (int i = 0; i < listCuentaPagar.size(); i++) {
                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();                   
                    obj.put("idMovimientoCC", listCuentaPagar.get(i).getIdmovimientocuentapagar());
                    obj.put("nroPago", (i+1));
                    obj.put("fecha", dateFormat.format(listCuentaPagar.get(i).getFecha().getTime()));
                    obj.put("monto", listCuentaPagar.get(i).getMonto());    
                    obj.put("saldoAnt", listCuentaPagar.get(i).getSaldoanterior());
                    obj.put("saldoAct", listCuentaPagar.get(i).getSaldoactual());
                    obj.put("acciones", "<button type='button' name='imprimir' id='"+listCuentaPagar.get(i).getIdmovimientocuentapagar()+"' class='btn btn-info btn-xs imprimir' title='Imprimir comprobante' ><span class='glyphicon glyphicon-print'></span></button>");      
                    datos.add(obj);
                }
                out.print(" {\"dataMovimientosCuentaPagar\":" + datos.toJSONString() + "} ");
            }
        } else if (opcion.equals("pago")) {
            response.setContentType("text/html;charset=UTF-8");
            System.out.println("REGISTRAR PAGO");
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

                Integer idNovimientoCuentaPagar = 0;
                String query = "SELECT NEXTVAL('gcbusiness.movimiento_cuentapagar_id_movimiento_cuentapagar_seq')";
                sqlEjecucion = query;

                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    idNovimientoCuentaPagar = rs.getInt(1);
                }
                
                Integer idcuentapagar = Integer.parseInt(request.getParameter("idcuentaPagar"));
                Double saldoAnterior = Double.parseDouble(request.getParameter("saldoTotal"));
                Double montoPagar = Double.parseDouble(request.getParameter("montoPagar")); 
                Double saldoActual = saldoAnterior-montoPagar;
                String referencia  = request.getParameter("referencia");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                
                query = "INSERT INTO gcbusiness.movimiento_cuentapagar (id_movimiento_cuentapagar, id_cuentapagar,fecha,monto,saldo_anterior,saldo_actual,documento_referencia"
                        + ",estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ("+ idNovimientoCuentaPagar + ", " + idcuentapagar + ",'" + ts + "',"+ montoPagar +", "+ saldoAnterior +", "+ saldoActual +", '"+ referencia
                        +"', 'A', '"+ ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                st.executeUpdate(query);
                
                String nuevoEstado = "P";
                if(saldoActual==0){
                    nuevoEstado="C";
                }
                
                query = "UPDATE gcbusiness.cuentapagar SET saldo="+saldoActual+", estado='"+nuevoEstado+"' WHERE id_cuentapagar="+idcuentapagar;                            
                sqlEjecucion = query;
                st.executeUpdate(query); 
  
                json = "{ \"mensaje\":\"<em>SE REGISTRÓ CORRECTAMENTE EL PAGO</em>\" ";

                cn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_CuentaPagar_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(GCBusiness_CuentaPagar_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                json += "}";
                out.print(json);
                out.close();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_CuentaPagar_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (cn != null) {
                        cn.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_CuentaPagar_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
