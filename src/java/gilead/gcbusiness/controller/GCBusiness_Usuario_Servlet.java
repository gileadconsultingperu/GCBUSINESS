package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoUsuarioImpl;
import gilead.gcbusiness.dao.impl.DaoUsuarioOpcionImpl;
import gilead.gcbusiness.dto.DTOUsuarioOpcion;
import gilead.gcbusiness.model.BeanUsuario;
import gilead.gcbusiness.model.BeanUsuarioOpcion;
import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_Usuario_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION USUARIO");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoUsuarioImpl daoUsuarioImpl = new DaoUsuarioImpl();
                List<BeanUsuario> listUsuarios = daoUsuarioImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listUsuarios.size(); i++) {
                    String acciones = "";
                    if (listUsuarios.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listUsuarios.get(i).getIdusuario() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listUsuarios.get(i).getIdusuario() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>"
                                + "<button type='button' name='actualizarpassword' id='" + listUsuarios.get(i).getIdusuario() + "' class='btn btn-xs btn-warning actualizarpassword' title='Cambiar password'><i class=\"ace-icon fa fa-lock bigger-120\"></i></button>"
                                + "<button type='button' name='accesos' id='" + listUsuarios.get(i).getIdusuario() + "' class='btn btn-xs btn-warning accesos' title='Gestionar accesos'><i class=\"ace-icon fa fa-key bigger-120\"></i></button></div>";
                    }
                    if (listUsuarios.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listUsuarios.get(i).getIdusuario() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listUsuarios.get(i).getIdusuario() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>"
                                + "<button type='button' name='actualizarpassword' id='" + listUsuarios.get(i).getIdusuario() + "' class='btn btn-xs btn-warning actualizarpassword' title='Cambiar password'><i class=\"ace-icon fa fa-lock bigger-120\"></i></button>"
                                + "<button type='button' name='accesos' id='" + listUsuarios.get(i).getIdusuario() + "' class='btn btn-xs btn-warning accesos' title='Gestionar accesos'><i class=\"ace-icon fa fa-key bigger-120\"></i></button></div>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("nro", (i+1));
                    obj.put("apellidos", listUsuarios.get(i).getApellidos());
                    obj.put("nombres", listUsuarios.get(i).getNombres());
                    obj.put("estado", "A".equals(listUsuarios.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("usuario", listUsuarios.get(i).getUsuario());
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR USUARIO");
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null, st1 = null;
            String sqlEjecucion = null;
            String json = null;
            try {
                cn = db.getConnection();
                cn.setAutoCommit(false);
                st = cn.createStatement();
                st1 = cn.createStatement();

                Integer idUsuario = 0;
                String query = "SELECT NEXTVAL('gcbusiness.usuario_id_usuario_seq')";
                sqlEjecucion = query;

                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    idUsuario = rs.getInt(1);
                }
                
                String usuario = request.getParameter("usuario");
                String nombres = request.getParameter("nombres");
                String apellidos = request.getParameter("apellidos");
                String password = request.getParameter("password");
                String login_usuario = (String) session.getAttribute("login_usuario");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());

                query = "INSERT INTO gcbusiness.usuario (id_usuario, usuario, password, nombres, apellidos, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                        + " VALUES ("+ idUsuario +", '" + usuario + "',MD5('" + password + usuario + "'), '"+ nombres + "', '" + apellidos + "', 'A', '" +  ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                st.executeUpdate(query);
                
                query = "SELECT id_opcionmenu FROM gcbusiness.opcionmenu ORDER BY id_opcionmenu";
                sqlEjecucion = query; 
                rs = st.executeQuery(query);
                while(rs.next()){
                    query = "INSERT INTO gcbusiness.usuarioopcion (id_usuario, id_opcionmenu, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                        + " VALUES ("+ idUsuario + ", " + rs.getInt("id_opcionmenu") +", 'I', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                    sqlEjecucion = query;
                    st1.executeUpdate(query);
                }  
  
                json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE EL USUARIO</em>\" ";

                cn.commit();

            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Usuario_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                json += ",";
                json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> " + ex.getMessage().replace("\n", "").concat(". " + sqlEjecucion) + "</div>\" ";
                if (cn != null) {
                    System.out.println("Rollback");
                    try {
                        //deshace todos los cambios realizados en los datos
                        cn.rollback();
                    } catch (SQLException ex1) {
                        //System.err.println( "No se pudo deshacer" + ex1.getMessage() );
                        Logger.getLogger(GCBusiness_Usuario_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                json += "}";
                out.print(json);
                out.close();
                try {
                    cn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Usuario_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (cn != null) {
                        cn.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GCBusiness_Usuario_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if (opcion.equals("actualizar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idusuario = Integer.parseInt(request.getParameter("idusuario"));
                String usuario = request.getParameter("usuario");
                String nombres = request.getParameter("nombres");
                String apellidos = request.getParameter("apellidos");
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanUsuario nuevo_usuario = new BeanUsuario(idusuario, usuario, null, nombres, apellidos, estado, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoUsuarioImpl daoUsuarioImpl = new DaoUsuarioImpl();
                String msg = daoUsuarioImpl.accionActualizar(nuevo_usuario);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE EL USUARIO</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        } else if (opcion.equals("actualizarpassword")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idusuario = Integer.parseInt(request.getParameter("idusuario"));
                String password = request.getParameter("password");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanUsuario usuario = new BeanUsuario(idusuario, null, password, null, null, null, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoUsuarioImpl daoUsuarioImpl = new DaoUsuarioImpl();
                String msg = daoUsuarioImpl.actualizarPassword(usuario);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE LA CONTRASEÑA</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        } else if (opcion.equals("buscar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idusuario = Integer.parseInt(request.getParameter("idusuario"));
                DaoUsuarioImpl daoUsuarioImpl = new DaoUsuarioImpl();
                BeanUsuario beanUsuario = (BeanUsuario) daoUsuarioImpl.accionObtener(idusuario);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idusuario", beanUsuario.getIdusuario());
                obj.put("apellidos", beanUsuario.getApellidos());
                obj.put("nombres", beanUsuario.getNombres());
                obj.put("usuario", beanUsuario.getUsuario());
                obj.put("estado", beanUsuario.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("listarAccesos")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idusuario = Integer.parseInt(request.getParameter("idusuario"));            
                DaoUsuarioOpcionImpl daoUsuarioOpcionImpl = new DaoUsuarioOpcionImpl();
                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();
                List<DTOUsuarioOpcion> list = (List<DTOUsuarioOpcion>) daoUsuarioOpcionImpl.listarAccesos(idusuario);
                for (int i = 0; i < list.size(); i++) {
                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    DTOUsuarioOpcion acceso = list.get(i);
                    obj.put("numeroOrden", (i+1)+"");
                    obj.put("nombreOpcion", acceso.getDescripcionOpcion());
                    if(acceso.getEstadoUsuarioOpcion().equals("A")){
                        obj.put("acceso", "<input type=\"checkbox\" class=\"seleccionar\" onclick=\"cambiar(this);\" id='"+acceso.getIdopcionmenu()+"' checked value=\"S\"><label class=\"lblCheck\">Si</label>");
                    }else{
                        obj.put("acceso", "<input type=\"checkbox\" class=\"seleccionar\" onclick=\"cambiar(this);\" id='"+acceso.getIdopcionmenu()+"' value=\"N\"><label class=\"lblCheck\">No</label>");
                    }
                    datos.add(obj);
                }
                out.print(" {\"dataAccesos\":"+datos.toJSONString()+"} ");            
            
                
            }
        } else if (opcion.equals("grabarAccesos")) {
            try (PrintWriter out = response.getWriter()) {
                String json = "{";
                String mensaje = null;
                String data = request.getParameter("detalle")!= null ?(String) request.getParameter("detalle"): "";
                Integer idusuario = Integer.parseInt(request.getParameter("idusuario"));            
                DaoUsuarioOpcionImpl daoUsuarioOpcionImpl = new DaoUsuarioOpcionImpl();
                mensaje = daoUsuarioOpcionImpl.actualizarAccesos(idusuario, data);
                
                if(mensaje.equals("Accesos actualizados")){
                    json += " \"mensaje\":\"<em>"+mensaje+"</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-success'><span class='glyphicon glyphicon-ok'></span>"+mensaje+"</div>\" ";
                }else{
                    if(mensaje.equals("0")){
                        json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA ACTUALIZACION</em>\" ";
                        json += ",";
                        json += " \"html\":<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> Advertencia: NO SE EJECUTO LA ACTUALIZACION.</div>";
                    }else{
                        json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                        json += ",";
                        json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> "+mensaje+"</div>\" ";
                    }
                }
                
                json += "}";
                out.print(json);
                out.close();            
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idusuario = Integer.parseInt(request.getParameter("idusuario"));
                DaoUsuarioImpl daoUsuarioImpl = new DaoUsuarioImpl();
                String msg = daoUsuarioImpl.accionEliminar(idusuario);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE EL USUARIO</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        } else if (opcion.equals("activar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idusuario = Integer.parseInt(request.getParameter("idusuario"));
                DaoUsuarioImpl daoUsuarioImpl = new DaoUsuarioImpl();
                String msg = daoUsuarioImpl.accionActivar(idusuario);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE EL USUARIO</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
