package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoAlmacenImpl;
import gilead.gcbusiness.dao.impl.DaoSucursalImpl;
import gilead.gcbusiness.model.BeanAlmacen;
import gilead.gcbusiness.model.BeanSucursal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_Almacen_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION ALMACEN");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoAlmacenImpl daoAlmacenImpl = new DaoAlmacenImpl();
                DaoSucursalImpl daoSucursalImpl = new DaoSucursalImpl();
                List<BeanAlmacen> listAlmacenes = daoAlmacenImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listAlmacenes.size(); i++) {
                    String acciones = "";
                    String descripcionSucursal = "";
                    
                    if(listAlmacenes.get(i).getIdsucursal()>0){
                        BeanSucursal sucursal = null;                  
                        sucursal = daoSucursalImpl.accionObtener(listAlmacenes.get(i).getIdsucursal());
                        descripcionSucursal= sucursal.getDescripcion();
                    }
                    
                    if (listAlmacenes.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listAlmacenes.get(i).getIdalmacen() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listAlmacenes.get(i).getIdalmacen() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listAlmacenes.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listAlmacenes.get(i).getIdalmacen() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listAlmacenes.get(i).getIdalmacen() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("nro", (i+1));
                    obj.put("sucursal", descripcionSucursal);
                    obj.put("descripcion", listAlmacenes.get(i).getDescripcion());
                    obj.put("estado", "A".equals(listAlmacenes.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("transito", "S".equals(listAlmacenes.get(i).getFlagtransito())?"SI":"NO");
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR ALMACEN");
            try (PrintWriter out = response.getWriter()) {
                Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
                String descripcion = request.getParameter("descripcion");
                String transito = request.getParameter("transito");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanAlmacen nuevo_almacen = new BeanAlmacen(null, idsucursal, descripcion, "A", transito, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr(), null, null, null, null);
                DaoAlmacenImpl daoAlmacenImpl = new DaoAlmacenImpl();
                String msg = daoAlmacenImpl.accionCrear(nuevo_almacen);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE EL ALMACEN</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        }else if (opcion.equals("actualizar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
                Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
                String descripcion = request.getParameter("descripcion");
                String transito = request.getParameter("transito");
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                System.out.println("idsucursal: "+idsucursal+" - transito: "+transito);
                BeanAlmacen nuevo_almacen = new BeanAlmacen(idalmacen, idsucursal, descripcion, estado, transito, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoAlmacenImpl daoAlmacenImpl = new DaoAlmacenImpl();
                String msg = daoAlmacenImpl.accionActualizar(nuevo_almacen);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE EL ALMACEN</em>\" ";
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
                Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
                DaoAlmacenImpl daoAlmacenImpl = new DaoAlmacenImpl();
                BeanAlmacen beanAlmacen = (BeanAlmacen) daoAlmacenImpl.accionObtener(idalmacen);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idalmacen", beanAlmacen.getIdalmacen());
                obj.put("idsucursal", beanAlmacen.getIdsucursal());
                obj.put("descripcion", beanAlmacen.getDescripcion());
                obj.put("transito", beanAlmacen.getFlagtransito());
                obj.put("estado", beanAlmacen.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
                DaoAlmacenImpl daoAlmacenImpl = new DaoAlmacenImpl();
                String msg = daoAlmacenImpl.accionEliminar(idalmacen);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE EL ALMACEN</em>\" ";
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
                Integer idalmacen = Integer.parseInt(request.getParameter("idalmacen"));
                DaoAlmacenImpl daoAlmacenImpl = new DaoAlmacenImpl();
                String msg = daoAlmacenImpl.accionActivar(idalmacen);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE EL ALMACEN</em>\" ";
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
