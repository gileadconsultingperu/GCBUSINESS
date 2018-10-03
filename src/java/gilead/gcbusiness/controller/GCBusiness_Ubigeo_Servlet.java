package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoUbigeoImpl;
import gilead.gcbusiness.model.BeanUbigeo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GCBusiness_Ubigeo_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("edded");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        DaoUbigeoImpl daoUbigeo = new DaoUbigeoImpl();
        
        String codigo_ubidepartamento = request.getParameter("codigo_ubidepartamento")!=null?(String) request.getParameter("codigo_ubidepartamento"):"";
        String codigo_ubiprovincia = request.getParameter("codigo_ubiprovincia")!=null?(String) request.getParameter("codigo_ubiprovincia"):"";
        
        System.out.println("codigo_ubiprovincia: "+codigo_ubiprovincia + "  codigo_ubidepartamento"+codigo_ubidepartamento);
        /*
        if(!codigo_ubidepartamento.equals("") && codigo_ubiprovincia.equals("")){
            System.out.println("codigo_ubidepartamento "+codigo_ubidepartamento);
            List<BeanUbigeo> listaProvincia = daoUbigeo.accionListarProvincias(codigo_ubidepartamento);
            
            //out.println("<select id='ubigeoProvincia' name='ubigeoProvincia' >");
            //out.println("<option value='00' selected='selected' >Seleccione</option>");
            for(BeanUbigeo ubigeo : listaProvincia){
                out.println("<option value='"+ubigeo.getCodigo_ubiprovincia()+"' >"
                        +ubigeo.getDescripcionUbigeo()+"</option>");
            }
            //out.println("</select>");
        }
        if(codigo_ubidepartamento.equals("") && !codigo_ubiprovincia.equals("")){
            System.out.println("codigo_ubiprovincia "+codigo_ubiprovincia);
            List<BeanUbigeo> listaDistrito = daoUbigeo.accionListarDistritos(codigo_ubiprovincia);
            
            //out.println("<select id='ubigeoDistrito' name='ubigeoDistrito' >");
            //out.println("<option value='00' selected='selected' >Seleccione</option>");
            for(BeanUbigeo ubigeo : listaDistrito){
                out.println("<option value='"+ubigeo.getCodigo_ubidistrito()+"' >"
                        +ubigeo.getDescripcionUbigeo()+"</option>");
            }
            //out.println("</select>");
        }
        */
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
