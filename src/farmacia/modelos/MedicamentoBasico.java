package farmacia.modelos;

/**
 * Classe MedicamentoBasico - representa um medicamento que não exige receita.
 * Herda de Medicamento e não adiciona atributos extras, apenas diferencia-se pela disponibilidade
 * de compra sem necessidade de receita médica (venda livre).
 */
public class MedicamentoBasico extends Medicamento {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor de MedicamentoBasico.
     * @param codigo o código único do medicamento
     * @param nome o nome do medicamento
     * @param fabricante o fabricante
     * @param preco o preço
     * @param principioAtivo o princípio ativo
     * @param dosagem a dosagem
     */
    public MedicamentoBasico(int codigo, String nome, String fabricante, double preco, String principioAtivo, String dosagem) {
        super(codigo, nome, fabricante, preco, principioAtivo, dosagem);
    }

    /**
     * Representação em texto do medicamento básico.
     */
    @Override
    public String toString() {
        return "MedicamentoBasico [" + super.toString() + "]";
    }
}
