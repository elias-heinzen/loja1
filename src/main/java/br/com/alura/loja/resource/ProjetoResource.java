package main.java.br.com.alura.loja.resource;

import main.java.br.com.alura.loja.dao.ProjetoDAO;
import main.java.br.com.alura.loja.modelo.Projeto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("projetos")
public class ProjetoResource {

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String busca(@PathParam("id") long id) {
        Projeto projeto = new ProjetoDAO().busca(id);
        return projeto.toJson();
    }
}
