package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanAlmacenProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoAlmacenProductoImpl {
    
    public BeanAlmacenProducto obtenerAlmacenProducto(Integer idalmacen, Integer idproducto){
        BeanAlmacenProducto almacenproducto = null;
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        if (cn != null) {
            try {
                String qry = "SELECT t.id_almacen, t.id_producto, t.valor, t.estado, s.descripcion\n" +
                            "FROM gcbusiness.almacenproducto t INNER JOIN gcbusiness.almacen s using (id_almacen)\n" +
                            "WHERE t.id_producto = ? ORDER BY t.id_almacen";

                st = cn.prepareStatement(qry);
                if(idproducto>0){
                    st.setInt(1, idproducto);
                }

                rs = st.executeQuery();
                
                while(rs.next()){
                    almacenproducto = new BeanAlmacenProducto();
                    almacenproducto.setIdalmacen(rs.getInt(1));
                    almacenproducto.setIdproducto(rs.getInt(2));
                    almacenproducto.setStockactual(rs.getDouble(3));
                    almacenproducto.setStockminimo(rs.getDouble(4));
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                almacenproducto = null;
            }
        }

        return almacenproducto;
    }
}
