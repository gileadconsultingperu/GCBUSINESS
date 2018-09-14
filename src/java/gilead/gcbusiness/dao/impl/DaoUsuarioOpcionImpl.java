/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanOpcionMenu;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUsuarioOpcionImpl {
    
    public List<BeanOpcionMenu> listarOpcionUsuario(Integer idusuario){
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<BeanOpcionMenu> opciones = null;    
        if (cn != null) {
            try {
                String qry = "SELECT t.id_opcionmenu, s.descripcion\n" +
                            "FROM gcbusiness.usuarioopcion t INNER JOIN gcbusiness.opcionmenu s using (id_opcionmenu)\n" +
                            "INNER JOIN gcbusiness.usuario u using (id_usuario)\n" +
                            "WHERE t.estado = 'A' AND s.estado='A' AND u.id_usuario = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, idusuario);

                rs = st.executeQuery();
                
                opciones = new LinkedList<BeanOpcionMenu>();
                while(rs.next()){
                    BeanOpcionMenu op = new BeanOpcionMenu();
                    op.setIdopcionmenu(rs.getInt(1));
                    op.setDescripcion(rs.getString(2));
                    opciones.add(op);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
            finally{
                try {
                    cn.close();
                }catch(SQLException e2){
                    Logger.getLogger(DaoUsuarioOpcionImpl.class.getName()).log(Level.SEVERE, null, e2);
                    System.err.println(e2.getMessage());
                }
            }
        }

        return opciones;
    }
}
