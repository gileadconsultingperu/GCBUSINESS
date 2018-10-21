package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoProductoImpl;
import gilead.gcbusiness.dto.DTOProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_Venta_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION VENTA");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                List<DTOProducto> listProductos = daoProductoImpl.accionListarDTOProducto();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listProductos.size(); i++) {
                    String acciones = "";
                   
                    if (listProductos.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listProductos.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("codigo", listProductos.get(i).getCodigo());
                    obj.put("descripcion", listProductos.get(i).getDescripcion());
                    obj.put("marca", listProductos.get(i).getDescripcionmarca());
                    obj.put("categoriaproducto", listProductos.get(i).getDescripcioncategoria());
                    obj.put("unidadmedida", listProductos.get(i).getAbreviaturaunidadmedida());
                    obj.put("estado", "A".equals(listProductos.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR VENTA");
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null;
            String sqlEjecucion = null;
            String json = null;
            try{           
                cn = db.getConnection();
                cn.setAutoCommit(false);
                st = cn.createStatement();
                
                Integer idProducto = 0;
                String query = "SELECT NEXTVAL('gcbusiness.producto_id_producto_seq')";
                sqlEjecucion = query;
                
                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    idProducto = rs.getInt(1);
                }
           
                Integer idmarca = Integer.parseInt(request.getParameter("idmarca"));
                String descripcion = request.getParameter("descripcion");
                String codigo = request.getParameter("codigo");
                String codigosunat = request.getParameter("codigosunat");
                String afectoigv = request.getParameter("afectoigv");
                String afectoisc = request.getParameter("afectoisc");
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                Integer idunidadmedida = Integer.parseInt(request.getParameter("idunidadmedida"));
                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));
                Integer idtipoproducto = Integer.parseInt(request.getParameter("idtipoproducto"));
                Double preciocompra = Double.parseDouble(request.getParameter("preciocompra"));
                String flaglote = request.getParameter("flaglote");
                Integer idtipoisc = Integer.parseInt(request.getParameter("idtipoisc"));
                Double baseisc = Double.parseDouble(request.getParameter("baseisc"));
                Double factorisc = Double.parseDouble(request.getParameter("factorisc"));
                String preciosventa = request.getParameter("preciosVenta")!=null?(String)request.getParameter("preciosVenta"):"";
                String codigoproveedor = request.getParameter("codigoproveedor");
                Double pesoproveedor = Double.parseDouble(request.getParameter("pesoproveedor"));
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                Double valorcompra = preciocompra;
                if(afectoigv.equals("G"))
                    valorcompra = preciocompra*(1/(1+0.18));

                query = "INSERT INTO gcbusiness.producto (id_producto, id_marca, id_categoriaproducto, id_moneda, id_tipoproducto, id_unidadmedida, codigo_interno,"
                      + " codigo_ean, codigo_sunat, descripcion, afecto_igv, afecto_isc, id_tipoisc, base_isc, factor_isc, valor_compra, precio_compra, flag_lote, imagen,"
                      + " codigo_proveedor, peso_proveedor, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                      + " VALUES ("+idProducto+", "+idmarca+", "+idcategoriaproducto+", "+idmoneda+", "+idtipoproducto+", "+idunidadmedida+", '"+codigo
                      + "', '"+codigo+"', '"+codigosunat+"', '"+descripcion+"', '"+afectoigv+"', '"+afectoisc+"', "+idtipoisc+", "+baseisc+", "+factorisc+", "+Math.round(valorcompra* Math.pow(10,2)) / Math.pow(10, 2)+", "+preciocompra+", '"+flaglote+"', null"
                      + ", '"+codigoproveedor+"', "+pesoproveedor+", 'A','"+ts+"', '"+login_usuario+ "', '"+request.getRemoteHost()+ "', '"+request.getRemoteAddr()+"')";
                
                sqlEjecucion = query;
                st.executeUpdate(query); 
               
                query = "INSERT INTO gcbusiness.tarifaproducto (id_tarifa, id_producto, valor, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) VALUES (";
                String qry = "";
                if(!preciosventa.equals("")){
                    String [] lstPrecios = preciosventa.split("-/-");
                    String precio [];   
                    for (String precios : lstPrecios) {
                        if (!precios.equals("")) {
                            precio = precios.split(";");
                            if(precio.length > 0){
                                qry = query + precio[0] + ", "+idProducto+", "+precio[1]+", 'A','"+ts+"', '"+login_usuario+ "', '"+request.getRemoteHost()+ "', '"+request.getRemoteAddr()+"')";
                                sqlEjecucion = qry;
                                st.executeUpdate(qry);
                            }
                        }       
                    }
                }else{
                    String qry2 = "SELECT id_tarifa FROM gcbusiness.tarifa ORDER BY id_tarifa";
                    ResultSet rs2 = st.executeQuery(qry2);
                    Integer idtarifa;
                    while(rs2.next()){
                        idtarifa = rs2.getInt(1);
                        qry = query + idtarifa + ", "+idProducto+", 0.00 , 'A','"+ts+"', '"+login_usuario+ "', '"+request.getRemoteHost()+ "', '"+request.getRemoteAddr()+"')";
                        sqlEjecucion = qry;
                        st.executeUpdate(qry);
                    }
                }
                
                query = "INSERT INTO gcbusiness.proveedorproducto (id_producto, id_proveedor, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                +"SELECT "+idProducto+",id_proveedor, 'N', '"+ts+"', '"+login_usuario+ "', '"+request.getRemoteHost()+ "', '"+request.getRemoteAddr()+"'  FROM gcbusiness.proveedor ";
                sqlEjecucion = query;
                st.executeUpdate(query); 
                    
                json = "{ \"mensaje\":\"<em>SE GENERÓ CORRECTAMENTE LA VENTA</em>\" ";

                cn.commit();
                
            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                json += ",";
                json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> "+ex.getMessage().replace("\n", "").concat(". "+sqlEjecucion)+"</div>\" ";
                if(cn!=null)
                {
                    System.out.println("Rollback");
                    try {
                        //deshace todos los cambios realizados en los datos
                        cn.rollback();
                    } catch (SQLException ex1) {
                        //System.err.println( "No se pudo deshacer" + ex1.getMessage() );
                        Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                json += "}";
                out.print(json);
                out.close();
                try {
                    cn.close();
                }catch(SQLException ex){
                    Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {if(cn!=null)cn.close();if(st!=null)st.close();} catch (SQLException ex) {Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);}
            }
            
            
        }else if (opcion.equals("actualizar")) {
            System.out.println("ACTUALIZAR VENTA");
            PrintWriter out = response.getWriter();
            ConectaDb db = new ConectaDb();
            Connection cn = null;
            Statement st = null;
            String sqlEjecucion = null;
            String json = null;
            try{           
                cn = db.getConnection();
                cn.setAutoCommit(false);
                st = cn.createStatement();

                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                Integer idmarca = Integer.parseInt(request.getParameter("idmarca"));
                String descripcion = request.getParameter("descripcion");
                String codigo = request.getParameter("codigo");
                String codigosunat = request.getParameter("codigosunat");
                String afectoigv = request.getParameter("afectoigv");
                String afectoisc = request.getParameter("afectoisc");
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                Integer idunidadmedida = Integer.parseInt(request.getParameter("idunidadmedida"));
                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));
                Integer idtipoproducto = Integer.parseInt(request.getParameter("idtipoproducto"));
                Double preciocompra = Double.parseDouble(request.getParameter("preciocompra"));
                String flaglote = request.getParameter("flaglote");
                Integer idtipoisc = Integer.parseInt(request.getParameter("idtipoisc"));
                Double baseisc = Double.parseDouble(request.getParameter("baseisc"));
                Double factorisc = Double.parseDouble(request.getParameter("factorisc"));
                String preciosventa = request.getParameter("preciosVenta")!=null?(String)request.getParameter("preciosVenta"):"";
                String codigoproveedor = request.getParameter("codigoproveedor");
                Double pesoproveedor = Double.parseDouble(request.getParameter("pesoproveedor"));
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                Double valorcompra = preciocompra;
                if(afectoigv.equals("G"))
                    valorcompra = preciocompra*(1/(1+0.18));
                
                String query = "UPDATE gcbusiness.producto SET id_marca="+idmarca+", id_categoriaproducto="+idcategoriaproducto+", id_moneda="+idmoneda+", id_tipoproducto="+idtipoproducto
                + ", id_unidadmedida="+idunidadmedida+", codigo_interno='"+codigo+"', codigo_sunat='"+codigosunat+"', descripcion='"+descripcion+"', afecto_igv='"+afectoigv+"', afecto_isc='"+afectoisc+"', id_tipoisc="+idtipoisc
                +", base_isc="+baseisc+", factor_isc="+factorisc+", valor_compra="+Math.round(valorcompra* Math.pow(10,2)) / Math.pow(10,2)+", precio_compra="+preciocompra+", flag_lote='"+flaglote+"', estado='"+estado
                +"', codigo_proveedor='"+codigoproveedor+"', peso_proveedor="+pesoproveedor+", fecha_modificacion='"+ts+"', usuario_modificacion='"+login_usuario+"', terminal_modificacion='"+request.getRemoteHost()
                +"', ip_modificacion='"+request.getRemoteAddr()+"' WHERE id_producto="+idproducto+";";
                
                System.err.println("query: "+query);
               
                sqlEjecucion = query;
                st.executeUpdate(query); 
               
                query = "UPDATE gcbusiness.tarifaproducto SET valor=";
                String qry = "";
                
                String [] lstPrecios = preciosventa.split("-/-");
                String precio [];   
                for (String precios : lstPrecios) {
                    if (!precios.equals("")) {
                        precio = precios.split(";");
                        if(precio.length > 0){
                            qry = query + precio[1] + ", fecha_modificacion='"+ts+"', usuario_modificacion='"+login_usuario+
                                "', terminal_modificacion='"+request.getRemoteHost()+"', ip_modificacion='"+request.getRemoteAddr()+
                                "' WHERE id_tarifa="+precio[0] + " AND id_producto="+idproducto+";";
                            sqlEjecucion = qry;
                            st.executeUpdate(qry);
                        }
                    }       
                }
                    
                json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE LA VENTA</em>\" ";

                cn.commit();
                
            } catch (SQLException ex) {
                Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                json += ",";
                json += " \"html\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-remove'></span> "+ex.getMessage().replace("\n", "").concat(". "+sqlEjecucion)+"</div>\" ";
                if(cn!=null)
                {
                    System.out.println("Rollback");
                    try {
                        //deshace todos los cambios realizados en los datos
                        cn.rollback();
                    } catch (SQLException ex1) {
                        //System.err.println( "No se pudo deshacer" + ex1.getMessage() );
                        Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                json += "}";
                out.print(json);
                out.close();
                try {
                    cn.close();
                }catch(SQLException ex){
                    Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {if(cn!=null)cn.close();if(st!=null)st.close();} catch (SQLException ex) {Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);}
            }
        } else if (opcion.equals("buscar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                DTOProducto dtoProducto = (DTOProducto) daoProductoImpl.accionObtener(idproducto);
   
                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idproducto", dtoProducto.getIdproducto());
                obj.put("codigo", dtoProducto.getCodigo());
                obj.put("codigosunat", dtoProducto.getCodigosunat());
                obj.put("descripcion", dtoProducto.getDescripcion());
                obj.put("familiaproducto", dtoProducto.getIdfamiliaproducto());
                obj.put("claseproducto", dtoProducto.getIdclaseproducto());
                obj.put("lineaproducto", dtoProducto.getIdlineaproducto());
                obj.put("categoriaproducto", dtoProducto.getIdcategoriaproducto());
                obj.put("afectoigv", dtoProducto.getAfectoIGV());
                obj.put("afectoisc", dtoProducto.getAfectoISC());
                obj.put("marca", dtoProducto.getIdmarca());
                obj.put("unidadmedida", dtoProducto.getIdunidadmedida());
                obj.put("moneda", dtoProducto.getIdmoneda());
                obj.put("tipoproducto", dtoProducto.getIdtipoproducto());
                obj.put("preciocompra", dtoProducto.getPreciocompra());
                obj.put("tipoisc", dtoProducto.getIdtipoISC());                
                obj.put("baseisc", dtoProducto.getBaseISC());
                obj.put("factorisc", dtoProducto.getFactorISC());
                obj.put("codigoproveedor", dtoProducto.getCodigoproveedor());
                obj.put("pesoproveedor", dtoProducto.getPesoproveedor());
                obj.put("flaglote", dtoProducto.getFlaglote());
                obj.put("estado", dtoProducto.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                String msg = daoProductoImpl.accionEliminar(idproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ANULÓ CORRECTAMENTE LA VENTA</em>\" ";
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
