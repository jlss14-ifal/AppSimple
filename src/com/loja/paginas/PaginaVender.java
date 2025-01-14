package com.loja.paginas;

import com.loja.controles.Sistema;
import com.loja.excessoes.CadastrarException;
import com.loja.io.LeitorDeEntrada;
import com.loja.model.Item;
import com.loja.model.Item.Status;
import com.loja.model.Produto;
import com.loja.model.VendedorAnonimo;
import com.loja.util.MenssagensProntas;

public class PaginaVender {

	private MenssagensProntas menssagens;
	private LeitorDeEntrada leitor;
	private Sistema sistema;
	
	public PaginaVender(MenssagensProntas menssagens, LeitorDeEntrada leitor, Sistema sistema) {
		this.menssagens = menssagens;
		this.leitor = leitor;
		this.sistema = sistema;
	}
	
	public void iniciaPaginaDeVenda() {

        System.out.println(menssagens.getInformacoesVenda());

        VendedorAnonimo vendedor = retornaVendedor(leitor, sistema);
        
        Produto produto = retornaProduto(leitor, sistema);
        
        Item item = criarItem(leitor, vendedor, produto);
        
        // Adicionar item a lista / banco de dados
        
        try {
			
        	sistema.cadastrarItem(item, vendedor, produto);

	        System.out.println("Produto Cadastrado com Sucesso"); // Esta aqui para nao apresentar caso de algum erro no cadastro
	        
        } catch (CadastrarException e) {
			System.err.println(e.getMessage());
		}
        
	}

	private Item criarItem(LeitorDeEntrada leitor, VendedorAnonimo vendedor, Produto produto) {
		System.out.println("\nCadastro de Item:");

        String titulo    = leitor.proximoTexto("\nInsira um titulo para seu produto: ");
        String descricao = leitor.proximoTexto("\nInsira uma descrição para seu produto: ");
        double preco     = leitor.proximoDecimal("\nInsira o valor do produto: R$");

        return new Item(-1, titulo, descricao, preco, Status.DISPONIVEL, produto.getId(), vendedor.getId());
	}
	
	private Produto retornaProduto(LeitorDeEntrada leitor, Sistema sistema) {
		System.out.println("\nCadastro de Produto:");
        
        String linkAmostraDados = leitor.proximoTexto("\nInsira um link para amostra dos dados: ");
        String linkDados        = leitor.proximoTexto("\nInsira um link para dos dados(criptografados): ");
        
        return new Produto(-1, linkAmostraDados, linkDados);
	}
	
	private VendedorAnonimo retornaVendedor(LeitorDeEntrada leitor, Sistema sistema) {
		System.out.println("\nCadastro de vendedor:");
        
        String pseudonimo = leitor.proximoTexto("\nInsira um Pseudonimo de identificação: ");
        String pagamento = leitor.proximoTexto("\nInsira o método de pagamento: ");
        String contato    = leitor.proximoTexto("\nInsira os dados necessarios para contato: ");
        
        return new VendedorAnonimo(-1, pseudonimo, pagamento, contato);
	}

}
