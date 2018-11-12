/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanAlmacen;
import gilead.gcbusiness.model.BeanSucursal;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUsuarioSucursalAlmacenImpl {

    public List<BeanSucursal> listarSucursalUsuario(String usuario, String password) {
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<BeanSucursal> sucursales = null;
        if (cn != null) {
            try {
                String qry = "SELECT DISTINCT t.id_sucursal, s.descripcion\n"
                        + "FROM gcbusiness.usuariosucursalalmacen t INNER JOIN gcbusiness.sucursal s using (id_sucursal)\n"
                        + "INNER JOIN gcbusiness.almacen a using (id_almacen)\n"
                        + "INNER JOIN gcbusiness.usuario u using (id_usuario)\n"
                        + "WHERE t.estado = 'S' AND s.estado='A' AND a.estado='A' AND u.usuario = ? AND u.password = MD5(?)";

                st = cn.prepareStatement(qry);
                st.setString(1, usuario);
                st.setString(2, password + usuario);

                rs = st.executeQuery();

                sucursales = new LinkedList<BeanSucursal>();
                while (rs.next()) {
                    BeanSucursal su = new BeanSucursal();
                    su.setIdsucursal(rs.getInt(1));
                    su.setDescripcion(rs.getString(2));
                    sucursales.add(su);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    Logger.getLogger(DaoUsuarioSucursalAlmacenImpl.class.getName()).log(Level.SEVERE, null, e2);
                    System.err.println(e2.getMessage());
                }
            }
        }

        return sucursales;
    }

    public List<BeanAlmacen> listarAlmacenUsuario(String idSucursal, String usuario, String password) {
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<BeanAlmacen> almacenes = null;
        if (cn != null) {
            try {
                String qry = "SELECT t.id_almacen, a.descripcion\n"
                        + "FROM gcbusiness.usuariosucursalalmacen t INNER JOIN gcbusiness.sucursal s using (id_sucursal)\n"
                        + "INNER JOIN gcbusiness.almacen a using (id_almacen)\n"
                        + "INNER JOIN gcbusiness.usuario u using (id_usuario)\n"
                        + "WHERE t.estado = 'S' AND s.estado='A' AND a.estado='A' AND s.id_sucursal= ? AND u.usuario = ? AND u.password = MD5(?)";

                st = cn.prepareStatement(qry);
                st.setInt(1, Integer.parseInt(idSucursal));
                st.setString(2, usuario);
                st.setString(3, password + usuario);

                rs = st.executeQuery();

                almacenes = new LinkedList<BeanAlmacen>();
                while (rs.next()) {
                    BeanAlmacen al = new BeanAlmacen();
                    al.setIdalmacen(rs.getInt(1));
                    al.setDescripcion(rs.getString(2));
                    almacenes.add(al);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    Logger.getLogger(DaoUsuarioSucursalAlmacenImpl.class.getName()).log(Level.SEVERE, null, e2);
                    System.err.println(e2.getMessage());
                }
            }
        }

        return almacenes;
    }

    public List<BeanAlmacen> listarAlmacenSucursal(String idSucursal) {
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<BeanAlmacen> almacenes = null;
        if (cn != null) {
            try {
                String qry = "SELECT a.id_almacen, a.descripcion\n"
                        + "FROM gcbusiness.sucursal s\n"
                        + "INNER JOIN gcbusiness.almacen a using (id_sucursal)\n"
                        + "WHERE s.estado='A' AND a.estado='A' AND s.id_sucursal= ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, Integer.parseInt(idSucursal));

                rs = st.executeQuery();

                almacenes = new LinkedList<BeanAlmacen>();
                while (rs.next()) {
                    BeanAlmacen al = new BeanAlmacen();
                    al.setIdalmacen(rs.getInt(1));
                    al.setDescripcion(rs.getString(2));
                    almacenes.add(al);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    Logger.getLogger(DaoUsuarioSucursalAlmacenImpl.class.getName()).log(Level.SEVERE, null, e2);
                    System.err.println(e2.getMessage());
                }
            }
        }

        return almacenes;
    }
}
