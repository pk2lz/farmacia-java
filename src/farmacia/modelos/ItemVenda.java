package farmacia.modelos;

import java.io.Serializable;

/**
 * Classe ItemVenda - representa um item individual dentro de uma venda.
 * Associa um produto a uma quantidade, permitindo calcular o valor total do item.
 */
public class ItemVenda implements Serializable {
    private static final long serialVersionUID = 1L;

    // O produto sendo vendido
    private Produto produto;
    
    // Quantidade desse produto na venda
    private int quantidade;

    /**
     * Construtor de ItemVenda.
     * @param produto o produto sendo vendido
     * @param quantidade a quantidade desse produto
     */
    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    /**
     * Retorna o produto deste item.
     * @return o Produto associado
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Modifica o produto deste item.
     * @param produto o novo produto
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Retorna a quantidade de unidades do produto.
     * @return a quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Modifica a quantidade de unidades.
     * @param quantidade a nova quantidade
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Calcula o valor total deste item (sem descontos).
     * Multiplica o preço unitário do produto pela quantidade.
     * @return preço do produto * quantidade
     */
    public double getValorTotal() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return quantidade + "x " + produto.getNome() + " (Cód: " + produto.getCodigo() + ") = " + getValorTotal();
    }
}
