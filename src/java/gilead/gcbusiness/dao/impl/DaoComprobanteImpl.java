/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dto.DTOComprobante;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Luis
 */
public class DaoComprobanteImpl {

    public List<DTOComprobante> accionListarDTOComprobanteParaNotas(String numeroComprobante, String tipoComprobante, String fecha_desde, String fecha_hasta) {
        DTOComprobante comprobante = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOComprobante> listComprobante = null;

        if (cn != null) {
            try {
                String qry = "SELECT v.id_venta, v.fecha_emision, v.id_tipocomprobante, tc.codigo_sunat codigocomprobante, tc.abreviatura tipocomprobante, v.id_serie, s.serie, v.correlativo_serie, c.numero_documento, c.nombre cliente, m.codigo_sunat, v.total_venta "
                        + "FROM gcbusiness.venta v "
                        + "LEFT JOIN gcbusiness.tipocomprobante tc ON tc.id_tipocomprobante = v.id_tipocomprobante "
                        + "LEFT JOIN gcbusiness.serie s ON s.id_serie = v.id_serie "
                        + "LEFT JOIN gcbusiness.cliente c ON c.id_cliente = v.id_cliente "
                        + "LEFT JOIN gcbusiness.tipodocumento td ON td.id_tipodocumento = c.id_tipodocumento "
                        + "LEFT JOIN gcbusiness.moneda m ON m.id_moneda = v.id_moneda "
                        + "WHERE v.id_tipocomprobante IN (1, 2, 7) AND v.estado = 'E' ";

                if (!numeroComprobante.equals("")) {
                    String[] arrayNroComprobante = null;
                    qry += "AND CONCAT(s.serie,'-', v.correlativo_serie)  = '" + numeroComprobante.toUpperCase() + "' ";
                }
                if (!tipoComprobante.equals("0")) {
                    qry += "AND tc.codigo_sunat = '" + tipoComprobante + "' ";
                }
                qry += "AND date(v.fecha_emision) BETWEEN '" + fecha_desde + "' AND '" + fecha_hasta + "' "
                        + "ORDER BY v.fecha_emision DESC";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listComprobante = new LinkedList<DTOComprobante>();

                while (rs.next()) {
                    comprobante = new DTOComprobante();
                    comprobante.setIdventa(rs.getInt("id_venta"));
                    comprobante.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    comprobante.setIdtipocomprobante(rs.getInt("id_tipocomprobante"));
                    comprobante.setCodigoSunatcomprobante(rs.getString("codigocomprobante"));
                    comprobante.setAbreviaturacomprobante(rs.getString("tipocomprobante"));
                    comprobante.setIdserie(rs.getInt("id_serie"));
                    comprobante.setSerie(rs.getString("serie"));
                    comprobante.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    comprobante.setNumerodocumentocliente(rs.getString("numero_documento"));
                    comprobante.setNombrecliente(rs.getString("cliente"));
                    comprobante.setCodigoSunatMoneda(rs.getString("codigo_sunat"));
                    comprobante.setTotal_venta(rs.getDouble("total_venta"));
                    listComprobante.add(comprobante);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                comprobante = null;
            }
        }

        return listComprobante;
    }

    public List<DTOComprobante> accionListarDTOComprobanteParaAnular(String numeroComprobante, String tipoComprobante, String fecha_desde, String fecha_hasta) {
        DTOComprobante comprobante = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOComprobante> listComprobante = null;

        if (cn != null) {
            try {
                String qry;
                String sqlVenta = "SELECT v.id_venta, v.fecha_emision, tc.codigo_sunat codigocomprobante, tc.abreviatura tipocomprobante, s.serie, v.correlativo_serie, c.numero_documento, c.nombre cliente, m.codigo_sunat, v.total_venta, v.estado "
                        + "FROM gcbusiness.venta v "
                        + "LEFT JOIN gcbusiness.tipocomprobante tc ON tc.id_tipocomprobante = v.id_tipocomprobante "
                        + "LEFT JOIN gcbusiness.serie s ON s.id_serie = v.id_serie "
                        + "LEFT JOIN gcbusiness.cliente c ON c.id_cliente = v.id_cliente "
                        + "LEFT JOIN gcbusiness.tipodocumento td ON td.id_tipodocumento = c.id_tipodocumento "
                        + "LEFT JOIN gcbusiness.moneda m ON m.id_moneda = v.id_moneda "
                        + "WHERE v.estado = 'E'";

                String sqlNota = "SELECT v.id_nota, v.fecha_emision, tc.codigo_sunat codigocomprobante, tc.abreviatura tipocomprobante, s.serie, v.correlativo_serie, c.numero_documento, c.nombre cliente, m.codigo_sunat, v.total_venta, v. estado "
                        + "FROM gcbusiness.nota v "
                        + "LEFT JOIN gcbusiness.tipocomprobante tc ON tc.id_tipocomprobante = v.id_tipocomprobante "
                        + "LEFT JOIN gcbusiness.serie s ON s.id_serie = v.id_serie "
                        + "LEFT JOIN gcbusiness.cliente c ON c.id_cliente = v.id_cliente "
                        + "LEFT JOIN gcbusiness.tipodocumento td ON td.id_tipodocumento = c.id_tipodocumento "
                        + "LEFT JOIN gcbusiness.moneda m ON m.id_moneda = v.id_moneda "
                        + "WHERE v.estado = 'E'";

                if (!numeroComprobante.equals("")) {
                    String[] arrayNroComprobante = null;
                    sqlVenta += " AND CONCAT(s.serie,'-', v.correlativo_serie)  = '" + numeroComprobante.toUpperCase() + "'\n";
                    sqlNota += " AND CONCAT(s.serie,'-', v.correlativo_serie)  = '" + numeroComprobante.toUpperCase() + "'";
                }
                System.out.println("tipoComprobante: " + tipoComprobante);
                if (!tipoComprobante.equals("0")) {
                    sqlVenta += " AND tc.codigo_sunat = '" + tipoComprobante + "'\n";
                    sqlNota += " AND tc.codigo_sunat = '" + tipoComprobante + "'";
                }

                sqlVenta += " AND date(v.fecha_emision) BETWEEN '" + fecha_desde + "' AND '" + fecha_hasta + "'\n";
                sqlNota += " AND date(v.fecha_emision) BETWEEN '" + fecha_desde + "' AND '" + fecha_hasta + "'";

                qry = sqlVenta + "union all\n" + sqlNota;
                System.out.println("qry: " + qry);
                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listComprobante = new LinkedList<DTOComprobante>();

                while (rs.next()) {
                    comprobante = new DTOComprobante();
                    comprobante.setIdventa(rs.getInt("id_venta"));
                    comprobante.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    comprobante.setAbreviaturacomprobante(rs.getString("tipocomprobante"));
                    comprobante.setCodigoSunatcomprobante(rs.getString("codigocomprobante"));
                    comprobante.setSerie(rs.getString("serie"));
                    comprobante.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    comprobante.setNumerodocumentocliente(rs.getString("numero_documento"));
                    comprobante.setNombrecliente(rs.getString("cliente"));
                    comprobante.setCodigoSunatMoneda(rs.getString("codigo_sunat"));
                    comprobante.setTotal_venta(rs.getDouble("total_venta"));
                    comprobante.setEstado(rs.getString("estado"));
                    listComprobante.add(comprobante);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                comprobante = null;
            }
        }

        return listComprobante;
    }

    public List<DTOComprobante> accionListarDTOComprobantes(String numeroComprobante, String tipoComprobante, String fecha_desde, String fecha_hasta) {
        DTOComprobante comprobante = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOComprobante> listComprobante = null;

        if (cn != null) {
            try {
                String qry;
                String sqlVenta = "SELECT v.id_venta, v.fecha_emision, tc.codigo_sunat codigocomprobante, tc.abreviatura tipocomprobante, s.serie, v.correlativo_serie, c.numero_documento, c.nombre cliente, m.codigo_sunat, v.total_venta, v.estado "
                        + "FROM gcbusiness.venta v "
                        + "LEFT JOIN gcbusiness.tipocomprobante tc ON tc.id_tipocomprobante = v.id_tipocomprobante "
                        + "LEFT JOIN gcbusiness.serie s ON s.id_serie = v.id_serie "
                        + "LEFT JOIN gcbusiness.cliente c ON c.id_cliente = v.id_cliente "
                        + "LEFT JOIN gcbusiness.tipodocumento td ON td.id_tipodocumento = c.id_tipodocumento "
                        + "LEFT JOIN gcbusiness.moneda m ON m.id_moneda = v.id_moneda "
                        + "WHERE date(v.fecha_emision) BETWEEN '" + fecha_desde + "' AND '" + fecha_hasta + "'";

                String sqlNota = "SELECT v.id_nota, v.fecha_emision, tc.codigo_sunat codigocomprobante, tc.abreviatura tipocomprobante, s.serie, v.correlativo_serie, c.numero_documento, c.nombre cliente, m.codigo_sunat, v.total_venta, v. estado "
                        + "FROM gcbusiness.nota v "
                        + "LEFT JOIN gcbusiness.tipocomprobante tc ON tc.id_tipocomprobante = v.id_tipocomprobante "
                        + "LEFT JOIN gcbusiness.serie s ON s.id_serie = v.id_serie "
                        + "LEFT JOIN gcbusiness.cliente c ON c.id_cliente = v.id_cliente "
                        + "LEFT JOIN gcbusiness.tipodocumento td ON td.id_tipodocumento = c.id_tipodocumento "
                        + "LEFT JOIN gcbusiness.moneda m ON m.id_moneda = v.id_moneda "
                        + "WHERE date(v.fecha_emision) BETWEEN '" + fecha_desde + "' AND '" + fecha_hasta + "'";

                if (!numeroComprobante.equals("")) {
                    String[] arrayNroComprobante = null;
                    sqlVenta += " AND CONCAT(s.serie,'-', v.correlativo_serie)  = '" + numeroComprobante.toUpperCase() + "'\n";
                    sqlNota += " AND CONCAT(s.serie,'-', v.correlativo_serie)  = '" + numeroComprobante.toUpperCase() + "'";
                }
                System.out.println("tipoComprobante: " + tipoComprobante);
                if (!tipoComprobante.equals("0")) {
                    sqlVenta += " AND tc.codigo_sunat = '" + tipoComprobante + "'\n";
                    sqlNota += " AND tc.codigo_sunat = '" + tipoComprobante + "'";
                }

                qry = sqlVenta + "union all\n" + sqlNota;
                System.out.println("qry: " + qry);
                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listComprobante = new LinkedList<DTOComprobante>();

                while (rs.next()) {
                    comprobante = new DTOComprobante();
                    comprobante.setIdventa(rs.getInt("id_venta"));
                    comprobante.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    comprobante.setAbreviaturacomprobante(rs.getString("tipocomprobante"));
                    comprobante.setCodigoSunatcomprobante(rs.getString("codigocomprobante"));
                    comprobante.setSerie(rs.getString("serie"));
                    comprobante.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    comprobante.setNumerodocumentocliente(rs.getString("numero_documento"));
                    comprobante.setNombrecliente(rs.getString("cliente"));
                    comprobante.setCodigoSunatMoneda(rs.getString("codigo_sunat"));
                    comprobante.setTotal_venta(rs.getDouble("total_venta"));
                    comprobante.setEstado(rs.getString("estado"));
                    listComprobante.add(comprobante);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                comprobante = null;
            }
        }

        return listComprobante;
    }

    public Integer accionContarDocumentoRelacionado(Integer idtipocomprobante, Integer idserie, Integer correlativoserie) {
        Integer cantidaddocumento = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT count(*) cantidaddocumento "
                        + "FROM gcbusiness.nota "
                        + "WHERE id_tipocomprobante_modifica = " + idtipocomprobante + " "
                        + "AND id_serie_modifica = " + idserie + " "
                        + "AND correlativo_serie_modifica = " + correlativoserie + " "
                        + "AND estado = 'E'";

                System.out.println("qry: " + qry);
                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                while (rs.next()) {
                    cantidaddocumento = rs.getInt("cantidaddocumento");
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                cantidaddocumento = null;
            }
        }

        return cantidaddocumento;
    }

    public List<DTOComprobante> accionObtenerDocumentoRelacionado(Integer idtipocomprobante, Integer idserie, Integer correlativoserie) {
        DTOComprobante comprobante = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOComprobante> listComprobante = null;

        if (cn != null) {
            try {
                String qry = "SELECT tc.abreviatura, s.serie, n.correlativo_serie "
                        + "FROM gcbusiness.nota n "
                        + "LEFT JOIN gcbusiness.tipocomprobante tc ON tc.id_tipocomprobante = n.id_tipocomprobante "
                        + "LEFT JOIN gcbusiness.serie s ON s.id_serie = n.id_serie "
                        + "WHERE n.id_tipocomprobante_modifica = " + idtipocomprobante + " "
                        + "AND n.id_serie_modifica = " + idserie + " "
                        + "AND n.correlativo_serie_modifica = " + correlativoserie + " "
                        + "AND n.estado = 'E'";

                System.out.println("qry: " + qry);
                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listComprobante = new LinkedList<DTOComprobante>();

                while (rs.next()) {
                    comprobante = new DTOComprobante();
                    comprobante.setAbreviaturacomprobante(rs.getString("abreviatura"));;
                    comprobante.setSerie(rs.getString("serie"));
                    comprobante.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    listComprobante.add(comprobante);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                comprobante = null;
            }
        }

        return listComprobante;
    }
}
