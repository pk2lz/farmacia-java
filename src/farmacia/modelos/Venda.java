package farmacia.modelos;

import farmacia.promocoes.Promocao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Classe abstrata que representa uma venda genérica.
 * Define o comportamento comum para vendas presenciais e online:
 * armazena itens vendidos, calcula valores com descontos e aplica taxas específicas.
 * Utiliza o padrão Template Method com o método calcularValores().
 */
public abstract class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    // Código único identificador da venda
    private int codigo;
    
    // Lista de itens inclusos nesta venda (produto + quantidade)
    private List<ItemVenda> itens;
    
    // Valor total da venda sem aplicar descontos (apenas somatório dos itens)
    private double valorTotalSemDesconto;
    
    // Valor final da venda após descontos e aplicação de taxas específicas
    private double valorTotalComDesconto;
    
    // Valor total economizado através de promoções e descontos aplicados
    private double valorDesconto;

    /**
     * Construtor da classe Venda.
     * @param codigo o código único identificador da venda
     */
    public Venda(int codigo) {
        this.codigo = codigo;
        // Inicializa com lista vazia de itens
        this.itens = new ArrayList<>();
    }

    /**
     * Adiciona um item (produto com quantidade) à venda.
     * @param item o ItemVenda a adicionar
     */
    public void adicionarItem(ItemVenda item) {
        this.itens.add(item);
    }

    public int getCodigo() {
        return codigo;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public double getValorTotalSemDesconto() {
        return valorTotalSemDesconto;
    }

    public double getValorTotalComDesconto() {
        return valorTotalComDesconto;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    /**
     * Calcula o total sem e com desconto utilizando streams e promoções.
     * Aplica todas as promoções ativas e calcula o valor final considerando taxas específicas.
     * @param promocoes a lista de promoções ativas no sistema
     */
    public void calcularValores(List<Promocao> promocoes) {
        // Calcula valor total sem desconto usando streams (soma dos itens)
        this.valorTotalSemDesconto = itens.stream()
            .mapToDouble(ItemVenda::getValorTotal)
            .sum();

        // Calcula desconto total aplicando todas as promoções disponíveis
        this.valorDesconto = Optional.ofNullable(promocoes)
            .orElse(List.of())
            .stream()
            .mapToDouble(promocao -> promocao.calcularDesconto(this))
            .sum();

        // Garante que o desconto não seja maior que o valor total da venda
        if (this.valorDesconto > this.valorTotalSemDesconto) {
            this.valorDesconto = this.valorTotalSemDesconto;
        }

        // Calcula valor com desconto (antes de taxas)
        this.valorTotalComDesconto = this.valorTotalSemDesconto - this.valorDesconto;
        
        // Chama método abstrato para subclasses aplicarem suas taxas específicas
        // (por exemplo, taxa de entrega para vendas online)
        aplicarTaxasExtras();
    }

    /**
     * Método gancho (hook) para subclasses aplicarem taxas adicionais.
     * Exemplo: VendaOnline adiciona taxa de entrega, VendaPresencial não adiciona nada.
     */
    protected abstract void aplicarTaxasExtras();

    /**
     * Define manualmente o valor total com desconto (usado pelas subclasses para somar taxas).
     * @param valor o novo valor total
     */
    protected void setValorTotalComDesconto(double valor) {
        this.valorTotalComDesconto = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return codigo == venda.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
