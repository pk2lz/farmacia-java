package farmacia.excecoes;

/**
 * Classe ClienteNaoEncontradoException - exceção lançada quando um cliente não é encontrado.
 * Herda de RegraNegocioException e é usada durante operações de busca ou vendas online
 * quando o CPF fornecido não corresponde a nenhum cliente cadastrado.
 */
public class ClienteNaoEncontradoException extends RegraNegocioException {
    /**
     * Construtor da exceção.
     * @param mensagem a mensagem explicativa
     */
    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
