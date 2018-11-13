package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dto.DTOCompra;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoCompraImpl {

    public List<DTOCompra> accionListarDTOCompra() {
        DTOCompra compra = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOCompra> listCompra = null;

        if (cn != null) {
            try {
                String qry = "SELECT v.id_compra, v.id_proveedor, c.nombre, c.numero_documento, v.id_tipocomprobante, i.codigo_sunat as codigocomprobante, i.descripcion descripcioncomprobante, "
                        + "i.abreviatura as abreviaturacomprobante, v.id_sucursal, s.descripcion as descripcionsucursal, v.id_almacen, a.descripcion as descripcionalmacen, v.id_vendedor, e.descripcion as descripcionvendedor, "
                        + "v.id_serie, r.serie, v.correlativo_serie, v.fecha_emision, v.flag_negociable, v.fecha_vencimiento, v.id_moneda, m.codigo_sunat as codigomoneda, m.descripcion as descripcionmoneda, m.simbolo, "
                        + "v.id_formapago, v.id_estatuspago, v.tipo_descuentoglobal, v.monto_descuentoglobal, v.pcto_descuentoglobal, v.flag_gravada, v.total_gravada, v.total_inafecta, v.total_exonerada, v.total_gratuita, "
                        + "v.total_impuesto, v.total_igv, v.total_isc, v.total_otrotributo, v.total_valorcompra, v.total_preciocompra, v.total_descuento, v.total_otrocargo, v.total_anticipo, v.total_compra, v.importe_pago, "
                        + "v.monto_efectivo, v.monto_otro, v.referencia_otro, v.cambio_pago, v.estado, v.motivo_anulacion FROM gcbusiness.compra v, gcbusiness.proveedor c, gcbusiness.tipocomprobante i, gcbusiness.sucursal, "
                        + "gcbusiness.almacen a, gcbusiness.vendedor e, gcbusiness.serie r, gcbusiness.moneda m "
                        + "WHERE v.id_proveedor = c.id_proveedor AND v.id_tipocomprobante = i.id_tipocomprobante AND v.id_sucursal = s.id_sucursal AND v.id_almacen = a.id_almacen AND v.id_vendedor = e.id_vendedor "
                        + "AND v.id_serie = r.id_serie AND v.id_moneda = m.id_moneda "
                        + "ORDER BY p.id_compra";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listCompra = new LinkedList<DTOCompra>();

                while (rs.next()) {
                    compra = new DTOCompra();
                    compra.setIdcompra(rs.getInt("id_compra"));
                    compra.setIdproveedor(rs.getInt("id_proveedor"));
                    compra.setNombreproveedor(rs.getString("nombre"));
                    compra.setNumerodocumentoproveedor(rs.getString("numero_documento"));
                    compra.setIdtipocomprobante(rs.getInt("id_tipocomprobante"));
                    compra.setCodigoSunatcomprobante(rs.getString("codigocomprobante"));
                    compra.setDescripcioncomprobante(rs.getString("descripcioncomprobante"));
                    compra.setAbreviaturacomprobante(rs.getString("abreviaturacomprobante"));
                    compra.setIdsucursal(rs.getInt("id_sucursal"));
                    compra.setDescripcionsucursal(rs.getString("descripcionsucursal"));
                    compra.setIdalmacen(rs.getInt("id_almacen"));
                    compra.setDescripcionalmacen(rs.getString("descripcionalmacen"));
                    compra.setSerie(rs.getString("serie"));
                    compra.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    compra.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    compra.setFlag_negociable(rs.getString("flag_negociable"));
                    compra.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                    compra.setIdmoneda(rs.getInt("id_moneda"));
                    compra.setCodigoSunatMoneda(rs.getString("codigomoneda"));
                    compra.setDescripcionMoneda(rs.getString("descripcionmoneda"));
                    compra.setSimboloMoneda(rs.getString("simbolo"));
                    compra.setFormapago(rs.getString("id_formapago"));
                    compra.setEstadopago(rs.getString("id_estatuspago"));
                    compra.setTipo_descuentoglobal(rs.getString("tipo_descuentoglobal"));
                    compra.setMonto_descuentoglobal(rs.getDouble("monto_descuentoglobal"));
                    compra.setPcto_descuentoglobal(rs.getDouble("pcto_descuentoglobal"));
                    compra.setFlag_gravada(rs.getString("flag_gravada"));
                    compra.setTotal_gravada(rs.getDouble("total_gravada"));
                    compra.setTotal_inafecta(rs.getDouble("total_inafecta"));
                    compra.setTotal_exonerada(rs.getDouble("total_exonerada"));
                    compra.setTotal_gratuita(rs.getDouble("total_gratuita"));
                    compra.setTotal_impuesto(rs.getDouble("total_impuesto"));
                    compra.setTotal_igv(rs.getDouble("total_igv"));
                    compra.setTotal_isc(rs.getDouble("total_isc"));
                    compra.setTotal_otrotributo(rs.getDouble("total_otrotributo"));
                    compra.setTotal_valorcompra(rs.getDouble("total_valorcompra"));
                    compra.setTotal_preciocompra(rs.getDouble("total_preciocompra"));
                    compra.setTotal_descuento(rs.getDouble("total_descuento"));
                    compra.setTotal_otrocargo(rs.getDouble("total_otrocargo"));
                    compra.setTotal_anticipo(rs.getDouble("total_anticipo"));
                    compra.setTotal_compra(rs.getDouble("total_compra"));
                    compra.setImporte_pago(rs.getDouble("importe_pago"));
                    compra.setMonto_efectivo(rs.getDouble("monto_efectivo"));
                    compra.setMonto_otro(rs.getDouble("monto_otro"));
                    compra.setReferencia_otro(rs.getString("referencia_otro"));
                    compra.setCambio_pago(rs.getDouble("cambio_pago"));
                    compra.setEstado(rs.getString("estado"));
                    compra.setMotivo_anulacion(rs.getString("motivo_anulacion"));
                    listCompra.add(compra);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                compra = null;
            }
        }

        return listCompra;
    }

    public List<DTOCompra> accionListarDTOCompraParaAnular(String numeroComprobante, String tipoComprobante, String fecha_desde, String fecha_hasta) {
        DTOCompra compra = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOCompra> listCompras = null;

        if (cn != null) {
            try {
                String qry = "SELECT c.id_compra, c.fecha_emision, tc.codigo_sunat codigocomprobante, tc.abreviatura tipocomprobante, c.serie_comprobante, c.correlativo_serie, p.numero_documento, p.nombre proveedor, "
                        + "m.codigo_sunat, c.total_compra, c.estado "
                        + "FROM gcbusiness.compra c "
                        + "LEFT JOIN gcbusiness.tipocomprobante tc ON tc.id_tipocomprobante = c.id_tipocomprobante "
                        + "LEFT JOIN gcbusiness.proveedor p ON p.id_proveedor = c.id_proveedor "
                        + "LEFT JOIN gcbusiness.tipodocumento td ON td.id_tipodocumento = p.id_tipodocumento "
                        + "LEFT JOIN gcbusiness.moneda m ON m.id_moneda = c.id_moneda "
                        + "WHERE date(c.fecha_emision) BETWEEN to_date('" + fecha_desde + "','dd/mm/yyyy') AND to_date('" + fecha_hasta + "','dd/mm/yyyy')\n";

                if (!numeroComprobante.equals("")) {
                    qry += " AND CONCAT(c.serie_comprobante,'-', c.correlativo_serie)  = '" + numeroComprobante.toUpperCase() + "'\n";
                }

                if (!tipoComprobante.equals("0")) {
                    qry += " AND tc.codigo_sunat = '" + tipoComprobante + "'\n";
                }

                System.out.println("qry: " + qry);
                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listCompras = new LinkedList<DTOCompra>();

                while (rs.next()) {
                    compra = new DTOCompra();
                    compra.setIdcompra(rs.getInt("id_compra"));
                    compra.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    compra.setAbreviaturacomprobante(rs.getString("tipocomprobante"));
                    compra.setCodigoSunatcomprobante(rs.getString("codigocomprobante"));
                    compra.setSerie(rs.getString("serie_comprobante"));
                    compra.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    compra.setNumerodocumentoproveedor(rs.getString("numero_documento"));
                    compra.setNombreproveedor(rs.getString("proveedor"));
                    compra.setCodigoSunatMoneda(rs.getString("codigo_sunat"));
                    compra.setTotal_compra(rs.getDouble("total_compra"));
                    compra.setEstado(rs.getString("estado"));
                    listCompras.add(compra);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                compra = null;
            }
        }

        return listCompras;
    }

    public DTOCompra accionObtener(Integer id) {
        DTOCompra compra = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT v.id_compra, v.id_proveedor, c.nombre, c.numero_documento, v.id_tipocomprobante, i.codigo_sunat as codigocomprobante, i.descripcion descripcioncomprobante, "
                        + "i.abreviatura as abreviaturacomprobante, v.id_sucursal, s.descripcion as descripcionsucursal, v.id_almacen, a.descripcion as descripcionalmacen, v.id_vendedor, e.descripcion as descripcionvendedor, "
                        + "v.id_serie, r.serie, v.correlativo_serie, v.fecha_emision, v.flag_negociable, v.fecha_vencimiento, v.id_moneda, m.codigo_sunat as codigomoneda, m.descripcion as descripcionmoneda, m.simbolo, "
                        + "v.id_formapago, v.id_estatuspago, v.tipo_descuentoglobal, v.monto_descuentoglobal, v.pcto_descuentoglobal, v.flag_gravada, v.total_gravada, v.total_inafecta, v.total_exonerada, v.total_gratuita, "
                        + "v.total_impuesto, v.total_igv, v.total_isc, v.total_otrotributo, v.total_valorcompra, v.total_preciocompra, v.total_descuento, v.total_otrocargo, v.total_anticipo, v.total_compra, v.importe_pago, "
                        + "v.monto_efectivo, v.monto_otro, v.referencia_otro, v.cambio_pago, v.estado, v.motivo_anulacion FROM gcbusiness.compra v, gcbusiness.proveedor c, gcbusiness.tipocomprobante i, gcbusiness.sucursal, "
                        + "gcbusiness.almacen a, gcbusiness.vendedor e, gcbusiness.serie r, gcbusiness.moneda m "
                        + "WHERE v.id_proveedor = c.id_proveedor AND v.id_tipocomprobante = i.id_tipocomprobante AND v.id_sucursal = s.id_sucursal AND v.id_almacen = a.id_almacen AND v.id_vendedor = e.id_vendedor "
                        + "AND v.id_serie = r.id_serie AND v.id_moneda = m.id_moneda AND v.id_compra = ?;";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    compra = new DTOCompra();
                    compra.setIdcompra(rs.getInt("id_compra"));
                    compra.setIdproveedor(rs.getInt("id_proveedor"));
                    compra.setNombreproveedor(rs.getString("nombre"));
                    compra.setNumerodocumentoproveedor(rs.getString("numero_documento"));
                    compra.setIdtipocomprobante(rs.getInt("id_tipocomprobante"));
                    compra.setCodigoSunatcomprobante(rs.getString("codigocomprobante"));
                    compra.setDescripcioncomprobante(rs.getString("descripcioncomprobante"));
                    compra.setAbreviaturacomprobante(rs.getString("abreviaturacomprobante"));
                    compra.setIdsucursal(rs.getInt("id_sucursal"));
                    compra.setDescripcionsucursal(rs.getString("descripcionsucursal"));
                    compra.setIdalmacen(rs.getInt("id_almacen"));
                    compra.setDescripcionalmacen(rs.getString("descripcionalmacen"));
                    compra.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    compra.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    compra.setFlag_negociable(rs.getString("flag_negociable"));
                    compra.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                    compra.setIdmoneda(rs.getInt("id_moneda"));
                    compra.setCodigoSunatMoneda(rs.getString("codigomoneda"));
                    compra.setDescripcionMoneda(rs.getString("descripcionmoneda"));
                    compra.setSimboloMoneda(rs.getString("simbolo"));
                    compra.setFormapago(rs.getString("id_formapago"));
                    compra.setEstadopago(rs.getString("id_estatuspago"));
                    compra.setTipo_descuentoglobal(rs.getString("tipo_descuentoglobal"));
                    compra.setMonto_descuentoglobal(rs.getDouble("monto_descuentoglobal"));
                    compra.setPcto_descuentoglobal(rs.getDouble("pcto_descuentoglobal"));
                    compra.setFlag_gravada(rs.getString("flag_gravada"));
                    compra.setTotal_gravada(rs.getDouble("total_gravada"));
                    compra.setTotal_inafecta(rs.getDouble("total_inafecta"));
                    compra.setTotal_exonerada(rs.getDouble("total_exonerada"));
                    compra.setTotal_gratuita(rs.getDouble("total_gratuita"));
                    compra.setTotal_impuesto(rs.getDouble("total_impuesto"));
                    compra.setTotal_igv(rs.getDouble("total_igv"));
                    compra.setTotal_isc(rs.getDouble("total_isc"));
                    compra.setTotal_otrotributo(rs.getDouble("total_otrotributo"));
                    compra.setTotal_valorcompra(rs.getDouble("total_valorcompra"));
                    compra.setTotal_preciocompra(rs.getDouble("total_preciocompra"));
                    compra.setTotal_descuento(rs.getDouble("total_descuento"));
                    compra.setTotal_otrocargo(rs.getDouble("total_otrocargo"));
                    compra.setTotal_anticipo(rs.getDouble("total_anticipo"));
                    compra.setTotal_compra(rs.getDouble("total_compra"));
                    compra.setImporte_pago(rs.getDouble("importe_pago"));
                    compra.setMonto_efectivo(rs.getDouble("monto_efectivo"));
                    compra.setMonto_otro(rs.getDouble("monto_otro"));
                    compra.setReferencia_otro(rs.getString("referencia_otro"));
                    compra.setCambio_pago(rs.getDouble("cambio_pago"));
                    compra.setEstado(rs.getString("estado"));
                    compra.setMotivo_anulacion(rs.getString("motivo_anulacion"));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                compra = null;
            }
        }

        return compra;
    }

    public String accionEliminar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.compra SET estado = 'A' WHERE id_compra = " + id;

                st = cn.createStatement();

                int n = st.executeUpdate(qry);

                if (n <= 0) {
                    msg = "0 filas afectadas";
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                msg = e1.getMessage();
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                    msg = e2.getMessage();
                }
            }
        }

        return msg;
    }
}
