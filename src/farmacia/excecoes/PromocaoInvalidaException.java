package farmacia.excecoes;

/**
 * Classe PromocaoInvalidaException - exceção lançada quando uma promoção inválida é tentada.
 * Herda de RegraNegocioException e é usada quando há violação de regras de promoção,
 * como tentar aplicar \"Leve X Pague Y\" a um medicamento controlado.
 */
public class PromocaoInvalidaException extends RegraNegocioException {
    /**
     * Construtor da exceção.
     * @param mensagem a mensagem explicativa
     */
    public PromocaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
