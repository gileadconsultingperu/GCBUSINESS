package gilead.gcbusiness.controller;

import gilead.gcbusiness.util.NumeroLetra;
import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = request.getSession(false);
        String rucEmpresa = (String) session.getAttribute("rucEmpresa");
        String tipo = request.getParameter("tipo");

        if (tipo.equals("FA")) {
            Integer idventa = Integer.parseInt(request.getParameter("idventa"));
            String total = request.getParameter("total");
            NumeroLetra NumLetra = new NumeroLetra();
            String numeroenletra = NumLetra.Convertir(total, true);
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcomprobante", idventa);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/" + rucEmpresa + "/jasper/");
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");
                parametros.put("P_qr", "/factele/" + rucEmpresa + "/jasper/QR.png");
                parametros.put("P_importeletras", numeroenletra);

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/FacElectronica.jasper");

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
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("BO")) {
            Integer idventa = Integer.parseInt(request.getParameter("idventa"));
            String total = request.getParameter("total");
            NumeroLetra NumLetra = new NumeroLetra();
            String numeroenletra = NumLetra.Convertir(total, true);
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcomprobante", idventa);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/" + rucEmpresa + "/jasper/");
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");
                parametros.put("P_qr", "/factele/" + rucEmpresa + "/jasper/QR.png");
                parametros.put("P_importeletras", numeroenletra);

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/BolElectronica.jasper");

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
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("NP")) {
            Integer idventa = Integer.parseInt(request.getParameter("idventa"));
            String total = request.getParameter("total");
            NumeroLetra NumLetra = new NumeroLetra();
            String numeroenletra = NumLetra.Convertir(total, true);
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcomprobante", idventa);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/" + rucEmpresa + "/jasper/");
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");
                parametros.put("P_qr", "/factele/" + rucEmpresa + "/jasper/QR.png");
                parametros.put("P_importeletras", numeroenletra);

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/NotaPedido.jasper");

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
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("NC")) {
            Integer idnota = Integer.parseInt(request.getParameter("idnota"));
            String total = request.getParameter("total");
            NumeroLetra NumLetra = new NumeroLetra();
            String numeroenletra = NumLetra.Convertir(total, true);
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcomprobante", idnota);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/" + rucEmpresa + "/jasper/");
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");
                parametros.put("P_qr", "/factele/" + rucEmpresa + "/jasper/QR.png");
                parametros.put("P_importeletras", numeroenletra);

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/NCElectronica.jasper");

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
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("DE")) {
            Integer idnota = Integer.parseInt(request.getParameter("idnota"));
            String total = request.getParameter("total");
            NumeroLetra NumLetra = new NumeroLetra();
            String numeroenletra = NumLetra.Convertir(total, true);
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcomprobante", idnota);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/" + rucEmpresa + "/jasper/");
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");
                parametros.put("P_qr", "/factele/" + rucEmpresa + "/jasper/QR.png");
                parametros.put("P_importeletras", numeroenletra);

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/DevNotaPedido.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "DevolucionNotaPedido" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("CC")) {
            String idcuentacobrar = request.getParameter("idcuentacobrar");
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcuentacobrar", Integer.parseInt(idcuentacobrar));
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/" + rucEmpresa + "/jasper/");

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/CuentaxCobrar.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "CuentaxCobrar" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("MCC")) {
            String idmovimientocuentacobrar = request.getParameter("idmovimientocuentacobrar");
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idmovimientocuentacobrar", Integer.parseInt(idmovimientocuentacobrar));
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/DocPago.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "DocPago" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("CP")) {
            String idcuentapagar = request.getParameter("idcuentapagar");
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcuentapagar", Integer.parseInt(idcuentapagar));
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/" + rucEmpresa + "/jasper/");

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/CuentaxPagar.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "CuentaxPagar" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("MCP")) {
            String idmovimientocuentapagar = request.getParameter("idmovimientocuentapagar");
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idmovimientocuentapagar", Integer.parseInt(idmovimientocuentapagar));
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/DocPagoCP.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "DocPagoCP" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("MI")) {
            String idmovimientoinventario = request.getParameter("idmovimientoinventario");
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idmovimientoinventario", Integer.parseInt(idmovimientoinventario));
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/ConInventario.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "MovimientoInventario" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (tipo.equals("CO")) {
            Integer idcompra = Integer.parseInt(request.getParameter("idcompra"));
            ConectaDb db = new ConectaDb();
            Connection cn = db.getConnection();
            try {
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_idcompra", idcompra);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("SUBREPORT_DIR", "/factele/" + rucEmpresa + "/jasper/");

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/RegCompra.jasper");

                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline;filename=" + "RegistroCompra" + ".pdf");
                response.getOutputStream().write(pdfBytes);
                response.flushBuffer();

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_ImprimirComprobante_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_ImprimirComprobante_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
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
