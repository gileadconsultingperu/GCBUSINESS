/**
 * Compañia            : Gilead Consulting S.A.C.
 * Sistema             : GC-Business
 * Módulo              : Seguridad
 * Nombre              : DaoMotivoMovimientoImpl.java
 * Versión             : 1.0
 * Fecha Creación      : 21-08-2018
 * Autor Creación      : Pablo Jimenez Aguado
 * Uso                 : Clase que accede a los datos de la tabla almacen
 */
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.dto.DTOMovimientoAlmacen;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DaoMovimientoAlmacenImpl implements DaoAccion {

    public List<DTOMovimientoAlmacen> listarMovimientoAlmacen() {
        DTOMovimientoAlmacen movimientoAlmacen = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOMovimientoAlmacen> listMovimientoAlmacen = null;

        if (cn != null) {
            try {
                String qry = "SELECT ma.id_movimientoalmacen, ma.fecha, o.descripcion almacenorigen, d.descripcion almacendestino, ma.observacion"
                        + " FROM gcbusiness.movimientoalmacen ma"
                        + " INNER JOIN gcbusiness.almacen o ON ma.id_almacen_origen = o.id_almacen"
                        + " INNER JOIN gcbusiness.almacen d ON ma.id_almacen_destino = d.id_almacen";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listMovimientoAlmacen = new LinkedList<DTOMovimientoAlmacen>();

                while (rs.next()) {
                    movimientoAlmacen = new DTOMovimientoAlmacen();
                    movimientoAlmacen.setIdmovimientoalmacen(rs.getInt("id_movimientoalmacen"));
                    movimientoAlmacen.setFecha(rs.getDate("fecha"));
                    movimientoAlmacen.setAlmacenorigen(rs.getString("almacenorigen"));
                    movimientoAlmacen.setAlmacendestino(rs.getString("almacendestino"));
                    movimientoAlmacen.setObservacion(rs.getString("observacion"));

                    listMovimientoAlmacen.add(movimientoAlmacen);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                movimientoAlmacen = null;
            }
        }

        return listMovimientoAlmacen;
    }

    @Override
    public String accionCrear(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
