package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dto.DTOCuentaCobrar;
import gilead.gcbusiness.model.BeanMovimientoCuentaCobrar;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoCuentaCobrarImpl {
    
    public List<DTOCuentaCobrar> listarCuentaCobrar(Integer idcliente, String estado){
        DTOCuentaCobrar dtoCuentaCobrar = null;
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<DTOCuentaCobrar> listaCuentaCobrar = null;    
        if (cn != null) {
            try {
                String qry = "SELECT v.id_venta, v.fecha_emision, v.id_serie, s.serie, v.correlativo_serie, v.total_venta, c.saldo, c.id_cuentacobrar " +
                            "FROM gcbusiness.venta v, gcbusiness.cuentacobrar c, gcbusiness.serie s "+
                            "WHERE v.id_venta = c.id_comprobante AND v.id_serie = s.id_serie AND v.id_cliente = ? AND c.estado = ? ORDER BY v.fecha_emision ASC, c.saldo DESC";
                
                st = cn.prepareStatement(qry);
                st.setInt(1, idcliente);
                st.setString(2, estado);
                rs = st.executeQuery();
                
                listaCuentaCobrar = new LinkedList<DTOCuentaCobrar>();
                while(rs.next()){
                    dtoCuentaCobrar = new DTOCuentaCobrar();
                    dtoCuentaCobrar.setIdventa(rs.getInt("id_venta"));
                    dtoCuentaCobrar.setFechaemision(rs.getTimestamp("fecha_emision"));
                    dtoCuentaCobrar.setIdserie(rs.getInt("id_serie"));
                    dtoCuentaCobrar.setSerie(rs.getString("serie"));
                    dtoCuentaCobrar.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    dtoCuentaCobrar.setTotal_venta(rs.getDouble("total_venta"));
                    dtoCuentaCobrar.setSaldo(rs.getDouble("saldo"));
                    dtoCuentaCobrar.setIdcuentacobrar(rs.getInt("id_cuentacobrar"));
                    listaCuentaCobrar.add(dtoCuentaCobrar);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                dtoCuentaCobrar =null;
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

        return listaCuentaCobrar;
    }
    
    public List<BeanMovimientoCuentaCobrar> listarMovimientosCuentaCobrar(Integer idcuentacobrar){
        BeanMovimientoCuentaCobrar beanMovimientoCuentaCobrar = null;
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<BeanMovimientoCuentaCobrar> listaMovimientosCuentaCobrar = null;    
        if (cn != null) {
            try {
                String qry = "SELECT id_movimiento_cuentacobrar, fecha, monto, saldo_anterior, saldo_actual " +
                            "FROM gcbusiness.movimiento_cuentacobrar "+
                            "WHERE id_cuentacobrar = ? ORDER BY id_movimiento_cuentacobrar ASC";
                
                st = cn.prepareStatement(qry);
                st.setInt(1, idcuentacobrar);
                rs = st.executeQuery();
                
                listaMovimientosCuentaCobrar = new LinkedList<BeanMovimientoCuentaCobrar>();
                while(rs.next()){
                    beanMovimientoCuentaCobrar = new BeanMovimientoCuentaCobrar();
                    beanMovimientoCuentaCobrar.setIdmovimientocuentacobrar(rs.getInt("id_movimiento_cuentacobrar"));
                    beanMovimientoCuentaCobrar.setFecha(rs.getTimestamp("fecha"));
                    beanMovimientoCuentaCobrar.setMonto(rs.getDouble("monto"));
                    beanMovimientoCuentaCobrar.setSaldoanterior(rs.getDouble("saldo_anterior"));
                    beanMovimientoCuentaCobrar.setSaldoactual(rs.getDouble("saldo_actual"));
                    listaMovimientosCuentaCobrar.add(beanMovimientoCuentaCobrar);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                beanMovimientoCuentaCobrar =null;
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

        return listaMovimientosCuentaCobrar;
    }
    
    public String registrarMovimiento(BeanMovimientoCuentaCobrar movimiento) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.movimiento_cuentacobrar (id_cuentacobrar,fecha,monto,saldo_anterior,saldo_actual,documento_referencia,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + movimiento.getIdcuentacobrar()+ ",'" + movimiento.getFecha() + "',"+movimiento.getMonto()+", "+ movimiento.getSaldoanterior()+", "+ movimiento.getSaldoactual()+", '"+ movimiento.getDocumentoreferencia()
                        +"', '"+ movimiento.getEstado()+"', '"+ movimiento.getFechaInsercion()+ "', '" + movimiento.getUsuarioInsercion()+"', '"+movimiento.getTerminalInsercion()+"', '"+movimiento.getIpInsercion()+"')";
                
                st = cn.createStatement();

                int n = st.executeUpdate(qry);
                
                if (n <= 0) {
                    msg = "0 filas afectadas";
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                msg = e1.getMessage();
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                    msg = e2.getMessage();
                }
            }
        }

        return msg;
    }
}
