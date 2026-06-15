package farmacia.modelos;

/**
 * Classe MedicamentoControlado - representa um medicamento que exige receita médica.
 * Herda de Medicamento e não adiciona atributos extras, apenas diferencia-se pela regulação
 * que requer receita médica para venda (medicamentos mais perigosos).
 * Restrição: não podem ser vendidos online, apenas presencialmente com apresentação de receita.
 */
public class MedicamentoControlado extends Medicamento {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor de MedicamentoControlado.
     * @param codigo o código único do medicamento
     * @param nome o nome do medicamento
     * @param fabricante o fabricante
     * @param preco o preço
     * @param principioAtivo o princípio ativo
     * @param dosagem a dosagem
     */
    public MedicamentoControlado(int codigo, String nome, String fabricante, double preco, String principioAtivo, String dosagem) {
        super(codigo, nome, fabricante, preco, principioAtivo, dosagem);
    }

    /**
     * Representação em texto do medicamento controlado.
     */
    @Override
    public String toString() {
        return "MedicamentoControlado [" + super.toString() + "]";
    }
}
