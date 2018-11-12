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
import gilead.gcbusiness.dto.DTOIngresoSalida;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DaoMovimientoInventarioImpl implements DaoAccion {

    public List<DTOIngresoSalida> listarIngresoSalida(String almacen, String tipomovimiento, String fecha_desde, String fecha_hasta) {
        DTOIngresoSalida ingresoSalida = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOIngresoSalida> listIngresoSalida = null;

        if (cn != null) {
            try {
                String qry = "SELECT mi.id_movimientoinventario,mi.fecha,a.descripcion almacen,CASE mm.tipomovimiento WHEN 'I' THEN 'INGRESO' WHEN 'E' THEN 'SALIDA' END tipomovimiento,mm.descripcion motivomovimiento, mi.observacion\n"
                        + "FROM gcbusiness.movimientoinventario mi\n"
                        + "LEFT JOIN gcbusiness.motivomovimiento mm ON mm.id_motivomovimiento=mi.id_motivomovimiento\n"
                        + "LEFT JOIN gcbusiness.almacen a ON a.id_almacen=mi.id_almacen\n"
                        + "WHERE mm.flag_proceso='N'";

                if (!almacen.equals("0")) {
                    qry += "AND mi.id_almacen = " + almacen + " ";
                }
                if (!tipomovimiento.equals("0")) {
                    qry += "AND mm.tipomovimiento = '" + tipomovimiento + "' ";
                }

                qry += "AND date(mi.fecha) BETWEEN '" + fecha_desde + "' AND '" + fecha_hasta + "' "
                        + "ORDER BY mi.fecha DESC";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listIngresoSalida = new LinkedList<DTOIngresoSalida>();

                while (rs.next()) {
                    ingresoSalida = new DTOIngresoSalida();
                    ingresoSalida.setIdmovimientoinventario(rs.getInt("id_movimientoinventario"));
                    ingresoSalida.setFecha(rs.getDate("fecha"));
                    ingresoSalida.setAlmacen(rs.getString("almacen"));
                    ingresoSalida.setTipomovimiento(rs.getString("tipomovimiento"));
                    ingresoSalida.setMotivomovimiento(rs.getString("motivomovimiento"));
                    ingresoSalida.setObservacion(rs.getString("observacion"));

                    listIngresoSalida.add(ingresoSalida);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                ingresoSalida = null;
            }
        }

        return listIngresoSalida;
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
