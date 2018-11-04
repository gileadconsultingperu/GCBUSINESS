package gilead.gcbusiness.controller;

import gilead.gcbusiness.util.NumeroLetra;
import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class GCBusiness_ImprimirComprobante_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String tipo = request.getParameter("tipo");

        if (tipo.equals("FA")) {
            Integer idventa = Integer.parseInt(request.getParameter("idventa"));
            String total = request.getParameter("total");
            NumeroLetra NumLetra = new NumeroLetra();
            String numeroenletra = NumLetra.Convertir(total, true);
            try {
                ConectaDb db = new ConectaDb();
                Connection cn = db.getConnection();

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcomprobante", idventa);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/20394040101/jasper/");
                parametros.put("P_logo", "/factele/20394040101/sunat/logo/logo.png");
                parametros.put("P_qr", "/factele/20394040101/jasper/QR.png");
                parametros.put("P_importeletras", numeroenletra);

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/20394040101/jasper/FacElectronica.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "Factura" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
            }
        } else if (tipo.equals("BO")) {
            Integer idventa = Integer.parseInt(request.getParameter("idventa"));
            String total = request.getParameter("total");
            NumeroLetra NumLetra = new NumeroLetra();
            String numeroenletra = NumLetra.Convertir(total, true);
            try {
                ConectaDb db = new ConectaDb();
                Connection cn = db.getConnection();

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcomprobante", idventa);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/20394040101/jasper/");
                parametros.put("P_logo", "/factele/20394040101/sunat/logo/logo.png");
                parametros.put("P_qr", "/factele/20394040101/jasper/QR.png");
                parametros.put("P_importeletras", numeroenletra);

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/20394040101/jasper/BolElectronica.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "Boleta" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
            }
        } else if (tipo.equals("NP")) {
            Integer idventa = Integer.parseInt(request.getParameter("idventa"));
            String total = request.getParameter("total");
            NumeroLetra NumLetra = new NumeroLetra();
            String numeroenletra = NumLetra.Convertir(total, true);
            try {
                ConectaDb db = new ConectaDb();
                Connection cn = db.getConnection();

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcomprobante", idventa);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/20394040101/jasper/");
                parametros.put("P_logo", "/factele/20394040101/sunat/logo/logo.png");
                parametros.put("P_qr", "/factele/20394040101/jasper/QR.png");
                parametros.put("P_importeletras", numeroenletra);

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/20394040101/jasper/NotaPedido.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "NotaPedido" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
            }
        } else if (tipo.equals("NC")) {
            Integer idnota = Integer.parseInt(request.getParameter("idnota"));
            String total = request.getParameter("total");
            NumeroLetra NumLetra = new NumeroLetra();
            String numeroenletra = NumLetra.Convertir(total, true);
            try {
                ConectaDb db = new ConectaDb();
                Connection cn = db.getConnection();

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcomprobante", idnota);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/20394040101/jasper/");
                parametros.put("P_logo", "/factele/20394040101/sunat/logo/logo.png");
                parametros.put("P_qr", "/factele/20394040101/jasper/QR.png");
                parametros.put("P_importeletras", numeroenletra);

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/20394040101/jasper/NCElectronica.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "NotaCredito" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
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
