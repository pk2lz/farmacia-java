package farmacia.promocoes;

import farmacia.modelos.Venda;

/**
 * Classe PromocaoPercentual - implementa uma promoção de desconto percentual.
 * Aplica um percentual de desconto em um produto específico.
 * Exemplo: 15% de desconto no produto código 001.
 */
public class PromocaoPercentual implements Promocao {
    private static final long serialVersionUID = 1L;

    // Código do produto que receberá o desconto percentual
    private int codigoProduto;
    
    // Percentual de desconto (ex: 10.0 para 10%, 15.5 para 15,5%)
    private double percentualDesconto;

    /**
     * Construtor de PromocaoPercentual.
     * @param codigoProduto o código do produto que terá desconto
     * @param percentualDesconto o percentual de desconto (ex: 15 para 15%)
     */
    public PromocaoPercentual(int codigoProduto, double percentualDesconto) {
        this.codigoProduto = codigoProduto;
        this.percentualDesconto = percentualDesconto;
    }

    /**
     * Calcula o desconto percentual para todos os itens da venda que correspondem ao produto da promoção.
     * Para cada item que pertence ao produto da promoção, calcula o desconto sobre seu valor total.
     */
    @Override
    public double calcularDesconto(Venda venda) {
        // Filtra itens que pertencem a este produto, calcula valor total de cada item
        // e aplica o percentual de desconto, depois soma todos os descontos
        return venda.getItens().stream()
            .filter(item -> item.getProduto().getCodigo() == this.codigoProduto)
            .mapToDouble(item -> item.getValorTotal() * (percentualDesconto / 100.0))
            .sum();
    }

    /**
     * Representação em texto da promoção percentual.
     */
    @Override
    public String toString() {
        return percentualDesconto + "% de desconto (Produto Cód " + codigoProduto + ")";
    }

    /**
     * Retorna o código do produto desta promoção.
     * @return o código do produto
     */
    public int getCodigoProduto() {
        return codigoProduto;
    }
}
