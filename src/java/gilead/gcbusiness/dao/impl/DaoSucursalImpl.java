/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Seguridad
    Nombre              : DaoSucursalImpl.java
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase que accede a los datos de la tabla sucursal
*/
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanSucursal;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoSucursalImpl implements DaoAccion{

    @Override
    public String accionCrear(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BeanSucursal accionObtener(Integer id) {
        BeanSucursal sucursal = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.sucursal\n"
                        + "    WHERE id_sucursal = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    sucursal = new BeanSucursal();
                    sucursal.setIdsucursal(rs.getInt(1));
                    sucursal.setIdempresa(rs.getInt(2));
                    sucursal.setDescripcion(rs.getString(3));
                    sucursal.setDireccion(rs.getString(4));
                    sucursal.setTelefono(rs.getString(5));
                    sucursal.setEstado(rs.getString(6));     
                    sucursal.setFechaInsercion(rs.getTimestamp(7));
                    sucursal.setUsuarioInsercion(rs.getString(8));
                    sucursal.setTerminalInsercion(rs.getString(9));
                    sucursal.setIpInsercion(rs.getString(10));
                    sucursal.setFechaModificacion(rs.getTimestamp(11));
                    sucursal.setUsuarioModificacion(rs.getString(12));
                    sucursal.setTerminalModificacion(rs.getString(13));
                    sucursal.setIpModificacion(rs.getString(14));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                sucursal = null;
            }
        }

        return sucursal;
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
    
}
