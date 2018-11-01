package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dto.DTOCotizacion;
import gilead.gcbusiness.dto.DTODetalleCotizacion;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DaoCotizacionImpl {

    public List<DTOCotizacion> accionListarDTOCotizacion(Integer idsucursal, Integer idalmacen, String estado, String fecha_desde, String fecha_hasta) {
        DTOCotizacion cotizacion = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOCotizacion> listCotizacion = null;

        if (cn != null) {
            try {
                String qry = "SELECT v.id_cotizacion, v.fecha_emision, m.codigo_sunat, v.total_venta, a.descripcion almacen, CASE v.estado WHEN 'E' THEN 'PENDIENTE' WHEN 'P' THEN 'PROCESADO' WHEN 'A' THEN 'ANULADO' END estado "
                        + "FROM gcbusiness.cotizacion v "
                        + "LEFT JOIN gcbusiness.moneda m ON m.id_moneda = v.id_moneda "
                        + "LEFT JOIN gcbusiness.almacen a ON a.id_almacen = v.id_almacen "
                        + "WHERE date(v.fecha_emision) BETWEEN '" + fecha_desde + "' AND '" + fecha_hasta + "' "
                        + "AND v.id_sucursal = " + idsucursal + " AND v.id_almacen = " + idalmacen + " ";

                if (!estado.equals("0")) {
                    qry += "AND v.estado = '" + estado + "' ";
                }
                qry += "ORDER BY v.fecha_emision DESC";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listCotizacion = new LinkedList<DTOCotizacion>();

                while (rs.next()) {
                    cotizacion = new DTOCotizacion();
                    cotizacion.setIdcotizacion(rs.getInt("id_cotizacion"));
                    cotizacion.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    cotizacion.setCodigoSunatMoneda(rs.getString("codigo_sunat"));
                    cotizacion.setTotal_venta(rs.getDouble("total_venta"));
                    cotizacion.setAlmacen(rs.getString("almacen"));
                    cotizacion.setEstado(rs.getString("estado"));
                    listCotizacion.add(cotizacion);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                cotizacion = null;
            }
        }

        return listCotizacion;
    }

    public DTOCotizacion accionObtenerDTOCotizacion(Integer id) {
        DTOCotizacion cotizacion = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT v.id_cotizacion, v.id_sucursal, v.id_almacen, a.descripcion almacen, v.fecha_emision, v.id_moneda, m.codigo_sunat, v.tipo_descuentoglobal, v.monto_descuentoglobal, v.pcto_descuentoglobal, v.total_gravada, "
                        + "v.total_inafecta, v.total_exonerada, v.total_gratuita, v.total_impuesto, v.total_igv, v.total_isc, v.total_otrotributo, v.total_valorventa, v.total_precioventa, "
                        + "v.total_descuento, v.total_otrocargo, v.total_venta, CASE v.estado WHEN 'E' THEN 'PENDIENTE' WHEN 'P' THEN 'PROCESADO' WHEN 'A' THEN 'ANULADO' END estado "
                        + "FROM gcbusiness.cotizacion v "
                        + "LEFT JOIN gcbusiness.moneda m ON m.id_moneda = v.id_moneda "
                        + "LEFT JOIN gcbusiness.almacen a ON a.id_almacen = v.id_almacen";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                while (rs.next()) {
                    cotizacion = new DTOCotizacion();
                    cotizacion.setIdcotizacion(rs.getInt("id_cotizacion"));
                    cotizacion.setIdsucursal(rs.getInt("id_sucursal"));
                    cotizacion.setIdalmacen(rs.getInt("id_almacen"));
                    cotizacion.setAlmacen(rs.getString("almacen"));
                    cotizacion.setFecha_emision(rs.getTimestamp("fecha_emision"));
                    cotizacion.setIdmoneda(rs.getInt("id_moneda"));
                    cotizacion.setCodigoSunatMoneda(rs.getString("codigo_sunat"));
                    cotizacion.setTipo_descuentoglobal(rs.getString("tipo_descuentoglobal"));
                    cotizacion.setMonto_descuentoglobal(rs.getDouble("monto_descuentoglobal"));
                    cotizacion.setPcto_descuentoglobal(rs.getDouble("pcto_descuentoglobal"));
                    cotizacion.setTotal_gravada(rs.getDouble("total_gravada"));
                    cotizacion.setTotal_inafecta(rs.getDouble("total_inafecta"));
                    cotizacion.setTotal_exonerada(rs.getDouble("total_exonerada"));
                    cotizacion.setTotal_gratuita(rs.getDouble("total_gratuita"));
                    cotizacion.setTotal_impuesto(rs.getDouble("total_impuesto"));
                    cotizacion.setTotal_igv(rs.getDouble("total_igv"));
                    cotizacion.setTotal_isc(rs.getDouble("total_isc"));
                    cotizacion.setTotal_otrotributo(rs.getDouble("total_otrotributo"));
                    cotizacion.setTotal_valorventa(rs.getDouble("total_valorventa"));
                    cotizacion.setTotal_precioventa(rs.getDouble("total_precioventa"));
                    cotizacion.setTotal_descuento(rs.getDouble("total_descuento"));
                    cotizacion.setTotal_otrocargo(rs.getDouble("total_otrocargo"));
                    cotizacion.setTotal_venta(rs.getDouble("total_venta"));
                    cotizacion.setEstado(rs.getString("estado"));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                cotizacion = null;
            }
        }

        return cotizacion;
    }

    public List<DTODetalleCotizacion> accionListarDTODetalleCotizacion(Integer id) {
        DTODetalleCotizacion detalleCotizacion = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTODetalleCotizacion> listDetalleCotizacion = null;

        if (cn != null) {
            try {
                String qry = "SELECT d.id_detalle_cotizacion, d.id_cotizacion, d.id_producto, p.codigo_interno, p.descripcion producto, u.abreviatura unidadmedida, d.id_lote, d.cantidad, "
                        + "p.afecto_igv, d.tipo_igv, d.pcto_igv, d.valor_unitario_venta, d.precio_unitario_venta, d.valor_venta, d.precio_venta, d.monto_igv, d.tipo_isc, d.monto_isc, "
                        + "d.flag_bonificacion, d.tipo_descuento, d.monto_descuento, d.pcto_descuento, d.valor_venta_descuento, d.precio_venta_descuento, d.monto_igv_descuento, "
                        + "round(d.valor_venta_descuento/d.cantidad,2) valor_unitario_descuento, round(d.precio_venta_descuento/d.cantidad,2) precio_unitario_descuento "
                        + "FROM gcbusiness.detalle_cotizacion d "
                        + "LEFT JOIN gcbusiness.producto p on p.id_producto = d.id_producto "
                        + "LEFT JOIN gcbusiness.unidadmedida u on u.id_unidadmedida = p.id_unidadmedida "
                        + "WHERE d.id_cotizacion = ? "
                        + "ORDER BY d.id_detalle_cotizacion";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                listDetalleCotizacion = new LinkedList<DTODetalleCotizacion>();

                while (rs.next()) {
                    detalleCotizacion = new DTODetalleCotizacion();
                    detalleCotizacion.setIddetallecotizacion(rs.getInt("id_detalle_cotizacion"));
                    detalleCotizacion.setIdcotizacion(rs.getInt("id_cotizacion"));
                    detalleCotizacion.setIdproducto(rs.getInt("id_producto"));
                    detalleCotizacion.setIdlote(rs.getInt("id_lote"));
                    detalleCotizacion.setCodigoproducto(rs.getString("codigo_interno"));
                    detalleCotizacion.setNombreproducto(rs.getString("producto"));
                    detalleCotizacion.setUnidadmedida(rs.getString("unidadmedida"));
                    detalleCotizacion.setCantidad(rs.getDouble("cantidad"));
                    detalleCotizacion.setTipoigv(rs.getString("tipo_igv"));
                    detalleCotizacion.setAfectoigv(rs.getString("afecto_igv"));
                    detalleCotizacion.setPctoigv(rs.getDouble("pcto_igv"));
                    detalleCotizacion.setValorunitarioventa(rs.getDouble("valor_unitario_venta"));
                    detalleCotizacion.setPreciounitarioventa(rs.getDouble("precio_unitario_venta"));
                    detalleCotizacion.setValorventa(rs.getDouble("valor_venta"));
                    detalleCotizacion.setPrecioventa(rs.getDouble("precio_venta"));
                    detalleCotizacion.setMontoigv(rs.getDouble("monto_igv"));
                    detalleCotizacion.setTipoisc(rs.getString("tipo_isc"));
                    detalleCotizacion.setMontoisc(rs.getDouble("monto_isc"));
                    detalleCotizacion.setFlagbonificacion(rs.getString("flag_bonificacion"));
                    detalleCotizacion.setTipodescuento(rs.getString("tipo_descuento"));
                    detalleCotizacion.setMontodescuento(rs.getDouble("monto_descuento"));
                    detalleCotizacion.setPctodescuento(rs.getDouble("pcto_descuento"));
                    detalleCotizacion.setValorunitariodescuento(rs.getDouble("valor_unitario_descuento"));
                    detalleCotizacion.setPreciounitariodescuento(rs.getDouble("precio_unitario_descuento"));
                    detalleCotizacion.setValorventadescuento(rs.getDouble("valor_venta_descuento"));
                    detalleCotizacion.setPrecioventadescuento(rs.getDouble("precio_venta_descuento"));
                    detalleCotizacion.setMontoigvdescuento(rs.getDouble("monto_igv_descuento"));
                    listDetalleCotizacion.add(detalleCotizacion);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                detalleCotizacion = null;
            }
        }

        return listDetalleCotizacion;
    }
}
