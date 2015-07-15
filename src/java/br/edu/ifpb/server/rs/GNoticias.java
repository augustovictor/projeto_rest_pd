/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.server.rs;

import br.edu.ifpb.pd.model.Noticia;
import br.edu.ifpb.pd.dao.DAO;
import br.edu.ifpb.pd.dao.DAONoticia;
import com.sun.media.jfxmedia.logging.Logger;
import java.io.StringReader;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * REST Web Service
 *
 * @author Professor
 */
@Path("noticias")
public class GNoticias {

    @Context
    private UriInfo context;

    private DAONoticia dao;
    
    private List<Noticia> noticias;
    /**
     * Creates a new instance of Noticia
     */
    public GNoticias() {
       //em = Persistence.createEntityManagerFactory("Projeto_Rest_PDPU").createEntityManager();
       
      dao = new DAONoticia();
      DAO.open();
      DAO.begin();
      this.noticias = dao.findAll();
      DAO.close();
       
  
         
    }

   
    @GET
    @Path("/listar")
    public Response listarNoticias(){//@PathParam("id")Long id //@PathParam("id")Long id@QueryParam("id") String id, @QueryParam("formato")String formato
        
        String resposta = "";
        
        if(noticias.size() == 0)
            resposta += "Nao possui noticias cadastradas!";
        else{
            
            for(Noticia n : noticias){
                resposta += n.toString();
            }
        }
        
        return Response.ok(resposta,MediaType.APPLICATION_JSON).build();
    }
    
  
    
    @GET
    //@Path("{id}")
    @Produces("application/json")
    public Response getNoticia(@QueryParam("id")Long id){ //@PathParam("id")Long id@QueryParam("id") String id, @QueryParam("formato")String formato
        dao.open();
        dao.begin();
        Noticia n = dao.find(Noticia.class,id);
        if(n.getAutor() != null){
            dao.close();
            return Response.ok(n, MediaType.APPLICATION_JSON).build();
            
        }else{
            dao.close();
            return Response.status(Status.NOT_FOUND).build();
            
        }
            
        
    }
   
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response criarNoticia(Noticia noticia) {
        
        dao.open();
        dao.begin();
        Noticia exist = dao.findByTitulo(noticia.getTitulo());
        if(exist != null){
            dao.close();
            return Response.ok("A noticia ja existe").build();
        }
            
        Noticia n = new Noticia(noticia.getTitulo() , noticia.getAutor(), noticia.getConteudo());

        dao.persist(n);
        dao.commit();
        dao.close();

       
        return Response.ok(noticia.toString()).build();
    }
    
    
    
    @DELETE
    
    @Path("{id}")
    @Produces("application/xml")
    public Response remove(@PathParam("id")Long id){//@QueryParam
    
      
        dao.open();
        dao.begin();
        Noticia n = dao.find(Noticia.class,id);
   
        if (n==null){
            dao.close();
            //return Response.ok(n,MediaType.APPLICATION_JSON+"teste").build(); 
            return Response.status(Status.NOT_FOUND).build();
        }
            
        dao.remove(n);
        dao.commit();
        dao.close();
       
        return Response.ok(n,MediaType.APPLICATION_XML).build();
    }
    
    
    @PUT
    @Produces("application/json")
    @Path("{id}/{titulo}")
    public Response atualizar(@PathParam("id") Long id,@PathParam("titulo")String titulo) { 
        dao.open();
        dao.begin();
        Noticia n = dao.find(id); 
        
        if(n == null){
            dao.close();
            return Response.status(Status.NOT_FOUND).build();
        }
         
        n.setTitulo(titulo);        
        dao.merge(n);
        dao.commit();
        dao.close();
        return Response.ok(n,MediaType.APPLICATION_JSON).build();
    }
}
