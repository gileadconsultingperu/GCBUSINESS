package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanTarifaProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoTarifaProductoImpl {
    
    public List<BeanTarifaProducto> listarTarifaProducto(Integer idproducto){
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String qry = "";
        List<BeanTarifaProducto> tarifas = null;    
        if (cn != null) {
            try {
                if(idproducto>0){
                    qry = "SELECT t.id_tarifa, t.id_producto, t.valor, t.estado, s.descripcion\n" +
                                "FROM gcbusiness.tarifaproducto t INNER JOIN gcbusiness.tarifa s using (id_tarifa)\n" +
                                "WHERE t.id_producto = ? ORDER BY t.id_tarifa";
                }else{
                    qry = "SELECT id_tarifa, 0 as id_producto, 0 as valor, estado, descripcion\n" +
                                "FROM gcbusiness.tarifa\n" +
                                "ORDER BY id_tarifa";
                }
                
                st = cn.prepareStatement(qry);
                if(idproducto>0){
                    st.setInt(1, idproducto);
                }

                rs = st.executeQuery();
                
                tarifas = new LinkedList<BeanTarifaProducto>();
                while(rs.next()){
                    BeanTarifaProducto ta = new BeanTarifaProducto();
                    ta.setIdtarifa(rs.getInt(1));
                    ta.setIdproducto(rs.getInt(2));
                    ta.setValor(rs.getDouble(3));
                    ta.setEstado(rs.getString(4));
                    ta.setDescripciontarifa(rs.getString(5));
                    tarifas.add(ta);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
            finally{
                try {
                    cn.close();
                }catch(SQLException e2){
                    Logger.getLogger(DaoTarifaProductoImpl.class.getName()).log(Level.SEVERE, null, e2);
                    System.err.println(e2.getMessage());
                }
            }
        }

        return tarifas;
    }
}
