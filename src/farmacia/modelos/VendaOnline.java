package farmacia.modelos;

/**
 * Classe VendaOnline - representa uma venda realizada pela internet.
 * Herda de Venda e adiciona funcionalidades específicas: cliente identificado e taxa de entrega.
 * A taxa de entrega é calculada dinamicamente baseada na quantidade de produtos.
 */
public class VendaOnline extends Venda {
    private static final long serialVersionUID = 1L;

    // Cliente que realizou a compra online (necessário para envio)
    private Cliente cliente;
    
    // Taxa de entrega calculada baseada na quantidade total de produtos
    private double taxaEntrega;

    /**
     * Construtor de VendaOnline.
     * @param codigo o código único da venda
     * @param cliente o cliente que realizou a compra (com endereço para entrega)
     */
    public VendaOnline(int codigo, Cliente cliente) {
        super(codigo);
        this.cliente = cliente;
    }

    /**
     * Retorna o cliente da compra.
     * @return o cliente associado a esta venda
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna o valor da taxa de entrega.
     * @return o valor da taxa cobrada
     */
    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    /**
     * Calcula e aplica a taxa de entrega ao valor final da venda.
     * A taxa é fixa de R$ 10 para cada faixa de até 20 produtos.
     * Exemplo: 1-20 produtos = R$ 10; 21-40 produtos = R$ 20; 41-60 produtos = R$ 30.
     */
    @Override
    protected void aplicarTaxasExtras() {
        // Calcula o total de produtos usando streams
        int totalProdutos = getItens().stream()
            .mapToInt(ItemVenda::getQuantidade)
            .sum();

        // Taxa de entrega: R$10 para cada 20 produtos (ou fração)
        if (totalProdutos > 0) {
            // Calcula quantas faixas de 20 são necessárias (arredonda para cima)
            int multiplicador = ((totalProdutos - 1) / 20) + 1;
            this.taxaEntrega = multiplicador * 10.0;
        } else {
            this.taxaEntrega = 0.0;
        }

        // A taxa de entrega é adicionada ao valor total FINAL da compra (após descontos)
        setValorTotalComDesconto(getValorTotalComDesconto() + this.taxaEntrega);
    }

    @Override
    public String toString() {
        return "VendaOnline [codigo=" + getCodigo() + ", cliente=" + cliente.getNome() + 
               ", totalSemDesconto=" + getValorTotalSemDesconto() + 
               ", taxaEntrega=" + taxaEntrega + 
               ", totalComDesconto(comTaxa)=" + getValorTotalComDesconto() + "]";
    }
}
