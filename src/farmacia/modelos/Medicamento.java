package farmacia.modelos;

/**
 * Classe abstrata Medicamento - representa um medicamento genérico.
 * Herda de Produto e adiciona características específicas de medicamentos:
 * princípio ativo e dosagem. É base para MedicamentoBasico e MedicamentoControlado.
 */
public abstract class Medicamento extends Produto {
    private static final long serialVersionUID = 1L;

    // Princípio ativo do medicamento (substância que causa efeito terapêutico)
    private String principioAtivo;
    
    // Dosagem do medicamento (quantidade de princípio ativo, ex: 500mg)
    private String dosagem;

    /**
     * Construtor de Medicamento.
     * @param codigo o código único do medicamento
     * @param nome o nome do medicamento
     * @param fabricante o fabricante do medicamento
     * @param preco o preço do medicamento
     * @param principioAtivo o princípio ativo do medicamento
     * @param dosagem a dosagem do medicamento
     */
    public Medicamento(int codigo, String nome, String fabricante, double preco, String principioAtivo, String dosagem) {
        super(codigo, nome, fabricante, preco);
        this.principioAtivo = principioAtivo;
        this.dosagem = dosagem;
    }

    /**
     * Retorna o princípio ativo do medicamento.
     * @return o princípio ativo
     */
    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    /**
     * Modifica o princípio ativo do medicamento.
     * @param principioAtivo o novo princípio ativo
     */
    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    /**
     * Retorna a dosagem do medicamento.
     * @return a dosagem
     */
    public String getDosagem() {
        return dosagem;
    }

    /**
     * Modifica a dosagem do medicamento.
     * @param dosagem a nova dosagem
     */
    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    /**
     * Representação em texto incluindo informações de medicamento.
     */
    @Override
    public String toString() {
        return super.toString() + ", principioAtivo=" + principioAtivo + ", dosagem=" + dosagem;
    }
}
