package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanLote;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoLoteImpl {
    
    public List<BeanLote> listarLotePorProducto(Integer idProducto){
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String qry = "";
        List<BeanLote> lstSeries = null;    
        if (cn != null) {
            try {
                qry = "SELECT id_lote, id_producto, numero_lote, fecha_vencimiento, estado\n" +
                            "FROM gcbusiness.lote\n" +
                            "WHERE id_producto = ? ORDER BY id_lote";
          
                
                st = cn.prepareStatement(qry);
                
                st.setInt(1, idProducto);

                rs = st.executeQuery();
                
                lstSeries = new LinkedList<BeanLote>();
                while(rs.next()){
                    BeanLote lote = new BeanLote();
                    lote.setIdlote(rs.getInt("id_lote"));
                    lote.setIdproducto(rs.getInt("id_producto"));
                    lote.setNumerolote(rs.getString("numero_lote"));
                    lote.setFechavencimiento(rs.getDate("fecha_vencimiento"));
                    lote.setEstado(rs.getString("estado"));                   
                    lstSeries.add(lote);
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

        return lstSeries;
    }
    
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanLote lote = (BeanLote) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.lote (id_lote, id_producto, numero_lote, fecha_vencimiento, estado, fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + lote.getIdlote()+ "," + lote.getIdproducto()+ ",'"  + lote.getNumerolote()+ "','" + lote.getFechavencimiento() + "','"+ lote.getEstado().toUpperCase() + "','"+lote.getFechaInsercion()
                        + "', '" + lote.getUsuarioInsercion()+"', '"+lote.getTerminalInsercion()+"', '"+lote.getIpInsercion()+"')";
                
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
