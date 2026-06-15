package farmacia.promocoes;

import farmacia.modelos.Venda;

/**
 * Classe PromocaoLeveXPagueY - implementa a promoção clássica "Leve X Pague Y".
 * Exemplo: "Leve 3 pague 2" significa que a cada 3 itens comprados, apenas 2 são cobrados.
 * O cliente economiza (X - Y) itens para cada pacote completo de X itens.
 */
public class PromocaoLeveXPagueY implements Promocao {
    private static final long serialVersionUID = 1L;

    // Código do produto que terá promoção Leve X Pague Y
    private int codigoProduto;
    
    // Quantidade necessária para ativar a promoção (ex: 3 para "Leve 3")
    private int quantidadeX;
    
    // Quantidade que o cliente efetivamente paga (ex: 2 para "Pague 2")
    private int quantidadeY;

    /**
     * Construtor de PromocaoLeveXPagueY.
     * @param codigoProduto o código do produto que terá promoção
     * @param quantidadeX a quantidade necessária para ativar a promoção
     * @param quantidadeY a quantidade que o cliente paga
     */
    public PromocaoLeveXPagueY(int codigoProduto, int quantidadeX, int quantidadeY) {
        this.codigoProduto = codigoProduto;
        this.quantidadeX = quantidadeX;
        this.quantidadeY = quantidadeY;
    }

    /**
     * Calcula o desconto da promoção Leve X Pague Y.
     * Para cada "X" itens comprados, o cliente paga por apenas "Y", economizando (X-Y) itens.
     * Exemplo: Se o cliente compra 6 itens e a promoção é Leve 3 Pague 2,
     * ele passa a ter 2 pacotes de 3, pagando por 4 e ganhando 2 grátis.
     */
    @Override
    public double calcularDesconto(Venda venda) {
        // Filtra itens que pertencem a este produto e calcula o desconto
        return venda.getItens().stream()
            .filter(item -> item.getProduto().getCodigo() == this.codigoProduto)
            .mapToDouble(item -> {
                int qtdComprada = item.getQuantidade();
                // Só há desconto se foi comprada pelo menos a quantidade X
                if (qtdComprada >= quantidadeX) {
                    // Quantos pacotes completos de X itens o cliente comprou
                    int pacotes = qtdComprada / quantidadeX;
                    // Quantos produtos saem grátis em cada pacote (diferença entre X e Y)
                    int produtosGratisPorPacote = quantidadeX - quantidadeY;
                    // Total de produtos grátis
                    int totalProdutosGratis = pacotes * produtosGratisPorPacote;
                    // Desconto é o preço dos produtos grátis
                    return totalProdutosGratis * item.getProduto().getPreco();
                }
                // Se não completou o mínimo, sem desconto
                return 0.0;
            })
            .sum();
    }

    /**
     * Representação em texto da promoção Leve X Pague Y.
     */
    @Override
    public String toString() {
        return "Leve " + quantidadeX + " Pague " + quantidadeY + " (Produto Cód " + codigoProduto + ")";
    }

    /**
     * Retorna o código do produto desta promoção.
     * @return o código do produto
     */
    public int getCodigoProduto() {
        return codigoProduto;
    }
}
