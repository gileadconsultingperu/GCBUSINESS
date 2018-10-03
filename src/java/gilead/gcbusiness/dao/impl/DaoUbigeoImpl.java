package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanUbigeo;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DaoUbigeoImpl {
    
    public List<BeanUbigeo> accionListarDepartamentos() {
        BeanUbigeo ubidepartamento = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanUbigeo> listUbigeo = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                                + "	FROM gcbusiness.ubidepartamento ORDER BY codigo_ubidepartamento";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listUbigeo = new LinkedList<BeanUbigeo>();

                while (rs.next()) {
                    ubidepartamento = new BeanUbigeo();
                    ubidepartamento.setCodigo_ubidepartamento(rs.getString(1));
                    ubidepartamento.setCodigo_ubiprovincia("00");
                    ubidepartamento.setCodigo_ubidistrito("00");
                    ubidepartamento.setDescripcionUbigeo(rs.getString(2));   
                    ubidepartamento.setEstado(rs.getString(3));
                    ubidepartamento.setFechaInsercion(rs.getTimestamp(4));
                    ubidepartamento.setUsuarioInsercion(rs.getString(5));
                    ubidepartamento.setTerminalInsercion(rs.getString(6));
                    ubidepartamento.setIpInsercion(rs.getString(7));
                    ubidepartamento.setFechaModificacion(rs.getTimestamp(8));
                    ubidepartamento.setUsuarioModificacion(rs.getString(9));
                    ubidepartamento.setTerminalModificacion(rs.getString(10));
                    ubidepartamento.setIpModificacion(rs.getString(11));

                    listUbigeo.add(ubidepartamento);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                ubidepartamento = null;
            }
        }

        return listUbigeo;
    }    
    
    public List<BeanUbigeo> accionListarProvincias(String codigo_ubidepartamento) {
        BeanUbigeo ubiprovincia = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanUbigeo> listUbigeo = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                                + "	FROM gcbusiness.ubiprovincia WHERE codigo_ubidepartamento ='"+ codigo_ubidepartamento+"' ORDER BY codigo_ubiprovincia";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listUbigeo = new LinkedList<BeanUbigeo>();

                while (rs.next()) {
                    ubiprovincia = new BeanUbigeo();
                    ubiprovincia.setCodigo_ubiprovincia(rs.getString(1));
                    ubiprovincia.setCodigo_ubidepartamento(rs.getString(2));
                    ubiprovincia.setCodigo_ubidistrito("00");
                    ubiprovincia.setDescripcionUbigeo(rs.getString(3));   
                    ubiprovincia.setEstado(rs.getString(4));
                    ubiprovincia.setFechaInsercion(rs.getTimestamp(5));
                    ubiprovincia.setUsuarioInsercion(rs.getString(6));
                    ubiprovincia.setTerminalInsercion(rs.getString(7));
                    ubiprovincia.setIpInsercion(rs.getString(8));
                    ubiprovincia.setFechaModificacion(rs.getTimestamp(9));
                    ubiprovincia.setUsuarioModificacion(rs.getString(10));
                    ubiprovincia.setTerminalModificacion(rs.getString(11));
                    ubiprovincia.setIpModificacion(rs.getString(12));

                    listUbigeo.add(ubiprovincia);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                ubiprovincia = null;
            }
        }

        return listUbigeo;
    }  
    
    public List<BeanUbigeo> accionListarDistritos(String codigo_ubiprovincia) {
        BeanUbigeo ubidistrito = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanUbigeo> listUbigeo = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                                + "	FROM gcbusiness.ubidistrito WHERE codigo_ubiprovincia ='"+ codigo_ubiprovincia+"' ORDER BY codigo_ubidistrito";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listUbigeo = new LinkedList<BeanUbigeo>();

                while (rs.next()) {
                    ubidistrito = new BeanUbigeo();
                    ubidistrito.setCodigo_ubidistrito(rs.getString(1));
                    ubidistrito.setCodigo_ubiprovincia(rs.getString(2));
                    ubidistrito.setCodigo_ubidepartamento(rs.getString(2).substring(0, 3));
                    System.out.println("Dpto: "+ubidistrito.getCodigo_ubidepartamento());;
                    ubidistrito.setDescripcionUbigeo(rs.getString(3));   
                    ubidistrito.setEstado(rs.getString(4));
                    ubidistrito.setFechaInsercion(rs.getTimestamp(5));
                    ubidistrito.setUsuarioInsercion(rs.getString(6));
                    ubidistrito.setTerminalInsercion(rs.getString(7));
                    ubidistrito.setIpInsercion(rs.getString(8));
                    ubidistrito.setFechaModificacion(rs.getTimestamp(9));
                    ubidistrito.setUsuarioModificacion(rs.getString(10));
                    ubidistrito.setTerminalModificacion(rs.getString(11));
                    ubidistrito.setIpModificacion(rs.getString(12));

                    listUbigeo.add(ubidistrito);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                ubidistrito = null;
            }
        }

        return listUbigeo;
    }  
}
