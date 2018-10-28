package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.model.BeanOpcionMenu;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DaoOpcionMenuImpl {
    
    public List<BeanOpcionMenu> accionListar() {
        BeanOpcionMenu opcionmenu = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanOpcionMenu> listOpcionMenu = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.opcionmenu ORDER BY id_opcionmenu";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listOpcionMenu = new LinkedList<BeanOpcionMenu>();

                while (rs.next()) {
                    opcionmenu = new BeanOpcionMenu();
                    opcionmenu.setIdopcionmenu(rs.getInt("id_opcionmenu"));
                    opcionmenu.setDescripcion(rs.getString("descripcion"));       
                    opcionmenu.setEstado(rs.getString("estado"));
                    opcionmenu.setFechaInsercion(rs.getTimestamp("fecha_insercion"));
                    opcionmenu.setUsuarioInsercion(rs.getString("usuario_insercion"));
                    opcionmenu.setTerminalInsercion(rs.getString("terminal_insercion"));
                    opcionmenu.setIpInsercion(rs.getString("ip_insercion"));
                    opcionmenu.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
                    opcionmenu.setUsuarioModificacion(rs.getString("usuario_modificacion"));
                    opcionmenu.setTerminalModificacion(rs.getString("terminal_modificacion"));
                    opcionmenu.setIpModificacion(rs.getString("ip_modificacion"));

                    listOpcionMenu.add(opcionmenu);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                opcionmenu = null;
            }
        }

        return listOpcionMenu;
    }    
}
