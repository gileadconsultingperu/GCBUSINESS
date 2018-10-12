package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanAlmacenProductoLote;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoAlmacenProductoLoteImpl {
    
    
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanAlmacenProductoLote almacenproductolote = (BeanAlmacenProductoLote) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.almacenproductolote (id_almacen, id_producto, id_lote, stock_actual,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + almacenproductolote.getIdalmacen() + "," + almacenproductolote.getIdproducto()+ ","  + almacenproductolote.getIdlote() + "," + almacenproductolote.getStockactual() + ",'"+almacenproductolote.getFechaInsercion()
                        + "', '" + almacenproductolote.getUsuarioInsercion()+"', '"+almacenproductolote.getTerminalInsercion()+"', '"+almacenproductolote.getIpInsercion()+"')";
                
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
    
    public BeanAlmacenProductoLote obtenerAlmacenProductoLote(Integer idalmacen, Integer idproducto, Integer idlote){
        BeanAlmacenProductoLote almacenproductolote = null;
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        if (cn != null) {
            try {
                String qry = "SELECT id_almacen, id_producto, id_lote, stock_actual\n" +
                            "FROM gcbusiness.almacenproductolote\n" +
                            "WHERE id_almacen = ? AND id_producto = ? AND id_lote = ? ORDER BY id_almacen, id_producto";

                st = cn.prepareStatement(qry);
                
                st.setInt(1, idalmacen);
                st.setInt(2, idproducto);
                st.setInt(3, idlote);

                rs = st.executeQuery();
                
                while(rs.next()){
                    almacenproductolote = new BeanAlmacenProductoLote();
                    almacenproductolote.setIdalmacen(rs.getInt(1));
                    almacenproductolote.setIdproducto(rs.getInt(2));
                    almacenproductolote.setIdlote(rs.getInt(3));
                    almacenproductolote.setStockactual(rs.getDouble(4));
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                almacenproductolote = null;
            }
        }

        return almacenproductolote;
    }
}
