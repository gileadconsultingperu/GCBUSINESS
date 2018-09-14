/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Seguridad
    Nombre              : DaoUsuarioImpl.java
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase que accede a los datos de la tabla usuario
*/
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanUsuario;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoUsuarioImpl implements DaoAccion{

    @Override
    public String accionCrear(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BeanUsuario accionObtener(Integer id) {
        BeanUsuario usuario = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.usuario\n"
                        + "    WHERE id_usuario = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    usuario = new BeanUsuario();
                    usuario.setIdusuario(rs.getInt(1));
                    usuario.setUsuario(rs.getString(2));
                    usuario.setPassword(rs.getString(3));
                    usuario.setNombres(rs.getString(4));
                    usuario.setApellidos(rs.getString(5));
                    usuario.setEstado(rs.getString(6));     
                    usuario.setFechaInsercion(rs.getTimestamp(7));
                    usuario.setUsuarioInsercion(rs.getString(8));
                    usuario.setTerminalInsercion(rs.getString(9));
                    usuario.setIpInsercion(rs.getString(10));
                    usuario.setFechaModificacion(rs.getTimestamp(11));
                    usuario.setUsuarioModificacion(rs.getString(12));
                    usuario.setTerminalModificacion(rs.getString(13));
                    usuario.setIpModificacion(rs.getString(14));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                usuario = null;
            }
        }

        return usuario;
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
    
    public Integer autentica(String usuario, String password) {
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        Integer idusuario = null;

        if (cn != null) {
            try {
                String qry = "SELECT id_usuario\n"
                        + "	FROM gcbusiness.usuario\n"
                        + "    WHERE usuario = ? AND password = MD5(?)";

                st = cn.prepareStatement(qry);
                st.setString(1, usuario);
                st.setString(2, password + usuario);

                rs = st.executeQuery();

                while (rs.next()) {
                    idusuario = rs.getInt(1);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                idusuario = null;
            }
        }

        return idusuario;
    }

}
