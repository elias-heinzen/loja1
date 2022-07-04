package main.java.br.com.alura.loja.resource;

import main.java.br.com.alura.loja.dao.CarrinhoDAO;
import main.java.br.com.alura.loja.modelo.Carrinho;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("carrinhos")
public class CarrinhoResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String busca() {
        Carrinho carrinho = new CarrinhoDAO().busca(1L);
        return carrinho.toXML();
    }

}
