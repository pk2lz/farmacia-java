package farmacia.excecoes;

/**
 * Classe RegraNegocioException - exceção customizada para erros de regras de negócio.
 * É lançada quando há violação de regras específicas do sistema de farmácia,
 * como tentativa de cadastrar cliente com CPF duplicado, produto com código duplicado, etc.
 */
public class RegraNegocioException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da exceção.
     * @param mensagem a mensagem explicativa do erro de negócio
     */
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
