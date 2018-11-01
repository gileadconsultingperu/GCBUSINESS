package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dto.DTOCuentaPagar;
import gilead.gcbusiness.model.BeanMovimientoCuentaPagar;
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

public class DaoCuentaPagarImpl {
    
    public List<DTOCuentaPagar> listarCuentaPagar(Integer idproveedor, String estado){
        DTOCuentaPagar dtoCuentaPagar = null;
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<DTOCuentaPagar> listaCuentaPagar = null;    
        if (cn != null) {
            try {
                String qry = "SELECT v.id_compra, v.fecha_emision, v.serie_comprobante, v.correlativo_serie, v.total_compra, c.saldo, c.id_cuentapagar " +
                            "FROM gcbusiness.compra v, gcbusiness.cuentapagar c "+
                            "WHERE v.id_compra = c.id_compra AND v.id_proveedor = ? AND c.estado = ? ORDER BY v.fecha_emision ASC, c.saldo DESC";
                System.out.println("qry: "+qry);
                st = cn.prepareStatement(qry);
                st.setInt(1, idproveedor);
                st.setString(2, estado);
                rs = st.executeQuery();
                
                listaCuentaPagar = new LinkedList<DTOCuentaPagar>();
                while(rs.next()){
                    dtoCuentaPagar = new DTOCuentaPagar();
                    dtoCuentaPagar.setIdcompra(rs.getInt("id_compra"));
                    dtoCuentaPagar.setFechaemision(rs.getTimestamp("fecha_emision"));
                    dtoCuentaPagar.setSerie(rs.getString("serie_comprobante"));
                    dtoCuentaPagar.setCorrelativoserie(rs.getInt("correlativo_serie"));
                    dtoCuentaPagar.setTotal_compra(rs.getDouble("total_compra"));
                    dtoCuentaPagar.setSaldo(rs.getDouble("saldo"));
                    dtoCuentaPagar.setIdcuentapagar(rs.getInt("id_cuentapagar"));
                    listaCuentaPagar.add(dtoCuentaPagar);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                dtoCuentaPagar =null;
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

        return listaCuentaPagar;
    }
    
    public List<BeanMovimientoCuentaPagar> listarMovimientosCuentaPagar(Integer idcuentapagar){
        BeanMovimientoCuentaPagar beanMovimientoCuentaPagar = null;
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<BeanMovimientoCuentaPagar> listaMovimientosCuentaPagar = null;    
        if (cn != null) {
            try {
                String qry = "SELECT id_movimiento_cuentapagar, fecha, monto, saldo_anterior, saldo_actual " +
                            "FROM gcbusiness.movimiento_cuentapagar "+
                            "WHERE id_cuentapagar = ? ORDER BY id_movimiento_cuentapagar ASC";
                
                st = cn.prepareStatement(qry);
                st.setInt(1, idcuentapagar);
                rs = st.executeQuery();
                
                listaMovimientosCuentaPagar = new LinkedList<BeanMovimientoCuentaPagar>();
                while(rs.next()){
                    beanMovimientoCuentaPagar = new BeanMovimientoCuentaPagar();
                    beanMovimientoCuentaPagar.setIdmovimientocuentapagar(rs.getInt("id_movimiento_cuentapagar"));
                    beanMovimientoCuentaPagar.setFecha(rs.getTimestamp("fecha"));
                    beanMovimientoCuentaPagar.setMonto(rs.getDouble("monto"));
                    beanMovimientoCuentaPagar.setSaldoanterior(rs.getDouble("saldo_anterior"));
                    beanMovimientoCuentaPagar.setSaldoactual(rs.getDouble("saldo_actual"));
                    listaMovimientosCuentaPagar.add(beanMovimientoCuentaPagar);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                beanMovimientoCuentaPagar =null;
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

        return listaMovimientosCuentaPagar;
    }
    
    public String registrarMovimiento(BeanMovimientoCuentaPagar movimiento) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.movimiento_cuentapagar (id_cuentapagar,fecha,monto,saldo_anterior,saldo_actual,documento_referencia,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + movimiento.getIdcuentapagar()+ ",'" + movimiento.getFecha() + "',"+movimiento.getMonto()+", "+ movimiento.getSaldoanterior()+", "+ movimiento.getSaldoactual()+", '"+ movimiento.getDocumentoreferencia()
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
