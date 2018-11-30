package gilead.gcbusiness.controller;

import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        String rucEmpresa = (String) session.getAttribute("rucEmpresa");
        String opcion = request.getParameter("opcion");
        System.out.println("opcion: " + opcion);
        System.out.println("Entro REPORTE");

        if (opcion.equals("CC")) {//CUENTAS POR COBRAR
            response.setContentType("text/html;charset=UTF-8");
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            String fdesde = request.getParameter("fdesde");
            String fhasta = request.getParameter("fhasta");
            String busqueda = request.getParameter("busqueda").toUpperCase();
            Integer idvendedor = Integer.parseInt(request.getParameter("idvendedor"));
            Integer idcliente = Integer.parseInt(request.getParameter("idcliente"));
            String estadoCC = request.getParameter("estadoCC");
            String fileType = request.getParameter("fileType");
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                cn = db.getConnection();

                String query = "";
                String titulo = "";
                if (busqueda.equals("RBVENDEDOR")) {
                    query = "select 'VENDEDOR: ' || ve.descripcion criterio, 'CLIENTE: ' || c.numero_documento || '-' || c.nombre subcriterio,v.fecha_emision,tc.abreviatura,s.serie,v.correlativo_serie,"
                            + "v.total_venta,cc.saldo, CASE WHEN cc.estado='P' THEN 'PENDIENTE' WHEN cc.estado='C' THEN 'CANCELADO' WHEN cc.estado='A' THEN 'ANULADO' END estado "
                            + "from gcbusiness.cuentacobrar cc "
                            + "left join gcbusiness.venta v on v.id_venta=cc.id_comprobante "
                            + "left join gcbusiness.cliente c on c.id_cliente=v.id_cliente "
                            + "left join gcbusiness.serie s on s.id_serie=v.id_serie "
                            + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante=v.id_tipocomprobante "
                            + "left join gcbusiness.vendedor ve on ve.id_vendedor=c.id_vendedor "
                            + "WHERE date(v.fecha_emision) between to_date('" + fdesde + "','dd/mm/yyyy') AND to_date('" + fhasta
                            + "','dd/mm/yyyy') ";

                    titulo = "POR VENDEDOR - TODOS";

                    if (idvendedor != 0) {
                        query += "AND ve.id_vendedor=" + idvendedor;
                        titulo = "POR VENDEDOR";
                    }

                } else {
                    query = "select 'CLIENTE: ' || c.numero_documento || '-' || c.nombre criterio, 'VENDEDOR: ' || ve.descripcion subcriterio,v.fecha_emision,tc.abreviatura,s.serie,v.correlativo_serie,"
                            + "v.total_venta,cc.saldo, CASE WHEN cc.estado='P' THEN 'PENDIENTE' WHEN cc.estado='C' THEN 'CANCELADO' WHEN cc.estado='A' THEN 'ANULADO' END estado "
                            + "from gcbusiness.cuentacobrar cc "
                            + "left join gcbusiness.venta v on v.id_venta=cc.id_comprobante "
                            + "left join gcbusiness.cliente c on c.id_cliente=v.id_cliente "
                            + "left join gcbusiness.serie s on s.id_serie=v.id_serie "
                            + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante=v.id_tipocomprobante "
                            + "left join gcbusiness.vendedor ve on ve.id_vendedor=c.id_vendedor "
                            + "WHERE date(v.fecha_emision) between to_date('" + fdesde + "','dd/mm/yyyy') AND to_date('" + fhasta
                            + "','dd/mm/yyyy') ";

                    titulo = "POR CLIENTE - TODOS";

                    if (idcliente != 0) {
                        query += "AND c.id_cliente=" + idcliente;
                        titulo = "POR CLIENTE";
                    }
                }

                if (!estadoCC.equals("0")) {
                    query += " AND cc.estado = '" + estadoCC + "' ";
                }

                query += " order by criterio, subcriterio, v.fecha_emision,s.serie,v.correlativo_serie;";

                System.out.println("query: " + query);

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_finicio", sourceFormat.parse(fdesde));
                parametros.put("P_ffin", sourceFormat.parse(fhasta));
                parametros.put("P_estado", estadoCC.equals("P") ? "PENDIENTE" : estadoCC.equals("C") ? "CANCELADO" : "A");
                parametros.put("P_titulo", titulo);
                parametros.put("P_QUERY", query);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/reporte/R06_CuentasPorCobrar.jasper");
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

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_Reporte_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_Reporte_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (opcion.equals("VE")) {//VENTAS
            response.setContentType("text/html;charset=UTF-8");
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            String fdesde = request.getParameter("fdesde");
            String fhasta = request.getParameter("fhasta");
            String busqueda = request.getParameter("busqueda").toUpperCase();
            Integer idvendedor = Integer.parseInt(request.getParameter("idvendedor"));
            Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
            Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
            String fileType = request.getParameter("fileType");
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                cn = db.getConnection();

                String query = "";
                String titulo = "";
                if (busqueda.equals("RBVENDEDOR")) {
                    query += "SELECT ve.descripcion vendedor,v.fecha_emision fechaemision, tc.abreviatura tipocomprobante, s.serie || '-' || v.correlativo_serie nrocomprobante,"
                            + " c.numero_documento nrodocumento_cliente, c.nombre nombre_cliente, v.total_venta monto\n"
                            + "from gcbusiness.venta v\n"
                            + "left join gcbusiness.cliente c on c.id_cliente = v.id_cliente "
                            + "left join gcbusiness.vendedor ve on ve.id_vendedor = c.id_vendedor "
                            + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante = v.id_tipocomprobante "
                            + "left join gcbusiness.serie s on s.id_serie = v.id_serie "
                            + "left join gcbusiness.moneda m on m.id_moneda = v.id_moneda "
                            + "WHERE date(v.fecha_emision) between to_date('" + fdesde + "','dd/mm/yyyy') AND to_date('" + fhasta
                            + "','dd/mm/yyyy') AND v.estado = 'E' ";
                    if (idvendedor != 0) {
                        query += "AND ve.id_vendedor=" + idvendedor;
                    }
                    titulo = "POR VENDEDOR";
                } else {
                    query += "SELECT 'Sucursal: ' || su.descripcion || ' - Almacén: ' || a.descripcion vendedor,v.fecha_emision fechaemision, tc.abreviatura tipocomprobante, s.serie || '-' || v.correlativo_serie nrocomprobante,"
                            + " c.numero_documento nrodocumento_cliente, c.nombre nombre_cliente, v.total_venta monto\n"
                            + "from gcbusiness.venta v\n"
                            + "left join gcbusiness.cliente c on c.id_cliente = v.id_cliente "
                            + "left join gcbusiness.sucursal su on su.id_sucursal = v.id_sucursal left join gcbusiness.almacen a on a.id_almacen = v.id_almacen "
                            + "left join gcbusiness.tipocomprobante tc on tc.id_tipocomprobante = v.id_tipocomprobante\n"
                            + "left join gcbusiness.serie s on s.id_serie = v.id_serie\n"
                            + "left join gcbusiness.moneda m on m.id_moneda = v.id_moneda "
                            + "WHERE date(v.fecha_emision) between to_date('" + fdesde + "','dd/mm/yyyy') AND to_date('" + fhasta
                            + "','dd/mm/yyyy') AND v.estado = 'E' ";
                    if (idsucursal != 0) {
                        if (idalmacen != 0) {
                            query += "AND su.id_sucursal=" + idsucursal + " AND a.id_almacen=" + idalmacen;
                        } else {
                            query += "AND su.id_sucursal=" + idsucursal;
                        }
                    }
                    titulo = "POR ALMACEN";
                }
                query += " order by vendedor, v.fecha_emision;";

                System.out.println("query: " + query);

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_finicio", sourceFormat.parse(fdesde));
                parametros.put("P_ffin", sourceFormat.parse(fhasta));
                parametros.put("P_titulo", titulo);
                parametros.put("P_QUERY", query);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/reporte/R02_Venta.jasper");
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

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_Reporte_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_Reporte_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (opcion.equals("INV")) {//INVENTARIO
            response.setContentType("text/html;charset=UTF-8");
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
            Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
            Integer idfamilia = Integer.parseInt(request.getParameter("idfamilia"));
            String stock = request.getParameter("stock");
            String fileType = request.getParameter("fileType");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
            SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaActual = simpleFormat.format(utilDate);
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String tipo = request.getParameter("tipo").toUpperCase();
            Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
            try {
                cn = db.getConnection();

                String query = "";
                if (tipo.equals("RBVALORIZADO")) {
                    query = "select s.descripcion sucursal, a.descripcion almacen, f.descripcion familia, cl.descripcion clase, "
                            + "l.descripcion linea, c.descripcion categoria, p.codigo_interno || ' - ' || p.descripcion producto, "
                            + "pa.stock_actual stock, p.precio_compra costo\n"
                            + "from gcbusiness.almacenproductolote pa\n"
                            + "left join gcbusiness.almacen a on a.id_almacen = pa.id_almacen\n"
                            + "left join gcbusiness.sucursal s on s.id_sucursal = a.id_sucursal\n"
                            + "left join gcbusiness.producto p on p.id_producto = pa.id_producto\n"
                            + "left join gcbusiness.categoriaproducto c on c.id_categoriaproducto = p.id_categoriaproducto\n"
                            + "left join gcbusiness.lineaproducto l on l.id_lineaproducto = c.id_lineaproducto\n"
                            + "left join gcbusiness.claseproducto cl on cl.id_claseproducto = l.id_claseproducto\n"
                            + "left join gcbusiness.familiaproducto f on f.id_familiaproducto = cl.id_familiaproducto\n"
                            + "where p.estado = 'A' AND s.descripcion is not null\n";
                } else {
                    query = "select s.descripcion sucursal, a.descripcion almacen, f.descripcion familia, cl.descripcion clase, "
                            + "l.descripcion linea, c.descripcion categoria, p.codigo_interno || ' - ' || p.descripcion producto, "
                            + "pa.stock_actual stock, tp1.valor tp1, tp2.valor tp2, tp3.valor tp3, tp4.valor tp4, tp5.valor tp5\n"
                            + "from gcbusiness.almacenproductolote pa\n"
                            + "left join gcbusiness.almacen a on a.id_almacen = pa.id_almacen\n"
                            + "left join gcbusiness.sucursal s on s.id_sucursal = a.id_sucursal\n"
                            + "left join gcbusiness.producto p on p.id_producto = pa.id_producto\n"
                            + "left join gcbusiness.categoriaproducto c on c.id_categoriaproducto = p.id_categoriaproducto\n"
                            + "left join gcbusiness.lineaproducto l on l.id_lineaproducto = c.id_lineaproducto\n"
                            + "left join gcbusiness.claseproducto cl on cl.id_claseproducto = l.id_claseproducto\n"
                            + "left join gcbusiness.familiaproducto f on f.id_familiaproducto = cl.id_familiaproducto\n"
                            + "left join gcbusiness.tarifaproducto tp1 on tp1.id_producto = p.id_producto and tp1.id_tarifa=1\n"
                            + "left join gcbusiness.tarifaproducto tp2 on tp2.id_producto = p.id_producto and tp2.id_tarifa=2\n"
                            + "left join gcbusiness.tarifaproducto tp3 on tp3.id_producto = p.id_producto and tp3.id_tarifa=3\n"
                            + "left join gcbusiness.tarifaproducto tp4 on tp4.id_producto = p.id_producto and tp4.id_tarifa=4\n"
                            + "left join gcbusiness.tarifaproducto tp5 on tp5.id_producto = p.id_producto and tp5.id_tarifa=5\n"
                            + "where p.estado = 'A' AND s.descripcion is not null\n";
                }

                if (stock.equals("S")) {
                    query += "AND pa.stock_actual>0.00\n";
                }

                if (idfamilia != 0) {
                    query += "AND f.id_familiaproducto=" + idfamilia;
                }

                if (idsucursal != 0) {
                    if (idalmacen != 0) {
                        query += " AND s.id_sucursal=" + idsucursal + " AND pa.id_almacen=" + idalmacen;
                    } else {
                        query += " AND s.id_sucursal=" + idsucursal;
                    }
                }

                if (idproducto != 0) {
                    query += " AND p.id_producto=" + idproducto;
                }

                query += " order by s.descripcion, a.id_almacen, f.id_familiaproducto, cl.id_claseproducto, l.id_lineaproducto, c.id_categoriaproducto, p.id_producto;";

                System.out.println("query: " + query);

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_factual", timestamp);
                parametros.put("P_QUERY", query);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");

                JasperReport reporte;
                if (tipo.equals("RBVALORIZADO")) {
                    reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/reporte/R03_Inventario.jasper");
                } else {
                    reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/reporte/R04_InventarioPrecio.jasper");
                }
                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                if (fileType.equals("PDF")) {
                    byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline;filename=" + "Reporte_Inventario" + ".pdf");
                    response.getOutputStream().write(pdfBytes);
                    response.flushBuffer();
                }

                if (fileType.equals("EXCEL")) {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-disposition", "attachment; filename=" + "Reporte_Inventario" + ".xls");
                    ServletOutputStream out = response.getOutputStream();
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                    exporter.exportReport();
                    out.flush();
                }

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_Reporte_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_Reporte_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (opcion.equals("MOVINV")) {//MOVIMIENTO DE INVENTARIO
            response.setContentType("text/html;charset=UTF-8");
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            String fdesde = request.getParameter("fdesde");
            String fhasta = request.getParameter("fhasta");
            String tipomovimiento = request.getParameter("tipomovimiento");
            Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
            Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
            Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
            String fileType = request.getParameter("fileType");
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                cn = db.getConnection();

                String query = "select p.codigo_interno,p.descripcion producto,a.descripcion almacen,s.descripcion sucursal,CASE WHEN mm.tipomovimiento='E' THEN 'EGRESO' WHEN mm.tipomovimiento='I' THEN 'INGRESO' END tipomov,\n"
                        + "mm.descripcion motivomov,mi.observacion,mi.fecha,dmi.cantidad,pa.stock_actual \n"
                        + "from gcbusiness.detalle_movimientoinventario dmi \n"
                        + "left join gcbusiness.producto p on p.id_producto=dmi.id_producto \n"
                        + "left join gcbusiness.movimientoinventario mi on mi.id_movimientoinventario=dmi.id_movimientoinventario \n"
                        + "left join gcbusiness.almacen a on a.id_almacen=mi.id_almacen \n"
                        + "left join gcbusiness.motivomovimiento mm on mm.id_motivomovimiento=mi.id_motivomovimiento \n"
                        + "left join gcbusiness.almacenproductolote pa on pa.id_producto=dmi.id_producto and pa.id_almacen=mi.id_almacen \n"
                        + "left join gcbusiness.sucursal s on s.id_sucursal=a.id_sucursal "
                        + "WHERE date(mi.fecha) between to_date('" + fdesde + "','dd/mm/yyyy') AND to_date('" + fhasta
                        + "','dd/mm/yyyy') AND mi.estado = 'A' ";

                if (!tipomovimiento.equals("T")) {
                    query += "AND mm.tipomovimiento='" + tipomovimiento + "' ";
                }

                if (idsucursal != 0) {
                    if (idalmacen != 0) {
                        query += "AND s.id_sucursal=" + idsucursal + " AND pa.id_almacen=" + idalmacen;
                    } else {
                        query += "AND s.id_sucursal=" + idsucursal;
                    }
                }

                if (idproducto != 0) {
                    query += " AND p.id_producto=" + idproducto;
                }

                query += " order by codigo_interno,mm.tipomovimiento,mi.fecha,mm.descripcion desc;";

                System.out.println("query: " + query);

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_finicio", sourceFormat.parse(fdesde));
                parametros.put("P_ffin", sourceFormat.parse(fhasta));
                parametros.put("P_QUERY", query);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/reporte/R05_MovimientoInventario.jasper");
                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                if (fileType.equals("PDF")) {
                    byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline;filename=" + "Reporte_Movimientos_Inventario" + ".pdf");
                    response.getOutputStream().write(pdfBytes);
                    response.flushBuffer();
                }

                if (fileType.equals("EXCEL")) {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-disposition", "attachment; filename=" + "Reporte_Movimientos_Inventario" + ".xls");
                    ServletOutputStream out = response.getOutputStream();
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                    exporter.exportReport();
                    out.flush();
                }

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_Reporte_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_Reporte_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } else if (opcion.equals("MOVINVPROD")) {//MOVIMIENTO DE INVENTARIO
            response.setContentType("text/html;charset=UTF-8");
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            String fdesde = request.getParameter("fdesde");
            String fhasta = request.getParameter("fhasta");
            Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
            Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
            Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
            String fileType = request.getParameter("fileType");
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                cn = db.getConnection();

                String query = "select p.codigo_interno,p.descripcion producto,a.descripcion almacen,s.descripcion sucursal,CASE WHEN mm.tipomovimiento='E' THEN 'EGRESO' WHEN mm.tipomovimiento='I' THEN 'INGRESO' END tipomov,\n"
                        + "mm.descripcion motivomov,mi.observacion,mi.fecha,dmi.cantidad,pa.stock_actual, dmi.stock_saldo \n"
                        + "from gcbusiness.detalle_movimientoinventario dmi \n"
                        + "left join gcbusiness.producto p on p.id_producto=dmi.id_producto \n"
                        + "left join gcbusiness.movimientoinventario mi on mi.id_movimientoinventario=dmi.id_movimientoinventario \n"
                        + "left join gcbusiness.almacen a on a.id_almacen=mi.id_almacen \n"
                        + "left join gcbusiness.motivomovimiento mm on mm.id_motivomovimiento=mi.id_motivomovimiento \n"
                        + "left join gcbusiness.almacenproductolote pa on pa.id_producto=dmi.id_producto and pa.id_almacen=mi.id_almacen \n"
                        + "left join gcbusiness.sucursal s on s.id_sucursal=a.id_sucursal "
                        + "WHERE date(mi.fecha) between to_date('" + fdesde + "','dd/mm/yyyy') AND to_date('" + fhasta
                        + "','dd/mm/yyyy') AND mi.estado = 'A' ";

                if (idsucursal != 0) {
                    if (idalmacen != 0) {
                        query += "AND s.id_sucursal=" + idsucursal + " AND pa.id_almacen=" + idalmacen;
                    } else {
                        query += "AND s.id_sucursal=" + idsucursal;
                    }
                }

                if (idproducto != 0) {
                    query += " AND p.id_producto=" + idproducto;
                }

                query += " order by p.codigo_interno, mi.fecha desc, dmi.id_detalle_movimientoinventario desc;";

                System.out.println("query: " + query);

                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("P_finicio", sourceFormat.parse(fdesde));
                parametros.put("P_ffin", sourceFormat.parse(fhasta));
                parametros.put("P_QUERY", query);
                parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "PE"));
                parametros.put("P_logo", "/factele/" + rucEmpresa + "/sunat/logo/logo.png");

                JasperReport reporte;
                reporte = (JasperReport) JRLoader.loadObjectFromFile("/factele/" + rucEmpresa + "/jasper/reporte/R05_MovimientoInventarioPorProducto.jasper");
                JasperPrint jasperPrint;
                jasperPrint = JasperFillManager.fillReport(reporte, parametros, cn);

                if (fileType.equals("PDF")) {
                    byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline;filename=" + "Reporte_Movimientos_Inventario_Producto" + ".pdf");
                    response.getOutputStream().write(pdfBytes);
                    response.flushBuffer();
                }

                if (fileType.equals("EXCEL")) {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-disposition", "attachment; filename=" + "Reporte_Movimientos_Inventario_Producto" + ".xls");
                    ServletOutputStream out = response.getOutputStream();
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                    exporter.exportReport();
                    out.flush();
                }

                cn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("GCBusiness_Reporte_Servlet - Error: " + ex.toString());
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger(GCBusiness_Reporte_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
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
