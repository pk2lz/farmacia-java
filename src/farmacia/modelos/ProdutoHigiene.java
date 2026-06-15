package farmacia.modelos;

/**
 * Classe ProdutoHigiene - representa um produto de higiene pessoal ou limpeza.
 * Herda de Produto e não adiciona atributos extras.
 * Exemplos: shampoo, sabonete, desinfetante, etc.
 * Diferencia-se de medicamentos por não serem para fins terapêuticos.
 */
public class ProdutoHigiene extends Produto {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor de ProdutoHigiene.
     * @param codigo o código do produto
     * @param nome o nome do produto
     * @param fabricante o nome do fabricante
     * @param preco o preço do produto
     */
    public ProdutoHigiene(int codigo, String nome, String fabricante, double preco) {
        super(codigo, nome, fabricante, preco);
    }

    /**
     * Representação em texto do produto de higiene.
     */
    @Override
    public String toString() {
        return "ProdutoHigiene [" + super.toString() + "]";
    }
}
