package test.java.br.com.alura.loja;

import main.java.br.com.alura.loja.Servidor;
import main.java.br.com.alura.loja.modelo.Carrinho;
import main.java.br.com.alura.loja.modelo.Produto;
import main.java.br.com.alura.loja.modelo.Projeto;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClienteTest {

    private HttpServer server;
    private WebTarget target;
    private Client client;

    @Before
    public void startaServidor() {
        server = Servidor.inicializaServidor();
        ClientConfig config = new ClientConfig();
        config.register(new LoggingFilter());
        this.client = ClientBuilder.newClient(config);
        this.target = client.target("http://localhost:8080");
    }

    @After
    public void mataServidor() {
        server.stop();
    }

    @Test
    public void testaQueSuportaNovosCarrinhos() {
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Microfone", 37, 1));
        carrinho.setRua("Rua Vergueiro 3185");
        carrinho.setCidade("Sao Paulo");
        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());
        String location = response.getHeaderString("Location");

        Carrinho carrinhoCarregado = client.target(location).request().get(Carrinho.class);
        Assert.assertEquals("Microfone", carrinhoCarregado.getProdutos().get(0).getNome());
    }

    @Test
    public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
        Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
        Assert.assertEquals("Minha loja", projeto.getNome());
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueAConexaoComOServidorFunciona() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.mocky.io");
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
        Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));

    }

    @Test
    public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
        Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
        Assert.assertEquals(1L, projeto.getId(), 0);
    }

}
