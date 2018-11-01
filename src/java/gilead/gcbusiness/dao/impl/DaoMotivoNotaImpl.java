/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanMotivoNota;
import gilead.gcbusiness.model.BeanSerie;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis
 */
public class DaoMotivoNotaImpl {

    public List<BeanMotivoNota> listarMotivoNotaTipoComprobante(String codigocomprobante) {
        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String qry = "";
        List<BeanMotivoNota> lstMotivoNotas = null;
        if (cn != null) {
            try {
                qry = "SELECT s.id_motivonota, s.id_tipocomprobante, s.codigo_sunat, s.descripcion, s.estado\n"
                        + "FROM gcbusiness.motivonota s INNER JOIN gcbusiness.tipocomprobante c using (id_tipocomprobante)\n"
                        + "WHERE c.codigo_sunat = ? AND s.estado = 'A' ORDER BY s.id_motivonota";

                st = cn.prepareStatement(qry);

                st.setString(1, codigocomprobante);

                rs = st.executeQuery();

                lstMotivoNotas = new LinkedList<BeanMotivoNota>();
                while (rs.next()) {
                    BeanMotivoNota motivoNota = new BeanMotivoNota();
                    motivoNota.setIdmotivonota(rs.getInt("id_motivonota"));
                    motivoNota.setIdtipocomprobante(rs.getInt("id_tipocomprobante"));
                    motivoNota.setCodigoSunat(rs.getString("codigo_sunat"));
                    motivoNota.setDescripcion(rs.getString("descripcion"));
                    motivoNota.setEstado(rs.getString("estado"));
                    lstMotivoNotas.add(motivoNota);
                }
                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    Logger.getLogger(DaoMotivoNotaImpl.class.getName()).log(Level.SEVERE, null, e2);
                    System.err.println(e2.getMessage());
                }
            }
        }

        return lstMotivoNotas;
    }
}
