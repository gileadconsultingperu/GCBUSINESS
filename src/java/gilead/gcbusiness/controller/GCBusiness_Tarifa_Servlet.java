package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoTarifaImpl;
import gilead.gcbusiness.dao.impl.DaoTarifaProductoImpl;
import gilead.gcbusiness.model.BeanTarifa;
import gilead.gcbusiness.model.BeanTarifaProducto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

public class GCBusiness_Tarifa_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION TARIFA");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoTarifaImpl daoTarifaImpl = new DaoTarifaImpl();
                List<BeanTarifa> listTarifas = daoTarifaImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listTarifas.size(); i++) {
                    String acciones = "";
                    if (listTarifas.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listTarifas.get(i).getIdtarifa() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listTarifas.get(i).getIdtarifa() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listTarifas.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listTarifas.get(i).getIdtarifa() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listTarifas.get(i).getIdtarifa() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("nro", (i+1));
                    obj.put("estado", "A".equals(listTarifas.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("descripcion", listTarifas.get(i).getDescripcion());
                    obj.put("abreviatura", listTarifas.get(i).getAbreviatura());
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR TARIFA");
            try (PrintWriter out = response.getWriter()) {
                String descripcion = request.getParameter("descripcion");
                String abreviatura = request.getParameter("abreviatura");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanTarifa nuevo_tarifa = new BeanTarifa(null, descripcion, abreviatura, "A", ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr(), null, null, null, null);
                DaoTarifaImpl daoTarifaImpl = new DaoTarifaImpl();
                String msg = daoTarifaImpl.accionCrear(nuevo_tarifa);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE LA TARIFA</em>\" ";
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
                Integer idtarifa = Integer.parseInt(request.getParameter("idtarifa"));
                String descripcion = request.getParameter("descripcion");
                String abreviatura = request.getParameter("abreviatura");
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanTarifa nuevo_tarifa = new BeanTarifa(idtarifa, descripcion, abreviatura, estado, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoTarifaImpl daoTarifaImpl = new DaoTarifaImpl();
                String msg = daoTarifaImpl.accionActualizar(nuevo_tarifa);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE LA TARIFA</em>\" ";
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
                Integer idtarifa = Integer.parseInt(request.getParameter("idtarifa"));
                DaoTarifaImpl daoTarifaImpl = new DaoTarifaImpl();
                BeanTarifa beanTarifa = (BeanTarifa) daoTarifaImpl.accionObtener(idtarifa);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idtarifa", beanTarifa.getIdtarifa());
                obj.put("descripcion", beanTarifa.getDescripcion());
                obj.put("abreviatura", beanTarifa.getAbreviatura());
                obj.put("estado", beanTarifa.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idtarifa = Integer.parseInt(request.getParameter("idtarifa"));
                DaoTarifaImpl daoTarifaImpl = new DaoTarifaImpl();
                String msg = daoTarifaImpl.accionEliminar(idtarifa);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE LA TARIFA</em>\" ";
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
                Integer idtarifa = Integer.parseInt(request.getParameter("idtarifa"));
                DaoTarifaImpl daoTarifaImpl = new DaoTarifaImpl();
                String msg = daoTarifaImpl.accionActivar(idtarifa);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE LA TARIFA</em>\" ";
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoTarifaProductoImpl daoTarifaProducto = new DaoTarifaProductoImpl();
                
        Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
        
        JsonArrayBuilder array = Json.createArrayBuilder();//creamos el arreglo json
        System.out.println("idproducto: "+idproducto);
        //obtenemos la lista de Tarifas del producto
        List<BeanTarifaProducto> lstTarifas = daoTarifaProducto.listarTarifaProducto(idproducto);
        //debemos preparar la lista a devolver. 

        //recorremos la lista
        for (BeanTarifaProducto tarifa : lstTarifas) {
            //creamos un objeto json con los campos que necesitamos
            JsonObject item = Json.createObjectBuilder()
                    .add("idtarifa", tarifa.getIdtarifa())
                    .add("idproducto", tarifa.getIdproducto())
                    .add("valor", tarifa.getValor())
                    .add("estado", tarifa.getEstado())
                    .add("descripcion", tarifa.getDescripciontarifa()).build();
            array.add(item); //.. y lo agregamos al arreglo json
        }
        
        response.setContentType(MediaType.APPLICATION_JSON); //ahora preparamos la salida json al cliente...
        try (JsonWriter jsonWriter = Json.createWriter(response.getOutputStream())) { //.. para imprimir... 
            jsonWriter.writeArray(array.build());
        }
    }

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
