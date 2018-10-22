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
import gilead.gcbusiness.model.BeanMotivoMovimiento;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DaoMotivoMovimientoImpl implements DaoAccion {

    public List<BeanMotivoMovimiento> listarPorTipoMovimiento(String tipomovimiento) {
        BeanMotivoMovimiento motivoMovimiento = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanMotivoMovimiento> listMotivoMovimiento = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "FROM gcbusiness.motivomovimiento\n"
                        + "WHERE tipomovimiento = ?\n"
                        + "AND flag_proceso = 'N'\n"
                        + "ORDER BY descripcion";

                st = cn.prepareStatement(qry);
                st.setString(1, tipomovimiento);

                rs = st.executeQuery();

                listMotivoMovimiento = new LinkedList<BeanMotivoMovimiento>();

                while (rs.next()) {
                    motivoMovimiento = new BeanMotivoMovimiento();
                    motivoMovimiento.setIdmotivomovimiento(rs.getInt(1));
                    motivoMovimiento.setTipomovimiento(rs.getString(2));
                    motivoMovimiento.setDescripcion(rs.getString(3));
                    motivoMovimiento.setEstado(rs.getString(4));
                    motivoMovimiento.setFlagproceso(rs.getString(5));
                    motivoMovimiento.setFechaInsercion(rs.getTimestamp(6));
                    motivoMovimiento.setUsuarioInsercion(rs.getString(7));
                    motivoMovimiento.setTerminalInsercion(rs.getString(8));
                    motivoMovimiento.setIpInsercion(rs.getString(9));
                    motivoMovimiento.setFechaModificacion(rs.getTimestamp(10));
                    motivoMovimiento.setUsuarioModificacion(rs.getString(11));
                    motivoMovimiento.setTerminalModificacion(rs.getString(12));
                    motivoMovimiento.setIpModificacion(rs.getString(13));

                    listMotivoMovimiento.add(motivoMovimiento);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                motivoMovimiento = null;
            }
        }

        return listMotivoMovimiento;
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
