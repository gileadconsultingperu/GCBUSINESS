package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanLote;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoLoteImpl implements DaoAccion {

    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanLote lote = (BeanLote) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.lote (id_producto,numero_lote,fecha_vencimiento,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + lote.getIdproducto() + ",'" + lote.getNumerolote().toUpperCase() + "','" + lote.getFechavencimiento() + "','" + lote.getEstado().toUpperCase() + "','" + lote.getFechaInsercion()
                        + "', '" + lote.getUsuarioInsercion() + "', '" + lote.getTerminalInsercion() + "', '" + lote.getIpInsercion() + "')";

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

    public List<BeanLote> listarLotePorProducto(Integer idproducto) {
        BeanLote lote = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanLote> listLote = null;

        if (cn != null) {
            try {
                String qry = "SELECT id_lote, id_producto, numero_lote, fecha_vencimiento\n"
                        + "FROM gcbusiness.lote\n"
                        + "WHERE id_producto = ?\n"
                        + "AND estado = 'A'\n"
                        + "ORDER BY id_lote";

                st = cn.prepareStatement(qry);
                st.setInt(1, idproducto);

                rs = st.executeQuery();

                listLote = new LinkedList<BeanLote>();

                while (rs.next()) {
                    lote = new BeanLote();
                    lote.setIdlote(rs.getInt("id_lote"));
                    lote.setIdproducto(rs.getInt("id_producto"));
                    lote.setNumerolote(rs.getString("numero_lote"));
                    lote.setFechavencimiento(rs.getDate("fecha_vencimiento"));

                    listLote.add(lote);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                lote = null;
            }
        }

        return listLote;
    }

    @Override
    public Object accionObtener(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String accionActualizar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String accionEliminar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<?> accionListar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String accionActivar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
