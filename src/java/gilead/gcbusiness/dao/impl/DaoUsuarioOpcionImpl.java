package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dto.DTOUsuarioOpcion;
import gilead.gcbusiness.model.BeanOpcionMenu;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUsuarioOpcionImpl {
    
    public ArrayList listarOpcionUsuario(Integer idusuario){
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList opciones = null;    
        if (cn != null) {
            try {
                String qry = "SELECT t.id_opcionmenu\n" +
                            "FROM gcbusiness.usuarioopcion t INNER JOIN gcbusiness.opcionmenu s using (id_opcionmenu)\n" +
                            "INNER JOIN gcbusiness.usuario u using (id_usuario)\n" +
                            "WHERE t.estado = 'A' AND s.estado='A' AND u.id_usuario = ? ORDER BY t.id_opcionmenu";

                st = cn.prepareStatement(qry);
                st.setInt(1, idusuario);

                rs = st.executeQuery();
                
                opciones = new ArrayList();
                while(rs.next()){
                    opciones.add(rs.getInt(1));
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
    
    public List<DTOUsuarioOpcion> listarAccesos(Integer idusuario){
        DTOUsuarioOpcion opcion = null;
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<DTOUsuarioOpcion> opciones = null;    
        if (cn != null) {
            try {
                String qry = "SELECT t.id_usuario, t.id_opcionmenu, t.estado as estadoUsuarioOpcion, s.descripcion, u.usuario, u.nombres, u.apellidos \n" +
                            "FROM gcbusiness.usuarioopcion t INNER JOIN gcbusiness.opcionmenu s using (id_opcionmenu) "+
                            "INNER JOIN gcbusiness.usuario u using (id_usuario)" +
                            "WHERE t.id_usuario = ? ORDER BY t.id_opcionmenu";

                st = cn.prepareStatement(qry);
                st.setInt(1, idusuario);

                rs = st.executeQuery();
                
                opciones = new LinkedList<DTOUsuarioOpcion>();
                while(rs.next()){
                    opcion = new DTOUsuarioOpcion();
                    opcion.setIdusuario(rs.getInt("id_usuario"));
                    opcion.setIdopcionmenu(rs.getInt("id_opcionmenu"));
                    opcion.setEstadoUsuarioOpcion(rs.getString("estadoUsuarioOpcion"));
                    opcion.setDescripcionOpcion(rs.getString("descripcion"));
                    opcion.setUsuario(rs.getString("usuario"));
                    opcion.setNombres(rs.getString("nombres"));
                    opcion.setApellidos(rs.getString("apellidos"));
                    opciones.add(opcion);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                opcion =null;
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
    
    public String actualizarAccesos(int idUser, String accesos) {
        String sql1=null, sql2=null, sqlEjecucion=null;
        DaoOpcionMenuImpl daoOpcionMenuImpl = new DaoOpcionMenuImpl();
        String error = "Accesos actualizados";
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;
        if (cn != null) {
            try {                 
                if(accesos.equals("")){
                    sql1 = "UPDATE gcbusiness.usuarioopcion SET estado = 'I' WHERE id_usuario="+idUser;
                }else{
                    sql1 = "UPDATE gcbusiness.usuarioopcion SET estado = 'A' WHERE id_usuario="+idUser+" AND id_opcionmenu IN ("+accesos+")";
                    sql2 = "UPDATE gcbusiness.usuarioopcion SET estado = 'I' WHERE id_usuario="+idUser+" AND id_opcionmenu NOT IN ("+accesos+")";
                }    
                sqlEjecucion = sql1;
                st = cn.createStatement();
 
                int n = st.executeUpdate(sql1);
                if(n<=0){
                    error = "0";
                    sql2 = null;
                }

                if(sql2!=null){
                    List<BeanOpcionMenu> list = (List<BeanOpcionMenu>) daoOpcionMenuImpl.accionListar();
                    String [] listaAccesos = accesos.split(",");
                    if(list.size()!=listaAccesos.length){
                        sqlEjecucion = sql2;
                        n = st.executeUpdate(sql2);
                        if(n<=0){
                            error = "0";
                        }
                    }
                }
            }
            catch(SQLException e1){
                Logger.getLogger(DaoUsuarioOpcionImpl.class.getName()).log(Level.SEVERE, null, e1);
                error = e1.getMessage().replace("\n", "").concat(". "+sqlEjecucion); 
            }
            finally{
                try {
                    if(cn!=null)cn.close();
                    if(st!=null)st.close(); 
                } catch(SQLException e2){
                    Logger.getLogger(DaoUsuarioOpcionImpl.class.getName()).log(Level.SEVERE, null, e2);
                    error = e2.getMessage(); 
                }
            }
        }    
        return error;
    }
}
