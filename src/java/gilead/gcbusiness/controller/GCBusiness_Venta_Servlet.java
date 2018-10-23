package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoVentaImpl;
import gilead.gcbusiness.dto.DTOVenta;
import gilead.gcbusiness.sql.ConectaDb;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GCBusiness_Venta_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION VENTA");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoVentaImpl daoVentaImpl = new DaoVentaImpl();
                List<DTOVenta> listVentas = daoVentaImpl.accionListarDTOVenta();
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.UK);
                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listVentas.size(); i++) {
                    String acciones = "";
                   
                    if (listVentas.get(i).getEstado().equals("E")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listVentas.get(i).getIdventa() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listVentas.get(i).getIdventa() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listVentas.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listVentas.get(i).getIdventa() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listVentas.get(i).getIdventa() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("fecha", dateFormat.format(listVentas.get(i).getFecha_emision().getTime()));
                    obj.put("codigocomprobante", listVentas.get(i).getCodigoSunatcomprobante());
                    obj.put("tipocomprobante", listVentas.get(i).getDescripcioncomprobante());
                    obj.put("idserie", listVentas.get(i).getIdserie());
                    obj.put("serie", listVentas.get(i).getSerie());
                    obj.put("correlativo", listVentas.get(i).getCorrelativoserie());
                    obj.put("idcliente", listVentas.get(i).getIdcliente());
                    obj.put("numerodocumento", listVentas.get(i).getNumerodocumentocliente());
                    obj.put("nombrecliente", listVentas.get(i).getNombrecliente());
                    obj.put("idmoneda", listVentas.get(i).getIdmoneda());
                    obj.put("moneda", listVentas.get(i).getDescripcionMoneda());
                    obj.put("totalventa", listVentas.get(i).getTotal_venta());
                    obj.put("pagado", "A".equals(listVentas.get(i).getEstado()) ? "SI" : "NO");
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
                
                Integer idVenta = 0;
                String query = "SELECT NEXTVAL('gcbusiness.venta_id_venta_seq')";
                sqlEjecucion = query;
                
                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    idVenta = rs.getInt(1);
                }
                
                Integer idserie = Integer.parseInt(request.getParameter("idserie"));
                
                //Obtener serie y correlativo actual
                String serie = "";
                int correlativo = 0;
                String query2 = "SELECT serie, correlativo FROM gcbusiness.serie WHERE id_serie=" + idserie + ";";
                ResultSet rs2 = st.executeQuery(query2);

                if (rs2.next()) {
                    serie = rs2.getString(1);
                    correlativo = rs2.getInt(2);
                }

                int newCorrelativo = correlativo + 1;
                String query3 = "UPDATE gcbusiness.serie SET correlativo=" + newCorrelativo + " WHERE id_serie=" + idserie + ";";
                st.executeUpdate(query3);
                cn.commit();
           
                Integer idcliente = Integer.parseInt(request.getParameter("idcliente"));
                Integer idtipoComprobante = Integer.parseInt(request.getParameter("idtipoComprobante"));
                Integer idvendedor = Integer.parseInt(request.getParameter("idvendedor"));              
                String flag_negociable = request.getParameter("flag_negociable");
                String fecha_vencimiento = request.getParameter("fecha_vencimiento");
                if(flag_negociable.equals("N"))
                    fecha_vencimiento = null;
                
                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));
                
                String formapago = request.getParameter("formapago");
                
                //LOGICA PARA FORMA DE PAGO
                Double importe_pago = Double.parseDouble(request.getParameter("importe_pago"));
                Double monto_efectivo = Double.parseDouble(request.getParameter("monto_efectivo"));
                Double monto_otro = Double.parseDouble(request.getParameter("monto_otro"));
                String referencia_otro = request.getParameter("referencia_otro");
                Double cambio_pago = Double.parseDouble(request.getParameter("cambio_pago"));
                //FIN LOGICA FORMA DE PAGO
                
                String estatuspago = request.getParameter("estatuspago");             
                Double montopagado = 0.00;
     
                
                String tipo_descuentoglobal = request.getParameter("tipo_dctoglobal");
                Double pcto_dctoglobal = Double.parseDouble(request.getParameter("pcto_dctoglobal"));
                Double monto_dctoglobal = Double.parseDouble(request.getParameter("monto_dctoglobal"));
                
                String flag_gravada = request.getParameter("flag_gravada");
                Double total_gravada = Double.parseDouble(request.getParameter("total_gravada"));
                Double total_inafecta = Double.parseDouble(request.getParameter("total_inafecta"));
                Double total_exonerada = Double.parseDouble(request.getParameter("total_exonerada"));
                Double total_gratuita = Double.parseDouble(request.getParameter("total_gratuita"));
                Double total_impuesto = Double.parseDouble(request.getParameter("total_impuesto"));
                Double total_igv = Double.parseDouble(request.getParameter("total_igv"));
                Double total_isc = Double.parseDouble(request.getParameter("total_isc"));
                Double total_otrotributo = Double.parseDouble(request.getParameter("total_otrotributo"));
                Double total_valorventa = Double.parseDouble(request.getParameter("total_valorventa"));
                Double total_precioventa = Double.parseDouble(request.getParameter("total_precioventa"));
                Double total_descuento = Double.parseDouble(request.getParameter("total_descuento"));
                Double total_otrocargo = request.getParameter("total_otrocargo")==""?0.00:Double.parseDouble(request.getParameter("total_otrocargo"));
                Double total_venta = Double.parseDouble(request.getParameter("total_venta"));
                
                //INICIO LOGICA PARA ANTICIPO
                Double total_anticipo = Double.parseDouble(request.getParameter("total_anticipo"));;
                //FIN LOGICA ANTICIPO 
                
                Integer idsucursal = Integer.parseInt((String) session.getAttribute("idSucursal"));
                Integer idalmacen = Integer.parseInt((String) session.getAttribute("idAlmacen"));
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                
                //REGISTRAR VENTA
                query = "INSERT INTO gcbusiness.venta (id_venta, id_cliente, id_tipocomprobante, id_sucursal, id_almacen, id_vendedor, id_serie, correlativo_serie, fecha_emision, flag_negociable, "
                      + "fecha_vencimiento, id_moneda, id_formapago, id_estatuspago, tipo_descuentoglobal, monto_descuentoglobal, pcto_descuentoglobal, flag_gravada, total_gravada, total_inafecta, "
                      + "total_exonerada, total_gratuita, total_impuesto, total_igv, total_isc, total_otrotributo, total_valorventa, total_precioventa, total_descuento, total_otrocargo, total_anticipo, "
                      + "total_venta, importe_pago, monto_efectivo, monto_otro, referencia_otro, cambio_pago, estado, motivo_anulacion, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                      + " VALUES ("+idVenta+", "+idcliente+", "+idtipoComprobante+", "+idsucursal+", "+idalmacen+", "+idvendedor+", "+idserie+", "+correlativo+", '"+ts+"', '"+flag_negociable+"', "
                      + fecha_vencimiento+", "+idmoneda+", '"+formapago+"', '"+estatuspago+"', '"+tipo_descuentoglobal+"', "+monto_dctoglobal+", "+pcto_dctoglobal+", '"+flag_gravada+"', "+total_gravada+", "+total_inafecta+", "
                      + total_exonerada+", "+total_gratuita+", "+total_impuesto+", "+total_igv+", "+total_isc+", "+total_otrotributo+", "+total_valorventa+", "+total_precioventa+", "+total_descuento+", "+total_otrocargo+", "+total_anticipo+", "
                      + total_venta+", "+importe_pago+", "+monto_efectivo+", "+monto_otro+", '"+referencia_otro+"', "+cambio_pago+", 'E', null, '"+ts+"', '"+login_usuario+ "', '"+request.getRemoteHost()+ "', '"+request.getRemoteAddr()+"')";
                
                sqlEjecucion = query;
                System.out.println("query: "+query);
                st.executeUpdate(query); 
                //FIN REGISTRAR VENTA
                
                
                //INICIO REGISTRAR MOVIMIENTO INVENTARIO 
                String observacion = serie+"-"+correlativo;
             
                Integer idMovimientoInventario = 0;
                query = "SELECT NEXTVAL('gcbusiness.movimientoinventario_id_movimientoinventario_seq')";
                sqlEjecucion = query;

                rs = st.executeQuery(query);

                if (rs.next()) {
                    idMovimientoInventario = rs.getInt(1);
                }

                query = "INSERT INTO gcbusiness.movimientoinventario (id_movimientoinventario, id_almacen, id_motivomovimiento, fecha, observacion, estado,"
                        + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                        + " VALUES (" + idMovimientoInventario + ", " + idalmacen + ", 9 , '" + ts + "', '" + observacion + "', 'A',"
                        + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                sqlEjecucion = query;
                st.executeUpdate(query);    
                //FIN REGISTRAR MOVIMIENTO INVENTARIO
                
                
                //INICIO REGISTRAR DETALLE VENTA Y DETALLE MOVIMIENTO INVENTARIO
                String detalleVenta = request.getParameter("detalleventa");
                JSONArray arrayDetalleVenta;
                arrayDetalleVenta = new JSONArray(detalleVenta);
                
                Integer idProducto = 0, idTarifa = 0;
                Double cantidad = 0.00, pctoIGV = 0.00, valor_unitario_venta = 0.00, precio_unitario_venta=0.00, valor_venta = 0.00, precio_venta=0.00, monto_igv=0.00, monto_isc=0.00,
                       monto_descuento=0.00, pcto_descuento=0.00, valor_venta_descuento=0.00, precio_venta_descuento=0.00, monto_igv_descuento=0.00,
                       stock_actual=0.000 ;
                String tipoIGV = "", tipoISC="", flag_bonificacion="", descuento="", tipo_descuento="", afectoIGV="";
                for (int i = 0; i < arrayDetalleVenta.length(); i++) {
                        JSONObject fila1 = null;
                        fila1 = arrayDetalleVenta.getJSONObject(i);
                        
                        idProducto = Integer.parseInt((String) fila1.get("IdProducto"));
                        
                        stock_actual = Double.parseDouble((String) fila1.get("Stock Actual"));

                        cantidad = Double.parseDouble((String) fila1.get("Cantidad"));
                        
                        valor_unitario_venta = Double.parseDouble((String) fila1.get("Valor Unitario"));
                        valor_venta = valor_unitario_venta*cantidad;
                        valor_venta = Math.round(valor_venta * Math.pow(10, 2)) / Math.pow(10, 2);  
                        
                        idTarifa = Integer.parseInt((String) fila1.get("Precio Unitario"));
                        query = "SELECT valor FROM gcbusiness.tarifaproducto WHERE id_producto =" + idProducto + " AND id_tarifa = " + idTarifa;
                        ResultSet rs3 = st.executeQuery(query);
                        if (rs3.next()) {
                            precio_unitario_venta = rs3.getDouble(1);
                        }
                        precio_venta = precio_unitario_venta*cantidad;
                        precio_venta = Math.round(precio_venta * Math.pow(10, 2)) / Math.pow(10, 2);
                        
                        monto_igv = Double.parseDouble((String) fila1.get("IGV"));
                        
                        //FALTA TIPO ISC
                        monto_isc = Double.parseDouble((String) fila1.get("ISC"));
                        
                        afectoIGV = (String) fila1.get("Afecto IGV");
                        flag_bonificacion = (String) fila1.get("Bonificación");
                        
                        //TIPO IGV Y PCTO IGV
                        if(flag_bonificacion.equals("S")){                           
                            if(afectoIGV.trim().equals("G")){
                                tipoIGV = "15"; //Gravado – Bonificaciones
                                pctoIGV = (precio_venta-valor_venta)/valor_venta;
                                pctoIGV *= 100;
                                pctoIGV = Math.round(pctoIGV * Math.pow(10, 2)) / Math.pow(10, 2);
                            }
                            if(afectoIGV.trim().equals("E")){
                                tipoIGV = "21"; //Exonerado – Transferencia Gratuita
                            }
                            if(afectoIGV.trim().equals("I")){
                                tipoIGV = "31"; //Inafecto – Retiro por Bonificación
                            }
                        }else{
                            if(afectoIGV.trim().equals("G")){
                                tipoIGV = "10"; //Gravado – Operación Onerosa
                                pctoIGV = (precio_venta-valor_venta)/valor_venta; 
                                pctoIGV *= 100;
                                pctoIGV = Math.round(pctoIGV * Math.pow(10, 2)) / Math.pow(10, 2);
                            }
                            if(afectoIGV.trim().equals("E")){
                                tipoIGV = "20"; //Exonerado – Operación Onerosa
                            }
                            if(afectoIGV.trim().equals("I")){
                                tipoIGV = "30"; //Inafecto – Operación Onerosa
                            }
                        }
                        System.out.println("tipoIGV: " + tipoIGV + "  pctoIGV: "+pctoIGV);
                        
                        //DESCUENTO
                        descuento = (String) fila1.get("Descuento");
                        System.out.println("descuento: "+descuento);
                        String[] detalleDcto = descuento.split(" | ");
                        
                        tipo_descuento = detalleDcto[2].trim();
                        System.out.println("tipo_descuento: "+tipo_descuento);
                        System.out.println("sss  "+detalleDcto[0]);
                        
                        if(tipo_descuento.equals("P")){
                            if (detalleDcto[0] != null && detalleDcto[0].length() > 0 && detalleDcto[0]!="") {
                                pcto_descuento = Double.parseDouble(detalleDcto[0]);
                            }
                            //pcto_descuento = detalleDcto[0]==null?0.00:Double.parseDouble(detalleDcto[0].trim());
                            valor_venta_descuento = valor_venta*(1 - pcto_descuento/100);
                        }else{
                            if (detalleDcto[0] != null && detalleDcto[0].length() > 0 && detalleDcto[0]!="") {
                                monto_descuento = Double.parseDouble(detalleDcto[0]);
                            }
                            //monto_descuento = detalleDcto[0]==null?0.00:Double.parseDouble(detalleDcto[0].trim());
                            valor_venta_descuento = valor_venta - monto_descuento;
                        }
                        System.out.println("monto_descuento  "+monto_descuento);
                        System.out.println("pcto_descuento  "+pcto_descuento);
                        
                        valor_venta_descuento = Math.round(valor_venta_descuento * Math.pow(10, 2)) / Math.pow(10, 2); 
                        
                        monto_igv_descuento = valor_venta_descuento*pctoIGV/100;
                        monto_igv_descuento = Math.round(monto_igv_descuento * Math.pow(10, 2)) / Math.pow(10, 2); 
                                
                        precio_venta_descuento = valor_venta_descuento+monto_igv_descuento;

                        //Insertar detalle venta
                        query = "INSERT INTO gcbusiness.detalle_venta (id_venta, id_producto, cantidad, tipo_igv, pcto_igv, valor_unitario_venta, precio_unitario_venta, valor_venta, precio_venta, "
                              + "monto_igv, tipo_isc, monto_isc, flag_bonificacion, tipo_descuento, monto_descuento, pcto_descuento, valor_venta_descuento, precio_venta_descuento, monto_igv_descuento, "
                              + "fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                              + "VALUES (" + idVenta + ", " + idProducto + ", " + cantidad + ", '" + tipoIGV + "', " + pctoIGV + ", " + valor_unitario_venta + ", " + precio_unitario_venta + ", " + valor_venta + ", " + precio_venta + ", "
                              + monto_igv +", '" + tipoISC +"', " + monto_isc +", '" + flag_bonificacion +"', '" + tipo_descuento +"', " + monto_descuento +", " + pcto_descuento +", " + valor_venta_descuento +", " + precio_venta_descuento +", " + monto_igv_descuento +", '"    
                              + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                        
                        sqlEjecucion = query;
                        st.executeUpdate(query);
                        
                        Integer idlote = null;
                        String columnaLote = (String) fila1.get("Lote|F.V.");
                        if(columnaLote.contains("|")){
                            String[] detalleLote = columnaLote.split("|");
                            idlote = Integer.parseInt(detalleLote[0].trim());
                        }
                        
                        Double nuevostock = stock_actual - cantidad;
                        
                        query = "INSERT INTO gcbusiness.detalle_movimientoinventario (id_movimientoinventario, id_producto, id_lote, cantidad, stock_saldo,"
                                + " fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion)"
                                + " VALUES (" + idMovimientoInventario + ", " + idProducto + ", " + idlote + ", " + cantidad + "," + nuevostock + ","
                                + " '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";

                        sqlEjecucion = query;
                        st.executeUpdate(query);
                        
                        
                        //ACTUALIZAR STOCK
                        if (idlote == null) {
                            query = "UPDATE gcbusiness.almacenproductolote SET stock_actual = " + nuevostock
                                    + " WHERE id_producto = " + idProducto + " AND id_almacen = " + idalmacen;
                        } else {
                            query = "UPDATE gcbusiness.almacenproductolote SET stock_actual = " + nuevostock
                                    + " WHERE id_producto = " + idProducto + " AND id_almacen = " + idalmacen + " AND id_lote = " + idlote;
                        }

                        sqlEjecucion = query;
                        st.executeUpdate(query);                      
                }
                //FIN REGISTRAR DETALLE VENTA Y DETALLE MOVIMIENTO INVENTARIO
   
                //REGISTRAR A CUENTAS POR COBRAR SI APLICA
                if(total_venta>0){
                    if(estatuspago.equals("P") || estatuspago.equals("S")){
                        if(estatuspago.equals("P")) 
                            montopagado = Double.parseDouble(request.getParameter("montopagado"));

                        Integer id_cuentacobrar = 0;
                        query = "SELECT NEXTVAL('gcbusiness.cuentacobrar_id_cuentacobrar_seq')";
                        rs = st.executeQuery(query);
                        if (rs.next()) {
                            id_cuentacobrar = rs.getInt(1);
                        }
                        
                        Double saldo = total_venta - montopagado;
                        
                        query = "INSERT INTO gcbusiness.cuentacobrar(id_cuentacobrar, id_cliente, id_venta, saldo, estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                              + "VALUES ("+ id_cuentacobrar + ", " + idcliente+ ", " + idVenta + ", " + saldo + ", 'P' , '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                        sqlEjecucion = query;
                        st.executeUpdate(query);
                        
                        //INSERTAR EN MOVIMIENTO CUENTA COBRAR
                        if(montopagado>0){
                            query = "INSERT INTO gcbusiness.movimiento_cuentapagar(id_cuentacobrar, fecha, monto, saldo_anterior, saldo_actual, documento_referencia, "
                                  + "estado, fecha_insercion, usuario_insercion, terminal_insercion, ip_insercion) "
                              + "VALUES ("+ id_cuentacobrar + ", '" + ts+ "', " + montopagado + ", " + total_venta + ", " + saldo + ",'"+ observacion +"', '" + ts + "', '" + login_usuario + "', '" + request.getRemoteHost() + "', '" + request.getRemoteAddr() + "')";
                            sqlEjecucion = query;
                            st.executeUpdate(query);
                        }
                    }
                }
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
  
        } else if (opcion.equals("buscar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idventa = Integer.parseInt(request.getParameter("idventa"));
                DaoVentaImpl daoVentaImpl = new DaoVentaImpl();
                DTOVenta dtoVenta = (DTOVenta) daoVentaImpl.accionObtener(idventa);
   
                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idventa", dtoVenta.getIdventa());               
                obj.put("cliente", dtoVenta.getIdcliente());               
                obj.put("moneda", dtoVenta.getIdmoneda());              
                obj.put("estado", dtoVenta.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idventa = Integer.parseInt(request.getParameter("idventa"));
                DaoVentaImpl daoVentaImpl = new DaoVentaImpl();
                String msg = daoVentaImpl.accionEliminar(idventa);

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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(GCBusiness_Venta_Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
