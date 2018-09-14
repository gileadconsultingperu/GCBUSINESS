/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Seguridad
    Nombre              : DaoAlmacenImpl.java
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase que accede a los datos de la tabla almacen
*/
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanAlmacen;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoAlmacenImpl implements DaoAccion{

    @Override
    public String accionCrear(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BeanAlmacen accionObtener(Integer id) {
        BeanAlmacen almacen = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.almacen\n"
                        + "    WHERE id_almacen = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    almacen = new BeanAlmacen();
                    almacen.setIdalmacen(rs.getInt(1));
                    almacen.setIdsucursal(rs.getInt(2));
                    almacen.setDescripcion(rs.getString(3));
                    almacen.setEstado(rs.getString(4));     
                    almacen.setFlagtransito(rs.getString(5));
                    almacen.setFechaInsercion(rs.getTimestamp(6));
                    almacen.setUsuarioInsercion(rs.getString(7));
                    almacen.setTerminalInsercion(rs.getString(8));
                    almacen.setIpInsercion(rs.getString(9));
                    almacen.setFechaModificacion(rs.getTimestamp(10));
                    almacen.setUsuarioModificacion(rs.getString(11));
                    almacen.setTerminalModificacion(rs.getString(12));
                    almacen.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                almacen = null;
            }
        }

        return almacen;
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
