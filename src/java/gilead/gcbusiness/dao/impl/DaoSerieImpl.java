package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanSerie;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoSerieImpl {

    public List<BeanSerie> listarSerieTipoComprobante(String codigocomprobante) {
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String qry = "";
        List<BeanSerie> lstSeries = null;
        if (cn != null) {
            try {
                qry = "SELECT s.id_serie, s.id_tipocomprobante, s.serie, s.correlativo, s.estado\n"
                        + "FROM gcbusiness.serie s INNER JOIN gcbusiness.tipocomprobante c using (id_tipocomprobante)\n"
                        + "WHERE c.codigo_sunat = ? ORDER BY s.id_serie";

                st = cn.prepareStatement(qry);

                st.setString(1, codigocomprobante);

                rs = st.executeQuery();

                lstSeries = new LinkedList<BeanSerie>();
                while (rs.next()) {
                    BeanSerie serie = new BeanSerie();
                    serie.setIdserie(rs.getInt(1));
                    serie.setIdtipocomprobante(rs.getInt(2));
                    serie.setSerie(rs.getString(3));
                    serie.setCorrelativo(rs.getInt(4));
                    serie.setEstado(rs.getString(5));
                    lstSeries.add(serie);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    Logger.getLogger(DaoTarifaProductoImpl.class.getName()).log(Level.SEVERE, null, e2);
                    System.err.println(e2.getMessage());
                }
            }
        }

        return lstSeries;
    }

    public List<BeanSerie> listarSerieTipoComprobanteAlmacen(String codigocomprobante, Integer idalmacen) {
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String qry = "";
        List<BeanSerie> lstSeries = null;
        if (cn != null) {
            try {
                qry = "SELECT s.id_serie, s.id_tipocomprobante, s.serie, s.correlativo, s.estado"
                        + " FROM gcbusiness.serie s"
                        + " INNER JOIN gcbusiness.tipocomprobante c using (id_tipocomprobante)"
                        + " INNER JOIN gcbusiness.seriealmacen d using (id_serie)"
                        + " WHERE c.codigo_sunat = ? AND d.id_almacen = ? AND d.estado = 'A' ORDER BY s.id_serie";

                st = cn.prepareStatement(qry);

                st.setString(1, codigocomprobante);
                st.setInt(2, idalmacen);

                rs = st.executeQuery();

                lstSeries = new LinkedList<BeanSerie>();
                while (rs.next()) {
                    BeanSerie serie = new BeanSerie();
                    serie.setIdserie(rs.getInt(1));
                    serie.setIdtipocomprobante(rs.getInt(2));
                    serie.setSerie(rs.getString(3));
                    serie.setCorrelativo(rs.getInt(4));
                    serie.setEstado(rs.getString(5));
                    lstSeries.add(serie);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    Logger.getLogger(DaoTarifaProductoImpl.class.getName()).log(Level.SEVERE, null, e2);
                    System.err.println(e2.getMessage());
                }
            }
        }

        return lstSeries;
    }

    public BeanSerie obtenerCorrelativoSerie(Integer idserie) {
        BeanSerie serie = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.serie\n"
                        + "    WHERE id_serie = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, idserie);

                rs = st.executeQuery();

                while (rs.next()) {
                    serie = new BeanSerie();
                    serie.setIdserie(rs.getInt("id_serie"));
                    serie.setIdtipocomprobante(rs.getInt("id_tipocomprobante"));
                    serie.setSerie(rs.getString("serie"));
                    serie.setCorrelativo(rs.getInt("correlativo"));
                    serie.setEstado(rs.getString("estado"));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                serie = null;
            }
        }

        return serie;
    }
}
