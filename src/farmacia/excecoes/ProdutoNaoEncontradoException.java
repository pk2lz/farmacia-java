package farmacia.excecoes;

/**
 * Classe ProdutoNaoEncontradoException - exceção lançada quando um produto não é encontrado.
 * Herda de RegraNegocioException e é usada durante operações de busca ou ao tentar
 * adicionar um produto a uma venda quando o código fornecido não existe no sistema.
 */
public class ProdutoNaoEncontradoException extends RegraNegocioException {
    /**
     * Construtor da exceção.
     * @param mensagem a mensagem explicativa
     */
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
