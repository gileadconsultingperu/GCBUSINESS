package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoAlmacenProductoLoteImpl;
import gilead.gcbusiness.dao.impl.DaoLoteImpl;
import gilead.gcbusiness.model.BeanAlmacenProductoLote;
import gilead.gcbusiness.model.BeanLote;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_Lote_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        String accion = request.getParameter("accion");
        DaoLoteImpl daoLoteImpl = new DaoLoteImpl();

        if (accion.equals("lote")) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);
            String idProducto = request.getParameter("idProducto");
            if (idProducto != null && !"".equals(idProducto.trim())) {
                List<BeanLote> lstLote = daoLoteImpl.listarLotePorProducto(Integer.parseInt(idProducto));
                String opciones = "<option value=''>Seleccione</option>\n";
                for (BeanLote lot : lstLote) {
                    opciones = opciones + "<option value='" + lot.getIdlote() + "' >"
                            + lot.getNumerolote() + "|" + dateFormat.format(lot.getFechavencimiento().getTime()) + "</option>\n";
                }
                out.println(opciones);
            }
        } else if (accion.equals("madlote")) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);
            String idProducto = request.getParameter("idProducto");
            if (idProducto != null && !"".equals(idProducto.trim())) {
                List<BeanLote> lstLote = daoLoteImpl.listarLotePorProducto(Integer.parseInt(idProducto));
                String opciones = "";
                for (BeanLote lot : lstLote) {
                    opciones = opciones + "<option value='" + lot.getIdlote() + "' >"
                            + lot.getNumerolote() + "|" + dateFormat.format(lot.getFechavencimiento().getTime()) + "</option>\n";
                }
                out.println(opciones);
            }
        } else if (accion.equals("insertar")) {
            System.out.println("INSERTAR LOTE");
            PrintWriter out = response.getWriter();
            Integer idProducto = Integer.parseInt(request.getParameter("idProducto"));
            String numeroLote = request.getParameter("numeroLote");
            String fechaVencimiento = request.getParameter("fechaVencimiento");
            String login_usuario = (String) session.getAttribute("login_usuario");
            java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());

            java.util.Date utilDateF = new java.util.Date(fechaVencimiento);
            java.sql.Date sqlDateF = new java.sql.Date(utilDateF.getTime());

            BeanLote lote = new BeanLote();
            lote.setIdproducto(idProducto);;
            lote.setNumerolote(numeroLote);
            lote.setFechavencimiento(sqlDateF);
            lote.setEstado("A");
            lote.setFechaInsercion(ts);
            lote.setUsuarioInsercion(login_usuario);
            lote.setTerminalInsercion(request.getRemoteHost());
            lote.setIpInsercion(request.getRemoteAddr());

            String json = null;
            String respuesta = daoLoteImpl.accionCrear(lote);
            if (respuesta == null) {
                json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE EL LOTE</em>\" ";
            } else {
                json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                json += ",";
                json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> " + respuesta.replace("\n", "").concat(". " + "") + "</div>\" ";
            }

            json += "}";
            out.print(json);
            out.close();
        } else if (accion.equals("stock")) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String idAlmacen = request.getParameter("idAlmacen");
            String idProducto = request.getParameter("idProducto");
            Integer idLote = request.getParameter("idLote") == null ? null : Integer.parseInt(request.getParameter("idLote"));
            if (idProducto != null && !"".equals(idProducto.trim())) {
                DaoAlmacenProductoLoteImpl almacenProductoLoteImpl = new DaoAlmacenProductoLoteImpl();
                BeanAlmacenProductoLote almacenProductoLote = almacenProductoLoteImpl.obtenerAlmacenProductoLote(Integer.parseInt(idAlmacen), Integer.parseInt(idProducto), idLote);
                out.println(almacenProductoLote.getStockactual());
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
