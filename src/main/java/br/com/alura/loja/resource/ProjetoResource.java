package main.java.br.com.alura.loja.resource;

import main.java.br.com.alura.loja.dao.ProjetoDAO;
import main.java.br.com.alura.loja.modelo.Projeto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("projetos")
public class ProjetoResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String busca() {
        Projeto projeto = new ProjetoDAO().busca(1L);
        return projeto.toXML();
    }
}
