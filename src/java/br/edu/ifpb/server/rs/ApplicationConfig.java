/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.server.rs;

import br.edu.ifpb.pd.dao.DAO;
import br.edu.ifpb.pd.dao.DAOInterface;
import br.edu.ifpb.pd.dao.DAONoticia;
import br.edu.ifpb.pd.model.Noticia;
import java.util.Set;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Application;

/**
 *
 * @author Professor
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.edu.ifpb.server.rs.GNoticias.class);
    }
    
}
