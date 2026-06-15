package farmacia.modelos;

/**
 * Classe VendaPresencial - representa uma venda realizada diretamente na loja.
 * Herda de Venda e não adiciona taxas extras, apenas calcula descontos normalmente.
 * É simples e direta: cliente compra na loja, não há taxa de entrega.
 */
public class VendaPresencial extends Venda {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor de VendaPresencial.
     * @param codigo o código único da venda
     */
    public VendaPresencial(int codigo) {
        super(codigo);
    }

    /**
     * Não aplica taxas extras para vendas presenciais.
     * O valor final já foi calculado com descontos no método calcularValores() da classe pai.
     */
    @Override
    protected void aplicarTaxasExtras() {
        // Vendas presenciais não possuem taxas adicionais - nada a fazer aqui
    }

    @Override
    public String toString() {
        return "VendaPresencial [codigo=" + getCodigo() + ", totalSemDesconto=" + getValorTotalSemDesconto() + 
               ", totalComDesconto=" + getValorTotalComDesconto() + "]";
    }
}
