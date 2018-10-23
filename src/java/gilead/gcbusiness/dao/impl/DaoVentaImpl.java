package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dto.DTOVenta;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoVentaImpl {
    
    public List<DTOVenta> accionListarDTOVenta() {
        DTOVenta venta = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOVenta> listVenta = null;

        if (cn != null) {
            try {
                String qry = "SELECT v.id_venta, v.id_cliente, c.nombre, c.numero_documento, v.id_tipocomprobante, i.codigo_sunat as codigocomprobante, i.descripcion descripcioncomprobante, "
                        + "i.abreviatura as abreviaturacomprobante, v.id_sucursal, s.descripcion as descripcionsucursal, v.id_almacen, a.descripcion as descripcionalmacen, v.id_vendedor, e.descripcion as descripcionvendedor, "
                        + "v.id_serie, r.serie, v.correlativo_serie, v.fecha_emision, v.flag_negociable, v.fecha_vencimiento, v.id_moneda, m.codigo_sunat as codigomoneda, m.descripcion as descripcionmoneda, m.simbolo, "
                        + "v.id_formapago, v.id_estatuspago, v.tipo_descuentoglobal, v.monto_descuentoglobal, v.pcto_descuentoglobal, v.flag_gravada, v.total_gravada, v.total_inafecta, v.total_exonerada, v.total_gratuita, "
                        + "v.total_impuesto, v.total_igv, v.total_isc, v.total_otrotributo, v.total_valorventa, v.total_precioventa, v.total_descuento, v.total_otrocargo, v.total_anticipo, v.total_venta, v.importe_pago, "
                        + "v.monto_efectivo, v.monto_otro, v.referencia_otro, v.cambio_pago, v.estado, v.motivo_anulacion FROM gcbusiness.venta v, gcbusiness.cliente c, gcbusiness.tipocomprobante i, gcbusiness.sucursal, "
                        + "gcbusiness.almacen a, gcbusiness.vendedor e, gcbusiness.serie r, gcbusiness.moneda m "
                        + "WHERE v.id_cliente = c.id_cliente AND v.id_tipocomprobante = i.id_tipocomprobante AND v.id_sucursal = s.id_sucursal AND v.id_almacen = a.id_almacen AND v.id_vendedor = e.id_vendedor "
                        + "AND v.id_serie = r.id_serie AND v.id_moneda = m.id_moneda "
                        + "ORDER BY p.id_venta";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listVenta = new LinkedList<DTOVenta>();

                while (rs.next()) {
                    venta = new DTOVenta();
                    venta.setIdventa(rs.getInt("id_venta"));
                    venta.setIdcliente(rs.getInt("id_cliente"));
                    venta.setNombrecliente(rs.getString("nombre"));
                    venta.setNumerodocumentocliente(rs.getString("numero_documento"));
                    venta.setIdtipocomprobante(rs.getInt("id_tipocomprobante"));
                    venta.setCodigoSunatcomprobante(rs.getString("codigocomprobante"));
                    venta.setDescripcioncomprobante(rs.getString("descripcioncomprobante"));
                    venta.setAbreviaturacomprobante(rs.getString("abreviaturacomprobante"));
                    venta.setIdsucursal(rs.getInt("id_sucursal"));
                    venta.setDescripcionsucursal(rs.getString("descripcionsucursal"));
                    venta.setIdalmacen(rs.getInt("id_almacen"));
                    venta.setDescripcionalmacen(rs.getString("descripcionalmacen"));
                    venta.setIdvendedor(rs.getInt("id_vendedor"));
                    venta.setDescripcionvendedor(rs.getString("descripcionvendedor"));
                    venta.setIdserie(rs.getInt("id_serie"));
                    venta.setSerie(rs.getString("serie"));
                    venta.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    venta.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    venta.setFlag_negociable(rs.getString("flag_negociable"));
                    venta.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                    venta.setIdmoneda(rs.getInt("id_moneda"));
                    venta.setCodigoSunatMoneda(rs.getString("codigomoneda"));
                    venta.setDescripcionMoneda(rs.getString("descripcionmoneda"));
                    venta.setSimboloMoneda(rs.getString("simbolo"));
                    venta.setFormapago(rs.getString("id_formapago"));
                    venta.setEstadopago(rs.getString("id_estatuspago"));
                    venta.setTipo_descuentoglobal(rs.getString("tipo_descuentoglobal"));
                    venta.setMonto_descuentoglobal(rs.getDouble("monto_descuentoglobal"));
                    venta.setPcto_descuentoglobal(rs.getDouble("pcto_descuentoglobal"));
                    venta.setFlag_gravada(rs.getString("flag_gravada"));
                    venta.setTotal_gravada(rs.getDouble("total_gravada"));
                    venta.setTotal_inafecta(rs.getDouble("total_inafecta"));
                    venta.setTotal_exonerada(rs.getDouble("total_exonerada"));
                    venta.setTotal_gratuita(rs.getDouble("total_gratuita"));
                    venta.setTotal_impuesto(rs.getDouble("total_impuesto"));
                    venta.setTotal_igv(rs.getDouble("total_igv"));
                    venta.setTotal_isc(rs.getDouble("total_isc"));
                    venta.setTotal_otrotributo(rs.getDouble("total_otrotributo"));
                    venta.setTotal_valorventa(rs.getDouble("total_valorventa"));
                    venta.setTotal_precioventa(rs.getDouble("total_precioventa"));
                    venta.setTotal_descuento(rs.getDouble("total_descuento"));
                    venta.setTotal_otrocargo(rs.getDouble("total_otrocargo"));
                    venta.setTotal_anticipo(rs.getDouble("total_anticipo"));
                    venta.setTotal_venta(rs.getDouble("total_venta"));
                    venta.setImporte_pago(rs.getDouble("importe_pago"));
                    venta.setMonto_efectivo(rs.getDouble("monto_efectivo"));
                    venta.setMonto_otro(rs.getDouble("monto_otro"));
                    venta.setReferencia_otro(rs.getString("referencia_otro"));
                    venta.setCambio_pago(rs.getDouble("cambio_pago"));
                    venta.setEstado(rs.getString("estado"));
                    venta.setMotivo_anulacion(rs.getString("motivo_anulacion"));            
                    listVenta.add(venta);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                venta = null;
            }
        }

        return listVenta;
    }
    
    public DTOVenta accionObtener(Integer id) {
        DTOVenta venta = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT v.id_venta, v.id_cliente, c.nombre, c.numero_documento, v.id_tipocomprobante, i.codigo_sunat as codigocomprobante, i.descripcion descripcioncomprobante, "
                        + "i.abreviatura as abreviaturacomprobante, v.id_sucursal, s.descripcion as descripcionsucursal, v.id_almacen, a.descripcion as descripcionalmacen, v.id_vendedor, e.descripcion as descripcionvendedor, "
                        + "v.id_serie, r.serie, v.correlativo_serie, v.fecha_emision, v.flag_negociable, v.fecha_vencimiento, v.id_moneda, m.codigo_sunat as codigomoneda, m.descripcion as descripcionmoneda, m.simbolo, "
                        + "v.id_formapago, v.id_estatuspago, v.tipo_descuentoglobal, v.monto_descuentoglobal, v.pcto_descuentoglobal, v.flag_gravada, v.total_gravada, v.total_inafecta, v.total_exonerada, v.total_gratuita, "
                        + "v.total_impuesto, v.total_igv, v.total_isc, v.total_otrotributo, v.total_valorventa, v.total_precioventa, v.total_descuento, v.total_otrocargo, v.total_anticipo, v.total_venta, v.importe_pago, "
                        + "v.monto_efectivo, v.monto_otro, v.referencia_otro, v.cambio_pago, v.estado, v.motivo_anulacion FROM gcbusiness.venta v, gcbusiness.cliente c, gcbusiness.tipocomprobante i, gcbusiness.sucursal, "
                        + "gcbusiness.almacen a, gcbusiness.vendedor e, gcbusiness.serie r, gcbusiness.moneda m "
                        + "WHERE v.id_cliente = c.id_cliente AND v.id_tipocomprobante = i.id_tipocomprobante AND v.id_sucursal = s.id_sucursal AND v.id_almacen = a.id_almacen AND v.id_vendedor = e.id_vendedor "
                        + "AND v.id_serie = r.id_serie AND v.id_moneda = m.id_moneda AND v.id_venta = ?;";
 

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    venta = new DTOVenta();
                    venta.setIdventa(rs.getInt("id_venta"));
                    venta.setIdcliente(rs.getInt("id_cliente"));
                    venta.setNombrecliente(rs.getString("nombre"));
                    venta.setNumerodocumentocliente(rs.getString("numero_documento"));
                    venta.setIdtipocomprobante(rs.getInt("id_tipocomprobante"));
                    venta.setCodigoSunatcomprobante(rs.getString("codigocomprobante"));
                    venta.setDescripcioncomprobante(rs.getString("descripcioncomprobante"));
                    venta.setAbreviaturacomprobante(rs.getString("abreviaturacomprobante"));
                    venta.setIdsucursal(rs.getInt("id_sucursal"));
                    venta.setDescripcionsucursal(rs.getString("descripcionsucursal"));
                    venta.setIdalmacen(rs.getInt("id_almacen"));
                    venta.setDescripcionalmacen(rs.getString("descripcionalmacen"));
                    venta.setIdvendedor(rs.getInt("id_vendedor"));
                    venta.setDescripcionvendedor(rs.getString("descripcionvendedor"));
                    venta.setIdserie(rs.getInt("id_serie"));
                    venta.setSerie(rs.getString("serie"));
                    venta.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    venta.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    venta.setFlag_negociable(rs.getString("flag_negociable"));
                    venta.setFecha_vencimiento(rs.getDate("fecha_vencimiento"));
                    venta.setIdmoneda(rs.getInt("id_moneda"));
                    venta.setCodigoSunatMoneda(rs.getString("codigomoneda"));
                    venta.setDescripcionMoneda(rs.getString("descripcionmoneda"));
                    venta.setSimboloMoneda(rs.getString("simbolo"));
                    venta.setFormapago(rs.getString("id_formapago"));
                    venta.setEstadopago(rs.getString("id_estatuspago"));
                    venta.setTipo_descuentoglobal(rs.getString("tipo_descuentoglobal"));
                    venta.setMonto_descuentoglobal(rs.getDouble("monto_descuentoglobal"));
                    venta.setPcto_descuentoglobal(rs.getDouble("pcto_descuentoglobal"));
                    venta.setFlag_gravada(rs.getString("flag_gravada"));
                    venta.setTotal_gravada(rs.getDouble("total_gravada"));
                    venta.setTotal_inafecta(rs.getDouble("total_inafecta"));
                    venta.setTotal_exonerada(rs.getDouble("total_exonerada"));
                    venta.setTotal_gratuita(rs.getDouble("total_gratuita"));
                    venta.setTotal_impuesto(rs.getDouble("total_impuesto"));
                    venta.setTotal_igv(rs.getDouble("total_igv"));
                    venta.setTotal_isc(rs.getDouble("total_isc"));
                    venta.setTotal_otrotributo(rs.getDouble("total_otrotributo"));
                    venta.setTotal_valorventa(rs.getDouble("total_valorventa"));
                    venta.setTotal_precioventa(rs.getDouble("total_precioventa"));
                    venta.setTotal_descuento(rs.getDouble("total_descuento"));
                    venta.setTotal_otrocargo(rs.getDouble("total_otrocargo"));
                    venta.setTotal_anticipo(rs.getDouble("total_anticipo"));
                    venta.setTotal_venta(rs.getDouble("total_venta"));
                    venta.setImporte_pago(rs.getDouble("importe_pago"));
                    venta.setMonto_efectivo(rs.getDouble("monto_efectivo"));
                    venta.setMonto_otro(rs.getDouble("monto_otro"));
                    venta.setReferencia_otro(rs.getString("referencia_otro"));
                    venta.setCambio_pago(rs.getDouble("cambio_pago"));
                    venta.setEstado(rs.getString("estado"));
                    venta.setMotivo_anulacion(rs.getString("motivo_anulacion"));       
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                venta = null;
            }
        }

        return venta;
    }
    
    public String accionEliminar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.venta SET estado = 'A' WHERE id_venta = " + id;

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
