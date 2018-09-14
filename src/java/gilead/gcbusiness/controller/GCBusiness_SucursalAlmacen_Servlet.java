/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Seguridad
    Nombre              : GCBusiness_SucursalAlmacen_Servlet.java
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase controlador para obtener lista de Sucursales de un usuario
*/
package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoUsuarioSucursalAlmacenImpl;
import gilead.gcbusiness.model.BeanAlmacen;
import gilead.gcbusiness.model.BeanSucursal;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.ws.rs.core.MediaType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GCBusiness_SucursalAlmacen_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        //response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        
        DaoUsuarioSucursalAlmacenImpl daoUsuarioSucursalAlmacenImpl = new DaoUsuarioSucursalAlmacenImpl();
        
        if(accion.equals("sucursal")){
            String user = request.getParameter("user");
            String pass = request.getParameter("pass");
            if(user!=null && !"".equals(user.trim())){
                if(pass!=null && !"".equals(pass.trim())){
                    
                    List<BeanSucursal> list = (List<BeanSucursal>) daoUsuarioSucursalAlmacenImpl.listarSucursalUsuario(user, pass);

                    JsonArrayBuilder array = Json.createArrayBuilder();//creamos el arreglo json      javax.json-1.0.2.jar (JsonArrayBuilder y Json)     
                    for (int i = 0; i < list.size(); i++) {
                        BeanSucursal beanSucursal = list.get(i);
                        //creamos un objeto json con los campos que necesitamos
                        JsonObject item = Json.createObjectBuilder()  // javax.json-1.0.2.jar (JsonObject + Json)
                        .add("idSucursal", beanSucursal.getIdsucursal())
                        .add("nombreSucursal", beanSucursal.getDescripcion()).build();
                        array.add(item); //.. y lo agregamos al arreglo json
                    }

                    response.setContentType(MediaType.APPLICATION_JSON); //ahora preparamos la salida json al cliente...  javax.ws.rs.jar (MediaType)
                    try (JsonWriter jsonWriter = Json.createWriter(response.getOutputStream())) { //.. para imprimir...   javax.json-1.0.2.jar (JsonWriter) +  
                        jsonWriter.writeArray(array.build());
                }  
                }else{
                    request.setAttribute("errorSesion", "<div align=\"center\" class='alert alert-danger'>Debe escribir una contraseña válida.</div>");
                    request.getRequestDispatcher("GC-Business-Login.jsp").forward(request, response);
                }
            }else{
                request.setAttribute("errorSesion", "<div align=\"center\" class='alert alert-danger'>Debe escribir un usuario válido.</div>");
                request.getRequestDispatcher("GC-Business-Login.jsp").forward(request, response);
            }
        }
        
        if(accion.equals("almacen")){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String idSucursal = request.getParameter("idSucursal");
            if(idSucursal!=null && !"".equals(idSucursal.trim())){
                List<BeanAlmacen> lstAlmacen = daoUsuarioSucursalAlmacenImpl.listarAlmacenSucursal(idSucursal);
            
                out.println("<select id='almacenSucursal' name='almacenSucursal' >");
                //out.println("<option value='00' selected='selected' >Seleccione</option>");
                for(BeanAlmacen alm : lstAlmacen){
                    out.println("<option value='"+alm.getIdalmacen()+"' >"
                            +alm.getDescripcion()+"</option>");
                }
                out.println("</select>");
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
        } catch (SQLException ex) {
            Logger.getLogger(GCBusiness_SucursalAlmacen_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(GCBusiness_SucursalAlmacen_Servlet.class.getName()).log(Level.SEVERE, null, ex);
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
