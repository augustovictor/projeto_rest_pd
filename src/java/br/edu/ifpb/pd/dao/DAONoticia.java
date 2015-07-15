/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pd.dao;

import br.edu.ifpb.pd.model.Noticia;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Geferson
 */
public class DAONoticia extends DAO<Noticia>{

    public DAONoticia(EntityManager context) {
        super(context);
    }
    
    public DAONoticia(){
        
    }

    public Noticia find(Class<Noticia> aClass, Long id) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //return super.find(id);
        return super.getManager().find(aClass, id);
    }
    
    public Noticia findByTitulo(String titulo) {
            try{
                    Query q = getManager().createQuery("select n from Noticia n where n.titulo= '" + titulo +"'");
                    return (Noticia) q.getSingleResult();
            }
            catch(PersistenceException e){
                    return null;
            }
    }
    
}
