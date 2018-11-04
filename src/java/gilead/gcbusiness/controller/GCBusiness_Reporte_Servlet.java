package gilead.gcbusiness.controller;

import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class GCBusiness_Reporte_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);

        String opcion = request.getParameter("opcion");
        System.out.println("opcion: " + opcion);
        System.out.println("Entro REPORTE");

        if (opcion.equals("CC")) {//CUENTAS POR COBRAR
            response.setContentType("text/html;charset=UTF-8");
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            String fdesde = request.getParameter("fdesde");
            String fhasta = request.getParameter("fhasta");
            Integer idvendedor = Integer.parseInt(request.getParameter("idvendedor"));
            String estadoCC = request.getParameter("estadoCC");
            String fileType = request.getParameter("fileType");
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                cn = db.getConnection();

                String query = "select ve.descripcion,c.numero_documento,c.nombre,v.fecha_emision,tc.abreviatura,s.serie,v.correlativo_serie,"
                        + "v.total_venta,cc.saldo from gcbusiness.cuentacobrar cc "
                        + "left join gcbusiness.venta v on v.id_venta=cc.id_comprobante "
                        + "left join gcbusiness.cliente c on c.id_cliente=v.id_cliente "
                        + "left join gcbusiness.serie s on s.id_serie=v.id_serie "
                        + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante=v.id_tipocomprobante "
                        + "left join gcbusiness.vendedor ve on ve.id_vendedor=c.id_vendedor "
                        + "WHERE date(v.fecha_emision) between '" + fdesde + "' AND '" + fhasta
                        + "' AND cc.estado = '" + estadoCC + "' ";

                if (idvendedor != 0) {
                    query += "AND ve.id_vendedor=" + idvendedor;
                }

                query += " order by ve.id_vendedor, v.id_cliente, v.fecha_emision,s.serie,v.correlativo_serie;";

                System.out.println("query: " + query);

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_finicio", sourceFormat.parse(fdesde));
                parametros.put("P_ffin", sourceFormat.parse(fhasta));
                parametros.put("P_estado", estadoCC.equals("P") ? "PENDIENTE" : "CANCELADO");
                parametros.put("P_QUERY", query);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("P_logo", "/factele/20352417794/sunat/logo/logo.jpg");

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele2/jasper/reporte/R06_CuentasPorCobrar.jasper");
                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                if (fileType.equals("PDF")) {
                    byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline;filename=" + "Reporte_CuentasPorCobrar" + ".pdf");
                    response.getOutputStream().write(pdfBytes);
                    response.flushBuffer();
                }

                if (fileType.equals("EXCEL")) {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-disposition", "attachment; filename=" + "Reporte_CuentasPorCobrar" + ".xls");
                    ServletOutputStream out = response.getOutputStream();
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                    exporter.exportReport();
                    out.flush();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_CuentaCobrar_Servlet - Error: " + ex.toString());
            }
        } else if (opcion.equals("VE")) {//CUENTAS POR COBRAR
            response.setContentType("text/html;charset=UTF-8");
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            String fdesde = request.getParameter("fdesde");
            String fhasta = request.getParameter("fhasta");
            Integer idvendedor = Integer.parseInt(request.getParameter("idvendedor"));
            Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
            Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
            String fileType = request.getParameter("fileType");
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                cn = db.getConnection();

                String query = "select ve.descripcion vendedor, v.fecha_emision fechaemision, tc.abreviatura tipocomprobante, s.serie || '-' || v.correlativo_serie nrocomprobante,"
                        + " c.numero_documento nrodocumento_cliente, c.nombre nombre_cliente, v.total_venta monto\n"
                        + "from gcbusiness.venta v\n"
                        + "left join gcbusiness.cliente c on c.id_cliente = v.id_cliente\n"
                        + "left join gcbusiness.vendedor ve on ve.id_vendedor = c.id_vendedor\n"
                        + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante = v.id_tipocomprobante\n"
                        + "left join gcbusiness.serie s on s.id_serie = v.id_serie\n"
                        + "left join gcbusiness.moneda m on m.id_moneda = v.id_moneda "
                        + "WHERE date(v.fecha_emision) between '" + fdesde + "' AND '" + fhasta
                        + "' AND v.estado = 'E' ";

                if (idvendedor != 0) {
                    query += "AND ve.id_vendedor=" + idvendedor;
                }

                query += " order by ve.descripcion, v.fecha_emision;";

                System.out.println("query: " + query);
                
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_finicio", sourceFormat.parse(fdesde));
                parametros.put("P_ffin", sourceFormat.parse(fhasta));
                parametros.put("P_QUERY", query);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("P_logo", "/factele/20352417794/sunat/logo/logo.jpg");

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele2/jasper/reporte/R02_VentaPorVendedor.jasper");
                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                if (fileType.equals("PDF")) {
                    byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline;filename=" + "Reporte_Ventas" + ".pdf");
                    response.getOutputStream().write(pdfBytes);
                    response.flushBuffer();
                }

                if (fileType.equals("EXCEL")) {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-disposition", "attachment; filename=" + "Reporte_Ventas" + ".xls");
                    ServletOutputStream out = response.getOutputStream();
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                    exporter.exportReport();
                    out.flush();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_CuentaCobrar_Servlet - Error: " + ex.toString());
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
